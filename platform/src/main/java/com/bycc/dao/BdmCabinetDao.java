package com.bycc.dao;

import com.bycc.entity.BdmCabinet;
import com.bycc.entity.BdmHandlingArea;
import org.smartframework.platform.repository.jpa.BaseJpaRepository;

import java.util.List;

/**
 * Created by wanghaidong on 2017/4/20.
 */
public interface BdmCabinetDao extends BaseJpaRepository<BdmCabinet,Integer>{
    List<BdmCabinet> findBdmCabinetByHandlingArea(BdmHandlingArea handlingArea);
}
