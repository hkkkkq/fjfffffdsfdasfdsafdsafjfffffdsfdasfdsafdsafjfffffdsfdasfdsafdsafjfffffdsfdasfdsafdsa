package com.henc.cdrs.mgmt.matrial.model;

import java.util.List;

import com.henc.dream.domain.IBSheetDomain;

import lombok.Data;

@Data
public class MatrialDetl extends IBSheetDomain {

    private int mtilNo;
    private int sameMtilNo;
    private String bizhdofCd;

    private List<MatrialDetl> matrialDetls;

}