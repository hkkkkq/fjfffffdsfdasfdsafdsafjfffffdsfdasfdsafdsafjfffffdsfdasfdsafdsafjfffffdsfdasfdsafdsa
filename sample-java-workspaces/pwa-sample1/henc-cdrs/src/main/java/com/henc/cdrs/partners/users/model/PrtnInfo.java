package com.henc.cdrs.partners.users.model;

import java.util.List;

import com.henc.dream.domain.IBSheetDomain;

import lombok.Data;

@Data
public class PrtnInfo extends IBSheetDomain{

    private String corpKor;
    private String coprcpNo;
    private String deptCd;

    private String corpId;
    private String except;

    private List<PrtnInfo> prtnInfos;
}
