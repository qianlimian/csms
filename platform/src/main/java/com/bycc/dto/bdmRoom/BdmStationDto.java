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
     * 基站编号
     */
    @NotNull(message = "不能为空")
    private Integer code;
    /**
     * 基站状态
     */
    private String status;
    /**
     * 基站IP
     */
    @NotNull(message = "不能为空")
    private String ip;
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
        if (entity != null) {
        	BdmStationDto dto = new BdmStationDto();
        	dto.setId(entity.getId());
            dto.setName(entity.getName());
            dto.setCode(entity.getCode());
            dto.setStatus(entity.getStatus() != null ? entity.getStatus().key() : null);
            dto.setNote(entity.getNote());
            dto.setIp(entity.getIp());
            return dto;
		}
		return new BdmStationDto();
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
        entity.setCode(dto.getCode());
        entity.setStatus(DeviceStatus.getMatchByKey(dto.getStatus()));
        entity.setNote(dto.getNote());
        entity.setIp(dto.getIp());
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

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
}
