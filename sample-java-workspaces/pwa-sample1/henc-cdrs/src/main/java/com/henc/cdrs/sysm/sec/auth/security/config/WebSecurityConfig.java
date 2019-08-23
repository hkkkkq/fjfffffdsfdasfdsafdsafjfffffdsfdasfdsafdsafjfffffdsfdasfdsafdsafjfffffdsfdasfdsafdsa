package com.henc.cdrs.sysm.sec.auth.security.config;

import java.util.Arrays;
import java.util.List;

import com.henc.cdrs.sysm.sec.auth.security.auth.RememberProcessingFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.henc.cdrs.sysm.sec.auth.security.auth.ApiProcessingFilter;
import com.henc.cdrs.sysm.sec.auth.security.auth.LoginProcessingFilter;
import com.henc.cdrs.sysm.sec.auth.security.auth.LogoutSuccessHandler;
import com.henc.cdrs.sysm.sec.auth.security.filter.CustomCorsFilter;
import com.henc.cdrs.sysm.sec.auth.security.jwt.JwtAuthenticationProvider;
import com.henc.cdrs.sysm.sec.auth.security.jwt.JwtTokenAuthenticationProcessingFilter;
import com.henc.cdrs.sysm.sec.auth.security.jwt.SkipPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    public static final String ROOT_PATH                    = "/";
    public static final String SECURITY_PATH                = "/security";

    public static final String LOGIN_PAGE 					= SECURITY_PATH + "/login";
    public static final String LOGIN_PROCESSING_URL         = SECURITY_PATH + "/sign_in";
    public static final String LOGOUT_URL               	= SECURITY_PATH + "/sign_out";
    public static final String LOGOUT_PAGE 					= SECURITY_PATH + "/logout";
    public static final String FAIL_PAGE 					= SECURITY_PATH + "/fail";
    public static final String SEND_CIRT_NO 				= SECURITY_PATH + "/sendCertNo";
    public static final String CHECK_CIRT_NO 				= SECURITY_PATH + "/checkCertNo";

    public static final String REMMEBER_PROCESSING_URL         = SECURITY_PATH + "/r_sign_in";


    public static final String LOGOUT_SUCCESS_URL       	= ROOT_PATH;
    public static final String API_SERVICE_URL 				= SECURITY_PATH + "/api/service";
    public static final String API_AUTH_URL                 = SECURITY_PATH + "/apiAuth";
    public static final String CHANGE_PASSWORD_URL          = SECURITY_PATH + "/changePassword";
    public static final String FAVICON_URL                  = "/favicon.ico";

    @Autowired private RestAuthenticationEntryPoint authenticationEntryPoint;
    @Autowired private AuthenticationSuccessHandler successHandler;
    @Autowired private AuthenticationFailureHandler failureHandler;
    @Autowired
    @Qualifier("authenticationProvider")
    private AuthenticationProvider authenticationProvider;
    @Autowired private JwtAuthenticationProvider jwtAuthenticationProvider;
    @Autowired private AuthenticationManager authenticationManager;

    /**
     * 로그인
     * @param loginEntryPoint
     * @return
     * @throws Exception
     */
    protected LoginProcessingFilter loginProcessingFilter(String loginEntryPoint) throws Exception {
        LoginProcessingFilter filter = new LoginProcessingFilter(loginEntryPoint, successHandler, failureHandler);
        filter.setAuthenticationManager(this.authenticationManager);
        return filter;
    }

    /**
     * LEGACY SYSTEM 연동
     * @param loginEntryPoint
     * @return
     * @throws Exception
     */
    protected ApiProcessingFilter apiProcessingFilter(String loginEntryPoint) throws Exception {
        ApiProcessingFilter filter = new ApiProcessingFilter(loginEntryPoint, successHandler, failureHandler);
        filter.setAuthenticationManager(this.authenticationManager);
        return filter;
    }

    /**
     * AUTH 검증
     * @param skipPath
     * @return
     * @throws Exception
     */
    protected JwtTokenAuthenticationProcessingFilter jwtTokenAuthenticationProcessingFilter(List<String> skipPath ) throws Exception {
        SkipPathRequestMatcher matcher = new SkipPathRequestMatcher(skipPath);
        JwtTokenAuthenticationProcessingFilter filter = new JwtTokenAuthenticationProcessingFilter(failureHandler ,matcher);
        filter.setAuthenticationManager(this.authenticationManager);
        return filter;
    }

    /**
     * REMEMBER ME Filter
     * @param loginEntryPoint
     * @return
     * @throws Exception
     */
    protected RememberProcessingFilter rememberProcessingFilter(String loginEntryPoint) throws Exception {
        RememberProcessingFilter filter = new RememberProcessingFilter(loginEntryPoint, successHandler, failureHandler);
        filter.setAuthenticationManager(this.authenticationManager);
        return filter;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider);
        auth.authenticationProvider(jwtAuthenticationProvider);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        //리소스 경로
        web.ignoring().antMatchers("/bootstrap/**"
                ,"/css/**"
                ,"/images/**"
                ,"/js/**"
                ,"/webjars/**"
                ,"/assets/**"
                , "/firebase*"
                , "/manifest*"
                , "/sw.js"
                , "/browserconfig.xml"
                , "/partnerConfirm/**"
                , "/.well-known/pki-validation/6E40249451CD5413C66CA21A2ADCBF4F.txt"
        );
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //AUTH 검증 FILTER 제외 URL
        List<String> skipPathList = Arrays.asList(
                LOGIN_PAGE,
                LOGOUT_URL,
                LOGOUT_PAGE,
                FAIL_PAGE,
                SEND_CIRT_NO,
                CHECK_CIRT_NO,
                API_SERVICE_URL,
                API_AUTH_URL,
                REMMEBER_PROCESSING_URL,
                CHANGE_PASSWORD_URL,
                FAVICON_URL
        );

        http
                .headers().disable()
                .csrf().disable() // We don't need CSRF for JWT based authentication
                .exceptionHandling()
                .authenticationEntryPoint(this.authenticationEntryPoint)

/*            .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)// Security 인증 세션을 유지하지 않음
                                                                       // SessionCreationPolicy.STATELESS 적용시 첫 호출시 에러 발생함.
                                                                       // 보안상은  STATELESS 유지를 권장하고 있으나 일단 주석 처리함.
*/            .and()
                //크로스 도메인 리소스 공유 FILTER
                .addFilterBefore(new CustomCorsFilter(), UsernamePasswordAuthenticationFilter.class)
                //로그인 FILTER
                .addFilterBefore(loginProcessingFilter(LOGIN_PROCESSING_URL), UsernamePasswordAuthenticationFilter.class)
                //LEGACY SYSTEM 연동 API
                .addFilterBefore(apiProcessingFilter(API_SERVICE_URL), UsernamePasswordAuthenticationFilter.class)
                //remmeberMe 로그인 FILTER
                .addFilterBefore(rememberProcessingFilter(REMMEBER_PROCESSING_URL), UsernamePasswordAuthenticationFilter.class)
                //AUTH 검증 FILTER
                .addFilterBefore(jwtTokenAuthenticationProcessingFilter(skipPathList), UsernamePasswordAuthenticationFilter.class)

                .logout()
                //.deleteCookies("JSESSIONID")
                .logoutUrl(LOGOUT_URL)
                .invalidateHttpSession(true)
                .logoutSuccessUrl(LOGOUT_SUCCESS_URL)
                .logoutSuccessHandler(new LogoutSuccessHandler());
    }
}
