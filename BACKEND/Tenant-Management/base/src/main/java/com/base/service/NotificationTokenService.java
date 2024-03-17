package com.base.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.dao.NotificationTokenDao;
import com.base.entity.BaseEntity;
import com.base.entity.NotificationToken;
import com.base.server.BaseSession;
import com.base.util.Log;
import com.platform.cloud.messaging.SubscriptionRequest;

/**
 * @author muhil
 * TODO: write a cleanup job to delete tokens persisted for more than a year!
 */
@Service
public class NotificationTokenService implements BaseService{
	
	@Autowired
	private NotificationTokenDao daoService;

	@Override
	public BaseEntity findById(Long rootId) {
		return daoService.findById(rootId);
	}
	
	public NotificationToken registerIfNotExists(SubscriptionRequest token) {
		NotificationToken nToken = daoService.findNotificationByToken(token.getSubscriber());
		if (nToken == null) {
			Log.base.debug("NotificationTokenService: create new token entry for user : {}",
					BaseSession.getUser().getRootid());
			NotificationToken tkn = new NotificationToken();
			tkn.setUserid(BaseSession.getUser().getRootid());
			tkn.setTenantid(BaseSession.getTenantId());
			tkn.setToken(token.getSubscriber());
			tkn.setDeviceinfo(token.getDeviceInfo());
			return (NotificationToken) daoService.save(tkn);
		}
		return nToken;
	}

	public List<NotificationToken> findAllUserTokens(Long userId) {
		return daoService.findAllUserTokens(userId);
	}

	public void deleteUserTokens() {
		daoService.deleteAllTokensForUser(BaseSession.getUser().getRootid());
	}

}
