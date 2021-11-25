package com.ndl.erp.constants;

public final class InvoiceNotaCreditoConstants {


    public static final String INVOICE_NOTA_CREDITO_STATUS_EMITIDA = "Emitida";
    public static final String INVOICE_NOTA_CREDITO_STATUS_ANULADA = "Anulada";
    public static final String INVOICE_NOTA_CREDITO_STATUS_EDICION = "Edicion";
    public static final String INVOICE_NOTA_CREDITO_STATUS_PENDIENTE = "Pendiente";



    //Estados hacienda
    public static final String INVOICE_NOTA_CREDITO_PENDIENTE_HACIENDA = "pendiente";
    public static final String INVOICE_NOTA_CREDITO_RECHAZADO_HACIENDA = "rechazado";
    public static final String INVOICE_NOTA_CREDITO_ACEPTADO_HACIENDA = "aceptado";
    public static final String INVOICE_NOTA_CREDITO_NO_ENVIADO_HACIENDA = "no_enviada";





    private InvoiceNotaCreditoConstants() {
            throw new IllegalStateException("Cannot create instance of static util class");
        }
    }
