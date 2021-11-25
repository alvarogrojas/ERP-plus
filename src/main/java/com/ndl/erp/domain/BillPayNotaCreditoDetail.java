package com.ndl.erp.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.Date;

@Entity
public class BillPayNotaCreditoDetail {


    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "parent_id", referencedColumnName = "id")
    private BillPayNotaCredito billPayNotaCredito;

    private Integer indexLine;
    private String description;
    private String type;
    private Double quantity;
    private Double price;
    private Double tax;
    private Double subTotal;
    private Double total;

    private Integer userId;

    private Date createAt;
    private Date updateAt;
    private Double exonerated;
    private String codigoCabys;
    private String codigoCabysBodega;
    private Integer parentCabysId;
    private boolean salidaBodega;

    @OneToOne
    @JoinColumn(name="cost_center_id", referencedColumnName="id")
    private CostCenter costCenter;

    @OneToOne
    @JoinColumn(name="inventario_id", referencedColumnName="id")
    private Inventario inventario;


    @OneToOne
    @JoinColumn(name="bill_pay_detail_id", referencedColumnName="id")
    private BillPayDetail billPayDetail;

//    private Integer BillPayDetailId;
    private Double discountPorcentage;
    private Double discountAmount;


    public BillPayNotaCreditoDetail() {
    }

    public BillPayNotaCreditoDetail(BillPayDetail d) {

        this.id=d.getId();
        this.costCenter = d.getCostCenter();
        this.description = d.getDetail();
        this.type = d.getType();
        this.quantity = d.getQuantity();
        this.price = d.getPrice();
        this.tax = d.getTax();
        this.subTotal = d.getSubTotal();
        this.total = d.getTotal();
        this.userId = d.getIdUser();
        this.createAt = d.getCreateDate();
        this.updateAt = d.getUpdateDate();
        this.inventario = d.getInventario();
        this.setSalidaBodega(false);
    }



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BillPayNotaCredito getBillPayNotaCredito() {
        return billPayNotaCredito;
    }

    public void setBillPayNotaCredito(BillPayNotaCredito billPayNotaCredito) {
        this.billPayNotaCredito = billPayNotaCredito;
    }

    public Integer getIndexLine() {
        return indexLine;
    }

    public void setIndexLine(Integer indexLine) {
        this.indexLine = indexLine;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public Double getExonerated() {
        return exonerated;
    }

    public void setExonerated(Double exonerated) {
        this.exonerated = exonerated;
    }

    public String getCodigoCabys() {
        return codigoCabys;
    }

    public void setCodigoCabys(String codigoCabys) {
        this.codigoCabys = codigoCabys;
    }

    public String getCodigoCabysBodega() {
        return codigoCabysBodega;
    }

    public void setCodigoCabysBodega(String codigoCabysBodega) {
        this.codigoCabysBodega = codigoCabysBodega;
    }

    public Integer getParentCabysId() {
        return parentCabysId;
    }

    public void setParentCabysId(Integer parentCabysId) {
        this.parentCabysId = parentCabysId;
    }

    public boolean isSalidaBodega() {
        return salidaBodega;
    }

    public void setSalidaBodega(boolean salidaBodega) {
        this.salidaBodega = salidaBodega;
    }

    public CostCenter getCostCenter() {
        return costCenter;
    }

    public void setCostCenter(CostCenter costCenter) {
        this.costCenter = costCenter;
    }

    public Inventario getInventario() {
        return inventario;
    }

    public void setInventario(Inventario inventario) {
        this.inventario = inventario;
    }

//    public Integer getBillPayDetailId() {
//        return BillPayDetailId;
//    }
//
//    public void setBillPayDetailId(Integer billPayDetailId) {
//        BillPayDetailId = billPayDetailId;
//    }


    public BillPayDetail getBillPayDetail() {
        return billPayDetail;
    }

    public void setBillPayDetail(BillPayDetail billPayDetail) {
        this.billPayDetail = billPayDetail;
    }

    public Double getDiscountPorcentage() {
        return discountPorcentage;
    }

    public void setDiscountPorcentage(Double discountPorcentage) {
        this.discountPorcentage = discountPorcentage;
    }

    public Double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(Double discountAmount) {
        this.discountAmount = discountAmount;
    }
}
