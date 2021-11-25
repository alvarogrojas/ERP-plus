package com.ndl.erp.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
public class RefundableDetail implements Serializable {

    @Id
    @GeneratedValue
    private Integer id;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "refundable_id", referencedColumnName = "id")
    private Refundable refundable;

    @OneToOne
    @JoinColumn(name="cost_center_id", referencedColumnName="id")
    private CostCenter costCenter;

    @OneToOne
    @JoinColumn(name="inventario_id", referencedColumnName="id")
    private Inventario inventario;

    @OneToOne
    @JoinColumn(name="bodega_id", referencedColumnName="id")
    private Bodega bodega;

    @OneToOne
    @JoinColumn(name="producto_id", referencedColumnName="id")
    private Producto producto;

    private Date dateDetail;

    private String description;
    private String provider;

    private String billNumber ;

    private String status;

    private String type;
    private Double mount;

    private Double tax;
    private Double taxPorcent;

    private Double subTotal;

    private Date createDate;

    private Integer idUser;

    private Date updateDate;

    private String typeLine;


    private Double quantity;
    private Double price;
    private boolean ingresadoBodega = false;
    private String barcode;




//    private String codigoCabys;
//    private String codigoIngpro;
//    private String typeExpend = "Servicio";
//    private Integer parentCabysId;
//
//


    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public Bodega getBodega() {
        return bodega;
    }

    public void setBodega(Bodega bodega) {
        this.bodega = bodega;
    }

    public Inventario getInventario() {
        return inventario;
    }

    public void setInventario(Inventario inventario) {
        this.inventario = inventario;
    }

    public String getTypeLine() {
        return typeLine;
    }

    public void setTypeLine(String typeLine) {
        this.typeLine = typeLine;
    }

    public boolean isIngresadoBodega() {
        return ingresadoBodega;
    }

    public void setIngresadoBodega(boolean ingresadoBodega) {
        this.ingresadoBodega = ingresadoBodega;
    }

    public Double getTaxPorcent() {
        return taxPorcent;
    }

    public void setTaxPorcent(Double taxPorcent) {
        this.taxPorcent = taxPorcent;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Refundable getRefundable() {
        return refundable;
    }

    public void setRefundable(Refundable refundable) {
        this.refundable = refundable;
    }

    public CostCenter getCostCenter() {
        return costCenter;
    }

    public void setCostCenter(CostCenter costCenter) {
        this.costCenter = costCenter;
    }

    public Date getDateDetail() {
        return dateDetail;
    }

    public void setDateDetail(Date dateDetail) {
        this.dateDetail = dateDetail;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBillNumber() {
        return billNumber;
    }

    public void setBillNumber(String billNumber) {
        this.billNumber = billNumber;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getMount() {
        return mount;
    }

    public void setMount(Double mount) {
        this.mount = mount;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
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
}
