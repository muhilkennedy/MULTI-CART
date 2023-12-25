package com.base.reactive.repository;

import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.base.entity.Notificationtoken;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author muhil
 */
@Repository
public interface NotificationTokenRepository extends R2dbcRepository<Notificationtoken, Long> {

	String findAllUserTokensQuery = "select * from NotificationToken where tenantid = :tenantId and userid = :userId ";

	@Query(value = findAllUserTokensQuery)
	Flux<Notificationtoken> findAllUserTokens(@Param("tenantId") Long tenantId, @Param("userId") Long userId);

	String findNotificationForTokenQuery = "select * from NotificationToken where tenantid = :tenantId and token=:token";

	@Query(value = findNotificationForTokenQuery)
	Mono<Notificationtoken> findNotificationForToken(@Param("tenantId") Long tenantId, @Param("token") String token);
	
	String deleteQuery = "delete from NotificationToken where tenantid = :tenantId and userid = :userId";

	@Query(value = deleteQuery)
	@Modifying
	void deleteTokensForUser(@Param("tenantId") Long tenantId, @Param("userId") Long userId);

}
