package com.shuogesha.cms.service;

import com.shuogesha.cms.web.mongo.Pagination;
import com.shuogesha.cms.entity.App;

public interface AppService {
	Pagination getPage(int pageNo, int pageSize);

	App findById(String id);

	void save(App bean);

	void update(App bean);

	void removeById(String id);
	
	void removeByIds(String[] ids);

	long countById(String appid);
}