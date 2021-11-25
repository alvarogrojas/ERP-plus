package com.ndl.erp.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
public class InvoiceMedioPago {

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   Integer id;


   @JsonBackReference
   @ManyToOne
   @JoinColumn(name = "invoice_id", referencedColumnName = "id")
   private Invoice invoice;


   @OneToOne
   @JoinColumn(name = "medio_pago_id", referencedColumnName = "id")
   private MedioPago medioPago;

   private String depositoDetalle;
   private String banco;
   private String estado;
   private Double montoPago;
   private String detalle;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public MedioPago getMedioPago() {
        return medioPago;
    }

    public void setMedioPago(MedioPago medioPago) {
        this.medioPago = medioPago;
    }

    public String getDepositoDetalle() {
        return depositoDetalle;
    }

    public void setDepositoDetalle(String depositoDetalle) {
        this.depositoDetalle = depositoDetalle;
    }

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Double getMontoPago() {
        return montoPago;
    }

    public void setMontoPago(Double montoPago) {
        this.montoPago = montoPago;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }
}
