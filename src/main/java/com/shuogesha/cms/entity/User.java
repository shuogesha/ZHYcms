package com.shuogesha.cms.entity;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class User {
	@Id
	private String id;
	private String username;
	private String email;
	private String phone;
	private String realname;
	private boolean admin=false;
	private boolean selfAdmin=false;
	private int step;

	@DBRef
	public UnifiedUser unifiedUser;
	@DBRef
	List<Role> roles = new ArrayList<Role>();
	@DBRef
	public Site site;//所属站管理员
	@DBRef
	List<Site> sites = new ArrayList<Site>();//管理其他站点
	
	@Transient
	private String district;
	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public UnifiedUser getUnifiedUser() {
		return unifiedUser;
	}

	public void setUnifiedUser(UnifiedUser unifiedUser) {
		this.unifiedUser = unifiedUser;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	
	public Site getSite() {
		return site;
	}

	public void setSite(Site site) {
		this.site = site;
	}

	public List<Site> getSites() {
		return sites;
	}

	public void setSites(List<Site> sites) {
		this.sites = sites;
	}

	public int getStep() {
		return step;
	}

	public void setStep(int step) {
		this.step = step;
	}
	
	public boolean isSelfAdmin() {
		return selfAdmin;
	}

	public void setSelfAdmin(boolean selfAdmin) {
		this.selfAdmin = selfAdmin;
	}

	public List<String> getPerms() {
		if(roles==null){
			return null;
		}
		List<String> jsonArray = new ArrayList<String>();
		for (Role role : roles) {
			if (role!=null&&role.getPerms()!=null) {
				List<String> perms= role.getPerms();
				jsonArray.addAll(perms);
			}
		}
		return jsonArray;
	}
	
	
}
