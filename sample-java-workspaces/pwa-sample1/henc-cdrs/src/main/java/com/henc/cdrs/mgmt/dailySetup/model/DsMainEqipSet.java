package com.henc.cdrs.mgmt.dailySetup.model;

import java.util.List;

import com.henc.dream.domain.IBSheetDomain;

import lombok.Data;

@Data
public class DsMainEqipSet extends IBSheetDomain{

    private String deptCd;
    private String directOperDivCd;
    private String aplyTerm;

    private int eqipNo;
    private int sortNo;

    private List<DsMainEqipSet> dsMainEqipSets;
}
