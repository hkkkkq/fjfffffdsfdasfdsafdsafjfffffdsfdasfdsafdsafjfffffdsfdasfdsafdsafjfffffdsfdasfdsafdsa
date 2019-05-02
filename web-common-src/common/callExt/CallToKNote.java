package com.kt.kkos.common.callExt;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MIME;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.kt.kkos.common.enc.SafeDBUtil;
import com.kt.kkos.common.util.ApiUtils;
import com.kt.kkos.common.util.DateUtil;
import com.kt.kkos.common.util.ITLKeyUTIL;
import com.kt.kkos.common.util.MapUtil;
import com.kt.kkos.common.util.ObjectConverter;
import com.kt.kkos.common.util.StringUtil;
import com.kt.kkos.exception.ITLBusinessException;
import com.kt.kkos.itl.bo.common.ImageService;
import com.kt.kkos.itl.bo.nwrc.CrisServiceCaller;
import com.kt.kkos.itl.mapper.common.ImageInfoMapper;
import com.kt.kkos.itl.mapper.common.TrtBaseInfoMapper;
import com.kt.kkos.itl.model.common.ImageInfoDTO;
import com.kt.kkos.itl.model.common.ImageRequestInVO;
import com.kt.kkos.itl.model.common.ImageRequestOutVO;
import com.kt.kkos.itl.model.common.KnoteIfResponseDTO;
import com.kt.kkos.itl.model.common.KnoteScanListReqDTO;
import com.kt.kkos.itl.model.common.KnoteScanListRespDTO;
import com.kt.kkos.itl.model.common.KnoteScanListRespVO;

@Service
public class CallToKNote {
	private static final Logger logger = LoggerFactory.getLogger(CallToKNote.class);
	
	@Autowired
	private CrisServiceCaller 	crisServiceCaller;
	
	@Autowired
	private ImageService imageService;
	
	@Value("${knoteScanListUrl}")
	private String knoteScanListUrl;
	
	@Value("${K-Note}")
	private String knoteUri;
	
	
	@Autowired
	private TrtBaseInfoMapper trtBaseInfoMapperDao;
	
	
	@Autowired
	private ImageInfoMapper imageInfoMapper;
	
	/**
	 * JSONObject Output 암호화 및 마스킹 처리
	 * @param JSONObject
	 * @param Class
	 * @throws ITLBusinessException 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public KnoteScanListRespVO getScanList(KnoteScanListReqDTO knoteScanListReqDTO) throws ITLBusinessException {
		
		logger.debug("knoteScanListReqDTO.getOrdr_lob() : " + knoteScanListReqDTO.getOrdr_lob());
		logger.debug("knoteScanListReqDTO.getOrdr_ctgr() : " + knoteScanListReqDTO.getOrdr_ctgr());
		logger.debug("knoteScanListReqDTO.getDoc_state() : " + knoteScanListReqDTO.getDoc_state());
		logger.debug("knoteScanListReqDTO.getOrgn_cd() : " + knoteScanListReqDTO.getOrgn_cd());
		logger.debug("knoteScanListReqDTO.getStrt_dt() : " + knoteScanListReqDTO.getStrt_dt());
		logger.debug("knoteScanListReqDTO.getEnd_dt() : " + knoteScanListReqDTO.getEnd_dt());
		logger.debug("knoteScanListReqDTO.getCust_nm() : " + knoteScanListReqDTO.getCust_nm());
		logger.debug("knoteScanListReqDTO.getOthr_trtm_prsn_cvr_yn() : " + knoteScanListReqDTO.getOthr_trtm_prsn_cvr_yn());
		logger.debug("knoteScanListReqDTO.getAgnc_id() : " + knoteScanListReqDTO.getAgnc_id());
		logger.debug("knoteScanListReqDTO.getIngr_cntpnt_cd() : " + knoteScanListReqDTO.getIngr_cntpnt_cd());
		logger.debug("knoteScanListReqDTO.getInqr_cascnt() : " + knoteScanListReqDTO.getInqr_cascnt());
		logger.debug("knoteScanListReqDTO.getInqr_base() : " + knoteScanListReqDTO.getInqr_base());
		logger.debug("knoteScanListReqDTO.getUser_id() : " + knoteScanListReqDTO.getUser_id());
		logger.debug("knoteScanListReqDTO.getItem_id() : " + knoteScanListReqDTO.getItem_id());

		/*
		 * Request 데이터 생성 (SafeDB 암호화 및 xml 포맷)
		*/
		String ordrLob = SafeDBUtil.getInstance().encrypt(knoteScanListReqDTO.getOrdr_lob() == null ? "901" : knoteScanListReqDTO.getOrdr_lob());
		String ordrCtgr = SafeDBUtil.getInstance().encrypt(knoteScanListReqDTO.getOrdr_ctgr() == null ? "" : knoteScanListReqDTO.getOrdr_ctgr());
		String docState = SafeDBUtil.getInstance().encrypt(knoteScanListReqDTO.getDoc_state() == null ? "000" : knoteScanListReqDTO.getDoc_state()); // 000 전체, 001 대기, 002 보류, 003 완료, 004 폐기
		String orgnCd = SafeDBUtil.getInstance().encrypt(knoteScanListReqDTO.getOrgn_cd() == null ? "000" : knoteScanListReqDTO.getOrgn_cd()); // 000 전체, 001 팩스, 002 스캔, 003 온라인, 004 Wibro, 005 스마트, 006 스마트(홈), 007 모바일
		String strtDt = SafeDBUtil.getInstance().encrypt(knoteScanListReqDTO.getStrt_dt() == null ? "" : knoteScanListReqDTO.getStrt_dt());
		String endDt = SafeDBUtil.getInstance().encrypt(knoteScanListReqDTO.getEnd_dt() == null ? "" : knoteScanListReqDTO.getEnd_dt());
		String custNm = SafeDBUtil.getInstance().encrypt(knoteScanListReqDTO.getCust_nm() == null ? "" : knoteScanListReqDTO.getCust_nm());
		String othrTrtmPrsnCvrYn = SafeDBUtil.getInstance().encrypt(knoteScanListReqDTO.getOthr_trtm_prsn_cvr_yn() == null ? "Y" : knoteScanListReqDTO.getOthr_trtm_prsn_cvr_yn());
		String agncId = SafeDBUtil.getInstance().encrypt(knoteScanListReqDTO.getAgnc_id() == null ? "" : knoteScanListReqDTO.getAgnc_id());
		String ingrCntpntCd = SafeDBUtil.getInstance().encrypt(knoteScanListReqDTO.getIngr_cntpnt_cd() == null ? "" : knoteScanListReqDTO.getIngr_cntpnt_cd());
		String inqrCascnt = SafeDBUtil.getInstance().encrypt(knoteScanListReqDTO.getInqr_cascnt() == null ? "" : knoteScanListReqDTO.getInqr_cascnt());
		String inqrBase = SafeDBUtil.getInstance().encrypt(knoteScanListReqDTO.getInqr_base() == null ? "" : knoteScanListReqDTO.getInqr_base());
		String userId = SafeDBUtil.getInstance().encrypt(knoteScanListReqDTO.getUser_id() == null ? "" : knoteScanListReqDTO.getUser_id());
		String itemId = SafeDBUtil.getInstance().encrypt(knoteScanListReqDTO.getItem_id() == null ? "" : knoteScanListReqDTO.getItem_id());
		
		StringBuffer strBuf = new StringBuffer();
		strBuf.append("<?xml version=\"1.0\"?>");
		strBuf.append("<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">");
		strBuf.append("	<soap:Header/>");
		strBuf.append("	<soap:Body>");
		strBuf.append("		<biz:Request xmlns:biz=\"http://bizhub.ktf.com\">");
		strBuf.append("			<commHeader/>");
		strBuf.append("			<bizHeader/>");
		strBuf.append("			<root>");
		strBuf.append("				<ORDR_LOB>").append(ordrLob).append("</ORDR_LOB>");		// 상품 코드 (무선: 901)
		strBuf.append("				<ORDR_CTGR>").append(ordrCtgr).append("</ORDR_CTGR>");	// 오더 카테고리 (NAC, HCNE)
		strBuf.append("				<DOC_STATE>").append(docState).append("</DOC_STATE>");	// 문서상태 (Fix : 000)
		strBuf.append("				<ORGN_CD>").append(orgnCd).append("</ORGN_CD>");		// 유입경로
		strBuf.append("				<STRT_DT>").append(strtDt).append("</STRT_DT>");		// 조회 From 일자 (YYYYMMDD)
		strBuf.append("				<END_DT>").append(endDt).append("</END_DT>");			// 조회 To 일자 (YYYYMMDD)
		strBuf.append("				<CUST_NM>").append(custNm).append("</CUST_NM>");		// 고객명
		strBuf.append("				<OTHR_TRTM_PRSN_CVR_YN>").append(othrTrtmPrsnCvrYn).append("</OTHR_TRTM_PRSN_CVR_YN>"); // 타처리자 포함 여부
		strBuf.append("				<AGNC_ID>").append(agncId).append("</AGNC_ID>");		// 대리점 아이디
		strBuf.append("				<INGR_CNTPNT_CD>").append(ingrCntpntCd).append("</INGR_CNTPNT_CD>");		// 접점 아이디
		strBuf.append("				<INQR_CASCNT>").append(inqrCascnt).append("</INQR_CASCNT>");	// 조회 기준
		strBuf.append("				<INQR_BASE>").append(inqrBase).append("</INQR_BASE>");		// NEXT 조회 건수
		strBuf.append("				<ITEM_ID>").append(itemId).append("</ITEM_ID>");			// 서식지 ID
		strBuf.append("				<USER_ID>").append(userId).append("</USER_ID>");			// 사용자 ID
		strBuf.append("			</root>");
		strBuf.append("		</biz:Request>");
		strBuf.append("	</soap:Body>");
		strBuf.append("</soap:Envelope> ");
		
		/*
		 * HttpRequest Create & Execute
		*/
//		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpClient httpClient = getSSLHttpClient();
		HttpPost httpPost = new HttpPost(knoteScanListUrl);
		
		// Set XML Data to RequestBody
		logger.debug("Request XML : " + strBuf.toString());
		httpPost.setEntity(new StringEntity(strBuf.toString(), StandardCharsets.UTF_8));

//		ContentType.MULTIPART_FORM_DATA
//		httpPost.addHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_FORM_URLENCODED.withCharset(StandardCharsets.UTF_8).toString());
		httpPost.addHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_XML.withCharset(StandardCharsets.UTF_8).toString());
		CloseableHttpResponse httpResponse;
		
		ArrayList<KnoteScanListRespDTO> arrayList = new ArrayList();
		
		try {
			httpResponse = httpClient.execute(httpPost);
			
			logger.debug("httpResponse.status : " + httpResponse.getStatusLine().getStatusCode());
			
			String respStr = EntityUtils.toString(httpResponse.getEntity(), StandardCharsets.UTF_8);
			
			logger.debug("httpResponse.respStr : " + respStr);
			
			arrayList = ObjectConverter.stringToArrayList(respStr, KnoteScanListRespDTO.class);
			
			for (int ii=0; ii<arrayList.size(); ii++) {
				KnoteScanListRespDTO knoteScanListRespDTO = (KnoteScanListRespDTO)arrayList.get(ii);
				
				if (knoteScanListRespDTO.getTitl() != null) knoteScanListRespDTO.setTitl(SafeDBUtil.getInstance().decrypt(knoteScanListRespDTO.getTitl()));
				if (knoteScanListRespDTO.getCust_nm() != null) knoteScanListRespDTO.setCust_nm(SafeDBUtil.getInstance().decrypt(knoteScanListRespDTO.getCust_nm()));
				if (knoteScanListRespDTO.getOrgn_id() != null) knoteScanListRespDTO.setOrgn_id(SafeDBUtil.getInstance().decrypt(knoteScanListRespDTO.getOrgn_id()));
				if (knoteScanListRespDTO.getOrgn_nm() != null) knoteScanListRespDTO.setOrgn_nm(SafeDBUtil.getInstance().decrypt(knoteScanListRespDTO.getOrgn_nm()));
				if (knoteScanListRespDTO.getOrdr_lob() != null) knoteScanListRespDTO.setOrdr_lob(SafeDBUtil.getInstance().decrypt(knoteScanListRespDTO.getOrdr_lob()));
				if (knoteScanListRespDTO.getOrdr_ctgr() != null) knoteScanListRespDTO.setOrdr_ctgr(SafeDBUtil.getInstance().decrypt(knoteScanListRespDTO.getOrdr_ctgr()));
				if (knoteScanListRespDTO.getCnt() != null) knoteScanListRespDTO.setCnt(SafeDBUtil.getInstance().decrypt(knoteScanListRespDTO.getCnt()));
				if (knoteScanListRespDTO.getStrt_dt() != null) knoteScanListRespDTO.setStrt_dt(SafeDBUtil.getInstance().decrypt(knoteScanListRespDTO.getStrt_dt()));
				if (knoteScanListRespDTO.getSls_path() != null) knoteScanListRespDTO.setSls_path(SafeDBUtil.getInstance().decrypt(knoteScanListRespDTO.getSls_path()));
				if (knoteScanListRespDTO.getSls_path_nm() != null) knoteScanListRespDTO.setSls_path_nm(SafeDBUtil.getInstance().decrypt(knoteScanListRespDTO.getSls_path_nm()));
				if (knoteScanListRespDTO.getUser_id() != null) knoteScanListRespDTO.setUser_id(SafeDBUtil.getInstance().decrypt(knoteScanListRespDTO.getUser_id()));
				if (knoteScanListRespDTO.getUser_nm() != null) knoteScanListRespDTO.setUser_nm(SafeDBUtil.getInstance().decrypt(knoteScanListRespDTO.getUser_nm()));
				if (knoteScanListRespDTO.getAplsht_imag_id() != null) knoteScanListRespDTO.setAplsht_imag_id(SafeDBUtil.getInstance().decrypt(knoteScanListRespDTO.getAplsht_imag_id()));
				// 2018.07.16 응답에서 제외
				if (knoteScanListRespDTO.getCntpnt_cd() != null) knoteScanListRespDTO.setCntpnt_cd(knoteScanListReqDTO.getIngr_cntpnt_cd());
//				if (knoteScanListRespDTO.getCntpnt_cd() != null) knoteScanListRespDTO.setCntpnt_cd(SafeDBUtil.getInstance().decrypt(knoteScanListRespDTO.getCntpnt_cd()));
//				if (knoteScanListRespDTO.getCntpnt_nm() != null) knoteScanListRespDTO.setCntpnt_nm(SafeDBUtil.getInstance().decrypt(knoteScanListRespDTO.getCntpnt_nm()));
				if (knoteScanListRespDTO.getFile_err_cd() != null) knoteScanListRespDTO.setFile_err_cd(SafeDBUtil.getInstance().decrypt(knoteScanListRespDTO.getFile_err_cd()));
				if (knoteScanListRespDTO.getRum() != null) knoteScanListRespDTO.setRum(SafeDBUtil.getInstance().decrypt(knoteScanListRespDTO.getRum()));
				if (knoteScanListRespDTO.getTotal_cnt() != null) knoteScanListRespDTO.setTotal_cnt(SafeDBUtil.getInstance().decrypt(knoteScanListRespDTO.getTotal_cnt()));
				if (knoteScanListRespDTO.getNfl_cust_idnt_no_ind_cd() != null) knoteScanListRespDTO.setNfl_cust_idnt_no_ind_cd(SafeDBUtil.getInstance().decrypt(knoteScanListRespDTO.getNfl_cust_idnt_no_ind_cd()));
				if (knoteScanListRespDTO.getNfl_use_cust_type_cd() != null) knoteScanListRespDTO.setNfl_use_cust_type_cd(SafeDBUtil.getInstance().decrypt(knoteScanListRespDTO.getNfl_use_cust_type_cd()));
				if (knoteScanListRespDTO.getNfl_cust_nm() != null) knoteScanListRespDTO.setNfl_cust_nm(SafeDBUtil.getInstance().decrypt(knoteScanListRespDTO.getNfl_cust_nm()));
				if (knoteScanListRespDTO.getNfl_cust_idnt_no() != null) knoteScanListRespDTO.setNfl_cust_idnt_no(SafeDBUtil.getInstance().decrypt(knoteScanListRespDTO.getNfl_cust_idnt_no()));
				if (knoteScanListRespDTO.getReal_evdn_data_ind() != null) knoteScanListRespDTO.setReal_evdn_data_ind(SafeDBUtil.getInstance().decrypt(knoteScanListRespDTO.getReal_evdn_data_ind()));
				if (knoteScanListRespDTO.getReal_cust_idnt_no() != null) knoteScanListRespDTO.setReal_cust_idnt_no(SafeDBUtil.getInstance().decrypt(knoteScanListRespDTO.getReal_cust_idnt_no()));
				if (knoteScanListRespDTO.getReal_issu_date() != null) knoteScanListRespDTO.setReal_issu_date(SafeDBUtil.getInstance().decrypt(knoteScanListRespDTO.getReal_issu_date()));
				if (knoteScanListRespDTO.getRegist_date() != null) knoteScanListRespDTO.setRegist_date(SafeDBUtil.getInstance().decrypt(knoteScanListRespDTO.getRegist_date()));
				if (knoteScanListRespDTO.getMobile_no() != null) knoteScanListRespDTO.setMobile_no(SafeDBUtil.getInstance().decrypt(knoteScanListRespDTO.getMobile_no()));
				
				// Cris 연동 대상 별도 관리
				switch (knoteScanListRespDTO.getReal_evdn_data_ind()) {
				case "DRIVE" :	// 운전면허증인 경우 (-> cris 연동 대상)
					// [작업필요] 임시 막음 (cris 연동 에러)
//					knoteScanListRespDTO.setDriveLicnsNo(knoteScanListRespDTO.getReal_cust_idnt_no());
					break;
				case "NAMEC" :	// 여권인 경우 (-> cris 연동 대상)
					knoteScanListRespDTO.setCustPassNo(knoteScanListRespDTO.getReal_cust_idnt_no());
					break;
				default :
					break;
				}

				arrayList.set(ii, knoteScanListRespDTO);
			}
			
			httpClient.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			ITLBusinessException.printErrorStack(e);
		}
		
		KnoteScanListRespVO knoteScanListRespVO = new KnoteScanListRespVO();
		knoteScanListRespVO.setKnoteScanListRespList(arrayList);
		return knoteScanListRespVO;
//		return arrayList;
	}
	
	

	/*
	 * 단순 K-Note 연동 테스트
	*/
	public ImageRequestOutVO testSendImageToKnote(ImageRequestInVO inVO) throws ITLBusinessException {
		
		logger.debug("(testSendImageToKnote) inVO:\n" + inVO.toString());
		
		// 응답객체
		ImageRequestOutVO outVO = new ImageRequestOutVO();
		MapUtil.copyDtoToDto(inVO.getTrtBaseInfoDTO(), outVO.getTrtBaseInfoDTO());
		
		// [작업필요] 개인정보 활용동의 연동 여부
		boolean isPrivate = false;
		if (inVO.getTrtBaseInfoDTO().getSrcId().equals("NNSB0100")) {
			isPrivate = true;
		}
		
		// 이미지 리스트 전송 대상 조회 (DocumentType이 A로 시작되는 건만 대상임. 나머지 이미지는 별도 보관용)
		List<ImageInfoDTO> imageInfoList = null;
		imageInfoList = imageService.getImageRegInfoForKnote(inVO.getTrtBaseInfoDTO().getOdrSeqNo());
		
		// 연동 대상이 없는 경우 에러처리.
		if ( imageInfoList == null || imageInfoList.size() == 0 ) {
			logger.debug("(sendImageToKnote) [Error] imageInfoList == null || imageInfoList.size()");
			outVO.getTrtErrInfoDTO().setResponseType("E");
			outVO.getTrtErrInfoDTO().setResponseCode("KKOS0001");
			outVO.getTrtErrInfoDTO().setResponseTitle("업무처리 오류");
			outVO.getTrtErrInfoDTO().setResponseBasc("서식지 연동 대상이 없습니다.");
			
			outVO.setImageInfoList(imageInfoList);
			
			logger.debug("outVO:\n" + outVO.toString());
			
			return outVO;
		}
		
		String apiKey = inVO.getTrtBaseInfoDTO().getApiKey();
		
		if (StringUtil.isEmpty(apiKey))	apiKey = ApiUtils.getApikey();
		
		// 개인정보 활용동의 연동인 경우 무조건 할당
		if (isPrivate)	apiKey = ApiUtils.getApikey();
		
		String beforeItemId = inVO.getTrtBaseInfoDTO().getItemId();
		String onlnAplshtSrlNo = inVO.getTrtBaseInfoDTO().getOnlnAplshtSrlNo();
		
		String DateFormat = "yyyyMMddHHmmss";
		String rsrvDt = StringUtil.isEmpty(inVO.getTrtBaseInfoDTO().getReceiptDt()) ? "" : inVO.getTrtBaseInfoDTO().getReceiptDt();
		
		
		// K-NOTE 연동 최종 완료 후 응답받는 서식지 연동 item Id
		String frmpapId = "";
		
		// 전체 K-Note 연동 개수만큼 순회. 매 연동마다, 최종 여부를 확인하기 위해 indexing 값을 같이 전달함.
		for ( int ii = 0 ; ii < imageInfoList.size() ; ii++ ) {
			
			logger.debug("\n");
			logger.debug("--------------[Start K-Note 연동: API_KEY:(" + apiKey + ") (" + ii + "/" + imageInfoList.size() + ") ]---------------------------------");
			try {
				
				ImageInfoDTO imageInfoDTO = imageInfoList.get(ii);
				imageInfoDTO.setApiKey(apiKey);
				imageInfoDTO.setBeforeItemId(beforeItemId);
				imageInfoDTO.setOnlnAplshtSrlNo(onlnAplshtSrlNo);
				imageInfoDTO.setRsrvDt(rsrvDt);
				imageInfoDTO.setPhoneUser(StringUtil.isEmpty(inVO.getNflCustNm()) ? inVO.getOcrInfoDTO().getCustNm() : inVO.getNflCustNm());
				
				frmpapId = fireToKnote(ii+1, imageInfoList.size(), imageInfoDTO, inVO);
			} catch (ITLBusinessException e) {
				
				// 연동 에러가 발생하면, 시도 건 까지 성공/실패 상태를 반영하고, 잔여건은 재시도 하지 않음. 호출UI로 에러 반환함.
				// 재시도시, API_KEY는 재 발행되어 모든 이미지는 다시 재전송 시도함.
				MapUtil.copyErrInfo(e, outVO.getTrtErrInfoDTO());
				outVO.setImageInfoList(imageInfoList);
				
				logger.debug("outVO:\n" + outVO.toString());
				
				return outVO;
				
			}
			logger.debug("\n");
			logger.debug("--------------[End   K-Note 연동: API_KEY:(" + apiKey + ") (" + ii + "/" + imageInfoList.size() + ") ]---------------------------------");
		}
		
		
		logger.debug("K-Note 연동 정상 종료 후, ItemID : [" + frmpapId + "] 상태를 모든 전송 대상 이미지의 DB 정보에 반영합니다.");
		// K-NOTE 연동 정상 처리 상태 반영. 마지막 전송에만 frmapId만 응답되어, 해당값을 다시 loop 하여 모든 대상 이미지에 반영함.
		for ( ImageInfoDTO imageInfoDTO : imageInfoList ) {
			imageInfoDTO.setKnoteIfYn("Y");
			imageInfoDTO.setKnoteIfFailMsg(null);
			
			imageInfoDTO.setApiKey(apiKey);
			imageInfoDTO.setBeforeItemId(beforeItemId);
			imageInfoDTO.setOnlnAplshtSrlNo(onlnAplshtSrlNo);
			imageInfoDTO.setRsrvDt(rsrvDt);
			imageInfoDTO.setFrmpapId(frmpapId);
		}
		
		// 이미지 연동 정보 리턴 세팅
		outVO.setImageInfoList(imageInfoList);
		
		// TrtBaseInfoDTO 에 K-Note 연동 완료 후 수신한 Item ID를 세팅 및
		// 생성한 API Key 세팅
		if (StringUtil.isEmpty(outVO.getTrtBaseInfoDTO().getItemId())) outVO.getTrtBaseInfoDTO().setItemId(frmpapId);
		if (StringUtil.isEmpty(outVO.getTrtBaseInfoDTO().getApiKey())) outVO.getTrtBaseInfoDTO().setApiKey(apiKey);
		
		return outVO;
	}


	/*
	 * 서식지 이미지 K-Note 연동
	*/
	public ImageRequestOutVO sendImageToKnote(ImageRequestInVO inVO) throws ITLBusinessException {
		
		logger.debug("(sendImageToKnote) inVO:\n" + inVO.toString());
		
		// 응답객체
		ImageRequestOutVO outVO = new ImageRequestOutVO();
		MapUtil.copyDtoToDto(inVO.getTrtBaseInfoDTO(), outVO.getTrtBaseInfoDTO());
		
		// 가상번호 로 연동 (필터에서 가상번호로 넘어옴)
//		crisServiceCaller.doCrisService(inVO, inVO.getClass());
		
		
		// 개인정보 활용동의 연동 또는 표준안내 연동 여부 체크 및 K-Note 연동 대상 조회
		//    - 개인정보 활용 동의인 경우 는 img_type : 00, 01 조회
		//    - 표준안내인 경우 는 img_type : 30
		//    - 그 외는 DocumentType이 A로 시작되는 건만 대상임. 나머지 이미지는 별도 보관용
		boolean isPrivate = false;		// 개인정보 활용 동의 연동
		boolean isStandard = false;		// 표준 안내 연동
		List<ImageInfoDTO> imageInfoList = null;	// K-Note 연동 대상 리스트
		
		Map<String, Object> reqMap = null;
		
		// srcId 기준으로 개인정보활욛옹의 sign, 표준안내 서식지 K-note 연동 여부 결정 
		switch (inVO.getTrtBaseInfoDTO().getSrcId() == null ? "" : inVO.getTrtBaseInfoDTO().getSrcId()) {
		case "NNSB0200" :	// 개통 사전체크
		case "NICG0200" :	// 기변 사전체크
			isPrivate = true;
			
			reqMap = new HashMap<String, Object>();
			reqMap.put("odrSeqNo", inVO.getTrtBaseInfoDTO().getOdrSeqNo());
			reqMap.put("sendCase", "P");
			
			imageInfoList = imageService.getImageRegInfoForKnoteCase(reqMap);
			break;
		case "NNSB0800" :	// 개통 표준안내
		case "NICG0800" :	// 개통 표준안내
			isStandard = true;
			
			reqMap = new HashMap<String, Object>();
			reqMap.put("odrSeqNo", inVO.getTrtBaseInfoDTO().getOdrSeqNo());
			reqMap.put("sendCase", "S");
			
			imageInfoList = imageService.getImageRegInfoForKnoteCase(reqMap);
			break;
		default :
			imageInfoList = imageService.getImageRegInfoForKnote(inVO.getTrtBaseInfoDTO().getOdrSeqNo());
			break;
		}

		logger.debug("(sendImageToKnote) isPrivage : " + isPrivate + ", isStandard : " + isStandard);
		
		// 연동 대상이 없는 경우 에러처리.
		if ( imageInfoList == null || imageInfoList.size() == 0 ) 
		{
			logger.debug("(sendImageToKnote) [Error] imageInfoList == null || imageInfoList.size()");
			outVO.getTrtErrInfoDTO().setResponseType("E");
			outVO.getTrtErrInfoDTO().setResponseCode("KKOS0001");
			outVO.getTrtErrInfoDTO().setResponseTitle("업무처리 오류");
			outVO.getTrtErrInfoDTO().setResponseBasc("서식지 연동 대상이 없습니다.");
			
			outVO.setImageInfoList(imageInfoList);
			
			logger.debug("outVO:\n" + outVO.toString());
			
			return outVO;
		}
		logger.debug("(sendImageToKnote) imageInfoList.size() : " + imageInfoList.size());

		
		// img_type = 20(서식지) 이 존재 하는지 체크 ( KOS 전송 호출 할 때만 체크 )
		//    - 조건 : 개인정보활용동의 연동이 아니고, 표준안내 전송이 아닌 경우
		if(!isPrivate && !isStandard && "KOS_TRNS".equals(inVO.getTrtBaseInfoDTO().getSrcId()))
		{	
			Map<String, Object> inMap = new HashMap<String, Object>();
			inMap.put("odrSeqNo", inVO.getTrtBaseInfoDTO().getOdrSeqNo());
			inMap.put("imgType", "20");
			
			
			Map<String, Object> outMap = imageInfoMapper.getImgInfoByType(inMap);
	
			Long cnt = (Long)outMap.get("cnt");
			
			// 서식지가 없는 경우
			if(cnt < 1)
			{
				
				outVO.getTrtErrInfoDTO().setResponseType("N");
				outVO.getTrtErrInfoDTO().setResponseCode("KKOS0001");
				outVO.getTrtErrInfoDTO().setResponseTitle("업무처리 오류");
				outVO.getTrtErrInfoDTO().setResponseBasc("서식지 연동 대상( img_type 20 )이 없습니다.");
				
				outVO.setImageInfoList(imageInfoList);
				
				logger.debug("outVO:\n" + outVO.toString());
				
				return outVO;
	
				
			}
		}
		
		// K-Note 전송 대상 및 작업 구분에 따라, K-Note 연동 또는 이미지 정보 리턴 (workFlag == "I")
		//  - 개인정보활용동의 sign, 표준안내 가 아니고, 건수 조회(workFlag == "R") 인 경우
		if (!isPrivate && !isStandard  && "R".equals(inVO.getWorkType())) {
			for (int ii=0; ii<imageInfoList.size(); ii++) {
				imageInfoList.get(ii).setFrmpapId(inVO.getTrtBaseInfoDTO().getItemId());
			}
			
			outVO.setImageInfoList(imageInfoList);
			return outVO;
		}
		
		// 연동 대상이 모두 성공한 경우 skip 처리. 응답은 성공으로. (개인정보활용동의 조건 별도)
		if (isPrivate) {
			Map<String, Object> getPriMap = new HashMap<String, Object>();
			getPriMap.put("odrSeqNo", inVO.getTrtBaseInfoDTO().getOdrSeqNo());
			getPriMap.put("userId", inVO.getTrtBaseInfoDTO().getUserId());
			getPriMap.put("privacyAgreeDt", DateUtil.toDateTimeString("yyyyMMdd"));
			
			Map<String, Object> resPriMap = imageService.getItemIdOfHist(getPriMap);
			
			if (resPriMap != null && resPriMap.get("itemId") != null) {
				outVO.setImageInfoList(imageInfoList);
				outVO.getTrtBaseInfoDTO().setItemId(imageInfoList.get(0).getFrmpapId());
				return outVO;
			}
		}
		else {
			logger.debug("(sendImageToKnote) imageInfoList.size() : " + imageInfoList.size());
			if ( imageInfoList.size() > 0 ) 
			{
				
				int successCnt = 0;
				for ( ImageInfoDTO imageInfoDTO : imageInfoList ) 
				{
					if ( "Y".equals(imageInfoDTO.getKnoteIfYn()) ) 
					{
						successCnt++;
					}
				}
				
				logger.debug("(sendImageToKnote) successCnt : " + successCnt);
				
				// k-note 전송 성공 건 기 존재
				if ( imageInfoList.size() == successCnt ) 
				{
					
					logger.debug("  ALL CLEAR... SKIP ALL CLEAR... SKIP ALL CLEAR... SKIP ALL CLEAR... SKIP");	
					
					// 모두 기 완료인 경우 imageInfoList 리턴
					outVO.setImageInfoList(imageInfoList);
					
					outVO.getTrtBaseInfoDTO().setItemId(imageInfoList.get(0).getFrmpapId());
					outVO.getTrtBaseInfoDTO().setApiKey(imageInfoList.get(0).getApiKey());
					outVO.getTrtBaseInfoDTO().setOnlnAplshtSrlNo(imageInfoList.get(0).getOnlnAplshtSrlNo());
					
					return outVO;

				}
			}
		}
		
		
		/*
		 * K-NOTE 연동을 위한 준비 작업
		 *   - 온라인일련번호 생성 (서식지 전송인 경우 - 개인정보활용동의, 표준안내 전송 제외)
		 *   - K-Note 전송 대상 관리 테이블 상태 값 초기화 (서식지 전송인 경우 - 개인정보활용동의, 표준안내 전송 제외)
		 *   - API Key 생성
		*/
		// 개인정보 활용동의 / 표준안내 연동이 아니면, 온라인일련번호 취득 및 예약일자 사용, K-Note 연동결과 상태값 초기화
		String onlnAplshtSrlNo = null;
		
		// 예약일자 현재 일자로 세팅
//		String DateFormat = "yyyyMMddHHmmss";
//		String rsrvDt = DateUtil.toDateTimeString(DateFormat);
		
		// 예약일자
		//  - 사전조회, 표준안내 연동인 경우 : trtBaseInfoDTO 에 등록된 예약일자 무시하고 blank(현재시간) 로 연동
		//  - 그외는 trtBaseInfoDTO 에 등록된 예약일자 사용하며, 없으면, blank(현재시간) 로 연동
		String rsrvDt = null;
		
		// 개인정보활용동의 / 표준안내 인 경우
		if (isPrivate || isStandard) {
			rsrvDt = "";
		}
		else {	// 개인정보활용동의 / 표준안내가 아닌 경우
			// 온라인 일련 번호 취득
			Map<String, Object> rtnMap = new HashMap<String, Object>();
			rtnMap = trtBaseInfoMapperDao.getApySeq();
			onlnAplshtSrlNo = ((Long)rtnMap.get("onlineApySeq")).toString();
			
			// 예약일자 없으면, 오늘일자로 세팅하여 K-Note 연동
//			rsrvDt = StringUtil.isEmpty(inVO.getTrtBaseInfoDTO().getReceiptDt()) ? DateUtil.toDateTimeString(DateFormat) : inVO.getTrtBaseInfoDTO().getReceiptDt();
			
			// 예약일자가 없는 경우 blank 처리
			rsrvDt = StringUtil.isEmpty(inVO.getTrtBaseInfoDTO().getReceiptDt()) ? "" : inVO.getTrtBaseInfoDTO().getReceiptDt();
			
			// K-Note 전송 대상 관리 테이블 상태 값 초기화 (서식지)
			ImageInfoDTO copyImageInfoDTO = new ImageInfoDTO();
			
			copyImageInfoDTO.setApiKey("");
			copyImageInfoDTO.setOnlnAplshtSrlNo("");
			copyImageInfoDTO.setBeforeItemId("");
			copyImageInfoDTO.setRsrvDt("");
			copyImageInfoDTO.setImgId(""); 
			
			imageService.updateKnoteIfStatus(null, 
											 null, 
											 null,
											 copyImageInfoDTO, 
											 inVO.getTrtBaseInfoDTO(),
											 "A"
											 ); 
		}
		
		// API key 취득
		String apiKey 	= ApiUtils.getApikey();
		
		// 합본을 위한 이전 Item ID 취득
		//  - 개인정보 활용동의인 경우 합본 안함
		String beforeItemId = null;
		if (isPrivate) {
			beforeItemId = "";
		}
		else {
			beforeItemId = inVO.getTrtBaseInfoDTO().getItemId() == null ? "" : inVO.getTrtBaseInfoDTO().getItemId();
		}
		
		
		// K-NOTE 연동 최종 완료 후 응답받는 서식지 연동 item Id
		//  - Before Item ID 로 초기화
		String frmpapId = beforeItemId;
		
		/*
		 * 조회한 K-note 연동 대상 K-Note 전송
		 *   - 전체 K-Note 연동 개수만큼 순회. 매 연동마다, 최종 여부를 확인하기 위해 indexing 값을 같이 전달함.
		*/
		for ( int ii = 0 ; ii < imageInfoList.size() ; ii++ ) 
		{
			
			logger.debug("\n");
			logger.debug("--------------[Start K-Note 연동: API_KEY:(" + apiKey + ") (" + (ii+1) + "/" + imageInfoList.size() + ") ]---------------------------------");
			try {
				
				ImageInfoDTO imageInfoDTO = imageInfoList.get(ii);
				imageInfoDTO.setApiKey(apiKey);
				imageInfoDTO.setBeforeItemId(beforeItemId);
				imageInfoDTO.setOnlnAplshtSrlNo(onlnAplshtSrlNo);
				imageInfoDTO.setRsrvDt(rsrvDt);
				imageInfoDTO.setPhoneUser(StringUtil.isEmpty(inVO.getNflCustNm()) ? inVO.getOcrInfoDTO().getCustNm() : inVO.getNflCustNm());
				
				frmpapId = fireToKnote(ii+1, imageInfoList.size(), imageInfoDTO, inVO);
			} catch (ITLBusinessException e) {
				
				// 연동 에러가 발생하면, 시도 건 까지 성공/실패 상태를 반영하고, 잔여건은 재시도 하지 않음. 호출UI로 에러 반환함.
				// 재시도시, API_KEY는 재 발행되어 모든 이미지는 다시 재전송 시도함.
				MapUtil.copyErrInfo(e, outVO.getTrtErrInfoDTO());
				outVO.setImageInfoList(imageInfoList);
				
				logger.debug("outVO:\n" + outVO.toString());
				
				return outVO;
				
			}
			logger.debug("\n");
			logger.debug("--------------[End   K-Note 연동: API_KEY:(" + apiKey + ") (" + (ii+1) + "/" + imageInfoList.size() + ") ]---------------------------------");
		}
		
		
		logger.debug("K-Note 연동 정상 종료 후, ItemID : [" + frmpapId + "] 상태를 모든 전송 대상 이미지의 DB 정보에 반영합니다.");
		// K-NOTE 연동 정상 처리 상태 반영. 마지막 전송에만 frmapId만 응답되어, 해당값을 다시 loop 하여 모든 대상 이미지에 반영함.
		for ( ImageInfoDTO imageInfoDTO : imageInfoList ) {
			imageInfoDTO.setKnoteIfYn("Y");
			imageInfoDTO.setKnoteIfFailMsg(null);
			
			imageInfoDTO.setApiKey(apiKey);
			imageInfoDTO.setBeforeItemId(beforeItemId);
			imageInfoDTO.setOnlnAplshtSrlNo(onlnAplshtSrlNo);
			imageInfoDTO.setRsrvDt(rsrvDt);
			imageInfoDTO.setFrmpapId(frmpapId);
			
			// 전송 대상 리스트에 KNOTE 연동 성공한 값(APIKEY, FrmpapId)를 DB에 반영도 하고, UI 응답에도 제공함. 
			//  - 전체 성공시 일괄 업데이트
			imageService.updateKnoteIfStatus("Y", 
											 null,
											 frmpapId,
											 imageInfoDTO, 
											 inVO.getTrtBaseInfoDTO(),
											 null
											 );
		}
		
		// 개인정보 활용동의 인 경우 추가로 이력 관리 (사용자별, 일 1회 이상 K-note 발송 방지용) 
		if (isPrivate) {
			Map<String, Object> setPriMap = new HashMap<String, Object>();
			setPriMap.put("odrSeqNo", inVO.getTrtBaseInfoDTO().getOdrSeqNo());
			setPriMap.put("userId", inVO.getTrtBaseInfoDTO().getUserId());
			setPriMap.put("privacyAgreeDt", DateUtil.toDateTimeString("yyyyMMdd"));
			setPriMap.put("itemId", frmpapId);
			
			imageService.insertHist(setPriMap);
		}

		
		
		// 이미지 연동 정보 리턴 세팅 (최초 조회한 정보)
		outVO.setImageInfoList(imageInfoList);
		
		// TrtBaseInfoDTO 에 K-Note 연동 완료 후 수신한 itemId 및 apiKey, onlnAplshtSrlNo 리턴
		outVO.getTrtBaseInfoDTO().setItemId(frmpapId);
		outVO.getTrtBaseInfoDTO().setApiKey(apiKey);
		outVO.getTrtBaseInfoDTO().setOnlnAplshtSrlNo(onlnAplshtSrlNo);
		
		//현재 세션에 있는 사용자보안정보를 trtBaseInfoDTO에서 삭제한다. (UI로 return하지 않음)
//		ITLStringUTIL.deleteTrtBaseInfoDto(outVO);
//		logger.debug("outVO:\n" + outVO.toString());
		
		return outVO;
	}


	/*
	 * 서식지 이미지 K-Note 연동
	*/
	public ImageRequestOutVO sendImageToKnoteAdd(ImageRequestInVO inVO) throws ITLBusinessException {
		
		logger.debug("(sendImageToKnoteAdd) inVO:\n" + inVO.toString());
		
		// 응답객체
		ImageRequestOutVO outVO = new ImageRequestOutVO();
		MapUtil.copyDtoToDto(inVO.getTrtBaseInfoDTO(), outVO.getTrtBaseInfoDTO());
		
		
		// 개인정보 활용동의 연동 또는 표준안내 연동 여부 체크 및 K-Note 연동 대상 조회
		//    - 개인정보 활용 동의인 경우 는 img_type : 00, 01 조회
		//    - 표준안내인 경우 는 img_type : 30
		//    - 그 외는 DocumentType이 A로 시작되는 건만 대상임. 나머지 이미지는 별도 보관용
		boolean isPrivate = false;		// 개인정보 활용 동의 연동
		boolean isStandard = false;		// 표준 안내 연동
		List<ImageInfoDTO> imageInfoList = null;	// K-Note 연동 대상 리스트
		int totSendImgCnt = 0;	// 전송대상 이미지 건수
		
		Map<String, Object> reqMap = null;
		
		// srcId 기준으로 개인정보활욛옹의 sign, 표준안내 서식지 K-note 연동 여부 결정 
		switch (inVO.getTrtBaseInfoDTO().getSrcId() == null ? "" : inVO.getTrtBaseInfoDTO().getSrcId()) {
		case "NNSB0200" :	// 개통 사전체크
		case "NICG0200" :	// 기변 사전체크
			isPrivate = true;
			
			reqMap = new HashMap<String, Object>();
			reqMap.put("odrSeqNo", inVO.getTrtBaseInfoDTO().getOdrSeqNo());
			reqMap.put("sendCase", "P");
			
			imageInfoList = imageService.getImageRegInfoForKnoteCase(reqMap);
			totSendImgCnt = imageInfoList == null ? 0 : imageInfoList.size();	// 이미지 전송 대상 총 건수를 이미지 목록 건수로 세팅
			break;
		case "NNSB0800" :	// 개통 표준안내
		case "NICG0800" :	// 개통 표준안내
			isStandard = true;
			
			reqMap = new HashMap<String, Object>();
			reqMap.put("odrSeqNo", inVO.getTrtBaseInfoDTO().getOdrSeqNo());
			reqMap.put("sendCase", "S");
			
			imageInfoList = imageService.getImageRegInfoForKnoteCase(reqMap);
			totSendImgCnt = imageInfoList == null ? 0 : imageInfoList.size();	// 이미지 전송 대상 총 건수를 이미지 목록 건수로 세팅
			break;
		default :
			imageInfoList = imageService.getImageRegInfoForKnote(inVO.getTrtBaseInfoDTO().getOdrSeqNo());
			totSendImgCnt = imageInfoList == null ? 0 : imageInfoList.size();	// 이미지 전송 대상 총 건수를 이미지 목록 건수로 세팅
			break;
		}

		logger.debug("(sendImageToKnoteAdd) isPrivage : " + isPrivate + ", isStandard : " + isStandard);
		
		// 연동 대상이 없는 경우 에러처리.
		if ( imageInfoList == null || imageInfoList.size() == 0 ) 
		{
			logger.debug("(sendImageToKnoteAdd) [Error] imageInfoList == null || imageInfoList.size()");
			outVO.getTrtErrInfoDTO().setResponseType("E");
			outVO.getTrtErrInfoDTO().setResponseCode("KKOS0001");
			outVO.getTrtErrInfoDTO().setResponseTitle("업무처리 오류");
			outVO.getTrtErrInfoDTO().setResponseBasc("서식지 연동 대상이 없습니다.");
			
			outVO.setImageInfoList(imageInfoList);
			
			logger.debug("outVO:\n" + outVO.toString());
			
			return outVO;
		}
		logger.debug("(sendImageToKnoteAdd) imageInfoList.size() : " + imageInfoList.size());

		
		// img_type = 20(서식지) 이 존재 하는지 체크 ( KOS 전송 호출 할 때만 체크 )
		//    - 조건 : 개인정보활용동의 연동이 아니고, 표준안내 전송이 아닌 경우
		if(!isPrivate && !isStandard && "KOS_TRNS".equals(inVO.getTrtBaseInfoDTO().getSrcId()))
		{	
			Map<String, Object> inMap = new HashMap<String, Object>();
			inMap.put("odrSeqNo", inVO.getTrtBaseInfoDTO().getOdrSeqNo());
			inMap.put("imgType", "20");
			
			
			Map<String, Object> outMap = imageInfoMapper.getImgInfoByType(inMap);
	
			Long cnt = (Long)outMap.get("cnt");
			
			// 서식지가 없는 경우
			if(cnt < 1)
			{
				
				outVO.getTrtErrInfoDTO().setResponseType("N");
				outVO.getTrtErrInfoDTO().setResponseCode("KKOS0001");
				outVO.getTrtErrInfoDTO().setResponseTitle("업무처리 오류");
				outVO.getTrtErrInfoDTO().setResponseBasc("서식지 연동 대상( img_type 20 )이 없습니다.");
				
				outVO.setImageInfoList(imageInfoList);
				
				logger.debug("outVO:\n" + outVO.toString());
				
				return outVO;
	
				
			}
		}
		
		// K-Note 전송 대상 및 작업 구분에 따라, K-Note 연동 또는 이미지 정보 리턴 (workFlag == "I")
		//  - 개인정보활용동의 sign, 표준안내 가 아니고, 건수 조회(workFlag == "R") 인 경우
		if (!isPrivate && !isStandard  && "R".equals(inVO.getWorkType())) {
			for (int ii=0; ii<imageInfoList.size(); ii++) {
				imageInfoList.get(ii).setFrmpapId(inVO.getTrtBaseInfoDTO().getItemId());
			}
			
			outVO.setImageInfoList(imageInfoList);
			return outVO;
		}
		
		// 연동 대상이 모두 성공한 경우 skip 처리. 응답은 성공으로. (개인정보활용동의 조건 별도)
		if (isPrivate) {
			Map<String, Object> getPriMap = new HashMap<String, Object>();
			getPriMap.put("odrSeqNo", inVO.getTrtBaseInfoDTO().getOdrSeqNo());
			getPriMap.put("userId", inVO.getTrtBaseInfoDTO().getUserId());
			getPriMap.put("privacyAgreeDt", DateUtil.toDateTimeString("yyyyMMdd"));
			
			Map<String, Object> resPriMap = imageService.getItemIdOfHist(getPriMap);
			
			if (resPriMap != null && resPriMap.get("itemId") != null) {
				outVO.setImageInfoList(imageInfoList);
				outVO.getTrtBaseInfoDTO().setItemId(imageInfoList.get(0).getFrmpapId());
				return outVO;
			}
		}
		else {
			logger.debug("(sendImageToKnoteAdd) imageInfoList.size() : " + imageInfoList.size());
			if ( imageInfoList.size() > 0 ) 
			{
				
				int successCnt = 0;
				for ( ImageInfoDTO imageInfoDTO : imageInfoList ) 
				{
					if ( "Y".equals(imageInfoDTO.getKnoteIfYn()) ) 
					{
						successCnt++;
					}
				}
				
				logger.debug("(sendImageToKnoteAdd) successCnt : " + successCnt);
				
				// k-note 전송 성공 건 기 존재
				if ( imageInfoList.size() == successCnt ) 
				{
					
					logger.debug("  ALL CLEAR... SKIP ALL CLEAR... SKIP ALL CLEAR... SKIP ALL CLEAR... SKIP");	
					
					// 모두 기 완료인 경우 imageInfoList 리턴
					outVO.setImageInfoList(imageInfoList);
					
					outVO.getTrtBaseInfoDTO().setItemId(imageInfoList.get(0).getFrmpapId());
					outVO.getTrtBaseInfoDTO().setApiKey(imageInfoList.get(0).getApiKey());
					outVO.getTrtBaseInfoDTO().setOnlnAplshtSrlNo(imageInfoList.get(0).getOnlnAplshtSrlNo());
					
					return outVO;

				}
				else {
					// 이미지 전송 대상 건수 계산 : 이미지 목록 총 건수 - 기 K-Note 연동 성공 건수
					totSendImgCnt = imageInfoList.size() - successCnt;
				}
			}
		}
		
		
		/*
		 * K-NOTE 연동을 위한 준비 작업
		 *   - 온라인일련번호 생성 (서식지 전송인 경우 - 개인정보활용동의, 표준안내 전송 제외)
		 *   - K-Note 전송 대상 관리 테이블 상태 값 초기화 (서식지 전송인 경우 - 개인정보활용동의, 표준안내 전송 제외)
		 *   - API Key 생성
		*/
		// 개인정보 활용동의 / 표준안내 연동이 아니면, 온라인일련번호 취득 및 예약일자 사용, K-Note 연동결과 상태값 초기화
		String onlnAplshtSrlNo = null;
		
		// 예약일자
		//  - 사전조회, 표준안내 연동인 경우 : trtBaseInfoDTO 에 등록된 예약일자 무시하고 blank(현재시간) 로 연동
		//  - 그외는 trtBaseInfoDTO 에 등록된 예약일자 사용하며, 없으면, blank(현재시간) 로 연동
		String rsrvDt = null;
		
		// 개인정보활용동의 / 표준안내 인 경우
		if (isPrivate || isStandard) {
			rsrvDt = "";
		}
		else {	// 개인정보활용동의 / 표준안내가 아닌 경우
			// 온라인 일련 번호 세팅 : 등록된 일련번호로 세팅
			for ( ImageInfoDTO imageInfoDTO : imageInfoList ) 
			{
				if ( ! StringUtils.isEmpty(imageInfoDTO.getOnlnAplshtSrlNo()) ) 
				{
					onlnAplshtSrlNo = imageInfoDTO.getOnlnAplshtSrlNo();
					break;
				}
			}
			
			// 온라인 일련 번호 세팅 : Null 인 경우 Sequence Table 로부터 취득
			if (onlnAplshtSrlNo == null) {
				Map<String, Object> rtnMap = new HashMap<String, Object>();
				rtnMap = trtBaseInfoMapperDao.getApySeq();
				onlnAplshtSrlNo = ((Long)rtnMap.get("onlineApySeq")).toString();
			}
			
			// 예약일자가 없는 경우 blank 처리
			rsrvDt = StringUtil.isEmpty(inVO.getTrtBaseInfoDTO().getReceiptDt()) ? "" : inVO.getTrtBaseInfoDTO().getReceiptDt();
		}
		
		// API key 취득
		String apiKey 	= ApiUtils.getApikey();
		
		// 합본을 위한 이전 Item ID 취득
		//  - 개인정보 활용동의인 경우 합본 안함
		String beforeItemId = null;
		if (isPrivate) {
			beforeItemId = "";
		}
		else {
			beforeItemId = inVO.getTrtBaseInfoDTO().getItemId() == null ? "" : inVO.getTrtBaseInfoDTO().getItemId();
		}
		
		
		// K-NOTE 연동 최종 완료 후 응답받는 서식지 연동 item Id
		//  - Before Item ID 로 초기화
		String frmpapId = beforeItemId;
		
		/*
		 * 조회한 K-note 연동 대상 K-Note 전송
		 *   - 전체 K-Note 연동 개수만큼 순회. 매 연동마다, 최종 여부를 확인하기 위해 indexing 값을 같이 전달함.
		*/
		int sendImgCnt = 0;
		for ( int ii = 0 ; ii < imageInfoList.size() ; ii++ ) 
		{
			
			logger.debug("\n");
			logger.debug("--------[Start K-Note 연동: API_KEY:(" + apiKey + ") (" + (ii+1) + "/" + imageInfoList.size() + ") ]---------------------------------");
			try {
				
				ImageInfoDTO imageInfoDTO = imageInfoList.get(ii);
				if ("Y".equals(imageInfoDTO.getKnoteIfYn())) {
					logger.debug("--------------[SKIP.... K-Note 연동- Already Sended Image (" + apiKey + ") : " + imageInfoDTO.getDocumentType() + " / " + imageInfoDTO.getImgId());
					continue;
				}
				imageInfoDTO.setApiKey(apiKey);
				imageInfoDTO.setBeforeItemId(beforeItemId);
				imageInfoDTO.setOnlnAplshtSrlNo(onlnAplshtSrlNo);
				imageInfoDTO.setRsrvDt(rsrvDt);
				imageInfoDTO.setPhoneUser(StringUtil.isEmpty(inVO.getNflCustNm()) ? inVO.getOcrInfoDTO().getCustNm() : inVO.getNflCustNm());
				
				sendImgCnt++;
				logger.debug("--------------[Sending.... K-Note 연동 (" + apiKey + ") : " + sendImgCnt + " / " + totSendImgCnt);
				
				frmpapId = fireToKnote(sendImgCnt, totSendImgCnt, imageInfoDTO, inVO);
			} catch (ITLBusinessException e) {
				
				// 연동 에러가 발생하면, 시도 건 까지 성공/실패 상태를 반영하고, 잔여건은 재시도 하지 않음. 호출UI로 에러 반환함.
				// 재시도시, API_KEY는 재 발행되어 모든 이미지는 다시 재전송 시도함.
				MapUtil.copyErrInfo(e, outVO.getTrtErrInfoDTO());
				outVO.setImageInfoList(imageInfoList);
				
				logger.debug("outVO:\n" + outVO.toString());
				
				return outVO;
				
			}
			logger.debug("\n");
			logger.debug("--------[End   K-Note 연동: API_KEY:(" + apiKey + ") (" + (ii+1) + "/" + imageInfoList.size() + "), frmpapId : " + frmpapId + " ]---------------------------------");
		}
		
		
		logger.debug("K-Note 연동 정상 종료 후, ItemID : [" + frmpapId + "] 상태를 모든 전송 대상 이미지의 DB 정보에 반영합니다.");
		// K-NOTE 연동 정상 처리 상태 반영. 마지막 전송에만 frmapId만 응답되어, 해당값을 다시 loop 하여 모든 대상 이미지에 반영함.
		for ( ImageInfoDTO imageInfoDTO : imageInfoList ) {
			if ("Y".equals(imageInfoDTO.getKnoteIfYn())) {
				logger.debug("....SKIP.... K-Note 연동 Success DB Update... already \"Y\" (" + imageInfoDTO.getDocumentType() + " / " + imageInfoDTO.getImgId());
				continue;
			}
			imageInfoDTO.setKnoteIfYn("Y");
			imageInfoDTO.setKnoteIfFailMsg(null);
			
			imageInfoDTO.setApiKey(apiKey);
			imageInfoDTO.setBeforeItemId(beforeItemId);
			imageInfoDTO.setOnlnAplshtSrlNo(onlnAplshtSrlNo);
			imageInfoDTO.setRsrvDt(rsrvDt);
			imageInfoDTO.setFrmpapId(frmpapId);
			
			// 전송 대상 리스트에 KNOTE 연동 성공한 값(APIKEY, FrmpapId)를 DB에 반영도 하고, UI 응답에도 제공함. 
			//  - 전체 성공시 일괄 업데이트
			imageService.updateKnoteIfStatus("Y", 
											 null,
											 frmpapId,
											 imageInfoDTO, 
											 inVO.getTrtBaseInfoDTO(),
											 null
											 );
		}
		
		// 개인정보 활용동의 인 경우 추가로 이력 관리 (사용자별, 일 1회 이상 K-note 발송 방지용) 
		if (isPrivate) {
			Map<String, Object> setPriMap = new HashMap<String, Object>();
			setPriMap.put("odrSeqNo", inVO.getTrtBaseInfoDTO().getOdrSeqNo());
			setPriMap.put("userId", inVO.getTrtBaseInfoDTO().getUserId());
			setPriMap.put("privacyAgreeDt", DateUtil.toDateTimeString("yyyyMMdd"));
			setPriMap.put("itemId", frmpapId);
			
			imageService.insertHist(setPriMap);
		}

		
		
		// 이미지 연동 정보 리턴 세팅 (최초 조회한 정보)
		outVO.setImageInfoList(imageInfoList);
		
		// TrtBaseInfoDTO 에 K-Note 연동 완료 후 수신한 itemId 및 apiKey, onlnAplshtSrlNo 리턴
		outVO.getTrtBaseInfoDTO().setItemId(frmpapId);
		outVO.getTrtBaseInfoDTO().setApiKey(apiKey);
		outVO.getTrtBaseInfoDTO().setOnlnAplshtSrlNo(onlnAplshtSrlNo);
		
		//현재 세션에 있는 사용자보안정보를 trtBaseInfoDTO에서 삭제한다. (UI로 return하지 않음)
//		ITLStringUTIL.deleteTrtBaseInfoDto(outVO);
//		logger.debug("outVO:\n" + outVO.toString());
		
		return outVO;
	}
	
	
	/**
	 * <pre>
	 * Kkos User Id별 존재하는 모든 Image 데이터를 순차적으로 전송처리 하는 
	 * K-Note 연동 로직.
	 * </pre>
	 *
	 * @param i
	 * @param size
	 * @param imageInfoDTO
	 * @param inVO
	 * @throws ITLBusinessException
	 */
	public String fireToKnote(int jj, int size, final ImageInfoDTO imageInfoDTO, final ImageRequestInVO inVO) throws ITLBusinessException {
		logger.debug("(fireToKnote) Start .... ");
		
		try {
			CloseableHttpClient httpClient = getSSLHttpClient();
			//현재 세션에 있는 사용자보안정보를 조회하여  trtBaseInfoDTO에 세팅한다.
//			final ImageRequestInVO inVOcp = (ImageRequestInVO)ITLStringUTIL.getTrtBaseInfoDto(inVO);
			

			// 로그 출력용
			logger.debug("(fireToKnote) Start logger data Create .... ");
			StringBuilder requestBuffer = new StringBuilder();
			requestBuffer.append("request Entity : \n")
						 .append("  system_cd:")			.append( imageInfoDTO.getSystemCd())							.append("\n")
						 .append("  user_id:") 				.append( imageInfoDTO.getUserId())								.append("\n")
						 .append("  trtm_orgn_id:") 		.append( imageInfoDTO.getTrtmOrgnId())							.append("\n")
						 .append("  api_key:") 				.append( imageInfoDTO.getApiKey())								.append("\n")
						 .append("  lob_cd:") 				.append( imageInfoDTO.getLobCd())								.append("\n")
						 .append("  category_cd:") 			.append( imageInfoDTO.getCategoryCd())							.append("\n")
						 .append("  phone_user:") 			.append( imageInfoDTO.getPhoneUser())							.append("\n")
						 .append("  mobile_no:") 			.append( imageInfoDTO.getMobileNo() )							.append("\n")
						 .append("  img_cnt:") 				.append( String.valueOf(size))									.append("\n")
						 .append("  final_yn:") 			.append( (jj == size ? "Y" : "N"))								.append("\n")
						 .append("  mms_yn:") 				.append( imageInfoDTO.getMmsYn())								.append("\n")
						 .append("  mms_pub_msg_no:") 		.append( imageInfoDTO.getMmsPubMsgNo())							.append("\n")
						 .append("  onln_aplsht_srl_no:") 	.append( imageInfoDTO.getOnlnAplshtSrlNo())						.append("\n")
						 .append("  ingr_cntpnt_cd:") 		.append( imageInfoDTO.getIngrCntpntCd())						.append("\n")
						 .append("  before_item_id:") 		.append( imageInfoDTO.getBeforeItemId())						.append("\n")
						 .append("  document_type:") 		.append( imageInfoDTO.getDocumentType())						.append("\n")
						 .append("  rsrv_dt:") 				.append( imageInfoDTO.getRsrvDt())						.append("\n")
						 .append("  idcard_info:") 			.append( (
								 					  "A0002".equals(imageInfoDTO.getDocumentType())
												 ||   "A0016".equals(imageInfoDTO.getDocumentType())
													  ?
													  getIdcardInfo(imageInfoDTO, inVO)
													  :
													  ""))
						.append( "\n");
			logger.debug(requestBuffer.toString());
			
			
			// Image Binary 획득
			Map<String, Object> imageMap = imageService.get(imageInfoDTO.getImgId());
			
			ContentType contentType = ContentType.create("text/plain", MIME.UTF8_CHARSET);
			
			logger.debug("(fireToKnote) Start StringBody .... ");
			StringBody 	systemCd 		= new StringBody(SafeDBUtil.getInstance().encrypt(StringUtil.nullToBlank(imageInfoDTO.getSystemCd())), contentType);
			StringBody 	userId			= new StringBody(SafeDBUtil.getInstance().encrypt(StringUtil.nullToBlank(imageInfoDTO.getUserId())), contentType);
			StringBody 	trtmOrgnnId		= new StringBody(SafeDBUtil.getInstance().encrypt(StringUtil.nullToBlank(imageInfoDTO.getTrtmOrgnId())), contentType);
			StringBody 	apiKey			= new StringBody(SafeDBUtil.getInstance().encrypt(StringUtil.nullToBlank(imageInfoDTO.getApiKey())), contentType);
			StringBody 	lobCd			= new StringBody(SafeDBUtil.getInstance().encrypt(StringUtil.nullToBlank(imageInfoDTO.getLobCd())), contentType);
			StringBody 	categoryCd		= new StringBody(SafeDBUtil.getInstance().encrypt(StringUtil.nullToBlank(imageInfoDTO.getCategoryCd())), contentType);
			logger.debug("(fireToKnote) Start StringBody before phoneUser.... ");
			StringBody 	phoneUser		= new StringBody(URLEncoder.encode(SafeDBUtil.getInstance().encrypt(
																											!StringUtil.isEmpty(imageInfoDTO.getPhoneUser()) ? imageInfoDTO.getPhoneUser() : inVO.getOcrInfoDTO().getCustNm()
																											), "UTF-8"), contentType);
			StringBody 	mobileNo		= new StringBody(SafeDBUtil.getInstance().encrypt(StringUtil.nullToBlank(imageInfoDTO.getMobileNo())), contentType);
			StringBody 	imgCnt			= new StringBody(SafeDBUtil.getInstance().encrypt(String.valueOf(size)), contentType);
			StringBody 	finalYn			= new StringBody(jj == size ? SafeDBUtil.getInstance().encrypt("Y")
																	 :
																	 SafeDBUtil.getInstance().encrypt("N"), contentType);
			StringBody 	mmsYn			= new StringBody(SafeDBUtil.getInstance().encrypt(StringUtil.nullToBlank(imageInfoDTO.getMmsYn())), contentType);
			StringBody 	mmsPubMsgNo		= new StringBody(SafeDBUtil.getInstance().encrypt(StringUtil.nullToBlank(imageInfoDTO.getMmsPubMsgNo())), contentType);
			logger.debug("(fireToKnote) Start StringBody before onlnAplshtSrlNo.... ");
			StringBody 	onlnAplshtSrlNo	= new StringBody(SafeDBUtil.getInstance().encrypt(StringUtil.nullToBlank(imageInfoDTO.getOnlnAplshtSrlNo())), contentType);
			StringBody 	ingrCntpntCd	= new StringBody(SafeDBUtil.getInstance().encrypt(StringUtil.nullToBlank(imageInfoDTO.getIngrCntpntCd())), contentType);
			StringBody 	beforeItemId	= new StringBody(SafeDBUtil.getInstance().encrypt(StringUtil.nullToBlank(imageInfoDTO.getBeforeItemId())), contentType);
			StringBody 	documentType	= new StringBody(SafeDBUtil.getInstance().encrypt(StringUtil.nullToBlank(imageInfoDTO.getDocumentType())), contentType);
			StringBody 	rsrvDt			= new StringBody(SafeDBUtil.getInstance().encrypt(StringUtil.nullToBlank(imageInfoDTO.getRsrvDt())), contentType);
			StringBody 	idcardInfo		= new StringBody("A0002".equals(imageInfoDTO.getDocumentType())
														 ||
														 "A0016".equals(imageInfoDTO.getDocumentType())
														 ?
														   SafeDBUtil.getInstance().encrypt(getIdcardInfo(imageInfoDTO, inVO))
														   :
														   SafeDBUtil.getInstance().encrypt(""), contentType);
			
			// K-Note 전송 정보 생성
			logger.debug("(fireToKnote) Start StringBody before MultipartEntityBuilder.... ");
			HttpEntity data = MultipartEntityBuilder.create()
								.setCharset(Charset.forName("UTF-8"))
								.setMode(HttpMultipartMode.BROWSER_COMPATIBLE)
								.addPart("system_cd"				, systemCd)					// 시스템 코드 : AppleStore
								.addPart("user_id"					, userId)					// 사용자ID
								.addPart("trtm_orgn_id"				, trtmOrgnnId)				// 서식지 처리조직
								.addPart("api_key"					, apiKey)					// API 키
								.addPart("lob_cd"					, lobCd)					// LOB 코드
								.addPart("category_cd"				, categoryCd)				// Category 코드
								.addPart("phone_user"				, phoneUser)				// 고객명
								.addPart("mobile_no"				, mobileNo)					// 고객연락처
								.addPart("img_cnt"					, imgCnt)					// 총 이미지 장 수
								.addPart("final_yn"					, finalYn)					// 최종 호출 여부
								.addPart("mms_yn"					, mmsYn)					// MMS 전송 여부
								.addPart("mms_pub_msg_no"			, mmsPubMsgNo)				// MMS공식메세지SEQ
								.addPart("onln_aplsht_srl_no"		, onlnAplshtSrlNo)			// 온라인일련번호
								.addPart("ingr_cntpnt_cd"			, ingrCntpntCd)				// 유입접점코드
								.addPart("before_item_id"			, beforeItemId)				// 이전서식지ID
								.addPart("document_type"			, documentType)				// 문서유형
								.addPart("idcard_info"				, idcardInfo)				// 신분증,소매신분 일때만 스캔 정보 전송
								.addPart("rsrv_dt"					, rsrvDt)				// 예약 일자
								.addBinaryBody("image", (byte[])imageMap.get("imgBinary"), ContentType.DEFAULT_BINARY, imageInfoDTO.getImgFileName())
								.build();
			
			
			// K-Note Request 객체 생성
			logger.debug("(fireToKnote) Start StringBody before RequestBuilder.... ");
			
			HttpUriRequest request = RequestBuilder
								.post(knoteUri)
								.setEntity(data)
								.build();
			
			logger.debug("Executing request : " + request.getRequestLine());
			
			// K-Note Response 객체 생성(Interface 직접 구현체 생성)
			ResponseHandler<String> responseHandler = new ResponseHandler<String>()
			{
				
				@Override
				public String handleResponse(HttpResponse httpResponse) throws ClientProtocolException, IOException
				{
					
					
					String content = null;
					int statusCode = httpResponse.getStatusLine().getStatusCode();
					if ( statusCode >= 200 && statusCode < 300 ) 
					{
						
						// HTTP 응답이 2XX 로 정상이지만, 값을 확인하여 기대하지 않은 경우 오류 처리 함.
						HttpEntity entity = httpResponse.getEntity();
						if ( entity != null ) {
							
							// 응답값을 임시적으로 JSON Format --> Object로 변환함.
							content = EntityUtils.toString(entity, "UTF-8");
							KnoteIfResponseDTO responseDTO = new ObjectMapper().readValue(content, KnoteIfResponseDTO.class);
							
							// SafeDB 복호화
							decryptKnoteIfResponseDTO(responseDTO);
							logger.debug("responseDTO:\n" + responseDTO.toString());
							
							
							// rst_code(응답코드)가 "00" 정상이 아닌 나머지는 오류로 처리
							if ( !"00".equals(responseDTO.getRst_code()) ) 
							{
								
								imageInfoDTO.setKnoteIfYn("N");
								imageInfoDTO.setKnoteIfFailMsg(responseDTO.getRst_msg());
								
								imageService.updateKnoteIfStatus("N", 
																 responseDTO.getRst_msg(), 
																 null,
																 imageInfoDTO, 
																 inVO.getTrtBaseInfoDTO(),
																 null
																 );
								
								logger.error("응답값이 정상으로 반환되지 않았습니다. imgId:" + imageInfoDTO.getImgId());
								throw new ClientProtocolException("응답값이 정상으로 반환되지 않았습니다.");
							}
							// MMS 전송이 Y or M 이고, send_mms_rslt_cd(MMS전송 결과코드)가 "10000" 정상이 아닌 나머지는 오류로 처리
							// MMS 전송이 N 인 경우, send_mms_rslt_cd는 NULL 이면 정상
							if ( !"10000".equals(responseDTO.getSend_mms_rslt_cd()) && ("Y".equals(imageInfoDTO.getMmsYn()) || "M".equals(imageInfoDTO.getMmsYn()))  )  
							{
								
								imageInfoDTO.setKnoteIfYn("N");
								imageInfoDTO.setKnoteIfFailMsg(responseDTO.getRst_msg());
								
								imageService.updateKnoteIfStatus("N", 
										 						 responseDTO.getRst_msg(),
										 						 null,
										 						 imageInfoDTO,
										 					 	 inVO.getTrtBaseInfoDTO(),
										 					 	 null
										 					 	 );
								
								logger.error("응답값(MMS전송 결과코드)이 정상으로 반환되지 않았습니다. imgId:" + imageInfoDTO.getImgId());
								throw new ClientProtocolException("응답값(MMS전송 결과코드)이 정상으로 반환되지 않았습니다.");
							}
						}
						
						return content;
					} 
					// HTTP 응답이 2xx 가 아닌경우, HTTP Error Code와 "Unexpected response status" 에러 문장을 포함하여, DB에 에러내용을 반영하고, ITLException을 발행함.
					else 
					{
						
						logger.error("Unexpected response status:" + statusCode);
						
						imageInfoDTO.setKnoteIfYn("N");
						imageInfoDTO.setKnoteIfFailMsg("Unexpected response status:" + statusCode);
						
						imageService.updateKnoteIfStatus("N", 
														 "Unexpected response status:" + statusCode,
														 null,
														 imageInfoDTO,
														 inVO.getTrtBaseInfoDTO(),
														 null
														 );
						
						logger.error("HTTP 200 이외의 오류가 발생되었습니다. HttpStatus:" + statusCode);
						throw new ClientProtocolException("HTTP 200 이외의 오류가 발생되었습니다. HttpStatus:" + statusCode);
					}
				}
			};
			
			// K-Note 응답 값.
			String responseBody = httpClient.execute(request, responseHandler);
			KnoteIfResponseDTO responseDTO = new ObjectMapper().readValue(responseBody, KnoteIfResponseDTO.class);
			
			// SafeDB 복호화
			decryptKnoteIfResponseDTO(responseDTO);
			
			// 개별 처리 건이 성공하면, APIKEY를 반영함. FrmpapID는 모든 항목이 완료 될 경우 최종 값에서 얻어서 일괄 반영함.
			//  - 개별 성공은 관리하지 않고, 전체 완료시 에만 성공 관리
//			imageInfoDTO.setKnoteIfYn("Y");
//			imageInfoDTO.setKnoteIfFailMsg(null);
//			imageService.updateKnoteIfStatus("Y", 
//											 null,
//											 responseDTO.getItem_id(),
//											 imageInfoDTO, 
//											 inVO.getTrtBaseInfoDTO());
//			logger.debug("responseBody:\n" + responseDTO.toString());
			
			return responseDTO.getItem_id();
								
		} catch (IOException e)
		{
			logger.error("K-NOTE 연동 중 시스템 오류가 발생되었습니다. imgId:" + imageInfoDTO.getImgId());
			throw new ITLBusinessException(ITLKeyUTIL.ITL_RESPONSE_TYPE_SYSERR, "KKOS0001", "시스템 오류", "K-NOTE 연동 중 시스템 오류가 발생되었습니다.", e.getMessage());
		}
	}
	
	/**
	 * <pre>
	 * 신분증 스캔정보 XML 문자화
	 * </pre>
	 *
	 * @param imageInfoDTO
	 * @param inVO
	 * @return
	 */
	private String getIdcardInfo(final ImageInfoDTO imageInfoDTO, final ImageRequestInVO inVO) {
		
		StringBuilder buffer = new StringBuilder();
		buffer.append("<idcard_info>").append(System.lineSeparator());
			buffer.append("  ").append("<scan_info>")		.append(System.lineSeparator());
				buffer.append("    ").append("<scan_type>")			.append(imageInfoDTO.getScanType())						.append("</scan_type>")			.append(System.lineSeparator());
				buffer.append("    ").append("<uvirtr>")			.append(imageInfoDTO.getUvirtr())						.append("</uvirtr>")			.append(System.lineSeparator());
				buffer.append("    ").append("<change_reason>")		.append(imageInfoDTO.getChangeReason())					.append("</change_reason>")		.append(System.lineSeparator());
				buffer.append("    ").append("<excp_code>")			.append(imageInfoDTO.getExcpCode())						.append("</excp_code>")			.append(System.lineSeparator());
				buffer.append("    ").append("<id_card_type>")		.append(imageInfoDTO.getIdCardType())					.append("</id_card_type>")		.append(System.lineSeparator());
				buffer.append("    ").append("<id_card_type2>")		.append(imageInfoDTO.getIdCardType2())					.append("</id_card_type2>")		.append(System.lineSeparator());
				buffer.append("    ").append("<sn>")				.append(imageInfoDTO.getSn())							.append("</sn>")				.append(System.lineSeparator());
				buffer.append("    ").append("<nstrd_reason_cd>")	.append(imageInfoDTO.getNstrdReasonCd())				.append("</nstrd_reason_cd>")	.append(System.lineSeparator());
			buffer.append("  ").append("</scan_info>")		.append(System.lineSeparator());
			buffer.append("  ").append("<ocr_info>")		.append(System.lineSeparator());
				buffer.append("    ").append("<real_cust_nm>")		.append(inVO.getOcrInfoDTO().getCustNm())				.append("</real_cust_nm>")				.append(System.lineSeparator());
				buffer.append("    ").append("<real_cust_idnt_no>")	.append(inVO.getOcrInfoDTO().getRealCustIdntNo())			.append("</real_cust_idnt_no>")			.append(System.lineSeparator());
				buffer.append("    ").append("<real_agnc_cust_idnt_no>").append(inVO.getOcrInfoDTO().getRealAgncCustIdntNo())	.append("</real_agnc_cust_idnt_no>")	.append(System.lineSeparator());
				buffer.append("    ").append("<real_issu_date>")	.append(inVO.getOcrInfoDTO().getIssDateVal())			.append("</real_issu_date>")				.append(System.lineSeparator());
			buffer.append("  ").append("</ocr_info>")		.append(System.lineSeparator());
		buffer.append("</idcard_info>").append(System.lineSeparator());
		
		return buffer.toString();
	}
	

	/**
	 * <pre>
	 * Knote로 부터 연동된 암호화 값 --> 복호화 수행
	 * </pre>
	 *
	 * @param responseDTO
	 */
	private void decryptKnoteIfResponseDTO(KnoteIfResponseDTO responseDTO) {
		
		String itemId 			= SafeDBUtil.getInstance().decrypt(responseDTO.getItem_id());
		String apiKey			= SafeDBUtil.getInstance().decrypt(responseDTO.getApi_key());
		String sendMmsRsrltCd 	= SafeDBUtil.getInstance().decrypt(responseDTO.getSend_mms_rslt_cd());
		String rstCode 			= SafeDBUtil.getInstance().decrypt(responseDTO.getRst_code());
		String rstMsg			= SafeDBUtil.getInstance().decrypt(responseDTO.getRst_msg());
		
		responseDTO.setItem_id(itemId);
		responseDTO.setApi_key(apiKey);
		responseDTO.setSend_mms_rslt_cd(sendMmsRsrltCd);
		responseDTO.setRst_code(rstCode);
		responseDTO.setRst_msg(rstMsg);
	}
	

	/**
	 * <pre>
	 * Knote로 부터 연동된 암호화 값 --> 복호화 수행
	 * </pre>
	 *
	 * @param responseDTO
	 */
	private CloseableHttpClient getSSLHttpClient() throws ITLBusinessException {
		try {
			SSLContextBuilder sslContextBuilder = new SSLContextBuilder();
			sslContextBuilder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
			
			// SSL 과 실 도메인 일치 여부 체크 SKIP
			HostnameVerifier hostnameVerifierAllowAll = new HostnameVerifier() {
				@Override
				public boolean verify(String hostname, SSLSession sslSession) {
					// TODO Auto-generated method stub
					return true;
				}
			};
			
			SSLConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory (sslContextBuilder.build(), hostnameVerifierAllowAll);
			return HttpClientBuilder.create().setSSLSocketFactory(sslSocketFactory).build();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			ITLBusinessException.printErrorStack(e);
			throw new ITLBusinessException(ITLKeyUTIL.ITL_RESPONSE_TYPE_SYSERR, "KKOS0001", "시스템 오류", "K-NOTE 연동 중 시스템 오류가 발생되었습니다.", e.getMessage());
		}
	}
	
	
	


	
	/*
	 * 서식지 k-note 재처리
	*/
	public ImageRequestOutVO rePrcsSendImageToKnote(ImageRequestInVO inVO) throws ITLBusinessException {
		
		logger.debug("서식지 재처리 인풋 ::::   " + inVO.toString());
		
		
		
		// 응답객체
		ImageRequestOutVO outVO = new ImageRequestOutVO();
		MapUtil.copyDtoToDto(inVO.getTrtBaseInfoDTO(), outVO.getTrtBaseInfoDTO());
		

		List<ImageInfoDTO> imageInfoList = null;	// K-Note 연동 대상 리스트
		
		imageInfoList = imageService.getImageRegInfoForKnote(inVO.getTrtBaseInfoDTO().getOdrSeqNo());
		
		
		
		logger.debug("재처리 조회 건 수   :::::   " + imageInfoList.size());

		
		
		String apiKey 			= "";
		String beforeItemId 	= "";
		String frmpapId 		= "";
		String onlnAplshtSrlNo 	= "";
		String rsrvDt 			= "";
		
		
		// apiKey 는 최초, 합본 상관없이 신규 생성하여 보냄.
		apiKey 	= ApiUtils.getApikey();
		
		
		// onlnAplshtSrlNo 는 최초, 합본 상관없이 신규 생성하여 보냄.
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		rtnMap = trtBaseInfoMapperDao.getApySeq();
		onlnAplshtSrlNo = ((Long)rtnMap.get("onlineApySeq")).toString();
		
		int rePrcsCnt = 0;
		
		if(!StringUtil.isEmpty(inVO.getTrtBaseInfoDTO().getItemId()))
		{
			
			
			beforeItemId	= inVO.getTrtBaseInfoDTO().getItemId();
			frmpapId		= inVO.getTrtBaseInfoDTO().getItemId();
			rsrvDt			= inVO.getTrtBaseInfoDTO().getReceiptDt();
			
			for(int i = 0 ; i < imageInfoList.size(); i++)
			{
				
				
				
				
				// 총 건수 빼기 위 함.
				if(!"Y".equals(imageInfoList.get(i).getKnoteIfYn()))
				{
					
					rePrcsCnt ++ ;
				}	
                
                // 최초에 실패가 나도 N 으로 업데이트하면서 해당 정보들이 업데이트 된다. 그러므로 존재 함.
				if(!StringUtil.isEmpty(imageInfoList.get(i).getApiKey()))
				{
					
					/*
					beforeItemId	= inVO.getTrtBaseInfoDTO().getItemId();
					frmpapId		= inVO.getTrtBaseInfoDTO().getItemId();
					onlnAplshtSrlNo	= imageInfoList.get(i).getOnlnAplshtSrlNo();
					rsrvDt			= inVO.getTrtBaseInfoDTO().getReceiptDt();
					*/	
				}	
				

			}
			
			
			
			ImageInfoDTO copyImageInfoDTO = new ImageInfoDTO();
			
			copyImageInfoDTO.setApiKey("");
			copyImageInfoDTO.setOnlnAplshtSrlNo("");
			copyImageInfoDTO.setBeforeItemId(beforeItemId);
			copyImageInfoDTO.setRsrvDt("");
			copyImageInfoDTO.setImgId(""); 
			
			imageService.updateKnoteIfStatus(null, 
											 null, 
											 frmpapId,
											 copyImageInfoDTO, 
											 inVO.getTrtBaseInfoDTO(),
											 "A"
											 ); 
			
			logger.debug("재처리 대상 건 수  ::::::   " + rePrcsCnt);
			
			
			for(int ii = 0 ; ii < imageInfoList.size(); ii++)
			{
				
			
				//if(!"Y".equals(imageInfoList.get(ii).getKnoteIfYn()))
				//{
					
					try 
					{
						
						ImageInfoDTO imageInfoDTO = imageInfoList.get(ii);
						imageInfoDTO.setApiKey(apiKey);
						imageInfoDTO.setFrmpapId(frmpapId);
						imageInfoDTO.setBeforeItemId(beforeItemId);
						imageInfoDTO.setOnlnAplshtSrlNo(onlnAplshtSrlNo);
						imageInfoDTO.setRsrvDt(rsrvDt);
						imageInfoDTO.setPhoneUser(StringUtil.isEmpty(inVO.getNflCustNm()) ? inVO.getOcrInfoDTO().getCustNm() : inVO.getNflCustNm());
						
						//fireToKnote(ii+1, rePrcsCnt, imageInfoDTO, inVO);
						fireToKnote(ii+1, imageInfoList.size(), imageInfoDTO, inVO);
						
						
						
						
						
						// 성공은 건당 업데이트. 실패하면 k-note 전송 후 실패로 업데이트 처리 함.
						// itemId 가 기존재 하므로, 기존 itemId 로 업데이트 한다. 
						imageService.updateKnoteIfStatus("Y", 
														 null,
														 frmpapId,
														 imageInfoDTO, 
														 inVO.getTrtBaseInfoDTO(),
														 null
														 );
						
						
					} catch (ITLBusinessException e) 
					{
						
						outVO.getTrtErrInfoDTO().setResponseType("S");
						outVO.getTrtErrInfoDTO().setResponseBasc("재처리 중 오류");
						
						outVO.setImageInfoList(imageInfoList);
						
						logger.debug("재처리 오류 ::::  " + outVO.toString());
						
						return outVO;
						
					}
				//}
			}
			
			
			
			
		}else
		{
			
			// 재처리 때는 여기로 들어오면 안된다.  
			// 혹시라도 들어오는 case 발생하게 되면,  trtBaseInfo 에 itemId 업데이트 필요 함.
			
			beforeItemId 	= "";
			frmpapId		= "";
			
			
			
			rsrvDt = inVO.getTrtBaseInfoDTO().getReceiptDt();
			
			
			
			for(int ii = 0 ; ii < imageInfoList.size(); ii++)
			{
					
				try 
				{
					
					ImageInfoDTO imageInfoDTO = imageInfoList.get(ii);
					imageInfoDTO.setApiKey(apiKey);
					imageInfoDTO.setBeforeItemId(beforeItemId);
					imageInfoDTO.setOnlnAplshtSrlNo(onlnAplshtSrlNo);
					imageInfoDTO.setRsrvDt(rsrvDt);
					imageInfoDTO.setPhoneUser(StringUtil.isEmpty(inVO.getNflCustNm()) ? inVO.getOcrInfoDTO().getCustNm() : inVO.getNflCustNm());
					
					frmpapId = fireToKnote(ii+1, imageInfoList.size(), imageInfoDTO, inVO);
					
					
					
					
					// 성공은 건당 업데이트. 실패하면 k-note 전송 후 실패로 업데이트 처리 함.
					// itemId 가 최종처리 후 리턴 되므로, 건당 업데이트 때는 안한다. 최종적으로 업데이트 함. 
					imageService.updateKnoteIfStatus("Y", 
													 null,
													 null,
													 imageInfoDTO, 
													 inVO.getTrtBaseInfoDTO(),
													 null
													 );
					
					
				} catch (ITLBusinessException e) 
				{
					
					outVO.getTrtErrInfoDTO().setResponseType("S");
					outVO.getTrtErrInfoDTO().setResponseBasc("재처리 중 오류");
					
					outVO.setImageInfoList(imageInfoList);
					
					logger.debug("재처리 오류 ::::  " + outVO.toString());
					
					return outVO;
					
				}
				
			}
			
			
			// 모두 성공 후 최종적으로 리턴받은 frmpapId 로 전체 업데이트 한다.
			for ( ImageInfoDTO imageInfoDTO : imageInfoList ) 
			{
				imageInfoDTO.setKnoteIfYn("Y");
				imageInfoDTO.setKnoteIfFailMsg(null);
				
				imageInfoDTO.setApiKey(apiKey);
				imageInfoDTO.setBeforeItemId(frmpapId);
				imageInfoDTO.setOnlnAplshtSrlNo(onlnAplshtSrlNo);
				imageInfoDTO.setRsrvDt(rsrvDt);
				imageInfoDTO.setFrmpapId(frmpapId);
				
				
				imageService.updateKnoteIfStatus("Y", 
												 null,
												 frmpapId,
												 imageInfoDTO, 
												 inVO.getTrtBaseInfoDTO(),
												 null
												 );
			}	
			
			
		}	
			


		

		
		outVO.getTrtErrInfoDTO().setResponseType("I");
		
		outVO.getTrtBaseInfoDTO().setItemId(frmpapId);
		outVO.getTrtBaseInfoDTO().setApiKey(apiKey);
		outVO.getTrtBaseInfoDTO().setOnlnAplshtSrlNo(onlnAplshtSrlNo);
		
		
		logger.debug("재처리 out   111111111111111   " +  outVO.getTrtErrInfoDTO().toString());
		logger.debug("재처리 out   222222222222222   " +  outVO.getTrtBaseInfoDTO().toString());

		return outVO;
	}
	
	
	
	
}
