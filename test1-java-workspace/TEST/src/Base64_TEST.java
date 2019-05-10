import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
import com.sun.org.apache.xml.internal.security.utils.Base64;


public class Base64_TEST {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String encodeStr = "SUZfR1dTOlBhJCR3MHJ6";
		
		try {
			byte[] decode = Base64.decode(encodeStr);
			System.out.println(new String(decode));
		} catch (Base64DecodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
	}

}
