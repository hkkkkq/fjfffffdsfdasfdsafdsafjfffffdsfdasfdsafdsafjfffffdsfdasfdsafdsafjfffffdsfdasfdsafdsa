package com.henc.cdrs.mgmt.dailySetup.model;

import java.util.List;

import com.henc.dream.domain.IBSheetDomain;

import lombok.Data;

@Data
public class DsMainMtilSet extends IBSheetDomain{

    private String deptCd;
    private String mtilNo;
    private String mtilNoOld;
    private String aplyTerm;

    private int sortNo;

    private List<DsMainMtilSet> dsMainMtilSets;
}
