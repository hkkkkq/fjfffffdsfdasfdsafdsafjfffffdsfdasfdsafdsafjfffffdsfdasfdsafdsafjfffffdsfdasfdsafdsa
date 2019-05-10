
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;



public class LotteHttp {	
	
	public LotteHttp() {
		super();
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		System.out.println("file encoding : " +System.getProperty("file.encoding"));
		
		LotteHttp sendServlet = new LotteHttp();
		sendServlet.call();	
		
		}
	
	


	public String call() {
		
		String id = "IF_GWS";
		String pw = "P@ssw0rz";
		
		StringBuffer soapXml = new StringBuffer();		
		
		soapXml.append("<SOAP-ENV:Envelope												  ");   
		soapXml.append("    xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\"> ");  
		soapXml.append("    <SOAP-ENV:Header/>                                            ");    
		soapXml.append("    <SOAP-ENV:Body>                                               ");    
		soapXml.append("        <wstxns1:MT_SD0110_KT                                     ");    
		soapXml.append("            xmlns:wstxns1=\"http://lotterental.net/SD/KT\">       ");  
		soapXml.append("            <SVCCONTID>I</SVCCONTID>                              ");    
		soapXml.append("            <ODERNO>0000000001</ODERNO>                           ");    
		soapXml.append("            <RCPDAY/>                                             ");    
		soapXml.append("            <DLVRPAMDAY/>                                         ");    
		soapXml.append("            <CHGTYPE/>                                            ");    
		soapXml.append("            <PRODTYPE>01</PRODTYPE>                               ");    
		soapXml.append("            <MODELNM/>                                            ");    
		soapXml.append("            <TYPE>01</TYPE>                                       ");    
		soapXml.append("            <MODELMAKR>03</MODELMAKR>                             ");    
		soapXml.append("            <CUSTNM/>                                             ");    
		soapXml.append("            <DLVRCUSTCNTPLC/>                                     ");    
		soapXml.append("            <DLVRCUSTCNTPLC1/>                                    ");    
		soapXml.append("            <DLVZNZIPCD/>                                         ");    
		soapXml.append("            <DLVZNADR/>                                           ");    
		soapXml.append("            <RMARK/>                                              ");    
		soapXml.append("            <RCPRNM/>                                             ");    
		soapXml.append("            <RCPRCNTPLC>01066554212</RCPRCNTPLC>                  ");    
		soapXml.append("        </wstxns1:MT_SD0110_KT>                                   ");    
		soapXml.append("    </SOAP-ENV:Body>                                              ");    
		soapXml.append("</SOAP-ENV:Envelope>                                              ");   
		
		String testXml = soapXml.toString();
		
		StringBuffer sb = new StringBuffer();
		long before = System.currentTimeMillis(); // 

		try {
			
			
			System.out.println("================= Call HttpSendClient =========================");
			
			URL anyURL = new URL("http://10.226.201.12:50000/dir/wsdl?p=sa/9fd512cd10ca3dbca0ece4fa77a1984d");
			
			URLConnection conn = anyURL.openConnection();

			System.out.println("opend connection !! ");

			
			HttpURLConnection anyConnection = (HttpURLConnection) conn;
//			anyConnection.setRequestMethod("POST");
			anyConnection.setRequestMethod("GET");
			anyConnection.setDoOutput(true);
			anyConnection.setDoInput(true);

			System.out.println("create PrintWriter !! ");
			
			PrintWriter output = new PrintWriter(anyConnection.getOutputStream());
			output.println(testXml);			
			
			output.close();

			System.out.println("closed PrintWriter !! ");

			Scanner sc = new Scanner(new InputStreamReader(anyConnection.getInputStream()));			

			while (sc.hasNextLine()) {
				
				
				String scLine = sc.nextLine();
				
				if(scLine != null)
				{
					System.out.println("===sc run=["+scLine+"]");
				}
				else
				{
					System.out.println("===sc run is null");
				}
				
				sb.append(scLine);
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
		String recvData = sb.toString();
		
		System.out.println("recvData=["+recvData+"]");
		System.out.println("recvDataByte=["+recvData.getBytes().length+"]");
		long processingTime = after - before;  
		System.out.println(processingTime + "ms");   

		return sb.toString();

	}
}
