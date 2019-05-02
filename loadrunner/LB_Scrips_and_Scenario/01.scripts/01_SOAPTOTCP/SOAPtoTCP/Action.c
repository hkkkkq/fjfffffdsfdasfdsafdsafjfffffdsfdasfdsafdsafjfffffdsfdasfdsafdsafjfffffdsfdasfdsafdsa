Action()
{

	lr_start_transaction("01_SOAPTOTCP");

    soap_request("StepName=SOAP Request",										
		"URL=http://{IP}:9030/NCDM/NIFE_NCDM_EKAI_O_0002_00",										
		"SOAPEnvelope="
		"<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:biz=\"http://bizhub.kt.com\">"
			"<soapenv:Header>"
				"<biz:commonHeader>"
					"<biz:appName>?</biz:appName>"
					"<biz:svcName>PTPK</biz:svcName>"
					"<biz:fnName>5090</biz:fnName>"
					"<biz:fnCd>?</biz:fnCd>"
					"<biz:globalNo>111111111111</biz:globalNo>"
					"<biz:chnlType>?</biz:chnlType>"
					"<biz:envrFlag>?</biz:envrFlag>"
					"<biz:trFlag>?</biz:trFlag>"
					"<biz:trDate>?</biz:trDate>"
					"<biz:trTime>?</biz:trTime>"
					"<biz:clntIp>?</biz:clntIp>"
					"<biz:responseType>?</biz:responseType>"
					"<biz:responseCode>?</biz:responseCode>"
					"<biz:responseLogcd>?</biz:responseLogcd>"
					"<biz:responseTitle>?</biz:responseTitle>"
					"<biz:responseBasc>?</biz:responseBasc>"
					"<biz:responseDtal>?</biz:responseDtal>"
					"<biz:responseSystem>?</biz:responseSystem>"
					"<biz:userId>?</biz:userId>"
					"<biz:realUserId>?</biz:realUserId>"
					"<biz:filler>?</biz:filler>"
					"<biz:langCode>?</biz:langCode>"
					"<biz:orgId>?</biz:orgId>"
					"<biz:srcId>?</biz:srcId>"
					"<biz:curHostId>?</biz:curHostId>"
					"<biz:lgDateTime>?</biz:lgDateTime>"
					"<biz:tokenId>?</biz:tokenId>"
					"<biz:cmpnCd>?</biz:cmpnCd>"
					"<biz:lockType>?</biz:lockType>"
					"<biz:lockId>?</biz:lockId>"
					"<biz:lockTimeSt>?</biz:lockTimeSt>"
					"<biz:businessKey>?</biz:businessKey>"
					"<biz:arbitraryKey>?</biz:arbitraryKey>"
					"<biz:resendFlag>?</biz:resendFlag>"
				"</biz:commonHeader>"
			"</soapenv:Header>"
			"<soapenv:Body>"
				"<biz:MSGIReqBody>"
					"<biz:bizHeader>"
						"<biz:orderId>?</biz:orderId>"
						"<biz:cbSvcName>?</biz:cbSvcName>"
						"<biz:cbFnName>?</biz:cbFnName>"
					"</biz:bizHeader>"
					"<biz:MSGIReqSubBody>"
						"<biz:trtDiv>I</biz:trtDiv>"
						"<biz:datType>R</biz:datType>"
						"<biz:bizrIdfyCd>B060</biz:bizrIdfyCd>"
						"<biz:resno>7101022780727???</biz:resno>"
						"<biz:divCd>1</biz:divCd>"
						"<biz:custNm>?</biz:custNm>"
						"<biz:rqtDiv>1</biz:rqtDiv>"
						"<biz:connrId>10035054??</biz:connrId>"
						"<biz:filr>?</biz:filr>"
						"<biz:fnsChr>\n</biz:fnsChr>"
					"</biz:MSGIReqSubBody>"
				"</biz:MSGIReqBody>"
			"</soapenv:Body>"
		"</soapenv:Envelope>",										
		"SOAPAction=http://bizhub.kt.com/l_NIFE_NCDM_EKAI_O_0002_00",										
		"ResponseParam=response",										
		"Snapshot=t1436860766.inf",									    
		LAST);

	//lr_output_message(lr_eval_string("{response}"));

	if (strncmp(lr_eval_string("{response}"),lr_eval_string("{Response1}"),287) ==0) {

		lr_end_transaction("01_SOAPTOTCP", LR_PASS);

	} else {

    lr_error_message(lr_eval_string("{response}"));


		lr_end_transaction("01_SOAPTOTCP", LR_FAIL);

	}





	/*
    lr_xml_get_values("XML={response}",
          "ValueParam=OutputParam",
          "Query=/Envelope/Body/MSGIResBody/MSGIResSubBody/msgId",
          LAST );
*/
    //lr_output_message(lr_eval_string("Query result = {OutputParam}"));
/*
	if (strcmp(lr_eval_string("{OutputParam}"),"0001") ==0) {

		lr_end_transaction("01_SOAPTOTCP", LR_PASS);

	} else {
	
		lr_end_transaction("01_SOAPTOTCP", LR_FAIL);

	}
 */   
	//lr_end_transaction("01_SOAPTOTCP", LR_AUTO);

}
