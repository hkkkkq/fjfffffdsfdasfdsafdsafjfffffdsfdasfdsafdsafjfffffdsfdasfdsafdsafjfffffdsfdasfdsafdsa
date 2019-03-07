package com.kt.svc2;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class Svc2Application {

	public static void main(String[] args) {
		SpringApplication.run(Svc2Application.class, args);
	}
	
	@Bean
    public RestTemplate getRestTemplate() {
		return new RestTemplate();
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
