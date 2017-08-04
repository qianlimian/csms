package com.bycc.enumItem;

/**
 * 性别枚举
 */
public enum Gender {
	MALE("1", "男"), FEMALE("2", "女");

	// 身份证代码
	private String code;

	private String value;

	private Gender(String code, String value) {
		this.code = code;
		this.value = value;
	}
	
	public static Gender getByCode(String code){
		for(Gender gender:Gender.values()){
			if(gender.getCode().equals(code)){
				return gender;
			}
		}
		return null;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
