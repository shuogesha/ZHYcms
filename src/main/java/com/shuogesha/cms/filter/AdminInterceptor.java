package com.shuogesha.cms.filter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UrlPathHelper;

import com.shuogesha.cms.entity.Site;
import com.shuogesha.cms.entity.User;
import com.shuogesha.cms.service.AuthenticationService;
import com.shuogesha.cms.service.RoleService;
import com.shuogesha.cms.service.SiteService;
import com.shuogesha.cms.service.UserService;
import com.shuogesha.cms.web.CmsUtils;
import com.shuogesha.cms.web.CookieUtils;
import com.shuogesha.cms.web.session.SessionProvider;

public class AdminInterceptor implements HandlerInterceptor {
	public static final String SITE_PARAM = "site_param";
	public static final String SITE_COOKIE = "shuogesha_site_cookie";
	public static final String PERMISSION_KEY = "_permission_key";

	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		User user = CmsUtils.getUser(request);
		if (auth && (user != null) && modelAndView != null && modelAndView.getModelMap() != null
				&& modelAndView.getViewName() != null && !modelAndView.getViewName().startsWith("redirect:")) {
			modelAndView.getModelMap().addAttribute(PERMISSION_KEY, roleService.getPerms(user.getRoles()));
		}
	}

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		
		String uri = getURI(request);
		 //不在验证的范围内
		if (exclude(uri)) {
			return true;
		}
		User user = null;
		String userId = authenticationService.retrieveUserIdFromSession(
				session, request);
		if (userId != null) {
			user = userService.findById(userId);
			CmsUtils.setUser(request, user);
		} 
		if (user == null) {
			java.io.PrintWriter out = response.getWriter();
			out.println("<html>");
			out.println("<script>");
			out.println("window.open ('" + getLoginUrl(request) + "','_top')");
			out.println("</script>");
			out.println("</html>");
			return false;
		}
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
			site = CmsUtils.getUser(request).getSite();
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

	private static String getURI(HttpServletRequest request)
			throws IllegalStateException {
		UrlPathHelper helper = new UrlPathHelper();
		String uri = helper.getOriginatingRequestUri(request);
		String ctxPath = helper.getOriginatingContextPath(request);
		int start = 0, i = 0, count = 1;
		if (!StringUtils.isBlank(ctxPath)) {
			count++;
		}
		while (i < count && start != -1) {
			start = uri.indexOf('/', start + 1);
			i++;
		}
		if (start <= 0) {
			throw new IllegalStateException(
					"admin access path not like '/admin/...' pattern: "
							+ uri);
		}
		return uri.substring(start);
	}
	
	private boolean exclude(String uri) {
		if (excludeUrls != null) {
			for (String exc : excludeUrls) {
				if (exc.equals(uri)) {
					return true;
				}
			}
		}
		return false;
	}
	
	private String getLoginUrl(HttpServletRequest request) {
		StringBuilder buff = new StringBuilder();
		if (loginUrl.startsWith("/")) {
			String ctx = request.getContextPath();
			if (!StringUtils.isBlank(ctx)) {
				buff.append(ctx);
			}
		}
		buff.append(loginUrl).append("?");
		buff.append("returnUrl").append("=").append(returnUrl);
		if (!StringUtils.isBlank(processUrl)) {
			buff.append("&").append("processUrl").append("=").append(
					getProcessUrl(request));
		}
		return buff.toString();
	}

	private String getProcessUrl(HttpServletRequest request) {
		StringBuilder buff = new StringBuilder();
		if (loginUrl.startsWith("/")) {
			String ctx = request.getContextPath();
			if (!StringUtils.isBlank(ctx)) {
				buff.append(ctx);
			}
		}
		buff.append(processUrl);
		return buff.toString();
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
	@Autowired
	public UserService userService;
	@Autowired
	public RoleService roleService;
	@Autowired
	public AuthenticationService authenticationService;
	@Autowired
	private SessionProvider session;
	
	public static String admin;
	
	private boolean auth = true;
	private String[] excludeUrls;
	private String loginUrl;
	private String processUrl;
	private String returnUrl;
	public boolean isAuth() {
		return auth;
	}

	public void setAuth(boolean auth) {
		this.auth = auth;
	}

	public String[] getExcludeUrls() {
		return excludeUrls;
	}

	public void setExcludeUrls(String[] excludeUrls) {
		this.excludeUrls = excludeUrls;
	}

	public String getLoginUrl() {
		return loginUrl;
	}

	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}

	public String getProcessUrl() {
		return processUrl;
	}

	public void setProcessUrl(String processUrl) {
		this.processUrl = processUrl;
	}

	public String getReturnUrl() {
		return returnUrl;
	}

	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}

	public String getAdmin() {
		return admin;
	}

	public void setAdmin(String admin) {
		this.admin = admin;
	}
	
	
}
