package com.shuogesha.cms.service;

import java.util.List;

import com.shuogesha.cms.web.mongo.Pagination;
import com.shuogesha.cms.entity.Channel;

public interface ChannelService {
	Pagination getPage(String pid,String siteId, int pageNo, int pageSize);

	Channel findById(String id);

	void save(Channel bean);

	void update(Channel bean);

	void removeById(String id);
	
	void removeByIds(String[] ids);

	List<Channel> getChildListForTag(String parentId, boolean hasContentOnly,String siteId);

	List<Channel> getTopListForTag(boolean hasContentOnly,String siteId);

	List<Channel> getNodeList(String parentId, String siteId);
}