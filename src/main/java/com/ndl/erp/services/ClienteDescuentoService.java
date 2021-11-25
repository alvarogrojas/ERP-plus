package com.ndl.erp.services;

import com.ndl.erp.domain.ClienteDescuento;
import com.ndl.erp.dto.ClienteDescuentoDTO;
import com.ndl.erp.dto.ClienteDescuentoListDTO;
import com.ndl.erp.repository.ClienteDescuentoRepository;
import com.ndl.erp.repository.ProductoCategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.ndl.erp.constants.ClienteDescuentoConstants.CLIENTE_DESCUENTO_ESTADO_ACTIVO;
import static com.ndl.erp.constants.ClienteDescuentoConstants.CLIENTE_DESCUENTO_ESTADO_INACTIVO;


@Component
public class ClienteDescuentoService {

        @Autowired
        private ClienteDescuentoService clienteDescuentoService;

        @Autowired
        private ProductoCategoriaRepository productoCategoriaRepository;

        @Autowired
        private ClienteDescuentoRepository clienteDescuentoRepository;


        public ClienteDescuentoDTO getClienteDescuento(Integer id) {
            ClienteDescuentoDTO clienteDescuentoDTO = this.getClienteDescuento();
            ClienteDescuento cd = this.clienteDescuentoRepository.findClienteDescuentoById(id);

            if (cd==null) {
                return clienteDescuentoDTO;
            }
            clienteDescuentoDTO.setCurrent(cd);
            return clienteDescuentoDTO;
        }

        public ClienteDescuentoListDTO getClientDescuentoList(Integer clienteId, Integer productoId) {

            ClienteDescuentoListDTO clienteDescuentoListDTO = new ClienteDescuentoListDTO();

            clienteDescuentoListDTO.setClienteDescuentoList(this.clienteDescuentoRepository.getClienteDescuentoByClienteAndProducto(clienteId,
                                                                                                                                    productoId));

            return clienteDescuentoListDTO;

        }


        public ClienteDescuentoDTO getClienteDescuento() {

           List<String> estadoList = new ArrayList<>();
           estadoList.add(CLIENTE_DESCUENTO_ESTADO_ACTIVO);
           estadoList.add(CLIENTE_DESCUENTO_ESTADO_INACTIVO);

            ClienteDescuentoDTO clienteDescuentoDTO = new ClienteDescuentoDTO();

            clienteDescuentoDTO.setEstadoList(estadoList);
            clienteDescuentoDTO.setProductoCategoriaList(this.productoCategoriaRepository.findAll());


            return clienteDescuentoDTO;
        }

        public ClienteDescuento save(ClienteDescuento cd) {

            return this.clienteDescuentoRepository.save(cd);
        }

        private PageRequest createPageable(Integer pageNumber, Integer pageSize, String direction, String byField) {

            return PageRequest.of(pageNumber, pageSize, direction==null || direction.equals("asc")? Sort.Direction.ASC: Sort.Direction.DESC, byField);
        }
    }