
import java.util.Calendar;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;



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
* @FileName com.kt.kkos.common.util.GlobalNoGenerator.java
* @Creator 82023121
* @CreateDate 2018.04.09
* @Version 0.1
* @Description KKOS-ESB 통신 시 사용할 globalNo 32자리 생성
*  
*/

/*
* ********************소스 수정 이력****************************************
* DATE                Modifier                      Description
* *********************************************************************
* 2018.04.09          전원호(82023121)               최초작성
* *********************************************************************
*/


public class GlobalNoGenerator {
	
	//private static final Logger logger = LoggerFactory.getLogger(GlobalNoGenerator.class);
	
	private final AtomicInteger seq = new AtomicInteger(0);
	private final AtomicLong beforeMilli = new AtomicLong(0);
	private final ReentrantLock lock = new ReentrantLock();
	private static final AtomicReference<GlobalNoGenerator> instanceRef = new AtomicReference<GlobalNoGenerator>();
	private static final ReentrantLock singletonLock = new ReentrantLock();
	
//	@Value("${jboss.node.name}") //kkosSvr11
	// was 실행시 -D 옵션으로 정의(server) 되어 있으면, System.getProperty 로 조회 해야 함
	private String instanceName = System.getenv("jboss.node.name") == null ? System.getProperty("jboss.node.name") : System.getenv("jboss.node.name");
	
	private GlobalNoGenerator() {
	}
	
	
	public static final GlobalNoGenerator getInstance() {
		GlobalNoGenerator instance  = instanceRef.get();
		if(instance==null) {
			try {
				singletonLock.lock();
				if( (instance = instanceRef.get()) == null) { 
					instance = new GlobalNoGenerator();
					instanceRef.set(instance);
				}
			} finally {
				singletonLock.unlock();
			}
		}
		return instance;
	}
	
	
	/**
	 * 
	 * @return 32byte Global_No:  서버인스턴스ID_yyyyMMddHHmmssSSS_중복방지 4자리 키
	 */
	public String getGlobalNo() {
		Long now = null;
		int seqInt = 0;
		try {
			// 잠금
			lock.lock();
			now = Long.valueOf(DateFormatUtils.format(Calendar.getInstance(), "yyyyMMddHHmmssSSS"));
			//앞에 발생된 날짜값과 같지 않으면
			if(!beforeMilli.compareAndSet(now, now)){
//				System.out.println(String.format("=====> Before : [%s] => Now : [%s] => [%b]"
//						,beforeMilli.get(), now, now.equals(beforeMilli.get())));
				//seq값을 초기화
				seq.set(0);
				beforeMilli.set(now);
				//System.out.println("now : " + now);
			}
			seqInt = seq.getAndIncrement();
			//100이면 0으로 set
			seq.compareAndSet(10000, 0);	
		}
		finally {
			// 잠금해제
			lock.unlock();
		}
		
		// 32byte kkosSvr11_20180409092310777_0001
		String result = instanceName + "_" + now.toString() + "_" + StringUtils.leftPad(String.valueOf(seqInt), 4, '0');
		//logger.info("### globalNo: " + result + " ###");
		return result;
	}
	
	public static void main(String[] args) {
		
		System.out.println(GlobalNoGenerator.getInstance().getGlobalNo());
		System.out.println(DateFormatUtils.format(Calendar.getInstance(),"yyyy-MM-dd HH:mm:ss.SSS"));
		
	}
}
