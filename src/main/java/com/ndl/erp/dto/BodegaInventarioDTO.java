package com.ndl.erp.dto;

import com.ndl.erp.domain.*;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

public class BodegaInventarioDTO {

    private Page<InventarioItem> items;


    private List<Bodega> bodegas;


    private Integer total = 0;

    private Integer pagesTotal = 0;


    public Page<InventarioItem> getItems() {
        return items;
    }

    public void setItems(Page<InventarioItem> items) {
        this.items = items;
    }

    public List<Bodega> getBodegas() {
        return bodegas;
    }

    public void setBodegas(List<Bodega> bodegas) {
        this.bodegas = bodegas;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getPagesTotal() {
        return pagesTotal;
    }

    public void setPagesTotal(Integer pagesTotal) {
        this.pagesTotal = pagesTotal;
    }
}
