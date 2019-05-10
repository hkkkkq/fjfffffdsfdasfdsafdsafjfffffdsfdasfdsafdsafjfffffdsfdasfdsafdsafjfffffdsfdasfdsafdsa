import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class Test003 {

	public static void main(String[] args) {
		
//		ArrayList<String> a = new ArrayList<String>();
//		
//		a.add("1");
//		a.add("2");
//		a.add("3");
//		a.add("4");
//		
//		for (String i:a) {
//			
//			System.out.println(i);
//		}
//		
//		
//		a.clear();
//		
//		System.out.println(a.size());
//		System.out.println(a);
		
		
		System.out.println(File.pathSeparator);
		System.out.println(File.separator);
		System.out.println(System.getenv());
		System.out.println(System.getProperties());
		System.out.println(System.getProperty("file.separator"));
		
		
		
		if (null != "aaa") {
			System.out.println("1");
		} else {
			System.out.println("2");
		}
		
		
		
		
		//String[] aaa = ["a", "b"];
		String[] bbb = {"a","b"};
		String[] ccc = new String[3];
		String[] ddd = {};
		
		System.out.println(ddd);
		System.out.println(ddd==null);
		
		List<?> a = new ArrayList<String>();
		

		
	}
	
	
}
