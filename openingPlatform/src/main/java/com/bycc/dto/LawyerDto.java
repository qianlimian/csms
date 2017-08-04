
package com.bycc.dto;



import com.bycc.common.ExcelColumn;
import com.bycc.entity.Lawyer;
import com.bycc.enumitem.CertificateStatus;
import org.hibernate.validator.constraints.NotEmpty;
import org.smartframework.manager.entity.User;
import org.smartframework.platform.dto.annotation.DtoClass;
import org.smartframework.utils.helper.DateHelper;

import java.util.Date;

/**
 * @description 律师
 * @author gaoningbo
 * @date 2017年7月11日
 * 
 */
@DtoClass
public class LawyerDto {
	private Integer id;
	/**
	 * 姓名
	 */
	@NotEmpty(message = "不能为空")
	@ExcelColumn(allowBlank=false, title = "律师姓名")
	private String name;
	/**
	 * 领域
	 */
	@ExcelColumn(allowBlank=true, title = "专业领域")
	private String domain;
	/**
	 * 证件号
	 */
	@ExcelColumn(allowBlank=true, title = "执业证号")
	private String registrationNum;
	/**
	 * 事务所
	 */
	@ExcelColumn(allowBlank=true, title = "所属事务所")
	private String lawyerOffice;
	/**
	 * 注册时间
	 */
	@ExcelColumn(allowBlank=true, title = "从业时间")
	private String registerDate;
	/**
	 * 证件状态
	 */
	@ExcelColumn(allowBlank=true, title = "状态")
	private String status;
	/**
	 * 联系电话
	 */
	@ExcelColumn(allowBlank=true, title = "联系方式")
	private String phone;

	/**
	 * 简介
	 */
	@ExcelColumn(allowBlank=true, title = "简介")
	private String info;

	/**
	 * 电子邮件
	 */
	@ExcelColumn(allowBlank = true,title = "电子邮件")
	private String email;

	public static LawyerDto toDto(Lawyer entity) {
		LawyerDto dto = new LawyerDto();
		dto.setId(entity.getId());
		dto.setName(entity.getName());
		dto.setDomain(entity.getDomain());
		dto.setLawyerOffice(entity.getLawyerOffice());
		dto.setRegisterDate(DateHelper.formatDateToString(entity.getRegisterDate(), "yyyy-MM-dd"));
		dto.setRegistrationNum(entity.getRegistrationNum());
		dto.setStatus(entity.getStatus() != null ? entity.getStatus().value() : null);
		dto.setPhone(entity.getPhone());
		dto.setInfo(entity.getInfo());
		dto.setEmail(entity.getEmail());
		return dto;
	}

	public Lawyer toEntity()throws Exception{
		Lawyer lawyer = new Lawyer();
		lawyer.setInsertDate(new Date());
		lawyer.setOperatorId(User.getCurrentUser().getId());
		return toEntity(lawyer);
	}

	public Lawyer toEntity(Lawyer entity)throws Exception{
		entity.setName(this.name);
		entity.setDomain(this.domain);
		entity.setRegistrationNum(this.registrationNum);
		entity.setLawyerOffice(this.lawyerOffice);
		//entity.setStatus(CertificateStatus.getMatchByKey(this.status));
		entity.setStatus(CertificateStatus.getMatchByValue(this.status));
		entity.setRegisterDate(DateHelper.formatStringToDate(this.registerDate,"yyyy-MM-dd"));
		entity.setPhone(this.phone);
		entity.setInfo(this.info);
		entity.setEmail(this.email);
		return  entity;
	}



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

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getRegistrationNum() {
		return registrationNum;
	}

	public void setRegistrationNum(String registrationNum) {
		this.registrationNum = registrationNum;
	}

	public String getLawyerOffice() {
		return lawyerOffice;
	}

	public void setLawyerOffice(String lawyerOffice) {
		this.lawyerOffice = lawyerOffice;
	}

	public String getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(String registerDate) {
		this.registerDate = registerDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPhone() {return phone;	}

	public void setPhone(String phone) {this.phone = phone; }

	public String getInfo() {return info;}

	public void setInfo(String info) {this.info = info;	}

	public String getEmail() {return email;}

	public void setEmail(String email) {this.email = email;}
}
