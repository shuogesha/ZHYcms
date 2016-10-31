package com.shuogesha.cms.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.shuogesha.cms.dao.UserDao;
import com.shuogesha.cms.entity.Role;
import com.shuogesha.cms.entity.Site;
import com.shuogesha.cms.entity.UnifiedUser;
import com.shuogesha.cms.entity.User;
import com.shuogesha.cms.service.UnifiedUserService;
import com.shuogesha.cms.service.UserService;
import com.shuogesha.cms.web.mongo.Pagination;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao dao;

	@Autowired
	private UnifiedUserService unifiedUserService;

	@Override
	public User findById(String uid) {
		return dao.findById(uid);
	}

	@Override
	public void saveAdmin(User user,String username, String password, String email,String ip, String siteId,
			String[] roleIds) {
		// 添加統一会员信息
		UnifiedUser bean = unifiedUserService.save(username, password, email,
				ip);
		user.setUsername(username);
		user.setEmail(email);
		user.setId(bean.getId());
		Site site = new Site();
		site.setId(siteId);
		user.setSite(site);
		user.setUnifiedUser(bean);
		List<Role> roles = new ArrayList<>();
		if (roleIds != null) {
			for (int i = 0; i < roleIds.length; i++) {
				Role role = new Role();
				role.setId(roleIds[i]);
				roles.add(role);
			}
		}
		user.setRoles(roles);
		dao.saveEntity(user);
	}

	@Override
	public Pagination getPage(int pageNo, int pageSize) {
		return dao.getPage(pageNo, pageSize);
	}

	@Override
	public void removeByIds(String[] ids) {
		for (int i = 0, len = ids.length; i < len; i++) {
			removeById(ids[i]);
		}
	}

	@Override
	public void removeById(String id) {
		unifiedUserService.removeById(id);
		dao.removeById(id);
	}

	@Override
	public void update(User user,String password, String email, String[] roleIds,String[] siteIds, String uid) {
		if (StringUtils.isNotBlank(password)) {
			unifiedUserService.updatePassword(password, uid);
		}
		Update update = new Update();
		update.set("realname", user.getRealname());
		update.set("email", email);
		update.set("step", user.getStep());
		update.set("phone", user.getPhone());
		update.set("selfAdmin", user.isSelfAdmin());
		List<Map<String, Object>> roles= new ArrayList<>();
		if (roleIds != null) {
			for (int i = 0; i < roleIds.length; i++) {
				Map<String, Object> role = new HashMap<>();
				role.put("$id", roleIds[i]);
				role.put("$ref", "role");
				roles.add(role);
			}
		}
		update.set("roles", roles);
		List<Map<String, Object>> sites= new ArrayList<>();
		if (siteIds != null) {
			for (int i = 0; i < siteIds.length; i++) {
				Map<String, Object> site = new HashMap<>();
				site.put("$id", siteIds[i]);
				site.put("$ref", "site");
				sites.add(site);
			}
		}
		update.set("sites", sites);
		dao.update(update, uid);
	}

	@Override
	public boolean usernameNotExist(String username) {
		return dao.countByUsername(username) <= 0;
	}
}
