package com.platform.cache;

import java.util.concurrent.TimeUnit;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.platform.util.Log;

/**
 * @author Muhil
 *
 */
public class UserCache {

	private static UserCache INSTANCE;
	private CacheStore cache;
	private Cache<String, String> otpCache;
	private final int MAX_CACHE_TTL_OTP = 5;// expire otp after 5mins

	private UserCache() {
		this.cache = new CacheStore();
		this.otpCache = CacheBuilder.newBuilder().expireAfterWrite(MAX_CACHE_TTL_OTP, TimeUnit.MINUTES).build();
	}

	public synchronized static UserCache getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new UserCache();
		}
		return INSTANCE;
	}

	public CacheStore userCache() {
		return cache;
	}
	
	public String getOtp(String key) {
		Log.tenant.debug(String.format("Fetching key %s from otp cache", key));
		return otpCache.getIfPresent(key);
	}

	public void addOtp(String key, String value) {
		Log.tenant.debug(String.format("Cached otp key %s ", key));
		otpCache.put(key, value);
	}
	
	public void removeOtp(String key) {
		Log.tenant.debug(String.format("Removed cached otp key %s ", key));
		otpCache.invalidate(key);
	}

}
