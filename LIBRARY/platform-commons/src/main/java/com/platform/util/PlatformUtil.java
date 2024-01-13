package com.platform.util;

import java.text.SimpleDateFormat;

/**
 * @author Muhil
 *
 */
public class PlatformUtil {

	public static final String EMPTY_STRING = "";
	public static final String EMPTY_SPACE = " ";
	
	public static final String INTERNAL_SYSTEM = "SYSTEM";
	
	public static final String DEFAULT_USER_ID = "MKEN_SYSTEM";
	public static final Long SYSTEM_USER_ROOTID = 0L;
	public static final Long SYSTEM_REALM_ROOTID = 0L;
	
	public static final String ADMIN_CUSTOMER_SUPPORT_DESIGNATION = "CustomerSupportAdmin";
	
	public static final String TENANT_HEADER = "X-Tenant";
	public static final String TENANTID_HEADER = "X-TenantId";
	public static final String TOKEN_HEADER = "X-Token";
	
	public static final String TENANT_PARAM = "tenantId";
	
	public static final String DOT_OPERATOR = ".";
	public static final String COLON_SEPERATOR = ":";
	public static final String MINUS_SEPERATOR = "-";
	public static final String AMPERSAND_OPERATOR = "&";
	public static final String FORWARD_SLASH_OPERATOR = "/";
	public static final String EQUAL_OPERATOR = "=";
	
	public static final String SLASH_ENCODE = "%2F";
	public static final String SPACE_ENCODE = "%20";
	
	public static final String TEMPLATE_EXTENTION = ".ftl";
	public static final String TEMPLATES_FOLDER = "EmailTemplates";
	
	public static final String TOPIC_ADMIN_USER = "ADMINUSER";
	public static final String TOPIC_CLIENT_USER = "CLIENTUSER";
	
	public static final SimpleDateFormat SIMPLE_DATE_ONLY_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
	public static final SimpleDateFormat SIMPLE_UI_DATE_ONLY_FORMAT = new SimpleDateFormat("dd/MM/yyyy");

}
