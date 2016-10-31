package com.shuogesha.cms.service.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.shuogesha.cms.dao.ContentDao;
import com.shuogesha.cms.entity.Content;
import com.shuogesha.cms.service.ContentService;
import com.shuogesha.cms.web.RequestUtils;
import com.shuogesha.cms.web.mongo.Pagination;


@Service
public class ContentServiceImpl implements ContentService{
	
	@Autowired
	private ContentDao dao;

	@Override
	public Content findById(String id) {
		return dao.findById(id);
	}
	
	@Override
	public Pagination getPage(String channelId,String siteId ,int pageNo, int pageSize) {
		return dao.getPage(channelId,siteId,pageNo,pageSize);
	}

	@Override
	public void save(Content bean) {
		bean.setDateline(RequestUtils.getNow());
		dao.saveEntity(bean);
	}

	@Override
	public void update(Content bean) {
		 Update update = new Update();
		 update.set("title", bean.getTitle());
		 update.set("source", bean.getSource());
		 update.set("content", bean.getContent());
		 update.set("dateline", RequestUtils.getNow());
		 update.set("status", bean.getStatus());
		 update.set("audit", bean.isAudit());
		 update.set("mark",bean.getMark());
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
	public void applyAudit(String id) {
		Update update = new Update();
		update.set("audit", true);
		dao.update(update, id);
	}

}
