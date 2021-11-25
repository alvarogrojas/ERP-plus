package com.ndl.erp.services;

import com.ndl.erp.domain.Provider;
import com.ndl.erp.dto.ProviderDTO;
import com.ndl.erp.dto.ProvidersDTO;
import com.ndl.erp.dto.IdentificationType;
import com.ndl.erp.repository.ProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class ProviderService {

    @Autowired
    private ProviderRepository providerRepository;


    public ProviderDTO getProvider(Integer id) {
        ProviderDTO d = this.getProvider();
        Optional<Provider> c = providerRepository.findById(id);
        if (c==null) {
            return d;
        }
        d.setCurrent(c.get());
        return d;
    }


    public ProvidersDTO getProviders(String filter, Integer pageNumber,
                                           Integer pageSize, String sortDirection,
                                 String sortField) {

        ProvidersDTO d = new ProvidersDTO();

        d.setProvidersPage(this.providerRepository.findUsingFilterPageable(filter,
                createPageable(pageNumber, pageSize, sortDirection, sortField)));

        d.setTotal(this.providerRepository.countAllByFilter(filter));
        if (d.getTotal()>0) {
            d.setPagesTotal(d.getTotal() /pageSize);
        } else {
            d.setPagesTotal(0);
        }

        return d;

    }

    public ProviderDTO getProvider() {

        List<String> estados = new ArrayList<>();
        estados.add("Activo");
        estados.add("Inactivo");

//        List<String> exonerados = new ArrayList<>();
//        exonerados.add("SI");
//        exonerados.add("NO");
        ProviderDTO d = new ProviderDTO();

        d.setEstados(estados);
//        d.setTypesId(types);
//        d.setExonedoStates(exonerados);
        return d;
    }

    public Provider save(Provider c) {

        return this.providerRepository.save(c);
    }

    private PageRequest createPageable(Integer pageNumber, Integer pageSize, String direction, String byField) {

        return PageRequest.of(pageNumber, pageSize, direction==null || direction.equals("asc")? Sort.Direction.ASC: Sort.Direction.DESC, byField);
    }
}
