package com.shuogesha.cms.dao;

import java.util.List;

import org.springframework.data.mongodb.core.query.Update;

import com.shuogesha.cms.web.mongo.Pagination;
import com.shuogesha.cms.entity.Channel;

public interface ChannelDao {
	void saveEntity(Channel bean);

	Channel findById(String id);

	void update(Update update, String id);
	
	Pagination getPage(String pid, String siteId,int pageNo, int pageSize);

	void removeById(String id);

	List<Channel> getChildList(String parentId, boolean hasContentOnly,String siteId);

	List<Channel> getNodeList(String pid, String siteId);
}