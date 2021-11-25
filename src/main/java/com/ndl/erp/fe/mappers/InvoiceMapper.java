package com.ndl.erp.fe.mappers;

import com.ndl.erp.domain.Client;
import com.ndl.erp.domain.GeneralParameter;
import com.ndl.erp.domain.Invoice;
import com.ndl.erp.domain.InvoiceDetail;

import com.ndl.erp.fe.helpers.BillHelper;
import com.ndl.erp.fe.helpers.ComprobanteElectronicoMapper;
import com.ndl.erp.fe.*;

import javax.xml.datatype.DatatypeConfigurationException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class InvoiceMapper extends InvoiceBaseMapper implements ComprobanteElectronicoMapper<Invoice, FacturaElectronica> {

    private DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    private Double totalImpuestos = 0d;
    private Double totalServiciosGrabados = 0d;
    private Double totalServiciosExcento = 0d;

    public InvoiceMapper(List<GeneralParameter> emisor, GeneralParameter iv ) {
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
    public FacturaElectronica mapFacturaElectronica(Invoice iv) throws DatatypeConfigurationException {
        FacturaElectronica fe = new FacturaElectronica();
        //Codigo del tiepo de documento.
        fe.setTipoDocumento(TIPO_DOCUMENTO_FACTURA_ELECTRONICA);

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


    private FacturaElectronica.ResumenFactura getResumen(Invoice invoice) {
        FacturaElectronica.ResumenFactura resumenFactura = new FacturaElectronica.ResumenFactura();

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
    protected FacturaElectronica.DetalleServicio createDetalleServicio(Set<InvoiceDetail> details) {
        FacturaElectronica.DetalleServicio detalleServicio = new FacturaElectronica.DetalleServicio();
        this.createDetalle(details, detalleServicio);
        return detalleServicio;
    }


    /**
     * Itera por los detalles y los agrega a una estructura.
     * @param detalles
     * @param detalleServicio
     */
    protected void createDetalle(Set<InvoiceDetail>  detalles, FacturaElectronica.DetalleServicio detalleServicio) {
        if (detalles != null) {
            this.totalServiciosExcento = 0d;
            this.totalServiciosGrabados = 0d;
            this.totalImpuestos = 0d;
            Integer linea = 0;
            for( InvoiceDetail detail: detalles) {
                FacturaElectronica.DetalleServicio.LineaDetalle ld = new FacturaElectronica.DetalleServicio.LineaDetalle();
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

    protected Double createLineaDetalle(Integer linea, FacturaElectronica.DetalleServicio.LineaDetalle ld, InvoiceDetail detail) {
        ld.setNumeroLinea(BigInteger.valueOf((long)linea.intValue()));
        ld.setCantidad(createQuantity(detail.getQuantity()));
        ld.setUnidadMedida(this.getSizeUnit(detail.getType()));
        ld.setDetalle(detail.getDescription());
        Double montoImpuesto = 0d;
        Double montoUnitarioAntesImpuestos = detail.getSubTotal() / detail.getQuantity();
        BigDecimal i = null;
        if (BillHelper.aplicaImpuestoVentas(detail.getTax())) {
                this.totalServiciosGrabados = this.totalServiciosGrabados + detail.getSubTotal();
                montoImpuesto = detail.getTax();
                i = createDinero(montoImpuesto);
                ImpuestoType it = new ImpuestoType();
                it.setCodigo("01");
                it.setTarifa(createDinero(detail.getIva().getTaxPorcent()));
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
    protected Double createLineaDetalleFromServices(Integer linea, FacturaElectronica.DetalleServicio.LineaDetalle ld, InvoiceDetail detail) {
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


    protected ReceptorType getReceptor(Client client) {
        //Tipo de identificacion.

        //Receptor.
        ReceptorType r = new ReceptorType();
        if (!client.isNacional())
            r.setIdentificacionExtranjero(client.getEnterpriceId());
        else {
            r.setNombre(client.getName());
            r.setCorreoElectronico(client.getEmail());
            IdentificacionType idType = new IdentificacionType();
            idType.setNumero(transformCedula(client.getEnterpriceId(), client.getIdType()));
            idType.setTipo(client.getIdType());
            r.setIdentificacion(idType);
        }

        return r;
    }



    protected FacturaElectronica.Normativa createNormativa() {
        FacturaElectronica.Normativa fn = new FacturaElectronica.Normativa();
        fn.setFechaResolucion("20-02-2017 13:22:22");
        fn.setNumeroResolucion("DGT-R-48-2016");
        return fn;
    }



}