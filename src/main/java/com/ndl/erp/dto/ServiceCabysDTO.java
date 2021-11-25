package com.ndl.erp.dto;

import com.ndl.erp.domain.ServiceCabys;

import java.util.List;
//import org.springframework.data.domain.Page;


public class ServiceCabysDTO {

    private List<ServiceCabys> serviceCabys;

    public List<ServiceCabys> getServiceCabys() {
        return serviceCabys;
    }

    public void setServiceCabys(List<ServiceCabys> serviceCabys) {
        this.serviceCabys = serviceCabys;
    }
}
