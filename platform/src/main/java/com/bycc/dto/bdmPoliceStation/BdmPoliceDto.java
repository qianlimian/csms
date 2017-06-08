package com.bycc.dto.bdmPoliceStation;

import com.bycc.entity.BdmPolice;
import com.bycc.entity.BdmPoliceStation;
import com.bycc.enumitem.DutyType;
import org.hibernate.validator.constraints.NotEmpty;
import org.smartframework.manager.entity.User;
import org.smartframework.platform.dto.annotation.DtoClass;
import org.smartframework.platform.validate.beans.GridDto;

import javax.validation.constraints.NotNull;

@DtoClass
public class BdmPoliceDto extends GridDto {

    private Integer id;

    @NotNull(message="不能为空")
    private Integer userId;
    private String userName; //显示值

    @NotEmpty(message = "不能为空")
    private String dutyType;

    public static BdmPoliceDto toDto(BdmPolice police) {
        BdmPoliceDto dto = new BdmPoliceDto();
        dto.setId(police.getId());
        dto.setUserId(police.getUser().getId());
        dto.setUserName(police.getUser().getName());
        dto.setDutyType(police.getDutyType().key());
        return dto;
    }

    public BdmPolice toEntity(BdmPolice police, User user, BdmPoliceStation policeStation) {
        police.setDutyType(DutyType.getMatchByKey(this.getDutyType()));
        police.setUser(user);
        police.setPoliceStation(policeStation);
        return police;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDutyType() {
        return dutyType;
    }

    public void setDutyType(String dutyType) {
        this.dutyType = dutyType;
    }

}
