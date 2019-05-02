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

/**
 * @author Administrator
 *
 */
public class QrCodeKey
{
	private static final Logger logger = LoggerFactory.getLogger(QrCodeKey.class);

	String key;
	
	private QrCodeKey() {
		
	}
	
	private static class QrCodeKeyHolder {
		
		public static final QrCodeKey INSTANCE = new QrCodeKey();
	}
	
	public static QrCodeKey getInstance() {
		return QrCodeKeyHolder.INSTANCE;
	}
	
	public void setKey(String key) {
		this.key = key;
	}
	
	public String getKey() {
		return this.key;
	}
}
