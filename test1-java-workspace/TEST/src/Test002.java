import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Test002 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
//		String[] s = new String[]{"1","2","3"};
		
//		String a = s[3];
		
//		System.out.println(a);
		
		System.out.println("===START==["+getCurrentTime()+"]");
		
		System.out.println(getTimeDiferrence("2016-11-01 10:00:00.000","2016-11-01 12:00:00.000"));
	}
	
	
	public static String getCurrentTime() {
	    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	
	    Date date = new Date();
	    String dateString = formatter.format(date);
	    return dateString;
	}
	
	
	public static long getTimeDiferrence(String startTime, String endTime) {
	    SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	
	    Date date1 = new Date();
	    Date date2 = new Date();

	    try {
			date1 = formatter1.parse(startTime);
			date2 = formatter1.parse(endTime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 초로 리턴
	    return (date2.getTime()-date1.getTime())/1000;
	    
	}
	

}
