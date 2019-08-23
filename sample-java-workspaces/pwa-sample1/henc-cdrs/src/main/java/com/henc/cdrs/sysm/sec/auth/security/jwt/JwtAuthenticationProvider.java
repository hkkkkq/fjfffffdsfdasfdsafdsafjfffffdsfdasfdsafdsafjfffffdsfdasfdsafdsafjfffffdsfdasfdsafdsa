package com.henc.cdrs.sysm.sec.auth.security.jwt;

import com.henc.cdrs.sysm.sec.auth.model.UserDetail;
import com.henc.cdrs.sysm.sec.auth.security.auth.AwareAuthenticationFailureHandler;
import com.henc.cdrs.sysm.sec.auth.security.common.SecurityUtil;
import com.henc.cdrs.sysm.sec.auth.security.config.JwtSettings;
import com.henc.cdrs.sysm.sec.auth.security.config.SecurityConstants;
import com.henc.cdrs.sysm.sec.auth.security.dto.UserContext;
import com.henc.cdrs.sysm.sec.auth.security.exceptions.InvalidJwtToken;
import com.henc.cdrs.sysm.sec.auth.security.exceptions.JwtBadCredentialsException;
import com.henc.cdrs.sysm.sec.auth.security.exceptions.JwtExpiredTokenException;
import com.henc.cdrs.sysm.sec.auth.security.jwt.verifier.TokenVerifier;
import com.henc.cdrs.sysm.sec.auth.security.service.AuthenticationUsersService;
import com.henc.cdrs.sysm.sec.auth.security.token.JwtToken;
import com.henc.cdrs.sysm.sec.auth.security.token.JwtTokenFactory;
import com.henc.cdrs.sysm.sec.auth.security.token.RawAccessJwtToken;
import com.henc.cdrs.sysm.sec.auth.security.token.RefreshToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.Map;

@Slf4j
@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private static Logger logger = LoggerFactory.getLogger(AwareAuthenticationFailureHandler.class);

    @Autowired
    private JwtTokenFactory tokenFactory;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpServletResponse response;
    @Autowired
    private AuthenticationUsersService authenticationUsersService;
    @Autowired
    private TokenVerifier tokenVerifier;

    private final JwtSettings jwtSettings;

    private Collection<GrantedAuthority> authorities;

    @Autowired
    public JwtAuthenticationProvider(JwtSettings jwtSettings) {
        this.jwtSettings = jwtSettings;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        try {
            RawAccessJwtToken rawAccessToken = (RawAccessJwtToken) authentication.getCredentials();

            if (StringUtils.isEmpty(rawAccessToken.getToken())) {
                throw new JwtBadCredentialsException("JWT String argument cannot be null or empty");

            } else {
                try{
                    request.getParameter("storageDeptCd");
                }catch(Exception e){
                    return authentication;
                }


                Jws<Claims> jwsClaims = rawAccessToken.parseClaims(jwtSettings.getTokenSigningKey());
                Claims claims = jwsClaims.getBody();
                Map map = (Map) claims.get("userDetail");
                String tokenDeptCd = (String) map.get("deptCd");
                String changeDeptCd = request.getParameter("storageDeptCd");
                // 메인 현장을 변경할 경우 refreshToken을 진행한다.
                if (changeDeptCd != null && !tokenDeptCd.equals(changeDeptCd)) {
                    return refreshToken();
                }
            }

            return authentication;

        } catch (JwtExpiredTokenException e) {
            logger.error(getClass().getName() + ".authenticate : " + e.getMessage());
            return refreshToken();
        }

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (JwtAuthenticationToken.class.isAssignableFrom(authentication));
    }

    public JwtAuthenticationToken refreshToken() {
        String tokenPayload = SecurityUtil.getCookie(request, SecurityConstants.CDRS_REFRESH_TOKEN);

        RawAccessJwtToken rawToken = new RawAccessJwtToken(tokenPayload);
        RefreshToken rfToken = RefreshToken.create(rawToken, jwtSettings.getTokenSigningKey()).orElseThrow(() -> new InvalidJwtToken());

        String jti = rfToken.getJti();
        if (!tokenVerifier.verify(jti)) {
            throw new InvalidJwtToken();
        }

        String username = rfToken.getSubject();
        UserDetail userDetail = authenticationUsersService.whoAreYou(username);
        if (null != request.getParameter("storageDeptCd")) {
            // 메인현장을 변경할 경우 선택된 현장코드를 셋업한다.
            userDetail.setDeptCd(request.getParameter("storageDeptCd"));
        }
        UserContext userContext = authenticationUsersService.createUserContext(userDetail);

        JwtToken accessToken = tokenFactory.createAccessJwtToken(userContext);
        JwtToken refreshToken = tokenFactory.createRefreshToken(userContext);

        RawAccessJwtToken rawAccessJwtToken = new RawAccessJwtToken(accessToken.getToken());
        Jws<Claims> claims = rawAccessJwtToken.parseClaims(jwtSettings.getTokenSigningKey());
        RequestContextHolder.getRequestAttributes().setAttribute(SecurityUtil.JWT_ATTR_KEY, claims, RequestAttributes.SCOPE_REQUEST);

        SecurityUtil.createCookie(response, accessToken, refreshToken, userContext);

        return new JwtAuthenticationToken(userContext, userContext.getAuthorities());

    }

}
