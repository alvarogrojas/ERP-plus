package com.ndl.erp.dto;

import com.ndl.erp.domain.CostCenter;
import com.ndl.erp.domain.LaborCostDetail;

import java.util.Objects;
import java.util.Set;

public class CostCenterListNoPODTO {
    Set<CostCenterNoPODTO> list;

    public Set<CostCenterNoPODTO> getList() {
        return list;
    }

    public void setList(Set<CostCenterNoPODTO> list) {
        this.list = list;
    }
}
