package com.ndl.erp.dto;

import com.ndl.erp.domain.Collaborator;
import com.ndl.erp.domain.CostCenter;
import com.ndl.erp.domain.Currency;
import com.ndl.erp.domain.LaborCost;

import java.util.List;


public class LaborCostDetailDTO {


    private List<HmHdDTO> details;

    private Double totalMonto = 0d, hs = 0d, hm = 0d , hd = 0d, totalHsMto = 0d ,
            totalHdMto = 0d,totalHmMto = 0d;

    private String currencySymbol;


    public List<HmHdDTO> getDetails() {
        return details;
    }

    public void setDetails(List<HmHdDTO> details) {

        for (HmHdDTO current: details) {
            current.calc();
            totalMonto = totalMonto + current.getGasto();
            hs = hs + current.getHs();
            hm = hm +  current.getHm();
            hd = hd +  current.getHd();
            totalHsMto = totalHsMto +  current.getGastoHs();
            totalHdMto = totalHdMto +  current.getGastoHd();
            totalHmMto = totalHmMto +  current.getGastoHm();
        }
        if (details!=null && details.size()>0) {
            currencySymbol = details.get(0).getCurSimbol();
        }
        this.details = details;
    }

    public Double getTotalMonto() {
        return totalMonto;
    }

    public void setTotalMonto(Double totalMonto) {
        this.totalMonto = totalMonto;
    }

    public Double getHs() {
        return hs;
    }

    public void setHs(Double hs) {
        this.hs = hs;
    }

    public Double getHm() {
        return hm;
    }

    public void setHm(Double hm) {
        this.hm = hm;
    }

    public Double getHd() {
        return hd;
    }

    public void setHd(Double hd) {
        this.hd = hd;
    }

    public Double getTotalHsMto() {
        return totalHsMto;
    }

    public void setTotalHsMto(Double totalHsMto) {
        this.totalHsMto = totalHsMto;
    }

    public Double getTotalHdMto() {
        return totalHdMto;
    }

    public void setTotalHdMto(Double totalHdMto) {
        this.totalHdMto = totalHdMto;
    }

    public Double getTotalHmMto() {
        return totalHmMto;
    }

    public void setTotalHmMto(Double totalHmMto) {
        this.totalHmMto = totalHmMto;
    }

    public String getCurrencySymbol() {
        return currencySymbol;
    }

    public void setCurrencySymbol(String currencySymbol) {
        this.currencySymbol = currencySymbol;
    }

    //     $.each(aiDisplay,function (i,val) {
//
//        totalMto = parseFloat(totalMto) + parseFloat(aaData[val].gasto);
//        hs = parseFloat(hs) + parseFloat(aaData[val].hs);
//        hm = parseFloat(hm) + parseFloat(aaData[val].hm);
//        hd = parseFloat(hd) + parseFloat(aaData[val].hd);
//        totalHSMto = parseFloat(totalHSMto) + parseFloat(aaData[val].gasto_hs);
//        totalHMMto = parseFloat(totalHMMto) + parseFloat(aaData[val].gasto_hm);
//        totalHDMto = parseFloat(totalHDMto) + parseFloat(aaData[val].gasto_hd);
//        curSimbol = aaData[val].curSimbol;
//    });
}
