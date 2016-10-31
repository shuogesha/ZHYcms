package com.shuogesha.cms.web.freemarker;

import java.io.IOException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

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
public class TimeFormatDirective implements TemplateDirectiveModel {
	public static final String PARAM_DATELINE = "dateline";
	public static final String PARAM_TIME = "time";
	public static final String PARAM_SECONDS = "seconds";
	public static final String PARAM_LEN = "len";

	@SuppressWarnings("unchecked")
	public void execute(Environment env, Map params, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		String dateline = DirectiveUtils.getString(PARAM_DATELINE, params);
		String time = DirectiveUtils.getString(PARAM_TIME, params);
		String seconds = DirectiveUtils.getString(PARAM_SECONDS, params);
		Integer len = DirectiveUtils.getInt(PARAM_LEN, params);
		if (len<=0) {
			len = 1;
		}
		StringBuffer sb = new StringBuffer();
		sb.append("");
		if (dateline != null||time!=null) {
			Writer out = env.getOut();
			if (len >0) {
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				java.util.Date now = new Date();
				try {
				   if (StringUtils.isNotBlank(time)) {
					   dateline = df.format(new Date(Long.valueOf(time)));
				   }
				   java.util.Date date=df.parse(dateline);
				   long l=now.getTime()-date.getTime();
				   long day=l/(24*60*60*1000);
				   if (day<=len) {
					   int y = (int)day/365; 
					   int year = Math.abs(y);   
					   if(year>0){
						   sb.append(year+"年");
					   }else {
						   if(day > 0){
							   sb.append(day+"天");
						   }else {
							   long hour=(l/(60*60*1000)-day*24);
							   if(hour > 0 ){
								   sb.append(hour+"小时");
							   }else {
								   long min=((l/(60*1000))-day*24*60-hour*60);
								   if(min > 0 ){
								   	   sb.append(min+"分");
								   }else {
									   long s=(l/1000-day*24*60*60-hour*60*60-min*60);
									   sb.append(s+"秒");
								   }
							   }
							   
						   }
					   }
					   if (y<0) {
						   sb.append("后");
					   }else{
						   sb.append("前");
					   }
				   }else {
					   sb.append(dateline);
				   }
				} catch (Exception e) {
					sb.append(dateline);
				}
				out.append(sb);
			} else {
				out.append(sb);
			}
		}else if (seconds!=null) {
			Writer out = env.getOut();
			int i = Integer.parseInt(seconds);
			int h=i/3600;
			int m=(i%3600)/60;
			int s=(i%3600)%60;
			if (h>0) {
				 sb.append(new java.text.DecimalFormat("00").format(h)+":");
			}
			 sb.append(new java.text.DecimalFormat("00").format(m)+":");
			 sb.append(new java.text.DecimalFormat("00").format(s));
			 out.append(sb);
		}
	}
}
