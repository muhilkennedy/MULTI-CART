package com.platform.cache;

import java.util.concurrent.TimeUnit;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.platform.entity.BaseObject;
import com.platform.util.Log;

/**
 * @author Muhil
 * Create a initializer in app module for diff objects as required.
 */
public class CacheStore implements CacheService {
	
	private Cache<String, BaseObject> cache;
	
	public CacheStore() {
		this.cache = CacheBuilder.newBuilder().maximumSize(MAX_CACHED_OBJECTS)
				.expireAfterWrite(MAX_CACHE_TTL_MIN, TimeUnit.MINUTES).build();
				//.expireAfterAccess(MAX_CACHE_TTL_MIN, TimeUnit.MINUTES).build();
	}

	public CacheStore(int ttl, TimeUnit unit) {
		this.cache = CacheBuilder.newBuilder().maximumSize(MAX_CACHED_OBJECTS).expireAfterWrite(ttl, unit)
				.expireAfterAccess(ttl, unit).build();
	}

	public CacheStore(boolean defaultpolicy) {
		this.cache = CacheBuilder.newBuilder().build();
	}

	@Override
	public BaseObject get(String key) {
		Log.platform.debug(String.format("Fetching key %s from cache", key));
		return cache.getIfPresent(key);
	}

	@Override
	public void add(String key, BaseObject value) {
		Log.platform.debug(String.format("Cached key %s ", key));
		cache.put(key, value);
	}

	@Override
	public void evict(String key) {
		Log.platform.debug(String.format("Evicted key %s from cache", key));
		cache.invalidate(key);
	}

	@Override
	public void evictAll() {
		Log.platform.debug("Clearing cache");
		Log.platform.debug("Cahce size : {}", cache.size());
		cache.invalidateAll();
	}

	@Override
	public void add(BaseObject value) {
		Log.platform.debug(String.format("Cached key %s ", value.getObjectId()));
		this.add(String.valueOf(value.getObjectId()), value);
	}

}
