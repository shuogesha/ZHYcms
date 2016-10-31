package com.shuogesha.cms.entity;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Site {
	@Id
	private String id;
	private String name;
	private String status;
	private String description;
	private java.lang.String imageUrl;
	private java.lang.String tplSolution;

	@DBRef
	private Site parent; //父
	@DBRef
	List<District> districts = new ArrayList<District>();

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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	
	public java.lang.String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(java.lang.String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Site getParent() {
		return parent;
	}

	public void setParent(Site parent) {
		this.parent = parent;
	}

	public List<District> getDistricts() {
		return districts;
	}

	public void setDistricts(List<District> districts) {
		this.districts = districts;
	}

	public java.lang.String getTplSolution() {
		return tplSolution;
	}

	public void setTplSolution(java.lang.String tplSolution) {
		this.tplSolution = tplSolution;
	}

	public String getSolutionPath() {
		return "/WEB-INF/template/front/"+tplSolution;
	}

	
}
