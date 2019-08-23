package com.henc.cdrs.mgmt.dailySetup.model;

import java.util.List;

import com.henc.dream.domain.IBSheetDomain;

import lombok.Data;

@Data
public class DsCnstQty extends IBSheetDomain {

    private int    qtyNo;

    private String qtyNm;
    private String deptCd;
    private String spec;
    private String unit;
    private String useYn;

    private List<DsCnstQty> dsCnstqtys;




}