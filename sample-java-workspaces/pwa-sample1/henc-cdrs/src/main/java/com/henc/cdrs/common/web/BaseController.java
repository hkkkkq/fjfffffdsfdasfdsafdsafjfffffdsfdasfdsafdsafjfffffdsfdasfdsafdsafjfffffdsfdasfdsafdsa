package com.henc.cdrs.common.web;

import com.henc.cdrs.sysm.sec.auth.model.UserDetail;
import com.henc.cdrs.sysm.sec.auth.security.common.SecurityUtil;

import javax.servlet.http.HttpServletRequest;

public class BaseController {

	public UserDetail getUserContext(HttpServletRequest request) {
        return SecurityUtil.getUserContext(request);
	}
}
