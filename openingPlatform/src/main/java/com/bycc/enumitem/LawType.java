/**
 * 
 */
package com.bycc.enumitem;

import org.smartframework.platform.dictionary.bean.entry.EnumEntry;

/**
 * @description 法律类型
 * @author gaoningbo
 * @date 2017年7月5日
 * 
 */
public enum LawType implements EnumEntry{
	ADMINIST("民法"),
	CRIMINAL("刑法");

	private String value;

	private LawType(String value) {
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
	public static LawType getMatchByKey(String key) {
		for (LawType e : LawType.values()) {
			if (e.key().equalsIgnoreCase(key)) {
				return e;
			}
		}
		return null;
	}

	/**
	 * 按value查找枚举
	 */
	public static LawType getMatchByValue(String value) {
		for (LawType e : LawType.values()) {
			if (e.value().equalsIgnoreCase(value)) {
				return e;
			}
		}
		return null;
	}

	/**
	 * 按ordinal查找枚举
	 */
	public static LawType getMatchByOrdinal(Integer ordinal) {
		for (LawType e : LawType.values()) {
			if (e.ordinal() == ordinal) {
				return e;
			}
		}
		return null;
	}

}
