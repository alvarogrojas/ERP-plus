package com.ndl.erp.domain;


import javax.persistence.*;
import java.sql.Date;


@Entity
public class InventarioItem {

    @Id
    @GeneratedValue
    private Integer id;

    private String codigoProveedor;
    private String catalogo;
    private String simboloDescuento;

    private Double precioLista;

    private Double stockIngpro;
    private Double stockFabricante;
    private Integer cantidadCotizacion;
    private Double multiplicador;
    private Double multiplicadorIngpro;
    private Double margenGanancia;
    private String entrega;
    private Double precioUnitario;

    private String codigoCabys;
    private String descripcion;
    private String codigoIngpro;

    @OneToOne
    @JoinColumn(name="currency_id", referencedColumnName="id")
    private Currency currency;

    private Integer productId;

    @OneToOne
    @JoinColumn(name="bodega_id", referencedColumnName="id")
    private Bodega bodega;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCodigoProveedor() {
        return codigoProveedor;
    }

    public void setCodigoProveedor(String codigoProveedor) {
        this.codigoProveedor = codigoProveedor;
    }

    public String getCatalogo() {
        return catalogo;
    }

    public void setCatalogo(String catalogo) {
        this.catalogo = catalogo;
    }

    public String getSimboloDescuento() {
        return simboloDescuento;
    }

    public void setSimboloDescuento(String simboloDescuento) {
        this.simboloDescuento = simboloDescuento;
    }

    public Double getPrecioLista() {
        return precioLista;
    }

    public void setPrecioLista(Double precioLista) {
        this.precioLista = precioLista;
    }

    public Double getStockIngpro() {
        return stockIngpro;
    }

    public void setStockIngpro(Double stockIngpro) {
        this.stockIngpro = stockIngpro;
    }

    public Double getStockFabricante() {
        return stockFabricante;
    }

    public void setStockFabricante(Double stockFabricante) {
        this.stockFabricante = stockFabricante;
    }

    public Integer getCantidadCotizacion() {
        return cantidadCotizacion;
    }

    public void setCantidadCotizacion(Integer cantidadCotizacion) {
        this.cantidadCotizacion = cantidadCotizacion;
    }

    public Double getMultiplicador() {
        return multiplicador;
    }

    public void setMultiplicador(Double multiplicador) {
        this.multiplicador = multiplicador;
    }

    public Double getMultiplicadorIngpro() {
        return multiplicadorIngpro;
    }

    public void setMultiplicadorIngpro(Double multiplicadorIngpro) {
        this.multiplicadorIngpro = multiplicadorIngpro;
    }

    public Double getMargenGanancia() {
        return margenGanancia;
    }

    public void setMargenGanancia(Double margenGanancia) {
        this.margenGanancia = margenGanancia;
    }

    public String getEntrega() {
        return entrega;
    }

    public void setEntrega(String entrega) {
        this.entrega = entrega;
    }

    public Double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(Double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }


    public String getCodigoCabys() {
        return codigoCabys;
    }

    public void setCodigoCabys(String codigoCabys) {
        this.codigoCabys = codigoCabys;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Bodega getBodega() {
        return bodega;
    }

    public void setBodega(Bodega bodega) {
        this.bodega = bodega;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getCodigoIngpro() {
        return codigoIngpro;
    }

    public void setCodigoIngpro(String codigoIngpro) {
        this.codigoIngpro = codigoIngpro;
    }

    //    public Cabys getCabys() {
//        return cabys;
//    }
//
//    public void setCabys(Cabys cabys) {
//        this.cabys = cabys;
//    }
}
