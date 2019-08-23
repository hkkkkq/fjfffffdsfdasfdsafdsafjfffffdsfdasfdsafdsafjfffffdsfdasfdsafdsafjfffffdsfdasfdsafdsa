package com.henc.cdrs.mgmt.matrial.model;

import java.util.List;

import com.henc.dream.domain.IBSheetDomain;

import lombok.Data;

@Data
public class Matrial extends IBSheetDomain {
    private String mtilNm;
    private String useYn;
    private String bizhdofCd;

    private int mtilNo;
    private String apbtYn;
    private String spec;
    private String unit;

    private int sameMtilCnt;

    private List<Matrial> matrials;
}
