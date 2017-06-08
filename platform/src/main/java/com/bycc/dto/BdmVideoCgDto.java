package com.bycc.dto;

import com.bycc.entity.BdmVideoCategory;
import org.hibernate.validator.constraints.NotEmpty;
import org.smartframework.platform.dto.annotation.DtoClass;

import javax.validation.constraints.Size;
import java.util.Date;

@DtoClass
public class BdmVideoCgDto {

    private Integer id;

    @NotEmpty(message = "不能为空")
    @Size(max = 50, message = "不能超过{max}个字符")
    private String code;

    @NotEmpty(message = "不能为空")
    @Size(max = 50, message = "不能超过{max}个字符")
    private String name;


    private String note;


    public static BdmVideoCgDto toDto(BdmVideoCategory videoCg) {
        BdmVideoCgDto dto = new BdmVideoCgDto();
        dto.setId(videoCg.getId());
        dto.setCode(videoCg.getCode());
        dto.setName(videoCg.getName());
        dto.setNote(videoCg.getNote());
        return dto;
    }

    public BdmVideoCategory toEntity() {
        BdmVideoCategory videoCg = new BdmVideoCategory();
        videoCg.setInsertDate(new Date());
        return toEntity(videoCg);
    }

    public BdmVideoCategory toEntity(BdmVideoCategory videoCg) {
        videoCg.setCode(this.getCode());
        videoCg.setName(this.getName());
        videoCg.setNote(this.getNote());
        videoCg.setUpdateDate(new Date());
        return videoCg;
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
