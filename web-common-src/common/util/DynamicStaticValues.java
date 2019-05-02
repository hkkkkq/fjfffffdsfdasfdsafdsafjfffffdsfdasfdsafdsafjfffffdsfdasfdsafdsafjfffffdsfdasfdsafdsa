package com.kt.kkos.common.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kt.kkos.exception.ITLBusinessException;

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
* @FileName com.kt.kkos.common.util.DynamicStaticValues.java
* @Creator 82023121
* @CreateDate 2018.04.09
* @Version 0.1
* @Description WatchService를 활용한 dynamic static 변수 활용
*  
*/

/*
* ********************소스 수정 이력****************************************
* DATE                Modifier                      Description
* *********************************************************************
* 2018.04.09          전원호(82023121)               최초작성(/jboss/properties/kkos/dynamics/kkos_dynamic.properties)
* *********************************************************************
*/

public class DynamicStaticValues {
	
	private static final Logger logger = LoggerFactory.getLogger(DynamicStaticValues.class);
	
	private static final AtomicReference<DynamicStaticValues> instanceRef = new AtomicReference<DynamicStaticValues>();
	private static final ReentrantLock singletonLock = new ReentrantLock();
	
	private static String kkosServiceCallBlockYn = "N";
	private static String kkosBmonCallBlockYn = "N";
	private static String kkosServiceCallBlockChnlTypeYn = "N";
	private static String kkosServicePermittedChnlTypeList = "PA,KO";		
	private static String kkosServiceNacBlockYn = "N";
	private static String kkosServiceMnpBlockYn = "N";
	private static String kkosServiceHcnBlockYn = "N";
	private static String kkosServiceRslBlockYn = "N";
	
	private DynamicStaticValues() {

		String sysTarget = StaticValues.getInstance().getSysTarget();
		logger.debug("### sysTarget: " + sysTarget + " ###");
		
		if (!"local".equals(sysTarget)) {
			WatchService();
		}
	}
	
	public static final DynamicStaticValues getInstance() {
		DynamicStaticValues instance  = instanceRef.get();
		if(instance==null) {
			try {
				singletonLock.lock();
				if( (instance = instanceRef.get()) == null) { 
					instance = new DynamicStaticValues();
					instanceRef.set(instance);
				}
			} finally {
				singletonLock.unlock();
			}
		}
		return instance;
	}
	
	public void WatchService() {
		
		ExecutorService service = Executors.newSingleThreadExecutor();
		
		service.submit(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				logger.info("### WatchService() Started ###");
				
				//Path path = Paths.get("e:/test/properties");
				Path path = Paths.get("/jboss/properties/kkos/dynamics");
				WatchService watchService = null;
				WatchKey watchKey = null;
				
				try {
					
					watchService = path.getFileSystem().newWatchService();
					
					//path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY);
					path.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);
					
					while (true) {
						
						//watchKey = watchService.take();
						watchKey = watchService.poll(30, TimeUnit.SECONDS);
						
						if (watchKey == null) {
							logger.info("### WatchService() polling every 30s ###");
							continue;
						}
						
						Thread.sleep(100);
						
						for (WatchEvent<?> event:watchKey.pollEvents()) {
							
							Path fileName = (Path) event.context();
							
//							logger.debug("****************************");
//							logger.debug(event.count());
//							logger.debug(event.toString());
//							logger.debug(event.context());
//							logger.debug((Path) event.context());
//							logger.debug(event.kind());
//							logger.debug(event.kind().name());
//							logger.debug(event.kind().toString());
//							logger.debug(event.kind().type());
									
							if (fileName.endsWith("kkos_dynamic.properties")) {
								
								//logger.debug("### 1 ###");
								
								if (event.kind().toString().equals("ENTRY_MODIFY")) {
									
									logger.info("### event.kind().toString(): " + event.kind().toString() + " ###");
									
									Properties props = new Properties();
									FileInputStream fis = null;
									InputStreamReader isr = null;
									BufferedReader br = null;
									
									try {
						
										fis = new FileInputStream(path.toFile() + File.separator + "kkos_dynamic.properties");
										isr = new InputStreamReader(fis, "UTF-8");
										br = new BufferedReader(isr);
										props.load(br);
										
										// set static values from the properties contents
										kkosServiceCallBlockYn = props.getProperty("kkos.service.call.block.yn", "N").toUpperCase();
										logger.info("### kkosServiceCallBlockYn: " + kkosServiceCallBlockYn + " ###");
										
										kkosBmonCallBlockYn = props.getProperty("kkos.bmon.call.block.yn", "N").toUpperCase();
										logger.info("### kkosBmonCallBlockYn: " + kkosBmonCallBlockYn + " ###");
										
										kkosServiceCallBlockChnlTypeYn = props.getProperty("kkos.service.call.block.chnlType.yn", "N").toUpperCase();
										logger.info("### kkosServiceCallBlockChhnlTypeYn: " + kkosServiceCallBlockChnlTypeYn + " ###");
										
										kkosServicePermittedChnlTypeList = props.getProperty("kkos.service.permitted_chnlType.list", "PA,KO").toUpperCase();
										logger.info("### kkosServicePermittedChhnlTypeList: " + kkosServicePermittedChnlTypeList + " ###");

										kkosServiceNacBlockYn = props.getProperty("kkos.service.nac.block.yn", "N").toUpperCase();
										logger.info("### kkosServiceNacBlockYn: " + kkosServiceNacBlockYn + " ###");
										
										kkosServiceMnpBlockYn = props.getProperty("kkos.service.mnp.block.yn", "N").toUpperCase();
										logger.info("### kkosServiceMnpBlockYn: " + kkosServiceMnpBlockYn + " ###");
										
										kkosServiceHcnBlockYn = props.getProperty("kkos.service.hcn.block.yn", "N").toUpperCase();
										logger.info("### kkosServiceHcnBlockYn: " + kkosServiceHcnBlockYn + " ###");
										
										kkosServiceRslBlockYn = props.getProperty("kkos.service.rsl.block.yn", "N").toUpperCase();
										logger.info("### kkosServiceRslBlockYn: " + kkosServiceRslBlockYn + " ###");
										
										br.close(); br=null;
										isr.close(); isr=null;
										fis.close(); fis=null;
										
										
									} catch (Exception e) {
										ITLBusinessException.printErrorStack(e);
									} finally {
										if(br!=null)  { try {br.close(); br = null;} catch (IOException e) { ITLBusinessException.printErrorStack(e);} }
										if(isr!=null) { try {isr.close(); isr = null;} catch (IOException e) { ITLBusinessException.printErrorStack(e);}  }
										if(fis!=null) { try {fis.close(); fis = null;} catch (IOException e) { ITLBusinessException.printErrorStack(e);}  }
									}
									
								} else {
									continue;
								}								
							}		
						}
						
						boolean valid = watchKey.reset();
						logger.debug("valid: " + valid);
						if (!valid) {
							break;
						}
					}
					
				} catch (Exception e) {
					ITLBusinessException.printErrorStack(e);
				} finally {
					
					try {
						//watchKey.cancel();
						watchService.close();
						instanceRef.set(null);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						ITLBusinessException.printErrorStack(e);
					}
				}
			}
		});
		
	}

	// Getters
	public String getKkosServiceCallBlockYn() {
		return kkosServiceCallBlockYn;
	}
	
	public String getKkosBmonCallBlockYn() {
		return kkosBmonCallBlockYn;
	}
	
	public String getKkosServiceCallBlockChnlTypeYn() {
		return kkosServiceCallBlockChnlTypeYn;
	}
	
	public String getKkosServicePermittedChnlTypeList() {
		return kkosServicePermittedChnlTypeList;
	}

	public String getKkosServiceNacBlockYn() {
		return kkosServiceNacBlockYn;
	}

	public String getKkosServiceMnpBlockYn() {
		return kkosServiceMnpBlockYn;
	}

	public String getKkosServiceHcnBlockYn() {
		return kkosServiceHcnBlockYn;
	}

	public String getKkosServiceRslBlockYn() {
		return kkosServiceRslBlockYn;
	}
	
//	public static void main(String[] args) throws InterruptedException {
//		
//		logger.debug(DynamicStaticValues.getInstance().getKkosServiceBlockYn());
//		
//		Thread.sleep(10000);
//		
//		logger.debug(DynamicStaticValues.getInstance().getKkosServiceBlockYn());
//	}
	
}
