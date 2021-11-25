package com.ndl.erp.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.sql.Date;

@Entity
public class PurchaseOrderClientDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "parent_id", referencedColumnName = "id")
    private PurchaseOrderClient parent;

    private Integer indexLine;

    @OneToOne
    @JoinColumn(name="cost_center_id", referencedColumnName="id")
    private CostCenter costCenter;

    @OneToOne
    @JoinColumn(name="iva_id", referencedColumnName="id")
    private TaxesIva iva;

    @OneToOne
    @JoinColumn(name="inventario_id", referencedColumnName="id")
    private Inventario inventario;

    private Double discountPorcentage = 0d;
    private Double discountAmount = 0d;


    private String description;
    private Double quantity;
    private Double price;
    private Double subTotal;

    private Double total;

    private String applyTax;
    private Double porTax;
    private Double tax;
    private Double taxBilled = 0d;
    private Float porBilled = 0f;
    private Double mtoBilled = 0d;
    private Double exonerated;

    private Integer userId;
    private Date createAt;
    private Date updateAt;

    private String codigoCabys;

    private String codigoCabysBodega;
    private String type;

    private Integer inventarioItemId;

    @OneToOne
    @JoinColumn(name="descuentos_id", referencedColumnName="id")
    private Descuentos descuentos;

    private Integer serviceCabysId;


    public Inventario getInventario() {
        return inventario;
    }

    public void setInventario(Inventario inventario) {
        this.inventario = inventario;
    }

    public PurchaseOrderClientDetail() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public PurchaseOrderClient getParent() {
        return parent;
    }

    public void setParent(PurchaseOrderClient parent) {
        this.parent = parent;
    }

    public Integer getIndexLine() {
        return indexLine;
    }

    public void setIndexLine(Integer indexLine) {
        this.indexLine = indexLine;
    }

    public CostCenter getCostCenter() {
        return costCenter;
    }

    public void setCostCenter(CostCenter costCenter) {
        this.costCenter = costCenter;
    }

    public TaxesIva getIva() {
        return iva;
    }

    public void setIva(TaxesIva iva) {
        this.iva = iva;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getApplyTax() {
        return applyTax;
    }

    public void setApplyTax(String applyTax) {
        this.applyTax = applyTax;
    }

    public Double getPorTax() {
        return porTax;
    }

    public void setPorTax(Double porTax) {
        this.porTax = porTax;
    }

    public Double getTax() {
        return tax;
    }

    public void setTax(Double tax) {
        this.tax = tax;
    }

    public Double getTaxBilled() {
        return taxBilled;
    }

    public void setTaxBilled(Double taxBilled) {
        this.taxBilled = taxBilled;
    }

    public Float getPorBilled() {
        return porBilled;
    }

    public void setPorBilled(Float porBilled) {
        this.porBilled = porBilled;
    }

    public Double getMtoBilled() {
        return mtoBilled;
    }

    public void setMtoBilled(Double mtoBilled) {
        this.mtoBilled = mtoBilled;
    }

    public Double getExonerated() {
        return exonerated;
    }

    public void setExonerated(Double exonerated) {
        this.exonerated = exonerated;
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


    public Integer getInventarioItemId() {
        return inventarioItemId;
    }

    public void setInventarioItemId(Integer inventarioItemId) {
        this.inventarioItemId = inventarioItemId;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getServiceCabysId() {
        return serviceCabysId;
    }

    public void setServiceCabysId(Integer serviceCabysId) {
        this.serviceCabysId = serviceCabysId;
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

    public Descuentos getDescuentos() {
        return descuentos;
    }

    public void setDescuentos(Descuentos descuentos) {
        this.descuentos = descuentos;
    }
}


