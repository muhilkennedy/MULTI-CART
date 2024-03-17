package com.base.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.base.entity.NotificationToken;

/**
 * @author Muhil
 */
@Repository
public interface NotificationTokenRepository extends JpaRepository<NotificationToken, Long> {

	String findAllUserTokensQuery = "select nt from NotificationToken nt where nt.userid = :userId ";

	@Query(value = findAllUserTokensQuery)
	List<NotificationToken> findAllUserTokens(@Param("userId") Long userId);

	String findNotificationForTokenQuery = "select nt from NotificationToken nt where nt.token=:token";

	@Query(value = findNotificationForTokenQuery)
	NotificationToken findNotificationForToken(@Param("token") String token);

	String deleteQuery = "delete from NotificationToken where userid = :userId";

	@Query(value = deleteQuery)
	@Modifying
	void deleteTokensForUser(@Param("userId") Long userId);

}
