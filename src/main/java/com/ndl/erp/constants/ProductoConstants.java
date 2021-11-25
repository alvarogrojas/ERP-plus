package com.ndl.erp.constants;

public final class ProductoConstants{


    public static final String PRODUCTO_ESTADO_ACTIVO = "Activo";
    public static final String PRODUCTO_ESTADO_INACTIVO = "Inactivo";

    public static final String PRODUCTO_TIPO_SIMPLE = "Simple";

    public static final String PRODUCTO_TIPO_VARIANT = "Variation";

    //Estrategias de manejo inventario en el producto
    public static final String MANEJO_BODEGA_PRODUCTO_PEPS = "PEPS";
    public static final String MANEJO_BODEGA_PRODUCTO_UEPS = "UEPS";
    public static final String MANEJO_BODEGA_PRODUCTO_SIMPLE = "SIMPLE";

    //Manejo de impuestos
    public static final Integer PRODUCTO_GRAVADO = 1;
    public static final Integer PRODUCTO_EXENTO = 0;

    private ProductoConstants() {
        throw new IllegalStateException("Cannot create instance of static util class");
    }
}
