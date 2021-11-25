package com.ndl.erp.dto;

import com.ndl.erp.domain.*;
import com.ndl.erp.domain.ExchangeRate;

import java.util.ArrayList;
import java.util.List;

public class CotizacionDTO {

    private Cotizacion current;
    private List<Client> clienteList;
    private List<User> vendedorList;
    private List<String> estadoList;
    private List<String> entregas;
    private List<Currency>  monedasList;
    private List<ContactClient> contactClientList;
    private List<GeneralParameter> empresaInfoParameters = new ArrayList<>();
    private List<ExchangeRate> exchangeRates = new ArrayList<>();

    private List<TaxesIva> taxes;


    public List<ExchangeRate> getExchangeRates() {
        return exchangeRates;
    }

    public void setExchangeRates(List<ExchangeRate> exchangeRates) {
        this.exchangeRates = exchangeRates;
    }

    public List<GeneralParameter> getEmpresaInfoParameters() {
        return empresaInfoParameters;
    }

    public void setEmpresaInfoParameters(List<GeneralParameter> empresaInfoParameters) {
        this.empresaInfoParameters = empresaInfoParameters;
    }

    public CotizacionDTO(){
        this.current = new Cotizacion();
    }

    public List<ContactClient> getContactClientList() {
        return contactClientList;
    }

    public void setContactClientList(List<ContactClient> contactClientList) {
        this.contactClientList = contactClientList;
    }

    public List<Currency> getMonedasList() {
        return monedasList;
    }

    public void setMonedasList(List<Currency> monedasList) {
        this.monedasList = monedasList;
    }

    public Cotizacion getCurrent() {
        return current;
    }

    public void setCurrent(Cotizacion current) {
        this.current = current;
    }

    public List<Client> getClienteList() {
        return clienteList;
    }

    public void setClienteList(List<Client> clienteList) {
        this.clienteList = clienteList;
    }

    public List<User> getVendedorList() {
        return vendedorList;
    }

    public void setVendedorList(List<User> vendedorList) {
        this.vendedorList = vendedorList;
    }

    public List<String> getEstadoList() {
        return estadoList;
    }

    public void setEstadoList(List<String> estadoList) {
        this.estadoList = estadoList;
    }

    public List<TaxesIva> getTaxes() {
        return taxes;
    }

    public void setTaxes(List<TaxesIva> taxes) {
        this.taxes = taxes;
    }

    public List<String> getEntregas() {
        return entregas;
    }

    public void setEntregas(List<String> entregas) {
        this.entregas = entregas;
    }
}
