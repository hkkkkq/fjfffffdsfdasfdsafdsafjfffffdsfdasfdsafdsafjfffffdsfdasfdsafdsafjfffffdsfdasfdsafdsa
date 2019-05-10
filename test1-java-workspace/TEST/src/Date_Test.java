import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class Date_Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

//		Calendar cal = Calendar.getInstance();
//		
//		//cal.add(Calendar.DATE, Integer.parseInt(minusDay));
//		
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
//		
//		String baseDate_String = sdf.format(cal.getTime());
//		
//		System.out.println(baseDate_String);
		
		Calendar cal = Calendar.getInstance();
		
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd");
		
		String now = sdf1.format(cal.getTime());
		System.out.println("Start Time: " + now);
		
		cal.add(Calendar.DATE, Integer.parseInt("-5"));
		
		String baseDate_String = sdf2.format(cal.getTime());
		
		System.out.println("baseDate_String: " + baseDate_String);
		
		int baseDate_Int = Integer.parseInt(baseDate_String);
		
		System.out.println("baseDate_Int: " + baseDate_Int);
		
		String YYYYMMDD_String = "eo_fnc1_svr2_rte_20160908.log.bak";
		
		YYYYMMDD_String = YYYYMMDD_String.substring(YYYYMMDD_String.indexOf("rte_")+4, YYYYMMDD_String.indexOf("rte_")+4+8);
		
		System.out.println("YYYYMMDD_String: " + YYYYMMDD_String);
		
		System.out.println("=======================");
		cal = Calendar.getInstance();
		SimpleDateFormat sdf3 = new SimpleDateFormat("dd");
		String dd = sdf3.format(cal.getTime());
		System.out.println(dd);
		
		System.out.println("=======================");
		
		Timestamp ts = Timestamp.valueOf("2017-02-21 11:28:12.877000000");
		System.out.println(ts);
		
		cal.setTime(new Date(ts.getTime()));	
		cal.add(Calendar.DATE, -1);
		System.out.println(sdf3.format(cal.getTime()));
		cal.add(Calendar.DATE, 2);
		System.out.println(sdf3.format(cal.getTime()));
		
		
		System.out.println("=======================");
		
		Calendar cal1 = Calendar.getInstance();
		SimpleDateFormat sdf4 = new SimpleDateFormat("M월 d일 HH:mm");
		SimpleDateFormat sdf5 = new SimpleDateFormat("M월 d일 yyyy");
		System.out.println(sdf4.format(cal1.getTime()));
		System.out.println(sdf5.format(cal1.getTime()));
		
		
		
		
		try {
			String s_fileSize = "124368773";
			long fileSize = Long.parseLong(s_fileSize);
			// 124368773 byte 전송 시 put 데이터세션 FIN_WAIT1 상태로 210초 전송한다.
			long sleepTime = (long)(fileSize * 0.0017 + 60000);
			System.out.println(sleepTime);
			Thread.sleep(sleepTime);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
