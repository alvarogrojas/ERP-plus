package com.ndl.erp.services;

import com.ndl.erp.domain.Client;
import com.ndl.erp.domain.ContactClient;
import com.ndl.erp.dto.ContactClientDTO;
import com.ndl.erp.dto.ContactClientsDTO;
import com.ndl.erp.dto.IdentificationType;
import com.ndl.erp.dto.Result;
import com.ndl.erp.repository.ClientRepository;
import com.ndl.erp.repository.ContactClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class ContactClientService {

    @Autowired
    private ContactClientRepository repository;

    @Autowired
    private ClientRepository clientRepository;


    public ContactClientDTO getContactClient(Integer id, Integer clientId) {
        ContactClientDTO d = this.createContactClient(clientId);
        if (id==null) {
            return d;
        }
        Optional<ContactClient> c = repository.findById(id);

        d.setCurrent(c.get());


        return d;
    }

    public ContactClientsDTO getContactClients(String filter) {

        ContactClientsDTO d = new ContactClientsDTO();

         d.setContactClients(this.repository.findUsingFilter(filter));

        return d;

    }

    public ContactClientsDTO getContactClients(String filter, Integer clientId, Integer pageNumber,
                                           Integer pageSize, String sortDirection,
                                 String sortField) {

        ContactClientsDTO d = new ContactClientsDTO();

        d.setContactsClientPage(this.repository.findUsingFilterPageable(filter, clientId,
                createPageable(pageNumber, pageSize, sortDirection, sortField)));

        d.setTotal(this.repository.countAllByFilter(filter, clientId));
        if (d.getTotal()>0) {
            d.setPagesTotal(d.getTotal() /pageSize);
        } else {
            d.setPagesTotal(0);
        }
        d.setClient(this.clientRepository.findByClientId(clientId));

        return d;

    }

    public ContactClientDTO createContactClient(Integer clientId) {

        ContactClientDTO d = new ContactClientDTO();
        Client c = this.clientRepository.findByClientId(clientId);
        d.setClient(c);

        return d;
    }

    public ContactClient save(ContactClient c) {

        return this.repository.save(c);
    }

    private PageRequest createPageable(Integer pageNumber, Integer pageSize, String direction, String byField) {

        return PageRequest.of(pageNumber, pageSize, direction==null || direction.equals("asc")? Sort.Direction.ASC: Sort.Direction.DESC, byField);
    }

    public ContactClientsDTO getActiveContactClients(Integer id) {
        ContactClientsDTO d = new ContactClientsDTO();
        d.setContactClients(this.repository.findByClient(id));
        return d;
    }

    public Result delete(ContactClient c) {
        Result r;
        c.setStatus("Borrado");
        this.repository.save(c);
        r = new Result(Result.RESULT_CODE.DELETE, "Se eliminó el contacto del Cliente ");
        return r;
    }
}
