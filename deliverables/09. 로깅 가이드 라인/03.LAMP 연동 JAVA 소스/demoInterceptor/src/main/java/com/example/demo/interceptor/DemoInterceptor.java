package com.example.demo.interceptor;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.annotation.LampLog;
import com.example.demo.common.WebConstant;
import com.example.demo.logger.LampLogVo;
import com.example.demo.logger.LampLogger;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class DemoInterceptor implements HandlerInterceptor {
	
	private LampLogger lampLogger;
	
	@PostConstruct
    public void postConstruct(){
        this.lampLogger = new LampLogger();
    }

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		String methodName = "";
		if(handler instanceof HandlerMethod) {
			HandlerMethod method = (HandlerMethod) handler;
			methodName = method.getMethod().getName();
		}
    	
        String transactionId = UUID.randomUUID().toString();
        String requestTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());
        
        // in_res를 위해 request attribute에 설정
        request.setAttribute("LAMP_TRANSACTION_ID", transactionId);
        request.setAttribute("LAMP_REQUEST_TIME", requestTime);
        request.setAttribute("LAMP_OPERATION", methodName);

        // log class 생성
        LampLogVo logVo = new LampLogVo();

        // timestamp
        logVo.setTimestamp(requestTime);

        // LAMP Service-Code
        logVo.setService(WebConstant.LAMP_SERVICE_CODE);

        // operation (Controller의 메소드 명으로 정의)
        logVo.setOperation(methodName);

        // transactionId
        logVo.setTransactionId(transactionId);

        // logType
        logVo.setLogType(LampLog.Type.IN_REQ.name());

        /*
        // payload 생성 : 사용자 정의 (IN_REQ: method + requestUrl + requestParam으로 정의)
        Map<String, Object> payloadMap = new LinkedHashMap<String, Object>();

        payloadMap.put("method", request.getMethod());
        payloadMap.put("requestUrl", request.getRequestURL());
        payloadMap.put("params", request.getParameterMap());
        logVo.setPayload(new ObjectMapper().writeValueAsString(payloadMap));
        */

        // host
        LampLogVo.HostVo hostVo = logVo.createInstance(LampLogVo.HostVo.class);
        //hostVo.setName(SystemUtil.getHostName());
        try {
			hostVo.setName(InetAddress.getLocalHost().getHostName());
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        hostVo.setIp(request.getRemoteAddr());
        logVo.setHost(hostVo);

        // user
        LampLogVo.UserVo userVo = logVo.createInstance(LampLogVo.UserVo.class);

        // 세션에서 사용자 ID 정보 가져와서 세팅
        //userVo.setId(LampLog.Operation.LOGIN.equals(annotation.operation()) ? request.getParameter("userId") : this.getPrincipal());
        userVo.setId("testuser");
        userVo.setIp(request.getRemoteAddr());
        logVo.setUser(userVo);

        try {
			lampLogger.txLog(logVo);
			return true;
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView model) throws Exception {
		
		HandlerMethod method = null;
		if(handler instanceof HandlerMethod) {
			method = (HandlerMethod) handler;
			method.getMethod().getAnnotation(LampLog.class);
		}

        String transactionId = (String) request.getAttribute("LAMP_TRANSACTION_ID");
        LampLog annotation = method.getMethod().getAnnotation(LampLog.class);
        String operation = (String) request.getAttribute("LAMP_OPERATION");
        String requestTime = (String) request.getAttribute("LAMP_REQUEST_TIME");

        String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());
        long responseTime = 0;
        //responseTime = DateUtil.getElaspedTime(requestTime, currentTime);

        // log vo 생성
        LampLogVo logVo = new LampLogVo();

        // timestamp
        logVo.setTimestamp(currentTime);

        // Lamp Service-Code
        logVo.setService(WebConstant.LAMP_SERVICE_CODE);

        // operation 명
        logVo.setOperation(operation);

        // transactionId
        logVo.setTransactionId(transactionId);

        // logType
        logVo.setLogType(LampLog.Type.IN_RES.name());

        /*
        // payload 생성 (json일 경우 method + returnJson, view일 경우 method + returnPage + modelParam으로 정의)
        // 일반 page 호출 시
        for (Object obj : joinPoint.getArgs()) {
            if (obj instanceof BindingAwareModelMap) {
                Map<String, Object> payloadMap = new LinkedHashMap<String, Object>();
                payloadMap.put("method", request.getMethod());
                payloadMap.put("returnPage", returnObject);
                payloadMap.put("modelParam", obj);
                //logVo.setPayload(payloadMap);
                logVo.setPayload(new ObjectMapper().writeValueAsString(payloadMap));
            }
        }

        // json 리턴 시
        if (returnObject instanceof ResponseEntity) {
            Map<String, Object> payloadMap = new LinkedHashMap<String, Object>();
            payloadMap.put("method", request.getMethod());
            ResponseEntity<?> responseEntity = (ResponseEntity<?>) returnObject;
            payloadMap.put("returnJson", responseEntity.getBody());

            //logVo.setPayload(payloadMap);
            logVo.setPayload(new ObjectMapper().writeValueAsString(payloadMap));
        }
        */

        // host
        LampLogVo.HostVo hostVo = logVo.createInstance(LampLogVo.HostVo.class);
        hostVo.setName("HostName");
        hostVo.setIp(request.getRemoteAddr());
        logVo.setHost(hostVo);

        // response 생성
        LampLogVo.ResponseVo responseVo = logVo.createInstance(LampLogVo.ResponseVo.class);
        responseVo.setType("");
        responseVo.setCode(WebConstant.SUCCESS);   // WebConstant.FAILURE
        responseVo.setDuration(String.valueOf(responseTime));
        logVo.setResponse(responseVo);

        // user
        LampLogVo.UserVo userVo = logVo.createInstance(LampLogVo.UserVo.class);
        // 세션 등 에서 사용자 ID 정보 가져와서 세팅 가능

        String loginUserId = "testUser";   // request.getParameter("userId")
        userVo.setId(loginUserId);
        userVo.setIp(request.getRemoteAddr());
        logVo.setUser(userVo);

        // 다음과 같은 경우는 security 정보를 추가함
        // 보안로그 유형: ACCESS(사용자 접속로그), PRIV(개인정보처리로그), MNGT(개인정보취급자 관리 로그)
        secureValid : {
            LampLogVo.SecureVo secureVo = logVo.createInstance(LampLogVo.SecureVo.class);
//            secureVo.setType(annotation.secureType().name());
//            secureVo.setEvent(annotation.secureEventType().name());
            ObjectMapper objectMapper = new ObjectMapper();

            if (LampLog.Operation.LOGIN.value().equals(operation)) {
                /** 보안로그 - 로그인 */
                secureVo.setReason("로그인");
                secureVo.setTarget(loginUserId);
                if(userVo != null) secureVo.setDetail(objectMapper.writeValueAsString(userVo));

            } else if (LampLog.Operation.LOGOUT.value().equals(operation)) {
                /** 보안로그 - 로그아웃 */
                secureVo.setReason("로그아웃");
                secureVo.setTarget(loginUserId);
                if(userVo != null) secureVo.setDetail(objectMapper.writeValueAsString(userVo));

            } else if (LampLog.Operation.USER_AUTH.value().equals(operation)) {
                /** 보안로그 - 권한승인 */
                secureVo.setReason("권한승인");
            } else if (LampLog.Operation.USER_INFO.value().equals(operation)) {
                /** 보안로그 - 권한승인 */
                if(LampLog.SecureType.PRCS.equals(annotation.secureType())){
                    secureVo.setTarget(loginUserId);
                    secureVo.setDetail(objectMapper.writeValueAsString(userVo));
                } else if(LampLog.SecureType.MNGT.equals(annotation.secureType())){
                    //관리자 특정사용자 조회
                    secureVo.setTarget(null);
                    secureVo.setDetail(null);
                }
            } else if (LampLog.Operation.ACCESS_LOG.value().equals(operation)) {
                /** 보안로그 - 사용자 접속로그조회 */
            } else if (LampLog.Operation.PRIVATE_LOG.value().equals(operation)) {
                /** 보안로그 - 보안 로그조회 */
            } else if (LampLog.Operation.MANAGEMENT_LOG.value().equals(operation)) {
                /** 보안로그 - 관리자 로그조회 */
            } else if (LampLog.Operation.USER_LIST.value().equals(operation)) {
                /** 보안로그 - 사용자 목록 */
            } else if (LampLog.Operation.LOGIN_LOG.value().equals(operation)) {
                /** 보안로그 - 로그인 이력조회 */
            }

            logVo.setSecurity(secureVo);
        }

        try {
			lampLogger.txLog(logVo);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception exception) throws Exception {
		// TODO Auto-generated catch block
	}
}
