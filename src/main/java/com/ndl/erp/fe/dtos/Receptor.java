package com.ndl.erp.fe.dtos;

public class Receptor {

    private String tipoIdentificacion = "02";

    private String numeroIdentificacion = "310120365421";
    private String nombre;

    public Receptor() {}

    public Receptor(com.ndl.erp.fe.v43.ReceptorType rt) {
        this.tipoIdentificacion = rt.getIdentificacion().getTipo();
        this.numeroIdentificacion = rt.getIdentificacion().getNumero();
        this.nombre = rt.getNombre();
    }

    public Receptor(com.ndl.erp.fe.v43.nc.ReceptorType rt) {
        this.tipoIdentificacion = rt.getIdentificacion().getTipo();
        this.numeroIdentificacion = rt.getIdentificacion().getNumero();
        this.nombre = rt.getNombre();
    }



    public String getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    public void setTipoIdentificacion(String tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    public String getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    public void setNumeroIdentificacion(String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
