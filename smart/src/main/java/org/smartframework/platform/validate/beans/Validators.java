package org.smartframework.platform.validate.beans;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Validators {
	
	private List<ValidatorBean> smart_validator = new ArrayList();

	@JsonProperty("smart_validator")
	public List<ValidatorBean> getValidatorBeans() {
		return this.smart_validator;
	}

	public void addValidatorBean(ValidatorBean valid) {
		this.smart_validator.add(valid);
	}
}
