package com.base.reactive.repository;

import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.base.entity.Notification;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author muhil
 */
@Repository
public interface NotificationRepository extends R2dbcRepository<Notification, Long> {

	String getAllUserUnreadNotificationQuery = "select * from Notification where tenantid = :tenantId and userid = :userId and notificationread = false order by timecreated desc";

	@Query(value = getAllUserUnreadNotificationQuery)
	Flux<Notification> getAllUserUnreadNotification(@Param("tenantId") Long tenantId, @Param("userId") Long userId);

	String getAllUserUnreadNotificationCountQuery = "select count(1) from Notification where tenantid = :tenantId and userid = :userId and notificationread = false";

	@Query(value = getAllUserUnreadNotificationCountQuery)
	Mono<Integer> getAllUserUnreadNotificationCount(@Param("tenantId") Long tenantId, @Param("userId") Long userId);

	String findByIdQuery = "select * from Notification where tenantid = :tenantId and rootid = :rootId";

	@Query(value = findByIdQuery)
	Mono<Notification> findById(@Param("tenantId") Long tenantId, @Param("rootId") Long rootId);

	String markAsReadQuery = "update Notification set notificationread = true where tenantid = :tenantId and rootid = :rootId";

	@Query(value = markAsReadQuery)
	@Modifying
	Mono<Object> markAsRead(@Param("tenantId") Long tenantId, @Param("rootId") Long rootId);
}
