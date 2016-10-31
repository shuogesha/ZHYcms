package com.shuogesha.cms.filter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.shuogesha.cms.entity.Site;
import com.shuogesha.cms.service.SiteService;
import com.shuogesha.cms.web.CmsUtils;
import com.shuogesha.cms.web.CookieUtils;

public class FrontInterceptor implements HandlerInterceptor {
	
	public static final String SITE_PARAM = "site_param";
	public static final String SITE_COOKIE = "shuogesha_site_cookie";


	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object handler) throws Exception {
		Site site = getSite(request, response);
		CmsUtils.setSite(request, site);
		return true;
	}
	
	private Site getSite(HttpServletRequest request,
			HttpServletResponse response) {
		Site site = getByParams(request, response);
		if (site == null) {
			site = getByCookie(request);
		}
		if (site == null) {
			site = siteService.findMaster();
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
	public SiteService siteService;
}
