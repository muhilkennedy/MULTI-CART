package com.base.api;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.base.entity.QuartzJobInfo;
import com.base.service.QuartzJobService;
import com.base.util.BaseUtil;
import com.platform.annotations.UserPermission;
import com.platform.annotations.ValidateUserToken;
import com.platform.messages.GenericResponse;
import com.platform.messages.Response;
import com.platform.user.Permissions;

/**
 * @author Muhil
 *
 */
@RestController
@RequestMapping("admin/quartz")
@ValidateUserToken
public class QuartzController {
	
	@Autowired
	private QuartzJobService quartzService;

	@UserPermission(values = { Permissions.SUPER_USER, Permissions.ADMIN })
	@GetMapping(value = "/groups", produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponse<String> getAllGroupNames() {
		GenericResponse<String> response = new GenericResponse<>();
		return response.setStatus(Response.Status.OK).setDataList(quartzService.findAllGroups()).build();
	}
	
	@UserPermission(values = { Permissions.SUPER_USER, Permissions.ADMIN })
	@GetMapping(value = "/jobs", produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponse<Page<QuartzJobInfo>> getAllJobs(
			@RequestParam(value = "pageNumber", defaultValue = "0") int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
			@RequestParam(value = "jobGroup", required = false) String jobGroup) {
		GenericResponse<Page<QuartzJobInfo>> response = new GenericResponse<>();
		Pageable pageable = BaseUtil.getPageable(pageNumber, pageSize);
		Page<QuartzJobInfo> page = (Page<QuartzJobInfo>) (StringUtils.isAllBlank(jobGroup)
				? quartzService.findAll(pageable)
				: quartzService.findAll(jobGroup, pageable));
		if (page.getContent() != null && page.getContent().size() > 0) {
			return response.setStatus(Response.Status.OK).setData(page).build();
		}
		return response.setStatus(Response.Status.NO_CONTENT).build();
	}

//	@UserPermission(values = { Permissions.SUPER_USER, Permissions.ADMIN })
//	@PostMapping(value = "/trigger", produces = MediaType.APPLICATION_JSON_VALUE)
//	public GenericResponse<ConfigType> triggerJob(@RequestParam(value = "jobName") String jobName,
//			@RequestParam(value = "jobGroup") String jobGroup) throws SchedulerException {
//		GenericResponse<ConfigType> response = new GenericResponse<>();
//		quartzService.forceExecuteJob(jobName, jobGroup);
//		return response.setStatus(Response.Status.OK).build();
//	}

}
