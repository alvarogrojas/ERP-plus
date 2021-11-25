package com.ndl.erp.dto;

import com.ndl.erp.domain.User;
import org.springframework.data.domain.Page;

import java.util.List;


public class UsersDTO {


    private List<User> users;

    private Long total;

    private Long pagesTotal;

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Long getPagesTotal() {
        return pagesTotal;
    }

    public void setPagesTotal(Long pagesTotal) {
        this.pagesTotal = pagesTotal;
    }

    //    public Page<User> getUsersPage() {
//        return usersPage;
//    }
//
//    public void setUsersPage(Page<User> usersPage) {
//        this.usersPage = usersPage;
//    }
//
//    public Integer getTotal() {
//        return total;
//    }
//
//    public void setTotal(Integer total) {
//        this.total = total;
//    }
//
//    public Integer getPagesTotal() {
//        return pagesTotal;
//    }
//
//    public void setPagesTotal(Integer pagesTotal) {
//        this.pagesTotal = pagesTotal;
//    }
}
