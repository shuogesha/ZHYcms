package com.shuogesha.cms.action.front;

import static com.shuogesha.cms.Constants.TPLDIR_INDEX;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.shuogesha.cms.entity.Site;
import com.shuogesha.cms.web.CmsUtils;
import com.shuogesha.cms.web.FrontUtils;

@Controller
public class IndexAct {

	public static final String TPL_INDEX = "tpl.index";

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		Site site = CmsUtils.getSite(request);
		FrontUtils.frontData(request, model, site);
		return FrontUtils.getTplPath(request, site.getSolutionPath(),
				TPLDIR_INDEX, TPL_INDEX);
	}

	@RequestMapping(value = "/index.htm", method = RequestMethod.GET)
	public String jetty(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		return index(request,response, model);
	}
}
