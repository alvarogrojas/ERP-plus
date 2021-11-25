package com.ndl.erp.dto;

import com.ndl.erp.domain.Bodega;
import com.ndl.erp.domain.Cabys;
import com.ndl.erp.domain.InventarioItem;
import com.ndl.erp.domain.ServiceCabys;
import org.springframework.data.domain.Page;

import java.util.List;
//import org.springframework.data.domain.Page;


public class CabysesDTO {


    private Page<Cabys> cabys;

    private Integer total;

    private Integer pagesTotal;
    private List<Bodega> bodegas;

    public Page<Cabys> getCabys() {
        return cabys;
    }

    public void setCabys(Page<Cabys> cabys) {
        this.cabys = cabys;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getPagesTotal() {
        return pagesTotal;
    }

    public void setPagesTotal(Integer pagesTotal) {
        this.pagesTotal = pagesTotal;
    }

    public void setBodegas(List<Bodega> b) {
        this.bodegas = b;
    }

    public List<Bodega> getBodegas() {
        return bodegas;
    }
}
