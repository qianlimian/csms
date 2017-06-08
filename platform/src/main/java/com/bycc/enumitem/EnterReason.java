package com.bycc.enumitem;

import org.smartframework.platform.dictionary.bean.entry.EnumEntry;

public enum EnterReason implements EnumEntry {
	TAZS("投案自首"), ZACH("治安传唤"), JXPW("继续盘问"), XSCH("刑事传唤"), JC("拘传"), XSJL("刑事拘留"),
	QBHS("取保候审"), JSJZ("监视居住"), DB("逮捕"), BHR("被害人"), ZR("证人"), QT("其他");

	private String value;

	EnterReason(String param) {
		this.value = param;
	}

	@Override
	public String key() {
		return this.name();
	}

	@Override
	public String value() {
		return this.value;
	}

	/**
	 * 按name查找
	 *
	 * @param key
	 * @return
	 */
	public static EnterReason getMatchByKey(String key) {
		for (EnterReason reason : EnterReason.values()) {
			if (reason.key().equalsIgnoreCase(key)) {
				return reason;
			}
		}
		return null;
	}

	/**
	 * 按value查找枚举
	 */
	public static EnterReason getMatchByValue(String value) {
		for (EnterReason e : EnterReason.values()) {
			if (e.value().equalsIgnoreCase(value)) {
				return e;
			}
		}
		return null;
	}

	/**
	 * 按ordinal查找枚举
	 */
	public static EnterReason getMatchByOrdinal(Integer ordinal) {
		for (EnterReason e : EnterReason.values()) {
			if (e.ordinal() == ordinal) {
				return e;
			}
		}
		return null;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
