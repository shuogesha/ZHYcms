package com.shuogesha.cms.action.admin;

import static com.shuogesha.cms.service.AuthenticationService.AUTH_KEY;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.shuogesha.cms.entity.Authentication;
import com.shuogesha.cms.entity.UnifiedUser;
import com.shuogesha.cms.service.AuthenticationService;
import com.shuogesha.cms.service.UnifiedUserService;
import com.shuogesha.cms.web.RequestUtils;
import com.shuogesha.cms.web.exception.BadCredentialsException;
import com.shuogesha.cms.web.exception.UsernameNotFoundException;
import com.shuogesha.cms.web.session.SessionProvider;

@Controller
public class LoginAct {

	@RequestMapping(value = "/login.do", method = RequestMethod.GET)
	public String input(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String authId = (String) session.getAttribute(request, AUTH_KEY);
		if (authId != null) {
			// 存在认证ID
			Authentication auth = authenticationService.retrieve(authId);
			// 存在认证信息，且未过期
			if (auth != null) {
				return "index";
			}
		}
		return "login";
	}
	
	@RequestMapping(value = "/login.do", method = RequestMethod.POST)
	public String submit(String username, String password, String captcha, 
			HttpServletRequest request, HttpServletResponse response,
			ModelMap model) {
		if(StringUtils.isNotBlank(username)&&StringUtils.isNotBlank(password)){
			try {
				String ip = RequestUtils.getIpAddr(request);
				Authentication auth = authenticationService.login(username, password, ip,
						request, response, session);
				// 是否需要在这里加上登录次数的更新？按正常的方式，应该在process里面处理的，不过这里处理也没大问题。
				unifiedUserService.updateLoginInfo(auth.getUid(), ip);
				UnifiedUser unifieduser = unifiedUserService.findById(auth.getUid());
				if (unifieduser.isDisabled()) {
					// 如果已经禁用，则退出登录。
					authenticationService.removeById(auth.getId());
					session.logout(request, response);
				}
				if(unifieduser!=null){
					//登录成功返回后台首页
					return "redirect:index.do";
				}else{
					return "redirect:login.do";
				}
			} catch (UsernameNotFoundException e) {
				 
			} catch (BadCredentialsException e) {
			  
			}
		}
		// 登录失败
		return "login";
	}
	
	@RequestMapping(value = "/logout.do")
	public String logout(HttpServletRequest request,
			HttpServletResponse response) {
		String authId = (String) session.getAttribute(request, AUTH_KEY);
		if (authId != null) {
			authenticationService.removeById(authId);
			session.logout(request, response);
		}
		String processUrl = RequestUtils.getQueryParam(request, "processUrl");
		String returnUrl = RequestUtils.getQueryParam(request, "returnUrl");
		String view = getView(processUrl, returnUrl, authId);
		if (view != null) {
			return view;
		} else {
			return "redirect:login.do";
		}
	}
	
	/**
	 * 获得地址
	 * 
	 * @param processUrl
	 * @param returnUrl
	 * @param authId
	 * @param defaultUrl
	 * @return
	 */
	private String getView(String processUrl, String returnUrl, String authId) {
		if (!StringUtils.isBlank(processUrl)) {
			StringBuilder sb = new StringBuilder("redirect:");
			sb.append(processUrl).append("?").append(AUTH_KEY).append("=")
					.append(authId);
			if (!StringUtils.isBlank(returnUrl)) {
				sb.append("&").append("returnUrl").append("=").append(returnUrl);
			}
			return sb.toString();
		} else if (!StringUtils.isBlank(returnUrl)) {
			StringBuilder sb = new StringBuilder("redirect:");
			sb.append(returnUrl);
			return sb.toString();
		} else {
			return null;
		}
	}
	
	@Autowired
	private AuthenticationService authenticationService;
	@Autowired
	private UnifiedUserService unifiedUserService;
	@Autowired
	private SessionProvider session;

}
