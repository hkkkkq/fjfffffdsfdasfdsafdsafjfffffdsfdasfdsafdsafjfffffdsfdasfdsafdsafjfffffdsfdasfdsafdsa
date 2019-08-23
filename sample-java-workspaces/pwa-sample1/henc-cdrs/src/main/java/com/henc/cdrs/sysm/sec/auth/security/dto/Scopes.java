package com.henc.cdrs.sysm.sec.auth.security.dto;

public enum Scopes {
	
    REFRESH_TOKEN;
    
    public String authority() {
        return "ROLE_" + this.name();
    }
}
