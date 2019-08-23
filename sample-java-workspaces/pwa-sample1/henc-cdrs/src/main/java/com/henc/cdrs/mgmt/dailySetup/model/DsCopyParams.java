package com.henc.cdrs.mgmt.dailySetup.model;

import com.henc.dream.domain.IBSheetDomain;

import lombok.Data;

@Data
public class DsCopyParams extends IBSheetDomain {

    /**
     * 현재 일자 조회 파라미터.
     */
    private SearchDsParams targetDayParams;

    /**
     * 복사 대상 일자 조회 파라미터.
     */
    private SearchDsParams copyDayParams;

}
