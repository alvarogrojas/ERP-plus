package com.ndl.erp.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
public class RequisicionBodegaDetalle {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "requisicion_id", referencedColumnName = "id")
    private RequisicionBodega requisicionBodega;

    @OneToOne
    @JoinColumn(name="inventario_id", referencedColumnName="id")
    private Inventario inventario;

    @OneToOne
    @JoinColumn(name="costs_center_id", referencedColumnName="id")
    private CostCenter costCenter;

    private Double cantidadSolicitada;
    private Double cantidadDespachada;
    private Double precioUnidad;
    private Double montoTotalSolicitado;
    private Double montoTotalDespachado;
    private boolean salidaBodega;
    private Double quantityReturned = 0d;


    public Inventario getInventario() {
        return inventario;
    }

    public void setInventario(Inventario inventario) {
        this.inventario = inventario;
    }

    public Double getQuantityReturned() {
        return quantityReturned;
    }

    public void setQuantityReturned(Double quantityReturned) {
        this.quantityReturned = quantityReturned;
    }

    public boolean isSalidaBodega() {
        return salidaBodega;
    }

    public void setSalidaBodega(boolean salidaBodega) {
        this.salidaBodega = salidaBodega;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public RequisicionBodega getRequisicionBodega() {
        return requisicionBodega;
    }

    public void setRequisicionBodega(RequisicionBodega requisicionBodega) {
        this.requisicionBodega = requisicionBodega;
    }


    public CostCenter getCostCenter() {
        return costCenter;
    }

    public void setCostCenter(CostCenter costCenter) {
        this.costCenter = costCenter;
    }

    public Double getCantidadSolicitada() {
        return cantidadSolicitada;
    }

    public void setCantidadSolicitada(Double cantidadSolicitada) {
        this.cantidadSolicitada = cantidadSolicitada;
    }

    public Double getCantidadDespachada() {
        return cantidadDespachada;
    }

    public void setCantidadDespachada(Double cantidadDespachada) {
        this.cantidadDespachada = cantidadDespachada;
    }

    public Double getPrecioUnidad() {
        return precioUnidad;
    }

    public void setPrecioUnidad(Double precioUnidad) {
        this.precioUnidad = precioUnidad;
    }

    public Double getMontoTotalSolicitado() {
        return montoTotalSolicitado;
    }

    public void setMontoTotalSolicitado(Double montoTotalSolicitado) {
        this.montoTotalSolicitado = montoTotalSolicitado;
    }

    public Double getMontoTotalDespachado() {
        return montoTotalDespachado;
    }

    public void setMontoTotalDespachado(Double montoTotalDespachado) {
        this.montoTotalDespachado = montoTotalDespachado;
    }
}
