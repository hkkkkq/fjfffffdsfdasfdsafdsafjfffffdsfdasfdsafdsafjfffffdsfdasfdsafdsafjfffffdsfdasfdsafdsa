package com.kt.svc1;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@Profile("!prd")
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket api() {
		
		return new Docket(DocumentationType.SWAGGER_2)
					.select()
					.apis(RequestHandlerSelectors.basePackage("com.kt.svc1"))
					.paths(PathSelectors.ant("/**"))
					.build()
					.apiInfo(getApiInfo());
	}
	
	private ApiInfo getApiInfo() {
		
		
		return new ApiInfo(
				"kt MSA 플랫폼 구축 프로젝트",
				"api Documents",
				"1.0",
				null,
				null,
				null,
				null,
				Collections.emptyList()
				);
	}
	
}
