package com.henc.cdrs.sysm.sec.support;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.henc.cdrs.common.web.BaseController;
import com.henc.cdrs.sysm.sec.auth.model.UserDetail;

@Component
public class UserDetailMethodArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        BaseController baseController = new BaseController();
        return baseController.getUserContext(webRequest.getNativeRequest(HttpServletRequest.class));
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return UserDetail.class.isAssignableFrom(parameter.getParameterType());
    }
}