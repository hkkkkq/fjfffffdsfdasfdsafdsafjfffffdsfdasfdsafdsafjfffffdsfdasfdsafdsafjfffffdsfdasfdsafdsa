package com.henc.cdrs.sysm.sec.usr.model;

import com.henc.dream.domain.IBSheetDomain;
import lombok.Data;

@Data
public class ComUserSearch extends IBSheetDomain {
    private String searchDeptCd;
    private String searchUserId;
    private String searchName;
}

