package com.user.security;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.base.server.BaseSession;
import com.base.util.PropertiesUtil;
import com.i18n.util.LocaleUtil;
import com.platform.entity.PlatformUser;
import com.platform.util.JWTUtil;
import com.platform.util.PlatformUtil;
import com.user.entity.Employee;
import com.user.entity.User;
import com.user.messages.UserMessages;
import com.user.service.UserService;

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
	
	@Autowired
	@Qualifier("EmployeeService")
	private UserService empService;

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
								Employee user = new Employee();
								user.setUniquename(systemUser.getUniquename());
								user.setRootid(systemUser.getRootid());
								BaseSession.setUser(user);
							}
							else {
								User user = (User) empService.findById(Long.valueOf(userRootId));
								String tokenUserUniqueName = JWTUtil.getUserUniqueNameFromToken(jwtToken);
								String tokenIpAddress = JWTUtil.getIpAddressFromToken(jwtToken);
								if (user == null || !user.getUniquename().equals(tokenUserUniqueName)
										|| !httpRequest.getRemoteAddr().equals(tokenIpAddress)) {
									httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, LocaleUtil
											.getLocalisedString(UserMessages.INVALID_ACCESS.getKey(), null, BaseSession.getLocale()));
									return;
								} else if (!user.isActive()) {
									httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, LocaleUtil
											.getLocalisedString(UserMessages.INACTIVE.getKey(), null, user.getLocale()));
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
						httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, LocaleUtil
								.getLocalisedString(UserMessages.TOKEN_VALIDATION_FAILED.getKey(), null, BaseSession.getLocale()));
						return;
					}
				}
				catch(ExpiredJwtException ex) {
					httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, LocaleUtil
							.getLocalisedString(UserMessages.TOKEN_EXPIRED.getKey(), null, BaseSession.getLocale()));
					return;
				}
			} else {
				httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, LocaleUtil
						.getLocalisedString(UserMessages.TOKEN_MISSIG.getKey(), null, BaseSession.getLocale()));
				return;
			}
		} else {
			//load some default user
			httpResponse.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED, "Impl pending - user filter");
			return;
		}
	}
	
}
