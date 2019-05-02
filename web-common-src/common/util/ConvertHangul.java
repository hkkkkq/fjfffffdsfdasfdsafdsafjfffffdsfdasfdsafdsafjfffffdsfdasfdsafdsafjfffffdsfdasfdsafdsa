package com.kt.kkos.common.util;

import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConvertHangul {
    String sCharset;
    String tCharset;
    
    private static final Logger logger = LoggerFactory.getLogger(ConvertHangul.class);
    
    public ConvertHangul() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    public ConvertHangul(String sourceCharset, String targetCharset) {
        sCharset = sourceCharset;
        tCharset = targetCharset;
    }
    
    

    

    public static String encodeToHexString(String str, String charset) {
        StringBuilder sb = new StringBuilder();
        try {
            byte[] key_source = str.getBytes(charset);
            for (byte b : key_source) {
                String hex = String.format("%02x", b).toUpperCase();
                sb.append("%");
                sb.append(hex);
            }
        } catch (UnsupportedEncodingException e) {
            logger.error("UnsupportedEncodingException Error : ", e);
        }// Exception
        return sb.toString();
    }
    
    public static byte[] encode(String str, String charset) throws UnsupportedEncodingException {
        return str.getBytes(charset);
    }
    
    public static byte[] encode(byte[] str, String charset) throws UnsupportedEncodingException {
        return new String(str).getBytes(charset);
    }
    
    public static byte[] decode(byte[] str, String sourceCharset, String targetcharset) throws UnsupportedEncodingException {
        return new String(str, sourceCharset).getBytes(targetcharset);
    }
    
    public static byte[] decode(byte[] str, int length, String sourceCharset, String targetcharset) throws UnsupportedEncodingException {
        byte[] temp = new byte[length];
        for(int i = 0; i < length; i ++) {		// NOPMD
            temp[i] = str[i];
        }
        return new String(temp, sourceCharset).getBytes(targetcharset);
    }
    
    public String decode(String encodeStr) {
        String decodeStr = null;
        try {
            decodeStr = new String(new String(encodeStr.getBytes(), sCharset).getBytes(tCharset));
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            logger.error("UnsupportedEncodingException Error : ", e);
        }
        return decodeStr;
    }

    public static String decodeFromHexString(String hex, String charset) {
        byte[] bytes = new byte[hex.length() / 3];
        int len = hex.length();
        for (int i = 0; i < len;) {
            int pos = hex.substring(i).indexOf("%");
            if (pos == 0) {
                String hex_code = hex.substring(i + 1, i + 3);
                bytes[i / 3] = (byte) Integer.parseInt(hex_code, 16);
                i += 3;
            } else {
                i += pos;
            }
        }
        try {
            return new String(bytes, charset);
        } catch (UnsupportedEncodingException e) {
            logger.error("UnsupportedEncodingException Error : ", e);
        }// Exception
        return "";
    }

    public static byte[] changeCharset(String str, String charset) throws UnsupportedEncodingException {
        try {
            byte[] bytes = str.getBytes(charset);
            return new String(bytes, charset).getBytes();
        } catch (UnsupportedEncodingException e) {
            logger.error("UnsupportedEncodingException Error : ", e);
            throw e;
        }// Exception
    }
    
    public static byte[] changeCharset(byte[] str, String charset) throws UnsupportedEncodingException {
        try {
            byte[] bytes = new String(str).getBytes(charset);
            return new String(bytes, charset).getBytes();
        } catch (UnsupportedEncodingException e) {
            logger.error("UnsupportedEncodingException Error : ", e);
            throw e;
        }// Exception
    }
}
