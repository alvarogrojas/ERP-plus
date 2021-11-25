package com.ndl.erp.dto;

import com.ndl.erp.domain.*;
import org.springframework.data.domain.Page;

import java.util.List;
//import org.springframework.data.domain.Page;


public class CabysDTO {

    private List<CategoryDTO> categoriasServicios;
    private List<CategoryDTO> categoriasProductos;
    private List<CategoryDTO> todasCategorias;
    private List<String> types;

    private ServiceCabys serviceCabys;
    private InventarioItem productoCabys;
    private Integer total;

    private Page<Cabys> cabysItems;

    private Integer pagesTotal;
    private List<Bodega> bodegas;

    public CabysDTO() {
        this.serviceCabys = new ServiceCabys();
        this.productoCabys = new InventarioItem();
    }


    public List<CategoryDTO> getCategoriasServicios() {
        return categoriasServicios;
    }

    public void setCategoriasServicios(List<CategoryDTO> categoriasServicios) {
        this.categoriasServicios = categoriasServicios;
    }

    public List<CategoryDTO> getCategoriasProductos() {
        return categoriasProductos;
    }

    public void setCategoriasProductos(List<CategoryDTO> categoriasProductos) {
        this.categoriasProductos = categoriasProductos;
    }

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }

    public ServiceCabys getServiceCabys() {
        return serviceCabys;
    }

    public void setServiceCabys(ServiceCabys serviceCabys) {
        this.serviceCabys = serviceCabys;
    }

    public InventarioItem getProductoCabys() {
        return productoCabys;
    }

    public void setProductoCabys(InventarioItem productoCabys) {
        this.productoCabys = productoCabys;
    }

//    public Page<Cabys> getCabys() {
//        return cabys;
//    }
//
//    public void setCabys(Page<Cabys> cabys) {
//        this.cabys = cabys;
//    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getTotal() {
        return total;
    }

    public List<CategoryDTO> getTodasCategorias() {
        return todasCategorias;
    }

    public void setTodasCategorias(List<CategoryDTO> todasCategorias) {
        this.todasCategorias = todasCategorias;
    }

    public void setCabysItems(Page<Cabys> pc) {
        this.cabysItems = pc;
    }

    public Page<Cabys> getCabysItems() {
        return cabysItems;
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
        return this.bodegas;
    }


}
