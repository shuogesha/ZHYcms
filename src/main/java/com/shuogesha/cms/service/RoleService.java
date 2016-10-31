package com.shuogesha.cms.service;

import java.util.List;

import net.sf.json.JSONArray;

import com.shuogesha.cms.entity.Role;
import com.shuogesha.cms.web.mongo.Pagination;

public interface RoleService {
	Pagination getPage(int pageNo, int pageSize);

	Role findById(String id);

	void save(Role bean);

	void update(Role bean);

	void removeById(String id);
	
	void removeByIds(String[] ids);

	List<Role> getList();

	List<String> getPerms(List<Role> roles);
}