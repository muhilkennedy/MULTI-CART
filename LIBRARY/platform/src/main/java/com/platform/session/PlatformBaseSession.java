package com.platform.session;

import com.platform.configuration.PlatformConfiguration;
import com.platform.entity.BaseObject;
import com.platform.entity.PlatformTenant;

/**
 * @author Muhil
 * platform base session
 *
 */
public class PlatformBaseSession {

	private static final ThreadLocal<BaseObject> tenant = new ThreadLocal<BaseObject>();
	private static final ThreadLocal<BaseObject> user = new ThreadLocal<BaseObject>();
	
	private static PlatformTenant dummyTenant = null;

	public static BaseObject getTenant() {
		if (PlatformConfiguration.isMultitenantApp()) {
			return tenant.get();
		} else {
			//Todo
			return dummyTenant;
		}
	}

	public static void setTenant(BaseObject tenantInfo) {
		tenant.set(tenantInfo);
	}
	
	public static Long getTenantId() {
		return getTenant().getObjectId();
	}

	public static String getTenantUniqueName() {
		return getTenant() != null ? getTenant().getUniqueId() : null;
	}

	public static void clear() {
		tenant.remove();
		user.remove();
	}
	
	public static void setUser(BaseObject usr) {
		user.set(usr);
	}

	public static BaseObject getUser() {
		return user.get();
	}

}
