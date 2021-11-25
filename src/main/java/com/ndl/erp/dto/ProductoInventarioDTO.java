package com.ndl.erp.dto;

import java.util.ArrayList;
import java.util.List;

public class ProductoInventarioDTO {
    List<ProductoInventarioItemDTO> items;

    private List<String> attributesColumns;
    private String uriFileName;
    private Integer attributesMax = 0;

    public ProductoInventarioDTO() {
        this.items = new ArrayList<ProductoInventarioItemDTO>();
    }

        public List<ProductoInventarioItemDTO> getItems() {
        return items;
    }

    public void setItems(List<ProductoInventarioItemDTO> items) {
        this.items = items;
    }

    public List<String> getAttributesColumns() {
        if (attributesColumns==null) {
            this.attributesColumns = new ArrayList<>();
        }
        return attributesColumns;
    }

    public String getUriFileName() {
        return uriFileName;
    }

    public void setUriFileName(String uriFileName) {
        this.uriFileName = uriFileName;
    }

    public void setAttributesColumns(List<String> attributesColumns) {
        this.attributesColumns = attributesColumns;
    }

    public Integer getAttributesMax() {
        return attributesMax;
    }

    public void setAttributesMax(Integer attributesMax) {
        this.attributesMax = attributesMax;
    }
}
