package com.ndl.erp.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.List;

@Entity
public class InitInventarioAtributo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "init_inventario_detalle_id", referencedColumnName = "id")
    private InitInventarioDetalle initInventarioDetalle;


    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL,
            mappedBy = "initInventarioDetalle", orphanRemoval = true)
    private List<InitInventarioAtributo> atributos;

    private String nombre;
    private String valor;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public InitInventarioDetalle getInitInventarioDetalle() {
        return initInventarioDetalle;
    }

    public void setInitInventarioDetalle(InitInventarioDetalle initInventarioDetalle) {
        this.initInventarioDetalle = initInventarioDetalle;
    }

    public List<InitInventarioAtributo> getAtributos() {
        return atributos;
    }

    public void setAtributos(List<InitInventarioAtributo> atributos) {
        this.atributos = atributos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }
}
