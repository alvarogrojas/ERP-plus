package com.ndl.erp.domain;
import javax.persistence.*;
import java.util.Date;

@Entity
public class Inventario {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @OneToOne
    @JoinColumn(name="producto_id", referencedColumnName="id")
    private Producto producto;

    private Date fecha;

    @OneToOne
    @JoinColumn(name="tienda_id", referencedColumnName="id")
    private Tienda tienda;

    private Double cantidadEntrada;
    private Double costoEntrada;
    private Double totalEntrada;
    private Double cantidadSalida;
    private Double costoSalida;
    private Double totalSalida;
    private Double cantidadSaldo;
    private Double costoSaldo;
    private Double totalSaldo;
    private Date fechaUltimoCambio;
    private Integer disponible;
    private String barcode;


    @OneToOne
    @JoinColumn(name="ultimo_cambio_id", referencedColumnName="id")
    private User usuarioUltimoCambio;

    @OneToOne
    @JoinColumn(name="bodega_id", referencedColumnName="id")
    private Bodega bodega;

    @OneToOne
    @JoinColumn(name="ubicacion_bodega_id", referencedColumnName="id")
    private UbicacionBodega ubicacionBodega;

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public Integer getDisponible() {
        return disponible;
    }

    public void setDisponible(Integer disponible) {
        this.disponible = disponible;
    }

    public Bodega getBodega() {
        return bodega;
    }

    public void setBodega(Bodega bodega) {
        this.bodega = bodega;
    }

    public UbicacionBodega getUbicacionBodega() {
        return ubicacionBodega;
    }

    public void setUbicacionBodega(UbicacionBodega ubicacionBodega) {
        this.ubicacionBodega = ubicacionBodega;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Tienda getTienda() {
        return tienda;
    }

    public void setTienda(Tienda tienda) {
        this.tienda = tienda;
    }

    public Double getCantidadEntrada() {
        return cantidadEntrada;
    }

    public void setCantidadEntrada(Double cantidadEntrada) {
        this.cantidadEntrada = cantidadEntrada;
    }

    public Double getCostoEntrada() {
        return costoEntrada;
    }

    public void setCostoEntrada(Double costoEntrada) {
        this.costoEntrada = costoEntrada;
    }

    public Double getTotalEntrada() {
        return totalEntrada;
    }

    public void setTotalEntrada(Double totalEntrada) {
        this.totalEntrada = totalEntrada;
    }

    public Double getCantidadSalida() {
        return cantidadSalida;
    }

    public void setCantidadSalida(Double cantidadSalida) {
        this.cantidadSalida = cantidadSalida;
    }

    public Double getCostoSalida() {
        return costoSalida;
    }

    public void setCostoSalida(Double costoSalida) {
        this.costoSalida = costoSalida;
    }

    public Double getTotalSalida() {
        return totalSalida;
    }

    public void setTotalSalida(Double totalSalida) {
        this.totalSalida = totalSalida;
    }

    public Double getCantidadSaldo() {
        return cantidadSaldo;
    }

    public void setCantidadSaldo(Double cantidadSaldo) {
        this.cantidadSaldo = cantidadSaldo;
    }

    public Double getCostoSaldo() {
        return costoSaldo;
    }

    public void setCostoSaldo(Double costoSaldo) {
        this.costoSaldo = costoSaldo;
    }

    public Double getTotalSaldo() {
        return totalSaldo;
    }

    public void setTotalSaldo(Double totalSaldo) {
        this.totalSaldo = totalSaldo;
    }

    public User getUsuarioUltimoCambio() {
        return usuarioUltimoCambio;
    }

    public void setUsuarioUltimoCambio(User usuarioUltimoCambio) {
        this.usuarioUltimoCambio = usuarioUltimoCambio;
    }


    public Date getFechaUltimoCambio() {
        return fechaUltimoCambio;
    }

    public void setFechaUltimoCambio(Date fechaUltimoCambio) {
        this.fechaUltimoCambio = fechaUltimoCambio;
    }
}
