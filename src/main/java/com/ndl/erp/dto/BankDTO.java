package com.ndl.erp.dto;

import com.ndl.erp.domain.Bank;

import com.ndl.erp.domain.Currency;

import java.util.List;
//import org.springframework.data.domain.Page;


public class BankDTO {

    private List<Currency> currencies;
    private List<String> states;

    private Bank current = new Bank();

    private Integer id;
    private String name;
    private String tel;
    private String contact;
    private String contactTel;
    private Double amount;
    private Currency currency;

    private String accion;
    private String status;

    public BankDTO() {

    }

    public BankDTO(Bank bank) {
        this.current = bank;
        this.setId(bank.getId());
        this.setName(bank.getName());
        this.setTel(bank.getTel());
        this.setContact(bank.getContact());
        this.setContactTel(bank.getTelContact());
        this.setAccion("edit");
        this.setStatus(bank.getStatus());
        this.setAmount(bank.getAmount());
        if(bank.getCurrency()!=null)
            this.setCurrency(bank.getCurrency());
    }

    public List<Currency> getCurrencies() {
        return currencies;
    }

    public void setCurrencies(List<Currency> currencies) {
        this.currencies = currencies;
    }

    public Bank getCurrent() {
        return current;
    }

    public void setCurrent(Bank current) {
        this.current = current;
    }

    public List<String> getStates() {
        return states;
    }

    public void setStates(List<String> states) {
        this.states = states;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getContactTel() {
        return contactTel;
    }

    public void setContactTel(String contactTel) {
        this.contactTel = contactTel;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
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
}
