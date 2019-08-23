package com.henc.cdrs.sysm.sec.auth.security.common;

import com.henc.cdrs.sysm.main.menu.model.Menu;
import com.henc.cdrs.sysm.sec.auth.model.UserDetail;
import com.henc.cdrs.sysm.sec.auth.security.config.JwtSettings;
import com.henc.cdrs.sysm.sec.auth.security.config.SecurityConstants;
import com.henc.cdrs.sysm.sec.auth.security.dto.UserContext;
import com.henc.cdrs.sysm.sec.auth.security.token.JwtToken;
import com.henc.cdrs.sysm.sec.auth.security.token.RawAccessJwtToken;
import com.henc.dream.spring.context.ApplicationContextProvider;
import com.henc.dream.util.CamelCaseMap;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public class SecurityUtil {

    public static final String JWT_ATTR_KEY = "__JWT_ATTR_KEY__";

    public static void setCookie(HttpServletResponse response, String name, String value) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    public static void setCookie(HttpServletResponse response, String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(maxAge);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    public static String getCookie(HttpServletRequest request, String name) {
        String result = "";
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (int i = 0; i < cookies.length; i++) {
                if (cookies[i].getName().equals(name)) {
                    result = cookies[i].getValue();
                }
            }
        }
        return result;
    }

    public static void deleteCookie(HttpServletResponse response, String name) {
        Cookie cookie = new Cookie(name, null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

    public static void deleteCookie(HttpServletRequest request, HttpServletResponse response, String name) {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(name)){

                cookie.setMaxAge(0);
                cookie.setValue(null);
                cookie.setPath("/");
                response.addCookie(cookie);

            }
        }
    }

    public static void removeCookie(HttpServletRequest request, HttpServletResponse response) {
        deleteCookie(request, response, SecurityConstants.CDRS_ACCESS_TOKEN);
        deleteCookie(request, response, SecurityConstants.CDRS_REFRESH_TOKEN);
    }

    public static void removeCookie(HttpServletResponse response) {
        deleteCookie(response, SecurityConstants.CDRS_ACCESS_TOKEN);
        deleteCookie(response, SecurityConstants.CDRS_REFRESH_TOKEN);
    }

    public static void createCookie(HttpServletResponse response, JwtToken accessToken, JwtToken refreshToken, UserContext userContext) {
        SecurityUtil.setCookie(response, SecurityConstants.CDRS_ACCESS_TOKEN, accessToken.getToken());
        SecurityUtil.setCookie(response, SecurityConstants.CDRS_REFRESH_TOKEN, refreshToken.getToken());
    }

    public static Jws<Claims> getToken(HttpServletRequest request) {
        JwtSettings JwtSettings = ApplicationContextProvider.getBean(JwtSettings.class);
        String tokenPayload = SecurityUtil.getCookie(request, SecurityConstants.CDRS_ACCESS_TOKEN);
        if(tokenPayload == null || "".equals(tokenPayload)) return null;
        RawAccessJwtToken rawAccessToken = new RawAccessJwtToken(tokenPayload);
        Jws<Claims> jwsClaims = rawAccessToken.parseClaims(JwtSettings.getTokenSigningKey());
        return jwsClaims;
    }

    public static UserDetail getUserContext(HttpServletRequest request) {
        Jws<Claims> jwsClaims = null;
        Claims claims = null;
        UserDetail userDetail = new UserDetail();
        userDetail.setLang(request.getLocales().nextElement().getLanguage());

        try {
            jwsClaims = (Jws<Claims>) RequestContextHolder.getRequestAttributes().getAttribute(JWT_ATTR_KEY, RequestAttributes.SCOPE_REQUEST);
            if (jwsClaims == null || jwsClaims.getBody().get("userDetail") == null) {
                throw new Exception();
            }
        } catch (Exception e) {

            jwsClaims = SecurityUtil.getToken(request);
        }

        if(jwsClaims == null) {
            return null;
        }

        claims = jwsClaims.getBody();


        Map map = (Map) claims.get("userDetail");

        userDetail.setUserId(claims.getSubject());
        userDetail.setName((String) map.get("name"));
        userDetail.setPwd((String) map.get("pwd"));
        userDetail.setPwdExpiYn((String) map.get("pwdExpiYn"));
        userDetail.setStDt((String) map.get("stDt"));
        userDetail.setEndDt((String) map.get("endDt"));
        userDetail.setLastConnTm((String) map.get("lastConnTm"));
        userDetail.setEncPwd((String) map.get("encPwd"));
        userDetail.setRoleId((String) map.get("roleId"));
        userDetail.setUserMenus((List<CamelCaseMap>) map.get("userMenus"));
        userDetail.setDeptCd((String) map.get("deptCd"));
        userDetail.setBizhdofCd((String) map.get("bizhdofCd"));
        userDetail.setCoprcpNo((String) map.get("coprcpNo"));
        userDetail.setUserDivCd((String) map.get("userDivCd"));
        userDetail.setClctAgreeYn((String) map.get("clctAgreeYn"));
        userDetail.setEntrustAgreeYn((String) map.get("entrustAgreeYn"));


        return userDetail;
    }
}
