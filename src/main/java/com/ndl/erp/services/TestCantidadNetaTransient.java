package com.ndl.erp.services;

import com.ndl.erp.domain.Producto;
import com.ndl.erp.repository.BodegaRepository;
import com.ndl.erp.repository.InventarioBodegaRepository;
import com.ndl.erp.repository.ProductoRepository;
import com.ndl.erp.services.bodega.BodegaManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;



@Transactional
@Component
public class TestCantidadNetaTransient {

    @Autowired
    BodegaRepository BodegaRepository;

    @Autowired
    ProductoRepository productoRepository;


    @Autowired
    InventarioBodegaRepository inventarioBodegaRepository;

    @Autowired
    BodegaManagerService manageInventarioBodega;

    //Todo: Ejemplo de pruebas usando cron
    //@Scheduled(cron = "*/60 * * * * *") //cada 60 segundos
    //@Scheduled(cron = "0 0 0 * * ?") //corre todos los dias  a las 12 horas
    public synchronized void procesarPrueba() throws  Exception{
        //manageInventarioBodega.congelarSaldoBodegaProducto(BodegaRepository.getById(1), productoRepository.getOne(34), 1d);
      Producto p =  productoRepository.getOne(34);
        System.out.print("El precio de lista del producto es  " + p.getPrecioList() + " "   +  " El precio de venta es " + p.getPrecioVenta());
    }

}

