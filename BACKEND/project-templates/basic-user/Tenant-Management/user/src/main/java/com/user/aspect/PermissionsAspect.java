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
import org.springframework.stereotype.Component;

import com.base.server.BaseSession;
import com.base.util.Log;
import com.platform.annotations.UserPermission;
import com.platform.user.Permissions;
import com.user.entity.Employee;
import com.user.entity.RolePermission;
import com.user.exception.InvalidUserPermission;
/**
 * @author Muhil
 */
@Aspect
@Component
public class PermissionsAspect {

	@Pointcut("@within(com.platform.annotations.UserPermission) || @annotation(com.platform.annotations.UserPermission)")
	protected void userTokenPointCut() {

	}

	@Around(value = "userTokenPointCut ()")
	public Object enableTenantFilter(ProceedingJoinPoint joinPoint) throws Throwable {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();
		UserPermission annotation = method.getAnnotation(UserPermission.class);
		Permissions[] permissions = annotation.values();
		// validate for permissions only if present in annotation.
		if (permissions.length > 0) {
			Employee employee = (Employee) BaseSession.getUser();
			List<RolePermission> rolePermissions = CollectionUtils.emptyIfNull(employee.getEmployeeeRoles()).stream()
					.map(empRole -> empRole.getRole().getPermissions()).collect(Collectors.toList()).parallelStream()
					.flatMap(List::stream).collect(Collectors.toList());
			List<String> empPermissions = rolePermissions.parallelStream()
					.map(rolePermission -> rolePermission.getPermission().getPermission()).collect(Collectors.toList());
			if (permissions.length > 0 && empPermissions.isEmpty()) {
				Log.user.error("User doesnt seem to have required permission to access this endpoint");
				Log.user.debug("Required permission(s) to access this endpoint {}", permissions);
				throw new InvalidUserPermission(
						"User doesnt seem to have required permission to access this endpoint");
			}
			if (!Stream.of(permissions).filter(prem -> empPermissions.contains(prem.getPermissionUniqueName()))
					.findFirst().isPresent()) {
				Log.user.error("Authorization denied for user to acces this endpoint");
				Log.user.debug("Required permission(s) to access this endpoint {}", permissions);
				throw new InvalidUserPermission("Authorization denied for user to acces this endpoint");
			}
			throw new InvalidUserPermission("Authorization denied for user to acces this endpoint");
		}
		Log.user.debug(String.format("User Permissions are valid for method : %s required permissions %s", method.getName(),
				Permissions.getPermissionsAsString(permissions)));
		// TODO : we can do super user permission check to allow all endpoint
		Object resultObj = joinPoint.proceed();
		return resultObj;

	}

}
