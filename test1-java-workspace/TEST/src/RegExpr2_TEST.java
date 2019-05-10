import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegExpr2_TEST {

	public static void main(String... args) {
		// TODO Auto-generated method stub
		
		Pattern p = Pattern.compile("abc");
		String data = "abc abc abc";
			
		Matcher m = p.matcher(data);
		
		// true => "Pattern"
		System.out.println(m.find());
		// false => "^Pattern$"
		System.out.println(m.matches());
		// true => "^Pattern"
		System.out.println(m.lookingAt());
		

	}

}
