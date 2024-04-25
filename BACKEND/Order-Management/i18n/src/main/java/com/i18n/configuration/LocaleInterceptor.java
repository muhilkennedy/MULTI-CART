package com.i18n.configuration;

import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.WebRequestInterceptor;

import com.base.server.BaseSession;
import com.base.util.Log;

import jakarta.ws.rs.core.HttpHeaders;


/**
 * @author Muhil
 *
 */
@Component
public class LocaleInterceptor implements WebRequestInterceptor {

	@Override
	public void preHandle(WebRequest request) throws Exception {
		String lang = request.getHeader(HttpHeaders.ACCEPT_LANGUAGE);
		if (StringUtils.isNotBlank(lang) && BaseSession.getLocale() == null) {
			try {
				BaseSession.setLocale(lang);
			}
			catch(RuntimeException ex) { // Not a good idea, deal later to resolve this
				BaseSession.setLocale(Locale.ENGLISH);
			}
		}
		else {
			Log.i18n.debug("No Locale information found in request");
		}
	}

	@Override
	public void postHandle(WebRequest request, ModelMap model) throws Exception {
		//No-OP
	}

	@Override
	public void afterCompletion(WebRequest request, Exception ex) throws Exception {
		Log.i18n.debug("Base Session cleaned : {}", request.toString());
		BaseSession.clear();
	}

}
