package com.henc.cdrs.mgmt.siteSetup.web;

import com.henc.cdrs.common.web.BaseController;
import com.henc.cdrs.mgmt.siteSetup.model.Dept;
import com.henc.cdrs.mgmt.siteSetup.service.SiteSetupService;
import com.henc.dream.util.CamelCaseMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/mgmt/siteSetup")
public class SiteSetupController extends BaseController {

    @Autowired
    private SiteSetupService siteSetupService;

    @GetMapping("/")
    public String siteSeup(Model model) {
        return "views/mgmt/siteSetup/siteSetup";
    }

    @GetMapping("/gridList")
    public @ResponseBody
    List<CamelCaseMap> siteGridList(Dept dept) {
        return siteSetupService.getDeptList(dept);
    }

    @PostMapping(value = "/gridSave")
    public @ResponseBody
    Dept siteGridSave(Dept dept) {
        siteSetupService.saveDeptList(dept.getDepts());
        return dept;
    }

}
