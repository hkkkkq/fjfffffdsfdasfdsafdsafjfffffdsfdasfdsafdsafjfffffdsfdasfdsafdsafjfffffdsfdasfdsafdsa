/*
 *  MIMP(KT Medical Information Mediation Platform) version 1.0
 *
 *  Copyright ⓒ 2019 kt corp. All rights reserved.
 *
 *  This is a proprietary software of kt corp, and you may not use this file except in
 *  compliance with license agreement with kt corp. Any redistribution or use of this
 *  software, with or without modification shall be strictly prohibited without prior written
 *  approval of kt corp, and the copyright notice above does not evidence any actual or
 *  intended publication of such software.
 */
package com.kt.athn.configuration;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.EnvironmentPBEConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.kt.athn.common.AdminConstants;

@Configuration
public class JasyptConfig{

	/**
	 * 작 성 자 : 박정구
	 * 작성일자 : 2019. 3. 21.
	 * 설    명 : application.yml 암복호화 Bean
	 * @return
	 */
	@Bean("jasyptStringEncrptor")
	public StandardPBEStringEncryptor StringEnc() {
		 StandardPBEStringEncryptor enc = new StandardPBEStringEncryptor();
		 EnvironmentPBEConfig conf = new EnvironmentPBEConfig();
		 conf.setPassword(AdminConstants.JASYPT_KEY);
		 conf.setAlgorithm("PBEWithMD5AndDES");
		 enc.setConfig(conf);
		 return enc;
	 }
}
