package com.ndl.erp.dto;

import java.util.Date;

public class ProductsPendingReturnTotalDTO {


    private String clientName;
    private String documentReturnNumber;
    private String documentOrigin;
    private Integer id;
    private String type; //"DEV-NC"  ||  "DEV-RQ"
    private Date documentDate;
    private Double total;

    public ProductsPendingReturnTotalDTO() {
    }
    public ProductsPendingReturnTotalDTO(Integer documentReturnNumber, String clientName, Integer id, String type, Date documentDate, Double total) {
        this.clientName = clientName;
        this.documentReturnNumber = documentReturnNumber.toString();
        this.documentOrigin = documentReturnNumber.toString();
        this.id = id;
        this.type = type;
        this.documentDate = documentDate;
        this.total = total;
    }

    public String getDocumentReturnNumber() {
        return documentReturnNumber;
    }

    public void setDocumentReturnNumber(String documentReturnNumber) {
        this.documentReturnNumber = documentReturnNumber;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
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

    public Date getDocumentDate() {
        return documentDate;
    }

    public void setDocumentDate(Date documentDate) {
        this.documentDate = documentDate;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getDocumentOrigin() {
        return documentOrigin;
    }

    public void setDocumentOrigin(String documentOrigin) {
        this.documentOrigin = documentOrigin;
    }
}