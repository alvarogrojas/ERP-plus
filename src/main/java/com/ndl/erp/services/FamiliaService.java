package com.ndl.erp.services;

import com.ndl.erp.domain.Familia;
import com.ndl.erp.dto.FabricantesListDTO;
import com.ndl.erp.dto.FamiliaDTO;
import com.ndl.erp.dto.FamiliasDTO;
import com.ndl.erp.exceptions.GeneralInventoryException;
import com.ndl.erp.repository.FamiliaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.ndl.erp.constants.FamiliaConstants.FAMILIA_ESTADO_ACTIVO;
import static com.ndl.erp.constants.FamiliaConstants.FAMILIA_ESTADO_INACTIVO;

@Component
public class FamiliaService {

    @Autowired
    FamiliaRepository familiaRepository;


    public FamiliaDTO getFamilia(Integer id) {
        FamiliaDTO familiaDTO = this.getFamilia();
        Familia f = this.familiaRepository.getFamiliaById(id);

        if (f==null) {
            return familiaDTO;
        }
        familiaDTO.setCurrent(f);
        return familiaDTO;
    }

    public FamiliaDTO getFamilia() {


        List<String> estados = this.getFamiliaEstados();

        Familia  f = new Familia();
        f.setEstado(FAMILIA_ESTADO_ACTIVO);

        FamiliaDTO familiaDTO = new FamiliaDTO();
        familiaDTO.setCurrent(f);
        familiaDTO.setEstados(estados);

        return familiaDTO;
    }


    public FamiliaDTO getFamiliaData(Integer id) {

        FamiliaDTO dto  = this.getFamilia();

        Familia f = this.familiaRepository.getFamiliaById(id);
        dto.setCurrent(f);

        return dto;
    }

    public List<String> getFamiliaEstados(){
        List<String> estados = new ArrayList<>(0);
        estados.add(FAMILIA_ESTADO_ACTIVO);
        estados.add(FAMILIA_ESTADO_INACTIVO);
        return estados;
    }

    public FamiliasDTO getFamilias(String filter, String estado, Integer pageNumber,
                                   Integer pageSize, String sortDirection,
                                   String sortField) {

        FamiliasDTO f = new FamiliasDTO();
        List<String> estados = this.getFamiliaEstados();
        f.setEstados(estados);

        f.setPage(this.familiaRepository.getFamiliaByFilterAndEstado(filter, estado,
                createPageable(pageNumber, pageSize, sortDirection, sortField)));


        f.setTotal(this.familiaRepository.countFamiliaByFilterAndEstado(filter, estado));
        if (f.getTotal()>0) {
            f.setPagesTotal(f.getTotal() /pageSize);
        } else {
            f.setPagesTotal(0);
        }

        return f;
    }


    boolean validarNombreFamiliaDuplicado(Familia f){
        Integer cont = 0;
        boolean existeNombre = false;
        cont = this.familiaRepository.countAllFamiliaByNombre(f.getNombre());
        if (cont > 0) {
            existeNombre = true;
        }
        return existeNombre;
    }

    @Transactional(rollbackFor = {Exception.class})
    public synchronized  Familia save(Familia f) throws Exception {
        try {
            if (f.getId() == null) {
                if (validarNombreFamiliaDuplicado(f)) {
                    throw new GeneralInventoryException("El nombre de la familia  ya existe!");
                }
            }

            this.familiaRepository.save(f);
        } catch(RuntimeException e1) {
            e1.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return f;
    }


    private PageRequest createPageable(Integer pageNumber, Integer pageSize, String direction, String byField) {

        return PageRequest.of(pageNumber, pageSize, direction==null || direction.equals("asc")? Sort.Direction.ASC: Sort.Direction.DESC, byField);
    }

    public FamiliasDTO getListByFilter(String filter) {

        FamiliasDTO f = new FamiliasDTO();


        f.setFamilias(this.familiaRepository.getFamiliasByFilterAndEstado(filter, FAMILIA_ESTADO_ACTIVO));

        return f;
    }
}
