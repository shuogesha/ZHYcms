package com.shuogesha.cms.dao;

import org.springframework.data.mongodb.core.query.Update;

import com.shuogesha.cms.entity.UnifiedUser;
import com.shuogesha.cms.web.mongo.Pagination;

public interface UnifiedUserDao {
	void saveEntity(UnifiedUser bean);

	UnifiedUser findById(String id);

	void update(Update update, String id);
	
	Pagination getPage(int pageNo, int pageSize);

	UnifiedUser findByUsername(String username);

	void removeById(String id);
}