package org.smartframework.platform.validate.beans;

import java.util.ArrayList;
import java.util.List;

public class ValidatorErrorInfo {
	private String propIndex;
	private List<String> errorMsg;
	private String rowId;
	private String columnId;

	public ValidatorErrorInfo(String propIndex, String msg) {
		this.propIndex = propIndex;
		this.errorMsg = new ArrayList();
		this.errorMsg.add(msg);
	}

	public ValidatorErrorInfo(String rowId, String columnId, String msg) {
		this.rowId = rowId;
		this.columnId = columnId;
		this.errorMsg = new ArrayList();
		this.errorMsg.add(msg);
	}

	public String getPropIndex() {
		return this.propIndex;
	}

	public void setPropIndex(String propIndex) {
		this.propIndex = propIndex;
	}

	public List<String> getErrorMsg() {
		return this.errorMsg;
	}

	public void setErrorMsg(List<String> errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getRowId() {
		return this.rowId;
	}

	public void setRowId(String rowId) {
		this.rowId = rowId;
	}

	public String getColumnId() {
		return this.columnId;
	}

	public void setColumnId(String columnId) {
		this.columnId = columnId;
	}
}
