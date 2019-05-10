//package test;

import java.net.URLEncoder;
import java.lang.*;
import java.util.*;
import java.io.*;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;

public class HttpClientTest {

	public static void main(String[] args) throws Exception {
		// Get target URL


/*	String url = 
			"http://165.141.7.179:8000/sap/xi/adapter_plain" +
			"?service=MMEPR_DEV" +
			"&namespace=" + URLEncoder.encode("http://kumhotire.co.kr/SAP/MM/OutsideStock", "euc-kr") +
			"&interface=OutsideStock_out" +
			"&sap-user=eachttp" +
			"&sap-password=eai";
*/
        
//		String id = 

		String url = 
			"http://165.141.7.179:8000/sap/xi/adapter_plain" +
			"?service=KHNET_DEV" +
			"&namespace=" + URLEncoder.encode("http://kumhotire.co.kr/SAP/SD/SalesOrderChg", "EUC-KR") +
			"&interface=SalesOrderChg_out" +
			"&sap-user=eachttp" +
			"&sap-password=eai";

        /*URL ���� PostMethod-Post���, getMethod-get���*/
		PostMethod post = new PostMethod(url);

		post.setRequestHeader("Content-type", "application/xml");

		/*String body = 
			"<?xml version='1.0' encoding='UTF-8'?>" +
			"<ns0:OutsideStock_MMEPR xmlns:ns0='http://kumhotire.co.kr/SAP/MM/OutsideStock'>" +
			"      <VENDOR_CODE>1</VENDOR_CODE>" +
			"   <ITEM_CODE>1</ITEM_CODE>" +
				"      <PLANT>1</PLANT>" +
				"      <SEARCH_YEAR>1</SEARCH_YEAR>" +
				"     <SEARCH_MONTH>1</SEARCH_MONTH>" +
			"</ns0:OutsideStock_MMEPR>";
		*/
		
        String body = 
			"<?xml version='1.0' encoding='UTF-8'?>" +
			"<ns0:SalesOrderChg_KHNET  xmlns:ns0='http://kumhotire.co.kr/SAP/SD/SalesOrderChg'>" +
			 "<ITEM>" +
			"<VBELN>0000000862</VBELN>" +
				"<POSNR>000010</POSNR>" +
				"<MATNR>000000000001071213</MATNR>" +
				"<KWMENG>1</KWMENG>" +
				"<XDATS />" +
				"<XTIMS />" +
			"</ITEM>" +
			"</ns0:SalesOrderChg_KHNET>";



		RequestEntity requestEntity = new StringRequestEntity(body);
		post.setRequestEntity(requestEntity);


		// Get HTTP client
		HttpClient httpclient = new HttpClient();
		// Execute request
		try {


			//int result = httpclient.executeMethod(new String(post.getBytes("ISO8859_1"),"KSC5601")); 

			int result = httpclient.executeMethod(post); 
			// Display status code
			System.out.println("Response status code: " + result);
			// Display response
			System.out.println("Response body: ");
			String returnString =  new String(post.getResponseBodyAsString().getBytes("ISO8859_1"),"UTF-8");
			System.out.println("returnString"+returnString);
			//System.out.println(post.getResponseBodyAsString());
		} catch (Exception e) {
                System.out.println("error: " + e);
        } finally {
			// Release current connection to the connection pool once you are done
			post.releaseConnection();
		}
	}
}