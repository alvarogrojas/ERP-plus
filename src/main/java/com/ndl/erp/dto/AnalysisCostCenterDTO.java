package com.ndl.erp.dto;

import com.ndl.erp.domain.*;


import java.io.Serializable;

/**
 * Created by wugalde on 4/25/17.
 */
public class AnalysisCostCenterDTO implements Serializable {

    private static final long serialVersionUID = 1777000000000000031L;

    private Integer id;
    private String name;
    private String code;
    private Double total;
    private Double totalBudgeted; //Total presupuestado mano de obra.
    private Double totalBudgetedMaterials; //Total presupuestado Materiales.

    private Currency currency;

    public AnalysisCostCenterDTO(BillPayDetail bpd, Currency cur, Currency systemCurrency) {
        CostCenter cc = bpd.getCostCenter();
        Double monto = bpd.getTotal();
        if(!systemCurrency.getId().equals(cur.getId())){
            monto= (monto * systemCurrency.getValueBuy()) / cur.getValueBuy();
            this.setCurrency(systemCurrency);
        }else{
            this.setCurrency(cur);
        }
        this.setCostCenterData(cc);
        this.setTotal(monto);
    }
    public AnalysisCostCenterDTO() {}

    public AnalysisCostCenterDTO(RefundableDetail rd, Currency cur, Currency systemCurrency) {
        CostCenter cc = rd.getCostCenter();
        Double monto = rd.getSubTotal();
        if(!systemCurrency.getId().equals(cur.getId())){
            monto= (monto * systemCurrency.getValueBuy()) / cur.getValueBuy();
            this.setCurrency(systemCurrency);
        }else{
            this.setCurrency(cur);
        }
        this.setCostCenterData(cc);
        this.setTotal(monto);
    }


    public AnalysisCostCenterDTO(KilometerDetail kmd, Currency cur, Currency systemCurrency) {
        CostCenter cc = kmd.getCostCenter();
        Double monto = kmd.getSubTotal();
        if(!systemCurrency.getId().equals(cur.getId())){
            monto= (monto * systemCurrency.getValueBuy()) / cur.getValueBuy();
            this.setCurrency(systemCurrency);
        }else{
            this.setCurrency(cur);
        }
        this.setCostCenterData(cc);
        this.setTotal(monto);
    }


    public AnalysisCostCenterDTO(BillCollectDetail bcd,Currency cur,Currency curDef) {
        CostCenter cc = bcd.getCostCenter();
        Double monto = bcd.getTotal();
        if(!curDef.getId().equals(cur.getId())){
            monto = (monto * curDef.getValueBuy()) / cur.getValueBuy();
        }
        this.setCostCenterData(cc);
        this.setTotal(monto);
        this.setCurrency(curDef);
    }



    private void setCostCenterData(CostCenter cc){
        this.setId(cc.getId());
        this.setCode(cc.getCode());
        this.setName(cc.getName());
        this.setTotalBudgeted(cc.getTotalBudgeted());
        this.setTotalBudgetedMaterials(cc.getTotalBudgetedMaterials());
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AnalysisCostCenterDTO that = (AnalysisCostCenterDTO) o;

        if (!id.equals(that.id)) return false;
        return code.equals(that.code);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + code.hashCode();
        return result;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Double getTotalBudgeted() {
        return totalBudgeted;
    }

    public void setTotalBudgeted(Double totalBudgeted) {
        this.totalBudgeted = totalBudgeted;
    }


    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Double getTotalBudgetedMaterials() {
        return totalBudgetedMaterials;
    }

    public void setTotalBudgetedMaterials(Double totalBudgetedMaterials) {
        this.totalBudgetedMaterials = totalBudgetedMaterials;
    }
}
