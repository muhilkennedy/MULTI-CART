package com.platform.cache;

/**
 * @author Muhil
 *
 */
public class UserCache {

	private static UserCache INSTANCE;
	private CacheStore cache;

	private UserCache() {
		this.cache = new CacheStore();
	}

	public synchronized static UserCache getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new UserCache();
		}
		return INSTANCE;
	}

	public CacheStore cache() {
		return cache;
	}
}
