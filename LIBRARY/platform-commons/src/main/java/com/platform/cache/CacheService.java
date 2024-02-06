package com.platform.cache;

import com.platform.entity.BaseObject;

/**
 * @author Muhil
 *
 */
public interface CacheService {
	
	final int MAX_CACHED_OBJECTS = 500;
	final int MAX_CACHE_TTL_MIN = 10;

	BaseObject get(String key);

	void add(String key, BaseObject value);
	
	void add(BaseObject value);
	
	void evict(String key);
	
	void evictAll();
	
}
