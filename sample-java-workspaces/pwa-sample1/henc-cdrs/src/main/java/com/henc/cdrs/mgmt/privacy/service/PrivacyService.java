package com.henc.cdrs.mgmt.privacy.service;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.henc.cdrs.sysm.sec.auth.model.Agreement;
import com.henc.cdrs.sysm.sec.auth.model.UserDetail;
import com.henc.cdrs.sysm.sec.auth.model.UserSign;
import com.henc.cdrs.sysm.sec.auth.repository.AgreementMapper;
import com.henc.cdrs.sysm.sec.auth.repository.UserSignMapper;
import com.henc.dream.util.CamelCaseMap;

@Service
@Transactional
public class PrivacyService {

    @Autowired
    UserSignMapper userSignMapper;

    @Autowired
    AgreementMapper agreementMapper;

    public CamelCaseMap setPrivacy(UserDetail userDetail, HashMap<String, String> params){
        CamelCaseMap cm = new CamelCaseMap();

        //Sign 정보 저장
        UserSign userSign = new UserSign();
        userSign.setUserId(userDetail.getUserId());
        userSign.setBas64FileDataUrl(params.get("signData"));
        userSignMapper.deleteUserSign(userSign);

        //base64 data url을 byte 배열로 변경
        String signData = params.get("signData");
        if(!"".equals(signData)){
            userSign.setSign(signData.getBytes());
            userSignMapper.insertUserSign(userSign);
        }

        //대표 현장 변경
        Agreement agreement = new Agreement();

        System.out.println(" TEST clctAgreeYn    : " + params.get("clctAgreeYn"));
        System.out.println(" TEST entrustAgreeYn : " + params.get("entrustAgreeYn"));

        agreement.setUserId(userDetail.getUserId());
        agreement.setClctAgreeYn(params.get("clctAgreeYn"));
        agreement.setEntrustAgreeYn(params.get("entrustAgreeYn"));

        agreementMapper.insertAgreement(agreement);

        return cm;
    }

}