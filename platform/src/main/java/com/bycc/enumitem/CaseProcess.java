package com.bycc.enumitem;

import org.smartframework.platform.dictionary.bean.entry.EnumEntry;

/**
 * 案件告警状态
 */
public enum CaseProcess implements EnumEntry {
    FL("分流"),
    SA("受案"),
    LA("立案"),
    JL("拘留"),
    DB("逮捕"),
    QS("起诉");

    private String value;

    CaseProcess(String param) {
        this.value = param;
    }

    @Override
    public String key() {
        return this.name();
    }

    @Override
    public String value() {
        return value;
    }

    /**
     * 按value查找枚举
     */
    public static CaseProcess getMatchByValue(String value) {
        for (CaseProcess e : CaseProcess.values()) {
            if (e.value().equalsIgnoreCase(value)) {
                return e;
            }
        }
        return null;
    }

    /**
     * 按ordinal查找枚举
     */
    public static CaseProcess getMatchByOrdinal(Integer ordinal) {
        for (CaseProcess e : CaseProcess.values()) {
            if (e.ordinal() == ordinal) {
                return e;
            }
        }
        return null;
    }
}
