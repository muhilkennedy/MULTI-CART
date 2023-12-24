package com.user.serviceimpl;

import java.sql.Date;
import java.text.ParseException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.quartz.JobDataMap;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.base.bgwork.BGWorkUtil;
import com.base.messages.NotificationRequest;
import com.base.server.BaseSession;
import com.base.service.AuditService;
import com.base.service.NotificationService;
import com.base.util.Log;
import com.platform.messages.AuditOperation;
import com.platform.messages.NotificationType;
import com.platform.messages.TaskStatus;
import com.platform.util.PlatformUtil;
import com.user.bgwork.BroadCastTaskJob;
import com.user.dao.TaskDaoService;
import com.user.entity.Employee;
import com.user.entity.Task;
import com.user.entity.TaskAssignee;
import com.user.entity.User;
import com.user.exception.TaskException;
import com.user.messages.TaskRequest;
import com.user.service.EmployeeService;
import com.user.service.TaskService;

/**
 * @author Muhil
 *
 */
@Service
public class TaskServiceImpl implements TaskService {

	@Autowired
	private TaskDaoService daoService;

	@Autowired
	private EmployeeService empService;
	
	@Autowired
	private AuditService auditService;
	
	@Autowired
	private NotificationService notify;

	@Override
	public Task findById(Long rootId) {
		return (Task) daoService.findById(rootId);
	}

	@Override
	public List<Task> findAll() {
		return (List<Task>) daoService.findAll();
	}

	@Override
	public Page<Task> findAll(Pageable pageable) {
		return daoService.findAll(pageable);
	}

	@Override
	public Page<Task> findAllTaskUserIsPartOf(Pageable pageable) {
		return daoService.findAllTasksUserPartOf(BaseSession.getUser().getRootId(), pageable);
	}
	
	@Override
	public Page<Task> findAllTaskUserIsPartOf(String status, Pageable pageable) {
		return daoService.findAllTasksUserPartOf(status, BaseSession.getUser().getRootId(), pageable);
	}
	
	@Override
	public Page<Task> findAllBroadCastTask( Pageable pageable) {
		return daoService.findAllBroadCastTask(BaseSession.getUser().getRootId(), pageable);
	}

	@Override
	public Task createTask(TaskRequest request) throws TaskException {
		Employee owner = null;
		if (request.getOwnerId() != null) {
			owner = (Employee) empService.findById(request.getOwnerId());
		}
		if (owner == null) {
			owner = (Employee) BaseSession.getUser();
		}
		if (request.isBroadCast()) {
			JobDataMap map = new JobDataMap();
			map.put("request", request);
			map.put("tenant", BaseSession.getTenant().getRootId());
			map.put("user", BaseSession.getUser().getRootId());
			try {
				BGWorkUtil.fireAndForget(BroadCastTaskJob.class, map, "INTENAL-TASK");
			} catch (SchedulerException e) {
				Log.base.error("Exception Broadcasting job : {}", e);
				auditService.logAuditInfo(AuditOperation.ERROR, e.getMessage());
			}
			return null;
		} else {
			return createTask(request, owner);
		}
	}

	public void broadcastTask(TaskRequest request) throws TaskException {
		List<User> employees = empService.findAllUsersReactive().collectList().block();
		for (User usr : employees) {
			createTask(request, (Employee) usr, true);
		}
	}
	
	private Task createTask(TaskRequest request, Employee owner) throws TaskException{
		return createTask(request, owner, false);
	}

	//TODO: better segregation can be done.
	private Task createTask(TaskRequest request, Employee owner, boolean isBroadCast) throws TaskException {
		Task task = new Task();
		task.setTitle(request.getTitle());
		task.setDescription(request.getDescription());
		task.setTasktype(request.getType().name());
		task.setStatus(TaskStatus.NOT_STARTED.name());
		task.setOwner(owner);
		task.setBroadcast(isBroadCast);
		try {
			if (StringUtils.isNotBlank(request.getStartDate())) {
				task.setStartdate(
						new Date(PlatformUtil.SIMPLE_UI_DATE_ONLY_FORMAT.parse(request.getStartDate()).getTime()));
				java.util.Date sdate = new java.util.Date(
						PlatformUtil.SIMPLE_UI_DATE_ONLY_FORMAT.parse(request.getStartDate()).getTime());
				if (Instant.now().isAfter(sdate.toInstant())) {
					task.setStatus(TaskStatus.PENDING.name());
				}
			}
			if (StringUtils.isNotBlank(request.getEndDate()))
				task.setEnddate(
						new Date(PlatformUtil.SIMPLE_UI_DATE_ONLY_FORMAT.parse(request.getEndDate()).getTime()));
		} catch (ParseException e) {
			throw new TaskException(e.getMessage());
		}
		daoService.save(task);
		List<TaskAssignee> assignees = new ArrayList<>();
		if (!isBroadCast && request.getAssigneeIds() != null && !request.getAssigneeIds().isEmpty()) {
			request.getAssigneeIds().stream().forEach(assigneeId -> {
				Employee emp = (Employee) empService.findById(assigneeId);
				assignees.add(createTaskAssignee(emp, task));
			});
		} else {
			assignees.add(createTaskAssignee(owner, task));
		}
		task.setAssignees(assignees);
		// TODO: task document uploads
		return (Task) daoService.save(task);
	}

	private TaskAssignee createTaskAssignee(Employee emp, Task task) {
		TaskAssignee assignee = new TaskAssignee();
		assignee.setAssignee(emp);
		assignee.setTask(task);
		assignee.setStatus(task.getStatus());
		daoService.saveTaskAssignee(assignee);
		try {
			NotificationRequest request = new NotificationRequest(emp.getObjectId(),
					String.format("New %s Task has been assigned to you!", task.getTasktype()), task.getTitle(),
					NotificationType.INFO, "/task");
			this.notify.createNotification(request);
		} catch (SchedulerException e) {
			Log.user.error("Error creating notification : {}", e);
		}
		return assignee;
	}

	@Override
	public Page<Task> findAllByType(String type, Pageable pageable) {
		return daoService.findAllByType(type, pageable);
	}

	@Override
	public Page<Task> findAllByStatus(String status, Pageable pageable) {
		return daoService.findAllByStatus(status, pageable);
	}

	@Override
	public Task updateTaskStatus(Long taskId, String status) {
		Task task = (Task) daoService.findById(taskId);
		task.setStatus(TaskStatus.getStatus(status).name());
		return (Task) daoService.save(task);
	}

	@Override
	public void checkAndUpdateTaskStatus() {
		// TODO Auto-generated method stub

	}
	
	@Override
	public int getTasksCount(TaskStatus status) {
		return daoService.getTasksCount(BaseSession.getUser().getRootId(), status.name());
	}

}
