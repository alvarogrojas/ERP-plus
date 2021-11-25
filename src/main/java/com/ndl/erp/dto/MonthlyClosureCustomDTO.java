package com.ndl.erp.dto;

import com.ndl.erp.domain.MonthlyClosure;

import java.io.Serializable;
import java.util.List;



public class MonthlyClosureCustomDTO implements Serializable{

    private String name;
    private java.util.Date start;

    private java.util.Date end;

    private String status;

    private Integer id;

//    public MonthlyClosureCustomDTO() {
//
//    }

    public MonthlyClosureCustomDTO(Integer id, String name, java.util.Date start, java.util.Date end, String status) {
        this.id = id;
        this.name = name;
        this.start = start;
        this.end = end;
        this.status = status;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public java.util.Date getStart() {
        return start;
    }

    public void setStart(java.util.Date start) {
        this.start = start;
    }

    public java.util.Date getEnd() {
        return end;
    }

    public void setEnd(java.util.Date end) {
        this.end = end;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
