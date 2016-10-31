package com.shuogesha.cms.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.shuogesha.cms.web.mongo.Pagination;
import com.shuogesha.cms.dao.DistrictDao;
import com.shuogesha.cms.entity.District;
import com.shuogesha.cms.web.mongo.MongodbBaseDao;
@Repository
public class DistrictDaoImpl extends MongodbBaseDao implements DistrictDao{

	@Override
	protected Class getEntityClass() {
		return District.class;
	}
	

	@Autowired
	@Qualifier("mongoTemplate")
	public void setMongoTemplate(MongoTemplate mongoTemplate) {
		super.mongoTemplate = mongoTemplate;
	}


	@Override
	public void saveEntity(District bean) {
		mongoTemplate.save(bean);
	}
	
	@Override
	public District findById(String id) {
		Query query = new Query(Criteria.where("_id").is(id));
		return (District) mongoTemplate.findOne(query, getEntityClass());
	}


	@Override
	public void update(Update update, String id) {
		Query query = new Query(Criteria.where("_id").is(id));
		mongoTemplate.updateFirst(query, update, getEntityClass());
	}
	
	@Override
	public Pagination getPage(String pcode, String level, int pageNo, int pageSize) {
		Query query = new Query(Criteria.where("pcode").is(pcode).and("level").is(level));
		return getPage(pageNo, pageSize, query);
	}


	@Override
	public void removeById(String id) {
		Query query = new Query(Criteria.where("_id").is(id));
		mongoTemplate.remove(query, getEntityClass());
	}
 
	@Override
	public List<District> find(String pcode, String level) {
		Query query = new Query(Criteria.where("pcode").is(pcode).and("level").is(level));
		return mongoTemplate.find(query, getEntityClass());
	}


	@Override
	public List<District> find(String pcode) {
		Query query = new Query(Criteria.where("pcode").is(pcode));
		return mongoTemplate.find(query, getEntityClass());
	}


	@Override
	public District findNext(String pcode) {
		Query query = new Query(Criteria.where("pcode").is(pcode));
		query.with(new Sort(Direction.DESC, "_id"));
		return (District) mongoTemplate.findOne(query, getEntityClass());
	}

}