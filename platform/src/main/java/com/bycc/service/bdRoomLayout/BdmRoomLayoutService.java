package com.bycc.service.bdRoomLayout;
import com.bycc.dto.bdmRoom.BdmRoomDto;
import java.util.List;
import java.util.Map;

/**
 * Created by yangqingming on 2017/5/8.
 */
public interface BdmRoomLayoutService {

    BdmRoomDto findById(Integer id);
    void saveLayout(Map<String, Object> params);
    List<BdmRoomDto> queryRooms(Integer areaId);
}
