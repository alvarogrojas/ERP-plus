package com.ndl.erp.constants;

public final class CostCenterConstants {

    public static final String ACTIVE = "ACTIVO";
    public static final String COLLABORATOR_ACTIVE = "Activo";

    public static final String QUOTE = "COTIZADO";

    public static final String BILL_PAY_SERVICE_LINE = "Servicio";
    public static final String BILL_PAY_PRODUCT_LINE = "Producto";
    public static final String BILL_PAY_TYPE_IN = "BILL_PAY_IN";
    public static final String INVENTARIO_BITCORA_UPDATE = "ACTUALIZACION";
    public static final String INVENTARIO_BITCORA_ADD = "INSERCION";
    public static final String INVENTARIO_BITCORA_OUT = "OUT";
    public static final String INVENTARIO_BITCORA_DELETE = "BORRADO";
    public static final String BILL_PENDING_STATUS = "Pendiente";


    public static final String PARAMETER_DEFAULT_COST_CENTER_SELLING = "DEFAULT_COSTS_CENTER_SELLING";
    public static final String PARAMETER_DEFAULT_COST_CENTER_TRASLADO = "DEFAULT_COSTS_CENTER_TRASLADO";
    private CostCenterConstants() {
        throw new IllegalStateException("Cannot create instance of static util class");
    }
}
