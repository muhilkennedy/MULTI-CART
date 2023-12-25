package com.base.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.entity.BaseEntity;
import com.base.entity.Notificationtoken;
import com.base.reactive.repository.NotificationTokenRepository;
import com.base.server.BaseSession;
import com.base.util.Log;
import com.platform.cloud.messaging.SubscriptionRequest;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author muhil
 * TODO: write a cleanup job to delete tokens persisted for more than a year!
 */
@Service
public class NotificationTokenService implements BaseService{
	
	@Autowired
	private NotificationTokenRepository repository;

	@Override
	public BaseEntity findById(Long rootId) {
		return repository.findById(rootId).block();
	}
	
	public Notificationtoken registerIfNotExists(SubscriptionRequest token) {
		Mono<Notificationtoken> nToken = repository.findNotificationForToken(BaseSession.getTenantId(), token.getSubscriber());
		if (nToken.block() == null) {
			Log.base.debug("NotificationTokenService: create new token entry for user : {}",
					BaseSession.getUser().getRootid());
			Notificationtoken tkn = new Notificationtoken();
			tkn.setUserid(BaseSession.getUser().getRootid());
			tkn.setTenantid(BaseSession.getTenantId());
			tkn.setToken(token.getSubscriber());
			tkn.setDeviceinfo(token.getDeviceInfo());
			return repository.save(tkn).block();
		}
		return nToken.block();
	}
	
	public Flux<Notificationtoken> findAllUserTokens() {
		return repository.findAllUserTokens(BaseSession.getTenantId(), BaseSession.getUser().getRootid());
	}
	
	public Flux<Notificationtoken> findAllUserTokens(Long userId) {
		return repository.findAllUserTokens(BaseSession.getTenantId(), userId);
	}

	public void deleteUserTokens() {
		repository.deleteTokensForUser(BaseSession.getTenantId(), BaseSession.getUser().getRootid());
	}

}
