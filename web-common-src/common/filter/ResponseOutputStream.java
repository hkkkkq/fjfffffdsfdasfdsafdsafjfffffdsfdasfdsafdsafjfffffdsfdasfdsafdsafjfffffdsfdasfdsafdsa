package com.kt.kkos.common.filter;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResponseOutputStream extends ServletOutputStream {
	
	private static final Logger logger = LoggerFactory.getLogger(ResponseOutputStream.class);
	
	DataOutputStream output;
	
	public ResponseOutputStream(OutputStream output) {
		logger.debug("Constructor Start ===");
		this.output = new DataOutputStream(output);
	}

	@Override
	public void write(int arg0) throws IOException {
		// TODO Auto-generated method stub
		logger.debug("write(int arg0) Start ===");
		output.write(arg0);
	}

	@Override
	public void write(byte[] arg0, int arg1, int arg2) throws IOException {
		// TODO Auto-generated method stub
		logger.debug("write(byte[] arg0, int arg1, int arg2) Start ... arg0.length : " + arg0.length + " / arg1 : " + arg1 + " / arg2 : " + arg2);
		//logger.debug("write(byte[] arg0, int arg1, int arg2) \n" + new String(arg0));
		output.write(arg0, arg1, arg2);
	}

	@Override
	public void write(byte[] arg0) throws IOException {
		// TODO Auto-generated method stub
		logger.debug("write(byte[] arg0) Start ===");
		output.write(arg0);
	}

}
