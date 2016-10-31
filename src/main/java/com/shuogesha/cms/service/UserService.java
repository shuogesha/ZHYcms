package com.shuogesha.cms.service;

import com.shuogesha.cms.entity.Site;
import com.shuogesha.cms.entity.User;
import com.shuogesha.cms.web.mongo.Pagination;

public interface UserService {

	User findById(String uid);
	
	void saveAdmin(User bean,String username, String password, String email,String ip, String siteId,String[] roleIds);

	Pagination getPage(int pageNo, int pageSize);

	void removeByIds(String[] ids);
	
	void removeById(String id);

	void update(User bean,String password, String email,String[] roleIds,String[] siteIds, String uid);

	boolean usernameNotExist(String username);
}
