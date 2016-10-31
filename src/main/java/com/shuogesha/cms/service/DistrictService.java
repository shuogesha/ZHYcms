package com.shuogesha.cms.service;

import java.util.List;
import java.util.Map;

import com.shuogesha.cms.entity.District;
import com.shuogesha.cms.web.mongo.Pagination;

public interface DistrictService {
	Pagination getPage(String pcode, String level, int pageNo, int pageSize);

	District findById(String id);

	void save(District bean);

	void update(District bean);

	void removeById(String id);
	
	void removeByIds(String[] ids);
	
	List<District> find(String pcode, String level);

	List<District> getNodeList(String pcode);
}