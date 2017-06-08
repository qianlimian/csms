package com.bycc.enumitem;

import org.smartframework.platform.dictionary.bean.entry.EnumEntry;

/**
 * 房间类型
 */
public enum RoomType implements EnumEntry {
	INSPECT("人身安全检查室"),
	COLLECT("信息采集室"),
	IDENTIFY("辨认室"),
	AWAIT("候审室"),
	INQUEST("讯问室"),
	INQUIRY("询问室"),
	AISLE("过道");
	private String value;

	RoomType(String value) {
		this.value = value;
	}

	/**
	 * 按name查找
	 * @param key
	 * @return
	 */
	public static RoomType getMatchByKey(String key){
		for(RoomType roomType : RoomType.values()){
			if (roomType.key().equalsIgnoreCase(key)) {
				return roomType;
			}
		}
		return null;
	}

	/**
	 * 按value查找枚举
	 */
	public static RoomType getMatchByValue(String value) {
		for (RoomType e : RoomType.values()) {
			if (e.value().equalsIgnoreCase(value)) {
				return e;
			}
		}
		return null;
	}

	/**
	 * 按ordinal查找枚举
	 */
	public static RoomType getMatchByOrdinal(Integer ordinal) {
		for (RoomType e : RoomType.values()) {
			if (e.ordinal() == ordinal) {
				return e;
			}
		}
		return null;
	}

	@Override
	public String key() {
		return this.name();
	}

	@Override
	public String value() {
		return this.value;
	}
}
