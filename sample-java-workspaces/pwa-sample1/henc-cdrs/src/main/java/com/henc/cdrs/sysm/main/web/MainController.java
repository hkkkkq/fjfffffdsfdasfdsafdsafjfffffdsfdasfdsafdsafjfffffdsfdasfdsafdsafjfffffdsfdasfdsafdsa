package com.henc.cdrs.sysm.main.web;

import com.henc.cdrs.common.config.CdrsProperties;
import com.henc.cdrs.common.web.BaseController;
import com.henc.cdrs.sysm.main.menu.service.MenuService;
import com.henc.cdrs.sysm.sec.auth.model.UserDetail;
import com.henc.cdrs.sysm.sec.auth.security.jwt.JwtAuthenticationProvider;
import com.henc.cdrs.sysm.sec.auth.security.service.AuthenticationUsersService;
import com.henc.cdrs.sysm.sec.usr.repository.UserDeptMapper;
import com.henc.dream.util.CamelCaseMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
@Slf4j
@Controller
public class MainController extends BaseController {

    @Autowired
    private MenuService menuService;

    @Autowired
    private CdrsProperties cdrsProperties;

    @Autowired
    UserDeptMapper userDeptMapper;

    @Autowired
    private AuthenticationUsersService authenticationUsersService;

    @RequestMapping("/")
    public String index(HttpServletRequest request, Model model) {
        HashMap<String, String> map = new HashMap<String, String>();

        UserDetail userDetail = getUserContext(request);
        map.put("userId", userDetail.getUserId());
        map.put("roleId", userDetail.getRoleId());
        map.put("lang", userDetail.getLang());

        log.debug("userId>>>>>>>>>>>>>>>"+userDetail.getUserId());
        log.debug("roleId>>>>>>>>>>>>>>>"+userDetail.getRoleId());
        log.debug("v>>>>>>>>>>>>>>>"+userDetail.getLang());

        List<CamelCaseMap> userDepts = new ArrayList<CamelCaseMap>();

        //        // 기본현장 설정
        if ("PORTAL".equals(userDetail.getLoginSource())) {
            userDepts = userDeptMapper.getHencDeptList(userDetail.getUserId());
        } else {
            userDepts = userDeptMapper.getUserDeptList(userDetail.getUserId());
        }

//        String mainDeptCd =


        model.addAttribute("userRoles", authenticationUsersService.getUserRoles(userDetail.getUserId()));
        model.addAttribute("userDepts", userDepts);
        model.addAttribute("mainDeptCd", userDetail.getDeptCd());
        model.addAttribute("mainBizhdofCd", userDetail.getBizhdofCd());
        model.addAttribute("partnerNo", userDetail.getCoprcpNo());
        model.addAttribute("tokenUser", map);
        model.addAttribute("loginInfo", userDetail.getName());

        model.addAttribute("reportUrl", cdrsProperties.getReportUrl());

        String viewPage;

        if("COPRCP".equals(userDetail.getUserDivCd())){
            viewPage = "views/daily/partner";
        }else{
            viewPage = "views/daily/field";
        }


        return viewPage;
    }

    @GetMapping("/mList.m")
    public @ResponseBody String menuList(HttpServletRequest request,@RequestParam("uri") String uri){
        UserDetail ud = getUserContext(request);
        return menuService.selectMenuTag(ud.getRoleId(), uri);
    }

    @RequestMapping("/portal")
    public String portal(Model model) {
        return "/views/sysm/main/portal";
    }

}