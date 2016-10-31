package com.shuogesha.cms.dao.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.shuogesha.cms.dao.AuthenticationDao;
import com.shuogesha.cms.entity.Authentication;
import com.shuogesha.cms.web.mongo.MongodbBaseDao;
@Repository
public class AuthenticationDaoImpl extends MongodbBaseDao implements AuthenticationDao{

	@Override
	protected Class getEntityClass() {
		return Authentication.class;
	}
	

	@Autowired
	@Qualifier("mongoTemplate")
	public void setMongoTemplate(MongoTemplate mongoTemplate) {
		super.mongoTemplate = mongoTemplate;
	}


	@Override
	public void removeById(String id) {
		Query query = new Query(Criteria.where("_id").is(id));
		mongoTemplate.remove(query, getEntityClass());
	}


	@Override
	public void saveEntity(Authentication bean) {
		mongoTemplate.save(bean);
	}
	
	@Override
	public Authentication findById(String id) {
		Query query = new Query(Criteria.where("_id").is(id));
		return (Authentication) mongoTemplate.findOne(query, getEntityClass());
	}


	@Override
	public void deleteExpire(long date) {
		Query query = new Query(Criteria.where("updateTime").lte(date));
		mongoTemplate.remove(query, getEntityClass());
	}

}
