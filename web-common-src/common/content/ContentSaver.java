/****************************************************************************************************
 * 
 * Copyright ⓒ 2017 kt corp. All rights reserved.
 *
 * This is a proprietary software of kt corp, and you may not use this file except in 
 * compliance with license agreement with kt corp. Any redistribution or use of this 
 * software, with or without modification shall be strictly prohibited without prior written 
 * approval of kt corp, and the copyright notice above does not evidence any actual or 
 * intended publication of such software. 
 * 
 * @Desction Vo 객체 임시 저장 처리 Service
 *
 *****************************************************************************************************
 * date        Modifier Description
 *****************************************************************************************************
 * 2017.09.20. 장종호
 * 
 *****************************************************************************************************/
package com.kt.kkos.common.content;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.gson.GsonBuilder;
import com.kt.kkos.common.util.ITLKeyUTIL;
import com.kt.kkos.exception.ITLBusinessException;

@Service
public class ContentSaver {

	private static final Logger logger = LoggerFactory.getLogger(ContentSaver.class);
	
	/*
	 * 임시 저장 여부
	*/
	@Value("${saveTemp:false}")
	private boolean saveTemp;
	
	
	/**
	 * 수동 임시 저장 메인
	 * @param Object
	 * @throws ITLBusinessException 
	 */
	public boolean manualSaveVO(Object object) throws ITLBusinessException
	{
		logger.debug("[manualSaveVO] ");
		
		JSONParser jsonParser = null;
		JSONObject jsonObject = null;
		
		try {
			jsonParser = new JSONParser();
			jsonObject = (JSONObject)jsonParser.parse(new GsonBuilder().serializeNulls().create().toJson(object));
			logger.debug("jsnObj.toString() : " + jsonObject.toString());
			
			// 임시 저장
			saveTemp = true;
			return saveVO(jsonObject);
			
		}
		catch (Exception e) {
			throw new ITLBusinessException(e);
		}
	}
	
	
	
	
	/**
	 * 자동 임시 저장 메인 (Filter 에서 call)
	 * @param JSONObject
	 * @throws ITLBusinessException 
	 */
	public boolean saveVO(JSONObject jsonObject) throws ITLBusinessException
	{
		logger.debug("[saveVO] JSONObject Data ...\n" + jsonObject);
		
		/**
		 * 수신 파라미터가 Null 또는 trtBaseInfoDTO 체크
		 */
		if ((jsonObject == null) || (jsonObject.get("trtBaseInfoDTO") == null)) {
			return false;
//			throw new ITLBusinessException(ITLKeyUTIL.ITL_RESPONSE_TYPE_SYSERR,
//					"KKOS0001", "시스템 오류", "시스템 오류. 잠시후 처리 하세요.",
//					"필수값이 존재하지 않습니다.");
		}

		// 화면 ID 또는 화면 Step ID 가 없으면, 저장 대상에서 제외
		if ( ((JSONObject)jsonObject.get("trtBaseInfoDTO")).get("itgOderTypeCd") == null ||
		     ((JSONObject)jsonObject.get("trtBaseInfoDTO")).get("srcId") == null ||
		     ((JSONObject)jsonObject.get("trtBaseInfoDTO")).get("stepId") == null ) {
			return false;
		}

		String itgOderTypeCd = ((JSONObject)jsonObject.get("trtBaseInfoDTO")).get("itgOderTypeCd").toString();
		logger.debug("saveVO() ... itgOderTypeCd : " + itgOderTypeCd);
		
		/**
		 * 통합오더유형코드(NAC : 신규개통, CAN : 개통취소, HCN : 기기변경, HCC : 기기변경취소, MNP : 번호이동)
		 */
		//신규, 번호이동 같이 처리
		if ( itgOderTypeCd.indexOf("NAC") > -1 || itgOderTypeCd.indexOf("MNP") > -1 ) {

			return saveVoNAC(jsonObject);
		}
//		else if ( itgOderTypeCd.indexOf("CAN") > -1 ) {
//			
//			saveVoCAN((TrmnTrtInfoVO)inVO);
//		}
		else if ( itgOderTypeCd.indexOf("HCN") > -1 ) {
			
			return saveVoHCN(jsonObject);
		}
//		else if ( itgOderTypeCd.indexOf("HCC") > -1 ) {
//			
//			saveVoHCC((IcgCnclTrtInVO)inVO);
//		}
//		else if ( itgOderTypeCd.indexOf("TST") > -1 ) { //테스트
//			
//			saveVoTst((KmosStdTestVO)(inVO));
//		}
		else {
			throw new ITLBusinessException(ITLKeyUTIL.ITL_RESPONSE_TYPE_SYSERR, "KMOS0001", "존재하지 않는 화면 아이디", "시스템 오류. 잠시후 처리 하세요.", "존재하지 않는 화면 아이디입니다.");
		}

	}

		
	/**
	 * 신규개통, 번호이동 관련 임시저장 처리
	 * @param JSONObject
	 * @throws ITLBusinessException 
	 */
	public boolean saveVoNAC(JSONObject jsonObject) throws ITLBusinessException
	{
		String srcId = ((JSONObject)jsonObject.get("trtBaseInfoDTO")).get("srcId").toString();
		String stepId = ((JSONObject)jsonObject.get("trtBaseInfoDTO")).get("stepId").toString();
		
		/**
		 * JSON Key(DTO 와 매칭) 목록 조회 및 임시 저장 수행
		 */
		// 공통 Key 추출 (Primary Key)
//		String odrSeqNo = ((JSONObject) jsonObject.get("trtBaseInfoDTO")).get(
//				"odrSeqNo").toString();
//		if ((odrSeqNo == null) || (odrSeqNo.trim().length() < 1)) {
//			odrSeqNo = getOdrSeqNo();
//		}
		
		/*
		 * 화면 단위 관련 JSONObject 만 저장
		 */
		logger.debug("saveVO() ... srcId : " + srcId + ", stepId : " + stepId);
		switch (srcId) {
		case "TEST" :  // 테스트용
			return true;
		case "NNSB0200": //실명인증 (개통)
			return true;
		case "NNSB0300": //청구생성
			return true;
		case "NNSB0400": //단말기정보
			return true;
		case "NNSB0500": //가입정보입력
			return true;
		case "NNSB0700" : //신규개통처리
			return true;
		case "NNPB0200": //번호인증
			return true;
		default:
			return false;
		}
	}
	
	
	
	/**
	 * 기기변경 관련 임시저장 처리
	 * @param JSONObject
	 * @throws ITLBusinessException 
	 */
	private boolean saveVoHCN(JSONObject jsonObject) throws ITLBusinessException {
		String srcId = ((JSONObject)jsonObject.get("trtBaseInfoDTO")).get("srcId").toString();
		
		switch (srcId) {
		case "NICG0200": //가입정보조회
			return true;
			
		case "NICG0400": //단말기정보
			return true;
			
		case "NICG0500": //가입정보입력
			return true;
			
		default:
			return false;
//			throw new ITLBusinessException(ITLKeyUTIL.ITL_RESPONSE_TYPE_SYSERR, "KMOS0001", "존재하지 않는 화면 아이디", "시스템 오류. 잠시후 처리 하세요.", "존재하지 않는 화면 아이디입니다.");
		}
		
	}
}

