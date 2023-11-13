package com.tenant.entity;

import java.util.Date;

import com.base.entity.MultiTenantEntity;
import com.platform.annotations.ClassMetaProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

/**
 * @author Muhil Kennedy
 *
 */
@Entity
@Table(name = "TENANTSUBSCRIPTION")
@ClassMetaProperty(code = "TS")
public class TenantSubscription extends MultiTenantEntity {

	private static final long serialVersionUID = 1L;

	@Column(name = "STARTDATE")
	@Temporal(TemporalType.DATE)
	private Date startdate;

	@Column(name = "ENDDATE")
	@Temporal(TemporalType.DATE)
	private Date enddate;

	@Column(name = "SLA")
	private String sla;

	public Date getStartdate() {
		return startdate;
	}

	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}

	public Date getEnddate() {
		return enddate;
	}

	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}

	public String getSla() {
		return sla;
	}

	public void setSla(String sla) {
		this.sla = sla;
	}

}
