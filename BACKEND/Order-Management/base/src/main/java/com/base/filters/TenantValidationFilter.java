package com.base.filters;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.base.server.BaseSession;
import com.base.util.Log;
import com.platform.entity.PlatformTenant;
import com.platform.exception.TenantException;
import com.platform.service.TenantService;
import com.platform.util.PlatformUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.core.HttpHeaders;

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
	
	//move to config file
	private static List<String> Whitelisted_URI = Arrays.asList("/actuator/");
	
    @Override
    protected boolean shouldNotFilter (HttpServletRequest request)
    {
        return Whitelisted_URI.parallelStream().filter(uri -> request.getRequestURI() != null && request.getRequestURI().contains(
            uri)).peek(uri -> Log.base.debug("Filtered URI -> {}", uri)).findAny().isPresent();
    }

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String requestUri = request.getRequestURI();
		Log.base.info("Request URI - {} : remote address - {} : user agent : {}", requestUri, request.getRemoteAddr(),
				request.getHeader(HttpHeaders.USER_AGENT));
		// error out if no teant header is present
		String tenantUniqueName = request.getHeader(PlatformUtil.TENANT_HEADER);
		if(StringUtils.isBlank(tenantUniqueName)) {
			Log.base.error("Tenant Header is Empty");
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
					"Tenant Header is Empty");
			return;
		}
		PlatformTenant tenant = null;
		if (tenantUniqueName.equals(PlatformUtil.INTERNAL_SYSTEM)) {
			// TODO and check for trusted subnet to make sure communication between services.
			tenant = PlatformTenant.getSystemTenant();
		}
		else {
			try {
				tenant = TenantService.findByUniqueName(tenantUniqueName);
			} catch (TenantException e) {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
						"Tenant Exception");
			}
		}
		if(!isValidTenant(tenant, response)) {
			return;
		}
		BaseSession.setCurrentTenant(tenant);
		Log.base.debug("Tenant filter validation successful");
		filterChain.doFilter(request, response);
	}
	
	private boolean isValidTenant(PlatformTenant tenant, HttpServletResponse response) throws IOException {
		if (tenant == null) {
			Log.base.error("Tenant Not Found");
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "Tenant Not Found");
			return false;
		}
		if (!tenant.isActive()) {
			Log.base.error("Tenant Not Active");
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "Tenant Not Active");
			return false;
		}
		return true;
	}

}
