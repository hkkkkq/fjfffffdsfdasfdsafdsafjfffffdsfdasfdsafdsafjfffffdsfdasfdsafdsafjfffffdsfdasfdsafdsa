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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.initech.safedb.SimpleSafeDB;
import com.initech.safedb.sdk.exception.SafeDBSDKException;

/**
 * @author Administrator
 *
 */
public class SafeDBUtil
{
	
	private static final Logger logger = LoggerFactory.getLogger(SafeDBUtil.class);
	
	private SimpleSafeDB safeDB = null;
	
	private static final String USER_NAME 		= "SAFEDB";
	private static final String TABLE_NAME 		= "SAFEDB.POLICY";
	private static final String COLUMN_NAME 	= "URL_BSS_KNOTE";
	
	private SafeDBUtil() {
		
		safeDB = SimpleSafeDB.getInstance();
		if ( !safeDB.login() ) {
			
			safeDB.getSafeDBConfigMgr().isLoginCheck();
		}
	}

	
	/**
	 * 
	 * @author Administrator
	 *
	 */
	private static class SafeDBUtilHolder {
		
		public static final SafeDBUtil INSTANCE = new SafeDBUtil();
	}
	
	/**
	 * <pre>
	 * SafeDBUtil 객체 반환 Singlton Method
	 * </pre>
	 *
	 * @return
	 */
	public static SafeDBUtil getInstance() {
		
		return SafeDBUtilHolder.INSTANCE;
	}
	
	/**
	 * <pre>
	 * 컬럼 타입 지정없는 암호화. URL_BSS_KNOTE 로 고정사용.
	 * </pre>
	 *
	 * @param plainString
	 * @return
	 */
	public String encrypt(String plainString) {
		
		try {
			
			return safeDB.encryptString(USER_NAME, TABLE_NAME, COLUMN_NAME, plainString);
		} 
		catch (SafeDBSDKException e) {
			
			logger.error("암복호화 시도중 에러가 발생하였습니다.");
		}
		
		return "";
	}
	
	/**
	 * <pre>
	 * 컬럼 타입 지정 암호화.
	 * </pre>
	 *
	 * @param columnName
	 * @param plainString
	 * @return
	 */
	public String encrypt(String columnName, String plainString) {
		
		try {
			
			return safeDB.encryptString(USER_NAME, TABLE_NAME, columnName, plainString);
		} 
		catch (SafeDBSDKException e) {
			
			logger.error("암복호화 시도중 에러가 발생하였습니다.");
		}
		
		return "";
	}
	
	
	/**
	 * <pre>
	 * 컬럼 타입 지정없는 복호화. URL_BSS_KNOTE 로 고정사용.
	 * </pre>
	 *
	 * @param encryptedString
	 * @return
	 */
	public String decrypt(String encryptedString) {
		
		try {
			
			return new String(safeDB.decryptString(USER_NAME, TABLE_NAME, COLUMN_NAME, encryptedString));
		} 
		catch (SafeDBSDKException e) {
			
			logger.error("암복호화 시도중 에러가 발생하였습니다.");
		}
		
		return "";
	}
	
	/**
	 * <pre>
	 * 컬럼 타입 지정 복호화.
	 * </pre>
	 *
	 * @param columnName
	 * @param encryptedString
	 * @return
	 */
	public String decrypt(String columnName, String encryptedString) {
		
		try {
			
			return new String(safeDB.decryptString(USER_NAME, TABLE_NAME, columnName, encryptedString));
		} 
		catch (SafeDBSDKException e) {
			
			logger.error("암복호화 시도중 에러가 발생하였습니다.");
		}
		
		return "";
	}
}
