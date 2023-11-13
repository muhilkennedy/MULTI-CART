package com.user.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.base.entity.BaseEntity;
import com.base.util.CacheUtil;
import com.user.entity.Employee;
import com.user.entity.User;
import com.user.jpa.repository.EmployeeRepository;
import com.user.reactive.repository.EmployeeReactiveRepository;

import reactor.core.publisher.Flux;

/**
 * @author Muhil
 *
 */
@Service
@Qualifier("EmployeeDaoService")
public class EmployeeDaoService implements UserDaoService {
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private EmployeeReactiveRepository empReactiveRepo;

	@Override
	public Flux<?> findAllReactive() {
		return empReactiveRepo.findAll();
	}

	@Override
	public Flux<?> saveAll(List<?> entities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteAll(List<?> entities) {
		employeeRepository.deleteAll((Iterable<Employee>) entities);
	}

	@Override
	@CachePut(value = CacheUtil.EMPLOYEE_CACHE_NAME, key = "#obj.rootId")
	public BaseEntity save(BaseEntity obj) {
		return employeeRepository.save((Employee) obj);
	}

	@Override
	@CachePut(value = CacheUtil.EMPLOYEE_CACHE_NAME, key = "#obj.rootId")
	public BaseEntity saveAndFlush(BaseEntity obj) {
		return employeeRepository.saveAndFlush((Employee) obj);
	}

	@Override
	@Cacheable(value = CacheUtil.EMPLOYEE_CACHE_NAME, key = "#rootId")
	public BaseEntity findById(Long rootId) {
		return employeeRepository.findById(rootId).get();
	}

	@Override
	@CacheEvict(value = CacheUtil.EMPLOYEE_CACHE_NAME, key = "#obj.rootId")
	public void delete(BaseEntity obj) {
		employeeRepository.delete((Employee)obj);
	}

	@Override
	public List<?> findAll() {
		return employeeRepository.findAll();
	}

	@Override
	@CacheEvict(value = CacheUtil.EMPLOYEE_CACHE_NAME, key = "#obj.rootId")
	public void deleteById(Long rootId) {
		employeeRepository.deleteById(rootId);
	}

	@Override
	public User findByEmailId(String emailId) {
		return employeeRepository.findByEmailId(emailId);
	}

	@Override
	public User findUserForLogin(User user) {
		return employeeRepository.findUserForLogin(user.getEmailid(), user.getMobile(), user.getUniquename());
	}

	@Override
	public User findByUniqueName(String uniqueName) {
		return employeeRepository.findByUniqueName(uniqueName);
	}
	
	

}
