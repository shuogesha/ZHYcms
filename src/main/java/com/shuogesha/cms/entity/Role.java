package com.shuogesha.cms.entity;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Role {
	@Id
	private String id;
	private String name;
	private boolean allPerms=false;//是否拥有所有权限
	private List<String> perms;//权限集合

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

	public boolean isAllPerms() {
		return allPerms;
	}

	public void setAllPerms(boolean allPerms) {
		this.allPerms = allPerms;
	}

	public List<String> getPerms() {
		return perms;
	}

	public void setPerms(List<String> perms) {
		this.perms = perms;
	}

	
}
