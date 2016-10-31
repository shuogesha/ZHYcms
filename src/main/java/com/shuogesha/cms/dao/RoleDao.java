package com.shuogesha.cms.dao;

import java.util.List;

import org.springframework.data.mongodb.core.query.Update;

import com.shuogesha.cms.web.mongo.Pagination;
import com.shuogesha.cms.entity.Role;

public interface RoleDao {
	void saveEntity(Role bean);

	Role findById(String id);

	void update(Update update, String id);
	
	Pagination getPage(int pageNo, int pageSize);

	void removeById(String id);

	List<Role> findAll();
}