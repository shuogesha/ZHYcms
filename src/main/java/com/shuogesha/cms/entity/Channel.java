package com.shuogesha.cms.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Channel {
	//根目录
	public static String GEN = "-1";
	public static String MODEL_ALONE = "alone";
	public static String MODEL_CONTENT = "content";
	@Id
	private String id;
	private String name;
	private String type;//alone,content
	private String content;
	private int sort;
	@DBRef
	public Channel parent;
	private int level=0;//子集个数

	public boolean is_display=true;
	public boolean hascontent=true;
	
	@DBRef
	public Site site;
	@DBRef
	public User user;

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


	public Channel getParent() {
		return parent;
	}

	public void setParent(Channel parent) {
		this.parent = parent;
	}

	public boolean isIs_display() {
		return is_display;
	}

	public void setIs_display(boolean is_display) {
		this.is_display = is_display;
	}

	public boolean isHascontent() {
		return hascontent;
	}

	public void setHascontent(boolean hascontent) {
		this.hascontent = hascontent;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Site getSite() {
		return site;
	}

	public void setSite(Site site) {
		this.site = site;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
	

}
