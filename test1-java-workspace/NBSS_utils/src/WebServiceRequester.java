import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

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


public class WebServiceRequester {	
    
	public static void main(String[] args)
	{
		String bizTxId = args[0];        
		String upmuSystemId =  args[1]; 	
		String filePattern =  args[2];
		WebServiceRequester wr = new WebServiceRequester();
		try
		{
			String[] resultArr = wr.callReprocessWebService(bizTxId, upmuSystemId, filePattern);
			
			String result = "";
			
			result = resultArr[0];
			
			if("SUCCESS".equals(result))
			{
				System.out.println("* WebServiceRequester.class: SUCCESS");
				System.exit(0);
			}
			else if ("FAIL".equals(result))
			{
				System.out.println("* WebServiceRequester.class: FAIL");
				System.exit(1);
			} else {
				System.out.println("* WebServiceRequester.class: ERROR");
				System.exit(1);
			}
		}
		catch (Exception e) 
		{
			System.out.println("* WebServiceRequester.class: ERROR");
			e.printStackTrace();
			System.exit(1);
		}
	}	
	
	private String[] callReprocessWebService(String bizTxId, String upmuSystemId, String filePattern)
	 {
		
		String returnCode = "";
		String returnString = "";
		// timeout: 5hrs
		int requestTimeout = 18000000;		
		String PROP_PATH_NAME = "/app/batch_all/script/ws_controlM_Batch_call.properties";
		Properties prop = load_file(PROP_PATH_NAME);		
		String hostStr  = prop.getProperty("wsCom01.host."+upmuSystemId);
		int portStr     = Integer.parseInt(prop.getProperty("wsCom01.port."+upmuSystemId));
		String epUrlStr = prop.getProperty("wsCom01.epUrl."+upmuSystemId);		
		HttpHost host = new HttpHost(hostStr, portStr);
		HttpPost post = new HttpPost(epUrlStr);		
		//post.addHeader("SOAPAction", "nbss.common.commonBusiness_01.ws_common_01.ws_controlM_Batch_call.ws_controlM_Batch_call");
		post.addHeader(HttpHeaders.CONTENT_TYPE, ContentType.TEXT_XML.withCharset("UTF-8").toString());	
		RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(requestTimeout).build();
		Document doc = null;
		
		try (CloseableHttpClient client = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).build())
		{			
			StringBuffer soapXml = new StringBuffer();			
//			soapXml.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:urn=\"urn:infinilink:nbss.common.commonBusiness_01.ws_common_01.ws_controlM_Batch_call\">");
//			soapXml.append("   <soapenv:Header/>");
//			soapXml.append("   <soapenv:Body>");
//			soapXml.append("      <urn:MSG_I_req_body>");
//			soapXml.append("         <urn:BIZTX_ID>"+bizTxId+"</urn:BIZTX_ID>");
//			soapXml.append("         <urn:FILE_PATTERN>"+filePattern+"</urn:FILE_PATTERN>");
//			soapXml.append("      </urn:MSG_I_req_body>");
//			soapXml.append("   </soapenv:Body>");
//			soapXml.append("</soapenv:Envelope>");
			
			soapXml.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:biz=\"http://bizhub.kt.com\">");
				soapXml.append("   <soapenv:Header>");
				soapXml.append("      <biz:commonHeader>");
				soapXml.append("         <biz:appName></biz:appName>");
				soapXml.append("         <biz:svcName></biz:svcName>");
				soapXml.append("         <biz:fnName></biz:fnName>");
				soapXml.append("         <biz:fnCd>l_ws_controlM_Batch_call</biz:fnCd>");
				soapXml.append("         <biz:globalNo>" + "gw_contolMbatch" + DateyyyyMMddHHmmssSSS() + "</biz:globalNo>");
				soapXml.append("         <biz:chnlType>GW</biz:chnlType>");
				soapXml.append("         <biz:envrFlag></biz:envrFlag>");
				soapXml.append("         <biz:trFlag>T</biz:trFlag>");
				soapXml.append("         <biz:trDate>" + DateyyyyMMdd() + "</biz:trDate>");
				soapXml.append("         <biz:trTime>" + DateHHmmssSSS() + "</biz:trTime>");
				soapXml.append("         <biz:clntIp>" + getIPAddress() + "</biz:clntIp>");
				soapXml.append("         <biz:responseType></biz:responseType>");
				soapXml.append("         <biz:responseCode></biz:responseCode>");
				soapXml.append("         <biz:responseLogcd></biz:responseLogcd>");
				soapXml.append("         <biz:responseTitle></biz:responseTitle>");
				soapXml.append("         <biz:responseBasc></biz:responseBasc>");
				soapXml.append("         <biz:responseDtal></biz:responseDtal>");
				soapXml.append("         <biz:responseSystem></biz:responseSystem>");
				soapXml.append("         <biz:userId>91124708</biz:userId>");
				soapXml.append("         <biz:realUserId></biz:realUserId>");
				soapXml.append("         <biz:filler></biz:filler>");
				soapXml.append("         <biz:langCode></biz:langCode>");
				soapXml.append("         <biz:orgId>SPT8050</biz:orgId>");
				soapXml.append("         <biz:srcId>l_ws_controlM_Batch_call</biz:srcId>");
				soapXml.append("         <biz:curHostId>" + getHostname() + "</biz:curHostId>");
				soapXml.append("         <biz:lgDateTime>" + DateyyyyMMddHHmmss() + "</biz:lgDateTime>");
				soapXml.append("         <biz:tokenId></biz:tokenId>");
				soapXml.append("         <biz:cmpnCd></biz:cmpnCd>");
				soapXml.append("         <biz:lockType></biz:lockType>");
				soapXml.append("         <biz:lockId></biz:lockId>");
				soapXml.append("         <biz:lockTimeSt></biz:lockTimeSt>");
				soapXml.append("         <biz:businessKey></biz:businessKey>");
				soapXml.append("         <biz:arbitraryKey></biz:arbitraryKey>");
				soapXml.append("         <biz:resendFlag>N</biz:resendFlag>");
				soapXml.append("         <biz:phase></biz:phase>");
				soapXml.append("      </biz:commonHeader>");
				soapXml.append("   </soapenv:Header>");
				soapXml.append("   <soapenv:Body>");
				soapXml.append("      <biz:MSGIReqBody>");
				soapXml.append("         <biz:bizHeader>");
				soapXml.append("            <biz:orderId></biz:orderId>");
				soapXml.append("            <biz:cbSvcName></biz:cbSvcName>");
				soapXml.append("            <biz:cbFnName></biz:cbFnName>");
				soapXml.append("         </biz:bizHeader>");
				soapXml.append("         <biz:MSGIReqSubBody>");
				soapXml.append("            <biz:BIZTX_ID>" + bizTxId + "</biz:BIZTX_ID>");
				soapXml.append("            <biz:FILE_PATTERN>" + filePattern + "</biz:FILE_PATTERN>");
				soapXml.append("         </biz:MSGIReqSubBody>");
				soapXml.append("      </biz:MSGIReqBody>");
				soapXml.append("   </soapenv:Body>");
				soapXml.append("</soapenv:Envelope>");
//			
			MessageFactory factory = MessageFactory.newInstance();
			SOAPMessage message = factory.createMessage(new MimeHeaders(), new ByteArrayInputStream(soapXml.toString().getBytes(Charset.forName("UTF-8"))));			
			HttpEntity entity = getSOAPEntity(message);
			post.setEntity(entity);			
			HttpResponse response = client.execute(host, post);	
			if(response.getStatusLine().getStatusCode()!=HttpStatus.SC_OK) {
				returnCode = "ERROR";
				returnString = "HTTP Staus is not 200";
				System.out.println("* RETURN_CODE: " + returnCode);
				System.out.println("* RETUNR_STR: " + returnString);
			} else {
				HttpEntity resultEntity = response.getEntity();
				SAXBuilder builder = new SAXBuilder();
				doc = builder.build(resultEntity.getContent());			
				//List<Content> docLst = doc.getContent();
				Element rootElement = doc.getRootElement();		
				
//				List<Element> childLst = rootElement.getChildren();			
//				Element MSG_RETURN = childLst.get(0).getChildren().get(0);		
//				Element RETURN_CODE = MSG_RETURN.getChildren().get(0);
//				Element RETUNR_STR = MSG_RETURN.getChildren().get(1);		
//				returnCode = RETURN_CODE.getValue();
//				returnString = RETUNR_STR.getValue();	
				
				List<Element> headerBody = rootElement.getChildren();			
				for(Element e : headerBody)
				{
					if("Body".equals(e.getName()))
					{
						List<Element> bodyChild = e.getChildren();
						for(Element subBody  : bodyChild)
						{
							if("MSGIResBody".equals(subBody.getName()))
							{
								List<Element> subBodyChild = subBody.getChildren();
								for(Element result : subBodyChild)
								{
									if("MSGIResSubBody".equals(result.getName()))
									{
										List<Element> result2 = result.getChildren();
										for(Element result3 : result2)
										{
											if("RETURN_CODE".equals(result3.getName()))
											{
												returnCode = result3.getValue();
											}
											if("RETUNR_STR".equals(result3.getName()))
											{
												returnString = result3.getValue();
											}
											
											if(returnCode != "" && returnString != "")
											{
												break;
											}
										}
									}
									else
									{
										continue;
									}
								} // end for
							}
							else
							{
								continue;
							}
						} // end for
					}  
					else
					{
						continue;
					}
				}
				
				System.out.println("* RETURN_CODE: " + returnCode);
				System.out.println("* RETUNR_STR: " + returnString);
			}
			
		}
		catch(Exception e)
		{
			returnCode = "ERROR";
			returnString = e.getMessage();
			System.out.println("* RETURN_CODE: " + returnCode);
			System.out.println("* RETUNR_STR: " + returnString);
			e.printStackTrace();
		}		
		return new String[]{returnCode, returnString};
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
