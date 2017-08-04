package com.bycc.enumitem;

import org.smartframework.platform.dictionary.bean.entry.EnumEntry;
/**
 * @description 表示案件是否开放
 * @author gaoningbo
 * @date 2017年7月10日
 *
 */
public enum OpenType implements EnumEntry {
	YES("公开"),
	NO("非公开");
	
	private String value;
	private OpenType(String value) {
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
	public static OpenType getMatchByKey(String key) {
		for (OpenType e : OpenType.values()) {
			if (e.key().equalsIgnoreCase(key)) {
				return e;
			}
		}
		return null;
	}

	/**
	 * 按value查找枚举
	 */
	public static OpenType getMatchByValue(String value) {
		for (OpenType e : OpenType.values()) {
			if (e.value().equalsIgnoreCase(value)) {
				return e;
			}
		}
		return null;
	}

	/**
	 * 按ordinal查找枚举
	 */
	public static OpenType getMatchByOrdinal(Integer ordinal) {
		for (OpenType e : OpenType.values()) {
			if (e.ordinal() == ordinal) {
				return e;
			}
		}
		return null;
	}
}
