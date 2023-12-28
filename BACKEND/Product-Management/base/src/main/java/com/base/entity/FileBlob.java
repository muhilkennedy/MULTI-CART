package com.base.entity;

import org.springframework.util.SerializationUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;

import jakarta.persistence.Column;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Lob;
import jakarta.persistence.MappedSuperclass;

/**
 * @author Muhil
 *
 */
@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class FileBlob extends MultiTenantEntity {

	private static final long serialVersionUID = 1L;

	@Column(name = "MEDIAURL")
	private String mediaurl;

	@JsonIgnore
	@Column(name = "STORETYPE")
	private String storetype;

	@JsonIgnore
	@Lob
	@Column(name = "BLOBINFO")
	private byte[] blobinfo;

	@Column(name = "EXTENSION")
	private String fileExtention;

	@Column(name = "FILENAME")
	private String fileName;
	
	@Column(name = "SIZE")
	private Long size;

	public String getMediaurl() {
		return mediaurl;
	}

	public void setMediaurl(String mediaurl) {
		this.mediaurl = mediaurl;
	}

	public String getStoretype() {
		return storetype;
	}

	public void setStoretype(String storetype) {
		this.storetype = storetype;
	}

	public String getFileExtention() {
		return fileExtention;
	}

	public void setFileExtention(String fileExtention) {
		this.fileExtention = fileExtention;
	}

	public byte[] getBlobinfo() {
		return blobinfo;
	}

	public void setBlobinfo(byte[] blobinfo) {
		this.blobinfo = blobinfo;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@JsonIgnore
	public Object getBlobInfo() {
		return SerializationUtils.deserialize(this.blobinfo);
	}

	public void setBlobInfo(Object blobinfo) throws JsonProcessingException {
		this.blobinfo = SerializationUtils.serialize(blobinfo);
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

}
