package com.ndl.erp.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.ndl.erp.util.DateUtil;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.ndl.erp.constants.BillPayNotaCreditoConstants.BILL_PAY_NOTA_CREDITO_EDICION;


@Entity
public class BillPayNotaCredito{

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;


    public BillPayNotaCredito() {

    }

    public BillPayNotaCredito(BillPay billPay) {
        BeanUtils.copyProperties(billPay, this);
        this.id = null;
        this.billPay = billPay;
        this.status = BILL_PAY_NOTA_CREDITO_EDICION;
        this.salidaBodega = false;
        this.addDetails(billPay);
    }

    private void addDetails(BillPay billPay) {
        if (billPay.getDetails()==null) {
            return;
        }
        if (this.details==null) {
            this.details = new ArrayList<>();
        }
        BillPayNotaCreditoDetail billPayNCD;
        for (BillPayDetail bpd: billPay.getDetails()) {
            billPayNCD = new BillPayNotaCreditoDetail(bpd);
            billPayNCD.setId(null);
            billPayNCD.setSalidaBodega(false);
            billPayNCD.setCreateAt(new java.sql.Date(DateUtil.getCurrentCalendar().getTime().getTime()));
            billPayNCD.setUpdateAt(new java.sql.Date(DateUtil.getCurrentCalendar().getTime().getTime()));
            this.details.add(billPayNCD);
        }
    }


    @OneToOne
    @JoinColumn(name="bill_pay_id", referencedColumnName="id")
    private BillPay billPay;


    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL,
            mappedBy = "billPayNotaCredito", orphanRemoval = true)
    private List<BillPayNotaCreditoDetail> details = new ArrayList<BillPayNotaCreditoDetail>(0);

    private Integer number;
    private Date date;
    private Double subTotal;
    private Double iv;
    private Double total;
    private String status;

    @OneToOne
    @JoinColumn(name="provider_id", referencedColumnName="id")
    private Provider  provider;

    @OneToOne
    @JoinColumn(name="currency_id", referencedColumnName="id")
    private Currency  currency;


    private Integer  userId;

    private Date createAt;
    private Date updateAt;
    private String clave;
    private String codigo;

    @OneToOne
    @JoinColumn(name="tipo_actividad_economica_id", referencedColumnName="id")
    private  EconomicActivity economicActivity;

    private Double exonerated;
    private Boolean salidaBodega;
    private Double discountTotal;
    private String consecutivo;
    private String consecutivoReferencia;

    public String getConsecutivo() {
        return consecutivo;
    }

    public void setConsecutivo(String consecutivo) {
        this.consecutivo = consecutivo;
    }

    public String getConsecutivoReferencia() {
        return consecutivoReferencia;
    }

    public void setConsecutivoReferencia(String consecutivoReferencia) {
        this.consecutivoReferencia = consecutivoReferencia;
    }



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BillPay getBillPay() {
        return billPay;
    }

    public void setBillPay(BillPay billPay) {
        this.billPay = billPay;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(Double subTotal) {
        this.subTotal = subTotal;
    }

    public Double getIv() {
        return iv;
    }

    public void setIv(Double iv) {
        this.iv = iv;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public List<BillPayNotaCreditoDetail> getDetails() {
        return details;
    }

    public void setDetails(List<BillPayNotaCreditoDetail> details) {
        this.details = details;
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

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public EconomicActivity getEconomicActivity() {
        return economicActivity;
    }

    public void setEconomicActivity(EconomicActivity economicActivity) {
        this.economicActivity = economicActivity;
    }

    public Double getExonerated() {
        return exonerated;
    }

    public void setExonerated(Double exonerated) {
        this.exonerated = exonerated;
    }

    public Boolean getSalidaBodega() {
        return salidaBodega;
    }

    public void setSalidaBodega(Boolean salidaBodega) {
        this.salidaBodega = salidaBodega;
    }

    public Double getDiscountTotal() {
        return discountTotal;
    }

    public void setDiscountTotal(Double discountTotal) {
        this.discountTotal = discountTotal;
    }
}
