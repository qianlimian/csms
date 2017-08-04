package com.bycc.service;

import java.util.Map;

import com.bycc.dto.RFParams;
import com.bycc.utils.CollusionCacheUtil;
import com.bycc.utils.LocateCacheUtil;

public class LocateServiceImpl {
	
	/**
	 * 
	 * @description 获取所有的定位信息
	 * @author liuxunhua
	 * @date 2017年6月26日 上午11:07:27
	 *
	 */
	public Map<Integer, RFParams> get() {
		return LocateCacheUtil.getAll();
	}

	/**
	 * 
	 * @description 获取多人共处一室信息
	 * @author liuxunhua
	 * @date 2017年7月13日 上午9:39:13
	 *
	 */
	public Map<Integer, Object> alarm() {
		return CollusionCacheUtil.getAll();
	}
}
