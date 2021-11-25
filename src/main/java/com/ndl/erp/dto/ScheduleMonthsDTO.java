package com.ndl.erp.dto;

import java.io.Serializable;

public class ScheduleMonthsDTO implements Serializable {

    private static final long serialVersionUID = 1777000000000000055L;
    private String name;
    private Integer weeks=0;
    private Integer number=0;
    private Integer year;


    public ScheduleMonthsDTO() {
    }

    public ScheduleMonthsDTO(ScheduleWeekDTO w) {
        this.name = w.getMonthName();
        this.number+=w.getMonth();
        this.year = w.getYear();
        this.weeks=1;
    }


    public void plusWeek(){
        this.weeks++;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getWeeks() {
        return weeks;
    }

    public void setWeeks(Integer weeks) {
        this.weeks = weeks;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
}
