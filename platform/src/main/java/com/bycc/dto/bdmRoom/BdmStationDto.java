package com.bycc.dto.bdmRoom;


import com.bycc.entity.BdmStation;
import com.bycc.enumitem.DeviceStatus;
import org.smartframework.platform.dto.annotation.DtoClass;

import javax.validation.constraints.NotNull;
import java.text.ParseException;

/**
 * Created by wanghaidong on 2017/4/17.
 */
@DtoClass
public class BdmStationDto {
    /**
     * 主键
     */

    private Integer id;
    /**
     * 基站名称
     */
    @NotNull(message = "不能为空")
    private String name;
    /**
     * 基站ip
     */
    @NotNull(message = "不能为空")
    private String ip;
    /**
     * 基站状态
     */
    private String status;
    /**
     * 备注
     */
    private String note;

    /**
     * to DTO
     *
     * @param entity
     * @return
     */
    public static BdmStationDto toDto(BdmStation entity) {
        BdmStationDto dto = new BdmStationDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setIp(entity.getIp());
        dto.setStatus(entity.getStatus().key());
        dto.setNote(entity.getNote());
        return dto;
    }

    /**
     * to Entity
     *
     * @param dto
     * @return
     * @throws ParseException
     */
    public static BdmStation toEntity(BdmStationDto dto) {
        BdmStation entity = new BdmStation();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setIp(dto.getIp());
        entity.setStatus(DeviceStatus.getMatchByKey(dto.getStatus()));
        entity.setNote(dto.getNote());
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
}
