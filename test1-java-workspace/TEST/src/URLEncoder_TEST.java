import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;



public class URLEncoder_TEST {

	public static void main(String[] args) throws UnsupportedEncodingException {
		// TODO Auto-generated method stub

		String data = "Pa$$w0rz";
		String encoded = "";
		String decoded = "";
		
		System.out.println("data: " + data);
		
		encoded =  URLEncoder.encode("Pa$$w0rz","utf-8");
		
		System.out.println("encoded: " + encoded);
		
		decoded = URLDecoder.decode(encoded, "utf-8");
		
		System.out.println("decoded: " + decoded);
		
		
	}

}
