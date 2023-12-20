package com.user.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.base.service.BaseService;
import com.platform.messages.TaskStatus;
import com.user.entity.Task;
import com.user.exception.TaskException;
import com.user.messages.TaskRequest;

/**
 * @author Muhil
 *
 */
public interface TaskService extends BaseService {

	List<Task> findAll();

	Page<Task> findAll(Pageable pageable);

	Task createTask(TaskRequest request) throws TaskException;

	Page<Task> findAllByType(String type, Pageable pageable);

	Page<Task> findAllByStatus(String status, Pageable pageable);
	
	Task updateTaskStatus(Long taskId, String status);
	
	void checkAndUpdateTaskStatus();

	Page<Task> findAllTaskUserIsPartOf(Pageable pageable);
	
	Page<Task> findAllTaskUserIsPartOf(String status, Pageable pageable);

	Page<Task> findAllBroadCastTask(Pageable pageable);

	int getTasksCount(TaskStatus status);

}
