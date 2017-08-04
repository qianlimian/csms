package com.bycc.dto.bdmPoliceStation;

import com.bycc.entity.BdmPoliceStation;
import com.bycc.enumitem.AreaType;
import com.bycc.enumitem.PoliceStationType;
import org.hibernate.validator.constraints.NotEmpty;
import org.smartframework.platform.dto.annotation.DtoClass;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.Pattern;

@DtoClass
public class BdmPoliceStationDto {

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
     * 地区
     */
    @NotEmpty(message = "不能为空")
    private String areaType;
    
    /**
     * 类型
     */
    @NotEmpty(message = "不能为空")
    private String policeStationType;

    /**
     * 地址
     */
    private String address;
    
    /**
     * 流媒体服务器IP
     */
    @Pattern(regexp = "^$|([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}", message = "IP地址格式不正确")
    private String ip;

    /**
     * 备注
     */
    private String note;

    /**
     * 子新增列表
     */
    private List<BdmPoliceDto> news = new ArrayList<BdmPoliceDto>();
    /**
     * 子编辑列表
     */
    private List<BdmPoliceDto> updates = new ArrayList<BdmPoliceDto>();
    /**
     * 子删除列表
     */
    private List<Integer> deletes = new ArrayList<Integer>();


    public static List<BdmPoliceStationDto> toDtoList(List<BdmPoliceStation> policeStations) {
        List<BdmPoliceStationDto> dtos = new ArrayList<BdmPoliceStationDto>();
        for (BdmPoliceStation policeStation : policeStations) {
            dtos.add(toDto(policeStation));
        }
        return dtos;
    }

    public static BdmPoliceStationDto toDto(BdmPoliceStation policeStation) {
        BdmPoliceStationDto dto = new BdmPoliceStationDto();
        dto.setId(policeStation.getId());
        dto.setCode(policeStation.getCode());
        dto.setName(policeStation.getName());
        dto.setAreaType(policeStation.getAreaType() != null ? policeStation.getAreaType().key() : null);
        dto.setPoliceStationType(policeStation.getPoliceStationType() != null ? policeStation.getPoliceStationType().key() : null);
        dto.setAddress(policeStation.getAddress());
        dto.setIp(policeStation.getIp());
        return dto;
    }

    public BdmPoliceStation toEntity(BdmPoliceStation policeStation) {
        policeStation.setCode(this.getCode());
        policeStation.setName(this.getName());
        policeStation.setAreaType(AreaType.getMatchByKey(this.getAreaType()));
        policeStation.setPoliceStationType(PoliceStationType.getMatchByKey(this.getPoliceStationType()));
        policeStation.setAddress(this.getAddress());
        policeStation.setNote(this.getNote());
        policeStation.setIp(this.getIp());
        return policeStation;
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

    public String getAreaType() {
        return areaType;
    }

    public void setAreaType(String areaType) {
        this.areaType = areaType;
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

    public List<BdmPoliceDto> getNews() {
        return news;
    }

    public void setNews(List<BdmPoliceDto> news) {
        this.news = news;
    }

    public List<BdmPoliceDto> getUpdates() {
        return updates;
    }

    public void setUpdates(List<BdmPoliceDto> updates) {
        this.updates = updates;
    }

    public List<Integer> getDeletes() {
        return deletes;
    }

    public void setDeletes(List<Integer> deletes) {
        this.deletes = deletes;
    }

	public String getPoliceStationType() {
		return policeStationType;
	}

	public void setPoliceStationType(String policeStationType) {
		this.policeStationType = policeStationType;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
}
