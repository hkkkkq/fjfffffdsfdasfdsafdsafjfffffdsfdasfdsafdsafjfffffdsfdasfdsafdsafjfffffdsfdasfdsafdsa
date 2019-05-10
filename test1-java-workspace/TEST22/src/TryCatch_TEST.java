
public class TryCatch_TEST {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
			
		System.out.println(test());
		

	}
	
	
	public static String test() {
		
		try {
			
			String a = null;
			a.length();
			
		} catch (Exception e) {
			//e.printStackTrace();
			System.out.println("aaaaaaaaaaaaaaaaaaaaa");
			return "bbbbbbbbbbbbbbbbbbbbbbbbb";
		} 
		
		try {
			
			String a = null;
			a.length();
			
		} catch (Exception e) {
			//e.printStackTrace();
			System.out.println("cccccccccccccccccccccccccccccc");
			return "dddddddddddddddddddddddddddddd";
		} finally {
			
			System.out.println("eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
			
			try {
				System.out.println("fffffffffffffffffffffffffffffff");

//				String a = null;	
//				a.length();
				
			} catch (Exception e) {
				//e.printStackTrace();
				System.out.println("ggggggggggggggggggggggggggggggggggggg");
				return "hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh";
			}
		}

		return "iiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii";
		
	}
	

}
