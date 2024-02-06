package com.user.service;

import java.io.File;
import java.io.IOException;
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
	
	User save(User user);
	
	User saveAndFlush(User user);

	User register(User user);

	User login(User user) throws UserException;

	User findByEmailId(String emailId);
	
	User findBySecondaryEmailId(String emailId);

	User findByUniqueName(String uniqueName);

	User toggleStatus(Long rootId);
	
	List<User> findAllUsers();
	
	Flux<User> findAllUsersReactive();
	
	Flux<Long> findAllUserIdsReactive();

	void initiatePasswordReset(User user);

	void resetPassword(User user, String password, String otp) throws UserException;
	
	void activateAccount(User user, String password, String otp) throws UserException;;
	
	User updateProfilePicture(File file) throws IOException;
	
	void sendBirthdayWishesMail();
	
	void updateLocale(String langCode);
	
	default User updateSecondaryEmail(String email) {
		return null;
	}

}
