package com.kt.svc1;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableCircuitBreaker
@EnableHystrixDashboard
public class Svc1Application {

	public static void main(String[] args) {
		SpringApplication.run(Svc1Application.class, args);
	}
	
	
	@Bean
	public RestTemplate restTemplate() {
		
		HttpComponentsClientHttpRequestFactory httpComponentsClientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
		
		// default timeout setting
		httpComponentsClientHttpRequestFactory.setReadTimeout(1000*10);
		httpComponentsClientHttpRequestFactory.setConnectTimeout(1000*3);
		
		return new RestTemplate(httpComponentsClientHttpRequestFactory);
	}
	
	
	@Bean
	public SqlSessionFactory sqlSessionFactory(DataSource ds) throws Exception {

	    SqlSessionFactoryBean factory = new SqlSessionFactoryBean();

	    factory.setDataSource(ds);

	    factory.setMapperLocations(

	        new PathMatchingResourcePatternResolver().getResources("classpath:/mappers/*.xml")

	    );

	    return factory.getObject();

	}


	@Bean
	public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory factory) {

	    return new SqlSessionTemplate(factory);
	}
	
}
