package com.bycc.dto.bdmHandlingArea;

import com.bycc.entity.BdmStrap;
import com.bycc.enumitem.UsageStatus;
import org.smartframework.platform.dto.annotation.DtoClass;

/**
 * Created by wanghaidong on 2017/4/20.
 */
@DtoClass
public class BdmStrapDto {
    private Integer id;
    private String name;
    private Integer code;
    private String status;
    private String note;

    /**
     * toDto
     * @param entity
     * @return
     */
    public static BdmStrapDto toDto(BdmStrap entity){
        BdmStrapDto dto = new BdmStrapDto();
        dto.setId(entity.getId());
        dto.setCode(entity.getCode());
        dto.setName(entity.getName());
        dto.setStatus(entity.getStatus() != null ? entity.getStatus().key() : null);
        dto.setNote(entity.getNote());
        return dto;


    }
    public static BdmStrap toEntity(BdmStrapDto dto) {
        BdmStrap entity = new BdmStrap();
        entity.setId(dto.getId());
        entity.setCode(dto.getCode());
        entity.setName(dto.getName());
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
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
