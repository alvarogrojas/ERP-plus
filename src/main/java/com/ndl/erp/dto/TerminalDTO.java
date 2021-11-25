package com.ndl.erp.dto;

import com.ndl.erp.domain.Bodega;
import com.ndl.erp.domain.Terminal;
import com.ndl.erp.domain.TerminalUsuario;
import com.ndl.erp.domain.User;

import java.util.List;

public class TerminalDTO {

    private List<User> cajeros;

    private List<String> estados;
    private List<Bodega> bodegas;


    private Terminal current;
    private List<TerminalUsuario> usuariosCurrentTerminal;
    private List<User> usuarios;

    public List<User> getCajeros() {
        return cajeros;
    }

    public void setCajeros(List<User> cajeros) {
        this.cajeros = cajeros;
    }

    public List<String> getEstados() {
        return estados;
    }

    public void setEstados(List<String> estados) {
        this.estados = estados;
    }

    public List<Bodega> getBodegas() {
        return bodegas;
    }

    public void setBodegas(List<Bodega> bodegas) {
        this.bodegas = bodegas;
    }

    public Terminal getCurrent() {
        return current;
    }

    public void setCurrent(Terminal current) {
        this.current = current;
    }

    public void setUsuarios(List<User> usersByRoles) {
        this.usuarios = usersByRoles;
        this.cajeros = usersByRoles;
    }

    public List<User> getUsuarios() {
        return usuarios;
    }


    public List<TerminalUsuario> getUsuariosCurrentTerminal() {
        return usuariosCurrentTerminal;
    }

    public void setUsuariosCurrentTerminal(List<TerminalUsuario> usuariosCurrentTerminal) {
        this.usuariosCurrentTerminal = usuariosCurrentTerminal;
    }
}
