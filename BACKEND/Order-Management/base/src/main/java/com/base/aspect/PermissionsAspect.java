package com.base.aspect;

import java.lang.reflect.Method;
import java.util.stream.Stream;

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
import com.platform.annotations.UserPermission;
import com.platform.entity.PlatformUser;
import com.platform.exception.InvalidUserPermission;
import com.platform.user.Permissions;

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
			PlatformUser employee = (PlatformUser) BaseSession.getUser();
			if (!employee.isSystemUser()) {
				if (permissions.length > 0 && employee.getUserPermissions().isEmpty()) {
					Log.base.error("User doesnt seem to have required permission to access this endpoint");
					Log.base.debug("Required permission(s) to access this endpoint {}", permissions);
					throw new InvalidUserPermission("Invalid permission");
				}
				if (!Stream.of(permissions)
						.filter(prem -> employee.getUserPermissions().contains(prem))
						.findFirst().isPresent()) {
					Log.base.error("Authorization denied for user to acces this endpoint");
					Log.base.debug("Required permission(s) to access this endpoint {}", permissions);
					throw new InvalidUserPermission("Invalid Permission");
				}
			}
		}
		Log.base.debug(String.format("User Permissions are valid for method : %s required permissions %s",
				method.getName(), Permissions.getPermissionsAsString(permissions)));
		Object resultObj = joinPoint.proceed();
		return resultObj;

	}

}
