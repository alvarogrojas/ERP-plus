package com.ndl.erp.services;

import com.ndl.erp.domain.GeneralParameter;
import com.ndl.erp.domain.PurchaseOrderProvider;
import com.ndl.erp.dto.PurchaseOrderProviderDTO;
import com.ndl.erp.dto.PurchaseOrderProvidersDTO;
import com.ndl.erp.repository.CentroCostosRepository;
import com.ndl.erp.repository.ClientRepository;
import com.ndl.erp.repository.CurrencyRepository;
import com.ndl.erp.repository.PurchaseOrderProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.*;

import static com.ndl.erp.constants.InvoiceConstants.INVOICE_PARAMETER_DATOS_EMPRESA;
import static com.ndl.erp.constants.InvoiceConstants.INVOICE_PARAMETER_NOMBRE_EMPRESA;

@Component
public class PurchaseOrderProviderService {

    @Autowired
    private PurchaseOrderProviderRepository purchaseOrderProviderRepository;

    @Autowired
    private CentroCostosRepository centroCostosRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    GeneralParameterService generalParameterService;

    @Autowired
    private CurrencyRepository currencyRepository;


    public PurchaseOrderProviderDTO getPurchaseOrderProvider(Integer id) throws Exception {
        PurchaseOrderProviderDTO d = this.getPurchaseOrderProvider();
        Optional<PurchaseOrderProvider> c = purchaseOrderProviderRepository.findById(id);
        if (c==null) {

            return d;
        } else {
            d.setCurrent(c.get());
            System.out.println("*********** getPurchaseOrderProvider" + d.getCurrent().getDate());
            System.out.println(d.getCurrent().getDatePay());
        }
        return d;
    }

    public PurchaseOrderProvidersDTO getPurchaseOrderProviders(String filter) {

        PurchaseOrderProvidersDTO d = new PurchaseOrderProvidersDTO();

         d.setPurchaseOrderProviders(this.purchaseOrderProviderRepository.findUsingFilter(filter));

        return d;

    }

    public PurchaseOrderProvidersDTO getPurchaseOrderProviders(String filter, Integer pageNumber,
                                           Integer pageSize, String sortDirection,
                                 String sortField) {

        PurchaseOrderProvidersDTO d = new PurchaseOrderProvidersDTO();

        d.setPurchaseOrderProvidersPage(this.purchaseOrderProviderRepository.getFilterPageable(filter,
                createPageable(pageNumber, pageSize, sortDirection, sortField)));


        d.setTotal(this.purchaseOrderProviderRepository.countAllByFilter(filter));
        if (d.getTotal()>0) {
            d.setPagesTotal(d.getTotal() /pageSize);
        } else {
            d.setPagesTotal(0);
        }

        return d;

    }

    public PurchaseOrderProviderDTO getPurchaseOrderProvider() throws Exception{


        List<String> estados = new ArrayList<>();
        estados.add("Ingresado");
        estados.add("Aprobado");
        estados.add("Anulado");
        estados.add("Entregada");

        PurchaseOrderProviderDTO d = new PurchaseOrderProviderDTO();


        d.setEstados(estados);
        d.setCurrencies(this.currencyRepository.findAll());
        d.setCostCenters(new ArrayList<>());

        String prefix = "IPOC" + this.generalParameterService.getYear() + this.generalParameterService.getMonth();
        PurchaseOrderProvider pop = new PurchaseOrderProvider();
        GeneralParameter gp = generalParameterService.getGeneralParameterByNameAndCode(GeneralParameterService.INFO_EMPRESA_PARAM_CODE, GeneralParameterService.INFO_EMPRESA_PARAM_NAME);
        pop.setTargetName(gp.getVal());
        pop.setOrderNumber(prefix);
        //this.generalParameterService.generateNextPOOrderNumber(pop);
        d.setCurrent(pop);


        return d;
    }

    public PurchaseOrderProvider save(PurchaseOrderProvider pop) {

        if (pop.getId()==null ) {
            this.generalParameterService.generateNextPOOrderNumber(pop);
            pop.setCreateAt(new Date(System.currentTimeMillis()));
        }

        pop.setUpdateAt(new Date(System.currentTimeMillis()));
        return this.purchaseOrderProviderRepository.save(pop);
    }

    private PageRequest createPageable(Integer pageNumber, Integer pageSize, String direction, String byField) {

        return PageRequest.of(pageNumber, pageSize, direction==null || direction.equals("asc")? Sort.Direction.ASC: Sort.Direction.DESC, byField);
    }

    public Map getDataForPdf(Integer id) throws Exception{

        GeneralParameter gp = this.generalParameterService.getByCode("EMPRESA_INFO");
        GeneralParameter nombreEmpresaParameter = this.generalParameterService.getGeneralParameterByNameAndCode(INVOICE_PARAMETER_DATOS_EMPRESA, INVOICE_PARAMETER_NOMBRE_EMPRESA);
        Optional<PurchaseOrderProvider> op = this.purchaseOrderProviderRepository.findById(id);
        PurchaseOrderProvider pop = op.get();
        Map <String,Object> data = new HashMap<String,Object>();
        data.put("",gp);
        data.put("nombreEmpresa", nombreEmpresaParameter);
        data.put("gp",gp);
        data.put("pop",pop);
        data.put("popds",pop.getDetails());
        return data;
    }
}
