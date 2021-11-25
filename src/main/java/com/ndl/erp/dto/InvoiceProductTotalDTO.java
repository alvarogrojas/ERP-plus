package com.ndl.erp.dto;

import java.util.Date;

public class InvoiceProductTotalDTO {



        private String invoiceNumber;
        private String type; //INV, REQUI
        private String clientName;
        private Integer invoiceId;
        private Date date;
        private Double total;


        public InvoiceProductTotalDTO () {

        }


    public InvoiceProductTotalDTO(Integer invoiceNumber, String type, String clientName, Integer invoiceId, Date date, Double total) {
        if (invoiceNumber != null) {
            this.invoiceNumber = invoiceNumber.toString();
        }
        this.type = type;
        this.clientName = clientName;
        this.invoiceId = invoiceId;
        this.date = date;
        this.total = total;
    }
    public InvoiceProductTotalDTO(String invoiceNumber, String type, String clientName, Integer invoiceId, Date date, Double total) {
        this.invoiceNumber = invoiceNumber;
        this.type = type;
        this.clientName = clientName;
        this.invoiceId = invoiceId;
        this.date = date;
        this.total = total;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public Integer getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Integer invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
}


