package com.henc.cdrs.sysm.sec.auth.security.common;

/**
 * 2018-09-20 수정합니다.
 * @author YongSang
 */
//@Component
public class JwtProperties {

    static String tokenSigningKey;

    private void setTokenSigningKey(String tokenSigningKey){
        JwtProperties.tokenSigningKey = tokenSigningKey;
    }

    public String getTokenSigningKey(){
        return tokenSigningKey;
    }
}
