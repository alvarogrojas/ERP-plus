package com.ndl.erp.services;

import com.ndl.erp.domain.*;
import com.ndl.erp.dto.*;
import com.ndl.erp.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.ndl.erp.constants.ClienteDescuentoConstants.CLIENTE_DESCUENTO_ESTADO_ACTIVO;
import static com.ndl.erp.constants.ClienteDescuentoConstants.CLIENTE_DESCUENTO_ESTADO_INACTIVO;


@Component
    public class UnidadMedidaService {



        @Autowired
        UnidadMedidaRepository unidadMedidaRepository;



        public UnidadMedidaDTO getUnidadMedida(Integer id) {
            UnidadMedidaDTO unidadMedidaDTO = this.getUnidadMedida();
            UnidadMedida u = this.unidadMedidaRepository.findUnidadMedidadById(id);

            if (u==null) {
                return unidadMedidaDTO;
            }
            unidadMedidaDTO.setCurrent(u);
            return unidadMedidaDTO;
        }

        public UnidadMedidaDTO getProductoData(Integer id) {
            UnidadMedidaDTO dto  = this.getUnidadMedida();

            UnidadMedida u = this.unidadMedidaRepository.findUnidadMedidadById(id);
            dto.setCurrent(u);

            return dto;
        }


        public UnidadMedidaDTO getUnidadMedida() {


            UnidadMedida u = new UnidadMedida();


            UnidadMedidaDTO unidadMedidaDTO = new UnidadMedidaDTO();
            unidadMedidaDTO.setCurrent(u);

            List<String> estadoList = new ArrayList<>();
            estadoList.add("Activo");
            estadoList.add("Inactivo");
            unidadMedidaDTO.setEstados(estadoList);

            return unidadMedidaDTO;
        }


        public UnidadesMedidaDTO getUnidadesMedida(String estado, Integer pageNumber,
                                                   Integer pageSize, String sortDirection,
                                                   String sortField) {
            UnidadesMedidaDTO u = new UnidadesMedidaDTO();

            List<String> estadoList = new ArrayList<>();
            estadoList.add("Activo");
            estadoList.add("Inactivo");
            u.setEstados(estadoList);
            u.setPage(this.unidadMedidaRepository.getUnidadMedidaByEstado(estado,
                    createPageable(pageNumber, pageSize, sortDirection, sortField)));

            u.setTotal(this.unidadMedidaRepository.countUnidadMedidaByEstado(estado));
            if (u.getTotal()>0) {
                u.setPagesTotal(u.getTotal() /pageSize);
            } else {
                u.setPagesTotal(0);
            }

            return u;
        }




        boolean validarNombreUnidadMedidaDuplicado(UnidadMedida u){
            Boolean existeNombre = false;
            Integer cont = 0;
            cont = this.unidadMedidaRepository.countUnidadMedidaByNombre(u.getNombre());
            if (cont > 0) {
                existeNombre = true;
            }
            return existeNombre;
        }

        @Transactional(rollbackFor = {Exception.class})
        public synchronized  UnidadMedida save(UnidadMedida u) throws Exception {

            if (u.getId() == null) {
                if (validarNombreUnidadMedidaDuplicado(u))
                    throw new RuntimeException("El nombre de la unidad de medida ya existe!");


            }

            this.unidadMedidaRepository.save(u);
            return u;
        }



        private PageRequest createPageable(Integer pageNumber, Integer pageSize, String direction, String byField) {

            return PageRequest.of(pageNumber, pageSize, direction==null || direction.equals("asc")? Sort.Direction.ASC: Sort.Direction.DESC, byField);
        }




    }
