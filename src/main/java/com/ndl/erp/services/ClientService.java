package com.ndl.erp.services;

import com.ndl.erp.domain.Client;
import com.ndl.erp.domain.CostCenter;
import com.ndl.erp.dto.*;
import com.ndl.erp.repository.CentroCostosRepository;
import com.ndl.erp.repository.ClientRepository;
import com.ndl.erp.repository.CostCenterStateRepository;
import com.ndl.erp.repository.CostCenterTypeRepository;
import com.ndl.erp.util.DateUtil;
import com.ndl.erp.util.StringHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;


    public ClientDTO getClient(Integer id) {
        ClientDTO d = this.getClient();
        Optional<Client> c = clientRepository.findById(id);
        if (c==null) {
            return d;
        }
        d.setCurrent(c.get());
        return d;
    }

    public ClientsDTO getClients(String filter) {

        ClientsDTO d = new ClientsDTO();

         d.setClients(this.clientRepository.findUsingFilter(filter));

        return d;

    }

    public ClientsDTO getClients(String filter, Integer pageNumber,
                                           Integer pageSize, String sortDirection,
                                 String sortField) {

        ClientsDTO d = new ClientsDTO();

        d.setClientsPage(this.clientRepository.findUsingFilterPageable(filter,
                createPageable(pageNumber, pageSize, sortDirection, sortField)));

        d.setTotal(this.clientRepository.countAllByFilter(filter));
        if (d.getTotal()>0) {
            d.setPagesTotal(d.getTotal() /pageSize);
        } else {
            d.setPagesTotal(0);
        }

        return d;

    }

    public ClientDTO getClient() {
        List<IdentificationType> types = new ArrayList<>();
        types.add(new IdentificationType("01","Cédula Física"));
        types.add(new IdentificationType("02","Cédula Jurídica"));
        types.add(new IdentificationType("03","DIMEX"));
        types.add(new IdentificationType("04","NITE"));

        List<String> estados = new ArrayList<>();
        estados.add("Activo");
        estados.add("Inactivo");

        List<String> exonerados = new ArrayList<>();
        exonerados.add("SI");
        exonerados.add("NO");
        ClientDTO d = new ClientDTO();

        d.setEstados(estados);
        d.setTypesId(types);
        d.setExonedoStates(exonerados);
        return d;
    }

    public Client save(Client c) {

        return this.clientRepository.save(c);
    }

    private PageRequest createPageable(Integer pageNumber, Integer pageSize, String direction, String byField) {

        return PageRequest.of(pageNumber, pageSize, direction==null || direction.equals("asc")? Sort.Direction.ASC: Sort.Direction.DESC, byField);
    }

    public ClientsDTO getActiveClient(String filter) {
        ClientsDTO r = new ClientsDTO();
        r.setClients(this.clientRepository.findClientsActive());
        return r;
    }
}
