package com.tenant.security;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.base.server.BaseSession;
import com.base.util.Log;
import com.platform.util.PlatformUtil;
import com.tenant.entity.Tenant;
import com.tenant.entity.TenantDetails;
import com.tenant.messages.TenantMessages;
import com.tenant.service.TenantService;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * @author Muhil Kennedy
 * Tenant filter to make sure only valid clients proceed further.
 *
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class TenantFilter extends OncePerRequestFilter{
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private TenantService tenantService;
	
	//move to config file
	private static List<String> Whitelisted_URI = Arrays.asList("/actuator/health","/actuator/metrics","/favicon.ico");
	
    @Override
    protected boolean shouldNotFilter (HttpServletRequest request)
    {
        return Whitelisted_URI.parallelStream().filter(uri -> request.getRequestURI() != null && request.getRequestURI().contains(
            uri)).findAny().isPresent();
    }

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String requestUri = request.getRequestURI();
		Log.tenant.info("Request URI - " + requestUri);
		//check for null tenant header
		String tenantUniqueName = request.getHeader(PlatformUtil.TENANT_HEADER);
		if(StringUtils.isBlank(tenantUniqueName)) {
			Log.tenant.error("Tenant Header is Empty");
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
					"Tenant Header is Empty");
			return;
		}
		Tenant tenant = tenantService.findByUniqueName(tenantUniqueName);
		if(!isValidTenant(tenant, response)) {
			return;
		}
		BaseSession.setTenant(tenant);
		Log.base.info("Tenant Session For {} is setup", tenant.getUniquename());
		TenantDetails td = tenant.getTenantDetail();
		//TODO: check valid tenant origins		
		// Set current session as new tenant
		if (tenant.getParent() == null) {
			String idParam = request.getParameter(PlatformUtil.TENANT_PARAM);
			if (!StringUtils.isAllBlank(idParam)) {
				Long id = Long.parseLong(idParam);
				BaseSession.setCurrentTenant(tenant);
				tenant = (Tenant) tenantService.findById(id);
				if (isValidTenant(tenant, response)) {
					BaseSession.setTenant(tenant);
					Log.base.info("Tenant Session Updated for {} to {} based on request",
							BaseSession.getCurrentTenant().getUniqueId(), tenant.getUniquename());
				} else {
					throw new RuntimeException("Invalid Request for Tenant");
				}
			}
		}
		Log.tenant.debug("Tenant filter validation successful");
		filterChain.doFilter(request, response);
	}
	
	@PostConstruct
	private void whiteListedEndpoints() {
		Log.tenant.warn("Whitelisted Uris : ");
		Whitelisted_URI.parallelStream().forEach(uri -> Log.tenant.warn(uri));
	}
	
	private boolean isValidTenant(Tenant tenant, HttpServletResponse response) throws IOException {
		if (tenant == null) {
			Log.tenant.error("Tenant Not Found");
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "Tenant Not Found");
			return false;
		}
		if (!tenant.isActive()) {
			Log.tenant.error("Tenant Not Active");
			response.sendError(HttpServletResponse.SC_FORBIDDEN, messageSource
					.getMessage(TenantMessages.INACTVE.getKey(), new String[] { tenant.getName() }, Locale.ENGLISH));
			return false;
		}
		return true;
	}

}
