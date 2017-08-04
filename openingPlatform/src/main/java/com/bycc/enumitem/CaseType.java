package com.bycc.enumitem;

import org.smartframework.platform.dictionary.bean.entry.EnumEntry;

public enum CaseType implements EnumEntry {
	CIVIL("行政案件"),
	CRIMINAL("刑事案件"),
	DISPUTE("现场调解");
	
	private String value;
	private CaseType(String value) {
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
	public static CaseType getMatchByKey(String key) {
		for (CaseType e : CaseType.values()) {
			if (e.key().equalsIgnoreCase(key)) {
				return e;
			}
		}
		return null;
	}

	/**
	 * 按value查找枚举
	 */
	public static CaseType getMatchByValue(String value) {
		for (CaseType e : CaseType.values()) {
			if (e.value().equalsIgnoreCase(value)) {
				return e;
			}
		}
		return null;
	}

	/**
	 * 按ordinal查找枚举
	 */
	public static CaseType getMatchByOrdinal(Integer ordinal) {
		for (CaseType e : CaseType.values()) {
			if (e.ordinal() == ordinal) {
				return e;
			}
		}
		return null;
	}
}
