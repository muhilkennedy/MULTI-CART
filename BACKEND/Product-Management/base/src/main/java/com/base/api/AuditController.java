package com.base.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.base.entity.Audit;
import com.base.service.AuditService;
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
@RequestMapping("admin/audit")
@ValidateUserToken
public class AuditController {
	
	@Autowired
	private AuditService auditService;

	@UserPermission(values = { Permissions.SUPER_USER, Permissions.ADMIN, Permissions.CUSTOMER_SUPPORT })
	@GetMapping(value = "/logs", produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponse<Page<Audit>> getAuditLogs(
			@RequestParam(value = "pageNumber", defaultValue = "0") int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
			@RequestParam(value = "sortBy", defaultValue = "timecreated") String sortByColumn,
			@RequestParam(value = "sortOrder", defaultValue = "DESC") String sortOrder) {
		GenericResponse<Page<Audit>> response = new GenericResponse<>();
		Pageable pageable = BaseUtil.getPageable(sortByColumn, sortOrder, pageNumber, pageSize);
		Page<Audit> page = auditService.getAuditLogs(pageable);
		if (page.getContent() != null && page.getContent().size() > 0) {
			return response.setStatus(Response.Status.OK).setData(page).build();
		}
		return response.setStatus(Response.Status.NO_CONTENT).build();
	}

}
