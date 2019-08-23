package com.henc.cdrs.sysm.pgm.model;

import com.henc.dream.domain.IBSheetDomain;

import lombok.Data;

@Data
public class PgmBtn extends IBSheetDomain{

    private String pgmId;
    private String btnId;
    private String nmspcCd;
    private String nmspcVal;
    private String btnSeq;
    private String btnGrpId;
    private String rangCd;
}
