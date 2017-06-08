package com.bycc.enumitem;

import org.smartframework.platform.dictionary.bean.entry.EnumEntry;

public enum Gender implements EnumEntry {
	MALE("男"), FEMALE("女");

	private String value;
	private Gender(String value) {
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
	public static Gender getMatchByKey(String key) {
		for (Gender e : Gender.values()) {
			if (e.key().equalsIgnoreCase(key)) {
				return e;
			}
		}
		return null;
	}

	/**
	 * 按value查找枚举
	 */
	public static Gender getMatchByValue(String value) {
		for (Gender e : Gender.values()) {
			if (e.value().equalsIgnoreCase(value)) {
				return e;
			}
		}
		return null;
	}

	/**
	 * 按ordinal查找枚举
	 */
	public static Gender getMatchByOrdinal(Integer ordinal) {
		for (Gender e : Gender.values()) {
			if (e.ordinal() == ordinal) {
				return e;
			}
		}
		return null;
	}
}
