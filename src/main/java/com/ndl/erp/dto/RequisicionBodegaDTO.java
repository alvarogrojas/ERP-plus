package com.ndl.erp.dto;

import com.ndl.erp.domain.Bodega;
import com.ndl.erp.domain.CostCenter;
import com.ndl.erp.domain.RequisicionBodega;
import com.ndl.erp.domain.User;

import java.util.List;

public class RequisicionBodegaDTO {

  private  RequisicionBodega current;
  private List<String> estadosList;
  private List<User> userList;
  private List<Bodega> bodegaList;

  private List<CostCenter> costCenters;


  public RequisicionBodegaDTO() {
      this.current = new RequisicionBodega();
  }

    public List<Bodega> getBodegaList() {
        return bodegaList;
    }

    public void setBodegaList(List<Bodega> bodegaList) {
        this.bodegaList = bodegaList;
    }

    public RequisicionBodega getCurrent() {
        return current;
    }

    public void setCurrent(RequisicionBodega current) {
        this.current = current;
    }

    public List<String> getEstadosList() {
        return estadosList;
    }

    public void setEstadosList(List<String> estadosList) {
        this.estadosList = estadosList;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public List<CostCenter> getCostCenters() {
        return costCenters;
    }

    public void setCostCenters(List<CostCenter> costCenters) {
        this.costCenters = costCenters;
    }
}
