package com.shuogesha.cms.service;

import com.shuogesha.cms.entity.UnifiedUser;
import com.shuogesha.cms.web.exception.BadCredentialsException;
import com.shuogesha.cms.web.exception.UsernameNotFoundException;
import com.shuogesha.cms.web.mongo.Pagination;

public interface UnifiedUserService {
	UnifiedUser login(String username, String password, String ip)
			throws UsernameNotFoundException, BadCredentialsException,
			BadCredentialsException;

	public UnifiedUser findByUsername(String username);

	void activeLogin(UnifiedUser unifiedUser, String ip);

	void updateLoginInfo(String uid, String ip);

	UnifiedUser save(String username, String password, String email, String ip);

	void updatePassword(String password, String uid);

	void logout(String appid, String uid);

	Pagination getPage(int pageNo, int pageSize);

	UnifiedUser findById(String id);

	void save(UnifiedUser bean);

	void update(UnifiedUser bean);

	void removeById(String id);

	void removeByIds(String[] ids);
}