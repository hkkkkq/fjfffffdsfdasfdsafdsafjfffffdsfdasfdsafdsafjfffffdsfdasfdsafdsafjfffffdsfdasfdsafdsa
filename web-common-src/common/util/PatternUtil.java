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

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Administrator
 *
 */
public final class PatternUtil
{
	/**
	 * 로거
	 */
	private static final Logger logger = LoggerFactory.getLogger(PatternUtil.class);

	/**
	 * <pre>
	 * Private 생성자
	 * </pre>
	 */
	private PatternUtil()
	{
	}

	/**
	 * <pre>
	 * 입력값을 주민등록번호 패턴으로 변환한다. 입력값이 null이거나 변환 불가능한 경우 Constants.EMPTY을 돌려준다.
	 * </pre>
	 * 
	 * @param string 13자리 숫자로 구성된 문자열 (예 : "1234567890123")
	 * @return 주민등록번호 패턴 (예 : "1234567-890123")
	 */
	public static String toResnoPattern(final String string)
	{
		return toSsNoPattern(string);
	}
	
	private static String toSsNoPattern(String string)
	{
		if (string == null)
		{
			return "";
		}

		if ((string.length() != 13) || (!StringUtil.isDigit(string)))
		{
			return "";
		}

		StringBuffer buffer = new StringBuffer();

		buffer.append(string.substring(0, 6));
		buffer.append('-');
		buffer.append(string.substring(6));

		return buffer.toString();
	}

	/**
	 * <pre>
	 * 입력값을 사업자 등록번호 패턴으로 변환한다. 입력값이 null이거나 변환 불가능한 경우 Constants.EMPTY을 돌려준다.
	 * </pre>
	 * 
	 * @param string 10자리 숫자로 구성된 문자열 (예 : "1234567890")
	 * @return 사업자 등록번호 패턴 (예 : "123-45-67890")
	 */
	public static String toBsmnRgstNoPattern(final String string)
	{
		return toErNoPattern(string);
	}
	
	private static String toErNoPattern(String string)
	{
		if (string == null)
		{
			return "";
		}

		if ((string.length() != 10) || (!StringUtil.isDigit(string)))
		{
			return "";
		}

		StringBuffer buffer = new StringBuffer();

		buffer.append(string.substring(0, 3));
		buffer.append('-');
		buffer.append(string.substring(3, 5));
		buffer.append('-');
		buffer.append(string.substring(5, 10));

		return buffer.toString();
	}

	/**
	 * <pre>
	 * 입력값을 우편번호 패턴으로 변환한다. 입력값이 null이거나 변환 불가능한 경우 Constants.EMPTY을 돌려준다.
	 * </pre>
	 * 
	 * @param string 6자리 숫자로 구성된 문자열 (예 : "123456")
	 * @return 우편번호 패턴 (예 : "123-456")
	 */
	public static String toPostPattern(String string)
	{
		return toZipCodePattern(string);
	}
	
	private static String toZipCodePattern(String string)
	{
		if (string == null)
		{
			return "";
		}

		if ((string.length() != 6) || (!StringUtil.isDigit(string)))
		{
			return "";
		}

		StringBuffer buffer = new StringBuffer();

		buffer.append(string.substring(0, 3));
		buffer.append('-');
		buffer.append(string.substring(3, 6));

		return buffer.toString();
	}

	/**
	 * <pre>
	 * 입력값이 주민등록번호 패턴인지 여부를 판단함. 주민등록번호 유효성 검증은 하지 않음.
	 * 
	 * 사용 예)
	 * BizUtil.isSsNoPattern("123456-7890123") => true
	 * BizUtil.isSsNoPattern("123456/7890123") => false
	 * BizUtil.isSsNoPattern("123456-1V12345") => true(가상번호)
	 * BizUtil.isSsNoPattern("	123456-7890123") => false (공백이 있으므로 FALSE.)
	 * </pre>
	 * 
	 * @param str 입력 문자열
	 * @return 입력값이 <숫자6자리>- <숫자7자리>로 구성된 문자열인 경우만 TRUE
	 */
	public static boolean isResnoPattern(String str)
	{
		return isFormattedString(str, "[0-9]{6}-[0-9][0-9,V][0-9]{5}");
	}

	private static boolean isFormattedString(String string, String pattern)
	{
		if ((string == null) || (pattern == null))
		{
			return false;
		}

		return string.matches(pattern);
	}

	/**
	 * <pre>
	 * 입력값이 사업자등록번호 패턴인지 여부를 판단함. 사업자등록번호 유효성 검증은 하지 않음.
	 * 
	 * 사용 예)
	 * BizUtil.isErNoPattern("123-45-67890") => true
	 * BizUtil.isErNoPattern("123/45/67890") => false
	 * BizUtil.isErNoPattern("	123-45-67890") => false (공백이 있으므로 FALSE.)
	 * </pre>
	 * 
	 * @param str 입력 문자열
	 * @return 입력값이 <숫자3자리>- <숫자2자리>- <숫자5자리>로 구성된 문자열인 경우만 TRUE
	 */
	public static boolean isBsmnRgstNoPattern(String str)
	{
		return isFormattedString(str, "[0-9]{3}-[0-9]{2}-[0-9]{5}");
	}

	/**
	 * <pre>
	 * 주민/외국인 등록번호로 부터 성별을 구한다.
	 * </pre>
	 * 
	 * @param ssnNoStr 주민/외국인등록번호 13자리, 구분자가 있어도 상관없음.
	 * @return 남자:M, 여자:F
	 * @throws IllegalArgumentException
	 */
	public static String getSexDistinction(String ssnNoStr) throws IllegalArgumentException
	{
		boolean isDomestic = isValidResno(ssnNoStr);
		boolean isForign = isValidForgnRgstNo(ssnNoStr);
		if (!isDomestic && !isForign)
		{
			throw new IllegalArgumentException(Constants.SSN_FORMAT_ERROR + "주민/외국인등록번호");
		}

		final String tempSsnNoStr = ssnNoStr.replaceAll("\\D", Constants.EMPTY);
		String strGen = "M";
		char gen = '0';
		if (isDomestic)
		{
			gen = tempSsnNoStr.charAt(6);
			if (gen == '2' || gen == '4')
			{
				strGen = "F";
			}
		}
		if (isForign)
		{
			gen = tempSsnNoStr.charAt(6);
			if (gen == '6' || gen == '8')
			{
				strGen = "F";
			}
		}

		return strGen;
	}

	/**
	 * <pre>
	 * 주민등록번호의 CheckSum이 맞는지 체크함.
	 * </pre>
	 * 
	 * @param ssoNoStr 주민등록번호 13자리, 구분자가 있어도 상관없음.
	 * @return 주민등록번호 13자리의 CheckSum이 유효하면 true
	 */
	public static boolean isValidResno(String ssoNoStr)
	{
		if (ssoNoStr == null)
		{
			return false;
		}
		
		try {

			if (ssoNoStr.getBytes("EUC-KR").length == 13 && StringUtil.isVirtualNumber(ssoNoStr))
			{
				return true;
			}
		} catch (UnsupportedEncodingException e)
		{
			return false;
		}

		// 숫자 외의 문자를 제거한다.
		String tempSsoNoStr = ssoNoStr.replaceAll("\\D", Constants.EMPTY);
		if (tempSsoNoStr.length() != 13)
		{
			if (logger.isDebugEnabled())
			{
				logger.debug("주민등록번호 13자리 유효숫자가 존재하지 않습니다.");
			}
			return false;
		}

		char gen = tempSsoNoStr.charAt(6);
		if (!(gen == '1' || gen == '2' || gen == '3' || gen == '4'))
		{
			if (logger.isDebugEnabled())
			{
				logger.debug("내국인의 주민등록번호가 아닙니다.");
			}
			return false;
		}

		int year100 = 19; // 100단위 생년
		if (gen == '3' || gen == '4')
		{
			year100 = 20;
		}

		if (!DateUtil.isValidDate(year100 + tempSsoNoStr.substring(0, 6)))
		{
			if (logger.isDebugEnabled())
			{
				logger.debug("주민등록번호 생년월일의 형식이 틀립니다.");
			}
			return false;
		}

		int[] arr = StringUtil.toIntArray(tempSsoNoStr);

		final int checkDigit = arr[0] * 2 + arr[1] * 3 + arr[2] * 4 + arr[3] * 5 + arr[4] * 6 + arr[5] * 7 + arr[6] * 8
				+ arr[7] * 9 + arr[8] * 2 + arr[9] * 3 + arr[10] * 4 + arr[11] * 5;

		int temp = (11 - checkDigit % 11) % 10;
		return (temp == arr[12]);
	}

	/**
	 * <pre>
	 * 외국인등록번호의 CheckSum이 맞는지 체크함.
	 * </pre>
	 * 
	 * @param frNoStr 외국인등록번호 13자리, 구분자가 있어도 상관없음.
	 * @return 외국인등록번호 13자리의 CheckSum이 유효하면 true
	 */
	public static boolean isValidForgnRgstNo(String frNoStr)
	{
		if (frNoStr == null)
		{
			return false;
		}
		
		try {
			
			if ( frNoStr.getBytes("EUC-KR").length > 6 && frNoStr.getBytes("EUC-KR")[6] == 'W' && StringUtil.isVirtualNumber(frNoStr))
			{
				return true;
			}
		} catch (UnsupportedEncodingException e)
		{
			return false;
		}

		// 숫자 외의 문자를 제거한다.
		String tempFrNoStr = frNoStr.replaceAll("\\D", Constants.EMPTY);
		if (tempFrNoStr.length() < 13)
		{
			if (logger.isDebugEnabled())
			{
				logger.debug("외국인등록번호 13자리 유효숫자가 존재하지 않습니다.");
			}
			return false;
		}

		char gen = tempFrNoStr.charAt(6);
		if (gen == '1' || gen == '2' || gen == '3' || gen == '4')
		{
			if (logger.isDebugEnabled())
			{
				logger.debug("외국인의 등록번호가 아닙니다.");
			}
			return false;
		}

		// 100단위 생년
		int year100 = 19;
		if (gen == '7' || gen == '8')
		{
			year100 = 20;
		} else if (gen == '9' || gen == '0')
		{
			year100 = 18;
		}

		if (!DateUtil.isValidDate(year100 + tempFrNoStr.substring(0, 6)))
		{
			if (logger.isDebugEnabled())
			{
				logger.debug("외국인등록번호의 생년월일의 형식이 틀립니다.");
			}
			return false;
		}

		// 등록기관번호(8~9 자리)
		if (Integer.parseInt(tempFrNoStr.substring(7, 9)) % 2 != 0)
		{
			if (logger.isDebugEnabled())
			{
				logger.debug("외국인등록번호의 등록기관번호가 상이.");
			}
			return false;
		}

		int[] arr = StringUtil.toIntArray(tempFrNoStr);
		// 등록자구분(12번째 자리) - 9: 순수외국인, 8: 재외국민, 7: 외국국적동포, 6: ?
		if (!(arr[11] == 6 || arr[11] == 7 || arr[11] == 8 || arr[11] == 9))
		{
			if (logger.isDebugEnabled())
			{
				logger.debug("외국인등록번호의 등록자구분이 상이.");
			}
			return false;
		}

		int sum = 0;
		int[] multipliers = { 2, 3, 4, 5, 6, 7, 8, 9, 2, 3, 4, 5 };
		for (int i = 0; i < 12; i++)
		{
			sum += (arr[i] *= multipliers[i]);
		}

		sum = 11 - (sum % 11);
		if (sum >= 10)
		{
			sum -= 10;
		}
		sum += 2;
		if (sum >= 10)
		{
			sum -= 10;
		}

		if (sum == arr[12])
		{
			return true;
		} else
		{
			if (logger.isDebugEnabled())
			{
				logger.debug("외국인등록번호의 sum/arr[12] 비교값 상이.");
			}
			return false;
		}
	}

	/**
	 * <pre>
	 * 사업자등록번호의 CheckSum이 맞는지 체크함.
	 * </pre>
	 * 
	 * @param erNoStr 사업자등록번호 10자리, 구분자가 있어도 상관없음.
	 * @return 사업자등록번호 10자리의 CheckSum이 유효하면 true
	 */
	public static boolean isValidBsmnRgstNo(String erNoStr)
	{
		if (erNoStr == null)
		{
			return false;
		}

		// 숫자 외의 문자를 제거한다.
		String tempErNoStr = erNoStr.replaceAll("\\D", Constants.EMPTY);
		if (tempErNoStr.length() < 10)
		{
			if (logger.isDebugEnabled())
			{
				logger.debug("사업자등록번호 10자리 유효숫자가 존재하지 않습니다.");
			}
			return false;
		}

		int[] arr = StringUtil.toIntArray(tempErNoStr);
		int checkDigit = arr[0] + arr[1] * 3 % 10 + arr[2] * 7 % 10 + arr[3] * 1 % 10 + arr[4] * 3 % 10 + arr[5] * 7
				% 10 + arr[6] * 1 % 10 + arr[7] * 3 % 10 + (int) Math.floor(arr[8] * 5 / 10) + arr[8] * 5 % 10 + arr[9];
		return (checkDigit % 10 == 0);
	}

	/**
	 * <pre>
	 * 법인번호 체크.
	 * </pre>
	 * 
	 * @param coNoStr 법인번호 13자리, 구분자가 있어도 상관없음.
	 * @return 법인번호 13자리 CheckSum 이 맞으면 true
	 */
	public static boolean isValidCorpNo(String coNoStr)
	{
		if (coNoStr == null)
		{
			return false;
		}

		// 숫자 외의 문자를 제거한다.
		String tempCoNoStr = coNoStr.replaceAll("\\D", Constants.EMPTY);
		if (tempCoNoStr.length() < 13)
		{
			if (logger.isDebugEnabled())
			{
				logger.debug("법인번호 13자리 유효숫자가 존재하지 않습니다.");
			}
			return false;
		}

		char[] chArr = tempCoNoStr.toCharArray();

		int sum1 = 0;
		int sum2 = 0;

		for (int i = 0; i < 12; i++)
		{
			if (i % 2 == 0)
			{ // * 1
				sum1 = sum1 + (Integer.parseInt(Constants.EMPTY + chArr[i]) * 1);
			} else
			{ // * 2
				sum2 = sum2 + (Integer.parseInt(Constants.EMPTY + chArr[i]) * 2);
			}
		}

		int totalSum = sum1 + sum2;
		int mok = totalSum % 10;
		if (mok != 0)
		{
			mok = 10 - mok;
		}
		return (mok == Integer.parseInt(Constants.EMPTY + chArr[12]));
	}

	/**
	 * <pre>
	 * 	}
	 * 입력값이 우편번호 패턴인지 여부를 판단함.
	 * 
	 * 사용 예)
	 * BizUtil.isZipCodePattern("123-456") => true
	 * BizUtil.isZipCodePattern("123/456") => false
	 * BizUtil.isZipCodePattern("	123-456") => false (공백이 있으므로 FALSE.)
	 * </pre>
	 * 
	 * @param str 입력 문자열
	 * @return 입력값이 <숫자3자리>- <숫자3자리>로 구성된 문자열인 경우만 TRUE
	 */
	public static boolean isPostPattern(String str)
	{
		return isFormattedString(str, "[0-9]{3}-[0-9]{3}");
	}

	/**
	 * <pre>
	 * 입력값이 IP주소 패턴인지 여부를 판단함. IP주소 유효성 검증은 하지 않음.
	 * 
	 * 사용 예)
	 * BizUtil.isIpAddressPattern("123.123.123.123") => true
	 * BizUtil.isIpAddressPattern("	123.123.123.123") => false (공백이 있으므로 FALSE.)
	 * </pre>
	 * 
	 * @param str 입력 문자열
	 * @return 입력값이 <숫자1-3>. <숫자1-3>. <숫자1-3>. <숫자1-3>로 구성된 문자열인 경우만 TRUE
	 */
	public static boolean isIpAddrPattern(String str)
	{
		return isFormattedString(str, "[0-9]{1,3}.[0-9]{1,3}.[0-9]{1,3}.[0-9]{1,3}");
	}

	/**
	 * <pre>
	 * 입력값이 전화번호 패턴인지 여부를 판단함.
	 * 
	 * 사용 예)
	 * BizUtil.isTelNoPattern("02-202-2424") => true
	 * BizUtil.isTelNoPattern("019-202-2424") => true
	 * BizUtil.isTelNoPattern("	02-202-2424") => false (공백이 있으므로 FALSE.)
	 * </pre>
	 * 
	 * @param str 입력 문자열
	 * @return 입력값이 <숫자2-3>- <숫자3-3>- <숫자4자리>로 구성된 문자열인 경우만 TRUE
	 */
	public static boolean isTelnoPattern(String str)
	{
		return isFormattedString(str, "[0-9]{2,3}-[0-9]{3,4}-[0-9]{4}");
	}

	/**
	 * <pre>
	 * 입력값이 이메일 패턴인지 여부를 판단함.
	 * 
	 * 사용 예)
	 * isEmailPattern("kdhong@tmax.co.kr") => true
	 * isEmailPattern("kd_hong@tmax.com") => true
	 * isEmailPattern("	kdhong@tmax.co.kr") => false (공백이 있으므로 FALSE.)
	 * </pre>
	 * 
	 * @param str 입력 문자열
	 * @return 입력값이 ###@###.### 패턴을 가지는 문자열인 경우 TRUE
	 */
	public static boolean isMailPattern(String str)
	{
		if (str == null)
		{
			return false;
		}

		return str.matches("^((\\w|[\\-\\.])+)@((\\w|[\\-\\.])+)\\.([A-Za-z]+)$");
	}

	/**
	 * <pre>
	 * 숫자를 3자리마다 콤마(,)를 찍어서 화폐 패턴의 문자열로 변환함. 소수가 있는 경우 반올림되어 처리됨.
	 * 
	 * 사용 예)
	 * BizUtil.toMoneyPattern(54321) => "54,321"
	 * BizUtil.toMoneyPattern(54321.6) => "54,322"
	 * BizUtil.toMoneyPattern(-54321) => "-54,321"
	 * </pre>
	 * 
	 * @param d 모든 타입의 숫자
	 * @return ###,### 형태의 문자열
	 */
	public static String toMoneyPattern(double d)
	{
		return toMoneyPattern(d, 0);
	}

	/**
	 * <pre>
	 * 숫자를 3자리마다 콤마(,)를 찍어서 화폐 패턴의 문자열로 변환함. 지정된 소숫점 자리에서 반올림되어 처리됨.
	 * 
	 * 사용 예)
	 * BizUtil.toMoneyPattern(54321, 0) => "54,321"
	 * BizUtil.toMoneyPattern(54321.6, 2) => "54,321.60"
	 * BizUtil.toMoneyPattern(-54321, 1) => "-54,321.0"
	 * </pre>
	 * 
	 * @param d 모든 타입의 숫자
	 * @param scale 소수점 자리수 지정. 음수는 0으로 처리됨.
	 * @return ###,### 형태의 문자열
	 */
	public static String toMoneyPattern(double d, int scale)
	{

		StringBuffer pattern = new StringBuffer("#,##0");

		if (scale > 0)
		{ // 소수점 이하까지 출력하는 경우
			pattern.append('.');
			for (int i = 0; i < scale; i++)
			{
				pattern.append('0');
			}
		}

		DecimalFormat moneyFormatter = new DecimalFormat(pattern.toString());
		return moneyFormatter.format(d);
	}

	/**
	 * <pre>
	 * 숫자를 3자리마다 콤마(,)를 찍어서 화폐 패턴의 문자열로 변환함. 문자열에 소수가 있는 경우 소수가 "0"이라도 그대로 유지됨.
	 * 
	 * 사용 예)
	 * BizUtil.toMoneyPattern("54321") => "54,321"
	 * BizUtil.toMoneyPattern("54321.6") => "54,321.6"
	 * BizUtil.toMoneyPattern("-54321") => "-54,321"
	 * </pre>
	 * 
	 * @param strNum 모든 타입의 숫자형 문자열
	 * @return ###,### 형태의 문자열
	 */
	public static String toMoneyPattern(String strNum)
	{
		if (strNum == null)
		{
			return null;
		}

		String[] parts = PatternUtil.parseNumberString(strNum);

		double d = 0;
		if (Constants.EMPTY.equals(parts[2]))
		{
			d = Double.parseDouble(parts[0] + parts[1]);

			return toMoneyPattern(d, 0);
		} else
		{

			// 정수부에서 구분자 있는 경우 제거
			final String parts1 = parts[1].replaceAll("\\D", Constants.EMPTY);

			d = Double.parseDouble(parts[0] + parts1 + "." + parts[2]);

			int scale = parts[2].length();

			return toMoneyPattern(d, scale);
		}

	}

	/**
	 * <pre>
	 * 입력 문자열이 화폐 패턴인지 여부를 판단함.
	 * 
	 * 사용 예)
	 * BizUtil.isMoneyPattern("54,321") => true
	 * BizUtil.isMoneyPattern("54,321.0") => true
	 * 
	 * </pre>
	 * 
	 * @param str 입력 문자열
	 * @return 3자리마다 콤마가 찍혀진 형태의 문자열일 경우 TRUE
	 */
	public static boolean isMoneyPattern(String str)
	{
		if (str == null || str.length() == 0)
		{
			return false;
		}

		String tempStr = str;
		if (tempStr.startsWith("-"))
		{
			// 음수인 경우는 (-)를 빼고 생각한다.
			tempStr = tempStr.substring(1);
		}

		int dot = tempStr.indexOf('.');
		boolean sosuOk = true;
		if (dot > -1)
		{
			// 소숫점이 있는 경우는 소숫점 이하가 숫자만으로 구성되어 있는지 체크한다.
			String sosu = tempStr.substring(dot + 1);

			sosuOk = sosu.matches("\\d+");

			tempStr = tempStr.substring(0, dot);
		}

		return sosuOk && ("0".equals(tempStr) || tempStr.matches("^[1-9]\\d{0,2}(,\\d{3})*$"));
	}

	/**
	 * <pre>
	 * 
	 * </pre>
	 * 
	 * @param numStr 입력 숫자열
	 * @return
	 */
	private static String[] parseNumberString(String numStr)
	{
		if (numStr == null)
		{
			return null;
		}

		// 좌우 공백 제거
		String tempNumStr = numStr.trim();

		String[] parts = new String[3];
		parts[0] = Constants.EMPTY;
		if (tempNumStr.startsWith("-"))
		{
			parts[0] = "-";
			tempNumStr = tempNumStr.substring(1);
		}

		int dot = tempNumStr.indexOf('.');
		if (dot > -1)
		{
			parts[1] = tempNumStr.substring(0, dot);
			parts[2] = tempNumStr.substring(dot + 1);
		} else
		{
			parts[1] = tempNumStr;
			parts[2] = Constants.EMPTY;
		}
		return parts;
	}
}
