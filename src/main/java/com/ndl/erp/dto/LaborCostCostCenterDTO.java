package com.ndl.erp.dto;

import com.ndl.erp.domain.Collaborator;
import com.ndl.erp.domain.CostCenter;
import com.ndl.erp.domain.LaborCost;
import com.ndl.erp.domain.LaborCostDetail;
import org.springframework.beans.BeanUtils;

import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class LaborCostCostCenterDTO extends LaborCostDetail {

    private CostCenter costCenter;

    private Integer laborCostId;

    @Transient
    private Double totalHours;

    LaborCostCostCenterDTO() {}

    LaborCostCostCenterDTO(LaborCostDetail cl, CostCenter costCenter) {
        BeanUtils.copyProperties(cl,this);
        this.laborCostId = cl.getLaborCost().getId();
        this.costCenter = costCenter;
        this.initHours();
        //initDetails(cl);
    }

    private void initHours() {
        this.totalHours = 0d;
        if (getHoursDouble()!=null) {
            totalHours = totalHours + (getHoursDouble() * 2);
        }
        if (getHoursMedia()!=null) {
            totalHours = totalHours + (getHoursMedia() * 1.5);
        }
        if (getHoursSimple()!=null) {
            totalHours = totalHours + getHoursSimple();
        }
    }


    public CostCenter getCostCenter() {
        return costCenter;
    }

    public void setCostCenter(CostCenter costCenter) {
        this.costCenter = costCenter;
    }

    public Double getTotalHours() {
        return totalHours;
    }

    public void setTotalHours(Double totalHours) {
        this.totalHours = totalHours;
    }


    public Integer getLaborCostId() {
        return laborCostId;
    }

    public void setLaborCostId(Integer laborCostId) {
        this.laborCostId = laborCostId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LaborCostCostCenterDTO that = (LaborCostCostCenterDTO) o;
        return Objects.equals(costCenter.getCode(), that.costCenter.getCode()) &&
                this.getCollaborator().getId()==that.getCollaborator().getId() &&
                this.getLaborCost().getId()==that.getLaborCost().getId() &&
                this.getId()==that.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(costCenter.getCode());
    }
}
