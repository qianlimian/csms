package com.bycc.enumitem;

import org.smartframework.platform.dictionary.bean.entry.EnumEntry;
/**
 * 
 * @description 律师证件状态
 * @author gaoningbo
 * @date 2017年4月10日
 *
 */
public enum CertificateStatus implements EnumEntry {
	PRACTICE("实习"),
	FORMAL("正式"),
	CLOSE("注销");

	private String value;
	private CertificateStatus(String value) {
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
	public static CertificateStatus getMatchByKey(String key) {
		for (CertificateStatus e : CertificateStatus.values()) {
			if (e.key().equalsIgnoreCase(key)) {
				return e;
			}
		}
		return null;
	}

	/**
	 * 按value查找枚举
	 */
	public static CertificateStatus getMatchByValue(String value) {
		for (CertificateStatus e : CertificateStatus.values()) {
			if (e.value().equalsIgnoreCase(value)) {
				return e;
			}
		}
		return null;
	}

	/**
	 * 按ordinal查找枚举
	 */
	public static CertificateStatus getMatchByOrdinal(Integer ordinal) {
		for (CertificateStatus e : CertificateStatus.values()) {
			if (e.ordinal() == ordinal) {
				return e;
			}
		}
		return null;
	}
}
