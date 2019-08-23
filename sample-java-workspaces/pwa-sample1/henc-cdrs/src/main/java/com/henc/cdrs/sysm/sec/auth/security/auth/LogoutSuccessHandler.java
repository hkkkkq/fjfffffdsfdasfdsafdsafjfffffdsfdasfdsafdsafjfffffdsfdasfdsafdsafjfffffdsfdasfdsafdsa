package com.henc.cdrs.sysm.sec.auth.security.auth;

import com.henc.cdrs.sysm.sec.auth.security.common.SecurityUtil;
import com.henc.cdrs.sysm.sec.auth.security.config.WebSecurityConfig;
import org.springframework.security.core.Authentication;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LogoutSuccessHandler implements org.springframework.security.web.authentication.logout.LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        try {

            SecurityUtil.removeCookie(request, response);
            request.getSession().invalidate();

        } catch (Exception e) {
            e.printStackTrace();
        }

        response.setStatus(HttpServletResponse.SC_OK);
        response.sendRedirect(WebSecurityConfig.LOGIN_PAGE);
    }

}
