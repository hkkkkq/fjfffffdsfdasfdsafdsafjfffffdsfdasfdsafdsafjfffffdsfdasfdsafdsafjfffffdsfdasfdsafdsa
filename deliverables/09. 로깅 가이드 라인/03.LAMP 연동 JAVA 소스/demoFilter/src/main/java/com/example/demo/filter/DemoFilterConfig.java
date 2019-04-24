package com.example.demo.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class DemoFilterConfig {
	
	@Bean
	public FilterRegistrationBean<DemoFilter> loggingFilter() {
		
		FilterRegistrationBean<DemoFilter> registrationBean = new FilterRegistrationBean<>();
		
		registrationBean.setFilter(new DemoFilter());
//		registrationBean.addUrlPatterns("/*");
		
		return registrationBean;
	}
}
