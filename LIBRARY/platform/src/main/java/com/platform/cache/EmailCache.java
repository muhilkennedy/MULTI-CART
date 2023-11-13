package com.platform.cache;

import java.util.Properties;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

/**
 * @author Muhil
 */
public class EmailCache {

	private static EmailCache INSTANCE;
	private Cache<Long, Properties> cache;

	public EmailCache() {
		this.cache = CacheBuilder.newBuilder().build();
	}

	public synchronized static EmailCache getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new EmailCache();
		}
		return INSTANCE;
	}

	public Properties get(Long key) {
		return cache.getIfPresent(key);
	}

	public void add(Long key, Properties value) {
		cache.put(key, value);
	}

	public void evict(Long key) {
		cache.invalidate(key);
	}

	public void evictAll() {
		cache.invalidateAll();
	}

}
