package com.ndl.erp.fe.mappers;

import com.ndl.erp.domain.Client;
import com.ndl.erp.domain.GeneralParameter;
import com.ndl.erp.domain.Invoice;
import com.ndl.erp.domain.InvoiceDetail;
import com.ndl.erp.fe.nce.*;
import com.ndl.erp.fe.nce.NotaCreditoElectronica.DetalleServicio;
import com.ndl.erp.fe.nce.NotaCreditoElectronica.DetalleServicio.LineaDetalle;
import com.ndl.erp.fe.nce.NotaCreditoElectronica.Normativa;
import com.ndl.erp.fe.nce.NotaCreditoElectronica.ResumenFactura;
import com.ndl.erp.fe.helpers.ComprobanteElectronicoMapper;


import javax.xml.datatype.DatatypeConfigurationException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Set;


public class InvoceCreditNoteMapper extends InvoiceBaseMapper implements ComprobanteElectronicoMapper<Invoice, NotaCreditoElectronica> {


    public InvoceCreditNoteMapper(List<GeneralParameter> emisor, GeneralParameter ivProcentage ) {
        this.setEmisor(emisor);
        this.setIvPorcentage(ivProcentage);
        this.poulateEmisorData();

    }

    public NotaCreditoElectronica mapFacturaElectronica(Invoice iv) throws DatatypeConfigurationException {

        NotaCreditoElectronica nce = new NotaCreditoElectronica();

        //Codigo del tiepo de documento.
        nce.setTipoDocumento(TIPO_DOCUMENTO_FACTURA_ELECTRONICA);

        //Numero consecutivo de la factura.
        nce.setNumeroConsecutivo(iv.getConsecutivo());

        //Fecha de emision
        Date emisionDate = new Date();
        nce.setFechaEmision(this.getFechaEmision(emisionDate));
        nce.setFechaEmisionStr(this.getFechaEmisionStr(emisionDate));

        //Condicion de venta
        nce.setCondicionVenta(this.getCondicionVenta(iv.getCreditDays()));

        //Plazo del credito
        nce.setPlazoCredito(this.getPlazoCredito(iv.getCreditDays()));

        //Medio de pagos
        nce.getMedioPago().add(this.getMedioPago());

        //Emisor
        nce.setEmisor(this.getEmisor());

        //Receptor
        nce.setReceptor(this.getReceptor(iv.getClient()));

        //Detalles de la factura.
        nce.setDetalleServicio(this.createDetalleServicio(iv.getDetails()));


        // Alvaro tiene que validar de donde se estan tomando.
        nce.setResumenFactura(this.getResumen(iv));

        nce.setNormativa(this.createNormativa());
        //nce.setEmpresaId(this.emisorData.getEmpresaId());

        nce.setId(iv.getId());
        nce.setCorreo(iv.getClient().getEmail());

        return null;
    }

    private NotaCreditoElectronica.ResumenFactura getResumen(Invoice invoice) {
        ResumenFactura resumenFactura = new ResumenFactura();

        resumenFactura.setCodigoMoneda(this.getMoneda(invoice.getCurrency()));

        Double exentos = invoice.getTotal() - invoice.getIv();
        resumenFactura.setTotalMercanciasGravadas(BigDecimal.valueOf(0L));
        resumenFactura.setTotalMercanciasExentas(BigDecimal.valueOf(0L));
        resumenFactura.setTotalServExentos(this.createDinero(exentos));
        resumenFactura.setTotalServGravados(this.createDinero(invoice.getIv()));
        resumenFactura.setTotalGravado(this.createDinero(invoice.getIv()));
        resumenFactura.setTotalExento(this.createDinero(exentos));
        BigDecimal ventaTotal = resumenFactura.getTotalGravado().add(resumenFactura.getTotalExento());
        ventaTotal = ventaTotal.setScale(5, 1);
        resumenFactura.setTotalVenta(ventaTotal);
        resumenFactura.setTotalDescuentos(BigDecimal.valueOf(0L));
        resumenFactura.setTotalVentaNeta(ventaTotal);
        resumenFactura.setTotalImpuesto(this.createDinero(invoice.getIv()));
        BigDecimal totalComprobante = resumenFactura.getTotalVenta().add(resumenFactura.getTotalImpuesto());
        totalComprobante = totalComprobante.setScale(5, 1);
        resumenFactura.setTotalComprobante(totalComprobante);
        return resumenFactura;
    }



    /**
     * Datos del emisor
     * todo: No se de donde se obtienen estos datos.
     *
     * @return
     */
private EmisorType getEmisor() {
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
//        t.setNumTelefono(BigInteger.valueOf((long) this.getEmisorData().getTelefono().intValue()));
        return e;
    }




    /**
     * Agregar los detalles de los servicios
     *
     * @param details
     * @return
     */
    private DetalleServicio createDetalleServicio(Set<InvoiceDetail> details) {
        DetalleServicio detalleServicio = new DetalleServicio();
        this.createDetalle(details, detalleServicio);
        return detalleServicio;
    }


    /**
     * Itera por los detalles y los agrega a una estructura.
     *
     * @param detalles
     * @param detalleServicio
     */
    private void createDetalle(Set<InvoiceDetail> detalles, DetalleServicio detalleServicio) {
        if (detalles != null) {
            Integer linea = 0;
            for (InvoiceDetail detail : detalles) {
                LineaDetalle ld = new LineaDetalle();
                this.createLineaDetalle(++linea, ld, detail);
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
//    public static final String UNIDAD_MEDIDAS = "Otros";
    private void createLineaDetalle(Integer linea, LineaDetalle ld, InvoiceDetail detail) {
        ld.setNumeroLinea(BigInteger.valueOf((long)linea.intValue()));
        ld.setCantidad(createQuantity(detail.getQuantity()));

        //Todo: Validar este dato con alvaro.
        ld.setUnidadMedida(this.getSizeUnit(detail.getType()));
        ld.setDetalle(detail.getDescription());
        ld.setPrecioUnitario(BigDecimal.valueOf(detail.getPrice()));
        ld.setMontoTotal(BigDecimal.valueOf(detail.getQuantity() * detail.getPrice()));
        ld.setSubTotal(ld.getMontoTotal());

        //Poner el impuesto de ventas para cada inte.
        ld.setMontoTotalLinea(ld.getMontoTotal());
    }


    private ReceptorType getReceptor(Client client) {
        //Tipo de identificacion.
        IdentificacionType idType = new IdentificacionType();
        idType.setNumero(client.getEnterpriceId());
        idType.setTipo(client.getIdType());

        //Receptor.
        ReceptorType r = new ReceptorType();
        r.setNombre(client.getName());
        r.setCorreoElectronico(client.getEmail());
        r.setIdentificacion(idType);
        if (!client.isNacional())
            r.setIdentificacionExtranjero(client.getEnterpriceId());
        return r;
    }


    private Normativa createNormativa() {
        Normativa fn = new Normativa();
        fn.setFechaResolucion("20-02-2017 13:22:22");
        fn.setNumeroResolucion("DGT-R-48-2016");
        return fn;
    }

}
