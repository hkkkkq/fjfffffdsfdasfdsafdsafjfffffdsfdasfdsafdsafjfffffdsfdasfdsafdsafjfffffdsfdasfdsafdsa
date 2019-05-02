package com.kt.kkos.common.content;

import java.util.Map;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.kt.kkos.common.util.Constants;
import com.kt.kkos.common.util.ObjectConverter;
import com.kt.kkos.common.util.StringUtil;
import com.kt.kkos.exception.ITLBusinessException;
import com.kt.nbss.onm.bmon.collect.api.CommonPayloadCollector;

/**
* KKOS project version 1.0
* 
* Copyright ⓒ 2018 kt corp. All rights reserved.
* 
* This is a proprietary software of kt corp, and you may not use this file except in
* compliance with license agreement with kt corp. Any redistribution or use of this
* software, with or without modification shall be strictly prohibited without prior written
* approval of kt corp, and the copyright notice above does not evidence any actual or
* intended publication of such software.
* 
* @FileName com.kt.kkos.common.content.BMONSender.java
* @Creator 91182780
* @CreateDate 2018.04.09
* @Version 0.1
* @Description KKOS WAS 호출 및 응답 시 B-MON 연동 클래스
*  
*/

/*
* ********************소스 수정 이력****************************************
* DATE                Modifier                      Description
* *********************************************************************
* 2018.04.09          임영현(91182780)               최초작성
* 2018.07.18		  전원호(82023121)				수정보완
* *********************************************************************
*/

@Service
public class BMONSender {

	private static final Logger logger = LoggerFactory.getLogger(BMONSender.class);
	
	
	final static String HEADER_KEY_VALUE_SEPARATOR = "=";
	final static String BODY_KEY_VALUE_SEPARATOR = ":";
	
	final static String LINE_FEED = "\n";
	final static String EMPTY_STRING = "";
	
	/*
	 * HEADER Value
	*/
	final static String APP_NAME = "KKOS";
	final static String LOG_POINT = "KM";
	
	/**
	 * BMON 연동을 위한 헤더 정보 세팅
	 * Request때의 정보를 가지고 Response연동 시 활용한다.
	 * 사유: 내부 로직에서 Response 시 BMON 연동 필수 데이터 세팅 못하는 케이스가 존재하기 때문이다.
	 * 
	 * @param jsonObject
	 * @param global_No
	 * @param trt_Flag
	 * @param reqURI
	 * @param nodeName
	 * @param bmonHeader
	 * @throws ITLBusinessException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void setHeader(JSONObject jsonObject, String global_No, String tr_Flag, String reqURI, JSONObject bmonHeader) throws ITLBusinessException {
		
		// BMON Header Mandatory Info
		String appName = APP_NAME;
		String svcName = reqURI;
		String fnName = "service";
		String globalNo = global_No;
		String chnlType = "";
		String trFlag = tr_Flag;
		String trDate = "";
		String trTime = "";
		String clntIp = "";
		String responseType = "";
		String userId = "";
		String orgId = "";
		String srcId = "";
		String lgDateTime = "";
		
		// additional variables
		String stepId = "";
		
		trDate = StringUtil.Date_yyyyMMdd();
		trTime = StringUtil.Date_HHmmssSSS();
		clntIp = StringUtil.getIPAddress();
		lgDateTime = StringUtil.Date_yyyyMMddHHmmss();
		
		if (jsonObject.get(Constants.TRT_BASE_INFO_DTO) != null) {
			
			Map<String, String> map = (Map)ObjectConverter.jsonToObject((JSONObject)jsonObject.get(Constants.TRT_BASE_INFO_DTO), Map.class);
			srcId = map.get("srcId") == null ? "not_defined" : map.get("srcId");
			userId = map.get("userId") == null ? "not_defined" : map.get("userId");
			orgId = map.get("orgId") == null ? "not_defined" : map.get("orgId");
			chnlType = map.get("chnlType") == null ? "not_defined" : map.get("chnlType");
			stepId = map.get("stepId") == null ? "not_defined" : map.get("stepId");
			
			
		} else {
			bmonHeader.put("globalNo", "");
//			bmonHeader = null;
			logger.debug("## [" + globalNo + "] No trtBaseInfoDTO, No BmonSender!!");
			return;
		}
		
		// 예외 추가 로직
		svcName = svcName.concat("|").concat(srcId).concat("|").concat(stepId);
		
		
		bmonHeader.put("appName", appName);
		bmonHeader.put("svcName", svcName);
		bmonHeader.put("fnName", fnName);
		bmonHeader.put("globalNo", globalNo);
		bmonHeader.put("chnlType", chnlType);
		bmonHeader.put("trFlag", trFlag);
		bmonHeader.put("trDate", trDate);
		bmonHeader.put("trTime", trTime);
		bmonHeader.put("clntIp", clntIp);
		bmonHeader.put("responseType", responseType);
		bmonHeader.put("userId", userId);
		bmonHeader.put("orgId", orgId);
		bmonHeader.put("srcId", srcId);
		bmonHeader.put("lgDateTime", lgDateTime);

	}
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void sendBmon(JSONObject jsonObject, String global_No, String tr_Flag, String nodeName, JSONObject bmonHeader) throws ITLBusinessException
	{
 		
		// BMON Header Mandatory Info
		String appName = "";
		String svcName = "";
		String fnName = "";
		String globalNo = "";
		String chnlType = "";
		String trFlag = "";
		String trDate = "";
		String trTime = "";
		String clntIp = "";
		String responseType = "";
		String userId = "";
		String orgId = "";
		String srcId = "";
		String lgDateTime = "";
		
		// BMON Header Optional Info
		String responseCode = "";
		String responseTitle = "";
		String responseBasc = "";
		String responseDtal = "";
		String responseSystem = "";
		String curHostId = "";
		
		appName 		= bmonHeader.get("appName").toString();
		svcName 		= bmonHeader.get("svcName").toString();
		fnName 			= bmonHeader.get("fnName").toString();
		globalNo 		= bmonHeader.get("globalNo").toString();
		chnlType 		= bmonHeader.get("chnlType").toString();
		trFlag 			= tr_Flag;
		trDate 			= StringUtil.Date_yyyyMMdd();
		trTime 			= StringUtil.Date_HHmmssSSS();
		clntIp 			= bmonHeader.get("clntIp").toString();
		responseType 	= bmonHeader.get("responseType").toString();
		userId 			= bmonHeader.get("userId").toString();
		orgId 			= bmonHeader.get("orgId").toString();
		srcId 			= bmonHeader.get("srcId").toString();
		lgDateTime 		= StringUtil.Date_yyyyMMddHHmmss();
		
				
		// 응답시
		if ("R".equals(tr_Flag) && jsonObject.get(Constants.TRT_ERR_INFO_DTO) != null) {
			Map<String, String> map = (Map)ObjectConverter.jsonToObject((JSONObject)jsonObject.get(Constants.TRT_ERR_INFO_DTO), Map.class);
			responseType = map.get("responseType");
			responseCode = map.get("responseCode");
			responseTitle = map.get("responseTitle");
			responseBasc = map.get("responseBasc");
			responseDtal = map.get("responseDtal");
			responseSystem = map.get("responseSystem");
		}
		
		curHostId = nodeName;
		
		
		StringBuffer headerStrBuf = new StringBuffer();
						
		// BMON Header Mandatory Info
		headerStrBuf.append("appName").append(HEADER_KEY_VALUE_SEPARATOR).append(appName).append(LINE_FEED);
		headerStrBuf.append("svcName").append(HEADER_KEY_VALUE_SEPARATOR).append(svcName).append(LINE_FEED);
		headerStrBuf.append("fnName").append(HEADER_KEY_VALUE_SEPARATOR).append(fnName).append(LINE_FEED);
		headerStrBuf.append("globalNo").append(HEADER_KEY_VALUE_SEPARATOR).append(globalNo).append(LINE_FEED);
		headerStrBuf.append("chnlType").append(HEADER_KEY_VALUE_SEPARATOR).append(chnlType).append(LINE_FEED);
		headerStrBuf.append("trFlag").append(HEADER_KEY_VALUE_SEPARATOR).append(trFlag).append(LINE_FEED);
		headerStrBuf.append("trDate").append(HEADER_KEY_VALUE_SEPARATOR).append(trDate).append(LINE_FEED);
		headerStrBuf.append("trTime").append(HEADER_KEY_VALUE_SEPARATOR).append(trTime).append(LINE_FEED);
		headerStrBuf.append("clntIp").append(HEADER_KEY_VALUE_SEPARATOR).append(clntIp).append(LINE_FEED);
		
		if ("T".equals(tr_Flag)) {
			// 요청시
			responseType = EMPTY_STRING;
		} else {
			// 응답시
			if (responseType == null || responseType.isEmpty()) {
				logger.debug("## Check!!! trtErrInfoDTO data, No responseType!!");
				responseType = "I";
			}
		}
		
		headerStrBuf.append("responseType").append(HEADER_KEY_VALUE_SEPARATOR).append(responseType).append(LINE_FEED);
		headerStrBuf.append("userId").append(HEADER_KEY_VALUE_SEPARATOR).append(userId).append(LINE_FEED);
		headerStrBuf.append("orgId").append(HEADER_KEY_VALUE_SEPARATOR).append(orgId).append(LINE_FEED);
		headerStrBuf.append("srcId").append(HEADER_KEY_VALUE_SEPARATOR).append(srcId).append(LINE_FEED);
		headerStrBuf.append("lgDateTime").append(HEADER_KEY_VALUE_SEPARATOR).append(lgDateTime).append(LINE_FEED);
		

		//BMON Header Optional Info
		if ("T".equals(tr_Flag)) {
			// 요청시
			headerStrBuf.append("responseCode").append(HEADER_KEY_VALUE_SEPARATOR).append(EMPTY_STRING).append(LINE_FEED);
			headerStrBuf.append("responseTitle").append(HEADER_KEY_VALUE_SEPARATOR).append(EMPTY_STRING).append(LINE_FEED);
			headerStrBuf.append("responseBasc").append(HEADER_KEY_VALUE_SEPARATOR).append(EMPTY_STRING).append(LINE_FEED);
			headerStrBuf.append("responseDtal").append(HEADER_KEY_VALUE_SEPARATOR).append(EMPTY_STRING).append(LINE_FEED);
			headerStrBuf.append("responseSystem").append(HEADER_KEY_VALUE_SEPARATOR).append(EMPTY_STRING).append(LINE_FEED);
		} else {
			// 응답시
			headerStrBuf.append("responseCode").append(HEADER_KEY_VALUE_SEPARATOR).append(responseCode).append(LINE_FEED);
			headerStrBuf.append("responseTitle").append(HEADER_KEY_VALUE_SEPARATOR).append(responseTitle).append(LINE_FEED);
			headerStrBuf.append("responseBasc").append(HEADER_KEY_VALUE_SEPARATOR).append(responseBasc).append(LINE_FEED);
			headerStrBuf.append("responseDtal").append(HEADER_KEY_VALUE_SEPARATOR).append(responseDtal).append(LINE_FEED);
			
			if ("I".equals(responseType) || "D".equals(responseType)) {
				responseSystem = EMPTY_STRING;
			} else {
				if (responseSystem == null || responseSystem.isEmpty()) {
					responseSystem = LOG_POINT;
				}
				
			}
			
			headerStrBuf.append("responseSystem").append(HEADER_KEY_VALUE_SEPARATOR).append(responseSystem).append(LINE_FEED);
		}

		headerStrBuf.append("curHostId").append(HEADER_KEY_VALUE_SEPARATOR).append(curHostId).append(LINE_FEED);
		
		if ("T".equals(tr_Flag)) {
			//logger.debug("(sendBmon) Header REQEUST[" + globalNo + "]\n" + headerStrBuf.toString());
			logger.info("(sendBmon) Header REQEUST[" + globalNo + "]");
		} else {
			//logger.debug("(sendBmon) Header RESPONSE[" + globalNo + "]\n" + headerStrBuf.toString());
			logger.info("(sendBmon) Header RESPONSE[" + globalNo + "]");
			
		}

		
		/*
		 * BMON 연동 Body 작성 
		 */
		
		/*
		StringBuffer bodyStrBuf = new StringBuffer(); // BMON 연동 Body String
		
		if (jsonObject == null) {
			logger.debug("(sendBmon) jsonObject is NULL");
		}
		else {
			Set<String> key = jsonObject.keySet(); // java.util.Set
			Iterator<String> iter = key.iterator(); // java.util.Iterator
			
			String keyName;
			
			while (iter.hasNext()) {
				keyName = iter.next();
				
				// [Begin] BMON 연동 JSON 데이터 Logging
				if (jsonObject.get(keyName) == null) {
					bodyStrBuf.append(keyName).append(BODY_KEY_VALUE_SEPARATOR).append(EMPTY_STRING).append(LINE_FEED);
					logger.debug("(sendBmon)(" + globalNo + ") jsonObject.get(" + keyName + ") : " + jsonObject.get(keyName));
				}
				else {
					bodyStrBuf.append(keyName).append(BODY_KEY_VALUE_SEPARATOR).append(jsonObject.get(keyName).toString()).append(LINE_FEED);
					logger.debug("(sendBmon)(" + globalNo + ") jsonObject.get(" + keyName + ") : " + jsonObject.get(keyName) + " / " + jsonObject.get(keyName).getClass());
				}
				// [End] BMON 연동 JSON 데이터 Logging
			}
		}
		logger.debug("(sendBmon) Body : \n" + bodyStrBuf.toString());
		*/
		
		String bodyString = ObjectConverter.prettyPrintJsonString(jsonObject.toJSONString());
		
		if ("T".equals(tr_Flag)) {
			//logger.debug("(sendBmon) Body REQEUST[" + globalNo + "]\n" + bodyString);
			logger.info("(sendBmon) Body REQEUST[" + globalNo + "]");
		} else {
			//logger.debug("(sendBmon) Body RESPONSE[" + globalNo + "]\n" + bodyString);
			logger.info("(sendBmon) Body RESPONSE[" + globalNo + "]");
		}
		
		/*
		 * Send BMON
		*/
		// CommonPayloadCollector.sendPayloadKeyValue("ET", headerStrBuf.toString(), bodyStrBuf.toString());
		// CommonPayloadCollector.sendPayloadKeyValue(LOG_POINT, headerStrBuf.toString(), bodyStrBuf.toString());
		CommonPayloadCollector.sendPayloadKeyValue(LOG_POINT, headerStrBuf.toString(),bodyString);
	}
}

