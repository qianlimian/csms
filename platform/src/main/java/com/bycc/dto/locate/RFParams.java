package com.bycc.dto.locate;

import java.io.Serializable;

public class RFParams implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2430964765118759532L;

	/**
	 * 基站ID
	 */
	private Integer devId;
	
	/**
	 * 标签ID
	 */
	private Integer tagId;
	
	/**
	 * 是否在激活区
	 * 
	 * true:在激活区  false:不在激活区
	 */
	private boolean activate;
	
	/**
	 * 电压是否正常
	 * 
	 * false:正常   true:异常
	 */
	private boolean voltage;
	
	/**
	 * 防拆是否正常
	 * 
	 * false:正常   true:异常
	 */
	private boolean tamper;
	
	/**
	 * button1是否按下
	 * 
	 * true:没按下  false:按下
	 */
	private boolean button1;
	
	/**
	 * button2是否按下
	 * 
	 * true:没按下  false:按下
	 */
	private boolean button2;
	
	/**
	 * 增益
	 */
	private Integer gain;
	
	/**
	 * 是否穿越
	 * 
	 * false:没穿越  true:穿越
	 */
	private boolean traverse;
	
	
	/**
	 * 天线1ID
	 */
	private Integer antenna1ID;
	
	/**
	 * 天线1场强
	 */
	private Integer rssi1;
	
	/**
	 * 天线2ID
	 */
	private Integer antenna2ID;
	
	/**
	 * 天线2场强
	 */
	private Integer rssi2;
	
	/**
	 * 激活次数
	 */
	private Integer activatetimes;
	
	/**
	 * 发送次数
	 */
	private Integer sendtimes;
	
	/**
	 * 主机场强
	 */
	private Integer masterRSSI;
	
	/**
	 * 从机场强
	 */
	private Integer slaveRSSI;

	public Integer getDevId() {
		return devId;
	}

	public void setDevId(Integer devId) {
		this.devId = devId;
	}

	public Integer getTagId() {
		return tagId;
	}

	public void setTagId(Integer tagId) {
		this.tagId = tagId;
	}

	public boolean isActivate() {
		return activate;
	}

	public void setActivate(boolean activate) {
		this.activate = activate;
	}

	public boolean isVoltage() {
		return voltage;
	}

	public void setVoltage(boolean voltage) {
		this.voltage = voltage;
	}

	public boolean isTamper() {
		return tamper;
	}

	public void setTamper(boolean tamper) {
		this.tamper = tamper;
	}

	public boolean isButton1() {
		return button1;
	}

	public void setButton1(boolean button1) {
		this.button1 = button1;
	}

	public boolean isButton2() {
		return button2;
	}

	public void setButton2(boolean button2) {
		this.button2 = button2;
	}

	public Integer getGain() {
		return gain;
	}

	public void setGain(Integer gain) {
		this.gain = gain;
	}

	public boolean isTraverse() {
		return traverse;
	}

	public void setTraverse(boolean traverse) {
		this.traverse = traverse;
	}

	public Integer getAntenna1ID() {
		return antenna1ID;
	}

	public void setAntenna1ID(Integer antenna1id) {
		antenna1ID = antenna1id;
	}

	public Integer getRssi1() {
		return rssi1;
	}

	public void setRssi1(Integer rssi1) {
		this.rssi1 = rssi1;
	}

	public Integer getAntenna2ID() {
		return antenna2ID;
	}

	public void setAntenna2ID(Integer antenna2id) {
		antenna2ID = antenna2id;
	}

	public Integer getRssi2() {
		return rssi2;
	}

	public void setRssi2(Integer rssi2) {
		this.rssi2 = rssi2;
	}

	public Integer getActivatetimes() {
		return activatetimes;
	}

	public void setActivatetimes(Integer activatetimes) {
		this.activatetimes = activatetimes;
	}

	public Integer getSendtimes() {
		return sendtimes;
	}

	public void setSendtimes(Integer sendtimes) {
		this.sendtimes = sendtimes;
	}

	public Integer getMasterRSSI() {
		return masterRSSI;
	}

	public void setMasterRSSI(Integer masterRSSI) {
		this.masterRSSI = masterRSSI;
	}

	public Integer getSlaveRSSI() {
		return slaveRSSI;
	}

	public void setSlaveRSSI(Integer slaveRSSI) {
		this.slaveRSSI = slaveRSSI;
	}
}
