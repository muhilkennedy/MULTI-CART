package com.platform.cache;

/**
 * @author Muhil
 * Singleton class for tenant cache.
 *
 */
public class TenantCache {

	private static TenantCache INSTANCE;
	private CacheStore cache;

	private TenantCache() {
		this.cache = new CacheStore();
	}

	public synchronized static TenantCache getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new TenantCache();
		}
		return INSTANCE;
	}

	public CacheStore cache() {
		return cache;
	}

}
