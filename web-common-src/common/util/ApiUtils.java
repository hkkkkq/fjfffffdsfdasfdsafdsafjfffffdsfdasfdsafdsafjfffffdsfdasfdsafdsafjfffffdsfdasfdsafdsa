package com.kt.kkos.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

public class ApiUtils {
	public static String getRequestBody(HttpServletRequest request) throws IOException {
		StringBuilder stringBuilder = new StringBuilder();
		BufferedReader bufferedReader = null;
		try {
			InputStream inputStream = request.getInputStream();
			if (inputStream != null) {
				bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
				char[] charBuffer = new char[128];
				int bytesRead = -1;
				while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
					stringBuilder.append(charBuffer, 0, bytesRead);
				}
			} else {
				stringBuilder.append("");
			}
		} catch (IOException ex) {
			throw ex;
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException ex) {
					throw ex;
				}
			}
		}
		String body = stringBuilder.toString();
		return body;
	}
	
	public static String getContentType(HttpServletRequest request){
		String result = "";
		String contentType = request.getContentType().replaceAll(" ", "").toLowerCase();
		
		if (contentType.startsWith("application/json") ) {
			result = "JSON";
		
		} else if (contentType.startsWith("text/plain")) {
			result = "JSON";
		
		} else if (contentType.startsWith("application/xml") || contentType.startsWith("text/xml")) {
			result = "XML";
		}
		return result;
	}
	
	public static String getApikey() {
		// UNIX TIME + 숫자문자랜덤6자리
		StringBuffer api_key = new StringBuffer();

		// UNIX TIME
		long unixTime = System.currentTimeMillis();

		Random rnd = new Random();
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < 6; i++)
		{
			if (rnd.nextBoolean())
			{
				buf.append((char) ((int) (rnd.nextInt(26)) + 97));
			} else
			{
				buf.append(rnd.nextInt(10));
			}
		}
		String rndString = buf.toString();

		// 시스템코드 + UNIX TIME + 랜덤숫자문자6자리
		// 모바일 BSS는 총 23자리
		api_key.append("kkos"); // 4자리
		api_key.append(unixTime); // 13자리
		api_key.append(rndString); // 6자리

		return api_key.toString();
	}
}
