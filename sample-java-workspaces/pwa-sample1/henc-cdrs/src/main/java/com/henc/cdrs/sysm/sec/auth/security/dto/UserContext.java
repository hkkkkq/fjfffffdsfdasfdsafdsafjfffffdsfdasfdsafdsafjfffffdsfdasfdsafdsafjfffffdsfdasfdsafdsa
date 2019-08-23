package com.henc.cdrs.sysm.sec.auth.security.dto;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;

import com.henc.cdrs.sysm.sec.auth.model.UserDetail;

public class UserContext {

    private final String username;
    private final UserDetail userDetail;
    private final List<GrantedAuthority> authorities;

    private UserContext(String username, UserDetail userDetail, List<GrantedAuthority> authorities) {
        this.username = username;
        this.userDetail = userDetail;
        this.authorities = authorities;
    }

    public static UserContext create(String username, UserDetail userDetail, List<GrantedAuthority> authorities) {
        if (StringUtils.isBlank(username)) throw new IllegalArgumentException("Username is blank: " + username);
        return new UserContext(username, userDetail, authorities);
    }

    public String getUsername() {
        return username;
    }

    public UserDetail getUserDetail() {
        return userDetail;
    }

    public List<GrantedAuthority> getAuthorities() {
        return authorities;
    }
}
