package com.platform.service;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.platform.cache.TenantCache;
import com.platform.entity.PlatformTenant;
import com.platform.exception.TenantException;
import com.platform.messages.GenericResponse;
import com.platform.util.HttpUtil;
import com.platform.util.JWTUtil;
import com.platform.util.Log;
import com.platform.util.PlatformPropertiesUtil;
import com.platform.util.PlatformUtil;

/**
 * @author Muhil
 *
 */
public class TenantService {

	private final static String findTenantUri = "/admin/tenant";
	private final static String findAllTenantUri = "/admin/tenant/alltenants";

	private static String getTenantUrl(String uri) {
		return PlatformPropertiesUtil.getTMFrontDoorUrl().concat(uri);
	}

	public static PlatformTenant findByUniqueName(String uniqueName) throws TenantException {
		PlatformTenant tenant = (PlatformTenant) TenantCache.getInstance().cache().get(uniqueName);
		if (tenant == null) {
			HttpClient<GenericResponse> client = new HttpClient<GenericResponse>(new GenericResponse());
			try {
				NameValuePair tenantParam = new BasicNameValuePair("tenantUniqueName", uniqueName);
				GenericResponse response = client.get(getTenantUrl(findTenantUri), HttpUtil.getDefaultSystemHeaders(), Arrays.asList(tenantParam));
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
	
	public static List<PlatformTenant> getAllTenants() throws TenantException {
		List<PlatformTenant> tenants = new ArrayList<PlatformTenant>();
		HttpClient<GenericResponse> client = new HttpClient<GenericResponse>(new GenericResponse());
		Header tenantHeader = new BasicHeader(HttpUtil.HEADER_TENANT, PlatformUtil.INTERNAL_SYSTEM);
		Header authHeader = new BasicHeader(HttpHeaders.AUTHORIZATION, JWTUtil.generateSystemUserToken());
		try {
			GenericResponse response = client.get(getTenantUrl(findAllTenantUri), Arrays.asList(tenantHeader, authHeader), null);
			Gson gson = new Gson();
			String json = gson.toJson(response.getDataList());
			Type listType = new TypeToken<List<PlatformTenant>>() {}.getType();
			tenants = new Gson().fromJson(json, listType);
			//tenants = HttpUtil.getDataListResponse(PlatformTenant.class, response);
			if (tenants == null || tenants.isEmpty()) {
				throw new TenantException("No Tenants found! Fisshy!");
			}
			Log.tenant.info(String.format("Loaded tenants %s from service", tenants));
		} catch (IOException | URISyntaxException e) {
			Log.tenant.error("Tenant fetch exception : " + e.getMessage());
			throw new TenantException(e.getMessage());
		}
		return tenants;
	}

}
