package com.ndl.erp.dto;

import com.ndl.erp.domain.*;

import java.util.List;
//import org.springframework.data.domain.Page;


public class CashFlowDTO {


    private List<Various> various;
    private List<FixedCost> fixedCosts;

    private List<Bank> banks;


    public List<Various> getVarious() {
        return various;
    }

    public void setVarious(List<Various> various) {
        this.various = various;
    }

    public List<FixedCost> getFixedCosts() {
        return fixedCosts;
    }

    public void setFixedCosts(List<FixedCost> fixedCosts) {
        this.fixedCosts = fixedCosts;
    }

    public List<Bank> getBanks() {
        return banks;
    }

    public void setBanks(List<Bank> banks) {
        this.banks = banks;
    }
}
