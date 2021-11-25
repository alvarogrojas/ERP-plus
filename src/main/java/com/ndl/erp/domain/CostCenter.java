package com.ndl.erp.domain;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Objects;

@Entity
@Table(name="costs_center")
public class CostCenter {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private String name;

    private String description;

    private String code;

    private Calendar createdDate;

    private Calendar lastUpdatedDate;

    @OneToOne
    @JoinColumn(name="client_id", referencedColumnName="clientId")
    private Client client;

    private Integer lastUpdatedById;

    private Integer createdById;

    private Double totalBudgetedMaterials;

    private Double totalBudgeted;

    private Boolean inPurchaseOrder;

    private String type;

    private String state;

    @OneToOne
    @JoinColumn(name="contact_id", referencedColumnName="id")
    private ContactClient contact;


    public CostCenter() {
    }

    public CostCenter(
            Integer id,
            String name,
            String description,
            Client cliente,
            String estado,
            String code

    ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.client = cliente;
        this.state = estado;
        this.code = code;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Client getClient() {
        return client;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

//    public Calendar getCreatedDate() {
//        return createdDate;
//    }
//
//    public void setCreatedDate(Calendar createdDate) {
//        this.createdDate = createdDate;
//    }

    @Column(name="lastUpdatedDate")
    public Calendar getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(Calendar lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    @Column(name="createdDate")
    public Calendar getCreatedDate() {
        return createdDate;
    }


    public void setCreatedDate(Calendar createdDate) {
        this.createdDate = createdDate;
    }

    public Integer getLastUpdatedById() {
        return lastUpdatedById;
    }

    public void setLastUpdatedById(Integer lastUpdatedById) {
        this.lastUpdatedById = lastUpdatedById;
    }

    public Integer getCreatedById() {
        return createdById;
    }

    public void setCreatedById(Integer createdById) {
        this.createdById = createdById;
    }

    public Double getTotalBudgetedMaterials() {
        return totalBudgetedMaterials;
    }

    public void setTotalBudgetedMaterials(Double totalBudgetedMaterials) {
        this.totalBudgetedMaterials = totalBudgetedMaterials;
    }

    public Double getTotalBudgeted() {
        return totalBudgeted;
    }

    public void setTotalBudgeted(Double totalBudgeted) {
        this.totalBudgeted = totalBudgeted;
    }

    public Boolean getInPurchaseOrder() {
        return inPurchaseOrder;
    }

    public void setInPurchaseOrder(Boolean inPurchaseOrder) {
        this.inPurchaseOrder = inPurchaseOrder;
    }

    public ContactClient getContact() {
        return contact;
    }

    public void setContact(ContactClient contact) {
        this.contact = contact;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CostCenter that = (CostCenter) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
