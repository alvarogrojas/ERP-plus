package com.ndl.erp.services;

import com.ndl.erp.constants.CostCenterConstants;
import com.ndl.erp.constants.DescuentosConstants;
import com.ndl.erp.domain.Descuentos;

import com.ndl.erp.domain.User;
import com.ndl.erp.dto.DescuentosDTO;

import com.ndl.erp.repository.CollaboratorRepository;
import com.ndl.erp.repository.DescuentosRepository;
import com.ndl.erp.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class DescuentosService {

    @Autowired
    private DescuentosRepository descuentosRepository;

    @Autowired
    private CollaboratorRepository collaboratorRepository;

    @Autowired
    private UserServiceImpl userDetailsService;


    public DescuentosDTO getDescuentos(Integer id) {
        DescuentosDTO d = this.getDescuentos();

        if (id==null) {
            Descuentos d1 = new Descuentos();
            d1.setUserIngresadoPor(userDetailsService.getCurrentLoggedUser());
            d.setCurrent(d1);
            return d;
        }
        Optional<Descuentos> c = descuentosRepository.findById(id);
        d.setCurrent(c.get());


        return d;
    }

    public DescuentosDTO getDescuentosByTipoAndReferenciaId(String tipo,Integer id) {
        DescuentosDTO d = this.getDescuentos();


        List<Descuentos> c = descuentosRepository.getDescuentosByTipoAndReferenciaId(DescuentosConstants.DESCUENTO_TIPO_CATEGORIA,id);
        d.setDescuentos(c);


        return d;
    }

    public DescuentosDTO getDescuentosByTipo(String tipo) {
        DescuentosDTO d = this.getDescuentos();


        List<Descuentos> c = descuentosRepository.getDescuentosByTipo(tipo);
        d.setDescuentos(c);


        return d;
    }



    public Descuentos getDescuentossById(Integer id) {

        Optional<Descuentos> c = descuentosRepository.findById(id);

        return c.get();
    }

//    public DescuentosDTO getDescuentos(String filter, Integer pageNumber,
//                                           Integer pageSize, String sortDirection,
//                                 String sortField) {
//
//        DescuentosDTO d = new DescuentosDTO();
//
//        d.setDescuentosPage(this.DescuentosRepository.getFilterPageable(filter,
//                createPageable(pageNumber, pageSize, sortDirection, sortField)));
//
//        d.setTotal(this.DescuentosRepository.countAllByFilter(filter));
//        if (d.getTotal()>0) {
//            d.setPagesTotal(d.getTotal() /pageSize);
//        } else {
//            d.setPagesTotal(0);
//        }
//
//        return d;
//
//    }

    public DescuentosDTO getDescuentos() {

        ArrayList<String> estados = new ArrayList<>();
        estados.add("Activo");
        estados.add("Inactivo");
        DescuentosDTO d = new DescuentosDTO();

        d.setEstados(estados);

        return d;
    }

    public Descuentos save(Descuentos c) {

        if (c.getId()==null) {
            User u = userDetailsService.getCurrentLoggedUser();
            c.setFechaUltimoCambio(new Date(DateUtil.getCurrentCalendar().getTime().getTime()));
            c.setFechaIngreso(new Date(DateUtil.getCurrentCalendar().getTime().getTime()));
            c.setUserIngresadoPor(u);
            c.setUserUltimoCambioPor(u);
        }


        return this.descuentosRepository.save(c);
    }

    private PageRequest createPageable(Integer pageNumber, Integer pageSize, String direction, String byField) {

        return PageRequest.of(pageNumber, pageSize, direction==null || direction.equals("asc")? Sort.Direction.ASC: Sort.Direction.DESC, byField);
    }
}
