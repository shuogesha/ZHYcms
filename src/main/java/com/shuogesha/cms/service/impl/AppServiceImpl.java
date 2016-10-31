package com.shuogesha.cms.service.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.shuogesha.cms.dao.AppDao;
import com.shuogesha.cms.entity.App;
import com.shuogesha.cms.service.AppService;
import com.shuogesha.cms.web.mongo.Pagination;


@Service
public class AppServiceImpl implements AppService{
	
	@Autowired
	private AppDao dao;

	@Override
	public App findById(String id) {
		return dao.findById(id);
	}
	
	@Override
	public Pagination getPage(int pageNo, int pageSize) {
		return dao.getPage(pageNo,pageSize);
	}

	@Override
	public void save(App bean) {
		 dao.saveEntity(bean);
	}

	@Override
	public void update(App bean) {
		 Update update = new Update();
		 update.set("name", bean.getName());
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
	public long countById(String appid) {
		return dao.countById(appid);
	}

}
