/****************************************************************************************************
 * NBSS (New Business Support System) version 1.0
 *
 * Copyright ⓒ 2016 kt corp. All rights reserved.
 *
 * This is a proprietary software of kt corp, and you may not use this file except in
 * compliance with license agreement with kt corp. Any redistribution or use of this
 * software, with or without modification shall be strictly prohibited without prior written
 * approval of kt corp, and the copyright notice above does not evidence any actual or
 * intended publication of such software.
 *************************************************************************************************
/**
 * <pre>
 * MPOnline Code Key UTIL
 * - KOS 추가 Key
 * </pre>
 * 
 * @path  com.kt.kkos.itl.bo.common
 * @file ITLSyncRater.java
 * @author 91096677
 * @authorName 김장현
 * @date 2016.05.23
 * @lastAuthor 91096677
 * @lastAuthorName 김장현
 * @lastAuthorDate 2016.05.23
 * @version 1.0
 * 
 * @history
 * 1 / 91096677 / 2016.05.23 / init
 */
package com.kt.kkos.common.util;


public class ITLKosKeyUTIL {

	// =============LE 추가 Start ===================

	/*
	 * 차세대 표준전문 변경 관련 COMMON_HEADER 변수 ※NBSS_2단계_SA_표준전문_0.64.xlsx 참고
	 */
	final static public String KOS_COMMON_HEADER_NAME = "CommonHeader"; // CommonHeader
																		// String
	final static public String KOS_BIZ_HEADER_NAME = "BizHeader"; // BizHeader
																	// String

	/*
	 * SC 프로비저닝 연동규격 작업코드 추가
	 */
	final static public String ITL_SC_WORK_JOB_CD_NAC = "NAC"; // 개통
	final static public String ITL_SC_WORK_JOB_CD_MNP = "MNP"; // MNP HLR 번호등록
	final static public String ITL_SC_WORK_JOB_CD_MCN = "MCN"; // 명의변경
	final static public String ITL_SC_WORK_JOB_CD_CCN = "CCN"; // 번호변경
	final static public String ITL_SC_WORK_JOB_CD_CAN = "CAN"; // 해지
	final static public String ITL_SC_WORK_JOB_CD_RCL = "RCL"; // 해지복구
	final static public String ITL_SC_WORK_JOB_CD_CCA = "CCA"; // OTA수신에의한발신금지해제
	final static public String ITL_SC_WORK_JOB_CD_OTA = "OTA"; // OTA발송
	final static public String ITL_SC_WORK_JOB_CD_EBD = "EBD"; // EIRBLACKLIST삭제처리
	final static public String ITL_SC_WORK_JOB_CD_SMB = "SMB"; // SMSBLOCKING등록/삭제
	final static public String ITL_SC_WORK_JOB_CD_CUA = "CUA"; // USIM이동성허용범위변경
	final static public String ITL_SC_WORK_JOB_CD_PCN = "PCN"; // 상품변경 처리
	final static public String ITL_SC_WORK_JOB_CD_GDS = "GDS"; // 타망번호변경안내서비스처리
	final static public String ITL_SC_WORK_JOB_CD_RTD = "RTD"; // 공항렌탈활성화
	final static public String ITL_SC_WORK_JOB_CD_RTA = "RTA"; // 공항렌탈비활성화
	final static public String ITL_SC_WORK_JOB_CD_HCN = "HCN"; // 기기변경
	final static public String ITL_SC_WORK_JOB_CD_HCC = "HCC"; // 기기변경취소
	final static public String ITL_SC_WORK_JOB_CD_UCN = "UCN"; // USIM변경
	final static public String ITL_SC_WORK_JOB_CD_EHC = "EHC"; // EIR자동기변원복
	final static public String ITL_SC_WORK_JOB_CD_SUS = "SUS"; // 일시정지
	final static public String ITL_SC_WORK_JOB_CD_RST = "RST"; // 일시정지복구

	/*
	 * SC 프로비저닝 연동규격 정지 상세 구분 코드
	 */
	final static public String ITL_SC_WORK_SUS_JOB_DTL_CD_AR06 = "AR06"; // 군대입대자장기정지(발착신)
	final static public String ITL_SC_WORK_SUS_JOB_DTL_CD_CR01 = "CR01"; // 고객요청(발신)
	final static public String ITL_SC_WORK_SUS_JOB_DTL_CD_CR04 = "CR04"; // 고객요청(발착신)
	final static public String ITL_SC_WORK_SUS_JOB_DTL_CD_LP01 = "LP01"; // 분실일시정지(발신)
	final static public String ITL_SC_WORK_SUS_JOB_DTL_CD_LP02 = "LP02"; // 분실일시정지(발착신)
	final static public String ITL_SC_WORK_SUS_JOB_DTL_CD_LP04 = "LP04"; // 분실(단말기+USIM)(발신)
	final static public String ITL_SC_WORK_SUS_JOB_DTL_CD_LP05 = "LP05"; // 분실(단말기+USIM)(발착신)
	final static public String ITL_SC_WORK_SUS_JOB_DTL_CD_LP07 = "LP07"; // 3G
																			// 분실-(USIM)
	final static public String ITL_SC_WORK_SUS_JOB_DTL_CD_LP06 = "LP06"; // 3G
																			// 분실-발신정지(USIM)
	final static public String ITL_SC_WORK_SUS_JOB_DTL_CD_LP10 = "LP10"; // 3G
																			// 분실-발착신정지(USIM)
	final static public String ITL_SC_WORK_SUS_JOB_DTL_CD_LP08 = "LP08"; // 3G
																			// 분실-정지없음(이전단말기)
	final static public String ITL_SC_WORK_SUS_JOB_DTL_CD_LP09 = "LP09"; // 3G
																			// 분실-정지없음(현재단말기)
	final static public String ITL_SC_WORK_SUS_JOB_DTL_CD_LP11 = "LP11"; // 3G
																			// 분실-정지없음:이전단말타인사용

	/*
	 * K-Rater 연동 업무 코드
	 */
	final static public String KOS_RATER_API_DIV_C01 = "C01"; // 신규고객생성
	final static public String KOS_RATER_API_DIV_S01 = "S01"; // 서비스 계약 생성
	final static public String KOS_RATER_API_DIV_S02 = "S02"; // 서비스 계약 정지, 정지복구
	final static public String KOS_RATER_API_DIV_S03 = "S03"; // 서비스 계약 해지
	final static public String KOS_RATER_API_DIV_S04 = "S04"; // 서비스 계약 해지복구
	final static public String KOS_RATER_API_DIV_S05 = "S05"; // 상품 변경
	final static public String KOS_RATER_API_DIV_S06 = "S06"; // 부가 서비스 가입
	final static public String KOS_RATER_API_DIV_S07 = "S07"; // 부가 서비스 해지
	final static public String KOS_RATER_API_DIV_S08 = "S08"; // 상품 파라메터
																// 변경(추가,부가)
	final static public String KOS_RATER_API_DIV_S09 = "S09"; // USIM변경, 번호변경
	final static public String KOS_RATER_API_DIV_S10 = "S10"; // 부가서비스 유효시작일 변경
	final static public String KOS_RATER_API_DIV_S11 = "S11"; // 부가서비스 유효종료일 변경
	final static public String KOS_RATER_API_DIV_S12 = "S12"; // 명의변경(잔액승계대상)

	/*
	 * K-Rater 연동 서비스 actRsn Type
	 */
	final static public String KOS_RATER_ACTRSN_MO = "MO"; // 발신정지
	final static public String KOS_RATER_ACTRSN_MT = "MT"; // 착신정지
	final static public String KOS_RATER_ACTRSN_BOTH = "BOTH"; // 발착신정지
	final static public String KOS_RATER_ACTRSN_REST = "REST"; // 정지복구
	final static public String KOS_RATER_ACTRSN_CREQ = "CREQ"; // CREQ

	/*
	 * K-Rater 연동 서비스 발착신 정지 상세코드
	 */
	final static public String KOS_RATER_SUS_WORK_BASE_INFO_STAT_01 = "01"; // 발신정지
	final static public String KOS_RATER_SUS_WORK_BASE_INFO_STAT_02 = "02"; // 착신정지
	final static public String KOS_RATER_SUS_WORK_BASE_INFO_STAT_03 = "03"; // 발착신정지
	final static public String KOS_RATER_SUS_WORK_BASE_INFO_STAT_99 = "99"; // 정지없음

	/*
	 * K-Rater 연동 고정 값
	 */
	final static public String KOS_RATER_FIXED_VALUE_SUBSCRIBERTYPE = "MOS"; // 서비스계약생성
																				// subscriberType
																				// 고정값
	final static public String KOS_RATER_FIXED_VALUE_OFFER_ID = "1"; // 상품 일련번호
																		// 고정값

	/*
	 * K-Rater 연동 Parameter 고정 값
	 */
	final static public String KOS_RATER_PARAMETER_NAME_MSISDN = "MSISDN"; // MSISDN
	final static public String KOS_RATER_PARAMETER_NAME_IMSI = "IMSI"; // IMSI
	final static public String KOS_RATER_PARAMETER_NAME_COMPANY_CODE = "COMPANY_CODE"; // COMPANY_CODE

	/*
	 * 차세대 표준전문 관련 COMMON_HEADER_APP_NAME 설명 : ESB,UI (Dynamic Routing 구분)
	 * ProObject( 프로젝트명) Infinilink(사용안함) ※NBSS_2단계_SA_표준전문_0.64.xlsx 참고
	 */
	// AUTH
	final static public String KOS_COMMON_HEADER_APP_NAME_NBSS_ATA = "NBSS_ATA";
	// LE_유통-NRTL
	final static public String KOS_COMMON_HEADER_APP_NAME_NBSS_RDS = "NBSS_RDS";
	// LE_물류
	final static public String KOS_COMMON_HEADER_APP_NAME_NBSS_PHY = "NBSS_PHY";
	// LE_수수료
	final static public String KOS_COMMON_HEADER_APP_NAME_NBSS_FEE = "NBSS_FEE";
	// LE_유통(조직?)
	final static public String KOS_COMMON_HEADER_APP_NAME_NBSS_CIT = "NBSS_CIT";
	// 차세대 OM
	final static public String KOS_COMMON_HEADER_APP_NAME_NBSS_OM = "NBSS_ORD";
	// 차세대 OM
	final static public String KOS_COMMON_HEADER_APP_NAME_NBSS_OCT = "NBSS_OCT";
	// 차세대 OM
	final static public String KOS_COMMON_HEADER_APP_NAME_NBSS_CMB = "NBSS_CMB";
	// 차세대 OM
	final static public String KOS_COMMON_HEADER_APP_NAME_NBSS_OIT = "NBSS_OIT";
	// 차세대 OM (상품PROD)
	final static public String KOS_COMMON_HEADER_APP_NAME_NBSS_OPD = "NBSS_OPD";
	// 차세대_CDM
	final static public String KOS_COMMON_HEADER_APP_NAME_NBSS_CDM = "NBSS_CDM";
	// 차세대_CDM-PO 고객
	final static public String KOS_COMMON_HEADER_APP_NAME_NBSS_CST = "NBSS_CST";
	// 차세대_CDM-PO 청구계정
	final static public String KOS_COMMON_HEADER_APP_NAME_NBSS_ACT = "NBSS_ACT";
	// 차세대_CDM-PO 통합UI
	final static public String KOS_COMMON_HEADER_APP_NAME_NBSS_IUI = "NBSS_IUI";
	// 차세대_OnBill
	final static public String KOS_COMMON_HEADER_APP_NAME_NBSS_ONBILL = "NBSS_ARO";
	// 차세대_OnBill
	final static public String KOS_COMMON_HEADER_APP_NAME_NBSS_CARO = "NBSS_CARO";
	// 차세대_CRM-B2B
	final static public String KOS_COMMON_HEADER_APP_NAME_NBSS_B2B = "NBSS_B2B";
	// 차세대_CRM-B2C
	final static public String KOS_COMMON_HEADER_APP_NAME_NBSS_B2C = "NBSS_B2C";
	// 차세대_SA공통
	final static public String KOS_COMMON_HEADER_APP_NAME_NBSS_SA = "NBSS_ESB";
	// 차세대 om 상품룰
	final static public String KOS_COMMON_HEADER_APP_NAME_NBSS_RLE = "NBSS_RLE";
	// 대리점 정산
	final static public String KOS_COMMON_HEADER_APP_NAME_NBSS_BND = "NBSS_BND";

	/*
	 * 차세대 표준전문 관련 TRFLAG 송수신 FLAG T:Request R:Reply ※NBSS_2단계_SA_표준전문_0.64.xlsx
	 * 참고
	 */
	final static public String KOS_COMMON_HEADER_TRFLAG_T = "T"; // T-Request
	final static public String KOS_COMMON_HEADER_TRFLAG_R = "R"; // Reply

	/*
	 * KM : K-MOS
	 */
	final static public String KOS_COMMON_HEADER_CHNL_TYPE_KM = "KM"; // 'KM'
																		// (K-MOS)

	// 셀프케어
	final static public String APP_USER_ID = "9911100";

	// 기기변경 구분코드
	final static public String ICG_DIV_CD_01 = "01"; // 신규
	final static public String ICG_DIV_CD_02 = "02"; // 기변
	final static public String ICG_DIV_CD_03 = "03"; // 기기변경취소

	// 기기변경 유형
	final static public String ICG_DTL_CD_01 = "01"; // 우수기변
	final static public String ICG_DTL_CD_07 = "07"; // 우수기변취소
	final static public String ICG_DTL_CD_38 = "38"; // 유심변경

	// 판매정책(애플전용)
	// final static public String INTM_SALEPLCY_NAC_ID = "P6401"; // 스폰서3_애플스폰서
	// TEST 개통할부
	// final static public String INTM_SALEPLCY_HCN_ID = "P6402"; // 스폰서3_애플스폰서
	// TEST 기변할부
	
	final static public String INTM_SALEPLCY_NAC_ID = "P6310"; // 스폰서3_애플스폰서
																// A_신규할부
	final static public String INTM_SALEPLCY_HCN_ID = "P6311"; // 스폰서3_애플스폰서
																// A_기변할부

	// 기변 화면 ID
	final static public String ICG_HCN_NICG0100 = "NICG0100"; // 본인인증
	final static public String ICG_HCN_NICG0200 = "NICG0200"; // 개인정보활용동의
	final static public String ICG_HCN_NICG0400 = "NICG0400"; // 단말정보
	final static public String ICG_HCN_NICG0500 = "NICG0500"; // 가입정보
	final static public String ICG_HCN_NICG0600 = "NICG0600"; // 계약정보 (가입정보확인)
	final static public String ICG_HCN_NICG0700 = "NICG0700"; // 약관동의
	final static public String ICG_HCN_NICG0800 = "NICG0800"; // 서식지


			
	//final static public String ICG_HCC_NICC0100 = "NICC0100"; // 기변 화면 ID	// NICC0100(기본정보)
	//final static public String ICG_HCC_NICC0300 = "NICC0300"; // 기변 화면 ID    // NICC0300(정보입력)

	// 기변 화면 스텝
	final static public String ICG_STEP_ID_1 = "1"; // 기변시 사용되는 STEP 1
	final static public String ICG_STEP_ID_2 = "2"; // 기변시 사용되는 STEP 2
	final static public String ICG_STEP_ID_3 = "3"; // 기변시 사용되는 STEP 3
	final static public String ICG_STEP_ID_4 = "4"; // 기변시 사용되는 STEP 4
	final static public String ICG_STEP_ID_5 = "5"; // 기변시 사용되는 STEP 5
	final static public String ICG_STEP_ID_6 = "6"; // 기변시 사용되는 STEP 6
	
	// 상품 정보
	final static public String RULE_EV_DTL_TYPE_CD_GOD = "GOD"; // 상품 률 이벤트 타입
																// 코드 우수기변
	final static public String RULE_EV_DTL_TYPE_CD_GCC = "GCC"; // 상품 률 이벤트 타입
																// 코드 우수기변취소

	/*
	 * 2016/05/30 긴급반영/서정득차장 요청사항 Rater 연동 시, Json Format에 들어가는 custId 앞에
	 * M-Platform은 'M'을 붙임
	 */
	final static public String KOS_RATER_CUST_ID_M = "M"; // 'M'
	// =============LE 추가 End ===================
}
