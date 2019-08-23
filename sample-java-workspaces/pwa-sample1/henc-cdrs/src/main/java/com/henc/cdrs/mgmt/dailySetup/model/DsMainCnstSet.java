package com.henc.cdrs.mgmt.dailySetup.model;

import java.util.List;

import com.henc.dream.domain.IBSheetDomain;

import lombok.Data;

@Data
public class DsMainCnstSet extends IBSheetDomain{

    private String deptCd;
    private String aplyTerm;
    private String cstkndNo;

    private int qtyNo;
    private int sortNo;

    private List<DsMainCnstSet> dsMainCnstSets;
}
