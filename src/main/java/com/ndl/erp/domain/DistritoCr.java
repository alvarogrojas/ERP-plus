package com.ndl.erp.domain;

import javax.persistence.*;

@Entity
public class DistritoCr implements Comparable<DistritoCr>{


    @OneToOne
    @JoinColumn(name = "canton_id", referencedColumnName = "id")
    private CantonCr cantonCr;

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;




    private String codigoDistrito;

    private String nombreDistrito;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }



    public String getCodigoDistrito() {
        return codigoDistrito;
    }

    public void setCodigoDistrito(String codigoDistrito) {
        this.codigoDistrito = codigoDistrito;
    }

    public String getNombreDistrito() {
        return nombreDistrito;
    }

    public void setNombreDistrito(String nombreDistrito) {
        this.nombreDistrito = nombreDistrito;
    }

    public CantonCr getCantonCr() {
        return cantonCr;
    }

    public void setCantonCr(CantonCr cantonCr) {
        this.cantonCr = cantonCr;
    }



    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public int compareTo(final DistritoCr o) {
        return Integer.compare(this.id, o.id);
    }
}
