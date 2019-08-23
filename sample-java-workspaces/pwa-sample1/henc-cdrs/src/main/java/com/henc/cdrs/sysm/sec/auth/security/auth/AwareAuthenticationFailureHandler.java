package com.henc.cdrs.sysm.sec.auth.security.auth;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.henc.cdrs.common.util.CipherUtil;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.henc.cdrs.sysm.sec.auth.security.config.WebSecurityConfig;
import com.henc.cdrs.sysm.sec.auth.security.exceptions.JwtBadCredentialsException;
import com.henc.cdrs.sysm.sec.auth.security.exceptions.JwtExpiredTokenException;
import com.henc.cdrs.sysm.sec.auth.security.exceptions.PasswordExpiredException;
import org.springframework.web.util.WebUtils;

@Component
public class AwareAuthenticationFailureHandler implements AuthenticationFailureHandler {

//    private RedirectStrategy redirectStrategy = null;
//    
//    
//    @Autowired
//    public AwareAuthenticationFailureHandler(ObjectMapper mapper) {
//        this.redirectStrategy = new DefaultRedirectStrategy();
//    }	

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException e) throws IOException, ServletException {

        if (e instanceof JwtBadCredentialsException) {
            //Invalid JWT Token
            String cookieUserName = RemeberMeAuthenticationRequestToken.rememberMeValue(request);
            if(cookieUserName == null){
                response.sendRedirect(WebSecurityConfig.LOGOUT_PAGE);
            }else{
                response.sendRedirect(WebSecurityConfig.REMMEBER_PROCESSING_URL);
            }
        } else if (e instanceof BadCredentialsException) {
            //비밀번호 불일치
//			redirectStrategy.sendRedirect(request, response, WebSecurityConfig.FAIL_PAGE + "?logFail=P");
            response.sendRedirect(WebSecurityConfig.FAIL_PAGE + "?logFail=E");   //logFail=P

        } else if (e instanceof PasswordExpiredException) {
//		    String userId = request.getParameter("username");
//		    redirectStrategy.sendRedirect(request, response, WebSecurityConfig.FAIL_PAGE + "?logFail=E&userId="+userId);
            response.sendRedirect(WebSecurityConfig.FAIL_PAGE + "?logFail=E");  //logFail=E

        } else if (e instanceof JwtExpiredTokenException) {
            //Refresh 토큰 만료
//			redirectStrategy.sendRedirect(request, response, WebSecurityConfig.LOGOUT_PAGE);
            String cookieUserName = RemeberMeAuthenticationRequestToken.rememberMeValue(request);
            if(cookieUserName == null){
                response.sendRedirect(WebSecurityConfig.LOGOUT_PAGE);
            }else{
                response.sendRedirect(WebSecurityConfig.REMMEBER_PROCESSING_URL);
            }
        } else if (e instanceof InsufficientAuthenticationException) {
            //User has no roles assigned
//			redirectStrategy.sendRedirect(request, response, WebSecurityConfig.FAIL_PAGE + "?logFail=R");
            response.sendRedirect(WebSecurityConfig.FAIL_PAGE + "?logFail=E");  //logFail=R

        } else if (e instanceof AuthenticationCredentialsNotFoundException) {
            // 사용자 존재하지 않음
            response.sendRedirect(WebSecurityConfig.FAIL_PAGE + "?logFail=E");  //logFail=N

        } else if (e instanceof AuthenticationServiceException) {
//			redirectStrategy.sendRedirect(request, response, WebSecurityConfig.FAIL_PAGE + "?logFail=F");
            response.sendRedirect(WebSecurityConfig.FAIL_PAGE + "?logFail=E");  //logFail=F

        } else if (e instanceof LockedException) {
            // 계정잠김
            response.sendRedirect(WebSecurityConfig.FAIL_PAGE + "?logFail=L");  //logFail=L

        } else {
//			redirectStrategy.sendRedirect(request, response, WebSecurityConfig.LOGOUT_PAGE);
            response.sendRedirect(WebSecurityConfig.LOGOUT_PAGE);
        }

    }

}
