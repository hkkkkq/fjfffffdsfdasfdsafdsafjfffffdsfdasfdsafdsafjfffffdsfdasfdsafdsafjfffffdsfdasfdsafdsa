package com.kt.svc1;

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
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;


@RestController
@RequestMapping("/api/users")
//@DefaultProperties(groupKey="",threadPoolKey="",commandProperties= {},threadPoolProperties= {})
public class UserApiController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserApiController.class);
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private SqlMapper sqlMapperDao;
	
	@Value("${services.host.svc2}")
	private String svc2Host;
	
	@Value("${services.port.svc2}")
	private String svc2Port;
	
	
//	@GetMapping(value = "")
//	public String getAllUsers(Request req) {
//		
//		logger.info("req => " + req.toString());
//		
//		return "getAllUsers() is ok!";
//	}
//
//	
//	@PostMapping(value = "/tmp", consumes= {MediaType.APPLICATION_FORM_URLENCODED_VALUE,MediaType.APPLICATION_JSON_UTF8_VALUE}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//	public Response createUser1(Request req) { // The problem is that when we use application/x-www-form-urlencoded, Spring doesn't understand it as a RequestBody. So, if we want to use this we must remove the @RequestBody annotation
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
	public ResponseEntity<Response> createUser2(Request req) { // The problem is that when we use application/x-www-form-urlencoded, Spring doesn't understand it as a RequestBody. So, if we want to use this we must remove the @RequestBody annotation
		
		logger.info("req => " + req.toString());
		
		Response res = new Response();
		res.setId(req.getId());
		res.setName(req.getName());
		res.setSecurity_number(req.getSecurity_number());
		res.setResultCode("ok");
		
		HttpHeaders headers = new HttpHeaders();
		try {
			headers.setLocation(new URI("/api/users"));
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
			headers.setLocation(new URI("/api/users/api2"));
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
	
	@PostMapping(value = "/members", consumes= {MediaType.APPLICATION_FORM_URLENCODED_VALUE,MediaType.APPLICATION_JSON_UTF8_VALUE}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//	@HystrixCommand(fallbackMethod = "createMemberFallback")
	@HystrixCommand(fallbackMethod = "createMemberFallback",
					commandProperties = {@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000")
										,@HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "10000")
										,@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "20")
										,@HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50")
										,@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000")
					},
					threadPoolProperties = {@HystrixProperty(name = "coreSize", value = "10")											
					}//,groupKey="" , commandKey="", threadPoolKey=""
	)
	public ResponseEntity<Response> createMember(Request req) { // The problem is that when we use application/x-www-form-urlencoded, Spring doesn't understand it as a RequestBody. So, if we want to use this we must remove the @RequestBody annotation
		
		logger.info("req => " + req.toString());
		
		req.setId(req.getId()+"_members");
		
		logger.info("# svc2 http://" + svc2Host + ":" + svc2Port + "/svc2/api/members/api2 POST 호출");
		
		// RestTemplate을 이용하여 svc2 서비스 Http Request 호출하기
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		HttpEntity<Request> requestHttpEntity = new HttpEntity<Request>(req, headers);
		
		logger.info("# requestHttpEntity.getBody().toString(): " + requestHttpEntity.getBody().toString());
		logger.info("# properties - services.host.svc2: " + svc2Host);
		logger.info("# properties - services.port.svc2: " + svc2Port);
		
		// 필요 시 default timeout 아닌 개별 timeout 설정
//		HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
//		requestFactory.setConnectionRequestTimeout(1000*5);
//		requestFactory.setReadTimeout(1000*15);
//		restTemplate.setRequestFactory(requestFactory);
		
		ResponseEntity<Response> responseEntity = restTemplate.exchange("http://" + svc2Host + ":" + svc2Port + "/svc2/api/members/api2", HttpMethod.POST, requestHttpEntity, Response.class);
		logger.info("# responseEntity.getBody().toString(): " + responseEntity.getBody().toString());
		responseEntity.getBody().setResultMsg("Member(svc2) user created!!!!");
		
		//DB Insert
		logger.info("# createConnectionHistory");
		//sqlMapperDao.createConnectionHistory(req);
		
		return responseEntity;

		//return new ResponseEntity<Response>(res, headers, HttpStatus.CREATED);
	    //return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}
	
	public ResponseEntity<Response> createMemberFallback(Request req) { 
		
		logger.info("req => " + req.toString());
		
		req.setId(req.getId()+"_members");
		
		logger.info("# svc2 http://" + svc2Host + ":" + svc2Port + "/svc2/api/members/api2 POST 호출  Fallback");
		
		Response response = new Response();
		response.setId(req.getId());
		response.setName(req.getName());
		response.setSecurity_number(req.getSecurity_number());
		response.setResultCode("Fallback");
		response.setResultMsg("svc1=>svc2 Fallback");
		
		ResponseEntity<Response> responseEntity = new ResponseEntity<Response>(response, HttpStatus.OK);
		return responseEntity;
	}
}

