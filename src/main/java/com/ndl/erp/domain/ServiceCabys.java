package com.ndl.erp.domain;


import javax.persistence.*;


@Entity
public class ServiceCabys {

    @Id
    @GeneratedValue
    private Integer id;


    @OneToOne
    @JoinColumn(name="cabys_id", referencedColumnName="id")
    private Cabys cabys;

    private String impuesto;
    private String descripcion;

    private String codigoCabys;
    private String codigoIngpro;
    private String status;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Cabys getCabys() {
        return cabys;
    }

    public void setCabys(Cabys cabys) {
        this.cabys = cabys;
    }

    //    public Integer getCabysId() {
//        return cabysId;
//    }
//
//    public void setCabysId(Integer cabysId) {
//        this.cabysId = cabysId;
//    }

    public String getImpuesto() {
        return impuesto;
    }

    public void setImpuesto(String impuesto) {
        this.impuesto = impuesto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCodigoCabys() {
        return codigoCabys;
    }

    public void setCodigoCabys(String codigoCabys) {
        this.codigoCabys = codigoCabys;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCodigoIngpro() {
        return codigoIngpro;
    }

    public void setCodigoIngpro(String codigoIngpro) {
        this.codigoIngpro = codigoIngpro;
    }
}
