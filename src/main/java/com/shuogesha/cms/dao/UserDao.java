package com.shuogesha.cms.dao;

import org.springframework.data.mongodb.core.query.Update;

import com.shuogesha.cms.entity.User;
import com.shuogesha.cms.web.mongo.Pagination;

public interface UserDao {
	void saveEntity(User bean);

	User findById(String id);

	void update(Update update, String id);

	Pagination getPage(int pageNo, int pageSize);

	void removeById(String id);

	long countByUsername(String username);
}
