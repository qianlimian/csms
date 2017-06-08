package com.bycc.enumitem;

public enum CaseStatus {
	ACCEPTED("已受理"),
	REGISTED("已立案"),
	CLOSED("已结案");

	private String value;
	private CaseStatus(String value) {
		this.value = value;
	}

	public String key() {
		return this.name();
	}

	public String value() {
		return this.value;
	}

}
