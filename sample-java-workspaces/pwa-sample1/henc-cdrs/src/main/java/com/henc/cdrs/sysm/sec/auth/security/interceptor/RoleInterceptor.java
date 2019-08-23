package com.henc.cdrs.sysm.sec.auth.security.interceptor;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.henc.cdrs.common.web.BaseController;
import com.henc.cdrs.sysm.main.menu.model.Menu;
import com.henc.cdrs.sysm.sec.auth.model.UserDetail;
import com.henc.dream.util.CamelCaseMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeType;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Slf4j
@Component
public class RoleInterceptor implements HandlerInterceptor {

    /**
     * 컨트롤러(즉 RequestMapping이 선언된 메서드 진입) 실행 직전에 동작.
     * 반환 값이 true일 경우 정상적으로 진행이 되고, false일 경우 실행이 멈춥니다.(컨트롤러 진입을 하지 않음)
     * 전달인자 중 Object handler는 핸들러 매핑이 찾은 컨트롤러 클래스 객체
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String accept = request.getHeader("accept");

        if(accept.indexOf(MediaType.APPLICATION_JSON_VALUE) >= 0
         || accept.indexOf(MediaType.TEXT_PLAIN_VALUE) >= 0){
            return true;
        }

        BaseController baseController = new BaseController();
        UserDetail userDetail = baseController.getUserContext(request);
        if(userDetail == null){
            return true;
        }

        int cnt = 0;
//        if ("ROLE_ADMIN".equals(userDetail.getRoleId())) {
//            return true;
//        }
        ObjectMapper mapper = new ObjectMapper();

        List<CamelCaseMap> userMenus = mapper.convertValue( userDetail.getUserMenus(), new TypeReference<List<CamelCaseMap>>(){});

        for (CamelCaseMap menu : userMenus) {
            String uriNm = menu.getString("urinm").substring(1);
            if(request.getRequestURI().indexOf(uriNm, 1) >= 0){
                cnt ++;
            }
        }

        if(cnt > 0){
            return true;
        }else{
            response.sendRedirect( "/");
            return false;
        }
    }

    /**
     * 컨트롤러 진입 후 view가 랜더링 되기 전 수행
     * 전달인자의 modelAndView을 통해 화면 단에 들어가는 데이터 등의 조작이 가능
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {

    }

    /**
     * 컨트롤러 진입 후 view가 정상적으로 랜더링 된 후 제일 마지막에 실행이 되는 메서드
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {

    }


}
