package com.ndl.erp.repository;

import com.ndl.erp.domain.UnidadMedida;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UnidadMedidaRepository  extends JpaRepository<UnidadMedida, Integer> {

    List<UnidadMedida>  findAll();


//    @Query(value = "select u from UnidadMedida u where (?1 = '' or ?1 = null " +
//            "or u.nombre like %?1%)")
    @Query(value = "select u from UnidadMedida u where (?1 = '' or ?1 = null " +
            "or u.estado= ?1)")
    Page<UnidadMedida> getUnidadMedidaByEstado(String filter,  Pageable pageable);

    @Query(value = "select count(u.id) from UnidadMedida u where (?1 = '' or ?1 = null " +
            "or u.estado= ?1)")
    Integer countUnidadMedidaByEstado(String filter);

    @Query(value = "select count(u.id) from UnidadMedida u where (?1 = '' or ?1 = null " +
            "or u.nombre like %?1%)")
    Integer countUnidadMedidaByNombre(String nombre);

    @Query(value = "select u from UnidadMedida u where u.id = ?1")
    UnidadMedida findUnidadMedidadById(Integer id);

    @Query(value = "select u from UnidadMedida u where u.nombre = ?1")
    UnidadMedida findUnidadMedidadByNombre(String nombre);


}
