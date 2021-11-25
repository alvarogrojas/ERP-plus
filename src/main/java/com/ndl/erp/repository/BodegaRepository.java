package com.ndl.erp.repository;



import com.ndl.erp.domain.Bodega;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public interface BodegaRepository extends JpaRepository<Bodega, Integer> {

    @Query(value = "select c from Bodega c where c.name like %?1% "
    )
    List<Bodega> getByFilter(String filter);


    @Query(value = "select c from Bodega c where c.status=?1"
    )
    List<Bodega> getBodegaByStatus(String activa);

    @Query(value = "select c from Bodega c where c.facturable='SI' and c.status='Activa'"
    )
    List<Bodega> getBodegasFacturables();

    @Query(value = "select b1 from Bodega b1 where b1.id not in(Select  b2.bodega.id from InventarioBodega b2 where b2.producto.id = ?1 ) " +
                  "and b1.status = ?2"
    )
    List<Bodega> getBodegaByStatusAndNonExistingProduct(Integer productoId, String activa);

    Bodega getById(Integer id);

    @Query(value= "Select t from Bodega t where t.name = ?1")
    Bodega getBodegaByName(String name);

    @Query(value = "select b1 from Bodega b1 where  ?1='' or ?1=null or b1.status = ?1"
    )
    Page<Bodega> getBodegasByEstado(String estado, PageRequest pageable);

    @Query(value = "select count(b1.id) from Bodega b1 where  ?1='' or ?1=null or b1.status = ?1 order by b1.id asc"
    )
    Integer countsBodegasByEstado(String estado);
}
