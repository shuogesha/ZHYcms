package com.shuogesha.cms.dao.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.shuogesha.cms.web.mongo.Pagination;
import com.shuogesha.cms.dao.RoleDao;
import com.shuogesha.cms.entity.Role;
import com.shuogesha.cms.web.mongo.MongodbBaseDao;
@Repository
public class RoleDaoImpl extends MongodbBaseDao implements RoleDao{

	@Override
	protected Class getEntityClass() {
		return Role.class;
	}
	

	@Autowired
	@Qualifier("mongoTemplate")
	public void setMongoTemplate(MongoTemplate mongoTemplate) {
		super.mongoTemplate = mongoTemplate;
	}


	@Override
	public void saveEntity(Role bean) {
		bean.setId(UUID.randomUUID().toString().replaceAll("-", ""));
		mongoTemplate.save(bean);
	}
	
	@Override
	public Role findById(String id) {
		Query query = new Query(Criteria.where("_id").is(id));
		return (Role) mongoTemplate.findOne(query, getEntityClass());
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
 

}