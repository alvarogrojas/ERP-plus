package com.ndl.erp.repository;

import com.ndl.erp.domain.Atributo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

@Component
public interface AtributoRepository extends JpaRepository<Atributo, Integer> {

    @Query(value = "Select a from Atributo a where a.familia.id =?1 and a.nombre = ?2 ")
    Atributo findAtributoByNombreAndFamlia(Integer familiaId, String Nombre);

}
