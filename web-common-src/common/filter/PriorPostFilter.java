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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

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
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.google.gson.JsonObject;
import com.kt.kkos.common.content.BMONSender;
import com.kt.kkos.common.content.ContentSecure;
import com.kt.kkos.common.enc.Seed256;
import com.kt.kkos.common.util.Constants;
import com.kt.kkos.common.util.DynamicStaticValues;
import com.kt.kkos.common.util.GlobalNoGenerator;
import com.kt.kkos.common.util.ITLErrMesgs;
import com.kt.kkos.common.util.ObjectConverter;
import com.kt.kkos.common.util.StringUtil;
import com.kt.kkos.exception.ITLBusinessException;
import com.kt.kkos.itl.bo.common.PadTokenChkBO;
import com.kt.kkos.itl.boc.common.TmpSaveInfoBOC;
import com.kt.kkos.itl.model.common.KkosBaseInfoVO;
import com.kt.kkos.itl.model.common.TrtBaseInfoDTO;
import com.kt.kkos.itl.model.common.TrtErrInfoDTO;
import com.kt.kkos.itl.model.nicg.IcgTrtInVO;
import com.kt.kkos.itl.model.nnsb.NewSbscTrtInfoVO;
import com.nprotect.common.json.ParseException;
import com.nprotect.pluginfree.PluginFree;
import com.nprotect.pluginfree.modules.PluginFreeRequest;
import com.nprotect.pluginfree.util.RequestUtil;

/**
 * @author Administrator
 *
 */
/**
 * @author User
 *
 */
@Service("priorPostFilter")
public class PriorPostFilter implements Filter {
	
	private static final Logger logger = LoggerFactory.getLogger(PriorPostFilter.class);
	
	final static String ACCUM_VO_JSON_FIELD_NM = "voData";
	
	final static String ALLOW_MAP_KEY_ALLOWYN = "allowYn";  //계속 진행여부를 의미
	final static String ALLOW_MAP_KEY_ERRTYPE = "errType";
	final static String ALLOW_MAP_KEY_ERRCD   = "errCd";
	final static String ALLOW_MAP_KEY_ERRMESG = "errMesg";
	
	@Autowired
	private ContentSecure contentSecure;
	
	@Autowired
	private TmpSaveInfoBOC tmpSaveInfoBOC;
	
	@Autowired
	private BMONSender bmonSender;
	
	@Autowired
	PadTokenChkBO padTokenChkBO;
	
	
	@Value("${SYSTarget}")
	private String sysTarget;
	
	@Value("${jboss.node.name}")
	private String nodeName;
	
	/* (non-Javadoc)
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	@Override
	public void init(FilterConfig arg0) throws ServletException
	{
		logger.debug("PriorPostFilter init");
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, arg0.getServletContext());
	}
	
	
	/* (non-Javadoc)
	 * @see javax.servlet.Filter#destroy()
	 */
	@Override
	public void destroy()
	{
		logger.debug("PriorPostFilter destroy");
	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException
	{
		logger.debug("(doFilter) Start ===" + Thread.currentThread().getId());
		
		HttpServletRequest  httpRequest  = (HttpServletRequest)  request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;

		String tmpStr;
		
		/*
		 * RequestContextHolder 와 HttpServletRequest 동기화
		 *   - HttpServletRequest 를 사용할 수 없는 곳에서 RequestContextHolder 를 사용
		*/
		ServletRequestAttributes servletRequestAttributes = new ServletRequestAttributes(httpRequest);
		RequestContextHolder.setRequestAttributes(servletRequestAttributes);


		String requestURI = httpRequest.getRequestURI();
		
		// 로그 확인용
		logger.info("==================================================================");
		logger.info("[doFilter] # sysTarget: " + sysTarget);
		logger.info("[doFilter] # Request URI: " + requestURI);
		logger.info("[doFilter] request.getQueryString() ====" + httpRequest.getQueryString());
		logger.info("[doFilter] request.getContentLength() ====" + httpRequest.getContentLength());
		logger.info("[doFilter] request.getMethod() ====" + httpRequest.getMethod());
		logger.info("[doFilter] request.getContentType() ====" + httpRequest.getContentType());
		logger.info("[doFilter] request.getCharacterEncoding() ====" + httpRequest.getCharacterEncoding());
		logger.info("[doFilter] request.getHeader(\"User-Agent\") ====" + httpRequest.getHeader("User-Agent"));
		logger.info("------------------------------------------------------------------");
		logger.info("### Blocking Info");
		logger.info("# KkosBmonCallBlockYn: " + DynamicStaticValues.getInstance().getKkosBmonCallBlockYn());
		logger.info("# KkosServiceCallBlockYn: " + DynamicStaticValues.getInstance().getKkosServiceCallBlockYn());
		logger.info("# kkosServiceCallBlockChhnlTypeYn: " + DynamicStaticValues.getInstance().getKkosServiceCallBlockChnlTypeYn());
		logger.info("# kkosServicePermittedChhnlTypeList: " + DynamicStaticValues.getInstance().getKkosServicePermittedChnlTypeList());
		logger.info("# kkosServiceNacBlockYn: " + DynamicStaticValues.getInstance().getKkosServiceNacBlockYn());
		logger.info("# kkosServiceMnpBlockYn: " + DynamicStaticValues.getInstance().getKkosServiceMnpBlockYn());
		logger.info("# kkosServiceHcnBlockYn: " + DynamicStaticValues.getInstance().getKkosServiceHcnBlockYn());
		logger.info("# kkosServiceRslBlockYn: " + DynamicStaticValues.getInstance().getKkosServiceRslBlockYn());
		logger.info("------------------------------------------------------------------");
		Enumeration<String> params1 = (Enumeration<String>)httpRequest.getParameterNames();
		List<String> list1 = Collections.list(params1);
		logger.debug("### httpRequest getParameterNames");
		for(String name : list1) {
			String values1[] = request.getParameterValues(name);
			if(values1 != null && values1.length > 0) {
				for(String value : values1) {
					logger.debug("# Param [name]" + name + "=[value]" + value);
				}
			}	
		}	
		logger.debug("------------------------------------------------------------------");
		logger.debug("### httpRequest getHeaders");
		Enumeration enuHdr = httpRequest.getHeaderNames();
		while (enuHdr.hasMoreElements()) {
			tmpStr = (String)enuHdr.nextElement();
			logger.debug("# Header [name]" + tmpStr + "=[value]" + httpRequest.getHeader(tmpStr));
		}	
		logger.info("------------------------------------------------------------------");
		logger.info("### HttpSessions Info");
		HttpSession httpSession = httpRequest.getSession();
		if (httpSession != null) {
			logger.info("# HttpSessions ID : " + httpSession.getId());
			Enumeration enuSes = httpSession.getAttributeNames();
			while (enuSes.hasMoreElements()) {
				tmpStr = (String)enuSes.nextElement();
//				logger.debug("# HttpSessions [name]" + tmpStr + "=[value]" + httpSession.getAttribute(tmpStr));
				logger.info("# HttpSessions [name]" + tmpStr);
			}
		}
		logger.info("==================================================================");
		
		try {
			tmpStr =  httpRequest.getContentType() == null ? "" : httpRequest.getContentType();
			if (tmpStr.toLowerCase().indexOf(Constants.COTENT_JSON) == 0) {
				doProcessJson(httpRequest, httpResponse, filterChain);
			}
			else {
				doProcessDefault(httpRequest, httpResponse, filterChain);
			}
		}
		catch (ITLBusinessException e) {
			e.printITLBusinessException();
			ITLBusinessException.printErrorStack(e);
			Map<String, String> allowMap = new HashMap<String, String>();
			allowMap.put(ALLOW_MAP_KEY_ERRTYPE, e.getResponseType());
			allowMap.put(ALLOW_MAP_KEY_ERRCD, "500");
			allowMap.put(ALLOW_MAP_KEY_ERRMESG, e.getResponseBasc());
			sendError(httpRequest, httpResponse, allowMap);
			return;
		}
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
	 * @throws ITLBusinessException 
	 * @throws ParseException 
	 */
	@SuppressWarnings("unchecked")
	private void doProcessJson(HttpServletRequest httpRequest, HttpServletResponse httpResponse, FilterChain filterChain) throws ITLBusinessException {

		logger.debug("[doProcessJson] Start....." + httpRequest.getRequestURI());
		String requestUri = httpRequest.getRequestURI();
		
		/*
		 * Secure Out 처리 SKIP (임시저장 데이터 조회등 저장된 데이터가 SecureOut 용 데이터로 저장된 상태임)
		 *   - 임시저장 데이터 조회의 경우 Secure Out 상태임
		*/
		boolean SKIP_SECURE_OUT = false;
		
		switch (requestUri) {
			case "/KKOS/services/NacTmpSaveDtlRetv.do" :	// 신규개통 임시저장 조회 서비스
			case "/KKOS/services/HcnTmpSaveDtlRetv.do" :	// 기기변경 임시저장 조회 서비스
				SKIP_SECURE_OUT = true;
				break;
			default :
				SKIP_SECURE_OUT = false;
				break;
		}
		
		/*
		 * Response & Request Wrapping 및 InputStream Data 를 JSONObject 으로 변환
		 */
		// HttpResponse Wrapping
		ResponseWrapper responseWrapper = new ResponseWrapper((HttpServletResponse)httpResponse);
		
		// HttpRequest Wrapping & HttpBody Data 추출
		RequestWrapper requestWrapper = null;
		StringBuilder buffer = null;
		try {
			requestWrapper = new RequestWrapper((HttpServletRequest)httpRequest);
			buffer = new StringBuilder(IOUtils.toString(requestWrapper.getReader()));
		} catch (IOException e1) {
			ITLBusinessException.printErrorStack(e1);
			ITLBusinessException itlBusinessException = new ITLBusinessException(ITLErrMesgs.RESPONSE_TYPE_SYSERR, ITLErrMesgs.RESPONSE_CODE_REQWRAP, ITLErrMesgs.RESPONSE_TITLE_SYSERR, ITLErrMesgs.RESPONSE_BASC_SYSNERR, ITLErrMesgs.RESPONSE_DTAIL_REQWRAP);
			itlBusinessException.addCurrentPos(e1.getStackTrace(), e1.getMessage());
			throw itlBusinessException;
		}
		
		logger.info("==================================================================");
		logger.info("[doProcessJson] # httpRequest requestJsonObject");
		
		String originBuffer = buffer.toString();
		int originBuffer_size = originBuffer.length();
		
		logger.debug("[doProcessJson] # originBuffer: " + originBuffer);
		
		// URLencoding 되어 들어오는 케이스 용
		String decodeBuffer = "";
		int decodeBuffer_size;
		try {
			decodeBuffer = URLDecoder.decode(originBuffer,"UTF-8");	
		} catch (Exception e) {
			// herasoo=100%+=+ 와 같이 평문에 %가 들어가 있는 케이스 방지
			decodeBuffer = originBuffer;
		} finally {
			decodeBuffer_size = decodeBuffer.length();
		}
		
		logger.debug("[doProcessJson] # decodeBuffer: " + decodeBuffer);

		// InputStream JSONObject 로 변환
		JSONObject requestJsonObject = null;
		
		// URLencoding 여부 판단하여 활용
		if (originBuffer_size > decodeBuffer_size) {
			logger.debug("[doProcessJson] # URLencoded!! Use decodeBuffer.");
			requestJsonObject = ObjectConverter.stringToJson(decodeBuffer);
		} else {
			logger.debug("[doProcessJson] # NOT URLencoded!! Use originBuffer.");
			requestJsonObject = ObjectConverter.stringToJson(originBuffer);
		}
		
		logger.info("[doProcessJson] # requestJsonObject: " + requestJsonObject);
		logger.info("[doProcessJson] # nodeName: " + nodeName);
		logger.info("==================================================================");
		
		
		// Request 서비스 허용 여부 체크
		try {
			Map<String, String> allowMap = isAllowRequest(httpRequest, httpResponse, requestJsonObject == null ? "" : requestJsonObject.toString());
			if (! "Y".equals(allowMap.get(ALLOW_MAP_KEY_ALLOWYN))) {
				sendError(httpRequest, httpResponse, allowMap);
				return;
			}
		} catch (ITLBusinessException e1) {
			ITLBusinessException.printErrorStack(e1);
			if (StringUtil.isEmpty(e1.getResponseCode())) {
				e1 = new ITLBusinessException(ITLErrMesgs.RESPONSE_TYPE_SYSERR, ITLErrMesgs.RESPONSE_CODE_REQALLOW, ITLErrMesgs.RESPONSE_TITLE_SYSERR, ITLErrMesgs.RESPONSE_BASC_SYSNERR, ITLErrMesgs.RESPONSE_DTAIL_REQALLOW);
			}
			e1.addCurrentPos(e1.getStackTrace(), e1.getMessage());
			throw e1;
		}
		
		/*
		 * Log4j 에 UserId 출력 설정
		 */
		if ((requestJsonObject != null) && (requestJsonObject.get(Constants.TRT_BASE_INFO_DTO) != null) && ((JSONObject)requestJsonObject.get(Constants.TRT_BASE_INFO_DTO)).get("userId") != null) {
			logger.debug("  log4j MDC.put   : "+ ((JSONObject)requestJsonObject.get(Constants.TRT_BASE_INFO_DTO)).get("userId"));
			MDC.put("userId", ((JSONObject)requestJsonObject.get(Constants.TRT_BASE_INFO_DTO)).get("userId"));
		}
		
		
		/*
		 * BMON 연동 및 KOS ESB 연동용 Global No 생성
		*/
		String globalNo = GlobalNoGenerator.getInstance().getGlobalNo();
		
		/*
		 * Request BMON 연동
		*/
		// local PC 테스트 경우 BMON 연동 제외 + KKOS BMON연동 제어 설정값 "N"인 경우만 BMON연동
		String kkosBmonCallBlockYn = DynamicStaticValues.getInstance().getKkosBmonCallBlockYn();
		JSONObject bmonHeader = new JSONObject();
		boolean bmonRequestSentYN =false;
		
		try {
			if (requestJsonObject != null) {
				
				//BMON 헤더 정보 세팅
				if (! "local".equals(sysTarget) && "N".equals(kkosBmonCallBlockYn)) bmonSender.setHeader(requestJsonObject, globalNo, "T", requestUri, bmonHeader);
				//BMON 연동
				if (! "local".equals(sysTarget) && "N".equals(kkosBmonCallBlockYn) && bmonHeader != null && !bmonHeader.isEmpty() && !StringUtil.isEmpty((String)bmonHeader.get("globalNo"))) {
					bmonSender.sendBmon(requestJsonObject, globalNo, "T", nodeName, bmonHeader);
					bmonRequestSentYN = true;
				}

			}
		}
		catch (ITLBusinessException e) {
			ITLBusinessException.printErrorStack(e);
		}
		
		/*
		 * RequestBody JSON 의 trtBaseInfoDTO 의 보안처리된 항목 복호화
		 * RequestBody JSON 의 trtBaseInfoDTO 에 globalNo 세팅 (KOS 연동 GlobalNo 로 사용)
		 * RequestBody JSON 내용에 포함된 Masking 또는 Ecrypt Data 를 UnMasking, Decrypt 처리 (__ENC__ 필드)
		 */
		// TrtBaseInfoDTO Secure & UnSecure
		if ( (requestJsonObject != null) && (requestJsonObject.get(Constants.TRT_BASE_INFO_DTO) != null) ) {
			// trtBaseInfoDTO 의 보안처리된 항목 복호화
			logger.debug("Before requestJsonObject.trtBaseInfoDTO : "+ ((JSONObject)requestJsonObject.get(Constants.TRT_BASE_INFO_DTO)).toString());
			
			// [확인필요-trtBaseInfo정보 암호화 여부] trtBaseInfoDTO 암/복호화 skip
			contentSecure.unSecureTrtBaseInfoDTO((JSONObject)requestJsonObject.get(Constants.TRT_BASE_INFO_DTO));
			
			// trtBaseInfoDTO 에 globalNo 세팅 (KOS 연동 GlobalNo 로 사용)
			((JSONObject)requestJsonObject.get(Constants.TRT_BASE_INFO_DTO)).put("globalNo", globalNo);
			logger.debug("After requestJsonObject.trtBaseInfoDTO : "+ ((JSONObject)requestJsonObject.get(Constants.TRT_BASE_INFO_DTO)).toString());
		}
		else {
			logger.debug("requestJsonObject.trtBaseInfoDTO is NULL");
		}
		
		
		/*
		 * RequestBody JSON 데이터 보안키패드 복호화 로직
		*/
		secureKeyPadDecrypt (httpRequest, requestJsonObject);
		
		/*
		 * RequestBody JSON 내용에 포함된 Masking 또는 Ecrypt Data 를 UnMasking, Decrypt 처리 (__ENC__ 필드)
		 */
		contentSecure.unSecureIn(requestJsonObject);
		logger.debug("doProcess().... After contentSecure.unSecureIn:" + requestJsonObject);
		
		
		/*
		 * [작업필요] CRIS 연동 및 가상번호 취득 & Request Body 중 가상번호 항목 값을 가상번호로 교체
		 *   - 요청 값이 실번호 이면, 가상번호 리턴 / 가상번호로 요청하면 실번호 리턴
		 */
		TrtBaseInfoDTO trtBaseInfoDTO = getTrtBaseInfoDTO(httpRequest, requestJsonObject);
		if (trtBaseInfoDTO != null)	{
			trtBaseInfoDTO.setGlobalNo(globalNo);
			contentSecure.crisApply(trtBaseInfoDTO, requestJsonObject);
//			try {
//				contentSecure.crisApply(trtBaseInfoDTO, requestJsonObject);
//			} catch (ITLBusinessException e) {
//				// TODO Auto-generated catch block
//				ITLBusinessException.printErrorStack(e);
//				throw new ITLBusinessException("E", "HTTP500", "CRIS Error", "", "");
//			}
		}
		
		
		/*
		 * UnMasking, Decrypt, CRIS 연동, 보안키패드 복호화 처리된 내용으로 RequestBody 교체
		 */
		logger.info("FINAL requestJsonObject : "+ requestJsonObject);
		requestWrapper.replaceRequestBody(requestJsonObject);
		
		/*
		 * Real Service 호출 (.do)
		 */
		try {
			filterChain.doFilter(requestWrapper, responseWrapper);
		} catch (IOException | ServletException e1) {
			// TODO Auto-generated catch block
			ITLBusinessException.printErrorStack(e1);
			throw new ITLBusinessException ();
		}
		
		/*
		 * Response 처리 전 Output Stream Intercept
		 */
		byte [] outBt = responseWrapper.getDataStream();
		
		/*
		 * ResponseBody Data JSONObject 로 변환
		 */
		JSONObject responseJsonObject = ObjectConverter.byteToJson(outBt);
		
		logger.info("==================================================================");
		logger.info("# httpResponse responseJsonObject");
		logger.info("# responseJsonObject: " + responseJsonObject);
		logger.info("==================================================================");
		
		/*
		 * CRIS 연동 및 실번호 취득 & Response Body 중 가상번호 항목 값을 실번호로 교체
		 *   - 요청 값이 실번호 이면, 가상번호 리턴 / 가상번호로 요청하면 실번호 리턴
		 */
		trtBaseInfoDTO = getTrtBaseInfoDTO(httpRequest, requestJsonObject);
		if (trtBaseInfoDTO != null)	{
			trtBaseInfoDTO.setGlobalNo(globalNo);
			if (! SKIP_SECURE_OUT)	contentSecure.crisApply(trtBaseInfoDTO, responseJsonObject);
		}
		
		/*
		 * ResponseBody OutputStream Secure 처리 (Masking & Encrypt)Masking & Encrypt, 임시저장, BMON 연동
		 */
		if (! SKIP_SECURE_OUT)	contentSecure.secureOut(responseJsonObject);
		
		/*
		 * ResponseBody OutputStream 임시 저장
		 */
		responseJsonObject = saveTemplateData(responseJsonObject, requestUri);
		
		/*
		 * trtBaseInfo 중 보안이 필요한 항목 취합 암호화 및 삭제
		 */
		// [확인필요-trtBaseInfo정보 암호화 여부] trtBaseInfoDTO 암/복호화 skip
//			if ( (responseJsonObject != null) && (responseJsonObject.get(Constants.TRT_BASE_INFO_DTO) != null) ) {
//				contentSecure.secureTrtBaseInfoDTO((JSONObject)responseJsonObject.get(Constants.TRT_BASE_INFO_DTO));
//			}
		
//			if (saveComplete)	httpRequest.getSession(true).setAttribute(Constants.SESSION_ATTNAME_VO, responseJsonObject);
		
		
		/*
		 * ResponseBody OutputStream BMON 연동
		 */					
		if (responseJsonObject != null && bmonRequestSentYN) {
			
			// local PC 테스트 경우 BMON 연동 제외 + KKOS BMON연동 제어 설정값 "N"인 경우만 BMON연동
			if (! "local".equals(sysTarget) && "N".equals(kkosBmonCallBlockYn) && bmonHeader != null && !bmonHeader.isEmpty())	bmonSender.sendBmon(responseJsonObject, globalNo, "R", nodeName, bmonHeader);
		}


		
		/*
		 * ResponseBody OutputStream jsonObject Response 처리
		 */
		//보안키패드 Sample TEST 용도
		if (!requestUri.startsWith("/KKOS/pluginfree/examples")) {
			
			// responseJsonObject 가 null 또는 작은따옴표(') 포함된 경우 추가 처리
			//  - null 인 경우 "" 리턴
			//  - 작은 따옴표(') 는 틸트(`) 로 변경
			try {
				if (responseJsonObject == null)	httpResponse.getOutputStream().write("".getBytes());
				else 							httpResponse.getOutputStream().write(responseJsonObject.toString().replaceAll("'", "`").getBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				ITLBusinessException.printErrorStack(e);
			}
		}
	}
	
	

	private void secureKeyPadDecrypt (HttpServletRequest httpRequest, JSONObject jsonObject) throws ITLBusinessException {
		if (jsonObject != null) {
			
			/*
			 * JSON DATA 내 보안키패드 적용 입력 대상 필드 복호화 수행 (CASE A)
			 * 해당 로직이 일반적으로 정상적인 케이스나 동작하지 않음, JSON DATA 내 내부 JSON에 대상 필드 존재할 시 복호화하면 null이 되는 솔루션사 확인
			 * 결론: 고려하였으나 사용하지 않는 소스로 주석 처리
			 */
			
			/*
			// JSON 내 보안키패드 암호화 대상 필드(ex. __NPKP__custInfoDTO_custIdfyNo) 복호화하여 decryptInfo 별도 저장
			Map<String, String> decryptInfo = new HashMap<String, String>();
			
			// JSON 내 보안키패드 암호화 대상 필드(ex. __NPKP__custInfoDTO_custIdfyNo) 복호화 값 decryptInfo 별도 저장
			contentSecure.decryptInfo(requestJsonObject, decryptInfo, httpRequest);
						
			if (decryptInfo != null && decryptInfo.keySet().size() > 0) {
				logger.debug("==================================================================");
				logger.debug("# decryptInfo List");
				
				Iterator<String> iterDecryptInfo = decryptInfo.keySet().iterator();
				while(iterDecryptInfo.hasNext()){
					String key = iterDecryptInfo.next();
					String value = decryptInfo.get(key);
					logger.debug("# key[" + key + "]:value[" + value + "]");
				}
				
				logger.debug("==================================================================");	
			} else {
				logger.debug("==================================================================");
				logger.debug("# None of decryptInfo List");
				logger.debug("==================================================================");	
			}
			*/
			

			/*
			 * JSON DATA 내 보안키패드 적용 입력 대상 필드 복호화 수행 (CASE B)
			 * 위 케이스 A가 되지 않아 보안키패드 적용 필드를 최 상위 레벨에 위치시켜 놓는 케이스 (솔루션 특성)
			 * 보안키패드 대상 필드를 최상위에 놓고 복호화 성공 후 JSON DATA 내 JSON 필드를 찾아 복호화하여 저장하는 구조
			 */
			
			Map<String, String> map = null;
			try {
				map = RequestUtil.parseJson(jsonObject.toJSONString());
			} catch (ParseException e) {
				ITLBusinessException.printErrorStack(e);
				ITLBusinessException itlBusinessException = new ITLBusinessException(ITLErrMesgs.RESPONSE_TYPE_SYSERR, ITLErrMesgs.RESPONSE_CODE_JSNPARSE, ITLErrMesgs.RESPONSE_TITLE_SYSERR, ITLErrMesgs.RESPONSE_BASC_SYSNERR, ITLErrMesgs.RESPONSE_DTAIL_JSNPARSE);
				itlBusinessException.addCurrentPos(e.getStackTrace(), e.getMessage());
				throw itlBusinessException;
			}
			
			// JSON 내 보안키패드 암호화 대상 필드(ex. __NPKP__custInfoDTO__custIdfyNo) 복호화하여 decryptInfo 별도 저장
			Map<String, String> decryptInfo = new HashMap<String, String>();
			
			HttpServletRequest httpRequest_nprotect = new PluginFreeRequest(httpRequest, map);
			
			logger.debug("# httpRequest_nprotect => " + httpRequest_nprotect);
			if (httpRequest_nprotect != null) {
				logger.debug("# httpRequest_nprotect Type => " + httpRequest_nprotect.getClass().toString());
			}
			
			String npk = httpRequest_nprotect.getParameter("__E2E_UNIQUE__");
			
			logger.debug("# npk => " + npk);
			if (npk != null) {
				logger.debug("# npk Type => " + npk.getClass().toString());
			}
			
			if(npk != null && !"".equals(npk)) {
				
				logger.debug("# 보안 키패드 입력입니다.!!!");

				try {
					PluginFree.verify(httpRequest_nprotect);
				} catch(Exception e) {
					ITLBusinessException.printErrorStack(e);
					logger.debug("# 보안 키패드 입력 복호화 검증 오류가 발생하였습니다.");
				}
			
				Iterator<String> iterMap = map.keySet().iterator();
				//logger.debug("==================================================================");
				//logger.debug("# nprotect key pad decrypt");
				while(iterMap.hasNext()){
					String key = iterMap.next();
					String value = httpRequest_nprotect.getParameter(key);
					//logger.debug("# key[" + key + "]:value[" + value + "]");
						
					if (key != null && key.startsWith(ContentSecure.PREFIX_NPKP_STRING_FIELD) && value != null && !"".equals(value)) {
												
						decryptInfo.put(key, value);					
					}
				}
				//logger.debug("==================================================================");
				
				if (decryptInfo != null && decryptInfo.keySet().size() > 0) {
					logger.debug("==================================================================");
					logger.debug("# decryptInfo List");
					
					Iterator<String> iterDecryptInfo = decryptInfo.keySet().iterator();
					while(iterDecryptInfo.hasNext()){
						String key = iterDecryptInfo.next();
						String value = decryptInfo.get(key);
						logger.debug("# key[" + key + "]:value[" + value + "]");
					}		
					logger.debug("==================================================================");	
				} else {
					logger.debug("==================================================================");
					logger.debug("# None of decryptInfo List");
					logger.debug("==================================================================");	
				}

				
				// JSON 내 상위 레벨에 위치한 보안키패드 암호화 대상 필드(ex. __NPKP__custInfoDTO__custIdfyNo)를 복호화한 값으로 일반 필드(ex. custIdfyNo) 찾아서 대체
				contentSecure.decryptSecureKeyPad(jsonObject, decryptInfo);
			} else {
				logger.debug("# 보안 키패드 입력이 아닙니다.!!!");
			}
		}
	}
	
	
	
	
	private JSONObject saveTemplateData(JSONObject jsonObject, String requestUri) {
		JSONObject rtnJsonObject = jsonObject;
		try {
			/*
			 * [임시저장] 개통 및 기기변경, 번호이동(사전체크, 오더처리등) 서비스 호출인 경우만 임시저장 수행
			 *  - 개통 : /KKOS/services/NewSbscTrtSO.do
			 *  - 기기변경 : /KKOS/services/IcgMainPrcsSO.do
			 *  - 번호이동 : /KKOS/services/NptmpSaveSO
			*/
			logger.debug("[Start] template Save : "+ requestUri);
			switch (requestUri) {
			case "/KKOS/services/NewSbscTrtSO.do" :	// 개통
			case "/KKOS/services/NptmpSaveSO.do" :	// 번호이동
				NewSbscTrtInfoVO newSbscTrtInfoVO = (NewSbscTrtInfoVO) ObjectConverter.jsonToObject(jsonObject, NewSbscTrtInfoVO.class);
				if (newSbscTrtInfoVO.getTrtErrInfoDTO().getResponseType() != null) {
					switch (newSbscTrtInfoVO.getTrtErrInfoDTO().getResponseType()) {
					case "S" :	// 임시저장 skip : 
					case "E" :
						logger.debug("[Skip] template Save - responseType : " + newSbscTrtInfoVO.getTrtErrInfoDTO().getResponseType());
						break;
					default :
						newSbscTrtInfoVO = tmpSaveInfoBOC.nacTmpSaveTrt(newSbscTrtInfoVO);
						rtnJsonObject = ObjectConverter.objectToJson(newSbscTrtInfoVO);
						break;
					}
				}
				else {
					newSbscTrtInfoVO = tmpSaveInfoBOC.nacTmpSaveTrt(newSbscTrtInfoVO);
					rtnJsonObject = ObjectConverter.objectToJson(newSbscTrtInfoVO);
				}
				
				break;
			case "/KKOS/services/IcgMainPrcsSO.do" :	// 기기변경
				IcgTrtInVO icgTrtInVO = (IcgTrtInVO) ObjectConverter.jsonToObject(jsonObject, IcgTrtInVO.class);
				if (icgTrtInVO.getTrtErrInfoDTO().getResponseType() != null) {
					switch (icgTrtInVO.getTrtErrInfoDTO().getResponseType()) {
					case "S" :
					case "E" :
						logger.debug("[Skip] template Save - responseType : " + icgTrtInVO.getTrtErrInfoDTO().getResponseType());
						break;
					default :
						icgTrtInVO = tmpSaveInfoBOC.hcnTmpSaveTrt(icgTrtInVO);
						rtnJsonObject = ObjectConverter.objectToJson(icgTrtInVO);
						break;
					}
				}
				else {
					icgTrtInVO = tmpSaveInfoBOC.hcnTmpSaveTrt(icgTrtInVO);
					rtnJsonObject = ObjectConverter.objectToJson(icgTrtInVO);
				}
				break;
			default :
				logger.debug("[Skip] template Save - Not Saved URI : "+ requestUri);
				break;
			}
			logger.debug("After tmpSave JSONObject : "+ rtnJsonObject);
			logger.debug("[End] template Save : "+ requestUri);
			
		}
		catch (Exception e) {
			ITLBusinessException.printErrorStack(e);
		}
		
		return rtnJsonObject;
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
	 * @throws ITLBusinessException 
	 * @throws ParseException 
	 */
	private void doProcessDefault(HttpServletRequest httpRequest, HttpServletResponse httpResponse, FilterChain filterChain) throws ITLBusinessException {
		
		logger.info("[doProcessDefault] Start - " + httpRequest.getRequestURI());
		
		// XSS Filter 적용
//		RequestWrapper requestWrapper = new RequestWrapper((HttpServletRequest)httpRequest);
//		filterChain.doFilter(requestWrapper, httpResponse);
		
		Map<String, String> map = isAllowRequest(httpRequest, httpResponse, httpRequest.getParameter("inputVo") == null ? "" : httpRequest.getParameter("inputVo"));
		if (! "Y".equals(map.get(ALLOW_MAP_KEY_ALLOWYN))) {
			sendError(httpRequest, httpResponse, map);
			return;
		}
		
		try {
			long start = System.currentTimeMillis();
			filterChain.doFilter(httpRequest, httpResponse);
			long end = System.currentTimeMillis();
			logger.info("[doProcessDefault] processing service() - " + httpRequest.getRequestURI() + " (" + (end-start)/1000.0 + "s elapsed)");

		} catch (IOException | ServletException e) {
			// TODO Auto-generated catch block
			ITLBusinessException.printErrorStack(e);
			ITLBusinessException itlBusinessException = new ITLBusinessException(ITLErrMesgs.RESPONSE_TYPE_SYSERR, ITLErrMesgs.RESPONSE_CODE_REQWRAP, ITLErrMesgs.RESPONSE_TITLE_SYSERR, ITLErrMesgs.RESPONSE_BASC_SYSNERR, ITLErrMesgs.RESPONSE_DTAIL_REQWRAP);
			itlBusinessException.addCurrentPos(e.getStackTrace(), e.getMessage());
			throw itlBusinessException;
		}
		
		logger.info("[doProcessDefault] End - " + httpRequest.getRequestURI());
	}
	
	
	/**
	 * <pre>
	 * 접속환경별로 PAD, Web에 대한 접속 가능 여부 확인.
	 * </pre>
	 *
	 * @param httpRequest
	 * @param httpResponse
	 * @return
	 * @throws ITLBusinessException 
	 */
	private Map<String, String> isAllowRequest (HttpServletRequest httpRequest, HttpServletResponse httpResponse, String jsonStr) throws ITLBusinessException
	{
		// 리턴 Map 정의
		Map<String, String> allowMap = new HashMap<String, String>();
		
		// HttpSession 생성 (기 생성된 경우 기 생성된 세션 유지)
		HttpSession httpSession = httpRequest.getSession(true);
		
		// Request URI 추출
		String requestUri = httpRequest.getRequestURI();
		logger.debug("[isAllowRequest] Connect URI : " + requestUri);
		
		/*
		 * 서비스 Block 체크 및 서비스가 Block 인 경우 서비스 진행 불가 처리
		*/
		allowMap = isBlockService(httpRequest, httpResponse, jsonStr);
		if (! "Y".equals(allowMap.get(ALLOW_MAP_KEY_ALLOWYN))) {
			return allowMap;
		}
		
		/*
		 * 배치인 경우 이하 체크 불필요 (서비스 진행 가능 처리) 
		*/
		if (! StringUtil.isEmpty(requestUri) && requestUri.matches(".*batch/ManualBatchInvokerSO.*") ) {
			allowMap.put(ALLOW_MAP_KEY_ALLOWYN, "Y");
			return allowMap;
		}
		
		/*
		 * WAS 서비스 호출이 아닌 경우 이하 체크 불필요 (서비스 진행 가능 처리) 
		 *   - html, js 등은 웹서버 처리용 
		*/
		if ( ! requestUri.startsWith("/KKOS/services/") ) {
			allowMap.put(ALLOW_MAP_KEY_ALLOWYN, "Y");
			return allowMap;
		}

		/*
		 * 서비스 접속 Cient 유형 취득 (패드, Kiosk)
		*/
		// HttpHeader User-Agent 값을 체크하여 pad 접속 여부 판단
		boolean isPad = (httpRequest.getHeader(Constants.USER_AGENT).indexOf(Constants.USER_AGENT_APP_KKOS) > -1) ? true : false;
		
		// HttpRequest URI 를 체크하여 KIOS 서비스 여부 판단
		boolean isKiosk = isKioskUri(httpRequest.getRequestURI(), jsonStr);
		
		// HttpRequest URI 를 체크하여 서비스 허용 여부 판단 ()
		boolean isAllowSvc = isCheckAllowServiceUri(httpRequest.getRequestURI());
		
		logger.debug("(isAllowRequest) Request URI : " + requestUri + ", isPad : " + isPad + ", isKiosk : " + isKiosk + ", isAllowSvc : " + isAllowSvc);
		
		/*
		 * 토큰 유효성 체크
		 *   - 허용 서비스가 아니고, Kiosk 서비스가 아닌 경우
		*/
		try {
			// 토큰 유효성 체크
			//   - header etc 에 trtBaseInfoDTO 암화화 되어 관리되며, isAllowSvc(로그인 전, 서버to서버) 와 isKiosk(KIOSK) 는 토큰 유효성 체크 제외
			if (!isAllowSvc && !isKiosk) {
				allowMap = isValidToken(httpRequest, httpSession, isPad);
				if (! "Y".equals(allowMap.get(ALLOW_MAP_KEY_ALLOWYN))) {
					return allowMap;
				}
			}
		} catch (ITLBusinessException e) {
			ITLBusinessException.printErrorStack(e);
			e.addCurrentPos(e.getStackTrace(), e.getMessage());
			throw e;
		}
		
		/*
		 * 접근 환경에 따른 체크
		*/
		switch (sysTarget)
		{
			// spt2, real2는 소스배포 없이 WAS 환경변수 변경으로 로그레벨 변경하기 위한 sysTarget

			case "real":
			case "real2":
			
				// 패드개통 앱 / KIOSK 아니면,, 접근 불가
				//  - 채널, 헤더정보, URI 패턴..
				// 체크 패드개통 앱 에서 접속 여부 판단
				if ( ! isKiosk && ! isPad && !isAllowSvc) {
					logger.debug("### Not Allowed Service - sysTarget : " + sysTarget + ", isKiosk : " + isKiosk + ", isPad : " + isPad);
					allowMap.put(ALLOW_MAP_KEY_ALLOWYN, "N");	// 허용하지 않음
					allowMap.put(ALLOW_MAP_KEY_ERRTYPE, "E");	// response error 코드
					allowMap.put(ALLOW_MAP_KEY_ERRCD, "405");	// response error 코드
					allowMap.put(ALLOW_MAP_KEY_ERRMESG, "잘못된 접근 입니다.");	// 에러 메시지
					return allowMap;
				}
				
				break;
			
			// PRD(real) 환경 외에는 별도 확인하지 않음.
			case "test":
			case "local" :
			case "dev" :
			case "spt" :
			case "spt2" :
				break;

			// 그 외 지정 되지 않은 환경은 접속을 허용하지 않음.
			default:
				logger.debug("### Not Allowed Service - sysTarget : " + sysTarget);
				allowMap.put(ALLOW_MAP_KEY_ALLOWYN, "N");	// 허용하지 않음
				allowMap.put(ALLOW_MAP_KEY_ERRTYPE, "E");	// response error 코드
				allowMap.put(ALLOW_MAP_KEY_ERRCD, "405");	// response error 코드
				allowMap.put(ALLOW_MAP_KEY_ERRMESG, "서버 환경 세팅을 확인하세요.");	// 에러 메시지
				return allowMap;
		}
		
		return allowMap;
		
	}
	
	

	
	/**
	 * JSONObject 로 부터 trtBaseInfoDTO 추출 및 TrtBaseInfoDTO 로 리턴
	 *
	 * @param httpRequest
	 * @param httpResponse
	 * @return
	 * @throws ITLBusinessException 
	 */
	private TrtBaseInfoDTO getTrtBaseInfoDTO (HttpServletRequest httpRequest, JSONObject jsonObject) throws ITLBusinessException {
		
		if (jsonObject == null)	return null;
		
		HttpSession httpSession = httpRequest.getSession();
		
		TrtBaseInfoDTO trtBaseInfoDTO = null;
		
		if (jsonObject.get(Constants.TRT_BASE_INFO_DTO) != null) {
			trtBaseInfoDTO = (TrtBaseInfoDTO)ObjectConverter.jsonToObject((JSONObject)jsonObject.get(Constants.TRT_BASE_INFO_DTO), TrtBaseInfoDTO.class);
			logger.debug("(getTrtBaseInfoDTO) jsonObject.get(Constants.TRT_BASE_INFO_DTO) is Not Null : " + ObjectConverter.objectToJson(trtBaseInfoDTO).toString());
		}
		else if ( httpSession.getAttribute(Constants.SESSION_ATTNAME_TRT_BASE_INFO_DTO) != null ) {
			trtBaseInfoDTO = (TrtBaseInfoDTO)httpSession.getAttribute(Constants.SESSION_ATTNAME_TRT_BASE_INFO_DTO);
			logger.debug("(getTrtBaseInfoDTO) httpSession.getAttribute(" + Constants.SESSION_ATTNAME_TRT_BASE_INFO_DTO + ") is Not Null : " + ObjectConverter.objectToJson(trtBaseInfoDTO).toString());
		}
		
		return trtBaseInfoDTO;
	}
	
	
	
	/**
	 * HttpRequest User-Agent Header 에서 특정 값 추출
	 *   - 앱에서 User-Agent 에 추가된 항목 추출
	 *   - key : "deviceUUID/" or "token/"
	 */
	public String getUserAgentValue (String contents, String key) {
		
		logger.debug("(getUserAgentValueo) contents : " + contents + ", Key : " + key);
		StringTokenizer strTkon = new StringTokenizer(contents, "|");
		
		String tmpStr = null;
		String value = null;
		
		while(strTkon.hasMoreTokens()) {
			tmpStr = strTkon.nextToken();
			if (tmpStr.indexOf(key) == 0) {
				value = tmpStr.replaceFirst(key, "");
				break;
			}
		}
		logger.debug("(getUserAgentValueo) " + key + " : " + value);
		
		return value;
	}
	
	
	
	/**
	 * HttpRequest URI 가 KIOSK 서비스 여부 체크
	 */
	public boolean isKioskUri (String uri, String jsonStr) throws ITLBusinessException {
		boolean isKioskService = false;
		
		/*
		 * Request URI 를 체크하여 KIOSK 서비스 여부 판단
		*/
		if (uri.indexOf("/KKOS/services/kiosk/") == 0) {
			logger.debug("(isKioskUri) Kiosk URI : " + uri);
			isKioskService = true;
		}
		
		/*
		 * trtBaseInfoDTO 를 체크하여 KIOSK 서비스 여부 판단 (Request URI 가 KIOSK 가 아닌 경우만 수행)
		*/
		if ( ! isKioskService && ! StringUtil.isEmpty(jsonStr) ) {
			try {
				JSONObject jsonObject = (JSONObject)ObjectConverter.stringToJson(jsonStr);
				if (jsonObject.get(Constants.TRT_BASE_INFO_DTO) != null) {
					TrtBaseInfoDTO trtBaseInfoDTO = (TrtBaseInfoDTO)ObjectConverter.jsonToObject((JSONObject)jsonObject.get(Constants.TRT_BASE_INFO_DTO), TrtBaseInfoDTO.class);
					if ( (trtBaseInfoDTO.getChnlType() != null) && ("KO".equals(trtBaseInfoDTO.getChnlType())) ) {
						isKioskService = true;
					}
				}
			} catch (ITLBusinessException e) {
				throw e;
			}
		}
		
		return isKioskService;
	}
	
	
	
	/**
	 * HttpRequest URI 를 조건으로 서비스 수행 여부 결정
	 *   - kiosk, pad 체크, 토큰 유효성 체크 제외
	 */
	public boolean isCheckAllowServiceUri (String uri) {
		boolean isAllow = false;
		
		logger.debug("(isCheckAllowServiceUri) uri : " + uri);
		
		switch (uri)
		{	
			case "/KKOS/services/kkosStartUp.do" :			// 최초 실행 (Token 정보 없음)
			case "/KKOS/services/premain.do" :				// 로그인 처리 (Token 정보 수신하여 관리 시작)
			case "/KKOS/services/getAppData.do" :			// 로그인 완료 후 패드 앱 Header 정보에 저장할 데이터 생성
			case "/KKOS/services/checkSupportGuide.do" :	// 가치제안 상담 내역 패드개통 가능 여부 체크(서버 to 서버 연동)
			case "/KKOS/services/padTokenExtend.do" :		// 토큰 연장 요청 (앱에서 호출하며, 유효성 체크 불필요)
			case "/KKOS/services/checkPadMdl.do" :			// 사용불가 모델 조회 (토큰 생성 전 안드로이드 앱에서 호출)
			case "/KKOS/services/PadUsableCheckSO.do" :		// 사용불가 모델 조회 (토큰 생성 전 안드로이드 앱에서 호출)
			case "/KKOS/services/PadVerCheckSO.do" :		// 사용 가능 설치 앱 버전 조회
//			case "/KKOS/services/registSession.do" :		// 앱으로 부터 토큰 유효성 체크를 위한 정보 수신 및 Session 에 관리 (미사용-삭제대상)
//			case "/KKOS/services/deleteSession.do" :		// 앱으로 부터 토큰 유효성 체크를 위한 정보 삭제 수신 (Session 관리되는 토큰 유효성 체크 정보 삭제) (미사용-삭제대상)
//			case "/KKOS/services/getOdrSeqNo.do" :			// 개인정보 활용 동의 이미지 업로드를 위한 OdrSeqNo 추출 (미사용-삭제대상)
				isAllow = true;
				break;
			
			// 앱에서 호출 (앱 용 세션에 trtBaseInfo 등록시 삭제 대상)
			case "/KKOS/services/image/Upload.do" :	
			case "/KKOS/services/image/getImageRegInfo.do" :
			case "/KKOS/services/image/KnoteIfStatusSO.do" :
			case "/KKOS/services/image/testSendImageToKnoteSO.do" :
			case "/KKOS/services/image/SendImageToKnoteSO.do" :
			case "/KKOS/services/image/DeleteImgByTypeSO.do" :
			case "/KKOS/services/image/DeleteImgByDocTypeSO.do" :
			case "/KKOS/services/image/DeleteImgByIdSO.do" :
			case "/KKOS/services/userImage/UserImageIdSO.do" :
			case "/KKOS/services/userImage/UserUpload.do" :
			case "/KKOS/services/userImage/uploadUserImage.do" :
			case "/KKOS/services/userImage/DeleteUserImgByTypeSO.do" :
			case "/KKOS/services/pdfImage/Upload.do" :
			case "/KKOS/services/pdfImage/deleteAll.do" :
			// 서식지 재 작성을 위한 임시 저장 데이터 조회
			case "/KKOS/services/getTmpSavedTBI.do" :
			case "/KKOS/services/KkosSysCdRetvSO.do" :
				isAllow = true;
				break;
			default :
				if ( (uri.indexOf("/KKOS/services/image/") == 0) || (uri.indexOf("/KKOS/services/userImage/") == 0) || (uri.indexOf("/KKOS/services/pdfImage/") == 0) ){
					logger.debug("(isCheckAllowServiceUri) uri.right(2) : " + uri + " -> " + uri.substring(uri.length()-2));
					isAllow = true;
				}
				break;
		}
		
		logger.debug("(isCheckAllowServiceUri) uri & isCheck : " + uri + " / " + isAllow);
		
		return isAllow;
	}
	
	
	public Map<String, String> isBlockService (HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, String jsoStr) {
		
		Map<String, String> allowMap = new HashMap<String, String>();
		
		boolean existTrtBaseInfoDTO = false;	// 수신 jsonObject 에 trtBaseInfoDTO 포함 여부
		
		/*
		 * 수신 JSON Type String 을 JSONObject 로 변환
		*/
		JSONObject jsonObject;
		try {
			jsonObject = (JSONObject) new JSONParser().parse(jsoStr);
			if (jsonObject.get(Constants.TRT_BASE_INFO_DTO) != null) {
				existTrtBaseInfoDTO = true;
			}
		} catch (org.json.simple.parser.ParseException e) {
			// TODO Auto-generated catch block
			// ITLBusinessException.printErrorStack(e);
			logger.debug("[isBlockService] Fail json Parse : " + jsoStr);
			existTrtBaseInfoDTO = false;
			jsonObject = new JSONObject();
		}
		logger.debug("[isBlockService] include trtBaseInfoDTO in JSONObject : " + existTrtBaseInfoDTO);

		
		/*
		 * 서비스 제한 여부 조회
		*/
		
		// Client userId, orgnId 전송 정보와 세션 정보 비교하여 변조 여부 확인
		HttpSession httpSession = httpServletRequest.getSession();
		String userId_session = (String) httpSession.getAttribute("userId");
		String orgId_session = (String) httpSession.getAttribute("orgId");
		if ((userId_session != null) && (!userId_session.isEmpty()) && (jsonObject != null) && (jsonObject.get(Constants.TRT_BASE_INFO_DTO) != null) && ((JSONObject)jsonObject.get(Constants.TRT_BASE_INFO_DTO)).get("userId") != null) {
			
			if ( ! ((JSONObject)jsonObject.get(Constants.TRT_BASE_INFO_DTO)).get("userId").toString().equals(userId_session) ) {
				logger.info("### TRT_BASE_INFO_DTO.userId Corrupted!!! ###");
				allowMap.put(ALLOW_MAP_KEY_ALLOWYN, "N");
				allowMap.put(ALLOW_MAP_KEY_ERRTYPE, "E");
				allowMap.put(ALLOW_MAP_KEY_ERRCD, "502");
				allowMap.put(ALLOW_MAP_KEY_ERRMESG, "잘못된 접속으로 서비스 사용 불가합니다.<br>다시 시도 바랍니다. 감사합니다.");
				return allowMap;
			}
		}
		
		if ((orgId_session != null) && (!orgId_session.isEmpty()) && (jsonObject != null) && (jsonObject.get(Constants.TRT_BASE_INFO_DTO) != null) && ((JSONObject)jsonObject.get(Constants.TRT_BASE_INFO_DTO)).get("orgId") != null) {
			
			if ( ! ((JSONObject)jsonObject.get(Constants.TRT_BASE_INFO_DTO)).get("orgId").toString().equals(orgId_session) ) {
				logger.info("### TRT_BASE_INFO_DTO.orgId Corrupted!!! ###");
				allowMap.put(ALLOW_MAP_KEY_ALLOWYN, "N");
				allowMap.put(ALLOW_MAP_KEY_ERRTYPE, "E");
				allowMap.put(ALLOW_MAP_KEY_ERRCD, "502");
				allowMap.put(ALLOW_MAP_KEY_ERRMESG, "잘못된 접속으로 서비스 사용 불가합니다.<br>다시 시도 바랍니다. 감사합니다.");
				return allowMap;
			}
		}
			
		// 전체 서비스 불가 처리
		String kkosServiceCallBlockYn = DynamicStaticValues.getInstance().getKkosServiceCallBlockYn();
		logger.debug("[isBlockService] ### kkosServiceCallBlockYn: " + kkosServiceCallBlockYn);
		if ("Y".equals(kkosServiceCallBlockYn)) {
			logger.info("### KKOS Service All Block!!! ###");
			allowMap.put(ALLOW_MAP_KEY_ALLOWYN, "N");
			allowMap.put(ALLOW_MAP_KEY_ERRTYPE, "E");
			allowMap.put(ALLOW_MAP_KEY_ERRCD, "502");
			allowMap.put(ALLOW_MAP_KEY_ERRMESG, "시스템 장애로 시스템 관리자에 의해 서비스가 차단되어 서비스 사용 불가합니다.<br>불편을 드려 죄송합니다. 빠른 조치하도록 하겠습니다. 감사합니다.");
			return allowMap;
		}
		
		// 채널별 서비스 제한 처리
		String kkosServiceCallBlockChhnlTypeYn = DynamicStaticValues.getInstance().getKkosServiceCallBlockChnlTypeYn();
		String kkosServicePermittedChhnlTypeList = DynamicStaticValues.getInstance().getKkosServicePermittedChnlTypeList();
		logger.debug("[isBlockService] ### kkosServiceCallBlockChhnlTypeYn: " + kkosServiceCallBlockChhnlTypeYn);
		logger.debug("[isBlockService] ### kkosServicePermittedChhnlTypeList: " + kkosServicePermittedChhnlTypeList);
		if ("Y".equals(kkosServiceCallBlockChhnlTypeYn) && existTrtBaseInfoDTO) {
			
			String[] channels = kkosServicePermittedChhnlTypeList.split("\\,");
			logger.debug("[isBlockService] ### kkosServicePermittedChhnlTypeList count: " + channels.length);
			
			JsonObject trt_base_info_dto = (JsonObject)jsonObject.get(Constants.TRT_BASE_INFO_DTO);
			String reqChnlInfo = null;
			
			if (trt_base_info_dto != null) {
				reqChnlInfo = trt_base_info_dto.get("chnlType").getAsString();
				logger.debug("[isBlockService] ### reqChnlInfo: " + reqChnlInfo);
			}
			
			boolean blockYN = true;
			
			// requestJsonObject에서 채널 정보와 설정 파일의 정의된 허용 채널 정보 비교
			if (reqChnlInfo != null) {
				for(String s : channels) {
					if (reqChnlInfo.equals(s)) {
						blockYN = false;
						break;
					}
				}
			}
			
			if (blockYN) {
				logger.info("### KKOS Service chnlType Block!!! ###");
				allowMap.put(ALLOW_MAP_KEY_ALLOWYN, "N");
				allowMap.put(ALLOW_MAP_KEY_ERRTYPE, "E");
				allowMap.put(ALLOW_MAP_KEY_ERRCD, "502");
				allowMap.put(ALLOW_MAP_KEY_ERRMESG, "시스템 장애로 시스템 관리자에 의해 대상 채널 서비스가 차단되어 서비스 사용 불가합니다.<br>불편을 드려 죄송합니다. 빠른 조치하도록 하겠습니다. 감사합니다.");
				return allowMap;
			}
		}
		
		// 업무별 처리 제한 (NAC, HCN, MNP) - trtBaseInfoDTO 가 존재하는 경우만...
		if (existTrtBaseInfoDTO) {
			if (jsonObject.get(Constants.TRT_BASE_INFO_DTO) != null) {
				JSONObject jsonTrtBaseInfoDTO = (JSONObject)jsonObject.get(Constants.TRT_BASE_INFO_DTO);
				logger.debug("[isBlockService] ### itgOderTypeCd: " + jsonTrtBaseInfoDTO.get("itgOderTypeCd"));
				switch ( jsonTrtBaseInfoDTO.get("itgOderTypeCd") == null ? "" : (String)jsonTrtBaseInfoDTO.get("itgOderTypeCd") ) {
				case "NAC" :
					logger.debug("[isBlockService] ### getKkosServiceNacBlockYn: " + DynamicStaticValues.getInstance().getKkosServiceNacBlockYn());
					if ("Y".equals(DynamicStaticValues.getInstance().getKkosServiceNacBlockYn())) {
						logger.info("### KKOS Service NAC Block!!! ###");
						allowMap.put(ALLOW_MAP_KEY_ALLOWYN, "N");
						allowMap.put(ALLOW_MAP_KEY_ERRTYPE, "E");
						allowMap.put(ALLOW_MAP_KEY_ERRCD, "502");
						allowMap.put(ALLOW_MAP_KEY_ERRMESG, "시스템 장애로 시스템 관리자에 의해 개통 서비스가 차단되어 서비스 사용 불가합니다.<br>불편을 드려 죄송합니다. 빠른 조치하도록 하겠습니다. 감사합니다.");
						return allowMap;
					}
					break;
				case "MNP" :
					logger.debug("[isBlockService] ### getKkosServiceMnpBlockYn: " + DynamicStaticValues.getInstance().getKkosServiceMnpBlockYn());
					if ("Y".equals(DynamicStaticValues.getInstance().getKkosServiceMnpBlockYn())) {
						logger.info("### KKOS Service MNP Block!!! ###");
						allowMap.put(ALLOW_MAP_KEY_ALLOWYN, "N");
						allowMap.put(ALLOW_MAP_KEY_ERRTYPE, "E");
						allowMap.put(ALLOW_MAP_KEY_ERRCD, "502");
						allowMap.put(ALLOW_MAP_KEY_ERRMESG, "시스템 장애로 시스템 관리자에 의해 번호이동 서비스가 차단되어 서비스 사용 불가합니다.<br>불편을 드려 죄송합니다. 빠른 조치하도록 하겠습니다. 감사합니다.");
						return allowMap;
					}
					break;
				case "HCN" :
					logger.debug("[isBlockService] ### getKkosServiceHcnBlockYn: " + DynamicStaticValues.getInstance().getKkosServiceHcnBlockYn());
					if ("Y".equals(DynamicStaticValues.getInstance().getKkosServiceHcnBlockYn())) {
						logger.info("### KKOS Service HCN Block!!! ###");
						allowMap.put(ALLOW_MAP_KEY_ALLOWYN, "N");
						allowMap.put(ALLOW_MAP_KEY_ERRTYPE, "E");
						allowMap.put(ALLOW_MAP_KEY_ERRCD, "502");
						allowMap.put(ALLOW_MAP_KEY_ERRMESG, "시스템 장애로 시스템 관리자에 의해 기기변경 서비스가 차단되어 서비스 사용 불가합니다.<br>불편을 드려 죄송합니다. 빠른 조치하도록 하겠습니다. 감사합니다.");
						return allowMap;
					}
					break;
				case "RSL" :
					logger.debug("[isBlockService] ### getKkosServiceRslBlockYn: " + DynamicStaticValues.getInstance().getKkosServiceRslBlockYn());
					if ("Y".equals(DynamicStaticValues.getInstance().getKkosServiceRslBlockYn())) {
						logger.info("### KKOS Service RSL Block!!! ###");
						allowMap.put(ALLOW_MAP_KEY_ALLOWYN, "N");
						allowMap.put(ALLOW_MAP_KEY_ERRTYPE, "E");
						allowMap.put(ALLOW_MAP_KEY_ERRCD, "502");
						allowMap.put(ALLOW_MAP_KEY_ERRMESG, "시스템 장애로 시스템 관리자에 의해 사전조회 서비스가 차단되어 서비스 사용 불가합니다.<br>불편을 드려 죄송합니다. 빠른 조치하도록 하겠습니다. 감사합니다.");
						return allowMap;
					}
					break;
				default:
					break;
				}
			}
		}
		

		allowMap.put(ALLOW_MAP_KEY_ALLOWYN, "Y");
		return allowMap;
	}
	
	
	public Map<String, String> isValidToken (HttpServletRequest httpRequest, HttpSession httpSession, boolean isPad) throws ITLBusinessException {
		
		Map<String, String> allowMap = new HashMap<String, String>();
		
		/*
		 * 유효성 체크 대상 Token & TrtBaseInfoDTO 추출
		 *   - 접속 방식(Pad)에 따른 Token & TrtBaseInfoDTO 추출
		*/
		String authToken = null;
		String etc = null;
		TrtBaseInfoDTO trtBaseInfoDTO = null;
		
		if (isPad) {
			String userAgent = httpRequest.getHeader(Constants.USER_AGENT);
			String [] userAgentSplit = userAgent.split("\\|");
			for (int ii=0; ii<userAgentSplit.length; ii++) {
				if (userAgentSplit[ii].startsWith(Constants.USER_AGENT_TOKEN))		authToken = userAgentSplit[ii].replaceFirst(Constants.USER_AGENT_TOKEN, "");
				else if (userAgentSplit[ii].startsWith(Constants.USER_AGENT_ETC))		etc = userAgentSplit[ii].replaceFirst(Constants.USER_AGENT_ETC, "");
			}
			
			if ( StringUtil.isEmpty(authToken) || StringUtil.isEmpty(etc) ) {
				logger.debug("### Not Allowed Service - isPad : " + isPad + ", authToken : " + authToken + ", etc : " + etc);
				allowMap.put(ALLOW_MAP_KEY_ALLOWYN, "N");
				allowMap.put(ALLOW_MAP_KEY_ERRTYPE, "E");
				allowMap.put(ALLOW_MAP_KEY_ERRCD, "405");
				allowMap.put(ALLOW_MAP_KEY_ERRMESG, "잘못된 접근입니다. 로그인 후 사용 가능합니다.");
				return allowMap;
			}
			
			trtBaseInfoDTO = (TrtBaseInfoDTO)ObjectConverter.stringToObject(Seed256.getInstance().decryptBase64Wrapped(etc), TrtBaseInfoDTO.class);
		}
		else {
			logger.debug("(isValidToken) HttpSession (" + httpSession.getId() + ") " +  Constants.SESSION_ATTNAME_AUTH_TOKEN + " : " + httpSession.getAttribute(Constants.SESSION_ATTNAME_AUTH_TOKEN));
			logger.debug("(isValidToken) HttpSession (" + httpSession.getId() + ") " +  Constants.SESSION_ATTNAME_TRT_BASE_INFO_DTO + " : " + httpSession.getAttribute(Constants.SESSION_ATTNAME_TRT_BASE_INFO_DTO));
			
			if (httpSession.getAttribute(Constants.SESSION_ATTNAME_AUTH_TOKEN) == null || httpSession.getAttribute(Constants.SESSION_ATTNAME_TRT_BASE_INFO_DTO) == null) {
				logger.debug("### Not Allowed Service - isPad : " + isPad + ", Session is Null :" + Constants.SESSION_ATTNAME_AUTH_TOKEN + ", " + Constants.SESSION_ATTNAME_TRT_BASE_INFO_DTO);
				allowMap.put(ALLOW_MAP_KEY_ALLOWYN, "N");
				allowMap.put(ALLOW_MAP_KEY_ERRTYPE, "E");
				allowMap.put(ALLOW_MAP_KEY_ERRCD, "405");
				allowMap.put(ALLOW_MAP_KEY_ERRMESG, "잘못된 접근입니다. 로그인 후 사용 가능합니다.");
				return allowMap;
			}
			
			authToken = (String)httpSession.getAttribute(Constants.SESSION_ATTNAME_AUTH_TOKEN);
			trtBaseInfoDTO = (TrtBaseInfoDTO)httpSession.getAttribute(Constants.SESSION_ATTNAME_TRT_BASE_INFO_DTO);
		}
		
		/*
		 * 접속 Client IP 체크 (httpRequest 와 TrtBaseInfoDTO)
		*/
		logger.debug("(isValidToken) Client IP : httpRequest = " + httpRequest.getRemoteAddr() + ", trtBaseInfoDTO = " + trtBaseInfoDTO.getClntIp());
		if ( ! httpRequest.getRemoteAddr().equals(trtBaseInfoDTO.getClntIp())) {
			logger.debug("### Not Allowed Service - Request Client IP : " + httpRequest.getRemoteAddr() + ", TrtBaseInfoDTO client IP : " + trtBaseInfoDTO.getClntIp());
			allowMap.put(ALLOW_MAP_KEY_ALLOWYN, "N");
			allowMap.put(ALLOW_MAP_KEY_ERRTYPE, "E");
			allowMap.put(ALLOW_MAP_KEY_ERRCD, "405");
			allowMap.put(ALLOW_MAP_KEY_ERRMESG, "잘못된 접근입니다. 로그인 후 사용 가능합니다.");
			return allowMap;
		}
		
		/*
		 * 토큰 유효성 체크
		*/
		com.kt.kwl.wj.auth.PadToknAvailVrfSO.PadToknAthnInputDTO padToknAthnInputDTO = new com.kt.kwl.wj.auth.PadToknAvailVrfSO.PadToknAthnInputDTO();
		
		// GlobalNo 세팅
		trtBaseInfoDTO.setGlobalNo(GlobalNoGenerator.getInstance().getGlobalNo());
		
		// 유효성 체크 대상 Token & SystemId 세팅
		padToknAthnInputDTO.setAthnToknId(authToken);
		padToknAthnInputDTO.setSysId(Constants.PAD_TOKEN_ATHN_SYSID);
		
		com.kt.kwl.wj.auth.PadToknAvailVrfSO.PadToknAthnOtputDTO padToknAthnOtputDTO = padTokenChkBO.chkPadToknAvail(trtBaseInfoDTO, padToknAthnInputDTO);
		
		// 유효성 체크 결과 유효하지 않은 경우(!Y) return false
		if ("Y".equals(padToknAthnOtputDTO.getTrtReslt())) {
			allowMap.put(ALLOW_MAP_KEY_ALLOWYN, "Y");
			return allowMap;
		}
		else {
			logger.debug("### Not Allowed Service - Invalid Token : " + authToken);
			allowMap.put(ALLOW_MAP_KEY_ALLOWYN, "N");
			allowMap.put(ALLOW_MAP_KEY_ERRTYPE, "E");
			allowMap.put(ALLOW_MAP_KEY_ERRCD, "405");
			allowMap.put(ALLOW_MAP_KEY_ERRMESG, "토큰이 유효하지 않습니다..");
			return allowMap;
		}
	}
	
	
	public void sendError (HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Map<String, String> allowMap) {
		try {
			logger.debug("(sendError) ContentType : " + httpServletRequest.getContentType());
			String tmpStr =  httpServletRequest.getContentType() == null ? "" : httpServletRequest.getContentType();
			
			if (tmpStr.toLowerCase().indexOf(Constants.COTENT_JSON) == 0) {
				KkosBaseInfoVO kkosBaseInfoVO = new KkosBaseInfoVO();
				
				TrtErrInfoDTO trtErrInfoDTO = kkosBaseInfoVO.getTrtErrInfoDTO();
				trtErrInfoDTO.setResponseCode(allowMap.get(ALLOW_MAP_KEY_ALLOWYN));
				trtErrInfoDTO.setResponseType(allowMap.get(ALLOW_MAP_KEY_ERRTYPE));
				trtErrInfoDTO.setResponseBasc(allowMap.get(ALLOW_MAP_KEY_ERRMESG));
				
				String errorMesg = ObjectConverter.objectToJson(kkosBaseInfoVO).toString();
				logger.debug("(sendError) Message : " + errorMesg);
				
				httpServletResponse.setContentType(Constants.COTENT_JSON);
				httpServletResponse.setCharacterEncoding(Constants.CHARSET);
				
				httpServletResponse.getOutputStream().write(errorMesg.getBytes());
			}
			else {
				
				logger.debug("(sendError) error code : " + allowMap.get(ALLOW_MAP_KEY_ERRCD));
				logger.debug("(sendError) Message : " + allowMap.get(ALLOW_MAP_KEY_ERRMESG));
				httpServletResponse.sendError(Integer.parseInt(allowMap.get(ALLOW_MAP_KEY_ERRCD)), allowMap.get(ALLOW_MAP_KEY_ERRMESG));
			}
		}
		catch (Exception e) {
			ITLBusinessException.printErrorStack(e);
		}
	}
}