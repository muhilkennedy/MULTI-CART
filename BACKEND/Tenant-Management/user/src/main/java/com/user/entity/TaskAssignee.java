package com.user.entity;

import java.sql.Date;

import com.base.entity.MultiTenantEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.platform.annotations.ClassMetaProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

/**
 * @author Muhil Kennedy
 *
 */
@Entity
@Table(name = "TASKASSIGNEE")
@ClassMetaProperty(code = "TSAS")
public class TaskAssignee extends MultiTenantEntity {

	private static final long serialVersionUID = 1L;

	@Column(name = "STATUS")
	private String status;
	
    @OneToOne
    @JoinColumn(name = "assigneeid", referencedColumnName = "rootid")
    private Employee assignee;

	@Column(name = "COMPLETEDON")
	private Date completedon;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "TASKID", nullable = false, updatable = false)
	private Task task;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCompletedon() {
		return completedon;
	}

	public void setCompletedon(Date completedon) {
		this.completedon = completedon;
	}

	public Employee getAssignee() {
		return assignee;
	}

	public void setAssignee(Employee assignee) {
		this.assignee = assignee;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

}
