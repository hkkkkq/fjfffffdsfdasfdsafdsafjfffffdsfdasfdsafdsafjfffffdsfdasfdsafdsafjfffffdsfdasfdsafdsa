package com.kt.kkos.common.util;

import java.util.HashMap;
import java.util.List;

public class ITLKeyUTIL {
	static public String NSTEP_CHNL_TYPE_CD = "MP";
	static public String NSTEP_APP_NAME_CD = "ESB";
	static public String OSST_CHNL_TYPE_CD = "OT";
	static public String PRDC_PROV_TYPE_CD_K = "K";   
	static public String PRDC_PROV_TYPE_CD_M = "M";   
	
	static public String PRDC_TYPE_CD_PP = "P";        
	static public String PRDC_TYPE_CD_SS = "R"; 
	
	static public String PRDC_SRV_CD_NEW  = "N";
	static public String PRDC_SRV_CD_PREV = "P";
	
	static public String PRDC_STAT_CD_ADD = "A";      
	static public String PRDC_STAT_CD_CAN = "C";      
	static public String PRDC_STAT_CD_NON = "N";      
	static public String PRDC_STAT_CD_UPD = "U";      
	
	static public String PRDC_VRBL_TYPE_CD_NULL_PASS = "02";
	static public String PRDC_VRBL_TYPE_CD_NULL_NOPASS = "01";
	
	static public String PRDC_VRBL_KIND_CD_MVNO_PROV = "04";
	static public String PRDC_VRBL_KIND_CD_MP_PROV = "01";
	
	static public String PRDC_FUTURE_DATE="99991231235959";
	
	static public String UICC_ALWD_LMT_CD_NO  = "1";   
	static public String UICC_ALWD_LMT_CD_YES = "F";  
	static public String UICC_ALWD_LMT_CD_NON = "N";  
	
	static public String ENTR_STAT_CD_ACT = "A";      
	static public String ENTR_STAT_CD_SUS = "S";      
	static public String ENTR_STAT_CD_CAN = "C";      
	
	static public String CUST_TYPE_CD_I = "I";        
	static public String CUST_TYPE_CD_O = "O";        
	static public String CUST_TYPE_CD_B = "B";
	static public String CUST_TYPE_CD_G = "G";
	
	static public String BLPYM_MTHD_CD_GIRO = "R";    
	static public String BLPYM_MTHD_CD_CARD = "C";    
	static public String BLPYM_MTHD_CD_ACCN = "D";    
	
	static public String STAT_CHNG_CD_NAC = "NAC";        
	static public String STAT_CHNG_CD_MCN = "MCN";        
	static public String STAT_CHNG_CD_CAN = "CAN";        
	static public String STAT_CHNG_CD_RCL = "RCL"; 
	static public String STAT_CHNG_CD_CCN = "CCN";        
	static public String STAT_CHNG_CD_RSP = "RSP";        
	static public String STAT_CHNG_CD_HCN = "HCN";
	static public String STAT_CHNG_CD_UCN = "UCN";
	static public String STAT_CHNG_CD_SUS = "SUS";        
	
	static public String ITL_DEFAULT_TIME_OUT="30";   // ITL DEFAULT TIME OUT
	static public long   ITL_TCP_DEFAULT_TIME_OUT = 10;
	static public int    ITL_TCP_CONNECTION_TIME_OUT = 10;
	static public String ITL_TCP_TIMEOUT_ERR_CD = "ITL_SYS_E1007";
	
	
	
	static public String ITL_SC_SVC_ID="ScProvRequestBO";
	static public String ITL_SC_COM_ID="SC";
	
	static public String ITL_MPNG_INFO_CUST = "CI";    // 고객 맵핑정보 구분자
	static public String ITL_MPNG_INFO_BILL = "BI";    // 청구 맵핑정보 구분자
	static public String ITL_MPNG_INFO_CNTR = "SI";    // 계약 맵핑정보 구분자
	static public String ITL_MPNG_INFO_NCN  = "NI";    // NCN 조회 구분자
		
	static public int ITL_NSTEP_CN_LENGTH = 9;     // NSTEP 가입계약 자리수
	
	static public String ITL_RESPONSE_TYPE_SYSERR  = "S"; // SYSTEM ERROR RESPONSE
	static public String ITL_RESPONSE_TYPE_BIZERR  = "E"; // BUSINESS ERROR RESPONSE
	static public String ITL_RESPONSE_TYPE_SUCCESS = "N"; // SUCCESS RESPONSE
	
	static public String ITL_DATE_FORMAT_DATETIME = "yyyyMMddHHmmss";
	static public String ITL_DATE_FORMAT_NRDS_DATETIME = "yyyy-MM-dd HH:mm:ss.S";
	static public String ITL_DATE_FORMAT_DATE = "yyyyMMdd";
	static public String ITL_DATE_FORMAT_TIME = "HHmmss";
	
	static public int    ITL_DATE_FORMAT_NRDS_DATETIME_LENGTH = ITLKeyUTIL.ITL_DATE_FORMAT_NRDS_DATETIME.length();
	
	static public String SC_REQ_MARKET_CODE = "KTF";
	static public String SC_REQ_TRX_SRC = "CSM";
	static public String SC_REQ_USER_ID = "12345";
	static public String SC_REQ_NEWBIZ_TYPE="P";
	static public String SC_REQ_NON_IMEI = "FFFFFFFFFFFFFFFF";

	static public String SC_WORK_JOB_IND_CD_OPEN_SVC_CNTR               = "OPEN_SVC_CNTR"              ;
	static public String SC_WORK_JOB_IND_CD_STOP_SVC_CNTR               = "STOP_SVC_CNTR"              ;
	static public String SC_WORK_JOB_IND_CD_RGST_LOSE_ACPT              = "RGST_LOSE_ACPT"             ;
	static public String SC_WORK_JOB_IND_CD_DLT_EIR_BLACKLIST           = "DLT_EIR_BLACKLIST"          ;
	static public String SC_WORK_JOB_IND_CD_RLS_SMS_BLOCKING            = "RLS_SMS_BLOCKING"           ;
	static public String SC_WORK_JOB_IND_CD_CHANGE_USIM_MOVE_ALWD_LVL   = "CHANGE_USIM_MOVE_ALWD_LVL"  ;
	static public String SC_WORK_JOB_IND_CD_RCVR_STOP_SVC_CNTR          = "RCVR_STOP_SVC_CNTR"         ;
	static public String SC_WORK_JOB_IND_CD_TRMN_SVC_CNTR               = "TRMN_SVC_CNTR"              ;
	static public String SC_WORK_JOB_IND_CD_SEND_OTA                    = "SEND_OTA"                   ;
	static public String SC_WORK_JOB_IND_CD_PBX_RFRS                    = "PBX_RFRS"                   ;
	static public String SC_WORK_JOB_IND_CD_CHNG_NFL                    = "CHNG_NFL"                   ;
	static public String SC_WORK_JOB_IND_CD_RGST_SMS_BLOCKING           = "RGST_SMS_BLOCKING"          ;
	static public String SC_WORK_JOB_IND_CD_RCVR_TRMN_SVC_CNTR          = "RCVR_TRMN_SVC_CNTR"         ;
	static public String SC_WORK_JOB_IND_CD_DLT_EIR_BLACKLIST_AFTR_TRMT = "DLT_EIR_BLACKLIST_AFTR_TRMT";
	static public String SC_WORK_JOB_IND_CD_TRTM_PNA_CARD_SLS           = "TRTM_PNA_CARD_SLS"          ;
	static public String SC_WORK_JOB_IND_CD_MNP_DEL_PIN                 = "MNP_DEL_PIN"                ;
	static public String SC_WORK_JOB_IND_CD_RCV_EIR_HNDSET_INFO         = "RCV_EIR_HNDSET_INFO"        ;
	static public String SC_WORK_JOB_IND_CD_CNCL_LOSE_ACPT              = "CNCL_LOSE_ACPT"             ;
	static public String SC_WORK_JOB_IND_CD_STOP_AIRPORT_RANTAL         = "STOP_AIRPORT_RANTAL"        ;
	static public String SC_WORK_JOB_IND_CD_CHANGE_TLPH_NO              = "CHANGE_TLPH_NO"             ;
	static public String SC_WORK_JOB_IND_CD_TRTM_PRDC_STRG              = "TRTM_PRDC_STRG"             ;
	static public String SC_WORK_JOB_IND_CD_TRTM_OTHR_NET_GDNC_SVC      = "TRTM_OTHR_NET_GDNC_SVC"     ;
	static public String SC_WORK_JOB_IND_CD_RLS_SEND_PRHB               = "RLS_SEND_PRHB"              ;
	static public String SC_WORK_JOB_IND_CD_MNP_RGST_POT                = "MNP_RGST_POT"               ;
	static public String SC_WORK_JOB_IND_CD_ACTV_AIRPORT_RANTAL         = "ACTV_AIRPORT_RANTAL"        ;
	static public String SC_WORK_JOB_IND_CD_CHNG_HNDSET                 = "CHNG_HNDSET"                ;
	static public String SC_WORK_JOB_IND_CD_CHNG_USIM                   = "CHNG_USIM"                  ;
	static public String SC_WORK_JOB_IND_CD_CHANGE_NEO_TRGT_INFO        = "CHANGE_NEO_TRGT_INFO"       ;
	static public String SC_WORK_JOB_IND_CD_RGST_UNTS_SVC               = "RGST_UNTS_SVC"              ;
	static public String SC_WORK_JOB_IND_CD_CHNG_STAT_STOP_SVC_CNTR     = "CHNG_STAT_STOP_SVC_CNTR"    ;
	static public String SC_WORK_JOB_IND_CD_ADD_IMEI_TO_EIR             = "ADD_IMEI_TO_EIR"            ;
	static public String SC_WORK_JOB_IND_CD_DEL_IMEI_IN_EIR             = "DEL_IMEI_IN_EIR"            ;
	
	
	/* SC 연동 코드 SRV_TRX_TP_CD */
	static public String SC_TRX_CD_DAC = "DAC";      // CTN 해지
	static public String SC_TRX_CD_RST = "RST";      // CTN 정지복구
	static public String SC_TRX_CD_EBD = "EBD";      // EIR BLACKLIST 삭제
	static public String SC_TRX_CD_EBT = "EBT";      // 해지 후 EIR BLACKLIST 삭제
	static public String SC_TRX_CD_EHC = "EHC";      // EIR 자동기변 원복
	static public String SC_TRX_CD_SUS = "SUS";      // CTN 정지
	static public String SC_TRX_CD_RSM = "RSM";      // CTN 해지 복구
	static public String SC_TRX_CD_ACT = "ACT";      // CTN 개통
	static public String SC_TRX_CD_CCN = "CCN";      // CTN 번호 변경
	static public String SC_TRX_CD_CCD = "CCD";      // CTN 정보변경
	static public String SC_TRX_CD_CCA = "CCA";      // OTA수신에 의한 발신금지해제
	static public String SC_TRX_CD_SMB = "SMB";      // SMSBLOCKING 등록/해제
	static public String SC_TRX_CD_RTA = "RTA";      // 공항렌탈비활성화
	static public String SC_TRX_CD_RTD = "RTD";      // 공항렌탈활성화
	static public String SC_TRX_CD_MNP = "MNP";      // NDB 등록/해지
	static public String SC_TRX_CD_CAD = "CAD";      // 선불카드 판매/출고/판매취소 확정
	
	static public String SC_WORK_JOB_DTL_CD_SUS_LP08 = "LP08"; // 3G 분실-정지없음(이전단말기)
	static public String SC_WORK_JOB_DTL_CD_SUS_LP11 = "LP11"; // 3G 분실-정지없음:이전단말타인사용
	
	
	static public int SC_PARAM_HEIGHT_LENGTH = 3;
	static public int SC_PARAM_SVC_PWD_LENGTH = 6;
	static public int SC_PARAM_FN_PWD_LENGTH = 6;
	static public int SC_PARAM_LNGG_CHC_LIMIT = 7;
	static public int SC_PARAM_ON_OFF_LIMIT = 1;
	static public int SC_PARAM_BRTH_IND_LIMIT = 1;
	
	/* SC APND_HIST 이력 체크 */
	static public boolean SC_APND_HIST_CHECK = true; 
	
	/* WIFI NEOSS 연동 유형 코드 */
	static public String NEOSS_GEAR_TYPE_CD_NEOSS = "N";
	static public String NEOSS_GEAR_TYPE_CD_MAC	  = "M";
	static public String NEOSS_GEAR_TYPE_CD_BOTH  = "B";
	
	/* WIFI NEOSS 연동 오더 유형 코드 */
	static public String NEOSS_CHANGE_CD_ADD_4201 = "4201"; 	// 신규
	static public String NEOSS_CHANGE_CD_CHG_4202 = "4202";		// 변경
	static public String NEOSS_CHANGE_CD_DEL_4203 = "4203";		// 해지
	static public String NEOSS_CHANGE_CD_SUS_4204 = "4204";		// 일시정지
	static public String NEOSS_CHANGE_CD_RSP_4205 = "4205";		// 일시정지 복구
	static public String NEOSS_CHANGE_CD_RCL_4210 = "4210";		// 해지복구
	
	/* WIFI NEOSS 연동 시설오더 유형 코드 */
	static public String NEOSS_UPDATE_TYPE_CD_A0 = "A0";        // 명의 변경
	static public String NEOSS_UPDATE_TYPE_CD_A1 = "A1";		// 고객 정보 변경
	static public String NEOSS_UPDATE_TYPE_CD_A2 = "A2";		// 전화번호 변경
	static public String NEOSS_UPDATE_TYPE_CD_A3 = "A3";		// USIM 카드 변경
	static public String NEOSS_UPDATE_TYPE_CD_A4 = "A4";		// 지능망 코드 변경
	static public String NEOSS_UPDATE_TYPE_CD_A5 = "A5";		// 네스팟 여부 변경
	static public String NEOSS_UPDATE_TYPE_CD_A6 = "A6";		// 와이브로 여부 변경
	static public String NEOSS_UPDATE_TYPE_CD_A7 = "A7";		// ISIWLAN 여부 변경
	static public String NEOSS_UPDATE_TYPE_CD_A8 = "A8";		// 3G 정액 유형 변경
	static public String NEOSS_UPDATE_TYPE_CD_B0 = "B0";		// 단말 해지
	static public String NEOSS_UPDATE_TYPE_CD_B1 = "B1";		// 단말 신규
	static public String NEOSS_UPDATE_TYPE_CD_B2 = "B2";		// 단말 변경
	static public String NEOSS_UPDATE_TYPE_CD_C0 = "C0";		// 부가서비스 해지
	static public String NEOSS_UPDATE_TYPE_CD_C1 = "C1";		// 부가서비스 가입
	static public String NEOSS_UPDATE_TYPE_CD_3C = "3C";		// 일반정지
	static public String NEOSS_UPDATE_TYPE_CD_4C = "4C";		// 일반부활
	static public String NEOSS_UPDATE_TYPE_CD_FF = "FF";		// 정합
	
	static public String NEOSS_PRDC_UPDATE_TYPE_STR = "C0C1";   // 상품관련 UPDATE_TYPE STRING
	static public String NEOSS_INTM_UPDATE_TYPE_STR = "B0B1B2";	// 단말관련 UPDATE_TYPE STRING
	
	
	static public String NEOSS_INTM_TERM_TYPE_CD_97 = "97";		// 3G only or 기타단말
	static public String NEOSS_INTM_TERM_TYPE_CD_98 = "98";		// 3G + WIBRO 단말
	static public String NEOSS_INTM_TERM_TYPE_CD_99 = "99";		// 3G + WIFI + WIBRO 단말
	
	/* WIFI NEOSS 자료 유형 */
	static public String NEOSS_SEND_INFO_CNTR = "C";			// 고객&서비스 정보
	static public String NEOSS_SEND_INFO_INTM = "I";			// 단말정보
	static public String NEOSS_SEND_INFO_PRDC = "P";			// 상품정보
	
	/* 연동 기관 */
	static public String NEOSS_SEND = "NEOSS";					// NEOSS USIM 인증
	static public String NEOSS_MAC  = "MAC";					// ICIS MAC 인증
	
	/* NEOSS 연동 일련번호(ORD_NO) 조회 FLAG */
	static public String NEOSS_ITEM_SEARCH_TYPE_NSTEP = "N";	// NSTEP에서  항목 조회
	static public String NEOSS_ITEM_SEARCH_TYPE_MP    = "M";	// M-Platform에서 항목 조회
	static public String NEOSS_ITEM_SEARCH_TYPE_MVNO  = "V";	// MVNO에서 보낸 input에서 항목 조회
		
	static public String NEOSS_SEND_NULL_OR_SPACE = null;		// NEOSS 연동에 쓰이는 빈값
	
	static public String NEOSS_SEND_CNTR_SA_CD = "0WZZ";		// 서비스 계약 코드
	static public String NEOSS_SEND_CNTR_SA_DTL_CD = "0WZZ";	// 서비스 계약상세  코드
	static public String NEOSS_SEND_USIM_VENDOR_NAME = "86036@(주)엔투비 220-81-96244(월합)";		// NEOSS 계약정보 제조사 명
	static public String NEOSS_SEND_CNTR_LOCK_FLAG = "U";		// NEOSS 계약정보 LOCK_FLAG
	static public String NEOSS_SEND_CNTR_ISWISE = "Y";			// NEOSS 계약정보 ISWISE
	
	static public String NEOSS_SEND_PRDC_STND_GRP_CD_ISIWLAN = "NEOSS_PRDC_IWLAN_INTM_GRP"; // NEOSS IWLAN 단말 그룹코드
	static public String NEOSS_SEND_PRDC_STND_GRP_CD_PRD3GYN = "NEOSS_PRDC_IWLAN_REL_GRP";	// NEOSS PRD3G_YN(3g정액상품) 그룹코드
	static public String NEOSS_SEND_PRDC_STND_GRP_CD_MACGEAR = "ICIS_MAC_SEND_INTM_GRP";	// NEOSS MAC 연동 단말 코드
	
	static public String NEOSS_INTM_TERM_PURPOSE_CD = "01";		// NEOSS 단말 용도 코드(01 판매용)
	static public String NEOSS_SEND_INTM_VENDOR_NAME = "84373@삼성전자 124-81-00998"; 		// NEOSS 단말 제조사 명
	
	/* MAC 연동 업무구분 정의 */
	static public String NEOSS_MAC_WRKN_CD_ACT          = "2100";		// NEOSS MAC 연동 신규
	static public String NEOSS_MAC_WRKN_CD_CAN			= "3002";		// NEOSS MAC 연동 해지
	
	/* MAC 연동 규격에는 없으나 프로세스상 필요로 인한 정의 */
	static public String NEOSS_MAC_WRKN_CD_CAN_AND_ACT_MCN	= "MCN";	// NEOSS MAC 연동 해지 후 신규
	static public String NEOSS_MAC_WRKN_CD_CAN_AND_ACT_HCN	= "HCN";	// NEOSS MAC 연동 해지 후 신규
	
	
	/* MAC TCP 전문 항목별 길이 정의 */
	static public int NEOSS_MAC_MOT_SIZE_LEN 			= 4;
	static public int NEOSS_MAC_MOT_SEND_SYSTEM_LEN 	= 1;
	static public int NEOSS_MAC_MOT_SEND_DATE_LEN 		= 8;
	static public int NEOSS_MAC_MOT_SEND_TIME_LEN 		= 6;
	static public int NEOSS_MAC_MOT_SRL_NO_LEN			= 6;
	static public int NEOSS_MAC_WRKN_CD_LEN 			= 4;
	static public int NEOSS_MAC_RESPONSE_CODE_LEN		= 6;
	static public int NEOSS_MAC_FILLER_LEN				= 15;
	static public int NEOSS_MAC_NSTEP_CUST_ID_LEN		= 9;
	static public int NEOSS_MAC_WIFI_SA_ID_LEN			= 11;
	static public int NEOSS_MAC_WIFI_ID_LEN				= 20;
	static public int NEOSS_MAC_SVC_NO_LEN				= 12;
	static public int NEOSS_MAC_INTM_TYPE_CD_LEN		= 4;
	static public int NEOSS_MAC_SMART_PHONE_CD_LEN		= 4;
	static public int NEOSS_MAC_INTM_MDL_CD_LEN			= 20;
	static public int NEOSS_MAC_INTM_MDL_NAME_LEN		= 20;
	static public int NEOSS_MAC_INTM_MAC_ID_LEN			= 12;
	static public int NEOSS_MAC_INTM_SRL_NO_LEN			= 15;
	static public int NEOSS_MAC_RSLT_CD_LEN				= 1;
	static public int NEOSS_MAC_RSLT_DTL_CD_LEN			= 2;
	static public int NEOSS_MAC_ERR_MSG_LEN				= 100;
	
	static public String NEOSS_MAC_MOT_SEND_SYSTEM_NSTEP = "N";
	static public String NEOSS_MAC_MOT_SEND_SYSTEM_ICIS = "I";
	
	static public String NEOSS_MAC_RSLT_CD_SUCCESS 		= "Y";
	static public String NEOSS_MAC_RSLT_CD_FALSE		= "N";
	
	static public char NEOSS_MAC_MOT_SPACE = ' ';
	
	static public String NEOSS_MAC_ROLLBACK_FLAG = "Y"; 	// ICIS MAC 연동 ROLLBACK 플래그
	
	static public String NEOSS_MAC_RSLT_DTL_CD_NOGEAR = "ZZ";	// ICIS MAC 연동 안함
	static public String NEOSS_MAC_ERR_MSG_NOGEAR = "MAC 연동 없음.";
	static public String NEOSS_MAC_RSLT_DTL_CD_NONEOSS="NN"; // NEOSS 연동 안함
	static public String NEOSS_MAC_ERR_MSG_NONEOSS="NEOSS 연동 없음.";
	

	static public String NEOSS_CNTR_CUST_ID = "MVNO_NEOSS_CUST_ID";
	static public String NEOSS_CNTR_CUST_NO_TYPE = "MVNO_NEOSS_CNTR_CUST_NO_TYPE";
	static public String NEOSS_CNTR_CUST_NO = "MVNO_NEOSS_CNTR_CUST_NO";
	static public String NEOSS_CNTR_CUST_NAME = "MVNO_NEOSS_CNTR_CUST_NAME";
	static public String NEOSS_CNTR_CNTC_TEL_NO = "MVNO_NEOSS_CNTR_CNTC_TEL_NO"; // 가입자 연락처
	static public String NEOSS_CNTR_EMAIL_ADDR = "MVNO_NEOSS_CNTR_EMAIL_ADDR";
	static public String NEOSS_CNTR_ADDR_ZIP_CD = "MVNO_NEOSS_CNTR_ADDR_ZIP_CD";
	static public String NEOSS_CNTR_ADDR_REF = "MVNO_NEOSS_CNTR_ADDR_REF";
	
	static public String ICIS_NSTEP_CUST_ID = "MVNO_ICIS_NSTEP_CUST_ID";
	
	static public String NEOSS_CNTR_CNTC_TEL_NO1 = "";
	static public String NEOSS_CNTR_CNTC_TEL_NO2 = "";
	
	static public String ITL_UMC_OTA_REQEUST_APLY_WRKJOB_CD = "UREQ";
	static public String ITL_UMC_OTA_REPORT_APLY_WRKJOB_CD = "URPT";
	
	static public String ITL_TLPH_NO_OWN_CD_KTF = "KTF";
	
	static public String ITL_NEOSS_SOCKET_DECODE_CHARSET = "KSC5601";
	
	static public String ITL_MP_STRING_TO_BYTE_CHARSET = "KSC5601";
	
	static public String[] GCS_PRDC_LIST={"PMNEWGCS", "NEWGCS", "PLHGCS", "PLUGCS"};
	
	/* IMS IDMS 연동 처리*/
	static public String ITL_IMS_IDMS_TRTM_TYPE_CD_ADD = "4201";
	static public String ITL_IMS_IDMS_TRTM_TYPE_CD_UPD = "4202";
	static public String ITL_IMS_IDMS_TRTM_TYPE_CD_DEL = "4203";
	static public String ITL_IMS_IDMS_TRTM_TYPE_CD_RCL = "4273";
	static public String ITL_IMS_IDMS_BASE_INFO = "B";
	static public String ITL_IMS_IDMS_PRDC_INFO = "P";
	static public String ITL_IMS_IDMS_INTM_INFO = "I";
	static public String ITL_IMS_IDMS_ENCRYPT_KEY;
	/*static 
	{
	    //= "FB27A086FB5864DB228DA893015D5D66";
	    List<HashMap<String, Object>> rs = new ITLCommonUTIL().inqrMvnoGnrlCd("ENCRYPT_KEY_CD", "IMS");
	    if(rs != null && rs.size() > 0)
	    {
	        ITL_IMS_IDMS_ENCRYPT_KEY = (String)rs.get(0).get("GNRL_CD_DSCR_SBST");
	    }
	}*/
	
	
	
	static public String ITL_IMS_IDMS_DOMAIN_IMS = "ims.mnc008.mcc450.3gppnetwork.org";
	static public int    ITL_IMS_IDMS_MSISDN_LENGTH = 30;
	static public int    ITL_IMS_IDMS_DOMAIN_LENGTH = 72;
	static public int    ITL_IMS_IDMS_PRID_LENGTH = 72;
	static public int    ITL_IMS_IDMS_PUID_LENGTH = 72;
	static public String ITL_IMS_IDMS_ISIM_SPEC_CD = "077";  
	static public String ITL_IMS_IDMS_USIM_PRTBL_TERML_SPEC_CD = "089"; 
	
	
	/* MP현행화 정보 조회 SEARCH_IND_CD */
	static public String MP_CNTR_INFO_SRCH_BY_CNTR_NO      = "1";
	static public String MP_CNTR_INFO_SRCH_BY_SVC_CNTR_NO  = "2";
	static public String MP_CNTR_INFO_SRCH_BY_TLPH_NO      = "3";
	static public String MP_CNTR_INFO_SRCH_BY_IMSI         = "4";
	static public String MP_CNTR_INFO_SRCH_BY_IMEI         = "5";
	
	/* CRIS 가상번호 발급 및 조회 */
	static public String CRIS_ATHN_VRTL_SSN = "300";
	static public String CRIS_ISSUE_VRTL_SSN = "400";
	static public String CRIS_REQ_VRTL_SSN = "500";
	static public String CRIS_ATHN_VRTL_PASSPORT = "360";
	static public String CRIS_ISSUE_VRTL_PASSPORT = "440";
	static public String CRIS_REQ_VRTL_PASSPORT = "560";
	static public String CRIS_ATHN_VRTL_CARD_NO = "370";
	static public String CRIS_ISSUE_VRTL_CARD_NO = "470";
	static public String CRIS_REQ_VRTL_CARD_NO = "570";
	
	static public String CRIS_ADD_DATA_PASSPORT = "P";
	static public String CRIS_ADD_DATA_DRIVR_LICNS_NO = "D";
	
	/* TCP 전문 MASK 방법 */
	static public String TCP_GEAR_SBST_MASK_BY_KEY = "K"; // key값으로 마스킹
	static public String TCP_GEAR_SBST_MASK_BY_COORD = "C"; // 좌표값으로 마스킹
	
	static public String CRIS_VRTL_NO_ISSUE_CD = "ISSUE";
	static public String CRIS_REAL_NO_ATHN_CD = "ATHN";
	
	static public String ITL_SC_AUC_AES_CBC_ENCR_KEY;
	/*static 
    {
        //= "D6ECFB18D04EF833AFFF8F3E465905D1";
        List<HashMap<String, Object>> rs = new ITLCommonUTIL().inqrMvnoGnrlCd("ENCRYPT_KEY_CD", "AUC");
        if(rs != null && rs.size() > 0)
        {
            ITL_SC_AUC_AES_CBC_ENCR_KEY = (String)rs.get(0).get("GNRL_CD_DSCR_SBST");
        }
    }*/
	
	
	static public String OMD_WRKJOB_IND_CD_UPDATE = "U";
	static public String OMD_WRKJOB_IND_CD_ADD = "A";
	
	static public String MOVE_TLCM_IND_CD_SKT = "S";
	static public String MOVE_TLCM_IND_CD_LGT = "L";
	static public String MOVE_TLCM_IND_CD_TOT = "O";
	
	static public String ITL_CUST_IDNT_NO_IND_CD_SSN = "01";
	static public String ITL_CUST_IDNT_NO_IND_CD_TAX_NO = "02";
	static public String ITL_CUST_IDNT_NO_IND_CD_CORP_NO = "03";
	static public String ITL_CUST_IDNT_NO_IND_CD_PASSPORT_NO = "04";
	static public String ITL_CUST_IDNT_NO_IND_CD_FRG_NO = "05";
	static public String ITL_CUST_IDNT_NO_IND_CD_DOD_NO = "06";
	static public String ITL_CUST_IDNT_NO_IND_CD_DRVR_NO = "07";
	static public String ITL_CUST_IDNT_NO_IND_CD_ETC = "99";
}
