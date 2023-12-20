package com.user.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.base.entity.BaseEntity;
import com.base.service.BaseDaoService;
import com.user.entity.Task;
import com.user.entity.TaskAssignee;
import com.user.jpa.repository.TaskAssigneeRepository;
import com.user.jpa.repository.TaskRepository;

/**
 * @author Muhil
 *
 */
@Service
public class TaskDaoService implements BaseDaoService {
	
	@Autowired
	private TaskRepository taskRepository;
	
	@Autowired
	private TaskAssigneeRepository taskAssigneeRepo;

	@Override
	public BaseEntity save(BaseEntity obj) {
		return taskRepository.save((Task) obj);
	}

	@Override
	public BaseEntity saveAndFlush(BaseEntity obj) {
		return taskRepository.saveAndFlush((Task) obj);
	}

	@Override
	public BaseEntity findById(Long rootId) {
		return taskRepository.findById(rootId).get();
	}

	@Override
	public void delete(BaseEntity obj) {
		taskRepository.delete((Task) obj);
	}

	@Override
	public List<?> findAll() {
		return taskRepository.findAll();
	}

	@Override
	public void deleteById(Long rootId) {
		taskRepository.deleteById(rootId);
	}
	
	public TaskAssignee saveTaskAssignee(TaskAssignee assignee) {
		return taskAssigneeRepo.save(assignee);
	}
	
	@Override
	public Page<Task> findAll(Pageable pageable) {
		return taskRepository.findAll(pageable);
	}

	public Page<Task> findAllByType(String type, Pageable pageable) {
		return taskRepository.findByType(type, pageable);
	}

	public Page<Task> findAllByStatus(String status, Pageable pageable) {
		return taskRepository.findByStatus(status, pageable);
	}
	
	public Page<Task> findAllTasksUserPartOf(Long assigneeId, Pageable pageable) {
		return taskRepository.findAllTasksUserPartOfTask(assigneeId, pageable);
	}

	public Page<Task> findAllTasksUserPartOf(String status, Long assigneeId, Pageable pageable) {
		return taskRepository.findAllUserTaskByStatus(status, assigneeId, pageable);
	}

	public Page<Task> findAllBroadCastTask(Long ownerId, Pageable pageable) {
		return taskRepository.findAllBroadCastTask(ownerId, pageable);
	}
	
	public int getTasksCount(Long ownerId, String status) {
		return taskAssigneeRepo.getPendingTasksCount(ownerId, status);
	}

}
