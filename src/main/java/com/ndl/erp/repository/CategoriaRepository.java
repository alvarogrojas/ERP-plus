package com.ndl.erp.repository;

import com.ndl.erp.domain.Categoria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {

  @Query(value = "Select c from Categoria c where c.id = ?1")
  Categoria findCategoriaById(Integer id);

  @Query(value = "select c from Categoria c " +
          " where (?1=null or ?1='' or c.nombre like %?1%) " +
  " and (?2=null or ?2='' or c.estado = ?2) ")
  Page<Categoria> findAllCategoriaByNameAndEstado(String filter,String estado, Pageable pageable);

  @Query(value = "select count(c.id) from Categoria c where (?1=null or ?1='' or c.nombre like %?1%) " +
          " and (?2=null or ?2='' or c.estado = ?2) ")
  Integer countAllCategoriaByNameAndEstado(String filter,String estado);
}
