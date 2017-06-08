package org.smartframework.manager.service.group;

import java.util.*;

import org.smartframework.common.kendo.QueryBean;
import org.smartframework.manager.dao.group.GroupDao;
import org.smartframework.manager.dao.menu.MenuDao;
import org.smartframework.manager.dao.operate.OperateDao;
import org.smartframework.manager.dao.role.RoleDao;
import org.smartframework.manager.dao.user.UserDao;
import org.smartframework.manager.dto.common.CheckboxDto;
import org.smartframework.manager.entity.*;
import org.smartframework.manager.dto.group.GroupDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupServiceImpl implements GroupService {

    @Autowired
    private GroupDao groupDao;

    @Autowired
    private RoleDao roleDao;
    
    @Autowired
    private UserDao userDao;
    
    @Autowired
    private MenuDao menuDao;

    @Autowired
    private OperateDao operateDao;

    /**
     * 根据条件查询
     */
    @Override
    public List<GroupDto> query(QueryBean qb) {
        //调用全局dao查询
        List<Group> list = groupDao.findByQueryBean(qb);
        List<GroupDto> dtos = new ArrayList<GroupDto>();
        for (Group group : list) {
            GroupDto dto = GroupDto.toDto(group);
            dtos.add(dto);
        }
        return dtos;
    }

    /**
     * 根据id查找
     */
    @Override
    public GroupDto findById(Integer id) {
        Group role = groupDao.findOne(id);
        GroupDto dto = GroupDto.toDto(role);
        return dto;
    }

    /**
     * 保存
     */
    @Override
    public GroupDto save(GroupDto dto) {
        Group role = null;
        if(dto.getId() == null){
            role = dto.toEntity();
        }else{
            role = groupDao.findOne(dto.getId());
            if(role != null){
                dto.toEntity(role);
            }
        }
        role = groupDao.save(role);
        dto = GroupDto.toDto(role);
        return dto;
    }

    /**
     * 删除
     */
    @Override
    public void delete(String ids) {
        for (String id : ids.split(",")) {
            Group role = groupDao.findOne(Integer.parseInt(id));
            if (role != null) {
                groupDao.delete(role);
            }
        }
    }

    /**
     * 保存组用户
     */
    @Override
    public void addUsers(Integer groupId, String userIds) {
        Group group = groupDao.findOne(groupId);

        for (String userId : userIds.split(",")) {
            User user = userDao.findOne(Integer.parseInt(userId));
            if (user != null) {
                List<Group> groups = user.getGroups();
                if (!groups.contains(group)) {
                    groups.add(group);
                }
                userDao.save(user);
            }
        }
    }

    /**
     * 删除组用户
     */
    @Override
    public void removeUsers(Integer groupId, String userIds) {
        Group group = groupDao.findOne(groupId);

        for (String userId : userIds.split(",")) {
            User user = userDao.findOne(Integer.parseInt(userId));
            if (user != null) {
                user.getGroups().remove(group);
                userDao.save(user);
            }
        }
    }

    /**
     * 查询组菜单
     */
    @Override
    public List<Integer> getMenus(Integer groupId) {
        List<Integer> result = new ArrayList<Integer>();
        Group group = groupDao.findOne(groupId);
        Set<Menu> menus = group.getMenus();
        for (Menu menu : menus) {
            result.add(menu.getId());
        }
        return result;
    }

    /**
     * 保存组菜单
     */
    @Override
    public void saveMenus(Integer groupId, CheckboxDto checkboxDto) {
        Group group = groupDao.findOne(groupId);

        for (Integer menuId : checkboxDto.getUnchecked()) {
            Menu menu = menuDao.findOne(menuId);
            group.getMenus().remove(menu);
        }

        for (Integer menuId : checkboxDto.getChecked()) {
            Menu menu = menuDao.findOne(menuId);
            group.getMenus().add(menu);
        }

        groupDao.save(group);
    }


    /**
     * 查询组操作
     */
    @Override
    public List<Integer> getOperates(Integer groupId) {
        List<Integer> result = new ArrayList<Integer>();
        Group group = groupDao.findOne(groupId);
        Set<Operate> operates = group.getOperates();
        for (Operate operate : operates) {
            result.add(operate.getId());
        }
        return result;
    }

    /**
     * 保存组操作
     */
    @Override
    public void saveOperates(Integer groupId, CheckboxDto checkboxDto) {
        Group group = groupDao.findOne(groupId);

        for (Integer operateId : checkboxDto.getUnchecked()) {
            Operate operate = operateDao.findOne(operateId);
            group.getOperates().remove(operate);
        }

        for (Integer operateId : checkboxDto.getChecked()) {
            Operate operate = operateDao.findOne(operateId);
            group.getOperates().add(operate);
        }

        groupDao.save(group);
    }

    /**
     * 查询组角色
     */
    @Override
    public List<String> getRoles(Integer groupId) {
        List<String> result = new ArrayList<String>();
        Group group = groupDao.findOne(groupId);
        List<Role> roles = group.getRoles();
        for (Role role : roles) {
            result.add(role.getName());
        }
        return result;
    }

    /**
     * 保存组角色
     */
    @Override
    public void saveRoles(Integer groupId, String roleNames) {
        Group group = groupDao.findOne(groupId);

        group.setRoles(new ArrayList<Role>());
        for (String roleName : roleNames.split(",")) {
            Role role = roleDao.findByName(roleName);
            if (role != null) {
                group.getRoles().add(role);
            }
        }
        groupDao.save(group);
    }
}
