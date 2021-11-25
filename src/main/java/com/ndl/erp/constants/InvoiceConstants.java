package com.ndl.erp.constants;

public final class InvoiceConstants{

    //constantes para tipo de factura
    public static final String INVOICE_STATUS_EDICION = "Edicion";
    public static final String INVOICE_STATUS_PENDIENTE = "Pendiente";
    public static final String INVOICE_STATUS_VENCIDA = "Vencida";
    public static final String INVOICE_STATUS_PAGADA = "Pagada";
    public static final String INVOICE_STATUS_ANULADO = "Anulado";

    //Constantes de tipo de factura (Tiquete o factura electronica)
    public static final String INVOICE_TIPO_TRANSACCION_FE = "FE";
    public static final String INVOICE_TIPO_TRANSACCION_TIQUETE = "TIQUETE";


    //Constantes de tipo de detalle
    public static final String INVOICE_DETAIL_TIPO_SERVICIO = "Servicio";
    public static final String  INVOICE_DETAIL_TIPO_PRODUCTO = "Producto";


    //Parametros de centros de costo y cliente default del invoice
    public static final String INVOICE_DEFAULT_COST_CENTER = "DEFAULT_COSTS_CENTER_SELLING";
    public static final String INVOICE_DEFAULT_CLIENT = "DEFAULT_CUSTOMER_SELLING";

    //Parametro para regimen simplificado
    public static final String  INVOICE_REGIMEN_SIMPLIFICADO_FLAG =  "REGIMEN_SIMPLIFICADO_TIQUETE";

    //Parametros informacion de la empresa
    public static final String  INVOICE_PARAMETER_NOMBRE_EMPRESA =  "NAME";
    public static final String  INVOICE_PARAMETER_DATOS_EMPRESA =  "INGP_INF";
    public static final String  POS_SHORT_ADDRESS =  "POS_SHORT_ADDRESS";
    public static final String  POS_TIQUETE_BOTTOM =  "POS_TIQUETE_BOTTOM";
    public static final String  POS_TIQUETE_BOTTOM1 =  "POS_TIQUETE_BOTTOM_1";
    public static final String  POS_TIQUETE_BOTTOM2 =  "POS_TIQUETE_BOTTOM_2";
    public static final String  POS_TIQUETE_BOTTOM3 =  "POS_TIQUETE_BOTTOM_3";
    public static final String  INVOICE_PARAMETER_CEDULA_EMPRESA =  "CEDULA";
    public static final String  INVOICE_PARAMETER_PHONE_EMPRESA =  "PHONE";
    public static final String  INVOICE_PARAMETER_EMAIL_EMPRESA =  "EMAIL";
    public static final String  INVOICE_PARAMETER_ADDRESS_EMPRESA =  "ADDRESS";





    private InvoiceConstants() {
        throw new IllegalStateException("Cannot create instance of static util class");
    }
}