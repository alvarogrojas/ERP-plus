package com.ndl.erp.services;

import com.ndl.erp.domain.Producto;
import com.ndl.erp.domain.ProductoDescuento;
import com.ndl.erp.dto.ProductoDescuentoDTO;
import com.ndl.erp.dto.ProductoDescuentoListDTO;
import com.ndl.erp.repository.ProductoDescuentoRepository;
import com.ndl.erp.repository.ProductoRepository;
import com.ndl.erp.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.ndl.erp.constants.ProductoDescuentoConstants.PRODUCTO_DESCUENTO_ESTADO_ACTIVA;
import static com.ndl.erp.constants.ProductoDescuentoConstants.PRODUCTO_DESCUENTO_ESTADO_INACTIVA;



@Component
public class ProductoDescuentoService {

    @Autowired
    UserServiceImpl userService;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private ProductoDescuentoRepository productoDescuentoRepository;


    public ProductoDescuentoDTO getProductoDescuento(Integer id) {
        ProductoDescuentoDTO productoDescuentoDTO = this.getProductoDescuento();
        ProductoDescuento pd = this.productoDescuentoRepository.getProductoDescuentoByIdAndEstado(id);

        if (pd==null) {
            return productoDescuentoDTO;
        }
        productoDescuentoDTO.setCurrent(pd);
        return productoDescuentoDTO;
    }

    public ProductoDescuentoListDTO getProductoDescuentoList() {

        ProductoDescuentoListDTO productoDescuentoListDTO = new ProductoDescuentoListDTO();

        productoDescuentoListDTO.setProductoDescuentoList(this.productoDescuentoRepository.getAllProductoDescuento());


        return productoDescuentoListDTO;

    }

    public ProductoDescuentoListDTO getProductoDescuentoList(Integer productoId) {

        ProductoDescuentoListDTO productoDescuentoListDTO = new ProductoDescuentoListDTO();

        productoDescuentoListDTO.setProductoDescuentoList(this.productoDescuentoRepository.getProductoDescuentoByProductoAndEstado(productoId));


        return productoDescuentoListDTO;

    }


    public ProductoDescuentoDTO getProductoDescuento() {

        List<String> estadoList = new ArrayList<>();

        estadoList.add(PRODUCTO_DESCUENTO_ESTADO_ACTIVA);
        estadoList.add(PRODUCTO_DESCUENTO_ESTADO_INACTIVA);

        List<Producto> productoList = this.productoRepository.findAll();

        ProductoDescuentoDTO productoDescuentoDTO = new ProductoDescuentoDTO();

        productoDescuentoDTO.setEstadoList(estadoList);
        productoDescuentoDTO.setProductoList(productoList);


        return productoDescuentoDTO;
    }

    public ProductoDescuento save(ProductoDescuento pd) {

        if (pd.getId() == null) {
            pd.setFechaIngreso(new Date(DateUtil.getCurrentCalendar().getTime().getTime()));
            pd.setUserIngresadoPor(this.userService.getCurrentLoggedUser());
            pd.setFechaUltimaActualizacion(new Date(DateUtil.getCurrentCalendar().getTime().getTime()));
            pd.setUserUltimaActualizacionPor(this.userService.getCurrentLoggedUser());
        } else{
            pd.setFechaUltimaActualizacion(new Date(DateUtil.getCurrentCalendar().getTime().getTime()));
            pd.setUserUltimaActualizacionPor(this.userService.getCurrentLoggedUser());
        }

        return this.productoDescuentoRepository.save(pd);
    }

    private PageRequest createPageable(Integer pageNumber, Integer pageSize, String direction, String byField) {

        return PageRequest.of(pageNumber, pageSize, direction==null || direction.equals("asc")? Sort.Direction.ASC: Sort.Direction.DESC, byField);
    }
}