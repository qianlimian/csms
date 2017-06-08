package com.bycc.dto.bdmHandlingArea;

import com.bycc.entity.BdmCabinet;
import com.bycc.enumitem.UsageStatus;
import org.smartframework.platform.dto.annotation.DtoClass;

/**
 * Created by wanghaidong on 2017/4/20.
 *
 */
@DtoClass
public class BdmCabinetDto {
    /**
     * id
     */
    private Integer id;
    /**
     * 编码
     */
    private String code;
    /**
     * 名称
     */
    private String name;
    /**
     * 使用状态
     */
    private String status;
    /**
     * 备注
     */
    private String note;


    /**
     * toDto
     * @param entity
     * @return
     */
    public static BdmCabinetDto toDto(BdmCabinet entity){
        BdmCabinetDto dto = new BdmCabinetDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setCode(entity.getCode());
        dto.setStatus(entity.getStatus().key());
        dto.setNote(entity.getNote());
        return dto;
    }

    /**
     * toEntity
     * @return
     */
    public static BdmCabinet toEntity(BdmCabinetDto dto) {
        BdmCabinet entity = new BdmCabinet();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setCode(dto.getCode());
        entity.setStatus(UsageStatus.getMatchByKey(dto.getStatus()));
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
