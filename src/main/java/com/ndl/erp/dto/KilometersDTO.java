package com.ndl.erp.dto;

import com.ndl.erp.domain.*;
import org.springframework.data.domain.Page;

import java.util.List;

public class KilometersDTO {

    private Page<Kilometer> page;
    private Page<KilometerDetail> pageDetail;

    private List<Collaborator> collaboratorList;
    private List<CostCenter> costCenters;
    private List<Currency> currencies;
    private List<String> states;

    private Integer total;

    private Integer pagesTotal;

    private String currency;
    private Double totalAmount;
    private Double totalKilometers;

    public Page<Kilometer> getPage() {
        return page;
    }

    public void setPage(Page<Kilometer> page) {
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

    public List<Collaborator> getCollaboratorList() {
        return collaboratorList;
    }

    public void setCollaboratorList(List<Collaborator> collaboratorList) {
        this.collaboratorList = collaboratorList;
    }

    public List<CostCenter> getCostCenters() {
        return costCenters;
    }

    public void setCostCenters(List<CostCenter> costCenters) {
        this.costCenters = costCenters;
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

    public Page<KilometerDetail> getPageDetail() {
        return pageDetail;
    }

    public void setPageDetail(Page<KilometerDetail> pageDetail) {
        this.pageDetail = pageDetail;
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

    public Double getTotalKilometers() {
        return totalKilometers;
    }

    public void setTotalKilometers(Double totalKilometers) {
        this.totalKilometers = totalKilometers;
    }
}
