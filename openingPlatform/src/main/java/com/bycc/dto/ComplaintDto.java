package com.bycc.dto;

import com.bycc.common.ExcelColumn;
import com.bycc.entity.Complaint;
import org.hibernate.validator.constraints.NotBlank;
import org.smartframework.platform.dto.annotation.DtoClass;
import org.smartframework.utils.helper.DateHelper;

/**
 * Description: 投诉dto
 * User: yumingzhe
 * Time: 2017-7-10
 */
@DtoClass
public class ComplaintDto {

	/**
	 * 主键
	 */
	private Integer id;

	/**
	 * 投诉标题
	 */
	@ExcelColumn(title = "标题")
	@NotBlank(message = "不能为空") 
	private String title;

	/**
	 * 投诉内容
	 */
	@ExcelColumn(title = "投诉内容")
	@NotBlank(message = "不能为空") 
	private String content;

	/**
	 * 投诉人姓名
	 */
	@ExcelColumn(title = "投诉人姓名")
	@NotBlank(message = "不能为空") 
	private String name;
	
	/**
	 * 投诉人电话
	 */
	@ExcelColumn(title = "电话")
	@NotBlank(message = "不能为空") 
	private String tel;

	/**
	 * 投诉人邮箱
	 */
	@ExcelColumn(title = "邮箱")
	@NotBlank(message = "不能为空") 
	private String email;

	/**
	 * 回复状态
	 */
	@ExcelColumn(title = "回复状态")
	private String replyStatus;

	/**
	 * 回复类型
	 */
	@ExcelColumn(title = "回复类型")
	private String replyType;

	/**
	 * 投诉处理结果
	 */
	@ExcelColumn(title = "投诉处理结果")
	private String result;

	/**
	 * 插入时间
	 */
	private String insertDate;

	/**
	 * 修改时间
	 */
	private String updateDate;

	//-----------------Convert------------------
	public static ComplaintDto toDto(Complaint entity) {
		if (entity == null) {
			return null;
		}
		ComplaintDto dto = new ComplaintDto();
		dto.setId(entity.getId());
		dto.setContent(entity.getContent());
		dto.setResult(entity.getResult());
		dto.setTitle(entity.getTitle());
		if (entity.getInsertDate() != null) {
			dto.setInsertDate(DateHelper.formatDateToString(entity.getInsertDate(), "yyyy-MM-dd HH:mm:ss"));
		}
		if (entity.getUpdateDate() != null) {
			dto.setUpdateDate(DateHelper.formatDateToString(entity.getUpdateDate(), "yyyy-MM-dd HH:mm:ss"));
		}
		if (entity.getReplyStatus() != null) {
			dto.setReplyStatus(entity.getReplyStatus().key());
		}
		if (entity.getReplyType() != null) {
			dto.setReplyType(entity.getReplyType().key());
		}

		dto.setEmail(entity.getEmail());
		dto.setName(entity.getName());
		dto.setTel(entity.getTel());
		return dto;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getInsertDate() {
		return insertDate;
	}

	public void setInsertDate(String insertDate) {
		this.insertDate = insertDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getReplyStatus() {
		return replyStatus;
	}

	public void setReplyStatus(String replyStatus) {
		this.replyStatus = replyStatus;
	}

	public String getReplyType() {
		return replyType;
	}

	public void setReplyType(String replyType) {
		this.replyType = replyType;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
}
