package com.henc.cdrs.partners.applicant.model;

import java.util.List;

import com.henc.dream.domain.IBSheetDomain;

import lombok.Data;

@Data
public class Applicant extends IBSheetDomain{

    private int labatdPcntNo;

    private String deptCd;
    private String partnerNo;
    private String name;
    private String ocptNo;
    private String brtdy;
    private String note;
    private String corpKor;

    private String ocptNm;
    private String bizHdofCd;

    private List<Applicant> applicants;
}