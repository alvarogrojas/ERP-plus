package com.ndl.erp.services;

import com.ndl.erp.constants.ProductoCategoriaConstants;
import com.ndl.erp.domain.*;
import com.ndl.erp.dto.*;
import com.ndl.erp.exceptions.GeneralInventoryException;
import com.ndl.erp.repository.CategoriaRepository;
import com.ndl.erp.repository.ProductoCategoriaRepository;
import com.ndl.erp.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.ndl.erp.constants.CategoriaConstants.CATEGORIA_ACTIVA;
import static com.ndl.erp.constants.CategoriaConstants.CATEGORIA_INACTIVA;

@Component
public class CategoriaService {

    @Autowired
    CategoriaRepository categoriaRepository;

    @Autowired
    UserServiceImpl userService;

    @Autowired
    private ProductoCategoriaRepository productoCategoriaRepository;

    @Transactional(rollbackFor = {Exception.class})
    public synchronized Categoria save(Categoria c) throws Exception {

        if (c.getId() == null) {
           this.setAuditoriaCreacionCategoria(c);

        } else{
            this.setAuditoriaModificacionCategoria(c);
        }

        this.categoriaRepository.save(c);


        return c;
    }

    public void setAuditoriaCreacionCategoria(Categoria c){
        c.setFechaUltimoCambio(new Date(DateUtil.getCurrentCalendar().getTime().getTime()));
        c.setUltimoCambioPor(this.userService.getCurrentLoggedUser());
        c.setFechaIngreso(new Date(DateUtil.getCurrentCalendar().getTime().getTime()));
        c.setUserIngresadoPor(this.userService.getCurrentLoggedUser());

    }

    public void setAuditoriaModificacionCategoria(Categoria c){
        c.setFechaUltimoCambio(new Date(DateUtil.getCurrentCalendar().getTime().getTime()));
        c.setUltimoCambioPor(this.userService.getCurrentLoggedUser());
    }

    public CategoriaDTO getCategoriaData(Integer id) {

        CategoriaDTO dto = this.getCategoria();

        Categoria p = this.categoriaRepository.findCategoriaById(id);
        dto.setCurrent(p);

        return dto;
    }

    public CategoriaDTO getCategoria() {
        Categoria c = new Categoria();

        this.setAuditoriaCreacionCategoria(c);
        this.setAuditoriaModificacionCategoria(c);
        c.setEstado(CATEGORIA_ACTIVA);

        CategoriaDTO categoriaDTO = new CategoriaDTO();
        categoriaDTO.setCurrent(c);
        List<String> estados = this.getCategoriaEstados();
        categoriaDTO.setEstados(estados);

        return categoriaDTO;
    }

    public CategoriasDTO getCategorias(String filter, String estado, Integer pageNumber,
                                       Integer pageSize, String sortDirection,
                                       String sortField) {
        CategoriasDTO c = new CategoriasDTO();
        List<String> estados = this.getCategoriaEstados();
        c.setEstados(estados);

        c.setPage(this.categoriaRepository.findAllCategoriaByNameAndEstado(filter, estado,
                createPageable(pageNumber, pageSize, sortDirection, sortField)));


        c.setTotal(this.categoriaRepository.countAllCategoriaByNameAndEstado(filter, estado));
        if (c.getTotal()>0) {
            c.setPagesTotal(c.getTotal() /pageSize);
        } else {
            c.setPagesTotal(0);
        }

        return c;
    }

    public List<String> getCategoriaEstados(){
        List <String> estados = new ArrayList<>(0);
        estados.add(CATEGORIA_ACTIVA);
        estados.add(CATEGORIA_INACTIVA);
        return estados;
    }

    private PageRequest createPageable(Integer pageNumber, Integer pageSize, String direction, String byField) {

        return PageRequest.of(pageNumber, pageSize, direction==null || direction.equals("asc")? Sort.Direction.ASC: Sort.Direction.DESC, byField);
    }


    public ProductosCategoriaDTO saveProductosCategorias(ProductosCategoriaDTO c) {
        if (c.getCategoria()==null || c.getProductos()==null || c.getProductos().size()==0) {
            throw new GeneralInventoryException("La categoria y productos son requeridos");
        }

        User u = this.userService.getCurrentLoggedUser();

        ProductosCategoriaDTO result = new ProductosCategoriaDTO();
        result.setProductos(new ArrayList<Producto>());
        ProductoCategoria current = null;
        for (Producto p: c.getProductos()) {
            current = null;
            try {
                current = this.productoCategoriaRepository.getProductoCategoria(p.getId(), c.getCategoria().getId());

            } catch (Exception ex) {
                ex.printStackTrace();
            }
            if (current ==null) {
                ProductoCategoria pc = new ProductoCategoria();
                pc.setCategoria(c.getCategoria());
                pc.setEstado(ProductoCategoriaConstants.PRODUCTO_CATEGORIA_ACTIVO);
                pc.setFechaIngreso(new Date());
                pc.setUserIngresadoPor(u);
                pc.setUserUltimoCambioPor(u);
                pc.setFechaUltimoCambio(new Date());
                pc.setProducto(p);
                this.productoCategoriaRepository.save(pc);
                result.getProductos().add(p);
            }

        }
        return result;
    }

    public ProductoCategoriaListDTO getProductosCategoria(Integer id) {
        ProductoCategoriaListDTO result = new ProductoCategoriaListDTO();
        result.setProductoList(this.productoCategoriaRepository.getProductosCategorias(id));
        return result;
    }

    public Result deleteProductoCategoria(Integer productoId, Integer categoriaId) {

        Result r = null;

        try {


            try {
                ProductoCategoria pc = this.productoCategoriaRepository.getProductoCategoria(productoId,categoriaId);
                if (pc != null) {
                    this.productoCategoriaRepository.delete(pc);
                }
            } catch (Exception e) {
                //e.printStackTrace();
                throw new GeneralInventoryException("Error borrando el registro del producto categoria. " + e.getMessage());
            }


            r = new Result(Result.RESULT_CODE.DELETE, "Se elimino el registro del producto en la categoria");
        } catch (Exception e) {
            r = new Result(Result.RESULT_CODE.ERROR, "Error al borrar: " + e.getMessage());
        }
        return r;
    }


}
