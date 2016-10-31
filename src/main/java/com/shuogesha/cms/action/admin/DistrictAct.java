package com.shuogesha.cms.action.admin;

import static com.shuogesha.cms.web.mongo.SimplePage.cpn;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.shuogesha.cms.entity.Channel;
import com.shuogesha.cms.entity.Site;
import com.shuogesha.cms.entity.District;
import com.shuogesha.cms.service.DistrictService;
import com.shuogesha.cms.web.CmsUtils;
import com.shuogesha.cms.web.CookieUtils;
import com.shuogesha.cms.web.RequestUtils;
import com.shuogesha.cms.web.ResponseUtils;
import com.shuogesha.cms.web.mongo.Pagination;

@Controller
@RequestMapping("/district/")
public class DistrictAct {

	@RequestMapping(value = "/v_tree.do")
	public String v_tree(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		return "district/v_tree";
	}

	@RequestMapping(value = "/v_list.do")
	public String v_list(String pcode, String level, Integer pageNo,
			HttpServletRequest request, HttpServletResponse response,
			ModelMap model) throws UnsupportedEncodingException {
		Pagination pagination = districtService.getPage(pcode,level,cpn(pageNo),
				CookieUtils.getPageSize(request));
		model.addAttribute("pagination", pagination);
		model.addAttribute("pcode", pcode);
		model.addAttribute("level", level);
		return "district/v_list";
	}

	@RequestMapping(value = "/v_add.do")
	public String v_add(String pcode, String level,HttpServletRequest request,
			HttpServletResponse response, ModelMap model)
			throws UnsupportedEncodingException {
		model.addAttribute("pcode", pcode);
		model.addAttribute("level", level);
		return "district/v_add";
	}

	@RequestMapping(value = "/o_save.do", method = RequestMethod.POST)
	public String o_save(District bean,String pcode, String level, HttpServletRequest request,
			HttpServletResponse response, ModelMap model)
			throws UnsupportedEncodingException {
		districtService.save(bean);
		return v_list(pcode, level, null, request, response, model);
	}

	@RequestMapping(value = "/v_edit.do")
	public String v_edit(String id,String pcode, String level, HttpServletRequest request,
			HttpServletResponse response, ModelMap model)
			throws UnsupportedEncodingException {
		model.put("bean", districtService.findById(id));
		model.addAttribute("pcode", pcode);
		model.addAttribute("level", level);
		return "district/v_edit";
	}

	@RequestMapping(value = "/o_update.do", method = RequestMethod.POST)
	public String o_update(District bean,String pcode, String level, HttpServletRequest request,
			HttpServletResponse response, ModelMap model)
			throws UnsupportedEncodingException {
		districtService.update(bean);
		return v_list(pcode, level, null, request, response, model);
	}

	@RequestMapping(value = "/o_delete.do")
	public String o_delete(String[] ids, String pcode, String level,HttpServletRequest request,
			HttpServletResponse response, ModelMap model)
			throws UnsupportedEncodingException {
		districtService.removeByIds(ids);
		return v_list(pcode, level, null, request, response, model);
	}

	@RequestMapping(value = "/getNodeList.do")
	public void getNode(String id, String lv, String n, String pcode,
			HttpServletRequest request, HttpServletResponse response,
			ModelMap model) throws UnsupportedEncodingException {
		List<District> list = districtService.getNodeList(id);
		JSONArray re = new JSONArray();
		if (StringUtils.isNotBlank(pcode) && StringUtils.isBlank(id)
				&& StringUtils.isBlank(lv) && StringUtils.isBlank(n)) {
			District district = districtService.findById(pcode);
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("id", pcode);
			jsonObject.put("name", district.getName());
			jsonObject.put("pId", district.getPcode());
			jsonObject.put("isParent", true);
			jsonObject.put("open", true);
			jsonObject.put("level", district.getLevel());
			re.add(jsonObject);
		}
		for (District district : list) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("id", district.getId());
			jsonObject.put("name", district.getName());
			jsonObject.put("pId", district.getPcode());
			jsonObject.put("isParent", district.getEnd()<=0);
			jsonObject.put("level", district.getLevel());
			re.add(jsonObject);
		}
		ResponseUtils.renderJson(response, re.toString());
	}
	
	
	@RequestMapping(value = "/json.do")
	public void json(String pcode, String level,HttpServletRequest request,
			HttpServletResponse response, ModelMap model)
			throws UnsupportedEncodingException {
		JSONArray re = new JSONArray();
		List<District> list = districtService.find(pcode, level);
		for (District district : list) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("code", district.getId());
			jsonObject.put("name", district.getName());
			jsonObject.put("pcode", district.getPcode());
			re.add(jsonObject);
		}
		ResponseUtils.renderJson(response, re.toString());
	}


	@Autowired
	public DistrictService districtService;
}
