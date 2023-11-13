package com.base.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import jakarta.persistence.Column;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
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

	@Column(name = "STORETYPE")
	private String storetype;
	
	@JsonIgnore
	@Column(name = "BLOBINFO")
	private String blobinfo;
	
	@Column(name = "EXTENSION")
	private String fileExtention;

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

	public String getBlobinfo() {
		return blobinfo;
	}

	public Object getBlobinfo(Class<?> type) {
		Gson gson = new Gson();
		return gson.fromJson(this.blobinfo, type);
	}

	public void setBlobinfo(Object blobinfo) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		this.blobinfo = mapper.writeValueAsString(blobinfo);
	}

}
