package com.bycc.enumitem;

import org.smartframework.platform.dictionary.bean.entry.EnumEntry;
/**
 * 
 * @description 证件类型
 * @author gaoningbo
 * @date 2017年4月10日
 *
 */
public enum CertificateType implements EnumEntry {
	ID("身份证"),
	PP("护照");

	private String value;
	private CertificateType(String value) {
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
	public static CertificateType getMatchByKey(String key) {
		for (CertificateType e : CertificateType.values()) {
			if (e.key().equalsIgnoreCase(key)) {
				return e;
			}
		}
		return null;
	}

	/**
	 * 按value查找枚举
	 */
	public static CertificateType getMatchByValue(String value) {
		for (CertificateType e : CertificateType.values()) {
			if (e.value().equalsIgnoreCase(value)) {
				return e;
			}
		}
		return null;
	}

	/**
	 * 按ordinal查找枚举
	 */
	public static CertificateType getMatchByOrdinal(Integer ordinal) {
		for (CertificateType e : CertificateType.values()) {
			if (e.ordinal() == ordinal) {
				return e;
			}
		}
		return null;
	}
}
