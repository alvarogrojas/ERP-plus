package com.ndl.erp.constants;

    public final class DescuentosConstants {

        public static final String DESCUENTO_ACTIVA = "Activa";

        public static final String DESDCUENTO_INACTIVA = "Inactiva";

        public static final String DESCUENTO_TIPO_GLOBAL = "Global";
        public static final String DESCUENTO_TIPO_CATEGORIA = "Categoria";
        public static final String DESCUENTO_TIPO_CLIENTE = "Cliente";
        private DescuentosConstants() {
            throw new IllegalStateException("Cannot create instance of static util class");
        }
    }

