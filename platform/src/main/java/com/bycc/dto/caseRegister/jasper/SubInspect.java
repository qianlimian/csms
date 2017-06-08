package com.bycc.dto.caseRegister.jasper;
public class SubInspect {
    private String statement;
    private String inspection;
    private String collectItem;
    private Boolean collectOrNot;
    private Boolean storeOrNot;
    private Boolean verifyOrNot;

    public String getStatement() {
        return statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }

    public String getInspection() {
        return inspection;
    }

    public void setInspection(String inspection) {
        this.inspection = inspection;
    }

    public String getCollectItem() {
        return collectItem;
    }

    public void setCollectItem(String collectItem) {
        this.collectItem = collectItem;
    }

    public Boolean getCollectOrNot() {
        return collectOrNot;
    }

    public void setCollectOrNot(Boolean collectOrNot) {
        this.collectOrNot = collectOrNot;
    }

    public Boolean getStoreOrNot() {
        return storeOrNot;
    }

    public void setStoreOrNot(Boolean storeOrNot) {
        this.storeOrNot = storeOrNot;
    }

    public Boolean getVerifyOrNot() {
        return verifyOrNot;
    }

    public void setVerifyOrNot(Boolean verifyOrNot) {
        this.verifyOrNot = verifyOrNot;
    }
}