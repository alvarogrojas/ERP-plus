package com.ndl.erp.services;

import com.ndl.erp.domain.Department;
import com.ndl.erp.dto.DepartmentsDTO;
import com.ndl.erp.dto.DepartmentDTO;
import com.ndl.erp.repository.DepartmentRepository;
import com.ndl.erp.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private UserServiceImpl userDetailsService;


    public DepartmentDTO getDepartment(Integer id) {
        DepartmentDTO d = this.getDepartment();
        Optional<Department> c = departmentRepository.findById(id);
        if (c==null) {
            return d;
        }
        d.setCurrent(c.get());
        return d;
    }

    public DepartmentsDTO getDepartments(String filter, Integer pageNumber,
                                      Integer pageSize, String sortDirection,
                                      String sortField) {

        DepartmentsDTO d = new DepartmentsDTO();

        d.setPage(this.departmentRepository.findUsingFilterPageable(filter,
                createPageable(pageNumber, pageSize, sortDirection, sortField)));

        d.setTotal(this.departmentRepository.countAllByFilter(filter));
        if (d.getTotal()>0) {
            d.setPagesTotal(d.getTotal() /pageSize);
        } else {
            d.setPagesTotal(0);
        }

        return d;

    }

    public DepartmentDTO getDepartment() {
        DepartmentDTO d = new DepartmentDTO();
        d.setCurrent(new Department());
        return d;
    }

    public Department save(Department c) {

        if (c.getId()==null) {
            c.setCreateDate(new java.sql.Date(DateUtil.getCurrentCalendar().getTime().getTime()));
            c.setIdUserRegister(new Long(userDetailsService.getCurrentLoggedUser().getId()).intValue());
        }
        return this.departmentRepository.save(c);
    }

    private PageRequest createPageable(Integer pageNumber, Integer pageSize, String direction, String byField) {

        return PageRequest.of(pageNumber, pageSize, direction==null || direction.equals("asc")? Sort.Direction.ASC: Sort.Direction.DESC, byField);
    }
}
