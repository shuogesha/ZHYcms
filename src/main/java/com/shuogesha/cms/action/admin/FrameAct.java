package com.shuogesha.cms.action.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shuogesha.cms.web.CmsUtils;

@Controller
public class FrameAct {

	@RequestMapping("/config_main.do")
	public String configMain(ModelMap model) {
		return "frame/config_main";
	}
	
	@RequestMapping("/config_left.do")
	public String configLeft(ModelMap model) {
		return "frame/config_left";
	}
	

	@RequestMapping("/config_right.do")
	public String configRight(ModelMap model) {
		return "frame/config_right";
	}
	
	@RequestMapping("/user_main.do")
	public String userMain(ModelMap model) {
		return "frame/user_main";
	}

	@RequestMapping("/user_left.do")
	public String userLeft(ModelMap model) {
		return "frame/user_left";
	}
	

	@RequestMapping("/user_right.do")
	public String userRight(ModelMap model) {
		return "frame/user_right";
	}
	
	@RequestMapping("/channel_main.do")
	public String channelMain(ModelMap model) {
		return "frame/channel_main";
	}
	
	@RequestMapping("/channel_left.do")
	public String channelLeft(HttpServletRequest request,
			HttpServletResponse response,ModelMap model) {
		model.put("user", CmsUtils.getUser(request));
		model.put("site", CmsUtils.getSite(request));
		return "frame/channel_left";
	}
	

	@RequestMapping("/channel_right.do")
	public String channelRight(ModelMap model) {
		return "frame/channel_right";
	}
	
	
	@RequestMapping("/content_main.do")
	public String contentMain(ModelMap model) {
		return "frame/content_main";
	}
	
	@RequestMapping("/content_left.do")
	public String contentLeft(HttpServletRequest request,
			HttpServletResponse response,ModelMap model) {
		model.put("user", CmsUtils.getUser(request));
		model.put("site", CmsUtils.getSite(request));
		return "frame/content_left";
	}
	

	@RequestMapping("/content_right.do")
	public String contentRight(ModelMap model) {
		return "frame/content_right";
	}
	
	@RequestMapping("/work_main.do")
	public String workMain(ModelMap model) {
		return "frame/work_main";
	}

	@RequestMapping("/work_left.do")
	public String workLeft(ModelMap model) {
		return "frame/work_left";
	}
	

	@RequestMapping("/work_right.do")
	public String workRight(ModelMap model) {
		return "frame/work_right";
	}
	
	
}
