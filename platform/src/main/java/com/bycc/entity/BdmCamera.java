package com.bycc.entity;

import java.io.Serializable;
import java.util.Date;

import com.bycc.enumitem.DeviceStatus;

import javax.persistence.*;

/**
 * @description 摄像头
 * @author gaoningbo
 * @date 2017年4月12日
 * 
 */
@Entity
@Table(name = "bdm_camera")
@TableGenerator(name = "com.bycc.entity.BdmCamera", // TableGenerator的名字，下面的“generator”使用
		table = "ID_Sequence", // 存储自增id的表名，都是用统一的表
		pkColumnName = "KEY_ID_", // 列1的字段名
		valueColumnName = "GEN_VALUE_", // 列2的字段名
		pkColumnValue = "com.bycc.entity.BdmCamera", // 该表存在ID_GEN中列1的值
		initialValue = 1, // 初始值
		allocationSize = 1 // 增长率
)
public class BdmCamera implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * ID
	 */
	@Id
	@Column(name = "id_")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "com.bycc.entity.BdmCamera")
	private Integer id;

	/**
	 * 摄像头名称
	 */
	@Column(name = "name_")
	private String name;

	/**
	 * 摄像头IP
	 */
	@Column(name = "ip_")
	private String ip;

	/**
	 * 设备状态
	 */
	@Enumerated(EnumType.STRING)
	@Column(name = "status_")
	private DeviceStatus status;

	/**
	 * 账号
	 */
	@Column(name = "userName_")
	private String userName;
	/**
	 * 密码
	 */
	@Column(name = "password_")
	private String password;
	
	/**
	 * 房间号
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "room_id_")
	private BdmRoom room;

	/**
	 * 备注
	 */
	@Column(name = "note_")
	private String note;

	/**
	 * 操作人
	 */
	@Column(name = "operator_id_")
	private Integer operatorId;

	/**
	 * 插入日期
	 */
	@Temporal(value = TemporalType.TIMESTAMP)
	@Column(name = "insert_date_")
	private Date insertDate;

	/**
	 * 更新时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "update_date_")
	private Date updateDate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public DeviceStatus getStatus() {
		return status;
	}

	public void setStatus(DeviceStatus status) {
		this.status = status;
	}

	public BdmRoom getRoom() {
		return room;
	}

	public void setRoom(BdmRoom room) {
		this.room = room;
	}

	public Integer getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(Integer operatorId) {
		this.operatorId = operatorId;
	}

	public Date getInsertDate() {
		return insertDate;
	}

	public void setInsertDate(Date insertDate) {
		this.insertDate = insertDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
