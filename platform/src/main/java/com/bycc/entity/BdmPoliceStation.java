package com.bycc.entity;

import com.bycc.enumitem.AreaType;
import com.bycc.enumitem.PoliceStationType;
import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 公安局（派出所）
 */
@Entity
@Table(name = "bdm_police_station")
@TableGenerator(name = "com.bycc.entity.BdmPoliceStation",
		table = "ID_Sequence",
		pkColumnName = "KEY_ID_",
		valueColumnName = "GEN_VALUE_",
		pkColumnValue = "com.bycc.entity.BdmPoliceStation",
		initialValue = 1,
		allocationSize = 1
)
@NamedQueries({@NamedQuery(name = "com.bycc.entity.BdmPoliceStation.findAllPoliceStations", query = "SELECT o FROM BdmPoliceStation o order by o.id  ")})
public class BdmPoliceStation implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@Id
	@Column(name = "id_")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "com.bycc.entity.BdmPoliceStation")
	private Integer id;

	/**
	 * 编码
	 */
	@Column(name = "code_")
	private String code;

	/**
	 * 名称
	 */
	@Column(name = "name_")
	private String name;

	/**
	 * 区域
	 */
	@Enumerated(EnumType.STRING)
	@Column(name = "area_type_")
	private AreaType areaType;

	/**
	 * 类型
	 */
	@Enumerated(EnumType.STRING)
	@Column(name = "police_station_type_")
	private PoliceStationType policeStationType;

	/**
	 * 地址
	 */
	@Column(name = "address_")
	private String address;

	/**
	 * 服务器IP
	 */
	@Column(name = "ip_")
	private String ip;

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
	 * 民警列表
	 */
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "policeStation", fetch = FetchType.LAZY)
	private List<BdmPolice> polices = new ArrayList<BdmPolice>();

	public BdmPoliceStation(){}
	//构造函数--构造测试数据用
	public BdmPoliceStation(String code, String name, AreaType areaType, PoliceStationType policeStationType) {
		this.code = code;
		this.name = name;
		this.areaType = areaType;
		this.policeStationType = policeStationType;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public AreaType getAreaType() {
		return areaType;
	}

	public void setAreaType(AreaType areaType) {
		this.areaType = areaType;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public List<BdmPolice> getPolices() {
		return polices;
	}

	public void setPolices(List<BdmPolice> polices) {
		this.polices = polices;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public PoliceStationType getPoliceStationType() {
		return policeStationType;
	}

	public void setPoliceStationType(PoliceStationType policeStationType) {
		this.policeStationType = policeStationType;
	}


	@Override
	public String toString() {
		return "BdmPoliceStation{" +
				"id=" + id +
				", address='" + address + '\'' +
				", areaType=" + areaType +
				", code='" + code + '\'' +
				", name='" + name + '\'' +
				", operatorId=" + operatorId +
				'}';
	}
}
