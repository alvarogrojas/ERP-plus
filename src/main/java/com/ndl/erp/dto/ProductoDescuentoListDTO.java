package com.ndl.erp.dto;


import com.ndl.erp.domain.ProductoDescuento;
import org.springframework.data.domain.Page;
import java.util.List;

public class ProductoDescuentoListDTO {

    private List<ProductoDescuento> productoDescuentoList;

    private Page<ProductoDescuento> productoDescuentoListPage;

    private Integer total;

    private Integer pagesTotal;

    public List<ProductoDescuento> getProductoDescuentoList() {
        return productoDescuentoList;
    }

    public void setProductoDescuentoList(List<ProductoDescuento> productoDescuentoList) {
        this.productoDescuentoList = productoDescuentoList;
    }

    public Page<ProductoDescuento> getProductoDescuentoListPage() {
        return productoDescuentoListPage;
    }

    public void setProductoDescuentoListPage(Page<ProductoDescuento> productoDescuentoListPage) {
        this.productoDescuentoListPage = productoDescuentoListPage;
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