package com.base.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Muhil
 *
 */
public class CacheUtil {
	
	public static final String DEFAULT_CACHE_NAME = "default";
	public static final String TENANT_CACHE_NAME = "tenant";
	public static final String CUSTOMER_CACHE_NAME = "customer";
	public static final String EMPLOYEE_CACHE_NAME = "employee";
	public static final String PRODUCT_CACHE_NAME = "product";
	public static final String ORDER_CACHE_NAME = "order";
	public static final String DASHBOARD_CACHE_NAME = "dashboard";
	
	public static List<String> getAllCacheNames() throws IllegalArgumentException, IllegalAccessException{
		List<String> cacheNames = new ArrayList<String>();
		Field[] fields = CacheUtil.class.getFields();
		for (Field field : fields) {
			Object value = field.get(null);
			cacheNames.add(value.toString());
		}
		return cacheNames;
	}
	
}
