package com.henc.cdrs.mgmt.individual.service;

import com.henc.cdrs.common.config.CdrsProperties;
import com.henc.cdrs.common.util.FileUtil;
import com.henc.cdrs.common.util.KISA_SHA256;
import com.henc.cdrs.sysm.sec.auth.model.UserDetail;
import com.henc.cdrs.sysm.sec.auth.model.UserSign;
import com.henc.cdrs.sysm.sec.auth.repository.AuthMapper;
import com.henc.cdrs.sysm.sec.auth.repository.UserSignMapper;
import com.henc.cdrs.sysm.sec.usr.model.UserDept;
import com.henc.cdrs.sysm.sec.usr.repository.ComUserMapper;
import com.henc.cdrs.sysm.sec.usr.repository.UserDeptMapper;
import com.henc.dream.util.CamelCaseMap;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.util.HashMap;

/**
 * Created by beoms(lbg6848@sdcit.co.kr) on 2019-07-29
 * Corporation : SDCIT(주)
 * HomePage : http://www.sdcit.co.kr
 */
@Service
@Transactional
public class IndividualService {

    @Autowired
    ComUserMapper comUserMapper;

    @Autowired
    UserSignMapper userSignMapper;

    @Autowired
    AuthMapper authMapper;

    @Autowired
    UserDeptMapper userDeptMapper;

    @Autowired
    private CdrsProperties cdrsProperties;

    /**
     * 사용자 Sign 조회 한다.
     *
     * @param userId
     * @return
     */
    public UserSign getUserSign(String userId) {
        UserSign userSign = userSignMapper.selectUserSign(userId);
        if (userSign == null || userSign.getSign().length <= 0) {
            userSign = new UserSign();
            return userSign;
        }

        String signData = null;
        try {
            signData = new String(userSign.getSign(), "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        userSign.setBas64FileDataUrl(signData);
        return userSign;
    }

    public CamelCaseMap setMyInfo(UserDetail userDetail, HashMap<String, String> params) {
        CamelCaseMap cm = new CamelCaseMap();

        String encPwd = KISA_SHA256.encryptPasswordPortalStyle(params.get("pwd"));
        //패스워드 변경
        authMapper.changeExpiredPassword(userDetail.getUserId(), encPwd);


        //알림 수신 설정 변경
        userDetail.setPushUseYn(params.get("pushUseYn"));
        comUserMapper.updatePushUse(userDetail);

        //Sign 정보 저장
        UserSign userSign = new UserSign();
        userSign.setUserId(userDetail.getUserId());
        userSign.setBas64FileDataUrl(params.get("signData"));
        userSignMapper.deleteUserSign(userSign);

        //base64 data url을 byte 배열로 변경
        String signData = params.get("signData");
        if (!"".equals(signData)) {
            userSign.setSign(signData.getBytes());
            userSignMapper.insertUserSign(userSign);
        }

        if (signData != null) {
            try {
                OutputStream signFile = new FileOutputStream(cdrsProperties.getSignfilePath() + userDetail.getUserId() + ".png");
                byte[] signfileBytes = FileUtil.decodeBase64ToBytes(signData);
                signFile.write(signfileBytes);
                signFile.close();

            } catch (FileNotFoundException e) {
                e.printStackTrace();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //대표 현장 변경
        UserDept userDept = new UserDept();
        userDept.setUserId(userDetail.getUserId());
        userDept.setDeptCd(params.get("basDeptYn"));
        userDept.setBasDeptYn("Y");

        userDeptMapper.resetBasDeptYn(userDept);
        userDeptMapper.updateBasDeptYn(userDept);

        return cm;
    }
}
