package com.ndl.erp.util;

import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class StringHelper {

    public static final String MESES[]={"Enero","Febrero","Marzo","Abril","Mayo",
            "Junio","Julio","Agosto","Setiembre","Octubre",
            "Noviembre","Diciembre"};

    public static final String INTEGER = "INT";
    public static final String STR = "STR";
    public static final String DATE = "DATE";
    public static final String DOUBLE = "DOUBLE";

    public static final String CCSS = "CCSS";
    public static final String TAX1 = "TAX1";
    public static final String TAX2 = "TAX2";
    public static final String TAX3 = "TAX3";
    public static final Double HM = 1.5d;
    public static final Double HD = 2d;
    public static final String HORA = "HORA";
    private static final String PATTERN = "₡###,###.###";
    private static final String PATTERN2 = "₡###,###.00";
    private static final String PATTERN3 = "₡###,##0.00";

    public static final String EMP_CONFIANSA = "MENSUAL";
    public static final String HS = "HS";

    public static final String IMP_VENTA_DEFAULT = "IMP_VENTA_DEFAULT";
    public static final String IMP_VENTA = "IMP_VENTA";
    public static final String BP_NUMBER = "BP_NUMBER";
    public static final String DEVOLUTION = "DEVOLUCION";
    public static final String KMS = "KMS";
    public static final String REM = "REM";
    public static final String CXP = "CXP";
    public static final String CUR_DEF_PAY_ROLL = "CUR_PAY_ROLL";
    public static final Boolean NOT_INCLOSING = new Boolean(false);
    public static final String CUR_CLOSURE = "CUR_CLOSURE"; //Mondeda del cierre.
    public static final String NEW = "new";
    public static final String EDIT = "edit";
    public static final String DELETE = "delete";

    public static final String INVOICE_EDITION = "Edicion";
    public static final String INVOICE_PENDING = "Pendiente";
    public static final String INVOICE_EXPIRED = "Vencida";
    public static final String INVOICE_PAID = "Pagada";
    public static final String INVOICE_CANCELED = "Anulada";
    public static final String NO_ENVIADA = "no_enviada";

    private StringHelper() {
    }

    public static final String FORMAT_DATE = "dd-MM-yyyy";

    //public static final String HORA_SIMPLE = "hora_simple";
    public static final String HORA_MEDIA = "hora_media";
    public static final String HORA_DOBLE = "hora_doble";

    public static final String DEFAULT_SEPARATOR = "_";
    public static final String DEFAULT_REFUND_INIT_STATE = "ingresado";
    public static final String DEFAULT_COSTS_CENTER_TYPE_FOR_ARTICLES = "bodega";
    public static final String DEFAULT_COSTS_CENTER_TYPE_FOR_EMPLOYEE = "planilla";
    public static final String DEFAULT_CURRENCY_COUNTRY = "Colon";
    public static final String REJECTED_STATE = "rechazado";
    public static final String APPROVED_STATE = "aprobado";
    public static final String DEFAULT_CONCEPT_TYPE = "EL";
    public static final String DEFAULT_ACTIVE = "ACTIVO";
    public static final String DEFAULT_CLOSE = "CERRADO";
    public static final String DEFAULT_COTIZADO = "COTIZADO";
    public static final String MOCK_USER_NAME = "NO SELECTED";
    public static final String REGULAR_USER = "USUARIO REGULAR";
    public static final String DEFAULT_COSTS_CENTER_TYPE = "proyecto";
    public static final String RH_COSTS_CENTER_TYPE = "RH";

    public static final String MANO_OBRA = "obra";
    public static final String PRODUCT = "producto";
    public static final String KILOMETRAJE = "kilometraje";
    public static final String REEMBOLSO = "reembolso";
    public static final String MANO_OBRA_LABEL = "Mano de Obra";
    public static final String KILOMETRAJE_LABEL = "Kilometraje";
    public static final String REEMBOLSO_LABEL = "Reembolso";


    public static final String STATUS_DATA_ING = "Ingresado";
    public static final String STATUS_DATA_EDIT = "EDITABLE";
    public static final String STATUS_DATA_FROZEN = "CONGELADO";
    //public static final String STATUS_DATA_CLOSE = "Cerrado";
    //public static final String STATUS_DATA_APRO = "Aprobado";

    public static final String STATUS_DATA_DEL = "Borrado";
    public static final String STATUS_DATA_ACT = "Activo";
    public static final String STATUS_DATA_INA = "Inactivo";
    public static final String STATUS_DATA_PEN = "Pendiente";
    public static final String STATUS_DATA_VEN = "Vencida";
    public static final String STATUS_DATA_PAG = "Pagada";
    public static final String STATUS_DATA_ANU = "Anulada";
    public static final String STATUS_DATA_EDI = "Edicion";
    //public static final String STATUS_DATA_CAN = "Cancelado";

    //Constantes para
    public static final String STATUS_DATA_GEN = "Generada";
    //public static final String STATUS_DATA_REC = "Rechazada";
    public static final String STATUS_DATA_APROB = "Aprobada";

    public static final String STATUS_DATA_RO = "RO"; //Read Only
    public static final String STATUS_DATA_APLICADO = "APLICADO";


    public static final List<String> DEFAULT_COSTS_CENTER_STATES = new ArrayList<String>() {
        {
            add(DEFAULT_ACTIVE);
            add(DEFAULT_COTIZADO);
        }
    };

    public static final List<String> DEFAULT_COSTS_CENTER_ACTIVE = new ArrayList<String>(){
        {
            add(DEFAULT_ACTIVE);
        }
    };

    public static final List<String> DEFAULT_REPORT_COSTS_CENTER_STATES = new ArrayList<String>(){
        {
            add(DEFAULT_ACTIVE);
            add(DEFAULT_COTIZADO);
            add(DEFAULT_CLOSE);
        }
    };

    public static final List<String> DEFAULT_COSTS_CENTER_TYPES = new ArrayList<String>(){
        {
            add(DEFAULT_COSTS_CENTER_TYPE);
            add(RH_COSTS_CENTER_TYPE);
        }
    };

    public static final List<String> DEFAULT_COSTS_CENTER_TYPES_FOR_ARTICLES = new ArrayList<String>() {
        {
            add(DEFAULT_COSTS_CENTER_TYPE_FOR_ARTICLES);
        }
    };

    public static final List<String> DEFAULT_COSTS_CENTER_TYPES_EMPLOYEE = new ArrayList<String>() {
        {
            add(DEFAULT_COSTS_CENTER_TYPE_FOR_EMPLOYEE);
        }
    };

    public static final List<String> ALL_REFUND_STATES = new ArrayList<String>() {
        {
            add(StringHelper.DEFAULT_REFUND_INIT_STATE);
            add(StringHelper.REJECTED_STATE);
            add(StringHelper.APPROVED_STATE);
        }
    };

    public static final String DEFAULT_CLIENT_NAME = "Ingpro-Tec";


    public static String[] stringDataToArray(String data, String separator) {
        if (data == null || separator == null) {
            return new String[]{};
        }
        return data.split(separator);
    }

    public static List<String> convertLineToList(String list) {
        List<String> values = new ArrayList<String>();
        if (list == null || list.length() == 0) {
            return values;
        }
        String[] data = list.split(",");
        return Arrays.asList(data);
    }

    public static String convertToPhoneFormat(String text) {
        String t = text.trim();
        t = t.replace("_", "");
        if (t.endsWith("-") || t.startsWith("-")) {
            t = t.replace("-", "");
        }
        t = t.trim();
        return t;
    }


    private static boolean isBlank(String value) {
        return (value == null || value.equals("") || value.equals("null") || value.trim().equals(""));
    }


    private static boolean isNumber(String value) {
        boolean ret = false;
        if (!isBlank(value)) {
            ret = value.matches("^[0-9]+$");
        }
        return ret;
    }

    public static Integer getValInt(String val) {
        Integer ret = null;
        if (isNumber(val)) {
            ret = Integer.parseInt(val);
        }
        return ret;
    }

    public static Double getValDouble(String val) {
        Double ret = null;
        try {
            if (val != null)
                ret = Double.parseDouble(val);

        } catch (Exception e) {
            ret = null;
        }
        return ret;
    }

    public static Double getValDoubleWihoutDecimalSeparator(String val) {
        Double ret = null;
        try {
            if (val != null)
                ret = Double.valueOf(val);

        } catch (Exception e) {
            ret = null;
        }
        return ret;
    }


    public static String getNumber2Str(Object val) {
        String ret = null;
        try {
            if (val != null)
                ret = val.toString();
        } catch (Exception e) {
            ret = null;
        }
        return ret;
    }

    public static String get2DecimalToStr(Double val) {
        DecimalFormat df = new DecimalFormat("#.##");
        String ret = null;
        try {
            if (val != null)
                ret = df.format(val);
//            ret = val.toString();
        } catch (Exception e) {
            ret = null;
        }
        return ret;
    }


    public static Date getString2Date(String origen) {
        Date ret = null;
        try {
            if (origen != null && !origen.isEmpty()) {
                DateFormat formatter = new SimpleDateFormat(FORMAT_DATE);
                java.util.Date fdate = formatter.parse(origen);
                ret = new Date(fdate.getTime());
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return ret;
    }

    public static Date plusDays(Date start, Integer days) {
        Date ret = null;
        Calendar cal = Calendar.getInstance();
        cal.setTime(start);
        cal.add(Calendar.DATE, days);
        ret = new Date(cal.getTime().getTime());
        return ret;
    }

    public static String getDate2String(Date origen) {
        String ret = null;
        try {
            if (origen != null) {
                DateFormat formatter = new SimpleDateFormat(FORMAT_DATE);
                ret = formatter.format(origen);
            }
        } catch (Exception e) {
            e.printStackTrace();
            ret = null;
        }
        return ret;
    }

    public static String getDate2String(java.util.Date origen) {
        String ret = null;
        try {
            if (origen != null) {
                DateFormat formatter = new SimpleDateFormat(FORMAT_DATE);
                ret = formatter.format(origen);
            }
        } catch (Exception e) {
            e.printStackTrace();
            ret = null;
        }
        return ret;
    }

    public static String getDate2String(Date origen, String format) {
        String ret = null;
        try {
            if (origen != null) {
                DateFormat formatter = new SimpleDateFormat(format != null ? format : FORMAT_DATE);
                ret = formatter.format(origen);
            }
        } catch (Exception e) {
            e.printStackTrace();
            ret = null;
        }
        return ret;
    }


    public static String formatNumberCurrency(double value) {

        try {
            DecimalFormat myFormatter = new DecimalFormat(PATTERN);
            return myFormatter.format(value);
        } catch (Exception e) {
            return null;
        }
    }
    public static String formatNumberCurrency(double value,boolean forceDecimal) {
        try {
            DecimalFormat myFormatter = new DecimalFormat(PATTERN2);
            return myFormatter.format(value);
        } catch (Exception e) {
            return null;
        }
    }

    public static String getTraceTime() {
        java.util.Date time = new Date(System.currentTimeMillis());
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyy HH:mm:ss SSS");
        return formatter.format(time);
    }


    public static Object getValStr(String[] paramsQuery, int index, String type) {
        String temp = null;
        Object ret= null;
        try {
            temp = paramsQuery != null && paramsQuery.length > index && paramsQuery[index] != null && paramsQuery[index].trim().length() > 0 ? paramsQuery[index] : null;
            if (type.equals(INTEGER)) {
                ret = getValInt(temp);
            } else if (type.equals(DATE)) {
                ret = getString2Date(temp);
            } else if (type.equals(DOUBLE)) {
                ret = getValDouble(temp);
            } else {
                ret = temp;
            }
        }catch (Exception e){
            ret=null;
        }
        return ret;
    }


    public static Integer yearDif(Integer year){
        Integer ret=0;
        java.util.Date date = new java.util.Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        ret = cal.get(Calendar.YEAR) - year;
        return ret;
    }

    public static String getOrderField(Integer index,String[] fields){
        if(index!=null && fields!=null && fields.length >= index-1)
            return fields[index];
        return null;
    }

    public static final void printLog(boolean trace,String msj){
        if(trace) {
            System.out.format(msj + "%tT", Calendar.getInstance());
        }
    }


    public static final String getKeyMonths(int month, int year){
        return MESES[month-1] + " - "+ year;
    }


    public static final List<Date> getDates(String dates){
        List<Date> ret = new ArrayList(0);

        String datesArr[]=null;
        if(dates!=null && !dates.trim().equals("")){
            datesArr = dates.split("/");
            if(datesArr.length > 1){
                Date date1 =StringHelper.getString2Date(datesArr[0]);
                if (date1!=null)
                    ret.add(0,date1);
                Date date2 =StringHelper.getString2Date(datesArr[1]);
                if (date2!=null)
                    ret.add(1,date2);
            }else{
                Date date1= StringHelper.getString2Date(datesArr[0]);
                if (date1!=null)
                    ret.add(0,date1);
            }
        }
        return ret;
    }


    public static final String toUTF8(String source){
        String ret = null;
        try {
            if(source!=null){
                byte[] utf8Bytes = source.getBytes("UTF8");
                ret = new String(utf8Bytes, "UTF8");

            }
        } catch (UnsupportedEncodingException e) {
            ret = "Caracters no soportados por el sistema";
            e.printStackTrace();
        }
        return ret;
    }

    public static Integer getWeekOfDate(Date date) {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(date);
        return gc.get(Calendar.WEEK_OF_YEAR);
    }

    //Periodicidad
    public static class PERIODICITY {
        public static final String WEEKLY = "Semanal";
        public static final String BIWEEKLY = "Quincenal";
        public static final String MONTHLY = "Mensual";
        public static final String QUARTERLY = "Trimestral";
        public static final String BIANNUAL = "Semestral";
        public static final String ANNUAL = "Anual";

    }

    //Projecet Manater
    public static class PM_STATUS{
        public static final String INGRESADO = "Ingresado";
        public static final String ACTIVO = "Activo";
        public static final String INACTIVO = "Inactivo";
        public static final String APLICADO = "Aplicado";
        public static final String BORRADO = "Borrado";

    }

    //Ordenes de compra de clientes
    public static class POC_STATUS{
        public static final String INGRESADO = "Ingresado";
        public static final String EJECUCION = "Ejecucion";
        public static final String CONCLUIDA = "Concluida";
        public static final String ANULADO = "Anulado";

    }

    //Cuentas por pagar
    public static class BILL_COLLECT_STATUS{
        public static final String PENDIENTE = "Pendiente";
        public static final String PAGADA = "Pagada";
        public static final String VENCIDA = "Vencida";
        public static final String ANULADA = "Anulada";

    }
    //Facturas
    public static class INVOICE_STATUS{
        public static final String Edicion = "Edicion";
        public static final String PENDIENTE = "Pendiente";
        public static final String PAGADA = "Pagada";
        public static final String VENCIDA = "Vencida";
        public static final String ANULADA = "Anulada";
    }

    public static  class FORMAT_DATES{
        public static final String[] MONTHS = {"Ene", "Feb","Mar","Abr","May","Jun","Jul","Ago","Set","Oct","Nov","Dic"};
        public static final String getDateInitMonth(Date date){
            StringBuffer ret=new StringBuffer("");
            if(date!=null){
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                int month = cal.get(Calendar.MONTH);
                int year = cal.get(Calendar.YEAR);
                ret.append(day > 9 ? day :"0" + day).append("-");
                ret.append(MONTHS[month]).append("-");
                ret.append(year);
            }
            return ret.toString();
        }
    }

}
