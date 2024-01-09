package com.solr.config;

import java.io.Serializable;

import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;

import jakarta.persistence.Entity;

//import org.apache.solr.client.solrj.beans.Field;
//import org.springframework.data.solr.core.mapping.SolrDocument;

import jakarta.persistence.Id;

@Entity(name = "EMPLOYEE")
//@Indexed
//@SolrDocument(collection = "EMPLOYEE")
@Indexed(index = "employee_index")
public class Employee implements Serializable {
	
	@Id
//	@Field
	String id;
	
//	@Field
	@FullTextField
	String name;
	
	String pincode;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

}
