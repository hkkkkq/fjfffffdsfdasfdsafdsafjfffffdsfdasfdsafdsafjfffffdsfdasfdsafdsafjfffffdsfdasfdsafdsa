package com.henc.cdrs.sysm.sec.auth.security.jwt.verifier;

import org.springframework.stereotype.Component;

@Component
public class BloomFilterTokenVerifier implements TokenVerifier {
	
    public boolean verify(String jti) {
        return true;
    }
}
