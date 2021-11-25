package com.ndl.erp.dto;

import com.ndl.erp.domain.*;
import com.ndl.erp.domain.ExchangeRate;

import java.util.ArrayList;
import java.util.List;



public class PurchaseOrderClientDTO {


    private List<String> estadosOc;
    private List<String> estadosPm;
    private List<Currency> currencies;
    private List<Client> clients;
    private List<ServiceCabys> services;
    private List<InventarioItem> inventarioItems;
    private List<TaxesIva> taxes;
    private Iterable<CostCenter> costCenters;

    private List<DescuentosCacheDTO> descuentosCache;

    private CostCenter defaultCostCenter;

    private List<ServiceCabys> serviceCabys = new ArrayList<>();
    private List<InventarioItem> items = new ArrayList<>();
    private List<ExchangeRate> exchangeRates = new ArrayList<>();


    private PurchaseOrderClient current = new PurchaseOrderClient();
    private List<Descuentos> descuentos;
    private TaxesIva defaultIva;

    public List<ExchangeRate> getExchangeRates() {
        return exchangeRates;
    }

    public void setExchangeRates(List<ExchangeRate> exchangeRates) {
        this.exchangeRates = exchangeRates;
    }

    public PurchaseOrderClient getCurrent() {
        return current;
    }

    public void setCurrent(PurchaseOrderClient current) {
        this.current = current;
    }

    public List<Currency> getCurrencies() {
        return currencies;
    }

    public void setCurrencies(List<Currency> currencies) {
        this.currencies = currencies;
    }

    public Iterable<CostCenter> getCostCenters() {
        return costCenters;
    }

    public void setCostCenters(Iterable<CostCenter> costCenters) {
        this.costCenters = costCenters;
    }

    public List<Client> getClients() {
        return clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }

    public List<String> getEstadosOc() {
        return estadosOc;
    }

    public void setEstadosOc(List<String> estadosOc) {
        this.estadosOc = estadosOc;
    }

    public List<String> getEstadosPm() {
        return estadosPm;
    }

    public void setEstadosPm(List<String> estadosPm) {
        this.estadosPm = estadosPm;
    }

    public List<TaxesIva> getTaxes() {
        return taxes;
    }

    public void setTaxes(List<TaxesIva> taxes) {
        this.taxes = taxes;
    }

    public List<ServiceCabys> getServices() {
        return services;
    }

    public void setServices(List<ServiceCabys> services) {
        this.services = services;
    }

    public List<InventarioItem> getInventarioItems() {
        return inventarioItems;
    }

    public void setInventarioItems(List<InventarioItem> inventarioItems) {
        this.inventarioItems = inventarioItems;
    }

    public List<ServiceCabys> getServiceCabys() {
        return serviceCabys;
    }

    public void setServiceCabys(List<ServiceCabys> serviceCabys) {
        this.serviceCabys = serviceCabys;
    }

//    public List<ProductCabys> getItems() {
//        return items;
//    }
//
//    public void setItems(List<ProductCabys> items) {
//        this.items = items;
//    }

    public List<InventarioItem> getItems() {
        return items;
    }

    public void setItems(List<InventarioItem> items) {
        if (items==null) {
            return;
        }
        this.items = items;
    }

    public CostCenter getDefaultCostCenter() {
        return defaultCostCenter;
    }

    public void setDefaultCostCenter(CostCenter defaultCostCenter) {
        this.defaultCostCenter = defaultCostCenter;
    }

    public void setDescuentos(List<Descuentos> descuentosActivosByTipo) {
        this.descuentos = descuentosActivosByTipo;
    }

    public List<Descuentos> getDescuentos() {
        return descuentos;
    }

    public void setDescuentosCache(List<DescuentosCacheDTO> cache) {
        this.descuentosCache = cache;
    }

    public List<DescuentosCacheDTO> getDescuentosCache() {
        return descuentosCache;
    }

    public void setDefaultIva(TaxesIva defaultIva) {
        this.defaultIva = defaultIva;
    }

    public TaxesIva getDefaultIva() {
        return defaultIva;
    }
}
