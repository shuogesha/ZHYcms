package com.shuogesha.api.version;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@Configuration
public class ShuogeshaConfig extends WebMvcConfigurationSupport {
	@Override
	@Bean
	public RequestMappingHandlerMapping requestMappingHandlerMapping() {
		RequestMappingHandlerMapping handlerMapping = new CustomRequestMappingHandlerMapping();
		handlerMapping.setOrder(0);
		handlerMapping.setInterceptors(getInterceptors());
		return handlerMapping;
	}
}
