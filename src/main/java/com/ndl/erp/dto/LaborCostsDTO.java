package com.ndl.erp.dto;

import com.ndl.erp.domain.Collaborator;
import com.ndl.erp.domain.CostCenter;
import com.ndl.erp.domain.LaborCost;
import com.ndl.erp.domain.LaborCostDetail;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;


public class LaborCostsDTO {


    private Page<LaborCostDetail> page;

    private Integer total;

    private Integer pagesTotal;

    private Double totalHorasSimple = 0d;
    private Double totalHorasMedia = 0d ;
    private Double totalHorasDouble = 0d;

    private List<CostCenter> costCenters;

    private List<Collaborator> collaborators;

    public List<LaborCostCostCenterDTO> list = new ArrayList<>();

//    public Page<LaborCost> getDeductionsRefundsPage() {
//        return page;
//    }

    public void setPage(Page<LaborCostDetail> p) {
        this.page = p;
        this.initContent();
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getPagesTotal() {
        return pagesTotal;
    }

    public void setPagesTotal(Integer pagesTotal) {

        this.pagesTotal = pagesTotal;
    }

    public void initContent() {
        LaborCostCostCenterDTO laborCostDetail = null;
        this.page.forEach(laborCost ->
                {
//
                        if (!contains(laborCost)) {

                            this.list.add(new LaborCostCostCenterDTO(laborCost, laborCost.getCostCenter()));
                            this.totalHorasDouble = this.totalHorasDouble + laborCost.getHoursDouble();
                            this.totalHorasMedia = this.totalHorasMedia + laborCost.getHoursMedia();
                            this.totalHorasSimple = this.totalHorasSimple + laborCost.getHoursSimple();
                        } else {
                            includeHours(laborCost);
                        }

                });
    }

    private void includeHours(LaborCostDetail laborCost) {
        for(LaborCostCostCenterDTO d: list) {
            if (d.equals(laborCost)) {
                d.setTotalHours(d.getTotalHours().doubleValue() + getLaborCostTotalHours(laborCost));
                this.totalHorasDouble = this.totalHorasDouble + d.getHoursDouble();
                this.totalHorasMedia = this.totalHorasMedia + d.getHoursMedia();
                this.totalHorasSimple = this.totalHorasSimple + d.getHoursSimple();
                break;
            }
        }
    }

    private double getLaborCostTotalHours(LaborCostDetail detail) {
        double total = 0d;
        if (detail.getHoursDouble()!=null) {
            total = total + (detail.getHoursDouble() * 2);
        }
        if (detail.getHoursMedia()!=null) {
            total = total + (detail.getHoursMedia() * 1.5);
        }
        if (detail.getHoursSimple()!=null) {
            total = total + detail.getHoursSimple();
        }
        return total;
    }

    private boolean contains(LaborCostDetail laborCost) {
        boolean contains = false;
        for (LaborCostCostCenterDTO d: this.list) {
            if (d.getId()==laborCost.getLaborCost().getId() && d.getCostCenter().getId()==laborCost.getCostCenter().getId()) {
                contains = true;
                break;
            }
        }
        return contains;
    }

    public Page<LaborCostDetail> getPage() {
        return page;
    }

    public List<LaborCostCostCenterDTO> getList() {
        return list;
    }

    public void setList(List<LaborCostCostCenterDTO> list) {
        this.list = list;
    }

    public List<CostCenter> getCostCenters() {
        return costCenters;
    }

    public void setCostCenters(List<CostCenter> costCenters) {
        this.costCenters = costCenters;
    }

    public List<Collaborator> getCollaborators() {
        return collaborators;
    }

    public void setCollaborators(List<Collaborator> collaborators) {
        this.collaborators = collaborators;
    }

    public Double getTotalHorasSimple() {
        return totalHorasSimple;
    }

    public void setTotalHorasSimple(Double totalHorasSimple) {
        this.totalHorasSimple = totalHorasSimple;
    }

    public Double getTotalHorasMedia() {
        return totalHorasMedia;
    }

    public void setTotalHorasMedia(Double totalHorasMedia) {
        this.totalHorasMedia = totalHorasMedia;
    }

    public Double getTotalHorasDouble() {
        return totalHorasDouble;
    }

    public void setTotalHorasDouble(Double totalHorasDouble) {
        this.totalHorasDouble = totalHorasDouble;
    }

    public void initHours(LaborCostTotalHours sumHoursAllByDates) {
        if (sumHoursAllByDates!=null) {
            this.totalHorasSimple = sumHoursAllByDates.getHs();
            this.totalHorasMedia = sumHoursAllByDates.getHm();
            this.totalHorasDouble = sumHoursAllByDates.getHd();
        }
    }

    //    private void calculateHours(LaborCost laborCost) {
//        if (laborCost==null || laborCost.getDetails()==null) {
//            laborCost.setTotalHours(0d);
//            return;
//        }
//        double total = 0d;
//        for (LaborCostDetail detail: laborCost.getDetails()) {
//            if (detail.getHoursDouble()!=null) {
//                total = total + (detail.getHoursDouble() * 2);
//            }
//            if (detail.getHoursMedia()!=null) {
//                total = total + (detail.getHoursMedia() * 1.5);
//            }
//            if (detail.getHoursSimple()!=null) {
//                total = total + detail.getHoursSimple();
//            }
//        }
//        laborCost.setTotalHours(total);
//
//    }
}
