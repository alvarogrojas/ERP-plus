package com.ndl.erp.controller;

import com.ndl.erp.domain.Department;
import com.ndl.erp.dto.DepartmentsDTO;
import com.ndl.erp.dto.DepartmentDTO;
import com.ndl.erp.services.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/department")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;


    @PostMapping(path="/save")
    public @ResponseBody
    Department create(@RequestBody Department c) {
        return departmentService.save(c);
    }


    @GetMapping(path = "/data/list-page")
    public @ResponseBody
    DepartmentsDTO getDepartmenteList(@RequestParam(value = "filter", required = false) String filter,
                                   @RequestParam Integer pageNumber,
                                   @RequestParam Integer pageSize,
                                   @RequestParam String sortDirection,
                                   @RequestParam String sortField) {
        return departmentService.getDepartments(filter, pageNumber, pageSize, sortDirection, sortField);
    }

    @GetMapping(path = "/get")
    public @ResponseBody
    DepartmentDTO get(
            @RequestParam(value = "id", required = false) Integer id
    ) {
        return this.departmentService.getDepartment(id);
    }


@GetMapping(path = "/new-form")
    public @ResponseBody
    DepartmentDTO getNew(


    ) {
        return this.departmentService.getDepartment();
    }


}
