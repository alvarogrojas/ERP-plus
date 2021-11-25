package com.ndl.erp.dto;

import java.io.Serializable;


public class ScheduleGeneralWeekDTO implements Serializable {
    private static final long serialVersionUID = 1777000000000000060L;

    private Integer month;
    private Integer year;

    private Integer week;
    private boolean lastWeek=false;
    private Double amount=0d;



    public ScheduleGeneralWeekDTO(Integer year,Integer month, Integer week, boolean lastWeek, Double amount) {
        this.week = week;
        this.lastWeek = lastWeek;
        this.amount = amount;
        this.year = year;
        this.month=month;
    }

    public ScheduleGeneralWeekDTO(ScheduleWeekDTO week) {
        this.month = week.getMonth();
        this.year = week.getYear();
        this.week = week.getWeek();
        this.lastWeek = week.isLastWeek();
    }

    public ScheduleGeneralWeekDTO() {
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getWeek() {
        return week;
    }

    public void setWeek(Integer week) {
        this.week = week;
    }

    public boolean isLastWeek() {
        return lastWeek;
    }

    public void setLastWeek(boolean lastWeek) {
        this.lastWeek = lastWeek;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
