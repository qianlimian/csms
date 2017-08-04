package com.bycc.enumitem;

import org.smartframework.platform.dictionary.bean.entry.EnumEntry;

/**
 * @author yumingzhe
 * @description 回复方式
 * @date 2017年7月11日
 */
public enum ReplyType implements EnumEntry {
	EMAIL("邮件回复"),
	TEL("电话回复"),
	SMS("短信回复"),
	TALK("交谈回复");

	private String value;

	ReplyType(String value) {
		this.value = value;
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
	 * 按name查找枚举
	 */
	public static ReplyType getMatchByKey(String key) {
		for (ReplyType e : ReplyType.values()) {
			if (e.key().equalsIgnoreCase(key)) {
				return e;
			}
		}
		return null;
	}

	/**
	 * 按value查找枚举
	 */
	public static ReplyType getMatchByValue(String value) {
		for (ReplyType e : ReplyType.values()) {
			if (e.value().equalsIgnoreCase(value)) {
				return e;
			}
		}
		return null;
	}

	/**
	 * 按ordinal查找枚举
	 */
	public static ReplyType getMatchByOrdinal(Integer ordinal) {
		for (ReplyType e : ReplyType.values()) {
			if (e.ordinal() == ordinal) {
				return e;
			}
		}
		return null;
	}
}
