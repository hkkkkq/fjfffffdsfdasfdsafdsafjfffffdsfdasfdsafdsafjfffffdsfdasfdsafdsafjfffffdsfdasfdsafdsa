package com.henc.cdrs.sysm.sec.auth.security.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
public class ErrorInterceptor implements HandlerInterceptor {

    /**
     * 컨트롤러(즉 RequestMapping이 선언된 메서드 진입) 실행 직전에 동작.
     * 반환 값이 true일 경우 정상적으로 진행이 되고, false일 경우 실행이 멈춥니다.(컨트롤러 진입을 하지 않음)
     * 전달인자 중 Object handler는 핸들러 매핑이 찾은 컨트롤러 클래스 객체
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return true;
    }

    /**
     * 컨트롤러 진입 후 view가 랜더링 되기 전 수행
     * 전달인자의 modelAndView을 통해 화면 단에 들어가는 데이터 등의 조작이 가능
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {

        if(modelAndView == null ) return;
        int status = (int) modelAndView.getModel().get("status");
        if(status >= 400 && status <= 500) {
            modelAndView.setStatus(HttpStatus.OK);
        }
    }

    /**
     * 컨트롤러 진입 후 view가 정상적으로 랜더링 된 후 제일 마지막에 실행이 되는 메서드
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {

    }


}
