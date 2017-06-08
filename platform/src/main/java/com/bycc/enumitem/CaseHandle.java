package com.bycc.enumitem;

import org.smartframework.platform.dictionary.bean.entry.EnumEntry;

/**
 * 办理状态
 */
public enum CaseHandle implements EnumEntry {

    PENDING("待办理"),
    HANDLING("办理中"),
    HANDLED("已办理");

    private String value;
    private CaseHandle(String value) {
        this.value = value;
    }

    public String key() {
        return this.name();
    }

    public String value() {
        return this.value;
    }

    /**
     * 按name查找枚举
     */
    public static CaseHandle getMatchByKey(String key) {
        for (CaseHandle e : CaseHandle.values()) {
            if (e.key().equalsIgnoreCase(key)) {
                return e;
            }
        }
        return null;
    }

    /**
     * 按value查找枚举
     */
    public static CaseHandle getMatchByValue(String value) {
        for (CaseHandle e : CaseHandle.values()) {
            if (e.value().equalsIgnoreCase(value)) {
                return e;
            }
        }
        return null;
    }

    /**
     * 按ordinal查找枚举
     */
    public static CaseHandle getMatchByOrdinal(Integer ordinal) {
        for (CaseHandle e : CaseHandle.values()) {
            if (e.ordinal() == ordinal) {
                return e;
            }
        }
        return null;
    }
}
