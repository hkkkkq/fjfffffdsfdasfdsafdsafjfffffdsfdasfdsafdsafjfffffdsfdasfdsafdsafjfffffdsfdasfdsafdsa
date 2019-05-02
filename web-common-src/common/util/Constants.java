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
package com.kt.kkos.common.util;

/**
 * @author Administrator
 *
 */
public class Constants
{
	/**
	 * 개통(번호이동), 기변 처리 공통 헤더 Name
	*/
	public static final String TRT_BASE_INFO_DTO = "trtBaseInfoDTO"; // 개통(번호이동), 기변 처리 공통 헤더 Name
	
	/**
	 * 개통(번호이동), 기변 처리 공통 에러 헤더 Name
	*/
	public static final String TRT_ERR_INFO_DTO = "trtErrInfoDTO"; // 개통(번호이동), 기변 처리 공통 에러 헤더 Name
	
	/**
	 * HttpSession Attribute Name : 최종 임시저장된 VO Data
	*/
	public static final String SESSION_ATTNAME_TRT_BASE_INFO_DTO = "trtBaseInfoDTO"; // SESSION 에 trtBaseInfoDTO 별도 관리
	
	/**
	 * HttpSession Attribute Name : 최종 임시저장된 VO Data
	*/
	public static final String SESSION_ATTNAME_VO = "savedVO"; // SESSION_VO_NAME
	
	/**
	 * HttpSession Attribute Name : KTSales Advice Inquiry Key
	*/
	public static final String SESSION_ATTNAME_KTSALES_INQUIRY_KEY = "trtSeq"; // SESSION_KTSALES_INQUIRY_KEY
	
	/**
	 * HttpSession Attribute Name : Private Key
	*/
	public static final String SESSION_ATTNAME_PAIR_PRIVATE_KEY = "privateKey"; // SESSION_KEY_PAIR_PRIVATE
	
	/**
	 * HttpSession Attribute Name : deviceUUID
	*/
	public static final String SESSION_ATTNAME_DEVICE_UUID = "deviceUUID"; // SESSION_DEVICE_UUID
	
	/**
	 * HttpSession Attribute Name : AUTH SSO TOKEN
	*/
	public static final String SESSION_ATTNAME_AUTH_TOKEN = "token"; // SESSION_KEY_AUTH_TOKEN
	
	/**
	 * Http Header User-Agent 추출 Key
	*/
	public static final String USER_AGENT = "User-Agent";
	
	/**
	 * Http Header User-Agent 로 부터 패드개통 앱 접속 여부 판단 내부 값
	*/
	public static final String USER_AGENT_APP_KKOS = "AppLaunch/kkosapp";
	
	/**
	 * Http Header User-Agent 로 부터 추출할 deviceUUID Key
	*/
	public static final String USER_AGENT_DEVICE_UUID = "deviceUUID/";
	
	/**
	 * Http Header User-Agent 로 부터 추출할 SSO token Key
	*/
	public static final String USER_AGENT_TOKEN = "token/";
	
	/**
	 * Http Header User-Agent 로 부터 추출할 etc (TrtBaseInfoDTO)
	*/
	public static final String USER_AGENT_ETC = "etc/";
	
	/**
	 * SSO 토큰 연장 요청 및 유효성 체크 요청 시 sysId 값
	*/
	public static final String PAD_TOKEN_ATHN_SYSID = "KMOS";
	
	/**
	 * contentType : application/json
	*/
	public static final String COTENT_JSON = "application/json";
	
	/**
	 * ENC Field Name postfix Pattern
	 */
	public static final String ENC = "__ENC__";
	
	/**
	 * CHARACTER SET
	 */
	public static final String CHARSET = "UTF-8";

	/**
	 * 공백 문자
	 */
	public static final String EMPTY = "";

	/**
	 * 하이픈
	 */
	public static final String HYPHEN = "-";

	/**
	 * 날짜 포맷 에러
	 */
	public static final String DATE_FORMAT_ERROR = "Date format error : ";

	/**
	 * 미지원 년도 범위
	 */
	public static final String UNSUPPORTED_YEAR_RANGE = "Unsupported year range : ";

	/**
	 * 윤달이 아님
	 */
	public static final String NOT_LEAP_YEAR = "Input date  is not a leap year : ";

	/**
	 * 주민번호 포맷 에러
	 */
	public static final String SSN_FORMAT_ERROR = "Social security number format error : ";

	/**
	 * 숫자 포맷 에러
	 */
	public static final String NUMBER_FORMAT_ERROR = "Number format error : ";

	/**
	 * 파일 클로즈 에러
	 */
	public static final String FILE_CLOSE_ERROR = "Failed to close file : ";

	/**
	 * 파라메타 부족 에러
	 */
	public static final String PARAMETER_INSUFFICIENT_ERROR = "Insufficient parameter : ";

	/**
	 * 금지 경로 에러
	 */
	public static final String PROHIBITED_FILE_PATH_ERROR = "Prohibited file path : ";

	/**
	 * 미필수 필드 포함 에러
	 */
	public static final String ILLEGAL_MAND_FIELD_ERROR = "Some fields are not mandatory fields : ";

	/**
	 * 필드 미포함 에러
	 */
	public static final String EMPTY_MAND_FIELD_ERROR = "Empty mandatory field.";

	/**
	 * 은행 코드 길이
	 */
	public static final int BANK_CD_LEN = 3;

	/**
	 * 은행코드 길이 에러
	 */
	public static final String BANK_CD_LEN_ERROR = "The length of bank code is not sufficient : ";
	/**
	 * 계좌 번호 최소 길이
	 */
	public static final int ACC_MIN_LEN = 8;
	/**
	 * 계좌 번호 최대 길이
	 */
	public static final int ACC_MAX_LEN = 18;
	/**
	 * 카드 번호 제외 길이
	 */
	public static final int CARD_EXCEPT_LEN = 13;
	/**
	 * 카드 번호 최소 길이
	 */
	public static final int CARD_MIN_LEN = 12;
	/**
	 * 카드 번호 최대 길이
	 */
	public static final int CARD_MAX_LEN = 16;
	/**
	 * 금융 번호 최소 길이
	 */
	public static final int FNNC_MIN_LEN = 8;
	/**
	 * 금융 번호 최대 길이
	 */
	public static final int FNNC_MAX_LEN = 19;
	/**
	 * 기타 번호(운전면허 or 여권) 최소 길이
	 */
	public static final int OTHR_MIN_LEN = 8;
	/**
	 * 기타 번호(운전면허 or 여권) 최대 길이
	 */
	public static final int OTHR_MAX_LEN = 19;
	/**
	 * 주민 등록 번호 길이
	 */
	public static final int SOCL_LEN = 13;
	/**
	 * 계좌 번호 길이 에러
	 */
	public static final String ACC_LEN_ERROR = "The length of account number must be between 8 and 18 : ";
	/**
	 * 주민 등록 번호 길이 에러
	 */
	public static final String SOCL_LEN_ERROR = "The length of social security number must be 13 : ";
	/**
	 * 주민 등록 날짜 최소값
	 */
	public static final String SOCL_BIRTH_MIN = "18900000";
	/**
	 * 카드 번호 길이 에러
	 */
	public static final String CARD_LEN_ERROR = "The length of card number must be in (12, 14, 15, 16) : ";
	/**
	 * 금융 번호 길이 에러
	 */
	public static final String FNNC_LEN_ERROR = "The length of financial number must be between 8 and 19 : ";
	/**
	 * 기타 번호(운전면허/여권) 길이 에러
	 */
	public static final String OTHR_LEN_ERROR = "The length of driver license or passort number must be between 4 and 20 : ";
	/**
	 * 카드 유효기간 체크 에러
	 */
	public static final String INVALID_CARD_YEAR_ERROR = "The year of card must be between 2014 and 2099 : ";
	/**
	 * 기타 번호(운전면허/여권) 체크 에러
	 */
	public static final String INVALID_OTHR_ERROR = "The format of other number is invalid : ";
	/**
	 * 주민번호 체크 에러
	 */
	public static final String INVALID_SOCL_ERROR = "The format of social security number is invalid : ";
	/**
	 * 취득 데이터 사이즈 오류
	 */
	public static final String INVALID_RECV_SIZE_ERROR = "The size of received bytes is invalid : ";
	/**
	 * CRIS 연계 최대 건수
	 */
	public static final int MAX_DATA_LIMIT = 300;
	/**
	 * 최대 건수 제한 에러
	 */
	public static final String MAX_DATA_LIMIT_ERROR = "The maximum number of data is 300 : ";

	/**
	 * CommonHeader - 어플리케이션 이름
	 */
	public static final String APP_NAME = "appName";
	/**
	 * CommonHeader - 서비스 이름
	 */
	public static final String SVC_NAME = "svcName";
	/**
	 * CommonHeader - 오퍼레이션 이름
	 */
	public static final String FN_NAME = "fnName";
	/**
	 * CommonHeader - 기능코드
	 */
	public static final String FN_CD = "fnCd";
	/**
	 * CommonHeader - 거래고유번호
	 */
	public static final String GLOBAL_NO = "globalNo";
	/**
	 * CommonHeader - 채널구분
	 */
	public static final String CHNL_TYPE = "chnlType";
	/**
	 * CommonHeader - 환경구분
	 */
	public static final String ENVR_FLAG = "envrFlag";
	/**
	 * CommonHeader - 송수신 FLAG
	 */
	public static final String TR_FLAG = "trFlag";
	/**
	 * CommonHeader - 송수신 일자
	 */
	public static final String TR_DATE = "trDate";
	/**
	 * CommonHeader - 송수신 시간
	 */
	public static final String TR_TIME = "trTime";
	/**
	 * CommonHeader - 클라이언트 IP
	 */
	public static final String CLNT_IP = "clntIp";
	/**
	 * CommonHeader - 응답유형
	 */
	public static final String RESPONSE_TYPE = "responseType";
	/**
	 * CommonHeader - 응답코드
	 */
	public static final String RESPONSE_CODE = "responseCode";
	/**
	 * CommonHeader - 응답구분코드
	 */
	public static final String RESPONSE_LOGCD = "responseLogcd";
	/**
	 * CommonHeader - 응답타이틀
	 */
	public static final String RESPONSE_TITLE = "responseTitle";

	/**
	 * CommonHeader - 응답기본내역
	 */
	public static final String RESPONSE_BASC = "responseBasc";

	/**
	 * CommonHeader - 응답상세내역
	 */
	public static final String RESPONSE_DTAL = "responseDtal";
	/**
	 * CommonHeader - 최초 오류 발생 시스템
	 */
	public static final String RESPONSE_SYSTEM = "responseSystem";
	/**
	 * CommonHeader - 사용자ID
	 */
	public static final String USER_ID = "userId";

	/**
	 * CommonHeader - 실사용자ID
	 */
	public static final String REAL_USER_ID = "realUserId";

	/**
	 * CommonHeader - 필러
	 */
	public static final String FILLER = "filler";
	/**
	 * CommonHeader - 사용자 언어 코드
	 */
	public static final String LANG_CODE = "langCode";
	/**
	 * CommonHeader - 조직ID
	 */
	public static final String ORG_ID = "orgId";
	/**
	 * CommonHeader - Source ID
	 */
	public static final String SRC_ID = "srcId";
	/**
	 * CommonHeader - Current Host ID
	 */
	public static final String CUR_HOST_ID = "curHostId";
	/**
	 * CommonHeader - Logical Date&Time
	 */
	public static final String LG_DATE_TIME = "lgDateTime";
	/**
	 * CommonHeader - Token ID
	 */
	public static final String TOKEN_ID = "tokenId";
	/**
	 * CommonHeader - Company Code
	 */
	public static final String CMPN_CD = "cmpnCd";
	/**
	 * CommonHeader - Lock 유형
	 */
	public static final String LOCK_TYPE = "lockType";
	/**
	 * CommonHeader - Locking ID
	 */
	public static final String LOCK_ID = "lockId";
	/**
	 * CommonHeader - Locking Timestamp
	 */
	public static final String LOCK_TIME_ST = "lockTimeSt";
	/**
	 * CommonHeader - 비즈니스 키
	 */
	public static final String BUSINESS_KEY = "businessKey";
	/**
	 * CommonHeader - 임의 키
	 */
	public static final String ARBITRARY_KEY = "arbitraryKey";
	/**
	 * CommonHeader - 재처리 연동구분
	 */
	public static final String RESEND_FLAG = "resendFlag";
	/**
	 * CommonHeader - Phase Flag
	 */
	public static final String PHASE = "phase";

	/**
	 * BizHeader - Order ID
	 */
	public static final String ORDER_ID = "orderId";
	/**
	 * BizHeader - CB 서비스 이름
	 */
	public static final String CB_SVC_NAME = "cbSvcName";
	/**
	 * BizHeader - CB 오퍼레이션 이름
	 */
	public static final String CB_FN_NAME = "cbFnName";

	/**
	 * 월 최소값
	 */
	public static final int MONTH_ST = 1;
	/**
	 * 월 최대값
	 */
	public static final int MONTH_FNS = 12;
	/**
	 * 일 최소값
	 */
	public static final int DAY_ST = 1;
	/**
	 * 일 최대값
	 */
	public static final int DAY_FNS = 31;
	/**
	 * 년도 길이
	 */
	public static final int YEAR_LEN = 4;
	/**
	 * 년 포함 최소 길이
	 */
	public static final int HAS_YEAR_MIN_LEN = 4;
	/**
	 * 월 포함 최소 길이
	 */
	public static final int HAS_MONTH_MIN_LEN = 6;
	/**
	 * 일 포함 최소 길이
	 */
	public static final int HAS_DAY_MIN_LEN = 8;
	/**
	 * 시간 포함 최소 길이
	 */
	public static final int HAS_HOUR_MIN_LEN = 10;
	/**
	 * 분 포함 최소 길이
	 */
	public static final int HAS_MIN_MIN_LEN = 12;
	/**
	 * 초 포함 최소 길이
	 */
	public static final int HAS_SEC_MIN_LEN = 14;
	/**
	 * 밀리초 포함 최소 길이
	 */
	public static final int HAS_MLS_MIN_LEN = 17;
	/**
	 * 1900년도
	 */
	public static final String YEAR_1900 = "1900";

	/**
	 * CRIS prefix - ISSUE(발급)
	 */
	public static final String CRIS_PREFIX_ISSUE = "ISSUE";
	/**
	 * CRIS prefix - SEL(조회)
	 */
	public static final String CRIS_PREFIX_SEL = "SEL";
	/**
	 * CRIS prefix - AUTH(인증)
	 */
	public static final String CRIS_PREFIX_AUTH = "AUTH";
	/**
	 * CRIS prefix - AUTH_M(마스킹)
	 */
	public static final String CRIS_PREFIX_AUTH_M = "AUTH_M";
	/**
	 * CRIS postfix - ACK(응답)
	 */
	public static final String CRIS_POSTFIX_ACK = "ACK";
	/**
	 * CRIS head result
	 */
	public static final String CRIS_FAIL_MSG = "Response fail message:";
	
	/**
	 * CIRS debug 로그 출력 여부
	 */
	public static final String IS_DEBUG_ENABLED = "isDebugEnabled";

	/**
	 * 시스템 공통 필드 - 시스템 처리자 ID
	 */
	public static final String SYS_TRTR_ID = "sysTrtrId";
	/**
	 * 시스템 공통 필드 - 시스템 처리 조직 ID
	 */
	public static final String SYS_TRT_ORG_ID = "sysTrtOrgId";
	/**
	 * 시스템 공통 필드 - 시스템 서비스 ID
	 */
	public static final String SYS_SVC_ID = "sysSvcId";
	/**
	 * 시스템 공통 필드 - 시스템 컴포넌트 ID
	 */
	public static final String SYS_COMP_ID = "sysCompId";
	/**
	 * 시스템 공통 필드 - 시스템 레코드 생성일시
	 */
	public static final String SYS_RECD_CRET_DT = "sysRecdCretDt";
	/**
	 * 시스템 공통 필드 - 시스템 레코드 변경일시
	 */
	public static final String SYS_RECD_CHG_DT = "sysRecdChgDt";
	
}
