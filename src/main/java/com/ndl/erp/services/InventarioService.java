package com.ndl.erp.services;


import com.ndl.erp.domain.Inventario;
import com.ndl.erp.dto.InventariosDTO;
import com.ndl.erp.repository.BodegaRepository;
import com.ndl.erp.repository.InventarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.Date;

import static com.ndl.erp.constants.InventarioConstants.FILTRO_INVENTARIO_SOLO_EXISTENCIAS;
import static com.ndl.erp.constants.InventarioConstants.FILTRO_INVENTARIO_TODOS;

@Component
public class InventarioService {

    @Autowired
    InventarioRepository inventarioRepository;

    @Autowired
    private BodegaRepository bodegaRepository;

    @Autowired
    private UserServiceImpl userService;


    public Inventario saveInventario(Inventario i) {
        i.setFechaUltimoCambio(new Date());
        i.setUsuarioUltimoCambio(this.userService.getCurrentLoggedUser());
        return this.inventarioRepository.save(i);

    }


    public InventariosDTO getInventariosDTO(String filter,
                                            Integer bodegaId ,
                                            Integer existentes,
                                            Integer disponible,
                                            Integer pageNumber,
                                            Integer pageSize,
                                            String sortDirection,
                                            String sortField) {
        InventariosDTO inventarios= new InventariosDTO();

        inventarios.setPage(this.inventarioRepository.findInventoryByBarcodeAndProductCodeAndAvailability(filter, bodegaId , existentes, disponible,createPageable(pageNumber, pageSize, sortDirection, sortField)));


        inventarios.setBodegas(this.bodegaRepository.getBodegaByStatus("Activa"));

        inventarios.setTotal(this.inventarioRepository.countInventoryByBarcodeAndProductCodeAndAvailability(filter, bodegaId , existentes, disponible));
        if (inventarios.getTotal()>0) {
            inventarios.setPagesTotal(inventarios.getTotal() /pageSize);
        } else {
            inventarios.setPagesTotal(0);
        }

        inventarios.getFiltroExistencias().add(FILTRO_INVENTARIO_SOLO_EXISTENCIAS);
        inventarios.getFiltroExistencias().add(FILTRO_INVENTARIO_TODOS);


        return inventarios;
    }

    private PageRequest createPageable(Integer pageNumber, Integer pageSize, String direction, String byField) {

        return PageRequest.of(pageNumber, pageSize, direction==null || direction.equals("asc")? Sort.Direction.ASC: Sort.Direction.DESC, byField);
    }

}
