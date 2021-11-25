package com.ndl.erp.dto;

import com.ndl.erp.domain.*;

import java.util.List;
//import org.springframework.data.domain.Page;


public class SubmitCabys {

    private List<ServiceCabys> services;
    private List<ProductCabys> products;
    private List<InventarioItem> items;

    public List<ServiceCabys> getServices() {
        return services;
    }

    public void setServices(List<ServiceCabys> services) {
        this.services = services;
    }

    public List<ProductCabys> getProducts() {
        return products;
    }

    public void setProducts(List<ProductCabys> products) {
        this.products = products;
    }

    public List<InventarioItem> getItems() {
        return items;
    }

    public void setItems(List<InventarioItem> items) {
        this.items = items;
    }
}

