package com.ndl.erp.dto;

import java.util.Date;

public class BillPayProductTotalDTO {


        private String billNumber;
        private String providerName;
        private Integer id;
        private String type; //"CXP"  ||  "REM"
        private Date billDate;
        private Double total;

        public BillPayProductTotalDTO() {

        }


    public BillPayProductTotalDTO(String type, String billNumber, String providerName, String providerName2, Integer id, Date billDate, Double total) {

        if (providerName2 != null && !providerName2.equals("")) {
            this.providerName = providerName + " - " + providerName2;
        } else{
            this.providerName = providerName;
        }

        this.billNumber = billNumber;
        this.id = id;
        this.type = type;
        this.billDate = billDate;
        this.total = total;
    }



    public String getBillNumber() {
        return billNumber;
    }

    public void setBillNumber(String billNumber) {
        this.billNumber = billNumber;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getBillDate() {
        return billDate;
    }

    public void setBillDate(Date billDate) {
        this.billDate = billDate;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
}

