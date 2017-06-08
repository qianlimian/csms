package com.bycc.dto.bdmRoom;

import com.bycc.entity.BdmRoom;
import com.bycc.enumitem.RoomType;
import com.bycc.enumitem.UsageStatus;
import org.hibernate.validator.constraints.NotEmpty;
import org.smartframework.platform.dto.annotation.DtoClass;
import org.smartframework.utils.helper.StringHelper;

/**
 * Created by wanghaidong on 2017/4/18.
 */
@DtoClass
public class BdmRoomDto {
    /**
     * 主键
     */
    private Integer id;
    /**
     * 房间名称
     */
    @NotEmpty(message = "不能为空")
    private String name;
    /**
     * 房间编号
     */
    @NotEmpty(message = "不能为空")
    private String code;
    /**
     * 房间类型
     */
    @NotEmpty(message = "不能为空")
    private String roomType;
    /**
     * 使用状态
     */
    private String status;
    /**
     * 备注
     */
    private String note;
    /**
     * 所属办案区id
     */
    private Integer handlingAreaId;

    /**
     * 房间布局位置
     */
    private String position;

    /**
     * 子model
     */
    private CudStationDto station;
    private CudCameraDto camera;

    /**
     * toDto
     * @param entity
     * @return
     */
    public static BdmRoomDto toDto(BdmRoom entity){
        BdmRoomDto dto = new BdmRoomDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setCode(entity.getCode());
        dto.setRoomType(entity.getRoomType().key());
        dto.setStatus(entity.getStatus().key());
        dto.setPosition(entity.getPosition());
        dto.setNote(entity.getNote());
        dto.setHandlingAreaId(entity.getHandlingArea().getId());
//        dto.setOperatorId();
//        dto.setInsertDate();
//        dto.setUpdateDate();
        return dto;
    }

    /**
     * toEntity
     * @return
     */
    public BdmRoom toEntity() {
        BdmRoom entity = new BdmRoom();
        entity.setId(this.getId());
        entity.setName(this.getName());
        entity.setCode(this.getCode());
        entity.setRoomType(RoomType.getMatchByKey(this.getRoomType()));
        entity.setStatus(StringHelper.isAllEmpty(this.getStatus()) ? UsageStatus.UNUSED : UsageStatus.getMatchByKey(this.getStatus()));
        entity.setNote(this.getNote());
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
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

    public Integer getHandlingAreaId() {
        return handlingAreaId;
    }

    public void setHandlingAreaId(Integer handlingAreaId) {
        this.handlingAreaId = handlingAreaId;
    }

    public CudStationDto getStation() {
        return station;
    }

    public void setStation(CudStationDto station) {
        this.station = station;
    }

    public CudCameraDto getCamera() {
        return camera;
    }

    public void setCamera(CudCameraDto camera) {
        this.camera = camera;
    }
    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
