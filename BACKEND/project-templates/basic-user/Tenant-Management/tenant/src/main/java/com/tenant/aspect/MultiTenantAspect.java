package com.tenant.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.hibernate.Filter;
import org.hibernate.Session;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.base.server.BaseSession;
import com.base.util.Log;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

/**
 * @author Muhil
 */
@Aspect
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class MultiTenantAspect {

	@PersistenceContext
	private EntityManager entityManager;

	@Pointcut("@annotation(org.springframework.web.bind.annotation.GetMapping) || @annotation(org.springframework.web.bind.annotation.PostMapping) "
			+ "|| @annotation(org.springframework.web.bind.annotation.PutMapping) || @annotation(org.springframework.web.bind.annotation.DeleteMapping) "
			+ "|| @annotation(org.springframework.web.bind.annotation.PatchMapping)")
	protected void apiPointCut() {

	}

	/*@Around(value = "apiPointCut()")
	public Object enableTenantFilter(ProceedingJoinPoint joinPoint) throws Throwable {
		Session session = null;
		try {
			session = entityManager.unwrap(Session.class);
			// Enable the filter
			Filter filter = session.enableFilter("tenantFilter");
			// Set the parameter
			filter.setParameter("tenantid", BaseSession.getTenantId());
		} catch (Exception ex) {
			ex.printStackTrace();
			Log.tenant.error("Error enabling tenantFilter : " + ex.getMessage());
		}
		// Proceed with the joint point
		Object obj = joinPoint.proceed();
		if (session != null) {
			session.disableFilter("tenantFilter");
		}
		return obj;
	}*/
}
