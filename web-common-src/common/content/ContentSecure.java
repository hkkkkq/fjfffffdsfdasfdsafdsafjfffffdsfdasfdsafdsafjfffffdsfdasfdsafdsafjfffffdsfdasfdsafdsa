package com.kt.kkos.common.content;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kt.kkos.common.enc.Seed256;
import com.kt.kkos.common.util.PatternUtil;
import com.kt.kkos.common.util.StringUtil;
import com.kt.kkos.exception.ITLBusinessException;
import com.kt.kkos.itl.bo.common.ContentMasking;
import com.kt.kkos.itl.bo.common.CrisApplier;
import com.kt.kkos.itl.mapper.common.CrisServiceMapper;
import com.kt.kkos.itl.model.common.TrtBaseInfoDTO;
import com.kt.kwl.wj.cdm.CrisSvc.DSTMREQDATA;
import com.kt.kwl.wj.cdm.CrisSvc.DSTMRESDATA;
import com.kt.kwl.wsc.cdm.WsCallCrisSvc;

@Service
public class ContentSecure {
	private static final Logger logger = LoggerFactory.getLogger(ContentSecure.class);
	
	/**
	 * 입력값중에 찾을 JSON 문자열 (암호화 항목)
	 */
	private static final String FND_INCLUDE_STRING_ENC = "__ENC__";
	
	/**
	 * 입력값중에 찾을 JSON 문자열 (보안키패트 적용 필드 복호화 대상)
	 */
	public static final String PREFIX_NPKP_STRING_FIELD = "__NPKP__";
	
	@Autowired
	private ContentMasking contentMasking;
	
	@Autowired
	private CrisApplier crisServiceCaller;
	
	@Autowired
	private CrisServiceMapper crisServiceDao;
	
	/**
	 * JSONObject Output 암호화 및 마스킹 처리
	 * @param JSONObject
	 * @param Class
	 */
	public Object secureOut(Object object, Class<?> clazz) {

		JSONParser jsonParser = null;
		JSONObject jsnObj = null;
		Gson gson = new Gson();
		
		try {
			jsonParser = new JSONParser();
			jsnObj = (JSONObject)jsonParser.parse(new GsonBuilder().serializeNulls().create().toJson(object));
			
			logger.debug("(secureOut) jsnObj.toString() : " + jsnObj.toString());
			
			// 마스킹 처리
			mask(jsnObj);
			
		}
		catch (Exception e) {
			ITLBusinessException.printErrorStack(e);
		}
		
		return gson.fromJson(jsnObj.toString(), clazz);
	}
	
	
	/**
	 * JSONObject Output 암호화 및 마스킹 처리
	 * @param String
	 */
	public String secureOut(String jsonStr) {

		JSONParser jsonParser = null;
		JSONObject jsnObj = null;
		
		try {
			jsonParser = new JSONParser();
			jsnObj = (JSONObject)jsonParser.parse(jsonStr);
			logger.debug("(secureOut) jsnObj.toString() : " + jsnObj.toString());
			
			// 마스킹 처리
			mask(jsnObj);
		}
		catch (Exception e) {
			ITLBusinessException.printErrorStack(e);
		}
		
		return jsnObj.toString();
	}
	
	
	
	/**
	 * JSONObject Output 암호화 및 마스킹 처리
	 * @param JSONObject
	 * @throws ITLBusinessException 
	 */
	public void secureOut(JSONObject jsonObject) throws ITLBusinessException {
		try {
			if (jsonObject == null)	return;
			
			// 마스킹 처리
			mask(jsonObject);
		}
		catch (Exception e) {
			throw new ITLBusinessException(e);
		}
	}
	
	
	
	
	/**
	 * JSONObject Output 암호화 및 마스킹 처리
	 * @param byteArray
	 */
	public JSONObject secureOut(byte [] bt) {
		
		logger.debug("(secureOut) bt.length : " + bt.length);
		if (bt.length == 0) return null;

		String jsonStr = new String(bt);
		JSONParser jsonParser = null;
		JSONObject jsnObj = null;
		
		try {
			jsonParser = new JSONParser();
			jsnObj = (JSONObject)jsonParser.parse(jsonStr);
			logger.debug("(secureOut) jsnObj.toString() : " + jsnObj.toString());
			
			// 마스킹 처리
			mask(jsnObj);
		}
		catch (Exception e) {
			ITLBusinessException.printErrorStack(e);
		}
		
		return jsnObj;
	}
	
	
	/**
	 * output trtBaseInfoDTO 중 보안이 필요한 항목 취합 및 암호화 & 해당 항목 삭제 또는 마스킹 
	 * @param JSONObject
	 */
	@SuppressWarnings("unchecked")
	public void secureTrtBaseInfoDTO (JSONObject jsonObject) {
		
		/*
		 * trtBaseInfoEtc JSONObject 생성 및 TrtBaseInfoDTO 의 보안 항목 세팅
		 */
		JSONObject jsonTrtBaseInfoEtc = new JSONObject();
//		jsonTrtBaseInfoEtc.put("orgId", jsonObject.get("orgId"));
//		jsonTrtBaseInfoEtc.put("orgNm", jsonObject.get("orgNm"));
//		jsonTrtBaseInfoEtc.put("cpntId", jsonObject.get("cpntId"));
//		jsonTrtBaseInfoEtc.put("clntIp", jsonObject.get("clntIp"));
		jsonTrtBaseInfoEtc.put("cmpnCd", jsonObject.get("cmpnCd"));
		jsonTrtBaseInfoEtc.put("userTelNo", jsonObject.get("userTelNo"));
		
		/*
		 * trtBaseInfoDTO 에서 보안 항목 삭제 처리
		 */
//		jsonObject.remove("orgId");
//		jsonObject.remove("orgNm");
//		jsonObject.remove("cpntId");
//		jsonObject.remove("clntIp");
		jsonObject.remove("cmpnCd");
		jsonObject.remove("userTelNo");
		
		/*
		 * trtBaseInfoDTO 에 trtBaseInfoEtc 항목 추가 및 데이터 암호화
		 */
		jsonObject.put("trtBaseInfoEtc", Seed256.getInstance().encryptBase64Wrapped(jsonTrtBaseInfoEtc.toString()));
		
		logger.debug("(secureTrtBaseInfoDTO) jsnObj.toString() : " + jsonObject.toString());
	}
	
	
	/**
	 * input trtBaseInfoDTO 중 암호화된 trtBaseInfoEtc 항목 복호화 및 trtBaseInfoDTO 에 추가 & trtBaseInfoEtc 삭제
	 * @param JSONObject
	 */
	@SuppressWarnings("unchecked")
	public void unSecureTrtBaseInfoDTO (JSONObject jsonObject) {
		
		/*
		 * trtBaseInfoEtc 가 Null 또는 값이 없는 경우 Return
		 */
		if ( (jsonObject.get("trtBaseInfoEtc") == null) || (jsonObject.get("trtBaseInfoEtc").toString().trim().length() < 1) ) {
			return;
		}

		try {
			/*
			 * trtBaseInfoEtc 값 복호화 및 JSONObject 로 변환
			 */
			String tmpDecStr = Seed256.getInstance().decryptBase64Wrapped(jsonObject.get("trtBaseInfoEtc").toString());
			
			JSONObject jsonTrtBaseInfoEtc = (JSONObject)(new JSONParser()).parse(tmpDecStr);
			
			/*
			 * trtBaseInfoDTO 에 복호화 된 항목 등록
			 *   - 단, 해당 정보가 UI 로 부터 입력된 경우, 입력된 정보 유지.. 
			 */
//			if (jsonObject.get("orgId") == null)	jsonObject.put("orgId", jsonTrtBaseInfoEtc.get("orgId"));
//			if (jsonObject.get("orgNm") == null)	jsonObject.put("orgNm", jsonTrtBaseInfoEtc.get("orgNm"));
//			if (jsonObject.get("cpntId") == null)	jsonObject.put("cpntId", jsonTrtBaseInfoEtc.get("cpntId"));
//			if (jsonObject.get("clntIp") == null)	jsonObject.put("clntIp", jsonTrtBaseInfoEtc.get("clntIp"));
			if (jsonObject.get("cmpnCd") == null)	jsonObject.put("cmpnCd", jsonTrtBaseInfoEtc.get("cmpnCd"));
			if (jsonObject.get("userTelNo") == null)	jsonObject.put("userTelNo", jsonTrtBaseInfoEtc.get("userTelNo"));
			
			/*
			 * trtBaseInfoDTO 의 trtBaseInfoEtc 항목 삭제
			 */
			jsonObject.remove("trtBaseInfoEtc");
			
			logger.debug("(unSecureTrtBaseInfoDTO) jsnObj.toString() : " + jsonObject.toString());
		}
		catch (Exception e) {
			ITLBusinessException.printErrorStack(e);
		}
	}
	
	
	/**
	 * Masking 및 데이터 암호화
	 * @param JSONObject
	 * @throws IOException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 * @throws SecurityException 
	 * @throws NoSuchFieldException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	@SuppressWarnings("unchecked")
	public void mask(JSONObject jsonObject) throws JsonParseException, JsonMappingException, NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, IOException {
		
		Set<String> key = jsonObject.keySet(); // java.util.Set
		Iterator<String> iter = key.iterator(); // java.util.Iterator
		
		String keyName;
		String orgKeyName;
		String valueType;
		String tmpStr;
		
		while (iter.hasNext()) {
			keyName = iter.next();
			valueType = (jsonObject.get(keyName) == null) ? "" : jsonObject.get(keyName).getClass().toString();
//			logger.debug("(mask) [valueType][keyName] Value : [" + valueType + "][" + keyName + "] " + ((jsonObject.get(keyName) == null) ? "null" : jsonObject.get(keyName).toString()));
			
			// Value 가 JSONObject 인 경우 재귀 호출
			if (valueType.indexOf("JSONObject") >= 0) {
				try {
					mask((JSONObject)jsonObject.get(keyName));
				}
				catch (Exception e) {
					ITLBusinessException.printErrorStack(e);
				}
			}
			// Value 가 JSONArray 인 경우 JSONArray.size 만큼 Looping 및 재귀 호출
			else if (valueType.indexOf("JSONArray") >= 0) {
				try {
					JSONArray jsonArray = (JSONArray)jsonObject.get(keyName);
					for (int ii=0; ii<jsonArray.size(); ii++) {
						mask((JSONObject)jsonArray.get(ii));
					}
				}
				catch (Exception e) {
					ITLBusinessException.printErrorStack(e);
				}
			}
			// Value 가 JSONObject 또는 JSONArray 가 아니면(String 이면) UnMasking 처리
			else {
				if ( (keyName != null) && (keyName.indexOf(FND_INCLUDE_STRING_ENC) > 0) ) {
					
					// Original Key Name 추출
					orgKeyName = keyName.replaceAll(FND_INCLUDE_STRING_ENC, "");
					
//					logger.debug("(mask) Encrypt KeyName Mapping : " + keyName + " -> " + orgKeyName);
					
					if ( (jsonObject.get(orgKeyName) != null) && (jsonObject.get(orgKeyName).toString().trim().length() > 0) ) {
						tmpStr = jsonObject.get(orgKeyName).toString();
						
						// 암호화 데이터 Encrypt 항목에 입력
						jsonObject.put(keyName, Seed256.getInstance().encryptBase64Wrapped(tmpStr));
						
						// Encrypt 대상 원래 항목 값 마스킹 처리
						jsonObject.put(orgKeyName, contentMasking.getMaskingAplyStr(orgKeyName, tmpStr, new ObjectMapper().readValue(jsonObject.toString(), Map.class)));
						
						
						logger.debug("(mask) orgKeyName (" + orgKeyName + ") Value Mask & Encrypt : Masking : " + tmpStr + " -> " + jsonObject.get(orgKeyName) + ", Encrypt : " + jsonObject.get(keyName));
					}
//					else {
//						logger.debug("(mask) orgKeyName (" + orgKeyName + ") Value : " + (jsonObject.get(orgKeyName) == null ? "null" : jsonObject.get(orgKeyName).toString()));
//					}
				}
			}
		}
	}
	
	
	/*
	 * RequestBody 에 Masking 또는 Encrypt 된 내용을 UnMasking & Decrypt 처리 Main
	 * @param StringBuilder
	 */
	@SuppressWarnings("unchecked")
	public JSONObject unSecureIn(StringBuilder buffer)
	{
		logger.debug("(unSecureIn) buffer : \n" + buffer);
//		logger.debug("(unSecureIn) buffer.toString : " + buffer.toString());
		logger.debug("(unSecureIn) buffer.length : " + buffer.length());
		
		JSONParser jsonParser = null;
		JSONObject jsonObject = null;
		
		try {
			// RequestBody(buffer) 내용을 JSONObject 포맷으로 변경 
			jsonParser = new JSONParser();
			
			logger.debug("(unSecureIn) buffer.length() : " + buffer.length());
			logger.debug("(unSecureIn) buffer.capacity() : " + buffer.capacity());
			
			if ( buffer.length() < 1) {
				return jsonObject;
			}
			
			jsonObject = (JSONObject)jsonParser.parse(buffer.toString());
			
			logger.debug("(unSecureIn) jsonObject.size() : " + jsonObject.size());
			
			// [Begin] JSON 으로의 전환 결과에 대해 상위 Key 목록 조회용 (테스트)
			Set<String> key = jsonObject.keySet(); // java.util.Set
			Iterator<String> iter = key.iterator(); // java.util.Iterator
			
			String keyName;
			
			while (iter.hasNext()) {
				keyName = iter.next();
//				logger.debug("(unSecureIn) iter.next() : " + keyName);
				if (jsonObject.get(keyName) == null) {
					logger.debug("(unSecureIn) jsonObject.get(" + keyName + ") : " + jsonObject.get(keyName) + " / NULL");
				}
				else {
					logger.debug("(unSecureIn) jsonObject.get(" + keyName + ") : " + jsonObject.get(keyName) + " / " + jsonObject.get(keyName).getClass());
				}
				
			}
			// [End] JSON 으로의 전환 결과에 대해 상위 Key 목록 조회용 (테스트)
			
			// 항목 Name 체크하여 UnMask & Decrypt 처리 수행
			unMask (jsonObject);
			
			logger.debug("(unSecureIn) jsonObject.toJSONString() : " + jsonObject.toJSONString());
			logger.debug("(unSecureIn) jsonObject.toString() : " + jsonObject.toString());
		}
		catch (Exception e) {
					ITLBusinessException.printErrorStack(e);
		}
		
		// RequestBody 수정 내용 리턴
		return jsonObject;
	}
	
	
	
	/*
	 * RequestBody 에 Masking 또는 Encrypt 된 내용을 UnMasking & Decrypt 처리 Main
	 * @param JSONObject
	 */
	@SuppressWarnings("unchecked")
	public void unSecureIn(JSONObject jsonObject) throws ITLBusinessException
	{
		try {
			if (jsonObject == null)	return;
			
			logger.debug("(unSecureIn) jsonObject.size() : " + jsonObject.size());
			
			// [Begin] JSON 으로의 전환 결과에 대해 상위 Key 목록 조회용 (테스트)
			/*
			Set<String> key = jsonObject.keySet(); // java.util.Set
			Iterator<String> iter = key.iterator(); // java.util.Iterator
			
			String keyName;
			
			while (iter.hasNext()) {
				keyName = iter.next();
//				logger.debug("(unSecureIn) iter.next() : " + keyName);
				if (jsonObject.get(keyName) == null) {
					logger.debug("(unSecureIn) jsonObject.get(" + keyName + ") : " + jsonObject.get(keyName) + " / NULL");
				}
				else {
					logger.debug("(unSecureIn) jsonObject.get(" + keyName + ") : " + jsonObject.get(keyName) + " / " + jsonObject.get(keyName).getClass());
				}
				
			}
			*/
			// [End] JSON 으로의 전환 결과에 대해 상위 Key 목록 조회용 (테스트)
			
			// 항목 Name 체크하여 UnMask & Decrypt 처리 수행
			unMask (jsonObject);
			
			logger.debug("(unSecureIn) jsonObject.toJSONString() : " + jsonObject.toJSONString());
			//logger.debug("(unSecureIn) jsonObject.toString() : " + jsonObject.toString());
		}
		catch (Exception e) {
			throw new ITLBusinessException(e);
		}
	}
	
	
	/*
	 * Masking 또는 Encrypt 된 내용을 UnMasking & Decrypt 처리
	 * @param JSONObject
	 */
	@SuppressWarnings("unchecked")
	public void unMask(JSONObject jsonObject)
	{
		Set<String> key = jsonObject.keySet(); // java.util.Set
		Iterator<String> iter = key.iterator(); // java.util.Iterator
		
		String keyName;
		String orgKeyName;
		String valueType;
		
		while (iter.hasNext()) {
			try {
				keyName = iter.next();
				valueType = (jsonObject.get(keyName) == null) ? "" : jsonObject.get(keyName).getClass().toString();
//				logger.debug("(unMask) [valueType][keyName] Value : [" + valueType + "][" + keyName + "] " + ((jsonObject.get(keyName) == null) ? "null" : jsonObject.get(keyName).toString()));
				/*
				 * JSONObject 인 경우 
				 * 항목을 추출하기 위해 재귀 호출
				 */
				if (valueType.indexOf("JSONObject") >= 0) {
					unMask((JSONObject)jsonObject.get(keyName));
				}
				/*
				 * JSONArray 인 경우 
				 * Array 개수 만큼 for looping & JSONObject 추출 후 재귀 호출
				 */
				else if (valueType.indexOf("JSONArray") >= 0) {
					JSONArray jsonArray = (JSONArray)jsonObject.get(keyName);
					for (int ii=0; ii<jsonArray.size(); ii++) {
						unMask((JSONObject)jsonArray.get(ii));
					}
				}
				/*
				 * JSONObject 또는 JSONArray 가 아닌 경우 
				 * Key Name 을 체크하여 Value 를 암호화 또는 마스킹, 가상번호 조회 수행
				 */
				else {
					if ( (keyName != null) && (keyName.indexOf(FND_INCLUDE_STRING_ENC) > 0) ) {
						if ( (jsonObject.get(keyName) != null) && (jsonObject.get(keyName).toString().trim().length() > 0) ) {
							// 원래 항목에 복호화 값 입력
							orgKeyName = keyName.replaceAll(FND_INCLUDE_STRING_ENC, "");
							
							// 암호화 해제 여부 확인 (마스킹(*) 이 되어 있으면,, 암호화된 값을 복호화 하여 입력, else 암호화 항목 null 처리)
							//   ==> 응답시, 원래 값을 이용하여 암호화 처리 및 암호화 항목에 입력
//							logger.debug("(unMask) Encrypt KeyName Mapping : " + keyName + " -> " + orgKeyName);
//							logger.debug("(unMask) orgKeyName Value : " + orgKeyName + " -> " + jsonObject.get(orgKeyName).toString());
							if ( (jsonObject.get(orgKeyName) != null) && (jsonObject.get(orgKeyName).toString().indexOf("*") >= 0) ) {
								// 복호화 결과 원래 항목에 입력
								logger.debug("(unMask) Modify Value (" + orgKeyName + ") : " + jsonObject.get(orgKeyName).toString() + " -> " + Seed256.getInstance().decryptBase64Wrapped(jsonObject.get(keyName).toString()));
								jsonObject.put(orgKeyName, Seed256.getInstance().decryptBase64Wrapped(jsonObject.get(keyName).toString()));
								
								// 암호화 항목 값 Null 처리
								jsonObject.put(keyName, null);
							}
							else {
//								logger.debug("(unMask) Not Modify Value (" + orgKeyName + ") : "+ jsonObject.get(orgKeyName) == null ? "null" : jsonObject.get(orgKeyName).toString());
								
								// 암호화 항목 값 Null 처리
								jsonObject.put(keyName, null);
							}
						}
					}
				}
			}
			catch (Exception e) {
				ITLBusinessException.printErrorStack(e);
			}
		}
	}
	
	
	
	/*
	 * 가상 번호 적용 (Cris 연동)
	 * @param JSONObject
	 */
	public void crisApply(TrtBaseInfoDTO trtBaseInfoDTO, JSONObject jsonObject) throws ITLBusinessException
	{
		if (jsonObject == null) return;
		
		//logger.debug("(crisApply) trtBaseInfoDTO.toString() : "+ trtBaseInfoDTO);
//		logger.debug("(crisApply) jsonObject.toString() : "+ jsonObject.toString());
		
		// 가상번호 적용 대상 column 명 조회 (DB 조회)
		List<Map<String, String>> crisAplyInfo	= crisServiceDao.getCrisAplyInfo();
		
		/*
		 * 가상번호 적용 대상 Map 변환
		*/
		Map<String, String> crisApplyInfoMap = new HashMap<String, String>();
//		logger.debug("(crisApply) crisAplyInfo.size() : "+ crisAplyInfo.size());
		for ( Map<String, String> aplyInfo : crisAplyInfo ) {
			crisApplyInfoMap.put(aplyInfo.get("crisFld"), aplyInfo.get("crisType"));
//			logger.debug("(crisApply) crisAplyInfo - crisFld : " + aplyInfo.get("crisFld") + ", crisType : " + aplyInfo.get("crisType")); 
		}
		
		
		// 가상번호 적용 Original Data 추출 및 Cris Request 작성
		List<DSTMREQDATA> crisRequestList = new ArrayList<>();
		makeCrisRequest(jsonObject, crisApplyInfoMap, crisRequestList);
//		logger.debug("(crisApply) crisRequestList.size() : "+ crisRequestList.size());
		
		// 가상번호 조회 (Cris 연동)
		List<DSTMRESDATA> crisResponseList = new ArrayList<>();
		if ( (crisRequestList != null) && (crisRequestList.size() > 0) ) {
			WsCallCrisSvc wsCallCrisSvc = new WsCallCrisSvc();
//			TrtBaseInfoDTO trtBaseInfoDTO = new ObjectMapper().readValue(jsonObject.get("trtBaseInfoDTO").toString(), TrtBaseInfoDTO.class);
			crisResponseList = wsCallCrisSvc.wscallCrisSvc(trtBaseInfoDTO, crisRequestList);
		}
		
		/*
		 * 가상 번호 적용 (치환)
		*/
		// Cris 연동 결과 Map 으로 변환 (Original Value, Cris Result Value)
		Map<String, String> crisAppliedMap = new HashMap<String, String>();
		for ( DSTMRESDATA resData : crisResponseList ) {
			crisAppliedMap.put(resData.getReqData(), resData.getResData1());
			logger.debug("(crisApply) crisApliedMap - orgValue : " + resData.getReqData() + ", crisAppliedData : " + resData.getResData1());
		}
		
		applyCrisData (jsonObject, crisApplyInfoMap, crisAppliedMap);
	}
	
	
	/*
	 * 가상 번호 적용 대상 추출
	 * @param JSONObject
	 */
	@SuppressWarnings("unchecked")
	public void makeCrisRequest(JSONObject jsonObject, Map<String, String> crisApplyInfoMap, List<DSTMREQDATA> crisRequestList)
	{	
		Set<String> key = jsonObject.keySet(); // java.util.Set
		Iterator<String> iter = key.iterator(); // java.util.Iterator
		
		String keyName;
		String valueType;
		
		while (iter.hasNext()) {
			try {
				keyName = iter.next();
				valueType = (jsonObject.get(keyName) == null) ? "" : jsonObject.get(keyName).getClass().toString();
				
				/*
				 * JSONObject 인 경우 
				 * 항목을 추출하기 위해 재귀 호출
				 */
				if (valueType.indexOf("JSONObject") >= 0) {
					makeCrisRequest((JSONObject)jsonObject.get(keyName), crisApplyInfoMap, crisRequestList);
				}
				/*
				 * JSONArray 인 경우 
				 * Array 개수 만큼 for looping & JSONObject 추출 후 재귀 호출
				 */
				else if (valueType.indexOf("JSONArray") >= 0) {
					JSONArray jsonArray = (JSONArray)jsonObject.get(keyName);
					for (int ii=0; ii<jsonArray.size(); ii++) {
						makeCrisRequest((JSONObject)jsonArray.get(ii), crisApplyInfoMap, crisRequestList);
					}
				}
				/*
				 * JSONObject 또는 JSONArray 가 아닌 경우 
				 * Key Name 을 체크하여 Cris 일괄 조회를 위한 request 작성
				 */
				else {
					// Cris 적용 대상 컬럼명일 경우
//					if ( fieldName.equals(aplyColumnName) && !StringUtil.isEmpty((String)fieldValue) ) {
//					if (keyName.equals("custIdfyNo")) {
//						logger.debug("(makeCrisRequest) custIdfyNo : "+ jsonObject.get(keyName));
//						logger.debug("(makeCrisRequest) crisAplyInfoMap.get(" + keyName + ") : "+ crisAplyInfoMap.get(keyName));
//						logger.debug("(makeCrisRequest) StringUtil.isEmpty((String)jsonObject.get(" + keyName + ") : "+ StringUtil.isEmpty((String)jsonObject.get(keyName)));
//					}
					if ( (crisApplyInfoMap.get(keyName) != null) && !StringUtil.isEmpty((String)jsonObject.get(keyName)) ) {
//						logger.debug("(makeCrisRequest) Cris Apply Dest name : "+ keyName + ", value : " + jsonObject.get(keyName));

						DSTMREQDATA reqData = new DSTMREQDATA();
						
						//skip Flag 
						boolean skipFlag = false;
						
						switch (crisApplyInfoMap.get(keyName))
						{
							
							case "01"	 	:		// 주민/외국인 번호
								
								//custIdfyNo 컬럼중 대상은 주민등록번호/외국인등록번호임 ,   사업자번호의경우 SKIP 처리 함.
								if(!StringUtil.isVirtualNumber((String)jsonObject.get(keyName)) && !PatternUtil.isValidResno((String)jsonObject.get(keyName)) && !PatternUtil.isValidForgnRgstNo((String)jsonObject.get(keyName)))
								{
//									logger.debug("(makeCrisRequest) SKIP Cris Convert (Not Virtual, ResNo, ForgnRgs) : "+ keyName + ", value : " + jsonObject.get(keyName));
									skipFlag = true;
									break;
								}
								
								// 가상주민번호인 경우 SKIP 처리함
								if(StringUtil.isVirtualNumber((String)jsonObject.get(keyName)))
								{
//									logger.debug("(makeCrisRequest) SKIP Cris Convert (already Virtual No) : "+ keyName + ", value : " + jsonObject.get(keyName));
									skipFlag = true;
									break;
								}
								
								reqData.setReqType(StringUtil.isVirtualNumber((String)jsonObject.get(keyName)) ? "300" : "400");
								reqData.setReqData((String)jsonObject.get(keyName));
								
								break;
								
							case "02"	 	:		// 금융 번호
								
								// 가상주민번호인 경우 SKIP 처리함
								if(StringUtil.isVirtualNumber((String)jsonObject.get(keyName)))
								{
									skipFlag = true;
									break;
								}
								
								reqData.setReqType(StringUtil.isVirtualNumber((String)jsonObject.get(keyName)) ? "370" : "470");
								reqData.setReqData((String)jsonObject.get(keyName));
								break;
								
							case "03"	 	:		// 운전면허 번호
								
								// 가상주민번호인 경우 SKIP 처리함
								if(StringUtil.isVirtualNumber((String)jsonObject.get(keyName)))
								{
									skipFlag = true;
									break;
								}
								
								reqData.setReqType(StringUtil.isVirtualNumber((String)jsonObject.get(keyName)) ? "360" : "440");
								reqData.setReqData((String)jsonObject.get(keyName));
								reqData.setReqAdd1("D");
								
								break;
								
							case "04"		:		// 여권 번호
								
								// 가상주민번호인 경우 SKIP 처리함
								if(StringUtil.isVirtualNumber((String)jsonObject.get(keyName)))
								{
									skipFlag = true;
									break;
								}
								
								reqData.setReqType(StringUtil.isVirtualNumber((String)jsonObject.get(keyName)) ? "360" : "440");
								reqData.setReqData((String)jsonObject.get(keyName));
								reqData.setReqAdd1("P");
								
								break;

							default:
								break;
						}
						
						//SKIP이 아닌경우에만 Add 처리
						if(!skipFlag) crisRequestList.add(reqData);
					}
				}
			}
			catch (Exception e) {
				ITLBusinessException.printErrorStack(e);
			}
		}
	}
	
	/*
	 * Cris 연동 결과 적용
	 * @param JSONObject
	 */
	@SuppressWarnings("unchecked")
	public void applyCrisData(JSONObject jsonObject, Map<String, String> crisApplyInfoMap, Map<String, String> crisAppliedMap)
	{	
		Set<String> key = jsonObject.keySet(); // java.util.Set
		Iterator<String> iter = key.iterator(); // java.util.Iterator
		
		String keyName;
		String valueType;
		
		while (iter.hasNext()) {
			try {
				keyName = iter.next();
				valueType = (jsonObject.get(keyName) == null) ? "" : jsonObject.get(keyName).getClass().toString();
				
				/*
				 * JSONObject 인 경우 
				 * 항목을 추출하기 위해 재귀 호출
				 */
				if (valueType.indexOf("JSONObject") >= 0) {
					applyCrisData((JSONObject)jsonObject.get(keyName), crisApplyInfoMap, crisAppliedMap);
				}
				/*
				 * JSONArray 인 경우 
				 * Array 개수 만큼 for looping & JSONObject 추출 후 재귀 호출
				 */
				else if (valueType.indexOf("JSONArray") >= 0) {
					JSONArray jsonArray = (JSONArray)jsonObject.get(keyName);
					for (int ii=0; ii<jsonArray.size(); ii++) {
						applyCrisData((JSONObject)jsonArray.get(ii), crisApplyInfoMap, crisAppliedMap);
					}
				}
				/*
				 * JSONObject 또는 JSONArray 가 아닌 경우 
				 * Key Name 을 체크하여 Cris 일괄 조회를 위한 request 작성
				 */
				else {
					/*
					 * Cris 적용 대상 항목명 이고, 값이 존재하는 경우로
					 *   - 항목명이 Cris 연동 대상이고, 
					 *   - 항목명의 값이 Null 또는 빈 값이 아니고,
					 *   - Cris로 요청한 값이면
					*/
					if ( (crisApplyInfoMap.get(keyName) != null) && !StringUtil.isEmpty((String)jsonObject.get(keyName)) && crisAppliedMap.get((String)jsonObject.get(keyName)) != null) {
						logger.debug("(applyCrisData) Cris Apply Dest name : "+ keyName + ", value : " + jsonObject.get(keyName) + " -> 치환값 : " + crisAppliedMap.get((String)jsonObject.get(keyName)));

						// Original 값을 Cris 요청 결과 값으로 치환
						jsonObject.put(keyName, crisAppliedMap.get((String)jsonObject.get(keyName)));
					}
				}
			}
			catch (Exception e) {
				ITLBusinessException.printErrorStack(e);
			}
		}
	}
	
	
	/*
	 * JSON 내 보안키패드 암호화 대상 필드(ex. __NPKP__custInfoDTO_custIdfyNo) 복호화 값 decryptInfo 별도 저장
	 * JSON DATA 내 보안키패드 적용 입력 대상 필드 복호화 수행 (CASE A)에서 사용했던 로직으로 현재 사용하지 않아 주석 처리 함.
	 * @param JSONObject
	 * @param Map<String,String>
	 */
	
//	public void decryptInfo(JSONObject jsonObject, Map<String,String> decryptInfo, HttpServletRequest  httpRequest) throws ParseException, ITLBusinessException
//	{
//		
//		Map<String, String> map = RequestUtil.parseJson(jsonObject.toJSONString());
//		//Map<String, String> map = RequestUtil.parseJson(decodeBuffer);
//		
//		HttpServletRequest httpRequest_nprotect = new PluginFreeRequest(httpRequest, map);
//		
//		Iterator<String> iterMap = map.keySet().iterator();
//		
//		String valueType;
//		
//		logger.debug("==================================================================");
//		logger.debug("# nprotect key pad decrypt info");
//		
//		while(iterMap.hasNext()){
//			
//			String key = iterMap.next();
//			String value = httpRequest_nprotect.getParameter(key);
//									
//			logger.debug("# key[" + key + "]:value[" + value + "]");
//			
//			JSONObject jsonObject2 = new JSONObject();
//			
//			try {
//				jsonObject2 = ObjectConverter.stringToJson(value);
//			} catch (Exception e) {
//				// JSON Parsing Error 시
//				jsonObject2 = null;
//			}
//			
//			valueType = (jsonObject2 == null) ? "" : jsonObject2.getClass().toString();
//			
//			/*
//			 * JSONObject 인 경우 
//			 * 항목을 추출하기 위해 재귀 호출
//			 */
//			if (valueType.indexOf("JSONObject") >= 0) {
//				decryptInfo(jsonObject2, decryptInfo, httpRequest_nprotect);
//			}
//			/*
//			 * JSONArray 인 경우 
//			 * Array 개수 만큼 for looping & JSONObject 추출 후 재귀 호출
//			 */
//			else if (valueType.indexOf("JSONArray") >= 0) {
//
//				for (int ii=0; ii<jsonObject2.size(); ii++) {
//					decryptInfo((JSONObject)jsonObject2.get(ii), decryptInfo, httpRequest_nprotect);
//				}
//			}
//			/*
//			 * JSONObject 또는 JSONArray 가 아닌 경우 
//			 * Key를 체크하여 복호화 대상 값 decryptInfo 저장
//			 */
//			else {
//				if ( (key != null) && (key.startsWith(PREFIX_NPKP_STRING_FIELD)) ) {
//					
//					if (value != null && value.length() > 0) {
//						decryptInfo.put(key, value);
//					}
//				} else if (key != null && key.equals("__E2E_UNIQUE__")) {
//					if(value != null && !"".equals(value)) {
//						logger.debug("# 보안 키패드 입력기가 실행중입니다.");
//
//						try {
//							PluginFree.verify(httpRequest_nprotect);
//						} catch(Exception e) {
//							ITLBusinessException.printErrorStack(e);
//							logger.debug("# 키보드보안/마우스입력기 복호화 검증 오류가 발생하였습니다.");
//						}
//					}
//				}
//			}
//		}
//		logger.debug("==================================================================");
//	}

	
	
	/*
	 * JSON 내 보안키패드 암호화 대상 필드(ex. __NPKP__custInfoDTO__custIdfyNo) 복호화 값으로 일반 필드(ex. custIdfyNo)를 찾아 대체
	 * @param JSONObject
	 * @param Map<String,String>
	 */
	public void decryptSecureKeyPad(JSONObject jsonObject, Map<String,String> decryptInfo) throws ITLBusinessException
	{
		try {
			if (jsonObject == null || decryptInfo == null)	return;
			
			// jsonObject 조회
			logger.debug("(decryptSecureKeyPad) BEFORE_jsonObject : " + jsonObject);
			logger.debug("(decryptSecureKeyPad) jsonObject.size() : " + jsonObject.size());
			
			// Params 로그 출력 부분 주석 처리
			/*
			Set<String> key1 = jsonObject.keySet(); // java.util.Set
			Iterator<String> iter1 = key1.iterator(); // java.util.Iterator
			
			String keyName1;
			
			while (iter1.hasNext()) {
				keyName1 = iter1.next();

				if (jsonObject.get(keyName1) == null) {
					logger.debug("(decryptSecureKeyPad) jsonObject key[" + keyName1 + "]:value[" + jsonObject.get(keyName1) + "], value_type=NULL");
				}
				else {
					logger.debug("(decryptSecureKeyPad) jsonObject key[" + keyName1 + "]:value[" + jsonObject.get(keyName1) + "], value_type=" + jsonObject.get(keyName1).getClass());
				}
				
			}
			
			// decryptInfo 조회
			Set<String> key2 = decryptInfo.keySet();
			Iterator<String> iter2 = key2.iterator();
			
			String keyName2;
			
			while (iter2.hasNext()) {
				keyName2 = iter2.next();

				if (decryptInfo.get(keyName2) == null) {
					logger.debug("(decryptSecureKeyPad) decryptInfo key[" + keyName2 + "]:value[" + decryptInfo.get(keyName2) + "], value_type=NULL");
				}
				else {
					logger.debug("(decryptSecureKeyPad) decryptInfo key[" + keyName2 + "]:value[" + decryptInfo.get(keyName2) + "], value_type=" + decryptInfo.get(keyName2).getClass());
				}
				
			}
			*/

			// jsonObject value값 대체 (ex. __NPKP__custInfoDTO__custIdfyNo 필드 복화화 값으로 대상 필드를 찾아 대체)
			replaceDecryptSecurePad(jsonObject,decryptInfo);
			
			// jsonObject value값 복사 (ex. custIdfyNo 필드에 복화화 값으로 복사)
			// copyDecryptSecurePad(jsonObject,decryptInfo);
			
			logger.debug("(decryptSecureKeyPad) AFTER_jsonObject : " + jsonObject);
			//logger.debug("(decryptSecurePad) jsonObject.toJSONString() : " + jsonObject.toJSONString());
			//logger.debug("(decryptSecurePad) jsonObject.toString() : " + jsonObject.toString());
		}
		catch (Exception e) {
			throw new ITLBusinessException(e);
		}
	}
	
	/*
	 * JSON 내 보안키패드 암호화 대상 필드(ex. __NPKP__custInfoDTO__custIdfyNo) 복호화 값으로 일반 필드(ex. custIdfyNo)를 찾아 대체
	 * @param JSONObject
	 * @param Map<String,String>
	 */
	@SuppressWarnings("unchecked")
	public void replaceDecryptSecurePad(JSONObject jsonObject, Map<String,String> decryptInfo)
	{
//		Set<String> key = jsonObject.keySet();
//		Iterator<String> iter = key.iterator();
//		
//		String keyName;
//		String valueType;
//		
//		while (iter.hasNext()) {
//			try {
//				keyName = iter.next();
//				valueType = (jsonObject.get(keyName) == null) ? "" : jsonObject.get(keyName).getClass().toString();
//				//logger.debug("(unMask) [valueType][keyName] Value : [" + valueType + "][" + keyName + "] " + ((jsonObject.get(keyName) == null) ? "null" : jsonObject.get(keyName).toString()));
//				
//				/*
//				 * JSONObject 인 경우 
//				 * 항목을 추출하기 위해 재귀 호출
//				 */
//				if (valueType.indexOf("JSONObject") >= 0) {
//					replaceDecryptSecurePad((JSONObject)jsonObject.get(keyName),decryptInfo);
//				}
//				/*
//				 * JSONArray 인 경우 
//				 * Array 개수 만큼 for looping & JSONObject 추출 후 재귀 호출
//				 */
//				else if (valueType.indexOf("JSONArray") >= 0) {
//					JSONArray jsonArray = (JSONArray)jsonObject.get(keyName);
//					for (int ii=0; ii<jsonArray.size(); ii++) {
//						replaceDecryptSecurePad((JSONObject)jsonArray.get(ii),decryptInfo);
//					}
//				}
//				/*
//				 * JSONObject 또는 JSONArray 가 아닌 경우 
//				 * Key Name 을 체크하여 Value값 대체
//				 */
//				else {
//					if ( (keyName != null) && (keyName.startsWith(PREFIX_NPKP_STRING_FIELD)) ) {
//						if ( (jsonObject.get(keyName) != null) && (jsonObject.get(keyName).toString().trim().length() > 0) ) {
//							
//							// 암호화 대상 필드(ex. __NPKP__custInfoDTO_custIdfyNo) 복호화 값으로 대체
//							jsonObject.put(keyName,decryptInfo.get(keyName));
//							
//							// 암호화 대상 필드(ex. __NPKP__custInfoDTO_custIdfyNo) 복호화 값으로 일반 필드에 복사(custIdfyNo)
//							
//							String orgKeyname = keyName.replace(PREFIX_NPKP_STRING_FIELD, ""); 						// __NPKP__custInfoDTO_custIdfyNo => custInfoDTO_custIdfyNo
//							orgKeyname = orgKeyname.substring(orgKeyname.indexOf("DTO")+4, orgKeyname.length());    // custInfoDTO_custIdfyNo => custIdfyNo
//							
//							jsonObject.put(orgKeyname, decryptInfo.get(keyName));
//						}
//					}
//				}
//			}
//			catch (Exception e) {
//				ITLBusinessException.printErrorStack(e);
//			}
//		}
		
		Set<String> key = decryptInfo.keySet();
		Iterator<String> iter = key.iterator();
		
		
		while (iter.hasNext()) {
			
			// __NPKP__custInfoDTO__custIdfyNo => 복호화 데이터가 custInfoDTO.custIdfyNo에 저장된다.
			String keyName = iter.next();
			String value = decryptInfo.get(keyName);
			
			String[] splitKeyName = keyName.split("__");
			int len_splitKeyName = splitKeyName.length;
			
			// custInfoDTO
			String dtoName = splitKeyName[2];
			// custIdfyNo
			String fieldName = splitKeyName[3];
					
			if (len_splitKeyName >= 4) {
				
				
				/*******************************************************/
				// 예외1 추가. 주민번호/외국인등록번호의 경우 뒤 6자리만 보안키패드 통해 입력된다. 그러나 DTO의 하나의 필드에 합쳐 저장되어야 한다.
				// ex. 최상위 레벨에 __MERG1__custInfoDTO__custIdfyNo (주민번호 앞 6자리 + 성별 1자리) 평문 데이터가 존재
				String merge1Key = keyName.replace("__NPKP__","__MERG1__");
				String merge1Value = (String) jsonObject.get(merge1Key);
				
				if (merge1Value != null && !merge1Value.isEmpty()) {
									
					logger.debug("==================================================================");
					logger.debug("# __MERG1__: " + merge1Value + " + " + value +  " => " + dtoName + "." + fieldName);
					logger.debug("==================================================================");
					value = merge1Value.concat(value);
				}
				/*******************************************************/
				
				/*******************************************************/
				// 예외2 추가. 신용카드 번호의 경우, 특정 DTO가 JSON Array 형태이며 JSON Object로 구성되어 있다 
				// ex. __NPKP__rmnyTrtMethListDTO-rmnyTrtMethDTO__ccardNo__arr__0 (0은 배열의 index) => 복호화 데이터가 rmnyTrtMethListDTO.rmnyTrtMethDTO[0].ccardNo에 저장된다.
				// rmnyTrtMethListDTO_rmnyTrtMethDTO 사이는 "-"이다.
				boolean isArray = false;
				int arrIdx = 0;
				
				if ("arr".equals(splitKeyName[4])) {
					isArray = true;
					try {
						
						arrIdx = Integer.parseInt(splitKeyName[5]);
						
					} catch (Exception e) {
						logger.debug("==================================================================");
						logger.debug("# 보안 키패드 입력 대상 필드 명명 규칙이 비 정상적입니다.!!! => " + keyName);
						logger.debug("==================================================================");
						arrIdx = 0;
					}
				}
				/*******************************************************/
				
				// 보안키패드 복호화 값 저장
				
				if (isArray) { 		// ************ 예외 2번 추가 로직
					
					String[] dtoNames = dtoName.split("-");
					int len_dtoNames = dtoNames.length;
									
					if (len_dtoNames == 2) {
						
						JSONObject dtoJson = (JSONObject)jsonObject.get(dtoNames[0]);
						
						if (dtoJson != null ) {
							
							JSONArray dtoArray = (JSONArray)dtoJson.get(dtoNames[1]);
							
							if (dtoArray != null) {
								
								JSONObject dtoJson2 = (JSONObject)dtoArray.get(arrIdx);
								
								if (dtoJson2 != null) {
									
									dtoJson2.put(fieldName, value);
									
								} else {
									logger.debug("==================================================================");
									logger.debug("# 보안 키패드 입력 대상 JSONArray 내 idx번째 JSONObject DTO가 존재하지 않습니다.!!! => " + keyName);
									logger.debug("==================================================================");
								}			
							} else {
								logger.debug("==================================================================");
								logger.debug("# 보안 키패드 입력 대상 JSONArray DTO가 존재하지 않습니다.!!! => " + keyName);
								logger.debug("==================================================================");
							}					
						} else {
							logger.debug("==================================================================");
							logger.debug("# 보안 키패드 입력 대상 JSONObject DTO가 존재하지 않습니다.!!! => " + keyName);
							logger.debug("==================================================================");
						}	
					} else {
						logger.debug("==================================================================");
						logger.debug("# 보안 키패드 입력 대상 필드(array 신용카드) 명명 규칙이 비 정상적입니다.!!! => " + keyName);
						logger.debug("==================================================================");
					}
					
					
				} else { 			// ************ 기존 기본형 타입 로직 + 예외 1번 로직
										
					JSONObject dtoJson = (JSONObject)jsonObject.get(dtoName);
					
					if (dtoJson != null) {
						
						dtoJson.put(fieldName, value);
						
					} else {
						logger.debug("==================================================================");
						logger.debug("# 보안 키패드 입력 대상 JSONObject DTO가 존재하지 않습니다.!!! => " + keyName);
						logger.debug("==================================================================");
					}	
					
				}
				
			} else {
				// 비 정상 케이스
				logger.debug("==================================================================");
				logger.debug("# 보안 키패드 입력 대상 필드 명명 규칙이 비 정상적입니다.!!! => " + keyName);
				logger.debug("==================================================================");
			}
		}
		
	}
}
