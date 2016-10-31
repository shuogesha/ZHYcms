package com.shuogesha.api.action;

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
import com.shuogesha.cms.service.ChannelService;

@Controller
@RequestMapping("/{version}/")
public class ApiChannelAct{
	private static Logger log = LoggerFactory.getLogger(ApiChannelAct.class);
	
	@RequestMapping(value = "get_channel_list")
	@ApiVersion(1)
	public @ResponseBody Object get_channel_list(String pid, HttpServletRequest request,
			HttpServletResponse response, ModelMap model) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("data", channelService.getNodeList(pid, ApiUtils.getSiteId(request)));
		map.put("status", true);
		return map;
	}
	@Autowired
	private ChannelService channelService;
}
