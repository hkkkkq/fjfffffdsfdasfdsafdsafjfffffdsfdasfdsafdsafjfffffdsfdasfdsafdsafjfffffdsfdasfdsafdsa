package com.henc.cdrs.mgmt.dailySetup.web;

import java.io.IOException;
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
import com.henc.cdrs.mgmt.dailySetup.model.DsCnstQty;
import com.henc.cdrs.mgmt.dailySetup.model.DsCopyParams;
import com.henc.cdrs.mgmt.dailySetup.model.DsEquip;
import com.henc.cdrs.mgmt.dailySetup.model.DsMainBsnsSet;
import com.henc.cdrs.mgmt.dailySetup.model.DsMainCnstSet;
import com.henc.cdrs.mgmt.dailySetup.model.DsMainEqipSet;
import com.henc.cdrs.mgmt.dailySetup.model.DsMainMtilSet;
import com.henc.cdrs.mgmt.dailySetup.model.DsMatrial;
import com.henc.cdrs.mgmt.dailySetup.service.DailySetupService;
import com.henc.cdrs.sysm.sec.auth.model.UserDetail;
import com.henc.dream.util.CamelCaseMap;

import freemarker.template.TemplateException;

@Controller
@RequestMapping("/mgmt/dailySetup")
public class DailySetupController extends BaseController {

    @Autowired
    private DailySetupService dailySetupService;

    @Autowired
    HttpServletRequest request;

    /* 공사일보설정 화면 */
    @GetMapping("/")
    public String list(Model model) {
        return "views/mgmt/dailySetup/dailySetup";
    }

    /* 적용기간 리스트 */
    @GetMapping("/gridList")
    public @ResponseBody
    List<CamelCaseMap> gridList(DsMainBsnsSet dsMainBsnsSet) {
        UserDetail userDetail = getUserContext(request);
        String deptCd = userDetail.getDeptCd();
        dsMainBsnsSet.setDeptCd(deptCd);
        return dailySetupService.selectAplyTermGrdList(dsMainBsnsSet);
    }

    /* 적용기간 저장 */
    @PostMapping(value = "/gridSave")
    public @ResponseBody DsMainBsnsSet gridSave(DsMainBsnsSet dsMainBsnsSet) {
        dailySetupService.saveDailySetupList(dsMainBsnsSet.getDsMainBsnsSets());
        return dsMainBsnsSet;
    }



    /* 주요자재 리스트 */
    @GetMapping("/gridMtilList")
    public @ResponseBody
    List<CamelCaseMap> gridMtilList(DsMainMtilSet dsMainMtilSet) {
        UserDetail userDetail = getUserContext(request);
        String deptCd = userDetail.getDeptCd();
        dsMainMtilSet.setDeptCd(deptCd);
        return dailySetupService.selectMainMtilSetGrdList(dsMainMtilSet);
    }

    /* 주요자재 저장 */
    @PostMapping(value = "/gridMtilSave")
    public @ResponseBody DsMainMtilSet gridMtilSave(DsMainMtilSet dsMainMtilSet) {
        dailySetupService.saveMtilList(dsMainMtilSet.getDsMainMtilSets());
        return dsMainMtilSet;
    }





    /* 직영준비, 지입 리스트 */
    @GetMapping("/gridEqipList")
    public @ResponseBody
    List<CamelCaseMap> gridEqipList(DsMainEqipSet dsMainEqipSet) {
        UserDetail userDetail = getUserContext(request);
        String deptCd = userDetail.getDeptCd();
        dsMainEqipSet.setDeptCd(deptCd);
        return dailySetupService.selectMainEqipSetGrdList(dsMainEqipSet);
    }

    /* 직영준비, 지입 저장 */
    @PostMapping(value = "/gridEqipSave")
    public @ResponseBody DsMainEqipSet gridEqipSave(DsMainEqipSet dsMainEqipSet) {
        dailySetupService.saveEqipList(dsMainEqipSet.getDsMainEqipSets());
        return dsMainEqipSet;
    }





    /* 공사진행현황 리스트 */
    @GetMapping("/gridCnstQtyList")
    public @ResponseBody
    List<CamelCaseMap> gridCnstQtyList(DsMainCnstSet dsMainCnstSet) {
        UserDetail userDetail = getUserContext(request);
        String deptCd = userDetail.getDeptCd();
        dsMainCnstSet.setDeptCd(deptCd);
        return dailySetupService.selectMainCnstQtySetGrdList(dsMainCnstSet);
    }

    /* 공사진행현황 저장 */
    @PostMapping(value = "/gridCnstQtySave")
    public @ResponseBody DsMainCnstSet gridCnstQtySave(DsMainCnstSet dsMainCnstSet) {
        dailySetupService.saveCnstQtyList(dsMainCnstSet.getDsMainCnstSets());
        return dsMainCnstSet;
    }



    /* 자재 팝업 화면 */
    @GetMapping({"/mtilListModal", "/mtilListModal/{searchMtilNm}"})
    public String mtilListModal(@PathVariable(required = false) String searchMtilNm, Model model) {
        return "views/mgmt/dailySetup/mtilListModal";
    }

    /* 자재등록 동의어 팝업 */
    @GetMapping(value = "/gridMtilPopList")
    public @ResponseBody List<CamelCaseMap> gridMtilPopList(DsMatrial dsMatrial) {
        UserDetail userDetail = getUserContext(request);
        String bizhdofCd = userDetail.getBizhdofCd();
        dsMatrial.setBizhdofCd(bizhdofCd);
        return dailySetupService.selectMtilListPopup(dsMatrial);
    }



    /* 장비 팝업 화면 */
    @GetMapping({"/eqipListModal", "/eqipListModal/{searchEqipNm}"})
    public String eqipListModal(@PathVariable(required = false) String searchEqipNm, Model model) {
        return "views/mgmt/dailySetup/eqipListModal";
    }

    /* 장비등록 동의어 팝업 */
    @GetMapping(value = "/gridEqipPopList")
    public @ResponseBody List<CamelCaseMap> gridEqipPopList(DsEquip dsEquip) {
        UserDetail userDetail = getUserContext(request);
        String bizhdofCd = userDetail.getBizhdofCd();
        dsEquip.setBizhdofCd(bizhdofCd);
        return dailySetupService.selectEqipListPopup(dsEquip);
    }



    /* 공사진행현황 팝업 화면 */
    @GetMapping({"/cnstQtyListModal", "/cnstQtyListModal/{searchCnstQtyNm}"})
    public String cnstQtyListModal(@PathVariable(required = false) String searchCnstQtyNm, Model model) {
        return "views/mgmt/dailySetup/cnstQtyListModal";
    }

    /* 공사진행현황등록 동의어 팝업 */
    @GetMapping(value = "/gridCnstQtyPopList")

    public @ResponseBody List<CamelCaseMap> gridCnstQtyPopList(DsCnstQty dsCnstQty) {
        UserDetail userDetail = getUserContext(request);
        String deptCd = userDetail.getDeptCd();
        dsCnstQty.setDeptCd(deptCd);
        return dailySetupService.selectCnstQtyListPopup(dsCnstQty);
    }

    /* 복사 저장 */
    @PostMapping(value = "/dailyCopySave")
    public @ResponseBody
    CamelCaseMap copyHisotryData(DsCopyParams dsCopyParams, Model model) throws IOException, TemplateException {
        dailySetupService.copyDailySetupData(dsCopyParams);
        return null;
    }

}
