package com.ndl.erp.services;

import com.ndl.erp.domain.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

	User getUser(long id);

	User save(User user);

	User getCurrentLoggedUser();

	Boolean isPowerUsuario(String username, String password) throws Exception ;

}
