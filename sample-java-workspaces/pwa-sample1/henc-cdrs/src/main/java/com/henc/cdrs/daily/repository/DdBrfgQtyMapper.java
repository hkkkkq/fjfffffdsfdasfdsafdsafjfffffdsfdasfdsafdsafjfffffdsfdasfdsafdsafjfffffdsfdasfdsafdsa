package com.henc.cdrs.daily.repository;

import com.henc.cdrs.daily.model.DdBrfg;
import com.henc.cdrs.daily.model.DdBrfgQty;
import com.henc.cdrs.daily.model.HistoryDailyCopyParams;
import com.henc.cdrs.daily.model.SearchDailyParams;
import com.henc.dream.util.CamelCaseMap;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 일일 보고 물량 매퍼
 */
@Mapper
public interface DdBrfgQtyMapper {

    List<DdBrfgQty> dailyList(SearchDailyParams searchDailyParams);

    /**
     * 현장담당이 조회하는 해당 일자의 전체 공사물량 투입량 리스트
     *
     * @param searchDailyParams
     * @return
     */
    List<CamelCaseMap> dailyGroupList(SearchDailyParams searchDailyParams);

    int insert(DdBrfgQty ddBrfgQty);

    int update(DdBrfgQty ddBrfgQty);

    int delete(DdBrfgQty ddBrfgQty);

    int insertCnstQty(DdBrfgQty ddBrfgQty);

    /**
     * 계약에 속한 내용 모두 삭제.
     *
     * @param searchDailyParams
     * @return
     */
    int deleteAll(SearchDailyParams searchDailyParams);

    /**
     * 히스토리 복사 생성.
     *
     * @param searchDailyParams
     * @return
     */
    int copyHistoryCreate(HistoryDailyCopyParams historyDailyCopyParams);

    /**
     * 공사 물량 퀵서치 리스트
     *
     * @param searchDailyParams
     * @return
     */
    List<CamelCaseMap> quickSearchList(SearchDailyParams searchDailyParams);

    /**
     * 변경대상에 속하는 내역 조회.
     *
     * @param ddBrfg
     * @return
     */
    List<DdBrfgQty> getListInChange(DdBrfg ddBrfg);

    /**
     * 변경작성된 내역 삭제
     *
     * @param ddBrfg
     * @return
     */
    int deleteChanged(DdBrfg ddBrfg);
}
