package com.henc.cdrs.daily.model;

import com.henc.dream.domain.Domain;
import lombok.Data;

import java.util.List;

/**
 * 히스토리 복사를 위한 파라미터 Model.
 */
@Data
public class HistoryDailyCopyParams extends Domain {

    /**
     * 현재 일자 조회 파라미터.
     */
    private SearchDailyParams targetDayParams;

    /**
     * 복사 대상 일자 조회 파라미터.
     */
    private SearchDailyParams copyDayParams;
}
