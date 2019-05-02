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

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * @author Administrator
 *
 */
public class DateUtil
{

	/**
	 * 일자 Formatter
	 */
	private static SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyyMMdd", Locale.KOREA);

	/**
	 * 일자 리스트
	 */
	public static String[] dateFormatterList = { "yyyy-MM-dd", "yyyy/MM/dd", "yyyyMMdd", "yyyy년 MM월 dd일", "yyyy.MM.dd", "yyyy MM dd" };
	
	/**
	 * 시간 Formatter
	 */
	private static SimpleDateFormat timeFormatter = new SimpleDateFormat("HHmmss", Locale.KOREA);

	/**
	 * 시간(밀리초) Formatter
	 */
	private static SimpleDateFormat militimeFormatter = new SimpleDateFormat("HHmmssSSS", Locale.KOREA);

	/**
	 * 일시 Formatter
	 */
	private static SimpleDateFormat dateTimeFormatter = new SimpleDateFormat("yyyyMMddHHmmss", Locale.KOREA);

	/**
	 * TimeZone
	 */
	private static TimeZone tz = TimeZone.getTimeZone("Asia/Seoul");

	static
	{
		dateFormatter.setTimeZone(tz);
		timeFormatter.setTimeZone(tz);
		militimeFormatter.setTimeZone(tz);
		dateTimeFormatter.setTimeZone(tz);
	}

	/**
	 * <pre>
	 * Private 생성자
	 * </pre>
	 */
	private DateUtil()
	{
	}

	/**
	 * <pre>
	 * 현재 날짜를 Date 형으로 가져옴.
	 * </pre>
	 *
	 * @return Date
	 */
	public static Date getDate()
	{
		return getSystemDate();
	}

	/**
	 * <pre>
	 * 현재 날짜를 Timestamp 형으로 가져옴.
	 * </pre>
	 *
	 * @return Timestamp
	 */
	public static Timestamp getTimestamp()
	{
		return new Timestamp(getSystemDate().getTime());
	}

	/**
	 * <pre>
	 * 날짜(+시간) 문자열을 Date 형으로 변환함.
	 * </pre>
	 *
	 * @param strDate 날짜(+시간) 문자열 - yyyyMMdd 형식의 문자열, 구분자가 있어도 상관없음.
	 * @return Date
	 * @throws IllegalArgumentException 년월일이 없는 경우 발생
	 */
	public static Date toDate(final String strDate) throws IllegalArgumentException
	{
		return new CalendarWrapper(strDate).getDate();
	}

	/**
	 * <pre>
	 * 현재 날짜를 yyyyMMdd 형식의 문자열로 가져옴.
	 * </pre>
	 *
	 * 사용 예) DateUtilPO.getDateString() =&gt; &quot;20060311&quot; (현재 날짜가 출력됨) </pre>
	 *
	 * @return yyyyMMdd 형식의 현재 날짜
	 */
	public static String toDateString()
	{
		synchronized (dateFormatter)
		{
			return dateFormatter.format(getSystemDate());
		}
	}

	/**
	 * <pre>
	 * Date 객체를 yyyyMMdd 형식의 문자열로 변환함.
	 * </pre>
	 *
	 * @param date Date 객체
	 * @return yyyyMMdd 형식의 입력 날짜
	 */
	public static String toDateString(final Date date)
	{
		if (date == null || date.toString().startsWith(Constants.YEAR_1900))
		{
			return Constants.EMPTY;
		}

		synchronized (dateFormatter)
		{
			return dateFormatter.format(date);
		}
	}

	/**
	 * <pre>
	 * Timestamp 객체를 yyyyMMdd 형식의 문자열로 변환함.
	 * </pre>
	 *
	 * @param date Date 객체
	 * @return yyyyMMdd 형식의 입력 날짜
	 */
	public static String toDateString(final Timestamp date)
	{
		if (date == null || date.toString().startsWith(Constants.YEAR_1900))
		{
			return Constants.EMPTY;
		}
		synchronized (dateFormatter)
		{
			return dateFormatter.format(date);
		}
	}

	/**
	 * <pre>
	 * 현재 날짜를 HHmmss 형식의 문자열로 변환함.
	 * </pre>
	 *
	 * @return HHmmss 형식의 현재 시간
	 */
	public static String toTimeString()
	{
		synchronized (timeFormatter)
		{
			return timeFormatter.format(getSystemDate());
		}
	}

	/**
	 * <pre>
	 * Date 객체를 HHmmss 형식의 문자열로 변환함.
	 * </pre>
	 *
	 * @param date Date 객체
	 * @return HHmmss 형식의 입력 시간
	 */
	public static String toTimeString(final Date date)
	{
		if (date == null || date.toString().startsWith(Constants.YEAR_1900))
		{
			return Constants.EMPTY;
		}
		synchronized (timeFormatter)
		{
			return timeFormatter.format(date);
		}
	}

	/**
	 * <pre>
	 * Timestamp 객체를 HHmmss 형식의 문자열로 변환함.
	 * </pre>
	 *
	 * @param date Date 객체
	 * @return HHmmss 형식의 입력 시간
	 */
	public static String toTimeString(final Timestamp date)
	{
		if (date == null || date.toString().startsWith(Constants.YEAR_1900))
		{
			return Constants.EMPTY;
		}
		synchronized (timeFormatter)
		{
			return timeFormatter.format(date);
		}
	}

	/**
	 * <pre>
	 * 현재 날짜를 HHmmssSSS 형식의 문자열로 변환함.
	 * </pre>
	 * 
	 * @return
	 */
	public static String toMilliTimeString()
	{
		synchronized (militimeFormatter)
		{
			return militimeFormatter.format(getSystemDate());
		}
	}

	/**
	 * <pre>
	 * 현재 날짜를 지정 포맷 형식의 문자열로 변환함.
	 * </pre>
	 *
	 * @param format 일시 포맷
	 * @return 지정 형식의 현재 날짜+시간
	 */
	public static String toDateTimeString(String format)
	{
		DateFormat df = new SimpleDateFormat(format);
		df.setTimeZone(DateUtil.tz);
		return df.format(getSystemDate());
	}

	/**
	 * <pre>
	 * 현재 날짜를 yyyyMMddHHmmss 형식의 문자열로 변환함.
	 * </pre>
	 *
	 * @return yyyyMMddHHmmss 형식의 현재 날짜+시간
	 */
	public static String toDateTimeString()
	{
		synchronized (dateTimeFormatter)
		{
			return dateTimeFormatter.format(getSystemDate());
		}
	}

	/**
	 * <pre>
	 * Date 객체를 yyyyMMddHHmmss 형식의 문자열로 변환함.
	 * </pre>
	 * 
	 * @param date Date 객체
	 * @return yyyyMMddHHmmss 형식의 입력 날짜+시간
	 */
	public static String toDateTimeString(final Date date)
	{
		if (date == null || date.toString().startsWith(Constants.YEAR_1900))
		{
			return Constants.EMPTY;
		}
		synchronized (dateFormatter)
		{
			return dateTimeFormatter.format(date);
		}
	}

	/**
	 * <pre>
	 * Timestamp 객체를 yyyyMMddHHmmss 형식의 문자열로 변환함.
	 * </pre>
	 * 
	 * @param date Date 객체
	 * @return yyyyMMddHHmmss 형식의 입력 날짜+시간
	 */
	public static String toDateTimeString(final Timestamp date)
	{
		if (date == null || date.toString().startsWith(Constants.YEAR_1900))
		{
			return Constants.EMPTY;
		}

		synchronized (dateTimeFormatter)
		{
			return dateTimeFormatter.format(date);
		}
	}

	/**
	 * <pre>
	 * 날짜(+시간) 문자열을 Timestamp 형으로 변환함.
	 * </pre>
	 * 
	 * @param strDate 날짜(+시간) 문자열 - yyyyMMdd 형식의 문자열, 구분자가 있어도 상관없음.
	 * @return Timestamp
	 * @throws IllegalArgumentException 년월일이 없는 경우 발생
	 */
	public static Timestamp toTimestamp(final String strDate) throws IllegalArgumentException
	{
		return new Timestamp(new CalendarWrapper(strDate).getDate().getTime());
	}

	/**
	 * <pre>
	 * Date 객체를 Timestamp 형으로 변환함.
	 * </pre>
	 * 
	 * @param date Date 객체
	 * @return Timestamp
	 */
	public static Timestamp toTimestamp(final Date date)
	{
		if (date == null || date.toString().startsWith(Constants.YEAR_1900))
		{
			return null;
		}
		return new Timestamp(date.getTime());
	}

	/**
	 * <pre>
	 * 날짜 형식이 유효한지 체크한다. 구분자는 제거하고 유효 체크함.
	 * </pre>
	 * 
	 * @param strDate 날짜(+시간) 문자열 - yyyyMMdd 형식의 문자열, 구분자가 있어도 상관없음.
	 * @return 날짜 형식이 유효할 경우 TRUE
	 */
	public static boolean isValidDate(final String strDate)
	{
		if (strDate == null)
		{
			return false;
		}

		final String tempStrDate = strDate.replaceAll("\\D", Constants.EMPTY); // 숫자를 제외한 모든 문자를 제거한다.
		try
		{
			final CalendarWrapper calendar = new CalendarWrapper(tempStrDate);

			final int len = tempStrDate.length();
			if (len >= Constants.HAS_YEAR_MIN_LEN
					&& calendar.getYear() != Integer.parseInt(tempStrDate.substring(0, 4)))
			{
				return false;
			}
			if (len >= Constants.HAS_MONTH_MIN_LEN
					&& calendar.getMonth() != Integer.parseInt(tempStrDate.substring(4, 6)))
			{
				return false;
			}
			if (len >= Constants.HAS_DAY_MIN_LEN && calendar.getDay() != Integer.parseInt(tempStrDate.substring(6, 8)))
			{
				return false;
			}
			if (len >= Constants.HAS_HOUR_MIN_LEN
					&& calendar.getHour() != Integer.parseInt(tempStrDate.substring(8, 10)))
			{
				return false;
			}
			if (len >= Constants.HAS_MIN_MIN_LEN
					&& calendar.getMinute() != Integer.parseInt(tempStrDate.substring(10, 12)))
			{
				return false;
			}
			if (len >= Constants.HAS_SEC_MIN_LEN
					&& calendar.getSecond() != Integer.parseInt(tempStrDate.substring(12, 14)))
			{
				return false;
			}
			return !(len >= Constants.HAS_MLS_MIN_LEN && calendar.getMilliSecond() != Integer.parseInt(tempStrDate
					.substring(14, 17)));

		} catch (IllegalArgumentException e)
		{
			return false;
		}
	}

	/**
	 * <pre>
	 * 입력된 연도가 윤년인지를 판단함.
	 * 
	 *  사용 예)
	 *  DateUtilPO.isLeapYear(2006)  =&gt; false (윤년이 아님)
	 * </pre>
	 *
	 * @param year 판단할 연도, int 값 (예: 2000)
	 * @return 윤년일 경우 <code>true</code>
	 */
	public static boolean isLeapYear(final int year)
	{
		if ( (year % 4 == 0 &&  year % 100 != 0) || year % 400 == 0 ) return true;
		else return false;
	}

	public static int getLeapYearCount(int year)
	  {
	    if ((year % 400 == 0) || ((year % 4 == 0) && (year % 100 != 0))) return 1;

	    return 0;
	  }
	
	/**
	 * <pre>
	 * 입력된 연도가 윤년인지를 판단함.
	 * </pre>
	 * 
	 * 사용 예) DateUtilPO.isLeapYear(&quot;2006&quot;) =&gt; false (윤년이 아님) DateUtilPO.isLeapYear(&quot;XXXX&quot;) =&gt;
	 * NumberFormatException 발생. </pre>
	 *
	 * @param strYear 판단할 연도, 4자리 String (예: "2000")
	 * @return 윤년일 경우 <code>true</code>
	 */
	public static boolean isLeapYear(final String strYear)
	{
		return isLeapYear(Integer.parseInt(strYear));
	}

	/**
	 * <pre>
	 * 입력일자에 입력 년, 월, 일 이전의 날짜를 구함.
	 * </pre>
	 * 
	 * @param date Date 객체
	 * @param year 년수
	 * @param month 개월수
	 * @param day 일수
	 * @return 입력일자에 년, 월, 일이 각각 빠진 Date형
	 */
	public static Date getBeforeDate(final Date date, final int year, final int month, final int day)
	{
		return new CalendarWrapper(date).beforeDate(year, month, day);
	}

	/**
	 * <pre>
	 * 입력일자에 입력 년, 월, 일 이전의 날짜를 구함.
	 * 
	 *  사용 예)
	 *  DateUtilPO.getBeforeDate(&quot;20060523&quot;, 1, 1, 1) =&gt; &quot;20050422&quot; 값을 가지는 Date 객체
	 * </pre>
	 *
	 * @param stdDate yyyyMMdd 형식의 문자열, 구분자가 있어도 상관없음.
	 * @param year 년수
	 * @param month 개월수
	 * @param day 일수
	 * @return 입력일자에서 년, 월, 일이 각각 빠진 Date형
	 * @throws IllegalArgumentException 입력일자가 날짜 형식이 아닐 경우에 발생
	 */
	public static Date getBeforeDate(final String stdDate, final int year, final int month, final int day)
			throws IllegalArgumentException
	{
		return new CalendarWrapper(stdDate).beforeDate(year, month, day);
	}

	/**
	 * <pre>
	 * 입력일자에 입력 년, 월, 일 이전의 날짜를 문자열로 구함.
	 * 
	 *  사용 예)
	 *  DateUtilPO.getBeforeDateString(&quot;20060523&quot;, 1, 1, 1) =&gt; &quot;20050422&quot;
	 * </pre>
	 *
	 * @param stdDate yyyyMMdd 형식의 문자열, 구분자가 있어도 상관없음.
	 * @param year 년수
	 * @param month 개월수
	 * @param day 일수
	 * @return yyyyMMdd 형식의 문자열
	 * @throws IllegalArgumentException 입력일자가 날짜 형식이 아닐 경우에 발생
	 */
	public static String getBeforeDateString(final String stdDate, final int year, final int month, final int day)
			throws IllegalArgumentException
	{
		return new CalendarWrapper(new CalendarWrapper(stdDate).beforeDate(year, month, day)).getDateString();
	}

	/**
	 * <pre>
	 * 입력일자에 입력 년, 월, 일, 시, 분, 초 이전의 날짜를 구함.
	 * 
	 *  사용 예)
	 *  DateUtilPO.getBeforeDateString(&quot;20060523123030&quot;, 1, 1, 1, 1, 1, 1) =&gt; &quot;20050422112929&quot; 값을 가지는 Date 객체
	 * </pre>
	 *
	 * @param stdDate yyyyMMddHHmmss 형식의 문자열, 구분자가 있어도 상관없음.
	 * @param year 년수
	 * @param month 개월수
	 * @param day 일수
	 * @param hour 시
	 * @param min 분
	 * @param sec 초
	 * @return 입력일자에서 년, 월, 일, 시, 분, 초가 각각 빠진 Date형
	 * @throws IllegalArgumentException 입력일자가 날짜 형식이 아닐 경우에 발생
	 */
	public static Date getBeforeDate(final String stdDate, final int year, final int month, final int day,
			final int hour, final int min, final int sec) throws IllegalArgumentException
	{
		return new CalendarWrapper(stdDate).beforeDate(year, month, day, hour, min, sec);
	}

	/**
	 * <pre>
	 * 입력일자에 입력 년, 월, 일, 시, 분, 초 이전의 날짜를 구함.
	 * 
	 *  사용 예)
	 *  DateUtilPO.getBeforeDateString(&quot;20060523123030&quot;, 1, 1, 1, 1, 1, 1) =&gt; &quot;20050422112929&quot;
	 * </pre>
	 *
	 * @param stdDate yyyyMMddHHmmss 형식의 문자열, 구분자가 있어도 상관없음.
	 * @param year 년수
	 * @param month 개월수
	 * @param day 일수
	 * @param hour 시
	 * @param min 분
	 * @param sec 초
	 * @return yyyyMMddHHmmss 형식의 문자열
	 * @throws IllegalArgumentException 입력일자가 날짜 형식이 아닐 경우에 발생
	 */
	public static String getBeforeDateString(final String stdDate, final int year, final int month, final int day,
			final int hour, final int min, final int sec) throws IllegalArgumentException
	{
		return new CalendarWrapper(new CalendarWrapper(stdDate).beforeDate(year, month, day, hour, min, sec))
				.getDateTimeString();
	}

	/**
	 * <pre>
	 * 입력일자에 입력 년, 월, 일 이후의 날짜를 구함.
	 * </pre>
	 * 
	 * @param date Date 객체
	 * @param year 년수
	 * @param month 개월수
	 * @param day 일수
	 * @return 입력일자에 년, 월, 일이 각각 더해진 Date형
	 */
	public static Date getAfterDate(final Date date, final int year, final int month, final int day)
	{
		return new CalendarWrapper(date).afterDate(year, month, day);
	}

	/**
	 * <pre>
	 * 입력일자에 입력 년, 월, 일 이후의 날짜를 구함.
	 * 
	 *  사용 예)
	 *  DateUtilPO.getAfterDate(&quot;20060523&quot;, 1, 1, 1) =&gt; &quot;20070624&quot; 값을 가지는 Date 객체
	 *  DateUtilPO.getAfterDate(&quot;20060523&quot;, -1, -1, -1) =&gt; &quot;20050422&quot; 값을 가지는 Date 객체
	 * </pre>
	 *
	 * @param stdDate yyyyMMdd 형식의 문자열, 구분자가 있어도 상관없음.
	 * @param year 년수
	 * @param month 개월수
	 * @param day 일수
	 * @return 입력일자에 년, 월, 일이 각각 더해진 Date형
	 * @throws IllegalArgumentException 입력일자가 날짜 형식이 아닐 경우에 발생
	 */
	public static Date getAfterDate(final String stdDate, final int year, final int month, final int day)
			throws IllegalArgumentException
	{
		return new CalendarWrapper(stdDate).afterDate(year, month, day);
	}

	/**
	 * <pre>
	 * 입력일자에 입력 년, 월, 일 이후의 날짜를 문자열로 구함.
	 * 
	 *  사용 예)
	 *  DateUtilPO.getAfterDateString(&quot;20060523&quot;, 1, 1, 1) =&gt; &quot;20070624&quot;
	 *  DateUtilPO.getAfterDateString(&quot;20060523&quot;, -1, -1, -1) =&gt; &quot;20050422&quot;
	 * </pre>
	 *
	 * @param stdDate yyyyMMdd 형식의 문자열, 구분자가 있어도 상관없음.
	 * @param year 년수
	 * @param month 개월수
	 * @param day 일수
	 * @return yyyyMMdd 형식의 문자열
	 * @throws IllegalArgumentException 입력일자가 날짜 형식이 아닐 경우에 발생
	 */
	public static String getAfterDateString(final String stdDate, final int year, final int month, final int day)
			throws IllegalArgumentException
	{
		return new CalendarWrapper(new CalendarWrapper(stdDate).afterDate(year, month, day)).getDateString();
	}

	/**
	 * <pre>
	 * 입력일자에 입력 년, 월, 일, 시, 분, 초 이후의 날짜를 구함.
	 * 
	 *  사용 예)
	 *  DateUtilPO.getAfterDateString(&quot;20060523123030&quot;, 1, 1, 1, 1, 1, 1) =&gt; &quot;20070624133131&quot; 값을 가지는 Date 객체
	 *  DateUtilPO.getAfterDateString(&quot;20060523123030&quot;, -1, -1, -1, -1, -1, -1) =&gt; &quot;20050422112929&quot; 값을 가지는 Date 객체
	 * </pre>
	 *
	 * @param stdDate yyyyMMdd 형식의 문자열, 구분자가 있어도 상관없음.
	 * @param year 년수
	 * @param month 개월수
	 * @param day 일수
	 * @param hour 시
	 * @param min 분
	 * @param sec 초
	 * @return 입력일자에 년, 월, 일, 시, 분, 초가 각각 더해진 Date형
	 * @throws IllegalArgumentException 입력일자가 날짜 형식이 아닐 경우에 발생
	 */
	public static Date getAfterDate(final String stdDate, final int year, final int month, final int day,
			final int hour, final int min, final int sec) throws IllegalArgumentException
	{
		return new CalendarWrapper(stdDate).afterDate(year, month, day, hour, min, sec);
	}

	/**
	 * <pre>
	 * 입력일자에 입력 년, 월, 일, 시, 분, 초 이후의 날짜를 구함.
	 * 
	 *  사용 예)
	 *  DateUtilPO.getAfterDateString(&quot;20060523123030&quot;, 1, 1, 1, 1, 1, 1) =&gt; &quot;20070624133131&quot;
	 *  DateUtilPO.getAfterDateString(&quot;20060523123030&quot;, -1, -1, -1, -1, -1, -1) =&gt; &quot;20050422112929&quot;
	 * </pre>
	 *
	 * @param stdDate yyyyMMddHHmmss 형식의 문자열, 구분자가 있어도 상관없음.
	 * @param year 년수
	 * @param month 개월수
	 * @param day 일수
	 * @param hour 시
	 * @param min 분
	 * @param sec 초
	 * @return yyyyMMddHHmmss 형식의 문자열
	 * @throws IllegalArgumentException 입력일자가 날짜 형식이 아닐 경우에 발생
	 */
	public static String getAfterDateString(final String stdDate, final int year, final int month, final int day,
			final int hour, final int min, final int sec) throws IllegalArgumentException
	{
		return new CalendarWrapper(new CalendarWrapper(stdDate).afterDate(year, month, day, hour, min, sec))
				.getDateTimeString();
	}

	/*
	 * public static long getDaysDiffAbove(String beginDate, String endDate) throws IllegalArgumentException return
	 * getDaysDiff(beginDate, endDate) +1 ; }
	 */

	/**
	 * <pre>
	 * 일자간의 일수 차이를 구한다.
	 * </pre>
	 * 
	 * @param beginDate yyyyMMdd 형식의 문자열, 구분자가 있어도 상관없음.
	 * @param endDate yyyyMMdd 형식의 문자열, 구분자가 있어도 상관없음.
	 * @return 두 일자간의 일수 차이
	 * @throws IllegalArgumentException 입력값이 날짜 형식이 아닐 경우에 발생
	 */
	public static long getDaysDiff(final String beginDate, final String endDate) throws IllegalArgumentException
	{
		// return proframe.util.DateUtil.getPeriodBetween(beginDate,
		// endDate); //두번째 인자가 작으면 0을 리턴함.

		return getDaysDiff(toDate(beginDate), toDate(endDate));
	}

	/**
	 * <pre>
	 * 일자간의 일수 차이를 구한다.
	 * 24시간 이내일 경우, 0
	 * </pre>
	 *
	 * @param beginDate Date beginDate
	 * @param endDate Date beginDate
	 * @return 두 일자간의 일수 차이
	 */
	private static long getDaysDiff(final Date beginDate, final Date endDate)
	{
		if (endDate.before(beginDate))
		{
			return -doGetDaysDiff(endDate, beginDate);
		} else
		{
			return doGetDaysDiff(beginDate, endDate);
		}
	}

	/**
	 * <pre>
	 * 일자간의 일수 차이를 구한다.
	 * 
	 * 24시간 미만 -> 0
	 * 48시간 미만 -> 1
	 * </pre>
	 *
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	private static long doGetDaysDiff(final Date beginDate, final Date endDate)
	{
		final long diff = endDate.getTime() - beginDate.getTime();
		return (long) diff / (24 * 3600 * 1000);
	}

	/**
	 * <pre>
	 * 일자간의 개월수 차이를 구한다.
	 * </pre>
	 * 
	 * @param beginDate yyyyMMdd 형식의 문자열, 구분자가 있어도 상관없음.
	 * @param endDate yyyyMMdd 형식의 문자열, 구분자가 있어도 상관없음.
	 * @return 두 일자간의 개월수 차이, 첫번째 인자가 큰 경우 음수
	 * @throws IllegalArgumentException 입력값이 날짜 형식이 아닐 경우에 발생
	 */
	public static int getMonthsDiff(final String beginDate, final String endDate) throws IllegalArgumentException
	{
		return getMonthsDiff(toDate(beginDate), toDate(endDate));
	}

	/**
	 * <pre>
	 * 일자간의 개월수 차이를 구한다.
	 * </pre>
	 * 
	 * @param beginDate 시작일
	 * @param endDate 종료일
	 * @return 두 일자간의 개월수 차이, 첫번째 인자가 큰 경우 음수
	 */
	public static int getMonthsDiff(final Date beginDate, final Date endDate)
	{
		if (endDate.before(beginDate))
		{
			return -doGetMonthsDiff(endDate, beginDate);
		} else
		{
			return doGetMonthsDiff(beginDate, endDate);
		}
	}

	/**
	 * <pre>
	 * 일자간의 개월 수 차이를 구한다.
	 * </pre>
	 * 
	 * @param beginDate 시작일
	 * @param endDate 종료일
	 * @return 두 일자간의 개월수 차이, 첫번째 인자가 큰 경우 음수
	 */
	private static int doGetMonthsDiff(final Date beginDate, final Date endDate)
	{
		final CalendarWrapper begin = new CalendarWrapper(beginDate);
		final CalendarWrapper end = new CalendarWrapper(endDate);

		int diffMonth = (end.getYear() - begin.getYear()) * 12 + (end.getMonth() - begin.getMonth());
		final int diffDays = end.getDay() - begin.getDay();
		if (diffMonth >= 0)
		{
			if (diffDays < 0)
			{
				diffMonth--;
			}
		} else
		{
			if (diffDays > 0)
			{
				diffMonth++;
			}
		}
		return diffMonth;
	}

	/**
	 * <pre>
	 * 일자간의 년수 차이를 구한다.
	 * </pre>
	 * 
	 * @param beginDate yyyyMMdd 형식의 문자열, 구분자가 있어도 상관없음.
	 * @param endDate yyyyMMdd 형식의 문자열, 구분자가 있어도 상관없음.
	 * @return 두 일자간의 년수 차이, 첫번째 인자가 큰 경우 음수
	 * @throws IllegalArgumentException 입력값이 날짜 형식이 아닐 경우에 발생
	 */
	public static int getYearsDiff(final String beginDate, final String endDate) throws IllegalArgumentException
	{
		return getYearsDiff(toDate(beginDate), toDate(endDate));
	}

	/**
	 * <pre>
	 * 일자간의 년수 차이를 구한다.
	 * </pre>
	 *
	 * @param beginDate 시작일
	 * @param endDate 종료일
	 * @return 두 일자간의 년수 차이, 첫번째 인자가 큰 경우 음수
	 */
	public static int getYearsDiff(final Date beginDate, final Date endDate)
	{
		if (endDate.after(beginDate))
		{
			return doGetYearsDiff(beginDate, endDate);
		} else
		{
			return -doGetYearsDiff(endDate, beginDate);
		}
	}

	/**
	 * <pre>
	 * 일자간의 년수 차이를 구한다.
	 * </pre>
	 *
	 * @param beginDate 시작일
	 * @param endDate 종료일
	 * @return 두 일자간의 년수 차이, 첫번째 인자가 큰 경우 음수
	 */
	private static int doGetYearsDiff(final Date beginDate, final Date endDate)
	{
		return getMonthsDiff(beginDate, endDate) / 12;
	}

	/**
	 * <pre>
	 * 일자간의 일수 차이를 구한다. 1일을 넘어서면 2일로 계산한다.
	 * </pre>
	 * 
	 * @param beginDate yyyyMMdd 형식의 문자열, 구분자가 있어도 상관없음.
	 * @param endDate yyyyMMdd 형식의 문자열, 구분자가 있어도 상관없음.
	 * @return 두 일자간의 일수 차이
	 * @throws IllegalArgumentException 입력값이 날짜 형식이 아닐 경우에 발생
	 */
	public static long getDaysDiffAbove(final String beginDate, final String endDate) throws IllegalArgumentException
	{
		return getDaysDiffAbove(toDate(beginDate), toDate(endDate));
	}

	/**
	 * <pre>
	 * 일자간의 일수 차이를 구한다.
	 * 1일을 넘어서면 2일로 계산한다.
	 * 밀리초의 올림 연산을 수행한다.
	 * </pre>
	 * 
	 * @param beginDate Date beginDate
	 * @param endDate Date beginDate
	 * @return 두 일자간의 일수 차이
	 */
	public static long getDaysDiffAbove(final Date beginDate, final Date endDate)
	{
		if (endDate.before(beginDate))
		{
			return -doGetDaysDiffAbove(endDate, beginDate);
		} else
		{
			return doGetDaysDiffAbove(beginDate, endDate);
		}
	}

	/**
	 * <pre>
	 * 일자간의 일수 차이를 구한다.
	 * 1일을 넘어서면 2일로 계산한다.
	 * 밀리초의 올림 연산을 수행한다.
	 * </pre>
	 * 
	 * @param beginDate Date beginDate
	 * @param endDate Date beginDate
	 * @return 두 일자간의 일수 차이
	 */
	private static long doGetDaysDiffAbove(final Date beginDate, final Date endDate)
	{
		final long diff = endDate.getTime() - beginDate.getTime();
		final double dayDiff = diff / (24 * 3600 * 1000.0);
		return (long) Math.ceil(dayDiff);
	}

	/**
	 * <pre>
	 * 일자간의 개월수 차이를 구한다. 1개월을 넘어서면 2개월로 계산한다.
	 * </pre>
	 * 
	 * @param beginDate yyyyMMdd 형식의 문자열, 구분자가 있어도 상관없음.
	 * @param endDate yyyyMMdd 형식의 문자열, 구분자가 있어도 상관없음.
	 * @return 두 일자간의 개월수 차이, 첫번째 인자가 큰 경우 음수
	 * @throws IllegalArgumentException 입력값이 날짜 형식이 아닐 경우에 발생
	 */
	public static int getMonthsDiffAbove(final String beginDate, final String endDate) throws IllegalArgumentException
	{
		return getMonthsDiffAbove(toDate(beginDate), toDate(endDate));
	}

	/**
	 * <pre>
	 * 일자간의 개월수 차이를 구한다. 1개월을 넘어서면 2개월로 계산한다.
	 * </pre>
	 * 
	 * @param beginDate Date beginDate
	 * @param endDate Date beginDate
	 * @return 두 일자간의 개월수 차이, 첫번째 인자가 큰 경우 음수
	 */
	public static int getMonthsDiffAbove(final Date beginDate, final Date endDate)
	{
		if (endDate.before(beginDate))
		{
			return -doGetMonthsDiffAbove(endDate, beginDate);
		} else
		{
			return doGetMonthsDiffAbove(beginDate, endDate);
		}
	}

	/**
	 * <pre>
	 * 일자간의 개월수 차이를 구한다. 1개월을 넘어서면 2개월로 계산한다.
	 * </pre>
	 * 
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	private static int doGetMonthsDiffAbove(final Date beginDate, final Date endDate)
	{
		final CalendarWrapper begin = new CalendarWrapper(beginDate);
		final CalendarWrapper end = new CalendarWrapper(endDate);

		final int years = end.getYear() - begin.getYear();
		final int months = end.getMonth() - begin.getMonth();
		final int days = end.getDay() - begin.getDay();

		if (years == 0)
		{
			if (days == 0)
			{
				return months;
			} else
			{
				return (months + (days > 0 ? 1 : 0));
			}
		} else if (years > 0)
		{

			if (days == 0)
			{
				return years * 12 + months;
			} else
			{
				return (years * 12 + months + (days < 0 ? 0 : 1));
			}
		} else
		{
			return (years * 12 + months + (days > 0 ? 1 : 0));
		}
	}

	/**
	 * <pre>
	 * 일자간의 년수 차이를 구한다. 1년을 넘어서면 2년으로 계산한다.
	 * </pre>
	 * 
	 * @param beginDate yyyyMMdd 형식의 문자열, 구분자가 있어도 상관없음.
	 * @param endDate yyyyMMdd 형식의 문자열, 구분자가 있어도 상관없음.
	 * @return 두 일자간의 년수 차이, 첫번째 인자가 큰 경우 음수
	 * @throws IllegalArgumentException 입력값이 날짜 형식이 아닐 경우에 발생
	 */
	public static int getYearsDiffAbove(final String beginDate, final String endDate) throws IllegalArgumentException
	{
		return getYearsDiffAbove(toDate(beginDate), toDate(endDate));
	}

	/**
	 * <pre>
	 * 일자간의 년수 차이를 구한다. 1년을 넘어서면 2년으로 계산한다.
	 * </pre>
	 * 
	 * @param beginDate Date beginDate
	 * @param endDate Date beginDate
	 * @return 두 일자간의 년수 차이, 첫번째 인자가 큰 경우 음수
	 */
	public static int getYearsDiffAbove(final Date beginDate, final Date endDate)
	{
		if (endDate.before(beginDate))
		{
			return -doGetYearsDiffAbove(endDate, beginDate);
		} else
		{
			return doGetYearsDiffAbove(beginDate, endDate);
		}
	}

	/**
	 * <pre>
	 * 일자간의 년수 차이를 구한다. 1년을 넘어서면 2년으로 계산한다.
	 * </pre>
	 * 
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	private static int doGetYearsDiffAbove(final Date beginDate, final Date endDate)
	{

		final int months = getMonthsDiffAbove(beginDate, endDate);
		return (int) Math.ceil(months / 12.);
	}

	/**
	 * <pre>
	 * 양력을 음력으로 바꾼다. 바뀐 음력이 윤달인 경우 날짜 뒤에 "(윤달)" 문자열이 포함되므로 사용시 주의할 것.
	 * 
	 *  사용 예)
	 *  DateUtilPO.toLunarDate(&quot;20040311&quot;)  =&gt; &quot;20040221&quot;
	 *  DateUtilPO.toLunarDate(&quot;20040410&quot;)  =&gt; &quot;20040221(윤달)&quot;  - 위와 동일한 음력날짜이지만 윤달이 표시됨.
	 * </pre>
	 *
	 * @param solarDate 양력 문자열 - yyyyMMdd 형식의 문자열, 구분자가 있어도 상관없음.
	 * @return yyyyMMdd 형식의 문자열
	 * @throws IllegalArgumentException 입력값이 날짜 형식이 아닐 경우에 발생
	 */
	public static String toLunarDate(final String solarDate) throws IllegalArgumentException
	{
		final LunarCalendar lunarCal = new LunarCalendar();
		return lunarCal.solar2lunar(solarDate) + (lunarCal.isLeapMonth() ? "(윤달)" : Constants.EMPTY);
	}

	/**
	 * <pre>
	 * 음력을 양력으로 바꾼다.
	 * </pre>
	 *
	 * @param lunarDate 양력 문자열 - yyyyMMdd 형식의 문자열, 구분자가 있어도 상관없음.
	 * @param leapMonth 평달일 경우 false, 윤달인 경우 true
	 * @return yyyyMMdd 형식의 문자열
	 * @throws IllegalArgumentException 입력값이 날짜 형식이 아닐 경우에 발생
	 */
	public static String toSolarDate(final String lunarDate, final boolean leapMonth) throws IllegalArgumentException
	{
		final LunarCalendar lunarCal = new LunarCalendar();
		return lunarCal.lunar2solar(lunarDate, leapMonth);
	}

	/**
	 * <pre>
	 * 입력일자의 분기를 구한다.
	 * </pre>
	 * 
	 * @param strDate yyyyMMdd 형식의 문자열, 구분자가 있어도 상관없음.
	 * @return 입력일자의 분기(int)
	 */
	public static int getQuater(final String strDate)
	{

		final String[] parsedDateArray = parseDate(strDate);
		if (parsedDateArray == null || parsedDateArray.length == 0)
		{
			return -1;
		}

		final int month = Integer.parseInt(parsedDateArray[1]);
		return (month - 1) / 3 + 1;
	}

	/**
	 * <pre>
	 * 일자간의 차이를 년월일로 가져온다. 인자의 순서에 상관없이 큰 날짜에서 작은 날짜의 차이를 계산한다.
	 * </pre>
	 * 
	 * @param beginDate yyyyMMdd 형식의 문자열
	 * @param endDate yyyyMMdd 형식의 문자열
	 * @return 일자간의 차이
	 * @throws IllegalArgumentException
	 */
	public static int[] getDifference(final String beginDate, final String endDate) throws IllegalArgumentException
	{

		if (!(isValidDate(beginDate) && isValidDate(endDate)))
		{
			throw new IllegalArgumentException(Constants.DATE_FORMAT_ERROR + endDate);
		}

		return getDifference(toDate(beginDate), toDate(endDate));
	}

	/**
	 * <pre>
	 * 일자간의 차이를 년월일로 가져온다. 인자의 순서에 상관없이 큰 날짜에서 작은 날짜의 차이를 계산한다.
	 * </pre>
	 * 
	 * @param beginDate Date 객체
	 * @param endDate Date 객체
	 * @return 일자간의 차이
	 * @throws IllegalArgumentException
	 */
	public static int[] getDifference(final Date beginDate, final Date endDate) throws IllegalArgumentException
	{

		CalendarWrapper begin = new CalendarWrapper(beginDate);
		CalendarWrapper end = new CalendarWrapper(endDate);
		if (endDate.before(beginDate))
		{
			begin = new CalendarWrapper(endDate);
			end = new CalendarWrapper(beginDate);
		} else
		{
			begin = new CalendarWrapper(beginDate);
			end = new CalendarWrapper(endDate);
		}

		int months = Math.abs(getMonthsDiff(beginDate, endDate));
		final int years = months / 12;
		months = months % 12;
		int days = 0;

		if (begin.getDay() > end.getDay())
		{
			days = (new CalendarWrapper(end.beforeMonth(1)).getLastDayOfMonth()) - begin.getDay() + end.getDay();
		} else
		{
			days = end.getDay() - begin.getDay();
		}

		return new int[] { years, months, days };
	}

	/**
	 * <pre>
	 * 시스템 날짜를 가져옴.
	 * </pre>
	 *
	 * @return Date
	 */
	private static Date getSystemDate()
	{
		return new Date(System.currentTimeMillis());
	}

	/**
	 * <pre>
	 * 날짜를 년,월,일,시로 분할하여 문자열 배열로 반환한다.
	 * </pre>
	 * 
	 * @param strDate
	 * @return
	 */
	private static String[] parseDate(final String strDate)
	{
		if (strDate == null)
		{
			return new String[0];
		}

		final String tempStrDate = strDate.replaceAll("^\\D+$", Constants.EMPTY);
		final int len = tempStrDate.length();
		if (len >= Constants.HAS_HOUR_MIN_LEN)
		{
			return new String[] { tempStrDate.substring(0, 4), tempStrDate.substring(4, 6),
					tempStrDate.substring(6, 8), tempStrDate.substring(8, 10) };
		} else if (len >= Constants.HAS_DAY_MIN_LEN)
		{
			return new String[] { tempStrDate.substring(0, 4), tempStrDate.substring(4, 6),
					tempStrDate.substring(6, 8), "00" };
		} else if (len >= Constants.HAS_MONTH_MIN_LEN)
		{
			return new String[] { tempStrDate.substring(0, 4), tempStrDate.substring(4, 6), "00", "00" };
		} else if (len >= Constants.HAS_YEAR_MIN_LEN)
		{
			return new String[] { tempStrDate.substring(0, 4), "00", "00", "00" };
		}
		return new String[0];
	}
	
	
	/**
	 * <pre>
	 * 현재 날짜를 아래와 같은 형식으로 반환함
	 *0 : 2016-08-22
	 *1 : 2016/08/22
	 *2 : 20160822
	 *3 : 2016년 08월 22일
	 *4 : 2016.08.22
	 *5 : 2016 08 22
	 *
	 *	사용 예) DateUtil.getCurrentDate(2) => 20160822 (오늘 날짜가 20160822일 경우)
	 * </pre>
	 * @param format int 헝식으로 입력(0~5)
	 * @return String 형식의 날짜
	 */
	public static String getCurrentDate(int format)
	{
	    if ((format >= dateFormatterList.length) || (format < 0)) {
	      return null;
	    }
	    SimpleDateFormat simpledateformat = new SimpleDateFormat(dateFormatterList[format]);
	    Calendar calendar = Calendar.getInstance();
	    simpledateformat.setCalendar(calendar);
	    String s = simpledateformat.format(simpledateformat.getCalendar().getTime());
	    return s;
	}
	  
	
	/**
	 * <pre>
	 * 	기준일로 부터 입력값 이전 일을 계산하여 리턴
	 * </pre>
	 * @param before int 형이며 몇일 이전인지 나타내는 정수
	 * @param date String 형식의 기준일
	 * @return
	 */
	public static String getDateDaysBefore(int before, String date)
	{
	    return getBeforeDateString(date,0,0,before);
	}
	/**
	 * <pre>
	 * 입력일자에 입력 월 이후 의 월의 마지막 일자를 구함.
	 * 
	 *  사용 예)
	 *  DateUtilPO.getAftMonthLastDate(&quot;20060523&quot;, 1) =&gt; &quot;20050422&quot; 값을 가지는 Date 객체
	 * </pre>
	 * @param stdDate yyyyMMdd 형식의 문자열, 구분자가 있어도 상관없음.
	 * @param month 개월수
	 * @return 입력일자에서 년, 월, 일이 각각 더한 마지막 일자 Date형
	 * @throws IllegalArgumentException 입력일자가 날짜 형식이 아닐 경우에 발생
	 */
	public static String getAftMonthLastDay(final Date date,final int month)
			throws IllegalArgumentException
	{ 
		return toDateString(getBeforeDate(ITLStringUTIL.subStr(toDateString(DateUtil.getAfterDate(date,0,month + 1,0)),0,6) + "01",0,0,1));
	}
	/**
	 * <pre>
	 * 입력일자에 입력 월 이후 의 월의 마지막 일자를 구함.
	 * 
	 *  사용 예)
	 *  DateUtilPO.getAftMonthLastDate(&quot;20060523&quot;,1) =&gt; &quot;20050422&quot; 값을 가지는 Date 객체
	 * </pre>
	 * @param stdDate yyyyMMdd 형식의 문자열, 구분자가 있어도 상관없음.
	 * @param month 개월수
	 * @return 입력일자에서 년, 월, 일이 각각 더한 마지막 일자 Date형
	 * @throws IllegalArgumentException 입력일자가 날짜 형식이 아닐 경우에 발생
	 */
	public static String getAftMonthLastDay(final String date, int month)
			throws IllegalArgumentException
	{ 
		return toDateString(getBeforeDate(ITLStringUTIL.subStr(toDateString(DateUtil.getAfterDate(date,0,month + 1,0)),0,6) + "01",0,0,1));
	}
	
	/**
	 * 시작일자, 종료일자 간 개월수 차이
	 * @param fromDateYmd
	 * @param toDateYmd
	 * @return
	 */
	public static String getMonthGap(final String fromDateYmd, final String toDateYmd)
	{
		int tmpYear = 0; 		//계산된 년도
		int tmpMonth = 0; 		//계산된 개월수
		String diffMonth = "0"; 	//반환할 개월수

		if(Integer.parseInt(fromDateYmd) <= Integer.parseInt(toDateYmd))
		{
			tmpYear = Integer.parseInt(toDateYmd.substring(0, 4)) - Integer.parseInt(fromDateYmd.substring(0, 4));
			tmpMonth = Integer.parseInt(toDateYmd.substring(4, 6)) - Integer.parseInt(fromDateYmd.substring(4, 6));
			
			diffMonth = String.valueOf((12 * tmpYear) + tmpMonth);
		}
		else
		{
			diffMonth = "0";
		}
		return diffMonth;
	}
}
