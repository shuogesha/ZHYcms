package com.shuogesha.cms.dao.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.shuogesha.cms.dao.UserDao;
import com.shuogesha.cms.entity.User;
import com.shuogesha.cms.web.mongo.MongodbBaseDao;
import com.shuogesha.cms.web.mongo.Pagination;
@Repository
public class UserDaoImpl extends MongodbBaseDao implements UserDao{

	@Override
	protected Class getEntityClass() {
		return User.class;
	}
	

	@Autowired
	@Qualifier("mongoTemplate")
	public void setMongoTemplate(MongoTemplate mongoTemplate) {
		super.mongoTemplate = mongoTemplate;
	}


	@Override
	public void saveEntity(User bean) {
		mongoTemplate.save(bean);
	}
	
	@Override
	public User findById(String id) {
		Query query = new Query(Criteria.where("_id").is(id));
		return (User) mongoTemplate.findOne(query, getEntityClass());
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
	public void removeById(String id) {
		Query query = new Query(Criteria.where("_id").is(id));
		mongoTemplate.remove(query, getEntityClass());
	}


	@Override
	public long countByUsername(String username) {
		Query query = new Query(Criteria.where("username").is(username));
		return mongoTemplate.count(query, getEntityClass());
	}
 

}
