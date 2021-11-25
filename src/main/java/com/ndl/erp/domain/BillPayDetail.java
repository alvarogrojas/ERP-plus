package com.ndl.erp.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
public class BillPayDetail implements Serializable {

    @Id
    @GeneratedValue
    private Integer id;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bill_pay_id", referencedColumnName = "id")
    private BillPay billPay;

    private String detail;
    private Double quantity;
    private Double price;
    private Double tax;
    private Double discount;
    private String creditNoteNumber;
    private Double creditNoteMto;
    private String status;
    private String groceryCode;
    private Double subTotal;
    private Double taxPorcent;
    private Double total;

    @OneToOne
    @JoinColumn(name="cost_center_id", referencedColumnName="id")
    private CostCenter costCenter;


    @OneToOne
    @JoinColumn(name="inventario_id", referencedColumnName="id")
    private Inventario inventario;

    @OneToOne
    @JoinColumn(name="producto_id", referencedColumnName="id")
    private Producto producto;

    @OneToOne
    @JoinColumn(name="bodega_id", referencedColumnName="id")
    private Bodega bodega;



    private Date createDate;
    private Integer idUser;
    private Date updateDate;

    private String codigoCabys;
    private String codigoIngpro;
    private String type = "Servicio";
    private Integer parentCabysId;
    private String barcode;


    @Column(columnDefinition = "boolean default false")
    private Boolean ingresadoBodega = false;

    public Bodega getBodega() {
        return bodega;
    }

    public void setBodega(Bodega bodega) {
        this.bodega = bodega;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public Inventario getInventario() {
        return inventario;
    }

    public void setInventario(Inventario inventario) {
        this.inventario = inventario;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Boolean getIngresadoBodega() {
        return ingresadoBodega;
    }

    public void setIngresadoBodega(Boolean ingresadoBodega) {
        this.ingresadoBodega = ingresadoBodega;
    }

    public Double getTaxPorcent() {
        return taxPorcent;
    }

    public void setTaxPorcent(Double taxPorcent) {
        this.taxPorcent = taxPorcent;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getGroceryCode() {
        return groceryCode;
    }

    public void setGroceryCode(String groceryCode) {
        this.groceryCode = groceryCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getCreditNoteMto() {
        return creditNoteMto;
    }

    public void setCreditNoteMto(Double creditNoteMto) {
        this.creditNoteMto = creditNoteMto;
    }

    public String getCreditNoteNumber() {
        return creditNoteNumber;
    }

    public void setCreditNoteNumber(String creditNoteNumber) {
        this.creditNoteNumber = creditNoteNumber;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Double getTax() {
        return tax;
    }

    public void setTax(Double tax) {
        this.tax = tax;
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


    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public BillPay getBillPay() {
        return billPay;
    }

    public void setBillPay(BillPay billPay) {
        this.billPay = billPay;
    }

    public CostCenter getCostCenter() {
        return costCenter;
    }

    public void setCostCenter(CostCenter costCenter) {
        this.costCenter = costCenter;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getCodigoCabys() {
        return codigoCabys;
    }

    public void setCodigoCabys(String codigoCabys) {
        this.codigoCabys = codigoCabys;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getParentCabysId() {
        return parentCabysId;
    }

    public void setParentCabysId(Integer parentCabysId) {
        this.parentCabysId = parentCabysId;
    }

    public String getCodigoIngpro() {
        return codigoIngpro;
    }

    public void setCodigoIngpro(String codigoIngpro) {
        this.codigoIngpro = codigoIngpro;
    }
}
