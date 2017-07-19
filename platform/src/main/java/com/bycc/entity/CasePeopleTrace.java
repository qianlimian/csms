package com.bycc.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 
 * @description 人员轨迹
 * @author liuxunhua
 * @date 2017年7月5日 上午8:49:11
 *
 */
@Entity
@Table(name = "case_people_trace")
@TableGenerator(name = "com.bycc.entity.CasePeopleTrace",
		table = "ID_Sequence",
		pkColumnName = "KEY_ID_",
		valueColumnName = "GEN_VALUE_",
		pkColumnValue = "com.bycc.entity.CasePeopleTrace",
		initialValue = 1,
		allocationSize = 1
)
public class CasePeopleTrace {
    /**
     * ID
     */
    @Id
    @Column(name = "id_")
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "com.bycc.entity.CasePeopleTrace")
    private Integer id;
    
    /**
     * 涉案人员
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "case_people_id_")
	private CasePeople casePeople;
    
    /**
     * 进入时间
     */
    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "enter_time_")
    private Date enterTime;

    /**
     * 离开时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "leave_time_")
    private Date leaveTime;
    
	/**
	 * 房间名称
	 */
	@Column(name = "room_name_")
	private String roomName;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public CasePeople getCasePeople() {
		return casePeople;
	}

	public void setCasePeople(CasePeople casePeople) {
		this.casePeople = casePeople;
	}

	public Date getEnterTime() {
		return enterTime;
	}

	public void setEnterTime(Date enterTime) {
		this.enterTime = enterTime;
	}

	public Date getLeaveTime() {
		return leaveTime;
	}

	public void setLeaveTime(Date leaveTime) {
		this.leaveTime = leaveTime;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
	
}
