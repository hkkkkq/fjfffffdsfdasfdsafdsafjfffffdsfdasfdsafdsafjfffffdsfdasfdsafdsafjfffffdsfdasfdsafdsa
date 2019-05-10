import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegExpr1_TEST {

	public static void main(String... args) {
		// TODO Auto-generated method stub
		
		Pattern p = Pattern.compile("^\\d{6}-[1-4]\\d{6}$");
//		String pattern = "^\\d{6}-[1-4]\\d{6}$";
		
		System.out.print("Input(Registeration No): ");
		Scanner scanner = new Scanner(System.in);
		
		String registerNo = scanner.nextLine();
		
		Matcher m = p.matcher(registerNo);
		
		if (m.find()) {
			System.out.println("Pattern is matched!!!");
			System.out.println(m.replaceAll("111111-2222222"));
		} else {
			System.out.println("Pattern is NOT matched!!!");
		}
		
//		boolean i = Pattern.matches(pattern, registerNo);
//		
//		if (i) {
//			System.out.println("Pattern is matched!!!");
//			
//		} else {
//			System.out.println("Pattern is NOT matched!!!");
//		}
		

	}

}
