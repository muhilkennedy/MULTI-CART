package com.base.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author Muhil Kennedy
 * Autowire based on spring context
 */
@Component
public class AutowireHelper implements ApplicationContextAware 
{
	private static ApplicationContext applicationContext;
	private static AutowireHelper INSTANCE = new AutowireHelper();

	private AutowireHelper() {
	}

	@Override
	public void setApplicationContext(final ApplicationContext applicationContext) {
		AutowireHelper.applicationContext = applicationContext;
	}
	
	public static void autowire(Object classToAutowire, Object beanToAutowire) {
		if (beanToAutowire == null) {
			applicationContext.getAutowireCapableBeanFactory().autowireBean(classToAutowire);
		}
	}

	public static void autowire(Object classToAutowire, Object... beansToAutowireInClass) {
		for (Object bean : beansToAutowireInClass) {
			if (bean == null) {
				applicationContext.getAutowireCapableBeanFactory().autowireBean(classToAutowire);
			}
		}
	}

	public static AutowireHelper getInstance() {
		return INSTANCE;
	}
}
