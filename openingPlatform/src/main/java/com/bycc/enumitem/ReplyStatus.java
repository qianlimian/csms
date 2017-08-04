/**
 *
 */
package com.bycc.enumitem;

import org.smartframework.platform.dictionary.bean.entry.EnumEntry;

/**
 * @description 投诉状态
 * @author gaoningbo
 * @date 2017年7月5日
 *
 */
public enum ReplyStatus implements EnumEntry{
	REPLYED("已回复"),
	UNREPLY("未回复");

	private String value;

	private ReplyStatus(String value) {
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
	public static CaseHandle getMatchByKey(String key) {
		for (CaseHandle e : CaseHandle.values()) {
			if (e.key().equalsIgnoreCase(key)) {
				return e;
			}
		}
		return null;
	}

	/**
	 * 按value查找枚举
	 */
	public static CaseHandle getMatchByValue(String value) {
		for (CaseHandle e : CaseHandle.values()) {
			if (e.value().equalsIgnoreCase(value)) {
				return e;
			}
		}
		return null;
	}

	/**
	 * 按ordinal查找枚举
	 */
	public static CaseHandle getMatchByOrdinal(Integer ordinal) {
		for (CaseHandle e : CaseHandle.values()) {
			if (e.ordinal() == ordinal) {
				return e;
			}
		}
		return null;
	}
}
