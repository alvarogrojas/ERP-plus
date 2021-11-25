package com.ndl.erp.dto;

import com.ndl.erp.domain.InventarioItem;
import com.ndl.erp.domain.ProductCabys;
import com.ndl.erp.domain.ServiceCabys;

import java.util.List;
//import org.springframework.data.domain.Page;


public class InventarioItemsDTO {

    private List<ProductCabys> products;

    private List<InventarioItem> items;

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
