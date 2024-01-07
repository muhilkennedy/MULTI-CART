package com.base.service;

import java.util.List;

import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.base.bgwork.BGWorkUtil;
import com.base.entity.BaseEntity;
import com.base.entity.QuartzJobInfo;
import com.base.jpa.repository.QuartzJobRepository;
import com.base.reactive.repository.QuartzReactiveRepository;
import com.base.server.BaseSession;
import com.platform.messages.ScheduledTaskStatus;

import reactor.core.publisher.Flux;

/**
 * @author Muhil
 *
 */
@Service
public class QuartzJobService implements BaseService, BaseReactiveDaoService {

	@Autowired
	private QuartzJobRepository jobRepository;

	@Autowired
	private QuartzReactiveRepository reativeRepository;

	@Autowired
	private Scheduler quartzScheduler;

	@Override
	public BaseEntity findById(Long rootId) {
		return jobRepository.findById(rootId).get();
	}

	public Page<QuartzJobInfo> findAll(Pageable pageable) {
		return jobRepository.findAll(pageable);
	}

	public Page<QuartzJobInfo> findAll(String jobGroup, Pageable pageable) {
		return jobRepository.findAllJobForGroup(jobGroup, pageable);
	}

	public List<QuartzJobInfo> findAllJobForTenant() {
		return jobRepository.findAllJobsForTenant(BaseSession.getTenantId());
	}

	public Page<QuartzJobInfo> findAllJobForTenant(Pageable pageable) {
		return jobRepository.findAllJobsForTenant(BaseSession.getTenantId(), pageable);
	}

	public List<String> findAllGroups() {
		return jobRepository.findAllGroupName();
	}

	public QuartzJobInfo createQuartzJobInfo(String jobName, String jobGroup, boolean isRecurring) {
		QuartzJobInfo info = jobRepository.findJob(jobGroup, jobName);
		if (info == null) {
			info = new QuartzJobInfo();
			info.setJobgroup(jobGroup);
			info.setJobname(jobName);
			info.setIsrecurring(isRecurring);
		}
		info.setJobstatus(ScheduledTaskStatus.CREATED.name());
		return jobRepository.saveAndFlush(info);
	}

	public void updateJobInfo(String jobName, String jobGroup, ScheduledTaskStatus status) {
		QuartzJobInfo job = jobRepository.findJob(jobGroup, jobName);
		job.setJobstatus(status.name());
		jobRepository.save(job);
	}

	public void markJobProcessing(String jobName, String jobGroup) {
		QuartzJobInfo job = jobRepository.findJob(jobGroup, jobName);
		job.setJobstatus(ScheduledTaskStatus.INPROGRESS.name());
		jobRepository.save(job);
	}

	public void markJobFailure(String jobName, String jobGroup, String errorMessage) {
		QuartzJobInfo job = jobRepository.findJob(jobGroup, jobName);
		job.setJobstatus(ScheduledTaskStatus.FAILED.name());
		job.setErrorinfo(errorMessage);
		jobRepository.save(job);
	}

	public void markJobComplete(String jobName, String jobGroup) {
		QuartzJobInfo job = jobRepository.findJob(jobGroup, jobName);
		job.setJobstatus(ScheduledTaskStatus.SUCCESS.name());
		jobRepository.save(job);
	}

	public void deleteJob(String jobName, String jobGroup) throws SchedulerException {
		QuartzJobInfo job = jobRepository.findJob(jobGroup, jobName);
		JobKey key = new JobKey(jobName, jobGroup);
		BGWorkUtil.deleteJobIfExists(key);
		jobRepository.delete(job);
	}

	public void forceExecuteJob(String jobName, String jobGroup) throws SchedulerException {
		QuartzJobInfo job = jobRepository.findJob(jobGroup, jobName);
		JobKey key = new JobKey(jobName, jobGroup);
		JobDetail job1 = quartzScheduler.getJobDetail(key);
		BGWorkUtil.forceFireJob(key);
	}

	@Override
	public Flux<QuartzJobInfo> findAllReactive() {
		return reativeRepository.findAll();
	}

	@Override
	public Flux<?> saveAll(List<?> entities) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void deleteAll(List<?> entities) {
		throw new UnsupportedOperationException();
	}

}
