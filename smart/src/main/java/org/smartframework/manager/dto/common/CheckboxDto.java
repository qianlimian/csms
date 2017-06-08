package org.smartframework.manager.dto.common;

import java.util.ArrayList;
import java.util.List;

public class CheckboxDto {

	private List<Integer> checked = new ArrayList<Integer>();

	private List<Integer> unchecked = new ArrayList<Integer>();;

	public List<Integer> getChecked() {
		return checked;
	}

	public void setChecked(List<Integer> checked) {
		this.checked = checked;
	}

	public List<Integer> getUnchecked() {
		return unchecked;
	}

	public void setUnchecked(List<Integer> unchecked) {
		this.unchecked = unchecked;
	}
}
