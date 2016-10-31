package com.shuogesha.cms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.shuogesha.cms.dao.ChannelDao;
import com.shuogesha.cms.entity.Channel;
import com.shuogesha.cms.service.ChannelService;
import com.shuogesha.cms.web.CmsUtils;
import com.shuogesha.cms.web.mongo.Pagination;

@Service
public class ChannelServiceImpl implements ChannelService {

	@Autowired
	private ChannelDao dao;

	@Override
	public Channel findById(String id) {
		return dao.findById(id);
	}

	@Override
	public Pagination getPage(String pid, String siteId, int pageNo,
			int pageSize) {
		return dao.getPage(pid, siteId, pageNo, pageSize);
	}

	@Override
	public void save(Channel bean) {
		dao.saveEntity(bean);
		if (bean.getParent() != null) {
			countLevel(bean.getParent().getId(), true);
		}
	}

	private void countLevel(String id, boolean b) {
		Channel bean = dao.findById(id);
		Update update = new Update();
		if (b) {
			update.set("level", bean.getLevel() + 1);
		} else {
			update.set("level", bean.getLevel() - 1);
		}
		dao.update(update, id);
	}

	@Override
	public void update(Channel bean) {
		Update update = new Update();
		update.set("name", bean.getName());
		update.set("type", bean.getType());
		update.set("sort", bean.getSort());
		update.set("content", bean.getContent());
		dao.update(update, bean.getId());
	}

	@Override
	public void removeById(String id) {
		Channel bean = dao.findById(id);
		if (bean.getParent() != null) {
			countLevel(bean.getParent().getId(), false);
		}
		dao.removeById(id);
	}

	@Override
	public void removeByIds(String[] ids) {
		for (int i = 0, len = ids.length; i < len; i++) {
			removeById(ids[i]);
		}
	}

	@Override
	public List<Channel> getChildListForTag(String parentId,
			boolean hasContentOnly,String siteId) {
		return dao.getChildList(parentId, hasContentOnly,siteId);
	}

	@Override
	public List<Channel> getTopListForTag(boolean hasContentOnly,String siteId) {
		return dao.getChildList(null, hasContentOnly,siteId);
	}

	@Override
	public List<Channel> getNodeList(String pid, String siteId) {
		return dao.getNodeList(pid, siteId);
	}

}
