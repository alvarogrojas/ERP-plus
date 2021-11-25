package com.ndl.erp.fe.dtos;

import com.ndl.erp.fe.model.FacturaBase;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class FacturaDataDTO implements DocumentoElectronicoDTO {

    private String cedula;
    private String clave;
    private Date fechaFacturacion = new Date();

    private Integer clienteId;

    private String clienteNombre;

    private Boolean esClienteNuevo = false;

    private String correoCliente;
    private String correoCliente2;
    private String correoCliente3;
    private String correoCliente4;

    private String telefono;

    private String direccion;

    private String contacto;

    private String cobroPorCuentaDe;

    private String observaciones;

    private String estado = "Ingresada";

    private String estadoPago = "Pendiente Pago";

    private Integer credito;

    private Integer diasCredito;

    private Double porcentajeComision = 3d;

    private Double subtotal = 0d;

    private Double comisionFinanciamiento = 0d;

    private Double impuestoVentas = 0d;

    private Double total = 0d;

    private Double montoAnticipado = 0d;

    private Double saldoPendiente = 0d;

    private Double saldoPendienteMoneda = 0d;

    private Integer encargadoId;

    private List<FacturaDetalleDTO> detallesDTO;

    private String fisicaOJuridica = "02";

    private Integer facturaElectronicaConsecutivo;

    private String proveedor;

    private Date fechaVencimiento = new Date();

    private Integer facturaId;

    private Double tipoCambioMonto = 0d;

    private Integer enviadaHacienda = 0;
    private TipoCambioDTO tipoCambio;

    public FacturaDataDTO() {
        detallesDTO =  new ArrayList<FacturaDetalleDTO>();
        esClienteNuevo = true;

    }

    public FacturaDataDTO(
                          Integer clienteId,
                          String cliente,
                          String telefono,
                          String direccion,
                          String contacto,
                          Integer credito,
                          Integer diasCredito, String tipoTramite, String aduana, String bl, String proveedor, String dua, Short previoExamen, Short aforoFisico, Short permisos, String correo, String correo2, String correo3, String correo4, Integer enviadaHacienda) {

        this.enviadaHacienda = enviadaHacienda;
        this.correoCliente = correo;
        this.correoCliente2 = correo2;
        this.correoCliente3 = correo3;
        this.correoCliente4 = correo4;
        this.clienteId = clienteId;
        this.clienteNombre = cliente;
        this.telefono = telefono;
        this.direccion = direccion;
        this.contacto = contacto;
        this.credito = credito;
        this.diasCredito = diasCredito;
//        this.tipoTramite = tipoTramite;
        this.proveedor = proveedor;
        esClienteNuevo = false;

        if (this.credito!=null && this.credito==1 && this.diasCredito!=null && this.diasCredito>0 ) {

            Calendar c = Calendar.getInstance();
                c.setTime(fechaFacturacion);
            c.add(Calendar.DAY_OF_MONTH, this.diasCredito);
            this.fechaVencimiento = c.getTime();
        }

        detallesDTO =  new ArrayList<FacturaDetalleDTO>();
    }



    public FacturaDataDTO(
                          FacturaBase factura
                          ) {

        this.enviadaHacienda = factura.getEnviadaHacienda();
        this.correoCliente = factura.getClienteContacto1Correo();
        this.correoCliente2 = factura.getClienteContacto2Correo();
        this.correoCliente3 = factura.getClienteContacto3Correo();
        this.correoCliente4 = factura.getClienteContacto4Correo();
        this.cedula = factura.getClienteCedulaJuridica();


        this.clienteId = factura.getClienteId();
        this.telefono = factura.getClienteTelefono();
        this.direccion = factura.getClienteDireccion();
        this.contacto = factura.getClienteContacto1();
        this.credito = factura.getClienteTieneCredito();
        this.diasCredito = factura.getClienteDiasCredito();
        this.proveedor = "";
        mapTipoCedula(factura);

        this.facturaId = factura.getFacturaId();
        this.clienteNombre = factura.getClienteNombre();

        BeanUtils.copyProperties(factura,this);
        this.impuestoVentas = factura.getImpuestoVentas();
        initFacturaList(factura);
    }

    private void mapTipoCedula(FacturaBase factura) {
        if (factura.getClienteEsJuridico()!=null && factura.getClienteEsJuridico()==0) {
            this.fisicaOJuridica = "01";
        } else if (factura.getClienteEsJuridico()!=null && factura.getClienteEsJuridico()==4) {
            this.fisicaOJuridica = "04";
        } else if (factura.getClienteEsJuridico()!=null && factura.getClienteEsJuridico()==3) {
            this.fisicaOJuridica = "03";

        } else if (factura.getClienteEsJuridico()!=null && factura.getClienteEsJuridico()==1) {
            this.fisicaOJuridica = "02";

        }
    }

    public void recalculateDiasCredito() {
        if (this.fechaVencimiento==null || this.fechaFacturacion==null) {
            this.diasCredito = 0;
            return;
        }
        long difference = this.fechaVencimiento.getTime() - this.fechaFacturacion.getTime();
        this.diasCredito = (int) (difference / (1000*60*60*24));
    }

    private void initFacturaList(FacturaBase factura) {

        this.detallesDTO = new ArrayList<>();
        FacturaDetalleDTO d;
        for (FacturaDetalleDTO fd:factura.getFacturaDetalle()) {
            d = new FacturaDetalleDTO();
            BeanUtils.copyProperties(d,fd);

            this.detallesDTO.add(d);

        }
    }


    public Date getFechaFacturacion() {
        return fechaFacturacion;
    }

    public void setFechaFacturacion(Date fechaFacturacion) {
        this.fechaFacturacion = fechaFacturacion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public Integer getCredito() {
        return credito;
    }

    public void setCredito(Integer credito) {
        this.credito = credito;
    }

    public Integer getDiasCredito() {
        return diasCredito;
    }

    public void setDiasCredito(Integer diasCredito) {
        this.diasCredito = diasCredito;
    }

    public List<FacturaDetalleDTO> getDetallesDTO() {
        return detallesDTO;
    }

    public void setDetallesDTO(List<FacturaDetalleDTO> detalles) {
        this.detallesDTO = detalles;
    }



    public TipoCambioDTO getTipoCambio() {
        return tipoCambio;
    }

    public void setTipoCambio(TipoCambioDTO tipoCambio) {
        this.tipoCambio = tipoCambio;

    }

    public String getCobroPorCuentaDe() {
        return cobroPorCuentaDe;
    }

    public void setCobroPorCuentaDe(String cobroPorCuentaDe) {
        this.cobroPorCuentaDe = cobroPorCuentaDe;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Integer getClienteId() {
        return clienteId;
    }

    public void setClienteId(Integer id) {
        this.clienteId = id;
    }

    public Double getPorcentajeComision() {
        return porcentajeComision;
    }

    public void setPorcentajeComision(Double porcentajeComision) {
        this.porcentajeComision = porcentajeComision;
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    public Integer getFacturaId() {
        return facturaId;
    }

    public void setFacturaId(Integer facturaId) {
        this.facturaId = facturaId;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    public Double getComisionFinanciamiento() {
        return comisionFinanciamiento;
    }

    public void setComisionFinanciamiento(Double comisionFinanciamiento) {
        this.comisionFinanciamiento = comisionFinanciamiento;
    }

    public Double getImpuestoVentas() {
        return impuestoVentas;
    }

    public void setImpuestoVentas(Double impuestoVentas) {
        this.impuestoVentas = impuestoVentas;

    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Double getMontoAnticipado() {
        return montoAnticipado;
    }

    public void setMontoAnticipado(Double montoAnticipado) {
        this.montoAnticipado = montoAnticipado;
    }

    public Double getSaldoPendiente() {
        return saldoPendiente;
    }

    public void setSaldoPendiente(Double saldoPendiente) {
        this.saldoPendiente = saldoPendiente;
    }

    public Double getSaldoPendienteMoneda() {
        return saldoPendienteMoneda;
    }

    public void setSaldoPendienteMoneda(Double saldoPendienteMoneda) {
        this.saldoPendienteMoneda = saldoPendienteMoneda;
    }

    public Double getTipoCambioMonto() {
        return tipoCambioMonto;
    }

    public void setTipoCambioMonto(Double tipoCambioMonto) {
        this.tipoCambioMonto = tipoCambioMonto;
    }

    public Integer getEncargadoId() {
        return encargadoId;
    }

    public void setEncargadoId(Integer encargadoId) {
        this.encargadoId = encargadoId;
    }

    public String getCorreoCliente() {
        return correoCliente;
    }

    public void setCorreoCliente(String correoCliente) {
        this.correoCliente = correoCliente;
    }

    public Integer getEnviadaHacienda() {
        return enviadaHacienda;
    }

    public void setEnviadaHacienda(Integer enviadaHacienda) {
        this.enviadaHacienda = enviadaHacienda;
    }

    public String getCorreoCliente2() {
        return correoCliente2;
    }

    public void setCorreoCliente2(String correoCliente2) {
        this.correoCliente2 = correoCliente2;
    }

    public String getCorreoCliente3() {
        return correoCliente3;
    }

    public void setCorreoCliente3(String correoCliente3) {
        this.correoCliente3 = correoCliente3;
    }

    public String getCorreoCliente4() {
        return correoCliente4;
    }

    public void setCorreoCliente4(String correoCliente4) {
        this.correoCliente4 = correoCliente4;
    }

    public Boolean getEsClienteNuevo() {
        return esClienteNuevo;
    }

    public void setEsClienteNuevo(Boolean esClienteNuevo) {
        this.esClienteNuevo = esClienteNuevo;
    }

    public String getEstadoPago() {
        return estadoPago;
    }

    public void setEstadoPago(String estadoPago) {
        this.estadoPago = estadoPago;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }


    public String getFisicaOJuridica() {
        return fisicaOJuridica;
    }

    public void setFisicaOJuridica(String fisicaOJuridica) {
        this.fisicaOJuridica = fisicaOJuridica;
    }

    public Integer getFacturaElectronicaConsecutivo() {
        return facturaElectronicaConsecutivo;
    }

    public void setFacturaElectronicaConsecutivo(Integer facturaElectronicaConsecutivo) {
        this.facturaElectronicaConsecutivo = facturaElectronicaConsecutivo;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getClienteNombre() {
        return clienteNombre;
    }

    public void setClienteNombre(String clienteNombre) {
        this.clienteNombre = clienteNombre;
    }
}
