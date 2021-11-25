package com.ndl.erp.dto;

import java.util.List;

public class ProductsPendingsOutDTO {

        private List<InvoiceProductTotalDTO> invoiceProductsPendings;

    public List<InvoiceProductTotalDTO> getInvoiceProductsPendings() {
        return invoiceProductsPendings;
    }

    public void setInvoiceProductsPendings(List<InvoiceProductTotalDTO> invoiceProductsPendings) {
        this.invoiceProductsPendings = invoiceProductsPendings;
    }
}
