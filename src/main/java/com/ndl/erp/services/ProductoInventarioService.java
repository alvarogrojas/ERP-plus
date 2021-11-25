package com.ndl.erp.services;

import com.ndl.erp.domain.*;
import com.ndl.erp.dto.InitInventarioDTO;
import com.ndl.erp.dto.ProductoInventarioDTO;
import com.ndl.erp.dto.ProductoInventarioItemDTO;
import com.ndl.erp.exceptions.GeneralInventoryException;
import com.ndl.erp.repository.*;
import com.ndl.erp.services.bodega.BodegaManagerService;
import com.ndl.erp.services.bodega.ParseArchivoProducto;
import com.ndl.erp.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;

import static com.ndl.erp.constants.AtributoConstants.ATRIBUTO_ESTADO_ACTIVO;
import static com.ndl.erp.constants.AtributoDetalleConstants.ATRIBUTO_DETALLE_ESTADO_ACTIVO;
import static com.ndl.erp.constants.FabricanteConstants.FABRICANTE_ESTADO_ACTIVO;
import static com.ndl.erp.constants.FamiliaConstants.FAMILIA_ESTADO_ACTIVO;
import static com.ndl.erp.constants.InitInventarioConstants.INIT_INVENTARIO_ESTADO_EDICION;
import static com.ndl.erp.constants.ParseCargaArchivoConstants.*;
import static com.ndl.erp.constants.ProductoConstants.*;
import static com.ndl.erp.constants.ProductoFamiliaAtributoConstants.PRODUCTO_FAMILIA_ATRIBUTO_ACTIVO;
import static com.ndl.erp.constants.UnidadMedidaConstants.UNIDAD_MEDIDA_ESTADO_ACTIVO;

@Component
public class ProductoInventarioService {

    @Autowired
    ParseArchivoProducto parseArchivoProducto;

    @Autowired
    InitInventarioRepository initInventarioRepository;

    @Autowired
    InitInventarioDetalleRepository initInventarioDetalleRepository;

    @Autowired
    UserServiceImpl userService;

    @Autowired
    UnidadMedidaRepository unidadMedidaRepository;

    @Autowired
    FamiliaRepository familiaRepository;

    @Autowired
    AtributoRepository atributoRepository;

    @Autowired
    AtributoDetalleRepository atributoDetalleRepository;

    @Autowired
    InitInventarioAtributoRepository initInventarioAtributoRepository;

    @Autowired
    FabricanteRepository fabricanteRepository;

    @Autowired
    ProductoRepository productoRepository;

    @Autowired
    ProductoFamiliaAtributoRepository productoFamiliaAtributoRepository;

    @Autowired
    BodegaManagerService bodegaManagerService;

    @Autowired
    GeneralParameterService generalParameterService;

    @Autowired
    InventarioRepository inventarioRepository;

    @Autowired
    BodegaRepository bodegaRepository;



    private final Path rootConfirmationLocation = Paths.get("content");
    private Path fileStorageLocation;

    public ProductoInventarioDTO loadDataFromFile(MultipartFile file) throws Exception {

        return storeConfirmation(file);
    }

    private ProductoInventarioDTO storeConfirmation(MultipartFile file) throws Exception {

        String contentType = file.getContentType();
        String fullFileName = "";

        try {
            try {
                if (!Files.exists(rootConfirmationLocation)) {
                    Files.createDirectories(rootConfirmationLocation);

                }
            } catch (IOException e) {
                throw new GeneralInventoryException("Could not initialize storage!");
            }

            //String file

            try {
                if (!Files.exists(rootConfirmationLocation)) {
                    Files.createDirectories(rootConfirmationLocation);

                }
            } catch (IOException e) {
                throw new GeneralInventoryException("Could not initialize storage!");
            }
            Path p = this.rootConfirmationLocation.resolve(file.getOriginalFilename());
            if (Files.exists(p)) {
                Files.delete(p);
            }

            Files.copy(file.getInputStream(), this.rootConfirmationLocation.resolve(file.getOriginalFilename()));
            fullFileName = this.rootConfirmationLocation.toString() + File.separator + file.getOriginalFilename();

//

        } catch (IOException e) {
            throw new GeneralInventoryException("Could not delete or copy the file");
        }

        return parseArchivoProductoInventario(fullFileName, contentType);
    }


    private ProductoInventarioDTO parseArchivoProductoInventario(String fullFileName, String contentType) throws Exception {

        Date date = new Date(DateUtil.getCurrentCalendar().getTime().getTime());
        ProductoInventarioDTO productoInventarioDTO = null;


        productoInventarioDTO = this.parseArchivoProducto.parserCargaArchivoCSV(fullFileName);



        for(int i=0;i<productoInventarioDTO.getAttributesMax()/2;i++) {
            productoInventarioDTO.getAttributesColumns().add("Atributo " + i);
            productoInventarioDTO.getAttributesColumns().add("Valor " + i);
        }


        return productoInventarioDTO;

    }

    public InitInventarioDTO getInits() {
        InitInventarioDTO initInventarioDTO = new InitInventarioDTO();
        initInventarioDTO.setList(this.initInventarioRepository.findAllInitInventario());
        return initInventarioDTO;
    }

    public void setAuditoriamodificacion(InitInventario initInventario) {
        initInventario.setFechaInit((new java.sql.Date(DateUtil.getCurrentCalendar().getTime().getTime())));
        initInventario.setCreadaPorUser(this.userService.getCurrentLoggedUser());
        initInventario.setActualizadaPorUser(this.userService.getCurrentLoggedUser());
    }

    public void setAuditoriaCreacion(InitInventario initInventario) {
      initInventario.setCreadaPorUser(this.userService.getCurrentLoggedUser());
      initInventario.setFechaCreacion((new java.sql.Date(DateUtil.getCurrentCalendar().getTime().getTime())));
    }

   
    //Crea la unidad de medida si no existe
    public UnidadMedida crearUnidadMedida(String nombreUnidad, String simbolo) throws Exception{

        if (this.parseArchivoProducto.validarStringNulo(nombreUnidad) || parseArchivoProducto.validarStringNulo(simbolo)){
            throw new GeneralInventoryException("No se pemite unidad o simbolo de la unidad en nulo");
        }

        UnidadMedida unidadMedida = this.unidadMedidaRepository.findUnidadMedidadByNombre(nombreUnidad);
        if (unidadMedida == null){
           unidadMedida = new UnidadMedida();
           unidadMedida.setEstado(UNIDAD_MEDIDA_ESTADO_ACTIVO);
           unidadMedida.setNombre(nombreUnidad);
           unidadMedida.setSimbolo(simbolo);
           this.unidadMedidaRepository.save(unidadMedida);
        }
        return unidadMedida;
    }

    //Crea la familia  si no existe
    @Transactional(rollbackFor = {Exception.class})
    public Familia crearFamilia(String nombreFamilia) throws Exception{

        if (parseArchivoProducto.validarStringNulo(nombreFamilia)){
            throw new GeneralInventoryException("No se pemite el nombre de la familia nulo");
        }
        if (nombreFamilia.length()>100) {
            nombreFamilia = nombreFamilia.substring(0,99);
        }

        Familia familia = this.familiaRepository.findFamiliaByNombre(nombreFamilia);
        try {

        if (familia == null){
            familia = new Familia();
            familia.setEstado(FAMILIA_ESTADO_ACTIVO);
            familia.setNombre(nombreFamilia);
            this.familiaRepository.save(familia);
        }
        } catch(RuntimeException e1) {
            e1.printStackTrace();
            throw new GeneralInventoryException("Error insertando nueva familia " + nombreFamilia + " " + e1.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new GeneralInventoryException("Error insertando nueva familia " + nombreFamilia + " " + e.getMessage());
        }
        return familia;
    }

    //Crea un  atributo  si no existe
    @Transactional(rollbackFor = {Exception.class})
    public Atributo crearAtributo(Familia familia, String nombreAtributo) throws Exception{

        if (parseArchivoProducto.validarStringNulo(nombreAtributo)){
            throw new GeneralInventoryException("No se pemite el nombre del atributo nulo");
        }

        if (nombreAtributo.length()>100) {
            nombreAtributo = nombreAtributo.substring(0,99);
        }

        Atributo atributo = this.atributoRepository.findAtributoByNombreAndFamlia(familia.getId(), nombreAtributo);
        try {
            if (atributo == null){
                atributo = new Atributo();
                atributo.setEstado(ATRIBUTO_ESTADO_ACTIVO);
                atributo.setFamilia(familia);
                atributo.setNombre(nombreAtributo);
                this.atributoRepository.save(atributo);
            }
        } catch(RuntimeException e1) {
            e1.printStackTrace();
            throw new GeneralInventoryException("Error insertando nueva atributo " + nombreAtributo + " " + e1.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new GeneralInventoryException("Error insertando nueva atributo " + nombreAtributo + " " + e.getMessage());
        }

        return atributo;
    }


    //Crea un  fabricante si no existe
    @Transactional(rollbackFor = {Exception.class})
    public Fabricante crearFabricante(String nombreFabricante) throws Exception{

        if (parseArchivoProducto.validarStringNulo(nombreFabricante)){
            throw new GeneralInventoryException("No se pemite el nombre del fabricante nulo");
        }

        if (nombreFabricante.length()>50) {
            nombreFabricante = nombreFabricante.substring(0,49);
        }

        Fabricante fabricante = this.fabricanteRepository.findFabricanteByNombre(nombreFabricante);
        try {
            if (fabricante == null){
                fabricante = new Fabricante();
                fabricante.setEstado(FABRICANTE_ESTADO_ACTIVO);
                fabricante.setNombre(nombreFabricante);
                this.fabricanteRepository.save(fabricante);
            }
        } catch(RuntimeException e1) {
            e1.printStackTrace();
            throw new GeneralInventoryException("Error insertando nueva familia " + nombreFabricante + " " + e1.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new GeneralInventoryException("Error insertando nueva familia " + nombreFabricante + " " + e.getMessage());
        }
        return fabricante;
    }

    @Transactional(rollbackFor = {Exception.class})
    public AtributoDetalle crearAtributoDetalle(Atributo atributo, String valorAtributo) throws Exception{


        if (parseArchivoProducto.validarStringNulo(valorAtributo)){
            throw new GeneralInventoryException("No se permite el nombre del atributo nulo");
        }

        if (valorAtributo.length()>100) {
            valorAtributo = valorAtributo.substring(0,99);
        }

        AtributoDetalle atributoDetalle = this.atributoDetalleRepository.findAtributoDetalleByNombreAndAtributo(atributo.getId(), valorAtributo);
        try {
            if (atributoDetalle == null) {
                atributoDetalle = new AtributoDetalle();
                atributoDetalle.setEstado(ATRIBUTO_DETALLE_ESTADO_ACTIVO);
                atributoDetalle.setAtributo(atributo);
                atributoDetalle.setNombre(valorAtributo);
                this.atributoDetalleRepository.save(atributoDetalle);
            }
        } catch(RuntimeException e1) {
            e1.printStackTrace();
            throw new GeneralInventoryException("Error insertando nueva detalle atributo " + atributo + " " + e1.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new GeneralInventoryException("Error insertando nueva detalle atributo " + atributo + " " + e.getMessage());
        }
        return atributoDetalle;
    }

    @Transactional(rollbackFor = {Exception.class})
    public void crearInitInventarioAtributo(String nombre, String valor, InitInventarioDetalle initInventarioDetalle) throws Exception{

        if (parseArchivoProducto.validarStringNulo(nombre)){
            throw new GeneralInventoryException("No se pemite el nombre del atributo nulo");
        }

        if (parseArchivoProducto.validarStringNulo(valor)){
            throw new GeneralInventoryException("No se pemite el valor del atributo nulo");
        }

        InitInventarioAtributo initInventarioAtributo;
        initInventarioAtributo = new InitInventarioAtributo();
        initInventarioAtributo.setInitInventarioDetalle(initInventarioDetalle);
        initInventarioAtributo.setNombre(nombre);
        initInventarioAtributo.setValor(valor);
        this.initInventarioAtributoRepository.save(initInventarioAtributo);


    }


    /*Procesa un item de tipo Variable y lo devuelve como un InitInventarioDetalle*/
    @Transactional(rollbackFor = {Exception.class})
    public Familia procesarItemVariable(ProductoInventarioItemDTO item, InitInventario initInventario) throws Exception {

       InitInventarioDetalle initInventarioDetalle = new InitInventarioDetalle();
       initInventarioDetalle.setInitInventario(initInventario);
       initInventarioDetalle.setTipo(item.getTipo());
       Familia familia =  crearFamilia(item.getNombre());
       initInventarioDetalle.setNombre(familia.getNombre());
       initInventarioDetalle.setPrecioCosto(item.getPrecioCosto());
       initInventarioDetalle.setSku(item.getSku());
       familia.setSku(item.getSku());
       this.initInventarioDetalleRepository.save(initInventarioDetalle);
       initInventario.getDetalles().add(initInventarioDetalle);
      //Procesar los atributos
       List<String> atributos = item.getAtributos();
      ListIterator <String> it = null;
      it = atributos.listIterator();

      //Procesar la lista de atributos
      while(it.hasNext()) {
          procesarAtributos(it, familia, initInventarioDetalle);

      }

      return familia;
    }



    /*Procesa un item de tipo Variation o Simple y  lo devuelve como un InitInventarioDetalle*/
    @Transactional(rollbackFor = {Exception.class})
    public void procesarItemVariation(ProductoInventarioItemDTO item, Familia familiaActual,  InitInventario initInventario, int count) throws Exception {

        //Obtener de parametros generales la estrategia para actualizar el producto
        GeneralParameter generalParameterImportProducto = this.generalParameterService.getByCode(PARAMETER_IMPORT_ACTUALIZA_PRODUCTO);
        GeneralParameter generalParameterImportInventario = this.generalParameterService.getByCode(PARAMETER_IMPORT_ACTUALIZA_INVENTARIO);
        GeneralParameter generalParameterImportBodega = this.generalParameterService.getByCode(PARAMETER_IMPORT_BODEGA);



        if (generalParameterImportProducto == null) {
            throw new GeneralInventoryException("Error carga de  productos: Falta definir el parametro " + PARAMETER_IMPORT_ACTUALIZA_PRODUCTO);
        }

        if (generalParameterImportInventario == null) {
            throw new GeneralInventoryException("Error carga de  inventario: Falta definir el parametro " + PARAMETER_IMPORT_ACTUALIZA_INVENTARIO);
        }

        if (generalParameterImportBodega == null) {
            throw new GeneralInventoryException("Error carga de  inventario: la bodega de importación no ha sido definida  " + PARAMETER_IMPORT_BODEGA);
        }

        Bodega bodega = this.bodegaRepository.getById(generalParameterImportBodega.getIntVal());

        if (bodega  == null){
            throw new GeneralInventoryException("Error carga de  inventario: la bodega de importación no ha sido encontrada " + PARAMETER_IMPORT_BODEGA);
        }


        InitInventarioDetalle initInventarioDetalle = new InitInventarioDetalle();
        initInventarioDetalle.setInitInventario(initInventario);
        initInventarioDetalle.setTipo(item.getTipo());
        initInventarioDetalle.setNombre(item.getNombre().replaceAll(";", ","));
        initInventarioDetalle.setCodigo(item.getCodigo());
        initInventarioDetalle.setSku(String.format(item.getSku() + count));
        UnidadMedida unidadMedida = this.crearUnidadMedida(item.getUnidadMedida(), "??");
        initInventarioDetalle.setUnidadMedida(unidadMedida.getNombre());
        initInventarioDetalle.setCodigoCabys(item.getCodigoCabys());
        initInventarioDetalle.setUnidadMedida(unidadMedida.getNombre());
        initInventarioDetalle.setCantidadInventario(item.getInventario());
        initInventarioDetalle.setCategorias(item.getCategoria());
        initInventarioDetalle.setPrecioList(item.getPrecioLista());
        initInventarioDetalle.setPrecioCosto(item.getPrecioCosto());
        initInventarioDetalle.setMargenUtilidad(item.getMargenUtilidad());
        initInventarioDetalle.setFabricante(item.getFabricante());
        initInventarioDetalle.setModeloCatalogo(item.getModeloCatalogo());

        //Si el producto existe lo reutiliza y no lo modifica
        Producto  producto = this.productoRepository.findProductoByCodigo(item.getCodigo());

        //Validaciones para campo de auditoria cantidadIncrementoNeto
        Inventario inventario = null;
        if (producto != null) {
            inventario = this.inventarioRepository.findByBodegaAndBarcodeAndProducto(bodega.getId(), producto.getId(), item.getCodigo());
        }

        if (generalParameterImportInventario.getVal().equals(IMPORT_REEMPLAZAR_INVENTARIO)){
            if (inventario == null) {
                initInventarioDetalle.setCantidadIncrementoNeto(item.getInventario());
            }else {
                if (item.getInventario() >= inventario.getCantidadEntrada()) {
                   initInventarioDetalle.setCantidadIncrementoNeto(item.getInventario() - inventario.getCantidadEntrada());
                } else{
                    initInventarioDetalle.setCantidadIncrementoNeto(0d);
                }
            }
        } else if(generalParameterImportInventario.getVal().equals(IMPORT_ACUMULAR_INVENTARIO)){
            initInventarioDetalle.setCantidadIncrementoNeto(item.getInventario());
        }

        try {
            if (producto == null) {
                producto = new Producto();
                producto.setTipo(item.getTipo());
                producto.setMargenUtilidad(item.getMargenUtilidad());
                producto.setPrecioList(item.getPrecioLista());
                producto.setCodigo(item.getCodigo());
                producto.setUnidadMedida(unidadMedida);
                producto.setCodigoCabys(item.getCodigoCabys()!=null?item.getCodigoCabys().replaceAll("[^0-9.]",""):null);
                producto.setCatalogo(item.getModeloCatalogo());
                producto.setSku(item.getSku());
                Fabricante fabricante = crearFabricante(item.getFabricante());
                producto.setFabricante(fabricante);
                producto.setDescripcionEspanol(item.getNombre());
                producto.setGravado(PRODUCTO_GRAVADO);
                producto.setEstado(PRODUCTO_ESTADO_ACTIVO);
                producto.setManejoBodega(MANEJO_BODEGA_PRODUCTO_SIMPLE);
                if (familiaActual == null) {
                    throw new GeneralInventoryException("La familia del producto es requerida, codigo:  " + item.getCodigo() + " Verifique que si es Variation antes exista un Variable.");
                }
//                producto.setFamilia(familiaActual);  //TODO UNDO
                producto.setPrecioList(item.getPrecioLista());
                producto.setUserUltimoCambio(this.userService.getCurrentLoggedUser());
                producto.setFechaUltimoCambio((new java.sql.Date(DateUtil.getCurrentCalendar().getTime().getTime())));
                this.productoRepository.save(producto);
            } else if (generalParameterImportProducto.getVal().equals(IMPORT_ACTUALIZAR_PRODUCTO)) {
                producto.setMargenUtilidad(item.getMargenUtilidad());
                producto.setCodigoCabys(item.getCodigoCabys());
                producto.setPrecioList(item.getPrecioLista());
                producto.setDescripcionEspanol(item.getNombre().replaceAll(";", ","));
            }
        } catch (RuntimeException e) {
            //e.printStackTrace();
            throw new GeneralInventoryException("Error procesando producto " + producto.getCodigo() + " " + e.getMessage());
        }

        initInventarioDetalle.setProductoId(producto.getId());
        this.initInventarioDetalleRepository.save(initInventarioDetalle);
        initInventario.getDetalles().add(initInventarioDetalle);

        //Procesar los atributos
        List<String> atributos = item.getAtributos();
        ListIterator <String> it = null;
        it = atributos.listIterator();

        //Procesar la lista de atributos
        while(it.hasNext()) {
            procesarAtributosProducto(it, producto, initInventarioDetalle);
        }

    }

    public void procesarAtributos(ListIterator<String> it, Familia familia, InitInventarioDetalle initInventarioDetalle) throws Exception{
        //Procesar la lista de atributos
        while(it.hasNext()) {

            String atributo = it.next();
            String propiedades = it.next();
            //Si uno de los campos es nulo no los procesa
            if (!parseArchivoProducto.validarStringNulo(atributo) && ! parseArchivoProducto.validarStringNulo(propiedades)){
                Atributo a = this.crearAtributo(familia, atributo);
                List<String> atributosList =  Arrays.asList(this.parseArchivoProducto.parsePropertyList(propiedades));
                ListIterator<String> atributosIterator = null;
                atributosIterator =  atributosList.listIterator();
                while(atributosIterator.hasNext()){
                    AtributoDetalle atributoDetalle = this.crearAtributoDetalle(a,atributosIterator.next());
                    crearInitInventarioAtributo(a.getNombre(), atributoDetalle.getNombre(), initInventarioDetalle);

                }

            }

        }
    }
    @Transactional(rollbackFor = {Exception.class})
    public void crearProductoFAmiliaAtributo(Atributo atributo, Producto producto, AtributoDetalle atributoDetalle) throws Exception{

        if (atributo == null || producto == null || atributoDetalle == null){
            throw new GeneralInventoryException("Atributos por familia y producto: se requiere atributo, producto y detalle del atributo asignado");
        }

        ProductoFamiliaAtributo productoFamiliaAtributo = this.productoFamiliaAtributoRepository.findAtributosProductoByAtributoProductoAndDetalle(atributo.getId(), producto.getId(), atributoDetalle.getId());
        if (productoFamiliaAtributo == null){
            productoFamiliaAtributo = new ProductoFamiliaAtributo();
            productoFamiliaAtributo.setEstado(PRODUCTO_FAMILIA_ATRIBUTO_ACTIVO);
            productoFamiliaAtributo.setAtributo(atributo);
            productoFamiliaAtributo.setProducto(producto);
            productoFamiliaAtributo.setAtributoDetalle(atributoDetalle);
            this.productoFamiliaAtributoRepository.save(productoFamiliaAtributo);
        }
    }

    public void procesarAtributosProducto(ListIterator<String> it, Producto producto, InitInventarioDetalle initInventarioDetalle) throws Exception{
        //Procesar la lista de atributos de un producto
        while(it.hasNext()) {

            String atributo = it.next();
            String propiedades = it.next();
            //Si uno de los campos es nulo no los procesa
            if (!parseArchivoProducto.validarStringNulo(atributo) && ! parseArchivoProducto.validarStringNulo(propiedades)){
//                Atributo a = this.crearAtributo(producto.getFamilia(), atributo);
//                List<String> atributosList =  Arrays.asList(this.parseArchivoProducto.parsePropertyList(propiedades));
//                ListIterator<String> atributosIterator = null;
//                atributosIterator =  atributosList.listIterator();
//                while(atributosIterator.hasNext()){
//                    AtributoDetalle atributoDetalle = this.crearAtributoDetalle(a,atributosIterator.next());
//                    crearInitInventarioAtributo(a.getNombre(), atributoDetalle.getNombre(), initInventarioDetalle);
//                    crearProductoFAmiliaAtributo(a, producto, atributoDetalle);
//                } //TODO UNDO

            }

        }
    }


    //Este metodo crea el documento inicializador del inventario y se lo pasa
    //Al BodegaManagerService para que meta al inventario productos si es necesario
    @Transactional(rollbackFor = {Exception.class})
    public synchronized boolean  initializerInventario(ProductoInventarioDTO productoInventarioDTO) throws Exception {
        boolean cargaExitosa = false;
        try {
            GeneralParameter generalParameterImportInventario = this.generalParameterService.getByCode(PARAMETER_IMPORT_ACTUALIZA_INVENTARIO);

            if (generalParameterImportInventario == null) {
                throw new GeneralInventoryException("Error de parámetro importacion inventario no encontrado: " + PARAMETER_IMPORT_ACTUALIZA_INVENTARIO);
            }

            if (productoInventarioDTO.getItems() == null) {
                throw new GeneralInventoryException("La lista de familias / productos está vacia");
            }

            //Ciclo para crear y cargar un documento initializer del inventario
            InitInventario initInventario = new InitInventario();
            initInventario.setEstado(INIT_INVENTARIO_ESTADO_EDICION);
            initInventario.setFlagModoImportInventario(generalParameterImportInventario.getVal());
            initInventario.setObservacion("Inicializacion de inventario por el usuario: " + this.userService.getCurrentLoggedUser().getUsername());
            setAuditoriamodificacion(initInventario);
            setAuditoriaCreacion(initInventario);
            initInventario.setUriFileName(productoInventarioDTO.getUriFileName());
            this.initInventarioRepository.save(initInventario);
            int count = 0;
            Familia familiaActual = null;

            for (ProductoInventarioItemDTO item : productoInventarioDTO.getItems()) {
                InitInventarioDetalle initInventarioDetalle = null;

                if (item.getTipo().equals(TIPO_LINEA_ARTICULO_VARIABLE)) {
                    count = 0;
                    familiaActual = procesarItemVariable(item, initInventario);

                } else if (item.getTipo().equals(TIPO_LINEA_ARTICULO_VARIATION) || item.getTipo().equals(TIPO_LINEA_ARTICULO_SIMPLE)) {
                    count += 1;
                    procesarItemVariation(item, familiaActual, initInventario, count);

                } else {
                    throw new GeneralInventoryException("Tipo de linea de carga masiva de familias/productos desconocida: " + item.getTipo());
                }
            }
            //Cargar el nuevo inventario a la bodega
            this.bodegaManagerService.initializerBodega(initInventario);

            cargaExitosa = true;
        } catch (GeneralInventoryException re) {
            throw re;

        } catch (RuntimeException re) {
            re.printStackTrace();
            throw new GeneralInventoryException("Error durante la importacion " + re.getMessage());
        } catch (Exception re) {
            re.printStackTrace();
            throw new GeneralInventoryException("Error durante la importacion " + re.getMessage());
        }

        return cargaExitosa;
    }
}
