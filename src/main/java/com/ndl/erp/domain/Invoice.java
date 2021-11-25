package com.ndl.erp.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import static com.ndl.erp.constants.InvoiceConstants.INVOICE_TIPO_TRANSACCION_FE;

@Entity
public class Invoice {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private Integer number;
    private Date date;
    private Date datePay;

    private String phone;
    private String address;
    private Integer creditDays;
    private Double subTotal = 0d;
    private Double iv =  0d;
    private Double total =  0d;
    private String status = "Edicion";
    private boolean salidaBodega;

    private String tipoDescuento="Global";

    @OneToOne
    @JoinColumn(name="purchase_order_client_id", referencedColumnName="id")
    private PurchaseOrderClient poc;

    @OneToOne
    @JoinColumn(name="client_id", referencedColumnName="clientId")
    private Client client;

    @OneToOne
    @JoinColumn(name="currency_id", referencedColumnName="id")
    private Currency currency;

    private Integer userId;

    @OneToOne
    @JoinColumn(name="bill_collect_id", referencedColumnName="id")
    private BillCollect billCollect;


    private Date createAt;
    private Date updateAt;

    private String observaciones;


    private String clave;
    private String consecutive;
    private String consecutivo;

    private Boolean hacienda;

    private Integer enviadaHacienda = -1;
    private Integer esClienteInternacional = 0;

    private String estadoHacienda = "no_enviada";
    private Integer billSenderId;

    @OneToOne
    @JoinColumn(name="tipo_actividad_economica_id", referencedColumnName="id")
    private EconomicActivity economicActivity;

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL,
            mappedBy = "invoice", orphanRemoval = true)
    @OrderBy("indexLine ASC")
    private Set<InvoiceDetail> details = new TreeSet<>();

    @OneToOne
    @JoinColumn(name="session_pos_id", referencedColumnName="id")
    private SessionPos sessionPos;

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL,
            mappedBy = "invoice", orphanRemoval = true)
    private List<InvoiceMedioPago> medioPagoDetails = new ArrayList<InvoiceMedioPago>();



    @OneToOne
    @JoinColumn(name = "exchange_rate_id", referencedColumnName = "id")
    private ExchangeRate exchangeRate;

    public ExchangeRate getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(ExchangeRate exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public List<InvoiceMedioPago> getMedioPagoDetails() {
        return medioPagoDetails;
    }

    public void setMedioPagoDetails(List<InvoiceMedioPago> medioPagoDetails) {
        this.medioPagoDetails = medioPagoDetails;
    }

    private String tipoTransaccion = INVOICE_TIPO_TRANSACCION_FE;


    private Double discountTotal = 0d;

    public String getTipoTransaccion() {
        return tipoTransaccion;
    }

    public void setTipoTransaccion(String tipoTransaccion) {
        this.tipoTransaccion = tipoTransaccion;
    }

    public SessionPos getSessionPos() {
        return sessionPos;
    }

    public void setSessionPos(SessionPos sessionPos) {
        this.sessionPos = sessionPos;
    }

    public boolean isSalidaBodega() {
        return salidaBodega;
    }

    public void setSalidaBodega(boolean salidaBodega) {
        this.salidaBodega = salidaBodega;
    }

    private Double exonerated  = 0d;

    public Date getDatePay() {
        return datePay;
    }

    public void setDatePay(Date datePay) {
        this.datePay = datePay;
    }

    public BillCollect getBillCollect() {
        return billCollect;
    }

    public void setBillCollect(BillCollect billCollect) {
        this.billCollect = billCollect;
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

    public Set<InvoiceDetail> getDetails() {
        return details;
    }

    public void setDetails(Set<InvoiceDetail> details) {
        this.details = details;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public PurchaseOrderClient getPoc() {
        return poc;
    }

    public void setPoc(PurchaseOrderClient poc) {
        this.poc = poc;
    }


    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getConsecutive() {
        return consecutive;
    }

    public void setConsecutive(String consecutive) {
        this.consecutive = consecutive;
    }

    public EconomicActivity getEconomicActivity() {
        return economicActivity;
    }

    public void setEconomicActivity(EconomicActivity economicActivity) {
        this.economicActivity = economicActivity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Double getIv() {
        return iv;
    }

    public void setIv(Double iv) {
        this.iv = iv;
    }

    public Double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(Double subTotal) {
        this.subTotal = subTotal;
    }

    public Integer getCreditDays() {
        return creditDays;
    }

    public void setCreditDays(Integer creditDays) {
        this.creditDays = creditDays;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public Boolean getHacienda() {
        return hacienda;
    }

    public void setHacienda(Boolean hacienda) {
        this.hacienda = hacienda;
    }

    public Integer getEsClienteInternacional() {
        return esClienteInternacional;
    }

    public void setEsClienteInternacional(Integer esClienteInternacional) {
        this.esClienteInternacional = esClienteInternacional;
    }

    public String getEstadoHacienda() {
        return estadoHacienda;
    }

    public void setEstadoHacienda(String estadoHacienda) {
        this.estadoHacienda = estadoHacienda;
    }

    public Integer getEnviadaHacienda() {
        return enviadaHacienda;
    }

    public void setEnviadaHacienda(Integer enviadaHacienda) {
        this.enviadaHacienda = enviadaHacienda;
    }

    public String getConsecutivo() {
        return consecutivo;
    }

    public void setConsecutivo(String consecutivo) {
        this.consecutivo = consecutivo;
    }

    public void setBillSenderId(Integer billSenderId) {
        this.billSenderId = billSenderId;
    }

    public Integer getBillSenderId() {
        return billSenderId;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Double getExonerated() {
        return exonerated;
    }

    public void setExonerated(Double exonerated) {
        this.exonerated = exonerated;
    }

    public Double getDiscountTotal() {
        return discountTotal;
    }

    public void setDiscountTotal(Double discountTotal) {
        this.discountTotal = discountTotal;
    }

    public String getTipoDescuento() {
        return tipoDescuento;
    }

    public void setTipoDescuento(String tipoDescuento) {
        this.tipoDescuento = tipoDescuento;
    }
}
