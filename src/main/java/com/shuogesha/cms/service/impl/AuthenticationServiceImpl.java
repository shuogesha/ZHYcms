package com.shuogesha.cms.service.impl;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shuogesha.cms.dao.AuthenticationDao;
import com.shuogesha.cms.entity.Authentication;
import com.shuogesha.cms.entity.UnifiedUser;
import com.shuogesha.cms.service.AuthenticationService;
import com.shuogesha.cms.service.UnifiedUserService;
import com.shuogesha.cms.web.exception.BadCredentialsException;
import com.shuogesha.cms.web.exception.UsernameNotFoundException;
import com.shuogesha.cms.web.session.SessionProvider;
@Service
public class AuthenticationServiceImpl implements AuthenticationService{
	@Autowired
	private UnifiedUserService unifiedUserService;

	@Autowired
	private AuthenticationDao dao;
	
	public Authentication login(String username, String password, String ip,
			HttpServletRequest request, HttpServletResponse response,
			SessionProvider session) throws UsernameNotFoundException,
			BadCredentialsException {
		UnifiedUser unifiedUser = unifiedUserService.login(username, password, ip);
		Authentication auth = new Authentication();
		auth.setUid(unifiedUser.getId());
		auth.setUsername(unifiedUser.getUsername());
		auth.setEmail(unifiedUser.getEmail());
		auth.setPhone(unifiedUser.getPhone());
		auth.setLoginIp(ip);
		save(auth);
		session.setAttribute(request, response, AUTH_KEY, auth.getId());
		return auth;
	}
	
	public Authentication activeLogin(UnifiedUser unifiedUser, String ip,
			HttpServletRequest request, HttpServletResponse response,
			SessionProvider session) {
		unifiedUserService.activeLogin(unifiedUser, ip);
		Authentication auth = new Authentication();
		auth.setUid(unifiedUser.getId());
		auth.setUsername(unifiedUser.getUsername());
		auth.setEmail(unifiedUser.getEmail());
		auth.setPhone(unifiedUser.getPhone());
		auth.setLoginIp(ip);
		save(auth);
		session.setAttribute(request, response, AUTH_KEY, auth.getId());
		return auth;
	}

	public Authentication retrieve(String authId) {
		long current = System.currentTimeMillis();
		// 是否刷新数据库
		if (refreshTime < current) {
			refreshTime = getNextRefreshTime(current, interval);
			dao.deleteExpire(current - timeout);
		}
		Authentication auth = findById(authId);
		if (auth != null && auth.getUpdateTime() + timeout > current) {
			auth.setUpdateTime(current);
			return auth;
		} else {
			return null;
		}
	}

	public String retrieveUserIdFromSession(SessionProvider session,
			HttpServletRequest request) {
		String authId = (String) session.getAttribute(request, AUTH_KEY);
		if (authId == null) {
			return null;
		}
		Authentication auth = retrieve(authId);
		if (auth == null) {
			return null;
		}
		return auth.getUid();
	}

	public void storeAuthIdToSession(SessionProvider session,
			HttpServletRequest request, HttpServletResponse response,
			String authId) {
		session.setAttribute(request, response, AUTH_KEY, authId);
	}

	@Transactional(readOnly = true)
	public Authentication findById(String id) {
		Authentication entity = dao.findById(id);
		return entity;
	}

	public Authentication save(Authentication bean) {
		bean.setId(StringUtils.remove(UUID.randomUUID().toString(), '-'));
		bean.setLoginTime(System.currentTimeMillis());
		bean.setUpdateTime(System.currentTimeMillis());
		dao.saveEntity(bean);
		return bean;
	}

	public void removeById(String id) {
		dao.removeById(id);
	}

	public void removeByIds(String[] ids) {
		for (int i = 0, len = ids.length; i < len; i++) {
			removeById(ids[i]);
		}
	}

	// 过期时间
	private int timeout = 30 * 60 * 1000; // 30分钟

	// 间隔时间
	private int interval = 4 * 60 * 60 * 1000; // 4小时

	// 刷新时间。
	private long refreshTime = getNextRefreshTime(System.currentTimeMillis(),
			this.interval);
	
	/**
	 * 设置认证过期时间。默认30分钟。
	 * 
	 * @param timeout
	 *            单位分钟
	 */
	public void setTimeout(int timeout) {
		this.timeout = timeout * 60 * 1000;
	}

	/**
	 * 设置刷新数据库时间。默认4小时。
	 * 
	 * @param interval
	 *            单位分钟
	 */
	public void setInterval(int interval) {
		this.interval = interval * 60 * 1000;
		this.refreshTime = getNextRefreshTime(System.currentTimeMillis(),
				this.interval);
	}

	/**
	 * 获得下一个刷新时间。
	 * 
	 * 
	 * 
	 * @param current
	 * @param interval
	 * @return 随机间隔时间
	 */
	private long getNextRefreshTime(long current, int interval) {
		return current + interval;
		// 为了防止多个应用同时刷新，间隔时间=interval+RandomUtils.nextInt(interval/4);
		// return current + interval + RandomUtils.nextInt(interval / 4);
	}
}
