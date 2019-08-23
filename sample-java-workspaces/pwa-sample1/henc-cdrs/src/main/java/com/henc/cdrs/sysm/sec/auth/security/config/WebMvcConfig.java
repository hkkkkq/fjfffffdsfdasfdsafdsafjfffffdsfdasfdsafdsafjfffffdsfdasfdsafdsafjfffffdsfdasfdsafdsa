package com.henc.cdrs.sysm.sec.auth.security.config;

import com.henc.cdrs.sysm.sec.auth.security.interceptor.ErrorInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.henc.cdrs.sysm.sec.auth.security.interceptor.RoleInterceptor;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private RoleInterceptor roleInterceptor;

    @Autowired
    private ErrorInterceptor errorInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(roleInterceptor)
                .excludePathPatterns(
                         "/bootstrap/**"
                        ,"/css**/**/*.css"
                        ,"/css**/**/*.js"
                        ,"/css**/**/*.png"
                        ,"/css**/**/*.ico"
                        ,"/css**/**/*.css.map"
                        ,"/images**/**/*.png"
                        ,"/images**/**/*.ico"
                        ,"/images**/**/*.jpg"
                        ,"/js**/**/*.js"
                        ,"/js**/**/*.png"
                        ,"/js**/**/*.css"
                        ,"/webjars/**"
                        ,"/assets/**"
                        , "/firebase*"
                        , "/manifest*"
                        , "/sw.js"
                        , "/browserconfig.xml"
                        , "/partnerConfirm/*"
                        , "/mList.m**"
                        , "/subscribe**"
                        , "/daily/**"
                        , "/partner/**"
                        , "/error**"
                        , "/individual/**"
                        , "/mgmt/privacy/privacy"
                        , "/**/**Modal/**"
                        , WebSecurityConfig.LOGOUT_PAGE
                );

        registry.addInterceptor(errorInterceptor).addPathPatterns("/error", "/error/**");
    }
}
