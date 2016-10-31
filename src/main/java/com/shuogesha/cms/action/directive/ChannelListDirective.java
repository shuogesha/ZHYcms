package com.shuogesha.cms.action.directive;

import static com.shuogesha.cms.web.freemarker.DirectiveUtils.OUT_LIST;
import static com.shuogesha.cms.web.freemarker.DirectiveUtils.OUT_PAGINATION;
import static freemarker.template.ObjectWrapper.DEFAULT_WRAPPER;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.shuogesha.cms.action.directive.abs.AbstractChannelDirective;
import com.shuogesha.cms.entity.Channel;
import com.shuogesha.cms.entity.Site;
import com.shuogesha.cms.web.FrontUtils;
import com.shuogesha.cms.web.freemarker.DirectiveUtils;
import com.shuogesha.cms.web.freemarker.DirectiveUtils.InvokeType;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

public class ChannelListDirective extends AbstractChannelDirective {
	public static final String TPL_NAME = "channel_list";
	public static final String PARAM_SITE_ID = "siteId";

	@SuppressWarnings("unchecked")
	public void execute(Environment env, Map params, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		String parentId = DirectiveUtils.getString(PARAM_PARENT_ID, params);
		String siteId = DirectiveUtils.getString(PARAM_SITE_ID, params);
		boolean hasContentOnly = getHasContentOnly(params);
		if (siteId == null) {
			siteId = FrontUtils.getSite(env).getId();
		}
		List<Channel> list;
		if (StringUtils.isNotBlank(parentId)) {
			list = channelService.getChildListForTag(parentId, hasContentOnly,siteId);
		} else {
			list = channelService.getTopListForTag(hasContentOnly,siteId);
		}

		Map<String, TemplateModel> paramWrap = new HashMap<String, TemplateModel>(
				params);
		paramWrap.put(OUT_LIST, DEFAULT_WRAPPER.wrap(list));
		Map<String, TemplateModel> origMap = DirectiveUtils
				.addParamsToVariable(env, paramWrap);
		body.render(env.getOut());
		DirectiveUtils.removeParamsFromVariable(env, paramWrap, origMap);
	}

}
