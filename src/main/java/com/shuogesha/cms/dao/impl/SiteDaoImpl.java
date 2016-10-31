package com.shuogesha.cms.dao.impl;

import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.shuogesha.cms.dao.SiteDao;
import com.shuogesha.cms.entity.Site;
import com.shuogesha.cms.web.mongo.MongodbBaseDao;
import com.shuogesha.cms.web.mongo.Pagination;
@Repository
public class SiteDaoImpl extends MongodbBaseDao implements SiteDao{

	@Override
	protected Class getEntityClass() {
		return Site.class;
	}
	

	@Autowired
	@Qualifier("mongoTemplate")
	public void setMongoTemplate(MongoTemplate mongoTemplate) {
		super.mongoTemplate = mongoTemplate;
	}


	@Override
	public void saveEntity(Site bean) {
		bean.setId(UUID.randomUUID().toString().replaceAll("-", ""));
		mongoTemplate.save(bean);
	}
	
	@Override
	public Site findById(String id) {
		Query query = new Query(Criteria.where("_id").is(id));
		return (Site) mongoTemplate.findOne(query, getEntityClass());
	}


	@Override
	public void update(Update update, String id) {
		Query query = new Query(Criteria.where("_id").is(id));
		mongoTemplate.updateFirst(query, update, getEntityClass());
	}
	
	@Override
	public Pagination getPage(String siteId,int pageNo, int pageSize) {
		Query query = new Query();
		if(StringUtils.isNotBlank(siteId)){
			query.addCriteria(Criteria.where("parent.$id").is(siteId));
		}
		return getPage(pageNo, pageSize, query);
	}


	@Override
	public void removeById(String id) {
		Query query = new Query(Criteria.where("_id").is(id));
		mongoTemplate.remove(query, getEntityClass());
	}
 
	@Override
	public List<Site> findAll() {
		Query query = new Query();
		return mongoTemplate.find(query, getEntityClass());
	}


	@Override
	public Site findMaster() {
		Query query = new Query(Criteria.where("parent.$id").is(null));
		return (Site) mongoTemplate.findOne(query, getEntityClass());
	}


	@Override
	public Site findByTplSolution(String path) {
		Query query = new Query(Criteria.where("tplSolution").is(path));
		return (Site) mongoTemplate.findOne(query, getEntityClass());
	}

}