package com.shuogesha.cms.dao.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.shuogesha.cms.dao.UnifiedUserDao;
import com.shuogesha.cms.entity.UnifiedUser;
import com.shuogesha.cms.web.mongo.MongodbBaseDao;
import com.shuogesha.cms.web.mongo.Pagination;
@Repository
public class UnifiedUserDaoImpl extends MongodbBaseDao implements UnifiedUserDao{

	@Override
	protected Class getEntityClass() {
		return UnifiedUser.class;
	}
	

	@Autowired
	@Qualifier("mongoTemplate")
	public void setMongoTemplate(MongoTemplate mongoTemplate) {
		super.mongoTemplate = mongoTemplate;
	}


	@Override
	public void saveEntity(UnifiedUser bean) {
		bean.setId(UUID.randomUUID().toString().replaceAll("-", ""));
		mongoTemplate.save(bean);
	}
	
	@Override
	public UnifiedUser findById(String id) {
		Query query = new Query(Criteria.where("_id").is(id));
		return (UnifiedUser) mongoTemplate.findOne(query, getEntityClass());
	}


	@Override
	public void update(Update update, String id) {
		Query query = new Query(Criteria.where("_id").is(id));
		mongoTemplate.updateFirst(query, update, getEntityClass());
	}
	
	@Override
	public Pagination getPage(int pageNo, int pageSize) {
		Query query = new Query();
		return getPage(pageNo, pageSize, query);
	}

	@Override
	public UnifiedUser findByUsername(String username) {
		Query query = new Query(Criteria.where("username").is(username));
		return (UnifiedUser) mongoTemplate.findOne(query, getEntityClass());
	}

	@Override
	public void removeById(String id) {
		Query query = new Query(Criteria.where("_id").is(id));
		mongoTemplate.remove(query, getEntityClass());
	}
 

}