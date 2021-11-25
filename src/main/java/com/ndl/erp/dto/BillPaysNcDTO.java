package com.ndl.erp.dto;

import com.ndl.erp.domain.BillPay;

import java.util.ArrayList;
import java.util.List;

public class BillPaysNcDTO {

    List<BillPay> billPayDetails = new ArrayList<>();

    public List<BillPay> getBillPayDetails() {
        return billPayDetails;
    }

    public void setBillPayDetails(List<BillPay> billPayDetails) {
        this.billPayDetails = billPayDetails;
    }
}
