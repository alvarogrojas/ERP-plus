package com.ndl.erp.services;

import com.ndl.erp.constants.FamiliaConstants;
import com.ndl.erp.domain.*;
import com.ndl.erp.dto.*;
import com.ndl.erp.exceptions.GeneralInventoryException;
import com.ndl.erp.repository.*;
import com.ndl.erp.services.bodega.BodegaManagerService;
import com.ndl.erp.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import static com.ndl.erp.constants.BodegaConstants.*;
import static com.ndl.erp.constants.ProductoConstants.*;

@Component
public class ProductoService {

    @Autowired
    InventarioBodegaRepository inventarioBodegaRepository;

    @Autowired
    InventarioRepository inventarioRepository;

    @Autowired
    ProductoRepository productoRepository;

    @Autowired
    BodegaRepository bodegaRepository;

    @Autowired
    ProductoCategoriaRepository productoCategoriaRepository;

    @Autowired
    BodegaManagerService bodegaManagerService;

    @Autowired
    FabricanteRepository fabricanteRepository;

    @Autowired
    UnidadMedidaRepository unidadMedidaRepository;

    @Autowired
    MultiplicadorRepository multiplicadorRepository;

    @Autowired
    UserServiceImpl userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private FamiliaRepository familiaRepository;

    @Autowired
    private DescuentosRepository descuentosRepository;


    @Transactional(rollbackFor = {Exception.class})
    public  void inicializarInventarioBodegaProducto(Producto producto) throws Exception{

        List<Bodega> bodegaList = this.bodegaRepository.getBodegaByStatusAndNonExistingProduct(producto.getId(), ESTADO_BODEGA_ACTIVA);


        if (producto == null){
            throw  new GeneralInventoryException("El producto suministrado es nulo!");
        }

        if (bodegaList == null){
            throw  new GeneralInventoryException("No se encontraron bodegas activas para inicializar productos!");
        }

        //Para cada bodega que carece del producto crearlo en InventarioBodega
        for (Bodega b : bodegaList){
          bodegaManagerService.nuevoInventarioBodega(b, producto, 0d);
        }

    }

    public ProductoDTO getProducto(Integer id) {
        ProductoDTO productoDTO = this.getProducto();
        Producto p = this.productoRepository.findProductoById(id);

        if (p==null) {
            return productoDTO;
        }
        productoDTO.setCurrent(p);
        return productoDTO;
    }

    public ProductoDTO getProductoData(Integer id) {

        ProductoDTO dto  = this.getProducto();

        Producto p = this.productoRepository.findProductoById(id);
        dto.setCurrent(p);

        return dto;
    }


    public ProductoDTO getProducto() {

       //List<ProductoCategoria>  productoCategoriaList = this.productoCategoriaRepository.findAll();
       /*List<Fabricante>  fabricanteList =  this.fabricanteRepository.findAll();*/
        List<ProductoCategoria> productoCategoriaList = new ArrayList<>();
        List<Fabricante> fabricanteList = new ArrayList<>();
        Fabricante f = new Fabricante();
        f.setNombre("");
        f.setEstado("Activo");
        fabricanteList.add(f);
       List<UnidadMedida> unidadMedidaList =  this.unidadMedidaRepository.findAll();
       List<Multiplicador> multiplicadorList = new ArrayList<>();
//       List<Multiplicador> multiplicadorList = this.multiplicadorRepository.findAll();
       List<String> estadosList = this.getProductoEstados();
       List<String> tipos = this.getProductoTipos();
//       List<User> responsablesList = this.userRepository.findUsersActive();
        List<User> responsablesList = new ArrayList<>();
       List<String> manejoBodegaList = this.getManejosBodega();
       Producto p = new Producto();
       this.setAuditoriaCreacionProducto(p);
       this.setAuditoriaModificacionProducto(p);
       p.setEstado(PRODUCTO_ESTADO_ACTIVO);

       ProductoDTO productoDTO = new ProductoDTO();
       productoDTO.setCurrent(p);
       productoDTO.setProductoCategoriaList(productoCategoriaList);
       productoDTO.setFabricanteList(fabricanteList);
       productoDTO.setUnidadMedidaList(unidadMedidaList);
       productoDTO.setMultiplicadorList(multiplicadorList);
       productoDTO.setEstadoList(estadosList);
       productoDTO.setTipos(tipos);

//       productoDTO.setFamilias(this.familiaRepository.getFamiliaByEstado(
//                FamiliaConstants.FAMILIA_ESTADO_ACTIVO));
        List<Familia> familias = new ArrayList<>();
        Familia f1 = new Familia();
        f1.setNombre("");
        f1.setEstado("Activo");
        f1.setDetails(new ArrayList<>());
        familias.add(f1);
       productoDTO.setFamilias(familias);
       productoDTO.setResponsableList(responsablesList);
       productoDTO.setManejoBodegaList(manejoBodegaList);

        return productoDTO;
    }


    public CatalogoProductosDTO getCatalogoProductos(String filter, String estado, Integer pageNumber,
                                                     Integer pageSize, String sortDirection,
                                                     String sortField) {
        CatalogoProductosDTO c = new CatalogoProductosDTO();
        List<String> estados = this.getProductoEstados();
        c.setEstados(estados);

        c.setPage(this.productoRepository.getProductoByFilterAndEstado(filter, estado,
                createPageable(pageNumber, pageSize, sortDirection, sortField)));


        c.setTotal(this.productoRepository.countProductoByFilterAndEstado(filter, estado));
        if (c.getTotal()>0) {
            c.setPagesTotal(c.getTotal() /pageSize);
        } else {
            c.setPagesTotal(0);
        }

        return c;
    }

    public ProductosDTO getProductosListDTO(Integer bodegaId, String filter, Boolean esEntrada) {
        ProductosDTO r = new ProductosDTO();

        if (esEntrada == null || esEntrada == false)
           r.setInventarios(this.inventarioRepository.findByBodegaAndProductoSalida(bodegaId, filter));
        else if (esEntrada == true) {
           r.setProductos(this.inventarioRepository.findByProductoAndCodigoEntrada(filter));
        }
        return r;
    }

    public ProductosDTO getProductosListDTO(String filter) {
        ProductosDTO r = new ProductosDTO();
        r.setProductos(this.productoRepository.findProductoByFilter(filter));

        return r;
    }

    public InventarioDTO getInventarioDTO(String filter, Integer bodegaId, Integer productoId) {
        InventarioDTO inventario = new InventarioDTO();

        if (bodegaId==null || bodegaId==0) {
            inventario.setInventarios(this.inventarioRepository.findByBarcode(filter));
        } else {
            inventario.setInventarios(this.inventarioRepository.findByBarcodeAndBodega(filter, bodegaId));

        }
//            inventario.setInventarios(this.inventarioRepository.findByBarcodeAndBodegaAndProducto(filter, bodegaId, productoId));
        return inventario;
    }



     public void setAuditoriaCreacionProducto(Producto p){
        p.setFechaUltimoCambio(new Date(DateUtil.getCurrentCalendar().getTime().getTime()));
        p.setUserUltimoCambio(this.userService.getCurrentLoggedUser());
     }

    public void setAuditoriaModificacionProducto(Producto p){
        p.setFechaUltimoCambio(new Date(DateUtil.getCurrentCalendar().getTime().getTime()));
        p.setUserUltimoCambio(this.userService.getCurrentLoggedUser());
    }

    boolean validarCodigoProductoDuplicado(Producto p){
       Boolean existeCodigo = false;
       Integer cont = 0;
       cont = this.productoRepository.countProductoByCodigo(p.getCodigo());
       if (cont > 0) {
           existeCodigo = true;
       }
       return existeCodigo;
    }

    @Transactional(rollbackFor = {Exception.class})
    public synchronized  Producto save(Producto p) throws Exception {
      boolean nuevoProducto = false;
        if (p.getId() == null) {
            if (validarCodigoProductoDuplicado(p))
                throw new GeneralInventoryException("El código del producto ya existe!");
            this.setAuditoriaCreacionProducto(p);
            nuevoProducto = true;
        } else{
            this.setAuditoriaModificacionProducto(p);
        }

        this.productoRepository.save(p);
        if (nuevoProducto){
            this.inicializarInventarioBodegaProducto(p);
        }

        return p;
    }


    public List<String> getProductoEstados(){
        List<String> estadosList = new ArrayList<>(0);
        estadosList.add(PRODUCTO_ESTADO_ACTIVO);
        estadosList.add(PRODUCTO_ESTADO_INACTIVO);
        return estadosList;
    }

    public List<String> getProductoTipos(){
        List<String> list = new ArrayList<>(0);
        list.add(PRODUCTO_TIPO_SIMPLE);
        list.add(PRODUCTO_TIPO_VARIANT);
        return list;
    }
    public List<String> getManejosBodega(){
        List<String> manejoList = new ArrayList<>(0);
        manejoList.add(MANEJO_BODEGA_SIMPLE);
        manejoList.add(MANEJO_BODEGA_PEPS);
        manejoList.add(MANEJO_BODEGA_UEPS);
        return manejoList;
    }

    private PageRequest createPageable(Integer pageNumber, Integer pageSize, String direction, String byField) {

        return PageRequest.of(pageNumber, pageSize, direction==null || direction.equals("asc")? Sort.Direction.ASC: Sort.Direction.DESC, byField);
    }



    public DescuentosDTO getDescuentosDTO(Integer productoId) {
        DescuentosDTO r = new DescuentosDTO();
        if (r.getDescuentos()==null || r.getDescuentos().size()==0) {
            this.initDefaultDiscount(r);
        }
        List<Descuentos> ld = this.productoRepository.getDescuentosCategoriaByProducto(productoId);
        if (ld!=null && ld.size()>0) {
            r.getDescuentos().addAll(ld);
        }
        return r;
    }

    public DescuentosDTO getDescuentosByCategoriaDTO(Integer productoId) {
        DescuentosDTO r = new DescuentosDTO();
        r.setDescuentos(this.productoRepository.getDescuentosCategoriaByProducto(productoId));
        if (r.getDescuentos()==null || r.getDescuentos().size()==0) {
            this.initDefaultDiscount(r);
        }
        return r;
    }

//    private void initDefaultDiscount(DescuentosDTO r) {
//
//        CategoriaDescuentos cd = new CategoriaDescuentos();
//        cd.setNombreDescuento("Sin Descuento");
//        cd.setPorcentajeDescuento(0d);
//        List<CategoriaDescuentos> descuentos = new ArrayList<>();
//        descuentos.add(cd);
//       r.setDescuentos(descuentos);
//
//    }

    private void initDefaultDiscount(DescuentosDTO r) {



        List<Descuentos> descuentos =this.descuentosRepository.getSinDescuento();
        if ( descuentos==null || descuentos.size()==0) {
             Descuentos cd = new Descuentos();
            cd.setNombreDescuento("Sin Descuento");

            cd.setPorcentajeDescuento(0d);
        }

       r.setDescuentos(descuentos);

    }

}
