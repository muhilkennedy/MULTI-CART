package com.user.service;

import com.user.entity.User;
import com.user.exception.EmployeeWidgetPreferences;

/**
 * @author muhil 
 */
public interface EmployeePreferenceService {

	User updateWidgetVisiblityPreference(EmployeeWidgetPreferences widget, boolean status);

}
