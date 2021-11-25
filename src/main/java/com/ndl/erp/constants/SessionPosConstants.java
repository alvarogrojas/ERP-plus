package com.ndl.erp.constants;

public final class SessionPosConstants {

    public static final Integer MAX_POS_OPEN_SESSIONS = 1;
    public static final String SESSION_POS_ESTADO_ABIERTO = "Abierta";
    public static final String SESSION_POS_ESTADO_CERRADO = "Cerrada";
    public static final String SESSION_POS_ESTADO_ESPERA = "Espera";



    private SessionPosConstants() {
        throw new IllegalStateException("Cannot create instance of static util class");
    }
}