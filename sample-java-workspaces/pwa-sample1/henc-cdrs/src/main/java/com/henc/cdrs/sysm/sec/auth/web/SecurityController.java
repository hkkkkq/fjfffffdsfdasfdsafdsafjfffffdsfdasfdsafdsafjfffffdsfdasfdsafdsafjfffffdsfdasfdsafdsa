package com.henc.cdrs.sysm.sec.auth.web;

import com.henc.cdrs.common.service.NotExistsException;
import com.henc.cdrs.common.util.CipherUtil;
import com.henc.cdrs.common.util.CommUtil;
import com.henc.cdrs.common.util.KISA_SHA256;
import com.henc.cdrs.common.util.MailUtils;
import com.henc.cdrs.sysm.sec.auth.model.CertNoSend;
import com.henc.cdrs.sysm.sec.auth.model.UserDetail;
import com.henc.cdrs.sysm.sec.auth.model.UserSign;
import com.henc.cdrs.sysm.sec.auth.security.auth.RemeberMeAuthenticationRequestToken;
import com.henc.cdrs.sysm.sec.auth.security.common.SecurityUtil;
import com.henc.cdrs.sysm.sec.auth.security.service.AuthenticationUsersService;
import com.henc.dream.util.CamelCaseMap;
import com.moyosoft.exchange.ExchangeServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Optional;

@Controller
public class SecurityController {

    @Autowired
    MailUtils mailUtils;

    @Autowired
    AuthenticationUsersService userDetailsService;

    @Autowired
    private Environment env;

    @RequestMapping("/security/login")
    public String login(@RequestParam("logFail") Optional<String> logFail, @RequestParam("userId") Optional<String> userId, Model model, HttpServletRequest request) {


        Cookie cookieSaveId = WebUtils.getCookie(request, RemeberMeAuthenticationRequestToken.REMEMBER_ME_COOKIE_NAME);
        String username = null;
        if(cookieSaveId != null){
            username = cookieSaveId.getValue();

            CipherUtil cu = new CipherUtil();
            username = cu.decrytion(username, CipherUtil.AES_ALGORITHM);
        };

        model.addAttribute("logFail", logFail.orElse(null));
        model.addAttribute("userId", userId.orElse(username));
        model.addAttribute("idSaveChecked", username==null?"false":"true");

        return "views/sysm/sec/auth/login";
    }

    @RequestMapping("/security/fail")
    public String fail() {

        return "views/sysm/sec/auth/fail";
    }

    @GetMapping("/security/accessDenied")
    public String accessDenied() {
        return "views/sysm/sec/auth/accessDenied";
    }

    @RequestMapping("/security/logout")
    public String logout() {
        return "views/sysm/sec/auth/logout";
    }

    @RequestMapping("/security/sendCertNo")
    public @ResponseBody CamelCaseMap sendCertNo(@RequestParam("username") String userNm, @RequestParam("userId") String userId){
        CamelCaseMap cm = new CamelCaseMap();
        String resultCode = "S";
        String resultMsg = "";
        UserDetail userDetail = userDetailsService.whoAreYou(userId, userNm);
        CertNoSend certNoSend = new CertNoSend();

        if (userDetail == null){
            resultCode = "UE";
            resultMsg = "사용자 정보를 찾을 수 없습니다.\n관리자에게 문의 바랍니다.";
        } else if (!"CDRS".equals(userDetail.getLoginSource())){
            resultCode = "SE";
            resultMsg = "포탈사용자는 변경 할 수 없습니다.\n관리자에게 문의 바랍니다.";
        } else if (userDetail.getTlno().length() <= 0){
            resultCode = "TE";
            resultMsg = "사용자 전화번호가 존재 하지 않습니다.\n관리자에게 문의 바랍니다.";
        }else{
            certNoSend = userDetailsService.certNoSend(userDetail);
        }


        cm.put("result_code", resultCode);
        cm.put("result_msg", resultMsg);

        return cm;
    }

    /**
     * 인증번호 검사 후 일치 하는경우 임시 비밀번호 발급 처리
     * @param certNo
     * @param userId
     * @return 임시비밀번호 및 상태 코드
     */
    @RequestMapping("/security/checkCertNo")
    public @ResponseBody CamelCaseMap checkCertNo(@RequestParam("certNo") String certNo, @RequestParam("userId") String userId){
        CamelCaseMap cm = userDetailsService.checkCertNo(certNo, userId);
        return cm;
    }



    /**
     * 패스워드 수정
     *
     * @return
     */
    @PutMapping("/security/changePassword")
    public @ResponseBody
    boolean changePassword(String changeUserId, String changePassword) {
        String userId = changeUserId;
        String encPwd = KISA_SHA256.encryptPasswordPortalStyle(changePassword);

        //패스워드 변경
        userDetailsService.changeExpiredPassword(userId, encPwd);

        return true;
    }


}
