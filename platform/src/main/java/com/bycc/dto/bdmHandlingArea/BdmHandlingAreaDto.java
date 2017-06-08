package com.bycc.dto.bdmHandlingArea;

import com.bycc.entity.BdmHandlingArea;
import com.bycc.entity.BdmPoliceStation;
import org.hibernate.validator.constraints.NotEmpty;
import org.smartframework.platform.dto.annotation.DtoClass;

import javax.validation.constraints.NotNull;

/**
 * Created by wanghaidong on 2017/4/14.
 */
@DtoClass
public class BdmHandlingAreaDto {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 编号
     */
    @NotEmpty(message = "不能为空")
    private String code;

    /**
     * 名称
     */
    @NotEmpty(message = "不能为空")
    private String name;


    /**
     * 地址
     */
    private String address;

    /**
     * 备注
     */
    private String note;

    /**
     * 派出所
     */
    @NotNull(message="不能为空")
    private Integer policeStationId;
    @NotEmpty(message = "不能为空")
    private String policeStationName;

    /**
     * 子model: 腕带
     */
    private CudStrapDto strap;
    /**
     * 子model: 物品柜
     */
    private CudCabinDto cabin;

    /**
     * toDto
     */
    public static BdmHandlingAreaDto toDto(BdmHandlingArea bdmPoliceStation) {
        BdmHandlingAreaDto dto = new BdmHandlingAreaDto();
        dto.setId(bdmPoliceStation.getId());
        dto.setCode(bdmPoliceStation.getCode());
        dto.setName(bdmPoliceStation.getName());
        dto.setAddress(bdmPoliceStation.getAddress());
        dto.setPoliceStationId(bdmPoliceStation.getPoliceStation().getId());
        dto.setPoliceStationName(bdmPoliceStation.getPoliceStation().getName());
        return dto;
    }


    /**
     * toEntity
     * @param dto
     * @return
     */
    public static BdmHandlingArea toEntity(BdmHandlingAreaDto dto, BdmPoliceStation ps) {
        BdmHandlingArea entity = new BdmHandlingArea();
        entity.setId(dto.getId());
        entity.setCode(dto.getCode());
        entity.setName(dto.getName());
        entity.setAddress(dto.getAddress());
        entity.setNote(dto.getNote());
        entity.setPoliceStation(ps);
//        entity.setOperatorId();
//        entity.setInsertDate(new Date());
//        entity.setUpdateDate(new Date());
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public CudStrapDto getStrap() {
        return strap;
    }

    public void setStrap(CudStrapDto strap) {
        this.strap = strap;
    }

    public CudCabinDto getCabin() {
        return cabin;
    }

    public void setCabin(CudCabinDto cabin) {
        this.cabin = cabin;
    }

    public Integer getPoliceStationId() {
        return policeStationId;
    }

    public void setPoliceStationId(Integer policeStationId) {
        this.policeStationId = policeStationId;
    }

    public String getPoliceStationName() {
        return policeStationName;
    }

    public void setPoliceStationName(String policeStationName) {
        this.policeStationName = policeStationName;
    }
}
