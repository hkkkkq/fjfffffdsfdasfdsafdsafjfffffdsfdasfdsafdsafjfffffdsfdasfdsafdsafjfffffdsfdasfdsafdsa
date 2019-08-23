package com.henc.cdrs.common.code.model;

import java.util.List;

import com.henc.dream.domain.IBSheetDomain;

import lombok.Data;

@Data
public class ComNmspcInfo extends IBSheetDomain {

    private String nmspcCd;
    private String rangCd;
    private String nmspcCnte;
    private String koNm;
    private String koAbbrNm;
    private String enNm;
    private String enAbbrNm;
    private String apbtYn;
    private String apbtDm;
    private String sopCd; 
    private List<ComNmspcInfo> comNmspcInfos; 
	
}
