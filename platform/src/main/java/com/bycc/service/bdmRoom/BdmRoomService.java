package com.bycc.service.bdmRoom;

import com.bycc.dto.bdmRoom.BdmCameraDto;
import com.bycc.dto.bdmRoom.BdmRoomDto;
import com.bycc.dto.bdmRoom.BdmStationDto;
import com.bycc.dto.bdmRoom.RoomCondition;
import org.smartframework.common.kendo.QueryBean;

import java.util.List;

/**
 * Created by wanghaidong on 2017/4/17.
 */
public interface BdmRoomService {

    List<BdmRoomDto> query(RoomCondition condition, QueryBean qb);

    BdmRoomDto findById(Integer id);

    BdmRoomDto save(BdmRoomDto bdmRoomDto);

    void delete(String ids);

    BdmStationDto findStationByRoomId(Integer id);

    List<BdmCameraDto> findCameraByRoomId(Integer id);
}
