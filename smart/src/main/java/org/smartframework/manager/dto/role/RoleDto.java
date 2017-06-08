package org.smartframework.manager.dto.role;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.smartframework.manager.entity.Role;
import org.smartframework.platform.dto.annotation.DtoClass;
import org.smartframework.utils.helper.DateHelper;

@DtoClass
public class RoleDto {

	private Integer id;

	@NotEmpty(message = "不能为空")
	@Size(max = 50, message = "不能超过{max}个字符")
	private String name;

	@Size(max = 100, message = "不能超过{max}个字符")
	private String desc;

	private String insertDate;

	private String updateDate;

	public Role toEntity() {
		Role role = new Role();
		return toEntity(role);
	}

	public Role toEntity(Role role) {
		role.setName(this.getName());
		role.setDescription(this.getDesc());
		return role;
	}

	public static RoleDto toDto(Role role) {
		RoleDto dto = new RoleDto();
		dto.setId(role.getId());
		dto.setName(role.getName());
		dto.setDesc(role.getDescription());
		dto.setInsertDate(DateHelper.formatDateToString(role.getInsertDate(), "yyyy-MM-dd HH:mm:ss"));
		dto.setUpdateDate(DateHelper.formatDateToString(role.getUpdateDate(), "yyyy-MM-dd HH:mm:ss"));
		return dto;
	}

	/*-----------------------get()&set()-----------------------------------------------*/
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

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getInsertDate() {
		return insertDate;
	}

	public void setInsertDate(String insertDate) {
		this.insertDate = insertDate;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

}
