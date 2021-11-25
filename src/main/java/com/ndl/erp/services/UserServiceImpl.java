package com.ndl.erp.services;


import com.ndl.erp.domain.User;
import com.ndl.erp.domain.UserRole;
import com.ndl.erp.dto.UserDTO;
import com.ndl.erp.dto.UsersDTO;
import com.ndl.erp.exceptions.NotFoundException;
import com.ndl.erp.exceptions.UserInvalidPasswordException;
import com.ndl.erp.repository.CollaboratorRepository;
import com.ndl.erp.repository.RoleRepository;
import com.ndl.erp.repository.UserRepository;
import com.ndl.erp.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.ndl.erp.constants.UserConstants.USUARIO_ADMINISTRADOR;
import static com.ndl.erp.constants.UserConstants.USUARIO_POWER_USUARIO;

@Service("userDetailsService")
public class UserServiceImpl implements UserService {

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private CollaboratorRepository collaboratorRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserRoleRepository userRoleRepository;


	public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
	}

	@Override
	public User getCurrentLoggedUser() {
		User u = null;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication==null || authentication.getPrincipal()==null) {
			//return null;
			throw new UsernameNotFoundException("Debe autenticarse nuevamente");
		}
		Object obj = authentication.getPrincipal();
		if (obj instanceof  String) {
			String usuario = (String) obj;
			u = userRepository.findByName(usuario);
			if (u==null) {
				throw new UsernameNotFoundException("Debe autenticarse nuevamente");
			}
		} else {
//			throw new RuntimeException("Principal type unexpected " + authentication.getPrincipal());
			throw new UsernameNotFoundException("Debe autenticarse nuevamente");
		}
		//u.setClave("");
		return u;
	}

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

//		final User user = userRepository.findByName(userName);
		final User user = userRepository.findByUsername(userName);
		if (user == null) {
			throw new UsernameNotFoundException("Invalid username or password");
		}

		if (user == null) {
			throw new UsernameNotFoundException(String.format("The username %s doesn't exist", userName));
		}
		if (user.getStatus().equals("Inactivo") || user.getStatus().equals("Borrado")) {
			throw new UsernameNotFoundException(String.format("El usuario no esta activo", userName));

		}
		List<GrantedAuthority> authorities = new ArrayList<>();
		List<UserRole> roles = this.userRoleRepository.findByUserId(user.getId());

		for (UserRole r : roles) {
			authorities.add(new SimpleGrantedAuthority(r.getRole().getName()));
		}
		UserDetails userDetails = new org.springframework.security.core.userdetails.
				User(user.getUsername(), user.getPassword(), authorities);
		return userDetails;
	}



	public Boolean isPowerUsuario(String userName, String password) throws Exception {

		Boolean isAdmin = false;

		final User user = userRepository.findByUsername(userName);
		if (user == null) {
			throw new UsernameNotFoundException("Nombre de usuario invalido");
		}

		if (user.getStatus().equals("Inactivo") || user.getStatus().equals("Borrado")) {
			throw new UsernameNotFoundException(String.format("El usuario no esta activo", userName));

		}

		//Revisar  que el password del usuario sea igual al de la base da datos
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		if (!bCryptPasswordEncoder.matches(password, user.getPassword())) {

			throw new UserInvalidPasswordException("La clave del usuario no es valida.");

		}

		List<UserRole> roles = user.getRoles();

		//Revisar si es power usuario
		for (UserRole r : roles) {
			if (r.getRole().getName().equals(USUARIO_POWER_USUARIO)) {
				isAdmin = true;
				break;
			}
		}


		return isAdmin;
	}

	public Boolean isPuOrAdminUsuario(String userName, String password) throws Exception {

		Boolean isAdmin = false;

		final User user = userRepository.findByUsername(userName);
		if (user == null) {
			throw new UserInvalidPasswordException("Nombre de usuario invalido");
		}

		if (user.getStatus().equals("Inactivo") || user.getStatus().equals("Borrado")) {
			throw new UserInvalidPasswordException(String.format("El usuario no esta activo", userName));

		}

		//Revisar  que el password del usuario sea igual al de la base da datos
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		if (!bCryptPasswordEncoder.matches(password, user.getPassword())) {

			throw new UserInvalidPasswordException("La clave del usuario no es valida.");

		}

		List<UserRole> roles = user.getRoles();

		//Revisar si es power usuario
		for (UserRole r : roles) {
			if (r.getRole().getName().equals(USUARIO_POWER_USUARIO) || r.getRole().getName().equals(USUARIO_ADMINISTRADOR)) {
				isAdmin = true;
				break;
			}
		}


		return isAdmin;
	}


	@Override
	public User getUser(long id) {
		return userRepository.findById(id);
	}

	@Override
	public User save(User user) {

		User user1 = this.userRepository.findByUsername(user.getUsername());
		if (user1 !=null && user.getId()==null) {
			throw new NotFoundException("El username debe ser unico y ya existe un usuario con el username " + user.getUsername());

		}
		user.setPassword(this.getEncriptPwd(user.getPassword()));

		return userRepository.save(user);
	}

	public UserDTO getUser(Integer id) {
		UserDTO d = this.getUser();
		if (id==null) {
			d.setCurrent(new User());
			return d;
		}
		User c = userRepository.findById(id);
		if (c==null) {
			return d;
		}
		d.setCurrent(c);
		return d;
	}


	public UsersDTO getUsers(String filter, Integer pageNumber,
							 Integer pageSize, String sortDirection,
							 String sortField) {

		UsersDTO d = new UsersDTO();
		d.setUsers(this.userRepository.findAll());

		d.setTotal(this.userRepository.count());
		if (d.getTotal()>0) {
			d.setPagesTotal(d.getTotal() /pageSize);
		} else {
			d.setPagesTotal(0l);
		}

		return d;

	}

	public UserDTO getUser() {

//		List<String> estados = new ArrayList<>();
//		estados.add("Activo");
//		estados.add("Inactivo");

//        List<String> exonerados = new ArrayList<>();
//        exonerados.add("SI");
//        exonerados.add("NO");
		UserDTO d = new UserDTO();

		d.setCollaborators(this.collaboratorRepository.findAll());
		d.setRoles(this.roleRepository.findAll());

//		d.setEstados(estados);
//        d.setTypesId(types);
//        d.setExonedoStates(exonerados);
		return d;
	}

//	public User save(User c) {
//
//		return this.userRepository.save(c);
//	}

	private PageRequest createPageable(Integer pageNumber, Integer pageSize, String direction, String byField) {

		return PageRequest.of(pageNumber, pageSize, direction==null || direction.equals("asc")? Sort.Direction.ASC: Sort.Direction.DESC, byField);
	}

	public String getEncriptPwd(String clave) {
		return BCrypt.hashpw(clave, BCrypt.gensalt());
//		MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
//		BCryptPasswordEncoder encoder =  new BCryptPasswordEncoder();
//		return encoder.encode(clave);
//		byte[] hash = sha256.digest(clave.getBytes(StandardCharsets.UTF_8));
//		StringBuffer hexString = new StringBuffer();
//		for (int i = 0; i < hash.length; i++) {
//			String hex = Integer.toHexString(0xff & hash[i]);
//			if(hex.length() == 1) hexString.append('0');
//			hexString.append(hex);
//		}
//		return hexString.toString();
	}

	public Set<User> getUsersByRoles(List<String> roles) {
		return this.userRoleRepository.getUsersByRole(roles);
	}
}
