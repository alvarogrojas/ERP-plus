package com.ndl.erp.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class FamiliaProducto {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    private String nombre;
    private Integer productoId;
    private String codigo;
    private String estado;

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }


//    @OneToMany(cascade = CascadeType.ALL,
//            mappedBy = "familiaProducto", orphanRemoval = true)
//    private List<DescuentoFamiliaCliente> descuentoFamiliaCliente = new ArrayList<DescuentoFamiliaCliente>(0);
//
//    public List<DescuentoFamiliaCliente> getDescuentoFamiliaCliente() {
//        return descuentoFamiliaCliente;
//    }
//
//    public void setDescuentoFamiliaCliente(List<DescuentoFamiliaCliente> descuentoFamiliaCliente) {
//        this.descuentoFamiliaCliente = descuentoFamiliaCliente;
//    }

    public FamiliaProducto(Integer id, String nombre, Integer productoId, String codigo) {
        this.id = id;
        this.nombre = nombre;
        this.productoId = productoId;
        this.codigo = codigo;
    }

    public FamiliaProducto() {}

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

    public Integer getProductoId() {
        return productoId;
    }

    public void setProductoId(Integer productoId) {
        this.productoId = productoId;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
}
