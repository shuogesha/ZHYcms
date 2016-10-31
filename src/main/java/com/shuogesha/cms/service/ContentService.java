package com.shuogesha.cms.service;

import com.shuogesha.cms.web.mongo.Pagination;
import com.shuogesha.cms.entity.Content;

public interface ContentService {
	Pagination getPage(String channelId,String siteId,int pageNo, int pageSize);

	Content findById(String id);

	void save(Content bean);

	void update(Content bean);

	void removeById(String id);
	
	void removeByIds(String[] ids);

	void applyAudit(String id);
}