package com.shuogesha.cms.action.admin;

import static com.shuogesha.cms.web.mongo.SimplePage.cpn;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
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
import com.shuogesha.cms.service.RoleService;
import com.shuogesha.cms.web.CmsUtils;
import com.shuogesha.cms.web.CookieUtils;
import com.shuogesha.cms.web.mongo.Pagination;

@Controller
@RequestMapping("/role/")
public class RoleAct {

	@RequestMapping(value = "/v_list.do")
	public String v_list(Integer pageNo, HttpServletRequest request,
			HttpServletResponse response, ModelMap model)
			throws UnsupportedEncodingException {
		Pagination pagination = roleService.getPage(cpn(pageNo),
				CookieUtils.getPageSize(request));
		model.addAttribute("pagination", pagination);
		return "role/v_list";
	}

	@RequestMapping(value = "/v_add.do")
	public String v_add(HttpServletRequest request,
			HttpServletResponse response, ModelMap model)
			throws UnsupportedEncodingException {

		return "role/v_add";
	}

	@RequestMapping(value = "/o_save.do", method = RequestMethod.POST)
	public String o_save(Role bean,String[] perms,
			HttpServletRequest request, HttpServletResponse response,
			ModelMap model) throws UnsupportedEncodingException {
		Site site = CmsUtils.getSite(request);
		bean.setPerms(splitPerms(perms));
		roleService.save(bean);
		return "redirect:v_list.do";
	}

	@RequestMapping(value = "/v_edit.do")
	public String v_edit(String id, HttpServletRequest request,
			HttpServletResponse response, ModelMap model)
			throws UnsupportedEncodingException {
		model.put("bean", roleService.findById(id));
		return "role/v_edit";
	}

	@RequestMapping(value = "/o_update.do", method = RequestMethod.POST)
	public String o_update(Role bean,String[] perms, HttpServletRequest request,
			HttpServletResponse response, ModelMap model)
			throws UnsupportedEncodingException {
		bean.setPerms(splitPerms(perms));
		roleService.update(bean);
		return "redirect:v_list.do";
	}

	@RequestMapping(value = "/o_delete.do")
	public String o_delete(String[] ids, HttpServletRequest request,
			HttpServletResponse response, ModelMap model)
			throws UnsupportedEncodingException {
		roleService.removeByIds(ids);
		return "redirect:v_list.do";
	}
	
	private List<String> splitPerms(String[] perms) {
		List<String> set = new ArrayList<String>();
		if (perms != null) {
			for (String perm : perms) {
				for (String p : StringUtils.split(perm, ',')) {
					if (!StringUtils.isBlank(p)) {
						set.add(p);
					}
				}
			}
		}
		return set;
	}
	
	@Autowired
	public RoleService roleService;
}
