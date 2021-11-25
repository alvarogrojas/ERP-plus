package com.ndl.erp.dto;

import com.ndl.erp.domain.Taxes;

public class TaxesDTO {

    private Taxes current = new Taxes();

    public Taxes getCurrent() {
        return current;
    }

    public void setCurrent(Taxes current) {
        this.current = current;
    }
}
