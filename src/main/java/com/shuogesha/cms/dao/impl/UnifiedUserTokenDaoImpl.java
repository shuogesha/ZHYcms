package com.shuogesha.cms.dao.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.shuogesha.api.web.util.MD5Util;
import com.shuogesha.cms.dao.UnifiedUserTokenDao;
import com.shuogesha.cms.entity.UnifiedUserToken;
import com.shuogesha.cms.web.mongo.MongodbBaseDao;

@Repository
public class UnifiedUserTokenDaoImpl  extends MongodbBaseDao  implements UnifiedUserTokenDao{

	@Override
	protected Class getEntityClass() {
		return UnifiedUserToken.class;
	}
	

	@Autowired
	@Qualifier("mongoTemplate")
	public void setMongoTemplate(MongoTemplate mongoTemplate) {
		super.mongoTemplate = mongoTemplate;
	}


	@Override
	public long count(String appid,String encryptSignature) {
		Query query = new Query(Criteria.where("encryptSignature").is(MD5Util.MD5(encryptSignature)).and("appid").is(appid));
		return mongoTemplate.count(query, getEntityClass());
	}


	@Override
	public void saveEntity(UnifiedUserToken memberToken) {
		mongoTemplate.save(memberToken);
	}


	@Override
	public UnifiedUserToken findBySignature(String appid,String encryptSignature) {
		Query query = new Query(Criteria.where("encryptSignature").is(MD5Util.MD5(encryptSignature)).and("appid").is(appid));
		return (UnifiedUserToken) mongoTemplate.findOne(query, getEntityClass());
	}


	@Override
	public void removeBySignature(String appid,String encryptSignature) {
		Query query = new Query(Criteria.where("encryptSignature").is(MD5Util.MD5(encryptSignature)).and("appid").is(appid));
		mongoTemplate.remove(query, getEntityClass());
	}
	 
}
