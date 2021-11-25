package com.ndl.erp.dto;

import com.ndl.erp.domain.CostCenter;
import com.ndl.erp.domain.LaborCost;
import com.ndl.erp.domain.LaborCostDetail;
import com.ndl.erp.domain.MonthlyClosureBillCollect;
import com.ndl.erp.util.DateUtil;

import java.io.Serializable;
import java.sql.Date;

public class BalanceLaborCostDetailDTO implements Comparable<BalanceLaborCostDetailDTO> {

    private static final long serialVersionUID = 1777000000000000036L;

    private Integer id;
    private Integer costCenterId;
    private Integer currencyId;
    private Double costHour;
    private Double hoursSimple;
    private Double hoursDouble;
    private Double hoursMedia;
    private Date laborDate;

    private CostCenter costCenter;


    public BalanceLaborCostDetailDTO() {
    }

//    BalanceLaborCostDetailDTO(lcd.id, lcd.costsCenter.id, lcd.currency.id, lcd.costHour, lcd.hoursSimple, " +
//            "lcd.hoursDouble, lcd.hoursMedia, lcd.laborDate, lcd.costsCenter)

//    public BalanceLaborCostDetailDTO(LaborCostDetail lbd) {
////        this(
////                lbd.getId(),
////                lbd.getCostsCenter().getId(),
////                lbd.getCurrency().getId(),
////                lbd.getCostHour(),
////                lbd.getHoursSimple(),
////                lbd.getHoursDouble(),
////                lbd.getHoursDouble(),
////                lbd.getHoursMedia(),
////                lbd.getLaborDate(), lbd.getCostsCenter()
////        );
//        this.id = lbd.getId();
//        this.costCenterId = lbd.getCostsCenter().getId();
//        this.currencyId = lbd.getCurrency().getId();
//        this.costHour = lbd.getCostHour()!=null?lbd.getCostHour():0;
//        this.hoursSimple = lbd.getHoursSimple()!=null?lbd.getHoursSimple():0;
//        this.hoursDouble = lbd.getHoursDouble()!=null?lbd.getHoursDouble():0;
//        this.hoursMedia = lbd.getHoursMedia()!=null?lbd.getHoursMedia():0;
//        this.laborDate = lbd.getLaborDate();
//        this.costCenter = lbd.getCostsCenter();
//    }
    public BalanceLaborCostDetailDTO( Integer id,
             Integer costCenterId,
             Integer currencyId,
             Double costHour,
             Double hoursSimple,
             Double hoursDouble,
             Double hoursMedia,
             java.util.Date laborDate,
                                      CostCenter costCenter) {
        this.id = id;
        this.costCenterId = costCenterId;
        this.currencyId = currencyId;
        this.costHour = costHour;
        this.hoursSimple = hoursSimple;
        this.hoursDouble = hoursDouble;
        this.hoursMedia = hoursMedia;
        this.laborDate = DateUtil.convertDateToSqlDate(laborDate);
//        this.laborDate = laborDate;
        this.costCenter = costCenter;
        this.costCenterId = costCenter.getId();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCostCenterId() {
        return costCenterId;
    }

    public void setCostCenterId(Integer costCenterId) {
        this.costCenterId = costCenterId;
    }

    public Integer getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(Integer currencyId) {
        this.currencyId = currencyId;
    }

    public Double getCostHour() {
        return costHour;
    }

    public void setCostHour(Double costHour) {
        this.costHour = costHour;
    }

    public Double getHoursSimple() {
        return hoursSimple;
    }

    public void setHoursSimple(Double hoursSimple) {
        this.hoursSimple = hoursSimple;
    }

    public Double getHoursDouble() {
        return hoursDouble;
    }

    public void setHoursDouble(Double hoursDouble) {
        this.hoursDouble = hoursDouble;
    }

    public Double getHoursMedia() {
        return hoursMedia;
    }

    public void setHoursMedia(Double hoursMedia) {
        this.hoursMedia = hoursMedia;
    }

    public Date getLaborDate() {
        return laborDate;
    }

    public void setLaborDate(Date laborDate) {
        this.laborDate = laborDate;
    }

    public CostCenter getCostCenter() {
        return costCenter;
    }

    public void setCostCenter(CostCenter costCenter) {
        this.costCenter = costCenter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (o instanceof BalanceLaborCostDetailDTO) {
            BalanceLaborCostDetailDTO that = (BalanceLaborCostDetailDTO) o;
            if (id != null) {

                return id.equals(that.id);
            } else {
                return false;
            }
//            that.id == null;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public int compareTo( final BalanceLaborCostDetailDTO o) {
        return Integer.compare(this.id, o.id);
    }


}
