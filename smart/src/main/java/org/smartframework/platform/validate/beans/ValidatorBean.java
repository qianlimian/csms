package org.smartframework.platform.validate.beans;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

public class ValidatorBean {
	private static final Logger LOG = Logger.getLogger(ValidatorBean.class);
	public static final String FORM = "form";
	public static final String GRID = "grid";
	private String type;
	private String className;
	private List<ValidatorErrorInfo> errors;

	public ValidatorBean(String type, String className) {
		this.type = type;
		this.className = className;
		this.errors = new ArrayList();
	}

	public void addValidatorErrorInfo(ValidatorErrorInfo info) {
		if (info == null) {
			throw new RuntimeException("新增校验异常对象时增加的对象为null");
		}
		if (this.errors == null) {
			this.errors = new ArrayList();
		}
		if (this.errors.size() == 0) {
			this.errors.add(info);
			return;
		}
		if ("form".equals(this.type)) {
			for (ValidatorErrorInfo errorInfo : this.errors) {
				if (errorInfo.getPropIndex().equals(info.getPropIndex())) {
					List<String> msgs = errorInfo.getErrorMsg();
					msgs.add((String) info.getErrorMsg().get(0));
					return;
				}
			}
			this.errors.add(info);
		} else if ("grid".equals(this.type)) {
			for (ValidatorErrorInfo errorInfo : this.errors) {
				if ((errorInfo.getColumnId().equals(info.getColumnId()))
						&& (errorInfo.getRowId().equals(info.getRowId()))) {
					List<String> msgs = errorInfo.getErrorMsg();
					msgs.add((String) info.getErrorMsg().get(0));
					return;
				}
			}
			this.errors.add(info);
		} else {
			LOG.error("error : 新增校验对象时试图添加简单组建和grid组件之外的类型");
		}
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getClassName() {
		return this.className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public List<ValidatorErrorInfo> getErrors() {
		return this.errors;
	}

	public void setErrors(List<ValidatorErrorInfo> errors) {
		this.errors = errors;
	}
}
