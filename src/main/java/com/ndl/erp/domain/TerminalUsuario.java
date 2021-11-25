package com.ndl.erp.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.Set;

@Entity
public class TerminalUsuario {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;


//    @OneToOne
   // @JsonBackReference
    @ManyToOne
    @JoinColumn(name="usuario_id", referencedColumnName="id")
    private User usuario;

//    @JsonBackReference

    @OneToOne
    @JoinColumn(name = "terminal_id", referencedColumnName = "id")
    private Terminal terminal;

    public Terminal getTerminal() {
        return terminal;
    }

    public void setTerminal(Terminal terminal) {
        this.terminal = terminal;
    }

    private String estado;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUsuario() {
        return usuario;
    }

    public void setUsuario(User usuario) {
        this.usuario = usuario;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
