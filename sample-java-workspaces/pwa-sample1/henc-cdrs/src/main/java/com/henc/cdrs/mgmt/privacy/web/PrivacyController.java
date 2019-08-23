package com.henc.cdrs.mgmt.privacy.web;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.henc.cdrs.common.web.BaseController;
import com.henc.cdrs.mgmt.privacy.service.PrivacyService;
import com.henc.cdrs.sysm.sec.auth.model.UserDetail;
import com.henc.dream.util.CamelCaseMap;

@Controller
@RequestMapping("/mgmt/privacy")
public class PrivacyController extends BaseController {

    @Autowired
    private PrivacyService privacyService;

    @Autowired
    HttpServletRequest request;

    @RequestMapping("/privacy")
    public String prtn(Model model, HttpServletRequest request) {
        return "views/mgmt/privacy/privacy";
    }

    @PostMapping("/privacySave")
    public @ResponseBody
    CamelCaseMap privacySave(UserDetail userDetail, @RequestParam HashMap<String, String> params){
        return privacyService.setPrivacy(userDetail, params);
    }
}
