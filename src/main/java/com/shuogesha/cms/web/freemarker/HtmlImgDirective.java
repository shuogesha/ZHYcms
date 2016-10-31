package com.shuogesha.cms.web.freemarker;

import static freemarker.template.ObjectWrapper.DEFAULT_WRAPPER;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * HTML文本提取并截断
 * 
 * 需要拦截器com.shuogesha.common.web.ProcessTimeFilter支持
 */
public class HtmlImgDirective implements TemplateDirectiveModel {
	public static final String PARAM_S = "s";
	public static final String PARAM_WIDTH = "width";
	public static final String PARAM_HEIGHT = "height";
	public static final String PARAM_IMGS = "imgs";
	public static final String PARAM_COUNT = "count";

	@SuppressWarnings("unchecked")
	public void execute(Environment env, Map params, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		String s = DirectiveUtils.getString(PARAM_S, params);
		Integer width = DirectiveUtils.getInt(PARAM_WIDTH, params);
		Integer height = DirectiveUtils.getInt(PARAM_HEIGHT, params);
		Integer c = DirectiveUtils.getInt(PARAM_COUNT, params);
		List<Element> list = new ArrayList<>();
		if (s != null) {
			Document docb = Jsoup.parseBodyFragment(s);
			Elements articletime = docb.select("img");
			int i= 1;
			for (Element element : articletime) {
				if (element!=null&&i<=c) {
					list.add(element);
					i++;
				}
			}
		}
		Map<String, TemplateModel> paramWrap = new HashMap<String, TemplateModel>(
				params);
		paramWrap.put(PARAM_IMGS, DEFAULT_WRAPPER.wrap(list));
		Map<String, TemplateModel> origMap = DirectiveUtils
				.addParamsToVariable(env, paramWrap);
		body.render(env.getOut());
		DirectiveUtils.removeParamsFromVariable(env, paramWrap, origMap);
	}
}
