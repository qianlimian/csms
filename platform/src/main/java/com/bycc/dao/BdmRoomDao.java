package com.bycc.dao;

import com.bycc.entity.BdmHandlingArea;
import com.bycc.entity.BdmRoom;
import org.smartframework.platform.repository.jpa.BaseJpaRepository;

import java.util.List;

/**
 * Created by wanghaidong on 2017/4/17.
 */
public interface BdmRoomDao extends BaseJpaRepository<BdmRoom, Integer> {

	List<BdmRoom> findByHandlingArea(BdmHandlingArea handlingArea);

	BdmRoom findByCode(String code);
}
