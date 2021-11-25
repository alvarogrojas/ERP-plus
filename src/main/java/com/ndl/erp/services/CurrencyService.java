package com.ndl.erp.services;

import com.ndl.erp.domain.Currency;
import com.ndl.erp.dto.CurrenciesDTO;
import com.ndl.erp.dto.CurrencyDTO;

import com.ndl.erp.repository.CurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class CurrencyService {

    @Autowired
    private CurrencyRepository currencyRepository;


    public CurrencyDTO getCurrency(Integer id) {
        CurrencyDTO d = this.getCurrency();
        Optional<Currency> c = currencyRepository.findById(id);
        if (c==null) {
            return d;
        }
        d.setCurrent(c.get());
        return d;
    }


    public CurrenciesDTO getCurrencys(String filter, Integer pageNumber,
                                      Integer pageSize, String sortDirection,
                                      String sortField) {

        CurrenciesDTO d = new CurrenciesDTO();

        d.setCurrencysPage(this.currencyRepository.findUsingFilterPageable(filter,
                createPageable(pageNumber, pageSize, sortDirection, sortField)));

        d.setTotal(this.currencyRepository.countAllByFilter(filter));
        if (d.getTotal()>0) {
            d.setPagesTotal(d.getTotal() /pageSize);
        } else {
            d.setPagesTotal(0);
        }

        return d;

    }

    public CurrencyDTO getCurrency() {

        CurrencyDTO d = new CurrencyDTO();

        return d;
    }

    public Currency getCurrencyById(Integer id) {

        Optional<Currency> co = this.currencyRepository.findById(id);

        if (co==null || co.get()==null) {
            throw new RuntimeException("Currency not found " + id);
        }

        return co.get();
    }

    public Currency save(Currency c) {

        return this.currencyRepository.save(c);
    }

    private PageRequest createPageable(Integer pageNumber, Integer pageSize, String direction, String byField) {

        return PageRequest.of(pageNumber, pageSize, direction==null || direction.equals("asc")? Sort.Direction.ASC: Sort.Direction.DESC, byField);
    }

    public List<Currency> findAllCurrencies(){
      List<Currency> currencies = new ArrayList<>();
      currencies = this.currencyRepository.findAllCurrency();
      return  currencies;
    }


    public Currency getSystemCurrency() {
        Currency c = this.currencyRepository.getSystemCurrency();
        if (c==null) {
            throw new RuntimeException("System currency not found");
        }
        return c;
//        List<Currency> lc = this.currencyRepository.getSystemCurrency();
//        if (lc==null || lc.size()<0) {
//            throw new RuntimeException("System currency not found");
//        }
//        return lc.get(0);
    }
}
