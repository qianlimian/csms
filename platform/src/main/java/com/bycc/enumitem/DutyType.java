package com.bycc.enumitem;

import org.smartframework.platform.dictionary.bean.entry.EnumEntry;

/**
 * 职务
 */
public enum DutyType implements EnumEntry {
    JY("警员"),
    FZY("法制员"),
    SZ("所长"),
    FD("分队"),
    ZD("支队");

    private String value;

    DutyType(String param) {
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
    public static DutyType getMatchByKey(String key){
        for(DutyType areaType : DutyType.values()){
            if (areaType.key().equalsIgnoreCase(key)) {
                return areaType;
            }
        }
        return null;
    }

    /**
     * 按value查找枚举
     */
    public static DutyType getMatchByValue(String value) {
        for (DutyType e : DutyType.values()) {
            if (e.value().equalsIgnoreCase(value)) {
                return e;
            }
        }
        return null;
    }

    /**
     * 按ordinal查找枚举
     */
    public static DutyType getMatchByOrdinal(Integer ordinal) {
        for (DutyType e : DutyType.values()) {
            if (e.ordinal() == ordinal) {
                return e;
            }
        }
        return null;
    }
}
