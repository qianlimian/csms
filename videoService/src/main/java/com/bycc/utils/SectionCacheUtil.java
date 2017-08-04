package com.bycc.utils;

import java.util.concurrent.TimeUnit;
import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;

public class SectionCacheUtil {
	private static CacheManager cacheManager = null;
	
	//获取Cache对象
	public static Cache<String, Object> getCache() {
		if (cacheManager == null) {
			cacheManager = CacheManagerBuilder.newCacheManagerBuilder()
				    .withCache("sectionCache", CacheConfigurationBuilder.newCacheConfigurationBuilder(String.class, Object.class, ResourcePoolsBuilder.heap(20))
				    												   .withExpiry(Expirations.timeToLiveExpiration(Duration.of(6000, TimeUnit.SECONDS)))) 
				    .build(true);
		}
		return cacheManager.getCache("sectionCache", String.class, Object.class); 
	}
	
	//向Cache添加数据
	public static void put(String key, Object val) {
		getCache().put(key, val);
	}
	
	//从Cache获取数据
	public static Object get(String key) {
		return getCache().get(key);
	}
	
	//关闭CacheManager
	public static void close() {
		if (cacheManager != null) {
			cacheManager.close(); 
		}
	}

	//从Cache中删除数据
	public static void remove(String key) {
		getCache().remove(key);
	}
}
