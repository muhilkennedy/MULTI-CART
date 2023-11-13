package com.user.service;

import java.util.List;

import com.base.service.BaseService;
import com.user.entity.User;
import com.user.exception.UserException;

import reactor.core.publisher.Flux;

/**
 * @author Muhil
 *
 */
public interface UserService extends BaseService {

	User register(User user);

	User login(User user) throws UserException;

	User findByEmailId(String emailId);

	User findByUniqueName(String uniqueName);

	User toggleStatus(Long rootId);
	
	List<User> findAllUsers();
	
	Flux<User> findAllUsersReactive();

}
