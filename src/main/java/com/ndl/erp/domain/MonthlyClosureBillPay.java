package com.ndl.erp.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.sql.Date;


@Entity
@Table(name="monthly_closure_cxp")
public class MonthlyClosureBillPay implements Comparable<MonthlyClosureBillPay> {
    @Id
    @GeneratedValue
    private Integer id;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_parent", referencedColumnName = "id")
    private MonthlyClosure parent;

    @OneToOne
    @JoinColumn(name="id_bill_pay", referencedColumnName="id")
    private BillPay billPay;

    private Date createDate;
    private Integer idUser;
    private Date updateDate;

    public BillPay getBillPay() {
        return billPay;
    }

    public void setBillPay(BillPay billPay) {
        this.billPay = billPay;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public MonthlyClosure getParent() {
        return parent;
    }

    public void setParent(MonthlyClosure parent) {
        this.parent = parent;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (o instanceof MonthlyClosureBillPay) {
            MonthlyClosureBillPay that = (MonthlyClosureBillPay) o;
            if (id != null) {

                return id.equals(that.id);
            } else {
                return false;
            }

        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public int compareTo( final MonthlyClosureBillPay o) {
        return Integer.compare(this.id, o.id);
    }
}

