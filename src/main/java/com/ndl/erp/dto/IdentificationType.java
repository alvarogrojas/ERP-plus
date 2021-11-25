package com.ndl.erp.dto;

public class IdentificationType {

    private String type;

    private String name;

    public IdentificationType() {}


    public IdentificationType(String type, String name) {
        this.type = type;
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
