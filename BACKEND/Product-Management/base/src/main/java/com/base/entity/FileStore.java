package com.base.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.platform.annotations.ClassMetaProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * @author Muhil
 *
 */
@Entity
@Table(name = "FILESTORE")
@ClassMetaProperty(code = "FS")
public class FileStore extends FileBlob {

	private static final long serialVersionUID = 1L;
	
	@Column(name = "ACLRESTRICTED")
	private boolean acl;
	
	@JsonIgnore
	@Column(name = "ISCLIENTFILE")
	private boolean clientfile;

	public boolean isAcl() {
		return acl;
	}

	public void setAcl(boolean acl) {
		this.acl = acl;
	}

	public boolean isClientfile() {
		return clientfile;
	}

	public void setClientfile(boolean clientfile) {
		this.clientfile = clientfile;
	}

}
