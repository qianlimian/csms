package com.bycc.dto.caseRegister.jasper;

public class SubBelongs {
    private String name;
    private String description;
    private Integer count;
    private String unit;
    private String storeType;
    private Boolean involvedOrNot;
    private String cabinetNo;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getStoreType() {
        return storeType;
    }

    public void setStoreType(String storeType) {
        this.storeType = storeType;
    }

    public Boolean getInvolvedOrNot() {
        return involvedOrNot;
    }

    public void setInvolvedOrNot(Boolean involvedOrNot) {
        this.involvedOrNot = involvedOrNot;
    }

    public String getCabinetNo() {
        return cabinetNo;
    }

    public void setCabinetNo(String cabinetNo) {
        this.cabinetNo = cabinetNo;
    }
}