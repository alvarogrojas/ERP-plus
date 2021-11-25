package com.ndl.erp.domain;
import javax.persistence.*;

@Entity
public class DescuentoFamiliaCliente{

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

//    @OneToOne
//    @JoinColumn(name="familiaProductoId", referencedColumnName="id")
//    private FamiliaProducto familiaProducto;

    @OneToOne
    @JoinColumn(name="clientId", referencedColumnName="clientId")
    private Client client;

    private Double porcentaje;
    private String descripcion;

    public DescuentoFamiliaCliente(){}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

//    public FamiliaProducto getFamiliaProducto() {
//        return familiaProducto;
//    }
//
//    public void setFamiliaProducto(FamiliaProducto familiaProducto) {
//        this.familiaProducto = familiaProducto;
//    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Double getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(Double porcentaje) {
        this.porcentaje = porcentaje;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
