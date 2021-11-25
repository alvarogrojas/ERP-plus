package com.ndl.erp.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
public class TrasladoDetalle {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;


  @JsonBackReference
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "traslado_id", referencedColumnName = "id")
  private Traslado traslado;


  @OneToOne
  @JoinColumn(name="cost_center_id", referencedColumnName = "id")
  private CostCenter costCenter;

  private Double cantidadTraslado;

  @OneToOne
  @JoinColumn(name="inventario_id", referencedColumnName = "id")
  private Inventario inventario;


  private Double costoTotal;

  private Double totalTraslado;
  @OneToOne
  @JoinColumn(name="justificacion_id", referencedColumnName = "id")
  private TrasladoJustificacion trasladoJustificacion;


  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Traslado getTraslado() {
    return traslado;
  }

  public void setTraslado(Traslado traslado) {
    this.traslado = traslado;
  }

  public CostCenter getCostCenter() {
    return costCenter;
  }

  public void setCostCenter(CostCenter costCenter) {
    this.costCenter = costCenter;
  }

  public Double getCantidadTraslado() {
    return cantidadTraslado;
  }

  public void setCantidadTraslado(Double cantidadTraslado) {
    this.cantidadTraslado = cantidadTraslado;
  }

  public Inventario getInventario() {
    return inventario;
  }

  public void setInventario(Inventario inventario) {
    this.inventario = inventario;
  }

  public Double getCostoTotal() {
    return costoTotal;
  }

  public void setCostoTotal(Double costoTotal) {
    this.costoTotal = costoTotal;
  }

  public TrasladoJustificacion getTrasladoJustificacion() {
    return trasladoJustificacion;
  }

  public void setTrasladoJustificacion(TrasladoJustificacion trasladoJustificacion) {
    this.trasladoJustificacion = trasladoJustificacion;
  }

  public Double getTotalTraslado() {
    return totalTraslado;
  }

  public void setTotalTraslado(Double totalTraslado) {
    this.totalTraslado = totalTraslado;
  }
}
