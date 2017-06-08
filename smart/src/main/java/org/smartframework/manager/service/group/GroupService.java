package org.smartframework.manager.service.group;

import java.util.List;

import org.smartframework.common.kendo.QueryBean;
import org.smartframework.manager.dto.common.CheckboxDto;
import org.smartframework.manager.dto.group.GroupDto;

public interface GroupService {
    
    /**
     * 查询用户组列表
     */
    List<GroupDto> query(QueryBean qb);

    /**
     * 查询用户组
     */
    GroupDto findById(Integer id);

    /**
     * 保存
     */
    public GroupDto save(GroupDto dto);

    /**
     * 删除
     */
    void delete(String ids);

    /**
     * 保存组用户
     */
    void addUsers(Integer groupId, String userIds);

    /**
     * 删除组用户
     */
    void removeUsers(Integer groupId, String userIds);

    /**
     * 查询组菜单
     */
    List<Integer> getMenus(Integer groupId);

    /**
     * 保存组菜单
     */
    void saveMenus(Integer groupId, CheckboxDto checkboxDto);

    /**
     * 查询组操作
     */
    List<Integer> getOperates(Integer groupId);

    /**
     * 保存组操作
     */
    void saveOperates(Integer groupId, CheckboxDto checkboxDto);

    /**
     * 查询组角色
     */
    List<String> getRoles(Integer groupId);

    /**
     * 保存组角色
     */
    void saveRoles(Integer groupId, String roles);
}
