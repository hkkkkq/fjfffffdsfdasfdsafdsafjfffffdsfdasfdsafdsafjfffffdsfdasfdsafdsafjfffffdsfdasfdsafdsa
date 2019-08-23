package com.henc.cdrs.daily.repository;

import com.henc.cdrs.daily.model.DdBrfg;
import com.henc.cdrs.daily.model.DdBrfgLabatd;
import com.henc.cdrs.daily.model.HistoryDailyCopyParams;
import com.henc.cdrs.daily.model.SearchDailyParams;
import com.henc.dream.util.CamelCaseMap;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 일일 보고 출역 매퍼
 */
@Mapper
public interface DdBrfgLabatdMapper {

    List<DdBrfgLabatd> dailyList(SearchDailyParams searchDailyParams);

    /**
     * 현장담당이 조회하는 해당 일자의 전체 출역인원
     * - searchDailyParams.partnerNo 에 값이 있으면 해당 협력사만 조회 할 수 있다.
     *
     * @param searchDailyParams
     * @return
     */
    List<CamelCaseMap> dailyGroupList(SearchDailyParams searchDailyParams);

    /**
     * 한화건설 출역인원 조회.
     * @param searchDailyParams
     * @return
     */
    List<CamelCaseMap> dailyGroupListForHenc(SearchDailyParams searchDailyParams);

    int insert(DdBrfgLabatd ddBrfgLabatd);

    int update(DdBrfgLabatd ddBrfgLabatd);

    int delete(DdBrfgLabatd ddBrfgLabatd);

    int insertLabatdPcnt(DdBrfgLabatd ddBrfgLabatd);

    /**
     * 생년월일 수정
     *
     * @param ddBrfgLabatd
     * @return
     */
    int updateLabatdPcnt(DdBrfgLabatd ddBrfgLabatd);

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
     * 주요 출역 퀵서치 리스트
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
    List<DdBrfgLabatd> getListInChange(DdBrfg ddBrfg);

    /**
     * 변경작성된 내역 삭제
     *
     * @param ddBrfg
     * @return
     */
    int deleteChanged(DdBrfg ddBrfg);
}
