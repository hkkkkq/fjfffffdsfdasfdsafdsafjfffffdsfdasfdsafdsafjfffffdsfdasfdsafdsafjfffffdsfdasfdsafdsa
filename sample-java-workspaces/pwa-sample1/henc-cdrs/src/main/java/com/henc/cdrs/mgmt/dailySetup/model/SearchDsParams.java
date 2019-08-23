package com.henc.cdrs.mgmt.dailySetup.model;

import com.henc.dream.domain.Domain;

import lombok.Data;

@Data
public class SearchDsParams extends Domain {

    private String deptCd;
    private String aplyDt;

}
