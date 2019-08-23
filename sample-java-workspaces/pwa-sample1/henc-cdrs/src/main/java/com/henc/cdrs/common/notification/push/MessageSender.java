package com.henc.cdrs.common.notification.push;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.*;
import com.henc.cdrs.common.config.CdrsProperties;
import com.henc.cdrs.common.notification.model.PushHst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by beoms(lbg6848@sdcit.co.kr) on 2019-07-01
 * Corporation : SDCIT(주)
 * HomePage : http://www.sdcit.co.kr
 */
@Component
public class MessageSender implements InitializingBean {
    public static final String TOPIC = "/topics/__HWENC_CDRS__";
    private static final Logger logger = LoggerFactory.getLogger(MessageSender.class);

    @Autowired
    private CdrsProperties props;

    /**
     * WEB Push Sender
     * @param notification
     * @param pushHst
     * @return
     */
    public String send(WebpushNotification notification, PushHst pushHst) {
        WebpushConfig config = WebpushConfig.builder().putHeader("ttl", "300").setNotification(notification).build();
        Message message = Message.builder().setToken(pushHst.getToken()).setWebpushConfig(config).build();
        String response = "";

        try {
            response = FirebaseMessaging.getInstance().sendAsync(message).get();
            logger.debug("MessageSender.send >> "+ response);

            /**
             * Push 정상 발송
             */
            pushHst.setPushStaCd("10");
            pushHst.setPushErrCntt("");

        } catch (Exception e) {
            logger.error(e.getMessage());
            response = "ERROR";
            pushHst.setPushStaCd("20");
            pushHst.setPushErrCntt(e.getMessage());
        }
        return response;
    }

//    public BatchResponse send(WebpushNotification notification, List<PushHst> pushHstList) {
//        WebpushConfig config = WebpushConfig.builder().putHeader("ttl", "300").setNotification(notification).build();
//        List<Message> messageList = null;
//        BatchResponse response = null;
//        for (PushHst pushHst:pushHstList) {
//            Message message = Message.builder().setToken(pushHst.getToken()).setWebpushConfig(config).build();
//            messageList.add(message);
//        }
//        try {
//            response = FirebaseMessaging.getInstance().sendAllAsync(messageList).get();
//
//            List<SendResponse> sedResponseList = response.getResponses();
//
//            for(SendResponse srp :sedResponseList){
//                if(!srp.isSuccessful()){
//                    logger.debug("Push Sending Failed! Message ID:" + srp.getMessageId());
//                }
//            }
//
//        } catch (Exception e) {
//            logger.error(e.getMessage());
//        }
//        return response;
//    }

    @Override
    public void afterPropertiesSet() throws Exception {
        List<FirebaseApp> apps = FirebaseApp.getApps();
        ClassPathResource resource = new ClassPathResource("firebase-adminsdk.json");
        if (CollectionUtils.isEmpty(apps)) {
            try (InputStream serviceAccount = resource.getInputStream()) {
                FirebaseOptions options = new FirebaseOptions.Builder()
                        .setCredentials(GoogleCredentials.fromStream(serviceAccount)).build();
                FirebaseApp.initializeApp(options);
            }
        }
    }
}
