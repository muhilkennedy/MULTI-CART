package com.base.filters;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.base.server.BaseSession;
import com.base.util.PropertiesUtil;
import com.platform.entity.PlatformUser;
import com.platform.service.UserService;
import com.platform.util.JWTUtil;
import com.platform.util.PlatformUtil;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.core.HttpHeaders;

/**
 * @author Muhil
 *
 */
@Component
@Order(2)
public class UserTokenSecurityFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		String token = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
		if (PropertiesUtil.isProdDeployment() || StringUtils.isNotBlank(token)) {
			String jwtToken = JWTUtil.extractToken(token);
			if (StringUtils.isNotBlank(jwtToken)) {
				try {
					if (JWTUtil.validateToken(jwtToken)) {
						String userRootId = JWTUtil.getUserIdFromToken(jwtToken);
						if (StringUtils.isNotBlank(userRootId)) {
							if (Long.parseLong(userRootId) == PlatformUtil.SYSTEM_USER_ROOTID) {
								PlatformUser systemUser = PlatformUser.getSystemUser();
								BaseSession.setUser(systemUser);
							} else {
								PlatformUser user = UserService.findById(Long.parseLong(userRootId));
								String tokenUserUniqueName = JWTUtil.getUserUniqueNameFromToken(jwtToken);
								String tokenIpAddress = JWTUtil.getIpAddressFromToken(jwtToken);
								if (user == null || !user.getUniquename().equals(tokenUserUniqueName)) {
										//|| !httpRequest.getRemoteAddr().equals(tokenIpAddress)) {
									httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "UNAUTHORIZED");
									return;
								} else if (!user.isActive()) {
									httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "FORBIDDEN");
									return;
								}
								BaseSession.setUser(user);
								BaseSession.setLocale(user.getLocale());
							}
							chain.doFilter(request, response);
						} else {
							httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED,
									"Token Validation failed! Token Might be tampered!");
							return;
						}
					} else {
						httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "UNAUTHORIZED");
						return;
					}
				} catch (ExpiredJwtException ex) {
					httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "UNAUTHORIZED");
					return;
				}
			} else {
				httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "BAD REQUEST");
				return;
			}
		} else {
			// load some default user
			httpResponse.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED, "Impl pending - user filter");
			return;
		}
	}

}
