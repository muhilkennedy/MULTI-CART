package com.user.entity;

import java.sql.Date;
import java.util.List;

import com.base.entity.MultiTenantEntity;
import com.platform.annotations.ClassMetaProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

/**
 * @author Muhil Kennedy
 *
 */
@Entity
@Table(name = "TASK")
@ClassMetaProperty(code = "TSK")
public class Task extends MultiTenantEntity {

	private static final long serialVersionUID = 1L;

	@Column(name = "TITLE")
	private String title;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "TASKTYPE")
	private String tasktype;

	@Column(name = "STATUS")
	private String status;
	
	@Column(name = "BROADCAST")
	private boolean broadcast;
	
    @OneToOne
    @JoinColumn(name = "OWNERID", referencedColumnName = "rootid")
    private Employee owner;

    @Temporal(TemporalType.DATE)
	@Column(name = "STARTDATE")
	private Date startdate;

    @Temporal(TemporalType.DATE)
	@Column(name = "ENDDATE")
	private Date enddate;
	
	@OneToMany(mappedBy = "task", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<TaskAssignee> assignees;
	
	@OneToMany(mappedBy = "task", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<TaskDocument> documents;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTasktype() {
		return tasktype;
	}

	public void setTasktype(String tasktype) {
		this.tasktype = tasktype;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

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

	public List<TaskAssignee> getAssignees() {
		return assignees;
	}

	public void setAssignees(List<TaskAssignee> assignees) {
		this.assignees = assignees;
	}

	public List<TaskDocument> getDocuments() {
		return documents;
	}

	public void setDocuments(List<TaskDocument> documents) {
		this.documents = documents;
	}

	public Employee getOwner() {
		return owner;
	}

	public void setOwner(Employee owner) {
		this.owner = owner;
	}

	public boolean isBroadcast() {
		return broadcast;
	}

	public void setBroadcast(boolean broadcast) {
		this.broadcast = broadcast;
	}

}
