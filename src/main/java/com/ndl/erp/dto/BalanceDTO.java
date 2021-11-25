package com.ndl.erp.dto;

import com.ndl.erp.domain.*;
import com.ndl.erp.util.StringHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class BalanceDTO {

    private Map<Integer,BalanceCostCenterDTO> balance;

    private List<String> months;

    private Currency systemCurrency;

//    public Map<Integer, BalanceCostCenterDTO> getBalance() {
//        return balance;
//    }

    public List<BalanceCostCenterDTO> getBalance() {
        if(this.balance==null || this.balance.isEmpty())
            return  new ArrayList();
        return  new ArrayList(this.balance.values());
    }

    public void setBalance(Map<Integer, BalanceCostCenterDTO> balance) {
        this.balance = balance;
    }

    public void setMonths(List<String> months) {
        this.months = months;
    }

    public List<String> getMonths() {
        return months;
    }


    public Currency getSystemCurrency() {
        return systemCurrency;
    }

    public void setSystemCurrency(Currency systemCurrency) {
        this.systemCurrency = systemCurrency;
    }
}
