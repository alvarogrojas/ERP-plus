package com.ndl.erp.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.ndl.erp.util.DateUtil;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.ndl.erp.constants.BodegaConstants.*;

@Entity
public class Devolucion {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private String consecutivo;
    private String tipo;
    private Integer referenciaId;
    private Boolean ingresadoBodega;
    private Date fechaUltimaActualizacion;
    private Date fechaDevolucion;

    @OneToOne
    @JoinColumn(name="bodega_id", referencedColumnName="id")
    private Bodega bodega;

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL,
            mappedBy = "devolucion", orphanRemoval = true)
    private List<DevolucionDetalle> details = new ArrayList<DevolucionDetalle>(0);

    private String estado;
    private Double monto;

    @OneToOne
    @JoinColumn(name="ingresado_por", referencedColumnName="id")
    private User ingresadoPor;

    @OneToOne
    @JoinColumn(name="ultima_actualizacion_por", referencedColumnName="id")
    private User ultimaActualizacionPor;

    public Date getFechaDevolucion() {
        return fechaDevolucion;
    }

    public void setFechaDevolucion(Date fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }

    public Date getFechaUltimaActualizacion() {
        return fechaUltimaActualizacion;
    }

    public void setFechaUltimaActualizacion(Date fechaUltimaActualizacion) {
        this.fechaUltimaActualizacion = fechaUltimaActualizacion;
    }

    public Devolucion() {}

    public Devolucion(RequisicionBodega requisicionBodega) {
        //BeanUtils.copyProperties(requisicionBodega, this);
        this.id = null;
        this.referenciaId = requisicionBodega.getId();
        this.tipo =  TIPO_ENTRADA_DEVOLUCION_REQUISICION;
        this.ingresadoBodega = false;
        this.fechaDevolucion = (new java.sql.Date(DateUtil.getCurrentCalendar().getTime().getTime()));
        this.addRQDetails(requisicionBodega.getRequisicionBodegaDetalle());

    }

    public void addRQDetails(List<RequisicionBodegaDetalle> requisicionBodegaDetalleList) {

        if (this.details==null) {
            this.details = new ArrayList<>();
        }

        for (RequisicionBodegaDetalle d: requisicionBodegaDetalleList) {
            DevolucionDetalle dRBD = new DevolucionDetalle(d);
            dRBD.setDevolucion(this);
            dRBD.setId(null);
            this.monto = (this.monto == null ? 0 : this.monto) + (dRBD.getCantidad() * dRBD.getPrecio());
            this.details.add(dRBD);
        }

    }

    public Devolucion(InvoiceNotaCredito invoiceNotaCredito) {
        //BeanUtils.copyProperties(invoiceNotaCredito, this);
        this.id = null;
        this.referenciaId = invoiceNotaCredito.getId();
        this.tipo = TIPO_ENTRADA_DEVOLUCION_NOTA_CREDITO;
        this.ingresadoBodega = false;
        this.fechaDevolucion = (new java.sql.Date(DateUtil.getCurrentCalendar().getTime().getTime()));
        this.addNCDetails(invoiceNotaCredito.getDetails());
    }

    public void addNCDetails(List<InvoiceNotaCreditoDetail> invoiceNotaCreditoDetailList) {

        if (this.details==null) {
            this.details = new ArrayList<>();
        }

        for (InvoiceNotaCreditoDetail d: invoiceNotaCreditoDetailList) {
            DevolucionDetalle dNCD = new DevolucionDetalle(d);
            dNCD.setDevolucion(this);
            dNCD.setId(null);
            this.monto = (this.monto == null ? 0 : this.monto) + (dNCD.getCantidad() * dNCD.getPrecio());
            this.details.add(dNCD);
        }

    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getConsecutivo() {
        return consecutivo;
    }

    public void setConsecutivo(String consecutivo) {
        this.consecutivo = consecutivo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Integer getReferenciaId() {
        return referenciaId;
    }

    public void setReferenciaId(Integer referenciaId) {
        this.referenciaId = referenciaId;
    }

    public Boolean getIngresadoBodega() {
        return ingresadoBodega;
    }

    public void setIngresadoBodega(Boolean ingresadoBodega) {
        this.ingresadoBodega = ingresadoBodega;
    }

    public Bodega getBodega() {
        return bodega;
    }

    public void setBodega(Bodega bodega) {
        this.bodega = bodega;
    }

    public List<DevolucionDetalle> getDetails() {
        return details;
    }

    public void setDetails(List<DevolucionDetalle> details) {
        this.details = details;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public User getIngresadoPor() {
        return ingresadoPor;
    }

    public void setIngresadoPor(User ingresadoPor) {
        this.ingresadoPor = ingresadoPor;
    }

    public User getUltimaActualizacionPor() {
        return ultimaActualizacionPor;
    }

    public void setUltimaActualizacionPor(User ultimaActualizacionPor) {
        this.ultimaActualizacionPor = ultimaActualizacionPor;
    }
}
