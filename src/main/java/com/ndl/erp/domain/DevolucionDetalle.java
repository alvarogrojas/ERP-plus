package com.ndl.erp.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
public class DevolucionDetalle {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "devolucion_id", referencedColumnName = "id")
    private Devolucion devolucion;

    @OneToOne
    @JoinColumn(name="inventario_id", referencedColumnName="id")
    private Inventario inventario;


    @OneToOne
    @JoinColumn(name="motivo_id", referencedColumnName="id")
    private MotivoDevolucion motivoDevolucion;

    private Integer lineaParentId;
    private Double cantidad;
    private Double precio;
    private boolean ingresadoBodega;


    public DevolucionDetalle() {
    }

    public DevolucionDetalle(RequisicionBodegaDetalle d) {
        this.lineaParentId = d.getId();
        this.cantidad = d.getCantidadDespachada();
        this.ingresadoBodega = false;
        this.inventario = d.getInventario();
        this.precio = d.getPrecioUnidad();

    }

    public DevolucionDetalle(InvoiceNotaCreditoDetail d) {
        this.lineaParentId = d.getId();
        this.cantidad = d.getQuantity();
        this.ingresadoBodega = false;
        this.inventario = d.getInventario();
        this.precio = d.getPrice();


    }

    public Inventario getInventario() {
        return inventario;
    }

    public void setInventario(Inventario inventario) {
        this.inventario = inventario;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Devolucion getDevolucion() {
        return devolucion;
    }

    public void setDevolucion(Devolucion devolucion) {
        this.devolucion = devolucion;
    }


    public MotivoDevolucion getMotivoDevolucion() {
        return motivoDevolucion;
    }

    public void setMotivoDevolucion(MotivoDevolucion motivoDevolucion) {
        this.motivoDevolucion = motivoDevolucion;
    }

    public Integer getLineaParentId() {
        return lineaParentId;
    }

    public void setLineaParentId(Integer lineaParentId) {
        this.lineaParentId = lineaParentId;
    }

    public Double getCantidad() {
        return cantidad;
    }

    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public boolean isIngresadoBodega() {
        return ingresadoBodega;
    }

    public void setIngresadoBodega(boolean ingresadoBodega) {
        this.ingresadoBodega = ingresadoBodega;
    }
}
