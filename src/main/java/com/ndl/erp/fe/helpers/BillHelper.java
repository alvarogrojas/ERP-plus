package com.ndl.erp.fe.helpers;

import com.ndl.erp.domain.Client;
import com.ndl.erp.domain.ExchangeRate;
import com.ndl.erp.domain.Invoice;
import com.ndl.erp.dto.ResultFe;
import com.ndl.erp.fe.v43.FacturaElectronica;
import com.ndl.erp.fe.v43.fee.FacturaElectronicaExportacion;
import com.ndl.erp.fe.v43.nc.NotaCreditoElectronica;
import org.apache.commons.lang.StringUtils;


import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class BillHelper {

    public static final String PAIS_CODE = "506";
    public static final String SITUATION_FE = "1";

    public static final Map<String, String> CONDICION_VENTA = new HashMap<String, String>()
    {
        {
            put("01", "Contado");
            put("02", "Crédito");
            put("03", "Consignación");
            put("04", "Apartado");
            put("05", "Arrendamiento con opción de compra ");
            put("06", "Arrendamiento en función financiera ");
            put("07", "Cobro a favor de un tercero");
            put("08", "Servicios prestados al Estado a crédito");
            put("09", "Pago del servicios prestado al Estado ");
            put("99", "Otros");
        };
    };

    public static final Map<String, String> MEDIO_PAGO = new HashMap<String, String>()
    {
        {
            put("01", "Efectivo");
            put("02", "Tarjeta");
            put("03", "Cheque");
            put("04", "Transferencia – depósito bancario ");
            put("05", "Recaudado por terceros  ");

            put("99", "Otros");
        };
    };

    public static final Map<String, String> TIPO_IDENTIFICACION = new HashMap<String, String>()
    {
        {
            put("01", "Cédula Física");
            put("02", "Cédula Jurídica");
            put("03", "DIMEX");
            put("04", "NITE ");

        };
    };

    public static final String TIPO_DOC_AUTORIZADO_POR_LEY = "03";
    public static final String NUM_DOCUMENTO_EXONERADO = "Num 7210 - Articulo 23";
//    public static final BigInteger PORCENTAJE_EXONERACION_ZONA_FRANCA = BigInteger.valueOf(100l);
    public static final BigInteger PORCENTAJE_EXONERACION_ZONA_FRANCA = BigInteger.valueOf(13);

    public static final String NATIONAL_CURRENCY = "CRC";

    public static final String ESTADO_HACIENDA_DEFAULT = "no_enviada";
    private static final String BILL_BASE_IMAGES_PATH = "/home/erp/billapp/images/";

    public static final String UNIT_PRODUCTO = "Producto";
    public static final String UNIT_SERVICE = "Servicio";

    public static final String UNIDAD = "Unid";
    public static final String SERVICIOS = "Sp";

    public static final String INGRESADA = "Ingresada";
    public static final String ANULADA = "Anulada";
    public static final String PENDIENTE_PAGO = "Pendiente Pago";
    public static final String VENCIDAS = "Vencida";

    public static final String ESPERA = "ESPERA";
    public static final int MAX_SENDER_EACH_FIVE_MINUTES = 20;

    private static final String TAX_NACIONALIZACION = "NACIONALIZACION";
    private static final String TAX_DUA_VENTA_LOCAL_ZF = "DUA VENTA LOCAL ZONA FRANCA";

    public static final String PENDIENTE = "pendiente";

    private static final String NA_EMAIL= "NA";
    public static final String ACTIVA= "Anulada";
    public static final String ACTIVA_NC = "ACTIVA_NC";

    public static final String SUPERVISOR = "SUPERVISOR";
    public static final String ADMIN = "ADMIN";
    public static final String COORDINADOR_LEGAL = "COORDINADOR_LEGAL";

    public static final String CALIFICACION_BUENA = "BUENA";
    public static final String CALIFICACION_MALA = "MALA";
    public static final String NO_CALIFICADA = "NO CALIFICADA";

    public static final String DOCUMENTO_FACTURA_NO = "Factura No.";
    public static final String DOCUMENTO_NOTA_CREDITO_NO = "Nota Credito.";

    public static final String FACTURA_ELECTRONICA_TIPO = "01";
    public static final String NOTA_DEBITO_TIPO = "02";
    public static final String NOTA_CREDITO_TIPO = "03";
    public static final String TIQUETE_ELECTRONICO = "04";
    public static final String FACTURA_ELECTRONICA_EXPORTACION_TIPO = "09";
    public static final String CONFIRMACION_ACEPTACION_COMPROBANTE_TIPO = "05";
    public static final String CONFIRMACION_ACEPTACION_PARCIAL_COMPROBANTE = "06";
    public static final String CONFIRMACION_RECHAZO_COMPROBANTE = "07";


    public static final String CONTADO = "01";
    public static final String CREDITO = "02";

    public static final String MEDIO_TRANSERENCIA_BANCARIA = "04";
    public static final String SERVICIOS_PROFESIONALES = "Sp";
    //    public static final Integer SERVICIOS_PROFESIONALES = 1;
    public static final Integer CONFIRMACION_ACEPTADO = 1;
    public static final Integer CONFIRMACION_ACEPTADO_PARCIALMENTE = 2;
    public static final Integer CONFIRMACION_RECHAZADO = 3;
    public static final String PREFIX_MENSAJE_HACIENDA_FILE = "mensajehacienda_";

//    public static final String FACTURA_NAMESPACE_V42 = "https://tribunet.hacienda.go.cr/docs/esquemas/2017/v4.2/facturaElectronica";
//    public static final String NOTA_CREDITO_NAMESPACE_V42 = "https://tribunet.hacienda.go.cr/docs/esquemas/2017/v4.2/notaCreditoElectronica";
//    public static final String MENSAJE_RECEPTOR_NAMESPACE_V42 = "https://tribunet.hacienda.go.cr/docs/esquemas/2017/v4.2/mensajeReceptor";

    public static final String FACTURA_NAMESPACE_V43 = "https://cdn.comprobanteselectronicos.go.cr/xml-schemas/v4.3/facturaElectronica";
    public static final String TIQUETE_NAMESPACE_V43 = "https://cdn.comprobanteselectronicos.go.cr/xml-schemas/v4.3/tiqueteElectronico";
    public static final String FACTURA_ELECTRONICA_NAMESPACE_V43 = "https://cdn.comprobanteselectronicos.go.cr/xml-schemas/v4.3/facturaElectronicaExportacion";
    public static final String NOTA_CREDITO_NAMESPACE_V43 = "https://cdn.comprobanteselectronicos.go.cr/xml-schemas/v4.3/notaCreditoElectronica";
    //public static final String CONFIRMACION_NAMESPACE_V43 = "https://tribunet.hacienda.go.cr/docs/esquemas/2017/v4.2/notaCreditoElectronica";
    public static final String MENSAJE_RECEPTOR_NAMESPACE_V43 = "https://cdn.comprobanteselectronicos.go.cr/xml-schemas/v4.3/mensajeReceptor";




    public static final Integer TARIFA_IMPUESTO_VENTAS = 13;
    public static final Double IMPUESTO_VENTAS_PORCENTAJE = 0.13;

    public static final String FACTURA_ELECTRONICA_BASE_XML = "/FacturaElectronica";
    public static final String FACTURA_ELECTRONICA_EXPORTACION_BASE_XML = "/FacturaElectronicaExportacion";
    public static final String TIQUETE_ELECTRONICO_BASE_XML = "/tiqueteElectronico";
    public static final String MENSAJE_RECEPTOR_BASE_XML = "/MensajeReceptor";


    public static final String NOTA_CREDITO_ELECTRONICA_BASE_XML = "/NotaCreditoElectronica";
    public static final String TIPO_CONFIRMACION_FE = "CFN";
    public static final String TIPO_RECHAZO_FE = "RCH";

    public static final String TIPO_FACTURA_FE = "FE";
    public static final String TIPO_TIQUETE_E = "TIQ";
    public static final String TIPO_FACTURA_FEE = "FEE";
    public static final String TIPO_NOTA_CREDITO_FE = "NCF";
    public static final String RESPUESTA_ACEPTADA = "aceptado";
    public static final String RESPUESTA_RECHAZADO = "rechazado";
    public static final String RESPUESTA_NO_ACEPTADO_NO_RECHAZADO = "pendiente";
    public static final String RESPUESTA_PENDIENTE = "pendiente";
    public static final String NO_ENVIADA = "no_enviada";
    public static final String ENVIANDO = "enviando";

    public static final String FINALIZADA = "Finalizada";
    public static final Integer AUTHENTICATION_ID_TASK = 1000;
    public static final Integer CONSECUTIVO_CLAVE_ID_TASK = 1001;

    public static String getDetailLine(String line) {

        if (line!=null && line.length()>160) {
            line.substring(0,159);
        }
        return line;
    }

    public static boolean isNAEmail(String email) {
        if (email.equals(NA_EMAIL)) {
            return true;
        } else {
            return false;
        }
    }

    public static String createLogoUrl(String logoPath) {

        return BILL_BASE_IMAGES_PATH + logoPath;
    }

    public static  String transformCedula(String cedula, String tipo) {
        if (cedula!=null) {
            cedula = cedula.replace(" ","");
            cedula = cedula.replace("-","");
            if (tipo!=null && tipo.equals("02") && cedula.length()>9 && (cedula.startsWith("30101") || cedula.startsWith("3101"))) {
                if (cedula.startsWith("30101")) {
                    cedula = "3" + cedula.substring(2);

                }

                if (cedula.startsWith("3101") && cedula.length()>9) {
                    cedula = cedula.substring(0,10);
                }
            }
        }
        return cedula;
    }

    public static boolean aplicaImpuestoVentas(Double montoImpuestos) {

        if (montoImpuestos!=null && montoImpuestos>0d) {
            return true;
        }
        return false;
    }

   public static final String COLON = "Colon";
   public static final String DOLAR = "Dolar";

    public static boolean isDataClientOk(Client c, ResultFe r) {
        if (c==null) {
            r.setResultStr("No existe un cliente asociado con la factura");
            r.setResult(-10);
            return false;
        } else if (c.getEnterpriceId()==null || c.getEnterpriceId().equals("")) {
            r.setResultStr("No existe una cédula asociada con la información del cliente");
            r.setResult(-10);
            return false;
        }
        return true;
    }

    public static String getMonedaNombre(String codigoMoneda) {
        if (codigoMoneda==null) {
            return COLON;
        }
        if (codigoMoneda.equals("CRC")) {
            return COLON;
        } else if (codigoMoneda.equals("USD")) {
            return DOLAR;

        }
        return COLON;
    }

    public static void generarClave(Invoice invoice, String tipoDocumento, String cedula) {
        String fechaStr = crearFechaClave();
        invoice.setConsecutivo(generarConsecutivoFactura(invoice.getConsecutivo(),tipoDocumento));
        String cedulaStr = generarCedula(cedula);

        String nStr = generarSeguridad();

        invoice.setClave(PAIS_CODE + fechaStr + cedulaStr + invoice.getConsecutivo() + SITUATION_FE + nStr);

    }

    public static void generarClave(NotaCreditoElectronica fe) {
        String fechaStr = crearFechaClave();
        fe.setNumeroConsecutivo(generarConsecutivoFactura(fe.getNumeroConsecutivo(), fe.getTipoDocumento()));
        String cedulaStr = generarCedula(fe.getEmisor().getIdentificacion().getNumero());

        String nStr = generarSeguridad();

        fe.setClave(PAIS_CODE + fechaStr + cedulaStr + fe.getNumeroConsecutivo() + SITUATION_FE + nStr);

    }

    public static void generarClave(FacturaElectronica fe) {
        String fechaStr = crearFechaClave();
        fe.setNumeroConsecutivo(generarConsecutivoFactura(fe.getNumeroConsecutivo(), fe.getTipoDocumento()));
        String cedulaStr = generarCedula(fe.getEmisor().getIdentificacion().getNumero());

        String nStr = generarSeguridad();

        fe.setClave(PAIS_CODE + fechaStr + cedulaStr + fe.getNumeroConsecutivo() + SITUATION_FE + nStr);

    }

    public static void generarClave(FacturaElectronicaExportacion fe) {
        String fechaStr = crearFechaClave();
        fe.setNumeroConsecutivo(generarConsecutivoFactura(fe.getNumeroConsecutivo(), fe.getTipoDocumento()));
        String cedulaStr = generarCedula(fe.getEmisor().getIdentificacion().getNumero());

        String nStr = generarSeguridad();

        fe.setClave(PAIS_CODE + fechaStr + cedulaStr + fe.getNumeroConsecutivo() + SITUATION_FE + nStr);

    }

    private static String generarConsecutivoFactura(String consecutivoFactura, String tipoDocumento) {
        String consecutivoStr =  StringUtils.leftPad(consecutivoFactura, 10, "0");
        return  "001" + "00001" + tipoDocumento + consecutivoStr;
    }

    private static String crearFechaClave() {
        Date fecha = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(fecha);
        Integer month = c.get(Calendar.MONTH) + 1;
        Integer ano = getAno();
        Integer day = c.get(Calendar.DAY_OF_MONTH);
        String fechaStr = (day < 10 ? "0" + day.toString(): day.toString()) + (month < 10 ? "0" + month.toString(): month.toString()) + ano.toString();
        return fechaStr;
    }

    private static int getAno() {
        DateFormat df = new SimpleDateFormat("yy"); // Just the year, with 2 digits
        String formattedDate = df.format(Calendar.getInstance().getTime());
        return Integer.parseInt(formattedDate);
    }

    private static String generarCedula(String cedula) {
        cedula = cedula.replaceAll("-","");

        StringBuilder stringBuilder = new StringBuilder(cedula);
        while (stringBuilder.length() < 12) {
            stringBuilder.insert(0, Integer.toString(0));
        }
        return stringBuilder.toString();
    }

    private static String generarSeguridad() {
        Random rnd = new Random();
        Integer n = 10000000 + rnd.nextInt(90000000);
        return StringUtils.leftPad(n.toString(), 8, "0");
    }

    public static boolean isNationalCurrency(String currency) {

        return BillHelper.NATIONAL_CURRENCY.equals(currency);
    }

    public static Double convertToDefaultCurrency(ExchangeRate exchangeRate, Double total) {
        Double amount = 0d;
        if (exchangeRate!=null) {
            amount =  total / exchangeRate.getBuyRate();
        }
        return amount;
    }
}

