package com.ndl.erp.controller;

import com.ndl.erp.domain.Role;
import com.ndl.erp.dto.RoleDTO;
import com.ndl.erp.dto.RolesDTO;
import com.ndl.erp.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;


    @PostMapping(path="/save")
    public @ResponseBody
    Role create(@RequestBody Role c) {
        return roleService.save(c);
    }


    @GetMapping(path = "/data/list-page")
    public @ResponseBody
    RolesDTO getRoleeList(@RequestParam(value = "filter", required = false) String filter,
                              @RequestParam Integer pageNumber,
                              @RequestParam Integer pageSize,
                              @RequestParam String sortDirection,
                              @RequestParam String sortField) {
        return roleService.getRoles(filter, pageNumber, pageSize, sortDirection, sortField);
    }

    @GetMapping(path = "/get")
    public @ResponseBody
    RoleDTO getCentroCostos(
            @RequestParam(value = "id", required = false) Integer id
    ) {
        return this.roleService.getRole(id);
    }


@GetMapping(path = "/new-form")
    public @ResponseBody
    RoleDTO getNewCentroCostos(


    ) {
        return this.roleService.getRole();
    }


}
