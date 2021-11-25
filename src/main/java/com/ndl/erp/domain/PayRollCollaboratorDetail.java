package com.ndl.erp.domain;


import com.ndl.erp.util.StringHelper;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;


@Entity
@Table(name = "pay_roll_collaborator_detail")
public class PayRollCollaboratorDetail implements Serializable {

    @Id
    @GeneratedValue
    private Integer id;

    @OneToOne
    @JoinColumn(name="id_pay_roll", referencedColumnName="id")
    private PayRoll payRoll;

    @OneToOne
    @JoinColumn(name="id_collaborator", referencedColumnName="id")
    private Collaborator collaborator;

    private String type;
    private Integer indice;
    private Double day1;
    private Double day2;
    private Double day3;
    private Double day4;
    private Double day5;
    private Double day6;
    private Double day7;
    private Double cantidad;
    private Double price;
    private Double total;

    private Integer idUserRegister;
    private Date startDate;
    private Date endDate;
    private Date createDate;
    private Date updateDate;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public PayRoll getPayRoll() {
        return payRoll;
    }

    public void setPayRoll(PayRoll payRoll) {
        this.payRoll = payRoll;
    }

    public Collaborator getCollaborator() {
        return collaborator;
    }

    public void setCollaborator(Collaborator collaborator) {
        this.collaborator = collaborator;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getIndice() {
        return indice;
    }

    public void setIndice(Integer indice) {
        this.indice = indice;
    }

    public Double getDay1() {
        return day1;
    }



    public void setDay1(Double day1) {
        if(day1==null)
            this.day1=0d;
        else{
            this.day1 = day1;
        }
    }

    public Double getDay2() {
        return day2;
    }

    public void setDay2(Double day2) {
        if(day2==null)
            this.day2=0d;
        else{
            this.day2 = day2;
        }
    }

    public Double getDay3() {
        return day3;
    }

    public void setDay3(Double day3) {
        if(day3==null)
            this.day3=0d;
        else{
            this.day3 = day3;
        }
    }

    public Double getDay4() {
        return day4;
    }

    public void setDay4(Double day4) {
        if(day4==null)
            this.day4=0d;
        else{
            this.day4 = day4;

        }
    }

    public Double getDay5() {
        return day5;
    }

    public void setDay5(Double day5) {
        if(day5==null)
            this.day5=0d;
        else{
            this.day5 = day5;

        }
    }

    public Double getDay6() {
        return day6;
    }

    public void setDay6(Double day6) {
        if(day6==null)
            this.day6=0d;
        else{
            this.day6 = day6;

        }
    }

    public Double getDay7() {
        return day7;
    }

    public void setDay7(Double day7) {
        if(day7==null)
            this.day7=0d;
        else{
            this.day7 = day7;
        }
    }

    public Integer getIdUserRegister() {
        return idUserRegister;
    }

    public void setIdUserRegister(Integer idUserRegister) {
        this.idUserRegister = idUserRegister;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public void refreshCantidad() {
        if (this.isCollaboratorConfianzaAndHS()){
            this.cantidad =  collaborator.getCcssHoursWork() /2; //StringHelper.EMP_CONFIANSA_CANTIDAD;
        }else{
            this.cantidad  = day1 != null ? day1 : 0;
            this.cantidad += day2 != null ? day2 : 0;
            this.cantidad += day3 != null ? day3 : 0;
            this.cantidad += day4 != null ? day4 : 0;
            this.cantidad += day5 != null ? day5 : 0;
            this.cantidad += day6 != null ? day6 : 0;
            this.cantidad += day7 != null ? day7 : 0;

        }
    }

    public Double getCantidad() {
        return cantidad;
    }

    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }


    public void addDay1(Double day) {
        if (this.isCollaboratorConfianzaAndHS())
            this.day1 = 0d;
        else
            this.day1 = this.day1 != null ? this.day1 + day : day;
    }

    public void addDay2(Double day) {
        if (this.isCollaboratorConfianzaAndHS())
            this.day2 = 0d;
        else
            this.day2 = this.day2 != null ? this.day2 + day : day;
    }

    public void addDay3(Double day) {
        if (this.isCollaboratorConfianzaAndHS())
            this.day3 = 0d;
        else
            this.day3 = this.day3 != null ? this.day3 + day : day;
    }

    public void addDay4(Double day) {
        if (this.isCollaboratorConfianzaAndHS())
            this.day4 = 0d;
        else
            this.day4 = this.day4 != null ? this.day4 + day : day;
    }

    public void addDay5(Double day) {
        if (this.isCollaboratorConfianzaAndHS())
            this.day5 = 0d;
        else
            this.day5 = this.day5 != null ? this.day5 + day : day;
    }

    public void addDay6(Double day) {
        if (this.isCollaboratorConfianzaAndHS())
            this.day6 = 0d;
        else
            this.day6 = this.day6 != null ? this.day6 + day : day;
    }

    public void addDay7(Double day) {
        if (this.isCollaboratorConfianzaAndHS())
            this.day7 = 0d;
        else
            this.day7 = this.day7 != null ? this.day7 + day : day;
    }


    private boolean isCollaboratorConfianzaAndHS(){
        return this.getCollaborator().getTypePayroll().equals(StringHelper.EMP_CONFIANSA) && this.getType().equals(StringHelper.HS);
    }
}
