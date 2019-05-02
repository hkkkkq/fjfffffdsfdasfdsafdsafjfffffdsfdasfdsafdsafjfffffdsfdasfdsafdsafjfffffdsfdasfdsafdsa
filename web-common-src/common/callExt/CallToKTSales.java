package com.kt.kkos.common.callExt;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import org.apache.http.HttpHeaders;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.kt.kkos.common.util.ObjectConverter;
import com.kt.kkos.exception.ITLBusinessException;
import com.kt.kkos.itl.model.common.KtSalesIfAdviceDTO;

@Service
public class CallToKTSales {
	private static final Logger logger = LoggerFactory.getLogger(CallToKTSales.class);
	
	@Value("${ktSalesGetAdviceUrl}")
	private String ktSalesGetAdviceUrl;
	
	/**
	 * JSONObject Output 암호화 및 마스킹 처리
	 * @param JSONObject
	 * @param Class
	 * @throws MalformedURLException 
	 */
	public KtSalesIfAdviceDTO getAdviceInfo(String trtSeq) {
		/*
		 * Request URL Define
		*/
		logger.debug("(getAdviceData) Inquiry Key : " + trtSeq);
		
		/*
		 * Setting Parameters
		*/
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		
		params.add(new BasicNameValuePair("trt_seq", trtSeq));
		
		/*
		 * HttpRequest Create & Execute
		*/
		CloseableHttpClient httpClient = null;
		try {
			SSLContextBuilder sslContextBuilder = new SSLContextBuilder();
			sslContextBuilder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
			
			// SSL 과 실 도메인 일치 여부 체크 SKIP
			HostnameVerifier hostnameVerifierAllowAll = new HostnameVerifier() {
				@Override
				public boolean verify(String hostname, SSLSession sslSession) {
					// TODO Auto-generated method stub
					return true;
				}
			};
			
			SSLConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory (sslContextBuilder.build(), hostnameVerifierAllowAll);
			httpClient = HttpClientBuilder.create().setSSLSocketFactory(sslSocketFactory).build();
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			ITLBusinessException.printErrorStack(e);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			ITLBusinessException.printErrorStack(e);
		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			ITLBusinessException.printErrorStack(e);
		}
		
		logger.debug("(getAdviceData) Connection KTSales URL : " + ktSalesGetAdviceUrl);
		HttpPost httpPost = new HttpPost(ktSalesGetAdviceUrl);
		httpPost.setEntity(new UrlEncodedFormEntity(params, StandardCharsets.UTF_8));

		httpPost.addHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_FORM_URLENCODED.withCharset(StandardCharsets.UTF_8).toString());
		CloseableHttpResponse httpResponse;
		
		JSONArray jsonArray = new JSONArray();
		
		KtSalesIfAdviceDTO ktSalesIfAdviceDTO = null;
		
		try {
			httpResponse = httpClient.execute(httpPost);
			
			logger.debug("(getAdviceData) httpResponse.status : " + httpResponse.getStatusLine().getStatusCode());
			
			String respStr = EntityUtils.toString(httpResponse.getEntity(), StandardCharsets.UTF_8);
			
			logger.debug("(getAdviceData) httpResponse respStr : " + respStr);
			
			jsonArray = ObjectConverter.stringToJsonArray(respStr);
			
			
			if ( (jsonArray != null) && (jsonArray.size() > 0) )	ktSalesIfAdviceDTO = (KtSalesIfAdviceDTO)ObjectConverter.jsonToObject((JSONObject)jsonArray.get(0), KtSalesIfAdviceDTO.class);
			
			httpClient.close();
		} catch (ITLBusinessException e) {
			// TODO Auto-generated catch block
			ITLBusinessException.printErrorStack(e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			ITLBusinessException.printErrorStack(e);
		}
		
		return ktSalesIfAdviceDTO;
	}
	
	
	
	
	
	/**
	 * JSONObject Output 암호화 및 마스킹 처리
	 * @param JSONObject
	 * @param Class
	 * 요청이 JSON 방식인 경우
	 */
	@SuppressWarnings("unchecked")
	public JSONObject getAdviceInfoJson(String trtSeq) {
		/*
		 * Request URL Define
		*/
		logger.debug("(getAdviceData) Inquiry Key : " + trtSeq);

		/*
		 * Setting Reqeuest Data
		*/
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("trt_seq", trtSeq);
		logger.debug("(getAdviceData) Request Data : " + jsonObject.toString());
		
		/*
		 * Define HttpPost & Set Header, Parameters
		*/
		HttpPost httpPost = new HttpPost(ktSalesGetAdviceUrl);
		
		// Set Parameters (JSON Data)
		httpPost.setEntity(new StringEntity(jsonObject.toString(), StandardCharsets.UTF_8));

		// Set Headers
//		httpPost.addHeader(HttpHeaders.USER_AGENT, userAgent);
		httpPost.addHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.withCharset(StandardCharsets.UTF_8).toString());
		httpPost.addHeader(HttpHeaders.ACCEPT, ContentType.APPLICATION_JSON.withCharset(StandardCharsets.UTF_8).toString());
		
		/*
		 * Send Request to KTSales & Receive Response
		*/
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse httpResponse;
		
		JSONObject jsonRespObject = null;
		
		try {
			httpResponse = httpClient.execute(httpPost);
			
			logger.debug("httpResponse.status : " + httpResponse.getStatusLine().getStatusCode());
			
			String jsonStr = EntityUtils.toString(httpResponse.getEntity(), StandardCharsets.UTF_8);
			
			logger.debug("httpResponse jsonStr : " + jsonStr);
			
			jsonRespObject = (JSONObject) (new JSONParser().parse(jsonStr));
		}
		catch (IOException e) {
			ITLBusinessException.printErrorStack(e);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			ITLBusinessException.printErrorStack(e);
		}
		finally {
			if (httpClient != null) {
				try { httpClient.close(); } catch (Exception e) { ITLBusinessException.printErrorStack(e); }
			}
		}
		
		return jsonRespObject;
	}
}
