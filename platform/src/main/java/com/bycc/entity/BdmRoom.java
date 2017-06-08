package com.bycc.entity;

import com.bycc.enumitem.RoomType;
import com.bycc.enumitem.UsageStatus;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author gaoningbo
 * @description 房间
 * @date 2017年4月12日
 */
@Entity
@Table(name = "bdm_room")
@TableGenerator(name = "com.bycc.entity.BdmRoom", // TableGenerator的名字，下面的“generator”使用
		table = "ID_Sequence", // 存储自增id的表名，都是用统一的表
		pkColumnName = "KEY_ID_", // 列1的字段名
		valueColumnName = "GEN_VALUE_", // 列2的字段名
		pkColumnValue = "com.bycc.entity.BdmRoom", // 该表存在ID_GEN中列1的值
		initialValue = 1, // 初始值
		allocationSize = 1 // 增长率
)
public class BdmRoom implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@Id
	@Column(name = "id_")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "com.bycc.entity.BdmRoom")
	private Integer id;

	/**
	 * 房间编号
	 */
	@Column(name = "code_")
	private String code;

	/**
	 * 房间名称
	 */
	@Column(name = "name_")
	private String name;

	/**
	 * 房间类型
	 */
	@Enumerated(EnumType.STRING)
	@Column(name = "room_type_")
	private RoomType roomType;

	/**
	 * 房间状态
	 */
	@Enumerated(EnumType.STRING)
	@Column(name = "status_")
	private UsageStatus status;

	/**
	 * 房间布局位置
	 */
	@Column(name = "position_")
	private String position;
	/**
	 * 派出所
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "handling_area_id_")
	private BdmHandlingArea handlingArea;

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



	/**
	 * 基站列表
	 */
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "room", fetch = FetchType.LAZY)
	private List<BdmStation> stations = new ArrayList<BdmStation>();

	/**
	 * 摄像头列表
	 */
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "room", fetch = FetchType.LAZY)
	private List<BdmCamera> cameras = new ArrayList<BdmCamera>();

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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public RoomType getRoomType() {
		return roomType;
	}

	public void setRoomType(RoomType roomType) {
		this.roomType = roomType;
	}

	public UsageStatus getStatus() {
		return status;
	}

	public void setStatus(UsageStatus status) {
		this.status = status;
	}

	public BdmHandlingArea getHandlingArea() {
		return handlingArea;
	}

	public void setHandlingArea(BdmHandlingArea handlingArea) {
		this.handlingArea = handlingArea;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
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

    public List<BdmStation> getStations() {
        return stations;
    }

    public void setStations(List<BdmStation> stations) {
        this.stations = stations;
    }

    public List<BdmCamera> getCameras() {
        return cameras;
    }

    public void setCameras(List<BdmCamera> cameras) {
        this.cameras = cameras;
    }
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
}
