package com.ndl.erp.dto;

import com.ndl.erp.domain.Descuentos;
import com.ndl.erp.domain.Inventario;
import sun.security.krb5.internal.crypto.Des;

import java.util.List;

public class DescuentosCacheDTO {
    private String barcode;

    List<Descuentos> descuentos;

    public DescuentosCacheDTO() {}
    public DescuentosCacheDTO(String bc, List<Descuentos> l) {
        this.barcode = bc;
        this.descuentos = l;
    }



    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public List<Descuentos> getDescuentos() {
        return descuentos;
    }

    public void setDescuentos(List<Descuentos> descuentos) {
        this.descuentos = descuentos;
    }
}
