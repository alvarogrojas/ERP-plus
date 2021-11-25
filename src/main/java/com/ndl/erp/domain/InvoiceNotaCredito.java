package com.ndl.erp.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.ndl.erp.util.DateUtil;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
public class InvoiceNotaCredito {


    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private Integer number;
    private Date date;
    private String phone;
    private String address;
    private Integer creditDays;
    private Double subTotal;
    private Double iv;
    private Double total;
    private String clave;
    private String consecutivo;
    private String status="Emitida";

    @OneToOne
    @JoinColumn(name="client_id", referencedColumnName="clientId")
    private Client client;

    @OneToOne
    @JoinColumn(name="user_id", referencedColumnName="id")
    private User user;

    @OneToOne
    @JoinColumn(name="currency_id", referencedColumnName="id")
    private Currency currency;


    @OneToOne
    @JoinColumn(name = "exchange_rate_id", referencedColumnName = "id")
    private ExchangeRate exchangeRate;


    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL,
            mappedBy = "invoiceNotaCredito", orphanRemoval = true)
    private List<InvoiceNotaCreditoDetail> details = new ArrayList<InvoiceNotaCreditoDetail>(0);


    @OneToOne
    @JoinColumn(name="invoice_id", referencedColumnName="id")
    private Invoice invoice;

    private Date createAt;
    private Date updateAt;
    private Boolean ingresadoBodega;
    private Integer enviadaHacienda;
    private Double discountTotal = 0d;



    public InvoiceNotaCredito() {

    }

    public InvoiceNotaCredito(Invoice invoice) {
        BeanUtils.copyProperties(invoice, this);
        this.id = null;
        this.estadoHacienda = "no_enviada";
        this.invoice = invoice;
        this.razon = "Anulando una factura que no debió ser creada!";
        this.status = "Edicion";
        this.enviadaHacienda = -1;
        this.ingresadoBodega = false;
        this.addDetails(invoice);
    }

    private void addDetails(Invoice invoice) {
        if (invoice.getDetails()==null) {
            return;
        }
        if (this.details==null) {
            this.details = new ArrayList<>();
        }
        InvoiceNotaCreditoDetail incd;
        for (InvoiceDetail id:invoice.getDetails()) {
            incd = new InvoiceNotaCreditoDetail(id);
            incd.setId(null);
            incd.setIngresadoBodega(false);
            incd.setUserId(id.getUserId());
            incd.setCreateAt(new Date(DateUtil.getCurrentCalendar().getTime().getTime()));
            incd.setUpdateAt(new Date(DateUtil.getCurrentCalendar().getTime().getTime()));
            this.details.add(incd);
        }
    }

    public ExchangeRate getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(ExchangeRate exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public Double getDiscountTotal() {
        return discountTotal;
    }

    public void setDiscountTotal(Double discountTotal) {
        this.discountTotal = discountTotal;
    }

    public Boolean getIngresadoBodega() {
        return ingresadoBodega;
    }

    public void setIngresadoBodega(Boolean ingresadoBodega) {
        this.ingresadoBodega = ingresadoBodega;
    }

//    public Integer getInvoiceId() {
//        return invoiceId;
//    }
//
//    public void setInvoiceId(Integer invoiceId) {
//        this.invoiceId = invoiceId;
//    }


    @OneToOne
    @JoinColumn(name="tipo_actividad_economica_id", referencedColumnName="id")
    private EconomicActivity economicActivity;

    private Integer esClienteInternacional = 0;

    private String estadoHacienda;

    private Integer billSenderId;

    private String codigo = "01";

    private String razon;
    private Double exonerated;
    private String observaciones;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Double getExonerated() {
        return exonerated;
    }

    public void setExonerated(Double exonerated) {
        this.exonerated = exonerated;
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

    public List<InvoiceNotaCreditoDetail> getDetails() {
        return details;
    }

    public void setDetails(List<InvoiceNotaCreditoDetail> details) {
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

    public Integer getEsClienteInternacional() {
        return esClienteInternacional;
    }

    public void setEsClienteInternacional(Integer esClienteInternacional) {
        this.esClienteInternacional = esClienteInternacional;
    }

    public Integer getEnviadaHacienda() {
        return enviadaHacienda;
    }

    public void setEnviadaHacienda(Integer enviadaHacienda) {
        this.enviadaHacienda = enviadaHacienda;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getRazon() {
        return this.razon;
    }

    public void setRazon(String razon) {
        this.razon = razon;
    }

    public String getConsecutivo() {
        return consecutivo;
    }

    public void setConsecutivo(String consecutivo) {
        this.consecutivo = consecutivo;
    }

    public String getEstadoHacienda() {
        return estadoHacienda;
    }

    public void setEstadoHacienda(String estadoHacienda) {
        this.estadoHacienda = estadoHacienda;
    }

    public Integer getBillSenderId() {
        return billSenderId;
    }

    public void setBillSenderId(Integer billSenderId) {
        this.billSenderId = billSenderId;
    }

    public Integer getCreditDays() {
        return creditDays;
    }

    public void setCreditDays(Integer creditDays) {
        this.creditDays = creditDays;
    }

//    public EconomicActivity geteActivity() {
//        return eActivity;
//    }
//
//    public void seteActivity(EconomicActivity eActivity) {
//        this.eActivity = eActivity;
//    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public EconomicActivity getEconomicActivity() {
        return economicActivity;
    }

    public void setEconomicActivity(EconomicActivity economicActivity) {
        this.economicActivity = economicActivity;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }


}
