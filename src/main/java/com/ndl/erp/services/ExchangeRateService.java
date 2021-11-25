package com.ndl.erp.services;


import com.ndl.erp.dto.ExchangeRateDTO;
import com.ndl.erp.dto.ExchangeRatesDTO;
import com.ndl.erp.exceptions.GeneralInventoryException;
import com.ndl.erp.domain.ExchangeRate;
import com.ndl.erp.repository.CurrencyRepository;
import com.ndl.erp.repository.ExhangeRateRepository;
import com.ndl.erp.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.ndl.erp.constants.ExchangeRateConstants.EXCHANGE_RATE_ESTADO_ACTIVO;
import static com.ndl.erp.constants.ExchangeRateConstants.EXCHANGE_RATE_ESTADO_INACTIVO;

@Service
public class ExchangeRateService {
	
	@Autowired
	private ExhangeRateRepository exchangeRateRepository;

	@Autowired
    private UserServiceImpl userService;

//	@Autowired
//    private CurrencyService currencyService;

	@Autowired
    private CurrencyRepository currencyRepository;


    public ExchangeRate getFirstActiveExchangeRate() {
	    ExchangeRate er = null;
        List<ExchangeRate> l = this.getActivesExchangeRate();
        if (l!=null && l.size() > 0) {
            er = l.get(0);
        }

        return er;
    }

    public List<ExchangeRate> getActivesExchangeRate()
    {
        return this.exchangeRateRepository.getActivoExchangeRates();
    }


    public ExchangeRateDTO getExhangeRate(Integer id) {
        ExchangeRateDTO d = this.getExchangeRate();
        ExchangeRate c = exchangeRateRepository.getExchangeRateById(id);
        if (c==null) {
            return d;
        }
        d.setCurrent(c);
        return d;
    }


    public ExchangeRatesDTO getExchangeRates(Date startDate, Date endDate, String status, Integer pageNumber,
                                             Integer pageSize, String sortDirection,
                                             String sortField) {

        ExchangeRatesDTO d = new ExchangeRatesDTO();

        d.setExchangeRatesPage(this.exchangeRateRepository.getFilterPageableExchangeRateByDateAndStatus(startDate, endDate, status,
                createPageable(pageNumber, pageSize, sortDirection, sortField)));

        d.setTotal(this.exchangeRateRepository.countFilterPageableExchangeRateByDateAndStatus(startDate, endDate, status));
        if (d.getTotal()>0) {
            d.setPagesTotal(d.getTotal() /pageSize);
        } else {
            d.setPagesTotal(0);
        }

        return d;

    }

    public ExchangeRateDTO getExchangeRate() {

        List<String> statuses = new ArrayList<>();
        statuses.add(EXCHANGE_RATE_ESTADO_ACTIVO);
        statuses.add(EXCHANGE_RATE_ESTADO_INACTIVO);

        ExchangeRateDTO d = new ExchangeRateDTO();
        d.setStatuses(statuses);
        d.setCurrencies(this.currencyRepository.getNotSystemCurrencies());

        return d;
    }

    public void setAuditoriaModificacion(ExchangeRate ec) {
        ec.setUpdatedByUser(this.userService.getCurrentLoggedUser());
        ec.setUpdatedDate(new Date(DateUtil.getCurrentCalendar().getTime().getTime()));
    }

    public void setAuditoriaCreacion(ExchangeRate ec) {
        ec.setCreatedByUser(this.userService.getCurrentLoggedUser());
        ec.setUpdatedDate(new Date(DateUtil.getCurrentCalendar().getTime().getTime()));
        ec.setCreatedDate(new Date(DateUtil.getCurrentCalendar().getTime().getTime()));
    }


    public void deactivateOldEchangeRate(Integer currencyId){
        List <ExchangeRate> tiposCambioActivos;

        tiposCambioActivos = this.exchangeRateRepository.getActivoExchangeRatesByCurrencyId(currencyId);

        for (ExchangeRate  tc : tiposCambioActivos) {
            tc.setStatus(EXCHANGE_RATE_ESTADO_INACTIVO);
            this.exchangeRateRepository.save(tc);
        }


    }

    public ExchangeRate save(ExchangeRate c) {


	    if (c.getCurrency() == null) {
	        throw new GeneralInventoryException("Falta definir la moneda para este tipo de cambio");
        }

	    if (c.getId() == null) {
            this.setAuditoriaModificacion(c);
            this.setAuditoriaCreacion(c);
            if (c.getStatus().equals(EXCHANGE_RATE_ESTADO_ACTIVO)) {
                deactivateOldEchangeRate(c.getCurrency().getId());
            }
        } else {
	         this.setAuditoriaModificacion(c);
            if (c.getStatus().equals(EXCHANGE_RATE_ESTADO_ACTIVO)) {
                deactivateOldEchangeRate(c.getCurrency().getId());
            }
        }

        return this.exchangeRateRepository.save(c);
    }

    private PageRequest createPageable(Integer pageNumber, Integer pageSize, String direction, String byField) {

        return PageRequest.of(pageNumber, pageSize, direction==null || direction.equals("asc")? Sort.Direction.ASC: Sort.Direction.DESC, byField);
    }



}
