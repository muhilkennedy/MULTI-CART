package com.base.server;

import java.util.Locale;

import org.apache.commons.lang3.LocaleUtils;

import com.base.entity.BaseEntity;
import com.platform.session.PlatformBaseSession;

/**
 * @author Muhil
 * Basesession should be set for each http request/thread.
 */
public class BaseSession {

	private static final ThreadLocal<BaseEntity> tenant = new ThreadLocal<>();
	private static final ThreadLocal<BaseEntity> user = new ThreadLocal<>();
	private static final ThreadLocal<Locale> locale = new ThreadLocal<>();
	
	public static void setTenant(BaseEntity tnt) {
		PlatformBaseSession.setTenant(tnt);
		tenant.set(tnt);
	}

	public static BaseEntity getTenant() {
		return tenant.get();
	}
	
	public static Long getTenantId() {
		return tenant.get().getRootId();
	}

	public static void setUser(BaseEntity usr) {
		PlatformBaseSession.setTenant(usr);
		user.set(usr);
	}

	public static BaseEntity getUser() {
		return user.get();
	}
	
	public static void setLocale(Locale loc) {
		locale.set(loc);
	}
	
	public static void setLocale(String localeCode) {
		locale.set(LocaleUtils.toLocale(localeCode));
	}
	
	public static Locale getLocale() {
		return locale.get();
	}
	
	public static void clear() {
		tenant.remove();
		user.remove();
		locale.remove();
		PlatformBaseSession.clear();
	}

}

