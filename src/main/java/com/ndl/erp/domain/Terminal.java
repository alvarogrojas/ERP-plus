package com.ndl.erp.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Terminal {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @OneToOne
    @JoinColumn(name="bodega_id", referencedColumnName="id")
    private Bodega bodega;

    private String nombre;
    private String direccion;
    private String estado;
    private Date createAt;
    private Date updateAt;

//    @JsonManagedReference
//    @OneToMany(cascade = CascadeType.ALL,
//            mappedBy = "terminal", orphanRemoval = true)
//    @JsonManagedReference
//    @OneToMany(cascade = CascadeType.ALL,
//        mappedBy = "usuario", orphanRemoval = true)
//    private List<TerminalUsuario> terminalUsuarios = new ArrayList<TerminalUsuario>(0);
//
//    public List<TerminalUsuario> getTerminalUsuarios() {
//        return terminalUsuarios;
//    }
//
//    public void setTerminalUsuarios(List<TerminalUsuario> terminalUsuarios) {
//        this.terminalUsuarios = terminalUsuarios;
//    }

    public Bodega getBodega() {
        return bodega;
    }

    public void setBodega(Bodega bodega) {
        this.bodega = bodega;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }


    public Date getUpdateAt() {

        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }
}

