package com.ndl.erp.dto;

import java.util.ArrayList;
import java.util.List;

public class SalesDetailInfoDTO {
    public List<SaleDetailInfoDTO> salesDetailInfo = new ArrayList<>();

    private String currency;

    private Double totalAmount;

    private Integer pagesTotal = 0;
    private Integer total = 0;


    public List<SaleDetailInfoDTO> getSalesDetailInfo() {
        return salesDetailInfo;
    }

    public void setSalesDetailInfo(List<SaleDetailInfoDTO> salesDetailInfo) {
        this.salesDetailInfo = salesDetailInfo;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Integer getPagesTotal() {
        return pagesTotal;
    }

    public void setPagesTotal(Integer pagesTotal) {
        this.pagesTotal = pagesTotal;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
