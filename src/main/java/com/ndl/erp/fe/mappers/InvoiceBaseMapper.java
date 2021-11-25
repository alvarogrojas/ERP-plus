package com.ndl.erp.fe.mappers;


import com.ndl.erp.fe.core.BillConfigurationData;
import com.ndl.erp.fe.dtos.EmisorData;

import com.ndl.erp.domain.Client;
import com.ndl.erp.domain.Currency;
import com.ndl.erp.domain.GeneralParameter;
import com.ndl.erp.fe.helpers.BillHelper;
import com.ndl.erp.domain.ExchangeRate;
import com.ndl.erp.fe.v43.te.CodigoMonedaType;
import org.springframework.beans.factory.annotation.Autowired;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public abstract class InvoiceBaseMapper {

//    public static final String UNIDAD_MEDIDAS = "Otros";

    @Autowired
    protected BillConfigurationData billConfigurationData;

    public static final String IV_CODE = "01";
    public static final Long COUNTRY_CODE = 506L;

    public final static String TIPO_DOCUMENTO_FACTURA_ELECTRONICA = "01";
    public final static String TIPO_DOCUMENTO_TIQUETE_ELECTRONIC0 = "04";
    public final static String TIPO_DOCUMENTO_FACTURA_ELECTRONICA_EXPORTACION = "09";
    public final static String TIPO_DOCUMENTO_NOTA_CREDITO_ELECTRONICA = "03";
    private DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    private EmisorData emisorData;
    private List<GeneralParameter> emisor;
    //    private GeneralParameter emisor;
    private GeneralParameter ivPorcentage;

    protected String getSizeUnit(String type) {
        if (BillHelper.UNIT_PRODUCTO.equalsIgnoreCase(type))
            return BillHelper.UNIDAD;
        else if (BillHelper.UNIT_SERVICE.equalsIgnoreCase(type))
            return BillHelper.SERVICIOS;
        else
            return BillHelper.SERVICIOS;
    }

    protected String getMoneda(Currency currency) {
        return currency.getName().equalsIgnoreCase("Colon") ? "CRC" : "USD";
    }

    protected BigDecimal createDinero(Double value) {
        BigDecimal bd = BigDecimal.valueOf(value);
        return bd.setScale(5,BigDecimal.ROUND_HALF_EVEN);
    }

    protected BigDecimal createQuantity(Double value) {
        BigDecimal bd = BigDecimal.valueOf(value);
        return bd.setScale(3,BigDecimal.ROUND_HALF_EVEN);
    }



    protected XMLGregorianCalendar getFechaEmision(Date fechaFacturacion) throws DatatypeConfigurationException {
        if (fechaFacturacion == null) {
            return null;
        } else {
            GregorianCalendar c = new GregorianCalendar();
            c.setTime(new Date());
            return DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
        }
    }


    protected String getFechaEmisionStr(Date fechaFacturacion) {
        return fechaFacturacion == null ? null : this.df.format(fechaFacturacion);
    }


    public static final String CONCICION_VENTA_CONTADO = "01";
    public static final String CONCICION_VENTA_CREDITO = "02";

    protected String getCondicionVenta(Integer credito) {
        String result = CONCICION_VENTA_CONTADO;
        if (credito!=null || credito ==0) {
            result = CONCICION_VENTA_CONTADO;
        } else if (credito==null || credito > 0) {
            result = CONCICION_VENTA_CREDITO;
        }

        return result;
    }


    /**
     * Indica si el pago es a credito debe haber un plazo de credito.
     *
     * @param days
     * @return
     */
    protected String getPlazoCredito(Integer days) {
        return days != null && days > 0 ? days.toString() : "";
    }

    /**
     * Los medios de pago posibles son.
     * Efectivo: 01
     * Tarjeta: 02
     * Cheque: 03
     * Transferencia : 04
     * Recaudado : 05
     * Otros: 99
     *
     * @return
     */
    protected String getMedioPago() {
        return "04";
    }


    /**
     * Transforma la cedula, pero a que no tengo ni idea.
     *
     * @param cedula
     * @param tipo
     * @return
     */
    protected String transformCedula(String cedula, String tipo) {
        if (cedula != null) {
            cedula = cedula.replace(" ", "");
            cedula = cedula.replace("-", "");
            if (tipo != null && tipo.equals("02") && cedula.length() > 9 && (cedula.startsWith("30101") || cedula.startsWith("3101"))) {
                if (cedula.startsWith("30101")) {
                    cedula = "3" + cedula.substring(2);
                }

                if (cedula.startsWith("3101") && cedula.length() > 9) {
                    cedula = cedula.substring(0, 10);
                }
            }
        }

        return cedula;
    }


    protected void poulateEmisorData() {
        if (this.emisorData == null)
            this.emisorData = new EmisorData();

        for (GeneralParameter gp : emisor) {
            switch (gp.getName()) {
                case "NAME":
                    this.emisorData.setNombre(gp.getVal());
                    break;
                case "IDTYPE":
                    this.emisorData.setTipo(gp.getVal());
                    break;
                case "CEDULA":
                    this.emisorData.setCedula(gp.getVal());
                    break;
                case "PROVINCIA":
                    this.emisorData.setProvincia(gp.getVal());
                    break;
                case "CANTON":
                    this.emisorData.setCanton(gp.getVal());
                    break;
                case "DISTRITO":
                    this.emisorData.setDistrito(gp.getVal());
                    break;
                case "BARRIO":
                    break;
                case "ADDRESS":
                    this.emisorData.setDireccion(gp.getVal());
                    break;
                case "EMAIL":
                    this.emisorData.setCorreo1(gp.getVal());
                    break;
                case "PHONE":
                    this.emisorData.setTelefono(gp.getVal());
                    break;
            }

        }
    }


    public EmisorData getEmisorData() {
        return emisorData;
    }

    public void setEmisorData(EmisorData emisorData) {
        this.emisorData = emisorData;
    }

    public void setEmisor(List<GeneralParameter> emisor) {
        this.emisor = emisor;
    }

    public GeneralParameter getIvPorcentage() {
        return ivPorcentage;
    }

    public void setIvPorcentage(GeneralParameter ivPorcentage) {
        this.ivPorcentage = ivPorcentage;
    }

    protected boolean estaClienteExonerado(Client c) {
        if (c.getExonerated()!=null && c.getExonerated().equals("SI")) {
            return true;
        }
        return false;
    }


    protected CodigoMonedaType getCodigoTipoMoneda(Currency currency, ExchangeRate e) {
        CodigoMonedaType codigoMonedaType = new CodigoMonedaType();

        codigoMonedaType.setCodigoMoneda(getMoneda(currency));
        codigoMonedaType.setTipoCambio(createDinero(getTipoCambio(currency)));
        return codigoMonedaType;
    }

    protected  Double getTipoCambio(Currency currency) {
        return currency.getName().equalsIgnoreCase("Colon")? 1d: 620d;
    }
}
