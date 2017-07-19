/**
 * 
 */
package com.bycc.service.locate;

import com.bycc.dto.locate.LocateDto;

/**
 * @description 定位开放接口
 * @author gaoningbo
 * @date 2017年6月29日
 * 
 */
public interface LocateService {

	/**	
	 * 根据手环编号和基站编号返回定位和截取信息并记录人员进入房间信息
	 * @param person 
	 */
	LocateDto getLocateInfo(Integer strapCode, Integer stationCode, Integer person);

	/**
	 * 
	 * @description 记录人员离开房间信息
	 * @author liuxunhua
	 * @date 2017年7月5日 上午9:24:11
	 *
	 */
	void leave(Integer traceId);
}
