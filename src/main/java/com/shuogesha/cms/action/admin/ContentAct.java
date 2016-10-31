package com.shuogesha.cms.action.admin;

import static com.shuogesha.cms.web.mongo.SimplePage.cpn;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.shuogesha.cms.entity.Channel;
import com.shuogesha.cms.entity.Site;
import com.shuogesha.cms.entity.Content;
import com.shuogesha.cms.entity.User;
import com.shuogesha.cms.service.ChannelService;
import com.shuogesha.cms.service.ContentService;
import com.shuogesha.cms.service.SiteService;
import com.shuogesha.cms.web.CmsUtils;
import com.shuogesha.cms.web.CookieUtils;
import com.shuogesha.cms.web.RequestUtils;
import com.shuogesha.cms.web.mongo.Pagination;

@Controller
@RequestMapping("/content/")
public class ContentAct {

	@RequestMapping(value = "/v_list.do")
	public String v_list(String channelId,String siteId,Integer pageNo, HttpServletRequest request,
			HttpServletResponse response, ModelMap model)
			throws UnsupportedEncodingException {
		User user = CmsUtils.getUser(request);
		Pagination pagination = contentService.getPage(channelId,siteId,cpn(pageNo),
				CookieUtils.getPageSize(request));
		model.addAttribute("pagination", pagination);
		Channel channel = new Channel();
		if (StringUtils.isNotBlank(channelId)) {
			channel=channelService.findById(channelId);
		}
		model.addAttribute("channel", channel);
		model.addAttribute("site", siteService.findById(siteId));
		return "content/v_list";
	}

	@RequestMapping(value = "/v_add.do")
	public String v_add(String channelId,String siteId,HttpServletRequest request,
			HttpServletResponse response, ModelMap model)
			throws UnsupportedEncodingException {
		Channel channel = channelService.findById(channelId);
		model.addAttribute("channel", channel);
		model.addAttribute("site", siteService.findById(siteId));
		model.addAttribute("user", CmsUtils.getUser(request));
		return "content/v_add";
	}

	@RequestMapping(value = "/o_save.do", method = RequestMethod.POST)
	public String o_save(Content bean,String channelId,String siteId,HttpServletRequest request, HttpServletResponse response,
			ModelMap model) throws UnsupportedEncodingException {
		User user = CmsUtils.getUser(request);
		bean.setUser(user);
		bean.setSite(siteService.findById(siteId));
		bean.setChannel(channelService.findById(channelId));
		contentService.save(bean);
		return v_list(channelId, siteId, null, request, response, model);
	}

	@RequestMapping(value = "/v_edit.do")
	public String v_edit(String id, HttpServletRequest request,
			HttpServletResponse response, ModelMap model)
			throws UnsupportedEncodingException {
		Content bean=contentService.findById(id);
		model.put("bean", contentService.findById(id));
		return "content/v_edit";
	}

	@RequestMapping(value = "/o_update.do", method = RequestMethod.POST)
	public String o_update(Content bean,String channelId,String siteId, HttpServletRequest request,
			HttpServletResponse response, ModelMap model)
			throws UnsupportedEncodingException {
		contentService.update(bean);
		return v_list(channelId, siteId, null, request, response, model);
	}

	@RequestMapping(value = "/o_delete.do")
	public String o_delete(String[] ids, String channelId,String siteId, HttpServletRequest request,
			HttpServletResponse response, ModelMap model)
			throws UnsupportedEncodingException {
		contentService.removeByIds(ids);
		return v_list(channelId, siteId, null, request, response, model);
	}
	
	@RequestMapping(value = "/o_audit.do")
	public String o_audit(String id, String channelId,String siteId, HttpServletRequest request,
			HttpServletResponse response, ModelMap model)
			throws UnsupportedEncodingException {
		contentService.applyAudit(id);
		return v_list(channelId, siteId, null, request, response, model);
	}
	
	@RequestMapping(value = "/v_audit.do")
	public String v_audit(String id, HttpServletRequest request,
			HttpServletResponse response, ModelMap model)
			throws UnsupportedEncodingException {
		Content bean=contentService.findById(id);
		model.put("bean", contentService.findById(id));
		return "content/v_audit";
	}
	
	
	@Autowired
	public ContentService contentService;
	@Autowired
	public ChannelService channelService;
	@Autowired
	public SiteService siteService;
}
