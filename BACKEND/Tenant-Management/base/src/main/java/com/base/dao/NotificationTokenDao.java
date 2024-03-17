package com.base.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.base.entity.BaseEntity;
import com.base.entity.NotificationToken;
import com.base.jpa.repository.NotificationTokenRepository;
import com.base.service.BaseDaoService;
import com.base.util.CacheUtil;

/**
 * @author muhil
 */
@Service
public class NotificationTokenDao implements BaseDaoService  {
	
	@Autowired
	private NotificationTokenRepository tknRepo;

	@Override
	public BaseEntity save(BaseEntity obj) {
		return tknRepo.save((NotificationToken) obj);
	}

	@Override
	public BaseEntity saveAndFlush(BaseEntity obj) {
		return tknRepo.saveAndFlush((NotificationToken) obj);
	}

	@Override
	public BaseEntity findById(Long rootId) {
		return tknRepo.findById(rootId).get();
	}

	@Override
	public void delete(BaseEntity obj) {
		tknRepo.deleteById(obj.getObjectId());
	}

	@Override
	public List<?> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteById(Long rootId) {
		tknRepo.deleteById(rootId);
	}
	
	@Cacheable(value = CacheUtil.DEFAULT_CACHE_NAME, key = "#token")
	public NotificationToken findNotificationByToken(String token) {
		return tknRepo.findNotificationForToken(token);
	}
	
	public List<NotificationToken> findAllUserTokens(Long userId){
		return tknRepo.findAllUserTokens(userId);
	}
	
	public void deleteAllTokensForUser(Long userId) {
		tknRepo.deleteTokensForUser(userId);
	}

}
