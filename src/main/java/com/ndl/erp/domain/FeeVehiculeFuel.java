package com.ndl.erp.domain;


import javax.persistence.*;

@Entity
public class FeeVehiculeFuel {

    @OneToOne
    @JoinColumn(name="vehicule_type_id", referencedColumnName="id")
    private VehiculeType vehicule;

    @OneToOne
    @JoinColumn(name="fuel_id", referencedColumnName="id")
    private Fuel fuel;

    private Integer old;
    private Double amount;

    @Id
    @GeneratedValue
    private Integer id;

    @OneToOne
    @JoinColumn(name="currency_id", referencedColumnName="id")
    private Currency currency;


    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getOld() {
        return old;
    }

    public void setOld(Integer old) {
        this.old = old;
    }

    public Fuel getFuel() {
        return fuel;
    }

    public void setFuel(Fuel fuel) {
        this.fuel = fuel;
    }

    public VehiculeType getVehicule() {
        return vehicule;
    }

    public void setVehicule(VehiculeType vehicule) {
        this.vehicule = vehicule;
    }
}
