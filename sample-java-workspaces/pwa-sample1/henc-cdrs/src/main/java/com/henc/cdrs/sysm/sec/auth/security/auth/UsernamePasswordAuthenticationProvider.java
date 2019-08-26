package com.henc.cdrs.sysm.sec.auth.security.auth;

import com.henc.cdrs.common.util.CipherUtil;
import com.henc.cdrs.common.util.KISA_SHA256;
import com.henc.cdrs.sysm.sec.auth.model.UserDetail;
import com.henc.cdrs.sysm.sec.auth.security.config.WebSecurityConfig;
import com.henc.cdrs.sysm.sec.auth.security.dto.UserContext;
import com.henc.cdrs.sysm.sec.auth.security.exceptions.PasswordExpiredException;
import com.henc.cdrs.sysm.sec.auth.security.service.AuthenticationUsersService;
import com.henc.dream.Profiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Profile(value = {Profiles.Constants.PRODUCTION})
@Component("authenticationProvider")
public class UsernamePasswordAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    HttpServletRequest request;

    private final BCryptPasswordEncoder encoder;
    private final AuthenticationUsersService authenticationUsersService;

    @Autowired
    public UsernamePasswordAuthenticationProvider(final AuthenticationUsersService userDetailsService, final BCryptPasswordEncoder encoder) {
        this.authenticationUsersService = userDetailsService;
        this.encoder = encoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String reqUri = request.getRequestURI();

        Assert.notNull(authentication, "No authentication data provided");

        String username = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();
        String encPwd = KISA_SHA256.encryptPasswordPortalStyle(password);

        UserDetail userDetail = authenticationUsersService.whoAreYou(username);

        if (userDetail == null) {
            throw new AuthenticationCredentialsNotFoundException("사용자명과 비밀번호를 확인해주십시오.");
        }

        String cookieUserName = RemeberMeAuthenticationRequestToken.rememberMeValue(request);


        if (!reqUri.equals(WebSecurityConfig.API_SERVICE_URL)) {
            if(cookieUserName == null || !cookieUserName.equals(username)){
                if (!encPwd.equals(userDetail.getPwd())) {
                    authenticationUsersService.failCntUpdate(userDetail.getUserId());
                    throw new BadCredentialsException("입력하신 비밀번호가 일치하지 않습니다.");
                }
            }
        }


        /**
         * 사용자 비밀번호 유효기간 관리 안하도록 요청 -2019년 07월 30일 공사일보 중간 보고
         */
//        if ("CDRS".equals(userDetail.getLoginSource()) && "Y".equals(userDetail.getPwdExpiYn())) {
//            throw new PasswordExpiredException("비밀번호 유효기간이 만료되었습니다.");
//        }


        if (!"A".equals(userDetail.getStatus()) || "N".equals(userDetail.getUseYn())) {
            throw new LockedException("사용자의 계정상태가 중지되었습니다.");
        }

        UserContext userContext = authenticationUsersService.createUserContext(userDetail);

        return new UsernamePasswordAuthenticationToken(userContext, null, userContext.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}