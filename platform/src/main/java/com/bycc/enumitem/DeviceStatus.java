package com.bycc.enumitem;

import org.smartframework.platform.dictionary.bean.entry.EnumEntry;
/**
 * 
 * @description 设备状态（基站、摄像头）
 * @author gaoningbo
 * @date 2017年4月11日
 *
 */
public enum DeviceStatus implements EnumEntry {
	NORMAL("正常"), STOP("停用"), DAMAGED("损坏");

	private String value;
	private DeviceStatus(String value) {
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
	public static DeviceStatus getMatchByKey(String key) {
		for (DeviceStatus e : DeviceStatus.values()) {
			if (e.key().equalsIgnoreCase(key)) {
				return e;
			}
		}
		return null;
	}

	/**
	 * 按value查找枚举
	 */
	public static DeviceStatus getMatchByValue(String value) {
		for (DeviceStatus e : DeviceStatus.values()) {
			if (e.value().equalsIgnoreCase(value)) {
				return e;
			}
		}
		return null;
	}

	/**
	 * 按ordinal查找枚举
	 */
	public static DeviceStatus getMatchByOrdinal(Integer ordinal) {
		for (DeviceStatus e : DeviceStatus.values()) {
			if (e.ordinal() == ordinal) {
				return e;
			}
		}
		return null;
	}
}
