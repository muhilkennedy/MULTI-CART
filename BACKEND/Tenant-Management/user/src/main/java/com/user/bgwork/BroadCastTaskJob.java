package com.user.bgwork;

import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.base.bgwork.BGJob;
import com.google.gson.Gson;
import com.user.exception.TaskException;
import com.user.messages.TaskRequest;
import com.user.serviceimpl.TaskServiceImpl;

/**
 * @author Muhil Job to create tasks for all employees
 */
@Component
public class BroadCastTaskJob extends BGJob {

	@Autowired
	private TaskServiceImpl taskService;

	@Override
	public void schedule() throws SchedulerException {
		// No-Op
	}

	@Override
	public void run() {
		// No-Op
	}

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		JobDataMap map = arg0.getJobDetail().getJobDataMap();
		setupSession(map.getLong("tenant"), map.getLong("user"));
		try {
			//This is a workaround fix for unnamed module error (need to check by disabling spring devtools)
			Object obj = map.get("request");
			TaskRequest tr = new TaskRequest();
			Gson gson = new Gson();
			String jsonString = gson.toJson(obj);
			tr = gson.fromJson(jsonString, TaskRequest.class);
			taskService.broadcastTask(tr);
		} catch (TaskException e) {
			throw new JobExecutionException(e.getMessage());
		} finally {
			teardownSession();
		}

	}

}