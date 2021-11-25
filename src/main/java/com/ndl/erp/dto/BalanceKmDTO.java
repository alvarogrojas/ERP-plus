package com.ndl.erp.dto;

import com.ndl.erp.domain.BillPay;
import com.ndl.erp.domain.CostCenter;
import com.ndl.erp.domain.Kilometer;
import com.ndl.erp.domain.KilometerDetail;

import java.util.Date;

public class BalanceKmDTO implements Comparable<BalanceKmDTO> {
    private static final long serialVersionUID = 1777000000000000038L;


    private Integer idMC;
    private String statusMC;
    private Double buyMC;
    private Double  saleMC;

    private Integer idBillPay;
    private Integer idKm;
    private Integer idKmd;
    private Integer idCostCenter;
    private Integer idCurrency;
    private String type;
    private Date dateBillPay;
    private Date dateKm;
    private String description;
    private Double km;
    private Double payFactor;
    private Double subTotal;
    private CostCenter costCenter;

    public BalanceKmDTO() {
    }

    public BalanceKmDTO(Integer mcId,
                             String mcStatus,
                             Double changeTypeBuy,
                             Double changeTypeSale,
                             BillPay bp,
                            Kilometer km,
                            KilometerDetail bpd
    ) {
        this.idMC = mcId;
        this.statusMC = mcStatus;
        this.buyMC = changeTypeBuy;
        this.saleMC = changeTypeSale;
        idBillPay = bp.getId();
        idKm = km.getId();
        idKmd = bpd.getCostCenter().getId();
        idCurrency = km.getCurrency().getId();
        this.dateBillPay = bp.getBillDate();
        dateKm = km.getDateKm();
        this.km = bpd.getKm();
        this.description = bpd.getDescription();
        payFactor = bpd.getPayFactor();
        subTotal = bpd.getSubTotal();
        costCenter = bpd.getCostCenter();
        this.idCostCenter = costCenter.getId();

    }

    public Integer getIdMC() {
        return idMC;
    }

    public void setIdMC(Integer idMC) {
        this.idMC = idMC;
    }

    public String getStatusMC() {
        return statusMC;
    }

    public void setStatusMC(String statusMC) {
        this.statusMC = statusMC;
    }

    public Double getBuyMC() {
        return buyMC;
    }

    public void setBuyMC(Double buyMC) {
        this.buyMC = buyMC;
    }

    public Double getSaleMC() {
        return saleMC;
    }

    public void setSaleMC(Double saleMC) {
        this.saleMC = saleMC;
    }

    public Integer getIdBillPay() {
        return idBillPay;
    }

    public void setIdBillPay(Integer idBillPay) {
        this.idBillPay = idBillPay;
    }

    public Integer getIdKm() {
        return idKm;
    }

    public void setIdKm(Integer idKm) {
        this.idKm = idKm;
    }

    public Integer getIdKmd() {
        return idKmd;
    }

    public void setIdKmd(Integer idKmd) {
        this.idKmd = idKmd;
    }

    public Integer getIdCostCenter() {
        return idCostCenter;
    }

    public void setIdCostCenter(Integer idCostCenter) {
        this.idCostCenter = idCostCenter;
    }

    public Integer getIdCurrency() {
        return idCurrency;
    }

    public void setIdCurrency(Integer idCurrency) {
        this.idCurrency = idCurrency;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getDateBillPay() {
        return dateBillPay;
    }

    public void setDateBillPay(Date dateBillPay) {
        this.dateBillPay = dateBillPay;
    }

    public Date getDateKm() {
        return dateKm;
    }

    public void setDateKm(Date dateKm) {
        this.dateKm = dateKm;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getKm() {
        return km;
    }

    public void setKm(Double km) {
        this.km = km;
    }

    public Double getPayFactor() {
        return payFactor;
    }

    public void setPayFactor(Double payFactor) {
        this.payFactor = payFactor;
    }

    public Double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(Double subTotal) {
        this.subTotal = subTotal;
    }

    public CostCenter getCostCenter() {
        return this.costCenter;
    }

    public void setCostCenter(CostCenter costCenter) {
        this.costCenter = costCenter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (o instanceof BalanceKmDTO) {
            BalanceKmDTO that = (BalanceKmDTO) o;
            if (idKmd != null) {

                return idKmd.equals(that.idKmd);
            } else {
                return false;
            }
//            that.id == null;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return idKmd.hashCode();
    }

    @Override
    public int compareTo( final BalanceKmDTO o) {
        return Integer.compare(this.idKmd, o.idKmd);
    }

}
