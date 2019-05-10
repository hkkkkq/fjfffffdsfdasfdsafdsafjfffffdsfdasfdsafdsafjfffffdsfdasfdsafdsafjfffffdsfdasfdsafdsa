
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;


public class HttpSendClient {	
	
	public HttpSendClient() {
		super();
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		System.out.println("file encoding : " +System.getProperty("file.encoding"));
		
		HttpSendClient sendServlet = new HttpSendClient();
		sendServlet.call();	
		
		}
	
	/* 
* InfiniLink HTTP IN - HTTP OUT �⺻ �׽�Ʈ 
* http://192.168.1.87:58087/http/txGrp_R_Http_0002*/
	
	private static final String TEST_XML =
				"<?xml version='1.0' encoding='utf-8'?>"
				+"<res:Req_Body_1078_XML  xmlns:res=\"urn:infinilink:com.tmax.ifl.tc_1078.txGrp_R_1078.tx_L_A_00_1078\">"				
				+"<res:txGrp_code>11111</res:txGrp_code>"
				+"<res:tx_code>0002</res:tx_code>"
//				+"<res:req_msg></res:req_msg>"
				+"<res:res_code>request</res:res_code>"
				+"</res:Req_Body_1078_XML>";	


	public String call() {

		StringBuffer sb = new StringBuffer();
		long before = System.currentTimeMillis(); // 

		try {
			
			
			System.out.println("================= Call HttpSendClient =========================");
			
//			URL anyURL = new URL("http://192.168.105.110:8088/http/txGrp_R_1078");
			URL anyURL = new URL("http://192.168.1.89:38087/http/txGrp_R_1078");
			
			URLConnection conn = anyURL.openConnection();

			System.out.println("opend connection !! ");

			HttpURLConnection anyConnection = (HttpURLConnection) conn;
			anyConnection.setRequestMethod("POST");
//			anyConnection.setRequestMethod("GET");
			anyConnection.setDoOutput(true);
			anyConnection.setDoInput(true);

			System.out.println("create PrintWriter !! ");
			
			PrintWriter output = new PrintWriter(anyConnection.getOutputStream());

			System.out.println("=======================send data is=============");
			output.println(TEST_XML);			
			System.out.println(TEST_XML);
			
			output.close();

			System.out.println("closed PrintWriter !! ");

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

		long after = System.currentTimeMillis();  
		
		System.out.println("=======================received data is=============");
		System.out.println(sb.toString());
		long processingTime = after - before;  
		System.out.println(processingTime + "ms");   

		return sb.toString();

	}
}
