package com.kt.kkos.common.util;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @FileName  	: MaskingUtil.java
 * @Project   	: mobile BSS 구축
 * @Date      	: 2016. 5. 19.
 * @Author      : 82022925
 * @Description : 암호화 필드 마스킹 처리에 필요한 util
 **/

/**
 * @author User
 *
 */
public class MaskingUtil {
	private static final Logger logger = LoggerFactory.getLogger(MaskingUtil.class);
	
	//이름
	//  - 길이가 2보다 작으면(1자) 마스킹 없이 리턴
	//  - 길이가 3보다 크면 뒤 3글자만 마스킹처리하고 나머지는 유지
	//  - 길이가, 2 또는 3이면 뒤 한 글자만 마스킹처리하고 나머지는 유지
	public static String maskingName(String oValue) {
		if (StringUtil.isEmpty(oValue)) return "";
		if (oValue.length() < 2) return oValue;

        if (oValue.length() > 3) {
        	return getMaskStr("R", oValue, 1, 3, "-");
//			return oValue.substring(0, oValue.length()-3)+"***";  // right 1-3
		} else {
			return getMaskStr("R", oValue, 1, 1, "-");
//			return oValue.substring(0, oValue.length()-1)+"*"; // right 1-1
		}
	}

	//은행 계좌번호
	//  - 길이가 6보다 작으면 마스킹 없이 리턴
	//  - 길이가 6보다 같거나 크면, 앞 6자리만 유지하고, 나머지는 마스킹
	public static String maskingBnkacnNo(String oValue) {
		if (StringUtil.isEmpty(oValue)) return "";
		if (oValue.length() < 6) return oValue;

		return getMaskStr("L", oValue, 7, 0, "-");
//		return StringUtil.rpad(oValue.substring(0, 6), oValue.length(), "*"); // left 7-0
	}

	//신용카드 번호
	//  - 길이가 15보다 작으면 마스킹 없이 리턴
	//  - 길이가 15이면 앞 6자리 유지, 8자리 마스킹, 나머지 유지
	//  = 길이가 15보다 크면 앞 6자리 유지, 9자리 마스킹, 나머지 유지
	public static String maskingCCardNo(String oValue) {
		if (StringUtil.isEmpty(oValue)) return "";
		String tmp1 = oValue.replaceAll("-", "");
		if (tmp1.length() < 15) 
			return oValue;
		else if (tmp1.length() == 15)
			return getMaskStr("L", oValue, 7, 8, "-");
//			return tmp1.substring(0, 4)+"-"+tmp1.substring(4, 6)+"**-****-**"+tmp1.substring(14);  // left 7-8
		else
			return getMaskStr("L", oValue, 7, 9, "-");
//			return  tmp1.substring(0, 4)+"-"+tmp1.substring(4, 6)+"**-****-***"+tmp1.substring(15); // left 7-9
	}

	//주민번호
	//  - 길이가 13보다 작으면 사업자번호(공공기관/법인사업자)로 판단함
	//  - 길이가 13이상이면 앞 7자리 유지 나머지 마스킹
	public static String maskingCustIdfy(String oValue) {
		if (StringUtil.isEmpty(oValue)) return "";
		String tmp1 = oValue.replaceAll("-", "");
		
		//13보다 작으면 사업자번호. 크면 주민번호
		if (tmp1.length() < 13)
		{
			if (oValue.length() < 5) return oValue;
			return getMaskStr("L", oValue, 6, 0, "");
		}
		else
		{
			return getMaskStr("L", oValue, 8, 0, "-");
		}
	}
	
	// 운전면허 마스킹
	//  - 뒤 6자리 마스킹
	public static String maskingDriveLicnsNo(String oValue) {
		if (StringUtil.isEmpty(oValue)) return "";
//		String tmp1 = oValue.replaceAll("-", "");
		
		return getMaskStr("L", oValue, 5, 0, "-");
//		return tmp1.substring(0, 2) + "-" + tmp1.substring(2, 4) + "-" + tmp1.substring(4, 6) + "****-**"; // left 7-0
	}

	//사업자번호
	//  - 앞 5자리만 유지, 나머지는 마스킹
	public static String maskingCorpNo(String oValue) {
		if (StringUtil.isEmpty(oValue)) return "";
		if (oValue.length() < 5) return oValue;

		return getMaskStr("L", oValue, 6, 0, "");
//		return oValue.substring(0, 5)+ "******";
	}

	//핸드폰 번호 (확인 필요)
	//  - 10자리 보다 작으면 마스킹 없이 리턴
	//  - 숫자만 12자리 이고, 전화번호 4번째 자리가 0 이면,, 0 제외하고 마스킹 진행, else 2번째 자리부터 마스킹 작업 진행 (즉, 11자리로 맞춰 마스킹 진행)
	//  - 숫자가 11자리이면, 앞 5자리 유지, 3자리 마스킹, 나머지 유지
	//  - 숫자가 10자리이면, 앞 2자리 유지, 2자리 마스킹, 나머지 유지
	public static String maskingMobileNo(String oValue) {
		if (StringUtil.isEmpty(oValue)) return "";
		String tmp1 = oValue.replaceAll("-", "");
		String tmpStr;
		
		switch (tmp1.length()) {
		case 0 :
		case 1 :
		case 2 :
		case 3 :
		case 4 :
		case 5 :
		case 6 :
		case 7 :
		case 8 :
		case 9 :
			return oValue;
		case 10 :
			return getMaskStr("L", oValue, 6, 2, "-");
		case 11 :
			return getMaskStr("L", oValue, 6, 3, "-");
		case 12 : // 앞 4자리 + 국번 4자리
			if ("0".equals(tmp1.substring(4, 5))) { // 10자리로 변환 : 앞 3자리 + 국번 3자리(국번 부분이 0으로 시작)
				StringBuffer strBuf = new StringBuffer();
				
				strBuf.append(tmp1.substring(1, 4)).append("-");
				strBuf.append(tmp1.substring(5, 8)).append("-");
				strBuf.append(tmp1.substring(8));
				
				return getMaskStr("L", strBuf.toString(), 6, 2, "-");
			} else { // 11자리로 변환 : 지역번호 3자리 + 국번 4자리(국번부분이 0이 아닌 숫자로 시작)
				return getMaskStr("L", oValue.substring(1), 6, 3, "-");
			}
		default :
			return oValue;
		}
		
		
		
		
//		if (tmp1.length() < 10) return oValue;
//
//		if (tmp1.length() == 12) { // 자리수 : 4 + 4 + 4
//			if ("0".equals(tmp1.substring(4, 5))) { // 10자리로 변환 : 앞 3 + 국번 3 + 4 (국번 부분이 0으로 시작)
//				tmp1 = tmp1.substring(1, 4)+tmp1.substring(5, 8)+tmp1.substring(8);
//				return maskingMobileNo(tmp1);
//			} else { // 10자리로 변환 : 앞 3 + 국번 4 + 4  (국번 시작 부분이 0이 아닌 경우)
//				return maskingMobileNo(tmp1.substring(1));
//			}
//		}
//
//		if (tmp1.length() == 11) {
//			return tmp1.substring(0, 3)+"-"+tmp1.substring(3, 5)+"**-*"+tmp1.substring(8);
//		}
//
//		if (tmp1.length() == 10) {
//			return tmp1.substring(0, 3)+"-"+tmp1.substring(3, 5)+"*-*"+tmp1.substring(7);
//		}
//
//		return oValue;
	}
	
	//일반 전화 번호 [확인필요] : 마스킹 프로세스 절차 맞는지?
	//  - 10자리 보다 작으면 마스킹 없이 리턴
	//  - 12자리인 경우 5번째 자리가 0이면, 5번째 자리 빼고, else 1번째 자리부터 마스킹 진행 (즉, 11자리로 마스킹 진행)
	//  - 11자리이면, 5자리 유지, 3자리 마스킹, 나머지 유지
	//  - 10자리이면, 4자리 유지, 3자리 마스킹, 나머지 유지
	//  - 마스킹 하고 나서, 서울이면(002) 앞 0 제거하고, 리턴 (마스킹 유지)
	public static String maskingTelNo(String oValue) {
		if (StringUtil.isEmpty(oValue)) return "";
		
		String tmp1 = oValue.replaceAll("-", "");
		
		switch (tmp1.length()) {
		case 0 :
		case 1 :
		case 2 :
		case 3 :
		case 4 :
		case 5 :
		case 6 :
		case 7 :
		case 8 :
		case 9 :
			return oValue;
		case 10 :
			return getMaskStr("L", oValue, 5, 3, "-");
		case 11 :
			return getMaskStr("L", oValue, 6, 3, "-");
		case 12 : // 지역번호가 4자리 + 국번 4자리
			if ("0".equals(tmp1.substring(4, 5))) { // 10자리로 변환 : 지역번호 3자리 + 국번 3자리(국번이 0으로 시작)
				StringBuffer strBuf = new StringBuffer();
				
				strBuf.append(tmp1.substring(1, 4)).append("-");
				strBuf.append(tmp1.substring(5, 8)).append("-");
				strBuf.append(tmp1.substring(8));
				
				return getMaskStr("L", strBuf.toString(), 5, 3, "-");
			} else { // 11자리로 변환 : 지역번호 3자리 + 국번 4자리(국번이 0이 아닌 숫자로 시작)
				return getMaskStr("L", oValue.substring(1), 6, 3, "-");
			}
		default :
			return oValue;
		}
		
		
//		if (tmp1.length() < 10) return oValue;
//		
//		if (tmp1.length() == 12) { // 지역번호가 4자리
//			if ("0".equals(tmp1.substring(4, 5))) { // 10자리 일때 (0061-0761-1234?)
//				tmp1 = tmp1.substring(1, 4)+tmp1.substring(5, 8)+tmp1.substring(8);
//				return maskingTelNo(tmp1);
//			} else {
//				return maskingTelNo(tmp1.substring(1));
//			}
//		}
//		if (tmp1.length() == 11) { // 국번이 4자리
//			return tmp1.substring(0, 3)+"-"+tmp1.substring(3, 5)+"**-*"+tmp1.substring(8);
//		}else	if (tmp1.length() == 10) { // 국번이 3자리
//			return tmp1.substring(0, 3)+"-"+tmp1.substring(3, 4)+"**-*"+tmp1.substring(7);
//		} 
//		//서울(02) 예외처리
//		if("002".equals(tmp1.subSequence(0, 3))) {
//			return tmp1.substring(1);
//		} 
//		return oValue;
	}
	
	//e-Mail 주소
	//  - 기본이 메일주소 앞 3개 유지하고, @ 앞까지 마스킹
	//  - 메일주소 가 3자리이하인 경우, 맥스 2자리 까지만 유지하며, 마스킹 포함 총 4자리로 리턴
	public static String maskingEmail(String oValue) {
		if (StringUtil.isEmpty(oValue)) return "";
		
		String [] strArray = oValue.split("@");
		StringBuffer strBuf = new StringBuffer();
		
		switch (strArray[0].length()) {
		case 0 :
			strArray[0] = "****";
			break;
		case 1 :
			strArray[0] = strBuf.append(strArray[0]).append("***").toString();
			break;
		case 2 :
			strArray[0] = strBuf.append(strArray[0]).append("**").toString();
			break;
		case 3 :
			strArray[0] = getMaskStr("R", strArray[0], 1, 2, "");
			break;
		default :
			strArray[0] = getMaskStr("R", strArray[0], 1, 3, "");
			break;
		}
		
		return StringUtils.join(strArray, "@");
		
		
//		// 입력값이 email prefix(@앞쪽만) 있는 경우 해당 문자열만 마스킹 처리
//		if (oValue.indexOf("@") == -1) {
//			
//			if ( oValue.length() < 4 ) {
//				String tmp1 = StringUtil.rpad(oValue, 3, "*");
//				if (tmp1.indexOf("*") < 1) tmp1 = tmp1.substring(0, 2);
//				return StringUtil.rpad(tmp1,  4,  "*");
//			}else {
//				return oValue.substring(0, oValue.length() - 3) + "***";
//			}
//		} 
//		// 입력값이 email full text 있는 경우 앞쪽 문자열만 짤라서 전체 full text로 마스킹 처리
//		else {
//		
//			String[] splitLine = null;
//			if (oValue != null) {
//				splitLine = oValue.split("@");
//			}
//	
//			if (splitLine == null || splitLine.length != 2) return oValue;
//	
//			if (splitLine[0].length() < 4) {
//				String tmp1 = StringUtil.rpad(splitLine[0], 3, "*");
//				if (tmp1.indexOf("*") < 1) tmp1 = tmp1.substring(0, 2);
//				return StringUtil.rpad(tmp1, 4, "*")+ "@" + splitLine[1];
//			}
//			return splitLine[0].substring(0, splitLine[0].length()-3) + "***@" + splitLine[1];
//		}
	}
	
	// IMEI, ICCID 마스킹. 입력된 자리수 만큼 처리함.
	public static String maskingAllAsterisk(String oValue) {
		if (StringUtil.isEmpty(oValue)) return "";
		
		// 전체 마스킹하여 리턴
		return getMaskStr("L", oValue, 1, 0, "");
		
//		return StringUtil.rpad("", '*', oValue.length());
	}
	
	// 여권번호. 뒤 4자리를 마스킹 처리 함
	public static String maskingCustPassNo(String oValue) {
		if (StringUtil.isEmpty(oValue)) return "";
		
		return getMaskStr("R", oValue, 1, 4, "-");
		
//		int countOf = oValue.replaceAll("-", "").length();
//		
//		//4자리 이하면 4자리로 마스킹하여 return
//		if (countOf < 5) return "****";
//		
//		return tmp1.substring(0, tmp1.length() - 4) + "****";
	}
	


	
	/**
	 * @param leftOrRight : 마스킹 방향 ('L' : 왼쪽에서 오른쪽방향, 'R' : 오른쪽에서 왼쪽방향)
	 * @param orgString : 마스킹 대상 원본 String
	 * @param beginIdx : 마스킹 시작 위치
	 * @param maskCount : 마스킹 문자 개수
	 * @param excludeLetter : 마스킹 대상에서 제외 문자이며, 마스킹 개수에 포함되지 않음
	 * @see ("L", "1234-567-89", 4, 2, "-") ==> 결과 : "123*-*6-789"
	 * @return String
	 */
	private static String getMaskStr (String leftOrRight, String orgString, int beginIdx, int maskCount, String excludeLetter) {
//		logger.debug("(getMaskStr) Params --- leftOrRight : " + leftOrRight + ", orgString : " + orgString + ", beginIdx : " + beginIdx + ", maskCount : " + maskCount + ", excludeLetter : " + excludeLetter);
		
		/*
		 * string.substring 은 0 부터 시작함에 따라, 시작위치 - 1
		*/
		int subStrIdx = beginIdx - 1;
		int maskingCnt = maskCount;
		String excludeStr = excludeLetter;
		
		/*
		 * 구분자 기준으로 Source String 분리 및 String Array 생성
		 *   - 마스킹 작업 완료 후 결과 리턴 시, String Array join & 중간에 excludeLetter 추가 위해
		*/
		String [] strArray = null;
		if ((excludeStr == null) || (excludeStr.length() == 0)) {
			excludeStr = "";
			strArray = new String [1];
			
			switch (leftOrRight) {
			case "L" :
				strArray[0] = orgString;
				break;
			case "R" :
				strArray[0] = StringUtils.reverse(orgString);
				break;
			default :
				break;
			}
		}
		else {
			switch (leftOrRight) {
			case "L" :
				strArray = orgString.split(excludeStr);
				break;
			case "R" :
				strArray = StringUtils.reverse(orgString).split(excludeStr);
				break;
			default :
				break;
			}
		}
//		logger.debug("(getMaskStr) strArray : " + strArray[0]);
		
		/*
		 * 처리 대상 위치 및 개수 계산 결과가 원본 문자열 보다 큰 경우 마스킹 문자(*) Add 
		*/
		int cntAddLetter = 0;
		
		if (maskingCnt == 0)	cntAddLetter = beginIdx - StringUtils.join(strArray).length();
		else					cntAddLetter = subStrIdx + maskingCnt - StringUtils.join(strArray).length();
		
//		logger.debug("(getMaskStr) cntAddLetter : " + cntAddLetter);

		StringBuffer strBuf = new StringBuffer();
		if (cntAddLetter > 0) {
			switch (leftOrRight) {
			case "L" : // 우측에 마스킹 문자 추가
				strArray[strArray.length-1] = strBuf.append(strArray[strArray.length-1]).append(StringUtils.repeat("*", cntAddLetter)).toString();
//				strArray[strArray.length-1] = strArray[strArray.length-1].concat(StringUtils.repeat("*", cntAddLetter));
				break;
			case "R" : // reverse 후 진행됨에 따라, Array[0] 의 맨 앞부분에 마스킹 문자 추가
				strArray[0] = strBuf.append(StringUtils.repeat("*", cntAddLetter)).append(strArray[0]).toString();
//				strArray[0] = StringUtils.repeat("*", cntAddLetter).concat(strArray[0]);
				break;
			default :
				break;
			}
		}
		
		/*
		 * 마스킹개수(maskingCnt) 가 0 이면,, 끝까지 마스킹 처리
		*/
		if (maskingCnt == 0) {
			maskingCnt = StringUtils.join(strArray).length() - subStrIdx;
		}
		
		/*
		 * 마스킹 처리 (구분자를 제외한 시작 위치부터 개수만큼 마스킹)
		*/
		strBuf.setLength(0);
		
		// 첫 문자부터 마스킹 위치 전까지는 원본 String
		strBuf.append(StringUtils.join(strArray).substring(0, subStrIdx));
		
		// 마스킹 위치부터 마스킹 개수 만큼 "*" 로 등록
		strBuf.append(StringUtils.repeat("*", maskingCnt));
		
		// 나머지 원본 String 으로 StringBuffer append
		strBuf.append(StringUtils.join(strArray).substring(subStrIdx + maskingCnt));
		
		/*
		 * 구분자 기준으로 생성된 String Array 값을 masking 처리된 String 으로 대체
		*/
		int fromIndex = 0;
		for (int ii = 0; ii < strArray.length; ii++) {
			strArray[ii] = strBuf.toString().substring(fromIndex, fromIndex + strArray[ii].length());
			fromIndex += strArray[ii].length();
			
//			logger.debug("(getMaskStr) strArray [" + ii + "] convert result : " + tmpStr + "--> " + strArray[ii]);
		}
		
		// [Debugging] 마스킹 처리된 Result String 조회
//		switch (leftOrRight) {
//		case "L" :
//			logger.debug("(getMaskStr) masking Result : " + orgString + " ---> " + StringUtils.join(strArray, excludeStr));
//			break;
//		case "R" :
//			logger.debug("(getMaskStr) masking Result : " + orgString + " ---> " + StringUtils.reverse(StringUtils.join(strArray, excludeStr)));
//			break;
//		}
		
		/*
		 * masking 데이터로 대체된 String Array를 구분자를 중간에 추가 및 하나의 String 으로 생성 후
		 *  마스킹 방향에 따른 Reverse & 리턴 또는 리턴
		*/
		switch (leftOrRight) {
		case "L" :
			return (StringUtils.join(strArray, excludeStr));
		case "R" :
			return (StringUtils.reverse(StringUtils.join(strArray, excludeStr)));
		default :
			return "";
		}
	}

}
