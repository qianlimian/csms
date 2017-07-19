package com.bycc.dto.locate;

import com.bycc.entity.BdmCamera;

public class CameraDto {
	/**
	 * IP
	 */
	private String cameraIp;
	/**
	 * 用户名
	 */
	private String cameraUserName;
	/**
	 * 密码
	 */
	private String cameraUserPassword;

	public static CameraDto toDto(BdmCamera entity) {
		CameraDto dto = new CameraDto();
		dto.setCameraIp(entity.getIp());
		dto.setCameraUserName(entity.getUserName());
		dto.setCameraUserPassword(entity.getPassword());

		return dto;
	}

	public String getCameraIp() {
		return cameraIp;
	}

	public void setCameraIp(String cameraIp) {
		this.cameraIp = cameraIp;
	}

	public String getCameraUserName() {
		return cameraUserName;
	}

	public void setCameraUserName(String cameraUserName) {
		this.cameraUserName = cameraUserName;
	}

	public String getCameraUserPassword() {
		return cameraUserPassword;
	}

	public void setCameraUserPassword(String cameraUserPassword) {
		this.cameraUserPassword = cameraUserPassword;
	}

}