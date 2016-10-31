package com.shuogesha.cms.action.directive;

import static com.shuogesha.cms.web.freemarker.DirectiveUtils.OUT_BEAN;
import static freemarker.template.ObjectWrapper.DEFAULT_WRAPPER;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.shuogesha.cms.entity.Channel;
import com.shuogesha.cms.entity.Site;
import com.shuogesha.cms.service.ChannelService;
import com.shuogesha.cms.web.CmsUtils;
import com.shuogesha.cms.web.FrontUtils;
import com.shuogesha.cms.web.freemarker.DirectiveUtils;
import com.shuogesha.cms.web.freemarker.ParamsRequiredException;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

public class ChannelDirective implements TemplateDirectiveModel {

	/**
	 * 输入参数，栏目ID。
	 */
	public static final String PARAM_ID = "id";
	
	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		Site site = FrontUtils.getSite(env);
		Channel channel;
		String id = DirectiveUtils.getString(PARAM_ID, params);
		Map<String, Object> model = new HashMap<>();
		if (id != null) {
			channel = channelService.findById(id);
		}
		Map<String, TemplateModel> paramWrap = new HashMap<String, TemplateModel>(
				params);
		paramWrap.put(OUT_BEAN, DEFAULT_WRAPPER.wrap(model));
		Map<String, TemplateModel> origMap = DirectiveUtils
				.addParamsToVariable(env, paramWrap);
		body.render(env.getOut());
		DirectiveUtils.removeParamsFromVariable(env, paramWrap, origMap);
	}

	@Autowired
	public ChannelService channelService;
}
