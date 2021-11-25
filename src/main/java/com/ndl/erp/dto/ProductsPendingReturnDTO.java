package com.ndl.erp.dto;

import java.util.List;

public class ProductsPendingReturnDTO {

 private List<ProductsPendingReturnTotalDTO> productsPendingReturn;

    public List<ProductsPendingReturnTotalDTO> getProductsPendingReturn() {
        return productsPendingReturn;
    }

    public void setProductsPendingReturn(List<ProductsPendingReturnTotalDTO> productsPendingReturn) {
        this.productsPendingReturn = productsPendingReturn;
    }
}

