package com.henc.cdrs.mgmt.progress.model;

import java.util.List;

import com.henc.dream.domain.IBSheetDomain;

import lombok.Data;

@Data
public class Progress extends IBSheetDomain{

    private String deptCd;
    private String qtyNm;
    private String spec;
    private String unit;
    private String useYn;

    private int qtyNo;

    private List<Progress> progresses;
}
