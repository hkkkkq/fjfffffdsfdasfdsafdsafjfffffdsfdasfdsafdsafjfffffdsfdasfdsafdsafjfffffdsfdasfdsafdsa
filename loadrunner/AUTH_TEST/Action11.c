//01_서비스번호로 고객검색	/CDM/PipelineSVC/NIUI/CustInfo/PL_CustInfoIdfy
//1.고객정보 조회    retrieveCustInfoByCustIdfy

Action11()
{



//	int EncYN = 1;			//암호화 여부    0=암호화     1:암호화 + CheckSum

	char* data_RequestXml_11;				//요청 평문 XML
	char *data_RequestXmlEnc_11;		//요청 Encoded XML


	char* data_ResponseXmlENC_11 = NULL;			//응답 암호화문 XML    
	char* data_ResponseXml_11 = NULL; 				//응답 Deoded XML

	int len1;
	unsigned int len;


	
/* Decoded XML에서 문자열 추출*/
	int offset1; 
	int offset2; 
	int lng;
    char * position1; 
	char * position2; 
	
	char search_str2[100];  
	char searched_str[8192];
	


    char custNo[4000];
    char cipherText[4000];
	char checkSum[100];

	if ((data_RequestXml_11 = (char *)calloc(20000, sizeof(char))) == NULL) { 			//6000
		 lr_output_message ("Insufficient memory available"); 
		 return -1; 
	 } 
	
     if ((data_ResponseXml_11 = (char *)calloc(20000, sizeof(char))) == NULL) { 		//6000
        lr_output_message ("Insufficient memory available"); 
        return 0; 
    }
	 if ((data_RequestXmlEnc_11 = (char *)calloc(20000, sizeof(char))) == NULL) { 		//6000
		lr_output_message ("Insufficient memory available"); 
		return 0; 
	}

/*
	if (FALSE == Init(handle_id,lr_eval_string("{temp_URL}")))
	 {
		 lr_message("[ERROR] %s", "Failed to initialize");
	 }
*/
	lr_output_message("서비스요요청 URL : %s",lr_eval_string("{temp_URL}"));

    //lr_load_dll("C:\\sadmod.dll"); 
    //lr_load_dll("C:\\nexaNbssAdp.dll"); 
	
    memset(custNo, 0x00, sizeof (custNo));
    strcpy(custNo,lr_eval_string("{custIdfyNo}"));
	//strcpy(custNo,"01033297329");
	//strcpy(custNo,"01090303608");
	//strcpy(custNo,"0KPRDS8OS21");

    memset(cipherText, 0, sizeof(cipherText)); 

    lng = seed_enc(secretKey1,custNo,strlen(custNo),cipherText);

//    lr_save_string((char*)cipherText, "custIdfyNo2"); 


	// 요청 XML 생성
     strcpy( data_RequestXml_11,"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
				"<Root xmlns=\"http://www.nexacroplatform.com/platform/dataset\">\n"
				"\t<Parameters />\n"
				"\t<Dataset id=\"commonHeader\">\n"
				"\t\t<ColumnInfo>\n"
				"\t\t\t<Column id=\"appName\" type=\"STRING\" size=\"256\"  />\n"
				"\t\t\t<Column id=\"svcName\" type=\"STRING\" size=\"256\"  />\n"
				"\t\t\t<Column id=\"fnName\" type=\"STRING\" size=\"256\"  />\n"
				"\t\t\t<Column id=\"fnCd\" type=\"STRING\" size=\"256\"  />\n"
				"\t\t\t<Column id=\"globalNo\" type=\"STRING\" size=\"256\"  />\n"
				"\t\t\t<Column id=\"chnlType\" type=\"STRING\" size=\"256\"  />\n"
				"\t\t\t<Column id=\"envrFlag\" type=\"STRING\" size=\"256\"  />\n"
				"\t\t\t<Column id=\"trFlag\" type=\"STRING\" size=\"256\"  />\n"
				"\t\t\t<Column id=\"trDate\" type=\"STRING\" size=\"256\"  />\n"
				"\t\t\t<Column id=\"trTime\" type=\"STRING\" size=\"256\"  />\n"
				"\t\t\t<Column id=\"clntIp\" type=\"STRING\" size=\"256\"  />\n"
				"\t\t\t<Column id=\"responseType\" type=\"STRING\" size=\"256\"  />\n"
				"\t\t\t<Column id=\"responseCode\" type=\"STRING\" size=\"256\"  />\n"
				"\t\t\t<Column id=\"responseLogcd\" type=\"STRING\" size=\"256\"  />\n"
				"\t\t\t<Column id=\"responseTitle\" type=\"STRING\" size=\"256\"  />\n"
				"\t\t\t<Column id=\"responseBasc\" type=\"STRING\" size=\"256\"  />\n"
				"\t\t\t<Column id=\"responseDtal\" type=\"STRING\" size=\"256\"  />\n"
				"\t\t\t<Column id=\"responseSystem\" type=\"STRING\" size=\"256\"  />\n"
				"\t\t\t<Column id=\"userId\" type=\"STRING\" size=\"256\"  />\n"
				"\t\t\t<Column id=\"realUserId\" type=\"STRING\" size=\"256\"  />\n"
				"\t\t\t<Column id=\"filler\" type=\"STRING\" size=\"256\"  />\n"
				"\t\t\t<Column id=\"langCode\" type=\"STRING\" size=\"256\"  />\n"
				"\t\t\t<Column id=\"orgId\" type=\"STRING\" size=\"256\"  />\n"
				"\t\t\t<Column id=\"srcId\" type=\"STRING\" size=\"256\"  />\n"
				"\t\t\t<Column id=\"curHostId\" type=\"STRING\" size=\"256\"  />\n"
				"\t\t\t<Column id=\"lgDateTime\" type=\"STRING\" size=\"256\"  />\n"
				"\t\t\t<Column id=\"tokenId\" type=\"STRING\" size=\"256\"  />\n"
				"\t\t\t<Column id=\"cmpnCd\" type=\"STRING\" size=\"256\"  />\n"
				"\t\t\t<Column id=\"lockType\" type=\"STRING\" size=\"256\"  />\n"
				"\t\t\t<Column id=\"lockId\" type=\"STRING\" size=\"256\"  />\n"
				"\t\t\t<Column id=\"lockTimeSt\" type=\"STRING\" size=\"256\"  />\n"
				"\t\t\t<Column id=\"businessKey\" type=\"STRING\" size=\"256\"  />\n"
				"\t\t\t<Column id=\"arbitraryKey\" type=\"STRING\" size=\"256\"  />\n"
				"\t\t\t<Column id=\"resendFlag\" type=\"STRING\" size=\"256\"  />\n"
				"\t\t\t<Column id=\"phase\" type=\"STRING\" size=\"256\"  />\n"
				"\t\t</ColumnInfo>\n"
				"\t\t<Rows>\n"
				"\t\t\t<Row type=\"insert\">\n"
				"\t\t\t\t<Col id=\"appName\">NBSS_IUI</Col>\n"
				"\t\t\t\t<Col id=\"svcName\">/CDM/PipelineSVC/NIUI/CustInfo/PL_CustInfoIdfy</Col>\n"
				"\t\t\t\t<Col id=\"fnName\">retrieveCustInfoByCustIdfy</Col>\n"
			  	//"\t\t\t\t<Col id=\"fnCd\" />\n"
			    "\t\t\t\t<Col id=\"fnCd\">");
				memset (search, 'a', 1000);
				search[1000] =0x00;
				strcat (data_RequestXml_11, search);
				strcat (data_RequestXml_11, search);
				strcat (data_RequestXml_11, search);
				strcat (data_RequestXml_11, search);
				strcat (data_RequestXml_11, search);
				strcat (data_RequestXml_11, search);
				strcat (data_RequestXml_11, "</Col>\n"
				"\t\t\t\t<Col id=\"globalNo\">00000");
				strcat(data_RequestXml_11, lr_eval_string("{login_ID}"));
				strcat(data_RequestXml_11, lr_eval_string("{DATE}"));
				strcat(data_RequestXml_11, lr_eval_string("{TIME}"));
				strcat(data_RequestXml_11, lr_eval_string("{G_Random}"));
				strcat (data_RequestXml_11, "</Col>\n"
				"\t\t\t\t<Col id=\"chnlType\">UI</Col>\n"
				"\t\t\t\t<Col id=\"envrFlag\" />\n"
				"\t\t\t\t<Col id=\"trFlag\">T</Col>\n"
				"\t\t\t\t<Col id=\"trDate\">");
				strcat (data_RequestXml_11, lr_eval_string("{DATE}"));
				strcat (data_RequestXml_11, "</Col>\n"
				"\t\t\t\t<Col id=\"trTime\">");
				strcat (data_RequestXml_11, lr_eval_string("{TIME}"));
				strcat (data_RequestXml_11, "</Col>\n"
				"\t\t\t\t<Col id=\"clntIp\">");
				strcat( data_RequestXml_11,lr_eval_string("{IP_Param}"));
				strcat (data_RequestXml_11, "</Col>\n"
				"\t\t\t\t<Col id=\"responseType\" />\n"
				"\t\t\t\t<Col id=\"responseCode\" />\n"
				"\t\t\t\t<Col id=\"responseLogcd\" />\n"
				"\t\t\t\t<Col id=\"responseTitle\" />\n"
				"\t\t\t\t<Col id=\"responseBasc\" />\n"
				"\t\t\t\t<Col id=\"responseDtal\" />\n"
				"\t\t\t\t<Col id=\"responseSystem\" />\n"
				"\t\t\t\t<Col id=\"userId\">");
				strcat (data_RequestXml_11, lr_eval_string("{login_ID}"));
				strcat (data_RequestXml_11, "</Col>\n"
				"\t\t\t\t<Col id=\"realUserId\">");
				strcat (data_RequestXml_11, lr_eval_string("{login_ID}"));
				strcat (data_RequestXml_11, "</Col>\n"
				"\t\t\t\t<Col id=\"filler\">E</Col>\n"
				"\t\t\t\t<Col id=\"langCode\" />\n"
				"\t\t\t\t<Col id=\"orgId\">SPT8050</Col>\n"
				"\t\t\t\t<Col id=\"srcId\">DNIUI1000LY</Col>\n"
				"\t\t\t\t<Col id=\"curHostId\" />\n"
				"\t\t\t\t<Col id=\"lgDateTime\">");
				strcat (data_RequestXml_11, lr_eval_string("{DATE}"));
				strcat (data_RequestXml_11, lr_eval_string("{TIME}"));
				strcat (data_RequestXml_11, "</Col>\n"
				"\t\t\t\t<Col id=\"tokenId\">");
				strcat (data_RequestXml_11, lr_eval_string("{bbb}"));
				strcat (data_RequestXml_11, "</Col>\n"
				"\t\t\t\t<Col id=\"cmpnCd\">KTF</Col>\n"
				"\t\t\t\t<Col id=\"lockType\" />\n"
				"\t\t\t\t<Col id=\"lockId\" />\n"
				"\t\t\t\t<Col id=\"lockTimeSt\" />\n"
				"\t\t\t\t<Col id=\"businessKey\" />\n"
				"\t\t\t\t<Col id=\"arbitraryKey\">{}</Col>\n"
				"\t\t\t\t<Col id=\"resendFlag\" />\n"
				"\t\t\t\t<Col id=\"phase\">PA1</Col>\n"
				"\t\t\t</Row>\n"
				"\t\t</Rows>\n"
				"\t</Dataset>\n"
				"\t<Dataset id=\"bizHeader\">\n"
				"\t\t<ColumnInfo>\n"
				"\t\t\t<Column id=\"orderId\" type=\"STRING\" size=\"256\"  />\n"
				"\t\t\t<Column id=\"cbSvcName\" type=\"STRING\" size=\"256\"  />\n"
				"\t\t\t<Column id=\"cbFnName\" type=\"STRING\" size=\"256\"  />\n"
				"\t\t</ColumnInfo>\n"
				"\t\t<Rows>\n"
				"\t\t\t<Row type=\"insert\">\n"
				"\t\t\t\t<Col id=\"orderId\">orderId</Col>\n"
				"\t\t\t\t<Col id=\"cbSvcName\">/CDM/PipelineSVC/NIUI/CustInfo/PL_CustInfoIdfy</Col>\n"
				"\t\t\t\t<Col id=\"cbFnName\">retrieveCustInfoByCustIdfy</Col>\n"
				"\t\t\t</Row>\n"
				"\t\t</Rows>\n"
				"\t</Dataset>\n"
				"\t<Dataset id=\"CustIdfyDTO\">\n"
				"\t\t<ColumnInfo>\n"
				"\t\t\t<Column id=\"custIdfyNoDivCd\" type=\"STRING\" size=\"256\"  />\n"
				"\t\t\t<Column id=\"custIdfyNoTypeCd\" type=\"STRING\" size=\"256\"  />\n"
				"\t\t\t<Column id=\"custIdfyNo\" type=\"STRING\" size=\"256\"  />\n"
				"\t\t\t<Column id=\"svcNo\" type=\"STRING\" size=\"256\"  />\n"
				"\t\t\t<Column id=\"custNm\" type=\"STRING\" size=\"256\"  />\n"
				"\t\t\t<Column id=\"dbRetvSeq\" type=\"STRING\" size=\"256\"  />\n"
				"\t\t\t<Column id=\"dbRetvCascnt\" type=\"STRING\" size=\"256\"  />\n"
				"\t\t\t<Column id=\"admrRetvYn\" type=\"STRING\" size=\"256\"  />\n"
				"\t\t\t<Column id=\"saleCmpnId\" type=\"STRING\" size=\"256\"  />\n"
				"\t\t\t<Column id=\"aftmnInclYn\" type=\"STRING\" size=\"256\"  />\n"
				"\t\t</ColumnInfo>\n"
				"\t\t<Rows>\n"
				"\t\t\t<Row>\n"
				"\t\t\t\t<Col id=\"custIdfyNoDivCd\">S</Col>\n"
				"\t\t\t\t<Col id=\"custIdfyNoTypeCd\">S01</Col>\n"
				"\t\t\t\t<Col id=\"custIdfyNo\">");
				strcat (data_RequestXml_11, cipherText); /* lr_eval_string("{custIdfyNo2}")); */
				//strcat (data_RequestXml_11, "01090303608");
				strcat (data_RequestXml_11, "</Col>\n"
				"\t\t\t\t<Col id=\"dbRetvSeq\">0</Col>\n"
				"\t\t\t\t<Col id=\"dbRetvCascnt\">20</Col>\n"
				"\t\t\t\t<Col id=\"admrRetvYn\">Y</Col>\n"
				"\t\t\t\t<Col id=\"saleCmpnId\">KT</Col>\n"
				"\t\t\t\t<Col id=\"aftmnInclYn\">N</Col>\n"
				"\t\t\t</Row>\n"
				"\t\t</Rows>\n"
				"\t</Dataset>\n"
				"</Root>",
		LAST);

	 //lr_output_message("%s",data_RequestXml_11);
	 
	 web_set_max_html_param_len("800000");



	  


 if (EncYN == 0) {		//평문 조회
	 lr_save_string( data_RequestXml_11, "P_data_RequestXml_1");
	//lr_start_transaction("AD_서비스 번호로 고객 조회_retrieveCustInfoByCustIdfy");
	 lr_start_transaction("01_서비스번호로 고객검색_ASIS");   
	
	 web_reg_find("Text=<Parameter id=\"ErrorCode\" type=\"string\">0</Parameter>",LAST);

	 web_reg_save_param("P_custId","LB=<Col id=\"custId\">","RB=</Col>",LAST);
	 web_reg_save_param("P_svcContId","LB=<Col id=\"svcContId\">","RB=</Col>",LAST);

	 //lr_save_string( "BZGL7HMK2LU", "P_custId");
	 //lr_save_string( "508853331", "P_svcContId");	

	 //web_custom_request("UiDynamicGateway",
	 web_custom_request("UiGateway",
          "URL=http://{ESB_IP}/UiGateway ",
		  "Method=POST",
		  "Resource=0",
		  "RecContentType=text/html",
		  "Referer=file://C:/Users/Administrator/Documents/nexacro/outs/AdaptorTest/Base/USAM9908FM_sit.xfdl.js",
		  "Snapshot=t11.inf",
		  "Mode=HTML",
		  "Body={P_data_RequestXml_1}",
		  LAST);
	 lr_end_transaction("01_서비스번호로 고객검색_ASIS", LR_AUTO);
	  //lr_message("data_ResponseXml_1=%s", data_ResponseXml_11);

   //lr_end_transaction("AD_서비스 번호로 고객 조회_retrieveCustInfoByCustIdfy", LR_AUTO);
 
	  

} else if (EncYN == 1) {			//암호화


	memset(data_RequestXmlEnc_11, 0, sizeof(data_RequestXmlEnc_11));	

	// Endcoe/Decode 비교 ransaction 위치 (03.25)
	lr_start_transaction("01_서비스번호로 고객검색_checkSum");   

	memset (checkSum, 0x00, sizeof (checkSum));

//	hmac_sha256_2 (secretKey2, strlen(secretKey2), 
	HmacSha256 (secretKey2, strlen(secretKey2), 
				 data_RequestXml_11 , strlen(data_RequestXml_11) ,
				 checksum_pos, checksum_len, // 메시지 시작위치, 메시지 크기
	  			 //-150, 1000, // 메시지 시작위치, 메시지 크기
                 checkSum, 32);
	//lr_output_message("checksum :: %s:%s:%s:%d", data_RequestXml_11, checkSum, secretKey2, strlen (secretKey2));


//	strcpy (checkSum, "LP3X9WkTIdZiHNJcqKMGNnQTaWpL+XS5Oy6eqSyTXuI=");
     sprintf (data_RequestXmlEnc_11, "%s%s", checkSum, data_RequestXml_11);
	 lr_save_string( data_RequestXmlEnc_11, "Data_encrypt_out_1");


	//web_reg_save_param("len","LB=Content-Length: ","RB=\r\n",LAST);
	//web_reg_save_param("Data_encrypt_res",  "LB=\r\n\r\n",  "RB=",  LAST);

	 //web_reg_save_param("P_custId","LB=<Col id=\"custId\">","RB=</Col>",LAST);
	 //web_reg_save_param("P_svcContId","LB=<Col id=\"svcContId\">","RB=</Col>",LAST);


	//lr_start_transaction("AE_서비스 번호로 고객 조회_retrieveCustInfoByCustIdfy");
	// 측정 Transaction 위치 (03.25)
	//lr_start_transaction("서비스번호 조회_암호");   

	 web_reg_find("Text=<Parameter id=\"ErrorCode\" type=\"string\">0</Parameter>",LAST);

	 web_reg_save_param("P_custId","LB=<Col id=\"custId\">","RB=</Col>",LAST);
	 web_reg_save_param("P_svcContId","LB=<Col id=\"svcContId\">","RB=</Col>",LAST);

	 //lr_save_string( "BZGL7HMK2LU", "P_custId");
	 //lr_save_string( "508853331", "P_svcContId");	

	 //web_custom_request("UiDynamicGateway",
	 web_custom_request("UiGateway",
          "URL=http://{ESB_IP}/UiGateway ",
		  "Method=POST",
		  "Resource=0",
		  "RecContentType=text/html",
		  "Referer=file://C:/Users/Administrator/Documents/nexacro/outs/AdaptorTest/Base/USAM9908FM_sit.xfdl.js",
		  "Snapshot=t11.inf",
		  "Mode=HTML",
		  "Body={Data_encrypt_out_1}",
		  LAST);
     
	  // 측정 Transaction 위치 (03.25)
	  //lr_stop_transaction("서비스번호 조회_암호");

	 //data_ResponseXmlENC_11 =  lr_eval_string("{Data_encrypt_res}");

	  /*
	  if (FALSE == Decode(handle_id, data_ResponseXmlENC_11 ,strlen(data_ResponseXmlENC_11), data_ResponseXml_11, (int *) & len1))
	  {
		lr_message("[ERROR] %s", "Failed to decrypted Response");
	  }
*/
	  // Endcoe/Decode 비교 ransaction 위치 (03.25)
//	  lr_stop_transaction("01_서비스번호로 고객검색_checkSum");
	 lr_end_transaction("01_서비스번호로 고객검색_checkSum", LR_AUTO);
     //lr_message("data_ResponseXml_1=%s", data_ResponseXml_11);

/*

	 //custId    String 에서 특정 문자열 추출
	 memset(search_str1,0x00,sizeof(search_str1));
	 memset(search_str2,0x00,sizeof(search_str2));
	 strcpy(search_str1,"<Col id=\"custId\">");
	 strcpy(search_str2,"</Col>");
	 memset(searched_str,0x00,sizeof(searched_str));


	
     position1 = (char *)strstr(data_ResponseXml_11, search_str1); 
     offset1 = (int)(position1 - data_ResponseXml_11 + 1); 
     //lr_output_message ("The string \"%s\" was found at position %d", search_str1, offset1); 
	 position2 = (char *)strstr(position1, search_str2); 
     offset2 = (int)(position2 - position1 + 1); 
     //lr_output_message ("The string \"%s\" was found at position %d", search_str2, offset2); 
	 
	 strncpy(searched_str,&position1[strlen(search_str1)],offset2-strlen(search_str1)-1);
	 lr_save_string(searched_str,"P_custId");
	 
	 
	 //lr_output_message ("추출 파라미터 \"%s\"", lr_eval_string("{P_custId}")); 


	 //svcContId    String 에서 특정 문자열 추출
	 memset(search_str1,0x00,sizeof(search_str1));
	 memset(search_str2,0x00,sizeof(search_str2));
	 strcpy(search_str1,"<Col id=\"svcContId\">");
	 strcpy(search_str2,"</Col>");
	 memset(searched_str,0x00,sizeof(searched_str));

     position1 = (char *)strstr(data_ResponseXml_11, search_str1); 
     offset1 = (int)(position1 - data_ResponseXml_11 + 1); 
     //lr_output_message ("The string \"%s\" was found at position %d", search_str1, offset1); 
	 position2 = (char *)strstr(position1, search_str2); 
     offset2 = (int)(position2 - position1 + 1); 
     
	 strncpy(searched_str,&position1[strlen(search_str1)],offset2-strlen(search_str1)-1);

	 lr_save_string(searched_str,"P_svcContId");
 

	 lr_output_message ("추출 파라미터 \"%s\"", lr_eval_string("{P_svcContId}")); 
*/    
}

	free(data_RequestXml_11);
	free(data_ResponseXml_11);
	free(data_RequestXmlEnc_11);

      

	   return 0;
}
