package com.bycc.enumitem;

import org.smartframework.platform.dictionary.bean.entry.EnumEntry;
/**
 * 
 * @description 采集项目
 * @author gaoningbo
 * @date 2017年4月11日
 *
 */
public enum CollectItem implements EnumEntry {
	ZW("指纹"),
	XY("血液"),
	NY("尿液"),
	QT("其他");

	private String value;
	private CollectItem(String value) {
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
	public static CollectItem getMatchByKey(String key) {
		for (CollectItem e : CollectItem.values()) {
			if (e.key().equalsIgnoreCase(key)) {
				return e;
			}
		}
		return null;
	}

	/**
	 * 按value查找枚举
	 */
	public static CollectItem getMatchByValue(String value) {
		for (CollectItem e : CollectItem.values()) {
			if (e.value().equalsIgnoreCase(value)) {
				return e;
			}
		}
		return null;
	}

	/**
	 * 按ordinal查找枚举
	 */
	public static CollectItem getMatchByOrdinal(Integer ordinal) {
		for (CollectItem e : CollectItem.values()) {
			if (e.ordinal() == ordinal) {
				return e;
			}
		}
		return null;
	}
}
