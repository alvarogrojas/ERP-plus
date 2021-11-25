package com.ndl.erp.services.bodega;

import com.ndl.erp.domain.Inventario;
import com.ndl.erp.repository.InventarioBodegaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static com.ndl.erp.constants.BodegaConstants.MANEJO_BODEGA_PRECIO_ACTUALIZA;
import static com.ndl.erp.constants.BodegaConstants.MANEJO_BODEGA_PRECIO_MAYOR;

@Component
public class ContextoEntradaPrecioProductoService {


        @Autowired
        InventarioBodegaRepository inventarioBodegaRepository;

        private EstrategiaEntradaPrecioProducto strategy;

        @Autowired
        private EstrategiaConcretaEntradaPrecioMayor estrategiaConcretaPrecioMayor;

        @Autowired
        private EstrategiaConcretaEntradaPrecioActualizado estrategiaConcretaEntradaPrecioActualizado;



    @Autowired
        private ManejoBodegaInOutImpl manejoBodegaIngpro;


    public EstrategiaEntradaPrecioProducto getStrategy() {
        return strategy;
    }

    public void setStrategy(EstrategiaEntradaPrecioProducto strategy) {
        this.strategy = strategy;
    }

    public void setPrecioEntradaStrategy(Inventario inventario) throws Exception{

            String manejoBodega;


                manejoBodega = this.manejoBodegaIngpro.getManejoPrecioBodega(inventario.getBodega());

                //Seleccion del algoritmo de entrada
                if (manejoBodega.equals(MANEJO_BODEGA_PRECIO_MAYOR)) {

                    this.setStrategy(estrategiaConcretaPrecioMayor);

                } else if(manejoBodega.equals(MANEJO_BODEGA_PRECIO_ACTUALIZA)){

                    this.setStrategy(estrategiaConcretaEntradaPrecioActualizado);

                } else { //Por ahora el default es manejo bodega precio mayor

                    this.setStrategy(estrategiaConcretaPrecioMayor);
                }

        }

        @Transactional(rollbackFor = {Exception.class})
        public synchronized Inventario aplicarEntradaPrecioProducto(Inventario inventario, Double precioIngreso) throws Exception{


            setPrecioEntradaStrategy(inventario);
            //efectuar la entrada del producto con el algoritmo seleccionado
            return this.strategy.ingresoPrecioProducto(inventario, precioIngreso);

        }

    }
