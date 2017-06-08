package org.smartframework.manager.service.role;

import java.util.ArrayList;
import java.util.List;

import org.smartframework.common.kendo.QueryBean;
import org.smartframework.manager.dao.role.RoleDao;
import org.smartframework.manager.entity.Role;
import org.smartframework.manager.dto.role.RoleDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao dao;

    /**
     * 查询角色列表
     */
    @Override
    public List<RoleDto> query(QueryBean qb) {
        //调用全局dao查询
        List<Role> roles = dao.findByQueryBean(qb);
        List<RoleDto> dtos = new ArrayList<RoleDto>();
        for (Role role : roles) {
            RoleDto dto = RoleDto.toDto(role);
            dtos.add(dto);
        }
        return dtos;
    }

    /**
     * 查询角色列表
     */
    @Override
    public List<RoleDto> queryByGroupId(QueryBean qb, Integer groupId) {
        List<Role> list = dao.findByGroupId(qb, groupId);
        List<RoleDto> dtos = new ArrayList<RoleDto>();
        for (Role role : list) {
            RoleDto dto = RoleDto.toDto(role);
            dtos.add(dto);
        }
        return dtos;
    }

    /**
     * 查询所有
     */
    @Override
    public List<RoleDto> findAll() {
        List<Role> roles = dao.findAll();
        List<RoleDto> dtos = new ArrayList<RoleDto>();
        for (Role role : roles) {
            RoleDto dto = RoleDto.toDto(role);
            dtos.add(dto);
        }
        return dtos;
    }

    /**
     * 根据id查找
     */
    @Override
    public RoleDto findById(Integer id) {
        Role role = dao.findOne(id);
        RoleDto dto = RoleDto.toDto(role);
        return dto;
    }


    /**
     * 保存
     */
    @Override
    public RoleDto save(RoleDto dto) {
        Role role = null;
        if(dto.getId() == null){
            role = dto.toEntity();
        }else{
            role = dao.findOne(dto.getId());
            if(role != null){
                dto.toEntity(role);
            }
        }
        role = dao.save(role);
        dto = RoleDto.toDto(role);
        return dto;
    }

    /**
     * 删除
     */
    @Override
    public void delete(String ids) {
        for (String id : ids.split(",")) {
            Role role = dao.findOne(Integer.parseInt(id));
            if (role != null) {
                dao.delete(role);
            }
        }
    }

}
