package com.bycc.enumitem;

import org.smartframework.platform.dictionary.bean.entry.EnumEntry;

/**
 * 评分加减分类别
 */
public enum ScoreType implements EnumEntry {
    ADD("加分"),
    SUB("减分");

    private String value;

    ScoreType(String param) {
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
    public static ScoreType getMatchByValue(String value) {
        for (ScoreType e : ScoreType.values()) {
            if (e.value().equalsIgnoreCase(value)) {
                return e;
            }
        }
        return null;
    }

    /**
     * 按ordinal查找枚举
     */
    public static ScoreType getMatchByOrdinal(Integer ordinal) {
        for (ScoreType e : ScoreType.values()) {
            if (e.ordinal() == ordinal) {
                return e;
            }
        }
        return null;
    }
}
