package com.henc.cdrs.sysm.sec.auth.model;

import com.henc.cdrs.sysm.main.menu.model.Menu;
import com.henc.dream.util.CamelCaseMap;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

public class UserDetail implements UserDetails, Serializable {

    private static final long serialVersionUID = 1L;

    private Collection<? extends GrantedAuthority> authorities;

    @Getter
    @Setter
    private String userId;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String pwd;

    @Getter
    @Setter
    private String pwdExpiYn;

    @Getter
    @Setter
    private String tlno;

    @Getter
    @Setter
    private String stDt;

    @Getter
    @Setter
    private String endDt;

    @Getter
    @Setter
    private String lastConnTm;

    @Getter
    @Setter
    private String encPwd;

    @Getter
    @Setter
    private List<CamelCaseMap> userMenus;

//    @Getter
//    @Setter
//    private List<CamelCaseMap> userDepts;

    @Getter
    @Setter
    private String lang;

    @Getter
    @Setter
    private String roleId;

    /**
     * HENC: 한화건설 임직원
     * ADMIN: 관리자(운영자)
     * COPRCP: 협력사
     */
    @Getter
    @Setter
    private String userDivCd;

    @Getter
    @Setter
    private String coprcpNo;

    /**
     * 로그인 유형(PORTAL, PMIS)
     */
    @Getter
    @Setter
    private String loginSource;

    /**
     * 사용자 상태(포탈 사용자 기준)
     * A: 사용중
     * S: 사용중지
     * D: 삭제
     */
    @Getter
    @Setter
    private String status;

    @Getter
    @Setter
    private String useYn;

    /**
     * 현재 선택된 현장 코드
     */
    @Getter
    @Setter
    private String deptCd;

    /**
     * 현재 선택된 사업본부 코드
     */
    @Getter
    @Setter
    private String bizhdofCd;


    @Getter
    @Setter
    private String pushUseYn;


    @Getter
    @Setter
    private String clctAgreeYn;

    @Getter
    @Setter
    private String entrustAgreeYn;


    @Getter
    @Setter
    private int failCnt;


    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
