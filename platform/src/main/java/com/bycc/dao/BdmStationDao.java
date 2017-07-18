package com.bycc.dao;

import com.bycc.entity.BdmRoom;
import com.bycc.entity.BdmStation;
import org.smartframework.platform.repository.jpa.BaseJpaRepository;

import java.util.List;

/**
 * Created by wanghaidong on 2017/4/17.
 */
public interface BdmStationDao extends BaseJpaRepository<BdmStation,Integer>{
    List<BdmStation> findByRoom(BdmRoom bdmRoom);
    BdmStation findByCode(Integer code);
}
