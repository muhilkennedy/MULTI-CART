package com.user.util;

import java.sql.SQLException;
import java.util.Arrays;

import com.base.server.BaseSession;
import com.base.service.DatabaseUtil;
import com.base.util.Log;

/**
 * @author Muhil
 *
 */
public class UserUtil {
	
	public static final String EMPLOYEE_PREFIX = "E";
	
	public static final String CUSTOMER_PREFIX = "C";
	
	public static synchronized String generateEmployeeUniqueName() throws SQLException {
		Long count = (Long) DatabaseUtil.executeDQL("select count(*) from Employee where tenantid = ? ",
				Arrays.asList(BaseSession.getTenantId()));
		String uniqueName = String.format("%s%s%s", EMPLOYEE_PREFIX, BaseSession.getTenantId(), ++count);
		Log.user.debug("Generated Unique Name for Employee User {}", uniqueName);
		return uniqueName;
	}
	
	public static synchronized String generateCustomerUniqueName() throws SQLException {
		Long count = (Long) DatabaseUtil.executeDQL("select count(*) from Customer where tenantid = ? ",
				Arrays.asList(BaseSession.getTenantId()));
		String uniqueName = String.format("%s%s%s", CUSTOMER_PREFIX, BaseSession.getTenantId(), ++count);
		Log.user.debug("Generated Unique Name for Customer User {}", uniqueName);
		return uniqueName;
	}

}
