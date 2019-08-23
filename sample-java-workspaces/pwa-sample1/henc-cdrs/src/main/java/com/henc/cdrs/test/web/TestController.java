package com.henc.cdrs.test.web;

import com.henc.cdrs.common.web.BaseController;
import com.henc.cdrs.sysm.com.uscd.model.ComDeptCd;
import com.henc.cdrs.sysm.com.uscd.service.ComDeptCdService;
import com.henc.cdrs.sysm.sec.auth.model.UserDetail;
import com.henc.cdrs.sysm.sec.auth.model.UserSign;
import com.henc.cdrs.sysm.sec.auth.security.common.SecurityUtil;
import com.henc.cdrs.sysm.sec.usr.model.ComUser;
import com.henc.cdrs.sysm.sec.usr.service.ComUserService;
import com.henc.dream.util.CamelCaseMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by beoms(lbg6848@sdcit.co.kr) on 2019-06-19
 * Corporation : SDCIT(주)
 * HomePage : http://www.sdcit.co.kr
 */
@Controller
@RequestMapping("/test")
public class TestController extends BaseController {
    @Autowired
    ComDeptCdService comDeptCdService;

    @Autowired
    ComUserService comUserService;

    @GetMapping("/quickSearchTest")
    public String quickSearchTest(Model model){
        return "views/test/quickSearchTest";
    }

    @GetMapping("/bootAutocomplete")
    public String bootAutocomplete(Model model){
        return "views/test/bootAutocomplete";
    }

    @GetMapping("/comDeptCdGridList")
    public @ResponseBody
    List<CamelCaseMap> comDeptCdGridList(ComDeptCd comDeptCd) {
        return comDeptCdService.getComDeptCdList(comDeptCd);
    }


    @GetMapping("/pwaTest")
    public String pwaTest(Model model){
        return "views/test/pwaTest";
    }


}
