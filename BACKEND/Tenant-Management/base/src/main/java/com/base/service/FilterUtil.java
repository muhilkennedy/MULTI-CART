package com.base.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.reflections.Reflections;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;

import com.base.util.Log;
import com.platform.annotations.ValidateUserToken;

/**
 * @author Muhil
 *
 */
public class FilterUtil {

	/**
	 * @return controller base context pattern for which user validation filter has
	 *         to be invoked. (another way to handle is using aop on controller
	 *         endpoints for method level validations)
	 */
	public static String[] getValidateUserTokenUrlPatterns() {
		List<String> paths = new ArrayList<>();
		Set<Class<?>> clsSet = new Reflections("com.").getTypesAnnotatedWith(ValidateUserToken.class);
		clsSet.stream().forEach(cls -> {
			RequestMapping clsRequestMapping = cls.getAnnotation(RequestMapping.class);
			Assert.notNull(clsRequestMapping.value(), "Value required for RequestMapping in class : " + cls.getName());
			Arrays.stream(clsRequestMapping.value()).forEach(mapping -> {
				String path = String.format("/%s/*", mapping);
				Log.base.debug(path);
				paths.add(path);
			});
		});
		Log.base.debug("Identified User Auth URL patterns : " + paths.toString());
		return paths.stream().toArray(String[]::new);
	}

}
