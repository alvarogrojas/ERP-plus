package com.ndl.erp.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Client {


    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer clientId;

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL,
            mappedBy = "client", orphanRemoval = true)
    private List<ClienteDescuento> clienteDescuentoList = new ArrayList<ClienteDescuento>(0);

    @OneToOne
    @JoinColumn(name="provincia_cr_id", referencedColumnName="id")
    private ProvinciaCr provinciaCr;

    @OneToOne
    @JoinColumn(name="canton_cr_id", referencedColumnName="id")
    private CantonCr cantonCr;


    @OneToOne
    @JoinColumn(name="distrito_cr_id", referencedColumnName="id")
    private DistritoCr distritoCr;


    private String idType;
    private boolean nacional = true;
    private String exonerated;
    private String name;
    private String address;
    private String description;
    private String phone;
    private String score;
    private Date updateDate;
    private String email;
    private String status;
    private String enterpriceId;
    private Integer creditDay;

//    @JsonManagedReference
//    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,
//            mappedBy = "client", orphanRemoval = true)
//    private List<ContactClient> contacts = new ArrayList<ContactClient>(0);


    public List<ClienteDescuento> getClienteDescuentoList() {
        return clienteDescuentoList;
    }

    public void setClienteDescuentoList(List<ClienteDescuento> clienteDescuentoList) {
        this.clienteDescuentoList = clienteDescuentoList;
    }

    public ProvinciaCr getProvinciaCr() {
        return provinciaCr;
    }

    public void setProvinciaCr(ProvinciaCr provinciaCr) {
        this.provinciaCr = provinciaCr;
    }

    public CantonCr getCantonCr() {
        return cantonCr;
    }

    public void setCantonCr(CantonCr cantonCr) {
        this.cantonCr = cantonCr;
    }

    public DistritoCr getDistritoCr() {
        return distritoCr;
    }

    public void setDistritoCr(DistritoCr distritoCr) {
        this.distritoCr = distritoCr;
    }

    public Client() {}

    public Client(Integer id, String name) {
        this.clientId = id;
        this.name = name;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public boolean isNacional() {
        return nacional;
    }

    public void setNacional(boolean nacional) {
        this.nacional = nacional;
    }

    public String getExonerated() {
        return exonerated;
    }

    public void setExonerated(String exonerated) {
        this.exonerated = exonerated;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEnterpriceId() {
        return enterpriceId;
    }

    public void setEnterpriceId(String enterpriceId) {
        this.enterpriceId = enterpriceId;
    }

    public Integer getCreditDay() {
        return creditDay;
    }

    public void setCreditDay(Integer creditDay) {
        this.creditDay = creditDay;
    }

//    public List<ContactClient> getContacts() {
//        return contacts;
//    }
//
//    public void setContacts(List<ContactClient> contacts) {
//        this.contacts = contacts;
//    }
}
