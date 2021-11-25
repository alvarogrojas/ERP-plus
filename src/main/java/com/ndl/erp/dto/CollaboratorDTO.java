package com.ndl.erp.dto;

import com.ndl.erp.domain.*;

import java.util.List;
//import org.springframework.data.domain.Page;


public class CollaboratorDTO {


    private List<Canton> cantons;
    private List<District> districts;

    private List<Province> provinces;
    private List<Currency> currencies;
    private List<Department> departments;
    private List<String> typePayrolls;

    private List<VehiculeType> vehiculeTypes;
    private List<Fuel> vehicleFuelTypes;
    private List<String> tractions;
    private List<String> status;

    private Collaborator current;

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

    public List<Province> getProvinces() {
        return provinces;
    }

    public void setProvinces(List<Province> provinces) {
        this.provinces = provinces;
    }

    public List<Currency> getCurrencies() {
        return currencies;
    }

    public void setCurrencies(List<Currency> currencies) {
        this.currencies = currencies;
    }

    public List<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }

    public Collaborator getCurrent() {
        return current;
    }

    public void setCurrent(Collaborator current) {
        this.current = current;
    }

    public List<VehiculeType> getVehiculeTypes() {
        return vehiculeTypes;
    }

    public void setVehiculeTypes(List<VehiculeType> vehiculeTypes) {
        this.vehiculeTypes = vehiculeTypes;
    }

    public List<Fuel> getVehicleFuelTypes() {
        return vehicleFuelTypes;
    }

    public void setVehicleFuelTypes(List<Fuel> vehicleFuelTypes) {
        this.vehicleFuelTypes = vehicleFuelTypes;
    }

    public List<String> getTractions() {
        return tractions;
    }

    public void setTractions(List<String> tractions) {
        this.tractions = tractions;
    }

    public List<String> getStatus() {
        return status;
    }

    public void setStatus(List<String> status) {
        this.status = status;
    }

    public List<String> getTypePayrolls() {
        return typePayrolls;
    }

    public void setTypePayrolls(List<String> typePayrolls) {
        this.typePayrolls = typePayrolls;
    }
}
