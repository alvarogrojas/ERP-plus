package com.ndl.erp.controller;

import com.ndl.erp.domain.User;
import com.ndl.erp.dto.UserDTO;
import com.ndl.erp.dto.UsersDTO;
import com.ndl.erp.services.UserServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserServiceImpl userService;


    @PostMapping(path="/save")
    public @ResponseBody
    User create(@RequestBody User c) {
        return userService.save(c);
    }


    @GetMapping(path = "/data/list-page")
    public @ResponseBody
    UsersDTO getUsereList(@RequestParam(value = "filter", required = false) String filter,
                              @RequestParam Integer pageNumber,
                              @RequestParam Integer pageSize,
                              @RequestParam String sortDirection,
                              @RequestParam String sortField) {
        return userService.getUsers(filter, pageNumber, pageSize, sortDirection, sortField);
    }

    @GetMapping(path = "/get")
    public @ResponseBody
    UserDTO get(
            @RequestParam(value = "id", required = false) Integer id
    ) {
        return this.userService.getUser(id);
    }


@GetMapping(path = "/new-form")
    public @ResponseBody
    UserDTO getNew(

    ) {
        return this.userService.getUser(null);
    }


    @GetMapping(path = "/is-power-user")
    public @ResponseBody
    boolean getAdmin(
            @RequestParam(value = "username", required = true) String userName,
            @RequestParam(value = "password", required = true) String password

    ) throws Exception{
        return this.userService.isPowerUsuario(userName, password);
    }

    @GetMapping(path = "/is-pu-or-admin")
    public @ResponseBody
    boolean getPuOrAdmin(
            @RequestParam(value = "username", required = true) String userName,
            @RequestParam(value = "password", required = true) String password

    ) throws Exception{
        return this.userService.isPuOrAdminUsuario(userName, password);
    }

}
