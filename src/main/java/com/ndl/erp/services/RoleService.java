package com.ndl.erp.services;

import com.ndl.erp.domain.Role;
import com.ndl.erp.dto.RoleDTO;
import com.ndl.erp.dto.RolesDTO;
import com.ndl.erp.exceptions.NotFoundException;
import com.ndl.erp.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;


    public RoleDTO getRole(Integer id) {
        RoleDTO d = this.getRole();
        Optional<Role> c = roleRepository.findById(new Long(id));
        if (c==null) {
            return d;
        }
        d.setCurrent(c.get());
        return d;
    }


    public RolesDTO getRoles(String filter, Integer pageNumber,
                                           Integer pageSize, String sortDirection,
                                 String sortField) {

        RolesDTO d = new RolesDTO();

        d.setRolesPage(this.roleRepository.findUsingFilterPageable(filter,
                createPageable(pageNumber, pageSize, sortDirection, sortField)));

        d.setTotal(this.roleRepository.countAllByFilter(filter));
        if (d.getTotal()>0) {
            d.setPagesTotal(d.getTotal() /pageSize);
        } else {
            d.setPagesTotal(0);
        }

        return d;

    }

    public RoleDTO getRole() {


            RoleDTO d = new RoleDTO();
            d.setCurrent(new Role());

        return d;
    }

    public Role save(Role c) {

        c.setName(c.getName().toUpperCase());

        Role r = this.roleRepository.findByName(c.getName());

        if (r!=null && c.getId()==null) {
            throw new NotFoundException("No se puede agregar un role que ya existe");
        }

        return this.roleRepository.save(c);
    }

    private PageRequest createPageable(Integer pageNumber, Integer pageSize, String direction, String byField) {

        return PageRequest.of(pageNumber, pageSize, direction==null || direction.equals("asc")? Sort.Direction.ASC: Sort.Direction.DESC, byField);
    }
}
