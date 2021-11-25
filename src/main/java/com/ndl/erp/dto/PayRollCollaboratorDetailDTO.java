package com.ndl.erp.dto;

import com.ndl.erp.domain.Collaborator;
import com.ndl.erp.domain.PayRollCollaboratorDetail;
import com.ndl.erp.util.StringHelper;

import java.io.Serializable;

public class PayRollCollaboratorDetailDTO implements Serializable{

    private static final long serialVersionUID = 1777000000000000010L;

    private Integer id;

    private PayRollDTO payRoll;
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
    private Double price;
    private Double total;
    private Double cantidad;

    private String startDate;
    private String endDate;

    public PayRollCollaboratorDetailDTO() {

    }

    public PayRollCollaboratorDetailDTO(PayRollCollaboratorDetail detail ) {
        this.id = detail.getId();
        this.payRoll = new PayRollDTO(detail.getPayRoll());
        this.collaborator = detail.getCollaborator();
        this.type = detail.getType();
        this.indice = detail.getIndice();
        this.day1 = detail.getDay1();
        this.day2 = detail.getDay2();
        this.day3 = detail.getDay3();
        this.day4 = detail.getDay4();
        this.day5 = detail.getDay5();
        this.day6 = detail.getDay6();
        this.day7 = detail.getDay7();

        this.cantidad = detail.getCantidad();
        this.price = detail.getPrice();
        this.total = detail.getTotal();

        this.startDate = StringHelper.getDate2String(detail.getStartDate());
        this.endDate = StringHelper.getDate2String(detail.getEndDate());
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public PayRollDTO getPayRoll() {
        return payRoll;
    }

    public void setPayRoll(PayRollDTO payRoll) {
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
        this.day1 = day1;
    }

    public Double getDay2() {
        return day2;
    }

    public void setDay2(Double day2) {
        this.day2 = day2;
    }

    public Double getDay3() {
        return day3;
    }

    public void setDay3(Double day3) {
        this.day3 = day3;
    }

    public Double getDay4() {
        return day4;
    }

    public void setDay4(Double day4) {
        this.day4 = day4;
    }

    public Double getDay5() {
        return day5;
    }

    public void setDay5(Double day5) {
        this.day5 = day5;
    }

    public Double getDay6() {
        return day6;
    }

    public void setDay6(Double day6) {
        this.day6 = day6;
    }

    public Double getDay7() {
        return day7;
    }

    public void setDay7(Double day7) {
        this.day7 = day7;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
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

    public Double getCantidad() {
        return cantidad;
    }

    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }
}
