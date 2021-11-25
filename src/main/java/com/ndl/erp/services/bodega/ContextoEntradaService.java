
package com.ndl.erp.services.bodega;

import com.ndl.erp.domain.Bodega;
import com.ndl.erp.domain.Inventario;
import com.ndl.erp.domain.Producto;
import com.ndl.erp.domain.Tienda;
import com.ndl.erp.exceptions.GeneralInventoryException;
import com.ndl.erp.repository.InventarioBodegaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static com.ndl.erp.constants.BodegaConstants.*;

@Component
public  class ContextoEntradaService {

    @Autowired
    InventarioBodegaRepository inventarioBodegaRepository;
    private EstrategiaEntradaInventario strategy;
    @Autowired
    private EstrategiaEntradaConcretaPEPS estrategiaEntradaConcretaPEPS;

    @Autowired
    private EstrategiaEntradaConcretaUEPS estrategiaEntradaConcretaUEPS;

    @Autowired
    private EstrategiaEntradaConcretaSimple estrategiaEntradaConcretaSimple;

    @Autowired
    private ManejoBodegaInOutImpl manejoBodegaIngpro;


    public EstrategiaEntradaInventario getStrategy() {
        return strategy;
    }

    public void setStrategy(EstrategiaEntradaInventario strategy) {
        this.strategy = strategy;
    }

    public EstrategiaEntradaConcretaPEPS getEstrategiaEntradaConcretaPEPS() {
        return estrategiaEntradaConcretaPEPS;
    }

    public void setEstrategiaEntradaConcretaPEPS(EstrategiaEntradaConcretaPEPS estrategiaEntradaConcretaPEPS) {
        this.estrategiaEntradaConcretaPEPS = estrategiaEntradaConcretaPEPS;
    }

    public void setProductStrategy(Bodega bodega, Producto producto) throws Exception{

        String manejoBodega;

        if (producto != null) {

            manejoBodega = this.manejoBodegaIngpro.getManejoInventarioBodega(bodega, producto);

            //Seleccion del algoritmo de entrada
            if (manejoBodega.equals(MANEJO_BODEGA_PEPS)) {

                this.setStrategy(estrategiaEntradaConcretaPEPS);

            } else if (manejoBodega.equals(MANEJO_BODEGA_UEPS)) {

                this.setStrategy(estrategiaEntradaConcretaUEPS);

            } else if (producto.getManejoBodega().equals(MANEJO_BODEGA_SIMPLE)) {

                this.setStrategy(estrategiaEntradaConcretaSimple);

            } else { //El algortimo entrada default es simple

                this.setStrategy(estrategiaEntradaConcretaUEPS);
            }
        } else {
            throw new GeneralInventoryException("El producto suministrado no es válido");
        }
    }

    @Transactional(rollbackFor = {Exception.class})
    public synchronized Inventario aplicarEntrada(Tienda tienda, Integer ventaId, Integer ventaDetalleId, Bodega bodega, Producto producto, String barcode, Double cantidad, Double costo, Boolean isDevolucion, String modoCargaInventario) throws Exception{


        setProductStrategy(bodega, producto);
        //efectuar la entrada del producto con el algoritmo seleccionado
        return this.strategy.entradaInventario(tienda, bodega, producto, barcode, cantidad, costo, isDevolucion, modoCargaInventario);

    }

}