package com.user.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.base.util.BaseUtil;
import com.platform.annotations.ValidateUserToken;
import com.platform.messages.GenericResponse;
import com.platform.messages.Response;
import com.platform.messages.TaskStatus;
import com.user.entity.Task;
import com.user.exception.TaskException;
import com.user.messages.TaskRequest;
import com.user.service.TaskService;

import io.micrometer.common.util.StringUtils;

/**
 * @author Muhil
 *
 */
@RestController
@RequestMapping("task")
@ValidateUserToken
public class TaskController {

	@Autowired
	private TaskService taskService;

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponse<Page<Task>> getTask(@RequestParam(value = "pageNumber", defaultValue = "0") int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
			@RequestParam(value = "taskId", required = false) Long taskId,
			@RequestParam(value = "status", required = false) String status,
			@RequestParam(value = "type", required = false) String type,
			@RequestParam(value = "broadCasted", required = false) boolean broadCastedByMe) {
		GenericResponse<Page<Task>> response = new GenericResponse<>();
		Pageable pageable = BaseUtil.getPageable(pageNumber, pageSize);
		Page<Task> page = null;
		if (broadCastedByMe) {
			page = (Page<Task>) taskService.findAllBroadCastTask(pageable);
		} else {
			if (StringUtils.isNotBlank(status)) {
				page = (Page<Task>) taskService.findAllTaskUserIsPartOf(status, pageable);
			} else {
				page = (Page<Task>) taskService.findAllTaskUserIsPartOf(pageable);
			}
		}
		if (page != null && page.getContent() != null && page.getContent().size() > 0) {
			return response.setStatus(Response.Status.OK).setData(page).build();
		}
		return response.setStatus(Response.Status.NO_CONTENT).build();
	}

	@GetMapping(value = "/{status}/count", produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponse<Integer> getStatusCount(@PathVariable("status") String status) {
		GenericResponse<Integer> response = new GenericResponse<>();
		return response.setStatus(Response.Status.OK).setData(taskService.getTasksCount(TaskStatus.getStatus(status)))
				.build();
	}

	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponse<Task> createTask(@RequestBody TaskRequest empRequest) throws TaskException {
		GenericResponse<Task> response = new GenericResponse<>();
		return response.setStatus(Response.Status.OK).setData(taskService.createTask(empRequest)).build();
	}

	@PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponse<Task> updateTaskStatus(@RequestParam(value = "markComplete") boolean completed,
			@RequestParam(value = "taskId", required = false) Long taskId) {
		GenericResponse<Task> response = new GenericResponse<>();
		return response.setStatus(Response.Status.OK).setData(taskService.updateTaskStatus(taskId,
				completed ? TaskStatus.COMPLETED.name() : TaskStatus.DECLINED.name())).build();
	}
}
