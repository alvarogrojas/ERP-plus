package com.ndl.erp.constants;

public final class BodegaConstants{

    public static final String BODEGA_DESECHO = "BOD_DESECHO";

    //Constantes para bandera de bodega facturable
    public static final String BODEGA_FACTURABLE = "SI";
    public static final String BODEGA_NO_FACTURABLE = "NO";


    //Estrategias de inventario en la bodega
    public static final String MANEJO_BODEGA_PEPS = "PEPS";
    public static final String MANEJO_BODEGA_UEPS = "UEPS";
    public static final String MANEJO_BODEGA_SIMPLE = "SIMPLE";

    //Estrategias de precios en la bodega
    public static final String MANEJO_BODEGA_PRECIO_MAYOR = "Precio Mayor";
    public static final String MANEJO_BODEGA_PRECIO_PONDERADO = "Precio Ponderado";
    public static final String MANEJO_BODEGA_PRECIO_ACTUALIZA = "Actualiza Precio";



    public static final String DETALLE_RECIBO_SERVICIO = "Servicio";
    public static final String DETALLE_RECIBO_PRODUCTO = "Producto";

    public static final String TIPO_INIT_INVENTARIO_DETALLE_VARIABLE = "VARIABLE";
    public static final String TIPO_INIT_INVENTARIO_DETALLE_VARIATION = "VARIATION";
    public static final String TIPO_INIT_INVENTARIO_DETALLE_SIMPLE = "SIMPLE";



    public static final String DETALLE_REEMBOLSO_SERVICIO = "Servicio";
    public static final String DETALLE_REEMBOLSO_PRODUCTO = "Producto";

    public static final String ESTADO_BODEGA_ACTIVA = "Activa";
    public static final String ESTADO_BODEGA_INACTIVA = "Inactiva";

    public static final String TIPO_ENTRADA_BODEGA_CXP = "CXP";
    public static final String TIPO_ENTRADA_BODEGA_REM = "REM";
    public static final String TIPO_ENTRADA_DEVOLUCION_NOTA_CREDITO = "DEV-NC";
    public static final String TIPO_ENTRADA_DEVOLUCION_REQUISICION = "DEV-RQ";



    public static final String TIPO_SALIDA_BODEGA_REQUISICION = "REQUI";
    public static final String TIPO_SALIDA_BODEGA_INVOICE = "INV";
    public static final String TIPO_SALIDA_BODEGA_BILL_PAY_NOTA_CREDITO = "NC-CXC";


    //tipos de documento
    public static final String TIPO_DOCUMENTO_COMPRA = "COMPRA";
    public static final String TIPO_DOCUMENTO_VENTA = "VENTA";
    public static final String TIPO_DOCUMENTO_DEVOLUCION = "DEVOLUCION";
    public static final String TIPO_DOCUMENTO_REEMBOLSO = "REEMBOLSO";
    public static final String TIPO_DOCUMENTO_REQUISICION = "REQUISICION";
    public static final String TIPO_DOCUMENTO_TRASLADO = "TRASLADO";
    public static final String TIPO_DOCUMENTO_INITIALIZER = "INICIALIZADOR";
    public static final String TIPO_DOCUMENTO_NC_PROVEEDOR = "NC-PROVEEDOR";


    private BodegaConstants() {
        throw new IllegalStateException("Cannot create instance of static util class");
    }
}
