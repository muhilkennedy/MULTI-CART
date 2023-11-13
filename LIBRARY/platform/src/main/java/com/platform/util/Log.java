package com.platform.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Muhil
 *
 */
public class Log {
	
	public static enum loggers { base, core, tenant, user, service };

	public static Logger logger = LoggerFactory.getLogger(Log.class);
	
	public static Logger getLogger() {
		return logger;
	}
	
	public static final Logger platform = LoggerFactory.getLogger("com.platform");
	public static final Logger tenant = LoggerFactory.getLogger("com.platform.tenant");
	public static final Logger user = LoggerFactory.getLogger("com.platform.user");

}
