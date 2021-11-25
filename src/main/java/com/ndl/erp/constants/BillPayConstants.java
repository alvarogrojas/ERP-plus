package com.ndl.erp.constants;

public final class BillPayConstants{

    public static final String BILL_PAY_ESTADO_EDICION = "Edicion";
    public static final String BILL_PAY_ESTADO_PAGADA = "Pagada";
    public static final String BILL_PAY_ESTADO_PENDIENTE = "Pendiente";
    public static final String BILL_PAY_ESTADO_VENCIDA = "Vencida";

    //Parametros de centros de costo y cliente default del billPay
    public static final String BILL_PAY_DEFAULT_COST_CENTER = "DEFAULT_COSTS_CENTER_BILLING";
    public static final String BILL_PAY_DEFAULT_CLIENT = "DEFAULT_CUSTOMER_BILLING";






    private BillPayConstants() {
        throw new IllegalStateException("Cannot create instance of static util class");
    }
}