package com.henc.cdrs.mgmt.constType.model;

import java.util.List;

import com.henc.dream.domain.IBSheetDomain;

import lombok.Data;

@Data
public class Ocpt extends IBSheetDomain {
    private int ocptNo;
    private String bizHdofCd;
    private String useYn;
    private String ocptNm;
    private int cstkndNo;

    private List<Ocpt> ocpts;
}
