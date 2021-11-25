package com.ndl.erp.dto;

import com.ndl.erp.domain.BillCollect;
import com.ndl.erp.domain.BillPay;
import com.ndl.erp.domain.MonthlyClosure;
import com.ndl.erp.domain.PayRoll;

import java.io.Serializable;
import java.util.List;

public class MonthlyClosureGenDTO implements Serializable{

    private static final long serialVersionUID = 1777000000000000007L;

    private MonthlyClosure current;

//    private List<PayRollDetailDTO> details;

    List<BillCollectDTO> billCollects ;
    List<BillPayDTO> billPays;
    List<PayRollDTO> payRollDTOS ;

    List<BillCollect> updateBillCollects ;
    List<BillPay> updateBillPays ;
    List<PayRoll> updatePayRolls ;


    public MonthlyClosureGenDTO() {

    }


    public MonthlyClosure getCurrent() {
        return current;
    }

    public void setCurrent(MonthlyClosure current) {
        this.current = current;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public List<BillCollectDTO> getBillCollects() {
        return billCollects;
    }

    public void setBillCollects(List<BillCollectDTO> billCollects) {
        this.billCollects = billCollects;
    }

    public List<BillPayDTO> getBillPays() {
        return billPays;
    }

    public void setBillPays(List<BillPayDTO> billPays) {
        this.billPays = billPays;
    }

    public List<PayRollDTO> getPayRollDTOS() {
        return payRollDTOS;
    }

    public void setPayRollDTOS(List<PayRollDTO> payRollDTOS) {
        this.payRollDTOS = payRollDTOS;
    }

    public List<BillCollect> getUpdateBillCollects() {
        return updateBillCollects;
    }

    public void setUpdateBillCollects(List<BillCollect> updateBillCollects) {
        this.updateBillCollects = updateBillCollects;
    }

    public List<BillPay> getUpdateBillPays() {
        return updateBillPays;
    }

    public void setUpdateBillPays(List<BillPay> updateBillPays) {
        this.updateBillPays = updateBillPays;
    }

    public List<PayRoll> getUpdatePayRolls() {
        return updatePayRolls;
    }

    public void setUpdatePayRolls(List<PayRoll> updatePayRolls) {
        this.updatePayRolls = updatePayRolls;
    }
}
