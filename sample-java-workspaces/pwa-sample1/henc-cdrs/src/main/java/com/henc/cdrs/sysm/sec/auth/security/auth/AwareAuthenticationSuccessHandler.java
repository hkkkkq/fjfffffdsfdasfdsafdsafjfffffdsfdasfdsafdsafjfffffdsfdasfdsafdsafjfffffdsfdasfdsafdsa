package com.henc.cdrs.sysm.sec.auth.security.auth;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.henc.cdrs.sysm.sec.auth.model.UserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.stereotype.Component;

import com.henc.cdrs.common.util.CipherUtil;
import com.henc.cdrs.sysm.sec.auth.security.common.SecurityUtil;
import com.henc.cdrs.sysm.sec.auth.security.config.WebSecurityConfig;
import com.henc.cdrs.sysm.sec.auth.security.dto.UserContext;
import com.henc.cdrs.sysm.sec.auth.security.token.JwtToken;
import com.henc.cdrs.sysm.sec.auth.security.token.JwtTokenFactory;

@Component
public class AwareAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtTokenFactory tokenFactory;
    private RedirectStrategy redirectStrategy = null;

    @Autowired
    public AwareAuthenticationSuccessHandler(final JwtTokenFactory tokenFactory) {
        this.tokenFactory = tokenFactory;
        this.redirectStrategy = new DefaultRedirectStrategy();
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        UserContext userContext = (UserContext) authentication.getPrincipal();

        JwtToken accessToken = tokenFactory.createAccessJwtToken(userContext);
        JwtToken refreshToken = tokenFactory.createRefreshToken(userContext);

        SecurityUtil.createCookie(response, accessToken, refreshToken, userContext);

        CipherUtil cu = new CipherUtil();

        String username = request.getParameter("username");

        if(username == null){
            UserDetail userDetail = userContext.getUserDetail();
            if("COPRCP".equals(userDetail.getUserDivCd())){
                username = userDetail.getTlno();
            }else{
                username = userDetail.getUserId();
            }
        }

        username = cu.encrytion(username, CipherUtil.AES_ALGORITHM);

        Cookie cookieSaveId = new Cookie(RemeberMeAuthenticationRequestToken.REMEMBER_ME_COOKIE_NAME,  username);
        cookieSaveId.setPath("/");



        if("true".equalsIgnoreCase(request.getParameter("idSaveCheck")) || RemeberMeAuthenticationRequestToken.rememberMeValue(request) != null){
            int cokeiMaxAge = 60 * 60 * 24 * 7;
            cookieSaveId.setMaxAge(cokeiMaxAge);
        }else{
            cookieSaveId.setMaxAge(0);
        }

        response.addCookie(cookieSaveId);

        String agreeYn = "Y";

        if(null == userContext.getUserDetail().getClctAgreeYn()){
            agreeYn = "N";
        }


        sendRedirectDefaultUrl(request, response, agreeYn);
    }

    private void sendRedirectDefaultUrl(HttpServletRequest request, HttpServletResponse response, String agreeYn) throws IOException {
        String nextUri = WebSecurityConfig.ROOT_PATH;

        if("N".equals(agreeYn)){
            nextUri ="/mgmt/privacy/privacy";
        }
        redirectStrategy.sendRedirect(request, response, nextUri);
    }
}