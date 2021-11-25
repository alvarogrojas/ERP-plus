
package com.ndl.erp.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.sql.Date;

@Entity
public class InvoiceDetail {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "parent_id", referencedColumnName = "id")
    private Invoice invoice;

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


    private String description;
    private String type;
    private Double quantity;
    private Double price;
    private Double total;
    private Integer userId;
    private Date createAt;
    private Date updateAt;
    private Integer pocdId;
    private Double tax;
    private Double subTotal;
    private Double discountPorcentage = 0d;
    private Double discountAmount = 0d;
    private boolean salidaBodega;
    private Double quantityReturned = 0d;
    private String codigoCabys;
    private String codigoCabysBodega;
    private Integer parentCabysId;
    private Double exonerated;

    private String tipoDescuento="Global";

    @OneToOne
    @JoinColumn(name="descuentos_id", referencedColumnName="id")
    private Descuentos descuentos;


    /*@Override
    public int compareTo(InvoiceDetail invoiceDetail) {

        if (this.indexLine==invoiceDetail.indexLine) {
            return 0;
        } else if(this.indexLine > invoiceDetail.indexLine) {
            return 1;
        } else {
            return -1;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InvoiceDetail detail = (InvoiceDetail) o;
        return id.equals(detail.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }*/

    public Inventario getInventario() {
        return inventario;
    }

    public void setInventario(Inventario inventario) {
        this.inventario = inventario;
    }


    public Double getQuantityReturned() {
        return quantityReturned;
    }

    public void setQuantityReturned(Double quantityReturned) {
        this.quantityReturned = quantityReturned;
    }

    public boolean isSalidaBodega() {
        return salidaBodega;
    }

    public void setSalidaBodega(boolean salidaBodega) {
        this.salidaBodega = salidaBodega;
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

    public Integer getPocdId() {
        return pocdId;
    }

    public void setPocdId(Integer pocdId) {
        this.pocdId = pocdId;
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

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public TaxesIva getIva() {
        return iva;
    }

    public void setIva(TaxesIva iva) {
        this.iva = iva;
    }


    public CostCenter getCostCenter() {
        return costCenter;
    }

    public void setCostCenter(CostCenter costCenter) {
        this.costCenter = costCenter;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

    public String getTipoDescuento() {
        return tipoDescuento;
    }

    public void setTipoDescuento(String tipoDescuento) {
        this.tipoDescuento = tipoDescuento;
    }
}
