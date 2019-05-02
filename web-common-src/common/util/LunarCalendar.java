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
public class LunarCalendar
{

	/**
	 * 음력 데이터 (평달 - 작은달 :1, 큰달:2 ) (윤달이 있는 달 - 평달이 작고 윤달도 작으면 :3 , 평달이 작고 윤달이 크면 : 4) (윤달이 있는 달 - 평달이 크고 윤달이 작으면 :5, 평달과
	 * 윤달이 모두 크면 : 6)
	 */
	private final int[][] lunarMonthTable = { { 2, 1, 2, 1, 2, 1, 2, 2, 1, 2, 1, 2 },
			{ 1, 2, 1, 1, 2, 1, 2, 5, 2, 2, 1, 2 }, { 1, 2, 1, 1, 2, 1, 2, 1, 2, 2, 2, 1 }, /* 1901 */
			{ 2, 1, 2, 1, 1, 2, 1, 2, 1, 2, 2, 2 }, { 1, 2, 1, 2, 3, 2, 1, 1, 2, 2, 1, 2 },
			{ 2, 2, 1, 2, 1, 1, 2, 1, 1, 2, 2, 1 }, { 2, 2, 1, 2, 2, 1, 1, 2, 1, 2, 1, 2 },
			{ 1, 2, 2, 4, 1, 2, 1, 2, 1, 2, 1, 2 }, { 1, 2, 1, 2, 1, 2, 2, 1, 2, 1, 2, 1 },
			{ 2, 1, 1, 2, 2, 1, 2, 1, 2, 2, 1, 2 }, { 1, 5, 1, 2, 1, 2, 1, 2, 2, 2, 1, 2 },
			{ 1, 2, 1, 1, 2, 1, 2, 1, 2, 2, 2, 1 }, { 2, 1, 2, 1, 1, 5, 1, 2, 2, 1, 2, 2 }, /* 1911 */
			{ 2, 1, 2, 1, 1, 2, 1, 1, 2, 2, 1, 2 }, { 2, 2, 1, 2, 1, 1, 2, 1, 1, 2, 1, 2 },
			{ 2, 2, 1, 2, 5, 1, 2, 1, 2, 1, 1, 2 }, { 2, 1, 2, 2, 1, 2, 1, 2, 1, 2, 1, 2 },
			{ 1, 2, 1, 2, 1, 2, 2, 1, 2, 1, 2, 1 }, { 2, 3, 2, 1, 2, 2, 1, 2, 2, 1, 2, 1 },
			{ 2, 1, 1, 2, 1, 2, 1, 2, 2, 2, 1, 2 }, { 1, 2, 1, 1, 2, 1, 5, 2, 2, 1, 2, 2 },
			{ 1, 2, 1, 1, 2, 1, 1, 2, 2, 1, 2, 2 }, { 2, 1, 2, 1, 1, 2, 1, 1, 2, 1, 2, 2 }, /* 1921 */
			{ 2, 1, 2, 2, 3, 2, 1, 1, 2, 1, 2, 2 }, { 1, 2, 2, 1, 2, 1, 2, 1, 2, 1, 1, 2 },
			{ 2, 1, 2, 1, 2, 2, 1, 2, 1, 2, 1, 1 }, { 2, 1, 2, 5, 2, 1, 2, 2, 1, 2, 1, 2 },
			{ 1, 1, 2, 1, 2, 1, 2, 2, 1, 2, 2, 1 }, { 2, 1, 1, 2, 1, 2, 1, 2, 2, 1, 2, 2 },
			{ 1, 5, 1, 2, 1, 1, 2, 2, 1, 2, 2, 2 }, { 1, 2, 1, 1, 2, 1, 1, 2, 1, 2, 2, 2 },
			{ 1, 2, 2, 1, 1, 5, 1, 2, 1, 2, 2, 1 }, { 2, 2, 2, 1, 1, 2, 1, 1, 2, 1, 2, 1 }, /* 1931 */
			{ 2, 2, 2, 1, 2, 1, 2, 1, 1, 2, 1, 2 }, { 1, 2, 2, 1, 6, 1, 2, 1, 2, 1, 1, 2 },
			{ 1, 2, 1, 2, 2, 1, 2, 2, 1, 2, 1, 2 }, { 1, 1, 2, 1, 2, 1, 2, 2, 1, 2, 2, 1 },
			{ 2, 1, 4, 1, 2, 1, 2, 1, 2, 2, 2, 1 }, { 2, 1, 1, 2, 1, 1, 2, 1, 2, 2, 2, 1 },
			{ 2, 2, 1, 1, 2, 1, 4, 1, 2, 2, 1, 2 }, { 2, 2, 1, 1, 2, 1, 1, 2, 1, 2, 1, 2 },
			{ 2, 2, 1, 2, 1, 2, 1, 1, 2, 1, 2, 1 }, { 2, 2, 1, 2, 2, 4, 1, 1, 2, 1, 2, 1 }, /* 1941 */
			{ 2, 1, 2, 2, 1, 2, 2, 1, 2, 1, 1, 2 }, { 1, 2, 1, 2, 1, 2, 2, 1, 2, 2, 1, 2 },
			{ 1, 1, 2, 4, 1, 2, 1, 2, 2, 1, 2, 2 }, { 1, 1, 2, 1, 1, 2, 1, 2, 2, 2, 1, 2 },
			{ 2, 1, 1, 2, 1, 1, 2, 1, 2, 2, 1, 2 }, { 2, 5, 1, 2, 1, 1, 2, 1, 2, 1, 2, 2 },
			{ 2, 1, 2, 1, 2, 1, 1, 2, 1, 2, 1, 2 }, { 2, 2, 1, 2, 1, 2, 3, 2, 1, 2, 1, 2 },
			{ 2, 1, 2, 2, 1, 2, 1, 1, 2, 1, 2, 1 }, { 2, 1, 2, 2, 1, 2, 1, 2, 1, 2, 1, 2 }, /* 1951 */
			{ 1, 2, 1, 2, 4, 2, 1, 2, 1, 2, 1, 2 }, { 1, 2, 1, 1, 2, 2, 1, 2, 2, 1, 2, 2 },
			{ 1, 1, 2, 1, 1, 2, 1, 2, 2, 1, 2, 2 }, { 2, 1, 4, 1, 1, 2, 1, 2, 1, 2, 2, 2 },
			{ 1, 2, 1, 2, 1, 1, 2, 1, 2, 1, 2, 2 }, { 2, 1, 2, 1, 2, 1, 1, 5, 2, 1, 2, 2 },
			{ 1, 2, 2, 1, 2, 1, 1, 2, 1, 2, 1, 2 }, { 1, 2, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1 },
			{ 2, 1, 2, 1, 2, 5, 2, 1, 2, 1, 2, 1 }, { 2, 1, 2, 1, 2, 1, 2, 2, 1, 2, 1, 2 }, /* 1961 */
			{ 1, 2, 1, 1, 2, 1, 2, 2, 1, 2, 2, 1 }, { 2, 1, 2, 3, 2, 1, 2, 1, 2, 2, 2, 1 },
			{ 2, 1, 2, 1, 1, 2, 1, 2, 1, 2, 2, 2 }, { 1, 2, 1, 2, 1, 1, 2, 1, 1, 2, 2, 2 },
			{ 1, 2, 5, 2, 1, 1, 2, 1, 1, 2, 2, 1 }, { 2, 2, 1, 2, 2, 1, 1, 2, 1, 2, 1, 2 },
			{ 1, 2, 2, 1, 2, 1, 5, 2, 1, 2, 1, 2 }, { 1, 2, 1, 2, 1, 2, 2, 1, 2, 1, 2, 1 },
			{ 2, 1, 1, 2, 2, 1, 2, 1, 2, 2, 1, 2 }, { 1, 2, 1, 1, 5, 2, 1, 2, 2, 2, 1, 2 }, /* 1971 */
			{ 1, 2, 1, 1, 2, 1, 2, 1, 2, 2, 2, 1 }, { 2, 1, 2, 1, 1, 2, 1, 1, 2, 2, 2, 1 },
			{ 2, 2, 1, 5, 1, 2, 1, 1, 2, 2, 1, 2 }, { 2, 2, 1, 2, 1, 1, 2, 1, 1, 2, 1, 2 },
			{ 2, 2, 1, 2, 1, 2, 1, 5, 2, 1, 1, 2 }, { 2, 1, 2, 2, 1, 2, 1, 2, 1, 2, 1, 1 },
			{ 2, 2, 1, 2, 1, 2, 2, 1, 2, 1, 2, 1 }, { 2, 1, 1, 2, 1, 6, 1, 2, 2, 1, 2, 1 },
			{ 2, 1, 1, 2, 1, 2, 1, 2, 2, 1, 2, 2 }, { 1, 2, 1, 1, 2, 1, 1, 2, 2, 1, 2, 2 }, /* 1981 */
			{ 2, 1, 2, 3, 2, 1, 1, 2, 2, 1, 2, 2 }, { 2, 1, 2, 1, 1, 2, 1, 1, 2, 1, 2, 2 },
			{ 2, 1, 2, 2, 1, 1, 2, 1, 1, 5, 2, 2 }, { 1, 2, 2, 1, 2, 1, 2, 1, 1, 2, 1, 2 },
			{ 1, 2, 2, 1, 2, 2, 1, 2, 1, 2, 1, 1 }, { 2, 1, 2, 2, 1, 5, 2, 2, 1, 2, 1, 2 },
			{ 1, 1, 2, 1, 2, 1, 2, 2, 1, 2, 2, 1 }, { 2, 1, 1, 2, 1, 2, 1, 2, 2, 1, 2, 2 },
			{ 1, 2, 1, 1, 5, 1, 2, 1, 2, 2, 2, 2 }, { 1, 2, 1, 1, 2, 1, 1, 2, 1, 2, 2, 2 }, /* 1991 */
			{ 1, 2, 2, 1, 1, 2, 1, 1, 2, 1, 2, 2 }, { 1, 2, 5, 2, 1, 2, 1, 1, 2, 1, 2, 1 },
			{ 2, 2, 2, 1, 2, 1, 2, 1, 1, 2, 1, 2 }, { 1, 2, 2, 1, 2, 2, 1, 5, 2, 1, 1, 2 },
			{ 1, 2, 1, 2, 2, 1, 2, 1, 2, 2, 1, 2 }, { 1, 1, 2, 1, 2, 1, 2, 2, 1, 2, 2, 1 },
			{ 2, 1, 1, 2, 3, 2, 2, 1, 2, 2, 2, 1 }, { 2, 1, 1, 2, 1, 1, 2, 1, 2, 2, 2, 1 },
			{ 2, 2, 1, 1, 2, 1, 1, 2, 1, 2, 2, 1 }, { 2, 2, 2, 3, 2, 1, 1, 2, 1, 2, 1, 2 }, /* 2001 */
			{ 2, 2, 1, 2, 1, 2, 1, 1, 2, 1, 2, 1 }, { 2, 2, 1, 2, 2, 1, 2, 1, 1, 2, 1, 2 },
			{ 1, 5, 2, 2, 1, 2, 1, 2, 1, 2, 1, 2 }, { 1, 2, 1, 2, 1, 2, 2, 1, 2, 2, 1, 1 },
			{ 2, 1, 2, 1, 2, 1, 5, 2, 2, 1, 2, 2 }, { 1, 1, 2, 1, 1, 2, 1, 2, 2, 2, 1, 2 },
			{ 2, 1, 1, 2, 1, 1, 2, 1, 2, 2, 1, 2 }, { 2, 2, 1, 1, 5, 1, 2, 1, 2, 1, 2, 2 },
			{ 2, 1, 2, 1, 2, 1, 1, 2, 1, 2, 1, 2 }, { 2, 1, 2, 2, 1, 2, 1, 1, 2, 1, 2, 1 }, /* 2011 */
			{ 2, 1, 6, 2, 1, 2, 1, 1, 2, 1, 2, 1 }, { 2, 1, 2, 2, 1, 2, 1, 2, 1, 2, 1, 2 },
			{ 1, 2, 1, 2, 1, 2, 1, 2, 5, 2, 1, 2 }, { 1, 2, 1, 1, 2, 1, 2, 2, 2, 1, 2, 1 },
			{ 2, 1, 2, 1, 1, 2, 1, 2, 2, 1, 2, 2 }, { 2, 1, 1, 2, 3, 2, 1, 2, 1, 2, 2, 2 },
			{ 1, 2, 1, 2, 1, 1, 2, 1, 2, 1, 2, 2 }, { 2, 1, 2, 1, 2, 1, 1, 2, 1, 2, 1, 2 },
			{ 2, 1, 2, 5, 2, 1, 1, 2, 1, 2, 1, 2 }, { 1, 2, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1 }, /* 2021 */
			{ 2, 1, 2, 1, 2, 2, 1, 2, 1, 2, 1, 2 }, { 1, 5, 2, 1, 2, 1, 2, 2, 1, 2, 1, 2 },
			{ 1, 2, 1, 1, 2, 1, 2, 2, 1, 2, 2, 1 }, { 2, 1, 2, 1, 1, 5, 2, 1, 2, 2, 2, 1 },
			{ 2, 1, 2, 1, 1, 2, 1, 2, 1, 2, 2, 2 }, { 1, 2, 1, 2, 1, 1, 2, 1, 1, 2, 2, 2 },
			{ 1, 2, 2, 1, 5, 1, 2, 1, 1, 2, 2, 1 }, { 2, 2, 1, 2, 2, 1, 1, 2, 1, 1, 2, 2 },
			{ 1, 2, 1, 2, 2, 1, 2, 1, 2, 1, 2, 1 }, { 2, 1, 5, 2, 1, 2, 2, 1, 2, 1, 2, 1 }, /* 2031 */
			{ 2, 1, 1, 2, 1, 2, 2, 1, 2, 2, 1, 2 }, { 1, 2, 1, 1, 2, 1, 2, 1, 2, 2, 5, 2 },
			{ 1, 2, 1, 1, 2, 1, 2, 1, 2, 2, 2, 1 }, { 2, 1, 2, 1, 1, 2, 1, 1, 2, 2, 1, 2 },
			{ 2, 2, 1, 2, 1, 4, 1, 1, 2, 2, 1, 2 }, { 2, 2, 1, 2, 1, 1, 2, 1, 1, 2, 1, 2 },
			{ 2, 2, 1, 2, 1, 2, 1, 2, 1, 1, 2, 1 }, { 2, 2, 1, 2, 5, 2, 1, 2, 1, 2, 1, 1 },
			{ 2, 1, 2, 2, 1, 2, 2, 1, 2, 1, 2, 1 }, { 2, 1, 1, 2, 1, 2, 2, 1, 2, 2, 1, 2 }, /* 2041 */
			{ 1, 5, 1, 2, 1, 2, 1, 2, 2, 2, 1, 2 }, { 1, 2, 1, 1, 2, 1, 1, 2, 2, 1, 2, 2 } };

	/**
	 * 음력 윤달 여부
	 */
	private boolean isLunarLeapMonth;

	/**
	 * 음력/양력 변환 Type
	 */
	public enum LUNAR_SOLAR {
		TO_SOLAR,
		TO_LUNAR
	}

	/**
	 * 입력일자를 음력으로 변환
	 * 
	 * @param solarDateStr 양력일자
	 * @return 음력일자
	 * @throws IllegalArgumentException
	 * 
	 */
	public String solar2lunar(final String solarDateStr) throws IllegalArgumentException
	{
		final String tempSolarDateStr = solarDateStr.replaceAll("\\D", Constants.EMPTY);

		final int year = Integer.parseInt(tempSolarDateStr.substring(0, 4));
		final int month = Integer.parseInt(tempSolarDateStr.substring(4, 6));
		final int day = Integer.parseInt(tempSolarDateStr.substring(6, 8));

		return lunarCalc(year, month, day, LUNAR_SOLAR.TO_LUNAR, false);
	}

	/**
	 * 입력일자를 양력으로 변환
	 * 
	 * @param lunarDateStr 음력일자
	 * @param isLunarLeapMonth 윤달여부
	 * @return 양력일자
	 * @throws IllegalArgumentException
	 * 
	 */
	public String lunar2solar(final String lunarDateStr, final boolean isLunarLeapMonth)
			throws IllegalArgumentException
	{
		final String tempLunarDateStr = lunarDateStr.replaceAll("\\D", Constants.EMPTY);

		final int year = Integer.parseInt(tempLunarDateStr.substring(0, 4));
		final int month = Integer.parseInt(tempLunarDateStr.substring(4, 6));
		final int day = Integer.parseInt(tempLunarDateStr.substring(6, 8));

		return lunarCalc(year, month, day, LUNAR_SOLAR.TO_SOLAR, isLunarLeapMonth);
	}

	/**
	 * 입력일자를 음력으로 변환
	 * 
	 * @param year 년수
	 * @param month 월수(1부터 시작)
	 * @param day 일수
	 * @return 음력일자
	 * @throws IllegalArgumentException
	 * 
	 */
	public String solar2lunar(final int year, final int month, final int day) throws IllegalArgumentException
	{
		return lunarCalc(year, month, day, LUNAR_SOLAR.TO_LUNAR, false);
	}

	/**
	 * 입력일자를 양력으로 변환
	 * 
	 * @param year 년수
	 * @param month 월수
	 * @param day 일수
	 * @param isLunarLeapMonth 윤달여부
	 * @return 양력일자
	 * @throws IllegalArgumentException
	 * 
	 */
	public String lunar2solar(final int year, final int month, final int day, final boolean isLunarLeapMonth)
			throws IllegalArgumentException
	{
		return lunarCalc(year, month, day, LUNAR_SOLAR.TO_SOLAR, isLunarLeapMonth);
	}

	/**
	 * 양력 <-> 음력 변환 함수 type : TYPE.TO_LUNAR - 양력 -> 음력 TYPE.TO_SOLAR - 음력 -> 양력 isLeapMonth : false - 평달 true - 윤달 (type
	 * = 2 일때만 유효)
	 * 
	 * @param year
	 * @param month
	 * @param day
	 * @param type TYPE.TO_LUNAR(음력, TYPE.TO_SOLAR(양력)
	 * @param isLeapMonth
	 * @return 양력 또는 음력일자
	 * @throws IllegalArgumentException
	 * 
	 */
	public String lunarCalc(final int year, final int month, final int day, final LUNAR_SOLAR type,
			final boolean isLeapMonth) throws IllegalArgumentException
	{
		/* range check */
		if (year < 1900 || year > 2040)
		{
			// 1900년부터 2040년까지만 지원합니다
			throw new IllegalArgumentException(Constants.UNSUPPORTED_YEAR_RANGE + year);
		}

		int[] solMonthDay = { 31, 0, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
		int solYear, solMonth, solDay;
		int lunYear, lunMonth, lunDay;
		int lunMonthDay;
		/* 속도 개선을 위해 기준 일자를 여러개로 한다 */
		if (year >= 2000)
		{
			/* 기준일자 양력 2000년 1월 1일 (음력 1999년 11월 25일) */
			solYear = 2000;
			solMonth = 1;
			solDay = 1;
			lunYear = 1999;
			lunMonth = 11;
			lunDay = 25;
			isLunarLeapMonth = false;

			solMonthDay[1] = 29; /* 2000 년 2월 28일 */
			lunMonthDay = 30; /* 1999년 11월 */
		} else if (year >= 1970)
		{
			/* 기준일자 양력 1970년 1월 1일 (음력 1969년 11월 24일) */
			solYear = 1970;
			solMonth = 1;
			solDay = 1;
			lunYear = 1969;
			lunMonth = 11;
			lunDay = 24;
			isLunarLeapMonth = false;

			solMonthDay[1] = 28; /* 1970 년 2월 28일 */
			lunMonthDay = 30; /* 1969년 11월 */
		} else if (year >= 1940)
		{
			/* 기준일자 양력 1940년 1월 1일 (음력 1939년 11월 22일) */
			solYear = 1940;
			solMonth = 1;
			solDay = 1;
			lunYear = 1939;
			lunMonth = 11;
			lunDay = 22;
			isLunarLeapMonth = false;

			solMonthDay[1] = 29; /* 1940 년 2월 28일 */
			lunMonthDay = 29; /* 1939년 11월 */
		} else
		{
			/* 기준일자 양력 1900년 1월 1일 (음력 1899년 12월 1일) */
			solYear = 1900;
			solMonth = 1;
			solDay = 1;
			lunYear = 1899;
			lunMonth = 12;
			lunDay = 1;
			isLunarLeapMonth = false;

			solMonthDay[1] = 28; /* 1900 년 2월 28일 */
			lunMonthDay = 30; /* 1899년 12월 */
		}

		int lunIndex = lunYear - 1899;
		while (true)
		{
			if (type.equals(LUNAR_SOLAR.TO_LUNAR) && year == solYear && month == solMonth && day == solDay)
			{
				return String.format("%04d%02d%02d", lunYear, lunMonth, lunDay);
			} else if (type.equals(LUNAR_SOLAR.TO_SOLAR) && year == lunYear && month == lunMonth && day == lunDay
					&& isLeapMonth == isLunarLeapMonth)
			{
				return String.format("%04d%02d%02d", solYear, solMonth, solDay);
			}

			/* add a day of solar calendar */
			if (solMonth == 12 && solDay == 31)
			{
				solYear++;
				solMonth = 1;
				solDay = 1;

				/* set monthDay of Feb */
				if (solYear % 400 == 0)
				{
					solMonthDay[1] = 29;
				} else if (solYear % 100 == 0)
				{
					solMonthDay[1] = 28;
				} else if (solYear % 4 == 0)
				{
					solMonthDay[1] = 29;
				} else
				{
					solMonthDay[1] = 28;
				}
			} else if (solMonthDay[solMonth - 1] == solDay)
			{
				solMonth++;
				solDay = 1;
			} else
			{
				solDay++;
			}

			/* add a day of lunar calendar */
			if (lunMonth == 12
					&& ((lunarMonthTable[lunIndex][lunMonth - 1] == 1 && lunDay == 29) || (lunarMonthTable[lunIndex][lunMonth - 1] == 2 && lunDay == 30)))
			{
				lunYear++;
				lunMonth = 1;
				lunDay = 1;

				lunIndex = lunYear - 1899;

				if (lunarMonthTable[lunIndex][lunMonth - 1] == 1)
				{
					lunMonthDay = 29;
				} else if (lunarMonthTable[lunIndex][lunMonth - 1] == 2)
				{
					lunMonthDay = 30;
				}
			} else if (lunDay == lunMonthDay)
			{
				if (lunarMonthTable[lunIndex][lunMonth - 1] >= 3 && !isLunarLeapMonth)
				{
					lunDay = 1;
					isLunarLeapMonth = true;
				} else
				{
					lunMonth++;
					lunDay = 1;
					isLunarLeapMonth = false;
				}

				if (lunarMonthTable[lunIndex][lunMonth - 1] == 1)
				{
					lunMonthDay = 29;
				} else if (lunarMonthTable[lunIndex][lunMonth - 1] == 2)
				{
					lunMonthDay = 30;
				} else if (lunarMonthTable[lunIndex][lunMonth - 1] == 3)
				{
					lunMonthDay = 29;
				} else if (lunarMonthTable[lunIndex][lunMonth - 1] == 4 && !isLunarLeapMonth)
				{
					lunMonthDay = 29;
				} else if (lunarMonthTable[lunIndex][lunMonth - 1] == 4 && isLunarLeapMonth)
				{
					lunMonthDay = 30;
				} else if (lunarMonthTable[lunIndex][lunMonth - 1] == 5 && !isLunarLeapMonth)
				{
					lunMonthDay = 30;
				} else if (lunarMonthTable[lunIndex][lunMonth - 1] == 5 && isLunarLeapMonth)
				{
					lunMonthDay = 29;
				} else if (lunarMonthTable[lunIndex][lunMonth - 1] == 6)
				{
					lunMonthDay = 30;
				}
			} else
			{
				lunDay++;
			}

			if (type.equals(LUNAR_SOLAR.TO_SOLAR) && isLeapMonth && lunYear > year && lunMonth > month)
			{
				// 입력하신 음력일자는 윤달이 아닙니다.
				throw new IllegalArgumentException(Constants.NOT_LEAP_YEAR + year);
			}
		}
	}

	/**
	 * 윤달여부
	 * 
	 * @return 윤달이면 TRUE
	 * 
	 */
	public boolean isLeapMonth()
	{
		return isLunarLeapMonth;
	}
}
