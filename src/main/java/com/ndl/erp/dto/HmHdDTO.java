package com.ndl.erp.dto;

import com.ndl.erp.domain.Client;
import com.ndl.erp.domain.Collaborator;
import com.ndl.erp.domain.CostCenter;
import com.ndl.erp.domain.Currency;
import com.ndl.erp.util.StringHelper;

import java.io.Serializable;


public class HmHdDTO implements Serializable {

    private static final long serialVersionUID = 1777000000000000070L;
    private Currency currency;

    private Integer idCostCenter;
    private String code;
    private String ccName;
    private String ccType;
    private Integer idCollaborator;
    private String name;
    private Double costHour;
    private Double hs;
    private Double hm;
    private Double hd;
    private Double cantidad;
    private Double gastoHs;
    private Double gastoHm;
    private Double gastoHd;
    private Double gasto;

    private Integer idCurrency;

    private String accion;
    private Integer idPayRoll;
    private String status;
    private String start;
    private String end;

    private String curName;
    private String curSimbol;

    private CostCenter costCenter;
    private Client client;


    public HmHdDTO(
           CostCenter cc,
           Collaborator c,
           Double costHour,
           Double hs,
           Double hm,
           Double hd,
           Double cantidadS,
           Double cantidadM,
           Double cantidadD,
            Currency cur
    ) {
        this.idCostCenter = cc.getId();
        this.code = cc.getCode();
        this.ccType = cc.getType();
        this.ccName = cc.getName();
        this.costCenter = cc;
        this.client = cc.getClient();
        this.idCollaborator = c.getId();
        this.name = c.getName() + " " + c.getLastName();
        this.costHour = costHour;
        this.hs = hs;
        this.hm = hm;
        this.hd = hd;
        this.cantidad = cantidadD + cantidadM + cantidadS;
        this.currency = cur;
        this.curSimbol = cur.getSimbol();
        this.curName = cur.getName();
//        this.curSimbol = cur.getSimbol().to;
//        this.curName = cur.getName();

//        this.tot

    }


    public HmHdDTO() {
        this.accion = "LIST";
        this.status = "EDIT";
    }

    public void calc() {
        if(this.hs != null && this.hs > 0){
            this.gastoHs = this.hs * this.costHour;
        }else {
                this.gastoHs = 0d;
        }

        if(this.hm != null && this.hm > 0){
            this.gastoHm = this.hm * StringHelper.HM * this.costHour;
        }else {
            this.gastoHm = 0d;
        }

        if(this.hd != null && this.hd > 0){
            this.gastoHd = this.hd * StringHelper.HD * this.costHour;
        }else {
            this.gastoHd = 0d;
        }

        this.gasto = this.gastoHs + this.gastoHm + this.gastoHd;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public Integer getIdCollaborator() {
        return idCollaborator;
    }

    public void setIdCollaborator(Integer idCollaborator) {
        this.idCollaborator = idCollaborator;
    }

    public CostCenter getCostCenter() {
        return costCenter;
    }

    public void setCostCenter(CostCenter costCenter) {
        this.costCenter = costCenter;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCcName() {
        return ccName;
    }

    public void setCcName(String ccName) {
        this.ccName = ccName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public Double getCantidad() {
        return cantidad;
    }

    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }

    public Double getGasto() {
        return gasto;
    }

    public void setGasto(Double gasto) {
        this.gasto = gasto;
    }

    public Integer getIdPayRoll() {
        return idPayRoll;
    }

    public void setIdPayRoll(Integer idPayRoll) {
        this.idPayRoll = idPayRoll;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public Double getHs() {
        return hs;
    }

    public void setHs(Double hs) {
        this.hs = hs;
    }

    public Double getHm() {
        return hm;
    }

    public void setHm(Double hm) {
        this.hm = hm;
    }

    public Double getHd() {
        return hd;
    }

    public void setHd(Double hd) {
        this.hd = hd;
    }

    public Integer getIdCostCenter() {
        return idCostCenter;
    }

    public void setIdCostCenter(Integer idCostCenter) {
        this.idCostCenter = idCostCenter;
    }

    public String getCcType() {
        return ccType;
    }

    public void setCcType(String ccType) {
        this.ccType = ccType;
    }

    public Double getCostHour() {
        return costHour;
    }

    public void setCostHour(Double costHour) {
        this.costHour = costHour;
    }

    public Double getGastoHs() {
        return gastoHs;
    }

    public void setGastoHs(Double gastoHs) {
        this.gastoHs = gastoHs;
    }

    public Double getGastoHm() {
        return gastoHm;
    }

    public void setGastoHm(Double gastoHm) {
        this.gastoHm = gastoHm;
    }

    public Double getGastoHd() {
        return gastoHd;
    }

    public void setGastoHd(Double gastoHd) {
        this.gastoHd = gastoHd;
    }

    public String getCurName() {
        return curName;
    }

    public void setCurName(String curName) {
        this.curName = curName;
    }

    public String getCurSimbol() {
        return curSimbol;
    }

    public void setCurSimbol(String curSimbol) {
        this.curSimbol = curSimbol;
    }
}
