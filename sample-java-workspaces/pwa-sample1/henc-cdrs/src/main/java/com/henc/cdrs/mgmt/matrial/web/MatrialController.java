package com.henc.cdrs.mgmt.matrial.web;

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
import com.henc.cdrs.mgmt.matrial.model.Matrial;
import com.henc.cdrs.mgmt.matrial.model.MatrialDetl;
import com.henc.cdrs.mgmt.matrial.service.MatrialService;
import com.henc.cdrs.sysm.sec.auth.model.UserDetail;
import com.henc.dream.util.CamelCaseMap;

@Controller
@RequestMapping("/mgmt/matrial")
public class MatrialController extends BaseController {

    @Autowired
    private MatrialService matrialService;

    @Autowired
    HttpServletRequest request;

    /* 자재등록 화면 */
    @GetMapping("/list")
    public String list(Model model) {
        return "views/mgmt/matrial/matrialList";
    }

    /* 자재등록 상단 리스트 */
    @GetMapping("/gridList")
    public @ResponseBody
    List<CamelCaseMap> gridList(Matrial matrial) {
        UserDetail userDetail = getUserContext(request);
        String bizhdofCd = userDetail.getBizhdofCd();
        matrial.setBizhdofCd(bizhdofCd);
        return matrialService.selectMgmtGrdList(matrial);
    }

    /* 자재등록 상단 저장 */
    @PostMapping(value = "/gridSave")
    public @ResponseBody Matrial gridSave(Matrial matrial) {
        matrialService.saveMatrialList(matrial.getMatrials(), getUserContext(request).getBizhdofCd());
        return matrial;
    }

    /* 자재등록 동의어 리스트 */
    @GetMapping(value = "/gridDetailList")
    public @ResponseBody
    List<CamelCaseMap> gridDetailList(MatrialDetl matrialdetl) {
        UserDetail userDetail = getUserContext(request);
        String bizhdofCd = userDetail.getBizhdofCd();
        matrialdetl.setBizhdofCd(bizhdofCd);
        return matrialService.selectMgmtGrdDetlList(matrialdetl);
    }

    /* 자재등록 동의어 저장 */
    @PostMapping(value = "/gridDetailSave")
    public @ResponseBody MatrialDetl gridDetailSave(MatrialDetl matrialdetl) {
        matrialService.saveMatrialDetail(matrialdetl.getMatrialDetls(), getUserContext(request).getBizhdofCd());
        return matrialdetl;
    }

    /* 자재등록 동의어 팝업 */
    @GetMapping(value = "/gridDetailSynPopList")
    public @ResponseBody List<CamelCaseMap> gridDetailSynPopList(Matrial matrial) {
        UserDetail userDetail = getUserContext(request);
        String bizhdofCd = userDetail.getBizhdofCd();
        matrial.setBizhdofCd(bizhdofCd);
        return matrialService.selectMgmtSynPoplList(matrial);
    }

    /* 자재등록 동의어 팝업 화면 */
    @GetMapping("/matrialListModal")
    public String matrialListModal(Model model) {
        return "views/mgmt/matrial/matrialListModal";
    }

}
