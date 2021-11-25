package com.ndl.erp.fe.model;

import com.ndl.erp.fe.dtos.FacturaDetalleDTO;

import java.util.List;

public interface FacturaBase {
    Integer getEnviadaHacienda();

    String getClienteContacto1Correo();

    String getClienteContacto2Correo();

    String getClienteContacto3Correo();

    String getClienteContacto4Correo();

    String getClienteCedulaJuridica();

    Integer getClienteId();

    String getClienteNombre();



    String getClienteTelefono();

    String getClienteDireccion();

    String getClienteContacto1();

    Integer getClienteTieneCredito();

    Integer getClienteDiasCredito();

    Integer getFacturaId();

    Double getImpuestoVentas();

    List<FacturaDetalleDTO> getFacturaDetalle();

    /**
     * 0 =  Fisico
     * 1 = Juridico
     * 3: DIMEX
     * @return
     */
    Integer getClienteEsJuridico();

    /**
     * Es un cliente fuera del pais: no enviar cedula
     *
     * @return
     */
    Integer esInternacional();
}
