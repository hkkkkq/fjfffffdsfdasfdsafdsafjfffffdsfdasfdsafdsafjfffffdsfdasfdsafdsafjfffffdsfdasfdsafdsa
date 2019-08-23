package com.henc.cdrs.mgmt.dailySetup.model;

import java.util.List;

import com.henc.dream.domain.IBSheetDomain;

import lombok.Data;

@Data
public class DsEquip extends IBSheetDomain {

    private int    eqipNo;
    private int    sameEqipCnt;

    private String eqipNm;
    private String bizhdofCd;
    private String apbtYn;
    private String spec;
    private String unit;
    private String useYn;

    private List<DsEquip> dsEquips;
}