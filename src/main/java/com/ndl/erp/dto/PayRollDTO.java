package com.ndl.erp.dto;

import com.ndl.erp.domain.Collaborator;
import com.ndl.erp.domain.Currency;
import com.ndl.erp.domain.PayRoll;
import com.ndl.erp.domain.PayRollDetail;
import com.ndl.erp.util.StringHelper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PayRollDTO implements Serializable{

    private static final long serialVersionUID = 1777000000000000007L;



    private Integer id;

    private Currency currency;

    private PayRoll current;

    private List<PayRollDetailDTO> details;

    private String accion;
    private String date;
    private String start;
    private String end;

    private String name;
    private String note;
    private String status;
    private Boolean inClosing;


    private String inThisClosure="FALSE";
    private String statusClosing = StringHelper.STATUS_DATA_ING;

    private Double devolutionTotal=0d;
    private Double deductionTotal=0d;
    private Double crudSalaryTotal=0d;
    private Double netSalaryTotal=0d;
    private Double ccssTotal=0d;
    private double tax1Total=0d;
    private double tax2Total=0d;
    private double tax3Total=0d;

    private List<Collaborator> collaborators;
    private Double totalGrossSalary = 0d,
            totaDeducctions = 0d,
            totalNetSalary = 0d,
            totalDevolutions = 0d,
            totalRefunds = 0d,
            totalSalary = 0d;


    public PayRollDTO() {

    }

    public PayRollDTO(PayRoll payRoll) {
        this.setId(payRoll.getId());
        this.setName(payRoll.getName());
        this.setNote(payRoll.getNote());
        this.setStatus(payRoll.getStatus());
        this.setDate(StringHelper.getDate2String(payRoll.getCreateDate()));
        this.setStart(StringHelper.getDate2String(payRoll.getStart()));
        this.setEnd(StringHelper.getDate2String(payRoll.getEnd()));
        this.setInClosing(payRoll.getInClosing());
        this.setStatusClosing(payRoll.getStatusClosing());
        this.current = payRoll;
    }


    /**
     * Crear un DTO de un listado de detalles de planillas y el pone la moneda por defecto.
     * @param cur : Moneda actual de las planillas, esta esta configurada en el sistema en la entidad de parametros generales.
     * @param prds : Listado de detalle de planillas.
     */
    public PayRollDTO(Currency cur, List<PayRollDetail> prds) {
        if(prds!=null && prds.size() > 0){
            PayRoll payRoll = prds.get(0).getPayRoll();
            this.setId(payRoll.getId());
            this.setName(payRoll.getName());
            this.setNote(payRoll.getNote());
            this.setStatus(payRoll.getStatus());
            this.setDate(StringHelper.getDate2String(payRoll.getCreateDate()));
            this.setStart(StringHelper.getDate2String(payRoll.getStart()));
            this.setEnd(StringHelper.getDate2String(payRoll.getEnd()));
            this.setInClosing(payRoll.getInClosing());
            this.setStatusClosing(payRoll.getStatusClosing());
            for (PayRollDetail bpd:prds){
                this.setDeductionTotal(this.getDeductionTotal() + bpd.getDeducctions());
                this.setDevolutionTotal(this.getDevolutionTotal() + bpd.getDevolutions());
                this.setCrudSalaryTotal(this.getCrudSalaryTotal() + bpd.getCrudeSalary());
                this.setNetSalaryTotal(this.getNetSalaryTotal() + bpd.getNetSalary());
                this.setCcssTotal(this.getCcssTotal() + bpd.getCcss());
                this.setTax1Total(this.getTax1Total() + bpd.getTax1());
                this.setTax2Total(this.getTax2Total() + bpd.getTax2());
                this.setTax3Total(this.getTax3Total() + bpd.getTax3());
            }
            this.currency = cur;
        }
    }


    /**
     * Para los monto a la moneda del sistema
     * @param def : Moneda del sistema
     */
    public void changeDefaultCurrency(Currency def){
        if(!this.getCurrency().getDefault()){
            this.setDeductionTotal((this.getDeductionTotal() * def.getValueBuy()) / this.getCurrency().getValueSale());
            this.setDevolutionTotal((this.getDevolutionTotal()  * def.getValueBuy()) / this.getCurrency().getValueSale());
            this.setCrudSalaryTotal((this.getCrudSalaryTotal()  * def.getValueBuy()) / this.getCurrency().getValueSale());
            this.setNetSalaryTotal((this.getNetSalaryTotal()  * def.getValueBuy()) / this.getCurrency().getValueSale());
            this.setCcssTotal((this.getCcssTotal()  * def.getValueBuy()) / this.getCurrency().getValueSale());
            this.setTax1Total((this.getTax1Total()  * def.getValueBuy()) / this.getCurrency().getValueSale());
            this.setTax2Total((this.getTax2Total()  * def.getValueBuy()) / this.getCurrency().getValueSale());
            this.setTax3Total((this.getTax3Total()  * def.getValueBuy()) / this.getCurrency().getValueSale());
            this.setInClosing(this.getInClosing());
        }
    }





    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getInThisClosure() {
        return inThisClosure;
    }

    public void setInThisClosure(String inThisClosure) {
        this.inThisClosure = inThisClosure;
    }

    public Double getDevolutionTotal() {
        return devolutionTotal;
    }

    public void setDevolutionTotal(Double devolutionTotal) {
        this.devolutionTotal = devolutionTotal;
    }

    public Double getDeductionTotal() {
        return deductionTotal;
    }

    public void setDeductionTotal(Double deductionTotal) {
        this.deductionTotal = deductionTotal;
    }

    public Double getCrudSalaryTotal() {
        return crudSalaryTotal;
    }

    public void setCrudSalaryTotal(Double crudSalaryTotal) {
        this.crudSalaryTotal = crudSalaryTotal;
    }

    public Double getNetSalaryTotal() {
        return netSalaryTotal;
    }

    public void setNetSalaryTotal(Double netSalaryTotal) {
        this.netSalaryTotal = netSalaryTotal;
    }

    public Double getCcssTotal() {
        return ccssTotal;
    }

    public void setCcssTotal(Double ccssTotal) {
        this.ccssTotal = ccssTotal;
    }

    public double getTax1Total() {
        return tax1Total;
    }

    public void setTax1Total(double tax1Total) {
        this.tax1Total = tax1Total;
    }

    public double getTax2Total() {
        return tax2Total;
    }

    public void setTax2Total(double tax2Total) {
        this.tax2Total = tax2Total;
    }

    public double getTax3Total() {
        return tax3Total;
    }

    public void setTax3Total(double tax3Total) {
        this.tax3Total = tax3Total;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Boolean getInClosing() {
        return inClosing;
    }

    public void setInClosing(Boolean inClosing) {
        this.inClosing = inClosing;
    }

    public String getStatusClosing() {
        return statusClosing;
    }

    public void setStatusClosing(String statusClosing) {
        this.statusClosing = statusClosing;
    }

    public PayRoll getCurrent() {
        return current;
    }

    public void setCurrent(PayRoll payRoll) {
        this.current = payRoll;
        this.setId(payRoll.getId());
        this.setName(payRoll.getName());
        this.setNote(payRoll.getNote());
        this.setStatus(payRoll.getStatus());
        this.setDate(StringHelper.getDate2String(payRoll.getCreateDate()));
        this.setStart(StringHelper.getDate2String(payRoll.getStart()));
        this.setEnd(StringHelper.getDate2String(payRoll.getEnd()));
        this.setInClosing(payRoll.getInClosing());
        this.setStatusClosing(payRoll.getStatusClosing());

        this.details = new ArrayList<>();
        PayRollDetailDTO cd;
        if (this.current.getDetails()!=null) {

            for (PayRollDetail d : this.current.getDetails()) {
                cd = new PayRollDetailDTO(d);
                this.totalGrossSalary = this.totalGrossSalary + cd.getGrossSalary();
                this.totaDeducctions = this.totaDeducctions + cd.getDeducctions();
                this.totalNetSalary = this.totalNetSalary + cd.getNetSalary();
                this.totalDevolutions = this.totalDevolutions + cd.getDevolutions();
                this.totalRefunds = this.totalRefunds + cd.getRefunds();
                this.totalSalary = this.totalSalary + cd.getTotalSalary();

                this.details.add(cd);
            }
            Collections.sort(this.details);
        }
    }

    public List<Collaborator> getCollaborators() {
        return collaborators;
    }

    public void setCollaborators(List<Collaborator> collaborators) {
        this.collaborators = collaborators;
    }

    public List<PayRollDetailDTO> getDetails() {
        return details;
    }

    public void setDetails(List<PayRollDetailDTO> details) {
        this.details = details;
    }

    public Double getTotalGrossSalary() {
        return totalGrossSalary;
    }

    public void setTotalGrossSalary(Double totalGrossSalary) {
        this.totalGrossSalary = totalGrossSalary;
    }

    public Double getTotaDeducctions() {
        return totaDeducctions;
    }

    public void setTotaDeducctions(Double totaDeducctions) {
        this.totaDeducctions = totaDeducctions;
    }

    public Double getTotalNetSalary() {
        return totalNetSalary;
    }

    public void setTotalNetSalary(Double totalNetSalary) {
        this.totalNetSalary = totalNetSalary;
    }

    public Double getTotalDevolutions() {
        return totalDevolutions;
    }

    public void setTotalDevolutions(Double totalDevolutions) {
        this.totalDevolutions = totalDevolutions;
    }

    public Double getTotalRefunds() {
        return totalRefunds;
    }

    public void setTotalRefunds(Double totalRefunds) {
        this.totalRefunds = totalRefunds;
    }

    public Double getTotalSalary() {
        return totalSalary;
    }

    public void setTotalSalary(Double totalSalary) {
        this.totalSalary = totalSalary;
    }


}
