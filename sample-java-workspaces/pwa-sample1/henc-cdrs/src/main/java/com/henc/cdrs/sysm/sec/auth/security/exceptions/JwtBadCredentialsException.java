package com.henc.cdrs.sysm.sec.auth.security.exceptions;

import org.springframework.security.core.AuthenticationException;

import com.henc.cdrs.sysm.sec.auth.security.token.JwtToken;

public class JwtBadCredentialsException extends AuthenticationException {

	private static final long serialVersionUID = 2253741112935579531L;

    private JwtToken token;

    public JwtBadCredentialsException(String msg) {
        super(msg);
    }

    public JwtBadCredentialsException(JwtToken token, String msg, Throwable t) {
        super(msg, t);
        this.token = token;
    }

    public String token() {
        return this.token.getToken();
    }

}
