package com.henc.cdrs.common.notification.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.TopicManagementResponse;
import com.google.firebase.messaging.WebpushNotification;
import com.henc.cdrs.common.config.CdrsProperties;
import com.henc.cdrs.common.notification.model.Device;
import com.henc.cdrs.common.notification.model.MmsMsg;
import com.henc.cdrs.common.notification.model.PushHst;
import com.henc.cdrs.common.notification.model.ScTran;
import com.henc.cdrs.common.notification.push.MessageSender;
import com.henc.cdrs.common.notification.repository.DeviceMapper;
import com.henc.cdrs.common.notification.repository.MmsMsgMapper;
import com.henc.cdrs.common.notification.repository.PushHstMapper;
import com.henc.cdrs.common.notification.repository.ScTranMapper;
import com.henc.cdrs.coprcp.model.RegReqSend;
import com.henc.cdrs.coprcp.repository.RegReqSendMapper;
import com.henc.cdrs.sysm.sec.auth.model.UserDetail;
import com.henc.dream.i18n.I18nMessage;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.ExecutionException;



/**
 * Created by beoms(lbg6848@sdcit.co.kr) on 2019-07-01
 * Corporation : SDCIT(주)
 * HomePage : http://www.sdcit.co.kr
 */
@Service
@Transactional
public class NotificationService {
    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);

    @Autowired
    private MessageSender messageSender;

    @Autowired
    private DeviceMapper deviceMapper;

    @Autowired
    private PushHstMapper pushHstMapper;

    @Autowired
    private MmsMsgMapper mmsMsgMapper;

    @Autowired
    private RegReqSendMapper regReqSendMapper;

    @Autowired
    private ScTranMapper scTranMapper;

    @Autowired
    private CdrsProperties props;

    @Autowired
    private ServerProperties serverProperties;

    @Autowired
    MessageSource messageSource;

    /**
     * Push를 사용자에게 전송 한다.
     * @param pushHst{PushTitl, PushCntt, Token, ReceiverId}
     * @return
     * @throws InterruptedException
     * @throws ExecutionException
     */
    public String pushByUser(PushHst pushHst) throws InterruptedException, ExecutionException {
        String retVal = "";
        String sLink = pushHst.getPushLink();
        List<Device> deviceList = deviceMapper.selectDevice(pushHst.getReceiverId(), null);
        for (Device device : deviceList) {
            if (!"Y".equals(device.getRecvYn())) {
                continue;
            }

            pushHst.setDeviceNo(device.getDeviceNo());
            pushHst.setToken(device.getToken());
            pushHst.setPushLink(sLink);

            retVal = push(pushHst);
        }
        return retVal;
    }

    /**
     * Push를 전송한다.
     * @param pushHst{PushTitl, PushCntt, Token, ReceiverId}
     * @return
     * @throws InterruptedException
     * @throws ExecutionException
     */
    public String push(PushHst pushHst) throws InterruptedException, ExecutionException {
        String retVal = null;
        Map<String, Object> data = new HashMap<>();
        data.put("url", pushHst.getPushLink());
        WebpushNotification notification = WebpushNotification.builder()
                .setTitle(pushHst.getPushTitl())
                .setIcon("/images/icon/android-icon-192x192.png")
                .setBody(pushHst.getPushCntt())
                .setVibrate(new int[] {500, 110, 500, 110, 450, 110, 200, 110, 170, 40, 450, 110, 200, 110, 170, 40, 500})
                .setData(data)
                .setTimestampMillis(System.currentTimeMillis())
                .build();

        retVal = messageSender.send(notification, pushHst);

        pushHstMapper.insertPushHst(pushHst);

        if("20".equals(pushHst.getPushStaCd())){
            Device device = new Device();
            device.setDeviceNo(pushHst.getDeviceNo());
            device.setToken(pushHst.getToken());
            device.setRecvYn("N");

            deviceMapper.updateDevice(device);
        }
        return retVal;
    }

    /**
     * Push 알람 승인 정보 처리
     * @param userId
     * @param clientToken
     * @return
     * @throws InterruptedException
     * @throws ExecutionException
     */
    public TopicManagementResponse subscribe(String userId, String clientToken) throws InterruptedException, ExecutionException {
        TopicManagementResponse response = FirebaseMessaging.getInstance().subscribeToTopicAsync(Collections.singletonList(clientToken), MessageSender.TOPIC).get();

        List<Device> deviceList = deviceMapper.selectDevice(userId, clientToken);


        if (deviceList.size() <= 0){
            Device device = new Device();
            device.setToken(clientToken);
            device.setUserId(userId);
            device.setRecvYn("Y");
            deviceMapper.insertDevice(device);
        } else {
            for (Device device : deviceList) {
                device.setToken(clientToken);
                device.setUserId(userId);
                device.setRecvYn("Y");
                deviceMapper.updateDevice(device);
            }
        }
        return response;
    }

    /**
     * Push 알람 삭제 처리
     * @param userId
     * @param clientToken
     * @return
     * @throws InterruptedException
     * @throws ExecutionException
     */
    public TopicManagementResponse unsubscribe(String userId, String clientToken) throws InterruptedException, ExecutionException {
        TopicManagementResponse response = FirebaseMessaging.getInstance()
                .unsubscribeFromTopicAsync(Collections.singletonList(clientToken), MessageSender.TOPIC).get();

        List<Device> deviceList = deviceMapper.selectDevice(userId, clientToken);
        for (Device device : deviceList){
            device.setToken(clientToken);
            device.setUserId(userId);
            device.setRecvYn("N");
            deviceMapper.updateDevice(device);

        }
        return response;
    }

    /**
     * MMS SEND
     * @param userDetail
     * @param phone
     * @param subject
     * @param msg
     */
    public int mmsSend(UserDetail userDetail, String phone, String subject, String msg){
        MmsMsg mmsMsg = new MmsMsg();

        mmsMsg.setId(userDetail.getUserId());
        mmsMsg.setPost(userDetail.getDeptCd());
        mmsMsg.setPhone(phone);
        mmsMsg.setMsg(msg);
        mmsMsg.setSubject(subject);
        mmsMsg.setCallback(props.getCallbackTelNo()); //발신자 번호

        return mmsMsgMapper.insertMmsMsg(mmsMsg);
    }


    public void invitePartner(RegReqSend regReqSend, UserDetail userdetail){
        /**
         * 3. 업체 번호 + 사용자 id 암호화
         * 4. 암화화 테이블 insert
         * 5. sms send
         */
        regReqSend.setSendId(RandomStringUtils.randomAlphanumeric(7));
        regReqSendMapper.insertRegReqSend(regReqSend);




        String serverUrl = serverProperties.getAddress().getHostAddress() + ":" + serverProperties.getPort() + "/partnerConfirm?info=" + regReqSend.getSendId();

        String [] arg = {serverUrl};
        String title = messageSource.getMessage("sms.partner.regi.title", null, Locale.getDefault());

        String message = messageSource.getMessage("sms.partner.regi", arg, Locale.getDefault());


        mmsSend(userdetail, regReqSend.getTlno(), title, message);
    }


    public int smsSend(UserDetail userDetail, String phone, String msg){
        ScTran scTran = new ScTran();

        scTran.setTrMsg(msg);
        scTran.setTrPhone(phone);
        scTran.setTrCallback(props.getCallbackTelNo());

        scTran.setTrEtc1(userDetail.getUserId());
        scTran.setTrEtc2(userDetail.getDeptCd());

        return scTranMapper.insertScTran(scTran);
    }


}
