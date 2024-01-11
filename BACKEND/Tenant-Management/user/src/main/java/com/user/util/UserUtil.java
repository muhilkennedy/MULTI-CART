package com.user.util;

import java.sql.SQLException;
import java.util.Arrays;

import com.base.server.BaseSession;
import com.base.util.DatabaseUtil;
import com.base.util.Log;
import com.platform.annotations.ClassMetaProperty;
import com.user.entity.Employee;
import com.user.entity.User;

/**
 * @author Muhil
 *
 */
public class UserUtil {
	
	public static synchronized String generateEmployeeUniqueName() throws SQLException {
		Long count = (Long) DatabaseUtil.executeDQL("select count(*) from Employee where tenantid = ? ",
				Arrays.asList(BaseSession.getTenantId()));
		ClassMetaProperty classMeta = Employee.class.getAnnotation(ClassMetaProperty.class);
		String uniqueName = String.format("%s%s%s", classMeta.code(), BaseSession.getTenantId(), ++count);
		Log.user.debug("Generated Unique Name for Employee User {}", uniqueName);
		return uniqueName;
	}
	
	public static synchronized String generateCustomerUniqueName() throws SQLException {
		Long count = (Long) DatabaseUtil.executeDQL("select count(*) from Customer where tenantid = ? ",
				Arrays.asList(BaseSession.getTenantId()));
		ClassMetaProperty classMeta = User.class.getAnnotation(ClassMetaProperty.class);//change
		String uniqueName = String.format("%s%s%s", classMeta.code(), BaseSession.getTenantId(), ++count);
		Log.user.debug("Generated Unique Name for Customer User {}", uniqueName);
		return uniqueName;
	}

}
