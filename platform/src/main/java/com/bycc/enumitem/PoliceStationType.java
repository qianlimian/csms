package com.bycc.enumitem;

import org.smartframework.platform.dictionary.bean.entry.EnumEntry;

/**
 * 警局级别
 */
public enum PoliceStationType implements EnumEntry {
    SJ("市局"),
    FJ("分局"),
    PCS("派出所");

    private String value;

    PoliceStationType(String param) {
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
    public static PoliceStationType getMatchByKey(String key){
        for(PoliceStationType areaType : PoliceStationType.values()){
            if (areaType.key().equalsIgnoreCase(key)) {
                return areaType;
            }
        }
        return null;
    }

    /**
     * 按value查找枚举
     */
    public static PoliceStationType getMatchByValue(String value) {
        for (PoliceStationType e : PoliceStationType.values()) {
            if (e.value().equalsIgnoreCase(value)) {
                return e;
            }
        }
        return null;
    }

    /**
     * 按ordinal查找枚举
     */
    public static PoliceStationType getMatchByOrdinal(Integer ordinal) {
        for (PoliceStationType e : PoliceStationType.values()) {
            if (e.ordinal() == ordinal) {
                return e;
            }
        }
        return null;
    }
}
