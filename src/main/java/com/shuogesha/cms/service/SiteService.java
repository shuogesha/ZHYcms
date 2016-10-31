package com.shuogesha.cms.service;

import java.util.List;

import com.shuogesha.cms.entity.Site;
import com.shuogesha.cms.web.mongo.Pagination;

public interface SiteService {
	Pagination getPage(String siteId,int pageNo, int pageSize);

	Site findById(String id);

	void save(Site bean);

	void update(Site bean,String[] districtIds);

	void removeById(String id);
	
	void removeByIds(String[] ids);
	
	List<Site> findAll();

	Site findMaster();
	
	Site findByTplSolution(String path);
}