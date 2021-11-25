package com.ndl.erp.dto;

import com.ndl.erp.domain.*;

import java.util.List;
//import org.springframework.data.domain.Page;


public class CountryDataDTO {


    private List<Canton> cantons;
    private List<District> districts;

    public List<Canton> getCantons() {
        return cantons;
    }

    public void setCantons(List<Canton> cantons) {
        this.cantons = cantons;
    }

    public List<District> getDistricts() {
        return districts;
    }

    public void setDistricts(List<District> districts) {
        this.districts = districts;
    }
}
