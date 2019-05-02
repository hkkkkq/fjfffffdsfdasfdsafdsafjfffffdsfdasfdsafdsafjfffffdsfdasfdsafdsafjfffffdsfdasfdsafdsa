/****************************************************************************************************
 * 
 *  Copyright ⓒ 2015 kt corp. All rights reserved.
 *
 *  This is a proprietary software of kt corp, and you may not use this file except in
 *  compliance with license agreement with kt corp. Any redistribution or use of this
 *  software, with or without modification shall be strictly prohibited without prior written
 *  approval of kt corp, and the copyright notice above does not evidence any actual or
 *  intended publication of such software.
 *
 * @Desction Vo 객체 임시 저장 처리 Service
 *
 *****************************************************************************************************
 * date        Modifier Description
 *****************************************************************************************************
 * 2017.09.20. 장종호
 * 
 *****************************************************************************************************/
package com.kt.kkos.common.filter;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.MDC;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.initech.safedb.core.utils.StringUtil;
import com.kt.kkos.common.util.DynamicStaticValues;
import com.kt.kkos.itl.model.common.TrtBaseInfoDTO;
import com.nprotect.pluginfree.modules.PluginFreeRequest;

/**
 * @author Administrator
 * @deprecated 사용하지 않는 클래스
 *
 */
@Service("requestAuthenticateFilter")
public class RequestAuthenticateFilter implements Filter {
	
	private static final Logger logger = LoggerFactory.getLogger(RequestAuthenticateFilter.class);

	
	/**
	 * 입력값중에 찾을 문자열
	 */
	private static final String FND_KEY_STRING 	= "\"trtBaseInfoDTO\"";
	
	/**
	 * JSON 포멧 시작 문자열
	 */
	private static final String FND_START_KEY	= "{";
	
	/**
	 * JSON 포멧 종료 문자열
	 */
	private static final String FND_END_KEY		= "}";
	
	
	
	
	
	@Value("${SYSTarget}")
	private String sysTarget;
	
	/* (non-Javadoc)
	 * @see javax.servlet.Filter#destroy()
	 */
	@Override
	public void destroy()
	{
		logger.debug("destroy");
	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
		
		//logger.debug("===== doFilter SKIP");
		
		HttpServletRequest  httpRequest  = (HttpServletRequest)  request;
		HttpServletResponse httpResponse = (HttpServletResponse) response; 
		
		Enumeration<String> params1 = (Enumeration<String>)httpRequest.getParameterNames();
		List<String> list1 = Collections.list(params1);
		logger.debug("==================================================================");
		logger.debug("# httpRequest getParameterNames");
		for(String name : list1) {
			String values1[] = request.getParameterValues(name);
			if(values1 != null && values1.length > 0) {
				for(String value : values1) {
					logger.debug("# " + name + "=" + value);
				}
			}	
		}
		logger.debug("==================================================================");

		
		//2018.04.11 전원호(82023121) 추가	
		String requestURI = httpRequest.getRequestURI();
		String kkosServiceCallBlockYn = DynamicStaticValues.getInstance().getKkosServiceCallBlockYn();
		String kkosBmonCallBlockYn = DynamicStaticValues.getInstance().getKkosBmonCallBlockYn();
		
		logger.debug("==================================================================");
		logger.debug("# sysTarget: " + sysTarget);
		logger.debug("# Request URI: " + requestURI);
		//logger.debug("  PathInfo              : " + httpRequest.getPathInfo());
		logger.debug("# KkosServiceCallBlockYn: " + kkosServiceCallBlockYn);
		logger.debug("# KkosBmonCallBlockYn: " + kkosBmonCallBlockYn);
		logger.debug("==================================================================");
			
		// 2018.04.11 전원호(82023121) 추가
		// com.kt.kkos.common.util.DynamicStaticValues.java의 설정 값에 따라 KKOS 서비스 호출을 제어 모듈 추가
		if ( ("Y".equals(kkosServiceCallBlockYn)) && (requestURI.contains("/KKOS/services/")) ) {
			logger.debug("### KKOS Service Block ###");
			httpResponse.sendError(503,"KKOS Service is unavailable due to a temporary intent");
		} 
		// 2018.06.07 전원호(82023121) 추가
		// 보안키패드 적용 암호화 대상 필드 복호화
		
		HttpServletRequest  httpRequest2 = new PluginFreeRequest(httpRequest);
		
		Enumeration<String> params2 = (Enumeration<String>)httpRequest2.getParameterNames();
		List<String> list2 = Collections.list(params2);	
		logger.debug("==================================================================");
		logger.debug("# httpRequest2 getParameterNames");
		for(String name : list2) {	
			String values2[] = request.getParameterValues(name);
			if(values2 != null && values2.length > 0) {		
				for(String value : values2) {
					logger.debug("# " + name + "=" + value);
				}
			}	
		}
		logger.debug("==================================================================");
		
		
		
		
		
		logHeaderInitialize(httpRequest, httpResponse, filterChain);
		
		
		
//		if ( canAccessible(httpRequest, httpResponse) ) {
//
//			/*
//			 * logHeader 까지 최종 처리하고 나서, filterCahin.doFilter으로 뒤쪽 로직에 최종 값을 전달함. 
//			 */
//			logHeaderInitialize(httpRequest, httpResponse, filterChain);
//			
//		} else {
//			
//			httpResponse.sendError(405);
//		}
		
		
	}
	
	

	/**
	 * <pre>
	 * Log Header에 사용자 ID 강제 지정 처리
	 * </pre>
	 *
	 * @param httpRequest
	 * @param httpResponse
	 * @param filterChain
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void logHeaderInitialize(HttpServletRequest httpRequest, HttpServletResponse httpResponse, FilterChain filterChain) throws IOException, ServletException {

		// IO Stream 을 읽고나면, read offset 이동으로 인해 다시 읽을 수 없는 문제를 예방함.
		RereadableRequestWrapper rereadableRequestWrapper = new RereadableRequestWrapper((HttpServletRequest)httpRequest);
		
		int keyIdx = -1, startIdx = -1, endIdx = -1;
		StringBuilder buffer = new StringBuilder(IOUtils.toString(rereadableRequestWrapper.getReader()));
		
		// trtBaseInfoDTO JSON 포멧이 존재하는 경우만 객체를 취득함.
		keyIdx = buffer.indexOf(FND_KEY_STRING);
		if ( keyIdx > -1 ) {
			
			startIdx = buffer.indexOf(FND_START_KEY, startIdx + FND_KEY_STRING.length());
			
			if ( startIdx > -1 ) {
				
				endIdx = buffer.indexOf(FND_END_KEY, startIdx) + 1;
			}
		}
		
		// URLEncode 된 경우 위에서 못찾음. 따라서, URLDecode를 거쳐 다시 한번 확인 함.
		if ( keyIdx == -1 ) {
			
			try {
				buffer = new StringBuilder(URLDecoder.decode(buffer.toString(), "UTF-8"));
			} catch (Exception e) { // NOPMD URLDecoding 에 오류 발생시, 별도로 trtBaseInfoDTO를 찾지 않음.
				keyIdx = -1;
			}
			
			// trtBaseInfoDTO JSON 포멧이 존재하는 경우만 객체를 취득함.
			keyIdx = buffer.indexOf(FND_KEY_STRING);
			if ( keyIdx > -1 ) {
				
				startIdx = buffer.indexOf(FND_START_KEY, startIdx + FND_KEY_STRING.length());
				
				if ( startIdx > -1 ) {
					
					endIdx = buffer.indexOf(FND_END_KEY, startIdx) + 1;
				}
			}
		}
		
		// trtBaseInfoDTO JSON 포멧이 존재하는 경우 LOG4J 포멧 설정.
		if ( keyIdx > -1 && startIdx > -1 && endIdx > -1 ) { 
			
			TrtBaseInfoDTO trtBaseInfoDTO = new ObjectMapper().readValue(buffer.substring(startIdx, endIdx), TrtBaseInfoDTO.class);
			
			//  userId 설정
			String userId = trtBaseInfoDTO.getUserId();

			MDC.put("userId", userId);
		}
		
		filterChain.doFilter(rereadableRequestWrapper, httpResponse);
	}
	
	/**
	 * <pre>
	 * 접속환경별로 PAD, Web에 대한 접속 가능 여부 확인.
	 * </pre>
	 *
	 * @param httpRequest
	 * @param httpResponse
	 * @return
	 */
	private boolean canAccessible(HttpServletRequest httpRequest, HttpServletResponse httpResponse) {
		
		boolean isAccessible = false;
		
		String reqURI = httpRequest.getRequestURI();
		logger.debug("Connect URI:" + reqURI);
		
		// Cookie 조회
//		Cookie [] coks = httpRequest.getCookies();
//		if (coks != null) {
//			for (int jj = 0; jj < coks.length; jj++) {
//				logger.debug("[Cookie] " + coks[jj].getName() + " : " + coks[jj].getValue());
//			}
//		}
		
		// HttpSession 에 사용자 ID 미 관리 시 접근 에러 처리 
		HttpSession ses = httpRequest.getSession();
		
		if (ses != null) {
			logger.debug("Session ID:" + ses.getId());
			logger.debug("Session KkosUserId:" + ses.getAttribute("KkosUserId"));
		}
		
		if ( ".do".equals(reqURI.substring(reqURI.length()-3)) 
				&& !("/KKOS/services/startup.do".equals(reqURI) 
						|| "/KKOS/services/authSSO.do".equals(reqURI) 
						|| "/KKOS/services/main.do".equals(reqURI)
						|| "/KKOS/services/registAppSession.do".equals(reqURI) 
						|| "/KKOS/services/deleteAppSession.do".equals(reqURI) )) {
			if (ses == null) {
				logger.debug("Session is NULL");
				return false;
			}
			
			if (ses.getAttribute("KkosUserId") == null) {
				logger.debug("Session KkosUserId is NULL");
				return false;
			}
		}
		
		switch (sysTarget)
		{
			case "real":
			case "test":
				
				/*
				 * Manual Batch 호출인 경우 App/Web 확인 보다 우선 하여, 수행 가능하게 처리함.
				 */
				String requestUri = httpRequest.getRequestURI();
				if ( !StringUtil.isEmpty(requestUri) && requestUri.matches(".*batch/ManualBatchInvokerSO.*") ) {
					
					logger.info("Start ManualBatch !!");
					isAccessible = true;
					break;
				}
				
				/*
				 * SW-VPN 웹화면에서 암호화된 SSO 응답 SAML을 해석하여, email 추출 및 A-KOS 앱 호출
				 */
				if ( !StringUtil.isEmpty(requestUri) && requestUri.matches("/KKOS/services/HandleSSO.do") ) {
					
					logger.info("Start /KKOS/services/HandleSSO.do !!");
					isAccessible = true;
					break;
				}
				
				/*
				 * Manual Batch를 제외한 그외의 경우 
				 * 모든 입력값에 App 호출 여부인지를 확인하고 시스템 처리를 수행함.
				 */
				Enumeration<String> headerNames = httpRequest.getHeaderNames();
				while ( headerNames.hasMoreElements() ) {
					String name  = headerNames.nextElement();
					String value = httpRequest.getHeader(name).toLowerCase();
					
					
					if ( "user-agent".equals(name.toLowerCase()) ) {
						
						logger.debug("user-agent:" + value);
						
						// Web Browser 또는 Web을 통한 IPAD 가상 연결 등인 경우 접속을 차단함. 실제 IPad APP 접속일 때만 처리 함.
						if ( value.matches(".*applaunch.*|.*akosapp.*") ) {
							
							logger.info("App Execute");
							
							isAccessible = true;
							break;
							
						} else {
							
							logger.error("Web Execute");
							
							isAccessible = false;
							break;
						}
					}
				}
				
				break;
			
			// PRD(real, test) 환경 외에는 별도 확인하지 않음.
			case "dev" :
			case "local" :
				
				isAccessible = true;
				break;

			// 그 외 지정 되지 않은 환경은 접속을 허용하지 않음.
			default:

				isAccessible = false;
				break;
		}
		
		return isAccessible;
		
	}
	
	
	

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	@Override
	public void init(FilterConfig arg0) throws ServletException
	{
//		logger.debug("init");
	}
}
