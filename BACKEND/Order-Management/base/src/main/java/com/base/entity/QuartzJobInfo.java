package com.base.entity;

import org.apache.commons.lang3.StringUtils;

import com.base.server.BaseSession;
import com.platform.annotations.ClassMetaProperty;
import com.platform.util.PlatformUtil;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

/**
 * @author Muhil
 *
 */
@Entity
@Table(name = "QUARTZJOBINFO")
@ClassMetaProperty(code = "QTZJOB")
public class QuartzJobInfo extends BaseEntity {

	private static final long serialVersionUID = 1L;
	private static final int ERROR_SIZE_LIMIT = 2048;

	@Column(name = "JOBNAME")
	private String jobname;

	@Column(name = "JOBGROUP")
	private String jobgroup;

	@Column(name = "JOBSTATUS")
	private String jobstatus;

	@Column(name = "ISRECURRING")
	private boolean isrecurring;

	@Column(name = "ERRORINFO", length = 2048)
	private String errorinfo;
	
	@Column(name = "TENANTID")
	private Long tenantid = PlatformUtil.SYSTEM_REALM_ROOTID;

	public QuartzJobInfo() {
		super();
	}

	public String getJobname() {
		return jobname;
	}

	public void setJobname(String jobname) {
		this.jobname = jobname;
	}

	public String getJobgroup() {
		return jobgroup;
	}

	public void setJobgroup(String jobgroup) {
		this.jobgroup = jobgroup;
	}

	public void setJobstatus(String jobstatus) {
		this.jobstatus = jobstatus;
	}

	public boolean isIsrecurring() {
		return isrecurring;
	}

	public void setIsrecurring(boolean isrecurring) {
		this.isrecurring = isrecurring;
	}

	public Long getTenantId() {
		return tenantid;
	}

	public void setTenantId(Long tenantId) {
		this.tenantid = tenantId;
	}

	public String getJobstatus() {
		return jobstatus;
	}

	public String getErrorinfo() {
		return errorinfo;
	}

	public void setErrorinfo(String errorinfo) {
		if (StringUtils.isNotBlank(errorinfo) && errorinfo.length() >= ERROR_SIZE_LIMIT) {
			this.errorinfo = errorinfo.substring(0, ERROR_SIZE_LIMIT);
		} else {
			this.errorinfo = errorinfo;
		}
	}
	
	@PrePersist
	private void manipulateTenant() {
		if(BaseSession.getTenant() != null) {
			setTenantId(BaseSession.getTenantId());
		}
	}

}
