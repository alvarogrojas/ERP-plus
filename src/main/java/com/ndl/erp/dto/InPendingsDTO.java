package com.ndl.erp.dto;

import com.ndl.erp.domain.Bodega;
import com.ndl.erp.domain.Devolucion;
import com.ndl.erp.domain.MotivoDevolucion;

import java.util.List;

public class InPendingsDTO {

    private Integer id;
    private String type;
    private Devolucion devolucion;
    private List<DetailsDTO> details;
    private List<Bodega> bodegas;
    private List<MotivoDevolucion> motivos;
    private Integer devolucionId;
    public InPendingsDTO() {}


    public Devolucion getDevolucion() {
        return devolucion;
    }

    public void setDevolucion(Devolucion devolucion) {
        this.devolucion = devolucion;
    }

    public List<DetailsDTO> getDetails() {
        return details;
    }

    public void setDetails(List<DetailsDTO> details) {
        this.details = details;
    }

    public InPendingsDTO(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Bodega> getBodegas() {
        return bodegas;
    }

    public void setBodegas(List<Bodega> bodegas) {
        this.bodegas = bodegas;
    }

    public List<MotivoDevolucion> getMotivos() {
        return motivos;
    }

    public void setMotivos(List<MotivoDevolucion> motivos) {
        this.motivos = motivos;
    }

    public Integer getDevolucionId() {
        return devolucionId;
    }

    public void setDevolucionId(Integer devolucionId) {
        this.devolucionId = devolucionId;
    }
}
