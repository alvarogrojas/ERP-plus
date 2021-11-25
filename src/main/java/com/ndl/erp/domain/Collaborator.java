package com.ndl.erp.domain;

import javax.persistence.*;

import java.sql.Date;
import java.util.Objects;

@Entity
public class Collaborator  {

    @Id
    @GeneratedValue
    private Integer id;

    private String name;
    private String lastName;
    private String identificationCard;
    private String phone;
    private String cellPhone;
    private String email;
    private String address;
    private String province;
    private String canton;
    private String district;
    private Date   birthdate;
    private String status;
    private String typePayroll;
    private String numberPayroll;
    private Double rate;
    private Double ccssHoursWork;
    private Integer vehiculeType;
    private Integer vehicleFuelType;
    private String vehicleBrand;
    private Integer vehicleYear;

    private Date createDate;
    private Date updateDate;

    @OneToOne
    @JoinColumn(name="id_currency", referencedColumnName="id")
    private Currency currency;

    @OneToOne
    @JoinColumn(name="id_department", referencedColumnName="id")
    private Department department;

    private Integer idUserRegister;
    private String ccMotor;
    private String traction;

    public String getTraction() {
        return traction;
    }

    public void setTraction(String traction) {
        this.traction = traction;
    }

    public String getCcMotor() {
        return ccMotor;
    }

    public void setCcMotor(String ccMotor) {
        this.ccMotor = ccMotor;
    }


    public Integer getIdUserRegister() {
        return idUserRegister;
    }

    public void setIdUserRegister(Integer idUserRegister) {
        this.idUserRegister = idUserRegister;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getIdentificationCard() {
        return identificationCard;
    }

    public void setIdentificationCard(String identificationCard) {
        this.identificationCard = identificationCard;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCellPhone() {
        return cellPhone;
    }

    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCanton() {
        return canton;
    }

    public void setCanton(String canton) {
        this.canton = canton;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }


    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTypePayroll() {
        return typePayroll;
    }

    public void setTypePayroll(String typePayroll) {
        this.typePayroll = typePayroll;
    }


    public String getNumberPayroll() {
        return numberPayroll;
    }

    public void setNumberPayroll(String numberPayroll) {
        this.numberPayroll = numberPayroll;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }


    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }


    public Double getCcssHoursWork() {
        return ccssHoursWork;
    }

    public void setCcssHoursWork(Double ccssHoursWork) {
        this.ccssHoursWork = ccssHoursWork;
    }

    public Integer getVehiculeType() {
        return vehiculeType;
    }

    public void setVehiculeType(Integer vehiculeType) {
        this.vehiculeType = vehiculeType;
    }

    public Integer getVehicleFuelType() {
        return vehicleFuelType;
    }

    public void setVehicleFuelType(Integer vehicleFuelType) {
        this.vehicleFuelType = vehicleFuelType;
    }

    public String getVehicleBrand() {
        return vehicleBrand;
    }

    public void setVehicleBrand(String vehicleBrand) {
        this.vehicleBrand = vehicleBrand;
    }

    public Integer getVehicleYear() {
        return vehicleYear;
    }

    public void setVehicleYear(Integer vehicleYear) {
        this.vehicleYear = vehicleYear;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Collaborator)) return false;
        Collaborator that = (Collaborator) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(lastName, that.lastName);

    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, lastName, createDate, updateDate, currency, department, idUserRegister, ccMotor, traction);
    }
}
