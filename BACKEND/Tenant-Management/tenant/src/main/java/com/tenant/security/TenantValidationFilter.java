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
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.base.server.BaseSession;
import com.base.util.Log;
import com.base.util.PropertiesUtil;
import com.platform.entity.PlatformTenant;
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
public class TenantValidationFilter extends OncePerRequestFilter{
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private TenantService tenantService;
	
	//move to config file
	private static List<String> Whitelisted_URI = Arrays.asList("/actuator/");
	
    @Override
    protected boolean shouldNotFilter (HttpServletRequest request)
    {
        return Whitelisted_URI.parallelStream().filter(uri -> request.getRequestURI() != null && request.getRequestURI().contains(
            uri)).peek(uri -> Log.tenant.debug("Filtered URI -> {}", uri)).findAny().isPresent();
    }

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String requestUri = request.getRequestURI();
		Log.tenant.info("Request URI - {} : remote address - {} : user agent : {}", requestUri, request.getRemoteAddr(),
				request.getHeader(HttpHeaders.USER_AGENT));
		// error out if no teant header is present
		String tenantUniqueName = request.getHeader(PlatformUtil.TENANT_HEADER);
		if(StringUtils.isBlank(tenantUniqueName)) {
			Log.tenant.error("Tenant Header is Empty");
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
					"Tenant Header is Empty");
			return;
		}
		Tenant tenant = null;
		if (tenantUniqueName.equals(PlatformUtil.INTERNAL_SYSTEM)) {
			// TODO and check for trusted subnet to make sure communication between services.
			PlatformTenant pTenant = PlatformTenant.getSystemTenant();
			tenant = new Tenant();
			tenant.setName(pTenant.getName());
			tenant.setActive(pTenant.isActive());
			tenant.setRootid(pTenant.getRootid());
			tenant.setUniquename(pTenant.getUniquename());
		}
		else {
			tenant = tenantService.findByUniqueName(tenantUniqueName);
		}
		if(!isValidTenant(tenant, response)) {
			return;
		}
		/*
		 * setting current tenant here indicates, he is the owner of this session. (but
		 * based on tenant id on parameter, tenant object will be updated so object
		 * persistance happens for tenant passed as parameter. In short tenantId passed
		 * as parameter will take precedence.(acts as on behalf of)
		 */
		BaseSession.setCurrentTenant(tenant);
		Log.base.debug("Tenant Session For {} is setup for request {}", tenant.getUniquename(), requestUri);
		TenantDetails td = tenant.getTenantDetail();
		if(PropertiesUtil.isProdDeployment() && td.getDetails().isInitialSetUpDone()) {
			String origin = request.getHeader(HttpHeaders.ORIGIN);
			if (!(origin.equals(td.getDetails().getAdminUrl()) || origin.equals(td.getDetails().getClientUrl()))) {
				Log.tenant.error("Invalid tenant origin");
				response.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE, "Invalid tenant origin");
				return;
			}
		}
		// Set current session as new tenant (tenantId passed in parameter takes precedence here)
		String idParam = request.getParameter(PlatformUtil.TENANT_PARAM);
		if (!StringUtils.isAllBlank(idParam)) {
			Long id = Long.parseLong(idParam);
			tenant = (Tenant) tenantService.findById(id);
			if (tenant != null) {
				BaseSession.setTenant(tenant);
				Log.base.info("Tenant Session Updated for {} to {} based on request",
						BaseSession.getCurrentTenant().getUniqueId(), tenant.getUniquename());
			} else {
				throw new RuntimeException("Invalid Request for Tenant");
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
