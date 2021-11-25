package com.ndl.erp.repository;


import com.ndl.erp.domain.Familia;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface FamiliaRepository extends JpaRepository<Familia, Integer> {

    @Query(value =  "select f from Familia f where id = ?1")
    Familia getFamiliaById(Integer id);

    @Query(value = "select f from Familia f where f.estado =?1 "
    )
    List<Familia> getFamiliaByEstado(String estado);

    @Query(value = "select f from Familia f where (?1 = '' or ?1 = null " +
            "or f.nombre like %?1%) " +
            " and (?2 = null or ?2 = '' or f.estado  = ?2) "
    )
    Page<Familia> getFamiliaByFilterAndEstado(String filter, String estado, Pageable pageable);

    @Query(value = "select count(f.id) from Familia f where (?1 = '' or ?1 = null " +
            "or f.nombre like %?1%) " +
            " and (?2 = null or ?2 = '' or f.estado  = ?2) "
    )
    Integer countFamiliaByFilterAndEstado(String filter, String estado);

    @Query(value = "Select count(f.id) from Familia f where f.nombre = ?1")
    Integer countAllFamiliaByNombre(String nombre);

    @Query(value = "Select f from Familia f where f.nombre = ?1")
    Familia findFamiliaByNombre(String nombre);

    @Query(value = "select f from Familia f where (?1 = '' or ?1 = null " +
            "or f.nombre like %?1%) " +
            " and (?2 = null or ?2 = '' or f.estado  = ?2) "
    )
    List<Familia> getFamiliasByFilterAndEstado(String filter, String estado);

}

