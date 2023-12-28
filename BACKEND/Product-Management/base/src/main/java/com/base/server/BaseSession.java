package com.base.server;

import java.util.Locale;

import org.apache.commons.lang3.LocaleUtils;

import com.platform.entity.BaseObject;
import com.platform.entity.UserBaseObject;
import com.platform.session.PlatformBaseSession;
import com.platform.util.PlatformUtil;

/**
 * @author Muhil
 * Basesession should be set for each http request/thread.
 */
public class BaseSession {

	private static final ThreadLocal<BaseObject> tenant = new ThreadLocal<>();//identifies active tenant
	private static final ThreadLocal<BaseObject> currentTenant = new ThreadLocal<>();//identifies tenant session owner
	private static final ThreadLocal<BaseObject> user = new ThreadLocal<>();
	private static final ThreadLocal<Locale> locale = new ThreadLocal<>();
	
	public static void setTenant(BaseObject tnt) {
		PlatformBaseSession.setTenant(tnt);
		tenant.set(tnt);
	}

	public static BaseObject getTenant() {
		return tenant.get();
	}
	
	public static void setCurrentTenant(BaseObject tnt) {
		setTenant(tnt);
		currentTenant.set(tnt);
	}

	public static BaseObject getCurrentTenant() {
		return currentTenant.get();
	}
	
	public static Long getTenantId() {
		return tenant.get().getObjectId();
	}
	
	public static String getTenantUniqueName() {
		return tenant.get() != null ? tenant.get().getUniqueId() : PlatformUtil.EMPTY_STRING;
	}

	public static void setUser(UserBaseObject usr) {
		PlatformBaseSession.setUser((UserBaseObject)usr);
		user.set(usr);
	}

	public static BaseObject getUser() {
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
		PlatformBaseSession.clear();
		tenant.remove();
		user.remove();
		locale.remove();
		currentTenant.remove();
	}

}

