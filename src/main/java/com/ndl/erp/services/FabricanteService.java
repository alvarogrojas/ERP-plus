package com.ndl.erp.services;

import com.ndl.erp.domain.Fabricante;
import com.ndl.erp.dto.FabricanteDTO;
import com.ndl.erp.dto.FabricantesDTO;
import com.ndl.erp.dto.FabricantesListDTO;
import com.ndl.erp.exceptions.GeneralInventoryException;
import com.ndl.erp.repository.*;
import com.ndl.erp.services.bodega.BodegaManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.ndl.erp.constants.FabricanteConstants.FABRICANTE_ESTADO_ACTIVO;
import static com.ndl.erp.constants.FabricanteConstants.FABRICANTE_ESTADO_INACTIVO;

@Component
public class FabricanteService {

    @Autowired
    InventarioBodegaRepository inventarioBodegaRepository;

    @Autowired
    InventarioRepository inventarioRepository;

    @Autowired
    FabricanteRepository fabricanteRepository;

    @Autowired
    BodegaRepository bodegaRepository;

    @Autowired
    ProductoCategoriaRepository productoCategoriaRepository;

    @Autowired
    BodegaManagerService bodegaManagerService;


    @Autowired
    UnidadMedidaRepository unidadMedidaRepository;

    @Autowired
    MultiplicadorRepository multiplicadorRepository;

    @Autowired
    UserServiceImpl userService;

    @Autowired
    UserRepository userRepository;



    public FabricanteDTO getFabricante(Integer id) {
        FabricanteDTO fabricanteDTO = this.getFabricante();
        Fabricante f = this.fabricanteRepository.findFabricanteById(id);

        if (f==null) {
            return fabricanteDTO;
        }
        fabricanteDTO.setCurrent(f);
        return fabricanteDTO;
    }

    public FabricanteDTO getFabricanteData(Integer id) {

        FabricanteDTO dto  = this.getFabricante();

        Fabricante f = this.fabricanteRepository.findFabricanteById(id);
        dto.setCurrent(f);

        return dto;
    }

    public FabricanteDTO getFabricante() {


        List<String> estados = this.getFabricanteEstados();

       Fabricante  f = new Fabricante();
       f.setEstado(FABRICANTE_ESTADO_ACTIVO);

        FabricanteDTO fabricanteDTO = new FabricanteDTO();
        fabricanteDTO.setCurrent(f);
        fabricanteDTO.setEstados(estados);

        return fabricanteDTO;
    }


    public FabricantesDTO getFabricantes(String filter, String estado, Integer pageNumber,
                                         Integer pageSize, String sortDirection,
                                         String sortField) {

        FabricantesDTO f = new FabricantesDTO();
        List<String> estados = this.getFabricanteEstados();
        f.setEstados(estados);

        f.setPage(this.fabricanteRepository.getFabricanteByFilterAndEstado(filter, estado,
                createPageable(pageNumber, pageSize, sortDirection, sortField)));


        f.setTotal(this.fabricanteRepository.countFabricanteByFilterAndEstado(filter, estado));
        if (f.getTotal()>0) {
            f.setPagesTotal(f.getTotal() /pageSize);
        } else {
            f.setPagesTotal(0);
        }

        return f;
    }



    boolean validarNombreFabricanteDuplicado(Fabricante p){
        Boolean existeCodigo = false;
        Integer cont = 0;
        cont = this.fabricanteRepository.countAllFabricanteByNombre(p.getNombre());
        if (cont > 0) {
            existeCodigo = true;
        }
        return existeCodigo;
    }

    @Transactional(rollbackFor = {Exception.class})
    public synchronized  Fabricante save(Fabricante f) throws Exception {

        if (f.getId() == null) {
            if (validarNombreFabricanteDuplicado(f)) {
                throw new GeneralInventoryException("El nombre del fabricante es  ya existe!");
            }
        }

        this.fabricanteRepository.save(f);

        return f;
    }

    public List<String> getFabricanteEstados(){
        List<String> estados = new ArrayList<>(0);
        estados.add(FABRICANTE_ESTADO_ACTIVO);
        estados.add(FABRICANTE_ESTADO_INACTIVO);
        return estados;
    }



    private PageRequest createPageable(Integer pageNumber, Integer pageSize, String direction, String byField) {

        return PageRequest.of(pageNumber, pageSize, direction==null || direction.equals("asc")? Sort.Direction.ASC: Sort.Direction.DESC, byField);
    }


    public FabricantesListDTO getListByFilter(String filter) {
        FabricantesListDTO result = new FabricantesListDTO();

        result.setFabricantes(this.fabricanteRepository.getFabricantesByFilterAndEstado(filter,FABRICANTE_ESTADO_ACTIVO));

        return result;
    }
}

