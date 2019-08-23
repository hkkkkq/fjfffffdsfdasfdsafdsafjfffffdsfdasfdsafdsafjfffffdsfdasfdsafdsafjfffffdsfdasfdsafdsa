package com.henc.cdrs.partnerConfirm.model;

import java.util.List;

import com.henc.dream.domain.IBSheetDomain;

import lombok.Data;

@Data
public class PartnerDept extends IBSheetDomain {
    
    private String deptCd;
    private String deptNm;
    private String baseDeptYn;
    private String userId;

    private List<PartnerDept> partnerDepts;
}
