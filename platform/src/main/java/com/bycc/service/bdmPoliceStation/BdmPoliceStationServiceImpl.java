package com.bycc.service.bdmPoliceStation;

import com.bycc.dao.BdmPoliceDao;
import com.bycc.dao.BdmPoliceStationDao;
import com.bycc.dto.bdmPoliceStation.BdmPoliceDto;
import com.bycc.dto.bdmPoliceStation.BdmPoliceStationDto;
import com.bycc.entity.BdmPolice;
import com.bycc.entity.BdmPoliceStation;
import org.smartframework.common.kendo.QueryBean;
import org.smartframework.manager.dao.user.UserDao;
import org.smartframework.manager.entity.User;
import org.smartframework.utils.helper.JsonHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BdmPoliceStationServiceImpl implements BdmPoliceStationService {

    @Autowired
    private BdmPoliceStationDao policeStationDao;

    @Autowired
    private BdmPoliceDao policeDao;

    @Autowired
    private UserDao userDao;

    /**
     * 按条件查询派出所列表
     */
    @Override
    public List<BdmPoliceStationDto> query(QueryBean qb) {
        List<BdmPoliceStation> policeStations = null;
        if (qb != null) {
            policeStations = policeStationDao.findByQueryBean(qb);
        } else {
            policeStations = policeStationDao.findAll();
        }
        List<BdmPoliceStationDto> dtos = new ArrayList<BdmPoliceStationDto>();
        for (BdmPoliceStation policeStation : policeStations) {
            dtos.add(BdmPoliceStationDto.toDto(policeStation));
        }
        return dtos;
    }

    /**
     * 按id查询派出所
     */
    @Override
    public BdmPoliceStationDto findById(Integer id) {
        BdmPoliceStation policeStation = policeStationDao.findOne(id);
        return BdmPoliceStationDto.toDto(policeStation);
    }

    /**
     * 查询所有派出所
     */
    @Override
    public List<BdmPoliceStationDto> findAll() {
        return query(null);
    }

    /**
     * 按id查询民警
     */
    @Override
    public List<BdmPoliceDto> findSubsById(Integer id) {
        List<BdmPoliceDto> dtos = new ArrayList<BdmPoliceDto>();
        List<BdmPolice> polices = new ArrayList<>();
        if (id == null) {
            polices = policeDao.findAll();
        } else {
            BdmPoliceStation policeStation = policeStationDao.findOne(id);
            polices = policeStation.getPolices();
        }

        for (BdmPolice police : polices) {
            dtos.add(BdmPoliceDto.toDto(police));
        }
        return dtos;
    }

    /**
     * 查询所有民警
     */
    @Override
    public List<BdmPoliceDto> findAllSubs() {
        return findSubsById(null);
    }

    /**
     * 保存派出所
     */
    @Override
    public BdmPoliceStationDto save(BdmPoliceStationDto dto) {
        // 保存主表
        BdmPoliceStation policeStation = null;
        if (dto.getId() == null) {
            policeStation = dto.toEntity(new BdmPoliceStation());
        } else {
            policeStation = policeStationDao.findOne(dto.getId());
            if (policeStation != null) {
                dto.toEntity(policeStation);
            }
        }

        // 保存子表
        // 删除
        for (Integer policeId : dto.getDeletes()) {
            policeDao.delete(policeId);
        }
        // 更新
        for (BdmPoliceDto policeDto : dto.getUpdates()) {
            BdmPolice police = policeDao.findOne(policeDto.getId());
            User user = userDao.findOne(policeDto.getUserId());

            policeDto.toEntity(police, user, policeStation);
            policeDao.save(police);
        }
        // 新增
        for (BdmPoliceDto policeDto : dto.getNews()) {
            BdmPolice police = new BdmPolice();
            User user = userDao.findOne(policeDto.getUserId());

            policeDto.toEntity(police, user, policeStation);
            policeDao.save(police);
        }

        policeStationDao.save(policeStation);
        return BdmPoliceStationDto.toDto(policeStation);
    }

    /**
     * 删除派出所
     */
    @Override
    public void delete(String ids) {
        for (String id : ids.split(",")) {
            BdmPoliceStation policeStation = policeStationDao.findOne(Integer.valueOf(id));
            policeStationDao.delete(policeStation);
        }
    }

}
