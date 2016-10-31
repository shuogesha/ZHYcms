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
import com.shuogesha.cms.service.ChannelService;
import com.shuogesha.cms.service.SiteService;
import com.shuogesha.cms.web.CmsUtils;
import com.shuogesha.cms.web.CookieUtils;
import com.shuogesha.cms.web.ResponseUtils;
import com.shuogesha.cms.web.mongo.Pagination;

@Controller
@RequestMapping("/channel/")
public class ChannelAct {

	@RequestMapping(value = "/v_list.do")
	public String v_list(String siteId,String pid,Integer pageNo, HttpServletRequest request,
			HttpServletResponse response, ModelMap model)
			throws UnsupportedEncodingException {
		if (StringUtils.isBlank(pid)) {
			pid=null;
		}
		Pagination pagination = channelService.getPage(pid,siteId,cpn(pageNo),
				CookieUtils.getPageSize(request));
		model.addAttribute("pagination", pagination);
		Channel parent = new Channel();
		if (StringUtils.isNotBlank(pid)) {
			parent=channelService.findById(pid);
		}
		model.addAttribute("parent", parent);
		model.addAttribute("site", siteService.findById(siteId));
		return "channel/v_list";
	}

	@RequestMapping(value = "/v_add.do")
	public String v_add(String pid,String siteId,HttpServletRequest request,
			HttpServletResponse response, ModelMap model)
			throws UnsupportedEncodingException {
		Channel parent = new Channel();
		if (StringUtils.isNotBlank(pid)) {
			parent=channelService.findById(pid);
		}
		model.addAttribute("parent", parent);
		model.addAttribute("site", siteService.findById(siteId));
		return "channel/v_add";
	}

	@RequestMapping(value = "/o_save.do", method = RequestMethod.POST)
	public String o_save(Channel bean,String pid,String siteId,
			HttpServletRequest request, HttpServletResponse response,
			ModelMap model) throws UnsupportedEncodingException {
		bean.setUser(CmsUtils.getUser(request));
		bean.setSite(siteService.findById(siteId));
		if(StringUtils.isNotBlank(pid)){
			Channel parent = new Channel();
			parent.setId(pid);
			bean.setParent(parent);
		}
		channelService.save(bean);
		return v_list(siteId, pid, null, request, response, model);
	}

	@RequestMapping(value = "/v_edit.do")
	public String v_edit(String id, HttpServletRequest request,
			HttpServletResponse response, ModelMap model)
			throws UnsupportedEncodingException {
		model.put("bean", channelService.findById(id));
		return "channel/v_edit";
	}

	@RequestMapping(value = "/o_update.do", method = RequestMethod.POST)
	public String o_update(Channel bean, String pid,String siteId,HttpServletRequest request,
			HttpServletResponse response, ModelMap model)
			throws UnsupportedEncodingException {
		channelService.update(bean);
		return  v_list(siteId, pid, null, request, response, model);
	}

	@RequestMapping(value = "/o_delete.do")
	public String o_delete(String[] ids,String pid,String siteId, HttpServletRequest request,
			HttpServletResponse response, ModelMap model)
			throws UnsupportedEncodingException {
		channelService.removeByIds(ids);
		return v_list(siteId, pid, null, request, response, model);
	}
	
	@RequestMapping(value = "/getNodeList.do")
	public void getNode(String id,String lv,String n,String siteId,HttpServletRequest request,
			HttpServletResponse response, ModelMap model)
			throws UnsupportedEncodingException {
		List<Channel> list= channelService.getNodeList(id,siteId);
		JSONArray re = new JSONArray();
		if(StringUtils.isNotBlank(siteId)&&StringUtils.isBlank(id)&&StringUtils.isBlank(lv)&&StringUtils.isBlank(n)){
			Site site = siteService.findById(siteId);
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("id", "0");
			jsonObject.put("name", "根目录");
			jsonObject.put("pId", "-1");
			jsonObject.put("isParent", true);
			jsonObject.put("siteId", siteId);
			re.add(jsonObject);
		}

			for (Channel channel : list) {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("id", channel.getId());
				jsonObject.put("name", channel.getName());
				if (channel.getParent()!=null) {
					jsonObject.put("pId", channel.getParent().getId());
				} else {
					jsonObject.put("pId", "0");
				}
				jsonObject.put("siteId", siteId);
				jsonObject.put("isParent", channel.getLevel()>0);
				re.add(jsonObject);
			}
		ResponseUtils.renderJson(response, re.toString());
	}
	
	@Autowired
	public ChannelService channelService;
	@Autowired
	public SiteService siteService;
}
