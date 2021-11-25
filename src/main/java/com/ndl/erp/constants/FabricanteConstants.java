package com.ndl.erp.constants;


    public final class FabricanteConstants{

        public static final String FABRICANTE_ESTADO_ACTIVO = "Activo";
        public static final String FABRICANTE_ESTADO_INACTIVO = "Inactivo";

        private FabricanteConstants() {
            throw new IllegalStateException("Cannot create instance of static util class");
        }
    }