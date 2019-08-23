package com.henc.cdrs.common.util;

/**
 * Created by beoms(lbg6848@sdcit.co.kr) on 2019-07-26
 * Corporation : SDCIT(주)
 * HomePage : http://www.sdcit.co.kr
 */
public class FileUtil {

    private static final String BASE64_PREFIX = "data:image/png;base64,";

    public static byte[] decodeBase64ToBytes(String imageString) {
        if (imageString.startsWith(BASE64_PREFIX)) {
//            return Base64.decode(imageString);
            return java.util.Base64.getDecoder().decode(imageString.substring(BASE64_PREFIX.length()));


        } else {
            throw new IllegalStateException("it is not base 64 string");
        }
    }

    public String encodeBytesToBase64(byte[] sign){
        return Base64.encode(sign);
    }

}
