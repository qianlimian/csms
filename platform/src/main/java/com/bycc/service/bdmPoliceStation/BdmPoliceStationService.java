package com.bycc.service.bdmPoliceStation;

import com.bycc.dto.bdmPoliceStation.BdmPoliceDto;
import com.bycc.dto.bdmPoliceStation.BdmPoliceStationDto;
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
	 * 按id查询
	 */
	BdmPoliceStationDto findById(Integer id);

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
