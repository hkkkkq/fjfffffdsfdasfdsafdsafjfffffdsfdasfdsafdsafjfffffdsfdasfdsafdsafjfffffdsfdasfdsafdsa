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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 * 날짜 연산 및 변환을 위한 클래스.
 * 
 * 이 클래스는 일반적인 Util 클래스와 달리 객체를 생성한 후에 사용 가능하다.
 * 
 * Date 객체 또는 날짜 형식의 문자열 년/월/일 등을 매개변수로 객체를 생성할 수 있으며 객체 생성 후에는 날짜 연산 및 변환, 다양한 패턴으로의 출력 등 다양한 기능을 사용할 수 있다.
 * 
 * SqlDate 클래스에서 대부분의 날짜 연산 및 변환 기능이 제공되지만 SqlDate 클래스가 구현하기 힘든 기능이나 Util 성 메소드 (static 메소드)가 편한 경우에 한하여 DateUtil 클래스에서
 * 추가적으로 기능을 제공하고 있다.
 * 
 * -. 두 날짜의 일수 차이 비교시 DateUtil.getDaysDiff("20060101", "20060201") => 31 일
 * 
 * -. 날짜 문자열의 유효성을 체크할 때 DateUtil.isValidDate("20061313") => false
 * 
 * -. 년, 월, 일을 각각 가져올 경우 SqlDate sqlDate = new SqlDate("20061212"); sqlDate.getYear() => 2006 년 sqlDate.getMonth() => 12
 * 월 sqlDate.getDay() => 12 일
 * 
 * @author Administrator
 *
 */
public class CalendarWrapper
{

	private static final int[] months = { 0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
	private static final int[] monthStacks = { 0, 0, 31, 59, 90, 120, 151, 181, 212, 243, 273, 304, 334, 365 };
	private static final int[] reverseMonthStacks = { 0, 365, 334, 306, 275, 245, 214, 184, 153, 122, 92, 61, 31, 0 };

	private static final int[] monthsLeapYear = { 0, 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
	private static final int[] monthStacksLeapYear = { 0, 0, 31, 60, 91, 121, 152, 182, 213, 244, 274, 305, 335, 366 };
	private static final int[] reverseMonthStacksLeapYear = { 0, 366, 335, 306, 275, 245, 214, 184, 153, 122, 92, 61, 31, 0 };

	public static String[] formatListDate = { "yyyy-MM-dd", "yyyy/MM/dd", "yyyyMMdd", "yyyy년 MM월 dd일", "yyyy.MM.dd", "yyyy MM dd" };

	/**
	 * GregorianCalendar instance.
	 */
	private transient GregorianCalendar cal;
	/**
	 * 한글 형식의 날짜 DateFormatter
	 */
	private static SimpleDateFormat textFormatter = new SimpleDateFormat("yyyy년 MM월 dd일", Locale.KOREA);
	/**
	 * 한글 형식의 시간 DateFormatter
	 */
	private static SimpleDateFormat textTimeFormatter = new SimpleDateFormat("HH시 mm분 ss초", Locale.KOREA);

	/**
	 * TimeZone
	 */
	private static TimeZone tz = TimeZone.getTimeZone("Asia/Seoul");

	static
	{
		textFormatter.setTimeZone(tz);
		textTimeFormatter.setTimeZone(tz);
	}

	/**
	 * 요일 한글 변환을 위한 문자열
	 */
	private static final String[] DOW_NMS_KR = new String[] { "일요일", "월요일", "화요일", "수요일", "목요일", "금요일", "토요일" };
	/**
	 * 요일 영문 변환을 위한 문자열
	 */
	private static final String[] DOW_NMS_EN = new String[] { "SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT" };

	/**
	 * 날짜 포함 최소 길이
	 */
	private static final int DATE_MIN_LEN = 8;
	/**
	 * 시간 포함 최소 길이
	 */
	private static final int HAS_HOUR_MIN_LEN = 10;
	/**
	 * 분 포함 최소 길이
	 */
	private static final int HAS_MIN_MIN_LEN = 12;
	/**
	 * 초 포함 최소 길이
	 */
	private static final int HAS_SEC_MIN_LEN = 14;
	/**
	 * 밀리초 포함 최소 길이
	 */
	private static final int HAS_MLS_MIN_LEN = 17;

	/**
	 * <pre>
	 * 현재 날짜에 대한 CalendarWrapper 객체를 생성함.
	 * </pre>
	 */
	public CalendarWrapper()
	{
		this(new Date(System.currentTimeMillis()));
	}

	/**
	 * <pre>
	 * 입력 문자열에 대한 CalendarWrapper 객체를 생성함.
	 * </pre>
	 *
	 * @param strDate yyyyMMdd 또는 yyyyMMddHHmmss 형식의 문자열, 구분자가 있어도 상관없음.
	 * @throws IllegalArgumentException 입력값이 날짜 형식이 아닐 경우 발생.
	 */
	public CalendarWrapper(final String strDate) throws IllegalArgumentException
	{
		cal = toCalendar(strDate);
	}

	/**
	 * <pre>
	 * 입력 Date 객체에 대한 CalendarWrapper 객체를 생성함.
	 * </pre>
	 *
	 * @param date Date 객체
	 */
	public CalendarWrapper(final Date date)
	{
		cal = new GregorianCalendar(CalendarWrapper.tz, Locale.KOREA);
		cal.setTime(date);
	}

	/**
	 * <pre>
	 * 입력 시간(long)에 대한 CalendarWrapper 객체를 생성함.
	 * </pre>
	 * 
	 * @param time 입력 시
	 */
	public CalendarWrapper(final long time)
	{
		cal = new GregorianCalendar(CalendarWrapper.tz, Locale.KOREA);
		cal.setTimeInMillis(time);
	}

	/**
	 * <pre>
	 * 입력 년월일에 대한 CalendarWrapper 객체를 생성함.
	 * </pre>
	 *
	 * @param year 년
	 * @param month 월 - 1월부터 시작함..
	 * @param day 일
	 */
	public CalendarWrapper(final int year, final int month, final int day)
	{
		cal = new GregorianCalendar(year, month - 1, day);
		cal.setTimeZone(CalendarWrapper.tz);
	}

	/**
	 * <pre>
	 * 입력 년월일시분초에 대한 CalendarWrapper 객체를 생성함.
	 * </pre>
	 *
	 * @param year 년
	 * @param month 월 - 1월부터 시작함..
	 * @param day 일
	 * @param hour 시
	 * @param min 분
	 * @param sec 초
	 */
	public CalendarWrapper(final int year, final int month, final int day, final int hour, final int min, final int sec)
	{
		cal = new GregorianCalendar(year, month - 1, day, hour, min, sec);
		cal.setTimeZone(CalendarWrapper.tz);
	}

	/**
	 * <pre>
	 * yyyy<구분자>MM<구분자>dd 형식의 문자열을 가져옴.
	 * </pre>
	 *
	 * @param delim 날짜 구분자
	 * @return 날짜 문자열
	 */
	public String getDateString(final String delim)
	{
		final SimpleDateFormat sdf = getFormatter("yyyy" + delim + "MM" + delim + "dd");
		return sdf.format(getDate());
	}

	/**
	 * <pre>
	 * HH(0-23)<구분자>mm<구분자>ss 형식의 문자열을 가져옴.
	 * </pre>
	 *
	 * @param delim 시간 구분자
	 * @return 시간 문자열
	 */
	public String getTimeString(final String delim)
	{
		final SimpleDateFormat sdf = getFormatter("HH" + delim + "mm" + delim + "ss");
		return sdf.format(getDate());
	}

	/**
	 * <pre>
	 * yyyy<날짜구분자>MM<날짜구분자>dd HH(0-23)<시간구분자>mm<시간구분자>ss 형식의 문자열을 가져옴.
	 * </pre>
	 *
	 * @param dateDeim 날짜 구분자
	 * @param timeDelim 시간 구분자
	 * @return 날짜 + 시간 문자열
	 */
	public String getDateTimeString(final String dateDeim, final String timeDelim)
	{
		final SimpleDateFormat sdf = getFormatter("yyyy" + dateDeim + "MM" + dateDeim + "dd HH" + timeDelim + "mm"
				+ timeDelim + "ss");
		return sdf.format(getDate());
	}

	/**
	 * <pre>
	 * NSP 표준 날짜 형식인 yyyyMMdd 형식의 문자열을 가져옴.
	 * </pre>
	 *
	 * @return yyyyMMdd 형식의 문자열
	 */
	public String getDateString()
	{
		return getBlankDateString();
	}

	/**
	 * <pre>
	 * NSP 표준 시간 형식인 HHmmss 형식의 문자열을 가져옴. 시간은 0-23 으로 표현됨.
	 * </pre>
	 *
	 * @return HHmmss 형식의 문자열
	 */
	public String getTimeString()
	{
		return getBlankTimeString();
	}

	/**
	 * <pre>
	 * NSP 표준 날짜 형식인 yyyyMMddHHmmss 형식의 문자열을 가져옴. 시간은 0-23 으로 표현됨.
	 * </pre>
	 *
	 * @return yyyyMMddHHmmss 형식의 문자열
	 */
	public String getDateTimeString()
	{
		return getBlankDateString() + getBlankTimeString();
	}

	/**
	 * <pre>
	 * yyyyMMdd 형식의 문자열을 가져옴.
	 * </pre>
	 *
	 * @return yyyyMMdd 형식의 문자열
	 */
	public String getBlankDateString()
	{
		final SimpleDateFormat sdf = getFormatter("yyyyMMdd");
		return sdf.format(getDate());
	}

	/**
	 * <pre>
	 * yyyy-MM-dd 형식의 문자열을 가져옴.
	 * </pre>
	 *
	 * @return yyyy-MM-dd 형식의 문자열
	 */
	public String getDashDateString()
	{
		final SimpleDateFormat sdf = getFormatter("yyyy-MM-dd");
		return sdf.format(getDate());
	}

	/**
	 * <pre>
	 * yyyy/MM/dd 형식의 문자열을 가져옴.
	 * </pre>
	 *
	 * @return yyyy/MM/dd 형식의 문자열
	 */
	public String getSlashDateString()
	{
		final SimpleDateFormat sdf = getFormatter("yyyy/MM/dd");
		return sdf.format(getDate());
	}

	/**
	 * <pre>
	 * yyyy.MM.dd 형식의 문자열을 가져옴.
	 * </pre>
	 *
	 * @return yyyy.MM.dd 형식의 문자열
	 */
	public String getDotDateString()
	{
		final SimpleDateFormat sdf = getFormatter("yyyy.MM.dd");
		return sdf.format(getDate());
	}

	/**
	 * <pre>
	 * HHmmss 형식의 문자열을 가져옴. 시간은 0-23 으로 표현됨.
	 * </pre>
	 *
	 * @return HHmmss 형식의 문자열
	 */
	public String getBlankTimeString()
	{
		final SimpleDateFormat sdf = getFormatter("HHmmss");
		return sdf.format(getDate());
	}

	/**
	 * <pre>
	 * HH:mm:ss 형식의 문자열을 가져옴. 시간은 0-23 으로 표현됨.
	 * </pre>
	 *
	 * @return yyyy-MM-dd HH:mm:ss 형식의 문자열
	 */
	public String getColonTimeString()
	{
		final SimpleDateFormat sdf = getFormatter("HH:mm:ss");
		return sdf.format(getDate());
	}

	/**
	 * <pre>
	 * yyyy년 MM월 dd일 형식의 문자열을 가져옴.
	 * </pre>
	 *
	 * @return yyyy년 MM월 dd일 형식의 문자열
	 */
	public String getTextDateString()
	{
		synchronized (textFormatter)
		{
			return textFormatter.format(getDate());
		}
	}

	/**
	 * <pre>
	 * yyyy년 MM월 dd일 HH시 mm분 ss초 형식의 문자열을 가져옴. 시간은 0-23 으로 표현됨.
	 * </pre>
	 *
	 * @return HH시 mm분 ss초 형식의 문자열
	 */
	public String getTextTimeString()
	{
		synchronized (textTimeFormatter)
		{
			return textTimeFormatter.format(getDate());
		}
	}

	/**
	 * <pre>
	 * yyyy년 MM월 dd일 HH시 mm분 ss초 형식의 문자열을 가져옴. 시간은 0-23 으로 표현됨.
	 * </pre>
	 *
	 * @return yyyy년 MM월 dd일 HH시 mm분 ss초 형식의 문자열
	 */
	public String getTextDateTimeString()
	{
		synchronized (textFormatter)
		{
			return textFormatter.format(getDate()) + " " + textTimeFormatter.format(getDate());
		}
	}

	/**
	 * <pre>
	 * 날짜 문자열을 캘린더 타입으로 변환한다.
	 * </pre>
	 *
	 * @param strDate yyyyMMdd 또는 yyyyMMddHHmmss 형식의 문자열, 구분자가 있어도 상관없음.
	 * @return GregorianCalendar
	 * @throws IllegalArgumentException 년월일이 없는 경우 발생
	 */
	private GregorianCalendar toCalendar(final String strDate) throws IllegalArgumentException
	{
		if (StringUtil.isEmpty(strDate))
		{
			throw new IllegalArgumentException("Date String Format Error : " + strDate);
		}

		String tempStrDate = strDate;
		if (tempStrDate == null)
		{
			tempStrDate = Constants.EMPTY;
		}

		// 숫자를 제외한 모든 문자를 제거한다.
		tempStrDate = tempStrDate.replaceAll("\\D", Constants.EMPTY);

		final int len = tempStrDate.length();
		if (len < DATE_MIN_LEN)
		{
			throw new IllegalArgumentException("Date String Format Error : " + tempStrDate);
		}

		final int year = Integer.parseInt(tempStrDate.substring(0, 4));
		final int mon = Integer.parseInt(tempStrDate.substring(4, 6));
		final int day = Integer.parseInt(tempStrDate.substring(6, 8));

		int hour = 0;
		int min = 0;
		int sec = 0;
		int millis = 0;
		if (len >= HAS_MLS_MIN_LEN)
		{
			millis = Integer.parseInt(tempStrDate.substring(14));
			sec = Integer.parseInt(tempStrDate.substring(12, 14));
			min = Integer.parseInt(tempStrDate.substring(10, 12));
			hour = Integer.parseInt(tempStrDate.substring(8, 10));
		} else if (len >= HAS_HOUR_MIN_LEN)
		{
			hour = Integer.parseInt(tempStrDate.substring(8, 10));
			if (len >= HAS_SEC_MIN_LEN)
			{
				sec = Integer.parseInt(tempStrDate.substring(12, 14));
				min = Integer.parseInt(tempStrDate.substring(10, 12));
			} else if (len >= HAS_MIN_MIN_LEN)
			{
				min = Integer.parseInt(tempStrDate.substring(10, 12));
			}
		}

		final GregorianCalendar cal = new GregorianCalendar(year, mon - 1, day, hour, min, sec);
		cal.set(Calendar.MILLISECOND, millis);
		cal.setTimeZone(CalendarWrapper.tz);

		return cal;
	}

	/**
	 * <pre>
	 * 해당 연도를 가져옴.
	 * </pre>
	 *
	 * @return 연도
	 */
	public int getYear()
	{
		return cal.get(Calendar.YEAR);
	}

	/**
	 * <pre>
	 * 해당 월을 가져옴.
	 * </pre>
	 *
	 * @return 월 - 1월부터
	 */
	public int getMonth()
	{
		return cal.get(Calendar.MONTH) + 1;
	}

	/**
	 * <pre>
	 * 해당 일을 가져옴.
	 * </pre>
	 *
	 * @return 일
	 * @Mody 20090220 22:10 Calendar.DATE -> Calendar.DAY_OF_MONTH
	 */
	public int getDay()
	{
		return cal.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * <pre>
	 * 해당 시간을 가져옴.
	 * </pre>
	 *
	 * @return 시 0-23
	 */
	public int getHour()
	{
		return cal.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * <pre>
	 * 해당 분을 가져옴.
	 * </pre>
	 *
	 * @return 분
	 */
	public int getMinute()
	{
		return cal.get(Calendar.MINUTE);
	}

	/**
	 * <pre>
	 * 해당 초를 가져옴.
	 * </pre>
	 *
	 * @return 초
	 */
	public int getSecond()
	{
		return cal.get(Calendar.SECOND);
	}

	/**
	 * <pre>
	 * 해당 밀리초를 가져옴.
	 * </pre>
	 *
	 * @return 밀리초
	 */
	public int getMilliSecond()
	{
		return cal.get(Calendar.MILLISECOND);
	}

	/**
	 * <pre>
	 * 입력 년, 월, 일 이후의 날짜를 가져옴.
	 * </pre>
	 *
	 * @param year 년수
	 * @param month 개월수
	 * @param day 일수
	 * @return 입력 년, 월, 일이 더해진 날짜
	 */
	public Date afterDate(final int year, final int month, final int day)
	{
		final GregorianCalendar aCal = (GregorianCalendar) cal.clone();
		aCal.add(Calendar.YEAR, year);
		aCal.add(Calendar.MONTH, month);
		aCal.add(Calendar.DATE, day);

		return new Date(aCal.getTimeInMillis());
	}

	/**
	 * <pre>
	 * 입력 년, 월, 일, 시, 분, 초 이후의 날짜를 가져옴.
	 * </pre>
	 *
	 * @param year 년수
	 * @param month 개월수
	 * @param day 일수
	 * @param hour 시간
	 * @param min 분
	 * @param sec 초
	 * @return 입력 년, 월, 일, 시, 분, 초가 더해진 날짜
	 */
	public Date afterDate(final int year, final int month, final int day, final int hour, final int min, final int sec)
	{
		final GregorianCalendar aCal = (GregorianCalendar) cal.clone();
		aCal.add(Calendar.YEAR, year);
		aCal.add(Calendar.MONTH, month);
		aCal.add(Calendar.DATE, day);
		aCal.add(Calendar.HOUR_OF_DAY, hour);
		aCal.add(Calendar.MINUTE, min);
		aCal.add(Calendar.SECOND, sec);

		return new Date(aCal.getTimeInMillis());
	}

	/**
	 * <pre>
	 * 입력 년, 월, 일 이전의 날짜를 가져옴.
	 * </pre>
	 *
	 * @param year 년수
	 * @param month 개월수
	 * @param day 일수
	 * @return 입력 년, 월, 일이 빠진 날짜
	 */
	public Date beforeDate(final int year, final int month, final int day)
	{
		return afterDate(-year, -month, -day);
	}

	/**
	 * <pre>
	 * 입력 년, 월, 일, 시, 분, 초 이전의 날짜를 가져옴.
	 * </pre>
	 *
	 * @param year 년수
	 * @param month 개월수
	 * @param day 일수
	 * @param hour 시간
	 * @param min 분
	 * @param sec 초
	 * @return 입력 년, 월, 일, 시, 분, 초가 빠진 날짜
	 */
	public Date beforeDate(final int year, final int month, final int day, final int hour, final int min, final int sec)
	{
		return afterDate(-year, -month, -day, -hour, -min, -sec);
	}

	/**
	 * <pre>
	 * 입력 연도 이후의 날짜를 가져옴.
	 * </pre>
	 *
	 * @param year 년수
	 * @return 입력 년수가 더해진 날짜, year 가 음수일 경우 빼기된 날짜
	 */
	public Date afterYear(final int year)
	{

		return add(Calendar.YEAR, year);
	}

	/**
	 * <pre>
	 * 입력 개월 이후의 날짜를 가져옴.
	 * </pre>
	 *
	 * @param month 개월수
	 * @return 입력 개월수가 더해진 날짜, month 가 음수일 경우 빼기된 날짜
	 */
	public Date afterMonth(final int month)
	{

		return add(Calendar.MONTH, month);
	}

	/**
	 * <pre>
	 * 입력 일자 이후의 날짜를 가져옴.
	 * </pre>
	 *
	 * @param day 일수
	 * @return 입력 일수가 더해진 날짜, day 가 음수일 경우 빼기된 날짜
	 */
	public Date afterDay(final int day)
	{
		return add(Calendar.DATE, day);
	}

	/**
	 * <pre>
	 * 입력 연도 이전의 날짜를 가져옴.
	 * </pre>
	 *
	 * @param year 년수
	 * @return 입력 년수가 빼기된 날짜, year 가 음수일 경우 더해된 날짜
	 */
	public Date beforeYear(final int year)
	{

		return add(Calendar.YEAR, -year);
	}

	/**
	 * <pre>
	 * 입력 개월 이전의 날짜를 가져옴.
	 * </pre>
	 *
	 * @param month 개월수
	 * @return 입력 개월수가 빼기된 날짜, month 가 음수일 경우 더해된 날짜
	 */
	public Date beforeMonth(final int month)
	{

		return add(Calendar.MONTH, -month);
	}

	/**
	 * <pre>
	 * 입력 일자 이전의 날짜를 가져옴.
	 * </pre>
	 *
	 * @param day 일수
	 * @return 입력 일수가 빼기된 날짜, day 가 음수일 경우 더해된 날짜
	 */
	public Date beforeDay(final int day)
	{

		return add(Calendar.DATE, -day);
	}

	/**
	 * <pre>
	 * 주어진 필드에 해당 값을 더한 날짜를 가져옴.
	 * </pre>
	 *
	 * @param field
	 * @param amount - 음수(-)인 경우 빼기 연산이 됨.
	 * @return Date
	 */
	private Date add(final int field, final int amount)
	{
		final GregorianCalendar aCal = (GregorianCalendar) cal.clone();
		aCal.add(field, amount);

		return new Date(aCal.getTimeInMillis());
	}

	/**
	 * <pre>
	 * 입력일자가 이후 날짜인지 판단한다.
	 * </pre>
	 *
	 * @param aDate Date 객체
	 * @return 입력일자가 이후인 경우 TRUE
	 */
	public boolean isAfter(final Date aDate)
	{
		final Calendar aCal = (GregorianCalendar) cal.clone();
		aCal.setTime(aDate);
		return cal.after(aCal);
	}

	/**
	 * <pre>
	 * 요일을 int 값으로 가져옴. (0: 일요일, 1: 월요일, ... , 6: 토요일)
	 * </pre>
	 *
	 * @return Calendar 클래스의 요일 상수와 일치하는 int 값. 예) Calendar.SUNDAY == 1
	 */
	public int getDayOfWeek()
	{
		return cal.get(Calendar.DAY_OF_WEEK) - 1;
	}

	/**
	 * <pre>
	 * 요일을 한글명으로 가져옴. (일요일, 월요일, .. 토요일)
	 * </pre>
	 *
	 * @return 요일 한글명
	 */
	public String getDayOfWeekInKorean()
	{
		final int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
		return DOW_NMS_KR[dayOfWeek - 1];
	}

	/**
	 * <pre>
	 * 요일을 영문명으로 가져옴. (SUN, MON, .. SAT)
	 * </pre>
	 *
	 * @return 요일 영문명
	 */
	public String getDayOfWeekInEnglish()
	{
		final int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
		return DOW_NMS_EN[dayOfWeek - 1];
	}

	/**
	 * <pre>
	 * 해당월에 몇 일 까지 있는지를 가져옴.
	 * </pre>
	 *
	 * @return 주어진 달이 가진 일 수
	 */
	public int getLastDayOfMonth()
	{
		return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	/**
	 * <pre>
	 * 해당 날짜가 속한 주의 날짜를 일요일 - 토요일 순의 배열로 돌려준다.
	 * 
	 * 사용 예)
	 * 
	 * new CalendarWrapper("20051220").getDatesOfWeeks() => {"20051218", "20051219", .. "20051224"}
	 * </pre>
	 *
	 * @return 해당 날짜가 속한 주의 날짜들의 배열
	 */
	public String[] getDatesOfWeeks()
	{
		final String dateStr = getBlankDateString();
		return getWeekDays(Integer.parseInt(dateStr.substring(0, 4)), Integer.parseInt(dateStr.substring(4, 6)),
				Integer.parseInt(dateStr.substring(6)));
	}

	public static String[] getWeekDays(int year, int month, int day)
	{
		int dow = getDayOfTheWeek(year, month, day) - 1;

		String[] days = new String[7];

		for (int i = 0; i < 7; i++)
		{
			days[i] = getDateDaysDiff(i - dow, year, month, day);
		}

		return days;
	}

	public static int getDayOfTheWeek(int year, int month, int day)
	{
		Calendar cal = Calendar.getInstance();
		cal.set(year, month - 1, day);

		return cal.get(7);
	}

	public static int getDayOfTheWeek(String date)
	{
		return getDayOfTheWeek(Integer.parseInt(date.substring(0, 4)), Integer.parseInt(date.substring(4, 6)),
				Integer.parseInt(date.substring(6)));
	}

	public static String getDateDaysDiff(int diff, int year, int month, int day)
	{
		if (diff > 0)
			return getDateDaysAfter(diff, year, month, day);

		return getDateDaysBefore(0 - diff, year, month, day);
	}

	public static String getDateDaysBefore(int before, String date)
	{
		return setDateOfMonth(before, date, false);
	}

	public static String getDateDaysBefore(int before, int year, int month, int day)
	{
		int upBefore = before;
		int upYear = year;
		int upMonth = month;
		
		if (!DateUtil.isLeapYear(upYear))
		{
			if (upBefore < day)
				return Integer.toString(upYear * 10000 + upMonth * 100 + (day - upBefore));

			upBefore -= day;
			upMonth--;
			if (upMonth == 0)
			{
				upYear--;
				upMonth = 12;
			}
		} else
		{
			if (upBefore < day)
				return Integer.toString(upYear * 10000 + upMonth * 100 + (day - upBefore));

			upBefore -= day;
			upMonth--;
			if (upMonth == 0)
			{
				upYear--;
				upMonth = 12;
			}
		}

		if (!DateUtil.isLeapYear(upYear))
		{
			if (upBefore < monthStacks[(upMonth + 1)])
			{
				while (upBefore >= months[upMonth])
				{
					upBefore -= months[upMonth];
					upMonth--;
				}

				return Integer.toString(upYear * 10000 + upMonth * 100 + months[upMonth] - upBefore);
			}

			upBefore -= monthStacks[(upMonth + 1)];
			upYear--;
			upMonth = 12;
		} else
		{
			if (upBefore < monthStacksLeapYear[(upMonth + 1)])
			{
				while (upBefore >= monthsLeapYear[upMonth])
				{
					upBefore -= monthsLeapYear[upMonth];
					upMonth--;
				}

				return Integer.toString(upYear * 10000 + upMonth * 100 + monthsLeapYear[upMonth] - upBefore);
			}

			upBefore -= monthStacksLeapYear[(upMonth + 1)];
			upYear--;
			upMonth = 12;
		}

		while (upBefore >= 365 + DateUtil.getLeapYearCount(upYear))
		{
			upBefore -= 365 + DateUtil.getLeapYearCount(upYear);
			upYear--;
		}

		if (!DateUtil.isLeapYear(upYear))
		{
			while (upBefore >= months[upMonth])
			{
				upBefore -= months[upMonth];
				upMonth--;
			}

			return Integer.toString(upYear * 10000 + upMonth * 100 + months[upMonth] - upBefore);
		}

		while (upBefore >= monthsLeapYear[upMonth])
		{
			upBefore -= monthsLeapYear[upMonth];
			upMonth--;
		}

		return Integer.toString(upYear * 10000 + upMonth * 100 + monthsLeapYear[upMonth] - upBefore);
	}

	public static String getDateDaysAfter(int after, String date)
	{
		return setDateOfMonth(after, date, true);
	}

	private static String setDateOfMonth(int days, String date, boolean isAfter)
	{
		SimpleDateFormat formatter = new SimpleDateFormat(formatListDate[2]);
		Date inputDate;
		try
		{
			inputDate = formatter.parse(date);
		} catch (ParseException e)
		{
			throw new RuntimeException(e);			 // NOPMD
		}
		Calendar calender = Calendar.getInstance();
		calender.setTime(inputDate);
		if (isAfter)
			calender.set(5, calender.get(5) + days);
		else
		{
			calender.set(5, calender.get(5) - days);
		}

		return formatter.format(calender.getTime()).toString();
	}

	public static String getDateDaysAfter(int after, int year, int month, int day)
	{
		int upAfter = after;
		int upYear = year;
		int upMonth = month;
		
		if (!DateUtil.isLeapYear(upYear))
		{
			if (upAfter <= months[upMonth] - day)
				return Integer.toString(upYear * 10000 + upMonth * 100 + (day + upAfter));

			upAfter -= months[upMonth] - day;
			upMonth++;
			if (upMonth == 13)
			{
				upYear++;
				upMonth = 1;
			}
		} else
		{
			if (upAfter <= monthsLeapYear[upMonth] - day)
				return Integer.toString(upYear * 10000 + upMonth * 100 + (day + upAfter));

			upAfter -= monthsLeapYear[upMonth] - day;
			upMonth++;
			if (upMonth == 13)
			{
				upYear++;
				upMonth = 1;
			}
		}

		if (!DateUtil.isLeapYear(upYear))
		{
			if (upAfter <= reverseMonthStacks[upMonth])
			{
				while (upAfter > months[upMonth])
				{
					upAfter -= months[upMonth];
					upMonth++;
				}

				return Integer.toString(upYear * 10000 + upMonth * 100 + upAfter);
			}

			upAfter -= reverseMonthStacks[upMonth];
			upYear++;
			upMonth = 1;
		} else
		{
			if (upAfter <= reverseMonthStacksLeapYear[upMonth])
			{
				while (upAfter > monthsLeapYear[upMonth])
				{
					upAfter -= monthsLeapYear[upMonth];
					upMonth++;
				}

				return Integer.toString(upYear * 10000 + upMonth * 100 + upAfter);
			}

			upAfter -= reverseMonthStacksLeapYear[upMonth];
			upYear++;
			upMonth = 1;
		}

		while (upAfter > 365 + DateUtil.getLeapYearCount(upYear))
		{
			upAfter -= 365 + DateUtil.getLeapYearCount(upYear);
			upYear++;
		}

		if (!DateUtil.isLeapYear(upYear))
		{
			while (upAfter > months[upMonth])
			{
				upAfter -= months[upMonth];
				upMonth++;
			}

			return Integer.toString(upYear * 10000 + upMonth * 100 + upAfter);
		}

		while (upAfter > monthsLeapYear[upMonth])
		{
			upAfter -= monthsLeapYear[upMonth];
			upMonth++;
		}

		return Integer.toString(upYear * 10000 + upMonth * 100 + upAfter);
	}

	/**
	 * <pre>
	 * 해당년도가 윤년인지를 판단한다.
	 * </pre>
	 *
	 * @return 윤년일 경우 <code>true</code>
	 */
	public boolean isLeapYear()
	{
		final int year = getYear();
		return DateUtil.isLeapYear(year);
	}

	/**
	 * <pre>
	 * 해당 일자를 밀리초로 환산함. 1970년 1월 1일 0시 0분 0초 기준으로 계산됨.
	 * 
	 * 사용 예)
	 * new CalendarWrapper("19700130").getTimeInMillis() => 29 * 24 * 3600 * 1000 (29일을 밀리초로 환산한 값.)
	 *
	 * </pre>
	 *
	 * @return 밀리초로 환산된 long형
	 */
	public long getTimeInMillis()
	{
		return getDate().getTime();
	}

	/**
	 * <pre>
	 * 해당일자가 적용된 Date 객체를 가져옴.
	 * 
	 * 사용 예)
	 * new CalendarWrapper().getDate() => new Date(System.currentTimeMillis())   (현재 날짜가 적용된 Date 객체)
	 * </pre>
	 *
	 * @return Date 객체
	 */
	public Date getDate()
	{
		final long timeInMillis = cal.getTimeInMillis();
		return new Date(timeInMillis);
	}

	/**
	 * <pre>
	 * 입력 날짜 패턴에 해당하는 DateFormat 객체를 생성.
	 * </pre>
	 * 
	 * @param pattern 입력 날짜 패턴
	 * @return DateFormat
	 */
	private SimpleDateFormat getFormatter(final String pattern)
	{
		SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.KOREA);
		sdf.setTimeZone(CalendarWrapper.tz);
		return sdf;
	}

	/**
	 * <pre>
	 * 날짜를 문자열로 변환하여 반환.
	 * </pre>
	 * 
	 * @return 날짜변환 문자열
	 */
	public String toString()
	{
		final Date date = cal.getTime();
		return date.toString();
	}
}
