package com.ndl.erp.repository;
import com.ndl.erp.domain.FamiliaProducto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public interface FamiliaProductoRepository extends JpaRepository<FamiliaProducto, Integer> {


    @Query(value = "select fp from FamiliaProducto fp where fp.nombre like %?1% ")
    List<FamiliaProducto> findUsingFilter(String filter);

    @Query(value = "select fp from FamiliaProducto fp where fp.nombre like %?1% ")
    Page<FamiliaProducto> findUsingFilterPageable(String filter, Pageable pageable);

    @Query(value = "select count(fp.id) from FamiliaProducto fp where fp.nombre like %?1% ")
    public Integer countAllByFilter(String filter);

    @Query(value = "select c from FamiliaProducto c where c.estado='Activo' order by nombre asc")
    List<FamiliaProducto> findFamiliaProductoEstado();

}
