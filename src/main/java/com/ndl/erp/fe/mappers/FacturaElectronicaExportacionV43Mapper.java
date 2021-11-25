package com.ndl.erp.fe.mappers;

import com.ndl.erp.domain.Client;
import com.ndl.erp.domain.GeneralParameter;
import com.ndl.erp.domain.Invoice;
import com.ndl.erp.domain.InvoiceDetail;
import com.ndl.erp.fe.v43.fee.*;
import com.ndl.erp.fe.v43.fee.FacturaElectronicaExportacion.DetalleServicio;
import com.ndl.erp.fe.v43.fee.FacturaElectronicaExportacion.DetalleServicio.LineaDetalle;
import com.ndl.erp.fe.v43.fee.FacturaElectronicaExportacion.ResumenFactura;
import com.ndl.erp.fe.helpers.BillHelper;
import com.ndl.erp.fe.helpers.ComprobanteElectronicoMapper;


import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class FacturaElectronicaExportacionV43Mapper extends InvoiceBaseMapper implements ComprobanteElectronicoMapper<Invoice, FacturaElectronicaExportacion> {

    private DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    private Double totalImpuestos = 0d;
    private Double totalServiciosGrabados = 0d;
    private Double totalMercGrabados = 0d;
    private Double totalServiciosExcento = 0d;
    private Double totalMercExcentos = 0d;
    private Double totalMercExoneradas = 0d;
    private Double totalServExoneradas = 0d;

    public FacturaElectronicaExportacionV43Mapper(List<GeneralParameter> emisor, GeneralParameter iv ) {
         this.setEmisor(emisor);
         this.setIvPorcentage(iv);
         this.poulateEmisorData();
    }
    /**
     * Tipo documento =
     * @param iv
     * @return
     * @throws DatatypeConfigurationException
     */
    public FacturaElectronicaExportacion mapFacturaElectronica(Invoice iv) throws DatatypeConfigurationException {
        FacturaElectronicaExportacion fe = new FacturaElectronicaExportacion();
        //Codigo del tiepo de documento.
        fe.setTipoDocumento(TIPO_DOCUMENTO_FACTURA_ELECTRONICA_EXPORTACION);

        //Numero consecutivo de la factura.
        fe.setNumeroConsecutivo(iv.getId().toString());

        //Fecha de emision
        Date emisionDate = new Date();
        XMLGregorianCalendar gc = this.getFechaEmision(emisionDate);

        fe.setFechaEmision(gc);
        fe.setFechaEmisionStr(this.getFechaEmisionStr(emisionDate));

        //Condicion de venta
        fe.setCondicionVenta(this.getCondicionVenta(iv.getCreditDays()));

        //Plazo del credito
        fe.setPlazoCredito(this.getPlazoCredito(iv.getCreditDays()));

        //Medio de pagos
        fe.getMedioPago().add(this.getMedioPago());

        //Emisor
        fe.setEmisor(this.getEmisor());

        //Receptor
        fe.setReceptor(this.getReceptor(iv, iv.getClient()));
        fe.setEsClienteInternacional(iv.getEsClienteInternacional());
        fe.setCodigoActividad(iv.getEconomicActivity().getCodigo());

        //Detalles de la factura.
        fe.setDetalleServicio(this.createDetalleServicio(iv.getDetails(), iv.getClient(),gc ));


        fe.setResumenFactura(this.getResumen(iv));

        agregarOtroContenido(fe);

        fe.setId(iv.getId());
        fe.setCorreo(iv.getClient().getEmail());
        return fe;
    }

    private void agregarOtroContenido(FacturaElectronicaExportacion fe) {

//        FacturaElectronica.Otros.OtroContenido oc = new FacturaElectronica.Otros.OtroContenido();
//        oc.setAny("aagonzalezrojas@gmail.com");
//        if (fe.getOtros()==null) {
//            fe.setOtros(new FacturaElectronica.Otros());
//        }
//
//        fe.getOtros().getOtroContenido().add(oc);
    }


    /**
     * Datos del emisor
     * todo: No se de donde se obtienen estos datos.
     *
     * @return
     */
    protected EmisorType getEmisor() {
        EmisorType e = new EmisorType();

        e.setNombre(this.getEmisorData().getNombre());

        IdentificacionType idType = new IdentificacionType();
        String cedula = this.transformCedula(this.getEmisorData().getCedula(), this.getEmisorData().getTipo());
        idType.setNumero(cedula);
        idType.setTipo(this.getEmisorData().getTipo());
        e.setIdentificacion(idType);

        UbicacionType u = new UbicacionType();
        u.setProvincia(this.getEmisorData().getProvincia());
        u.setCanton(this.getEmisorData().getCanton());
        u.setDistrito(this.getEmisorData().getDistrito());
        u.setBarrio("01");
        u.setOtrasSenas(this.getEmisorData().getDireccion());

        e.setUbicacion(u);
        e.setCorreoElectronico(this.getEmisorData().getCorreo1());
        TelefonoType t = new TelefonoType();
        t.setCodigoPais(BigInteger.valueOf(COUNTRY_CODE));
        //t.setNumTelefono(BigInteger.valueOf(Integer.parseInt(this.getEmisorData().getTelefono())));
        return e;
    }


    private ResumenFactura getResumen(Invoice invoice) {
        Double totalDescuentos = 0d;
        BigDecimal grabadoBD = createDinero(this.totalServiciosGrabados);
        BigDecimal exentoBD = createDinero(this.totalServiciosExcento);
        BigDecimal mercGrabado = createDinero(this.totalMercGrabados);
        BigDecimal totalGrabado = createDinero(this.totalMercGrabados + totalServiciosGrabados);
        BigDecimal totalExonerado = createDinero(this.totalMercExoneradas + totalServExoneradas);

        BigDecimal totalBD = totalGrabado.add(exentoBD);
        BigDecimal bdImpuestos = createDinero(this.totalImpuestos);

        BigDecimal totalComprobanteBD = totalBD.add(bdImpuestos.add(totalExonerado));

        Double totalVenta = totalGrabado.doubleValue() + totalExonerado.doubleValue() + this.totalServiciosExcento;
        Double totalVentaNeta = totalVenta - totalDescuentos;

        ResumenFactura resumenFactura = new ResumenFactura();

        setCodigoMonedaIfNeedIt(invoice, resumenFactura);
        resumenFactura.setTotalServGravados(createDinero(this.totalServiciosGrabados));
        resumenFactura.setTotalServExentos(createDinero(this.totalServiciosExcento));
        //resumenFactura.setTotalServExonerado(createDinero(this.totalServExoneradas));

        resumenFactura.setTotalMercanciasGravadas(createDinero(this.totalMercGrabados));
        resumenFactura.setTotalMercanciasExentas(createDinero(this.totalMercExcentos));

        //resumenFactura.setTotalMercExonerada(createDinero(this.totalMercExoneradas));
        resumenFactura.setTotalGravado(totalGrabado);
        resumenFactura.setTotalExento(exentoBD);

        //resumenFactura.setTotalExonerado(totalExonerado);

        resumenFactura.setTotalVenta(createDinero(totalVenta));
//        resumenFactura.setTotalVenta(totalBD);
        resumenFactura.setTotalDescuentos(BigDecimal.valueOf(totalDescuentos));
        resumenFactura.setTotalVentaNeta(createDinero(totalVentaNeta));
//        resumenFactura.setTotalVentaNeta(totalBD);
        resumenFactura.setTotalImpuesto(bdImpuestos);
        //resumenFactura.setTotalIVADevuelto(createDinero(0d));
        resumenFactura.setTotalOtrosCargos(createDinero(0d));
        resumenFactura.setTotalComprobante(totalComprobanteBD);

        return resumenFactura;
    }

    private void setCodigoMonedaIfNeedIt(Invoice invoice, ResumenFactura resumenFactura) {
        String currency = this.getMoneda(invoice.getCurrency());
        if (!BillHelper.isNationalCurrency(currency)) {
            CodigoMonedaType codigoMonedaType = new CodigoMonedaType();
            codigoMonedaType.setCodigoMoneda(currency);
            codigoMonedaType.setTipoCambio(createDinero(invoice.getCurrency().getValueSale()));
            resumenFactura.setCodigoTipoMoneda(codigoMonedaType);

        }
    }


    /**
     * Agregar los detalles de los servicios
     * @param details
     * @return
     */
    protected DetalleServicio createDetalleServicio(Set<InvoiceDetail> details, Client c, XMLGregorianCalendar date) {
        DetalleServicio detalleServicio = new DetalleServicio();
        this.createDetalle(details, detalleServicio, c, date);

        return detalleServicio;
    }


    /**
     * Itera por los detalles y los agrega a una estructura.
     * @param detalles
     * @param detalleServicio
     */
    protected void createDetalle(Set<InvoiceDetail>  detalles, DetalleServicio detalleServicio,
                                 Client c, XMLGregorianCalendar date) {
        if (detalles != null) {
            this.totalServiciosExcento = 0d;
            this.totalServiciosGrabados = 0d;
            this.totalMercExcentos = 0d;
            this.totalMercGrabados = 0d;
            this.totalImpuestos = 0d;
            totalMercExoneradas =0d;
            totalServExoneradas = 0d;
            Integer linea = 0;
            for( InvoiceDetail detail: detalles) {
                LineaDetalle ld = new LineaDetalle();
                this.totalImpuestos = totalImpuestos + this.createLineaDetalle(++linea, ld, detail, c, date);
                detalleServicio.getLineaDetalle().add(ld);
            }
        }
    }


    /**
     * Todo: Validar esta linea, parece que esta usando el Total en lugares incorrectos.
     * Obtiene un detalle
     * @param linea
     * @param ld
     * @param detail
     */

    protected Double createLineaDetalle(Integer linea, LineaDetalle ld, InvoiceDetail detail,
                                        Client c, XMLGregorianCalendar date) {
        ld.setNumeroLinea(BigInteger.valueOf((long)linea.intValue()));
        ld.setCodigo(detail.getCodigoCabys());
        ld.setCantidad(createQuantity(detail.getQuantity()));
        ld.setUnidadMedida(this.getSizeUnit(detail.getType()));

        ld.setDetalle(BillHelper.getDetailLine(detail.getDescription()));
        Double montoImpuesto = 0d;
        Double montoUnitarioAntesImpuestos = detail.getSubTotal() / detail.getQuantity();
        BigDecimal i = null;
        montoImpuesto = detail.getTax();

        i = createDinero(montoImpuesto);

        ImpuestoType it = new ImpuestoType();
        it.setCodigoTarifa(detail.getIva().getCode()); //13%
        it.setCodigo("01");
        it.setTarifa(createDinero(detail.getIva().getTaxPorcent()));
        it.setMonto(i);
        Boolean estaClienteExonerado = estaClienteExonerado(c);
        if (estaClienteExonerado) {
            //it.setExoneracion(createExoneracionZF(c, i, date));
            ld.setImpuestoNeto(createDinero(0d));
            if (detail.getType()!=null && BillHelper.UNIT_PRODUCTO.equalsIgnoreCase(detail.getType())) {

                this.totalMercExoneradas = this.totalMercExoneradas + + detail.getSubTotal();

            } else if (detail.getType()!=null && BillHelper.UNIT_SERVICE.equalsIgnoreCase(detail.getType())) {
                //} else if (fd.getMedida()!=null &&  BillHelper.UNIT_SERVICIO.equalsIgnoreCase(fd.getMedida().getCategoria())) {
                this.totalServExoneradas = this.totalServExoneradas + detail.getSubTotal();
            }
        } else {
            ld.setImpuestoNeto(i);

        }


        ld.getImpuesto().add(it);
        if ( !estaClienteExonerado && BillHelper.UNIT_PRODUCTO.equalsIgnoreCase(detail.getType())) {
            this.totalMercGrabados = this.totalMercGrabados + detail.getSubTotal();

        } else if (!estaClienteExonerado && BillHelper.UNIT_SERVICE.equalsIgnoreCase(detail.getType())) {
            this.totalServiciosGrabados = this.totalServiciosGrabados + detail.getSubTotal();
        }


        ld.setPrecioUnitario(createDinero(createPrecioUnitario(detail, detail.getSubTotal())));
        ld.setMontoTotal(createDinero(detail.getSubTotal()));
        ld.setSubTotal(createDinero(detail.getSubTotal()));
//        Double totalColones = (ld.getPrecioUnitario().doubleValue() * detail.getQuantity()) + montoImpuesto;
        Double totalColones = (ld.getPrecioUnitario().doubleValue() * detail.getQuantity()) + ld.getImpuestoNeto().doubleValue();
        ld.setMontoTotalLinea(createDinero((totalColones)));
        if (i==null) {
            return 0d;
        }
        return ld.getImpuestoNeto().doubleValue();

    }

    private Double createPrecioUnitario(InvoiceDetail fd, Double montoAntesImpuestos) {
        Double price = 0d;
        return montoAntesImpuestos / fd.getQuantity();
    }

    /**
     * Impuesto
     */
    private ImpuestoType getImpuesto(InvoiceDetail detail) {
        ImpuestoType imp = new ImpuestoType();
        BigDecimal tarifa = new BigDecimal(0);
        if(this.getIvPorcentage().getVal() !=null)
            tarifa = new BigDecimal(Double.parseDouble(this.getIvPorcentage().getVal()));
        imp.setTarifa(tarifa);
        imp.setMonto(BigDecimal.valueOf(detail.getTax()));

        //Impuesto General sobre las ventas == 01
        imp.setCodigo(IV_CODE);
        return imp;
    }


    /**
     * Todo: Validar esta funcion, me parece que esta inconsistente.
     * Obtiene la linea detalle service
     * @param linea
     * @param ld
     * @param detail
     * @return
     */
    protected Double createLineaDetalleFromServices(Integer linea, LineaDetalle ld, InvoiceDetail detail) {
        ld.setNumeroLinea(BigInteger.valueOf((long)linea.intValue()));
        ld.setCantidad(BigDecimal.valueOf(detail.getQuantity()));
        ld.setDetalle(detail.getDescription() + " " + detail.getDescription());
        ld.setPrecioUnitario(this.createDinero(detail.getPrice()));
        ld.setMontoTotal(this.createDinero(detail.getTotal()));
        ld.setSubTotal(this.createDinero(detail.getTotal())); // todo Validar este dato, me parece que hay que eliminarlo.
        Double totalColones = detail.getTotal();
        Double montoImpuesto = 0.0D;
        ld.setMontoTotalLinea(BigDecimal.valueOf(totalColones));
        ld.setUnidadMedida(this.getEmisorData().getUnidadMedida());
        return montoImpuesto; //todo: Validar este valor, se retorna 0.
    }


    protected ReceptorType getReceptor(Invoice i, Client client) {


        ReceptorType r = new ReceptorType();
            r.setNombre(client.getName());
            r.setCorreoElectronico(client.getEmail());
//            IdentificacionType idType = new IdentificacionType();
//            idType.setNumero(transformCedula(client.getEnterpriceId(), client.getIdType()));
//            idType.setTipo(client.getIdType());
//            r.setIdentificacion(idType);
//        }
        return r;
    }

    public FacturaElectronicaExportacion.InformacionReferencia getInformacionReferenciaSustituyePorRechazoFe(FacturaElectronicaExportacion oldDocument) {
        FacturaElectronicaExportacion.InformacionReferencia ir = new FacturaElectronicaExportacion.InformacionReferencia();

        ir.setCodigo("04");
        ir.setFechaEmision(oldDocument.getFechaEmision());
        ir.setNumero(oldDocument.getNumeroConsecutivo()); //INVESTIGAR SI ES CLAVE O EL CONSECUTIVO
        ir.setTipoDoc("09");
        ir.setRazon("Sustituye documento por rechazo");

        return ir;
    }



//    protected Normativa createNormativa() {
//        Normativa fn = new Normativa();
//        fn.setFechaResolucion("20-02-2017 13:22:22");
//        fn.setNumeroResolucion("DGT-R-48-2016");
//        return fn;
//    }

    protected ExoneracionType createExoneracionZF(Client cliente, BigDecimal monto, XMLGregorianCalendar date) {

        ExoneracionType et = new ExoneracionType();
        et.setTipoDocumento(BillHelper.TIPO_DOC_AUTORIZADO_POR_LEY);
        et.setNumeroDocumento(BillHelper.NUM_DOCUMENTO_EXONERADO);
        et.setNombreInstitucion(cliente.getName());
        et.setMontoExoneracion(monto);
        et.setPorcentajeExoneracion(BillHelper.PORCENTAJE_EXONERACION_ZONA_FRANCA);
        et.setFechaEmision(date);

        return et;

    }



}