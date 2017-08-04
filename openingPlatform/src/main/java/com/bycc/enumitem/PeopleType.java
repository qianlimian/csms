package com.bycc.enumitem;

import org.smartframework.platform.dictionary.bean.entry.EnumEntry;
/**
 * 
 * @description 涉案人员类型
 * @author gaoningbo
 * @date 2017年4月11日
 *
 */
public enum PeopleType implements EnumEntry {
	SUSPECT("嫌疑人"),
	VICTIM("被害人"),
	WITNESS("证人");

	private String value;
	private PeopleType(String value) {
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
	public static PeopleType getMatchByKey(String key) {
		for (PeopleType e : PeopleType.values()) {
			if (e.key().equalsIgnoreCase(key)) {
				return e;
			}
		}
		return null;
	}

	/**
	 * 按value查找枚举
	 */
	public static PeopleType getMatchByValue(String value) {
		for (PeopleType e : PeopleType.values()) {
			if (e.value().equalsIgnoreCase(value)) {
				return e;
			}
		}
		return null;
	}

	/**
	 * 按ordinal查找枚举
	 */
	public static PeopleType getMatchByOrdinal(Integer ordinal) {
		for (PeopleType e : PeopleType.values()) {
			if (e.ordinal() == ordinal) {
				return e;
			}
		}
		return null;
	}
}
