package com.henc.cdrs.common.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.Random;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.util.CookieGenerator;

/**
 * @author jungmin
 * @project henc.bncp
 * @package henc.bncp.common.util
 * @fileName CommUtil.java
 * @date 2014. 1. 8.
 */
public class CommUtil {

    private static final Logger logger = LoggerFactory.getLogger(CommUtil.class);


    /**
     * @param time
     * @return
     * @Method Name  : changeDateHMtoMM
     * @Date : 2014. 1. 8.
     * @Author : jungmin
     * @Description : 타입값 "0110" 4자리 스트링이 넘어올 경우 시간분을 총 분으로 반환한다.
     */
    public static int changeDateHMtoMM(String time) {

        int mm = 0;

        if (time != null && time != "" && time.length() == 4) {
            int h = Integer.parseInt(time.substring(0, 2)) * 60;
            int m = Integer.parseInt(time.substring(2, 4));
            mm = h + m;
        }

        return mm;
    }

    /**
     * @param mm
     * @return
     * @Method Name  : changeDateMMtoHM
     * @Date : 2014. 1. 8.
     * @Author : jungmin
     * @Description : 총 분 값을 넘기면 시간으로 계산하여 반환 70을 넘기면  '0110' String 변환한다.
     */
    public static String changeDateMMtoHM(int mm) {
        String Hour = mm / 60 + "";
        String Mm = mm % 60 + "";
        if (Hour.length() == 1) Hour = "0" + Hour;
        if (Mm.length() == 1) Mm = "0" + Mm;
        return Hour + Mm;
    }

    /**
     * @param d
     * @return
     * @Method Name  : changeCommaWon
     * @Date : 2013. 8. 8.
     * @Author : jumgmin
     * @Description : 세자리마다 콤마 찍기
     */
    public static String changeCommaWon(String d) {
        java.text.DecimalFormat df = (java.text.DecimalFormat) java.text.NumberFormat.getInstance();
        df = new java.text.DecimalFormat("###,###,###,###");
        int maskMoney = Integer.parseInt(d);
        return df.format(maskMoney);
    }

    /**
     * @param param
     * @return
     * @Method Name  : MD5로 암호화
     * @Date : 2013. 2. 7.
     * @Author : jumgmin
     * @Description :
     */
    public static String makeMD5(String param) {
        StringBuffer md5 = new StringBuffer();

        try {
            byte[] digest = MessageDigest.getInstance("MD5").digest(
                    param.getBytes());

            for (int i = 0; i < digest.length; i++) {
                md5.append(Integer.toString((digest[i] & 0xf0) >> 4, 16));
                md5.append(Integer.toString(digest[i] & 0x0f, 16));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return md5.toString();
    }

    /**
     * @param param
     * @return
     * @Method Name  : SHA-256으로 암호화
     * @Date : 2015. 5. 14.
     * @Author : sanghyun
     * @Description :
     */
    public static String makeSHA256(String param) {
        StringBuffer sha256 = new StringBuffer();

        try {
            byte[] digest = MessageDigest.getInstance("SHA-256").digest(
                    param.getBytes());

            for (int i = 0; i < digest.length; i++) {
                sha256.append(Integer.toString((digest[i] & 0xf0) >> 4, 16));
                sha256.append(Integer.toString(digest[i] & 0x0f, 16));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sha256.toString();
    }

    /**
     * @param count
     * @return
     * @Method Name  : getRandomNumber
     * @Date : 2013. 2. 7.
     * @Author : jumgmin
     * @Description : 인수에 만큼 랜덤숫자 생성 (ex : 122341123)
     */
    public static String getRandomNumber(int count) {
        String randomString = "";
        for (int i = 0; i < count; i++) {
            randomString += String.valueOf((int) (Math.random() * 10));
        }
        return randomString;
    }

    /**
     * @param StringUrl
     * @param startTag
     * @param endTag
     * @return
     * @Method Name  : getXmlTagContent
     * @Date : 2013. 2. 7.
     * @Author : jumgmin
     * @Description : URLConnection 을 사용하여 반환된 xml중 tag안의 content를 가지고 온다.
     */
    public static String getXmlTagContent(String StringUrl, String startTag, String endTag) {
        StringBuffer content = new StringBuffer();
        String xmlTagContent = "";
        try {

            URL url = new URL(StringUrl);
            URLConnection connection = url.openConnection();

            BufferedReader in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));

            String line = null;
            while ((line = in.readLine()) != null) {
                content.append(line);
            }

            in.close();

            xmlTagContent = content.substring(content.indexOf(startTag) + startTag.length(), content.indexOf(endTag));

        } catch (Exception e) {

            content = new StringBuffer("error");

        }


        return xmlTagContent;
    }

    /**
     * @param StringUrl
     * @return
     * @Method Name  : getXmlString
     * @Date : 2013. 2. 7.
     * @Author : jumgmin
     * @Description : URLConnection 을 사용하여 반환된 xml을 String 으로 가져온다.
     */
    public static String getXmlString(String StringUrl) {
        StringBuffer content = new StringBuffer();
        try {

            URL url = new URL(StringUrl);
            URLConnection connection = url.openConnection();

            BufferedReader in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));

            String line = null;
            while ((line = in.readLine()) != null) {
                content.append(line);
            }
            in.close();
        } catch (Exception e) {
            content = new StringBuffer("error");
        }

        return content.toString();
    }

    /**
     * @param str
     * @param len
     * @param addStr
     * @return
     * @Method Name  : lpad
     * @Date : 2014. 1. 22.
     * @Author : jungmin
     * @Description : 오라클 LPAD
     */
    public static String lpad(String str, int len, String addStr) {
        String result = str;
        int templen = len - result.length();

        for (int i = 0; i < templen; i++) {
            result = addStr + result;
        }

        return result;
    }

    /**
     * @param v
     * @param d
     * @return
     * @Method Name  : evl
     * @Date : 2013. 1. 30.
     * @Author : jumgmin
     * @Description : Map 에 담겨진 파라미터와 디폴트 값을 넣어 해당 데이터가 null 이거나 데이터가 빈값일 경우 default String 을 리턴한다.
     */
    public static String evl(Object strValue, String strDefault) {
        String strReturn = "";

        if (strValue == null) {
            strReturn = strDefault;

        } else if ("".equalsIgnoreCase(strValue.toString())) {
            strReturn = strDefault;

        } else {
            strReturn = strValue.toString();
        }

        return strReturn;
    }

    /**
     * @param response
     * @param message
     * @return
     * @throws Exception
     * @Method Name  : setMessageCookie
     * @Date : 2013. 2. 1.
     * @Author : jumgmin
     * @Description : 메시지를 쿠키에 담는다.
     */
    public static HttpServletResponse setMessageCookie(HttpServletResponse response, String message) throws Exception {

        Cookie killCookie = new Cookie("SCRIPT_MSG", null);
        killCookie.setMaxAge(0);
        response.addCookie(killCookie);

        CookieGenerator cg = new CookieGenerator();
        cg.setCookieName("SCRIPT_MSG");
        cg.setCookiePath("/");
        cg.addCookie(response, URLEncoder.encode(message, "UTF-8"));

        //Cookie cookie = new Cookie("SCRIPT_MSG", URLEncoder.encode(message, "UTF-8"));
        //response.addCookie(cookie);

        return response;
    }

    /**
     * @param response
     * @param url
     * @return
     * @throws Exception
     * @Method Name  : setUrlCookie
     * @Date : 2013. 2. 1.
     * @Author : jumgmin
     * @Description : url을 쿠키에 담는다.
     */
    public static HttpServletResponse setUrlCookie(HttpServletResponse response, String url) throws Exception {
        Cookie killCookie = new Cookie("SCRIPT_URL", null);
        killCookie.setMaxAge(0);
        response.addCookie(killCookie);

        CookieGenerator cg = new CookieGenerator();
        cg.setCookieName("SCRIPT_URL");
        cg.setCookiePath("/");
        cg.addCookie(response, URLEncoder.encode(url, "UTF-8"));

        //Cookie cookie = new Cookie("SCRIPT_URL", URLEncoder.encode(url, "UTF-8"));
        //response.addCookie(cookie);

        return response;
    }


    /**
     * @param gubun
     * @param seq
     * @param maxlength
     * @return
     * @Method Name  : fillZero
     * @Date : 2013. 2. 14.
     * @Author : jumgmin
     * @Description : "0"으로 채운다
     */
    public static String fillZero(String gubun, String seq, int maxlength) {
        String temp = gubun + seq;
        String zeroString = "";
        for (int i = temp.length(); i < maxlength; i++) {
            zeroString += "0";
        }
        return gubun + zeroString + seq;
    }

    /**
     * 쿠키를 저장하는 메쏘드
     *
     * @param res         HttpServletResponse
     * @param cookieName  쿠키명
     * @param cookieValue 쿠키값
     */
    static public void setCookie(HttpServletResponse res, String cookieName, String cookieValue) {
        Cookie saveCookie = new Cookie(cookieName, cookieValue);
        saveCookie.setPath("/");
        res.addCookie(saveCookie);
    }

    /**
     * 쿠키를 저장하는 메쏘드
     *
     * @param res         HttpServletResponse
     * @param cookieName  쿠키명
     * @param cookieValue 쿠키값
     * @param maxAge      쿠키저장시간
     * @throws UnsupportedEncodingException
     */
    static public void setCookie(HttpServletResponse res, String cookieName, String cookieValue, int maxAge) throws UnsupportedEncodingException {
        Cookie saveCookie = new Cookie(cookieName, java.net.URLEncoder.encode(cookieValue, "euc-kr"));
        saveCookie.setMaxAge(maxAge);
        saveCookie.setPath("/");
        res.addCookie(saveCookie);
    }


    /**
     * 쿠키값을 가져오는 메쏘드
     *
     * @param req        HttpServletRequest
     * @param cookieName 쿠키명
     * @return String                쿠키값
     */
    static public String getCookie(HttpServletRequest req, String cookieName) {
        Cookie[] readCookie = req.getCookies();

        String cookieValue = "";

        if (readCookie != null)
            for (int i = 0; i < readCookie.length; i++) {
                if (readCookie[i].getName().equals(cookieName)) {
                    try {
                        cookieValue = URLDecoder.decode(readCookie[i].getValue(), "euc-kr");
                    } catch (UnsupportedEncodingException e) {
                    }
                }
            }

        return cookieValue;
    }

    /**
     * 전체 쿠키값 비우기
     *
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     */
    static public void clearCookie(HttpServletRequest req, HttpServletResponse res) {
        Cookie[] cookies = req.getCookies();
        for (int i = 0; i < cookies.length; i++) {
            cookies[i].setMaxAge(0);
            cookies[i].setPath("/");
            res.addCookie(cookies[i]);
        }
    }

    /**
     * 요일 가져오기
     * <pre>
     * Calendar cal = Calendar.getInstance();
     * String dow = CommUtil.getDayOfWeek(cal);
     *
     *
     * </pre>
     *
     * @param cal Calendar
     * @return String                요일(월요일, 화요일...)
     */
    static public String getDayOfWeek(Calendar cal) {

        String dayOfWeek = "";
        int i = cal.get(Calendar.DAY_OF_WEEK);
        switch (i) {
            case Calendar.SUNDAY:
                dayOfWeek = "일요일";
                break;
            case Calendar.MONDAY:
                dayOfWeek = "월요일";
                break;
            case Calendar.TUESDAY:
                dayOfWeek = "화요일";
                break;
            case Calendar.WEDNESDAY:
                dayOfWeek = "수요일";
                break;
            case Calendar.THURSDAY:
                dayOfWeek = "목요일";
                break;
            case Calendar.FRIDAY:
                dayOfWeek = "금요일";
                break;
            case Calendar.SATURDAY:
                dayOfWeek = "토요일";
                break;
            default:
                dayOfWeek = "";
                break;
        }

        return dayOfWeek;
    }


    /**
     * BASE64 encode
     */
    public static String encode(String str) {
        if (str == null)
            return "";
        else
            return Base64.encode(str.getBytes());
    }

    /**
     * BASE64 decode
     */
    public static String decode(String str) {
        if (str == null) {
            return "";
        } else {
            byte bytes[] = Base64.decode(str);
            return new String(bytes);
        }
    }

    /**
     * 암화화 알고리즘이 BCrypt 형태인지 여부
     */
    public static boolean pwdBCryptCheck(String pwd) {
        if (pwd.startsWith("$2a$12$") && pwd.length() == 60) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * null check
     *
     * @param str
     * @return null = ""
     */
    public static String nullCheck(String str) {
        if (str == null) {
            return "";
        } else {
            return str;
        }
    }


    /**
     * null check
     *
     * @param str
     * @return null = true
     */
    public static boolean nullCheckB(String str) {
        if (str == null || "".equals(str)) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * 숫자인지 구분
     *
     * @param String s
     * @return Boolean
     * @throws Exception
     */
    public static boolean isNumeric(String s) {
        try {
            Double.parseDouble(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static String getRandomString(int length) {
        char[] charaters = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
        StringBuffer sb = new StringBuffer();
        Random rn = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(charaters[rn.nextInt(charaters.length)]);
        }
        return sb.toString();
    }

}
