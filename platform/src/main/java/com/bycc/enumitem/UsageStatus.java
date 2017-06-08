package com.bycc.enumitem;

import org.smartframework.platform.dictionary.bean.entry.EnumEntry;

/**
 * 
 * @description 使用状态
 * @author gaoningbo
 * @date 2017年4月11日
 *
 */
public enum UsageStatus implements EnumEntry {
	UNUSED("未使用"),
	USED("已使用");

	private String value;
	private UsageStatus(String value) {
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
	public static UsageStatus getMatchByKey(String key) {
		for (UsageStatus e : UsageStatus.values()) {
			if (e.key().equalsIgnoreCase(key)) {
				return e;
			}
		}
		return null;
	}

	/**
	 * 按value查找枚举
	 */
	public static UsageStatus getMatchByValue(String value) {
		for (UsageStatus e : UsageStatus.values()) {
			if (e.value().equalsIgnoreCase(value)) {
				return e;
			}
		}
		return null;
	}

	/**
	 * 按ordinal查找枚举
	 */
	public static UsageStatus getMatchByOrdinal(Integer ordinal) {
		for (UsageStatus e : UsageStatus.values()) {
			if (e.ordinal() == ordinal) {
				return e;
			}
		}
		return null;
	}
}
