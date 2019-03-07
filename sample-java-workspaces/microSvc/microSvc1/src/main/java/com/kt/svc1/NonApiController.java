package com.kt.svc1;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class NonApiController {
	
	private static final Logger logger = LoggerFactory.getLogger(NonApiController.class);

	@RequestMapping("/hello")
	public @ResponseBody String hello(HttpServletRequest request) {
		
		logger.info("# request.getRequestURI(): " + request.getRequestURI());
		
		return "Hello, Spring Boot!";
	}
}