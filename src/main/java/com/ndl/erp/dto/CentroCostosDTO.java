package com.ndl.erp.dto;

import com.ndl.erp.domain.CostCenter;
import org.springframework.data.domain.Page;

import java.util.List;
//import org.springframework.data.domain.Page;


public class CentroCostosDTO {

//    private Page<CostCenter> centroCostos;
    private List<CostCenter> centroCostos;

    private Page<CostCenter> costCentersPage;


    private Integer total;

    private Integer pagesTotal;



    //
    public List<CostCenter> getCentroCostos() {
        return centroCostos;
    }
//
    public void setCentroCostos(List<CostCenter> c) {
        this.centroCostos = c;
    }

    public Page<CostCenter> getCostCentersPage() {
        return costCentersPage;
    }

    public void setCostCentersPage(Page<CostCenter> costCentersPage) {
        this.costCentersPage = costCentersPage;
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
