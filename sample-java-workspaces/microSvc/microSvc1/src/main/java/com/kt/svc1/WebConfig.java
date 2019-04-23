package com.kt.svc1;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

//@Configuration
//public class WebConfig implements WebMvcConfigurer {
//	private static final String[] RESOURCE_LOCATIONS = {
// 		   "classpath:/META-INF/resources/", "classpath:/resources/",
//            "classpath:/static/", "classpath:/public/"
//            };
//
//	public void addResourceHandlers(ResourceHandlerRegistry registry) {
//	     registry
//	     .addResourceHandler("/**")
//	     .addResourceLocations(RESOURCE_LOCATIONS)
//	     .setCachePeriod(3600)
//	     .resourceChain(true)
//	     .addResolver(new PathResourceResolver());
//	 }
//}
