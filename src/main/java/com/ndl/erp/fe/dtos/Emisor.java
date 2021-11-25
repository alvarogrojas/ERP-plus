package com.ndl.erp.fe.dtos;

public class Emisor {

    private String tipoIdentificacion = "02";

    private String numeroIdentificacion = "310165487908";

    private String nombre = "RFS Logistica Integrada S.A.";

    public Emisor() {
    }

    public Emisor(com.ndl.erp.fe.v43.EmisorType et) {
        this.tipoIdentificacion = et.getIdentificacion().getTipo();
        this.numeroIdentificacion = et.getIdentificacion().getNumero();
        this.nombre = et.getNombre();
    }

    public Emisor(com.ndl.erp.fe.v43.nc.EmisorType et) {
        this.tipoIdentificacion = et.getIdentificacion().getTipo();
        this.numeroIdentificacion = et.getIdentificacion().getNumero();
        this.nombre = et.getNombre();
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
