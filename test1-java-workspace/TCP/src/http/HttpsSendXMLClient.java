package http;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

import org.xml.sax.InputSource;

public class HttpsSendXMLClient {
	
	
public HttpsSendXMLClient() {
		super();

		// TODO Auto-generated constructor stub
		
	}

	public static void main(String[] args) {
		
		System.out.println("안될경우 Install Java를 돌려야함");
		HttpSendClient sendServlet = new HttpSendClient();
		String returnStr = sendServlet.call();
		System.out.println("returnStr=["+returnStr+"]");
		

	}
	
//	private static final String TEST_XML =  "0000013801380999infinilinkTCPADTQA";
//	private static final String TEST_XML =  "111111111122222222223333333333";
	private static final String TEST_XML =  "<?xml version=\"1.0\" encoding=\"UTF-8\"?><IF_HTTP_HEADER xmlns=\"urn:infinilink:http.https.httpssoap\" xmlns:dd=\"urn:infinilink:http.https.httpssoap\"><dd:DATA1>1111111111</dd:DATA1><dd:DATA2>2222222222</dd:DATA2><dd:DATA3>3333333333</dd:DATA3></IF_HTTP_HEADER>";
	
	/** 
	 * @return
	 */
	public String call() {

		StringBuffer sb = new StringBuffer();
		long before = System.currentTimeMillis(); // 

		try {
			
			InputStream inputStream = new ByteArrayInputStream(TEST_XML.toString().getBytes("UTF-8"));			
			InputSource inputSource = new InputSource(inputStream);

			String str = new String(TEST_XML);
			
			URL anyURL = new URL("https://192.168.71.40:29999/http/https");
			HttpsURLConnection anyConnection = (HttpsURLConnection) anyURL.openConnection();
			anyConnection.setRequestMethod("POST");

			anyConnection.setDoOutput(true);
			anyConnection.setDoInput(true);


			
			PrintWriter output = new PrintWriter(anyConnection.getOutputStream());

			
//			output.print(TEST_XML);
			output.write(TEST_XML);
			output.flush();
			output.close();

			Scanner sc = new Scanner(new InputStreamReader(anyConnection.getInputStream()));
			
			while (sc.hasNextLine()) {

				sb.append(sc.nextLine());
			}

			sc.close();

		} catch (MalformedURLException me) {
			System.out.println("MalformedURLException: " + me);
			me.printStackTrace();
		} catch (IOException ioe) {

			System.out.println("IOException: " + ioe);
			ioe.printStackTrace();

		} catch (Exception exception) {
			System.out.println("Exception: " + exception);

			exception.printStackTrace();
		}

		return sb.toString();

	}
}
