package com.henc.cdrs.mgmt.equipment.model;

import java.util.List;

import com.henc.dream.domain.IBSheetDomain;

import lombok.Data;

@Data
public class Equipment extends IBSheetDomain {

    private int    eqipNo;
    private int    sameEqipCnt;

    private String eqipNm;
    private String bizhdofCd;
    private String apbtYn;
    private String spec;
    private String unit;
    private String useYn;

    private List<Equipment> equipments;
}