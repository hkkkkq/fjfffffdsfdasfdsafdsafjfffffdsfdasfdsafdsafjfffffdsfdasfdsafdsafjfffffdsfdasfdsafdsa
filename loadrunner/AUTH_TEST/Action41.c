//03_계약정보조회

//4. CustSvcContInfoRetvSO  스크립트 작업 테스트
Action41()
{
//	int EncYN = 1;			//암호화 여부    0=평문     1:암호화

	char* data_RequestXml_41;				//요청 평문 XML
	char data_RequestXmlEnc_41[20000];		//요청 Encoded XML


	char* data_ResponseXmlENC_41;			//응답 암호화문 XML    
	char* data_ResponseXml_41; 				//응답 Deoded XML

	int len1;
	unsigned int len;

	
/* Decoded XML에서 문자열 추출*/
	int offset1; 
	int offset2; 
    char * position1; 
	char * position2; 
	char * search_str1 = "<Col id=\"custId\">"; 
	char * search_str2 = "</Col>"; 
	char searched_str[8192];

	char checkSum[100];

	if ((data_RequestXml_41 = (char *)calloc(20000, sizeof(char))) == NULL) { 
		 lr_output_message ("Insufficient memory available"); 
		 return -1; 
	 } 
	
     if ((data_ResponseXml_41 = (char *)calloc(20000, sizeof(char))) == NULL) { 
        lr_output_message ("Insufficient memory available"); 
        return 0; 
    }

    //lr_load_dll("C:\\sadmod.dll"); 

    //lr_load_dll("C:\\nexaNbssAdp.dll"); 

	//if (FALSE == Init(handle_id,lr_eval_string("{temp_URL}")))
	 //{
	//	 lr_message("[ERROR] %s", "Failed to initialize");
	// }


	// 1.번 고객조회 요청 XML 생성
     strcpy( data_RequestXml_41,"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
			"<Root xmlns=\"http://www.nexacroplatform.com/platform/dataset\">\n"
			"	<Parameters>\n"
			"		<Parameter id=\"PHAROSVISITOR\">0000706f01539d8bf9c15d0e0ad99d58</Parameter>\n"
			"	</Parameters>\n"
			"	<Dataset id=\"commonHeader\">\n"
			"		<ColumnInfo>\n"
			"			<Column id=\"appName\" type=\"STRING\" size=\"256\"  />\n"
			"			<Column id=\"svcName\" type=\"STRING\" size=\"256\"  />\n"
			"			<Column id=\"fnName\" type=\"STRING\" size=\"256\"  />\n"
			"			<Column id=\"fnCd\" type=\"STRING\" size=\"256\"  />\n"
			"			<Column id=\"globalNo\" type=\"STRING\" size=\"256\"  />\n"
			"			<Column id=\"chnlType\" type=\"STRING\" size=\"256\"  />\n"
			"			<Column id=\"envrFlag\" type=\"STRING\" size=\"256\"  />\n"
			"			<Column id=\"trFlag\" type=\"STRING\" size=\"256\"  />\n"
			"			<Column id=\"trDate\" type=\"STRING\" size=\"256\"  />\n"
			"			<Column id=\"trTime\" type=\"STRING\" size=\"256\"  />\n"
			"			<Column id=\"clntIp\" type=\"STRING\" size=\"256\"  />\n"
			"			<Column id=\"responseType\" type=\"STRING\" size=\"256\"  />\n"
			"			<Column id=\"responseCode\" type=\"STRING\" size=\"256\"  />\n"
			"			<Column id=\"responseLogcd\" type=\"STRING\" size=\"256\"  />\n"
			"			<Column id=\"responseTitle\" type=\"STRING\" size=\"256\"  />\n"
			"			<Column id=\"responseBasc\" type=\"STRING\" size=\"256\"  />\n"
			"			<Column id=\"responseDtal\" type=\"STRING\" size=\"256\"  />\n"
			"			<Column id=\"responseSystem\" type=\"STRING\" size=\"256\"  />\n"
			"			<Column id=\"userId\" type=\"STRING\" size=\"256\"  />\n"
			"			<Column id=\"realUserId\" type=\"STRING\" size=\"256\"  />\n"
			"			<Column id=\"filler\" type=\"STRING\" size=\"256\"  />\n"
			"			<Column id=\"langCode\" type=\"STRING\" size=\"256\"  />\n"
			"			<Column id=\"orgId\" type=\"STRING\" size=\"256\"  />\n"
			"			<Column id=\"srcId\" type=\"STRING\" size=\"256\"  />\n"
			"			<Column id=\"curHostId\" type=\"STRING\" size=\"256\"  />\n"
			"			<Column id=\"lgDateTime\" type=\"STRING\" size=\"256\"  />\n"
			"			<Column id=\"tokenId\" type=\"STRING\" size=\"256\"  />\n"
			"			<Column id=\"cmpnCd\" type=\"STRING\" size=\"256\"  />\n"
			"			<Column id=\"lockType\" type=\"STRING\" size=\"256\"  />\n"
			"			<Column id=\"lockId\" type=\"STRING\" size=\"256\"  />\n"
			"			<Column id=\"lockTimeSt\" type=\"STRING\" size=\"256\"  />\n"
			"			<Column id=\"businessKey\" type=\"STRING\" size=\"256\"  />\n"
			"			<Column id=\"arbitraryKey\" type=\"STRING\" size=\"256\"  />\n"
			"			<Column id=\"resendFlag\" type=\"STRING\" size=\"256\"  />\n"
			"			<Column id=\"phase\" type=\"STRING\" size=\"256\"  />\n"
			"		</ColumnInfo>\n"
			"		<Rows>\n"
			"			<Row type=\"insert\">\n"
			"				<Col id=\"appName\">NBSS_IUI</Col>\n"
			"				<Col id=\"svcName\">CustSvcContInfoRetvSO</Col>\n"
			"				<Col id=\"fnName\">service</Col>\n"
			//"				<Col id=\"fnCd\" />\n"
			 "\t\t\t\t<Col id=\"fnCd\">");
				memset (search, 'a', 1000);
				search[1000] =0x00;
				strcat (data_RequestXml_41, search);
				strcat (data_RequestXml_41, search);
				strcat (data_RequestXml_41, search);
				strcat (data_RequestXml_41, search);
				strcat (data_RequestXml_41, search);
				strcat (data_RequestXml_41, search);
				strcat (data_RequestXml_41, "</Col>\n"
			"\t\t\t\t<Col id=\"globalNo\">00000");
				strcat(data_RequestXml_41, lr_eval_string("{login_ID}"));
				strcat(data_RequestXml_41, lr_eval_string("{DATE}"));
				strcat(data_RequestXml_41, lr_eval_string("{TIME}"));
				strcat(data_RequestXml_41, lr_eval_string("{G_Random}"));
				strcat (data_RequestXml_41, "</Col>\n"
			"				<Col id=\"chnlType\">UI</Col>\n"
			"				<Col id=\"envrFlag\" />\n"
			"				<Col id=\"trFlag\">T</Col>\n"
			"\t\t\t\t<Col id=\"trDate\">");
				strcat (data_RequestXml_41, lr_eval_string("{DATE}"));
				strcat (data_RequestXml_41, "</Col>\n"
				"\t\t\t\t<Col id=\"trTime\">");
				strcat (data_RequestXml_41, lr_eval_string("{TIME}"));
				strcat (data_RequestXml_41, "</Col>\n"
			"\t\t\t\t<Col id=\"clntIp\">");
				strcat( data_RequestXml_41,lr_eval_string("{IP_Param}"));
				strcat (data_RequestXml_41, "</Col>\n"
			"				<Col id=\"responseType\" />\n"
			"				<Col id=\"responseCode\" />\n"
			"				<Col id=\"responseLogcd\" />\n"
			"				<Col id=\"responseTitle\" />\n"
			"				<Col id=\"responseBasc\" />\n"
			"				<Col id=\"responseDtal\" />\n"
			"				<Col id=\"responseSystem\" />\n"
			"\t\t\t\t<Col id=\"userId\">");
				strcat (data_RequestXml_41, lr_eval_string("{login_ID}"));
				strcat (data_RequestXml_41, "</Col>\n"
				"\t\t\t\t<Col id=\"realUserId\">");
				strcat (data_RequestXml_41, lr_eval_string("{login_ID}"));
				strcat (data_RequestXml_41, "</Col>\n"
			"				<Col id=\"filler\">E</Col>\n"
			"				<Col id=\"langCode\" />\n"
			"				<Col id=\"orgId\">SPT8050</Col>\n"
			"				<Col id=\"srcId\">DNIUI3100LY</Col>\n"
			"				<Col id=\"curHostId\" />\n"
			"\t\t\t\t<Col id=\"lgDateTime\">");
				strcat (data_RequestXml_41, lr_eval_string("{DATE}"));
				strcat (data_RequestXml_41, lr_eval_string("{TIME}"));
				strcat (data_RequestXml_41, "</Col>\n"
			"\t\t\t\t<Col id=\"tokenId\">");
				strcat (data_RequestXml_41, lr_eval_string("{bbb}"));
				strcat (data_RequestXml_41, "</Col>\n"
			"				<Col id=\"cmpnCd\">KTF</Col>\n"
			"				<Col id=\"lockType\" />\n"
			"				<Col id=\"lockId\" />\n"
			"				<Col id=\"lockTimeSt\" />\n"
			"\t\t\t\t<Col id=\"businessKey\">0000");
				  strcat(data_RequestXml_41, lr_eval_string("{login_ID}"));
				  strcat(data_RequestXml_41, "00A_MASTER");
				  strcat(data_RequestXml_41, lr_eval_string("{DATE}"));
				  strcat(data_RequestXml_41, lr_eval_string("{TIME}"));
			strcat(data_RequestXml_41, "531</Col>\n");
			strcat(data_RequestXml_41, "				<Col id=\"arbitraryKey\">{}</Col>\n"
			"				<Col id=\"resendFlag\" />\n"
			"				<Col id=\"phase\">PA1</Col>\n"
			"			</Row>\n"
			"		</Rows>\n"
			"	</Dataset>\n"
			"	<Dataset id=\"bizHeader\">\n"
			"		<ColumnInfo>\n"
			"			<Column id=\"orderId\" type=\"STRING\" size=\"256\"  />\n"
			"			<Column id=\"cbSvcName\" type=\"STRING\" size=\"256\"  />\n"
			"			<Column id=\"cbFnName\" type=\"STRING\" size=\"256\"  />\n"
			"		</ColumnInfo>\n"
			"		<Rows>\n"
			"			<Row type=\"insert\">\n"
			"				<Col id=\"orderId\">orderId</Col>\n"
			"				<Col id=\"cbSvcName\">CustSvcContInfoRetvSO</Col>\n"
			"				<Col id=\"cbFnName\">service</Col>\n"
			"			</Row>\n"
			"		</Rows>\n"
			"	</Dataset>\n"
			"	<Dataset id=\"SvcContDTO\">\n"
			"		<ColumnInfo>\n"
			"			<Column id=\"svcContId\" type=\"STRING\" size=\"256\"  />\n"
			"			<Column id=\"custId\" type=\"STRING\" size=\"256\"  />\n"
			"			<Column id=\"intmModelId\" type=\"STRING\" size=\"256\"  />\n"
			"			<Column id=\"prodNm\" type=\"STRING\" size=\"256\"  />\n"
			"		</ColumnInfo>\n"
			"		<Rows>\n"
			"			<Row>\n"
			"				<Col id=\"svcContId\">");
			strcat(data_RequestXml_41, lr_eval_string("{P_svcContId}"));
			strcat(data_RequestXml_41, "</Col>\n"
			"				<Col id=\"custId\">");
			strcat(data_RequestXml_41, lr_eval_string("{P_custId}"));
			strcat(data_RequestXml_41, "</Col>\n"
			"				<Col id=\"intmModelId\" />\n"
			"				<Col id=\"prodNm\">Mobile</Col>\n"
			"			</Row>\n"
			"		</Rows>\n"
			"	</Dataset>\n"
			"</Root>\n",
		LAST);

	 //lr_output_message("%s",data_RequestXml_41);
	 
	 web_set_max_html_param_len("800000");



	  


 if (EncYN == 0) {		//평문 조회

	lr_save_string( data_RequestXml_41, "P_data_RequestXml_1");

	//lr_start_transaction("DD_계약정보 조회_CustSvcContInfoRetvSO");
	lr_start_transaction("03_계약정보조회_ASIS");
    

	 web_reg_find("Text=<Parameter id=\"ErrorCode\" type=\"string\">0</Parameter>",LAST);

	 web_custom_request("UiDynamicGateway",
         "URL=http://{ESB_IP}/UiDynamicGateway ",
		  "Method=POST",
		  "Resource=0",
		  "RecContentType=text/html",
		  "Referer=file://C:/Users/Administrator/Documents/nexacro/outs/AdaptorTest/Base/USAM9908FM_sit.xfdl.js",
		  "Snapshot=t11.inf",
		  "Mode=HTML",
		  "Body={P_data_RequestXml_1}",
		  LAST);


   //lr_end_transaction("DD_계약정보 조회_CustSvcContInfoRetvSO", LR_AUTO);
	  //lr_stop_transaction("서비스번호 조회_평문");
	 lr_end_transaction("03_계약정보조회_ASIS",LR_AUTO);
	  

} else if (EncYN == 1) {			//암호화

	memset(data_RequestXmlEnc_41, 0, sizeof(data_RequestXmlEnc_41));	 
	// Endcoe/Decode 비교 ransaction 위치 (03.25)
	lr_start_transaction("03_계약정보조회_checkSUM");


	memset (checkSum, 0x00, sizeof (checkSum));

//	hmac_sha256 (secretKey2, strlen(secretKey2), 
	HmacSha256 (secretKey2, strlen(secretKey2), 
				 data_RequestXml_41 , strlen(data_RequestXml_41) ,
				 checksum_pos, checksum_len, // 메시지 시작위치, 메시지 크기
//				 0, 100, // 메시지 시작위치, 메시지 크기
				checkSum, 32);
	 //lr_output_message("checksum : %s", checkSum);


	 sprintf (data_RequestXmlEnc_41, "%s%s", checkSum, data_RequestXml_41);
	 lr_save_string( data_RequestXmlEnc_41, "Data_encrypt_out_1");



/*	 if (FALSE == Encode(handle_id, data_RequestXml_41 , strlen(data_RequestXml_41), data_RequestXmlEnc_41))
	 {
		  lr_message("[ERROR] %s", "Failed to encrypted Request");
	 }
	 //lr_output_message("Encoded : %s",data_RequestXmlEnc_41);
*/

//	 lr_save_string( data_RequestXmlEnc_41, "Data_encrypt_out_1");


	//web_reg_save_param("len","LB=Content-Length: ","RB=\r\n",LAST);
//	web_reg_save_param("Data_encrypt_res",  "LB=\r\n\r\n",  "RB=",  LAST);


	//lr_start_transaction("DE_계약정보 조회_CustSvcContInfoRetvSO");
	// 측정 Transaction 위치 (03.25)
	//lr_resume_transaction("서비스번호 조회_암호");

	 web_reg_find("Text=<Parameter id=\"ErrorCode\" type=\"string\">0</Parameter>",LAST);

	 web_custom_request("UiDynamicGateway",
         "URL=http://{ESB_IP}/UiDynamicGateway",       
		  "Method=POST",
		  "Resource=0",
		  "RecContentType=text/html",
		  "Referer=file://C:/Users/Administrator/Documents/nexacro/outs/AdaptorTest/Base/USAM9908FM_sit.xfdl.js",
		  "Snapshot=t11.inf",
		  "Mode=HTML",
		  "Body={Data_encrypt_out_1}",
		  LAST);


	  //lr_stop_transaction("서비스번호 조회_암호");
	  // 측정 Transaction 위치 (03.25)
	  //lr_end_transaction("서비스번호 조회_암호",LR_AUTO);
     


/*	 data_ResponseXmlENC_41 =  lr_eval_string("{Data_encrypt_res}");

	 if (FALSE == Decode(handle_id, data_ResponseXmlENC_41 ,strlen(data_ResponseXmlENC_41), data_ResponseXml_41, (int *) & len1))
	 {
	   lr_message("[ERROR] %s", "Failed to decrypted Response");
	 }*/
	 // Endcoe/Decode 비교 ransaction 위치 (03.25)
	 lr_end_transaction("03_계약정보조회_checkSUM",LR_AUTO);

	 //lr_message("data_ResponseXml_1=%s", data_ResponseXml_41);




    
}

	free(data_RequestXml_41);
	free(data_ResponseXml_41);




// Order(Sync) ESB AP L4 : 10.219.3.190 

//	j = 36;

	if(j<1){
		//j++;
		return 0;
	}
	else{
		//lr_start_transaction("인증토큰연장_UserAthnToknExtendSO");
		lr_message("인증토큰연장_UserAthnToknExtendSO");


	soap_request("StepName=SOAP Request",	
				 "ExpectedResponse=AnySoap" ,
         		"URL=http://{ESB_IP}/SoapDynamicGateway",										
		"SOAPEnvelope="
		"<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
			"<soap:Header xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
				"<commonHeader>"
					"<appName>NBSS_ATA</appName>"
					"<svcName>UserAthnToknExtendSO</svcName>"
					"<fnName>service</fnName>"
					"<globalNo>00000{login_ID}{DATE}{TIME}00</globalNo>"
					"<userId>{login_ID}</userId>"
					"<chnlType >AU</chnlType>"
				 "</commonHeader>"
			"</soap:Header>"
			"<soap:Body>"
				"<UserAthnToknExtendSO_service_request xmlns:biz=\"http://bizhub.kt.com\">"
					"<bizHeader>"
						"<orderId>orderId</orderId>"
						"<cbSvcName>UserAthnToknExtendSO</cbSvcName>"
						"<cbFnName>service</cbFnName>"
					"</bizHeader>"
					"<AthnToknExtendInfoDTO>"
						"<athnToknId>{bbb}</athnToknId>"
					"</AthnToknExtendInfoDTO>"
				"</UserAthnToknExtendSO_service_request>"
			"</soap:Body>"
		"</soap:Envelope>",										
		"SOAPAction=",										
		"ResponseParam=response",										
		"Snapshot=t1448244919.inf",								    
		LAST);

		j=0;
	}




      

	   return 0;
}
