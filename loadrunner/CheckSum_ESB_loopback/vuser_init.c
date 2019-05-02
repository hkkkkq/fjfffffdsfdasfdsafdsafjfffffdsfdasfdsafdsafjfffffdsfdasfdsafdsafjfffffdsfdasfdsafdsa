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
	lr_load_dll("C:\\nexaNbssAdp.dll");


	return 0;
}

