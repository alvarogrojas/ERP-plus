package com.ndl.erp.domain;

import javax.persistence.*;

@Entity
public class CantonCr implements Comparable<CantonCr> {



    @OneToOne
    @JoinColumn(name = "provincia_id", referencedColumnName = "id")
    private ProvinciaCr provinciaCr;


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String codigoCanton;

    private String nombreCanton;

    public ProvinciaCr getProvinciaCr() {
        return provinciaCr;
    }

    public void setProvinciaCr(ProvinciaCr provinciaCr) {
        this.provinciaCr = provinciaCr;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getCodigoCanton() {
        return codigoCanton;
    }

    public void setCodigoCanton(String codigoCanton) {
        this.codigoCanton = codigoCanton;
    }

    public String getNombreCanton() {
        return nombreCanton;
    }

    public void setNombreCanton(String nombreCanton) {
        this.nombreCanton = nombreCanton;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public int compareTo(final CantonCr o) {
        return Integer.compare(this.id, o.id);
    }

}