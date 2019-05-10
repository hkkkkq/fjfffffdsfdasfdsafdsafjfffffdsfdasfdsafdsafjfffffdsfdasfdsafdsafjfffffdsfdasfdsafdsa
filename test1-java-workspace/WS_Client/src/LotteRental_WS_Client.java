import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

public class LotteRental_WS_Client {	
    
	public static void main(String[] args)
	{
		LotteRental_WS_Client wr = new LotteRental_WS_Client();
		try
		{
			String[] resultArr = wr.callReprocessWebService();		
			System.out.println("* resultArr[0]="+resultArr[0]);
			System.out.println("* resultArr[1]="+resultArr[1]);
			
			if(resultArr[0].startsWith("SUCCESS"))
			{
				System.out.println("* WebServiceRequester.class: SUCCESS");
				System.exit(0);
			}
			else
			{
				System.out.println("* WebServiceRequester.class: FAIL");
				System.exit(1);
			}			
		}
		catch (Exception e) 
		{
			System.out.println("* WebServiceRequester.class: ERROR");
			e.printStackTrace();
			System.exit(1);
		}
//		String pw = "P@ssw0rz";
//		try {
//			pw = URLEncoder.encode(pw, "utf-8");
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		System.out.println(pw);
		
	}	
	
	public String[] callReprocessWebService()
	 {
		String resultCode = "";
		String resultStr = "";
		// timeout: 5hrs
		int requestTimeout = 1000 * 60 * 10;		
		
		String id = "IF_GWS";
		String pw = "P@ssw0rz";
		
//		String idPw = Base64Coder.encodeString(id+":"+pw);
		
		
		try
		{
			id = URLEncoder.encode(id,"utf-8");
			pw = URLEncoder.encode(pw,"utf-8");
		}
		catch (UnsupportedEncodingException e1) 
		{
			e1.printStackTrace();
		}
		
		String idPw = id + ":" + pw;
		
		
		String hostStr  = "10.226.201.12";
		int port        = 50000;
//		String epUrlStr = "/dir/wsdl?p=sa/9fd512cd10ca3dbca0ece4fa77a1984d";
//		String epUrlStr = "/XISOAPAdapter/MessageServlet?senderParty=&amp;senderService=KT_QAS&amp;receiverParty=&amp;receiverService=&amp;interface=SD0110_SO&amp;interfaceNamespace=http%3A%2F%2Flotterental.net%2FSD%2FKT";
//		String epUrlStr = "/XISOAPAdapter/MessageServlet?senderParty=&senderService=KT_QAS&receiverParty=&receiverService=&interface=SD0110_SO&interfaceNamespace=http://lotterental.net/SD/KT";
//		String epUrlStr = "http://10.226.201.12:50000/XISOAPAdapter/MessageServlet?senderParty=&senderService=KT_QAS&receiverParty=&receiverService=&interface=SD0110_SO&interfaceNamespace=http%3A%2F%2Flotterental.net%2FSD%2FKT";
		String epUrlStr = "http://"+idPw+"@10.226.201.12:50000/XISOAPAdapter/MessageServlet?senderParty=&senderService=KT_QAS&receiverParty=&receiverService=&interface=SD0110_SO&interfaceNamespace=http://lotterental.net/SD/KT";
//		String epUrlStr = "http://10.226.201.12:50000/XISOAPAdapter/MessageServlet?senderService=KT_QAS&interface=SD0110_SO&interfaceNamespace=http://lotterental.net/SD/KT";
//		String epUrlStr = "http://10.226.201.12:50000/XISOAPAdapter/MessageServlet?channel=KT_QAS:SD0110_SO";
//		String epUrlStr = "/dir/wsdl?WSDL";
//		String epUrlStr = "/dir/wsdl";
		
//		CredentialsProvider provider = new BasicCredentialsProvider();
//		UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(id, pw);
//		provider.setCredentials(AuthScope.ANY, credentials);
		//HttpClient client = HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build();
		
		
		HttpHost host = new HttpHost(hostStr, port);
		HttpPost post = new HttpPost(epUrlStr); //POST 
//		HttpGet post = new HttpGet(epUrlStr);   //GET

//		post.addHeader("Authorization", "Basic "+ idpw);
		post.addHeader("SOAPAction", "http://sap.com/xi/WebService/soap1.1");
		post.addHeader(HttpHeaders.CONTENT_TYPE, ContentType.TEXT_XML.withCharset("UTF-8").toString());	
		RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(requestTimeout).build();
		Document doc = null;
		
		try (CloseableHttpClient client = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).build())
//		try (CloseableHttpClient client = HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build())
		{			
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

			
			MessageFactory factory = MessageFactory.newInstance();
			SOAPMessage message = factory.createMessage(new MimeHeaders(), new ByteArrayInputStream(soapXml.toString().getBytes(Charset.forName("UTF-8"))));			
			HttpEntity entity = getSOAPEntity(message);
			
//			HttpPut put = new HttpPut();
//			put.addHeader("SOAPAction", "http://sap.com/xi/WebService/soap1.1");
//			put.addHeader(HttpHeaders.CONTENT_TYPE, ContentType.TEXT_XML.withCharset("UTF-8").toString());	
//			put.setEntity(entity);
			
			post.setEntity(entity);	
			
			HttpResponse response = client.execute(host, post);	
//			HttpResponse response = client.execute(host, put);
			
			System.out.println("statusCode="+response.getStatusLine().getStatusCode());
			System.out.println("reasonPhrase="+response.getStatusLine().getReasonPhrase());
			
			if(response.getStatusLine().getStatusCode()!=HttpStatus.SC_OK)
			{
				resultCode = "Exception";
				resultStr  = "HTTP Staus is not 200";
				Header[] headers = response.getAllHeaders();
				for(Header header : headers)
				{
					String hName = header.getName();
					String hVal  = header.getValue();
					
					System.out.println("header Name=["+hName+"] value=["+hVal+"]");
				}
			}
			else
			{
				HttpEntity resultEntity = response.getEntity();
				SAXBuilder builder = new SAXBuilder();
				doc = builder.build(resultEntity.getContent());			
				//List<Content> docLst = doc.getContent();
				Element rootElement = doc.getRootElement();
				List<Element> headerBody = rootElement.getChildren();
				
				printElement(headerBody);
//				for(Element e : headerBody)
//				{
//					if("Body".equals(e.getName()))
//					{
//						List<Element> bodyChild = e.getChildren();
//						System.out.println("bodyChild="+bodyChild);
//						for(Element subBody  : bodyChild)
//						{
//							System.out.println("body subBody name="+subBody.getName());
//						}
//					}  
//					else
//					{
//						List<Element> headerChild = e.getChildren();
//						System.out.println("header="+headerChild);
//						for(Element subHeader  : headerChild)
//						{
//							System.out.println("header subBody name="+subHeader.getName());
//						}
//					}
//				}
			}
		}
		catch(Exception e)
		{
			resultCode = "Exception";
			resultStr = e.getMessage();
			e.printStackTrace();
		}		
		return new String[]{resultCode, resultStr};
	}
	
	private void printElement(List<Element> elementLst)
	{
		for(Element e : elementLst)
		{
			System.out.println("name, value=["+e.getName()+"]["+e.getValue()+"]");
			List<Element> child = e.getChildren();
			if(child!=null && child.size()>0)
			{
				printElement(child);
			}
		}
	}
	
	private HttpEntity getSOAPEntity(SOAPMessage message) throws IOException, SOAPException {
		
		HttpEntity entity = null;
		try (ByteArrayOutputStream out = new ByteArrayOutputStream())
		{
			message.writeTo(out);
			entity = new StringEntity(new String(out.toByteArray()));
		}
		return entity;
	}	
	
	private Properties load_file(String PROP_PATH_NAME) {
		Properties props = new Properties();		
		FileInputStream fis = null;
		InputStreamReader isr = null;
		BufferedReader br = null;		
		try
		{
			fis = new FileInputStream(PROP_PATH_NAME);
			isr = new InputStreamReader(fis, "UTF-8");
			br = new BufferedReader(isr);
			props.load(br);			
			br.close(); br=null;
			isr.close(); isr=null;
			fis.close(); fis=null;
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		finally
		{
			if(br!=null)  { try {br.close(); br = null;	} catch (IOException e) {	e.printStackTrace();}  }
			if(isr!=null) { try {isr.close();isr = null;	} catch (IOException e) {	e.printStackTrace();}  }
			if(fis!=null) { try {fis.close();fis = null;	} catch (IOException e) {	e.printStackTrace();}  }
		}			
		return props;
	}
	
	private String DateyyyyMMddHHmmssSSS() {
	    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
	
	    Date date = new Date();
	    String dateString = formatter.format(date);
	    return dateString;
	}
	
	private String DateyyyyMMdd() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");

	    Date date = new Date();
	    String dateString = formatter.format(date);
	    return dateString;
	}
	
	private String DateHHmmssSSS() {
		
	    SimpleDateFormat formatter = new SimpleDateFormat("HHmmssSSS");
	
	    Date date = new Date();
	    String dateString = formatter.format(date);
	    return dateString;
	}
	
	private String getIPAddress() {
		
		String ip = "";
		
		try {
			InetAddress inetAddress = InetAddress.getLocalHost();

			ip = inetAddress.getHostAddress();
			
		} catch (Exception e) {

		} 
		return ip;
	}
	
	private String getHostname() {
		
		String hostname = "";
		
		try {
			InetAddress inetAddress = InetAddress.getLocalHost();
			
			hostname = inetAddress.getHostName();
			
		} catch (Exception e) {

		} 
		return hostname;
	}
	
	private String DateyyyyMMddHHmmss() {
	    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
	
	    Date date = new Date();
	    String dateString = formatter.format(date);
	    return dateString;
	}
	
}
