package com.user.dao;

import com.base.service.BaseDaoService;
import com.base.service.BaseReactiveDaoService;
import com.user.entity.User;

/**
 * @author muhil
 *
 */
public interface UserDaoService extends BaseDaoService, BaseReactiveDaoService{

	User findByEmailId(String emailId);
	
	User findUserForLogin(User user);
	
	User findByUniqueName(String uniqueName);
	
}
