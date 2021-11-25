package com.ndl.erp.constants;

public final class RequisicionBodegaConstants{


    public static final String REQUISICION_BODEGA_ESTADO_EDICION = "Edicion";
    public static final String REQUISICION_BODEGA_ESTADO_PENDIENTE = "Pendiente";
    public static final String REQUISICION_BODEGA_ESTADO_RECHAZADO = "Rechazado";
    public static final String REQUISICION_BODEGA_ESTADO_APROBADO = "Aprobado";
    public static final String REQUISICION_BODEGA_ESTADO_DESPACHADO = "Despachado";


    private RequisicionBodegaConstants() {
        throw new IllegalStateException("Cannot create instance of static util class");
    }
}