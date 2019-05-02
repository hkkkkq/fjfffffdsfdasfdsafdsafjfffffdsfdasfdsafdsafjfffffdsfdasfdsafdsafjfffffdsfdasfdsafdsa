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

import java.util.Calendar;
import java.util.Date;


/**
 * @author Administrator
 *
 */
public final class AgeUtil
{
	/**
	 * Private 생성자
	 */
	private AgeUtil()
	{
	}

	/**
	 * <pre>
	 * 주민등록번호 기준 만나이가 성년인지 판별
	 * </pre>
	 *
	 * @param socl 주민등록번호
	 * @return 성년 여부
	 */
	public static boolean isAdultBySocl(final String socl)
	{
		if (StringUtil.isEmpty(socl) || socl.length() != Constants.SOCL_LEN)
		{
			throw new IllegalArgumentException(Constants.SOCL_LEN_ERROR + socl);
		}

		StringBuilder birthDateStr = new StringBuilder();
		switch (socl.substring(6, 7))
		{
			case "1":
			case "2":
			case "5":
			case "6":
				
				birthDateStr.append("19").append(socl.substring(0, 6));
				break;
			case "3":
			case "4":
			case "7":
			case "8":
				birthDateStr.append("20").append(socl.substring(0, 6));
				break;
			case "0":
				birthDateStr.append("18").append(socl.substring(0, 6));
				break;
			default:
				break;
		}

		return (AgeUtil.getRealAge(birthDateStr.toString()) >= 19);
	}
	
	/**
	 * <pre>
	 * 주민등록번호 기준 일반 나이 계산
	 * </pre>
	 *
	 * @param socl 주민등록번호
	 * @return 나이
	 */
	public static String getAgeBySoc(final String socl)
	{
		if (StringUtil.isEmpty(socl) || socl.length() != Constants.SOCL_LEN)
		{
			throw new IllegalArgumentException(Constants.SOCL_LEN_ERROR + socl);
		}

		StringBuilder birthDateStr = new StringBuilder();
		switch (socl.substring(6, 7))
		{
			case "1":
			case "2":
			case "5":
			case "6":
				
				birthDateStr.append("19").append(socl.substring(0, 6));
				break;
			case "3":
			case "4":
			case "7":
			case "8":
				birthDateStr.append("20").append(socl.substring(0, 6));
				break;
			case "0":
				birthDateStr.append("18").append(socl.substring(0, 6));
				break;
			default:
				break;
		}

		return String.valueOf(AgeUtil.getAge(birthDateStr + "000000"));
	}
	
	/**
	 * <pre>
	 * 현재일자를 기준으로 생년월일에서 성인여부 판단
	 * </pre>
	 * 
	 * @param birthDateStr 생년월일 - 맨앞에 년(YYYY) 형식이 들어가 있는 문자열
	 * @return 현재일자를 기준으로 성인여부 반환
	 * @throws IllegalArgumentException 생년월일에 연도가 포함되지 않은 경우 발생.
	 */
	public static boolean isAdult(final String birthDateStr) throws IllegalArgumentException
	{
		return getAge(birthDateStr) >= 19;
	}

	/**
	 * <pre>
	 * 현재일자를 기준으로 생년월일에서 성인여부 판단
	 * </pre>
	 * 
	 * @param birthDateStr 생년월일 - 맨앞에 년(YYYY) 형식이 들어가 있는 문자열
	 * @param stdDateStr 기준일자 - 맨앞에 년(YYYY) 형식이 들어가 있는 문자열
	 * @return 현재일자를 기준으로 성인여부 반환
	 * @throws IllegalArgumentException 생년월일에 연도가 포함되지 않은 경우 발생.
	 */
	public static boolean isAdult(final String birthDateStr, final String stdDateStr) throws IllegalArgumentException
	{
		return getAge(birthDateStr, stdDateStr) >= 19;
	}

	/**
	 * <pre>
	 * 현재년도를 기준으로 생년월일에서 한국식 나이를 구함.
	 * </pre>
	 * 
	 * @param birthDateStr 생년월일 - 맨앞에 년(YYYY) 형식이 들어가 있는 문자열
	 * @return 현재일자를 기준으로 계산된 한국식 나이
	 * @throws IllegalArgumentException 생년월일에 연도가 포함되지 않은 경우 발생.
	 */
	public static int getAge(final String birthDateStr) throws IllegalArgumentException
	{
		return getAge(birthDateStr, DateUtil.toDateString());
	}

	/**
	 * <pre>
	 * 기준일자를 기준으로 생년월일에서 한국식 나이를 구함.
	 * </pre>
	 * 
	 * @param birthDateStr 생년월일 - 맨앞에 년(YYYY) 형식이 들어가 있는 문자열
	 * @param stdDateStr 기준일자 - 맨앞에 년(YYYY) 형식이 들어가 있는 문자열
	 * @return 한국식 나이
	 * @throws IllegalArgumentException 생년월일에 연도가 포함되지 않은 경우 발생.
	 */
	public static int getAge(final String birthDateStr, final String stdDateStr) throws IllegalArgumentException
	{
		if (birthDateStr == null || birthDateStr.length() < 4 || !StringUtil.isNumberOnly(birthDateStr))
		{
			throw new IllegalArgumentException(Constants.DATE_FORMAT_ERROR + birthDateStr);
		}
		if (stdDateStr == null || stdDateStr.length() < 4 || !StringUtil.isNumberOnly(stdDateStr))
		{
			throw new IllegalArgumentException(Constants.DATE_FORMAT_ERROR + stdDateStr);
		}

		final int stdYear = Integer.parseInt(stdDateStr.substring(0, 4));
		int birthYear = 0;
		if (PatternUtil.isValidResno(birthDateStr))
		{
			if ("0".equals(birthDateStr.substring(0, 2)))
			{
				birthYear = Integer.parseInt("20" + birthDateStr.substring(0, 2));
			} else
			{
				birthYear = Integer.parseInt("19" + birthDateStr.substring(0, 2));
			}
		} else
		{
			birthYear = Integer.parseInt(birthDateStr.substring(0, 4));
		}
		return stdYear - birthYear + 1;
	}

	/**
	 * <pre>
	 * 기준일자를 기준으로 생년월일에서 한국식 나이를 구함.
	 * </pre>
	 * 
	 * @param birthDate 생년월일
	 * @param stdDate 기준일자
	 * @return 한국식 나이
	 */
	public static int getAge(final Date birthDate, final Date stdDate)
	{
		return DateUtil.getYearsDiff(birthDate, stdDate) + 1;
	}

	/**
	 * 현재일자를 기준으로 생년월일에서 만나이를 구함. </pre>
	 * 
	 * @param birthDateStr 생년월일 - 년(YYYY), 월(MM), 일(DD) 형식이 들어가 있는 문자열, 년월일 사이에 구분자가 있어도 상관없음.
	 * @return 만나이
	 * @throws IllegalArgumentException 생년월일에서 년(YYYY), 월(MM), 일(DD)을 추출할 수 없는 경우 발생
	 */
	public static int getRealAge(final String birthDateStr) throws IllegalArgumentException
	{
		return getRealAge(birthDateStr, new CalendarWrapper().getDateString());
	}

	/**
	 * </pre> 기준일자를 기준으로 생년월일에서 만나이를 구함. </pre>
	 * 
	 * @param birthDateStr 생년월일 - 년(YYYY), 월(MM), 일(DD) 형식이 들어가 있는 문자열, 년월일 사이에 구분자가 있어도 상관없음.
	 * @param stdDateStr 기준일자 - 년(YYYY), 월(MM), 일(DD) 형식이 들어가 있는 문자열, 년월일 사이에 구분자가 있어도 상관없음.
	 * @return 만나이
	 * @throws IllegalArgumentException 생년월일 또는 기준일자에서 년(YYYY), 월(MM), 일(DD)을 추출할 수 없는 경우 발생
	 */
	public static int getRealAge(final String birthDateStr, final String stdDateStr) throws IllegalArgumentException
	{
		if (birthDateStr == null || !DateUtil.isValidDate(birthDateStr))
		{
			throw new IllegalArgumentException(Constants.DATE_FORMAT_ERROR + birthDateStr);
		}
		if (stdDateStr == null || !DateUtil.isValidDate(stdDateStr))
		{
			throw new IllegalArgumentException(Constants.DATE_FORMAT_ERROR + stdDateStr);
		}
		final Date birthDate = DateUtil.toDate(birthDateStr);
		final Date stdDate = DateUtil.toDate(stdDateStr);

		return getRealAge(birthDate, stdDate);
	}

	/**
	 * <pre>
	 * 기준일자를 기준으로 생년월일에서 만나이를 구함.
	 * </pre>
	 * 
	 * @param birthDate 생년월일
	 * @param stdDate 기준일자
	 * @return 만나이
	 * @throws IllegalArgumentException 생년월일 또는 기준일자에서 년(YYYY), 월(MM), 일(DD)을 추출할 수 없는 경우 발생
	 */
	public static int getRealAge(final Date birthDate, final Date stdDate) throws IllegalArgumentException
	{
		final int monDiff = DateUtil.getMonthsDiff(birthDate, stdDate);
		return (int) Math.floor(monDiff / 12.0);
	}

	/**
	 * <pre>
	 * 기준일자를 기준으로 생년월일에서 만나이(XX세 XX개월 XX일)를 구함.
	 * </pre>
	 * 
	 * @param birthDateStr 생년월일 - 년(YYYY), 월(MM), 일(DD) 형식이 들어가 있는 문자열, 년월일 사이에 구분자가 있어도 상관없음.
	 * @param stdDateStr 기준일자 - 년(YYYY), 월(MM), 일(DD) 형식이 들어가 있는 문자열, 년월일 사이에 구분자가 있어도 상관없음.
	 * @return 만나이(XX세 XX개월 XX일)
	 * @throws IllegalArgumentException 생년월일 또는 기준일자에서 년(YYYY), 월(MM), 일(DD)을 추출할 수 없는 경우 발생
	 */
	public static String getRealAgeDiff(final String birthDateStr, final String stdDateStr)
			throws IllegalArgumentException
	{
		if (birthDateStr == null || !DateUtil.isValidDate(birthDateStr))
		{
			throw new IllegalArgumentException(Constants.DATE_FORMAT_ERROR + birthDateStr);
		}
		if (stdDateStr == null || !DateUtil.isValidDate(stdDateStr))
		{
			throw new IllegalArgumentException(Constants.DATE_FORMAT_ERROR + stdDateStr);
		}
		final Date birthDate = DateUtil.toDate(birthDateStr);
		final Date stdDate = DateUtil.toDate(stdDateStr);

		return getRealAgeDiff(birthDate, stdDate);
	}

	/**
	 * <pre>
	 * 기준일자를 기준으로 생년월일에서 만나이(XX세 XX개월 XX일)를 구함.
	 * </pre>
	 * 
	 * @param birthDate 생년월일
	 * @param stdDate 기준일자
	 * @return 만나이(XX세 XX개월 XX일)
	 * @throws IllegalArgumentException 생년월일 또는 기준일자에서 년(YYYY), 월(MM), 일(DD)을 추출할 수 없는 경우 발생
	 */
	public static String getRealAgeDiff(final Date birthDate, final Date stdDate) throws IllegalArgumentException
	{
		final int[] diff = DateUtil.getDifference(birthDate, stdDate);
		return diff[0] + "세 " + diff[1] + "개월 " + diff[2] + "일";
	}
	
	/**
	 * <pre>
	 * 미성년자 체크
	 * </pre>
	 *
	 * @param strJumin 주민등록번호
	 * @param strCheckYMD 기준일자
	 * @return 성년 여부
	 */
	public static boolean isYoungAge(String inStrJumin)
	{
		String strJumin = inStrJumin;
		// 현재일자 계산
		String strCheckYMD = "";
		
		Calendar today = Calendar.getInstance();
		Date newdate = new Date();
		
		today.setTime(newdate);
		
		String yeaer = String.valueOf(today.get(Calendar.YEAR));
		String month = String.valueOf(today.get(Calendar.MONTH));
		String date = String.valueOf(today.get(Calendar.DATE));
		
		StringBuilder  monthCal = new StringBuilder();
		
		if (month.length()<2) {
//			month = "0"+month;
//			String monthCal = "0";
//			monthCal = monthCal+month;
//			month =  monthCal;
			
			month = monthCal.append("0").append(month).toString();
		}
		
		StringBuilder dateCal = new StringBuilder();
		if (date.length()<2) {
//			date = "0"+date;
//			String dateCal = "0";
//			dateCal = dateCal+date;
//			date = dateCal;
			
			date = dateCal.append("0").append(date).toString();
		}
		
		strCheckYMD = yeaer+month+date;
		
		String ls_year		= "";
		String ls_day		= "";
		int ls_age		= 0;
	    String ls_birthDay	= "";
	    
	    String sMidJumin = strJumin.substring(6, 7);
	    if (sMidJumin.equals(1)||sMidJumin.equals(2)||sMidJumin.equals(5)||sMidJumin.equals(6)) {
	    	ls_year = "19";
		}else {
			ls_year = "20";
		}
	    
	  //외국인등록번호 __0000xxxxxxx인 경우 만나이 0 수정 0000->0101로 대체(CSM1요청)
	    if (strJumin.substring(2, 4).equals("0000")) {
			
	    	String FbirthDay = StringUtil.replace(strJumin.substring(2, 4), "0000", "0101");
	    	strJumin = strJumin.substring(0, 1)+FbirthDay+strJumin.substring(6, 12);
		}
	    
	    ls_birthDay = ls_year + strJumin.substring(0, 6);
	    
	    ls_age = Integer.parseInt(strCheckYMD.toString().substring(0, 8)) - Integer.parseInt(ls_birthDay);
	    
	    if(ls_age < 0) return false;
	    
	    if (String.valueOf(ls_age).trim().length()==4) {
	    	return false;
		}
	    
//	    String appLgDate = DateUtil.getDate().toString();
	    String appLgDate = yeaer+month+date;
	    
	    StringBuilder birthDateStr = new StringBuilder();
		
		switch (strJumin.substring(6, 7))
		{
			case "1":
			case "2":
			case "5":
			case "6":
				
				birthDateStr.append("19").append(strJumin.substring(0, 6));
				break;
			case "3":
			case "4":
			case "7":
			case "8":
				birthDateStr.append("20").append(strJumin.substring(0, 6));
				break;
			case "0":
				birthDateStr.append("18").append(strJumin.substring(0, 6));
				break;
			default:
				break;
		}
		
		ls_age = getAge(birthDateStr.toString())-1;
		
		if (20130701 <= Integer.parseInt(appLgDate.substring(0, 8))) {
			 if (ls_age <= 18) {
				 return true;
			 }else {
				return false;
			 }
			
		}else {
			if (ls_age <= 19) {
				 return true;
			 }else {
				return false;
			 }
		}
		
	}
	
	/**
	 * <pre>
	 * 고객식별번호로 고객생년월일 생성
	 * </pre>
	 *
	 * @param strCustIdfyNo : 고객식별번호
	 * @return 생년 월일
	 */
	public static String buildBirthDate(String strCustIdfyNo)
	{
		String strBirthDate = "";

		StringBuilder sbBirthDate = new StringBuilder();
		sbBirthDate.append("");
		
		switch (strCustIdfyNo.substring(6, 7))
		{
			case "1":
			case "2":
			case "5":
			case "6":
				sbBirthDate.append("19");
				break;
			case "3":
			case "4":
			case "7":
			case "8":
				sbBirthDate.append("20");
				break;
			case "9":
			case "0":
				sbBirthDate.append("18");
				break;
			default:
				break;
		}
		
		// 생년월일 생성
		strBirthDate = sbBirthDate.append(strCustIdfyNo.substring(0, 6)).toString();

		return strBirthDate;
	}
}
