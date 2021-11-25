package com.ndl.erp.dto;

import com.ndl.erp.domain.Bodega;
import com.ndl.erp.domain.Cabys;
import com.ndl.erp.domain.InventarioItem;
import com.ndl.erp.domain.ServiceCabys;
import org.springframework.data.domain.Page;

import java.util.List;
//import org.springframework.data.domain.Page;


public class CabysFilteredDTO {



    private List<Cabys> cabys;


    public List<Cabys> getCabys() {
        return cabys;
    }

    public void setCabys(List<Cabys> cabys) {
        this.cabys = cabys;
    }
}
