package com.bycc.enumitem;

import org.smartframework.platform.dictionary.bean.entry.EnumEntry;
/**
 * 
 * @description 保管措施
 * @author gaoningbo
 * @date 2017年4月11日
 *
 */
public enum StoreType implements EnumEntry {
	KY("扣押"),
	ZC("暂存"),
	BG("保管");

	private String value;
	private StoreType(String value) {
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
	public static StoreType getMatchByKey(String key) {
		for (StoreType e : StoreType.values()) {
			if (e.key().equalsIgnoreCase(key)) {
				return e;
			}
		}
		return null;
	}

	/**
	 * 按value查找枚举
	 */
	public static StoreType getMatchByValue(String value) {
		for (StoreType e : StoreType.values()) {
			if (e.value().equalsIgnoreCase(value)) {
				return e;
			}
		}
		return null;
	}

	/**
	 * 按ordinal查找枚举
	 */
	public static StoreType getMatchByOrdinal(Integer ordinal) {
		for (StoreType e : StoreType.values()) {
			if (e.ordinal() == ordinal) {
				return e;
			}
		}
		return null;
	}
}
