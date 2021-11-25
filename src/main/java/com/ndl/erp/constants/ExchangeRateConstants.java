package com.ndl.erp.constants;


public final class ExchangeRateConstants{

    public static final String EXCHANGE_RATE_ESTADO_ACTIVO = "Activo";
    public static final String EXCHANGE_RATE_ESTADO_INACTIVO = "Inactivo";

    private ExchangeRateConstants() {
        throw new IllegalStateException("Cannot create instance of static util class");
    }
}