package com.ndl.erp.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.sql.Date;

@Entity
public class InvoiceNotaCreditoDetail {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;


    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "parent_id", referencedColumnName = "id")
    private InvoiceNotaCredito invoiceNotaCredito;

    @OneToOne
    @JoinColumn(name="inventario_id", referencedColumnName="id")
    private Inventario inventario;

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
    private Boolean ingresadoBodega;
    private Integer invoiceDetailId;


    @OneToOne
    @JoinColumn(name="iva_id", referencedColumnName="id")
    private TaxesIva iva;

    @OneToOne
    @JoinColumn(name="cost_center_id", referencedColumnName="id")
    private CostCenter costCenter;

    public Integer getInvoiceDetailId() {
        return invoiceDetailId;
    }

    public void setInvoiceDetailId(Integer invoiceDetailId) {
        this.invoiceDetailId = invoiceDetailId;
    }

    public Inventario getInventario() {
        return inventario;
    }

    public void setInventario(Inventario inventario) {
        this.inventario = inventario;
    }


    public CostCenter getCostCenter() {
        return costCenter;
    }

    public void setCostCenter(CostCenter costCenter) {
        this.costCenter = costCenter;
    }

    public Boolean getIngresadoBodega() {
        return ingresadoBodega;
    }

    public void setIngresadoBodega(Boolean ingresadoBodega) {
        this.ingresadoBodega = ingresadoBodega;
    }

    private String codigoCabys;

    private String codigoCabysBodega;

    public InvoiceNotaCreditoDetail() {}

    public InvoiceNotaCreditoDetail(InvoiceDetail d) {
        this.id=d.getId();
        this.indexLine = d.getIndexLine();
        this.costCenter = d.getCostCenter();
        this.description = d.getDescription();
        this.type = d.getType();
        this.quantity = d.getQuantity();
        this.price = d.getPrice();
        this.tax = d.getTax();
        this.subTotal = d.getSubTotal();
        this.total = d.getTotal();
        this.userId = d.getUserId();
        createAt = d.getCreateAt();
        updateAt = d.getUpdateAt();
        this.iva = d.getIva();
        this.exonerated = d.getExonerated();
        this.inventario = d.getInventario();

    }



    private Double exonerated;
    private Double discountPorcentage = 0d;
    private Double discountAmount = 0d;


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

    public Double getExonerated() {
        return exonerated;
    }

    public void setExonerated(Double exonerated) {
        this.exonerated = exonerated;
    }

    public Double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(Double subTotal) {
        this.subTotal = subTotal;
    }

    public Double getTax() {
        return tax;
    }

    public void setTax(Double tax) {
        this.tax = tax;
    }


    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }



    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getIndexLine() {
        return indexLine;
    }

    public void setIndexLine(Integer indexLine) {
        this.indexLine = indexLine;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public InvoiceNotaCredito getInvoiceNotaCredito() {
        return invoiceNotaCredito;
    }

    public void setInvoiceNotaCredito(InvoiceNotaCredito invoiceNotaCredito) {
        this.invoiceNotaCredito = invoiceNotaCredito;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public TaxesIva getIva() {
        return iva;
    }

    public void setIva(TaxesIva iva) {
        this.iva = iva;
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
}
