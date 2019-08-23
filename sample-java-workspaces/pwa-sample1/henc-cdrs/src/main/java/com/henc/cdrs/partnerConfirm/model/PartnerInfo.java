package com.henc.cdrs.partnerConfirm.model;

import java.util.List;

import com.henc.dream.domain.IBSheetDomain;

import lombok.Data;

@Data
public class PartnerInfo extends IBSheetDomain {

    private String sendId;

    private List<PartnerInfo> partnerInfos;
}
