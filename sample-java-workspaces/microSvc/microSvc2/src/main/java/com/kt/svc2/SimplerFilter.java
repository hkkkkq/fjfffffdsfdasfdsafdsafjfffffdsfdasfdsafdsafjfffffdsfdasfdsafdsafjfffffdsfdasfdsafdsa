package com.kt.svc2;

import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class SimplerFilter implements Filter {
	
	private static final Logger logger = LoggerFactory.getLogger(SimplerFilter.class);

	@Override
	public void init(FilterConfig filterconfig) throws ServletException {}
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		
		HttpServletRequest request = (HttpServletRequest) req;
		
		logger.info("# request.getRequestURI(): " + request.getRequestURI());
		
		Enumeration<String> e1 = request.getParameterNames();	
		List<String> list1 = Collections.list(e1);
		for(String name : list1) {
			String[] values1 = request.getParameterValues(name);
		    if (values1 != null && values1.length > 0) {
		    	for(String value : values1) {
		    		logger.info("# param [name] " + name + " = [value] " + value);
		    	}
		    }
		}
		
//		Enumeration<String> e2 = request.getAttributeNames();
//		List<String> list2 = Collections.list(e2);	
//		for(String name : list2) {		
//			Object value = request.getAttribute(name);		
//	    	logger.info("# attr [name] " + name + " = [value] " + value.toString());
//	    }
		
		Enumeration<String> e3 = request.getHeaderNames();		
		List<String> list3 = Collections.list(e3);		
		for(String name : list3) {			
			String value = request.getHeader(name);			
	    	logger.info("# header [name] " + name + " = [value] " + value);
	    }
		
		filterChain.doFilter(req, res);
	}
	
	@Override
	public void destroy() {}

}
