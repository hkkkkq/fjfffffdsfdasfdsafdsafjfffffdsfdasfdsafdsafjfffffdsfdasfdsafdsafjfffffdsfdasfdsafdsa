package com.henc.cdrs.mgmt.individual.web;

import com.henc.cdrs.mgmt.individual.service.IndividualService;
import com.henc.cdrs.sysm.sec.auth.model.UserDetail;
import com.henc.cdrs.sysm.sec.auth.model.UserSign;
import com.henc.cdrs.sysm.sec.auth.security.common.SecurityUtil;
import com.henc.cdrs.sysm.sec.usr.model.ComUser;
import com.henc.cdrs.sysm.sec.usr.service.ComUserService;
import com.henc.dream.util.CamelCaseMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

/**
 * Created by beoms(lbg6848@sdcit.co.kr) on 2019-07-29
 * Corporation : SDCIT(주)
 * HomePage : http://www.sdcit.co.kr
 */

@Controller
@RequestMapping("/individual")
public class IndividualConroller {

    @Autowired
    ComUserService comUserService;
    @Autowired
    IndividualService myInfoService;

    @GetMapping("/myInfo")
    public String myInfo(Model model, HttpServletRequest request){
        UserDetail userDetail = SecurityUtil.getUserContext(request);


        ComUser comUser = comUserService.getComUser(userDetail.getUserId());

        List<CamelCaseMap> userDeptList = comUserService.getUserDeptList(comUser);

        UserSign userSign = myInfoService.getUserSign(userDetail.getUserId());


        model.addAttribute("userDeptList", userDeptList);
        model.addAttribute("comUser", comUser);
        model.addAttribute("userSign", userSign);

        return "views/mgmt/individual/myInfoModal";
    }


    @PostMapping("/myInfoSave")
    public @ResponseBody
    CamelCaseMap myInfoSave(UserDetail userDetail, @RequestParam HashMap<String, String> params){
        return myInfoService.setMyInfo(userDetail, params);
    }
}
