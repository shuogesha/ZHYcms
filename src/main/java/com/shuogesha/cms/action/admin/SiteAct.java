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
import com.shuogesha.cms.entity.Site;
import com.shuogesha.cms.service.DistrictService;
import com.shuogesha.cms.service.SiteService;
import com.shuogesha.cms.web.CmsUtils;
import com.shuogesha.cms.web.CookieUtils;
import com.shuogesha.cms.web.RequestUtils;
import com.shuogesha.cms.web.mongo.Pagination;

@Controller
@RequestMapping("/site/")
public class SiteAct {

	@RequestMapping(value = "/v_list.do")
	public String v_list(Integer pageNo, HttpServletRequest request,
			HttpServletResponse response, ModelMap model)
			throws UnsupportedEncodingException {
		Pagination pagination = siteService.getPage(CmsUtils.getSiteId(request),cpn(pageNo),
				CookieUtils.getPageSize(request));
		model.addAttribute("pagination", pagination);
		return "site/v_list";
	}

	@RequestMapping(value = "/v_add.do")
	public String v_add(HttpServletRequest request,
			HttpServletResponse response, ModelMap model)
			throws UnsupportedEncodingException {

		return "site/v_add";
	}

	@RequestMapping(value = "/o_save.do", method = RequestMethod.POST)
	public String o_save(Site bean,
			HttpServletRequest request, HttpServletResponse response,
			ModelMap model) throws UnsupportedEncodingException {
		Site site = CmsUtils.getSite(request);
		//所有均为添加子站点
		bean.setParent(site);
		siteService.save(bean);
		return "redirect:v_list.do";
	}

	@RequestMapping(value = "/v_edit.do")
	public String v_edit(String id, HttpServletRequest request,
			HttpServletResponse response, ModelMap model)
			throws UnsupportedEncodingException {
		model.put("bean", siteService.findById(id));
		model.put("districtList", districtService.find("51", "2"));
		return "site/v_edit";
	}

	@RequestMapping(value = "/o_update.do", method = RequestMethod.POST)
	public String o_update(Site bean, String[] districtIds,HttpServletRequest request,
			HttpServletResponse response, ModelMap model)
			throws UnsupportedEncodingException {
		siteService.update(bean,districtIds);
		return "redirect:v_list.do";
	}

	@RequestMapping(value = "/o_delete.do")
	public String o_delete(String[] ids, HttpServletRequest request,
			HttpServletResponse response, ModelMap model)
			throws UnsupportedEncodingException {
		siteService.removeByIds(ids);
		return "redirect:v_list.do";
	}
	
	@RequestMapping(value = "/v_config.do")
	public String v_config(HttpServletRequest request,
			HttpServletResponse response, ModelMap model)
			throws UnsupportedEncodingException {
		Site site =CmsUtils.getSite(request);
		model.put("bean", site);
		return "site/v_config";
	}
	
	@RequestMapping(value = "/o_config_update.do", method = RequestMethod.POST)
	public String o_config_update(Site bean,HttpServletRequest request,
			HttpServletResponse response, ModelMap model)
			throws UnsupportedEncodingException {
		siteService.update(bean,null);
		return "site/v_config";
	}
	
	@Autowired
	public SiteService siteService;
	@Autowired
	public DistrictService districtService;
}
