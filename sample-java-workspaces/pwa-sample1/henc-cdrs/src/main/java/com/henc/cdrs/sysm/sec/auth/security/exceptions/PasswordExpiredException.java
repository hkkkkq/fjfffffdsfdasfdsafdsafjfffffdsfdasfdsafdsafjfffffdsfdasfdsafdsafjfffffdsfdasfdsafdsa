package com.henc.cdrs.sysm.sec.auth.security.exceptions;

import org.springframework.security.core.AuthenticationException;

public class PasswordExpiredException extends AuthenticationException {

    private static final long serialVersionUID = 8691907715540897094L;
    public PasswordExpiredException(String msg) {
        super(msg);
    }
}
