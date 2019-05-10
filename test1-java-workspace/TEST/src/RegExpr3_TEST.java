import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegExpr3_TEST {

	public static void main(String... args) {
		// TODO Auto-generated method stub
		
		Pattern p = Pattern.compile("^(\\w+)-(\\w+)$");
		String data = "won-ho";
			
		Matcher m = p.matcher(data);
		
		System.out.println(m.matches());
		
		if (m.matches()) {
			
			System.out.println(m.group());
			System.out.println(m.groupCount());
			System.out.println(m.group(1));
			System.out.println(m.group(2));
			
		}
		
	}

}
