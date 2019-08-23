package com.henc.cdrs.partners.info.model;

import java.util.List;

import com.henc.dream.domain.IBSheetDomain;

import lombok.Data;

@Data
public class PrtnConInfo extends IBSheetDomain{

    private String deptCd;
    private String corpId;
    private String corpKor;

    private List<PrtnConInfo> prtnConInfos;
}
