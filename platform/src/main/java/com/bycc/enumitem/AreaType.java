package com.bycc.enumitem;

import org.smartframework.platform.dictionary.bean.entry.EnumEntry;

/**
 * 案件告警状态
 */
public enum AreaType implements EnumEntry {
    HCQ("环翠"),
    JQ("经区"),
    GQ("高区"),
    LGQ("临港区");

    private String value;

    AreaType(String param) {
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
     * 按name查找
     * @param key
     * @return
     */
    public static AreaType getMatchByKey(String key){
        for(AreaType areaType : AreaType.values()){
            if (areaType.key().equalsIgnoreCase(key)) {
                return areaType;
            }
        }
        return null;
    }

    /**
     * 按value查找枚举
     */
    public static AreaType getMatchByValue(String value) {
        for (AreaType e : AreaType.values()) {
            if (e.value().equalsIgnoreCase(value)) {
                return e;
            }
        }
        return null;
    }

    /**
     * 按ordinal查找枚举
     */
    public static AreaType getMatchByOrdinal(Integer ordinal) {
        for (AreaType e : AreaType.values()) {
            if (e.ordinal() == ordinal) {
                return e;
            }
        }
        return null;
    }
}
