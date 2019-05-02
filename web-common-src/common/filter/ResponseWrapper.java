package com.kt.kkos.common.filter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResponseWrapper extends HttpServletResponseWrapper {
	
	private static final Logger logger = LoggerFactory.getLogger(ResponseWrapper.class);
	
	ByteArrayOutputStream output;
	ResponseOutputStream filterOutput;
//	HttpResponseStatus status = HttpResponseStatus.OK;

	public ResponseWrapper(HttpServletResponse response) {
		super(response);
		// TODO Auto-generated constructor stub
		logger.debug("(Constructor) Start ....");
		output = new ByteArrayOutputStream();
	}
	
	

	@Override
	public ServletOutputStream getOutputStream() throws IOException {
		// TODO Auto-generated method stub
		logger.debug("getOutputStream() Start ....");
		if (filterOutput == null) {
			filterOutput = new ResponseOutputStream(output);
		}
		return filterOutput;
	}
	
	
	public byte[] getDataStream() {
		logger.debug("getDataStream() Start ....");
		
		return output.toByteArray();
	}

}
