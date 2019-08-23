package com.henc.cdrs.daily.repository;

import com.henc.cdrs.daily.model.*;
import com.henc.dream.util.CamelCaseMap;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 일일 보고 회사 매퍼
 */
@Mapper
public interface DdBrfgCompMapper {

    DdBrfgComp getDaily(SearchDailyParams searchDailyParams);

    /**
     * 현장담당 해당 계약에 속한 주요업무 목록 조회
     *
     * @param searchDailyParams
     * @return
     */
    List<CamelCaseMap> dailyGroupList(SearchDailyParams searchDailyParams);

    int insert(DdBrfgComp ddBrfgComp);

    int update(DdBrfgComp ddBrfgComp);

    /**
     * 일보제출 대상 여부 저장.
     *
     * @param ddBrfgComp
     * @return
     */
    int updateObjectYN(DdBrfgComp ddBrfgComp);

    /**
     * 현장담당 공사일보 승인 또는 반려 처리.
     *
     * @param saveDailyApprovalParams
     * @return
     */
    int saveApproval(SaveDailyApprovalParams saveDailyApprovalParams);

    int delete(SearchDailyParams searchDailyParams);

    /**
     * 히스토리 복사 생성.
     *
     * @param searchDailyParams
     * @return
     * @deprecated
     */
    int copyHistoryCreate(HistoryDailyCopyParams historyDailyCopyParams);

    /**
     * 히스토리 복사본 저장.
     *
     * @param ddBrfgComp
     * @return
     */
    int copyHistoryUpdate(DdBrfgComp ddBrfgComp);


    /**
     * 변경대상에 속하는 일보 회사 조회.
     *
     * @param searchDailyParams
     * @return
     */
    List<DdBrfgComp> getListInChange(DdBrfg ddBrfg);

    /**
     * 변경작성된 내역 삭제
     *
     * @param ddBrfg
     * @return
     */
    int deleteChanged(DdBrfg ddBrfg);
}
