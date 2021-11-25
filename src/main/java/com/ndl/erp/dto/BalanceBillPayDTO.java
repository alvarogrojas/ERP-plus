package com.ndl.erp.dto;

import com.ndl.erp.domain.BillPay;
import com.ndl.erp.domain.BillPayDetail;
import com.ndl.erp.domain.CostCenter;

import java.io.Serializable;
import java.util.Date;

public class BalanceBillPayDTO implements Serializable{
    private static final long serialVersionUID = 1777000000000000037L;



    private Integer idMC;
    private String statusMC;
    private Double buyMC;
    private Double  saleMC;

    private Integer idBillPayDetail;
    private Integer idBillPay;
    private Integer idCostCenter;
    private Integer idCurrency;

    private Double  quantity;
    private Double  price;
    private Double  tax;
    private Double  taxPorcent;
    private Double  discount;
    private Double  creditNoteMto;
    private Double  subTotal;
    private Double  total;
    private String  groceryCode; //Codigo de bodega
    private String  detail;
    private String  type;
    private Date    billDate;
    private CostCenter costCenter;



    public BalanceBillPayDTO() {
    }

    public BalanceBillPayDTO(Integer mcId,
                             String mcStatus,
                             Double changeTypeBuy,
                             Double changeTypeSale,
                             BillPay bp,
                             BillPayDetail bpd
    ) {
        this.idMC = mcId;
        this.statusMC = mcStatus;
        this.buyMC = changeTypeBuy;
        this.saleMC = changeTypeSale;
        idBillPay = bpd.getBillPay().getId();
        idBillPayDetail = bpd.getCostCenter().getId();
        idCurrency = bp.getCurrency().getId();
        billDate = bp.getBillDate();
        quantity = bpd.getQuantity();
        price = bpd.getPrice();
        tax = bpd.getTax();
        taxPorcent = bpd.getTaxPorcent();
        discount = bpd.getDiscount();
        creditNoteMto = bpd.getCreditNoteMto();
        subTotal = bpd.getSubTotal();
        total = bpd.getTotal();
        groceryCode = bpd.getGroceryCode();
        detail = bpd.getDetail();
        type = "CXP";
        costCenter = bpd.getCostCenter();
        this.idCostCenter = costCenter.getId();
    }


    //Metodos generados.


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

    public Integer getIdBillPayDetail() {
        return idBillPayDetail;
    }

    public void setIdBillPayDetail(Integer idBillPayDetail) {
        this.idBillPayDetail = idBillPayDetail;
    }

    public Integer getIdBillPay() {
        return idBillPay;
    }

    public void setIdBillPay(Integer idBillPay) {
        this.idBillPay = idBillPay;
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

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getTax() {
        return tax;
    }

    public void setTax(Double tax) {
        this.tax = tax;
    }

    public Double getTaxPorcent() {
        return taxPorcent;
    }

    public void setTaxPorcent(Double taxPorcent) {
        this.taxPorcent = taxPorcent;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Double getCreditNoteMto() {
        return creditNoteMto;
    }

    public void setCreditNoteMto(Double creditNoteMto) {
        this.creditNoteMto = creditNoteMto;
    }

    public Double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(Double subTotal) {
        this.subTotal = subTotal;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getGroceryCode() {
        return groceryCode;
    }

    public void setGroceryCode(String groceryCode) {
        this.groceryCode = groceryCode;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getBillDate() {
        return billDate;
    }

    public void setBillDate(Date billDate) {
        this.billDate = billDate;
    }

    public CostCenter getCostCenter() {
        return costCenter;
    }

    public void setCostCenter(CostCenter costCenter) {
        this.costCenter = costCenter;
    }
}
