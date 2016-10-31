package com.shuogesha.api.action;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shuogesha.api.version.ApiVersion;
import com.shuogesha.api.web.util.ApiUtils;
import com.shuogesha.cms.entity.UnifiedUser;
import com.shuogesha.cms.service.UnifiedUserService;
import com.shuogesha.cms.service.UnifiedUserTokenService;
import com.shuogesha.cms.web.RequestUtils;
import com.shuogesha.cms.web.exception.BadCredentialsException;
import com.shuogesha.cms.web.exception.UsernameNotFoundException;

@Controller
@RequestMapping("/{version}/")
public class ApiLoginAct{
	private static Logger log = LoggerFactory.getLogger(ApiLoginAct.class);
	
	@RequestMapping(value = "login")
	@ApiVersion(1)
	public @ResponseBody Object token_1(String username, String password,String appid, HttpServletRequest request,
			HttpServletResponse response, ModelMap model) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			UnifiedUser unifieduser =  unifiedUserService.login(username,password,RequestUtils.getIpAddr(request));
			if(unifieduser==null){
				map.put("error_code", "1");
				map.put("error", "用户名或密码错误");
			}else {
				map.put("uid", unifieduser.getId());
				map.put("token", unifiedUserTokenService.createToken(appid,unifieduser.getId()));
			}
			
			} catch (BadCredentialsException e) {
				map.put("error_code", "1");
				map.put("error", "用户名或密码错误");
			} catch (UsernameNotFoundException e) {
				map.put("error_code", "2");
				map.put("error", "用户名不存在");
		}
		return map;
	}
	
	@RequestMapping(value = "login")
	@ApiVersion(2)
	public @ResponseBody Object token_2(String username, String password, HttpServletRequest request,
			HttpServletResponse response, ModelMap model) throws UnsupportedEncodingException {
		Map<String, Object> map = new HashMap<String, Object>();
		return map;
	}
	
	/***
	 * 退出登录
	 * @param request
	 * @param tokenStr
	 * @return
	 */
	@RequestMapping(value = "logout")
	@ApiVersion(1)
	public @ResponseBody Object logout_1(HttpServletRequest request,String appid){
		Map<String, Object> map = new HashMap<>();
		try{
			unifiedUserService.logout(ApiUtils.getAppId(request),ApiUtils.getUnifiedUserId(request));
			map.put("status", true);
		}catch(Exception e){
			e.printStackTrace();
		}
		return map;
	}
	
	@Autowired
	private UnifiedUserService unifiedUserService;
	@Autowired
	private UnifiedUserTokenService unifiedUserTokenService;
}
