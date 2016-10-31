package com.shuogesha.cms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.shuogesha.cms.dao.DistrictDao;
import com.shuogesha.cms.entity.District;
import com.shuogesha.cms.service.DistrictService;
import com.shuogesha.cms.web.mongo.Pagination;


@Service
public class DistrictServiceImpl implements DistrictService{
	
	@Autowired
	private DistrictDao dao;

	@Override
	public District findById(String id) {
		return dao.findById(id);
	}
	
	@Override
	public Pagination getPage(String pcode, String level, int pageNo, int pageSize) {
		return dao.getPage(pcode,level,pageNo,pageSize);
	}

	@Override
	public void save(District bean) {
		 String pcode = bean.getPcode();
		 District district= dao.findNext(pcode);
		 if(district==null){
			 bean.setId(pcode+"01");
		 }else {
			 bean.setId(String.valueOf((Long.valueOf(district.getId())+1)));
		 }
		 bean.setEnd(1);
		 dao.saveEntity(bean);
		 //修改为结束
		 Update update = new Update();
		 update.set("end", 0);
		 dao.update(update, pcode);
		 
	}

	@Override
	public void update(District bean) {
		 Update update = new Update();
		 update.set("name", bean.getName());
		 dao.update(update, bean.getId());
	}

	@Override
	public void removeById(String id) {
		String pcode= dao.findById(id).getPcode();
		if(dao.findNext(pcode)==null){
			Update update = new Update();
			update.set("end", 1);
			dao.update(update, pcode);
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
	public List<District> find(String pcode, String level) {
		return dao.find(pcode,level);
	}

	@Override
	public List<District> getNodeList(String pcode) {
		return dao.find(pcode);
	}


}
