package com.user.aspect;

import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.collections4.CollectionUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.base.server.BaseSession;
import com.base.util.Log;
import com.i18n.util.LocaleUtil;
import com.platform.annotations.UserPermission;
import com.platform.user.Permissions;
import com.user.entity.Employee;
import com.user.entity.RolePermission;
import com.user.exception.InvalidUserPermission;
import com.user.messages.UserMessages;
/**
 * @author Muhil
 */
@Aspect
@Component
@Order(Ordered.LOWEST_PRECEDENCE)
public class PermissionsAspect {

	@Pointcut("@annotation(com.platform.annotations.UserPermission)")
	protected void userTokenPointCut() {

	}

	@Around(value = "userTokenPointCut ()")
	public Object validateUserPermissions(ProceedingJoinPoint joinPoint) throws Throwable {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();
		UserPermission annotation = method.getAnnotation(UserPermission.class);
		Permissions[] permissions = annotation.values();
		// validate for permissions only if present in annotation.
		if (permissions.length > 0) {
			Employee employee = (Employee) BaseSession.getUser();
			if (!employee.isSystemUser()) {
				List<RolePermission> rolePermissions = CollectionUtils.emptyIfNull(employee.getEmployeeeRoles())
						.stream().map(empRole -> empRole.getRole().getPermissions()).collect(Collectors.toList())
						.parallelStream().flatMap(List::stream).collect(Collectors.toList());
				List<String> empPermissions = rolePermissions.parallelStream()
						.map(rolePermission -> rolePermission.getPermission().getPermission())
						.collect(Collectors.toList());
				if (permissions.length > 0 && empPermissions.isEmpty()) {
					Log.user.error("User doesnt seem to have required permission to access this endpoint");
					Log.user.debug("Required permission(s) to access this endpoint {}", permissions);
					throw new InvalidUserPermission(LocaleUtil.getLocalisedString(UserMessages.PERMISSION_DENIED.name(),
							null, BaseSession.getLocale()));
				}
				if (!Stream.of(permissions).filter(prem -> empPermissions.contains(prem.getPermissionUniqueName()))
						.findFirst().isPresent()) {
					Log.user.error("Authorization denied for user to acces this endpoint");
					Log.user.debug("Required permission(s) to access this endpoint {}", permissions);
					throw new InvalidUserPermission(LocaleUtil.getLocalisedString(UserMessages.PERMISSION_DENIED.name(),
							null, BaseSession.getLocale()));
				}
			}
		}
		Log.user.debug(String.format("User Permissions are valid for method : %s required permissions %s", method.getName(),
				Permissions.getPermissionsAsString(permissions)));
		Object resultObj = joinPoint.proceed();
		return resultObj;

	}

}
