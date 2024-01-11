package com.platform.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;

import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;

import com.platform.cache.UserCache;
import com.platform.entity.PlatformUser;
import com.platform.exception.UserNotFoundException;
import com.platform.messages.GenericResponse;
import com.platform.session.PlatformBaseSession;
import com.platform.util.HttpUtil;
import com.platform.util.JWTUtil;
import com.platform.util.Log;
import com.platform.util.PlatformPropertiesUtil;

/**
 * @author Muhil
 *
 */
public class UserService {

	private final static String findEmployeeUserUri = "/employee/fetch";

	private static String getUserUrl(String uri) {
		return PlatformPropertiesUtil.getUMFrontDoorUrl().concat(uri);
	}

	public static PlatformUser findById(Long userId, Long tenantId) throws UserNotFoundException {
		PlatformUser user = (PlatformUser) UserCache.getInstance().userCache().get(String.valueOf(userId));
		if (user == null) {
			HttpClient<GenericResponse> client = new HttpClient<GenericResponse>(new GenericResponse());
			Header authHeader = new BasicHeader(HttpHeaders.AUTHORIZATION, JWTUtil.generateSystemUserToken());
			Header tenantTeader = new BasicHeader(HttpUtil.HEADER_TENANT, PlatformBaseSession.getTenantUniqueName());
			try {
				NameValuePair uniqueIdParam = new BasicNameValuePair("userId", String.valueOf(userId));
				GenericResponse response = client.get(getUserUrl(findEmployeeUserUri), Arrays.asList(tenantTeader, authHeader),
						Arrays.asList(uniqueIdParam));
				user = (PlatformUser) HttpUtil.getDataResponse(PlatformUser.class, response);
				if (user == null) {
					throw new UserNotFoundException();
				}
				Log.tenant.info(String.format("Loaded user %s from service", user.getUniqueId()));
				UserCache.getInstance().userCache().add(user);
			} catch (IOException | URISyntaxException e) {
				Log.tenant.error("User fetch exception : " + e.getMessage());
				throw new UserNotFoundException();
			}
		}
		return user;
	}
	
	public static PlatformUser findById(Long userId) {
		PlatformUser user = (PlatformUser) UserCache.getInstance().userCache().get(String.valueOf(userId));
		if (user == null) {
			try {
				return findById(userId, PlatformBaseSession.getTenantId());
			} catch (UserNotFoundException e) {
				return null;
			}
		}
		return user;
	}

}
