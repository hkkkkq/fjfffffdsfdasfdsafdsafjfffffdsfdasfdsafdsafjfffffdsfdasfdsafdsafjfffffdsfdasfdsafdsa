package com.henc.cdrs.partnerConfirm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.henc.cdrs.common.util.CommUtil;
import com.henc.cdrs.common.util.KISA_SHA256;
import com.henc.cdrs.partnerConfirm.model.PartnerConfirm;
import com.henc.cdrs.partnerConfirm.repository.PartnerConfirmMapper;
import com.henc.dream.util.CamelCaseMap;

@Service
@Transactional
public class PartnerConfirmService {

    @Autowired
    private PartnerConfirmMapper partnerConfirmMapper;

    public List<CamelCaseMap> selectPartnerInfo(PartnerConfirm partnerConfirm) {
        return partnerConfirmMapper.selectPartnerInfo(partnerConfirm);
    }

    public void savePartner(PartnerConfirm partnerConfirm) {
        String pwd = CommUtil.decode(partnerConfirm.getPwd());
        String encPwd = KISA_SHA256.encryptPasswordPortalStyle(pwd);
        partnerConfirm.setPwd(encPwd);

        partnerConfirmMapper.updatePartnerInfo(partnerConfirm);
        partnerConfirmMapper.updatePartnerDept(partnerConfirm);
    }

    public List<CamelCaseMap> selectPartnerDept(PartnerConfirm partnerConfirm) {
        return partnerConfirmMapper.selectPartnerDept(partnerConfirm);
    }

}