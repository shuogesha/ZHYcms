package com.shuogesha.cms.dao;

import java.util.Date;

import com.shuogesha.cms.entity.Authentication;


public interface AuthenticationDao {

	void removeById(String id);

	void saveEntity(Authentication bean);

	Authentication findById(String id);

	void deleteExpire(long date);


}
