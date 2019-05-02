
//05_상품조회

//3. CustSvcFirstLvlInfoRetvSO  스크립트 작업 테스트
Action31()
{
//	int EncYN =1;			//암호화 여부    0=평문     1:암호화

	char* data_RequestXml_31;				//요청 평문 XML
	char data_RequestXmlEnc_31[20000];		//요청 Encoded XML


	char* data_ResponseXmlENC_31;			//응답 암호화문 XML    
	char* data_ResponseXml_31; 				//응답 Deoded XML

	int len1;
	unsigned int len;

	
/* Decoded XML에서 문자열 추출*/
	int offset1; 
	int offset2; 
    char * position1; 
	char * position2; 
	char search_str1[100];  
	char search_str2[100];  
	char searched_str[8192];

//    char custNo[4000];
//    char cipherText[4000];
	char checkSum[100];

	if ((data_RequestXml_31 = (char *)calloc(20000, sizeof(char))) == NULL) { 
		 lr_output_message ("Insufficient memory available"); 
		 return -1; 
	 } 
	
     if ((data_ResponseXml_31 = (char *)calloc(20000, sizeof(char))) == NULL) { 
        lr_output_message ("Insufficient memory available"); 
        return 0; 
    }

    //lr_load_dll("C:\\sadmod.dll"); 

    //lr_load_dll("C:\\nexaNbssAdp.dll"); 

	 //if (FALSE == Init(handle_id,lr_eval_string("{temp_URL}")))
	 //{
	//	 lr_message("[ERROR] %s", "Failed to initialize");
	 //}

	// 요청 XML 생성
     strcpy( data_RequestXml_31,		"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
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
		"				<Col id=\"svcName\">CustSvcFirstLvlInfoRetvSO</Col>\n"
		"				<Col id=\"fnName\">service</Col>\n"
		//"				<Col id=\"fnCd\" />\n"
			 "\t\t\t\t<Col id=\"fnCd\">");
		memset (search, 'a', 1000);
		search[1000] =0x00;
		strcat (data_RequestXml_31, search);
		strcat (data_RequestXml_31, search);
		strcat (data_RequestXml_31, search);
		strcat (data_RequestXml_31, search);
		strcat (data_RequestXml_31, search);
		strcat (data_RequestXml_31, search);
		strcat (data_RequestXml_31, "</Col>\n"
		"\t\t\t\t<Col id=\"globalNo\">00000");
				strcat(data_RequestXml_31, lr_eval_string("{login_ID}"));
				strcat(data_RequestXml_31, lr_eval_string("{DATE}"));
				strcat(data_RequestXml_31, lr_eval_string("{TIME}"));
				strcat(data_RequestXml_31, lr_eval_string("{G_Random}"));
				strcat (data_RequestXml_31, "</Col>\n"
			"				<Col id=\"chnlType\">UI</Col>\n"
			"				<Col id=\"envrFlag\" />\n"
			"				<Col id=\"trFlag\">T</Col>\n"
			"\t\t\t\t<Col id=\"trDate\">");
				strcat (data_RequestXml_31, lr_eval_string("{DATE}"));
				strcat (data_RequestXml_31, "</Col>\n"
				"\t\t\t\t<Col id=\"trTime\">");
				strcat (data_RequestXml_31, lr_eval_string("{TIME}"));
				strcat (data_RequestXml_31, "</Col>\n"
			"\t\t\t\t<Col id=\"clntIp\">");
				strcat( data_RequestXml_31,lr_eval_string("{IP_Param}"));
				strcat (data_RequestXml_31, "</Col>\n"
			"				<Col id=\"responseType\" />\n"
			"				<Col id=\"responseCode\" />\n"
			"				<Col id=\"responseLogcd\" />\n"
			"				<Col id=\"responseTitle\" />\n"
			"				<Col id=\"responseBasc\" />\n"
			"				<Col id=\"responseDtal\" />\n"
			"				<Col id=\"responseSystem\" />\n"
			"\t\t\t\t<Col id=\"userId\">");
				strcat (data_RequestXml_31, lr_eval_string("{login_ID}"));
				strcat (data_RequestXml_31, "</Col>\n"
				"\t\t\t\t<Col id=\"realUserId\">");
				strcat (data_RequestXml_31, lr_eval_string("{login_ID}"));
				strcat (data_RequestXml_31, "</Col>\n"
			"				<Col id=\"filler\">E</Col>\n"
			"				<Col id=\"langCode\" />\n"
			"				<Col id=\"orgId\">SPT8050</Col>\n"
			"				<Col id=\"srcId\">DNIUI3000FM</Col>\n"
			"				<Col id=\"curHostId\" />\n"
			"\t\t\t\t<Col id=\"lgDateTime\">");
				strcat (data_RequestXml_31, lr_eval_string("{DATE}"));
				strcat (data_RequestXml_31, lr_eval_string("{TIME}"));
				strcat (data_RequestXml_31, "</Col>\n"
			"<Col id=\"tokenId\">");
			strcat (data_RequestXml_31, lr_eval_string("{bbb}"));
			strcat (data_RequestXml_31,"</Col>\n"
			"				<Col id=\"cmpnCd\">KTF</Col>\n"
			"				<Col id=\"lockType\" />\n"
			"				<Col id=\"lockId\" />\n"
			"				<Col id=\"lockTimeSt\" />\n"
			"\t\t\t\t<Col id=\"businessKey\">0000");
				  strcat(data_RequestXml_31, lr_eval_string("{login_ID}"));
				  strcat(data_RequestXml_31, "00A_MASTER");
				  strcat(data_RequestXml_31, lr_eval_string("{DATE}"));
				  strcat(data_RequestXml_31, lr_eval_string("{TIME}"));
			strcat(data_RequestXml_31, "531</Col>\n");
			strcat(data_RequestXml_31, "				<Col id=\"arbitraryKey\">{}</Col>\n"
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
		"				<Col id=\"cbSvcName\">CustSvcFirstLvlInfoRetvSO</Col>\n"
		"				<Col id=\"cbFnName\">service</Col>\n"
		"			</Row>\n"
		"		</Rows>\n"
		"	</Dataset>\n"
		"	<Dataset id=\"SvcItgHierParamDTO\">\n"
		"		<ColumnInfo>\n"
		"			<Column id=\"custId\" type=\"STRING\" size=\"256\"  />\n"
		"			<Column id=\"svcContId\" type=\"STRING\" size=\"256\"  />\n"
		"			<Column id=\"billAccId\" type=\"STRING\" size=\"256\"  />\n"
		"			<Column id=\"prodTypeCd\" type=\"STRING\" size=\"256\"  />\n"
		"			<Column id=\"retvTypeCd\" type=\"STRING\" size=\"256\"  />\n"
		"			<Column id=\"prodFindTypeCd\" type=\"STRING\" size=\"256\"  />\n"
		"			<Column id=\"prodFindNm\" type=\"STRING\" size=\"256\"  />\n"
		"			<Column id=\"aftmnInclYn\" type=\"STRING\" size=\"256\"  />\n"
		"			<Column id=\"admrRetvYn\" type=\"STRING\" size=\"256\"  />\n"
		"			<Column id=\"saleCmpnId\" type=\"STRING\" size=\"256\"  />\n"
		"			<Column id=\"ancyRetvAutYn\" type=\"STRING\" size=\"256\"  />\n"
		"			<Column id=\"ecustCentrDivCd\" type=\"STRING\" size=\"256\"  />\n"
		"		</ColumnInfo>\n"
		"		<Rows>\n"
		"			<Row>\n"
		"				<Col id=\"svcContId\">");
			strcat(data_RequestXml_31, lr_eval_string("{P_svcContId}"));
			strcat(data_RequestXml_31, "</Col>\n"
			"				<Col id=\"custId\">");
			strcat(data_RequestXml_31, lr_eval_string("{P_custId}"));
			strcat(data_RequestXml_31, "</Col>\n"
		"				<Col id=\"billAccId\" />\n"
		"				<Col id=\"prodTypeCd\" />\n"
		"				<Col id=\"retvTypeCd\">1</Col>\n"
		"				<Col id=\"prodFindTypeCd\" />\n"
		"				<Col id=\"prodFindNm\" />\n"
		"				<Col id=\"aftmnInclYn\">N</Col>\n"
		"				<Col id=\"admrRetvYn\">Y</Col>\n"
		"				<Col id=\"saleCmpnId\">KT</Col>\n"
		"				<Col id=\"ancyRetvAutYn\">Y</Col>\n"
		"				<Col id=\"ecustCentrDivCd\">N</Col>\n"
		"			</Row>\n"
		"		</Rows>\n"
		"	</Dataset>\n"
		"</Root>\n",
		LAST);

	 //lr_output_message("%s",data_RequestXml_31);
	 
	 web_set_max_html_param_len("1000000");

  


 if (EncYN == 0) {		//평문 조회

	 lr_save_string( data_RequestXml_31, "P_data_RequestXml_1");
	//lr_start_transaction("CD_고객보유상품 조회_CustSvcFirstLvlInfoRetvSO");
	 lr_start_transaction("05_상품조회_TOBE");
    
	

	web_reg_find("Text=<Parameter id=\"ErrorCode\" type=\"string\">0</Parameter>",LAST);
//	 web_reg_save_param("P_billAccId","LB=<Col id=\"billAccId\">","RB=</Col>",LAST);
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


   //lr_end_transaction("CD_고객보유상품 조회_CustSvcFirstLvlInfoRetvSO", LR_AUTO);
	  lr_end_transaction("05_상품조회_TOBE",LR_AUTO);
	  

} else if (EncYN == 1) {			//암호화

	memset(data_RequestXmlEnc_31, 0, sizeof(data_RequestXmlEnc_31));	 

	// Endcoe/Decode 비교 ransaction 위치 (03.25)
	lr_start_transaction("05_상품조회_checkSum");

	memset (checkSum, 0x00, sizeof (checkSum));

//	hmac_sha256_2 (secretKey2, strlen(secretKey2), 
	HmacSha256 (secretKey2, strlen(secretKey2), 
				 data_RequestXml_31 , strlen(data_RequestXml_31) ,
                 checksum_pos, checksum_len, // 메시지 시작위치, 메시지 크기
//                 0, 100, // 메시지 시작위치, 메시지 크기
                 checkSum, 32);
	//lr_output_message("checksum :: %s:%s:%s:%d", data_RequestXml_31, checkSum, secretKey2, strlen (secretKey2));


//	strcpy (checkSum, "LP3X9WkTIdZiHNJcqKMGNnQTaWpL+XS5Oy6eqSyTXuI=");
     sprintf (data_RequestXmlEnc_31, "%s%s", checkSum, data_RequestXml_31);
/*	 lr_save_string( data_RequestXmlEnc_21, "Data_encrypt_out_1");

	 if (FALSE == Encode(handle_id, data_RequestXml_31 , strlen(data_RequestXml_31), data_RequestXmlEnc_31))
	 {
		  lr_message("[ERROR] %s", "Failed to encrypted Request");
	 }
	 //lr_output_message("Encoded : %s",data_RequestXmlEnc_31);
*/

	 lr_save_string( data_RequestXmlEnc_31, "Data_encrypt_out_1");


	//web_reg_save_param("len","LB=Content-Length: ","RB=\r\n",LAST);
//	web_reg_save_param("Data_encrypt_res",  "LB=\r\n\r\n",  "RB=",  LAST);


	//lr_start_transaction("CE_고객보유상품 조회_CustSvcFirstLvlInfoRetvSO");
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

	  // 측정 Transaction 위치 (03.25)
	  //lr_stop_transaction("서비스번호 조회_암호");
     

//	 data_ResponseXmlENC_31 =  lr_eval_string("{Data_encrypt_res}");
/*
	 if (FALSE == Decode(handle_id, data_ResponseXmlENC_31 ,strlen(data_ResponseXmlENC_31), data_ResponseXml_31, (int *) & len1))
	 {
	   lr_message("[ERROR] %s", "Failed to decrypted Response");
	 }
*/
	 // Endcoe/Decode 비교 ransaction 위치 (03.25)
	 lr_end_transaction("05_상품조회_checkSum",LR_AUTO);

	 //lr_message("data_ResponseXml_1=%s", data_ResponseXml_31);



	 //custId    String 에서 특정 문자열 추출
/*	 memset(search_str1,0x00,sizeof(search_str1));
	 memset(search_str2,0x00,sizeof(search_str2));
	 strcpy(search_str1,"<Col id=\"billAccId\">");
	 strcpy(search_str2,"</Col>");
	 memset(searched_str,0x00,sizeof(searched_str));


	
     position1 = (char *)strstr(data_ResponseXml_31, search_str1); 
     offset1 = (int)(position1 - data_ResponseXml_31 + 1); 
     //lr_output_message ("The string \"%s\" was found at position %d", search_str1, offset1); 
	 position2 = (char *)strstr(position1, search_str2); 
     offset2 = (int)(position2 - position1 + 1); 
     //lr_output_message ("The string \"%s\" was found at position %d", search_str2, offset2); 
	 
	 strncpy(searched_str,&position1[strlen(search_str1)],offset2-strlen(search_str1)-1);
	 lr_save_string(searched_str,"P_billAccId");
*/	 
	 
	 //lr_output_message ("추출 파라미터 \"%s\"", lr_eval_string("{P_billAccId}")); 



}

	free(data_RequestXml_31);
	free(data_ResponseXml_31);






	   return 0;
}
