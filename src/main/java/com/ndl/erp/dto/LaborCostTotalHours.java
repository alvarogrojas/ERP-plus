package com.ndl.erp.dto;

public class LaborCostTotalHours {
    private Double hs = 0d;
    private Double hm = 0d;
    private Double hd = 0d;

    public LaborCostTotalHours() {}

    public LaborCostTotalHours(double hs, double hm, double hd) {
        this.hs = hs;
        this.hd = hd;
        this.hm = hm;
    }

    public Double getHs() {
        return hs;
    }

    public void setHs(Double hs) {
        this.hs = hs;
    }

    public Double getHm() {
        return hm;
    }

    public void setHm(Double hm) {
        this.hm = hm;
    }

    public Double getHd() {
        return hd;
    }

    public void setHd(Double hd) {
        this.hd = hd;
    }
}
