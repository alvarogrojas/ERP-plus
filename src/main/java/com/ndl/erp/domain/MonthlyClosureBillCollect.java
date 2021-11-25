package com.ndl.erp.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by wugalde on 7/28/17.
 */

@Entity
@Table(name="monthly_closure_cxc")
public class MonthlyClosureBillCollect implements Comparable<MonthlyClosureBillCollect> {
    @Id
    @GeneratedValue
    private Integer id;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_parent", referencedColumnName = "id")
    private MonthlyClosure parent;

    @OneToOne
    @JoinColumn(name="id_bill_collect", referencedColumnName="id")
    private BillCollect billCollect;

    private Date createDate;
    private Integer idUser;
    private Date updateDate;

    public BillCollect getBillCollect() {
        return billCollect;
    }

    public void setBillCollect(BillCollect billCollect) {
        this.billCollect = billCollect;
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
        if (o instanceof MonthlyClosureBillCollect) {
            MonthlyClosureBillCollect that = (MonthlyClosureBillCollect) o;
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
    public int compareTo( final MonthlyClosureBillCollect o) {
        return Integer.compare(this.id, o.id);
    }
}

