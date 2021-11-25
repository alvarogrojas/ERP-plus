package com.ndl.erp.repository;

import com.ndl.erp.domain.CategoriaDescuentos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

    @Component
    public interface CategoriaDescuentosRepository extends JpaRepository<CategoriaDescuentos, Integer> {



    }
