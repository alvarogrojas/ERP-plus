package com.ndl.erp.constants;

public final class MedioPagoConstants{

    public static final String MEDIO_PAGO_ESTADO_ACTIVO = "Activo";
    public static final String MEDIO_PAGO_ESTADO_INACTIVO = "Inactivo";

    public static final String MEDIO_PAGO_EFECTIVO = "Efectivo";
    public static final String MEDIO_PAGO_TARJETA = "Tarjeta";
    public static final String MEDIO_PAGO_SINPE = "SINPE";
    public static final String MEDIO_PAGO_TRANSFERENCIA = "Transferencia Bancaria";
    public static final String MEDIO_PAGO_OTRO = "Otro";





    private MedioPagoConstants() {
        throw new IllegalStateException("Cannot create instance of static util class");
    }
}
