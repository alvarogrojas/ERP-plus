package com.ndl.erp.dto;

import com.ndl.erp.domain.Fabricante;
import org.springframework.data.domain.Page;

import java.util.List;

public class FabricantesListDTO {

    private List<Fabricante> fabricantes;

    public List<Fabricante> getFabricantes() {
        return fabricantes;
    }

    public void setFabricantes(List<Fabricante> fabricantes) {
        this.fabricantes = fabricantes;
    }
}