package com.ndl.erp.domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class PermissionDetail implements Serializable{


//    private Integer permissionId;

    @Id
    private Integer id;

    @OneToOne
    @JoinColumn(name="permission_id", referencedColumnName="id")
    public Permission permission;

    @OneToOne
    @JoinColumn(name="role_id", referencedColumnName="id")
    public Role role;

    public Permission getPermission() {
        return permission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    //    private Integer roleId;

//    public Integer getPermissionId() {
//        return permissionId;
//    }
//
//    public void setPermissionId(Integer permissionId) {
//        this.permissionId = permissionId;
//    }
//
//    public Integer getRoleId() {
//        return roleId;
//    }
//
//    public void setRoleId(Integer roleId) {
//        this.roleId = roleId;
//    }
}
