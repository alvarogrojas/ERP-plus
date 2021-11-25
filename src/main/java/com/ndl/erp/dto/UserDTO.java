package com.ndl.erp.dto;

import com.ndl.erp.domain.Collaborator;
import com.ndl.erp.domain.Role;
import com.ndl.erp.domain.User;

import java.util.List;

public class UserDTO {

    private List<Collaborator> collaborators;
    private List<Role> roles;


    private User current;

    public List<Collaborator> getCollaborators() {
        return collaborators;
    }

    public void setCollaborators(List<Collaborator> collaborators) {
        this.collaborators = collaborators;
    }

    public User getCurrent() {
        return current;
    }

    public void setCurrent(User current) {
        this.current = current;
    }


    public void setRoles(List<Role> all) {
        this.roles = all;
    }

    public List<Role> getRoles() {
        return roles;
    }
}
