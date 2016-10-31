package com.shuogesha.cms.dao;

import org.springframework.data.mongodb.core.query.Update;

import com.shuogesha.cms.web.mongo.Pagination;
import com.shuogesha.cms.entity.Content;

public interface ContentDao {
	void saveEntity(Content bean);

	Content findById(String id);

	void update(Update update, String id);
	
	Pagination getPage(String channelId,String siteId,int pageNo, int pageSize);

	void removeById(String id);
}