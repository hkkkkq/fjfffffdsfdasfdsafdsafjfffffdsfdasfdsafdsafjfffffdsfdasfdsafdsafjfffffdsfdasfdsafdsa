package com.henc.cdrs.common.util;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.stream.Collectors;

import org.apache.commons.lang3.time.DateUtils;

/**
 * 날짜 및 시간과 관련된 유틸리티를 제공한다.
 *
 *  참조 : java.time 패키지에서는 1월을 1로 표현하여 월의 범위가 1~12가 되었으며, 요일은 월요일부터 1로 표현하도록 변경되었습니다.
 *  UTF-8 BOM Issue 확인 필요
 *
 * @author YongSang
 */
public class DateTimeUtils {

    /**
     * <p>특정일을 기준으로 담음 주차를 계산한다.
     * 기준일 <code>cal</code> 매개변수는 일요일이 첫 주의
     * 시작이므로 일요일의 날짜를 넣어야 PC-OFF관리 솔루션에 만족한다.</p>
     *
     * <p>만약 2018-09-02일을 기준으로 2주탄력 근무제의 주차를 구하고자 한다면
     * 기준일은 2018-09-02되고 <code>amount</code>는 1일된다
     * (9/2일 주와 그 다음주를 구해야 하므로 2주가 아닌 1주만 계산되면 되기 때문).</p>
     *
     * @param cal 기준일
     * @param amount 추가할 주, 음수일 수 있다.
     * @return 기준일의 주를 포함하는 다음주차
     */
    public static int[] getFirstAndLastWeekOfYear(Calendar cal, int amount) {
        if(cal == null) {
            throw  new IllegalArgumentException("The cal must not be null");
        }

        int[] firstAndLastWeekOfYear = new int[2];
        Date lastDate = DateUtils.addWeeks(DateUtils.addDays(cal.getTime(), -2), amount);
        if (amount > 0) {
            firstAndLastWeekOfYear[0] = cal.get(Calendar.WEEK_OF_YEAR);
            firstAndLastWeekOfYear[1] = DateUtils.toCalendar(lastDate).get(Calendar.WEEK_OF_YEAR);
        } else {
            firstAndLastWeekOfYear[0] = DateUtils.toCalendar(lastDate).get(Calendar.WEEK_OF_YEAR);
            firstAndLastWeekOfYear[1] = cal.get(Calendar.WEEK_OF_YEAR);
        }
        return firstAndLastWeekOfYear;
    }

    public static String getFirstAndLastWeekOfYearJoining(Date date, int amount) {
        return getFirstAndLastWeekOfYearJoining(DateUtils.toCalendar(date), amount);
    }

    public static String getFirstAndLastWeekOfYearJoining(Calendar cal, int amount) {
        int[] firstAndLastWeekOfYear = getFirstAndLastWeekOfYear(cal, amount);
        return Arrays.stream(firstAndLastWeekOfYear).mapToObj(String::valueOf).collect(Collectors.joining());
    }

    public static int getWeekRound(Date date, int amount) {
        return Integer.parseInt(DateUtils.toCalendar(date).get(Calendar.YEAR)
                + DateTimeUtils.getFirstAndLastWeekOfYearJoining(date, amount));
    }

    public static Date getDayOfWeek(Date date, int value) {
        Calendar cal = DateUtils.toCalendar(date);
        cal.set(Calendar.DAY_OF_WEEK, value);
        return cal.getTime();
    }

    /**
     * 원래 주의 시작 요일은 일요일이나 한화건설의 PC-OFF관리 시스템의
     * 시작 요일은 월요일부터 시작된다. 2주탄력 근무제와 같이 주 단위의
     * 계산을 할 때 시작일이 2018년 9월 11일이라고 가정한다면 다음 2주는
     * 9월 24일이 되기 때문에 3주에 걸친 요일이 계산되어 나오므로
     * 이런 문제를 해결하고 통일된 접근 방법을 제공할 필요가 있기 때문에
     * 날짜 계산은 공통 클래스를 이용하는 것이 여러모로 편리하다.
     * @param date
     * @return
     */
    public static Date getFirstDayOfWeek(Date date) {
        Calendar cal = DateUtils.toCalendar(DateUtils.addDays(date, -1));
        cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
        return cal.getTime();
    }

    /**
     * 특정일이 포함된 주의 첫째 날을 리턴한다.
     * @param cal 특정일
     * @return 주의 첫째 날
     */
    public static int getFirstDayOfWeek(Calendar cal) {
        if(cal == null) {
            throw  new IllegalArgumentException("The cal must not be null");
        }

        Calendar c = (Calendar) cal.clone();
        c.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
        return c.get(Calendar.DAY_OF_MONTH);
    }

    public static Date addWeeks(Date date, int amount) {
        return DateUtils.addDays(date, (amount * 7));
    }

    public static int getHour(float time) {
        return (int) time;
    }

    public static int getMinute(float time) {
        return (int) (time * 60) % 60;
    }

    /**
     * LocalDate
     * JavaTime의 지정일 반환
     * 파라미터로 주어진 날짜 정보를 저장한 LocalDate 객체를 리턴한다. 결과 : 1986-11-22
     *
     * 리턴   타입    메소드(매개변수)   설명
     * int     getYear()   년
     * Month   getMonth()  Month 열거 값(JANUARY, FEBRUARY, MARCH ..)
     * int     getMonthValue()     월(1, 2, 3 ..)
     * int     getDayOfYear()  일년의 몇 번째 일
     * int     getDayOfMonth()     월의 몇 번째 일
     * DayOfWeek   getDayOfWeek()  요일(MONDAY, TUESDAY, WEDNESDAY..)
     * boolean     isLeapYear()    윤년여부
     * @param year
     * @param month
     * @param dayOfMonth
     * @return
     */
    public static LocalDate getLocalDate(int year, int month, int dayOfMonth) {
        return LocalDate.of( year, month, dayOfMonth);
    }

    /**
     *
     * @param year
     * @param month
     * @param dayOfMonth
     * @param hour
     * @param minute
     * @param second
     * @param nanoOfSecond
     * @return
     */
    public static LocalDateTime getLocalDateTime(int year, int month, int dayOfMonth, int hour, int minute, int second, int nanoOfSecond) {
        return LocalDateTime.of(year, month, dayOfMonth, hour, minute, second, nanoOfSecond);
    }

    public static ZonedDateTime getNowUTC() {
        return ZonedDateTime.now(ZoneId.of("UTC"));
    }

    /**
     *
     * 리턴객체로 ZoneId나 UTC offset 값을 확인
     * ZoneId   getZone()   ZoneId를 리턴(Asia/Seoul)
     * ZoneOffset  getOffset()     UTC와의 시차를 리턴(+09:00)
     * @param zoneId 서울인 경우 Asia/Seoul
     * @return
     */
   public static ZonedDateTime getNowZoneDateTime (String zoneId) {

        return ZonedDateTime.now(ZoneId.of(zoneId));
    }


   /**
    * 기존 Java.util.Date 타입을 java8의 LocalDate 형식으로 변경한다.
    *
    * @param someDate
    * @return
    */
   public static LocalTime convertDateToLocalTime(Date someDate) {
       //Asia/Seoul +9
       return DateTimeUtils.convertDateToLocalTime(someDate, ZoneId.systemDefault());
   }

   public static LocalTime convertDateToLocalTime(Date someDate, ZoneId zoneId) {

       //1. Convert Date -> Instant : 2016-08-19T13:46:31.981Z
       Instant instant = someDate.toInstant();

       //2. Instant + system default time zone + toLocalDate() = LocalDate : 2016-08-19
       LocalTime localTime= instant.atZone(zoneId).toLocalTime();

       return localTime;
   }

    /**
     * 기존 Java.util.Date 타입을 java8의 LocalDate 형식으로 변경한다.
     *
     * @param someDate
     * @return
     */
   public static LocalDate convertDateToLocalDate(Date someDate) {
       //Asia/Seoul +9
       return DateTimeUtils.convertDateToLocalDate(someDate, ZoneId.systemDefault());
   }

    public static LocalDate convertDateToLocalDate(Date someDate, ZoneId zoneId) {

        //1. Convert Date -> Instant : 2016-08-19T13:46:31.981Z
        Instant instant = someDate.toInstant();

        //2. Instant + system default time zone + toLocalDate() = LocalDate : 2016-08-19
        LocalDate localDate = instant.atZone(zoneId).toLocalDate();

        return localDate;
    }

    /**
     * 기존 LocalDate를 Java.util.Date 타입형식으로 변경한다.
     *
     * @param someLocalDate
     * @return
     */
    public static Date convertLocalDateToDate(LocalDate someLocalDate) {
        //Asia/Seoul +9
        return DateTimeUtils.convertLocalDateToDate(someLocalDate, ZoneId.systemDefault());
    }

    public static Date convertLocalDateToDate(LocalDate someLocalDate, ZoneId zoneId) {

        return Date.from(someLocalDate.atStartOfDay(zoneId).toInstant());
    }

    /**
     * 기존 LocalDateTime를 Java.util.Date 타입형식으로 변경한다.
     *
     * @param someLocalDateTime
     * @return
     */
    public static Date convertLocalDateTimeToDate(LocalDateTime someLocalDateTime) {

        return DateTimeUtils.convertLocalDateTimeToDate(someLocalDateTime, ZoneId.systemDefault());
    }

    public static Date convertLocalDateTimeToDate(LocalDateTime someLocalDateTime, ZoneId zoneId) {

        return Date.from(someLocalDateTime.atZone(zoneId).toInstant());
    }

    /**
     * 기존 Java.util.Date 타입을 java8의 LocalDate 형식으로 변경한다.
     *
     * @param someDate
     * @return
     */
    public static ZonedDateTime convertDateToZonedDateTime(Date someDate) {

        //Asia/Seoul +9
        ZoneId defaultZoneId = ZoneId.systemDefault();
        return DateTimeUtils.convertDateToZonedDateTime(someDate, defaultZoneId);
    }

    public static ZonedDateTime convertDateToZonedDateTime(Date someDate, ZoneId zoneId) {
        //1. Convert Date -> Instant : 2016-08-19T13:46:31.981Z
        Instant instant = someDate.toInstant();

        //3. Instant + system default time zone = ZonedDateTime: 2016-08-19T21:46:31.981+09:00[Asia/Seoul]
        ZonedDateTime zonedDateTime = instant.atZone(zoneId);

        return zonedDateTime;
    }

    /**
     * 시작시간부터 종료시간까지 시간차이를 리턴한다.
     * @param startDate
     * @param endDate
     * @return
     */
    public static long getSecondsInDuration(Date sDate, Date eDate) {

        LocalTime sTime = DateTimeUtils.convertDateToLocalTime(sDate);
        LocalTime eTime = DateTimeUtils.convertDateToLocalTime(eDate);

      return DateTimeUtils.getDuration(sTime, eTime).getSeconds();
    }

    /**
     * LocalDate, LocalTime, LocaDateTime은 until(Temporal end, TemporalUnit unit) 메소드를 사용하여 시간 차이를 계산 할 수 있고,
     * 리턴 타입은 long 이다. 두 번째 파라미터인 TemporalUnit 인터페이스의 구현체로 자바가 제공해주는 ChronoUnit을 사용하면 된다.
     *
     * 또 다른 방법으로 java.time.Duration 클래스의 between(Temporal start, Temporal end) 메소드를 사용해서도 시간 차이를 구할 수 있다.
     * 리턴 타입은 Duration 이다.
     *
     * @param sTime
     * @param eTime
     * @return
     */
    public static Duration getDuration(LocalTime sTime, LocalTime eTime) {
        //sTime.until(eTime, ChronoUnit.HOURS);
        //Other way
        Duration duration = Duration.between(sTime, eTime);
        return duration;
    }

    /**
     * 시작일자부터 종료일자까지 날짜차이를 리턴한다.
     * 전체 시간을 기준으로 계산한다.
     * @param sDate
     * @param eDate
     * @return
     */
    public static long getDaysInChronoUnit(Date sDate, Date eDate) {
        LocalDate startDate = DateTimeUtils.convertDateToLocalDate(sDate);
        LocalDate endDate = DateTimeUtils.convertDateToLocalDate(eDate);
      return DateTimeUtils.getDaysInChronoUnit(startDate, endDate);
    }

    public static long getMonthsInChronoUnit(LocalDate sDate, LocalDate eDate) {
        return ChronoUnit.MONTHS.between(sDate, eDate);
    }

    public static long getWeeksInChronoUnit(LocalDate sDate, LocalDate eDate) {
        return ChronoUnit.WEEKS.between(sDate, eDate);
    }

    public static long getDaysInChronoUnit(LocalDate sDate, LocalDate eDate) {
      return ChronoUnit.DAYS.between(sDate, eDate);
    }

    public static long getHoursInChronoUnit(LocalDate sDate, LocalDate eDate) {
        return ChronoUnit.HOURS.between(sDate, eDate);
    }

    public static long getSecondsInChronoUnit(LocalDate sDate, LocalDate eDate) {
        return ChronoUnit.SECONDS.between(sDate, eDate);
    }

    /**
     * 시작일자부터 종료일자까지 날짜차이를 리턴한다.
     * @param startDate
     * @param endDate
     * @return
     */
    public static int getDaysInPeriod(Date sDate, Date eDate) {

        LocalDate startDate = DateTimeUtils.convertDateToLocalDate(sDate);
        LocalDate endDate = DateTimeUtils.convertDateToLocalDate(eDate);

      return DateTimeUtils.getPeriod(startDate, endDate).getDays();
    }

    /**
     * 시작일자부터 종료일자까지 Period 객체를 리턴한다.
     *
     * Period 클래스의 getYears(), getMonths(), getDays() 메소드를 사용해서 년도 차이, 월의 차이, 일의 차이를 계산 할 수 있다.
     *
     * @param sDate
     * @param eDate
     * @return
     */
    public static Period getPeriod(LocalDate sDate, LocalDate eDate) {
      return Period.between(sDate, eDate);
    }

    /**
     * 시작일자부터 종료일자까지 주말이 있는 지 확인하고 카운트를 리턴한다.
     * @param startDate
     * @param endDate
     * @return
     */
    public static int countWeekendInPeriod(Date sDate, Date eDate) {

        LocalDate startDate = DateTimeUtils.convertDateToLocalDate(sDate);
        LocalDate endDate = DateTimeUtils.convertDateToLocalDate(eDate);

      return DateTimeUtils.countWeekendInPeriod(startDate, endDate);
    }

    public static int countWeekendInPeriod(LocalDate sDate, LocalDate eDate) {

        int weekendCount = 0;

        Period period = DateTimeUtils.getPeriod(sDate, eDate);

        LocalDate tempDate = sDate;

        int days = period.getDays();

        for(int i=1; i <= days; i++){

            if(tempDate.getDayOfWeek() == DayOfWeek.SATURDAY || tempDate.getDayOfWeek() == DayOfWeek.SUNDAY) {
                weekendCount++;
            }

            tempDate = sDate.plusDays(i);
        }

      return weekendCount;

    }

    /**
     * 시작일자에 일자를 더해 끝날짜를 반환한다.
     * @param startDate
     * @param daysToAdd
     * @return
     */
    public static Date addDays(Date startDate, long daysToAdd) {

        LocalDate sDate = DateTimeUtils.convertDateToLocalDate(startDate);

      return DateTimeUtils.convertLocalDateToDate(sDate.plusDays(daysToAdd));
    }

    /**
     * 시작일자에 주를 더해 끝날짜를 반환한다.
     * @param startDate
     * @param weeksToAdd
     * @return
     */
    public static Date addWeeks(Date startDate, long weeksToAdd) {

        LocalDate sDate = DateTimeUtils.convertDateToLocalDate(startDate);

      return DateTimeUtils.convertLocalDateToDate(sDate.plusWeeks(weeksToAdd));
    }

    /**
     * 기존 Java.util.Date 타입을 java8의 LocalDateTime 형식으로 변경한다.
     *
     * @param someDate
     * @return
     */
    public static LocalDateTime convertDateToLocalDateTime(Date someDate) {
        //Asia/Seoul +9
        return DateTimeUtils.convertDateToLocalDateTime(someDate, ZoneId.systemDefault());
    }

    public static LocalDateTime convertDateToLocalDateTime(Date someDate, ZoneId zoneId) {

        //1. Convert Date -> Instant : 2016-08-19T13:46:31.981Z
        Instant instant = someDate.toInstant();

        //2. Instant + system default time zone + toLocalDateTime() = LocalDateTime : 2016-08-19T21:46:31.981
        LocalDateTime localDateTime = instant.atZone(zoneId).toLocalDateTime();

        return localDateTime;
    }



}