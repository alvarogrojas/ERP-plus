package com.ndl.erp.services;

import com.ndl.erp.dto.EntradasSalidasInventarioDTO;
import com.ndl.erp.repository.EntradasInventarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class EntradaSalidaService {

    @Autowired
    EntradasInventarioRepository entradasInventarioRepository;

    public EntradasSalidasInventarioDTO getMovimientosInventario(Integer costCenterId,
                                                                 Date start,
                                                                 Date end) {

        EntradasSalidasInventarioDTO entradasSalidasInventarioDTO = new EntradasSalidasInventarioDTO();

        entradasSalidasInventarioDTO.setEntradasSalidasInventario(this.entradasInventarioRepository.getEntradasSalidasByDatesAndCostCenter(costCenterId,
                                                                                        start,
                                                                                        end));
        return entradasSalidasInventarioDTO;
    }

}
