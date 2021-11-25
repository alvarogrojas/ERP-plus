package com.ndl.erp.dto;

import com.ndl.erp.constants.TrasladoConstants;
import com.ndl.erp.domain.*;

import java.util.List;

public class TrasladoDTO {

    private Traslado current;
    private List<String> estados;
    private List<User> users;

    private List<Bodega> bodegas;
    private List<TrasladoJustificacion> justificaciones;

    private CostCenter defaulCostCenterTraslado;

    public TrasladoDTO() {
        this.current = new Traslado();
        this.current.setEstado(TrasladoConstants.TRASLADO_STATUS_EDICION);
    }

    public Traslado getCurrent() {
        return current;
    }

    public void setCurrent(Traslado current) {
        this.current = current;
    }

    public List<String> getEstados() {
        return estados;
    }

    public void setEstados(List<String> estados) {
        this.estados = estados;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Bodega> getBodegas() {
        return bodegas;
    }

    public void setBodegas(List<Bodega> bodegas) {
        this.bodegas = bodegas;
    }

    public List<TrasladoJustificacion> getJustificaciones() {
        return justificaciones;
    }

    public void setJustificaciones(List<TrasladoJustificacion> justificaciones) {
        this.justificaciones = justificaciones;
    }

    public CostCenter getDefaulCostCenterTraslado() {
        return defaulCostCenterTraslado;
    }

    public void setDefaulCostCenterTraslado(CostCenter defaulCostCenterTraslado) {
        this.defaulCostCenterTraslado = defaulCostCenterTraslado;
    }
}