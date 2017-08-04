package com.bycc.service.bdRoomLayout;

import com.bycc.dao.BdmHandlingAreaDao;
import com.bycc.dao.BdmRoomDao;
import com.bycc.dto.bdmRoom.*;
import com.bycc.entity.BdmRoom;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.smartframework.utils.helper.JsonHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by yangqingming on 2017/5/8.
 */
@Service
public class BdmRoomLayoutServiceImpl implements BdmRoomLayoutService {

    @Autowired
    private BdmRoomDao roomDao;
    @Autowired
    private BdmHandlingAreaDao handlingAreaDao;

    @Override
    public BdmRoomDto findById(Integer id) {
        BdmRoom room =  roomDao.findOne(id);
        return BdmRoomDto.toDto(room);
    }

    @Override
    public List<BdmRoomDto> queryRooms(Integer areaId) {
        List<BdmRoom> rooms =  roomDao.findByHandlingArea(handlingAreaDao.findOne(areaId));
        List<BdmRoomDto> dtos = new ArrayList<>();
        for (BdmRoom room : rooms) {
            BdmRoomDto dto = BdmRoomDto.toDto(room);
//            dto.setRoomType(room.getRoomType().value());
            dtos.add(dto);
        }
        return dtos;
    }

    @Override
    public void saveLayout(List<Map<String, Object>> list) {
        for (Map<String, Object> map : list) {
            //{id=room1, h=, w=, x=, y=}
            String id = map.get("id").toString().substring(4);
            BdmRoom bdmRoom = roomDao.findOne(Integer.valueOf(id));

            if (map.get("x") == null || "".equals(map.get("x"))) {
                bdmRoom.setPosition(null);
            } else {
                bdmRoom.setPosition(JsonHelper.getJson(map));
            }
            roomDao.save(bdmRoom);
        }
    }
}
