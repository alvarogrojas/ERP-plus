package com.ndl.erp.constants;

public final class TransaccionPendienteConstants {



    //Estado de la transacció
    public static final String TRANSACCION_PENDIENTE_ESTADO_ESPERA = "ESPERA";
    public static final String TRANSACCION_PENDIENTE_ESTADO_ENVIANDO = "ENVIADO";
    public static final String TRANSACCION_PENDIENTE_ESTADO_ACEPTADA = "ACEPTADA";
    public static final String TRANSACCION_PENDIENTE_ESTADO_FALLO = "FALLO";
    //Tipo de Envio
    public static final String TRANSACCION_PENDIENTE_TTPO_ENVIO_INMEDIATO = "INMEDIATO";
    public static final String TRANSACCION_PENDIENTE_TTPO_ENVIO_ASINCRONO = "ASINCRONO";


    private TransaccionPendienteConstants(){
        throw new IllegalStateException("Cannot create instance of static util class");
    }
}
