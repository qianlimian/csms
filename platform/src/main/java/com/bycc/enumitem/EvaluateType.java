package com.bycc.enumitem;

import org.smartframework.platform.dictionary.bean.entry.EnumEntry;

/**
 * 案件评分类别
 */
public enum EvaluateType implements EnumEntry {
    SUBJECTIVE("主观"),
    OBJECTIVE("客观");

    private String value;

    EvaluateType(String param) {
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
    public static EvaluateType getMatchByValue(String value) {
        for (EvaluateType e : EvaluateType.values()) {
            if (e.value().equalsIgnoreCase(value)) {
                return e;
            }
        }
        return null;
    }

    /**
     * 按ordinal查找枚举
     */
    public static EvaluateType getMatchByOrdinal(Integer ordinal) {
        for (EvaluateType e : EvaluateType.values()) {
            if (e.ordinal() == ordinal) {
                return e;
            }
        }
        return null;
    }
}
