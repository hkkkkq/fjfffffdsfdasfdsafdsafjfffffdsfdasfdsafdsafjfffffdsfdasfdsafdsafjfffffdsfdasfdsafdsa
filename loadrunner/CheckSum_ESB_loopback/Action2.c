Action2()
{
	  
	char* data_RequestXml_2;
	char* data_RequestXmlEnc_2;

	char* data_ResponseXml_2 = NULL;
	char* data_ResponseXmlENC_2 = NULL;

	int x;

	
/* Decoded XML에서 문자열 추출*/
/*
	int offset1; 
	int offset2; 
	int lng;
    char * position1; 
	char * position2; 
	
	char search_str2[100];  
	char searched_str[8192];
	
    char custNo[4000];
    char cipherText[4000];
*/
	char checkSum[100];


	 if ((data_RequestXml_2 = (char *)calloc(20000, sizeof(char))) == NULL) { 			//6000
		 lr_output_message ("Insufficient memory available"); 
		 return -1; 
	 } 
	
     if ((data_RequestXmlEnc_2 = (char *)calloc(20000, sizeof(char))) == NULL) { 		//6000
        lr_output_message ("Insufficient memory available"); 
        return 0; 
     }

	 if ((data_ResponseXml_2 = (char *)calloc(20000, sizeof(char))) == NULL) { 		//6000
		lr_output_message ("Insufficient memory available"); 
		return 0; 
	 }

	 if ((data_ResponseXmlENC_2 = (char *)calloc(20000, sizeof(char))) == NULL) { 		//6000
		lr_output_message ("Insufficient memory available"); 
		return 0; 
	 }


	//lr_output_message("서비스요요청 URL : %s",lr_eval_string("{temp_URL}"));

	 /*
    lr_load_dll("C:\\sadmod.dll"); 

    //x = lr_load_dll("C:\\nexaNbssAdp.dll");
	//lr_message("lr_load_dll result =%d",x);
	lr_load_dll("nexaNbssAdp.dll");
*/

	/*
    memset(custNo, 0x00, sizeof (custNo));
    strcpy(custNo,lr_eval_string("{custIdfyNo}"));
	//strcpy(custNo,"01033297329");
	//strcpy(custNo,"01090303608");
	//strcpy(custNo,"0KPRDS8OS21");

    memset(cipherText, 0, sizeof(cipherText)); 

    lng = seed_enc(secretKey1,custNo,strlen(custNo),cipherText);

//    lr_save_string((char*)cipherText, "custIdfyNo2"); 

	*/

	// 요청 XML 생성
    strcpy( data_RequestXml_2,"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
				"<Root xmlns=\"http://www.nexacroplatform.com/platform/dataset\">\n"
				"\t<Parameters>\n"
                "<Parameter id=\"PHAROSVISITOR\">00004f3f0154e03263c62cdd0adb0756</Parameter>"
			    "\t</Parameters>\n"
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
				"\t\t\t\t<Col id=\"appName\">NBSS_CST</Col>\n"
				"\t\t\t\t<Col id=\"svcName\">/ESBFW/SATest/ETC/PL_LoopBack2</Col>\n"
				"\t\t\t\t<Col id=\"fnName\">service</Col>\n"
			  	"\t\t\t\t<Col id=\"fnCd\" />\n"
				"\t\t\t\t<Col id=\"globalNo\">00000");
				strcat(data_RequestXml_2, lr_eval_string("{login_ID}"));
				strcat(data_RequestXml_2, lr_eval_string("{DATE}"));
				strcat(data_RequestXml_2, lr_eval_string("{TIME}"));
				strcat(data_RequestXml_2, lr_eval_string("{G_Random}"));
				strcat(data_RequestXml_2, "</Col>\n"
				"\t\t\t\t<Col id=\"chnlType\">UI</Col>\n"
				"\t\t\t\t<Col id=\"envrFlag\" />\n"
				"\t\t\t\t<Col id=\"trFlag\">T</Col>\n"
				"\t\t\t\t<Col id=\"trDate\">");
				strcat (data_RequestXml_2, lr_eval_string("{DATE}"));
				strcat (data_RequestXml_2, "</Col>\n"
				"\t\t\t\t<Col id=\"trTime\">");
				strcat (data_RequestXml_2, lr_eval_string("{TIME}"));
				strcat (data_RequestXml_2, "</Col>\n"
				"\t\t\t\t<Col id=\"clntIp\">");
				strcat( data_RequestXml_2,lr_eval_string("{IP}"));
				strcat (data_RequestXml_2, "</Col>\n"
				"\t\t\t\t<Col id=\"responseType\" />\n"
				"\t\t\t\t<Col id=\"responseCode\" />\n"
				"\t\t\t\t<Col id=\"responseLogcd\" />\n"
				"\t\t\t\t<Col id=\"responseTitle\" />\n"
				"\t\t\t\t<Col id=\"responseBasc\" />\n"
				"\t\t\t\t<Col id=\"responseDtal\" />\n"
				"\t\t\t\t<Col id=\"responseSystem\" />\n"
				"\t\t\t\t<Col id=\"userId\">");
				strcat (data_RequestXml_2, lr_eval_string("{login_ID}"));
				strcat (data_RequestXml_2, "</Col>\n"
				"\t\t\t\t<Col id=\"realUserId\">");
				strcat (data_RequestXml_2, lr_eval_string("{login_ID}"));
				strcat (data_RequestXml_2, "</Col>\n"
				"\t\t\t\t<Col id=\"filler\">E</Col>\n"
				"\t\t\t\t<Col id=\"langCode\" />\n"
				"\t\t\t\t<Col id=\"orgId\">SPT8050</Col>\n"
				"\t\t\t\t<Col id=\"srcId\">DNIUI1000LY</Col>\n"
				"\t\t\t\t<Col id=\"curHostId\" />\n"
				"\t\t\t\t<Col id=\"lgDateTime\">");
				strcat (data_RequestXml_2, lr_eval_string("{DATE}"));
				strcat (data_RequestXml_2, lr_eval_string("{TIME}"));
				strcat (data_RequestXml_2, "</Col>\n"
				"\t\t\t\t<Col id=\"tokenId\">");
				strcat (data_RequestXml_2, lr_eval_string("{bbb}"));
				strcat (data_RequestXml_2, "</Col>\n"
				"\t\t\t\t<Col id=\"cmpnCd\">KT</Col>\n"
				"\t\t\t\t<Col id=\"lockType\" />\n"
				"\t\t\t\t<Col id=\"lockId\" />\n"
				"\t\t\t\t<Col id=\"lockTimeSt\" />\n"
				"\t\t\t\t<Col id=\"businessKey\" />\n"
				"\t\t\t\t<Col id=\"arbitraryKey\">{}</Col>\n"
				"\t\t\t\t<Col id=\"resendFlag\" />\n"
				"\t\t\t\t<Col id=\"phase\">PA3</Col>\n"
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
				"\t\t\t\t<Col id=\"cbSvcName\">/ESBFW/SATest/ETC/PL_LoopBack2</Col>\n"
				"\t\t\t\t<Col id=\"cbFnName\">service</Col>\n"
				"\t\t\t</Row>\n"
				"\t\t</Rows>\n"
				"\t</Dataset>\n"
				"<Dataset id=\"EchoInDTO\">"
						"<ColumnInfo>"
							"<Column id=\"loopCnt\" type=\"STRING\" size=\"32\"  />"
						"</ColumnInfo>"
						"<Rows>"
							"<Row>"
								"<Col id=\"loopCnt\">10</Col>"
							"</Row>"
						"</Rows>"
					"</Dataset>"
				"</Root>",
		LAST);

     
	 web_set_max_html_param_len("800000");

	 lr_start_transaction("ESBFW_SATest_ETC_PL_LoopBack2"); 

	  if (EncYN == 0) {	

          lr_save_string( data_RequestXml_2, "P_data_RequestXml_1");

		  web_reg_find("Text=<Parameter id=\"ErrorCode\" type=\"string\">0</Parameter>",LAST);

		 //web_reg_save_param("P_custId","LB=<Col id=\"custId\">","RB=</Col>",LAST);
		 //web_reg_save_param("P_svcContId","LB=<Col id=\"svcContId\">","RB=</Col>",LAST);
	
		 //lr_save_string( "BZGL7HMK2LU", "P_custId");
		 //lr_save_string( "508853331", "P_svcContId");	

		   lr_output_message("%s",data_RequestXml_2);
		 //web_custom_request("UiDynamicGateway",
		 web_custom_request("UiGateway",
			  "URL=http://{ESB_IP}/UiGateway", 
			  "Method=POST",
			  "Resource=0",
			  "RecContentType=text/html",
			  "Referer=http://10.219.6.130:5000/NBSS/FRM/NBSS_LOADING.xfdl.js",
			  "Snapshot=t11.inf",
			  "Mode=HTML",
			  "Body={P_data_RequestXml_1}",
			  LAST);
	

	  } else if (EncYN == 1) {
		   memset(data_RequestXmlEnc_2, 0, sizeof(data_RequestXmlEnc_2));	

		  	memset (checkSum, 0x00, sizeof (checkSum));


			HmacSha256 (secretKey2, strlen(secretKey2), 
						 data_RequestXml_2 , strlen(data_RequestXml_2) ,
						 checksum_pos, checksum_len, // 메시지 시작위치, 메시지 크기
						 //-150, 1000, // 메시지 시작위치, 메시지 크기
						 checkSum, 32);


			 sprintf (data_RequestXmlEnc_2, "%s%s", checkSum, data_RequestXml_2);

			 lr_save_string( data_RequestXmlEnc_2, "Data_encrypt_out_1");
		
		
			 web_reg_find("Text=<Parameter id=\"ErrorCode\" type=\"string\">0</Parameter>",LAST);
		
			 //web_reg_save_param("P_custId","LB=<Col id=\"custId\">","RB=</Col>",LAST);
			 //web_reg_save_param("P_svcContId","LB=<Col id=\"svcContId\">","RB=</Col>",LAST);
		
			 //lr_save_string( "BZGL7HMK2LU", "P_custId");
			 //lr_save_string( "508853331", "P_svcContId");	

			 lr_output_message("%s",data_RequestXmlEnc_2);

			 web_custom_request("UiGateway",
				  "URL=http://{ESB_IP}/UiGateway", 
				  "Method=POST",
				  "Resource=0",
				  "RecContentType=text/html",
				  "Referer=http://10.219.6.130:5000/NBSS/FRM/NBSS_LOADING.xfdl.js",
				  "Snapshot=t11.inf",
				  "Mode=HTML",
				  "Body={Data_encrypt_out_1}",
				  LAST);
		
	}


     
	lr_end_transaction("ESBFW_SATest_ETC_PL_LoopBack2", LR_AUTO);

	free(data_RequestXml_2);
	free(data_RequestXmlEnc_2);
	free(data_ResponseXml_2);
	free(data_ResponseXmlENC_2);

	return 0;
}

