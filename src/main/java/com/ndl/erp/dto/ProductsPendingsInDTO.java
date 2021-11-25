package com.ndl.erp.dto;

import java.util.List;

public class ProductsPendingsInDTO {

    private List<BillPayProductTotalDTO> billPaysPendings;

    public List<BillPayProductTotalDTO> getBillPaysPendings() {
        return billPaysPendings;
    }

    public void setBillPaysPendings(List<BillPayProductTotalDTO> billPaysPendings) {
        this.billPaysPendings = billPaysPendings;
    }
}
