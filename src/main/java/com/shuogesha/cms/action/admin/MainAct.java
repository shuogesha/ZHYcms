package com.shuogesha.cms.action.admin;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shuogesha.cms.entity.User;
import com.shuogesha.cms.web.CmsUtils;

@Controller
public class MainAct {

	@RequestMapping(value = "/index.do")
	public String index(HttpServletRequest request,
			HttpServletResponse response, ModelMap model)
			throws UnsupportedEncodingException {
		model.put("user", CmsUtils.getUser(request));
		return "index";
	}
	
	@RequestMapping(value = "/welcome.do")
	public String welcome(HttpServletRequest request,
			HttpServletResponse response, ModelMap model)
			throws UnsupportedEncodingException {
		return "welcome";
	}
	
	@RequestMapping(value = "/top.do")
	public String top(HttpServletRequest request,
			HttpServletResponse response, ModelMap model)
			throws UnsupportedEncodingException {
		model.put("user", CmsUtils.getUser(request));
		model.put("site", CmsUtils.getSite(request));
		return "top";
	}
	

}
