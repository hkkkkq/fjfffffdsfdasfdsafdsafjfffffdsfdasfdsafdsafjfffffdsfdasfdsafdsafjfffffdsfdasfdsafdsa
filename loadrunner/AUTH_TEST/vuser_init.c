int handle_id;
int Enc_size;

char* cert_RequestXml_1;
char* cert_ResquextXmlEnc;
char* policy_RequestXml_1;
char* policy_RequestXmlEnc;
char* keyhandshake_RequestXml_1;
char* keyhandshake_RequestXmlEnc;

char* certReq;
char* certURL;
char* policyReq;
char* policyURL;
char* handshakeURL;
char* handshakeReq;

char* uuid;

char search[1000+1];

int j=0;
int checksum_len = 5000;
int checksum_pos = -150;
int EncYN = 0;	        //암호화 여부    0=암호화     1:암호화 + CheckSum

unsigned char secretKey1[512] = "d0ff169dddad20cadfe0d018"; 
unsigned char secretKey2[512] = "efdfe6f93682de29222289d2"; 


vuser_init()
{

	int offset1; 
	int offset2; 
    char * position1; 
	char * position2; 
    
    char * search_str1 = "<Parameter id=\"ErrorMsg\" type=\"string\">"; 
	char * search_str2 = "</Parameter>"; 

	char searched_str[8192];




	char out[8192];

	char* out1; 
    int len1;


	char* handshake_out;


   int result;
   int i;
   char host_name[21][20]={"loadrunner01","loadrunner02","loadrunner03","loadrunner04","loadrunner05","loadrunner06","WIN-DEIMWP6RXRA","WIN-GYLWUXTMDNE","bloadgen02","WIN-RFANNWK6HOZ","bloadcon01","WIN-XMWMB2EK2R8","WIN-1BJ6FAQ5IN8","cldrap08","cldrap07","cldrap06","WIN-KW4CULPDHOA","WIN-OCY5RJXQMEA","WIN-2YN3ZKAH5IX","WIN-1KRKSMJDU0M","WIN-ZZU2XD8GBD2"};
   char host_ip[21][15]={"10.219.1.184","10.219.1.185","10.219.1.186","10.219.1.187","10.219.1.188","10.219.1.189","10.217.233.11","10.217.233.12","10.217.233.13","10.217.233.14","10.217.233.15","10.217.233.16","10.217.233.17","10.217.233.18","10.217.233.19","10.217.233.20","10.217.233.21","10.217.233.22","10.217.233.23","10.217.233.25","10.217.233.26"};


   char * my_host;
 my_host = lr_get_host_name(  );
 lr_output_message("%s", my_host);


  for(i=0; i<21;i++)
  {
 result = strcmp( host_name [i],
  my_host );

    if(result == 0)
    {
break;
    }
  }

  if(i>=21)
  {
 lr_error_message("Check for host name");
  }
  else
  {
// lr_output_message("The IP address is %s", host_ip[i]);
 lr_save_string( lr_eval_string(host_ip [ i]),
  "IP_Param" );
  }


   web_set_max_html_param_len("2048");

   web_reg_save_param("aaa", 
    "LB=CSRFToken\" value=\"", "RB=\">", LAST ); 

	web_url("main.csr", 
		"URL=http://{배포IP}/main.csr", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer=", 
		"Snapshot=t1.inf", 
		"Mode=HTML", 
		LAST);

//	lr_start_transaction("로그인");


/*
    web_reg_save_param("aaa", 
    "LB=CSRFToken\" value=\"", "RB=\">", LAST ); 
*/
  //  web_reg_find("Text=CSRFToken", 
  //      LAST ); 


	web_submit_data("login.csr", 
		"Action=http://{배포IP}/login/login.csr", 
		"Method=POST", 
		"TargetFrame=", 
		"RecContentType=text/html", 
		"Referer=http://{배포IP}/login/formLogin.csr", 
		"Snapshot=t2.inf", 
		"Mode=HTML", 
		ITEMDATA, 
		"Name=plainUserId", "Value={login_ID}", ENDITEM, 
		"Name=plainScrtNo", "Value=New1234!", ENDITEM, 
		"Name=userLocalIp", "Value={IP_Param}", ENDITEM, 
		"Name=macAdrsNm", "Value=FF-FF-FF-FF-FF-FF", ENDITEM, 
		"Name=CSRFToken", "Value={aaa}", ENDITEM, 
		LAST);

//	lr_end_transaction("로그인",LR_AUTO);

//	lr_start_transaction("UI호출");

 //   web_set_max_html_param_len("1024");


    //web_reg_save_param("bbb", "LB=TOKEN_ID\":\"", "RB=\"\,\"SMART", LAST ); 
    web_reg_save_param("bbb", "LB=TOKEN_ID\":\"", "RB=\",\"PAYLOAD_YN", LAST ); 		//03.22 RB 변경
	

	web_submit_data("systemLinkAuth.csr", 
		"Action=http://{배포IP}/main/systemLinkAuth.csr", 
		"Method=POST", 
		"TargetFrame=", 
		"RecContentType=application/json", 
		"Referer=http://{배포IP}/main.csr", 
		"Snapshot=t3.inf", 
		"Mode=HTML", 
		ITEMDATA, 
		"Name=systId", "Value=NSTU", ENDITEM, 
		"Name=CSRFToken", "Value={aaa}", ENDITEM, 
		LAST);
//	lr_end_transaction("UI호출", LR_AUTO);

//	return 0;

	lr_load_dll("C:\\sadmod.dll"); 
	//lr_load_dll("C:\\nexaNbssAdp.dll");


/*
	cert_RequestXml_1 = lr_eval_string("{cert_RequestXml}");
	lr_save_string(cert_RequestXml_1, "{cert_RequestXml}");


	uuid = lr_eval_string("{UUID1}");
	lr_save_string(uuid, "{UUID1}");

    policy_RequestXml_1=lr_eval_string("{policy_RequestXml}");
	keyhandshake_RequestXml_1=lr_eval_string("{keyhandshake_RequestXml}");

	certURL="http://10.219.6.110:9100/web/server/certificate_response.jsp";
	//certReq="<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Root xmlns=\"http://www.nexacroplatform.com/platform/dataset\">\n\t<Parameters>\n\t\t<Parameter id=\"command\">request_cert</Parameter>\n\t</Parameters>\n</Root>";
	certReq="<?xml version=\"1.0\" encoding=\"UTF-8\"?><Root xmlns=\"http://www.nexacroplatform.com/platform/dataset\"><Parameters><Parameter id=\"command\">request_cert</Parameter></Parameters></Root>";

	//lr_message("xml=%s", certReq);

	policyURL="http://10.219.6.110:9100/web/server/policy_response.jsp";
	policyReq="<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Root xmlns=\"http://www.nexacroplatform.com/platform/dataset\">\n\t<Parameters>\n\t\t<Parameter id=\"command\">request_policy</Parameter>\n\t</Parameters>\n</Root>";

	handshakeURL="http://10.219.6.110:9100/web/server/exchange_response.jsp";
	handshakeReq="<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Root xmlns=\"http://www.nexacroplatform.com/platform/dataset\">\n\t<Parameters>\n\t\t<Parameter id=\"command\"handshake</Parameter>\n\t</Parameters>\n</Root>";

	// 암호화 DLL loading...
	//lr_load_dll("D:\\inisafenet\\nexaInitechAdp.dll");
	lr_load_dll("C:\\inisafenet_0321\\nexaInitechAdpWrp.dll");

	handle_id= CreateProtocolAdaptor();
	//lr_message("id= %d", handle_id);
	 
	// Set parameter
	if (FALSE == SetParam(handle_id, ("rootPath"), ("C:\\inisafenet")))
	{
		lr_message("[ERROR] %s", "Failed to set root path parameter\n");
	}
	if (FALSE == SetParam(handle_id, ("netPath"), ("inisafenet_v7.2.dll")))
	{
		lr_message("[ERROR] %s", "Failed to set net path parameter\n");
	}
	if (FALSE == SetParam(handle_id, ("cnfPath"), ("INISAFENet_win.properties")))
	{
		lr_message("[ERROR] %s", "Failed to set config path parameter\n");
	}
	if (FALSE == SetParam(handle_id, ("LogFile"), ("C:\\inisafenet\\log\\nexaInitechLog.log")))
	{
		lr_message("[ERROR] %s", "ailed to set log file parameter\n");
	}
	//if (FALSE == SetParam(handle_id, ("LogLevel"), ("DEBUG")))
	if (FALSE == SetParam(handle_id, ("LogLevel"), ("ERROR")))
	{
		lr_message("[ERROR] %s", "Failed to set log level parameter");
	}
	if (FALSE == SetParam(handle_id, ("UUID"),  uuid))
	{
		lr_message("[ERROR] %s", "Failed to set UUID");
	}

//	lr_message("UUID=%s",  uuid);



	// 인증서 요청
	// init
	if (FALSE == Init(handle_id, lr_eval_string("{URL}")))
	{
		lr_message("[ERROR] %s", "Failed to initialize");
	}
	lr_output_message("인증서요청 URL : %s",lr_eval_string("{URL}"));

	// Encode Certificate request xml

//lr_output_message("%s",certReq);

//	strcpy( out , Encode(handle_id,certReq, strlen(certReq)));

//		lr_message("out=%s",  out);

	memset(&out, 0, sizeof(out));

	if (FALSE == Encode(handle_id, cert_RequestXml_1, strlen(cert_RequestXml_1), out, (int *) & len1))
	{
		lr_message("[ERROR] %s", "Failed to certRequest");
	}


	web_set_max_html_param_len("8096");


	web_reg_save_param("certificate_res",
		"LB=\r\n\r\n",
		"RB=\r\n",
		LAST);
*/
/*	
	lr_start_transaction("01_인증서요청");


	web_custom_request("certificate_response.jsp", 
		"URL=http://10.219.6.110:9100/web/server/certificate_response.jsp", 
		"Method=POST", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer=file://C:/Users/Administrator/Documents/nexacro/outputs/AdaptorTest/Base/USAM9908FM_sit.xfdl.js", 
		"Snapshot=t11.inf", 
		"Mode=HTML", 
		"Body=<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Root xmlns=\"http://www.nexacroplatform.com/platform/dataset\">\n\t<Parameters>\n\t\t<Parameter id=\"command\">request_cert</Parameter>\n\t</Parameters>\n</Root>", 
		LAST);

	// Decode response xml

    
	lr_end_transaction("01_인증서요청", LR_AUTO);
*/
/*
	cert_ResquextXmlEnc =  lr_eval_string("{certificate_res}");
//	lr_save_string( lr_eval_string("{certificate_res}"), cert_ResquextXmlEnc );

	//strcpy( out , Decode(handle_id, cert_ResquextXmlEnc , strlen(cert_ResquextXmlEnc)));

	memset(&out, 0, sizeof(out));
//
	
	if (FALSE == Decode(handle_id, cert_ResquextXmlEnc , strlen(cert_ResquextXmlEnc), out, (int *) & len1))
	{
		//Log(LOG_ERR, _T("[THREAD:%d][%s]Failed to decode\n"), dwThread, RequestData[nRCnt].pszCmd);

	}


	//lr_message("certi_out=%s",  out);

	// 정책 요청
	// init
	if (FALSE == Init(handle_id, policyURL))
	{
		lr_message("[ERROR] %s", "Failed to initialize");
	}
	lr_output_message("정책요청 URL : %s",policyURL);

	memset(&out, 0, sizeof(out));

	if (FALSE == Encode(handle_id, policy_RequestXml_1, strlen(policy_RequestXml_1), out, (int *) & len1))
	{
		lr_message("[ERROR] %s", "Failed to policy Request");
	}


	//web_set_max_html_param_len("8096");

	
	web_reg_save_param("policy_res",
		"LB=\r\n\r\n",
		"RB=\r\n",
		LAST);

    
	lr_start_transaction("02_정책요청");


	web_custom_request("policy_response.jsp", 
		"URL=http://10.219.6.110:9100/web/server/policy_response.jsp", 
		"Method=POST", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer=file://C:/Users/Administrator/Documents/nexacro/outputs/AdaptorTest/Base/USAM9908FM_sit.xfdl.js", 
		"Snapshot=t11.inf", 
		"Mode=HTML", 
		"Body=<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Root xmlns=\"http://www.nexacroplatform.com/platform/dataset\">\n\t<Parameters>\n\t\t<Parameter id=\"command\">request_policy</Parameter>\n\t</Parameters>\n</Root>", 
		LAST);


    
	lr_end_transaction("02_정책요청", LR_AUTO);
*/
/*
    policy_RequestXmlEnc =  lr_eval_string("{policy_res}");

		memset(&out, 0, sizeof(out));

	
	if (FALSE == Decode(handle_id, policy_RequestXmlEnc , strlen(policy_RequestXmlEnc), out, (int *) & len1))
	{
		//Log(LOG_ERR, _T("[THREAD:%d][%s]Failed to decode\n"), dwThread, RequestData[nRCnt].pszCmd);

	}

//	lr_message("Policy_out=%s",  out);


	//handshake

	// 정책 요청
	// init
	if (FALSE == Init(handle_id,handshakeURL))
	{
		lr_message("[ERROR] %s", "Failed to initialize");
	}

	lr_output_message("핸드쉐이크요청 URL : %s",handshakeURL);

	memset(&out, 0, sizeof(out));

	if (FALSE == Encode(handle_id, keyhandshake_RequestXml_1 , strlen(keyhandshake_RequestXml_1), out, (int *) & len1))
	{
		lr_message("[ERROR] %s", "Failed to handshake Request");
	}


	// web_set_max_html_param_len("8096");

	
	web_reg_save_param("keyhandshake_res",
		"LB=\r\n\r\n",
		"RB=\r\n",
		LAST);

	lr_save_string( out, "handshake_out");


    
	lr_start_transaction("03_키교환");


	web_custom_request("exchange_response.jsp", 
		"URL=http://10.219.6.110:9100/web/server/exchange_response.jsp", 
		"Method=POST", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer=file://C:/Users/Administrator/Documents/nexacro/outputs/AdaptorTest/Base/USAM9908FM_sit.xfdl.js", 
		"Snapshot=t11.inf", 
		"Mode=HTML", 
		"Body={handshake_out}", 
		LAST);

    
	lr_end_transaction("03_키교환", LR_AUTO);
*/
/*
    keyhandshake_RequestXmlEnc =  lr_eval_string("{keyhandshake_res}");

		memset(&out, 0, sizeof(out));

	
	if (FALSE == Decode(handle_id, keyhandshake_RequestXmlEnc , strlen(keyhandshake_RequestXmlEnc), out, (int *) & len1))
	{
		//Log(LOG_ERR, _T("[THREAD:%d][%s]Failed to decode\n"), dwThread, RequestData[nRCnt].pszCmd);
	}
	//lr_message("out=%s",  out);
*/
/*
	 if (FALSE == Init(handle_id,lr_eval_string("{temp_URL}")))
	 {
		 lr_message("[ERROR] %s", "Failed to initialize");
	 }
*/

	
	return 0;
}

