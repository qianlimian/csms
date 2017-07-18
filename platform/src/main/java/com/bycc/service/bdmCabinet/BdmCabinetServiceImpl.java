package com.bycc.service.bdmCabinet;

import com.bycc.dao.BdmCabinetDao;
import com.bycc.dao.BdmHandlingAreaDao;
import com.bycc.dto.bdmHandlingArea.BdmCabinetDto;
import com.bycc.entity.BdmCabinet;
import com.bycc.service.permission.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * User: yumingzhe
 * Time: 2017-5-8 20:28
 */
@Service
public class BdmCabinetServiceImpl implements BdmCabinetService {
	@Autowired
	private BdmCabinetDao cabinetDao;
	@Autowired
	private PermissionService serviceDao;
	@Autowired
	private BdmHandlingAreaDao handlingAreaDao;

	@Override
	public List<BdmCabinetDto> findAll() {
		
		List<BdmCabinet> cabinets = cabinetDao.findBdmCabinetByHandlingArea(handlingAreaDao.findByPoliceStation(serviceDao.findPoliceStation()).get(0));
		List<BdmCabinetDto> dtos = new ArrayList<>();
		for (BdmCabinet cabinet : cabinets) {
			dtos.add(BdmCabinetDto.toDto(cabinet));
		}
		return dtos;
	}
}
