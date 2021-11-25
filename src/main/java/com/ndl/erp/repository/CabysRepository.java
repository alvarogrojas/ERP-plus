package com.ndl.erp.repository;



import com.ndl.erp.domain.Cabys;

import com.ndl.erp.dto.CategoryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.List;



@Component
public interface CabysRepository extends JpaRepository<Cabys, Integer> {

    @Query(value = "select c from Cabys c where c.categoria1=?1 and (c.descripcion like %?2%)"
    )
    List<Cabys> getCabysByCategoria1AndFilter(String categoria1, String filter);


    @Query(value = "select c from Cabys c where c.categoria1=?1 and (c.codigoBien like %?2%  or c.descripcion like %?2%)"
    )
    Page<Cabys> getCabysByCategoria1AndFilter(String categoria1, String filter, Pageable pageable);

    @Query(value = "select c from Cabys c where (c.codigoBien like %?1%  or c.descripcion like %?1%)"
    )
    Page<Cabys> getCabysByFilter(String filter, Pageable pageable);

    @Query(value = "select count(c.id) from Cabys c where (c.codigoBien like %?1%  or  c.codigoBien like %?1% or c.descripcion like %?1%)"
    )
    Integer countCabysByFilter(String filter);

    @Query(value = "select count(c.id) from Cabys c where c.categoria1=?1 and (c.codigoBien like %?2%  or  c.codigoBien like %?2% or c.descripcion like %?2%)"
    )
    Integer countCabysByCategoria1AndFilter(String categoria1, String filter);


    @Query(value = "select DISTINCT new com.ndl.erp.dto.CategoryDTO(c.categoria1, c.descripcion1)  from Cabys c"
    )
    List<CategoryDTO> getCategorias();

    @Query(value = "select DISTINCT new com.ndl.erp.dto.CategoryDTO(c.categoria1,c.descripcion1)  from Cabys c where c.descripcion1 like '%Servicio%'"
    )
    List<CategoryDTO> getCategoriasServicios();

    @Query(value = "select DISTINCT new com.ndl.erp.dto.CategoryDTO(c.categoria1,c.descripcion1)  from Cabys c where c.categoria1 in ('0','1','2','3','4')"
    )
    List<CategoryDTO> getCategoriasProductos();


    @Query(value = "select c  from Cabys c where c.categoria1 in ('0','1','2','3','4')" +
            " and (c.codigoBien like %?1% or c.descripcion like %?1%)"
    )
    List<Cabys> getProductosByFilter(String filtro);
}
