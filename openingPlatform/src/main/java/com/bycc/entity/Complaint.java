package com.bycc.entity;

import com.bycc.enumitem.ReplyStatus;
import com.bycc.enumitem.ReplyType;

import org.smartframework.manager.entity.LoginUser;
import org.smartframework.manager.entity.User;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author gaoningbo
 * @description 投诉
 * @date 2017年7月5日
 */
@Entity
@Table(name = "complaint")
@TableGenerator(name = "com.bycc.entity.Complaint",
		table = "ID_Sequence",
		pkColumnName = "KEY_ID_",
		valueColumnName = "GEN_VALUE_",
		pkColumnValue = "com.bycc.entity.Complaint",
		initialValue = 1,
		allocationSize = 1)
public class Complaint implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id_")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "com.bycc.entity.Complaint")
	private Integer id;
	
	/**
	 * 投诉标题
	 */
	@Column(name = "name_", length = 100)
	private String name;
	
	/**
	 * 投诉标题
	 */
	@Column(name = "tel_", length = 100)
	private String tel;
	
	/**
	 * 投诉标题
	 */
	@Column(name = "email_", length = 100)
	private String email;

	/**
	 * 投诉标题
	 */
	@Column(name = "title_", length = 100)
	private String title;

	/**
	 * 投诉内容
	 */
	@Column(name = "content_", length = 1000)
	private String content;

	/**
	 * 回复状态
	 */
	@Enumerated(EnumType.STRING)
	@Column(name = "reply_status_")
	private ReplyStatus replyStatus;

	/**
	 * 回复方式
	 */
	@Enumerated(EnumType.STRING)
	@Column(name = "reply_type_")
	private ReplyType replyType;

	/**
	 * 投诉处理结果
	 */
	@Column(name = "result_", columnDefinition = "text")
	private String result;

	/**
	 * 插入时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "insertDate_")
	private Date insertDate;

	/**
	 * 修改时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updateDate_")
	private Date updateDate;

	/**
	 * 操作人
	 */
	@Column(name = "operator_")
	private String operator;

	@PrePersist
	public void prePersist() {
		this.insertDate = new Date();
		this.replyStatus = ReplyStatus.UNREPLY;
		LoginUser currentUser = User.getCurrentUser();
		this.operator = currentUser != null ? currentUser.getLoginName() : null;
	}

	@PreUpdate
	public void PreUpdate() {
		this.updateDate = new Date();
		LoginUser currentUser = User.getCurrentUser();
		this.operator = currentUser != null ? currentUser.getLoginName() : null;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public ReplyStatus getReplyStatus() {
		return replyStatus;
	}

	public void setReplyStatus(ReplyStatus replyStatus) {
		this.replyStatus = replyStatus;
	}

	public ReplyType getReplyType() {
		return replyType;
	}

	public void setReplyType(ReplyType replyType) {
		this.replyType = replyType;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
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

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
