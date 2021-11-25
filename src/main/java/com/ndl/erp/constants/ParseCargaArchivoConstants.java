package com.ndl.erp.constants;


public final class ParseCargaArchivoConstants{


    //Constantes parser de archivo csv de articulos
    public static final String SEPARADOR_LISTA = ",";
    public static final String SEPARADOR_DE_SUBLISTA = ";";
    public static final String TIPO_LINEA_ARTICULO_VARIABLE = "VARIABLE";
    public static final String TIPO_LINEA_ARTICULO_VARIATION = "VARIATION";
    public static final String TIPO_LINEA_ARTICULO_SIMPLE = "SIMPLE";

    //Constantes para parametros de actualizar productos e inventario
    public static final String PARAMETER_IMPORT_ACTUALIZA_PRODUCTO = "IMP_UPD_PROD";
    public static final String PARAMETER_IMPORT_ACTUALIZA_INVENTARIO = "IMP_UPD_INV";


    //Constantes bodega y centro costo de importacion inventario
    public static final String PARAMETER_IMPORT_BODEGA = "IMP_BODEGA";
    public static final String PARAMETER_IMPORT_CENTRO_COSTO = "IMP_CENCOSTO";



    public static final String IMPORT_ACTUALIZAR_PRODUCTO = "1";
    public static final String IMPORT_NO_ACTUALIZAR_PRODUCTO = "0";

    public static final String IMPORT_ACUMULAR_INVENTARIO = "0";
    public static final String IMPORT_REEMPLAZAR_INVENTARIO = "1";


    private ParseCargaArchivoConstants() {
        throw new IllegalStateException("Cannot create instance of static util class");
    }
}
