package com.henc.cdrs.partners.info.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.henc.cdrs.common.web.BaseController;
import com.henc.cdrs.partners.info.model.Partner;
import com.henc.cdrs.partners.info.model.PrtnConInfo;
import com.henc.cdrs.partners.info.service.PartnerInfoService;
import com.henc.cdrs.sysm.sec.auth.model.UserDetail;
import com.henc.dream.util.CamelCaseMap;

@Controller
@RequestMapping("/partners/info")
public class PartnerInfoController extends BaseController {

    @Autowired
    private PartnerInfoService partnerInfoService;

    @Autowired
    HttpServletRequest request;

    /* 협력사 관리 화면 */
    @GetMapping("/")
    public String list(Model model) {
        return "views/partners/info/info";
    }

    /* 협력사 정보 조회  */
    @GetMapping("/gridList")
    public @ResponseBody
    List<CamelCaseMap> gridList(Partner partner) {
        UserDetail userDetail = getUserContext(request);
        String deptCd = userDetail.getDeptCd();
        partner.setDeptCd(deptCd);
        return partnerInfoService.selectCoprcpInfoGrdList(partner);
    }

    /* 협력사 정보 등록 저장 */
    @PostMapping(value = "/gridSave")
    public @ResponseBody Partner gridSave(Partner partner) {
        partnerInfoService.savePartnerInfoList(partner.getPartners(), getUserContext(request).getDeptCd());
        return partner;
    }


    /* 협력사 계약 현황 팝업 화면 */
    @GetMapping({"/prtnConListModal", "/prtnConListModal/{searchCorpKorNm}"})
    public String prtnConListModal(@PathVariable(required = false) String searchCorpKorNm, Model model) {
        return "views/partners/info/prtnConListModal";
    }

    /* 협력사 계약 현황 팝업 조회 */
    @GetMapping(value = "/gridPrtnConPopList")
    public @ResponseBody List<CamelCaseMap> gridPrtnPopList(PrtnConInfo prtnConInfo) {
        UserDetail userDetail = getUserContext(request);
        String deptCd = userDetail.getDeptCd();
        prtnConInfo.setDeptCd(deptCd);
        return partnerInfoService.selectprtnConListPopup(prtnConInfo);
    }

    /* 협력사 계약 현황 팝업 화면 */
    @GetMapping({"/siteConstListModal", "/siteConstListModal/{searchSiteConstNm}/{searchSiteConstDept}"})
    public String siteConstListModal(@PathVariable(required = false) String searchSiteConstNm, @PathVariable(required = false) String searchSiteConstDept, Model model) {
        return "views/partners/info/siteConstListModal";
    }

}
