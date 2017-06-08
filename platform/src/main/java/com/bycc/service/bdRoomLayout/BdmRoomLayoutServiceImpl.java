package com.bycc.service.bdRoomLayout;
import com.bycc.dao.BdmHandlingAreaDao;
import com.bycc.dao.BdmRoomDao;
import com.bycc.dto.bdmRoom.*;
import com.bycc.entity.BdmRoom;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
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
    public void saveLayout(Map<String, Object> params) {
        try{
            BdmRoom bdmRoom = roomDao.findOne((Integer) params.get("id"));
            String position = (String)params.get("position");
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> positionMap =  objectMapper.readValue(position, Map.class);
            System.out.println(positionMap.get("x"));
            if (positionMap.get("x") == null || "".equals(positionMap.get("x"))) {
                bdmRoom.setPosition(null);
            } else {
                bdmRoom.setPosition((String) params.get("position"));
            }
            roomDao.save(bdmRoom);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
