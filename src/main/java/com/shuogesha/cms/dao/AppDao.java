package com.shuogesha.cms.dao;

import org.springframework.data.mongodb.core.query.Update;

import com.shuogesha.cms.web.mongo.Pagination;
import com.shuogesha.cms.entity.App;

public interface AppDao {
	void saveEntity(App bean);

	App findById(String id);

	void update(Update update, String id);
	
	Pagination getPage(int pageNo, int pageSize);

	void removeById(String id);

	long countById(String appid);
}