package com.ndl.erp.domain;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import java.io.Serializable;


@Entity
public class Permission implements Serializable {

    private String resourceCode;
    private String action;

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    public Permission() {}

//    private Set<Role> roles =
//            new HashSet<Role>(0);

//    public Set<Role> getRoles() {
//        return roles;
//    }
//
//    public void setRoles(Set<Role> roles) {
//        this.roles = roles;
//    }

    public String getResourceCode() {
        return resourceCode;
    }

    public void setResourceCode(String resourceCode) {
        this.resourceCode = resourceCode;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

//    public boolean isRoleSelected(Role r) {
//        boolean isSelected = false;
//        for (Role cr: roles) {
//            if (cr.equals(r)) {
//                isSelected = true;
//                break;
//            }
//        }
//        return isSelected;
//    }
//
//    public String getRolesToString() {
//        String result = "";
//        for (Role r: roles) {
//            result = result + (!result.isEmpty()?" - ":"") + r.toString();
//        }
//        return result;
//    }
}
