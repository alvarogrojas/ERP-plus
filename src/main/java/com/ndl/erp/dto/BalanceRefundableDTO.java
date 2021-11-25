package com.ndl.erp.dto;

import com.ndl.erp.domain.*;

import java.util.Date;


public class BalanceRefundableDTO implements Comparable<BalanceRefundableDTO> {

    private static final long serialVersionUID = 1777000000000000039L;


    private Integer idMC;
    private String statusMC;
    private Double buyMC;
    private Double  saleMC;

    private Integer idBillPay;
    private Integer idRf;
    private Integer idRfd;
    private Integer idCostCenter;
    private Integer idCurrency;
    private String type;
    private Date dateBillPay;
    private Date dateRfd;
    private String description;
    private Double mount;
    private Double taxPorcent;
    private Double tax;
    private Double subTotal;
    private CostCenter costCenter;

    public BalanceRefundableDTO() {
    }

    public BalanceRefundableDTO(Integer mcId,
                        String mcStatus,
                        Double changeTypeBuy,
                        Double changeTypeSale,
                        BillPay bp,
                        Refundable km,
                                RefundableDetail bpd
    ) {
        this.idMC = mcId;
        this.statusMC = mcStatus;
        this.buyMC = changeTypeBuy;
        this.saleMC = changeTypeSale;
        idBillPay = bp.getId();
        idRf = km.getId();
        idRfd = bpd.getCostCenter().getId();
        idCurrency = km.getCurrency().getId();
        this.dateBillPay = bp.getBillDate();
        dateRfd = km.getDateInvoice();
        this.mount = bpd.getMount();
        this.description = bpd.getDescription();
        tax = bpd.getTax();
        taxPorcent = bpd.getTaxPorcent();
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

    public Integer getIdRf() {
        return idRf;
    }

    public void setIdRf(Integer idRf) {
        this.idRf = idRf;
    }

    public Integer getIdRfd() {
        return idRfd;
    }

    public void setIdRfd(Integer idRfd) {
        this.idRfd = idRfd;
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

    public Date getDateRfd() {
        return dateRfd;
    }

    public void setDateRfd(Date dateRfd) {
        this.dateRfd = dateRfd;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getMount() {
        return mount;
    }

    public void setMount(Double mount) {
        this.mount = mount;
    }

    public Double getTaxPorcent() {
        return taxPorcent;
    }

    public void setTaxPorcent(Double taxPorcent) {
        this.taxPorcent = taxPorcent;
    }

    public Double getTax() {
        return tax;
    }

    public void setTax(Double tax) {
        this.tax = tax;
    }

    public Double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(Double subTotal) {
        this.subTotal = subTotal;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public CostCenter getCostCenter() {
        return costCenter;
    }

    public void setCostCenter(CostCenter costCenter) {
        this.costCenter = costCenter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (o instanceof BalanceRefundableDTO) {
            BalanceRefundableDTO that = (BalanceRefundableDTO) o;
            if (idRfd != null) {

                return idRfd.equals(that.idRfd);
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
        return idRfd.hashCode();
    }

    @Override
    public int compareTo( final BalanceRefundableDTO o) {
        return Integer.compare(this.idRfd, o.idRfd);
    }
}
