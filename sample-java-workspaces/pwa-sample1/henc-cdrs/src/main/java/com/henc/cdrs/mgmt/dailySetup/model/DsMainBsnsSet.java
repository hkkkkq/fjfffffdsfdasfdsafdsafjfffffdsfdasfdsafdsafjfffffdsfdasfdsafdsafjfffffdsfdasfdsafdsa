package com.henc.cdrs.mgmt.dailySetup.model;

import java.util.List;

import com.henc.dream.domain.IBSheetDomain;

import lombok.Data;

@Data
public class DsMainBsnsSet extends IBSheetDomain{

    private String deptCd;
    private String aplyTerm;
    private String aplyFromDt;
    private String aplyToDt;

//    @DateTimeFormat(pattern = "yyyy-MM-dd")
//    private Date applyFromDt;
//
//    @DateTimeFormat(pattern = "yyyy-MM-dd")
//    private Date applyToDt;
//
//    @DateTimeFormat(pattern = "yyyy-MM-dd")
//    private Date applyTerm;

    private List<DsMainBsnsSet> dsMainBsnsSets;
}
