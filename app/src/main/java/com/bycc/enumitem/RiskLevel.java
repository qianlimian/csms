package com.bycc.enumitem;

public enum RiskLevel {
    HIGH("高"),
    MEDIUM("中"),
    LOW("低");

    private String value;
    RiskLevel(String value) {
        this.value = value;
    }

    public String key() {
        return this.name();
    }

    public String value() {
        return value;
    }
}