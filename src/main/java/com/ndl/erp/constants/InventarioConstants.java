package com.ndl.erp.constants;

public final class InventarioConstants{

    public static final String FILTRO_INVENTARIO_TODOS = "Incluir Agotados";
    public static final String FILTRO_INVENTARIO_SOLO_EXISTENCIAS = "Solo Existencias";

    private InventarioConstants() {
        throw new IllegalStateException("Cannot create instance of static util class");
    }
}
