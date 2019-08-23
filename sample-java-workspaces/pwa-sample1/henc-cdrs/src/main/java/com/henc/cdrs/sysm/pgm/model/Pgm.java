package com.henc.cdrs.sysm.pgm.model;

import java.util.List;

import com.henc.dream.domain.IBSheetDomain;

import lombok.Data;

@Data
public class Pgm extends IBSheetDomain{

    private String pgmId;
    private String sysClsCd;
    private String nmspcCd;
    private String nmspcVal;
    private String pgmNote;
    private String pgmTypCd;
    private String menuYn;
    private String uriNm;
    private String mainPgmId;
    private String mainPgmNm;
    private String userSessionLang;
    private String rangCd;
    private List<PgmBtn> pgmBtns;
	
}
