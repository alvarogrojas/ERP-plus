package com.ndl.erp.dto;

import com.ndl.erp.domain.Client;
import com.ndl.erp.domain.Currency;
import com.ndl.erp.domain.Invoice;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

public class InvoicesListDTO {

    List<Invoice> invoicesList;

    public List<Invoice> getInvoicesList() {
        return invoicesList;
    }

    public void setInvoicesList(List<Invoice> invoicesList) {
        this.invoicesList = invoicesList;
    }
}
