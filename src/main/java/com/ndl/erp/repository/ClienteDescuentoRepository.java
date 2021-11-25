package com.ndl.erp.repository;

import com.ndl.erp.domain.ClienteDescuento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ClienteDescuentoRepository extends JpaRepository<ClienteDescuento, Integer> {


    ClienteDescuento findClienteDescuentoById(Integer id);

    @Query(value = "select cd from ClienteDescuento cd " +
            " where cd.client.id = ?1 and categoria.id = ?2 " +
            " and  cd.estado = 'Activo'")
    List<ClienteDescuento> getClienteDescuentoByClienteAndProducto(Integer clienteId, Integer productoId);

}