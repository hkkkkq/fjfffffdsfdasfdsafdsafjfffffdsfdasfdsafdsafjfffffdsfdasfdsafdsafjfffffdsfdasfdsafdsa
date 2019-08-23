package com.henc.cdrs.common.notification.web;

import com.google.firebase.messaging.TopicManagementResponse;
import com.henc.cdrs.common.notification.model.PushHst;
import com.henc.cdrs.common.notification.service.NotificationService;
import com.henc.cdrs.common.web.BaseController;
import com.henc.cdrs.coprcp.model.RegReqSend;
import com.henc.cdrs.sysm.sec.auth.model.UserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.ExecutionException;

/**
 * Created by beoms(lbg6848@sdcit.co.kr) on 2019-07-01
 * Corporation : SDCIT(주)
 * HomePage : http://www.sdcit.co.kr
 */
@RestController
public class NotificationController extends BaseController {

    @Autowired
    private NotificationService service;

    @GetMapping("/push")
    public ResponseEntity<String> push() throws InterruptedException, ExecutionException {
        String title = "일보 제출";
        String body = "홍길동님이 공사일보를 제출 하였습니다.";
        String recUserId = "P4";

        PushHst pushHst = new PushHst();

        pushHst.setPushTitl(title);
        pushHst.setPushCntt(body);
        pushHst.setReceiverId(recUserId);
        pushHst.setPushLink("/daily/partner/");

        /**
         * 발송 업무 식별자
         */
        pushHst.setDeptCd("");
        pushHst.setDay("");
        pushHst.setOrderNo(0);
        pushHst.setPartnerNo("");
        pushHst.setCtrcNo("");

        return new ResponseEntity<>(service.pushByUser(pushHst), HttpStatus.OK);
    }

    @PostMapping("/subscribe")
    public TopicManagementResponse subscribe(@RequestParam("clientToken") String clientToken, HttpServletRequest request) throws InterruptedException, ExecutionException {
        String userId = getUserContext(request).getUserId();
        return service.subscribe(userId, clientToken);
    }

    @DeleteMapping("/unsubscribe")
    public TopicManagementResponse unsubscribe(@RequestParam("clientToken") String clientToken, HttpServletRequest request) throws InterruptedException, ExecutionException {
        String userId = getUserContext(request).getUserId();
        return service.unsubscribe(userId, clientToken);
    }

    @GetMapping("/smsSend")
    public String smsSend(HttpServletRequest request) {
        UserDetail userDetail = getUserContext(request);

        return "";
    }

    @PostMapping("/invite")
    public String invite(RegReqSend regReqSend, HttpServletRequest request){

        UserDetail userDetail = getUserContext(request);
        /*추가 사용자 정보*/
        regReqSend.setDeptCd("62000000001");
        regReqSend.setUserId("P4");
        regReqSend.setCoprcpNo("1234123");


        service.invitePartner(regReqSend, userDetail);


        return "";
    }

}
