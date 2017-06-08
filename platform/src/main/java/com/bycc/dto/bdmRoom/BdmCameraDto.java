package com.bycc.dto.bdmRoom;

import com.bycc.entity.BdmCamera;
import com.bycc.enumitem.DeviceStatus;
import com.bycc.service.bdmRoom.BdmRoomService;
import org.smartframework.platform.dto.annotation.DtoClass;
import org.smartframework.utils.helper.DateHelper;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.constraints.NotNull;
import java.text.ParseException;

/**
 * Created by wanghaidong on 2017/4/18.
 */
@DtoClass
public class BdmCameraDto {
    /**
     * 摄像头id
     */
    private Integer id;
    /**
     * 摄像头名称
     */
    @NotNull(message = "不能为空")
    private  String name;
    /**
     * 摄像头ip
     */
    @NotNull(message = "不能为空")
    private String ip;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 设备状态
     */
    private String status;
    /**
     * 备注
     */
    private String note;

    /**
     * ToDto
     * @param entity
     * @return
     */
    public static BdmCameraDto toDto(BdmCamera entity){
        BdmCameraDto dto = new BdmCameraDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setIp(entity.getIp());
        dto.setStatus(entity.getStatus().key());
        dto.setNote(entity.getNote());
        dto.setUsername(entity.getUserName());
        dto.setPassword(entity.getPassword());
//        dto.setOperatorId();
//        dto.setInsertDate();
//        dto.setUpdateDate();
        return dto;
    }

    /**
     * ToEntity
     * @param dto
     * @return
     */
    public static BdmCamera toEntity(BdmCameraDto dto) {
        BdmCamera entity = new BdmCamera();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setIp(dto.getIp());
        entity.setStatus(DeviceStatus.getMatchByKey(dto.getStatus()));
        entity.setNote(dto.getNote());
        entity.setUserName(dto.getUsername());
        entity.setPassword(dto.getPassword());
//        entity.setOperatorId();
//        entity.setInsertDate();
//        entity.setUpdateDate();
        return entity;
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

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
