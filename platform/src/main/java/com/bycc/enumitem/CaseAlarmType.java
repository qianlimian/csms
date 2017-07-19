package com.bycc.enumitem;

import org.smartframework.platform.dictionary.bean.entry.EnumEntry;

/**
 * 案件告警状态
 */
public enum CaseAlarmType implements EnumEntry {
    FCGGJ("防串供告警"),
    SBGJ("设备告警"),
    JYCSGJ("羁押超时告警");

    private String value;

    CaseAlarmType(String param) {
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
    public static CaseAlarmType getMatchByKey(String key){
        for(CaseAlarmType areaType : CaseAlarmType.values()){
            if (areaType.key().equalsIgnoreCase(key)) {
                return areaType;
            }
        }
        return null;
    }

    /**
     * 按value查找枚举
     */
    public static CaseAlarmType getMatchByValue(String value) {
        for (CaseAlarmType e : CaseAlarmType.values()) {
            if (e.value().equalsIgnoreCase(value)) {
                return e;
            }
        }
        return null;
    }

    /**
     * 按ordinal查找枚举
     */
    public static CaseAlarmType getMatchByOrdinal(Integer ordinal) {
        for (CaseAlarmType e : CaseAlarmType.values()) {
            if (e.ordinal() == ordinal) {
                return e;
            }
        }
        return null;
    }
}
