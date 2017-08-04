package com.bycc.utils;

import java.util.concurrent.TimeUnit;
import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;

/**
 * 
 * @description 保存房间内人员信息
 * @author liuxunhua
 * @date 2017年7月14日 上午8:10:15
 *
 */
public class MessageCacheUtil {
	private static CacheManager cacheManager = null;
	
	//获取Cache对象
	public static Cache<Integer, Object> getCache() {
		if (cacheManager == null) {
			cacheManager = CacheManagerBuilder.newCacheManagerBuilder()
				    .withCache("messageCache", CacheConfigurationBuilder.newCacheConfigurationBuilder(Integer.class, Object.class, ResourcePoolsBuilder.heap(20))
				    												   .withExpiry(Expirations.timeToLiveExpiration(Duration.of(30, TimeUnit.SECONDS)))) 
				    .build(true);
		}
		return cacheManager.getCache("messageCache", Integer.class, Object.class); 
	}
	
	//向Cache添加数据
	public static void put(Integer key, Object val) {
		getCache().put(key, val);
	}
	
	//从Cache获取数据
	public static Object get(Integer key) {
		return getCache().get(key);
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
