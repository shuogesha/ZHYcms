package com.shuogesha.cms.dao.impl;

import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.shuogesha.cms.web.mongo.Pagination;
import com.shuogesha.cms.dao.ChannelDao;
import com.shuogesha.cms.entity.Channel;
import com.shuogesha.cms.web.mongo.MongodbBaseDao;
@Repository
public class ChannelDaoImpl extends MongodbBaseDao implements ChannelDao{

	@Override
	protected Class getEntityClass() {
		return Channel.class;
	}
	

	@Autowired
	@Qualifier("mongoTemplate")
	public void setMongoTemplate(MongoTemplate mongoTemplate) {
		super.mongoTemplate = mongoTemplate;
	}


	@Override
	public void saveEntity(Channel bean) {
		bean.setId(UUID.randomUUID().toString().replaceAll("-", ""));
		mongoTemplate.save(bean);
	}
	
	@Override
	public Channel findById(String id) {
		Query query = new Query(Criteria.where("_id").is(id));
		return (Channel) mongoTemplate.findOne(query, getEntityClass());
	}


	@Override
	public void update(Update update, String id) {
		Query query = new Query(Criteria.where("_id").is(id));
		mongoTemplate.updateFirst(query, update, getEntityClass());
	}
	
	@Override
	public Pagination getPage(String pid,String siteId,int pageNo, int pageSize) {
		Query query = new Query(Criteria.where("site.$id").is(siteId));
		query.addCriteria(Criteria.where("parent").is(pid));
		return getPage(pageNo, pageSize, query);
	}


	@Override
	public void removeById(String id) {
		Query query = new Query(Criteria.where("_id").is(id));
		mongoTemplate.remove(query, getEntityClass());
	}


	private Query getChild(String parentId,
			boolean displayOnly) {
		Query query = new Query(Criteria.where("parent").is(parentId));
		if(displayOnly){
			query.addCriteria(Criteria.where("is_display").is(0));
		}
		appendOrder(query,1);
		return query;
	}
	@Override
	public List<Channel> getChildList(String parentId,
			boolean displayOnly,String siteId) {
		Query query = getChild(parentId, displayOnly);
		query.addCriteria(Criteria.where("site.$id").is(siteId));
		return mongoTemplate.find(query, getEntityClass());
	}

	
	private void appendOrder(Query query, int orderBy) {
		switch (orderBy) {
		case 0:
			query.with(new Sort(Sort.Direction.DESC,"sort"));
			break;
		case 1:
			query.with(new Sort(Sort.Direction.ASC,"sort"));
			break;
		}
	}


	@Override
	public List<Channel> getNodeList(String pid, String siteId) {
		Query query = new Query();
		query.fields().include("_id");
		query.fields().include("name");
		query.fields().include("parent");
		query.fields().include("type");
		query.fields().include("level");
		if (StringUtils.isNotBlank(siteId)) {
			query.addCriteria(Criteria.where("site.$id").is(siteId));
		}
		if (StringUtils.isBlank(pid)) {
			pid=null;
		}
		query.addCriteria(Criteria.where("parent.$id").is(pid));
		return mongoTemplate.find(query, getEntityClass());
	}
 

}