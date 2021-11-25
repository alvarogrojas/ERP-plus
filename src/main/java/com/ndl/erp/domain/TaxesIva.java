package com.ndl.erp.domain;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
public class TaxesIva {
    @Id
    @GeneratedValue
    private Integer id;

    private String code;
    private String description;
    private Double taxPorcent;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getTaxPorcent() {
        return taxPorcent;
    }

    public void setTaxPorcent(Double tax_porcent) {
        this.taxPorcent = tax_porcent;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
