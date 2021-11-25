package com.ndl.erp.repository;

import com.ndl.erp.domain.AtributoDetalle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

@Component
public interface AtributoDetalleRepository extends JpaRepository<AtributoDetalle, Integer> {
    @Query("Select  ad from AtributoDetalle ad where ad.atributo.id = ?1 and ad.nombre = ?2")
    AtributoDetalle findAtributoDetalleByNombreAndAtributo( Integer atributoId, String valorAtributo);
}
