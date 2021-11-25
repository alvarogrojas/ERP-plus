package com.ndl.erp.dto;

public class EmpresaTiqueteDTO {

    private String empresaNombre;
    private String  cedula;
    private String telefono;
    private String correo;
    private String direccionCorta;

    private String nombreCajero;
    private String tiqueteBottom1;
    private String tiqueteBottom2;
    private String tiqueteBottom3;
    private String tiqueteBottom;
    public String getEmpresaNombre() {
        return empresaNombre;
    }

    public void setEmpresaNombre(String empresaNombre) {
        this.empresaNombre = empresaNombre;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getDireccionCorta() {
        return direccionCorta;
    }

    public void setDireccionCorta(String direccionCorta) {
        this.direccionCorta = direccionCorta;
    }

    public String getNombreCajero() {
        return nombreCajero;
    }

    public void setNombreCajero(String nombreCajero) {
        this.nombreCajero = nombreCajero;
    }

    public String getTiqueteBottom() {
        return tiqueteBottom;
    }

    public void setTiqueteBottom(String tiqueteBottom) {
        this.tiqueteBottom = tiqueteBottom;
    }

    public String getTiqueteBottom1() {
        return tiqueteBottom1;
    }

    public void setTiqueteBottom1(String tiqueteBottom1) {
        this.tiqueteBottom1 = tiqueteBottom1;
    }

    public String getTiqueteBottom2() {
        return tiqueteBottom2;
    }

    public void setTiqueteBottom2(String tiqueteBottom2) {
        this.tiqueteBottom2 = tiqueteBottom2;
    }

    public String getTiqueteBottom3() {
        return tiqueteBottom3;
    }

    public void setTiqueteBottom3(String tiqueteBottom3) {
        this.tiqueteBottom3 = tiqueteBottom3;
    }
}
