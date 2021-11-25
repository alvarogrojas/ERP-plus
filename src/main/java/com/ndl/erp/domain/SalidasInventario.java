package com.ndl.erp.domain;

import javax.persistence.*;
import java.sql.Date;

@Entity
public class SalidasInventario {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;


    @OneToOne
    @JoinColumn(name="usuario_id", referencedColumnName="id")
    private User user;

    @OneToOne
    @JoinColumn(name="tipo_documento_id", referencedColumnName="id")
    private TipoDocumento tipoDocumento;

    @OneToOne
    @JoinColumn(name="inventario_id", referencedColumnName="id")
    private Inventario inventario;




    private String detalle;
    private Double cantidad;
    private Double costo;
    private Double total;
    private Date fecha;
    private Integer documentoOrigenId;
    private Integer ventaDetalleId;
    private Integer costsCenterId;

    public Integer getCostsCenterId() {
        return costsCenterId;
    }

    public void setCostsCenterId(Integer costsCenterId) {
        this.costsCenterId = costsCenterId;
    }

    public Inventario getInventario() {
        return inventario;
    }

    public void setInventario(Inventario inventario) {
        this.inventario = inventario;
    }

    public Integer getDocumentoOrigenId() {
        return documentoOrigenId;
    }

    public void setDocumentoOrigenId(Integer documentoOrigenId) {
        this.documentoOrigenId = documentoOrigenId;
    }

    public Integer getVentaDetalleId() {
        return ventaDetalleId;
    }

    public void setVentaDetalleId(Integer ventaDetalleId) {
        this.ventaDetalleId = ventaDetalleId;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public TipoDocumento getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(TipoDocumento tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public Double getCantidad() {
        return cantidad;
    }

    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }

    public Double getCosto() {
        return costo;
    }

    public void setCosto(Double costo) {
        this.costo = costo;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}
