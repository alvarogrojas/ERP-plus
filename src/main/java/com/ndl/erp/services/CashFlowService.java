package com.ndl.erp.services;

import com.ndl.erp.domain.Bank;
import com.ndl.erp.domain.Currency;
import com.ndl.erp.domain.FixedCost;
import com.ndl.erp.domain.Various;
import com.ndl.erp.dto.*;

import com.ndl.erp.repository.BankRepository;
import com.ndl.erp.repository.CurrencyRepository;
import com.ndl.erp.repository.FixedCostRepository;
import com.ndl.erp.repository.VariousRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class CashFlowService {

    @Autowired
    private BankRepository bankRepository;

    @Autowired
    private VariousRepository variousRepository;

    @Autowired
    private FixedCostRepository fixedCostRepository;

    @Autowired
    private CurrencyRepository currencyRepository;


    public BankDTO getBank(Integer id) {
        BankDTO d = this.getBank();
        Optional<Bank> c = bankRepository.findById(id);
        if (c==null) {
            return d;
        }
        d.setCurrent(c.get());
        return d;
    }

    public CashFlowDTO getBanks(String filter) {

        CashFlowDTO d = new CashFlowDTO();

         d.setBanks(this.bankRepository.getByFilter(filter));

        return d;

    }

    public BankDTO getBank() {


        List<Currency> currencies = currencyRepository.findAll();


        List<String> estados = new ArrayList<>();
        estados.add("Activo");
        estados.add("Inactivo");

        BankDTO d = new BankDTO();

        d.setCurrencies(currencies);
        d.setStates(estados);
        return  d;

    }

    public Bank save(Bank c) {

        return this.bankRepository.save(c);
    }


    public FixedCostDTO getFixedCost(Integer id) {
        FixedCostDTO d = this.getFixedCost();
        Optional<FixedCost> c = fixedCostRepository.findById(id);
        if (c==null) {
            return d;
        }
        d.setCurrent(c.get());
        return d;
    }

    public CashFlowDTO getFixedCosts(String filter) {
        CashFlowDTO d = new CashFlowDTO();
        d.setFixedCosts(this.fixedCostRepository.getByFilter(filter));
        return d;
    }

    public FixedCostDTO getFixedCost() {


        List<Currency> currencies = currencyRepository.findAll();



        List<String> periodicidad = new ArrayList();
        periodicidad.add("Semanal");
        periodicidad.add("Quincenal");
        periodicidad.add("Mensual");
        periodicidad.add("Trimestral");
        periodicidad.add("Semestral");
        periodicidad.add("Anual");

        List<String> estados = new ArrayList();
        estados.add("Activo");
        estados.add("Inactivo");

        FixedCostDTO d = new FixedCostDTO();

        d.setCurrencies(currencies);
        d.setEstados(estados);
        d.setPeriodicidad(periodicidad);
        return  d;

    }

    public FixedCost save(FixedCost c) {

        return this.fixedCostRepository.save(c);
    }

   


    public VariousDTO getVarious(Integer id) {
        VariousDTO d = this.getVarious();
        Optional<Various> c = variousRepository.findById(id);
        if (c==null) {
            return d;
        }
        d.setCurrent(c.get());
        return d;
    }

    public CashFlowDTO getVariousList(String filter) {

        CashFlowDTO d = new CashFlowDTO();

        d.setVarious(this.variousRepository.getByFilter(filter));

        return d;

    }

    public VariousDTO getVarious() {


        List<Currency> currencies = currencyRepository.findAll();
        List<String> periodicidad = new ArrayList();
        periodicidad.add("Semanal");
        periodicidad.add("Quincenal");
        periodicidad.add("Mensual");
        periodicidad.add("Trimestral");
        periodicidad.add("Semestral");
        periodicidad.add("Anual");

        List<String> estados = new ArrayList();
        estados.add("Activo");
        estados.add("Inactivo");

        VariousDTO d = new VariousDTO();

        d.setCurrencies(currencies);
        d.setPeriodicidad(periodicidad);
        d.setEstados(estados);
        return  d;

    }

    public Various save(Various c) {

        return this.variousRepository.save(c);
    }


}
