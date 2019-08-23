package com.henc.cdrs.mgmt.siteConstType.web;

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
import com.henc.cdrs.mgmt.siteConstType.model.SiteConstType;
import com.henc.cdrs.mgmt.siteConstType.service.SiteConstTypeService;
import com.henc.cdrs.sysm.sec.auth.model.UserDetail;
import com.henc.dream.util.CamelCaseMap;

@Controller
@RequestMapping("/mgmt/siteConstType")
public class SiteConstTypeController extends BaseController {

    @Autowired
    private SiteConstTypeService siteConstTypeService;

    @Autowired
    HttpServletRequest request;

    @GetMapping("/")
    public String site(Model model) {
        return "views/mgmt/siteConstType/siteConstType";
    }

    @GetMapping("/siteConstType/gridList")
    public @ResponseBody
    List<CamelCaseMap> siteGridList(SiteConstType siteConstType) {
        UserDetail userDetail = getUserContext(request);
        String deptCd = userDetail.getDeptCd();
        siteConstType.setDeptCd(deptCd);

        String bizhdofCd = userDetail.getBizhdofCd();

        if(siteConstType.getBizhdofCd() == null) {
            siteConstType.setBizhdofCd(bizhdofCd);
        }

        return siteConstTypeService.selectSiteConstTypeGrdList(siteConstType);
    }

    @PostMapping(value = "/siteConstType/gridSave")
    public @ResponseBody
    SiteConstType siteGridSave(SiteConstType siteConstType) {
        siteConstTypeService.saveSiteConstTypeList(siteConstType.getSiteConstTypes(), getUserContext(request).getDeptCd());
        return siteConstType;
    }

}