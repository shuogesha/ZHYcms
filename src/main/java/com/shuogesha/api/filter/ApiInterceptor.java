package com.shuogesha.api.filter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.shuogesha.api.web.util.ApiUtils;
import com.shuogesha.cms.entity.App;
import com.shuogesha.cms.entity.Site;
import com.shuogesha.cms.service.AppService;
import com.shuogesha.cms.service.SiteService;
import com.shuogesha.cms.web.CmsUtils;
import com.shuogesha.cms.web.CookieUtils;
import com.shuogesha.cms.web.ResponseUtils;
/**
 * 拦截全部请求，需要带appid
 * @author zhaohy
 *
 */
public class ApiInterceptor extends HandlerInterceptorAdapter {
	public static final String SITE_PARAM = "api_site_param";
	public static final String SITE_ID = "api_site_id";
	public static final String SITE_COOKIE = "api_shuogesha_site_cookie";

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//测试默认关闭
//		if (debug) {
//			return true;
//		}
		// 验证appid 是否有效
		String appid = request.getParameter("appid");
		if(StringUtils.isBlank(appid)||appService.countById(appid)<=0){
			JSONObject json = new JSONObject();
			json.put("error_code", "10000");
			json.put("error", "参数错误");
			ResponseUtils.renderJson(response, json.toString());
			return false;
		}
		App app =  appService.findById(appid);
		ApiUtils.setApp(request, app);
		Site site = getSite(request, response);
		ApiUtils.setSite(request, site);
		return true;
	}
	
	private Site getSite(HttpServletRequest request,
			HttpServletResponse response) {
		Site site = getByParams(request, response);
		if (site == null) {
			site = getByCookie(request);
		}
		if (site == null) {
			throw new RuntimeException("no site!");
		} else {
			return site;
		}
	}
	
	private Site getByParams(HttpServletRequest request,
			HttpServletResponse response) {
		String p = request.getParameter(SITE_PARAM);
		if (!StringUtils.isBlank(p)) {
			try {
				Site site = siteService.findByTplSolution(p);
				if (site != null) {
					// 若使用参数选择站点，则应该把站点保存至cookie中才好。
					CookieUtils.addCookie(request, response, SITE_COOKIE, site
							.getId().toString(), null, null);
					return site;
				}
			} catch (NumberFormatException e) {
				 
			}
		}
		p = request.getParameter(SITE_ID);
		if (!StringUtils.isBlank(p)) {
			try {
				Site site = siteService.findById(p);
				if (site != null) {
					// 若使用参数选择站点，则应该把站点保存至cookie中才好。
					CookieUtils.addCookie(request, response, SITE_COOKIE, site
							.getId().toString(), null, null);
					return site;
				}
			} catch (NumberFormatException e) {
				 
			}
		}
		return null;
	}
	
	private Site getByCookie(HttpServletRequest request) {
		Cookie cookie = CookieUtils.getCookie(request, SITE_COOKIE);
		if (cookie != null) {
			String v = cookie.getValue();
			if (!StringUtils.isBlank(v)) {
				try {
					return siteService.findById(v);
				} catch (NumberFormatException e) {
					
				}
			}
		}
		return null;
	}
	
	@Autowired
	private AppService appService;
	@Autowired
	private SiteService siteService;
	private boolean debug;

	public boolean isDebug() {
		return debug;
	}

	public void setDebug(boolean debug) {
		this.debug = debug;
	}
}
