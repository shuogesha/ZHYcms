package com.shuogesha.api.web.util;

import javax.servlet.http.HttpServletRequest;

import com.shuogesha.cms.entity.App;
import com.shuogesha.cms.entity.Site;
import com.shuogesha.cms.entity.UnifiedUser;

public class ApiUtils {

	/**
	 * appKEY
	 */
	public static final String APP_KEY = "_c_m_s_api_app_key";
	
	/**
	 * 会员KEY
	 */
	public static final String UNIFIEDUSER_KEY = "_c_m_s_api_unifiedUser_key";
	
	/**
	 * 站点KEY
	 */
	public static final String SITE_KEY = "_c_m_s_api_site_key";
	
	
	/**
	 * 设置App
	 * 
	 * @param request
	 * @param site
	 */
	public static void setApp(HttpServletRequest request, App app) {
		request.setAttribute(APP_KEY, app);
	}

	/**
	 * 获得Appid
	 * 
	 * @param request
	 * @return
	 */
	public static String getAppId(HttpServletRequest request) {
		if(getApp(request)==null){
			return "";
		}
		return getApp(request).getId();
	}
	/**
	 * 获取当前请求来源
	 * @param request
	 * @return
	 */
	public static App getApp(HttpServletRequest request) {
		return (App) request.getAttribute(APP_KEY);
	}
	
	/**
	 * 设置会员
	 * 
	 * @param request
	 * @param site
	 */
	public static void setUnifiedUser(HttpServletRequest request, UnifiedUser unifiedUser) {
		request.setAttribute(UNIFIEDUSER_KEY, unifiedUser);
	}

	/**
	 * 获得会员ID
	 * 
	 * @param request
	 * @return
	 */
	public static String getUnifiedUserId(HttpServletRequest request) {
		if(getUnifiedUser(request)==null){
			return "";
		}
		return getUnifiedUser(request).getId();
	}
	/**
	 * 活动当前会员
	 * @param request
	 * @return
	 */
	public static UnifiedUser getUnifiedUser(HttpServletRequest request) {
		return (UnifiedUser) request.getAttribute(UNIFIEDUSER_KEY);
	}
	
	/**
	 * 获得站点
	 * 
	 * @param request
	 * @return
	 */
	public static Site getSite(HttpServletRequest request) {
		return (Site) request.getAttribute(SITE_KEY);
	}

	/**
	 * 设置站点
	 * 
	 * @param request
	 * @param site
	 */
	public static void setSite(HttpServletRequest request, Site site) {
		request.setAttribute(SITE_KEY, site);
	}

	/**
	 * 获得站点ID
	 * 
	 * @param request
	 * @return
	 */
	public static String getSiteId(HttpServletRequest request) {
		return getSite(request).getId();
	}
}
