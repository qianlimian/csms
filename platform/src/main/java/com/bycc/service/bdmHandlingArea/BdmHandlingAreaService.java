package com.bycc.service.bdmHandlingArea;

import com.bycc.dto.bdmHandlingArea.BdmCabinetDto;
import com.bycc.dto.bdmHandlingArea.BdmHandlingAreaDto;
import com.bycc.dto.bdmHandlingArea.BdmStrapDto;
import org.smartframework.common.kendo.QueryBean;

import java.util.List;

/**
 * Created by wanghaidong on 2017/4/14.
 */
public interface BdmHandlingAreaService {

    /**
     * 返回所有的派出所
     * @param qb
     * @return
     */
    List<BdmHandlingAreaDto> query(QueryBean qb);

    /**
     * 按id查询警局
     * @return
     */
    BdmHandlingAreaDto findById(Integer id);

    /**
     * 查找所有派出所
     * @return
     */
    List<BdmHandlingAreaDto> findAll();

    /**
     * 保存警局
     * @param dto
     * @return
     */
    BdmHandlingAreaDto save(BdmHandlingAreaDto dto) throws Exception;

    /**
     * 删除警局
     * @param ids
     */
    void delete(String ids);

    /**
     * 根据handlingAreaId查找Straps
     * @param id
     * @return
     */
    List<BdmStrapDto> findStrapsByAreaId(Integer id);

    /**
     * 根据handlingAreaId查找Cabinets
     * @param id
     * @return
     */
    List<BdmCabinetDto> findCabinetByAreaId(Integer id);
}
