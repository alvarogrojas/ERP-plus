package com.ndl.erp.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Producto {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private String codigo;
    private String sku;

    @OneToOne
    @JoinColumn(name="fabricante_id", referencedColumnName="id")
    private Fabricante fabricante;

    private String tipo = "Simple";

    @OneToOne
    @JoinColumn(name="ultimo_cambio_id", referencedColumnName="id")
    private User userUltimoCambio;

    @OneToOne
    @JoinColumn(name="familia_id", referencedColumnName="id")
    private Familia familia;

    private Double margenUtilidad;
    private Double precioList;

    @OneToOne
    @JoinColumn(name="alto_unidad_Medida_id", referencedColumnName="id")
    private UnidadMedida unidadMedidaAlto;


    @OneToOne
    @JoinColumn(name="ancho_unidad_medida_id", referencedColumnName="id")
    private UnidadMedida unidadMedidaAncho;


    @OneToOne
    @JoinColumn(name="unidad_medida_id", referencedColumnName="id")
    private UnidadMedida unidadMedida;


    @OneToOne
    @JoinColumn(name="multiplicador_id", referencedColumnName="id")
    private Multiplicador multiplicador;


//    @JsonManagedReference
//    @OneToMany(cascade = CascadeType.ALL,
//            mappedBy = "producto", orphanRemoval = true)
//    private List<ProductoCategoria> categorias = new ArrayList<ProductoCategoria>(0);


    private Integer gravado;
    private Double existenciaMaxima;
    private Double existenciaMinima;
    private Double puntoReorden;
    private String catalogo;
    private String descripcionIngles;
    private String descripcionEspanol;
    private Double unidadEmpaque;
    private String unidadEmpaqueCodigo;
    private String paisOrigen;
    private Integer tiempoFabricacion;
    private String notas;
    private String despachoDesde;
    private String responsableProducto;
    private Double alto;
    private Double ancho;
    private  Double pesoKg;
    private String terminoEntrega;
    private String codigoCabys;
    private String simboloDescuento;
    private String manejoBodega;
    private String estado;

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL,
            mappedBy = "producto", orphanRemoval = true)
    private List<ProductoFamiliaAtributo> familiaAtributos = new ArrayList<ProductoFamiliaAtributo>(0);

    @Transient
    private Double precioVenta;

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Double getPrecioVenta() {

        if (this.precioList!= null) {
            this.precioVenta = precioList + (this.getMultiplicador()  != null ? precioList * this.getMultiplicador().getFactor() : 0);
        } else {
            this.precioVenta = 0d;
        }

        return precioVenta;
    }


//    public List<ProductoCategoria> getCategorias() {
//        return categorias;
//    }
//
//    public void setCategorias(List<ProductoCategoria> categorias) {
//        this.categorias = categorias;
//    }

    public Familia getFamilia() {
        return familia;
    }

    public void setFamilia(Familia familia) {
        this.familia = familia;
    }

    public User getUserUltimoCambio() {
        return userUltimoCambio;
    }

    public void setUserUltimoCambio(User userUltimoCambio) {
        this.userUltimoCambio = userUltimoCambio;
    }

    private Date fechaUltimoCambio;

    public String getManejoBodega() {
        return manejoBodega;
    }

    public void setManejoBodega(String manejoBodega) {
        this.manejoBodega = manejoBodega;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Double getPrecioList() {
        return precioList;
    }

    public void setPrecioList(Double precioList) {
        this.precioList = precioList;
    }

    public Integer getGravado() {
        return gravado;
    }

    public void setGravado(Integer gravado) {
        this.gravado = gravado;
    }

    public String getCatalogo() {
        return catalogo;
    }

    public void setCatalogo(String catalogo) {
        this.catalogo = catalogo;
    }

    public String getDescripcionIngles() {
        return descripcionIngles;
    }

    public void setDescripcionIngles(String descripcionIngles) {
        this.descripcionIngles = descripcionIngles;
    }

    public String getDescripcionEspanol() {
        return descripcionEspanol;
    }

    public void setDescripcionEspanol(String descripcionEspanol) {
        this.descripcionEspanol = descripcionEspanol;
    }

    public Double getUnidadEmpaque() {
        return unidadEmpaque;
    }

    public void setUnidadEmpaque(Double unidadEmpaque) {
        this.unidadEmpaque = unidadEmpaque;
    }

    public String getUnidadEmpaqueCodigo() {
        return unidadEmpaqueCodigo;
    }

    public void setUnidadEmpaqueCodigo(String unidadEmpaqueCodigo) {
        this.unidadEmpaqueCodigo = unidadEmpaqueCodigo;
    }

    public String getPaisOrigen() {
        return paisOrigen;
    }

    public void setPaisOrigen(String paisOrigen) {
        this.paisOrigen = paisOrigen;
    }

    public Integer getTiempoFabricacion() {
        return tiempoFabricacion;
    }

    public void setTiempoFabricacion(Integer tiempoFabricacion) {
        this.tiempoFabricacion = tiempoFabricacion;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    public String getDespachoDesde() {
        return despachoDesde;
    }

    public void setDespachoDesde(String despachoDesde) {
        this.despachoDesde = despachoDesde;
    }

    public String getResponsableProducto() {
        return responsableProducto;
    }

    public void setResponsableProducto(String responsableProducto) {
        this.responsableProducto = responsableProducto;
    }

    public Double getAlto() {
        return alto;
    }

    public void setAlto(Double alto) {
        this.alto = alto;
    }

    public UnidadMedida getUnidadMedidaAlto() {
        return unidadMedidaAlto;
    }

    public void setUnidadMedidaAlto(UnidadMedida unidadMedidaAlto) {
        this.unidadMedidaAlto = unidadMedidaAlto;
    }

    public Double getAncho() {
        return ancho;
    }

    public void setAncho(Double ancho) {
        this.ancho = ancho;
    }

    public UnidadMedida getUnidadMedidaAncho() {
        return unidadMedidaAncho;
    }

    public void setUnidadMedidaAncho(UnidadMedida unidadMedidaAncho) {
        this.unidadMedidaAncho = unidadMedidaAncho;
    }

    public Double getPesoKg() {
        return pesoKg;
    }

    public void setPesoKg(Double pesoKg) {
        this.pesoKg = pesoKg;
    }

    public String getTerminoEntrega() {
        return terminoEntrega;
    }

    public void setTerminoEntrega(String terminoEntrega) {
        this.terminoEntrega = terminoEntrega;
    }

    public String getCodigoCabys() {
        return codigoCabys;
    }

    public void setCodigoCabys(String codigoCabys) {
        this.codigoCabys = codigoCabys;
    }

    public String getSimboloDescuento() {
        return simboloDescuento;
    }

    public void setSimboloDescuento(String simboloDescuento) {
        this.simboloDescuento = simboloDescuento;
    }

    public Multiplicador getMultiplicador() {
        return multiplicador;
    }

    public void setMultiplicador(Multiplicador multiplicador) {
        this.multiplicador = multiplicador;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }



    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Fabricante getFabricante() {
        return fabricante;
    }

    public void setFabricante(Fabricante fabricante) {
        this.fabricante = fabricante;
    }


    public Double getMargenUtilidad() {
        return margenUtilidad;
    }

    public void setMargenUtilidad(Double margenUtilidad) {
        this.margenUtilidad = margenUtilidad;
    }



    public UnidadMedida getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(UnidadMedida unidadMedida) {
        this.unidadMedida = unidadMedida;
    }



    public Double getExistenciaMaxima() {
        return existenciaMaxima;
    }

    public void setExistenciaMaxima(Double existenciaMaxima) {
        this.existenciaMaxima = existenciaMaxima;
    }

    public Double getExistenciaMinima() {
        return existenciaMinima;
    }

    public void setExistenciaMinima(Double existenciaMinima) {
        this.existenciaMinima = existenciaMinima;
    }

    public Double getPuntoReorden() {
        return puntoReorden;
    }

    public void setPuntoReorden(Double puntoReorden) {
        this.puntoReorden = puntoReorden;
    }

    public Date getFechaUltimoCambio() {
        return fechaUltimoCambio;
    }

    public List<ProductoFamiliaAtributo> getFamiliaAtributos() {
        return familiaAtributos;
    }

    public void setFamiliaAtributos(List<ProductoFamiliaAtributo> familiaAtributos) {
        this.familiaAtributos = familiaAtributos;
    }

    public void setFechaUltimoCambio(Date fechaUltimoCambio) {
        this.fechaUltimoCambio = fechaUltimoCambio;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setPrecioVenta(Double precioVenta) {
        this.precioVenta = precioVenta;
    }
}
