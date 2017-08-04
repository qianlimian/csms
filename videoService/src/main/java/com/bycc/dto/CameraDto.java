package com.bycc.dto;
public class CameraDto{
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