package com.bycc.service.bdmPoliceStation;

import com.bycc.dto.bdmPoliceStation.BdmPoliceDto;
import com.bycc.dto.bdmPoliceStation.BdmPoliceStationDto;
import com.bycc.entity.BdmPoliceStation;
import org.smartframework.common.kendo.QueryBean;

import java.util.List;

public interface BdmPoliceStationService {

	/**
	 * 按条件查询
	 */
	List<BdmPoliceStationDto> query(QueryBean qb);

	/**
	 * 查询所有
	 */
    List<BdmPoliceStationDto> findAll();

    /**
     * 查询所有(地区参数)
     */
	List<BdmPoliceStationDto> find4Select(String areaType);

	/**
	 * 按id查询
	 */
	BdmPoliceStationDto findById(Integer id);

	/**
	 * 按名称查找
	 * @param name
	 * @return
     */
	BdmPoliceStation findByName(String name);

    /**
     * 查询所有子项
     */
    List<BdmPoliceDto> findAllSubs();

	/**
	 * 按id查询子项
	 */
	List<BdmPoliceDto> findSubsById(Integer id);

	/**
	 * 保存
	 */
	BdmPoliceStationDto save(BdmPoliceStationDto dto);
	
	/**
	 * 删除
	 */
	void delete(String ids);

}
