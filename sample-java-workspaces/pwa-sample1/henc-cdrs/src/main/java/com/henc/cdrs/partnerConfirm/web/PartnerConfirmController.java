package com.henc.cdrs.partnerConfirm.web;

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
import com.henc.cdrs.partnerConfirm.model.PartnerConfirm;
import com.henc.cdrs.partnerConfirm.service.PartnerConfirmService;
import com.henc.dream.util.CamelCaseMap;

@Controller
@RequestMapping("/partnerConfirm")
public class PartnerConfirmController extends BaseController {

    @Autowired
    private PartnerConfirmService partnerConfirmService;

    @Autowired
    HttpServletRequest request;

    @RequestMapping("/partnerReg")
    public String prtn(Model model, HttpServletRequest request) {
        return "views/partnerConfirm/partnerReg";
    }

    /* 협력사 사용자 정보 조회  */
    @GetMapping("/partnerInfoList")
    public @ResponseBody List<CamelCaseMap> partnerInfoList(PartnerConfirm partnerConfirm) {
        return partnerConfirmService.selectPartnerInfo(partnerConfirm);
    }

    /* 협력사 현장 리스트 조회 */
    @GetMapping("/partnerDeptList")
    public @ResponseBody List<CamelCaseMap> partnerDeptList(PartnerConfirm partnerConfirm) {
        return partnerConfirmService.selectPartnerDept(partnerConfirm);
    }



    /* 협력사 사용자,현장 저장 */
    @PostMapping(value = "/partnerSave")
    public @ResponseBody PartnerConfirm partnerSave(PartnerConfirm partnerConfirm) {
        partnerConfirmService.savePartner(partnerConfirm);
        return partnerConfirm;
    }

}
