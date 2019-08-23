package com.henc.cdrs;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
@Slf4j
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class}) //h2를 넣거나 자동 구성을 빼거나...
public class Application extends SpringBootServletInitializer {
    /**
     * JEUS/WebLogic 등 Servelt Container 상에서 Spring Boot 초기화
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.listeners(new PropertyFileRegisteringListener()).sources(Application.class);
    }

    /**
     * Standalone의 경우 Spring Boot 기동
     * @param args
     */
    public static void main(String[] args) {
        SpringApplicationBuilder application = new SpringApplicationBuilder(Application.class);
        application.listeners(new PropertyFileRegisteringListener()).build().run(args);
    }

}