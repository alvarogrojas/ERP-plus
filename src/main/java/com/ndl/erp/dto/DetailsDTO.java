package com.ndl.erp.dto;

public class DetailsDTO {
    private String type;
    private Integer id;
    private String productName;
    private String bodega;
    private String detalle;
    private double cantidad;
    private double precio;
    private double total;

    private String costCenter;
    private String costCenterLabel;
    public DetailsDTO(String type, Integer id, String productName, String bodega, String detalle, double cantidad, double precio, double total, String costCenter) {
        this.type = type;
        this.id = id;
        this.productName = productName;
        this.bodega = bodega;
        this.detalle = detalle;
        this.cantidad = cantidad;
        this.precio = precio;
        this.total = total;
        this.costCenter = costCenter;
        this.costCenterLabel = costCenter;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCostCenter() {
        return costCenter;
    }

    public void setCostCenter(String costCenter) {
        this.costCenter = costCenter;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBodega() {
        return bodega;
    }

    public void setBodega(String bodega) {
        this.bodega = bodega;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }


}
