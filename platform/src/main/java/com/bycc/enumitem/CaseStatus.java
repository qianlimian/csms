package com.bycc.enumitem;

import org.smartframework.platform.dictionary.bean.entry.EnumEntry;

/**
 * 
 * @description 案件状态
 * @author gaoningbo
 * @date 2017年4月11日
 *
 */
public enum CaseStatus implements EnumEntry {
	ACCEPTED("受理"),
	REGISTED("立案"),
	DETAINED("拘留"),
	ARRESTED("逮捕"),
	PROSECUETD("起诉"),
	CLOSED("结案");

	private String value;
	private CaseStatus(String value) {
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
	public static CaseStatus getMatchByKey(String key) {
		for (CaseStatus e : CaseStatus.values()) {
			if (e.key().equalsIgnoreCase(key)) {
				return e;
			}
		}
		return null;
	}

	/**
	 * 按value查找枚举
	 */
	public static CaseStatus getMatchByValue(String value) {
		for (CaseStatus e : CaseStatus.values()) {
			if (e.value().equalsIgnoreCase(value)) {
				return e;
			}
		}
		return null;
	}

	/**
	 * 按ordinal查找枚举
	 */
	public static CaseStatus getMatchByOrdinal(Integer ordinal) {
		for (CaseStatus e : CaseStatus.values()) {
			if (e.ordinal() == ordinal) {
				return e;
			}
		}
		return null;
	}
}
