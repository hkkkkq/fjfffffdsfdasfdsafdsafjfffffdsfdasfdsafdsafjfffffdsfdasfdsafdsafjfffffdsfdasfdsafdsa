////////////////////////////  미사용 //////////////////////////////////////

Action()
{

	int EncYN = 1;			//암호화 여부    0=평문     1:암호화

	char* data_RequestXml_1;				//요청 평문 XML
	char data_RequestXmlEnc_1[8192];		//요청 Encoded XML


	char* data_ResponseXmlENC_1;			//응답 암호화문 XML    
	char* data_ResponseXml_1; 				//응답 Deoded XML

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






////////////////////////////////////////////////////////////////////////
// 서비스 번호로 고객 조회_retrieveCustInfoByCustIdfy




	if ((data_RequestXml_1 = (char *)calloc(60000, sizeof(char))) == NULL) { 			//6000
		 lr_output_message ("Insufficient memory available"); 
		 return -1; 
	 } 
	
     if ((data_ResponseXml_1 = (char *)calloc(60000, sizeof(char))) == NULL) { 		//6000
        lr_output_message ("Insufficient memory available"); 
        return 0; 
    }
	 if ((data_ResponseXmlENC_1 = (char *)calloc(60000, sizeof(char))) == NULL) { 		//6000
        lr_output_message ("Insufficient memory available"); 
        return 0; 
    }

	
	


	// 요청 XML 생성
     strcpy( data_RequestXml_1,"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
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
				"\t\t\t\t<Col id=\"fnCd\" />\n"
				"\t\t\t\t<Col id=\"globalNo\">00000");
				strcat(data_RequestXml_1, lr_eval_string("{login_ID}"));
				strcat(data_RequestXml_1, lr_eval_string("{DATE}"));
				strcat(data_RequestXml_1, lr_eval_string("{TIME}"));
				strcat(data_RequestXml_1, lr_eval_string("{G_Random}"));
				strcat (data_RequestXml_1, "</Col>\n"
				"\t\t\t\t<Col id=\"chnlType\">UI</Col>\n"
				"\t\t\t\t<Col id=\"envrFlag\" />\n"
				"\t\t\t\t<Col id=\"trFlag\">T</Col>\n"
				"\t\t\t\t<Col id=\"trDate\">");
				strcat (data_RequestXml_1, lr_eval_string("{DATE}"));
				strcat (data_RequestXml_1, "</Col>\n"
				"\t\t\t\t<Col id=\"trTime\">");
				strcat (data_RequestXml_1, lr_eval_string("{TIME}"));
				strcat (data_RequestXml_1, "</Col>\n"
				"\t\t\t\t<Col id=\"clntIp\">");
				strcat( data_RequestXml_1,lr_eval_string("{IP_Param}"));
				strcat (data_RequestXml_1, "</Col>\n"
				"\t\t\t\t<Col id=\"responseType\" />\n"
				"\t\t\t\t<Col id=\"responseCode\" />\n"
				"\t\t\t\t<Col id=\"responseLogcd\" />\n"
				"\t\t\t\t<Col id=\"responseTitle\" />\n"
				"\t\t\t\t<Col id=\"responseBasc\" />\n"
				"\t\t\t\t<Col id=\"responseDtal\" />\n"
				"\t\t\t\t<Col id=\"responseSystem\" />\n"
				"\t\t\t\t<Col id=\"userId\">");
				strcat (data_RequestXml_1, lr_eval_string("{login_ID}"));
				strcat (data_RequestXml_1, "</Col>\n"
				"\t\t\t\t<Col id=\"realUserId\">");
				strcat (data_RequestXml_1, lr_eval_string("{login_ID}"));
				strcat (data_RequestXml_1, "</Col>\n"
				"\t\t\t\t<Col id=\"filler\" />\n"
				"\t\t\t\t<Col id=\"langCode\" />\n"
				"\t\t\t\t<Col id=\"orgId\">SPT8050</Col>\n"
				"\t\t\t\t<Col id=\"srcId\">DNIUI1000LY</Col>\n"
				"\t\t\t\t<Col id=\"curHostId\" />\n"
				"\t\t\t\t<Col id=\"lgDateTime\">");
				strcat (data_RequestXml_1, lr_eval_string("{DATE}"));
				strcat (data_RequestXml_1, lr_eval_string("{TIME}"));
				strcat (data_RequestXml_1, "</Col>\n"
				"\t\t\t\t<Col id=\"tokenId\">");
				strcat (data_RequestXml_1, lr_eval_string("{bbb}"));
				strcat (data_RequestXml_1, "</Col>\n"
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
				strcat (data_RequestXml_1, lr_eval_string("{custIdfyNo}"));
				strcat (data_RequestXml_1, "</Col>\n"
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

	 lr_output_message("%s",data_RequestXml_1);
	 
	 web_set_max_html_param_len("800000");


 if (EncYN == 0) {		//평문 조회

	  lr_start_transaction("A_서비스번호로고객조회_retrieveCustInfoByCustIdfy");

    
	lr_save_string( data_RequestXml_1, "P_data_RequestXml_1");

	 web_reg_save_param("P_custId","LB=<Col id=\"custId\">","RB=</Col>",LAST);
	 web_custom_request("SUiDynamicGateway",
         "URL=http://10.219.7.80:5000/UiGateway ",
		  "Method=POST",
		  "Resource=0",
		  "RecContentType=text/html",
		  "Referer=file://C:/Users/Administrator/Documents/nexacro/outs/AdaptorTest/Base/USAM9908FM_sit.xfdl.js",
		  "Snapshot=t11.inf",
		  "Mode=HTML",
		  "Body={P_data_RequestXml_1}",
		  LAST);


   lr_end_transaction("A_서비스번호로고객조회_retrieveCustInfoByCustIdfy", LR_AUTO);
 
	  

} else if (EncYN == 1) {			//암호화

	memset(data_RequestXmlEnc_1, 0, sizeof(data_RequestXmlEnc_1));	 

	 if (FALSE == Encode(handle_id, data_RequestXml_1 , strlen(data_RequestXml_1), data_RequestXmlEnc_1))
	 {
		  lr_message("[ERROR] %s", "Failed to encrypted Request");
	 }
	 lr_output_message("Encoded : %s",data_RequestXmlEnc_1);


	 lr_save_string( data_RequestXmlEnc_1, "Data_encrypt_out_1");


	web_reg_save_param("len","LB=Content-Length: ","RB=\r\n",LAST);
	web_reg_save_param("Data_encrypt_res",  "LB=\r\n\r\n",  "RB=",  LAST);


	lr_start_transaction("A_서비스번호로고객조회_retrieveCustInfoByCustIdfy");

	  web_custom_request("SUiDynamicGateway",
         "URL=http://10.219.7.80:5000/SUiGateway ",       
		  "Method=POST",
		  "Resource=0",
		  "RecContentType=text/html",
		  "Referer=file://C:/Users/Administrator/Documents/nexacro/outs/AdaptorTest/Base/USAM9908FM_sit.xfdl.js",
		  "Snapshot=t11.inf",
		  "Mode=HTML",
		  "Body={Data_encrypt_out_1}",
		  LAST);


	  memset(data_ResponseXmlENC_1, 0, sizeof(data_ResponseXmlENC_1));	 
	  data_ResponseXmlENC_1 =  lr_eval_string("{Data_encrypt_res}");

	  if (FALSE == Decode(handle_id, data_ResponseXmlENC_1 ,strlen(data_ResponseXmlENC_1), data_ResponseXml_1, (int *) & len1))
	  {
		lr_message("[ERROR] %s", "Failed to decrypted Response");
	  }

      lr_message("data_ResponseXml_1=%s", data_ResponseXml_1);



	  //String 에서 특정 문자열 추출
	 //<Parameter id="ErrorMsg" type="string">Handshake Success</Parameter>
     position1 = (char *)strstr(data_ResponseXml_1, search_str1); 
     offset1 = (int)(position1 - data_ResponseXml_1 + 1); 
     //lr_output_message ("The string \"%s\" was found at position %d", search_str1, offset1); 
	 position2 = (char *)strstr(position1, search_str2); 
     offset2 = (int)(position2 - position1 + 1); 
     //lr_output_message ("The string \"%s\" was found at position %d", search_str2, offset2); 
	 memset(searched_str,0x00,sizeof(searched_str));
	 strncpy(searched_str,&position1[strlen(search_str1)],offset2-strlen(search_str1)-1);

	 lr_output_message ("추출 파라미터 \"%s\"", searched_str); 
     
	 lr_save_string(searched_str,"P_custId");


     lr_end_transaction("A_서비스번호로고객조회_retrieveCustInfoByCustIdfy", LR_AUTO);
    
}

	//free(data_RequestXml_1);
	//free(data_ResponseXml_1);



/////////////////////////////////////////////////////////////////////////////////////////////////
// 2. 고객요약정보 조화 CustDtlInfoRetvSO


	if ((data_RequestXml_1 = (char *)calloc(6000, sizeof(char))) == NULL) { 
		 lr_output_message ("Insufficient memory available"); 
		 return -1; 
	 } 
	
     if ((data_ResponseXml_1 = (char *)calloc(6000, sizeof(char))) == NULL) { 
        lr_output_message ("Insufficient memory available"); 
        return 0; 
    }

	
	if (FALSE == Init(handle_id,lr_eval_string("{temp_URL}")))
	{
		lr_message("[ERROR] %s", "Failed to initialize");
	}


	// 요청 XML 생성
     strcpy( data_RequestXml_1,"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
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
			"				<Col id=\"svcName\">CustDtlInfoRetvSO</Col>\n"
			"				<Col id=\"fnName\">service</Col>\n"
			"				<Col id=\"fnCd\" />\n"
			"\t\t\t\t<Col id=\"globalNo\">00000");
				strcat(data_RequestXml_1, lr_eval_string("{login_ID}"));
				strcat(data_RequestXml_1, lr_eval_string("{DATE}"));
				strcat(data_RequestXml_1, lr_eval_string("{TIME}"));
				strcat(data_RequestXml_1, lr_eval_string("{G_Random}"));
				strcat (data_RequestXml_1, "</Col>\n"
			"				<Col id=\"chnlType\">UI</Col>\n"
			"				<Col id=\"envrFlag\" />\n"
			"				<Col id=\"trFlag\">T</Col>\n"
			"\t\t\t\t<Col id=\"trDate\">");
				strcat (data_RequestXml_1, lr_eval_string("{DATE}"));
				strcat (data_RequestXml_1, "</Col>\n"
				"\t\t\t\t<Col id=\"trTime\">");
				strcat (data_RequestXml_1, lr_eval_string("{TIME}"));
				strcat (data_RequestXml_1, "</Col>\n"
			"\t\t\t\t<Col id=\"clntIp\">");
				strcat( data_RequestXml_1,lr_eval_string("{IP_Param}"));
				strcat (data_RequestXml_1, "</Col>\n"
			"				<Col id=\"responseType\" />\n"
			"				<Col id=\"responseCode\" />\n"
			"				<Col id=\"responseLogcd\" />\n"
			"				<Col id=\"responseTitle\" />\n"
			"				<Col id=\"responseBasc\" />\n"
			"				<Col id=\"responseDtal\" />\n"
			"				<Col id=\"responseSystem\" />\n"
			"\t\t\t\t<Col id=\"userId\">");
				strcat (data_RequestXml_1, lr_eval_string("{login_ID}"));
				strcat (data_RequestXml_1, "</Col>\n"
				"\t\t\t\t<Col id=\"realUserId\">");
				strcat (data_RequestXml_1, lr_eval_string("{login_ID}"));
				strcat (data_RequestXml_1, "</Col>\n"
			"				<Col id=\"filler\">E</Col>\n"
			"				<Col id=\"langCode\" />\n"
			"				<Col id=\"orgId\">SPT8050</Col>\n"
			"				<Col id=\"srcId\">DNIUI2001LY</Col>\n"
			"				<Col id=\"curHostId\" />\n"
			"\t\t\t\t<Col id=\"lgDateTime\">");
				strcat (data_RequestXml_1, lr_eval_string("{DATE}"));
				strcat (data_RequestXml_1, lr_eval_string("{TIME}"));
				strcat (data_RequestXml_1, "</Col>\n"
			"\t\t\t\t<Col id=\"tokenId\">");
				strcat (data_RequestXml_1, lr_eval_string("{bbb}"));
				strcat (data_RequestXml_1, "</Col>\n"
			"				<Col id=\"cmpnCd\">KTF</Col>\n"
			"				<Col id=\"lockType\" />\n"
			"				<Col id=\"lockId\" />\n"
			"				<Col id=\"lockTimeSt\" />\n"
			"				<Col id=\"businessKey\"></Col>\n"
			"				<Col id=\"arbitraryKey\">{}</Col>\n"
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
			"				<Col id=\"cbSvcName\">CustDtlInfoRetvSO</Col>\n"
			"				<Col id=\"cbFnName\">service</Col>\n"
			"			</Row>\n"
			"		</Rows>\n"
			"	</Dataset>\n"
			"	<Dataset id=\"CustIdDTO\">\n"
			"		<ColumnInfo>\n"
			"			<Column id=\"custId\" type=\"STRING\" size=\"256\"  />\n"
			"		</ColumnInfo>\n"
			"		<Rows>\n"
			"			<Row>\n"
			"				<Col id=\"custId\">8C5L70RJ9QJ</Col>\n"
			"			</Row>\n"
			"		</Rows>\n"
			"	</Dataset>\n"
			"</Root>\n",
		LAST);

	 lr_output_message("%s",data_RequestXml_1);
	 
	 web_set_max_html_param_len("800000");



	  


 if (EncYN == 0) {		//평문 조회

	  lr_start_transaction("B_고객요약정보 조화_CustDtlInfoRetvSO");

    
	lr_save_string( data_RequestXml_1, "P_data_RequestXml_1");

	 web_reg_save_param("P_custId","LB=<Col id=\"custId\">","RB=</Col>",LAST);
	 web_custom_request("UiDynamicGateway",
         "URL=http://10.219.7.80:5000/UiDynamicGateway",
		  "Method=POST",
		  "Resource=0",
		  "RecContentType=text/html",
		  "Referer=file://C:/Users/Administrator/Documents/nexacro/outs/AdaptorTest/Base/USAM9908FM_sit.xfdl.js",
		  "Snapshot=t11.inf",
		  "Mode=HTML",
		  "Body={P_data_RequestXml_1}",
		  LAST);


   lr_end_transaction("B_고객요약정보 조화_CustDtlInfoRetvSO", LR_AUTO);
 
	  

} else if (EncYN == 1) {			//암호화

	memset(data_RequestXmlEnc_1, 0, sizeof(data_RequestXmlEnc_1));	 

	 if (FALSE == Encode(handle_id, data_RequestXml_1 , strlen(data_RequestXml_1), data_RequestXmlEnc_1))
	 {
		  lr_message("[ERROR] %s", "Failed to encrypted Request");
	 }
	 lr_output_message("Encoded : %s",data_RequestXmlEnc_1);


	 lr_save_string( data_RequestXmlEnc_1, "Data_encrypt_out_1");


	web_reg_save_param("len","LB=Content-Length: ","RB=\r\n",LAST);
	web_reg_save_param("Data_encrypt_res",  "LB=\r\n\r\n",  "RB=",  LAST);


	lr_start_transaction("B_고객요약정보 조화_CustDtlInfoRetvSO");

	  web_custom_request("SUiDynamicGateway",
         "URL=http://10.219.7.80:5000/SUiDynamicGateway",       
		  "Method=POST",
		  "Resource=0",
		  "RecContentType=text/html",
		  "Referer=file://C:/Users/Administrator/Documents/nexacro/outs/AdaptorTest/Base/USAM9908FM_sit.xfdl.js",
		  "Snapshot=t11.inf",
		  "Mode=HTML",
		  "Body={Data_encrypt_out_1}",
		  LAST);


	  memset(data_ResponseXmlENC_1, 0, sizeof(data_ResponseXmlENC_1));	
	  data_ResponseXmlENC_1 =  lr_eval_string("{Data_encrypt_res}");

	  if (FALSE == Decode(handle_id, data_ResponseXmlENC_1 ,strlen(data_ResponseXmlENC_1), data_ResponseXml_1, (int *) & len1))
	  {
		lr_message("[ERROR] %s", "Failed to decrypted Response");
	  }

      lr_message("data_ResponseXml_1=%s", data_ResponseXml_1);

     lr_end_transaction("B_고객요약정보 조화_CustDtlInfoRetvSO", LR_AUTO);
    
}


////////////////////////////////////////////////////////////////////////////////////////////////////////
// 3. 고객보유상품 조회_CustSvcFirstLvlInfoRetvSO
// 

	if ((data_RequestXml_1 = (char *)calloc(6000, sizeof(char))) == NULL) { 
		 lr_output_message ("Insufficient memory available"); 
		 return -1; 
	 } 

	 if ((data_ResponseXml_1 = (char *)calloc(6000, sizeof(char))) == NULL) { 
		lr_output_message ("Insufficient memory available"); 
		return 0; 
	}


	if (FALSE == Init(handle_id,lr_eval_string("{temp_URL}")))
	{
		lr_message("[ERROR] %s", "Failed to initialize");
	}


	// 요청 XML 생성
	 strcpy( data_RequestXml_1,		"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
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
		"				<Col id=\"fnCd\" />\n"
		"\t\t\t\t<Col id=\"globalNo\">00000");
				strcat(data_RequestXml_1, lr_eval_string("{login_ID}"));
				strcat(data_RequestXml_1, lr_eval_string("{DATE}"));
				strcat(data_RequestXml_1, lr_eval_string("{TIME}"));
				strcat(data_RequestXml_1, lr_eval_string("{G_Random}"));
				strcat (data_RequestXml_1, "</Col>\n"
			"				<Col id=\"chnlType\">UI</Col>\n"
			"				<Col id=\"envrFlag\" />\n"
			"				<Col id=\"trFlag\">T</Col>\n"
			"\t\t\t\t<Col id=\"trDate\">");
				strcat (data_RequestXml_1, lr_eval_string("{DATE}"));
				strcat (data_RequestXml_1, "</Col>\n"
				"\t\t\t\t<Col id=\"trTime\">");
				strcat (data_RequestXml_1, lr_eval_string("{TIME}"));
				strcat (data_RequestXml_1, "</Col>\n"
			"\t\t\t\t<Col id=\"clntIp\">");
				strcat( data_RequestXml_1,lr_eval_string("{IP_Param}"));
				strcat (data_RequestXml_1, "</Col>\n"
			"				<Col id=\"responseType\" />\n"
			"				<Col id=\"responseCode\" />\n"
			"				<Col id=\"responseLogcd\" />\n"
			"				<Col id=\"responseTitle\" />\n"
			"				<Col id=\"responseBasc\" />\n"
			"				<Col id=\"responseDtal\" />\n"
			"				<Col id=\"responseSystem\" />\n"
			"\t\t\t\t<Col id=\"userId\">");
				strcat (data_RequestXml_1, lr_eval_string("{login_ID}"));
				strcat (data_RequestXml_1, "</Col>\n"
				"\t\t\t\t<Col id=\"realUserId\">");
				strcat (data_RequestXml_1, lr_eval_string("{login_ID}"));
				strcat (data_RequestXml_1, "</Col>\n"
			"				<Col id=\"filler\">E</Col>\n"
			"				<Col id=\"langCode\" />\n"
			"				<Col id=\"orgId\">SPT8050</Col>\n"
			"				<Col id=\"srcId\">DNIUI3000FM</Col>\n"
			"				<Col id=\"curHostId\" />\n"
			"\t\t\t\t<Col id=\"lgDateTime\">");
				strcat (data_RequestXml_1, lr_eval_string("{DATE}"));
				strcat (data_RequestXml_1, lr_eval_string("{TIME}"));
				strcat (data_RequestXml_1, "</Col>\n"
			"<Col id=\"tokenId\">");
			strcat (data_RequestXml_1, lr_eval_string("{bbb}"));
			strcat (data_RequestXml_1,"</Col>\n"
			"				<Col id=\"cmpnCd\">KTF</Col>\n"
			"				<Col id=\"lockType\" />\n"
			"				<Col id=\"lockId\" />\n"
			"				<Col id=\"lockTimeSt\" />\n"
			"\t\t\t\t<Col id=\"businessKey\">0000");
				  strcat(data_RequestXml_1, lr_eval_string("{login_ID}"));
				  strcat(data_RequestXml_1, "00A_MASTER");
				  strcat(data_RequestXml_1, lr_eval_string("{DATE}"));
				  strcat(data_RequestXml_1, lr_eval_string("{TIME}"));
			strcat(data_RequestXml_1, "531</Col>\n");
			strcat(data_RequestXml_1, "				<Col id=\"arbitraryKey\">{}</Col>\n"
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
		"				<Col id=\"custId\">8C5L70RJ9QJ</Col>\n"
		"				<Col id=\"svcContId\">480262016</Col>\n"
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

	 lr_output_message("%s",data_RequestXml_1);

	 web_set_max_html_param_len("800000");




 if (EncYN == 0) {		//평문 조회

	  lr_start_transaction("C_고객보유상품 조회_CustSvcFirstLvlInfoRetvSO");

	lr_save_string( data_RequestXml_1, "P_data_RequestXml_1");

	 web_reg_save_param("P_custId","LB=<Col id=\"custId\">","RB=</Col>",LAST);
	 web_custom_request("UiDynamicGateway",
		 "URL=http://10.219.7.80:5000/UiDynamicGateway ",
		  "Method=POST",
		  "Resource=0",
		  "RecContentType=text/html",
		  "Referer=file://C:/Users/Administrator/Documents/nexacro/outs/AdaptorTest/Base/USAM9908FM_sit.xfdl.js",
		  "Snapshot=t11.inf",
		  "Mode=HTML",
		  "Body={P_data_RequestXml_1}",
		  LAST);


   lr_end_transaction("C_고객보유상품 조회_CustSvcFirstLvlInfoRetvSO", LR_AUTO);



} else if (EncYN == 1) {			//암호화

	memset(data_RequestXmlEnc_1, 0, sizeof(data_RequestXmlEnc_1));	 

	 if (FALSE == Encode(handle_id, data_RequestXml_1 , strlen(data_RequestXml_1), data_RequestXmlEnc_1))
	 {
		  lr_message("[ERROR] %s", "Failed to encrypted Request");
	 }
	 lr_output_message("Encoded : %s",data_RequestXmlEnc_1);


	 lr_save_string( data_RequestXmlEnc_1, "Data_encrypt_out_1");


	web_reg_save_param("len","LB=Content-Length: ","RB=\r\n",LAST);
	web_reg_save_param("Data_encrypt_res",  "LB=\r\n\r\n",  "RB=",  LAST);


	lr_start_transaction("C_고객보유상품 조회_CustSvcFirstLvlInfoRetvSO");

	  web_custom_request("SUiDynamicGateway",
		 "URL=http://10.219.7.80:5000/SUiDynamicGateway",       
		  "Method=POST",
		  "Resource=0",
		  "RecContentType=text/html",
		  "Referer=file://C:/Users/Administrator/Documents/nexacro/outs/AdaptorTest/Base/USAM9908FM_sit.xfdl.js",
		  "Snapshot=t11.inf",
		  "Mode=HTML",
		  "Body={Data_encrypt_out_1}",
		  LAST);


	  memset(data_ResponseXmlENC_1, 0, sizeof(data_ResponseXmlENC_1));	
	  data_ResponseXmlENC_1 =  lr_eval_string("{Data_encrypt_res}");

	  if (FALSE == Decode(handle_id, data_ResponseXmlENC_1 ,strlen(data_ResponseXmlENC_1), data_ResponseXml_1, (int *) & len1))
	  {
		lr_message("[ERROR] %s", "Failed to decrypted Response");
	  }

	  lr_message("data_ResponseXml_1=%s", data_ResponseXml_1);



	 lr_end_transaction("C_고객보유상품 조회_CustSvcFirstLvlInfoRetvSO", LR_AUTO);

}





//////////////////////////////////////////////////////////////////////////////////
// 4. 계약정보 조회_CustSvcContInfoRetvSO


	if ((data_RequestXml_1 = (char *)calloc(6000, sizeof(char))) == NULL) { 
		 lr_output_message ("Insufficient memory available"); 
		 return -1; 
	 } 

	 if ((data_ResponseXml_1 = (char *)calloc(6000, sizeof(char))) == NULL) { 
		lr_output_message ("Insufficient memory available"); 
		return 0; 
	}


	if (FALSE == Init(handle_id,lr_eval_string("{temp_URL}")))
	{
		lr_message("[ERROR] %s", "Failed to initialize");
	}


	// 요청 XML 생성
	 strcpy( data_RequestXml_1,"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
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
			"				<Col id=\"fnCd\" />\n"
			"\t\t\t\t<Col id=\"globalNo\">00000");
				strcat(data_RequestXml_1, lr_eval_string("{login_ID}"));
				strcat(data_RequestXml_1, lr_eval_string("{DATE}"));
				strcat(data_RequestXml_1, lr_eval_string("{TIME}"));
				strcat(data_RequestXml_1, lr_eval_string("{G_Random}"));
				strcat (data_RequestXml_1, "</Col>\n"
			"				<Col id=\"chnlType\">UI</Col>\n"
			"				<Col id=\"envrFlag\" />\n"
			"				<Col id=\"trFlag\">T</Col>\n"
			"\t\t\t\t<Col id=\"trDate\">");
				strcat (data_RequestXml_1, lr_eval_string("{DATE}"));
				strcat (data_RequestXml_1, "</Col>\n"
				"\t\t\t\t<Col id=\"trTime\">");
				strcat (data_RequestXml_1, lr_eval_string("{TIME}"));
				strcat (data_RequestXml_1, "</Col>\n"
			"\t\t\t\t<Col id=\"clntIp\">");
				strcat( data_RequestXml_1,lr_eval_string("{IP_Param}"));
				strcat (data_RequestXml_1, "</Col>\n"
			"				<Col id=\"responseType\" />\n"
			"				<Col id=\"responseCode\" />\n"
			"				<Col id=\"responseLogcd\" />\n"
			"				<Col id=\"responseTitle\" />\n"
			"				<Col id=\"responseBasc\" />\n"
			"				<Col id=\"responseDtal\" />\n"
			"				<Col id=\"responseSystem\" />\n"
			"\t\t\t\t<Col id=\"userId\">");
				strcat (data_RequestXml_1, lr_eval_string("{login_ID}"));
				strcat (data_RequestXml_1, "</Col>\n"
				"\t\t\t\t<Col id=\"realUserId\">");
				strcat (data_RequestXml_1, lr_eval_string("{login_ID}"));
				strcat (data_RequestXml_1, "</Col>\n"
			"				<Col id=\"filler\">E</Col>\n"
			"				<Col id=\"langCode\" />\n"
			"				<Col id=\"orgId\">SPT8050</Col>\n"
			"				<Col id=\"srcId\">DNIUI3100LY</Col>\n"
			"				<Col id=\"curHostId\" />\n"
			"\t\t\t\t<Col id=\"lgDateTime\">");
				strcat (data_RequestXml_1, lr_eval_string("{DATE}"));
				strcat (data_RequestXml_1, lr_eval_string("{TIME}"));
				strcat (data_RequestXml_1, "</Col>\n"
			"\t\t\t\t<Col id=\"tokenId\">");
				strcat (data_RequestXml_1, lr_eval_string("{bbb}"));
				strcat (data_RequestXml_1, "</Col>\n"
			"				<Col id=\"cmpnCd\">KTF</Col>\n"
			"				<Col id=\"lockType\" />\n"
			"				<Col id=\"lockId\" />\n"
			"				<Col id=\"lockTimeSt\" />\n"
			"\t\t\t\t<Col id=\"businessKey\">0000");
				  strcat(data_RequestXml_1, lr_eval_string("{login_ID}"));
				  strcat(data_RequestXml_1, "00A_MASTER");
				  strcat(data_RequestXml_1, lr_eval_string("{DATE}"));
				  strcat(data_RequestXml_1, lr_eval_string("{TIME}"));
			strcat(data_RequestXml_1, "531</Col>\n");
			strcat(data_RequestXml_1, "				<Col id=\"arbitraryKey\">{}</Col>\n"
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
			"				<Col id=\"svcContId\">480262016</Col>\n"
			"				<Col id=\"custId\">8C5L70RJ9QJ</Col>\n"
			"				<Col id=\"intmModelId\" />\n"
			"				<Col id=\"prodNm\">Mobile</Col>\n"
			"			</Row>\n"
			"		</Rows>\n"
			"	</Dataset>\n"
			"</Root>\n",
		LAST);

	 lr_output_message("%s",data_RequestXml_1);

	 web_set_max_html_param_len("800000");






 if (EncYN == 0) {		//평문 조회

	  lr_start_transaction("D_계약정보 조회_CustSvcContInfoRetvSO");


	lr_save_string( data_RequestXml_1, "P_data_RequestXml_1");

	 web_reg_save_param("P_custId","LB=<Col id=\"custId\">","RB=</Col>",LAST);
	 web_custom_request("UiDynamicGateway",
		 "URL=http://10.219.7.80:5000/UiDynamicGateway ",
		  "Method=POST",
		  "Resource=0",
		  "RecContentType=text/html",
		  "Referer=file://C:/Users/Administrator/Documents/nexacro/outs/AdaptorTest/Base/USAM9908FM_sit.xfdl.js",
		  "Snapshot=t11.inf",
		  "Mode=HTML",
		  "Body={P_data_RequestXml_1}",
		  LAST);


   lr_end_transaction("D_계약정보 조회_CustSvcContInfoRetvSO", LR_AUTO);



} else if (EncYN == 1) {			//암호화

	memset(data_RequestXmlEnc_1, 0, sizeof(data_RequestXmlEnc_1));	 

	 if (FALSE == Encode(handle_id, data_RequestXml_1 , strlen(data_RequestXml_1), data_RequestXmlEnc_1))
	 {
		  lr_message("[ERROR] %s", "Failed to encrypted Request");
	 }
	 lr_output_message("Encoded : %s",data_RequestXmlEnc_1);


	 lr_save_string( data_RequestXmlEnc_1, "Data_encrypt_out_1");


	web_reg_save_param("len","LB=Content-Length: ","RB=\r\n",LAST);
	web_reg_save_param("Data_encrypt_res",  "LB=\r\n\r\n",  "RB=",  LAST);


	lr_start_transaction("D_계약정보 조회_CustSvcContInfoRetvSO");

	  web_custom_request("SUiDynamicGateway",
		 "URL=http://10.219.7.80:5000/SUiDynamicGateway",       
		  "Method=POST",
		  "Resource=0",
		  "RecContentType=text/html",
		  "Referer=file://C:/Users/Administrator/Documents/nexacro/outs/AdaptorTest/Base/USAM9908FM_sit.xfdl.js",
		  "Snapshot=t11.inf",
		  "Mode=HTML",
		  "Body={Data_encrypt_out_1}",
		  LAST);


	  memset(data_ResponseXmlENC_1, 0, sizeof(data_ResponseXmlENC_1));	
	  data_ResponseXmlENC_1 =  lr_eval_string("{Data_encrypt_res}");

	  if (FALSE == Decode(handle_id, data_ResponseXmlENC_1 ,strlen(data_ResponseXmlENC_1), data_ResponseXml_1, (int *) & len1))
	  {
		lr_message("[ERROR] %s", "Failed to decrypted Response");
	  }

	  lr_message("data_ResponseXml_1=%s", data_ResponseXml_1);



	 lr_end_transaction("D_계약정보 조회_CustSvcContInfoRetvSO", LR_AUTO);

}

	//free(data_RequestXml_1);
	//free(data_ResponseXml_1);







	////////////////////////////////////////////////////////////////////////////////////////
	// 5. 납부정보 조회_CustSvcPayInfoRetvSO


	if ((data_RequestXml_1 = (char *)calloc(6000, sizeof(char))) == NULL) { 
		 lr_output_message ("Insufficient memory available"); 
		 return -1; 
	 } 

	 if ((data_ResponseXml_1 = (char *)calloc(6000, sizeof(char))) == NULL) { 
		lr_output_message ("Insufficient memory available"); 
		return 0; 
	}


	if (FALSE == Init(handle_id,lr_eval_string("{temp_URL}")))
	{
		lr_message("[ERROR] %s", "Failed to initialize");
	}


	// 요청 XML 생성
	 strcpy( data_RequestXml_1,			"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
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
		"				<Col id=\"svcName\">CustSvcPayInfoRetvSO</Col>\n"
		"				<Col id=\"fnName\">service</Col>\n"
		"				<Col id=\"fnCd\" />\n"
		"\t\t\t\t<Col id=\"globalNo\">00000");
				strcat(data_RequestXml_1, lr_eval_string("{login_ID}"));
				strcat(data_RequestXml_1, lr_eval_string("{DATE}"));
				strcat(data_RequestXml_1, lr_eval_string("{TIME}"));
				strcat(data_RequestXml_1, lr_eval_string("{G_Random}"));
				strcat (data_RequestXml_1, "</Col>\n"
			"				<Col id=\"chnlType\">UI</Col>\n"
			"				<Col id=\"envrFlag\" />\n"
			"				<Col id=\"trFlag\">T</Col>\n"
			"\t\t\t\t<Col id=\"trDate\">");
				strcat (data_RequestXml_1, lr_eval_string("{DATE}"));
				strcat (data_RequestXml_1, "</Col>\n"
				"\t\t\t\t<Col id=\"trTime\">");
				strcat (data_RequestXml_1, lr_eval_string("{TIME}"));
				strcat (data_RequestXml_1, "</Col>\n"
			"\t\t\t\t<Col id=\"clntIp\">");
				strcat( data_RequestXml_1,lr_eval_string("{IP_Param}"));
				strcat (data_RequestXml_1, "</Col>\n"
			"				<Col id=\"responseType\" />\n"
			"				<Col id=\"responseCode\" />\n"
			"				<Col id=\"responseLogcd\" />\n"
			"				<Col id=\"responseTitle\" />\n"
			"				<Col id=\"responseBasc\" />\n"
			"				<Col id=\"responseDtal\" />\n"
			"				<Col id=\"responseSystem\" />\n"
			"\t\t\t\t<Col id=\"userId\">");
				strcat (data_RequestXml_1, lr_eval_string("{login_ID}"));
				strcat (data_RequestXml_1, "</Col>\n"
				"\t\t\t\t<Col id=\"realUserId\">");
				strcat (data_RequestXml_1, lr_eval_string("{login_ID}"));
				strcat (data_RequestXml_1, "</Col>\n"
			"				<Col id=\"filler\">E</Col>\n"
			"				<Col id=\"langCode\" />\n"
			"				<Col id=\"orgId\">SPT8050</Col>\n"
			"				<Col id=\"srcId\">DNIUI3100LY</Col>\n"
			"				<Col id=\"curHostId\" />\n"
			"\t\t\t\t<Col id=\"lgDateTime\">");
				strcat (data_RequestXml_1, lr_eval_string("{DATE}"));
				strcat (data_RequestXml_1, lr_eval_string("{TIME}"));
				strcat (data_RequestXml_1, "</Col>\n"
			"<Col id=\"tokenId\">");
			strcat (data_RequestXml_1, lr_eval_string("{bbb}"));
			strcat (data_RequestXml_1,"</Col>\n"
			"				<Col id=\"cmpnCd\">KTF</Col>\n"
			"				<Col id=\"lockType\" />\n"
			"				<Col id=\"lockId\" />\n"
			"				<Col id=\"lockTimeSt\" />\n"
			"\t\t\t\t<Col id=\"businessKey\">0000");
				  strcat(data_RequestXml_1, lr_eval_string("{login_ID}"));
				  strcat(data_RequestXml_1, "00A_MASTER");
				  strcat(data_RequestXml_1, lr_eval_string("{DATE}"));
				  strcat(data_RequestXml_1, lr_eval_string("{TIME}"));
			strcat(data_RequestXml_1, "531</Col>\n");
			strcat(data_RequestXml_1, "				<Col id=\"arbitraryKey\">{}</Col>\n"
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
		"				<Col id=\"cbSvcName\">CustSvcPayInfoRetvSO</Col>\n"
		"				<Col id=\"cbFnName\">service</Col>\n"
		"			</Row>\n"
		"		</Rows>\n"
		"	</Dataset>\n"
		"	<Dataset id=\"BillAccIdDTO\">\n"
		"		<ColumnInfo>\n"
		"			<Column id=\"billAccId\" type=\"STRING\" size=\"256\"  />\n"
		"			<Column id=\"svcContId\" type=\"STRING\" size=\"256\"  />\n"
		"		</ColumnInfo>\n"
		"		<Rows>\n"
		"			<Row>\n"
		"				<Col id=\"billAccId\">773579776</Col>\n"
		"				<Col id=\"svcContId\">480262016</Col>\n"
		"			</Row>\n"
		"		</Rows>\n"
		"	</Dataset>\n"
		"</Root>\n",
		LAST);

	 lr_output_message("%s",data_RequestXml_1);

	 web_set_max_html_param_len("800000");






 if (EncYN == 0) {		//평문 조회

	  lr_start_transaction("E_납부정보 조회_CustSvcPayInfoRetvSO");


	lr_save_string( data_RequestXml_1, "P_data_RequestXml_1");

	 web_reg_save_param("P_custId","LB=<Col id=\"custId\">","RB=</Col>",LAST);
	 web_custom_request("UiDynamicGateway",
		 "URL=http://10.219.7.80:5000/UiDynamicGateway ",
		  "Method=POST",
		  "Resource=0",
		  "RecContentType=text/html",
		  "Referer=file://C:/Users/Administrator/Documents/nexacro/outs/AdaptorTest/Base/USAM9908FM_sit.xfdl.js",
		  "Snapshot=t11.inf",
		  "Mode=HTML",
		  "Body={P_data_RequestXml_1}",
		  LAST);


   lr_end_transaction("E_납부정보 조회_CustSvcPayInfoRetvSO", LR_AUTO);



} else if (EncYN == 1) {			//암호화

	memset(data_RequestXmlEnc_1, 0, sizeof(data_RequestXmlEnc_1));	 

	 if (FALSE == Encode(handle_id, data_RequestXml_1 , strlen(data_RequestXml_1), data_RequestXmlEnc_1))
	 {
		  lr_message("[ERROR] %s", "Failed to encrypted Request");
	 }
	 lr_output_message("Encoded : %s",data_RequestXmlEnc_1);


	 lr_save_string( data_RequestXmlEnc_1, "Data_encrypt_out_1");


	web_reg_save_param("len","LB=Content-Length: ","RB=\r\n",LAST);
	web_reg_save_param("Data_encrypt_res",  "LB=\r\n\r\n",  "RB=",  LAST);


	lr_start_transaction("E_납부정보 조회_CustSvcPayInfoRetvSO");

	  web_custom_request("SUiDynamicGateway",
		 "URL=http://10.219.7.80:5000/SUiDynamicGateway",       
		  "Method=POST",
		  "Resource=0",
		  "RecContentType=text/html",
		  "Referer=file://C:/Users/Administrator/Documents/nexacro/outs/AdaptorTest/Base/USAM9908FM_sit.xfdl.js",
		  "Snapshot=t11.inf",
		  "Mode=HTML",
		  "Body={Data_encrypt_out_1}",
		  LAST);


	  memset(data_ResponseXmlENC_1, 0, sizeof(data_ResponseXmlENC_1));	
	  data_ResponseXmlENC_1 =  lr_eval_string("{Data_encrypt_res}");

	  if (FALSE == Decode(handle_id, data_ResponseXmlENC_1 ,strlen(data_ResponseXmlENC_1), data_ResponseXml_1, (int *) & len1))
	  {
		lr_message("[ERROR] %s", "Failed to decrypted Response");
	  }

	  lr_message("data_ResponseXml_1=%s", data_ResponseXml_1);



	 


	 lr_end_transaction("E_납부정보 조회_CustSvcPayInfoRetvSO", LR_AUTO);

}

	free(data_RequestXml_1);
	free(data_ResponseXml_1);

	   return 0;
}
