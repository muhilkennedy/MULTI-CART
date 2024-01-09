package com.platform.util;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

/**
 * @author Muhil
 */
public class BasicUtil {

	public static Map<String, String> parseQueryParams(String url) throws MalformedURLException {
		Map<String, String> paramMap = new HashMap<String, String>();
		String queryParam = new URL(url).getQuery();
		if (!StringUtils.isAllBlank(queryParam)) {
			String[] queryParams = queryParam.split(PlatformUtil.AMPERSAND_OPERATOR);
			Arrays.stream(queryParams).forEach(param -> {
				String[] keyValue = param.split(PlatformUtil.EQUAL_OPERATOR);
				if (!(keyValue.length < 2) && !StringUtils.isAllBlank(keyValue[0])
						&& !StringUtils.isAllBlank(keyValue[1])) {
					paramMap.put(keyValue[0], keyValue[1]);
				}
			});
		}
		return paramMap;
	}

	public static List<String> parseUrlPath(String url) throws MalformedURLException {
		String path = new URL(url).getPath();
		return Arrays.asList(path.split(PlatformUtil.FORWARD_SLASH_OPERATOR));
	}

}
