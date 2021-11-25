package com.ndl.erp.constants;

public final class CotizacionConstants{

    public static final String ENTREGA_INMEDIATA = "Inmediata";
    public static final String ENTREGA_2_3_SEMANAS = "2 a 3 Semanas";
    public static final String ENTREGA_4_5_SEMANAS = "4 a 5 Semanas";
    public static final String COTIZACION_ESTADO_EDICION = "Edicion";
    public static final String COTIZACION_ESTADO_ENVIADA = "Enviada";
    public static final String COTIZACION_ESTADO_NO_ADJUDICADA = "No Adjudicada";
    public static final String COTIZACION_ESTADO_VENCIDA   = "Vencida";
    public static final String COTIZACION_ESTADO_APROBADA   = "Aprobada";
    public static final String COTIZACION_TIPO_DESCUENTO_CLIENTE   = "Cliente";
    public static final String COTIZACION_TIPO_DESCUENTO_PRODUCTO   = "Producto";

      private CotizacionConstants() {
        throw new IllegalStateException("Cannot create instance of static util class");
    }
}
