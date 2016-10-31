package com.shuogesha.cms.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.shuogesha.cms.entity.Authentication;
import com.shuogesha.cms.entity.UnifiedUser;
import com.shuogesha.cms.web.exception.BadCredentialsException;
import com.shuogesha.cms.web.exception.UsernameNotFoundException;
import com.shuogesha.cms.web.session.SessionProvider;

public interface AuthenticationService {
	/**
	 * 认证信息session key
	 */
	public static final String AUTH_KEY = "shuogesha_auth_key";

	public String retrieveUserIdFromSession(SessionProvider session,
			HttpServletRequest request);

	public void storeAuthIdToSession(SessionProvider session,
			HttpServletRequest request, HttpServletResponse response,
			String authId);

	/**
	 * 通过认证ID，获得认证信息。本方法会检查认证是否过期。
	 * 
	 * @param authId
	 *            认证ID
	 * @return 返回Authentication对象。如果authId不存在或已经过期，则返回null。
	 */
	public Authentication retrieve(String authId);

	/**
	 * 登录
	 * 
	 * @param username
	 *            用户名
	 * @param password
	 *            密码
	 * @param ip
	 *            登录IP
	 * @return 认证信息
	 * @throws UsernameNotFoundException
	 *             用户名没有找到
	 * @throws BadCredentialsException
	 *             错误的认证信息，比如密码错误
	 */
	public Authentication login(String username, String password, String ip,
			HttpServletRequest request, HttpServletResponse response,
			SessionProvider session) throws UsernameNotFoundException,
			BadCredentialsException ;
	
	/**
	 * 注册后登录
	 * @param unifiedUser
	 * @param ip
	 * 			登录IP
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	public Authentication activeLogin(UnifiedUser unifiedUser, String ip,
			HttpServletRequest request, HttpServletResponse response,
			SessionProvider session);

	/**
	 * 根据认证ID查找认证信息
	 * 
	 * @param id
	 *            认证ID
	 * @return
	 */
	public Authentication findById(String id);

	/**
	 * 保存认证信息
	 * 
	 * @param bean
	 * @return
	 */
	public Authentication save(Authentication bean);

	/**
	 * 删除认证信息
	 * 
	 * @param id
	 * @return
	 */
	public void removeById(String id);

	/**
	 * 删除认证信息
	 * 
	 * @param ids
	 * @return
	 */
	public void removeByIds(String[] ids);
}
