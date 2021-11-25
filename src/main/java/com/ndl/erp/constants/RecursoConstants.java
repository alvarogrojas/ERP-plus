package com.ndl.erp.constants;

public final class RecursoConstants {

    //Tipos de referencia
    public static final String RECURSO_TIPO_REFERENCIA_PRODUCTO = "Producto";

    //Tipos de recursos
    public static final String RECURSO_TIPO_FOTOGRAFIA = "Fotografia";
    public static final String RECURSO_TIPO_DOCUMENTO = "Documento";
    public static final String RECURSO_TIPO_HIPERVINCULO = "Hipervinculo";
    public static final String RECURSO_TIPO_VIDEO = "Video";
    public static final String RECURSO_TIPO_AUDIO = "Audio";



    private RecursoConstants() {
        throw new IllegalStateException("Cannot create instance of static util class");
    }
    }