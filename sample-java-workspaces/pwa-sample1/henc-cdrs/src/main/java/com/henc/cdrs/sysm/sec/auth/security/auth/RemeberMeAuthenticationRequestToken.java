package com.henc.cdrs.sysm.sec.auth.security.auth;

import com.henc.cdrs.common.util.CipherUtil;
import com.henc.cdrs.sysm.sec.auth.model.UserDetail;
import com.henc.cdrs.sysm.sec.auth.security.dto.UserContext;
import com.henc.cdrs.sysm.sec.auth.security.service.AuthenticationUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.Assert;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by beoms(lbg6848@sdcit.co.kr) on 2019-08-18
 * Corporation : SDCIT(주)
 * HomePage : http://www.sdcit.co.kr
 */
public class RemeberMeAuthenticationRequestToken implements AuthenticationProvider {

    public static final String REMEMBER_ME_COOKIE_NAME = "cdrs.rememberMe";

    @Autowired
    HttpServletRequest request;

    private final BCryptPasswordEncoder encoder;
    private final AuthenticationUsersService authenticationUsersService;

    @Autowired
    public RemeberMeAuthenticationRequestToken(final AuthenticationUsersService userDetailsService, final BCryptPasswordEncoder encoder) {
        this.authenticationUsersService = userDetailsService;
        this.encoder = encoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String reqUri = request.getRequestURI();

        Assert.notNull(authentication, "No authentication data provided");

        String username = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();

        String cookieUserName = rememberMeValue(request);

        if(cookieUserName == null || !cookieUserName.equals(username)){
            Cookie cookieSaveId = new Cookie(RemeberMeAuthenticationRequestToken.REMEMBER_ME_COOKIE_NAME,  username);
            cookieSaveId.setMaxAge(0);
            throw new AuthenticationCredentialsNotFoundException("사용자명과 RememberMe 정보가 다릅니다.");
        }

        UserDetail userDetail = authenticationUsersService.whoAreYou(cookieUserName);

        if (userDetail == null) {
            throw new AuthenticationCredentialsNotFoundException("사용자명과 비밀번호를 확인해주십시오.");
        }

        UserContext userContext = authenticationUsersService.createUserContext(userDetail);



        return new UsernamePasswordAuthenticationToken(userContext, null, userContext.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }

    public static String rememberMeValue(HttpServletRequest request){
        Cookie cookieSaveId = WebUtils.getCookie(request, RemeberMeAuthenticationRequestToken.REMEMBER_ME_COOKIE_NAME);
        String cookieUserName = null;
        if(cookieSaveId != null){
            cookieUserName = cookieSaveId.getValue();
            CipherUtil cu = new CipherUtil();
            cookieUserName = cu.decrytion(cookieUserName, CipherUtil.AES_ALGORITHM);
        }
        return cookieUserName;
    }

}
