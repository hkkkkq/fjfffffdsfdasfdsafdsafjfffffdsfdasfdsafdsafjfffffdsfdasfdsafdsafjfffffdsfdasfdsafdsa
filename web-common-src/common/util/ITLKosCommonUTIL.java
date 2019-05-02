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
 * MPOnline CommonUTIL
 * </pre>
 * 
 * @path  com.kt.kkos.itl.bo.common
 * @file ITLKosCommonUTIL.java
 * @author 91142754
 * @authorName 유지현
 * @date 2016.05.23
 * @lastAuthor 91142754
 * @lastAuthorName 유지현
 * @lastAuthorDate 2016.05.23
 * @version 1.0
 * 
 * @history
 * 1 / 91142754 / 2016.05.23 / init
 */
package com.kt.kkos.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import org.apache.cxf.headers.Header;
import org.apache.cxf.helpers.CastUtils;
import org.apache.cxf.jaxws.context.WrappedMessageContext;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.kt.kkos.exception.ITLBusinessException;

public class ITLKosCommonUTIL {

	// static Logger logger = LoggerFactory.getLogger(ITLCommonUTIL.class);

	/**
	 * <pre>
	 * ESB Response의 내용 중, 
	 * SOAPBody에 있는 Common Header의 내용을 CommonHeader DTO로 추출 하는 함수
	 * 호출하는 WsCall마다 CommonHeader의 Class 위치가 다르기 때문에 Object로 전달받고, Object로 리턴 함.
	 * 호출하는 WsCall에서 Object를 리턴받아 케스팅해서 사용 해야 함.
	 * </pre>
	 * 
	 * @param java.util.Map<String, Object> responseContext, Object obj
	 * @return Object
	 * @throws ITLBusinessException
	 */
	public Object getResKosCommHeader(
			java.util.Map<String, Object> responseContext, Object obj)
			throws ITLBusinessException {

		WrappedMessageContext messageContext = (WrappedMessageContext) responseContext;

		Class cls = obj.getClass();

		// CommonHeader Class File 명 체크
		if (cls == null
				|| !cls.getSimpleName().equals(
						ITLKosKeyUTIL.KOS_COMMON_HEADER_NAME)) {
			throw new ITLBusinessException(ITLKeyUTIL.ITL_RESPONSE_TYPE_SYSERR,
					"ESBS0001");
		}

		try {

			Field[] fieldList = cls.getDeclaredFields();

			Method[] methodList = cls.getMethods();

			if (messageContext != null) {
				// Header List 추출
				List<Header> tempHeaders = CastUtils
						.cast((List<?>) messageContext.get(Header.HEADER_LIST));

				if (tempHeaders != null) {
					// CommonHeader 추출
					Element element = (Element) (tempHeaders.get(0))
							.getObject();
					if (element != null) {
						// CommonHeader Child Node List 추출
						NodeList nodeList = element.getChildNodes();
						if (nodeList != null && nodeList.getLength() > 0) {
							for (int i = 0; i < nodeList.getLength(); i++) {
								if (nodeList.item(i).getLocalName() != null) {
									for (int j = 0; j < methodList.length; j++) {
										// CommonHeader DTO Method 추출
										Method method = methodList[j];

										if (method.getName().substring(0, 3)
												.equals("set")
												&& method
														.getName()
														.substring(3)
														.equalsIgnoreCase(
																nodeList.item(i)
																		.getLocalName())) {
											// CommonHeader set'Object' 실행
											method.invoke(obj,
													new Object[] { nodeList
															.item(i)
															.getTextContent() });
										}

									}
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			throw new ITLBusinessException(ITLKeyUTIL.ITL_RESPONSE_TYPE_SYSERR,
					"ESBS0001");
		}

		return obj;
	}
	
	/**
	 * <pre>
	 * Kos GlobalNo 채번 룰
	 * Customer Key 15자리+송신일자 8자리 + 송신시각 6자리 + 밀리세컨 3자리= 합계 32자리
	 * Customer Key-GlobalNo를 생성하는 주체 별로 유일한 ID를 부여할 수 있는 체계(Left padding zeros)	"   - 통합 UI / RDS UI : 사용자 ID - 채널:대표 사용자ID
	 *  송신일자- YYYYMMDD
	 *  송신시간 - HH24MISS
	 *  밀리세컨 - millisecond(3)
	 *  합계 32 ex) 000000008202215520150430095959001
	 * </pre>
	 * 
	 * @param String
	 *            appNstepUserId
	 * @return String
	 * @throws ITLBusinessException
	 */
	static public String getKosGlobalNo() throws ITLBusinessException {
		try {
			return UUID.randomUUID().toString().replaceAll("-", "");
		} catch (Exception e) {
			throw new ITLBusinessException(e);
		}
	}

	static public String getClientIp() {
		if (ITLStringUTIL.isNullOrEmpty(System.getenv("DOMAIN_IP"))) {
			return null;
		} else {
			return System.getenv("DOMAIN_IP");
		}
	}

	/*
	 * LE 프로젝트 코드 변환 고객식별번호 변환 함수 01:주민등록번호 -> 1 02:사업자번호 -> 7 03:법인번호 -> 3
	 * 04:여권번호 -> 2 05:외국인등록번호 -> 4 06:사회보장번호 -> 5 07:운전면허번호 -> 9 08:고객번호 -> 24
	 * 09:고유번호 -> 22 10:조직코드 -> 21 11:임의부여번호 -> 10 12:건강보험증번호 -> 24 13:국내거소신고증번호
	 * -> 24 14:해지고객식별번호 -> 14 15:신용조회식별번호 -> 24 8:법인실사용자식별번호 -> 98 99:식별번호 식별
	 * 안됨 -> 24 by jhyoo
	 */
	static public String convertCustIdntNoIndCd(String str) {

		if (str.equals("01") || str.equals("1")) {
			return "1";
		} else if (str.equals("02") || str.equals("7")) {
			return "7";
		} else if (str.equals("03") || str.equals("3")) {
			return "3";
		} else if (str.equals("04") || str.equals("2")) {
			return "2";
		} else if (str.equals("05") || str.equals("4")) {
			return "4";
		} else if (str.equals("06") || str.equals("5")) {
			return "5";
		} else if (str.equals("07") || str.equals("9")) {
			return "9";
		} else if (str.equals("09") || str.equals("22")) {
			return "22";
		} else if (str.equals("10") || str.equals("21")) {
			return "21";
		} else if (str.equals("11") || str.equals("10")) {
			return "10";
		} else if (str.equals("14") || str.equals("14")) {
			return "14";
		} else if (str.equals("98")) {
			return "98";
		} else if (str.equals("99") || str.equals("24")) {
			return "24";
		} else {
			return "24";
		}
	}

	/*
	 * LE 프로젝트 코드 변환 RDS IND_CD(구분검색조건코드) 변환 함수 구분 검색조건
	 * 1.기기모델ID(intmModelId)+기기일련번호(intmSeq) 2.기술기준확인증명번호(techBaseConfNo)
	 * 3.기기모델ID(intmModelId)+IMEI(intmIdfyNo1) 4.IMEI(intmIdfyNo1) 기존 코드 2와 5를
	 * 연동하는 부분에서 코드 4로 변경 by jhyoo
	 */
	static public String cnvtIndCd(String str) {
		if (str.equals("2") || str.equals("5")) {
			return "4";
		} else {
			return str;
		}
	}

	/*
	 * LE 프로젝트 코드 변환 기기공급구분코드 : INTM_JTS_IND_CD -> intmSplPathCd AS-IS TO-BE
	 * Y(자사) 01(KT유통) N(타사) 02(제조사유통) N(타사) 03(제조사유통) by jhyoo
	 */
	static public String cnvtIntmJtsIndCd(String str) {
		if (str.equals("01")) {
			return "Y";
		} else {
			return "N";
		}
	}

	/*
	 * LE 프로젝트 코드 변환 기기분류 코드 :INTM_KIND_CD -> intmCtgCd AS-IS TO-BE 1(단말기)
	 * M01(무선단말기) 3(정보기기) M03(정보기기) 4(부속품) M04(부속품) 6(USIM) M02(USIM)
	 * 7(단말기SUB모델) M05(단말기SUB모델) 8(결합용액세서리) M06(결합용액세서리)
	 * 
	 * by jhyoo
	 */
	static public String cnvtIntmKindCd(String str) {
		if (str.equals("M01")) {
			return "1";
		} else if (str.equals("M03")) {
			return "3";
		} else if (str.equals("M04")) {
			return "4";
		} else if (str.equals("M02")) {
			return "6";
		} else if (str.equals("M05")) {
			return "7";
		} else if (str.equals("M06")) {
			return "8";
		}

		return str;
	}

	/*
	 * LE 프로젝트 코드 변환 고객유형코드 : CUST_TYPE_CD -> custTypeCd AS-IS TO-BE B 법인사업자 3 G
	 * 공공기관 4 I 개인고객 1 O 개인사업자 2 J 법인 8 L 기타 9 V 임의단체 5 by jhyoo
	 */
	static public String cnvtCustTypeCd(String str) {
		// As-is 코드 --> To-be 코드
		if (str.equals("B")) {
			return "3";
		} else if (str.equals("G")) {
			return "4";
		} else if (str.equals("I")) {
			return "1";
		} else if (str.equals("O")) {
			return "2";
		} else if (str.equals("J")) {
			return "8";
		} else if (str.equals("L")) {
			return "9";
		} else if (str.equals("V")) {
			return "5";
		}// To-be 코드 --> As-is 코드
		else if (str.equals("3")) {
			return "B";
		} else if (str.equals("4")) {
			return "G";
		} else if (str.equals("1")) {
			return "I";
		} else if (str.equals("2")) {
			return "O";
		} else if (str.equals("8")) {
			return "J";
		} else if (str.equals("9")) {
			return "L";
		} else if (str.equals("5")) {
			return "V";
		}
		return str;
	}

	/*
	 * 은행코드 변환 함수 by 91127307
	 */
	static public String convertBankCd(String str) {
		return str.substring(0, 3);
	}

	/*
	 * 이전 월 구하기 (yyyyMM) by 91127307
	 */
	static public String getPreMonth(String date) {
		int year = Integer.parseInt(date.substring(0, 4));
		int month = Integer.parseInt(date.substring(4, 6));

		Calendar cal = Calendar.getInstance();
		cal.set(year, month, 1);
		cal.add(Calendar.MONTH, -2);
		String preYear = String.valueOf(cal.get(Calendar.YEAR));
		String preMonth = String.valueOf(cal.get(Calendar.MONTH) + 1);
		if (preMonth.length() < 2)
			preMonth = "0" + preMonth; // NOPMD
		String preDate = preYear + preMonth;
		return preDate;
	}

}
