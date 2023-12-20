package com.user.entity;

import com.base.entity.FileStore;
import com.base.entity.MultiTenantEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.platform.annotations.ClassMetaProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

/**
 * @author Muhil
 *
 */
@Entity
@Table(name = "TASKDOCUMENT")
@ClassMetaProperty(code = "TSKDOC")
public class TaskDocument extends MultiTenantEntity {

	private static final long serialVersionUID = 1L;

    @OneToOne
    @JoinColumn(name = "filestoreid", referencedColumnName = "rootid")
    private FileStore filestore;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "TASKID", nullable = false, updatable = false)
	private Task task;

	public FileStore getFilestore() {
		return filestore;
	}

	public void setFilestore(FileStore filestore) {
		this.filestore = filestore;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

}
