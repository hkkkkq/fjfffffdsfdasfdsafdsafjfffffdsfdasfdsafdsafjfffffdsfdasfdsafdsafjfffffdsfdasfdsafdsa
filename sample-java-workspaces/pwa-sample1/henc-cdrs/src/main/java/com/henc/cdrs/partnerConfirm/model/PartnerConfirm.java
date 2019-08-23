package com.henc.cdrs.partnerConfirm.model;

import java.util.List;

import com.henc.dream.domain.IBSheetDomain;

import lombok.Data;

@Data
public class PartnerConfirm extends IBSheetDomain {

    private String sendId;

    private String deptCd;
    private String deptNm;
    private String basDeptYn;
    private String userId;
    private String pwd;

    private List<PartnerConfirm> partnerConfirms;
}
