package com.henc.cdrs.partners.applicant.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.henc.cdrs.common.web.BaseController;
import com.henc.cdrs.coprcp.repository.CoprcpInfoMapper;
import com.henc.cdrs.partners.applicant.model.Applicant;
import com.henc.cdrs.partners.applicant.service.PartnerApplicantService;
import com.henc.cdrs.partners.users.model.PrtnInfo;
import com.henc.cdrs.partners.users.service.PartnerUsersService;
import com.henc.cdrs.sysm.sec.auth.model.UserDetail;
import com.henc.dream.util.CamelCaseMap;

@Controller
@RequestMapping("/partners/applicant")
public class PartnerApplicantController extends BaseController {

    @Autowired
    private PartnerApplicantService partnerApplicantService;

    @Autowired
    CoprcpInfoMapper coprcpInfoMapper;

    @Autowired
    PartnerUsersService partnerUsersService;


    /* 협력사 관리 화면 */
    @GetMapping("/")
    public String list(Model model, HttpServletRequest request) {
        UserDetail userDetail = getUserContext(request);

        PrtnInfo prtnInfo = new PrtnInfo();
        prtnInfo.setDeptCd(userDetail.getDeptCd());
        prtnInfo.setCorpId(userDetail.getCoprcpNo());

        List<CamelCaseMap> prtnCdNm = partnerUsersService.selectPartnerPopList(prtnInfo);

        prtnInfo.setCoprcpNo(prtnCdNm.get(0).get("corpId").toString());
        prtnInfo.setCorpKor(prtnCdNm.get(0).get("corpKor").toString());

        model.addAttribute("userDetail",userDetail);
        model.addAttribute("prtnInfo",prtnInfo);
        return "views/partners/applicant/applicant";
    }

    /* 협력사 정보 조회  */
    @GetMapping("/gridList")
    public @ResponseBody
    List<CamelCaseMap> gridList(Applicant applicant, HttpServletRequest request) {
        UserDetail userDetail = getUserContext(request);
        String deptCd = userDetail.getDeptCd();
        applicant.setDeptCd(deptCd);
        return partnerApplicantService.selectApplicantGrdList(applicant);
    }

    /* 협력사 정보 등록 저장 */
    @PostMapping(value = "/gridSave")
    public @ResponseBody Applicant gridSave(Applicant applicant) {
        partnerApplicantService.saveApplicantList(applicant.getApplicants());
        return applicant;
    }

    @GetMapping(value = "/getComboTest")
    public @ResponseBody List<CamelCaseMap> getComboTest(Applicant applicant, HttpServletRequest request) {

        UserDetail userDetail = getUserContext(request);

        String bizHdofCd = userDetail.getBizhdofCd();
        String deptCd = userDetail.getDeptCd();

        applicant.setBizHdofCd(bizHdofCd);
        applicant.setDeptCd(deptCd);

        return partnerApplicantService.getComboTest(applicant);
    }

}
