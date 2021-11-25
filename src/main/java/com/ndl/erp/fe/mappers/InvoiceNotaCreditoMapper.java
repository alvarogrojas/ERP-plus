package com.ndl.erp.fe.mappers;

//import com.ndl.erp.fe.FacturaElectronica;

import com.ndl.erp.domain.*;
import com.ndl.erp.fe.helpers.BillHelper;
import com.ndl.erp.fe.helpers.ComprobanteElectronicoMapper;
import com.ndl.erp.fe.nce.ImpuestoType;
import com.ndl.erp.fe.nce.NotaCreditoElectronica;
import com.ndl.erp.fe.nce.ReceptorType;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;


public class InvoiceNotaCreditoMapper extends InvoiceBaseMapper implements ComprobanteElectronicoMapper<InvoiceNotaCredito, NotaCreditoElectronica> {

    private DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    private Double totalImpuestos = 0d;
    private Double totalServiciosGrabados = 0d;
    private Double totalServiciosExcento = 0d;

    public InvoiceNotaCreditoMapper(List<GeneralParameter> emisor, GeneralParameter iv ) {
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
    public NotaCreditoElectronica mapFacturaElectronica(InvoiceNotaCredito iv) throws DatatypeConfigurationException {
        NotaCreditoElectronica fe = new NotaCreditoElectronica();

        //Codigo del tiepo de documento.
        fe.setTipoDocumento(TIPO_DOCUMENTO_NOTA_CREDITO_ELECTRONICA);

        //Numero consecutivo de la factura.
        fe.setNumeroConsecutivo(iv.getId().toString());

        //Fecha de emision
        Date emisionDate = new Date();
        fe.setFechaEmision(this.getFechaEmision(emisionDate));
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
        fe.setReceptor(this.getReceptor(iv.getClient()));
        fe.setEsClienteInternacional(iv.getEsClienteInternacional());

        //Detalles de la factura.
        fe.setDetalleServicio(this.createDetalleServicio(iv.getDetails()));

        fe.setResumenFactura(this.getResumen(iv));

        agregarInformacionReferencia(fe.getInformacionReferencia(),iv);

        fe.setNormativa(this.createNormativa());
        fe.setEmpresaId(this.getEmisorData().getEmpresaId());

        fe.setId(iv.getId());
        fe.setCorreo(iv.getClient().getEmail());
        return fe;
    }



    /**
     * Datos del emisor
     * todo: No se de donde se obtienen estos datos.
     *
     * @return
     */
    protected com.ndl.erp.fe.nce.EmisorType getEmisor() {
        com.ndl.erp.fe.nce.EmisorType e = new com.ndl.erp.fe.nce.EmisorType();

        e.setNombre(this.getEmisorData().getNombre());

        com.ndl.erp.fe.nce.IdentificacionType idType = new com.ndl.erp.fe.nce.IdentificacionType();
        String cedula = this.transformCedula(this.getEmisorData().getCedula(), this.getEmisorData().getTipo());
        idType.setNumero(cedula);
        idType.setTipo(this.getEmisorData().getTipo());
        e.setIdentificacion(idType);

        com.ndl.erp.fe.nce.UbicacionType u = new com.ndl.erp.fe.nce.UbicacionType();
        u.setProvincia(this.getEmisorData().getProvincia());
        u.setCanton(this.getEmisorData().getCanton());
        u.setDistrito(this.getEmisorData().getDistrito());
        u.setBarrio("01");
        u.setOtrasSenas(this.getEmisorData().getDireccion());

        e.setUbicacion(u);
        e.setCorreoElectronico(this.getEmisorData().getCorreo1());
        com.ndl.erp.fe.nce.TelefonoType t = new com.ndl.erp.fe.nce.TelefonoType();
        t.setCodigoPais(BigInteger.valueOf(COUNTRY_CODE));
        //t.setNumTelefono(BigInteger.valueOf(Integer.parseInt(this.getEmisorData().getTelefono())));
        return e;
    }


    private NotaCreditoElectronica.ResumenFactura getResumen(InvoiceNotaCredito invoice) {
        NotaCreditoElectronica.ResumenFactura resumenFactura = new NotaCreditoElectronica.ResumenFactura();

        resumenFactura.setCodigoMoneda(this.getMoneda(invoice.getCurrency()));
        resumenFactura.setTotalServGravados(createDinero(this.totalServiciosGrabados));
        resumenFactura.setTotalServExentos(createDinero(this.totalServiciosExcento));
        resumenFactura.setTotalMercanciasGravadas(BigDecimal.valueOf(0));


        BigDecimal grabadoBD = createDinero(this.totalServiciosGrabados);
        BigDecimal exentoBD = createDinero(this.totalServiciosExcento);
        resumenFactura.setTotalGravado(grabadoBD);
        resumenFactura.setTotalExento(exentoBD);

        BigDecimal totalBD = grabadoBD.add(exentoBD);
        resumenFactura.setTotalVenta(totalBD);
        resumenFactura.setTotalVentaNeta(totalBD);
        BigDecimal bdImpuestos = createDinero(this.totalImpuestos);
        resumenFactura.setTotalImpuesto(bdImpuestos);

        BigDecimal totalComprobanteBD = totalBD.add(bdImpuestos);
        resumenFactura.setTotalComprobante(totalComprobanteBD);

        return resumenFactura;
    }


    /**
     * Agregar los detalles de los servicios
     * @param details
     * @return
     */
    protected NotaCreditoElectronica.DetalleServicio createDetalleServicio(List<InvoiceNotaCreditoDetail> details) {
        NotaCreditoElectronica.DetalleServicio detalleServicio = new NotaCreditoElectronica.DetalleServicio();
        this.createDetalle(details, detalleServicio);
        return detalleServicio;
    }


    /**
     * Itera por los detalles y los agrega a una estructura.
     * @param detalles
     * @param detalleServicio
     */
    protected void createDetalle(List<InvoiceNotaCreditoDetail>  detalles, NotaCreditoElectronica.DetalleServicio detalleServicio) {
        if (detalles != null) {
            this.totalServiciosExcento = 0d;
            this.totalServiciosGrabados = 0d;
            this.totalImpuestos = 0d;
            Integer linea = 0;
            for( InvoiceNotaCreditoDetail detail: detalles) {
                NotaCreditoElectronica.DetalleServicio.LineaDetalle ld = new NotaCreditoElectronica.DetalleServicio.LineaDetalle();
                this.totalImpuestos = totalImpuestos + this.createLineaDetalle(++linea, ld, detail);
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

    protected Double createLineaDetalle(Integer linea, NotaCreditoElectronica.DetalleServicio.LineaDetalle ld, InvoiceNotaCreditoDetail detail) {
        ld.setNumeroLinea(BigInteger.valueOf((long)linea.intValue()));
        ld.setCantidad(createQuantity(detail.getQuantity()));
        ld.setUnidadMedida(this.getSizeUnit(detail.getType()));

        ld.setDetalle(BillHelper.getDetailLine(detail.getDescription()));
        Double montoImpuesto = 0d;
        Double montoUnitarioAntesImpuestos = detail.getSubTotal() / detail.getQuantity();
        BigDecimal i = null;
        if (BillHelper.aplicaImpuestoVentas(detail.getTax())) {
            this.totalServiciosGrabados = this.totalServiciosGrabados + detail.getSubTotal();
            montoImpuesto = detail.getTax();
            i = createDinero(montoImpuesto);
            ImpuestoType it = new ImpuestoType();
            it.setCodigo("01");
            it.setTarifa(createDinero(13d));
            it.setMonto(i);
            ld.getImpuesto().add(it);

        } else {
            this.totalServiciosExcento = detail.getSubTotal() + this.totalServiciosExcento;
        }

        ld.setPrecioUnitario(createDinero(createPrecioUnitario(detail, detail.getSubTotal())));
        ld.setMontoTotal(createDinero(detail.getSubTotal()));
        ld.setSubTotal(createDinero(detail.getSubTotal()));
        Double totalColones = (ld.getPrecioUnitario().doubleValue() * detail.getQuantity()) + montoImpuesto;
        ld.setMontoTotalLinea(createDinero((totalColones)));
        if (i==null) {
            return 0d;
        }
        return i.doubleValue();
    }

    private Double createPrecioUnitario(InvoiceNotaCreditoDetail fd, Double montoAntesImpuestos) {
        Double price = 0d;
        return montoAntesImpuestos / fd.getQuantity();
    }


    /**
     * Impuesto
     */
    private com.ndl.erp.fe.nce.ImpuestoType getImpuesto(InvoiceDetail detail) {
        com.ndl.erp.fe.nce.ImpuestoType imp = new com.ndl.erp.fe.nce.ImpuestoType();
        BigDecimal tarifa = new BigDecimal(0);
        if(this.getIvPorcentage().getVal() !=null)
            tarifa = new BigDecimal(Double.parseDouble(this.getIvPorcentage().getVal()));
        imp.setTarifa(tarifa);
        imp.setMonto(BigDecimal.valueOf(detail.getTax()));

        //Impuesto General sobre las ventas == 01
        imp.setCodigo(IV_CODE);
        return imp;
    }

    private  void agregarInformacionReferencia(List<NotaCreditoElectronica.InformacionReferencia> refl, InvoiceNotaCredito data) {
        NotaCreditoElectronica.InformacionReferencia ref = new NotaCreditoElectronica.InformacionReferencia();
        ref.setTipoDoc("01");
        GregorianCalendar c = new GregorianCalendar();
        c.setTime(data.getDate());

        try {
            ref.setFechaEmision(DatatypeFactory.newInstance().newXMLGregorianCalendar(c));
        } catch (DatatypeConfigurationException e) {
            e.printStackTrace();
        }
        //ref.setNumero(data.g);
        ref.setCodigo(data.getCodigo());
        ref.setRazon(data.getRazon());
        refl.add(ref);

    }


    /**
     * Todo: Validar esta funcion, me parece que esta inconsistente.
     * Obtiene la linea detalle service
     * @param linea
     * @param ld
     * @param detail
     * @return
     */
    protected Double createLineaDetalleFromServices(Integer linea, NotaCreditoElectronica.DetalleServicio.LineaDetalle ld, InvoiceDetail detail) {
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


    protected com.ndl.erp.fe.nce.ReceptorType getReceptor(Client client) {
        //Tipo de identificacion.
        com.ndl.erp.fe.nce.IdentificacionType idType = new com.ndl.erp.fe.nce.IdentificacionType();
        idType.setNumero(transformCedula(client.getEnterpriceId(),client.getIdType()));
        idType.setTipo(client.getIdType());

        //Receptor.
        com.ndl.erp.fe.nce.ReceptorType r = new ReceptorType();
        r.setNombre(client.getName());
        r.setCorreoElectronico(client.getEmail());
        r.setIdentificacion(idType);
        if (!client.isNacional())
            r.setIdentificacionExtranjero(client.getEnterpriceId());
        return r;
    }



    protected NotaCreditoElectronica.Normativa createNormativa() {
        NotaCreditoElectronica.Normativa fn = new NotaCreditoElectronica.Normativa();
        fn.setFechaResolucion("20-02-2017 13:22:22");
        fn.setNumeroResolucion("DGT-R-48-2016");
        return fn;
    }

}
