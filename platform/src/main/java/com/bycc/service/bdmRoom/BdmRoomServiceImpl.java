package com.bycc.service.bdmRoom;

import com.bycc.dao.BdmCameraDao;
import com.bycc.dao.BdmHandlingAreaDao;
import com.bycc.dao.BdmRoomDao;
import com.bycc.dao.BdmStationDao;
import com.bycc.dto.bdmRoom.*;
import com.bycc.entity.BdmCamera;
import com.bycc.entity.BdmHandlingArea;
import com.bycc.entity.BdmRoom;
import com.bycc.entity.BdmStation;
import org.smartframework.common.kendo.QueryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wanghaidong on 2017/4/17.
 */
@Service
public class BdmRoomServiceImpl implements BdmRoomService {

    @Autowired
    private BdmRoomDao roomDao;
    @Autowired
    private BdmStationDao stationDao;
    @Autowired
    private BdmCameraDao cameraDao;
    @Autowired
    private BdmHandlingAreaDao handlingAreaDao;

    @Override
    public List<BdmRoomDto> query(RoomCondition condition, QueryBean qb) {
        String[] hql = new String[]{"handlingArea.id = :handlingAreaId"};

        Map<String, Object> param = new HashMap<String, Object>() {
            {
                put("handlingAreaId", condition.getHandlingAreaId());
            }
        };

        List<BdmRoom> list = roomDao.findByQueryBeanCondition(hql, param, qb);
        List<BdmRoomDto> dtoList = new ArrayList<BdmRoomDto>();
        for (BdmRoom room : list) {
            BdmRoomDto roomDto = BdmRoomDto.toDto(room);
            dtoList.add(roomDto);
        }
        return dtoList;
    }

    @Override
    public BdmRoomDto findById(Integer id) {
        BdmRoom room =  roomDao.findOne(id);
        return BdmRoomDto.toDto(room);
    }

    @Override
    public BdmRoomDto save(BdmRoomDto roomDto) {
        //保存主表
        BdmRoom entity = roomDto.toEntity();
        BdmHandlingArea handlingArea = handlingAreaDao.findOne(roomDto.getHandlingAreaId());
        entity.setHandlingArea(handlingArea);
        BdmRoom room = roomDao.save(entity);

        //保存子表
        saveStations(roomDto.getStation(), room);
        saveCameras(roomDto.getCamera(), room);

        return BdmRoomDto.toDto(room);
    }

    private void saveStations(CudStationDto station, BdmRoom room) {
        for (BdmStationDto bdmStationDto : station.getNews()) {
            BdmStation entity = BdmStationDto.toEntity(bdmStationDto);
            entity.setRoom(room);
            stationDao.save(entity);
        }
        for (BdmStationDto bdmStationDto : station.getUpdates()) {
            BdmStation entity = BdmStationDto.toEntity(bdmStationDto);
            entity.setRoom(room);
            stationDao.save(entity);
        }
        for (String id : station.getDeletes()) {
            stationDao.delete(Integer.valueOf(id));
        }

    }

    private void saveCameras(CudCameraDto camera, BdmRoom room) {
        for (BdmCameraDto bdmCameraDto : camera.getNews()) {
            BdmCamera entity = BdmCameraDto.toEntity(bdmCameraDto);
            entity.setRoom(room);
            cameraDao.save(entity);
        }
        for (BdmCameraDto bdmCameraDto : camera.getUpdates()) {
            BdmCamera entity = BdmCameraDto.toEntity(bdmCameraDto);
            entity.setRoom(room);
            cameraDao.save(entity);
        }
        for (String id : camera.getDeletes()) {
            cameraDao.delete(Integer.valueOf(id));
        }
    }

    @Override
    public void delete(String ids) {
        for (String id : ids.split(",")) {
            BdmRoom room = roomDao.findOne(Integer.valueOf(id));
            //已配置级联删除BdmStation, BdmCamera
            roomDao.delete(room);

        }
    }

    @Override
    public List<BdmStationDto> findStationByRoomId(Integer id) {
        BdmRoom room = roomDao.findOne(Integer.valueOf(id));
        List<BdmStation> stations = room.getStations();
        List<BdmStationDto> list = new ArrayList<BdmStationDto>();
        for (BdmStation station : stations) {
            list.add(BdmStationDto.toDto(station));
        }
        return list;
    }

    @Override
    public List<BdmCameraDto> findCameraByRoomId(Integer id) {
        BdmRoom room = roomDao.findOne(Integer.valueOf(id));
        List<BdmCamera> cameras = room.getCameras();
        List<BdmCameraDto> list = new ArrayList<BdmCameraDto>();
        for (BdmCamera camera : cameras) {
            list.add(BdmCameraDto.toDto(camera));
        }
        return list;
    }


}
