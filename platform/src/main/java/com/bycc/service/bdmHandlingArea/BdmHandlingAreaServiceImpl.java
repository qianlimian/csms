package com.bycc.service.bdmHandlingArea;

import com.bycc.dao.*;
import com.bycc.dto.bdmHandlingArea.*;
import com.bycc.entity.*;
import org.smartframework.common.kendo.QueryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wanghaidong on 2017/4/14.
 */
@Service
public class BdmHandlingAreaServiceImpl implements BdmHandlingAreaService {
    @Autowired
    private BdmPoliceStationDao policeStationDao;
    @Autowired
    private BdmHandlingAreaDao handlingAreaDao;
    @Autowired
    private BdmCabinetDao cabinDao;
    @Autowired
    private BdmStrapDao strapDao;
    @Override
    public List<BdmHandlingAreaDto> query(QueryBean qb) {
        List<BdmHandlingArea> list = handlingAreaDao.findByQueryBean(qb);
        List<BdmHandlingAreaDto> dtoList = new ArrayList<BdmHandlingAreaDto>();
        for (BdmHandlingArea handlingArea : list) {
            BdmHandlingAreaDto dto = BdmHandlingAreaDto.toDto(handlingArea);
            dtoList.add(dto);
        }
        return dtoList;
    }

    @Override
    public BdmHandlingAreaDto findById(Integer id) {
        BdmHandlingArea handlingArea = handlingAreaDao.findOne(id);
        return BdmHandlingAreaDto.toDto(handlingArea);
    }

    @Override
    public List<BdmHandlingAreaDto> findAll() {
        List<BdmHandlingArea> list = handlingAreaDao.findAll();
        List<BdmHandlingAreaDto> dtoList = new ArrayList<BdmHandlingAreaDto>();
        for (BdmHandlingArea handlingArea : list) {
            BdmHandlingAreaDto dto = BdmHandlingAreaDto.toDto(handlingArea);
            dtoList.add(dto);
        }
        return dtoList;
    }

    @Override
    public BdmHandlingAreaDto save(BdmHandlingAreaDto dto) {
        //reference
        BdmPoliceStation ps = policeStationDao.findOne(dto.getPoliceStationId());

        BdmHandlingArea handlingArea = handlingAreaDao.save(BdmHandlingAreaDto.toEntity(dto, ps));

        //保存子表
        saveCabinets(dto.getCabin(), handlingArea);
        saveStraps(dto.getStrap(), handlingArea);

        return BdmHandlingAreaDto.toDto(handlingArea);
    }

    //保存物品柜
    private void saveCabinets(CudCabinDto cabin, BdmHandlingArea handlingArea) {
        for (BdmCabinetDto cabinDto : cabin.getNews()) {
            BdmCabinet bdmCabinet = BdmCabinetDto.toEntity(cabinDto);
            bdmCabinet.setHandlingArea(handlingArea);
            cabinDao.save(bdmCabinet);
        }
        for (BdmCabinetDto cabinDto : cabin.getUpdates()) {
            BdmCabinet bdmCabinet = BdmCabinetDto.toEntity(cabinDto);
            bdmCabinet.setHandlingArea(handlingArea);
            cabinDao.save(bdmCabinet);
        }
        for (String id : cabin.getDeletes()) {
            cabinDao.delete(Integer.valueOf(id));
        }
    }

    //保存腕带
    private void saveStraps(CudStrapDto strap, BdmHandlingArea handlingArea) {
        for (BdmStrapDto strapDto : strap.getNews()) {
            BdmStrap bdmStrap = BdmStrapDto.toEntity(strapDto);
            bdmStrap.setHandlingArea(handlingArea);
            strapDao.save(bdmStrap);
        }
        for (BdmStrapDto strapDto : strap.getUpdates()) {
            BdmStrap bdmStrap = BdmStrapDto.toEntity(strapDto);
            bdmStrap.setHandlingArea(handlingArea);
            strapDao.save(bdmStrap);
        }
        for (String id : strap.getDeletes()) {
            strapDao.delete(Integer.valueOf(id));
        }
    }

    @Override
    public void delete(String ids) {
        for (String id : ids.split(",")) {
            BdmHandlingArea handlingArea = handlingAreaDao.findOne(Integer.valueOf(id));
            //已配置级联删除BdmCabinet, BdmStrap
            handlingAreaDao.delete(handlingArea);
        }

    }

    @Override
    public List<BdmStrapDto> findStrapsByAreaId(Integer id) {
        BdmHandlingArea handlingArea = handlingAreaDao.findOne(id);
        List<BdmStrap> list = handlingArea.getStraps();
        List<BdmStrapDto> dtoList = new ArrayList<BdmStrapDto>();
        for (BdmStrap strap : list) {
            BdmStrapDto dto = BdmStrapDto.toDto(strap);
            dtoList.add(dto);
        }
        return dtoList;
    }

    @Override
    public List<BdmCabinetDto> findCabinetByAreaId(Integer id) {
        BdmHandlingArea handlingArea = handlingAreaDao.findOne(id);
        List<BdmCabinet> list = handlingArea.getCabinets();
        List<BdmCabinetDto> dtoList = new ArrayList<BdmCabinetDto>();
        for (BdmCabinet cabinet : list) {
            BdmCabinetDto dto = BdmCabinetDto.toDto(cabinet);
            dtoList.add(dto);
        }
        return dtoList;
    }

}
