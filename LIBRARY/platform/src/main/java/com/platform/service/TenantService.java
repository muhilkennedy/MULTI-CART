package com.platform.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;

import com.platform.cache.TenantCache;
import com.platform.entity.PlatformTenant;
import com.platform.exception.TenantException;
import com.platform.messages.GenericResponse;
import com.platform.util.HttpUtil;
import com.platform.util.Log;
import com.platform.util.PlatformPropertiesUtil;

/**
 * @author Muhil
 *
 */
public class TenantService {

	private final static String findTenantUri = "/tenant/ping";

	private static String getTenantUrl(String uri) {
		return PlatformPropertiesUtil.getTMFrontDoorUrl().concat(uri);
	}

	public static PlatformTenant findByUniqueName(String uniqueName) throws TenantException {
		PlatformTenant tenant = (PlatformTenant) TenantCache.getInstance().cache().get(uniqueName);
		if (tenant == null) {
			HttpClient<GenericResponse> client = new HttpClient<GenericResponse>(new GenericResponse());
			Header header = new BasicHeader(HttpUtil.HEADER_TENANT, uniqueName);
			try {
				GenericResponse response = client.get(getTenantUrl(findTenantUri), Arrays.asList(header), null);
				tenant = (PlatformTenant) HttpUtil.getDataResponse(PlatformTenant.class, response);
				if (tenant == null) {
					throw new TenantException("Tenant not found");
				}
				Log.tenant.info(String.format("Loaded tenant %s from service", tenant.getUniqueId()));
				TenantCache.getInstance().cache().add(tenant);
			} catch (IOException | URISyntaxException e) {
				Log.tenant.error("Tenant fetch exception : " + e.getMessage());
				throw new TenantException(e.getMessage());
			}
		}
		return tenant;
	}

}
