package com.ndl.erp.dto;

import com.ndl.erp.domain.CostCenter;
import com.ndl.erp.domain.LaborCostDetail;

import java.util.Objects;

public class CostCenterNoPODTO {
    private static final long serialVersionUID = 1777000000000000067L;

    private Integer id;
    private String code;
    private String type;
    private String name;
    private Double totalBudged;
    private Double totalBudgedMaterial;
    private Integer clientId;
    private String  client;
    private Integer contactId;
    private String contact;
    private Double total;

    private Integer idCurrency;
    private String simbol;

    private String accion;
    private String status;
    public static final String EDIT = "edit";

    public CostCenterNoPODTO() {
        this.id = null;
        this.accion = EDIT;
        this.status = "LIST";
    }

//    cc.id, cc.code, cc.type, cc.name,  cc.totalBudgeted, cc.totalBudgetedMaterials,
//    cc.client.clientId, cc.client.name, 0, 0d, lcd.currency.id, lcd.currency.simbol
    public CostCenterNoPODTO(
                            Integer id,
                             String code,
                             String type,
                             String name,
                             Double totalBudged,
                             Double totalBudgedMaterial,
                             Integer clientId,
                             String client,
                             String contac,
                             Integer currencyId,
                             String symbol, Double total
                             ) {
        this.accion = EDIT;
        this.status = "LIST";
        this.id = id;
        this.code = code;
        this.type = type;
        this.name = name;
        this.totalBudged = totalBudged;
        this.totalBudgedMaterial = totalBudgedMaterial;
        this.clientId = clientId;
        this.client = client;
        this.contact = contac;
        this.total = total;
        this.idCurrency = currencyId;
        this.simbol = symbol;


    }

    public CostCenterNoPODTO(CostCenter cc, LaborCostDetail lcd
                             ) {
        this.accion = EDIT;
        this.status = "LIST";
        this.id = cc.getId();
        this.code = cc.getCode();
        this.type = cc.getType();
        this.name = cc.getName();
        this.totalBudged = cc.getTotalBudgeted();
        this.totalBudgedMaterial = cc.getTotalBudgetedMaterials();
        this.clientId = cc.getClient().getClientId();
        this.client = cc.getClient().getName();
        this.contactId = null;
        this.total = 0d;
        this.idCurrency = lcd.getCurrency().getId();
        this.simbol = lcd.getCurrency().getSimbol();


    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getTotalBudged() {
        return totalBudged;
    }

    public void setTotalBudged(Double totalBudged) {
        this.totalBudged = totalBudged;
    }

    public Double getTotalBudgedMaterial() {
        return totalBudgedMaterial;
    }

    public void setTotalBudgedMaterial(Double totalBudgedMaterial) {
        this.totalBudgedMaterial = totalBudgedMaterial;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public Integer getContactId() {
        return contactId;
    }

    public void setContactId(Integer contactId) {
        this.contactId = contactId;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Integer getIdCurrency() {
        return idCurrency;
    }

    public void setIdCurrency(Integer idCurrency) {
        this.idCurrency = idCurrency;
    }

    public String getSimbol() {
        return simbol;
    }

    public void setSimbol(String simbol) {
        this.simbol = simbol;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static String getEDIT() {
        return EDIT;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CostCenterNoPODTO that = (CostCenterNoPODTO) o;
        return Objects.equals(code, that.code) &&
                Objects.equals(idCurrency, that.idCurrency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, idCurrency);
    }
}
