package com.bycc.enumitem;

public enum CaseType {
	CIVIL("行政"),
	CRIMINAL("刑事"),
	DISPUTE("纠纷");

	private String value;
	private CaseType(String value) {
		this.value = value;
	}

	public String key() {
		return this.name();
	}

	public String value() {
		return this.value;
	}
}
