<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<meta name="viewport" content="width=device-width, user-scalable=no">
<%@ include file="/WEB-INF/views/common/taglib.jsp"%>
<script>

$(document).ready(function() {
	document.ifForApp.location = "akos://web/documents";
});

var nextBtnId = "btnNext";
var curStep = "a";

	var inVo = {
		"trtBaseInfoDTO" : {
			"appleUserId" : "test11@apple.com",
			"userId" : "115160351",
			"userNm" : "이인희",
			"orgId" : "AA18310",
			"orgNm" : "(주)성연텔레콤",
			"cpntId" : "NDA1234567",
			"clntIp" : "127.0.0.1",
			"srcId" : null,
			"stepId" : null,
			"cmpnCd" : "KT",
			"itgOderTypeCd" : "NAC",
			"apiKey" : "akos15108150727704i5wz3",
			"aminorYn" : null,
			"userTelNo" : "01025007797",
			"reProcessYn" : null
		},
		"custInfoDTO" : {
			"custId" : null,
			"custNm" : "강희승",
			"custNm__ENC__" : "M+SDcCMKcfk7WDBIcL//dg==",
			"custTypeCd" : "1",
			"custPtclTypeCd" : null,
			"custIdfyNo" : "9210121930315",
			"custIdfyNo__ENC__" : "YZos48DL/gt3MRmjX1cClw==",
			"custIdfyNoTypeCd" : "1",
			"bthdayNepoDivCd" : null,
			"zipCd" : null,
			"adrBasSbst" : null,
			"adrDtlSbst" : null,
			"legalCustId" : null,
			"legalRelNm" : null,
			"legalCustNm" : null,
			"legalCustTypeCd" : null,
			"legalCustPtclTypeCd" : null,
			"legalCustIdfyNo" : null,
			"legalCustIdfyNoTypeCd" : null,
			"legalZipCd" : null,
			"legalAdrBasSbst" : null,
			"legalAdrDtlSbst" : null,
			"aminorYn" : "",
			"bthday" : "1992.02.26",
			"svcContAgreeInfoListDTO" : [ {
				"contCustInfoAgreeCd" : "01",
				"agreeYn" : "Y",
				"agreeSbst" : null
			} ]
		},
		"rnmAthnInfoDTO" : {
			"dgt3CntryCd" : null,
			"retvCdVal" : "REGID",
			"issDateVal" : "20110123",
			"rnmConfrTelNo" : "01022223333",
			"legalDgt3CntryCd" : null,
			"legalRetvCdVal" : null,
			"legalIssDateVal" : null,
			"legalRnmConfrTelNo" : null,
			"athnSkipCd" : null
		},
		"billAccInfoDTO" : {
			"billAccId" : null,
			"custNm" : "강희승",
			"billAccDivCd" : null,
			"acrndDspTypeCd" : null,
			"email" : "sang1234@naver.com",
			"emailDomain" : null,
			"mblSvcNo" : null,
			"billZipNo" : null,
			"billAdrBasSbst" : "서울시 강남구 00아파트",
			"billAdrDtlSbst" : null,
			"payMethCd" : "C",
			"payDate" : "매월 21일",
			"payMethIdfyCd" : "002",
			"payMethIdfyNo" : "4444-30**-****-***4",
			"payMethIdfyNo__ENC__" : "i7Jf8DCIJM/6VghXMeuHQw==",
			"cardEfctMonth" : null,
			"cardEfctYear" : null,
			"payCustNm" : null,
			"parCustBthDay" : null
		},
		"newSbscTrtInfoDTO" : {
			"svcNo" : "01012345678",
			"prodIndcChage" : 87000,
			"mlgUseVal" : 5000
		},
		"newSbscIntmUsimInfoDTO" : {
			"intmModelNm" : "a123456789",
			"saleEngtOptnCdNm" : "",
			"intmSaleAmt" : 10000,
			"usimSaleAmt" : 7000
		},
		"newSbscSpnsrInfoDTO" : {
			"prodNm" : "LTE데이타 87.8",
			"firstDcSuprtTotAmt" : "7000",
			"saleEngtOptnCd" : "",
			"dcSuprtAmtStor" : "",
			"engtPerdMonsNum" : 12
		},
		"intmInslInfoOutDTO" : {
			"mbyPayAmt" : 7000,
			"instaAmt" : 840000,
			"totInslMonsNum" : 24
		}
	};
	
	// 서식지 매핑 데이터 초기화
	function initDocuemtData() {
		return doc_data = {
			"signature" : { // 서명 및 사인 URL
				"custSignature" : "", // 고객 자필 서명 이미지 URL
				"custSign" : "", // 고객 자필 Sign 이미지 URL
				"userSignature" : "", // 판매자 자필 서명 이미지 URL
				"userSign" : "" // 판매자 자필 Sign 이미지 URL
			},
			"orderPattern" : { // 오더 유형
				"order" : "", // "MNP", "HCN" // 오더유형 : 신규, 번호이동, 기변 <- trtBaseInfoDO.itgOderTypeCd
				"joinAge" : "" // 성인 구분 (1:성인, 2:미성년) <- 
			},
			"baseInfo" : { // 상단 정보
				"orgNm" : "", // 대리점명 <- trtBaseInfoDO.orgNm
				"orgId" : "", // 대리점코드 <- trtBaseInfoDO.orgId
				"counselor" : "", // 상담사 <- trtBaseInfoDO.userNm
				"counselorNo" : "", // 상담사연락처
				"orderType":"", // (신규 => 1:신규가입, 2:번호이동, 3:명의변경, 4:선불가입, 기변=>1:보상기변, 2:핸드폰기변, 3:자동납부, 4:olleh wifi call, 5:기타변경)
				"custType" : "", //1:개인, 2:개인사업자, 3:법인, 4:기타
				"secGrade" : "", //1:온라인개통허용안함, 2:대리인개통허용안함, 3:2회선초과개통허용안함, 4:3회선초과개통허용안함
				"hcnType" : "", // 1:중고폰반납, 2:승계기변신청, 4: 요금할인만승계
				"mdlNm" : "", // 모델명 <- icgSbscIntmDTO.nowIntmModelNm
				"mdlserialNo" : "" // 모델일련 번호 <- icgSbscIntmDTO.nowIntmSeq
			},
			"privateInfoUseAgree" : { // 개인정보활용동의
				"idDiscern" : "", // 고유식별정보 (Y,N) <- icgSvcContInfoDTO.SvcContAgreeInfoListDTO.
				"privateInfoCollect" : "", // 개인정보수집
				"privateCreditInfoCollect" : "", // 개인신용정보
				"custConvenience" : "", // 고객편의
				"adReceiveAgree" : "", // 광고수신동의
				"privateInfoEntrust" : "", // 개인정보 처리 위탁
				"deviceInsurance" : "", // 단말보험
				"ollehManmile" : "", // 올레 만마일
				"wooriKTHoney" : "", // 우리KT 꿀데이터 통장
				"allianceAgree" : "" // 결합제휴 서비스 동의 (1:단말보험, 2:KB국민카드, 4:우리은행)
			},
			"supporFundInfo" : { // 지원금 방식별 
				"mdlNm" : "", // 모델명 <- (신규: newSbscIntmUsimInfoDTO.intmModelNm, )
				"factoryPrice" : "", // 출고가 (신규: newSbscIntmUsimInfoDTO.intmSaleAmt)
				"useDataAmountPerMonth" : "", // 월데이터사용량
				"useCallAmountPerMonth" : "", // 월 통화 시간
				"useSmsAmountPerMonth" : "", // 월 문자
				"pricePlan" : "", // 요금제 (신규: newSbscSpnsrInfoDTO.prodNm)
				"supportFundPriceSelect" : "", // 지원금 요금할인 선택
				"supportFundDeviceSelect" : "", // 지원금 단말기 선택
				"supportFundPrice" : "", // 지원금 요금할인 선택시
				"supportFundDevice" : "", // 지원금 단말기 선택시
				"payGiveWay" : "", // 대금지급방식 (1,2,3)
				"pubSupportFund" : "", // 공시지원금 (신규: newSbscSpnsrInfoDTO.firstDcSuprtTotAmt)
				"addSupportFund" : "", // 추가지원금 (0)
				"pointReduce" : "", // 포인트할인 (신규)
				"custReceived" : "", // 고객수납금
				"divRepay" : "", // 분할상환원금
				"divNumOfMonth" : "", // 분할상환 개월
				"divNumOfMonthPrincipal" : "", // 분할상환 개월에 따른 분할상환원금
				"amountOfMonth" : "", // A.핸드폰 월 납무액
				"divNumOfMonthCommission" : "", // 분할상환 수수료
				"totalAmountOfSupportFund" : "", // 지원금 총액
				"deviceReduce" : "", // 단말할인
				"priceReduce" : "", // 요금할인
				"panaltyPayAnother" : "", // 위약금대납
				"payBack" : "", // 페이백
				"etcPay" : "", // 기타
				"monthFixedAmount" : "", // 월정액요금
				"monthReduceAmount" : "", // 월요금할인액
				"monthMobileAmount" : "", // B.통신요금월납부액
				"monthBaseAmount" : "" // 월 기본 납부액 (A+B=C)
			},
			"custNBillInfo" : { // 고객정보 / 요금자동납부 
				"custNm" : "", // 고객명
				"birthday" : "", // 생년월일
				"Sex" : "", // 성별
				"contactNo" : "", // 연락처
				"email" : "", // 이메일
				"address" : "", // 주소
				"statement" : "", // 명세서 종류
				"naraNo" : "", // 나라사랑카드번호
				"phoneNo" : "", // 핸드폰 전화 번호 (기변시 사용)
				"sumUpBill" : "", // 전화번호/ID
				"useChargeNoti" : "", // 이용요금알리미 가입
				"autoPay" : "", // 자동납부
				"bankNm" : "", // 은행 또는 카드사명
				"bankNo" : "", // 은행계좌 또는 카드번호
				"anotherPayAgreeNm" : "", // 타인납부동의자 명
				"payerNm" : "", // 납부자명
				"payerRelation" : "", // 납부자와의 관계
				"payerBirthday" : "" // 납부자 생년월일
			},
			"joinInfo" : { // 가입 신청 내역
				"additionalProduct" : "", // 데이터/부가상품
				"additionalplanAmount" : "", // 데이터/부가상품 금액
				"dataRoaming" : "", // 데이터로밍
				"wirelessData" : "", // 무선데이터
				"harmfulInfoBlock" : "", // 유해정보차단 (1:네트워크유해차단,2:올레자녀폰안심프리,4:올레자녀폰안심,8:기타)
				"infoFeeMaxSet" : "", // 정보상한금액설정(청소년)
				"feeChargeMaxSet" : "", // 요금충전한도(청소년)
				"ktMembership" : "", // KT멤버쉽
				"deviceInsuranceProduct" : "", // 단말보험상품
				"phoneNo" : "", // 가입희망번호
				"noConnectService" : "", // 번호연결서비스
				"usimNm" : "", // 유심모델명 <- icgTrtInfoDTO.usimModelNm
				"usimNo" : "", // 유심일련번호 <- icgTrtInfoDTO.usimSeq
				"usimPayWay" : "", // 유심비용 납부 방법 (1:선납, 2:다음달 요금합산)
				"usimPay" : "", // 유심비용
				"etcSpecialContact" : "", // 기타특약
				"communicationAccount" : "", // 통신과금서비스
				"applier" : "" // 신청/가입자(대리인)
			},
			"legalAgent" : { // 법정대리인 정보
				"agentNm" : "", // 법정대리인 성명
				"agentRelation" : "", // 법정대리인 관계
				"agentBirthday" : "", // 법정대리인 생년월일
				"agentSex" : "", // 법정대리인 성별
				"agentContract" : "" // 법정대리인 연락처
			},
			"moveNo" : { //번호이동 정보
				"moveSvcNo" : "", // 이동할 전화번호
				"prevWirelessOperator" : "", // 변경전통신사
				"moveCertKind" : "", // 번호이동인증
				"moveCertNo" : "", // 번호이동인증 번호
				"thisMonthPayment" : "", // 이번달사용요금
				"divRepayment" : "", // 핸드폰분할상환금
				"notRefundPriceSetoff" : "" // 미환급액 요금상계 (Y:동의, N:동의안함)
			},
			"standardContractBreakdown" : { // 표준안내서
				"factoryPrice" : "", // 출고가
				"SupportFund" : "", // 지원금
				"custReceived" : "", // 선납금
				"etcReduce" : "", // 기타할인
				"totalInstallments" : "", // 총 할부 원금
				"InstallmentMonth" : "", // 할부 개월수
				"InstallmentFee" : "", // 할부 수수료(%)
				"totalInstallmentCommission" : "", // 총할부 수수료
				"monthlyInstallmentAmount" : "", // 월 할부금 (A)
				"pricePlan" : "", // 요금제
				"monthFixedAmount" : "", // 월 정액 요금
				"stipulatedTime" : "", // 약정기간
				"monthReduceAmount" : "", // 월 요금 할인액
				"reducePercent" : "", // 요금할인 %
				"reducePercentAmount" : "", // 요금할인금액
				"monthlyCommunicationAmount" : "", // 통신요금월납부액 (B)
				"monthlyBaseAmount" : "", // 월 기본 납부액 (A+B)
				"cancellationCharge12" : "", // 위약금 (12개월)
				"cancellationCharge18" : "", // 위약금 (18개월)
				"userNm" : "", // 판매직원
				"phoneNo" : "", // 이동전화번호
				"currentDay" : "" // 가입일
			},
			"sponsor" : { // 스폰서
				"discountProgram" : "", // 할인 프로그램(1,2)
				"chargeName" : "", // 요금제명
				"contractMonth" : "", // 약정 개월 수
				"discountChargeAmount" : "", // 요금할인 금액
				"contractContent" : "", // 약정내용 - 코스 (1:심플코드, 2:베이직코스)
				"contractDays" : "", // 약정 일수
				"contractSupportFund" : "", // 약정 지원금
				"contractMonthSelect" : "", // 약정 기간 (1,2)
				"monthlyDiscountAmount" : "" // 월 요금할인
			}
		};
	}
	
	

	
	// K-Note 연동 데이터 초기화
	function initKNData() {
		return obj = {
				"appleUserId":trtBaseUserInfo.appleUserId,
				"apiKey":trtBaseUserInfo.apiKey,
				"systemCd":"AppleStore",
				"userId":trtBaseUserInfo.userId,
				"trtmOrgnId":trtBaseUserInfo.orgId,
				"lobCd":"901",
				"categoryCd": trtBaseUserInfo.itgOderTypeCd,
				"phoneUser":"",
				"mobileNo":"",
				"finalYn":"",
				"mmsYn":"N",
				"mmsPubMsgNo":"",
				"onlnAplshtSrlNo":"0",
				"ingrCntpntCd":trtBaseUserInfo.cpntId,
				"beforeItemId":"",
				"documentType":"",
				"scanType":"",
				"uvirtr":"", //
				"changeReason":"",
				"excpCode":"00",
				"idCardType":"", //
				"idCardType2":"",
				"sn":"",
				"nstrdReasonCd":"",
				"imgType":""
			};
	}

	// 서식지 native 실행
	function docStart() {
		// -- 다음 버튼 비 활성화 처리
		alert ("start DocStart");
		document.ifForApp.location = "akos://web/documents";
	}
	
	// document Next 버튼
	function nextBtn() {
		uploadDocuments(); // 서식지 이미지 서버 업로드
	}
	
	// 서식지 이미지 서버 전송 (web -> Native)
	function uploadDocuments() {
		window.location.href = "akos://web/documents/upload";
	}
	
	// 서식지 이미지 서버 전송 완료 (Native -> web)
	function uploadDocumentsComplete() {
		standardDoc();
		// -- 개통 처리 또는 기기 변경 처리 (ajax)
		// -- 완료 후 완료 안내 화면 이동
		// -- 완료 안내 화면에서 onload 시 standardNativeCall() : 표준안내서 Native 호출 (표준안내서 + 신청서)
	}

	// 서식지 종류 및 매핑 정보 조회
	function getDocumentsVo(custSignatureUrl, custSignUrl, userSignatureUrl, userSignUrl)
	{
		var docuAllVo = initDocuemtData();

		var objArray = new Array();
		var obj;

		// 서식지 종류
		obj = new Object();
		if (inVo.trtBaseInfoDTO.itgOderTypeCd == "NAC"
				|| inVo.trtBaseInfoDTO.itgOderTypeCd == "MNP") {
			obj.Subject = "신규신청서";
			obj.DocServer = "http://10.217.81.120/oz70/server";
			obj.DocRepository = "/KtAppleStore/KT_mobile_신규신청서_custcut.ozr";
			if (inVo.trtBaseInfoDTO.aminorYn != null && inVo.trtBaseInfoDTO.aminorYn == "Y") {
				obj.DocRepository = "/KtAppleStore/KT_mobile_신규신청서_청소년_custcut.ozr";
			}
			objArray.push(obj);
		} else {
			obj.Subject = "변경신청서";
			obj.DocServer = "http://10.217.81.120/oz70/server";
			obj.DocRepository = "/KtAppleStore/KT_mobile_변경신청서.ozr";
			objArray.push(obj);
		}
		
		obj = new Object();
		obj.Subject = "개인정보활용동의서";
		obj.DocServer = "http://10.217.81.120/oz70/server";
		obj.DocRepository = "/KtAppleStore/KT_mobile_개인정보활용동의서_결합화면포함.ozr";
		objArray.push(obj);

		obj = new Object();
		obj.Subject = "스폰서";
		obj.DocServer = "http://10.217.81.120/oz70/server";
		obj.DocRepository = "/KtAppleStore/KT_mobile_스폰서신청서.ozr";
		objArray.push(obj);

		docuAllVo.DocumentsArray = objArray;

		// 서명 및 사인 위치
		docuAllVo.signature.custSignature = custSignatureUrl;
		docuAllVo.signature.custSign = custSignUrl;
		docuAllVo.signature.userSignature = userSignatureUrl;
		docuAllVo.signature.userSign = userSignUrl;

		// 개통 및 기변 공통 값 세팅
		docuAllVo.baseInfo.orgNm = trtBaseUserInfo.orgNm;
		docuAllVo.baseInfo.orgId = trtBaseUserInfo.orgId;
		docuAllVo.baseInfo.counselor = trtBaseUserInfo.userNm;
		docuAllVo.baseInfo.counselorNo = trtBaseUserInfo.userTelNo;
		if (inVo.trtBaseInfoDTO.itgOderTypeCd == "NAC") {
			docuAllVo.baseInfo.orderType = "1";
		}
		else if (inVo.trtBaseInfoDTO.itgOderTypeCd == "MNP") {
			docuAllVo.baseInfo.orderType = "2";
		}
		else if (inVo.trtBaseInfoDTO.itgOderTypeCd == "HCN") {
			docuAllVo.baseInfo.orderType = "3";
		}
		
		switch (inVo.trtBaseInfoDTO.itgOderTypeCd) {
		case "NAC":
		case "MNP":
			docuAllVo = setNACDocumentData(docuAllVo);
			break;
		case "HCN":
			docuAllVo = setHCNDocumentData(docuAllVo);
			break;
		}

		return JSON.stringify(docuAllVo);
	}
	
	// K-Note 전송을 위한 정보
	function getDocumentsUploadInfo()
	{
		var objKNData = new Object();
		var objArray = new Array();
		var obj;
		
		obj = new Object();
		obj = initKNData();
		obj.documentType = "A0023";
		obj.imgType = "99";
		
		objArray.push(obj);
		
		obj = new Object();
		obj = initKNData();
		obj.documentType = "A0001";
		obj.imgType = "99";
		
		objArray.push(obj);
		
		obj = new Object();
		obj = initKNData();
		obj.documentType = "A0017";
		obj.imgType = "99";
		
		objArray.push(obj);
		
//	 	objKNData.FamilyProofUploadInfo = objArray;
		objKNData = objArray;
		var jsonKNData = JSON.stringify(objKNData);
		
		return jsonKNData;
	}

	// 표준 안내 및 신청서 native 실행
	function standardDoc() {
		window.location.href = "akos://web/documents/complete";
		setTimeout(function () { window.location.href = "akos://web/standard"; }, 500);
	}
	
	// 표준 안내 업로드 요청
	function uploadStandard() {
		window.location.href = "akos://web/standard/upload";
	}
	
	// 표준 안내 업로드 완료
	function uploadStandardComplete() {
		window.location.href = "akos://web/standard/complete";
		
		document.form.action = "/KKOS/services/nnsb/NNSB0902.do";
		setTimeout(function () { $("#form").submit(); }, 500);
		
		// 고객안내 페이지로 이동
	}
	
	// 표준 안내 매핑 정보 전달
	function getStandardVo(custSignatureUrl, custSignUrl, userSignatureUrl, userSignUrl) {
		var docuAllVo = initDocuemtData();

		var objArray = new Array();
		var obj;

		// 서식지 종류
		obj = new Object();
		obj.Subject = "표준안내서";
		obj.DocServer = "http://10.217.81.120/oz70/server";
		obj.DocRepository = "/KtAppleStore/KT_mobile_표준안내서.ozr";
		objArray.push(obj);

		docuAllVo.DocumentsArray = objArray;

		// 서명 및 사인 위치
		docuAllVo.signature.custSignature = custSignatureUrl;
		docuAllVo.signature.custSign = custSignUrl;
		docuAllVo.signature.userSignature = userSignatureUrl;
		docuAllVo.signature.userSign = userSignUrl;

		// 개통 및 기변 공통 값 세팅
		docuAllVo.baseInfo.orgNm = trtBaseUserInfo.orgNm;
		docuAllVo.baseInfo.orgId = trtBaseUserInfo.orgId;
		docuAllVo.baseInfo.counselor = trtBaseUserInfo.userNm;
		docuAllVo.baseInfo.counselorNo = trtBaseUserInfo.userTelNo;
		if (inVo.trtBaseInfoDTO.itgOderTypeCd == "NAC") {
			docuAllVo.baseInfo.orderType = "1";
		}
		else if (inVo.trtBaseInfoDTO.itgOderTypeCd == "MNP") {
			docuAllVo.baseInfo.orderType = "2";
		}
		else if (inVo.trtBaseInfoDTO.itgOderTypeCd == "HCN") {
			docuAllVo.baseInfo.orderType = "3";
		}
		
// 		switch (inVo.trtBaseInfoDTO.itgOderTypeCd) {
// 		case "NAC":
// 		case "MNP":
// 			docuAllVo = setNACDocumentData(docuAllVo);
// 			break;
// 		case "HCN":
// 			docuAllVo = setHCNDocumentData(docuAllVo);
// 			break;
// 		}
		docuAllVo = setStandardData(docuAllVo);

		return JSON.stringify(docuAllVo);
	}

	// 고객안내 인쇄 대상 및 매핑 데이터 조회 (web -> Native)
	function stdNDoc() {
		window.location.href = "akos://web/doc";
	}

	// 고객안내 인쇄 대상 및 매핑 데이터 조회 (Native -> web)
	function getDocVo(custSignatureUrl, custSignUrl, userSignatureUrl, userSignUrl) {
		var docuAllVo = initDocuemtData();

		var objArray = new Array();
		var obj;

		// 서식지 종류
		obj = new Object();
		obj.Subject = "표준안내 및 신청서";
		obj.DocServer = "http://10.217.81.120/oz70/server";
		obj.DocRepository = "/KtAppleStore/KT_mobile_표준안내서.ozr";
		objArray.push(obj);

		obj = new Object();
		if (inVo.trtBaseInfoDTO.itgOderTypeCd == "NAC"
				|| inVo.trtBaseInfoDTO.itgOderTypeCd == "MNP") {
			obj.Subject = "신규신청서";
			obj.DocServer = "http://10.217.81.120/oz70/server";
			obj.DocRepository = "/KtAppleStore/KT_mobile_신규신청서_custcut.ozr";
			if (inVo.trtBaseInfoDTO.aminorYn != null && inVo.trtBaseInfoDTO.aminorYn == "Y") {
				obj.DocRepository = "/KtAppleStore/KT_mobile_신규신청서_청소년_custcut.ozr";
			}
			objArray.push(obj);
		} else {
			obj.Subject = "변경신청서";
			obj.DocServer = "http://10.217.81.120/oz70/server";
			obj.DocRepository = "/KtAppleStore/KT_mobile_변경신청서.ozr";
			objArray.push(obj);
		}

		docuAllVo.DocumentsArray = objArray;

		// 서명 및 사인 위치
		docuAllVo.signature.custSignature = custSignatureUrl;
		docuAllVo.signature.custSign = custSignUrl;
		docuAllVo.signature.userSignature = userSignatureUrl;
		docuAllVo.signature.userSign = userSignUrl;

		// 개통 및 기변 공통 값 세팅
		docuAllVo.baseInfo.orgNm = trtBaseUserInfo.orgNm;
		docuAllVo.baseInfo.orgId = trtBaseUserInfo.orgId;
		docuAllVo.baseInfo.counselor = trtBaseUserInfo.userNm;
		docuAllVo.baseInfo.counselorNo = trtBaseUserInfo.userTelNo;
		
		switch (inVo.trtBaseInfoDTO.itgOderTypeCd) {
		case "NAC":
		case "MNP":
			docuAllVo = setNACDocumentData(docuAllVo);
			break;
		case "HCN":
			docuAllVo = setHCNDocumentData(docuAllVo);
			break;
		}
		docuAllVo = setStandardData(docuAllVo);

		return JSON.stringify(docuAllVo);
	}

	//신규 및 번호이동 개통 가입 정보
	function setNACDocumentData(docData) {
		docData.baseInfo.custType = "1";
		docData.baseInfo.mdlNm = inVo.newSbscIntmUsimInfoDTO.intmModelNm;
		docData.supporFundInfo.factoryPrice = inVo.newSbscIntmUsimInfoDTO.intmSaleAmt;
		docData.supporFundInfo.pricePlan = inVo.newSbscTrtInfoDTO.prodNm;
		docData.supporFundInfo.supportFundPrice = inVo.newSbscSpnsrInfoDTO.tmpDcSuprtAmtChage12;
		docData.supporFundInfo.supportFundDevice = inVo.newSbscSpnsrInfoDTO.tmpDcSuprtAmtChage12;
		docData.supporFundInfo.pubSupportFund = inVo.newSbscSpnsrInfoDTO.firstDcSuprtTotAmt;
		docData.supporFundInfo.addSupportFund = "0";
		docData.supporFundInfo.pointReduce = inVo.newSbscTrtInfoDTO.mlgUseVal;
		docData.supporFundInfo.custReceived = "0";
		docData.supporFundInfo.divRepay = inVo.intmInslInfoOutDTO.instaAmt;
		docData.supporFundInfo.divNumOfMonth = inVo.intmInslInfoOutDTO.totInslMonsNum;
		docData.supporFundInfo.divNumOfMonthPrincipal = inVo.intmInslInfoOutDTO.instaAmt;
		docData.supporFundInfo.amountOfMonth = inVo.intmInslInfoOutDTO.mbyPayAmt;
		docData.supporFundInfo.divNumOfMonthCommission = inVo.intmInslInfoOutDTO.inslIntrTotAmt;
		docData.supporFundInfo.deviceReduce = inVo.newSbscSpnsrInfoDTO.firstDcSuprtTotAmt;
		docData.supporFundInfo.monthFixedAmount = inVo.newSbscTrtInfoDTO.prodIndcChage;
		if (inVo.intmInslInfoOutDTO.totInslMonsNum == 12) {
			docData.supporFundInfo.monthReduceAmount = inVo.newSbscSpnsrInfoDTO.DcSuprtAmtChage12 / inVo.intmInslInfoOutDTO.totInslMonsNum;
		}
		else {
			docData.supporFundInfo.monthReduceAmount = inVo.newSbscSpnsrInfoDTO.DcSuprtAmtChage24 / inVo.intmInslInfoOutDTO.totInslMonsNum;
		}
		docData.supporFundInfo.monthMobileAmount = docData.supporFundInfo.monthFixedAmount - docData.supporFundInfo.monthReduceAmount;
		docData.supporFundInfo.monthBaseAmount = docData.supporFundInfo.amountOfMonth + docData.supporFundInfo.monthMobileAmount;

		docData.custNBillInfo.custNm = inVo.custInfoDTO.custNm;
		docData.custNBillInfo.birthday = inVo.custInfoDTO.custIdfyNo.substring(0,6);
		docData.custNBillInfo.contactNo = inVo.newSbscTrtInfoDTO.svcNo;
		docData.custNBillInfo.email = inVo.billAccInfoDTO.email;
		docData.joinInfo.phoneNo = inVo.newSbscTrtInfoDTO.svcNo;
		docData.joinInfo.usimNm = inVo.newSbscIntmUsimInfoDTO.usimModelNm;
		docData.joinInfo.usimNo = inVo.newSbscIntmUsimInfoDTO.usimSeq;
		docData.joinInfo.usimPayWay = "2";
		docData.joinInfo.usimPay = inVo.newSbscIntmUsimInfoDTO.usimSaleAmt;
		docData.joinInfo.communicationAccount = "2";
		docData.joinInfo.applier = inVo.custInfoDTO.custNm;
		if (inVo.trtBaseInfoDTO.aminorYn != null && inVo.trtBaseInfoDTO.aminorYn == "Y") {
			docData.joinInfo.applier = inVo.custInfoDTO.legalCustNm;
		}
		if (inVo.trtBaseInfoDTO.itgOderTypeCd == "MNP") {
			docData.moveNo.moveSvcNo = inVo.NpInfoDTO.telNo;
			docData.moveNo.prevWirelessOperator = inVo.NpInfoDTO.bchngNpCommCmpnNm;
			// athnItemCd(1 지로, 2 단말기 일련번호, 3 계좌번호, 4 KT합산청구번호, 5 카드번호)
			if (inVo.NpInfoDTO.athnItemCd == "1") 		{ docData.moveNo.moveCertKind = "4"; }
			else if (inVo.NpInfoDTO.athnItemCd == "2") 	{ docData.moveNo.moveCertKind = "1"; }
			else if (inVo.NpInfoDTO.athnItemCd == "3") 	{ docData.moveNo.moveCertKind = "2"; }
			else if (inVo.NpInfoDTO.athnItemCd == "4")  { docData.moveNo.moveCertKind = "5"; } // 합산 청구인 경우?
			else if (inVo.NpInfoDTO.athnItemCd == "5")  { docData.moveNo.moveCertKind = "3"; }
			docData.moveNo.moveCertNo = inVo.NpInfoDTO.athnSbst;
			docData.moveNo.thisMonthPayment = "2";
			docData.moveNo.divRepayment = "2";
			docData.moveNo.notRefundPriceSetoff = inVo.NpInfoDTO.npHndsetInstaDuratDivCd;
		}

		return docData;
	}
	
	// 기기변경 정보
	function setHCNDocumentData(docData) {
		docData.baseInfo.custType = "1";
		docData.baseInfo.mdlNm = inVo.icgTrtInfoDTO.intmModelNm;
		docData.baseInfo.mdlserialNo = inVo.icgTrtInfoDTO.intmSeq;
		docData.supporFundInfo.mdlNm = inVo.icgTrtInfoDTO.intmModelNm;
		docData.supporFundInfo.factoryPrice = inVo.icgTrtInfoDTO.intmSaleAmt;
		docData.supporFundInfo.useDataAmountPerMonth = inVo.icgTrtInfoDTO.freeData;
		docData.supporFundInfo.useCallAmountPerMonth = inVo.icgTrtInfoDTO.freeCall;
		docData.supporFundInfo.useSmsAmountPerMonth = inVo.icgTrtInfoDTO.freeSms;
		docData.supporFundInfo.pricePlan = inVo.icgTrtInfoDTO.prodNm;
		docData.supporFundInfo.supportFundPrice = inVo.icgTrtInfoDTO.dcSuprtAmtChage;
		docData.supporFundInfo.supportFundDevice = inVo.icgTrtInfoDTO.firstDcSuprtTotAmt;
		docData.supporFundInfo.payGiveWay = "2";
		docData.supporFundInfo.pubSupportFund = inVo.icgTrtInfoDTO.firstDcSuprtTotAmt;
		docData.supporFundInfo.pointReduce = inVo.icgTrtInfoDTO.mlgUseVal + inVo.icgEtcInfoDTO.icgPointAmt + inVo.icgEtcInfoDTO.simplAcuAmt;
		docData.supporFundInfo.custReceived = "";
		docData.supporFundInfo.divRepay = inVo.icgTrtInfoDTO.totInslAmt;
		docData.supporFundInfo.divNumOfMonth = inVo.icgTrtInfoDTO.totInslMonsNum;
		docData.supporFundInfo.divNumOfMonthPrincipal = inVo.icgTrtInfoDTO.totInslAmt;
		docData.supporFundInfo.amountOfMonth = inVo.icgTrtInfoDTO.monthInslAmt;
		docData.supporFundInfo.divNumOfMonthCommission = inVo.icgTrtInfoDTO.inslIntrTotAmt;
		docData.supporFundInfo.totalAmountOfSupportFund = inVo.icgTrtInfoDTO.dcSuprtAmtChage + inVo.icgTrtInfoDTO.firstDcSuprtTotAmt + inVo.icgTrtInfoDTO.mlgUseVal + inVo.icgEtcInfoDTO.icgPointAmt + inVo.icgEtcInfoDTO.simplAcuAmt;
		docData.supporFundInfo.monthFixedAmount = inVo.icgTrtInfoDTO.prodIndcChage;
		docData.supporFundInfo.monthReduceAmount = inVo.icgTrtInfoDTO.dcSuprtAmtChage / inVo.icgTrtInfoDTO.engtPerdMonsNum;
		docData.supporFundInfo.monthMobileAmount = inVo.icgTrtInfoDTO.prodIndcChage - (inVo.icgTrtInfoDTO.dcSuprtAmtChage / inVo.icgTrtInfoDTO.engtPerdMonsNum);
		docData.supporFundInfo.monthBaseAmount = inVo.icgTrtInfoDTO.monthInslAmt + inVo.icgTrtInfoDTO.prodIndcChage - (inVo.icgTrtInfoDTO.dcSuprtAmtChage / inVo.icgTrtInfoDTO.engtPerdMonsNum);
		docData.custNBillInfo.custNm = inVo.icgSvcContInfoDTO.custNm;
		docData.custNBillInfo.birthday = inVo.icgSvcContInfoDTO.custIdfyNo;
		docData.custNBillInfo.Sex = inVo.icgSvcContInfoDTO.custIdfyNo;
		docData.custNBillInfo.contactNo = inVo.rnmAthnInfoDTO.rnmConfrTelNo;
		docData.joinInfo.usimNm = inVo.icgTrtInfoDTO.usimModelNm;
		docData.joinInfo.usimNo = inVo.icgTrtInfoDTO.usimSeq;
		docData.joinInfo.usimPay = inVo.icgTrtInfoDTO.usimAmt;
		docData.legalAgent.agentNm = inVo.icgSvcContInfoDTO.legalCustNm;
		docData.legalAgent.agentRelation = inVo.icgSvcContInfoDTO.legalRelNm;
		docData.legalAgent.agentBirthday = inVo.icgSvcContInfoDTO.legalCustIdfyNo;
		docData.legalAgent.agentSex = inVo.icgSvcContInfoDTO.legalCustIdfyNo;
		docData.legalAgent.agentContract = inVo.rnmAthnInfoDTO.legalRnmConfrTelNo;

		return docData;
	}
	
	// 표준 안내서 정보
	function setStandardData(docData) {
		// -- 표준안내 데이터 조회
		docData.standardContractBreakdown.factoryPrice = "1000";
		docData.standardContractBreakdown.SupportFund = "1000";
		docData.standardContractBreakdown.custReceived = "1000";
		docData.standardContractBreakdown.etcReduce = "1000";
		docData.standardContractBreakdown.totalInstallments = "1000";
		docData.standardContractBreakdown.InstallmentMonth = "1000";
		docData.standardContractBreakdown.InstallmentFee = "1000";
		docData.standardContractBreakdown.totalInstallmentCommission = "1000";
		docData.standardContractBreakdown.monthlyInstallmentAmount = "1000";
		docData.standardContractBreakdown.pricePlan = "요금제";
		docData.standardContractBreakdown.monthFixedAmount = "1000";
		docData.standardContractBreakdown.stipulatedTime = "2";
		docData.standardContractBreakdown.monthReduceAmount = "1000";
		docData.standardContractBreakdown.reducePercent = "100";
		docData.standardContractBreakdown.reducePercentAmount = "100";
		docData.standardContractBreakdown.monthlyCommunicationAmount = "100";
		docData.standardContractBreakdown.monthlyBaseAmount = "200";
		docData.standardContractBreakdown.cancellationCharge12 = "100";
		docData.standardContractBreakdown.cancellationCharge18 = "100";
		docData.standardContractBreakdown.userNm = "판매직원";
		docData.standardContractBreakdown.phoneNo = "01011111111";
		
		return docData;
	}
	
	
	// 고객 사인
	function custSignNative() {
		window.location.href = "akos://web/sign";
	}
	
	// 판매자 사인
	function userSignNative() {
		window.location.href = "akos://web/userSign";
	}
	
	// 고객 사인 서버 임시 등록 요청
	function custSignUpload() {
		window.location.href = "akos://web/sign/upload";
	}
	
	// 판매자 사인 서버 임시 등록 요청
	function userSignUpload() {
		window.location.href = "akos://web/userSign/upload";
	}
	
	// 고객 사인 이미지 업로드 완료 수신
	function custSignUploadComplete() {
		userSignNative();
	}

	// 판매자 사인 이미지 업로드 완료 수신
	function userSignUploadComplete() {
		// 다음 페이지 이동 (서식지 작성)
	}

	// 고객 사인 서버 등록 임시 관리 정보
	function getSignUploadInfo() {
		var objKNData = new Object();
		
		objKNData.name = initKNData();
		objKNData.name.imgType = "13";
		
		objKNData.sign = initKNData();
		objKNData.sign.imgType = "12";
		
		var jsonKNData = JSON.stringify(objKNData);
		
		return jsonKNData;		
	}

	// 판매자 사인 서버 등록 임시 관리 정보
	function getUserSignUploadInfo() {
		var objKNData = new Object();
		
		objKNData.name = initKNData();
		objKNData.name.imgType = "15";
		
		objKNData.sign = initKNData();
		objKNData.sign.imgType = "14";
		
		var jsonKNData = JSON.stringify(objKNData);
		
		return jsonKNData;
	}

	function goBack() {
		history.back();
	}

	function goNext() {
		if (curStep == "standard") {
			window.location.href = "akos://web/standard/complete";
			
			document.form.action = "/KKOS/services/nnsb/NNSB0902.do";
			setTimeout(function () { $("#form").submit(); }, 500);
		}
		else {
			standardDoc();
			curStep = "standard";
		}

	}
	
	// 다음 버튼 활성화 (Native -> web : 입력 완료시 호출)
	function btnNextEnabled(enableFlag) {
		if (enableFlag == "Y") {
			document.getElementById(nextBtnId).disabled = false;
		} else {
			document.getElementById(nextBtnId).disabled = true;
		}
	}
</script>
		 <form role="form" class="form-horizontal" name = "form" id = "form" method = "post">
		 <input type="hidden" id="inputVo" name = "inputVo" value = '${inputVo}' >
     	</form>
     	<iframe id="ifForApp" name="ifForApp" width="400" height="400" style="display:none"></iframe>
<!-- /.container -->

<footer class="footer">
  <div class="left"><span><img src="${ctx_res}/images/left_previous.png" class="img-responsive" onClick="goPrev()"></span> <span><img src="${ctx_res}/images/left_home.png" class="img-responsive"></span></div>
  <div class="right main">

    <div class="col-sm-2 floatR">
      <button type="button" class="btn btn-page  btn-block " id="btnNext" name="btnNext" onClick="goNext()">다음</button>
    </div>
  </div>
</footer>