package com.kt.kkos.common.util;


public class ITLErrMesgs {
	public final static String RESPONSE_TYPE_SUCCESS = "N";	// 성공
	public final static String RESPONSE_TYPE_SYSERR = "S";	// 시스템 에러
	public final static String RESPONSE_TYPE_BIZERR = "E";	// 비즈니스 에러
	
	public final static String RESPONSE_TITLE_SUCCESS = "정상";	// 성공
	public final static String RESPONSE_TITLE_SYSERR = "시스템 오류";	// 성공
	public final static String RESPONSE_TITLE_BIZERR = "비즈니스 오류";	// 성공
	
	public final static String RESPONSE_BASC_SUCCESS = "성공";	// 성공
	public final static String RESPONSE_BASC_SYSNERR = "내부 처리 중 기능 오류가 발생하였습니다.";
	public final static String RESPONSE_BASC_BIZERR = "내부 처리 중 비즈니스 오류가 발생하였습니다.";
	
	public final static String RESPONSE_CODE_SUCCESS = "KMOS0000";	// 성공
	public final static String RESPONSE_DTAIL_SUCCESS = "정상 처리 되었습니다.";
	
	public final static String RESPONSE_CODE_MASK = "KMOS0001";		// 마스킹 에러
	public final static String RESPONSE_DTAIL_MASK = "마스킹 처리 중 오류가 발생 하였습니다.";
	
	public final static String RESPONSE_CODE_OBJ = "KMOS0002";		// Object 비교 에러
	public final static String RESPONSE_DTAIL_OBJ = "지원되지 않는 비교 객체 유형입니다.";
	
	public final static String RESPONSE_CODE_FlTHCAIN = "KMOS0003";		// Object 비교 에러
	public final static String RESPONSE_DTAIL_FlTHCAIN = "필터 체인 처리 중 오류가 발생하였습니다.";
	
	public final static String RESPONSE_CODE_JSNPARSE = "KMOS0004";		// JSON Parse 에러
	public final static String RESPONSE_DTAIL_JSNPARSE = "JSON Parsing 처리 중 오류가 발생하였습니다.";
	
	public final static String RESPONSE_CODE_REQWRAP = "KMOS0005";		// JSON Parse 에러
	public final static String RESPONSE_DTAIL_REQWRAP = "Request Wrapping 처리 중 오류가 발생하였습니다.";
	
	public final static String RESPONSE_CODE_REQALLOW = "KMOS0006";		// JSON Parse 에러
	public final static String RESPONSE_DTAIL_REQALLOW = "서비스 가능 여부 처리 중 오류가 발생하였습니다.";
	
	public final static String RESPONSE_CODE_KNOTEOCRLIST = "KMOS0007";		// K-Note OCR List 불러오기 에러
	public final static String RESPONSE_DTAIL_KNOTEOCRLIST = "잠시 후 재시도에서도 동일 오류 발생시 스마트 신청서에서 업무 진행하실 수 있습니다.";
	
	public final static String RESPONSE_CODE_COMPOCR = "KMOS0008";		// 신분증 재 스캔 또는 재 왈영 시 OCR 정보 불일치
	public final static String RESPONSE_DTAIL_COMPOCR = "신분증 정보가 일치하지 않습니다.";
	
	public final static String RESPONSE_CODE_GETAPPDATA = "KMOS9000";		// getAppData 수행 중 발생 에러
	public final static String RESPONSE_DTAIL_GETAPPDATA = "로그인 정보 APP 연동 중 오류가 발생하였습니다.";
	
	public final static String RESPONSE_CODE_COMMONERR = "KMOS9999";		// getAppData 수행 중 발생 에러
	public final static String RESPONSE_DTAIL_COMMONERR= "내부 처리 중 오류가 발생하였습니다.";
}
