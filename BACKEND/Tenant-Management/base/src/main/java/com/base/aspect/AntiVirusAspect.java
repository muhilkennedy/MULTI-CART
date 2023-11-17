package com.base.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.base.util.Log;
import com.platform.antivirus.ClamAVService;
import com.platform.antivirus.VirusScanResult;
import com.platform.antivirus.VirusScanStatus;
import com.platform.exception.VirusScanException;

/**
 * @author Muhil
 * perform virus scan on multipart file on all controller endpoints.
 */
@Aspect
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
@ConditionalOnProperty(value = "app.security.clamav.enabled", havingValue = "true")
public class AntiVirusAspect {

	@Pointcut("@annotation(org.springframework.web.bind.annotation.GetMapping) || @annotation(org.springframework.web.bind.annotation.PostMapping) "
			+ "|| @annotation(org.springframework.web.bind.annotation.PutMapping) || @annotation(org.springframework.web.bind.annotation.DeleteMapping) "
			+ "|| @annotation(org.springframework.web.bind.annotation.PatchMapping) || @annotation(org.springframework.web.bind.annotation.RequestMapping)")
	protected void endPointspointCut() {

	}

	//TODO: change to before
	@Around(value = "endPointspointCut()")
	public Object endPointspointCut(ProceedingJoinPoint joinPoint) throws Throwable {
		Object[] arguments = joinPoint.getArgs();
		for (Object argument : arguments) {
			if (argument != null && argument instanceof MultipartFile) {
				MultipartFile file = ((MultipartFile) argument);
				Log.base.info("Performing virus scan for file {}", file.getOriginalFilename());
				VirusScanResult result = ClamAVService.getInstance().scan(file.getInputStream());
				if (result.getStatus() != VirusScanStatus.PASSED) {
					throw new VirusScanException(String.format(
							"File %s may be corrupt or infected with malicious contents!", file.getOriginalFilename()));
				}
			}
		}
		return joinPoint.proceed();
	}

}
