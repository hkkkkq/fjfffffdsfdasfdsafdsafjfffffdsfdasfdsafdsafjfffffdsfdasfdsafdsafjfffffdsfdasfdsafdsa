package com.henc.cdrs.sysm.stdcd.web;

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

import com.henc.dream.util.CamelCaseMap;

import com.henc.cdrs.common.web.BaseController;
import com.henc.cdrs.sysm.stdcd.model.AttributeDefi;
import com.henc.cdrs.sysm.stdcd.model.AttributeVal;
import com.henc.cdrs.sysm.stdcd.model.StdCd;
import com.henc.cdrs.sysm.stdcd.model.StdDetlCd;
import com.henc.cdrs.sysm.stdcd.service.StdCdService;

/**
 * 공통 코드 관리 controller
 */
@Controller
@RequestMapping("/sysm/stdcd")
public class StdCdController extends BaseController {

    @Autowired
    StdCdService stdCdService;

    @Autowired
    HttpServletRequest request;

    @GetMapping("/stdCdList")
    public String stdCdList(Model model) {
        return "views/sysm/stdcd/stdCdList";
    }

    @GetMapping("/gridList")
    public @ResponseBody
    List<CamelCaseMap> gridList(StdCd stdCd) {
        return stdCdService.getComStdCdList(stdCd);
    }

    @PostMapping(value = "/gridSave")
    public @ResponseBody
    StdCd gridSave(StdCd stdCd) {
        stdCdService.saveComStdCdList(stdCd.getStdCds());
        return stdCd;
    }

    @GetMapping(value = "/gridDetailList")
    public @ResponseBody
    List<CamelCaseMap> gridDetailList(StdDetlCd stdDetlCd) {
        return stdCdService.getComStdDetlCdList(stdDetlCd);
    }

    @PostMapping("/gridDetailSave")
    public @ResponseBody
    StdDetlCd gridDetailSave(StdDetlCd stdDetlCd) {
        stdCdService.saveComStdDetlCdList(stdDetlCd.getStdDetlCds());
        return stdDetlCd;
    }

    /**
     * 기준코드 보조정보 모달
     */
    @GetMapping({"/etcInfoModal/{stdCd}"})
    public String etcInfoModal(@PathVariable String stdCd, Model model) {
        model.addAttribute(stdCd);
        return "views/sysm/stdcd/etcInfoModal";
    }

    @GetMapping("/gridcomAttributeDefiList")
    public @ResponseBody
    List<CamelCaseMap> gridcomAttributeDefiList(AttributeDefi attributeDefi) {
        return stdCdService.getComStdCdEtcInfoList(attributeDefi);
    }

    @PostMapping("/gridAttributeDefiSave")
    public @ResponseBody
    AttributeDefi gridAttributeDefiSave(AttributeDefi attributeDefi) {
        stdCdService.saveComAttributeDefi(attributeDefi.getAttributeDefis());
        return attributeDefi;
    }

    /**
     * 기준코드 상세 보조정보 설정 모달
     */
    @GetMapping({"/etcInfoSetModal/{stdCd}/{stdDetlCd}"})
    public String etcInfoSetModal(@PathVariable("stdCd") String stdCd, @PathVariable("stdDetlCd") String stdDetlCd, Model model) {
        model.addAttribute(stdCd);
        model.addAttribute(stdDetlCd);
        return "views/sysm/stdcd/etcInfoSetModal";
    }

    @GetMapping("/gridcomAttributeValList")
    public @ResponseBody
    List<CamelCaseMap> gridcomAttributeValList(AttributeVal attributeVal) {
        return stdCdService.getComAttributeValList(attributeVal);
    }

    @PostMapping("/gridAttributeValSave")
    public @ResponseBody
    AttributeVal gridAttributeValSave(AttributeVal attributeVal) {
        stdCdService.saveComAttributeVal(attributeVal.getAttributeVals());
        return attributeVal;
    }
}
