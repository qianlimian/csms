package org.smartframework.manager.service.role;

import java.util.List;

import org.smartframework.common.kendo.QueryBean;
import org.smartframework.manager.dto.role.RoleDto;

public interface RoleService {

	/**
	 * 查询角色列表
	 */
	public List<RoleDto> query(QueryBean qb);

	/**
	 * 查询角色列表
	 */
	public List<RoleDto> queryByGroupId(QueryBean qb, Integer groupId);

	/**
	 * 查询所有
	 */
	public List<RoleDto> findAll();

	/**
	 * 根据id查找
	 */
	public RoleDto findById(Integer id);

	/**
	 * 保存
	 */
	public RoleDto save(RoleDto dto);

	/**
	 * 删除
	 */
	void delete(String ids);

}
