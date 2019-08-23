package com.henc.cdrs.partners.info.model;

import java.util.List;

import com.henc.dream.domain.IBSheetDomain;

import lombok.Data;

@Data
public class Partner extends IBSheetDomain{

    private String deptCd;
    private String coprcpNo;
    private String ctrcNo;
    private String useYn;
    private String ctrcDivCd;
    private String ctrcNm;
    private String corpKor;

    private int cstkndNo;
    private int sortNo;

    private List<Partner> partners;
}
