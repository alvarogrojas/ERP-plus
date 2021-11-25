package com.ndl.erp.dto;

import java.io.Serializable;

public class TotalDTO implements Serializable {
    private static final long serialVersionUID = 1777000000000000017L;

    private String currencySimbol;
    private Integer currencyId;
    private String currencyName;
    private Double total;
    private Double total2;


    public TotalDTO() {

    }

//    public TotalDTO(Object[] arr) {
    public TotalDTO(Double total, String currencyName, String currencySimbol) {
        this.total = total;
        this.currencyName = currencyName;
        this.currencySimbol = currencySimbol;
//        if(arr!=null && arr.length > 3){
//            this.total = (Double) arr[0];
//            this.currencyId = (Integer) arr[1];
//            this.currencyName =(String) arr[2];
//            this.currencySimbol =(String) arr[3];
//            if(arr.length > 4)
//               this.total2 =(Double) arr[4];
//        }
    }

    public String getCurrencySimbol() {
        return currencySimbol;
    }

    public void setCurrencySimbol(String currencySimbol) {
        this.currencySimbol = currencySimbol;
    }

    public Integer getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(Integer currencyId) {
        this.currencyId = currencyId;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Double getTotal2() {
        return total2;
    }

    public void setTotal2(Double total2) {
        this.total2 = total2;
    }
}
