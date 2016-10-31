package com.shuogesha.cms.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.shuogesha.cms.dao.SiteDao;
import com.shuogesha.cms.entity.Site;
import com.shuogesha.cms.service.SiteService;
import com.shuogesha.cms.web.mongo.Pagination;


@Service
public class SiteServiceImpl implements SiteService{
	
	@Autowired
	private SiteDao dao;

	@Override
	public Site findById(String id) {
		return dao.findById(id);
	}
	
	@Override
	public Pagination getPage(String siteId,int pageNo, int pageSize) {
		return dao.getPage(siteId,pageNo,pageSize);
	}

	@Override
	public void save(Site bean) {
		 dao.saveEntity(bean);
	}

	@Override
	public void update(Site bean,String[] districtIds) {
		 Update update = new Update();
		 update.set("name", bean.getName());
		 update.set("tplSolution", bean.getTplSolution());
		 List<Map<String, Object>> districts= new ArrayList<>();
			if (districtIds != null) {
				for (int i = 0; i < districtIds.length; i++) {
					Map<String, Object> district = new HashMap<>();
					district.put("$id", districtIds[i]);
					district.put("$ref", "district");
					districts.add(district);
				}
			}
			update.set("districts", districts);
		 dao.update(update, bean.getId());
	}

	@Override
	public void removeById(String id) {
		dao.removeById(id);
	}

	@Override
	public void removeByIds(String[] ids) {
		for (int i = 0, len = ids.length; i < len; i++) {
			removeById(ids[i]);
		}
	}
	
	@Override
	public List<Site> findAll() {
		return dao.findAll();
	}

	@Override
	public Site findMaster() {
		return dao.findMaster();
	}

	@Override
	public Site findByTplSolution(String path) {
		return dao.findByTplSolution(path);
	}


}
