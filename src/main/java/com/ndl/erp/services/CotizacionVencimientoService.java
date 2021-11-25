package com.ndl.erp.services;

import com.ndl.erp.domain.Cotizacion;
import com.ndl.erp.domain.CotizacionDetalle;
import com.ndl.erp.repository.CotizacionRepository;
import com.ndl.erp.services.bodega.BodegaManagerService;
import com.ndl.erp.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.ndl.erp.constants.CotizacionConstants.COTIZACION_ESTADO_ENVIADA;
import static com.ndl.erp.constants.CotizacionConstants.COTIZACION_ESTADO_VENCIDA;

@Transactional
@Component
public class CotizacionVencimientoService {

    @Autowired
    CotizacionRepository cotizacionRepository;

    @Autowired
    BodegaManagerService bodegaManagerService;

    // @Scheduled(cron = "*/1 * * * * *") //cada segundo
    @Scheduled(cron = "0 0 0 * * ?") //todos los dias a las 12 media noche
    public synchronized void procesarVencimientoCotizacion() throws  Exception{
        aplicaVencimientoCotizacion(null, null);
    }

    @Transactional(rollbackFor = {Exception.class})
    public void aplicaVencimientoCotizacion(Date startDate, Date endDate)throws  Exception{

        if (startDate == null  && endDate == null){
            Calendar c = Calendar.getInstance();
            endDate = new java.sql.Date(DateUtil.getCurrentCalendar().getTime().getTime());
            c.add(Calendar.DAY_OF_MONTH, -7);
            startDate = c.getTime();
        }

        List<Cotizacion> cotizacionList = this.cotizacionRepository.getCotizacionByFechaVencimiento(startDate, endDate);

        if (cotizacionList != null) {
            for (Cotizacion c : cotizacionList){
                for (CotizacionDetalle cd : c.getDetalles()){
                    if (c.getEstado().equals(COTIZACION_ESTADO_ENVIADA)) {
                        this.bodegaManagerService.liberarCantidadCotizadaBodegaProducto(c.getBodega(), cd.getInventario().getProducto(), cd.getCantidad());
                    }
                }
                c.setEstado(COTIZACION_ESTADO_VENCIDA);
                this.cotizacionRepository.save(c);
            }
        }
    }


}
