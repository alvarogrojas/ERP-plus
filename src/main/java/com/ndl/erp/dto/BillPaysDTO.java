package com.ndl.erp.dto;

import com.ndl.erp.domain.*;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

public class BillPaysDTO {

    private Page<BillPay> page;
//    private Page<BillPayDetail> pageDetail;

    private List<Provider> providers;
//    private List<CostCenter> costCenters;
    private List<Currency> currencies;
    private List<String> states;
    private List<String> types;

    private Integer total;

    private Integer pagesTotal;

    private String currency;
    private Double totalAmount;

    private ArrayList<TotalDTO> totals;
//    private Double totalKilometers;

    public Page<BillPay> getPage() {
        return page;
    }

    public void setPage(Page<BillPay> page) {
        this.page = page;

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

    public List<Provider> getProviders() {
        return providers;
    }

    public void setProviders(List<Provider> providers) {
        this.providers = providers;
    }


    public List<Currency> getCurrencies() {
        return currencies;
    }

    public void setCurrencies(List<Currency> currencies) {
        this.currencies = currencies;
    }

    public List<String> getStates() {
        return states;
    }

    public void setStates(List<String> states) {
        this.states = states;
    }

//    public Page<BillPayDetail> getPageDetail() {
//        return pageDetail;
//    }
//
//    public void setPageDetail(Page<BillPayDetail> pageDetail) {
//        this.pageDetail = pageDetail;
//    }

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

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }

    public ArrayList<TotalDTO> getTotals() {
        return totals;
    }

    public void setTotals(ArrayList<TotalDTO> totals) {
        this.totals = totals;
    }

    //    public Double getTotalKilometers() {
//        return totalKilometers;
//    }
//
//    public void setTotalKilometers(Double totalKilometers) {
//        this.totalKilometers = totalKilometers;
//    }
}
