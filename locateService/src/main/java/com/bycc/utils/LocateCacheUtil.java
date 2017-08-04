package com.bycc.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.Cache.Entry;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;

import com.bycc.dto.RFParams;

/**
 * 
 * @description 保存定位设备参数
 * @author liuxunhua
 * @date 2017年7月14日 上午8:10:38
 *
 */
public class LocateCacheUtil {
	private static CacheManager cacheManager = null;
	
	//获取Cache对象
	public static Cache<Integer, Object> getCache() {
		if (cacheManager == null) {
			cacheManager = CacheManagerBuilder.newCacheManagerBuilder()
				    .withCache("locateCache", CacheConfigurationBuilder.newCacheConfigurationBuilder(Integer.class, Object.class, ResourcePoolsBuilder.heap(20))
				    												   .withExpiry(Expirations.timeToLiveExpiration(Duration.of(30, TimeUnit.SECONDS)))) 
				    .build(true);
		}
		return cacheManager.getCache("locateCache", Integer.class, Object.class); 
	}
	
	//向Cache添加数据
	public static void put(Integer key, Object val) {
		getCache().put(key, val);
	}
	
	//从Cache获取数据
	public static Object get(Integer key) {
		return getCache().get(key);
	}
	
	//获取Cache中所有数据
	public static Map<Integer, RFParams> getAll() {
		Map<Integer, RFParams> result = new HashMap<Integer, RFParams>();
		Iterator<Entry<Integer, Object>> it = getCache().iterator();
		while (it.hasNext()) {
			Entry<Integer, Object> entry = it.next();
			result.put(entry.getKey(), (RFParams) entry.getValue());
		}
		return result;
	}
	
	//关闭CacheManager
	public static void close() {
		if (cacheManager != null) {
			cacheManager.close(); 
		}
	}

	//从Cache中删除数据
	public static void remove(Integer key) {
		getCache().remove(key);
	}
}
