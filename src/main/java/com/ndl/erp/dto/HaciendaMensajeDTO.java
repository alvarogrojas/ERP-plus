package com.ndl.erp.dto;

import com.ndl.erp.fe.helpers.BillHelper;
import com.ndl.erp.fe.v43.FacturaElectronica;
import com.ndl.erp.fe.v43.nc.NotaCreditoElectronica;


import java.io.Serializable;
import java.math.BigInteger;

public class HaciendaMensajeDTO implements Serializable {
    private static final long serialVersionUID = 1777000000000000065L;

    private String tipo;
    private String fechaEmision;
    private String consecutivo;
    private String clave;
    private String nombreEmisor;
    private String tipoIdentificacionEmisor;
    private String numeroCedulaEmisor;
    private String nombreReceptor;
    private String tipoIdentificacionReceptor;
    private String numeroCedulaReceptor;
    private BigInteger mensaje;
    private String detalleMensaje;
    private String montoTotalImpuesto;
    private String totalFactura;

    private String medioPago;

    private String condicionVenta;

    private String detalleAction;
    private String action;
    private String moneda = BillHelper.NATIONAL_CURRENCY;
    private Boolean fileFormatIndefine;

    private Double totalServiciosGrabados;
    private Double totalServiciosExcentos;
    private Double totalMercanciasGravadas;
    private Double totalMercanciasExentas;
    private Double totalGravado;
    private Double totalExcento;
    private Double totalVenta;
    private Double totalDescuentos;
    private Double totalImpuestos;
    private Double totalOtrosCargos;
    private Double totalVentaNeta;

    private FacturaElectronica fe;
    private NotaCreditoElectronica nc;



    public HaciendaMensajeDTO() {
    }

    public HaciendaMensajeDTO(FacturaElectronica fe) {
        totalServiciosGrabados = fe.getResumenFactura().getTotalServGravados()==null?0:fe.getResumenFactura().getTotalServGravados().doubleValue();
        totalServiciosExcentos = fe.getResumenFactura().getTotalServExentos()==null?0:fe.getResumenFactura().getTotalServExentos().doubleValue();
        totalMercanciasGravadas = fe.getResumenFactura().getTotalMercanciasGravadas()==null?0:fe.getResumenFactura().getTotalMercanciasGravadas().doubleValue();
        totalMercanciasExentas = fe.getResumenFactura().getTotalMercanciasExentas()==null?0:fe.getResumenFactura().getTotalMercanciasExentas().doubleValue();
        totalGravado = fe.getResumenFactura().getTotalGravado()==null?0:fe.getResumenFactura().getTotalGravado().doubleValue();
        totalExcento = fe.getResumenFactura().getTotalExento()==null?0:fe.getResumenFactura().getTotalExento().doubleValue();
        totalVenta = fe.getResumenFactura().getTotalVenta()==null?0:fe.getResumenFactura().getTotalVenta().doubleValue();
        totalDescuentos = fe.getResumenFactura().getTotalDescuentos()==null?0:fe.getResumenFactura().getTotalDescuentos().doubleValue();
        totalImpuestos = fe.getResumenFactura().getTotalImpuesto()==null?0:fe.getResumenFactura().getTotalImpuesto().doubleValue();
        totalOtrosCargos = fe.getResumenFactura().getTotalOtrosCargos()==null?0:fe.getResumenFactura().getTotalOtrosCargos().doubleValue();
        totalVentaNeta = fe.getResumenFactura().getTotalVentaNeta()==null?0:fe.getResumenFactura().getTotalVentaNeta().doubleValue();
        setCondicionVenta(fe.getCondicionVenta());

        this.moneda = fe.getResumenFactura()!=null && fe.getResumenFactura().getCodigoTipoMoneda()!=null?
                fe.getResumenFactura().getCodigoTipoMoneda().getCodigoMoneda():BillHelper.NATIONAL_CURRENCY;

        if (fe.getMedioPago()!=null && fe.getMedioPago().size()>0) {
            setMedioPago(fe.getMedioPago().get(0));
        }
        setNombreReceptor(fe.getReceptor().getNombre());
        setNumeroCedulaReceptor(fe.getReceptor().getIdentificacion().getNumero());
        setTipoIdentificacionReceptor(fe.getReceptor().getIdentificacion().getTipo());
    }

    public HaciendaMensajeDTO(NotaCreditoElectronica fe) {
        totalServiciosGrabados = fe.getResumenFactura().getTotalServGravados()==null?0:fe.getResumenFactura().getTotalServGravados().doubleValue();
        totalServiciosExcentos = fe.getResumenFactura().getTotalServExentos()==null?0:fe.getResumenFactura().getTotalServExentos().doubleValue();
        totalMercanciasGravadas = fe.getResumenFactura().getTotalMercanciasGravadas()==null?0:fe.getResumenFactura().getTotalMercanciasGravadas().doubleValue();
        totalMercanciasExentas = fe.getResumenFactura().getTotalMercanciasExentas()==null?0:fe.getResumenFactura().getTotalMercanciasExentas().doubleValue();
        totalGravado = fe.getResumenFactura().getTotalGravado()==null?0:fe.getResumenFactura().getTotalGravado().doubleValue();
        totalExcento = fe.getResumenFactura().getTotalExento()==null?0:fe.getResumenFactura().getTotalExento().doubleValue();
        totalVenta = fe.getResumenFactura().getTotalVenta()==null?0:fe.getResumenFactura().getTotalVenta().doubleValue();
        totalDescuentos = fe.getResumenFactura().getTotalDescuentos()==null?0:fe.getResumenFactura().getTotalDescuentos().doubleValue();
        totalImpuestos = fe.getResumenFactura().getTotalImpuesto()==null?0:fe.getResumenFactura().getTotalImpuesto().doubleValue();
        totalOtrosCargos = fe.getResumenFactura().getTotalOtrosCargos()==null?0:fe.getResumenFactura().getTotalOtrosCargos().doubleValue();
        totalVentaNeta = fe.getResumenFactura().getTotalVentaNeta()==null?0:fe.getResumenFactura().getTotalVentaNeta().doubleValue();
        this.moneda = fe.getResumenFactura()!=null && fe.getResumenFactura().getCodigoTipoMoneda()!=null?
                fe.getResumenFactura().getCodigoTipoMoneda().getCodigoMoneda():BillHelper.NATIONAL_CURRENCY;
        setCondicionVenta(fe.getCondicionVenta());

        if (fe.getMedioPago()!=null && fe.getMedioPago().size()>0) {
            setMedioPago(fe.getMedioPago().get(0));
        }

        setNombreReceptor(fe.getReceptor().getNombre());
        setNumeroCedulaReceptor(fe.getReceptor().getIdentificacion().getNumero());
        setTipoIdentificacionReceptor(fe.getReceptor().getIdentificacion().getTipo());

    }

    public Boolean getFileFormatIndefine() {
        return fileFormatIndefine;
    }

    public void setFileFormatIndefine(Boolean fileFormatIndefine) {
        this.fileFormatIndefine = fileFormatIndefine;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getNombreEmisor() {
        return nombreEmisor;
    }

    public void setNombreEmisor(String nombreEmisor) {
        this.nombreEmisor = nombreEmisor;
    }

    public String getTipoIdentificacionEmisor() {
        return tipoIdentificacionEmisor;
    }

    public void setTipoIdentificacionEmisor(String tipoIdentificacionEmisor) {
        if (tipoIdentificacionEmisor!=null && BillHelper.TIPO_IDENTIFICACION.get(tipoIdentificacionEmisor)!=null) {
            tipoIdentificacionEmisor = BillHelper.TIPO_IDENTIFICACION.get(tipoIdentificacionEmisor);
        }
        this.tipoIdentificacionEmisor = tipoIdentificacionEmisor;
    }

    public String getNumeroCedulaEmisor() {
        return numeroCedulaEmisor;
    }

    public void setNumeroCedulaEmisor(String numeroCedulaEmisor) {
        this.numeroCedulaEmisor = numeroCedulaEmisor;
    }

    public String getNombreReceptor() {
        return nombreReceptor;
    }

    public void setNombreReceptor(String nombreReceptor) {
        this.nombreReceptor = nombreReceptor;
    }

    public String getTipoIdentificacionReceptor() {
        return tipoIdentificacionReceptor;
    }

    public void setTipoIdentificacionReceptor(String tipoIdentificacionReceptor) {
        if (tipoIdentificacionReceptor!=null && BillHelper.TIPO_IDENTIFICACION.get(tipoIdentificacionReceptor)!=null) {
            tipoIdentificacionReceptor = BillHelper.TIPO_IDENTIFICACION.get(tipoIdentificacionReceptor);
        }
        this.tipoIdentificacionReceptor = tipoIdentificacionReceptor;
    }

    public String getNumeroCedulaReceptor() {
        return numeroCedulaReceptor;
    }

    public void setNumeroCedulaReceptor(String numeroCedulaReceptor) {
        this.numeroCedulaReceptor = numeroCedulaReceptor;
    }

    public BigInteger getMensaje() {
        return mensaje;
    }

    public void setMensaje(BigInteger mensaje) {
        this.mensaje = mensaje;
    }

    public String getDetalleMensaje() {
        return detalleMensaje;
    }

    public void setDetalleMensaje(String detalleMensaje) {
        this.detalleMensaje = detalleMensaje;
    }

    public String getMontoTotalImpuesto() {
        return montoTotalImpuesto;
    }

    public void setMontoTotalImpuesto(String montoTotalImpuesto) {
        this.montoTotalImpuesto = montoTotalImpuesto;
    }

    public String getTotalFactura() {
        return totalFactura;
    }

    public void setTotalFactura(String totalFactura) {
        this.totalFactura = totalFactura;
    }

    public String getDetalleAction() {
        return detalleAction;
    }

    public void setDetalleAction(String detalleAction) {
        this.detalleAction = detalleAction;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(String fechaEmision) {
        this.fechaEmision = fechaEmision;
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

    public FacturaElectronica getFe() {
        return fe;
    }

    public void setFe(FacturaElectronica fe) {
        this.fe = fe;
    }

    public NotaCreditoElectronica getNc() {
        return nc;
    }

    public void setNc(NotaCreditoElectronica nc) {
        this.nc = nc;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public Double getTotalServiciosGrabados() {
        return totalServiciosGrabados;
    }

    public void setTotalServiciosGrabados(Double totalServiciosGrabados) {
        this.totalServiciosGrabados = totalServiciosGrabados;
    }

    public Double getTotalServiciosExcentos() {
        return totalServiciosExcentos;
    }

    public void setTotalServiciosExcentos(Double totalServiciosExcentos) {
        this.totalServiciosExcentos = totalServiciosExcentos;
    }

    public Double getTotalMercanciasGravadas() {
        return totalMercanciasGravadas;
    }

    public void setTotalMercanciasGravadas(Double totalMercanciasGravadas) {
        this.totalMercanciasGravadas = totalMercanciasGravadas;
    }

    public Double getTotalMercanciasExentas() {
        return totalMercanciasExentas;
    }

    public void setTotalMercanciasExentas(Double totalMercanciasExentas) {
        this.totalMercanciasExentas = totalMercanciasExentas;
    }

    public Double getTotalGravado() {
        return totalGravado;
    }

    public void setTotalGravado(Double totalGravado) {
        this.totalGravado = totalGravado;
    }

    public Double getTotalExcento() {
        return totalExcento;
    }

    public void setTotalExcento(Double totalExcento) {
        this.totalExcento = totalExcento;
    }

    public Double getTotalVenta() {
        return totalVenta;
    }

    public void setTotalVenta(Double totalVenta) {
        this.totalVenta = totalVenta;
    }

    public Double getTotalDescuentos() {
        return totalDescuentos;
    }

    public void setTotalDescuentos(Double totalDescuentos) {
        this.totalDescuentos = totalDescuentos;
    }

    public Double getTotalImpuestos() {
        return totalImpuestos;
    }

    public void setTotalImpuestos(Double totalImpuestos) {
        this.totalImpuestos = totalImpuestos;
    }

    public Double getTotalOtrosCargos() {
        return totalOtrosCargos;
    }

    public void setTotalOtrosCargos(Double totalOtrosCargos) {
        this.totalOtrosCargos = totalOtrosCargos;
    }

    public Double getTotalVentaNeta() {
        return totalVentaNeta;
    }

    public void setTotalVentaNeta(Double totalVentaNeta) {
        this.totalVentaNeta = totalVentaNeta;
    }

    public String getMedioPago() {
        return medioPago;
    }

    public void setMedioPago(String medioPago) {
        if (medioPago!=null && BillHelper.MEDIO_PAGO.get(medioPago)!=null) {
            medioPago = BillHelper.MEDIO_PAGO.get(medioPago);
        }
        this.medioPago = medioPago;
    }

    public String getCondicionVenta() {
        return condicionVenta;
    }

    public void setCondicionVenta(String condicionVenta) {
        if (condicionVenta!=null && BillHelper.CONDICION_VENTA.get(condicionVenta)!=null) {
            condicionVenta = BillHelper.CONDICION_VENTA.get(condicionVenta);
        }
        this.condicionVenta = condicionVenta;
    }
}
