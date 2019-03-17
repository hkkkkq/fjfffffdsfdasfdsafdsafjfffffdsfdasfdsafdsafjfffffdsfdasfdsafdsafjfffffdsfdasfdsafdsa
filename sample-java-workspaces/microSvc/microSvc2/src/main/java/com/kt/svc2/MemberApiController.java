package com.kt.svc2;

import java.net.URI;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@RestController
@RequestMapping("/api/members")
public class MemberApiController {
	
	private static final Logger logger = LoggerFactory.getLogger(MemberApiController.class);
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private SqlMapper sqlMapperDao;
	
	@Value("${herasoo.host.svc1}")
	private String svc1Host;
	
	@Value("${herasoo.port.svc1}")
	private String svc1Port;
	
//	@GetMapping(value = "")
//	public String getAllMemberss(Request req) {
//		
//		logger.info("req => " + req.toString());
//		
//		return "getAllProducts() is ok!";
//	}
//
//	
//	@PostMapping(value = "/tmp", consumes= {MediaType.APPLICATION_FORM_URLENCODED_VALUE,MediaType.APPLICATION_JSON_UTF8_VALUE}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//	public Response createMembers1(Request req) { // The problem is that when we use application/x-www-form-urlencoded, Spring doesn't understand it as a RequestBody. So, if we want to use this we must remove the @RequestBody annotation
//		
//		logger.info("req => " + req.toString());
//		
//		Response res = new Response();
//		res.setId(req.getId());
//		res.setName(req.getName());
//		res.setSecurity_number(req.getSecurity_number());
//		res.setResultCode("ok");
//		
//		return res;
//	}
	
	@PostMapping(value = "", consumes= {MediaType.APPLICATION_FORM_URLENCODED_VALUE,MediaType.APPLICATION_JSON_UTF8_VALUE}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Response> createMembers2(Request req) { // The problem is that when we use application/x-www-form-urlencoded, Spring doesn't understand it as a RequestBody. So, if we want to use this we must remove the @RequestBody annotation
		
		logger.info("req => " + req.toString());
		
		Response res = new Response();
		res.setId(req.getId());
		res.setName(req.getName());
		res.setSecurity_number(req.getSecurity_number());
		res.setResultCode("ok");
		
		HttpHeaders headers = new HttpHeaders();
		try {
			headers.setLocation(new URI("/api/members"));
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//DB Insert
		logger.info("# createConnectionHistory");
		//sqlMapperDao.createConnectionHistory(req);
		
		return new ResponseEntity<Response>(res, headers, HttpStatus.CREATED);
	    //return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}
	
	@PostMapping(value = "/api2", consumes= MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Response> createMembers3(@RequestBody Request req) {
		
		logger.info("req => " + req.toString());
		
		Response res = new Response();
		res.setId(req.getId());
		res.setName(req.getName());
		res.setSecurity_number(req.getSecurity_number());
		res.setResultCode("ok");
		
		HttpHeaders headers = new HttpHeaders();
		try {
			headers.setLocation(new URI("/api/members/api2"));
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//DB Insert
		logger.info("# createConnectionHistory");
		//sqlMapperDao.createConnectionHistory(req);
				
		return new ResponseEntity<Response>(res, headers, HttpStatus.CREATED);
	    //return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}
	
	@PostMapping(value = "/users", consumes= {MediaType.APPLICATION_FORM_URLENCODED_VALUE,MediaType.APPLICATION_JSON_UTF8_VALUE}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Response> createUser(Request req) { // The problem is that when we use application/x-www-form-urlencoded, Spring doesn't understand it as a RequestBody. So, if we want to use this we must remove the @RequestBody annotation
		
		logger.info("req => " + req.toString());
		
		req.setId(req.getId()+"_userss");
		
		logger.info("# svc1 http://" + svc1Host + ":" + svc1Port + "//svc1/api/users/api2 POST 호출");
		
		// svc1 http 호출
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		HttpEntity<Request> requestHttpEntity = new HttpEntity<Request>(req, headers);
		
		logger.info("# requestHttpEntity.getBody().toString(): " + requestHttpEntity.getBody().toString());
		logger.info("# properties - herasoo.host.svc1: " + svc1Host);
		logger.info("# properties - herasoo.port.svc1: " + svc1Port);
		
		ResponseEntity<Response> responseEntity = restTemplate.exchange("http://" + svc1Host + ":" + svc1Port + "/svc1/api/users/api2", HttpMethod.POST, requestHttpEntity, Response.class);
		responseEntity.getBody().setResultMsg("User(svc1) member created!!!!");
		
		//DB Insert
		logger.info("# createConnectionHistory");
		//sqlMapperDao.createConnectionHistory(req);
		
		return responseEntity;

		//return new ResponseEntity<Response>(res, headers, HttpStatus.CREATED);
	    //return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}
	
}

