package com.shuogesha.cms.action.admin;

import static com.shuogesha.cms.web.mongo.SimplePage.cpn;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.shuogesha.cms.entity.Site;
import com.shuogesha.cms.entity.UnifiedUser;
import com.shuogesha.cms.service.UnifiedUserService;
import com.shuogesha.cms.web.CmsUtils;
import com.shuogesha.cms.web.CookieUtils;
import com.shuogesha.cms.web.RequestUtils;
import com.shuogesha.cms.web.mongo.Pagination;

@Controller
@RequestMapping("/unifiedUser/")
public class UnifiedUserAct {

	@RequestMapping(value = "/v_list.do")
	public String v_list(Integer pageNo, HttpServletRequest request,
			HttpServletResponse response, ModelMap model)
			throws UnsupportedEncodingException {
		Pagination pagination = unifiedUserService.getPage(cpn(pageNo),
				CookieUtils.getPageSize(request));
		model.addAttribute("pagination", pagination);
		return "unifiedUser/v_list";
	}

	@RequestMapping(value = "/v_add.do")
	public String v_add(HttpServletRequest request,
			HttpServletResponse response, ModelMap model)
			throws UnsupportedEncodingException {

		return "unifiedUser/v_add";
	}

	@RequestMapping(value = "/o_save.do", method = RequestMethod.POST)
	public String o_save(UnifiedUser bean,
			HttpServletRequest request, HttpServletResponse response,
			ModelMap model) throws UnsupportedEncodingException {
		unifiedUserService.save(bean);
		return "redirect:v_list.do";
	}

	@RequestMapping(value = "/v_edit.do")
	public String v_edit(String id, HttpServletRequest request,
			HttpServletResponse response, ModelMap model)
			throws UnsupportedEncodingException {
		model.put("bean", unifiedUserService.findById(id));
		return "unifiedUser/v_edit";
	}

	@RequestMapping(value = "/o_update.do", method = RequestMethod.POST)
	public String o_update(UnifiedUser bean, HttpServletRequest request,
			HttpServletResponse response, ModelMap model)
			throws UnsupportedEncodingException {
		unifiedUserService.update(bean);
		return "redirect:v_list.do";
	}

	@RequestMapping(value = "/o_delete.do")
	public String o_delete(String[] ids, HttpServletRequest request,
			HttpServletResponse response, ModelMap model)
			throws UnsupportedEncodingException {
		unifiedUserService.removeByIds(ids);
		return "redirect:v_list.do";
	}
	
	@Autowired
	public UnifiedUserService unifiedUserService;
}
