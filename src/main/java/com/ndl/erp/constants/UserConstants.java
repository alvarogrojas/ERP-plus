package com.ndl.erp.constants;

public final class UserConstants{

    public static final String USUARIO_POWER_USUARIO = "POWER USUARIO";
    public static final String USUARIO_ADMINISTRADOR = "ADMINISTRADOR";
    public static final String USUARIO_CAJERO = "CAJERO";

    private UserConstants() {
        throw new IllegalStateException("Cannot create instance of static util class");
    }
}