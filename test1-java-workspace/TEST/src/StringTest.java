import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class StringTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

//		String logFileName = "eo_cdrm1_svr1_rte_2016050615.log";
//		
//		String YYYYMMDD = logFileName.substring(logFileName.indexOf("rte_")+4, logFileName.indexOf("rte_")+4+8);
//		
//		System.out.println(YYYYMMDD);
//		
//		int year = Integer.parseInt(YYYYMMDD.substring(0, 4));
//		int month = Integer.parseInt(YYYYMMDD.substring(4, 6));
//		int date = Integer.parseInt(YYYYMMDD.substring(6, 8));
//		
//		System.out.println(year);
//		System.out.println(month);
//		System.out.println(date);
//
//		Calendar cal = Calendar.getInstance();
//		
//		//cal.set(year, month-1, date);
//		
//		cal.add(Calendar.DATE, -3);
//		
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
//		
//		String currDate = sdf.format(cal.getTime());
//		
//		System.out.println(currDate);
//		
//		
//		System.out.println("========================");
//		//String test1 = "20,,금융기관 연체자,";
//		String test1 = ",해피,";
//		String[] test2 = test1.split("\\,",4);
//		
//		System.out.println(test2.length);
//		for (int i=0;i<test2.length;i++) {
//			System.out.println("["+i+"]"+test2[i]);
//		}
		
		
//		String kkk = null;
//		System.out.println("####" + kkk.isEmpty());
		
		
		String reqData = "001F000132303136303831392D3038353833320022FB00004B544600424F535300";
					
		// hexString => byte[]
		byte[] b =  new BigInteger(reqData, 16).toByteArray();
		
		System.out.println(b);
		System.out.println(new String(b));
		
		// byte[] => hexString
		String hexString = new BigInteger(b).toString(16);
			
		System.out.println(hexString);
		
		
		System.out.println("========================");
		
		String bbb = "/applog/infinilink/message_log_by_biztx/io_cdrm1/20161026";
		
		String[] ccc = bbb.split("\\/");
		
		System.out.println(ccc[ccc.length-1]);
		
		String ddd = "C:\\applog\\infinilink\\message_log_by_biztx\\io_cdrm1\\20161026";
		
		String[] eee = ddd.split("\\\\");
		
		System.out.println(eee[eee.length-1]);
		
		System.out.println("========================");
		
		String fff = "aaa.bbb.ccc.ddd.eee.l_1234";
		String[] ggg = fff.split("\\.");
		String hhh = ggg[ggg.length-1];
		if (hhh.startsWith("l_")) {
			hhh=hhh.substring(2, hhh.length());
		}
		System.out.println(hhh);

		String dddd = "20170608";
		String ddddd = dddd.substring(6);
		System.out.println(ddddd);
			
		System.out.println("*****************************");
		//java.lang.StringIndexOutOfBoundsException: String index out of range
//		String inboundReq = "a";		
//		System.out.println(inboundReq.substring(1, 2));	

		System.out.println("Test1?.log");

	}

}
