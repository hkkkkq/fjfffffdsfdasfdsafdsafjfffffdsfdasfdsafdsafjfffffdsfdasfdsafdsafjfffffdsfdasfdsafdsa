package com.kt.kkos.common.util;


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
* @FileName com.kt.kkos.common.util.StaticValues.java
* @Creator 82023121
* @CreateDate 2018.04.09
* @Version 0.1
* @Description static 변수 선언 활용
*  
*/

/*
* ********************소스 수정 이력****************************************
* DATE                Modifier                      Description
* *********************************************************************
* 2018.04.09          전원호(82023121)               최초작성
* *********************************************************************
*/

public class StaticValues {

	private static StaticValues staticValues;
	
	// Definitions
	private static String instanceName = System.getProperty("jboss.node.name", "kkosSvr00");
	private static String sysTarget = System.getenv("SYSTarget");
	
	private StaticValues() {
		//readProperties();
	}
	
	public static final StaticValues getInstance() {
		
		if (staticValues == null) {
			staticValues = new StaticValues();
		}
		
		return staticValues;
	}
	
//	private void readProperties() {
//		Properties props = new Properties();
//		File f = new File("/jboss/properties/kkos/...");
//		FileInputStream fis = null;
//		InputStreamReader isr = null;
//		BufferedReader br = null;
//		
//		try {
//
//			fis = new FileInputStream(f);
//			isr = new InputStreamReader(fis, "UTF-8");
//			br = new BufferedReader(isr);
//			props.load(br);
//			
//			br.close(); br=null;
//			isr.close(); isr=null;
//			fis.close(); fis=null;
//			
//			
//		} catch (Exception e) {
//			ITLBusinessException.printErrorStack(e);
//		} finally {
//			if(br!=null)  { try {br.close(); br = null;} catch (IOException e) { ITLBusinessException.printErrorStack(e); } }
//			if(isr!=null) { try {isr.close(); isr = null;} catch (IOException e) { ITLBusinessException.printErrorStack(e); } }
//			if(fis!=null) { try {fis.close(); fis = null;} catch (IOException e) { ITLBusinessException.printErrorStack(e); } }
//		}
//	}
	
	// Getters
	public String getInstanceId() {
		return instanceName;
	}
	
	public String getSysTarget() {
		return sysTarget;
	}
	
//	public static void main(String[] args) {
//		System.out.println(StaticValues.getInstance().getInstanceId());
//		System.out.println(StaticValues.getInstance().getSysTarget());
//		
//		Properties p = System.getProperties();
//		
//		Enumeration e = p.propertyNames();
//		
//		while(e.hasMoreElements()) {
//			String key = (String)e.nextElement();
//			String value = p.getProperty(key);
//			System.out.println(key + "=" + value);
//		}
//		
//	}
}
