package com.user.messages;

import java.io.Serializable;
import java.util.List;

import com.platform.messages.TaskType;

import jakarta.validation.constraints.NotBlank;

/**
 * @author Muhil
 *
 */
public class TaskRequest implements Serializable {

	private static final long serialVersionUID = 1L;
	@NotBlank
	private String title;
	@NotBlank
	private String description;
	private Long ownerId;
	private List<Long> assigneeIds;
	@NotBlank
	private String type;
	private String startDate;
	private String endDate;
	private boolean broadCast;
	private boolean managerApproval;

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

	public Long getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(Long ownerId) {
		this.ownerId = ownerId;
	}

	public List<Long> getAssigneeIds() {
		return assigneeIds;
	}

	public void setAssigneeIds(List<Long> assigneeIds) {
		this.assigneeIds = assigneeIds;
	}

	public TaskType getType() {
		return TaskType.getType(type);
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public boolean isBroadCast() {
		return broadCast;
	}

	public void setBroadCast(boolean broadCast) {
		this.broadCast = broadCast;
	}

	public boolean isManagerApproval() {
		return managerApproval;
	}

	public void setManagerApproval(boolean managerApproval) {
		this.managerApproval = managerApproval;
	}

}
