package com.henc.cdrs.sysm.sec.auth.security.jwt.verifier;

public interface TokenVerifier {
    public boolean verify(String jti);
}
