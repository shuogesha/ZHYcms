package com.shuogesha.cms.action.admin;

import static com.shuogesha.cms.web.mongo.SimplePage.cpn;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.shuogesha.cms.entity.Role;
import com.shuogesha.cms.entity.Site;
import com.shuogesha.cms.entity.User;
import com.shuogesha.cms.service.RoleService;
import com.shuogesha.cms.service.SiteService;
import com.shuogesha.cms.service.UserService;
import com.shuogesha.cms.web.CmsUtils;
import com.shuogesha.cms.web.CookieUtils;
import com.shuogesha.cms.web.RequestUtils;
import com.shuogesha.cms.web.ResponseUtils;
import com.shuogesha.cms.web.mongo.Pagination;

@Controller
@RequestMapping("/user/")
public class UserAct {

	@RequestMapping(value = "/v_list.do")
	public String v_list(Integer pageNo, HttpServletRequest request,
			HttpServletResponse response, ModelMap model)
			throws UnsupportedEncodingException {
		Pagination pagination = userService.getPage(cpn(pageNo),
				CookieUtils.getPageSize(request));
		model.addAttribute("pagination", pagination);
		return "user/v_list";
	}

	@RequestMapping(value = "/v_add.do")
	public String v_add(HttpServletRequest request,
			HttpServletResponse response, ModelMap model)
			throws UnsupportedEncodingException {
		List<Role> roleList = roleService.getList();
		List<Site> siteList = siteService.findAll();
		model.put("roleList", roleList);
		model.put("siteList", siteList);
		return "user/v_add";
	}

	@RequestMapping(value = "/o_save.do", method = RequestMethod.POST)
	public String o_save(User bean,String username, String password, String email,String[] roleIds,String siteId,
			HttpServletRequest request, HttpServletResponse response,
			ModelMap model) throws UnsupportedEncodingException {
		Site site = CmsUtils.getSite(request);
		String ip = RequestUtils.getIpAddr(request);
		userService.saveAdmin(bean,username,password,email,ip,
				siteId,roleIds);
		return "redirect:v_list.do";
	}

	@RequestMapping(value = "/v_edit.do")
	public String v_edit(String id, HttpServletRequest request,
			HttpServletResponse response, ModelMap model)
			throws UnsupportedEncodingException {
		model.put("bean", userService.findById(id));
		List<Role> roleList = roleService.getList();
		List<Site> siteList = siteService.findAll();
		model.put("roleList", roleList);
		model.put("siteList", siteList);
		return "user/v_edit";
	}

	@RequestMapping(value = "/o_update.do", method = RequestMethod.POST)
	public String o_update(User bean, String password, String email,String[] roleIds,String[] siteIds,String id, HttpServletRequest request,
			HttpServletResponse response, ModelMap model)
			throws UnsupportedEncodingException {
		userService.update(bean,password,email,roleIds,siteIds, id);
		return "redirect:v_list.do";
	}

	@RequestMapping(value = "/o_delete.do")
	public String o_delete(String[] ids, HttpServletRequest request,
			HttpServletResponse response, ModelMap model)
			throws UnsupportedEncodingException {
		userService.removeByIds(ids);
		return "redirect:v_list.do";
	}
	@RequestMapping(value = "/v_check_username.do")
	public void checkUsername(HttpServletRequest request, HttpServletResponse response) {
		String username=RequestUtils.getQueryParam(request,"username");
		String pass;
		if (StringUtils.isBlank(username)) {
			pass = "false";
		} else {
			pass = userService.usernameNotExist(username) ? "true" : "false";
		}
		ResponseUtils.renderJson(response, pass);
	}
	
	@RequestMapping(value = "/v_chpwd.do")
	public String v_chpwd(HttpServletRequest request,
			HttpServletResponse response, ModelMap model){
			
		return "user/v_chpwd";
	}
	
	@RequestMapping(value = "/o_chpwd.do")
	public String o_chpwd(String oldPwd,String newPwd, HttpServletRequest request,
			HttpServletResponse response, ModelMap model){
		
		return "redirect:v_chpwd.do";
	}
	@Autowired
	public UserService userService;
	@Autowired
	public RoleService roleService;
	@Autowired
	public SiteService siteService;
}
