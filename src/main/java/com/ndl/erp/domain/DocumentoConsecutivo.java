package com.ndl.erp.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
@Entity
public class DocumentoConsecutivo {

     @Id
     @GeneratedValue(strategy= GenerationType.AUTO)
     private Integer id;

     private String empresa;
     private String tipoDocumento;
     private Integer docOrigenId;
     private Integer actualId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public Integer getDocOrigenId() {
        return docOrigenId;
    }

    public void setDocOrigenId(Integer docOrigenId) {
        this.docOrigenId = docOrigenId;
    }

    public Integer getActualId() {
        return actualId;
    }

    public void setActualId(Integer actualId) {
        this.actualId = actualId;
    }
}
