package com.base.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.base.entity.BaseEntity;
import com.base.entity.Notificationtoken;
import com.base.reactive.repository.NotificationTokenRepository;
import com.base.server.BaseSession;
import com.base.service.BaseDaoService;
import com.base.util.CacheUtil;

/**
 * @author muhil
 */
@Service
public class NotificationTokenDao implements BaseDaoService  {
	
	@Autowired
	private NotificationTokenRepository tokenRepo;

	@Override
	public BaseEntity save(BaseEntity obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BaseEntity saveAndFlush(BaseEntity obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BaseEntity findById(Long rootId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(BaseEntity obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<?> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteById(Long rootId) {
		// TODO Auto-generated method stub
		
	}
	
	@Cacheable(value = CacheUtil.DEFAULT_CACHE_NAME, key = "#token")
	public Notificationtoken findNotificationForToken(Long tenantId, String token) {
		return tokenRepo.findNotificationForToken(BaseSession.getTenantId(), token).block();
	}

}
