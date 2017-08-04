/**
 *
 */
package com.bycc.dto;

import com.bycc.common.ExcelColumn;
import com.bycc.entity.Law;
import org.hibernate.validator.constraints.NotEmpty;
import org.smartframework.platform.dto.annotation.DtoClass;
import org.smartframework.utils.helper.DateHelper;

import java.text.ParseException;
import java.util.Date;

/**
 * @author gaoningbo
 * @description 法律法规
 * @date 2017年7月11日
 */
@DtoClass
public class LawDto {

	private Integer id;

	/**
	 * 标题
	 */
	@NotEmpty(message = "标题不能为空")
	@ExcelColumn(allowBlank = false, title = "内容")
	private String title;

	/**
	 * 内容
	 */
	@NotEmpty(message = "内容不能为空")
	@ExcelColumn(allowBlank = false, title = "内容")
	private String content;
	/**
	 * 发布时间
	 */
	private String publishDate;
	
	public static LawDto toDto(Law law) {
		LawDto dto = new LawDto();
		dto.setId(law.getId());
		dto.setContent(law.getContent());
		dto.setTitle(law.getTitle());
		dto.setPublishDate(DateHelper.formatDateToString(law.getInsertDate(), "yyyy-MM-dd"));
		return dto;
	}

	public Law toEntity() throws ParseException {
		Law law = new Law();
		law.setInsertDate(new Date());
		law.setUpdateDate(new Date());
		return toEntity(law);
	}

	public Law toEntity(Law law) throws ParseException {
		law.setId(this.id);
		law.setContent(this.content);
		law.setTitle(this.title);
		law.setUpdateDate(new Date());
		return law;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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

	public String getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(String publishDate) {
		this.publishDate = publishDate;
	}
	
}
