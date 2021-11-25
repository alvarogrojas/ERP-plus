package com.ndl.erp.dto;

import com.ndl.erp.domain.*;
import com.ndl.erp.domain.ExchangeRate;

import java.util.ArrayList;
import java.util.List;
//import org.springframework.data.domain.Page;


public class PosDTO {

    private EmpresaTiqueteDTO empresaTiqueteDTO;

    private List<DescuentosCacheDTO> descuentosCache;

    private List<Currency> currencies;

    private List<TaxesIva> ivas;

    private List<EconomicActivity> activities;

    private List<MedioPago> medioPagos;

    private CostCenter defaultCostCenter;

    private SessionPos currentSession;

    private Client clienteDefault;

    private TaxesIva defaultIva;

    private Invoice current;

    private List<ExchangeRate> exchangeRates = new ArrayList<>();
    private List<Descuentos> descuentos;

    public List<ExchangeRate> getExchangeRates() {
        return exchangeRates;
    }

    public void setExchangeRates(List<ExchangeRate> exchangeRates) {
        this.exchangeRates = exchangeRates;
    }

    public Client getClienteDefault() {
        return clienteDefault;
    }

    public void setClienteDefault(Client clienteDefault) {
        this.clienteDefault = clienteDefault;
    }

    public EmpresaTiqueteDTO getEmpresaTiqueteDTO() {
        return empresaTiqueteDTO;
    }

    public void setEmpresaTiqueteDTO(EmpresaTiqueteDTO empresaTiqueteDTO) {
        this.empresaTiqueteDTO = empresaTiqueteDTO;
    }

    public List<Currency> getCurrencies() {
        return currencies;
    }

    public void setCurrencies(List<Currency> currencies) {
        this.currencies = currencies;
    }

    public List<TaxesIva> getIvas() {
        return ivas;
    }

    public void setIvas(List<TaxesIva> ivas) {
        this.ivas = ivas;
    }

    public List<MedioPago> getMedioPagos() {
        return medioPagos;
    }

    public void setMedioPagos(List<MedioPago> medioPagos) {
        this.medioPagos = medioPagos;
    }

    public CostCenter getDefaultCostCenter() {
        return defaultCostCenter;
    }

    public void setDefaultCostCenter(CostCenter defaultCostCenter) {
        this.defaultCostCenter = defaultCostCenter;
    }

    public void setCurrentSession(SessionPos sessionPosAbierta) {
        this.currentSession = sessionPosAbierta;
    }

    public SessionPos getCurrentSession() {
        return currentSession;
    }

    public List<EconomicActivity> getActivities() {
        return activities;
    }

    public void setActivities(List<EconomicActivity> activities) {

        this.activities = activities;
    }

    public Invoice getCurrent() {
        return current;
    }

    public void setCurrent(Invoice current) {
        this.current = current;
    }

    public void setDescuentos(List<Descuentos> d) {
        this.descuentos = d;
    }

    public List<Descuentos> getDescuentos() {
        return descuentos;
    }

    public List<DescuentosCacheDTO> getDescuentosCache() {
        return descuentosCache;
    }

    public void setDescuentosCache(List<DescuentosCacheDTO> descuentosCache) {
        this.descuentosCache = descuentosCache;
    }

    public TaxesIva getDefaultIva() {
        return defaultIva;
    }

    public void setDefaultIva(TaxesIva defaultIva) {
        this.defaultIva = defaultIva;
    }
}
