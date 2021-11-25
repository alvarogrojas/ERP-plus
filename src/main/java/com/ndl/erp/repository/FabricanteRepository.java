package com.ndl.erp.repository;
import com.ndl.erp.domain.Fabricante;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface FabricanteRepository extends JpaRepository<Fabricante, Integer> {

    @Query(value = "Select f from Fabricante f where id = ?1")
    Fabricante findFabricanteById(Integer id);

    @Query(value = "Select count(f.id) from Fabricante f where f.nombre = ?1")
    Integer countAllFabricanteByNombre(String nombre);

    @Query(value = "select f from Fabricante f where (?1 = '' or ?1 = null " +
            "or f.nombre like %?1%) " +
            " and (?2 = null or ?2 = '' or f.estado  = ?2) "
    )
    Page<Fabricante> getFabricanteByFilterAndEstado(String filter, String estado, Pageable pageable);


    @Query(value = "select f from Fabricante f where (?1 = '' or ?1 = null " +
            "or f.nombre like %?1%) " +
            " and (?2 = null or ?2 = '' or f.estado  = ?2) "
    )
    List<Fabricante> getFabricantesByFilterAndEstado(String filter, String estado);


    @Query(value = "select count(f.id) from Fabricante f where (?1 = '' or ?1 = null " +
            "or f.nombre like %?1%) " +
            " and (?2 = null or ?2 = '' or f.estado  = ?2) "
    )
    Integer countFabricanteByFilterAndEstado(String filter, String estado);

    @Query(value = "Select f from Fabricante f where f.nombre = ?1")
    Fabricante findFabricanteByNombre(String nombre);

}