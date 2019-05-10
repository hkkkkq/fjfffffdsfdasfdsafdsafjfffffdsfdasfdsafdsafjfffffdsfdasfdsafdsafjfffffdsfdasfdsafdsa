import java.text.SimpleDateFormat;
import java.util.Date;

public class ComUtil_Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		System.out.println(DateyyyyMMddHHmmssSSS());

	}
	
	public static String DateyyyyMMddHHmmssSSS() {
	    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
	
	    Date date = new Date();
	    String dateString = formatter.format(date);
	    return dateString;
	}
}
