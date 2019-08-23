package com.henc.cdrs.sysm.stdcd.model;

import java.util.List;

import com.henc.dream.domain.IBSheetDomain;
import lombok.Data;

@Data
public class StdCd extends IBSheetDomain {

    private String stdCd;
    private String nmspcCd;
    private String nmspcVal;
    private String cdNote;
    private List<StdCd> stdCds;

}
