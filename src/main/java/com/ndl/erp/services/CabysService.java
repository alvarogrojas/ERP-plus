package com.ndl.erp.services;

import com.ndl.erp.domain.Cabys;

import com.ndl.erp.domain.InventarioItem;
import com.ndl.erp.domain.ProductCabys;
import com.ndl.erp.domain.ServiceCabys;
import com.ndl.erp.dto.*;
import com.ndl.erp.repository.*;

import org.springframework.data.domain.Page;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

import java.util.List;


@Component
public class CabysService {

    @Autowired
    private CabysRepository cabysRepository;

    @Autowired
    private BodegaRepository bodegaRepository;

    @Autowired
    private ServiceCabysRepository serviceCabysRepository;

//    @Autowired
//    private InventarioItemRepository inventarioItemsRepository;

    @Autowired
    private ProductCabysRepository inventarioItemsRepository;

    @Autowired
    private InventarioItemRepository inventarioRepository;


    public CabysesDTO getCabys(String categoria1, String filter,
                             Integer pageNumber,
                             Integer pageSize, String sortDirection,
                             String sortField) {
        CabysesDTO d = new CabysesDTO();
        Page<Cabys> cl = cabysRepository.getCabysByCategoria1AndFilter(categoria1, filter, createPageable(pageNumber, pageSize, sortDirection, sortField));
        if (cl==null) {
            return null;
        }

        Integer total = cabysRepository.countCabysByCategoria1AndFilter(categoria1, filter);
        d.setTotal(total);
        d.setCabys(cl);

        if (d.getTotal()!=null && d.getTotal() > 0) {
            d.setPagesTotal(d.getTotal() / pageSize);
        } else {
            d.setPagesTotal(0);
        }

        return d;

    }

    public CabysDTO getCabyDTO() {

        CabysDTO dto = new CabysDTO();

        List<CategoryDTO> cl = cabysRepository.getCategoriasProductos();
        if (cl==null) {
            return null;
        }
        dto.setCategoriasProductos(cl);

        List<CategoryDTO> c2 = cabysRepository.getCategoriasServicios();
        dto.setCategoriasServicios(c2);


        List<CategoryDTO> todasL = cabysRepository.getCategorias();
        CategoryDTO todas = new CategoryDTO("-1", "TODAS");

        todasL.add(0,todas);
        dto.setTodasCategorias(todasL);

        Page<Cabys> pc = cabysRepository.getCabysByFilter("", createPageable(1, 25,
                "desc", "id"));
        dto.setCabysItems(pc);

        Integer total = cabysRepository.countCabysByFilter("");
        dto.setTotal(total);


        if (total!=null && total > 0) {
            dto.setPagesTotal(total / 25);
        } else {
            dto.setPagesTotal(0);
        }

        List<String> types = new ArrayList<>();
        types.add("Servicio");
        types.add("Producto");

        dto.setTypes(types);

        dto.setBodegas(this.bodegaRepository.getBodegaByStatus("Activa"));

        return dto;

    }

    public Boolean saveService(SubmitCabys c) {
        //return this.serviceCabysRepository.save(c);
        for (ServiceCabys sc: c.getServices()) {
            ServiceCabys sc1 = this.serviceCabysRepository.getByCabysIdAndCodigoIngpro(sc.getCabys().getId(), sc.getCodigoIngpro());
            if (sc1==null) {
                this.serviceCabysRepository.save(sc);
            }
        }

        return true;
    }

    public Boolean saveInventoryItem(SubmitCabys c) {
        for (ProductCabys sc: c.getProducts()) {
            ProductCabys sc1 = this.inventarioItemsRepository.getByCabysId(sc.getCabys().getId());
            if (sc1 == null) {
                this.inventarioItemsRepository.save(sc);
            }
        }

        for (InventarioItem sc: c.getItems()) {
            InventarioItem sc1 = this.inventarioRepository.getByCodigoIngproAndBodegaId(sc.getCodigoIngpro(), sc.getBodega().getId());
            if (sc1 == null) {
                this.inventarioRepository.save(sc);
            }
        }

        return true;
    }


    public CabysesDTO getCabyses(String filter, String categoria1,
                                  String descripcion1,
                                  Integer pageNumber,
                                  Integer pageSize, String sortDirection,
                                  String sortField) {

        CabysesDTO d = new CabysesDTO();
        Page<Cabys> cl = null;
        Integer total = -1;

        if (categoria1!=null && !categoria1.equals("-1")) {
            cl = cabysRepository.getCabysByCategoria1AndFilter(categoria1, filter, createPageable(pageNumber, pageSize, sortDirection, sortField));
           total = cabysRepository.countCabysByCategoria1AndFilter(categoria1, filter);

        } else {
            cl = cabysRepository.getCabysByFilter(filter, createPageable(pageNumber, pageSize, sortDirection, sortField));
            total = cabysRepository.countCabysByFilter(filter);

        }
        if (cl==null) {
            return null;
        }

        d.setTotal(total);
        d.setCabys(cl);

        d.setBodegas(this.bodegaRepository.getBodegaByStatus("Activa"));

        if (d.getTotal()!=null && d.getTotal() > 0) {
            d.setPagesTotal(d.getTotal() / pageSize);
        } else {
            d.setPagesTotal(0);
        }

        return d;

    }



    private PageRequest createPageable(Integer pageNumber, Integer pageSize, String direction, String byField) {

        return PageRequest.of(pageNumber, pageSize, direction == null || direction.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, byField);
    }

    public ServiceCabysDTO getServiceCabys(String filter) {
        ServiceCabysDTO d = new ServiceCabysDTO();
        d.setServiceCabys(this.serviceCabysRepository.getByFilter(filter));
        return d;
    }

    public InventarioItemsDTO getItems(String filter) {
        InventarioItemsDTO d = new InventarioItemsDTO();
//        d.setItems(this.inventarioItemsRepository.getByFilter(filter));
        d.setItems(inventarioRepository.getByFilter(filter));
        return d;
    }

    public ProductCabys getInventarioItem(Integer id) {
        return this.inventarioItemsRepository.getById(id);
    }

    public ServiceCabys getServiceCabys(Integer id) {

        return this.serviceCabysRepository.getById(id);

    }

    public CabysFilteredDTO getCabys(String filter, String categoria1) {
        List<Cabys> l = this.cabysRepository.getProductosByFilter(filter);

        CabysFilteredDTO r = new CabysFilteredDTO();
        r.setCabys(l);
        return r;
    }
}
