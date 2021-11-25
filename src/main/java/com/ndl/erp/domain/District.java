
package com.ndl.erp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by wugalde on 3/19/17.
 */

@Entity
@Table(name = "district")
public class District implements Serializable {

    @Id
    @GeneratedValue
    private Integer id;


    private String name;
    private String code;

    @OneToOne
    @JoinColumn(name="id_province", referencedColumnName="id")
    private Province province;

    @OneToOne
    @JoinColumn(name="id_canton", referencedColumnName="id")
    private Canton canton;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @JsonIgnore
    public Province getProvince() {
        return province;
    }

    public void setProvince(Province province) {
        this.province = province;
    }

    @JsonIgnore
    public Canton getCanton() {
        return canton;
    }

    public void setCanton(Canton canton) {
        this.canton = canton;
    }
}
