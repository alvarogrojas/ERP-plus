package com.ndl.erp.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class ScheduleGeneralWeeksDTO implements Serializable {

    private static final long serialVersionUID = 1777000000000000059L;

    private String title;


    private List<ScheduleGeneralWeekDTO> weeks;




    public ScheduleGeneralWeeksDTO( String title) {
        this.title = title;
    }

    public ScheduleGeneralWeeksDTO(ScheduleWeekDTO w) {
        this.title = title;
        this.weeks.add(new ScheduleGeneralWeekDTO(w.getYear(),w.getMonth(),w.getWeek(),w.isLastWeek(),0d));
    }


    public void putAmmount(Double amount, Integer week, Integer year){
        for(ScheduleGeneralWeekDTO w : this.weeks){
            if(w.getYear().equals(year) &&  w.getWeek().equals(week)){
               w.setAmount(amount);
            }
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }



    public List<ScheduleGeneralWeekDTO> getWeeks() {
        if (weeks==null) {
            this.weeks = new ArrayList<>();
        }
        return weeks;
    }

    public void setWeeks(List<ScheduleGeneralWeekDTO> weeks) {
        this.weeks = weeks;
    }
}
