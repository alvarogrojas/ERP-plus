package com.ndl.erp.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
public class CotizacionHistoricoVersion {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;


    @OneToOne
    @JoinColumn(name="cotizacion_id", referencedColumnName="id")
    private Cotizacion cotizacionPadre;


    @OneToOne
    @JoinColumn(name="cliente_id", referencedColumnName="clientId")
    private Client client;

    @OneToOne
    @JoinColumn(name="contact_id", referencedColumnName="id")
    private ContactClient contactClient;

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL,
            mappedBy = "cotizacionHistoricoVersion", orphanRemoval = true)
    private List<CotizacionHistoricoVersionDetalle> cotizacionDetalleHistoricoVersionList = new ArrayList<CotizacionHistoricoVersionDetalle>(0);

    @OneToOne
    @JoinColumn(name="currency_id", referencedColumnName="id")
    private Currency currency;

    @OneToOne
    @JoinColumn(name="bodega_id", referencedColumnName="id")
    private Bodega bodega;

    @OneToOne
    @JoinColumn(name="vendedor_id", referencedColumnName="id")
    private User vendedor;

    @OneToOne
    @JoinColumn(name="ingresado_por", referencedColumnName="id")
    private User userIngresadoPor;

    private String cotizacionNumber;
    private Date fechaEmision;
    private Date fechaVencimiento;
    private Integer versionNumber;
    private String asunto;
    private String direccion;
    private String phone;
    private String email;
    private Integer creditDays;
    private String telefonoVendedor;
    private String estado;
    private String correoVendedor;
    private String observaciones;
    private Double subTotal;
    private Double iva;
    private Double total;
    private Double exonerated;
    private Date fechaIngreso;
    private Date fechaUltimaModificacion;
    private Double totalDescuentos;

    public Double getTotalDescuentos() {
        return totalDescuentos;
    }

    public void setTotalDescuentos(Double totalDescuentos) {
        this.totalDescuentos = totalDescuentos;
    }

    public Double getExonerated() {
        return exonerated;
    }

    public void setExonerated(Double exonerated) {
        this.exonerated = exonerated;
    }

    public Bodega getBodega() {
        return bodega;
    }

    public void setBodega(Bodega bodega) {
        this.bodega = bodega;
    }

    public Integer getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(Integer versionNumber) {
        this.versionNumber = versionNumber;
    }

    public Cotizacion getCotizacionPadre() {
        return cotizacionPadre;
    }

    public void setCotizacionPadre(Cotizacion cotizacionPadre) {
        this.cotizacionPadre = cotizacionPadre;
    }

    public List<CotizacionHistoricoVersionDetalle> getCotizacionDetalleHistoricoVersionList() {
        return cotizacionDetalleHistoricoVersionList;
    }

    public void setCotizacionDetalleHistoricoVersionList(List<CotizacionHistoricoVersionDetalle> cotizacionDetalleHistoricoVersionList) {
        this.cotizacionDetalleHistoricoVersionList = cotizacionDetalleHistoricoVersionList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCotizacionNumber() {
        return cotizacionNumber;
    }

    public void setCotizacionNumber(String cotizacionNumber) {
        this.cotizacionNumber = cotizacionNumber;
    }

    public Date getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(Date fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public ContactClient getContactClient() {
        return contactClient;
    }

    public void setContactClient(ContactClient contactClient) {
        this.contactClient = contactClient;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getCreditDays() {
        return creditDays;
    }

    public void setCreditDays(Integer creditDays) {
        this.creditDays = creditDays;
    }

    public User getVendedor() {
        return vendedor;
    }

    public void setVendedor(User vendedor) {
        this.vendedor = vendedor;
    }

    public String getTelefonoVendedor() {
        return telefonoVendedor;
    }

    public void setTelefonoVendedor(String telefonoVendedor) {
        this.telefonoVendedor = telefonoVendedor;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCorreoVendedor() {
        return correoVendedor;
    }

    public void setCorreoVendedor(String correoVendedor) {
        this.correoVendedor = correoVendedor;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(Double subTotal) {
        this.subTotal = subTotal;
    }

    public Double getIva() {
        return iva;
    }

    public void setIva(Double iva) {
        this.iva = iva;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public User getUserIngresadoPor() {
        return userIngresadoPor;
    }

    public void setUserIngresadoPor(User userIngresadoPor) {
        this.userIngresadoPor = userIngresadoPor;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public Date getFechaUltimaModificacion() {
        return fechaUltimaModificacion;
    }

    public void setFechaUltimaModificacion(Date fechaUltimaModificacion) {
        this.fechaUltimaModificacion = fechaUltimaModificacion;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
}

