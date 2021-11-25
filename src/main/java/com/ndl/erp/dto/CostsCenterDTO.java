package com.ndl.erp.dto;

import com.ndl.erp.domain.CostCenter;


/**
 * Created by alvaro on 2/7/14.
 */
public class CostsCenterDTO {

    private static final String NAME = "name";
    private static final String CLIENT = "client.name";
    private static final String TYPE = "type";
    private static final String STATE = "state";
    private static final String CODE = "code";

    private String name;

    private String client;

    private String type;

    private String state;

    private String code;

    private Integer id;

    private Double totalBudgeted; //Presupuestado en mano de obras
    private Double totalBudgetedMaterials; //Presupuestado en materiales

    private boolean updatableDetail;
    private boolean updatable;
    private Boolean inPO;



    public CostsCenterDTO() {
    }

    public CostsCenterDTO(Object[] cc){
       this.id = (Integer) cc[0];
       this.code = (String) cc[1];
       this.name =(String) cc[2];
       this.setInPO(false);
    }

    public CostCenter getCostCenter(){
        CostCenter ret = new CostCenter();
        ret.setId(this.id);
        ret.setCode(this.code);
        ret.setName(this.name);
        ret.setType(this.type);
        ret.setState(this.state);
        ret.setTotalBudgeted(this.totalBudgeted);
        ret.setTotalBudgetedMaterials(this.totalBudgetedMaterials);
        ret.setInPurchaseOrder(this.getInPO());
        return ret;
    }

    public CostsCenterDTO(CostCenter cc) {
        this.totalBudgeted = cc.getTotalBudgeted();
        this.totalBudgetedMaterials = cc.getTotalBudgetedMaterials();
        this.name = cc.getName();
        this.code = cc.getCode();
        if (cc.getClient() != null) {
            this.client = cc.getClient().getName();
        }
        this.type = cc.getType();
        this.state = cc.getState();
        this.id = cc.getId();
        updatableDetail = false;
        updatable = false;
        this.setInPO(cc.getInPurchaseOrder());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public static String getSortField(Integer sortFieldNumber) {
        switch (sortFieldNumber) {
            case 1: return NAME;
            case 2: return CLIENT;
            case 3: return STATE;
            case 4: return CODE;
            default:return  NAME;
        }
    }

    public boolean isUpdatableDetail() {
        return updatableDetail;
    }

    public void setUpdatableDetail(boolean updatableDetail) {
        this.updatableDetail = updatableDetail;
    }

    public boolean isUpdatable() {
        return updatable;
    }

    public void setUpdatable(boolean isUpdatable) {
        this.updatable = isUpdatable;
    }

    public Double getTotalBudgeted() {
        return totalBudgeted;
    }

    public void setTotalBudgeted(Double totalBudgeted) {
        this.totalBudgeted = totalBudgeted;
    }

    public Double getTotalBudgetedMaterials() {
        return totalBudgetedMaterials;
    }

    public void setTotalBudgetedMaterials(Double totalBudgetedMaterials) {
        this.totalBudgetedMaterials = totalBudgetedMaterials;
    }

    public Boolean getInPO() {
        return inPO;
    }

    public void setInPO(Boolean inPO) {
        this.inPO = inPO;
    }
}
