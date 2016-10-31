package com.shuogesha.cms.dao;

import java.util.List;
import java.util.Map;

import org.springframework.data.mongodb.core.query.Update;

import com.shuogesha.cms.web.mongo.Pagination;
import com.shuogesha.cms.entity.District;

public interface DistrictDao {
	void saveEntity(District bean);

	District findById(String id);

	void update(Update update, String id);
	
	Pagination getPage(String pcode, String level, int pageNo, int pageSize);

	void removeById(String id);

	List<District> find(String pcode, String level);

	List<District> find(String pcode);

	District findNext(String pcode);
}