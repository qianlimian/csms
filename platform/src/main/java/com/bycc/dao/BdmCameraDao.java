package com.bycc.dao;

import com.bycc.entity.BdmCamera;
import com.bycc.entity.BdmRoom;
import org.smartframework.platform.repository.jpa.BaseJpaRepository;

import java.util.List;

/**
 * Created by wanghaidong on 2017/4/18.
 */
public interface BdmCameraDao extends BaseJpaRepository<BdmCamera,Integer>{
    List<BdmCamera> findByRoom(BdmRoom bdmRoom);
}
