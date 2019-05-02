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
package com.kt.kkos.common.enc;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Administrator
 *
 */
public class KeyEncrypt
{
	
	private static final Logger logger = LoggerFactory.getLogger(KeyEncrypt.class);

	public static String getEncSHA256(String str) {
		
		StringBuilder sbuf = new StringBuilder();
		
		MessageDigest mDigest;
		try
		{
			mDigest = MessageDigest.getInstance("SHA-256");
			mDigest.update(str.getBytes());
			
			byte[] msgStr = mDigest.digest();
			for ( int i = 0 ; i < msgStr.length ; i++ ) {
				
				byte tmpStrByte = msgStr[i];
				String tmpEncStr = Integer.toString((tmpStrByte & 0xff) + 0x100, 16).substring(1);
				
				sbuf.append(tmpEncStr);
			}
		} catch (NoSuchAlgorithmException e)
		{
			// NoSuchAlgorithm 없을 일이 없음.
			logger.debug("SHA-256 알고리즘 없음.");
		}
		
		/* 중간 32자리를 키로 사용함 */
		return sbuf.substring(16, 48);
	}
}
