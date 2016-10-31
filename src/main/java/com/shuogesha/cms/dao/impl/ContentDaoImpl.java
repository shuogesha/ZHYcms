package com.shuogesha.cms.dao.impl;

import java.util.UUID;

import org.apache.commons.lang.StringUtils;
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
import com.shuogesha.cms.dao.ContentDao;
import com.shuogesha.cms.entity.Content;
import com.shuogesha.cms.web.mongo.MongodbBaseDao;
@Repository
public class ContentDaoImpl extends MongodbBaseDao implements ContentDao{

	@Override
	protected Class getEntityClass() {
		return Content.class;
	}
	

	@Autowired
	@Qualifier("mongoTemplate")
	public void setMongoTemplate(MongoTemplate mongoTemplate) {
		super.mongoTemplate = mongoTemplate;
	}


	@Override
	public void saveEntity(Content bean) {
		bean.setId(UUID.randomUUID().toString().replaceAll("-", ""));
		mongoTemplate.save(bean);
	}
	
	@Override
	public Content findById(String id) {
		Query query = new Query(Criteria.where("_id").is(id));
		return (Content) mongoTemplate.findOne(query, getEntityClass());
	}


	@Override
	public void update(Update update, String id) {
		Query query = new Query(Criteria.where("_id").is(id));
		mongoTemplate.updateFirst(query, update, getEntityClass());
	}
	
	@Override
	public Pagination getPage(String channelId,String siteId,int pageNo, int pageSize) {
		Query query = new Query(Criteria.where("site.$id").is(siteId));
		if (StringUtils.isNotBlank(channelId)) {
			query.addCriteria(Criteria.where("channel.$id").is(channelId));
		}
		query.with(new Sort(Direction.DESC, "dateline"));
		return getPage(pageNo, pageSize, query);
	}


	@Override
	public void removeById(String id) {
		Query query = new Query(Criteria.where("_id").is(id));
		mongoTemplate.remove(query, getEntityClass());
	}
 

}