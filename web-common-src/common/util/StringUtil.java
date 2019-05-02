package com.kt.kkos.common.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.codehaus.jackson.map.ObjectMapper;

import com.kt.kkos.exception.ITLBusinessException;

/**
* KKOS project version 1.0
* 
* Copyright ⓒ 2018 kt corp. All rights reserved.
* 
* This is a proprietary software of kt corp, and you may not use this file except in
* compliance with license agreement with kt corp. Any redistribution or use of this
* software, with or without modification shall be strictly prohibited without prior written
* approval of kt corp, and the copyright notice above does not evidence any actual or
* intended publication of such software.
* 
* @FileName com.kt.kkos.common.util.StringUtil.java
* @Creator 91100014
* @CreateDate 2015.05.20
* @Version 0.1
* @Description 공통 문자열 함수 활용
*  
*/

/*
* ********************소스 수정 이력****************************************
* DATE                Modifier                      Description
* *********************************************************************
* 2015.05.20          서영일(91100014)               최초작성
* 2018.04.09          전원호(82023121)               내용추가
* *********************************************************************
*/

public final class StringUtil
{
	/**
	 * <pre>
	 * Private 생성자
	 * </pre>
	 */
	private StringUtil()
	{
	}
	
	public static String toString(Object obj) {
		
		ObjectMapper mapper = new ObjectMapper();
		try
		{
		
			if(obj.getClass().equals(ArrayList.class))
			{
				StringBuffer tempBuff = new StringBuffer();
				
				for(int i=0; i < ((List)obj).size(); i++)
				{
					String jsonStr 	= ToStringBuilder.reflectionToString(((List)obj).get(i), ToStringStyle.JSON_STYLE);
					Object jsonType = mapper.readValue(jsonStr.getBytes("UTF-8"), Object.class);
					
					tempBuff.append("\n").append(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonType));
				}
				
				return tempBuff.toString();
			}
			else
			{
				String jsonStr 	= ToStringBuilder.reflectionToString(obj, ToStringStyle.JSON_STYLE);
				Object jsonType = mapper.readValue(jsonStr.getBytes("UTF-8"), Object.class);
				
				return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonType);
			}
			
		} catch (IOException e)
		{
			return "pretty print convert error!!";
		}
	}
	
	
	/**
	 * <pre>
	 * length - str.length 만큼 앞에 0을 추가한다.
	 * </pre>
	 *
	 * @param str
	 * @param length
	 * @return
	 */
	public static String addZero(String str, int length) {
		StringBuilder temp = new StringBuilder("");
		
		for ( int i = str.length() ; i < length ; i++ ) {
			
			temp.append("0");
		}

		temp.append(str);
		return temp.toString();
	}

	/**
	 * <pre>
	 * 입력값에 whitespace가 아닌 글자가 있는지 판단함. whitespace - " ", "\t", "\n" 등의 눈에 보이지 않는 글자
	 * 
	 *   StringUtil.hasText(null) => false
	 *   StringUtil.hasText(" \t\n\r") => false
	 *   StringUtil.hasText("12345") => true
	 * </pre>
	 * 
	 * @param str 검사할 String, null일 수 있음
	 * @return whitespace가 아닌 글자가 하나 이상 있는 경우 true
	 */
	public static boolean hasText(final String str)
	{
		int strLen;
	    if ((str == null) || ((strLen = str.length()) == 0))
	      return false;
	    
	    
	    for (int i = 0; i < strLen; i++)
	    {
	      if (!Character.isWhitespace(str.charAt(i))) {
	        return true;
	      }
	    }
	    return false;
	}

	/**
	 * <pre>
	 * 문자열 s에 sub가 몇 번 나오는지 세어준다.
	 * </pre>
	 * 
	 * @param s 검사할 String, null일 수 있음
	 * @param sub 찾을 String, null일 수 있음
	 * @return 서브 문자열의 갯수, s가 null이거나 sub가 null이면 0
	 */
	public static int countSubstring(final String s, final String sub)
	{
		if ((s == null) || (sub == null) || ("".equals(sub)))
		{
			return 0;
		}
		int count = 0;
		int pos = 0;
		int idx = 0;
		while ((idx = s.indexOf(sub, pos)) != -1)
		{
			count++;
			pos = idx + sub.length();
		}
		return count;
	}

	/**
	 * <pre>
	 * 첫번째 인자에서 두번째 인자를 찾아 세번째 인자로 치환함.
	 * </pre>
	 * 
	 * @param inString 검사할 String, null이면 null을 돌려줌
	 * @param oldPattern 찾을 String, null이면 inString을 돌려줌
	 * @param newPattern 새로운 String, null이면 inString을 돌려줌
	 * @return 치환이 끝난 새로운 String
	 */
	public static String replace(final String inString, final String oldPattern, final String newPattern)
	{
		if (inString == null)
		{
			return null;
		}
		if ((oldPattern == null) || (newPattern == null))
		{
			return inString;
		}

		StringBuffer sbuf = new StringBuffer();

		int pos = 0;
		int index = inString.indexOf(oldPattern);

		int patLen = oldPattern.length();
		while (index >= 0)
		{
			sbuf.append(inString.substring(pos, index));
			sbuf.append(newPattern);
			pos = index + patLen;
			index = inString.indexOf(oldPattern, pos);
		}
		sbuf.append(inString.substring(pos));

		return sbuf.toString();
	}

	/**
	 * <pre>
	 * String에서 pattern을 찾아 제거한다.
	 * </pre>
	 * 
	 * @param inString 찾을 String
	 * @param pattern 제거할 패턴
	 * @return pattern이 제거된 새로운 String
	 */
	public static String delete(final String inString, final String pattern)
	{
		return replace(inString, pattern, "");
	}

	/**
	 * <pre>
	 * Object형 배열을 delim을 구분자로 가지는 String으로 만들어 준다.
	 * </pre>
	 * 
	 * @param arr Object형 배열
	 * @param delim 사용할 구분자
	 * @return 배열요소가 delim 구분자로 묶여진 String
	 */
	public static String toDelimitedString(final Object[] arr, final String delim)
	{
		if (arr == null)
		{
			return "null";
		}

		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < arr.length; i++)
		{
			if (i > 0)
				sb.append(delim);
			sb.append(arr[i]);
		}
		return sb.toString();
	}

	/**
	 * <pre>
	 * Collection의 내용을 delim을 구분자로 가지는 String으로 만들어 준다.
	 * </pre>
	 * 
	 * @param c Collection객체
	 * @param delim 사용할 구분자
	 * @return Collection 요소가 delim 구분자로 묶여진 String
	 */
	public static String toDelimitedString(final Collection<?> c, final String delim)
	{
		if (c == null)
		{
			return "null";
		}
		StringBuffer sb = new StringBuffer();
		Iterator it = c.iterator();
		int i = 0;
		while (it.hasNext())
		{
			if (i++ > 0)
			{
				sb.append(delim);
			}
			sb.append(it.next());
		}
		return sb.toString();
	}

	/**
	 * <pre>
	 * String의 첫번째 글자를 대문자로 바꿔 준다.
	 * 
	 * 사용 예)
	 * StringUtil.capitalize(null)   => null
	 * StringUtil.capitalize("abcd")  => "Abcd"
	 * </pre>
	 * 
	 * @param str 바뀌어질 String
	 * @return 바뀐 String, 입력 String이 null일 경우 null
	 */
	public static String capitalize(final String str)
	{
		return changeFirstCharacterCase(true, str);
	}
	
	private static String changeFirstCharacterCase(boolean capitalize, String str)
	{
		int strLen;
		if ((str == null) || ((strLen = str.length()) == 0))
			return str;
		
		StringBuffer buf = new StringBuffer(strLen);
		if (capitalize)
		{
			buf.append(Character.toUpperCase(str.charAt(0)));
		} else
		{
			buf.append(Character.toLowerCase(str.charAt(0)));
		}
		buf.append(str.substring(1));
		return buf.toString();
	}

	/**
	 * <pre>
	 * String의 첫번째 글자를 소문자로 바꿔 준다.
	 * 
	 * 사용 예)
	 * StringUtil.uncapitalize(null)   => null
	 * StringUtil.uncapitalize("ABCD")  => "aBCD"
	 * </pre>
	 * 
	 * @param str 바뀌어질 String
	 * @return 바뀐 String, 입력 String이 null일 경우 null
	 */
	public static String uncapitalize(final String str)
	{
		return changeFirstCharacterCase(false, str);
	}

	/**
	 * <pre>
	 * 입력문자열을 whitespace (" ", "\t", "\n" 등)로 분리하여 배열로 가져온다. 문자열 앞뒤의 whitespace는 무시한다.
	 * 
	 * 사용 예)
	 * StringUtil.split(null)         = {}
	 * StringUtil.split("  a\tb\nc") = {"a", "b", "c"}
	 * </pre>
	 * 
	 * @param str 분리할 String
	 * @return 나뉘어진 String배열, str이 null일 경우 빈 배열
	 */
	public static String[] split(final String str)
	{
		if (str == null)
		{
			return new String[] {};
		}
		return str.trim().split("\\s");
	}

	/**
	 * <pre>
	 * 지정된 delim으로 주어진 str을 배열로 분리한다.
	 * 
	 * 사용 예)
	 * StringUtil.split(null, ',')         = {}
	 * StringUtil.split("a.b.c", '.')    = {"a", "b", "c"}
	 * StringUtil.split("a b c", ' ')    = {"a", "b", "c"}
	 * </pre>
	 * 
	 * @param str 분리할 String
	 * @param delim 구분자로 사용할 글자
	 * @return 나뉘어진 String배열, str이 null일 경우 빈 배열
	 */
	public static String[] split(final String str, final char delim)
	{
		return split(str, delim + Constants.EMPTY);
	}

	/**
	 * <pre>
	 * 지정된 delim으로 주어진 str을 배열로 분리한다.
	 * 
	 * 사용 예)
	 * StringUtil.split(null, ",")         = {}
	 * StringUtil.split("a.b.c", ".")    = {"a", "b", "c"}
	 * StringUtil.split("a b c", " ")    = {"a", "b", "c"}
	 * StringUtil.split("abc", Constants.EMPTY)         = {Constants.EMPTY", "b", "c"}
	 * </pre>
	 * 
	 * @param str 나뉘어질 String
	 * @param delim 구분자로 사용할 글자
	 * @return 나뉘어진 String배열, str이 null일 경우 빈 배열
	 */
	public static String[] split(final String str, final String delim)
	{
		if (str == null)
		{
			return new String[] {};
		}

		if (Constants.EMPTY.equals(delim))
		{
			String[] arr = new String[str.length()];
			for (int i = 0; i < str.length(); i++)
			{
				arr[i] = str.charAt(i) + Constants.EMPTY;
			}
			return arr;
		}
		return delimitedListToStringArray(str, delim);
	}
	
	public static String[] delimitedListToStringArray(String s, String delim)
	{
		if (s == null)
		{
			return new String[0];
		}
		if (delim == null)
		{
			return new String[] { s };
		}

		List l = new LinkedList();
		int pos = 0;
		int delPos = 0;
		while ((delPos = s.indexOf(delim, pos)) != -1)
		{
			l.add(s.substring(pos, delPos));
			pos = delPos + delim.length();
		}
		if (pos <= s.length())
		{
			l.add(s.substring(pos));
		}

		return (String[]) l.toArray(new String[l.size()]);
	}

	/**
	 * <pre>
	 * 입력값이 NULL 또는 Constants.EMPTY 인지 체크한다.
	 * </pre>
	 * 
	 * @param str 체크할 String
	 * @return true|false
	 */
	public static boolean isEmpty(final String str)
	{
		return str == null || Constants.EMPTY.equals(str);
	}
	
	public static String rpad(String str, int length, String pad)
	{
		String upPad = pad;
		String resultStr = str;
		if (resultStr == null)
			resultStr = "";
		if (upPad == null)
			upPad = " ";
		int j = resultStr.length();
		if (j > length)
			return resultStr.substring(j - length);
		if (j == length)
			return resultStr;
		int k = upPad.length();
		int l = length - j;
		int i1 = l / k;
		int j1 = l % k;
		StringBuffer stringbuffer = new StringBuffer();
		stringbuffer.append(resultStr);
		for (; i1 > 0; i1--)
		{
			stringbuffer.append(upPad);
		}
		if (j1 > 0)
			stringbuffer.append(upPad.substring(0, j1));
		return stringbuffer.toString();
	}
	
	/**
	 * xssFilter.
	 * 
	 * @param value
	 * @return String
	 * @author Kim Donghyeong
	 */
	public static String xssFilter(String value, boolean convertBracket) {
		String resultValue = value;
		if (value != null) {
			// Null 문자 삭제
			resultValue = resultValue.replaceAll("\0", "");

			// %0d carriage return character, %0a linefeed character.
			resultValue = resultValue.replaceAll("%0a", "").replaceAll("0%d", "");

			// <script> 삭제
			Pattern scriptPattern = Pattern.compile("<script>(.*?)</script>", Pattern.CASE_INSENSITIVE);
			resultValue = scriptPattern.matcher(resultValue).replaceAll("");

			// src attribute 삭제
			scriptPattern = Pattern.compile("src[\r\n]*=[\r\n]*\\\'(.*?)\\\'", Pattern.CASE_INSENSITIVE
					| Pattern.MULTILINE | Pattern.DOTALL);
			resultValue = scriptPattern.matcher(resultValue).replaceAll("");

			scriptPattern = Pattern.compile("src[\r\n]*=[\r\n]*\\\"(.*?)\\\"", Pattern.CASE_INSENSITIVE
					| Pattern.MULTILINE | Pattern.DOTALL);
			resultValue = scriptPattern.matcher(resultValue).replaceAll("");

			// </script> 삭제
			scriptPattern = Pattern.compile("</script>", Pattern.CASE_INSENSITIVE);
			resultValue = scriptPattern.matcher(resultValue).replaceAll("");

			// <script ...> 삭제
			scriptPattern = Pattern.compile("<script(.*?)>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE
					| Pattern.DOTALL);
			resultValue = scriptPattern.matcher(resultValue).replaceAll("");

			// eval(...) 삭제
			scriptPattern = Pattern.compile("eval\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE
					| Pattern.DOTALL);
			resultValue = scriptPattern.matcher(resultValue).replaceAll("");

			// expression(...) 삭제
			scriptPattern = Pattern.compile("expression\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE
					| Pattern.DOTALL);
			resultValue = scriptPattern.matcher(resultValue).replaceAll("");

			// javascript:... 삭제
			scriptPattern = Pattern.compile("javascript:", Pattern.CASE_INSENSITIVE);
			resultValue = scriptPattern.matcher(resultValue).replaceAll("");

			// vbscript:... 삭제
			scriptPattern = Pattern.compile("vbscript:", Pattern.CASE_INSENSITIVE);
			resultValue = scriptPattern.matcher(resultValue).replaceAll("");

			// onload 삭제
			scriptPattern = Pattern.compile("onload(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE
					| Pattern.DOTALL);
			resultValue = scriptPattern.matcher(resultValue).replaceAll("");

			if (convertBracket) {
				resultValue = resultValue.replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\"", "&quot;");
			}
		}
		return resultValue;
	}

	
	/**
	 * <pre>
	 * 입력된 글자가 한글인지 판단한다.
	 * 
	 * 사용 예)
	 * StringUtil.isHangul('가')  => true
	 * StringUtil.isHangul('ㄱ')  => true
	 * StringUtil.isHangul('a')  => false
	 * </pre>
	 * 
	 * @param inputChar 판단할 char
	 * @return 한글이라면 true, 그렇지 않다면 false
	 */
	public static boolean isHangul(final char inputChar)
	{
		String unicodeBlock = Character.UnicodeBlock.of(inputChar).toString();

	    return (unicodeBlock.equals("HANGUL_JAMO")) || (unicodeBlock.equals("HANGUL_SYLLABLES")) || (unicodeBlock.equals("HANGUL_COMPATIBILITY_JAMO"));
	}
	
	public static boolean isHangul(String inputStr, boolean full)
	{
		char[] chars = inputStr.toCharArray();

		if (!full)
		{
			for (int i = 0; i < chars.length; i++)
			{
				if (isHangul(chars[i]))
				{
					return true;
				}
			}
			return false;
		}

		for (int i = 0; i < chars.length; i++)
		{
			if (!isHangul(chars[i]))
			{
				return false;
			}
		}

		return true;
	}

	/**
	 * <pre>
	 * 입력된 String이 한글인지 판단한다.
	 * 
	 * 사용 예)
	 * StringUtil.isHangulOnly("가나다")  => true
	 * StringUtil.isHangulOnly("ㄱㄴㅏㅑ")  => true
	 * StringUtil.isHangulOnly("a가나다")  => false
	 * StringUtil.isHangulOnly(null)  => false
	 * </pre>
	 * 
	 * @param inputStr 판단할 String
	 * @return 모든 글자가 한글일 경우 true, 입력 String이 null 일 경우 null
	 */
	public static boolean isHangulOnly(final String inputStr)
	{
		if (inputStr == null || Constants.EMPTY.equals(inputStr))
		{
			return false;
		}

		return isHangul(inputStr, true);
	}
	
	/**
	 * <pre>
	 * CRIS 연동에 사용하는 가상번호인지 확인한다.
	 * 

	 * </pre>
	 * 
	 * @param digit String입력값
	 * @return 가상번호인 경우 true, 그 외에는 모두 false
	 */
	public static boolean isVirtualNumber(final String digit)
	{
		if (isNull(digit))
		{
			return false;
		}
		
		try
		{
			
			final byte[] buf = digit.getBytes("EUC-KR");
			
			/* 총 13자리에 8번째에 'V'가 있으면 주민 또는 외국인 가상번호 */
			if ( buf.length == 13 && buf[7] == 'V' ) {
				
				return true;
			}
			/* 금융 7번째 'W' - 확인필요, 여권 5번째 'P' , 운전면허 5번째 'D' 인 경우 가상번호 */ 
			else {
				
				if ( buf.length > 3 && buf[3] == 'V' ) {
					
					return true;
				}
				
				if ( buf.length > 6 && buf[6] == 'W' ) {
					
					return true;
				}
				
				if ( buf.length <= 4 || (buf.length > 4 && buf[4] == 'P' || buf[4] == 'D') ) {
					
					return true;
				}
			}
			
			/* 
			 * 위 외에는 가상번호 아닌것으로 판단. 운전면허번호의 지역한글명등이 존재하며, 
			 * 이런경우 위 로직에 적용되지 않아 false 반환하여 실번호로 판단. 
			 * 그 외 실제 주민번호 13자리에 V가 없는 경우, 금융에 W 또는 카드나 계좌의 V가 없는 등만 true임.
			 */
			return false;
			
		} catch (UnsupportedEncodingException e)
		{
			return false;
		}
		
	}
	
	public static boolean isDigit(String digit)
	{
		if (digit == null)
		{
			return false;
		}

		char[] chars = digit.toCharArray();
		for (int i = 0; i < chars.length; i++)
		{
			if (!Character.isDigit(chars[i]))
			{
				return false;
			}
		}
		return true;
	}
	
	/**
	 * <pre>
	 * 주어진 문자열이 숫자만으로 구성되어 있는지 여부를 검사한다.
	 * 
	 * 사용 예)
	 * StringUtil.isNumberOnly("123") => true
	 * StringUtil.isNumberOnly(" 123") => false
	 * StringUtil.isNumberOnly(null) => false
	 * </pre>
	 * 
	 * @param digit String입력값
	 * @return 모두 숫자인 경우 true, null일 경우 false
	 */
	public static boolean isNumberOnly(final String digit)
	{
		if (digit == null)
		{
			return false;
		}
		if ( isVirtualNumber(digit) )
		{
			return false;
		}
		return isDigit(digit);
	}
	

	/**
	 * <pre>
	 * 주어진 문자열이 영문+숫자로 구성되어 있는지 여부를 검사한다.
	 * 
	 * 사용 예)
	 * StringUtil.isAlphabetNumberOnly("123") => true
	 * StringUtil.isAlphabetNumberOnly("abc123") => true
	 * StringUtil.isAlphabetNumberOnly("가123") => false
	 * StringUtil.isAlphabetNumberOnly(null) => false
	 * </pre>
	 * 
	 * @param str String입력값
	 * @return 모두 영문 또는 숫자인 경우 true, null일 경우 false
	 */
	public static boolean isAlphabetNumberOnly(final String str)
	{
		if (str == null)
		{
			return false;
		}

		return str.matches("^[0-9a-zA-Z]+$");
	}

	/**
	 * <pre>
	 * 연락처 자릿수를 12자리로 포멧 변경
	 * 
	 * 사용 예)
	 * StringUtil.toTelNoFormat("01012341234") => "001012341234"
	 * </pre>
	 * 
	 * @param orgStr 변경할 String
	 * @return 12자리로 변환된 String 
	 */
	public static String toTelNoFormat(final String orgStr)
	{
		String gugNo = "";
		String dddNo = "";
		String telNo = "";
		String tmpVar = "";
		 
		//번호 설정
		telNo = orgStr.substring(orgStr.length() - 4);  // 맨뒤 4자리 고정
		tmpVar = orgStr.substring(0,2);  // 지역번호
		
		if(orgStr.length() == 12 )
		{
			return orgStr;
		}
		else
		{
			if("02".equals(tmpVar))
			{
				dddNo = lpad(orgStr.substring(0,2), '0', 4);
				gugNo = lpad(orgStr.substring(2, orgStr.length() - 4), '0', 4);
			}
			else
			{
				dddNo = lpad(orgStr.substring(0,3), '0', 4);
				gugNo = lpad(orgStr.substring(3, orgStr.length() - 4), '0', 4);
			}
		}
		
		return dddNo+gugNo+telNo;
	}
	
	/**
	 * <pre>
	 * 문자열 앞에 특정문자를 붙여서 정해진 길이 문자열로 만듦.
	 * 
	 * 사용 예)
	 * StringUtil.lpad("123", '0', 5) => "00123"
	 * StringUtil.lpad("123", '0', 1) => "123"
	 * StringUtil.lpad(null, '0', 1) => null
	 * </pre>
	 * 
	 * @param orgStr 변경할 String
	 * @param appender 나머지를 채울 문자
	 * @param length 새로 만들 String의 길이
	 * @return 새로 만든 String, 입력 문자열이 주어진 길이보다 큰 경우 입력 문자열, 입력 문자열이 null일 경우 null
	 */
	public static String lpad(final String orgStr, final char appender, final int length)
	{
		if (orgStr == null)
		{
			return null;
		}

		final int orgLen = orgStr.length();
		if (orgLen >= length)
		{
			return orgStr;
		} else
		{
			final StringBuffer sb = new StringBuffer();
			for (int i = 0; i < length - orgLen; i++)
			{
				sb.append(appender);
			}
			sb.append(orgStr);
			return sb.toString();
		}
	}

	/**
	 * <pre>
	 * 문자열 뒤에 특정문자를 붙여서 정해진 길이 문자열로 만듦.
	 * 
	 * 사용 예)
	 * StringUtil.rpad("abc", ' ', 5) => "abc  "
	 * StringUtil.rpad("abc", '0', 1) => "abc"
	 * StringUtil.rpad(null, '0', 1) => null
	 * </pre>
	 * 
	 * @param str 변경할 String
	 * @param appender 나머지를 채울 문자
	 * @param length 새로 만들 String의 길이
	 * @return 새로 만든 String, 입력 문자열이 주어진 길이보다 큰 경우 입력 문자열, 입력 문자열이 null일 경우 null
	 */
	public static String rpad(final String str, final char appender, final int length)
	{
		if (str == null)
		{
			return null;
		}

		final int orgLen = str.length();
		if (orgLen >= length)
		{
			return str;
		} else
		{
			final StringBuffer sb = new StringBuffer();
			sb.append(str);
			for (int i = 0; i < length - orgLen; i++)
			{
				sb.append(appender);
			}
			return sb.toString();
		}
	}

	/**
	 * <pre>
	 * 문자열 앞의 whitespace(" ", "\t", "\n" 등) 제거한다.
	 * </pre>
	 * 
	 * @param str 대상 문자열
	 * @return whitespace가 제거된 문자열, 입력 문자열이 null이면 null
	 */
	public static String ltrim(final String str)
	{
		if (str == null)
		{
			return null;
		}

		return str.replaceFirst("^\\s+", Constants.EMPTY);
	}

	/**
	 * <pre>
	 * 문자열 뒤의 whitespace(" ", "\t", "\n" 등) 제거한다.
	 * </pre>
	 * 
	 * @param str 대상 문자열
	 * @return whitespace가 제거된 문자열, 입력 문자열이 null이면 null
	 */
	public static String rtrim(final String str)
	{
		if (str == null)
		{
			return null;
		}

		return str.replaceFirst("\\s+$", Constants.EMPTY);
	}

	/**
	 * <pre>
	 * 문자열 앞에서 입력 길이만큼 문자열을 추출함. 문자열의 길이가 입력 길이보다 작으면 전체 문자열을 리턴함.
	 * 
	 * 사용 예)
	 * StringUtil.left("abcd", 3) => "abc"
	 * StringUtil.right("abcd", 3) => "bcd"
	 * </pre>
	 * 
	 * @param str 대상 문자열
	 * @param len 잘라낼 길이
	 * @return 왼쪽에서부터 주어진 길이만큼의 문자열, 주어진 길이가 문자열의 길이보다 작으면 입력문자열, 입력 문자열이 null이면 null
	 */
	public static String left(final String str, final int len)
	{
		if (str == null)
		{
			return null;
		}

		if (str.length() <= len)
		{
			return str;
		} else
		{
			return str.substring(0, len);
		}
	}

	/**
	 * <pre>
	 * 문자열 뒤에서 특정 길이만큼 문자열을 추출함. 문자열의 길이가 입력 길이보다 작으면 전체 문자열을 리턴함.
	 * 
	 * 사용 예)
	 * StringUtil.left("abcd", 3) => "abc"
	 * StringUtil.right("abcd", 3) => "bcd"
	 * </pre>
	 * 
	 * @param str 대상 문자열
	 * @param len 잘라낼 길이
	 * @return 오른쪽에서부터 주어진 길이만큼의 문자열, 주어진 길이가 문자열의 길이보다 작으면 입력문자열, 입력 문자열이 null이면 null
	 */
	public static String right(final String str, final int len)
	{
		if (str == null)
		{
			return null;
		}

		if (str.length() <= len)
		{
			return str;
		} else
		{
			return str.substring(str.length() - len);
		}
	}

	/**
	 * <pre>
	 * 문자열을 int형으로 변환함.
	 * 
	 * 사용 예)
	 * StringUtil.parseInt("123") => 123
	 * StringUtil.parseInt("  -123") => -123
	 * StringUtil.parseInt("abc") => 예외 발생
	 * StringUtil.parseInt(null) => 0
	 * </pre>
	 * 
	 * @param inputValue 변환할 String
	 * @return int값
	 */
	public static int parseInt(final String inputValue)
	{
		int rtnVal = 0;
		
		//String null일 경우 0으로 리턴
		if(!ITLStringUTIL.isNullOrEmpty(inputValue))
		{
			rtnVal = Integer.parseInt(inputValue.trim());
		}
		
		return rtnVal; 
	}
	
	/**
	 * <pre>
	 * 문자열을 long형으로 변환함.
	 * 
	 * 사용 예)
	 * StringUtil.parseLong("123") => 123L
	 * StringUtil.parseLong("  -123") => -123L
	 * StringUtil.parseLong("abc") => 예외 발생
	 * StringUtil.parseLong(null) => 예외 발생
	 * </pre>
	 * 
	 * @param inputValue 변환할 String
	 * @return long값
	 */
	public static long parseLong(final String inputValue)
	{
		return Long.parseLong(inputValue.trim());
	}

	/**
	 * <pre>
	 * 문자열을 float형으로 변환함.
	 * 
	 * 사용 예)
	 * StringUtil.parseFloat("123.0") => 123.0
	 * StringUtil.parseFloat("  -123.0") => -123.0
	 * StringUtil.parseFloat("abc") => 예외 발생
	 * StringUtil.parseFloat(null) => 예외 발생
	 * </pre>
	 * 
	 * @param inputValue 변환할 String
	 * @return float값
	 */
	public static float parseFloat(final String inputValue)
	{
		return Float.parseFloat(inputValue.trim());
	}

	/**
	 * <pre>
	 * 문자열을 double형으로 변환함.
	 * 
	 * 사용 예)
	 * StringUtil.parseDouble("123.0") => 123.0
	 * StringUtil.parseDouble("  -123.0") => -123.0
	 * StringUtil.parseDouble("abc") => 예외 발생
	 * StringUtil.parseDouble(null) => 예외 발생
	 * </pre>
	 * 
	 * @param inputValue 변환할 String
	 * @return double값
	 */
	public static double parseDouble(final String inputValue)
	{
		return Double.parseDouble(inputValue.trim());
	}

	/**
	 * <pre>
	 * 문자열을 byte형으로 변환함.
	 * 
	 * 사용 예)
	 * StringUtil.parseByte("127") => 127
	 * StringUtil.parseByte("-128") => -128
	 * StringUtil.parseByte("128") => 예외 발생. -128 ~ 127 범위를 벗어남.
	 * </pre>
	 * 
	 * @param inputValue 변환할 String
	 * @return byte값
	 */
	public static byte parseByte(final String inputValue)
	{
		return Byte.parseByte(inputValue.trim());
	}

	/**
	 * <pre>
	 * 문자열을 BigDecimal형으로 변환함.
	 * </pre>
	 * 
	 * @param inputValue 변환할 String
	 * @return BigDecimal값
	 */
	public static BigDecimal parseBigDecimal(final String inputValue)
	{
		return new BigDecimal(inputValue.trim());
	}

	/**
	 * <pre>
	 * 숫자로 구성된 문자열을 int형 배열로 변환함.
	 * 숫자 이외의 필드는 -1로 반환
	 * </pre>
	 * 
	 * @param strNum 숫자로 구성된 문자열
	 * @return int형 배열
	 */
	public static int[] toIntArray(final String strNum)
	{
		if (strNum == null)
		{
			return null;
		}

		int[] arr = new int[strNum.length()];
		for (int i = 0; i < strNum.length(); i++)
		{
			arr[i] = Character.digit(strNum.charAt(i), 10);
		}
		return arr;
	}

	/**
	 * <pre>
	 * inputValue이 null인 경우 Constants.EMPTY를 return
	 * </pre>
	 * 
	 * @param inputValue 체크할 String
	 * @return null인 경우 Constants.EMPTY
	 */
	public static String nullToBlank(final String inputValue)
	{
		if (inputValue == null)
		{
			return Constants.EMPTY;
		} else
		{
			return inputValue;
		}
	}

	/**
	 * <pre>
	 * <code>join</code> 접두사, 접미사, 구분자를 사용하여 문자열을 합치는 메소드
	 * </pre>
	 * 
	 * @param prefix 접두사
	 * @param suffix 접미사
	 * @param msg 문자열
	 * @param delim 구분자
	 * @return String
	 */
	public static String join(final String prefix, final String suffix, final String[] msg, final String delim)
	{
		if (msg == null || msg.length == 0)
		{
			return null;
		}

		final int msgLen = msg.length;
		StringBuffer sb = new StringBuffer(1000);
		for (int i = 0; i < msgLen; i++)
		{
			if (i > 0)
			{
				sb.append(delim);
			}
			sb.append(msg[i]);
		}
		// prefix check
		if (prefix != null)
		{
			sb.insert(0, delim);
			sb.insert(0, prefix);
		}
		// suffix check
		if (suffix != null)
		{
			sb.append(delim);
			sb.append(suffix);
		}
		return sb.toString();
	}

	/**
	 * <pre>
	 * <code>join</code> 구분자를 사용하여 문자열을 합치는 메소드
	 * </pre>
	 * 
	 * @param msg 문자열
	 * @param delim 구분자
	 * @return String
	 */
	public static String join(final String[] msg, final String delim)
	{
		return join(null, null, msg, delim);
	}

	/**
	 * <pre>
	 * <code>checkNull</code> null 유무를 찾아서 null 이면 blank 를 반환한다.
	 * </pre>
	 * 
	 * @param param 체크할 String
	 * @return String
	 */
	public static String checkNull(final String param)
	{
		if (param == null)
		{
			return Constants.EMPTY;
		}
		return param;
	}

	/**
	 * <pre>
	 * input값이 condition와 같으면 output을 리턴한다.
	 * 같지 않으면 input을 리턴한다.
	 * </pre>
	 *
	 * @param input 비교하고픈 문자열
	 * @param condition 변환을 원하는 경우의 조건
	 * @param output 변환되어질 값
	 * @return 변환된 문자열
	 */
	public static String convertAtoB(final String input, final String condition, final String output)
	{
		if (input == null)
		{
			return ((condition == null) ? output : input);
		} else
		{
			return (input.equals(condition) ? output : input);
		}
	}
	
	
	/**
	 * @param obj null 인지 비교할 대상
	 * @param value 첫번째 인자가 Null일 경우 대체할 대상
	 * @return 첫번째 인자가 Not Null일 경우 obj(첫번째 인자), Null일 경우 value(두번째 인자)가 리턴됨
	 */
	public static Object nvl(Object obj, Object value) {
		return obj == null ? value : obj;
	}

	/**
	 * @param str null 인지 비교할 대상
	 * @param value 첫번째 인자가 Null일 경우 대체할 대상
	 * @return 첫번째 인자가 Not Null일 경우 str(첫번째 인자), Null일 경우 value(두번째 인자)가 리턴됨
	 */
	public static String nvl(String str, String value) {
		return str == null ? value : str;
	}

	/**
	 * @param str null 인지 비교할 대상
	 * @return str이 Null일 경우 Empty String, 아닐경우 str 리턴
	 */
	public static String nvl(String str) {
		return str == null ? Constants.EMPTY : str;
	}

	/**
	 * <pre>
	 * 문자열 앞에 특정문자를 붙여서 정해진 길이 문자열로 만듦.
	 * 
	 * 사용 예)
	 * StringUtil.lpad("123", '0', 5) => "00123"
	 * StringUtil.lpad("123", '0', 1) => "123"
	 * StringUtil.lpad(null, '0', 1) => null
	 * </pre>
	 * 
	 * @param orgStr
	 *            변경할 String
	 * @param appender
	 *            나머지를 채울 문자
	 * @param length
	 *            새로 만들 String의 길이
	 * @return 새로 만든 String, 입력 문자열이 주어진 길이보다 큰 경우 입력 문자열, 입력 문자열이 null일 경우 null
	 */
	public static String lpad(final String orgStr, final int length,
			final char appender) {
		if (orgStr == null) {
			return null;
		}

		final int orgLen = orgStr.length();
		if (orgLen >= length) {
			return orgStr;
		} else {
			final StringBuffer sb = new StringBuffer();
			for (int i = 0; i < length - orgLen; i++) {
				sb.append(appender);
			}
			sb.append(orgStr);
			return sb.toString();
		}
	}
	
	
	/**
	 * <pre>
	 * 입력 객체가 Null 이면 true, 아니면 false를 반환.
	 * </pre>
	 *
	 * @param obj
	 * @return
	 */
	public static final boolean isNull(Object obj) {
		return (obj == null);
	}

	/**
	 * <pre>
	 * 문자열이 Null 또는 Empty String(빈 문자열)이면 true, 아니면 false를 반환한다.
	 * 
	 * 사용 예)
	 * String str = null;
	 * StringUtil.isNull(str) => true
	 * 
	 * String str2 = "";
	 * StringUtil.isNull(str2) => true
	 * </pre>
	 * @param str 입력한 문자열
	 * @return (true or false)
	 */
	public static final boolean isNull(String str) {
		return (str == null) || (str.trim().equals(""));
	}
	
	/**
	 * <pre>
	 * 문자열을 반복한다.
	 * 
	 * 사용 예)
	 * String str = StringUtil.repeat(100, "A");
	 * => A가 100개 이은 문자열이 출력됨
	 * 
	 * </pre>
	 * @param repeat 문자반복 횟수
	 * @param mask 반복할 문자 또는 문자열
	 * @return (true or false)
	 */
	 public static String repeat(int repeat, String mask)
	 {
	    if (mask == null) {
	      return null;
	    }
	    if (repeat <= 0) {
	      return "";
	    }
	    int inputLength = mask.length();
	    if ((repeat == 1) || (inputLength == 0)) {
	      return mask;
	    }
	    if ((inputLength == 1) && (repeat <= 8192)) {
		    char[] buf = new char[repeat];
		    for (int i = 0; i < buf.length; i++) {
		      buf[i] = mask.charAt(0);
		    }
		    return new String(buf);
	    }
	    int outputLength = inputLength * repeat;
	    switch (inputLength)
	    {
	    case 1: 
	      char ch = mask.charAt(0);
	      char[] output1 = new char[outputLength];
	      for (int i = repeat - 1; i >= 0; i--) {
	        output1[i] = ch;
	      }
	      return new String(output1);
	    case 2: 
	      char ch0 = mask.charAt(0);
	      char ch1 = mask.charAt(1);
	      char[] output2 = new char[outputLength];
	      for (int i = repeat * 2 - 2; i >= 0; i=i-2)
	      {
	        output2[i] = ch0;
	        output2[(i + 1)] = ch1;
	      }
	      return new String(output2);
	    default: { new String(); }
	    }
	    
	    StringBuffer buf = new StringBuffer(outputLength);
	    for (int i = 0; i < repeat; i++) {
	      buf.append(mask);
	    }
	    return buf.toString();
	  }

	 
	 	// 2018.04.09 전원호 추가 --------------
	 
	 	/**
		 * 
		 * @return yyyyMMdd
		 */
		public static String Date_yyyyMMdd() {
		    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		
		    Date date = new Date();
		    String dateString = formatter.format(date);
		    return dateString;
		}
		
		/**
		 * 
		 * @return HHmmssSSS
		 */
		public static String Date_HHmmssSSS() {
		    SimpleDateFormat formatter = new SimpleDateFormat("HHmmssSSS");
		
		    Date date = new Date();
		    String dateString = formatter.format(date);
		    return dateString;
		}
		
		/**
		 * 
		 * @return yyyyMMddHHmmss
		 */
		public static String Date_yyyyMMddHHmmss() {
		    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		
		    Date date = new Date();
		    String dateString = formatter.format(date);
		    return dateString;
		}
		
		/**
		 * 
		 * @return yyyyMMdd-HHmmss
		 */
		public static String DateyyyyMMdd_HHmmss() {
		    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd-HHmmss");
		
		    Date date = new Date();
		    String dateString = formatter.format(date);
		    return dateString;
		}
		
		/**
		 * 
		 * @return yyyy-MM-dd HH:mm:ss
		 */
		public static String Date_yyyy_MM_dd_HH_mm_ss() {
		    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		    Date date = new Date();
		    String dateString = formatter.format(date);
		    return dateString;
		}
		
		/**
		 * 
		 * @return yyyyMMddHHmmssSSS
		 */
		public static String DateyyyyMMddHHmmssSSS() {
		    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		
		    Date date = new Date();
		    String dateString = formatter.format(date);
		    return dateString;
		}
		
		/**
		 * 
		 * @return yyyy-MM-dd HH:mm:ss.SSS
		 */
		public static String Date_yyyy_MM_dd_HH_mm_ss_SSS() {
		    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		
		    Date date = new Date();
		    String dateString = formatter.format(date);
		    return dateString;
		}
		
		/**
		 * 
		 * @return yyyy-MM-dd HH:mm:ss.SSSSSS
		 */
		public static String Date_yyyy_MM_dd_HH_mm_ss_SSSSSS() {
		    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS");
		
		    Date date = new Date();
		    String dateString = formatter.format(date);
		    return dateString;
		}
		
		public static String getIPAddress() {
			
			String ip = "";
			
			try {
				InetAddress inetAddress = InetAddress.getLocalHost();

				ip = inetAddress.getHostAddress();
				
			} catch (Exception e) {
				ITLBusinessException.printErrorStack(e);
			} 
			return ip;
		}
		
		public static String getHostname() {
			
			String hostname = "";
			
			try {
				InetAddress inetAddress = InetAddress.getLocalHost();
				
				hostname = inetAddress.getHostName();
				
			} catch (Exception e) {
				ITLBusinessException.printErrorStack(e);
			} 
			return hostname;
		}
		

		/**
		 * @param  Properties file path
		 * @return Properties
		 */
		public static Properties readProperties(String filePath) {
			
			Properties props = new Properties();
			
			FileInputStream fis = null;
			InputStreamReader isr = null;
			BufferedReader br = null;
			
			try {
				
				fis = new FileInputStream(filePath);
				isr = new InputStreamReader(fis, "UTF-8");
				br = new BufferedReader(isr);
				props.load(br);
			   
				br.close(); br=null;
				isr.close(); isr=null;
				fis.close(); fis=null;
									
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				ITLBusinessException.printErrorStack(e);
			} 
			catch (Exception e) {
				ITLBusinessException.printErrorStack(e);
			}
			finally {
				if(br!=null)  { try {br.close(); br = null;} catch (IOException e) { ITLBusinessException.printErrorStack(e);} }
				if(isr!=null) { try {isr.close(); isr = null;} catch (IOException e) { ITLBusinessException.printErrorStack(e);}  }
				if(fis!=null) { try {fis.close(); fis = null;} catch (IOException e) { ITLBusinessException.printErrorStack(e);}  }
			}
			
			return props;
		}
		
		
		// -------------- 2018.04.09 전원호 추가 종료 (여기까지)
		
		
		
		/**
		 * 
		 * @param str
		 * @param a
		 * @param b
		 * @return str.replaceAll(a, b);
		 */
		public static String replaceStr(String str, String a, String b) {
			String result = "";
			try {
				if ((!isNull(str)) && (!str.equals("")))
					result = str.replaceAll(a, b);
			} catch (Exception e) {
				return str;
			}
			return result;
		}
		
		/**
		 * 
		 * @param yyyyMMdd(20150924)
		 * @param amount(3)
		 * @return 20150927
		 */
		public static String addDate(String yyyyMMdd, int amount) {
			
			String result = "";
			
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
			
			try {
				Date date = formatter.parse(yyyyMMdd);
							
				//String s1 = formatter.format(date);
							
				Calendar cal = Calendar.getInstance();
				cal.setTime(date);
				cal.add(Calendar.DATE, amount);
										
				result = formatter.format(cal.getTime());	
							
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				//ITLBusinessException.printErrorStack(e);
				result = yyyyMMdd;
			}
					
			return result;
		}
		
		public static ArrayList<String> GetAryListByDelimit(String strSource, String strDelimitor)
		{
			java.util.ArrayList<String> aryValue = new java.util.ArrayList<String>();
			
			if (strSource == null || strSource.length() == 0)
				return aryValue;
			
			try{
				StringTokenizer st = null;

				if (strDelimitor == null || " ".equals(strDelimitor))
					st = new StringTokenizer(strSource);
				else
					st = new StringTokenizer(strSource, strDelimitor);
		 
				while (st.hasMoreTokens())
				{
					aryValue.add((String) st.nextToken());
				}
			} catch(Exception e) {
				ITLBusinessException.printErrorStack(e);
			}
			return aryValue;
		}
		
		/**
		 * <pre>
		 * 설명	 : 입력받은 List가 null 이거나 길이가 0 이면 True 를 리턴한다
		 * @param list
		 * @return
		 * </pre>
		 */
		public static <T> boolean isEmpty(List<T> list) {
			if (list == null || list.size() == 0) {
				return true;
			}
			return false;
		}
		
		/**
		 * <pre>
		 * 설명	 :입력받은 문자열이 null 이거나 길이가 0 이면 True 를 리턴한다 (주의:공백값으로 인정안함).
		 * @param param 검증할문자열
		 * @return 공백포함 빈값일경우 true 아니면 false
		 * value = '';
		 * StringHelper.isBlank(value) : true
		 * value = ' '; // 공백
		 * StringHelper.isBlank(value) : true	 *
		 * </pre>
		 */
		public static boolean isBlank(String param) {
			if (param == null || "".equals(param)) {
				return true;
			}
			return false;
		}
		
		/**
		 * <pre>
		 * 설명	 : 4/4/4 포맷을 짜른뒤 '-'구분자를 붙여서 리턴
		 * ex) 001012345678 > 010-1234-5678
		 * ex) 0002023405678 > 02-234-5678
		 * @param s
		 * @return
		 * </pre>
		 */
		public static String getDivPhoneNum(String s) {

			if ("".equals(nvl(s, ""))) {
				return "";
			}
			String str1 = "";
			String str2 = "";
			String str3 = "";
			if (s.length() == 12) {
				str1 = s.substring(0,4);
				str2 = s.substring(4,8);
				str3 = s.substring(8,12);
			} else if (s.length() == 11) {
				int startIndex = s.indexOf("010");
				if (startIndex == 1) {
					str1 = s.substring(1,4);
					str2 = s.substring(4,7);
					str3 = s.substring(7,11);
				} else {
					if (s.indexOf("00") == 0) {
						str1 = s.substring(0,4);
						str2 = s.substring(4,7);
						str3 = s.substring(7,11);
					} else {
						str1 = s.substring(0,3);
						str2 = s.substring(3,7);
						str3 = s.substring(7,11);
					}
				}
			} else if (s.length() == 10) {
				int wireIndex = s.indexOf("02");
				if (wireIndex == 0) {
					str1 = s.substring(0,2);
					str2 = s.substring(2,6);
					str3 = s.substring(6,10);
				} else {
					str1 = s.substring(0,3);
					str2 = s.substring(3,6);
					str3 = s.substring(6,10);
				}
			} else if (s.length() == 9) {
				str1 = s.substring(0,2);
				str2 = s.substring(2,5);
				str3 = s.substring(5,9);
			} else {
				return s;
			}

			// 앞자리가 0으로 시작하지 않으면 4-4-4 포맷으로 사용자에게 디스플레이 한다.
			boolean isDisp444 = !str1.startsWith("0");

			if (!isDisp444) {
				str1 = getUnitPhoneNum(str1, "0");
			}

			if (!"010".equals(str1) && !isDisp444) {
				if (str2.startsWith("0") && str2.length() == 4) {
					str2 = str2.substring(1, str2.length());
				}
			}

			String rtnTelNo = "";
			if(str1.equals("00")) {
				rtnTelNo = new StringBuffer().append(str2).append("-").append(str3).toString();
			} else {
				rtnTelNo = new StringBuffer().append(str1).append("-").append(str2).append("-").append(str3).toString();
			}

			return rtnTelNo;
		}
		
		/**
		 * <pre>
		 * 설명	 : 핸드폰번호 앞에 0포맷 짤라서 str1붙여서 리턴
		 * ex ) getPhoneNumUnit("0002", "0") : 02
		 * ex ) getPhoneNumUnit("0413", "") : 413
		 * @param s
		 * @return
		 * </pre>
		 */
		public static String getUnitPhoneNum(String str, String str1) {
			String s = str;
			String s1 = str1;
			if (s.startsWith("000")) {
				s = s.substring(3, s.length());
			} else if (s.startsWith("00")) {
				s = s.substring(2, s.length());
			} else if (s.startsWith("0")) {
				s = s.substring(1, s.length());
			}
			s1 = nvl(s1, "");
			return String.format("%s%s",s1, s);
		}
		
		/**
		 * 숫자포맷출력
		 *
		 * @param Long value
		 * @return
		 */
		public static String addComma(long val) {
			NumberFormat format = NumberFormat.getNumberInstance();
			return format.format(val);
		}

		/**
		 * 숫자포맷출력
		 *
		 * @param String value
		 * @return
		 */
		public static String addComma(String val) {
			long rslt = 0;

			try {
				rslt = Long.parseLong(val);
			} catch (Exception e) {
				return "";
			}

			return addComma(rslt);
		}
		
		/**
		 * <pre>
		 * 설명	 : 주민등록번호로 고객의 나이 알아내기
		 * @param custNo
		 * @param toDay
		 * @return
		 * </pre>
		 */
		public static int getAge(String custNo, String toDay) {

			int age = 0;

			if (custNo.equals("") || custNo.length() != 13) {
				return age;
			} else {
				// *********** ctn 사용자 나이 계산
				int toYear = Integer.parseInt(toDay.substring(0, 4)); // 현재 년도
				int toMonth = Integer.parseInt(toDay.substring(4, 6)); // 현재 월
				int toDate = Integer.parseInt(toDay.substring(6, 8)); // 현재 날짜

				int birYear = Integer.parseInt(custNo.substring(0, 2)); // 생일 년
				int birMonth = Integer.parseInt(custNo.substring(2, 4)); // 생일 월
				int birDate = Integer.parseInt(custNo.substring(4, 6)); // 생일 날짜

				@SuppressWarnings("unused")
				int toAgeMonth = 0; // 나이 월
				@SuppressWarnings("unused")
				int toAgeDate = 0; // 나이 날짜

				// 월의 마지막 날짜 계산
				String yyyy = toDay.substring(0, 4);
				String mm = toDay.substring(4, 6);
				String dd = "";
				if (mm.equals("01") || mm.equals("03") || mm.equals("05") || mm.equals("07") || mm.equals("08") || mm.equals("10") || mm.equals("12")) {
					dd = "31";
				} else if (mm.equals("02")) {
					if ((Float.parseFloat(yyyy) / 4) == (Integer.parseInt(yyyy) / 4)) {
						if ((Float.parseFloat(yyyy) / 100) == (Integer.parseInt(yyyy) / 100)) {
							if ((Float.parseFloat(yyyy) / 400) == (Integer.parseInt(yyyy) / 400)) {
								dd = "29";
							} else {
								dd = "28";
							}
						} else {
							dd = "29";
						}
					} else {
						dd = "28";
					}
				} else {
					dd = "30";
				}
				int intDd = Integer.parseInt(dd);

				// 년도 붙이기
				if (Integer.parseInt(custNo.substring(6, 7)) == 1 || Integer.parseInt(custNo.substring(6, 7)) == 2 ||
						Integer.parseInt(custNo.substring(6, 7)) == 5 || Integer.parseInt(custNo.substring(6, 7)) == 6) {
					birYear = 1900 + birYear;
				} else if (Integer.parseInt(custNo.substring(6, 7)) == 3 || Integer.parseInt(custNo.substring(6, 7)) == 4 ||
						Integer.parseInt(custNo.substring(6, 7)) == 7 || Integer.parseInt(custNo.substring(6, 7)) == 8) {
					birYear = 2000 + birYear;
				}

				// 나이 날짜 계산
				if (toDate < birDate) {
					toAgeDate = (toDate + intDd) - birDate;
					toMonth = toMonth - 1;
				} else {
					toAgeDate = toDate - birDate;
				}
				// 나이 월 계산
				if (toMonth < birMonth) {
					toAgeMonth = (toMonth + 12) - birMonth;
					toYear = toYear - 1;
				} else {
					toAgeMonth = toMonth - birMonth;
				}
				// 나이 년도 계산
				age = toYear - birYear;
			}

			return age;
		}
}
