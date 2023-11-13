package com.base.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Muhil
 *
 */
public class Log {
	
	public static enum loggers { base, itinerary, user, i18n, service };

	public static Logger logger = LoggerFactory.getLogger(Log.class);
	
	public static Logger getLogger() {
		return logger;
	}
	
	public static final Logger base = LoggerFactory.getLogger("com.base");
	public static final Logger service = LoggerFactory.getLogger("com.service");
	public static final Logger tenant = LoggerFactory.getLogger("com.tenant");
	public static final Logger user = LoggerFactory.getLogger("com.user");
	public static final Logger i18n = LoggerFactory.getLogger("com.i18n");

}